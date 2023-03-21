import processing.serial.*; //<>// //<>//
import javax.sound.midi.*;
import themidibus.*;
import static javax.swing.JOptionPane.*;
import java.util.*;
import java.util.regex.*;
import controlP5.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.SwingUtilities;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import org.json.JSONArray;
import org.json.JSONObject;

final static int TOP_COLOR = 255;
//Map function maps pitch first last note and number of leds
int mapMidiNoteToLED(int midiNote, int lowestNote, int highestNote, int stripLEDNumber, int outMin) {
  int outMax = outMin + stripLEDNumber - 1; // highest LED number
  int mappedLED = (midiNote - lowestNote) * (outMax - outMin) / (highestNote - lowestNote);
  return mappedLED + outMin;
}
int counter = 0;
int lastNoteSelected, firstNoteSelected, numberselected,
  notePushed, noteOnVelocity;
int leftMinPitch = 21;
int leftMaxPitch;
int rightMaxPitch = 108;

boolean BGColor = false, VelocityOn = false, RandomOn = false, SplitOn = false, GradientOn = false, SplashOn = false, AnimationOn = false;
List m = Arrays.asList("Default", "Splash", "Random", "Gradient", "Velocity", "Split", "Animation");

// Create an ArrayList to hold the names of the MIDI devices
ArrayList<String> midilist = new ArrayList<String>();

String portName;
String midiName;
String comlist[];
String presetText;
String os = System.getProperty("os.name").toLowerCase();
String VersionTag;
String VersionFile;
String owner = "serifpersia";
String repo = "pianoled-arduino";
String fileName;
String downloadUrl;
String releaseUrl = String.format("https://api.github.com/repos/%s/%s/releases/latest", owner, repo);
String saveDir = System.getProperty("user.dir") + "/";
String destinationFolderPath =  System.getProperty("user.dir") + "/";
String appPath = System.getProperty("user.dir");
String getDownloadUrl(JSONObject release, String fileName) {
  JSONArray assets = release.getJSONArray("assets");
  for (int i = 0; i < assets.length(); i++) {
    JSONObject asset = assets.getJSONObject(i);
    if (asset.getString("name").equals(fileName)) {
      return asset.getString("browser_download_url");
    }
  }
  return null;
}

JSONObject latestRelease = getLatestRelease(releaseUrl);
JSONObject getLatestRelease(String url) {
  try {
    //String authToken = ""; // replace with your PAT
    URL apiLink = new URL(url);
    URLConnection conn = apiLink.openConnection();
    conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
    conn.setRequestProperty("User-Agent", "Java");
    //conn.setRequestProperty("Authorization", "token " + authToken); // set the authorization header with your PAT
    BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
    byte[] dataBuffer = new byte[1024];
    int bytesRead;
    StringBuilder responseBuilder = new StringBuilder();
    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
      responseBuilder.append(new String(dataBuffer, 0, bytesRead));
    }
    in.close();
    return new JSONObject(responseBuilder.toString());
  }
  catch (Exception e) {
    System.err.println("Error: " + e.getMessage());
    return null;
  }
}

File folder = new File(appPath);
File[] listOfFiles = folder.listFiles();
File versionFile = null;

Serial arduino;
MidiBus myBus;

void setup() {
  size(930, 160);
  surface.setTitle("PianoLED");
  PImage icon = loadImage("PianoLED.png"); // replace with the name and extension of your icon file
  surface.setIcon(icon);

  cp5 = buildUI();

  cp5.getController("modelist").setValue(0);

  Refresh();

  numberselected = 176;
  firstNoteSelected = 21;
  lastNoteSelected = 108;

  setSystemFileDownload();

  checkLocalVersion();
}

void setSystemFileDownload()
{
  if (os.contains("win")) {
    fileName = "PianoLED-windows-amd64.zip";
    println("File to download: " + fileName);
  } else {
    fileName = "PianoLED-linux-amd64.zip";
  }
  downloadUrl = getDownloadUrl(latestRelease, fileName);
}

