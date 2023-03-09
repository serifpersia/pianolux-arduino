//PianoLED
import processing.serial.*;
import javax.sound.midi.*;
import themidibus.*;
import static javax.swing.JOptionPane.*;
import java.util.*;
import controlP5.*;
import java.io.ByteArrayOutputStream;

final static int TOP_COLOR = 255;

MidiBus myBus;
Serial arduino;
String portName;
String presetText;
// Create an ArrayList to hold the names of the MIDI devices
ArrayList<String> midilist = new ArrayList<String>();
String midiName;
String comlist[];

//Map function maps pitch first last note and number of leds
int MAP(int au32_IN, int au32_INmin, int au32_INmax, int au32_OUTmin, int au32_OUTmax)
{
  return ((((au32_IN - au32_INmin)*(au32_OUTmax - au32_OUTmin))/(au32_INmax - au32_INmin)) + au32_OUTmin);
}

int lastNoteSelected, firstNoteSelected, numberselected,
  notePushed, noteOnVelocity;

boolean VelocityOn = false, RandomOn = false, SplitOn = false, GradientOn = false, SplashOn = false, AnimationOn = false;
List m = Arrays.asList("Default", "Splash", "Random", "Gradient", "Velocity", "Split", "Animation");
int counter = 0;

void setup() {
  size(910, 160);
  surface.setTitle("PianoLEDVisualizer");
  PImage icon = loadImage("PianoLEDVisualizer.png"); // replace with the name and extension of your icon file
  surface.setIcon(icon);

  cp5 = buildUI();

  Refresh();

  cp5.getController("modelist").setValue(0);
  numberselected = 176;
  firstNoteSelected = 21;
  lastNoteSelected = 108;
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
  notePushed = MAP(pitch, firstNoteSelected, lastNoteSelected, 1, numberselected);
  Keys[pitch-21][0] = 1;
  Keys[pitch-21][1] = 1;
  try {
    ByteArrayOutputStream message = null;

    if (!AnimationOn)
    {
      //Note to self.Setup 4 different colors for 4 different velocity ranges
      println("velocity: " + velocity);
      if (RandomOn) {
        message = commandSetColor((int)random(1, 250), (int)random(1, 250), (int)random(1, 250), notePushed);
      } else if (VelocityOn) {
        // Send color based on velocity range
        if (velocity > 0 && velocity < 22) {
          message = commandSetColor(velRedL, velGreenL, velBlueL, notePushed);
        } else if (velocity > 22 && velocity < 50) {
          message = commandSetColor(velRedM, velGreenM, velBlueM, notePushed);
        } else if (velocity > 50 && velocity < 85) {
          message = commandSetColor(velRedM, velGreenM, velBlueM, notePushed);
        } else if (velocity > 85 && velocity < 128) {
          message = commandSetColor(velRedH, velGreenH, velBlueH, notePushed);
        }
      } else if (SplitOn) {
        // Send color based on velocity range
        if (pitch >= 21 && pitch <= 59) {
          println("Left Side Color");
          message = commandSetColor(splitLeftRed, splitLeftGreen, splitLeftBlue, notePushed);
        } else if (pitch >= 60 && pitch <= 174) {
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
        message = commandSplash(velocity, notePushed);
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
  notePushed = MAP(pitch, firstNoteSelected, lastNoteSelected, 1, numberselected);
  Keys[pitch-21][0] = 0;
  Keys[pitch-21][1] = 0;
  try {
    if (!AnimationOn)
    {
      sendCommandKeyOff(notePushed);
    }
    //if (FadeOn)
    //      {
    //        message = commandSplash(velocity, notePushed);
    //        arduino.write(252);
    //        arduino.write(notePushed);
    //      } else {

    //        arduino.write(notePushed);
    //        println("NoteOff: " + notePushed);
    //      }
    //    }
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

void modelist(int n) {
  sendCommandBlackOut();

  switch(n) {
  case 0: // Default
    disableAllModes();
    hideAllControls();
    showDefaultControls();
    break;
  case 1: // Splash
    disableAllModes();
    hideLegacyControls();
    showSplashControls();
    setSplashDefaults(11, 110);
    SplashOn = true;
    break;
  case 2: // Random
    disableAllModes();
    hideAllControls();
    showDefaultControls();
    RandomOn = true;
    break;
  case 3: // Gradient
    disableAllModes();
    hideAllControls();
    showDefaultControls();
    showGradientButtons();
    GradientOn = true;
    break;
  case 4: // Velocity
    disableAllModes();
    hideAllControls();
    showDefaultControls();
    showButtons();
    VelocityOn = true;
    break;
  case 5: // Split
    disableAllModes();
    hideAllControls();
    showDefaultControls();
    showSideButtons();
    SplitOn = true;
    break;
  case 6: // Animation
    disableAllModes();
    hideAllControls();
    AnimationOn = true;
    break;
  }
  println("Selected mode: " + m.get(n));
}

void Open() {
  if ( cp5.getController("Open").getCaptionLabel().getText().equals("Open")) {
    try {
      myBus = new MidiBus(this, midiName, 0);
      arduino = new Serial(this, portName, 115200);
      println("Selected Midi Port: " + midiName);
      println("Selected Serial Port: " + portName);
      cp5.getController("Open").getCaptionLabel().setText("Close");
      cp5.getController("Open").setColorBackground(color(0, 255, 0));

      sendCommandBlackOut();
      int fadeRate = (int)cp5.getController("FadeOnVal").getValue();
      sendCommandFadeRate(fadeRate);
    }
    catch (Exception NoDevicesSelected) {
      showMessageDialog(null, "Devices needed not selected or device busy!");
    }
  } else {
    try {
      myBus.dispose();
      sendCommandBlackOut();
      arduino.stop();
      println("Device closed: " + portName);
      println("Device closed: " + midiName);
      cp5.getController("Open").getCaptionLabel().setText("Open");
      cp5.getController("Open").setColorBackground(color(0, 0, 0));
    }
    catch (Exception NoDevicesOpen) {
      showMessageDialog(null, "No devices connected!");
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


boolean isPiano(String name)
{
  return name.toLowerCase().contains("piano");
}

boolean isArduino(String name)
{
  return name.toLowerCase().contains("arduino");
}

void Refresh() {
  // Clear the midilist
  midilist.clear();
  // Get the list of available MIDI devices on the system
  MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
  // Iterate through the list of MIDI devices
  for (MidiDevice.Info info : infos) {
    try {
      // Open the MIDI device
      MidiDevice device = MidiSystem.getMidiDevice(info);

      // Check if the device is an input device (i.e. it can receive MIDI messages)
      if (device.getMaxTransmitters() != 0) {
        // Add the device to the midilist ArrayList
        midilist.add(info.getName());
      }
      // Close the MIDI device
      device.close();
    }
    catch (MidiUnavailableException e) {
      // Handle the exception
    }
  }

  comlist = Serial.list();

  // Add the list of MIDI devices to the scrollable list in the GUI
  cp5.get(ScrollableList.class, "midi").clear();
  cp5.get(ScrollableList.class, "comlist").clear();
  cp5.get(ScrollableList.class, "midi").addItems(midilist);
  cp5.get(ScrollableList.class, "midi").setValue(findDefault(midilist, "piano"));
  cp5.get(ScrollableList.class, "comlist").addItems(comlist);
  cp5.get(ScrollableList.class, "comlist").setValue(findDefault(Arrays.asList(comlist), "piano"));
}

int findDefault(List<String> values, String keyword)
{
  int i = 0;
  for ( String val : values)
  {
    if ( val.toLowerCase().contains(keyword) )
    {
      return i;
    }
    i++;
  }
  return 0;
}


void dispose()
{
  myBus.dispose();
  sendCommandBlackOut();
  arduino.stop();
  exit();
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
void setL() {
  velRedL =(Red);
  velGreenL =(Green);
  velBlueL = (Blue);
}
void setLM() {
  velRedLM =(Red);
  velGreenLM =(Green);
  velBlueLM = (Blue);
}
void setM() {
  velRedM =(Red);
  velGreenM =(Green);
  velBlueM = (Blue);
}
void setH() {
  velRedH =(Red);
  velGreenH =(Green);
  velBlueH = (Blue);
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
void setRightSideG() {
  RightSideGRed = (Red);
  RightSideGGreen = (Green);
  RightSideGBlue = (Blue);
}
void setMiddleSideG() {
  MiddleSideGRed = (Red);
  MiddleSideGGreen = (Green);
  MiddleSideGBlue = (Blue);
}

public static void printArray(byte[] bytes) {
  print("Message:");
  for (byte b : bytes) {
    int unsignedValue = b & 0xFF;
    print(unsignedValue + " ");
  }
  println();
}