void checkLocalVersion()
{

  for (int i = 0; i < listOfFiles.length; i++) {
    if (listOfFiles[i].isFile()) {
      String fileName = listOfFiles[i].getName();
      if (fileName.matches(".*v\\d+\\.\\d+.*")) {
        VersionTag = fileName.replaceAll(".*(v\\d+\\.\\d+).*", "$1");
        versionFile = listOfFiles[i];
        break;
      }
    }
  }
  System.out.println("VersionTag: " + VersionTag);
}

void checkForUpdates() {
  Object[] options = {"Yes", "No"};
  int result = JOptionPane.showOptionDialog(null, "Check for updates?", "Check for Updates",
    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
  if (result == JOptionPane.YES_OPTION) {
    println("Yes, check for updates");
    getLatestRelease();
    checkReleaseVersion();
  } else {
    println("No, don't check for updates");
  }
}

void downloadLatestRelease(String downloadUrl, String saveDir, String fileName, String destinationFolderPath) {
  try {
    URL url = new URL(downloadUrl);
    URLConnection conn = url.openConnection();
    conn.connect();
    int contentLength = conn.getContentLength();
    BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
    FileOutputStream out = new FileOutputStream(saveDir + fileName);
    BufferedOutputStream bout = new BufferedOutputStream(out, 1024);
    byte[] data = new byte[1024];
    int x = 0;
    int bytesRead = 0;

    // Create progress bar
    JProgressBar progressBar = new JProgressBar();
    progressBar.setStringPainted(true);

    // Create dialog to show progress bar
    JDialog dialog = new JDialog();
    dialog.add(progressBar);
    dialog.setTitle("Downloading update...");
    dialog.setSize(300, 75);
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);

    while ((bytesRead = in.read(data, 0, 1024)) >= 0) {
      bout.write(data, 0, bytesRead);
      x += bytesRead;
      int percentCompleted = (int) ((x / (float) contentLength) * 100);

      // Update progress bar
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          progressBar.setValue(percentCompleted);
        }
      }
      );
    }
    bout.close();
    in.close();
    extractZipFile(saveDir + fileName, destinationFolderPath);
    dialog.dispose(); // Close progress bar dialog
    String restartMessage = "The app has been updated to " + latestRelease.getString("tag_name") + ". Please restart PianoLED.";
    JOptionPane.showMessageDialog(null, restartMessage, "Update", JOptionPane.INFORMATION_MESSAGE);
    if (versionFile != null) { // check the flag value before deleting the version file
      boolean deleted = versionFile.delete();
      if (deleted) {
        System.out.println("Deleted version file: " + versionFile.getName());
        exit();
      } else {
        System.out.println("Failed to delete version file: " + versionFile.getName());
      }
    }
  }
  catch (IOException e) {
    System.err.println("Error: " + e.getMessage());
  }
}

void checkReleaseVersion() {

  if (latestRelease == null) {
    System.out.println("Unable to retrieve latest release information.");
    return;
  }
  if (VersionTag == null)
  {
    String message = "Unable to retrieve local app information";
    JOptionPane.showMessageDialog(null, message, "Update", JOptionPane.INFORMATION_MESSAGE);
    return;
  }

  String latestTag = latestRelease.getString("tag_name");
  if (latestTag.equals(VersionTag)) {
    String message = "No need to update, you are using the latest " + latestTag + " version of PianoLED.";
    JOptionPane.showMessageDialog(null, message, "Update", JOptionPane.INFORMATION_MESSAGE);
  } else {
    Object[] options = {"Update", "Cancel"};
    int result = JOptionPane.showOptionDialog(null, "A new version of PianoLED is available. Do you want to update?", "Update Available",
      JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    if (result == JOptionPane.YES_OPTION) {
      downloadLatestRelease(downloadUrl, saveDir, fileName, destinationFolderPath);
    }
  }
}

void getLatestRelease()
{
  System.out.println("Latest release tag: " + latestRelease.getString("tag_name"));
}


void extractZipFile(String zipFilePath, String destinationFolderPath) {
  try {
    ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
    ZipEntry zipEntry = zipInputStream.getNextEntry();
    byte[] buffer = new byte[1024];

    while (zipEntry != null) {
      String fileName = zipEntry.getName();
      File newFile = new File(destinationFolderPath + fileName);
      System.out.println("Extracting file: " + newFile.getAbsolutePath());

      if (zipEntry.isDirectory()) {
        // Create the directory
        newFile.mkdirs();
      } else {
        // Create all non-existing parent directories
        new File(newFile.getParent()).mkdirs();

        // Write the file contents
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zipInputStream.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }
        fos.close();
      }

      zipEntry = zipInputStream.getNextEntry();
    }

    zipInputStream.closeEntry();
    zipInputStream.close();

    System.out.println("Zip file extracted to: " + destinationFolderPath);

    // Delete the zip file
    File zipFile = new File(zipFilePath);
    if (zipFile.delete()) {
      System.out.println("Zip file deleted successfully");
    } else {
      System.err.println("Failed to delete zip file");
    }
  }
  catch (IOException e) {
    System.err.println("Error extracting zip file: " + e.getMessage());
  }
}

void midi(int n) {
  try {
    // Set the midiName variable to the name of the selected MIDI device
    midiName = midilist.get(n);
    println("Selected midi device: " + midiName);
  }
  catch (Exception NoDevicesAvailable) {
    println("No devices Available. plugin devices into your computer first!");
  }
}

void noteOn(int channel, int pitch, int velocity) {
  notePushed = mapMidiNoteToLED(pitch, firstNoteSelected, lastNoteSelected, numberselected,1);
  Keys[pitch-21][0] = 1;
  Keys[pitch-21][1] = 1;
  try {
    ByteArrayOutputStream message = null;

    if (!AnimationOn)
    {
      if (RandomOn) {
        message = commandSetColor((int)random(1, 250), (int)random(1, 250), (int)random(1, 250), notePushed);
      } else if (VelocityOn) {
        message = commandVelocity(velocity, notePushed, Red, Green, Blue);
      } else if (SplitOn) {
        if (pitch >= leftMinPitch && pitch <= leftMaxPitch-1) {
          println("Left Side Color");
          message = commandSetColor(splitLeftRed, splitLeftGreen, splitLeftBlue, notePushed);
        } else if (pitch > leftMaxPitch-1  && pitch <= rightMaxPitch) {
          println("Right Side Color");
          message = commandSetColor(splitRightRed, splitRightGreen, splitRightBlue, notePushed);
        }
      } else if (GradientOn)
      {
        int numSteps = numberselected - 1;
        int step = notePushed - 1;
        float ratio = (float)step / (float)numSteps;

        color startColor = color(LeftSideGRed, LeftSideGGreen, LeftSideGBlue);
        color endColor = color(RightSideGRed, RightSideGGreen, RightSideGBlue);

        color currentColor;
        if (MiddleSideGRed == 0 && MiddleSideGGreen == 0 && MiddleSideGBlue == 0)
        {
          currentColor = lerpColor(startColor, endColor, ratio);
        } else
        {
          color middleColor = color(MiddleSideGRed, MiddleSideGGreen, MiddleSideGBlue);
          float leftRatio = ratio * 0.5f;
          float rightRatio = (ratio - 0.5f) * 2f;

          color leftColor = lerpColor(startColor, middleColor, leftRatio);
          color rightColor = lerpColor(middleColor, endColor, rightRatio);

          currentColor = lerpColor(leftColor, rightColor, ratio);
        }

        int red = (int)red(currentColor);
        int green = (int)green(currentColor);
        int blue = (int)blue(currentColor);

        message = commandSetColor(red, green, blue, notePushed);
      } else if (SplashOn)
      {
        message = commandSplash(velocity, notePushed, getSplashColor());
      } else
      {
        message = commandSetColor(Red, Green, Blue, notePushed);
      }

      if ( message != null )
      {
        sendToArduino(message);
      }
    }
  }
  catch (Exception e) {
    showMessageDialog(null, "Error sending command: "+e);
    println(e);
  }
}

public static void printMessage(ByteArrayOutputStream msg) {
  byte[] bytes = msg.toByteArray();
  print("Message:");
  for (byte b : bytes) {
    int unsignedValue = b & 0xFF;
    print(unsignedValue + " ");
  }
  println();
}

void noteOff(int channel, int pitch, int velocity) {
  notePushed = mapMidiNoteToLED(pitch, firstNoteSelected, lastNoteSelected, numberselected,1);
  Keys[pitch-21][0] = 0;
  Keys[pitch-21][1] = 0;
  try {
    if (!AnimationOn)
    {
      sendCommandKeyOff(notePushed);
    }
  }
  catch (Exception e)
  {
  }
}

void comlist(int n) {
  portName = Serial.list()[n];
  println("Selected serial device: " + portName);
}

void disableAllModes()
{
  RandomOn = false;
  VelocityOn = false;
  AnimationOn = false;
  SplitOn = false;
  GradientOn = false;
  SplashOn = false;
}

void colorlist(int n) {
  if (cp5 != null) {
    int selectedColor = presetColors[n];

    Red = round(red(selectedColor));
    Green = round(green(selectedColor));
    Blue = round(blue(selectedColor));

    cp5.get(ColorWheel.class, "Color").setRGB(selectedColor);


    println("Selected color: " + colorNames.get(n));
    println("RGB values: " + red(selectedColor) + ", " + green(selectedColor) + ", " + blue(selectedColor));
  } else {
    println("cp5 object is null");
  }
}

void animationlist(int n) {
  String selectedAnimation = animationNames.get(n);
  println("Selected Animation: " + selectedAnimation);

  // Select animation based on index
  switch (n) {
  case 0:
    // Select Animation 1
    sendCommandAnimation(0);
    break;
  case 1:
    // Select Animation 2
    sendCommandAnimation(1);
    break;
  case 2:
    // Select Animation 3
    sendCommandAnimation(2);
    break;
  case 3:
    // Select Animation 4
    sendCommandAnimation(3);
    break;
  case 4:
    // Select Animation 4
    sendCommandAnimation(4);
    break;
  case 5:
    // Select Animation 4
    sendCommandAnimation(5);
    break;
  case 6:
    // Select Animation 4
    sendCommandAnimation(6);
    break;
  default:
    println("Invalid animation selection.");
    break;
  }
}


void BGColor(boolean on)
{
  println("Set BG: "+on);
  int BG_HUE = 100;
  int BG_SATURATION = 0;
  int BG_BRIGHTNESS = 20;

  if (on) {
    sendCommandSetBG(BG_HUE, BG_SATURATION, BG_BRIGHTNESS);
    showBGControls();
  } else {
    sendCommandSetBG(0, 0, 0);
    hideBGControls();
  }
}

void stripDirection(boolean on) {
  sendCommandStripDirection(on ? 1 : 0);
}

void setBG() {
  int red = Red; // Red value from 0-255
  int green = Green; // Green value from 0-255
  int blue = Blue; // Blue value from 0-255

  float[] hsbValues = java.awt.Color.RGBtoHSB(red, green, blue, null);
  int hue = (int)(hsbValues[0] * 255);
  int saturation = (int)(hsbValues[1] * 255);
  int brightness = 20;

  sendCommandSetBG(hue, saturation, brightness);
}

void modelist(int n) {
  if (cp5 != null) {
    sendCommandBlackOut();

    switch(n) {
    case 0: // Default
      disableAllModes();
      hideAllControls();
      showDefaultControls();
      setDefaultDefaults(255, 127);
      break;
    case 1: // Splash
      disableAllModes();
      hideAllControls();
      showSplashControls();
      setSplashDefaults(11, 110, 0, 127);
      SplashOn = true;
      break;
    case 2: // Random
      disableAllModes();
      hideAllControls();
      setDefaultDefaults(255, 127);
      showRandomControls();
      RandomOn = true;
      break;
    case 3: // Gradient
      disableAllModes();
      hideAllControls();
      setDefaultDefaults(255, 127);
      showGradientControls();
      GradientOn = true;
      break;
    case 4: // Velocity
      disableAllModes();
      hideAllControls();
      setDefaultDefaults(255, 127);
      showVelocityControls();
      VelocityOn = true;
      break;
    case 5: // Split
      disableAllModes();
      hideAllControls();
      setDefaultDefaults(255, 127);
      showSplitControls();
      SplitOn = true;
      break;
    case 6: // Animation
      disableAllModes();
      hideAllControls();
      showAnimationControls();
      setAnimationDefaults(0, 127);
      AnimationOn = true;
      break;
    }
    println("Selected mode: " + m.get(n));
  } else {
    println("cp5 object is null");
  }
}

void Open() {

  if ( cp5.getController("Open").getCaptionLabel().getText().equals("Open")) {
    try {
      myBus = new MidiBus(this, midiName, 0);
      println("Midi Port Open: " + midiName);
      arduino = new Serial(this, portName, 115200);
      println("Serial Port Open : " + portName);
      cp5.getController("Open").getCaptionLabel().setText("Close");
      cp5.getController("Open").setColorBackground(color(0, 255, 0));

      sendCommandBlackOut();
      Toggle bg = (Toggle)cp5.getController("BGColor");
      BGColor(bg.getState());
      int fadeRate = (int)cp5.getController("FadeOnVal").getValue();
      sendCommandFadeRate(fadeRate);
      Toggle sd = (Toggle)cp5.getController("stripDirection");
      stripDirection(sd.getState());
    }
    catch (Exception e) {
      println("Error opening serial port: " + e.getMessage());
    }
  } else {
    if (arduino != null) {
      myBus.dispose();
      sendCommandBlackOut();
      BGColor(false);
      arduino.stop();
      println("Device closed: " + portName);
      println("Device closed: " + midiName);
      cp5.getController("Open").getCaptionLabel().setText("Open");
      cp5.getController("Open").setColorBackground(color(0, 0, 0));
    }
  }
}

//was float
void FadeOnVal(int value)
{
  sendCommandFadeRate((int)value);
}
//was float
void Brightness(int value)
{
  sendCommandBrightness((int)value);
}



void Refresh() {

  if (os.contains("win")) {
    findPortNameOnWindows("Arduino");
  } else {
    findPortNameOnLinux("ttyACM");
  }
  refreshComList();
  refreshMidiList();
}

void findPortNameOnWindows(String deviceName) {
  String[] cmd = {"cmd", "/c", "wmic path Win32_PnPEntity where \"Caption like '%(COM%)'\" get Caption /format:table"};
  portName = null;
  try {
    Process p = Runtime.getRuntime().exec(cmd);
    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.contains(deviceName)) {
        String[] tokens = line.split("\\s+");
        portName = tokens[tokens.length - 1].replaceAll("[()]", "");
        println("Device found: " + line);
        break;
      }
    }
    reader.close();
  }
  catch (IOException e) {
    println("Error: " + e.getMessage());
  }
}

void findPortNameOnLinux(String deviceName) {
  String[] cmd = {"sh", "-c", "dmesg | grep " + deviceName};
  portName = null;
  Pattern pattern = Pattern.compile(deviceName + "(\\d+)");
  try {
    Process p = Runtime.getRuntime().exec(cmd);
    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      Matcher matcher = pattern.matcher(line);
      if (matcher.find()) {
        portName = "/dev/" + matcher.group(0);
        println("Device found: " + portName);
        break;
      }
    }
    reader.close();
  }
  catch (IOException e) {
    println("Error: " + e.getMessage());
  }
}

void refreshComList() {
  comlist = Serial.list();
  cp5.get(ScrollableList.class, "comlist").clear();
  cp5.get(ScrollableList.class, "comlist").addItems(comlist);

  int portIndex = Arrays.asList(comlist).indexOf(portName);
  if (portIndex >= 0) {
    cp5.get(ScrollableList.class, "comlist").setValue(portIndex);
  }
}


void refreshMidiList() {
  midilist.clear();
  MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
  for (MidiDevice.Info info : infos) {
    try {
      MidiDevice device = MidiSystem.getMidiDevice(info);
      if (device.getMaxTransmitters() != 0) {
        midilist.add(info.getName());
      }
      device.close();
    }
    catch (MidiUnavailableException e) {
      // Handle the exception
    }
  }
  cp5.get(ScrollableList.class, "midi").clear();
  cp5.get(ScrollableList.class, "midi").addItems(midilist);
  cp5.get(ScrollableList.class, "midi").setValue(findDefaultMidi(midilist, Arrays.asList("piano", "midi")));
}

int findDefaultMidi(List<String> values, List<String> keywords) {
  int index = 0;
  for ( String value : values )
  {
    for ( String keyword : keywords )
    {
      if (value.toLowerCase().contains(keyword) )
      {
        return index;
      }
    }
    index++;
  }
  return 0;
}

void AdvanceUser()
{
  try {
    String inputFirsKey = showInputDialog("First key? (A0(21),E1(28),C2(36),C2(36),etc...)");
    int firstKey = Integer.parseInt(inputFirsKey);
    firstNoteSelected = firstKey;
    println("First note: " + firstKey);

    String inputLastKey = showInputDialog("Last key? (C8(108),G7(103),C7(96)C6(84),etc...)");
    int lastKey = Integer.parseInt(inputLastKey);
    lastNoteSelected = lastKey;
    println("Last note: " + lastKey);

    String inputNumberLEDS = showInputDialog("Number of LEDS? (176,152,122,98,etc...)");
    int ledNum = Integer.parseInt(inputNumberLEDS);
    numberselected = ledNum;
    println("Number of LEDS: " + ledNum);
  }
  catch (Exception e)
  {
  }
}

void CheckForUpdate()
{
  checkForUpdates();
}

void setLeftSide() {
  splitLeftRed =(Red);
  splitLeftGreen =(Green);
  splitLeftBlue = (Blue);
}
void setRightSide() {
  splitRightRed =(Red);
  splitRightGreen =(Green);
  splitRightBlue = (Blue);
}
void setLeftSideG() {
  LeftSideGRed = (Red);
  LeftSideGGreen = (Green);
  LeftSideGBlue = (Blue);
}
void setMiddleSideG() {
  MiddleSideGRed = (Red);
  MiddleSideGGreen = (Green);
  MiddleSideGBlue = (Blue);
}
void setRightSideG() {
  RightSideGRed = (Red);
  RightSideGGreen = (Green);
  RightSideGBlue = (Blue);
}

void mousePressed() {
  for (int i = 0; i < whiteKeys.length; i++) {
    // Check if the mouse click was inside a white key
    if (mouseX > i*15 + 15 && mouseX < (i+1)*15 + 15 && mouseY > 64 && mouseY < 134) {
      leftMaxPitch = whiteKeyPitches[i];
      Keys[whiteKeys[i]][0] = 1;
      println("Left Max Pitch: " + leftMaxPitch);
    }
  }
}


void mouseReleased() {
  for (int i = 0; i < whiteKeys.length; i++) {
    // Check if the mouse click was inside a white key
    if (mouseX > i*15 + 15 && mouseX < (i+1)*15 + 15 && mouseY > 64 && mouseY < 134) {
      // Change the color of the clicked key back to white
      Keys[whiteKeys[i]][0] = 0;
    }
  }
}


public static void printArray(byte[] bytes) {
  print("Message:");
  for (byte b : bytes) {
    int unsignedValue = b & 0xFF;
    print(unsignedValue + " ");
  }
  println();
}

void dispose() {
  try {
    sendCommandBlackOut();
    sendCommandSetBG(0, 0, 0);
    if (myBus != null) {
      myBus.dispose();
    }
    if (arduino != null) {
      arduino.stop();
    }
  }
  catch (Exception e) {
    println("Error while exiting: " + e);
  }
  exit();
}
