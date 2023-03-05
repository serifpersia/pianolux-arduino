//PianoLED
//Code by serifpersia
//youtube.com/serifpersia
//twitch.tv/serifpersia
//PianoLED community/dev server https://discord.com/invite/S6xmuX4Hx5
//discord serifpersia#0904
//Last modified 05/03/2023

import processing.serial.*;
import javax.sound.midi.*;
import themidibus.*;
import static javax.swing.JOptionPane.*;
import java.util.*;
import controlP5.*;
MidiBus myBus;
ControlP5 cp5;
Serial arduino;
String portName;
String presetText;
// Create an ArrayList to hold the names of the MIDI devices
ArrayList<String> midilist = new ArrayList<String>();
String midiName;
String comlist[] = Serial.list();
//Map function maps pitch first last note and number of leds
int MAP(int au32_IN, int au32_INmin, int au32_INmax, int au32_OUTmin, int au32_OUTmax)
{
  return ((((au32_IN - au32_INmin)*(au32_OUTmax - au32_OUTmin))/(au32_INmax - au32_INmin)) + au32_OUTmin);
}
int Red = 251, Green = 251, 
  Blue = 251, Brightness = 251, 
  FadeOnVal = 251, velRedL = 251, 
  velGreenL = 251, velBlueL = 251, 
  velRedLM = 251, velGreenLM = 251, 
  velBlueLM = 251, velRedM = 251, 
  velGreenM = 251, velBlueM = 251, 
  velRedH = 251, velGreenH = 251, velBlueH = 251, 
  splitLeftRed = 251, splitLeftGreen = 251, splitLeftBlue = 251, 
  splitRightRed = 251, splitRightGreen = 251, splitRightBlue = 251, 
  LeftSideGRed = 251, LeftSideGGreen = 251, LeftSideGBlue, 
  RightSideGRed = 251, RightSideGGreen = 251, RightSideGBlue;
int lastNoteSelected, firstNoteSelected, numberselected, 
  notePushed, noteOnVelocity;

boolean VelocityOn = false, RandomOn = false, SplitOn = false, GradientOn = false, AnimationOn = false, FadeOn = false;
List m = Arrays.asList("Default", "Fade", "Random", "Fade & Random", "Velocity", "Split", "Gradient", "Animation");
int counter = 0;
int[][] Keys;
int[][] Leds;
int rectASizeX = 0;
int rectBSizeX = 0;
int rectBX = 795;
// List of white keys in a 88-key piano
int whiteKeys[] = {0, 2, 3, 5, 7, 8, 10, 12, 14, 15
  , 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36, 38, 39, 41, 43
  , 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79
  , 80, 82, 84, 86, 87};
int[] blackKeys = {1, 4, 6, 9, 11, 13, 16, 18, 21, 23, 25, 28, 30, 33, 35, 
  37, 40, 42, 45, 47, 49, 52, 54, 57, 59, 61, 64, 66, 69, 71, 73, 76, 78, 
  81, 83, 85};
// Create a list of x-coordinates for each key
int[] keyXCoordinates = {11, 40, 56, 86, 101, 116, 145, 161, 191, 206, 221, 
  251, 266, 296, 311, 326, 356, 371, 401, 416, 431, 461, 476, 506, 521, 
  536, 566, 581, 611, 626, 641, 671, 686, 715, 731, 746}; // Add the missing x-coordinate
// List of white keys in a 88-key piano
int leds[] = {0, 2, 3, 5, 7, 8, 10, 12, 14, 15
  , 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36, 38, 39, 41, 43
  , 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79
  , 80, 82, 84, 86, 87};
int[] ledsBlack = {1, 4, 6, 9, 11, 13, 16, 18, 21, 23, 25, 28, 30, 33, 35, 
  37, 40, 42, 45, 47, 49, 52, 54, 57, 59, 61, 64, 66, 69, 71, 73, 76, 78, 
  81, 83, 85};
// Create a list of x-coordinates for each key
int[] keyYCoordinates = {11, 40, 56, 86, 101, 116, 145, 161, 191, 206, 221, 
  251, 266, 296, 311, 326, 356, 371, 401, 416, 431, 461, 476, 506, 521, 
  536, 566, 581, 611, 626, 641, 671, 686, 715, 731, 746}; // Add the missing x-coordinate
void setup() {
  size(910, 160);
  surface.setTitle("PianoLEDVisualizer");
  PImage icon = loadImage("PianoLEDVisualizer.png"); // replace with the name and extension of your icon file
  surface.setIcon(icon);
  Keys = new int[88][2];
  Leds = new int[88][2];
  cp5 = new ControlP5(this);

  cp5.addSlider("Brightness")
    .setPosition(800, 75)
    .setColorForeground(color(255))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setSize(99, 10)
    .setLabel("B")
    .setRange(10, 251)
    .setValue(188)
    .setNumberOfTickMarks(5)
    ;
  cp5.addSlider("Red")
    .setPosition(800, 95)
    .setCaptionLabel("R")
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setRange(0, 251)
    ;
  cp5.addSlider("Green")
    .setPosition(800, 105)
    .setCaptionLabel("G")
    .setColorForeground(color(0, 255, 0))
    .setColorBackground(color(0))
    .setColorActive(color(0, 255, 0))
    .setRange(0, 251)
    ;
  cp5.addSlider("Blue")
    .setCaptionLabel("B")
    .setPosition(800, 115)
    .setColorForeground(color(0, 0, 255))
    .setColorBackground(color(0))
    .setColorActive(color(0, 0, 255))
    .setRange(0, 251)
    ;
  cp5.addSlider("FadeOnVal")
    .setCaptionLabel("F")
    .setPosition(800, 125)
    .setColorForeground(color(0, 255, 255))
    .setColorBackground(color(0))
    .setColorActive(color(0, 255, 255))
    .setNumberOfTickMarks(10)
    .setRange(80, 15)
    .setValue(29)
    ;
  cp5.addButton("setLeftSideG")
    .setPosition(735, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set LG")
    ;
  cp5.addButton("setRightSideG")
    .setPosition(765, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set RG")
    ;
  cp5.addButton("setLeftSide")
    .setPosition(735, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set L")
    ;
  cp5.addButton("setRightSide")
    .setPosition(765, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set R")
    ;
  cp5.addButton("setL")
    .setPosition(675, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set L")
    ;
  cp5.addButton("setLM")
    .setPosition(705, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set LM");
  cp5.addButton("setM")
    .setPosition(735, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set M")
    ;
  cp5.addButton("setH")
    .setPosition(765, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set H")
    ;
  cp5.addButton("P174")
    .setPosition(200, 15)
    .setSize(60, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("174 Preset");
  cp5.addButton("Open")
    .setPosition(800, 47)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setSize(50, 15)
    ;
  cp5.addButton("Refresh")
    .setSize(50, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setPosition(850, 47)
    ;
  cp5.addScrollableList("midi")
    .close()
    .setPosition(800, 30)
    .setSize(100, 100)
    .setItemHeight(15)
    .setBarHeight(15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .getCaptionLabel().set("Midi Device");
  cp5.addScrollableList("comlist")
    .close()
    .setPosition(800, 15)
    .setSize(100, 100)
    .setItemHeight(15)
    .setBarHeight(15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .getCaptionLabel().set("Arduino Port")
    ;

  cp5.addButton("leftArrow")
    .setPosition(380, 25)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0, 0, 255))
    .setColorActive(color(255, 0, 0))
    .setLabel("<")
    ;
  cp5.addButton("rightArrow")
    .setPosition(415, 25)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0, 0, 255))
    .setColorActive(color(255, 0, 0))
    .setSize(30, 15)
    .setLabel(">");
  cp5.addButton("AdvanceUser")
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setPosition(15, 15)
    ;
  /*
  cp5.addButton("Exit")
   .setPosition(140, 90)
   .setColorForeground(color(255, 0, 0))
   .setColorBackground(color(0))
   .setColorActive(color(255, 0, 0))
   .setSize(50, 15);
   */
   
   //comment the close button before uploadin to github
   //or compiling to exe.
  cp5.addButton("Close")
   .setSize(45, 15)
   .setColorForeground(color(255, 0, 0))
   .setColorBackground(color(0))
   .setColorActive(color(255, 0, 0))
   .setPosition(650, 15);
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
  // Add the list of MIDI devices to the scrollable list in the GUI
  cp5.get(ScrollableList.class, "midi").addItems(midilist);
  cp5.get(ScrollableList.class, "comlist").addItems(comlist);

  numberselected = 176;
  firstNoteSelected = 21;
  lastNoteSelected = 108;
  println("Selected number led: " + numberselected);
  println("Selected first note: " + firstNoteSelected);
  println("Selected last note: " + lastNoteSelected);   
  ;
  cp5.addScrollableList("modelist")
    .setPosition(695, 15)
    .setSize(100, 100)
    .setBarHeight(15)
    .setItemHeight(15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .addItems(m)
    .setValue(0)
    .getCaptionLabel().set("Mode");
}
void leftArrow()
{
  if (counter <=0)
  {
    numberselected = 176;
    firstNoteSelected = 21;
    lastNoteSelected = 108;
    return;
  }
  counter--; // increment counter each time button is pressed
  println("counter: " + counter);
  if (counter == 3)
  {
    rectASizeX = 135;
    rectBSizeX+= 105;
    numberselected = 122;
    firstNoteSelected = 36;
    lastNoteSelected = 96;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 2)
  {
    rectASizeX-= 75;
    rectBSizeX+= 30;
    numberselected = 146;
    firstNoteSelected = 28;
    lastNoteSelected = 100;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 1)
  {
    rectASizeX= 60;
    rectBSizeX+= 30;
    numberselected = 152;
    firstNoteSelected = 28;
    lastNoteSelected = 103;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 0)
  {
    rectASizeX -= 60;
    rectBSizeX-=60;
    numberselected = 176;
    firstNoteSelected = 21;
    lastNoteSelected = 108;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 0)
  {
    rectASizeX = 0;
    rectBSizeX = 0;
  }
}
void rightArrow()
{
  if (counter >=4)
  {
    return;
  }
  counter++; // increment counter each time button is pressed
  println("counter: " + counter);
  if (counter == 0)
  {
    rectASizeX = 0;
    rectBSizeX = 0;
    numberselected = 176;
    firstNoteSelected = 21;
    lastNoteSelected = 108;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 1)
  {
    rectASizeX+=60;
    rectBSizeX-=45;
    numberselected = 152;
    firstNoteSelected = 28;
    lastNoteSelected = 103;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }

  if (counter == 2)
  {
    rectASizeX=+60;
    rectBSizeX-=30;
    numberselected = 146;
    firstNoteSelected = 28;
    lastNoteSelected = 100;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 3)
  {
    rectASizeX= 135;
    rectBSizeX-=30;
    numberselected = 122;
    firstNoteSelected = 36;
    lastNoteSelected = 96;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 4)
  {
    rectASizeX= 135;
    rectBSizeX-=105;
    numberselected = 98;
    firstNoteSelected = 36;
    lastNoteSelected = 84;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
}
void P174()
{
  numberselected = 174;
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
    if (!AnimationOn)
    {

      //Note to self.Setup 4 different colors for 4 different velocity ranges
      println("velocity: " + velocity);
      if (RandomOn) {
        // Send a random RGB color
        int Randomred = (int) random(1, 251);
        int Randomgreen = (int) random(1, 251);
        int Randomblue = (int) random(1, 251);

        arduino.write(255);
        arduino.write(Randomred);
        arduino.write(Randomgreen);
        arduino.write(Brightness);
        arduino.write(FadeOnVal);
        arduino.write(Randomblue);
      } else if (VelocityOn) {
        // Send color based on velocity range
        if (velocity > 0 && velocity < 22) {
          arduino.write(255);
          arduino.write(velRedL);
          arduino.write(velGreenL);
          arduino.write(Brightness);
          arduino.write(FadeOnVal);
          arduino.write(velBlueL);
        } else if (velocity > 22 && velocity < 50) {
          arduino.write(255);
          arduino.write(velRedLM);
          arduino.write(velGreenLM);
          arduino.write(Brightness);
          arduino.write(FadeOnVal);
          arduino.write(velBlueLM);
        } else if (velocity > 50 && velocity < 85) {
          arduino.write(255);
          arduino.write(velRedM);
          arduino.write(velGreenM);
          arduino.write(Brightness);
          arduino.write(FadeOnVal);
          arduino.write(velBlueM);
        } else if (velocity > 85 && velocity < 128) {
          arduino.write(255);
          arduino.write(velRedH);
          arduino.write(velGreenH);
          arduino.write(Brightness);
          arduino.write(FadeOnVal);
          arduino.write(velBlueH);
        }
      } else if (SplitOn) {
        // Send color based on velocity range
        if (pitch >= 21 && pitch <= 59) {
          println("Left Side Color");
          arduino.write(255);
          arduino.write(splitLeftRed);
          arduino.write(splitLeftGreen);
          arduino.write(Brightness);
          arduino.write(FadeOnVal);
          arduino.write(splitLeftBlue);
        } else if (pitch >= 60 && pitch <= 174) {
          println("Right Side Color");
          arduino.write(255);
          arduino.write(splitRightRed);
          arduino.write(splitRightGreen);
          arduino.write(Brightness);
          arduino.write(FadeOnVal);
          arduino.write(splitRightBlue);
        }
      } else if (GradientOn)
      {
        int numSteps = numberselected - 1;
        int step = notePushed - 1;
        float ratio = (float)step / (float)numSteps;

        int red = (int)(LeftSideGRed * (1 - ratio) + RightSideGRed * ratio);
        int green = (int)(LeftSideGGreen * (1 - ratio) + RightSideGGreen * ratio);
        int blue = (int)(LeftSideGBlue * (1 - ratio) + RightSideGBlue * ratio);

        arduino.write(255);
        arduino.write(red);
        arduino.write(green);
        arduino.write(Brightness);
        arduino.write(FadeOnVal);
        arduino.write(blue);
      } else 
      {
        // Send a fixed color
        arduino.write(255);
        arduino.write(Red);
        arduino.write(Green);
        arduino.write(Brightness);
        arduino.write(FadeOnVal);
        arduino.write(Blue);
      }
      arduino.write(notePushed);
      println("NoteOn: " + notePushed);
    }
  }
  catch (Exception e) {
    showMessageDialog(null, "Select the piano type!");
  }
}
void noteOff(int channel, int pitch, int velocity) {
  notePushed = MAP(pitch, firstNoteSelected, lastNoteSelected, 1, numberselected);
  Keys[pitch-21][0] = 0;
  Keys[pitch-21][1] = 0; 
  try {
    if (!AnimationOn)
    {
      if (FadeOn)
      {
        arduino.write(252);
        arduino.write(notePushed);
      } else {

        arduino.write(notePushed);
        println("NoteOff: " + notePushed);
      }
    }
  }
  catch (Exception e)
  {
  }
}
void comlist(int n) {
  portName = Serial.list()[n];
  println("Selected serialdevice: " + portName);
}
void setGradientButtonsVisible(boolean showGradientButtons)
{
  if (showGradientButtons) {
    cp5.getController("setLeftSideG").setVisible(true);
    cp5.getController("setRightSideG").setVisible(true);
  } else {
    cp5.getController("setLeftSideG").setVisible(false);
    cp5.getController("setRightSideG").setVisible(false);
  }
}

void setSideButtonsVisible(boolean showSideButtons)
{
  if (showSideButtons) {
    cp5.getController("setLeftSide").setVisible(true);
    cp5.getController("setRightSide").setVisible(true);
  } else {
    cp5.getController("setLeftSide").setVisible(false);
    cp5.getController("setRightSide").setVisible(false);
  }
}
void setButtonsVisible(boolean showButtons) {
  if (showButtons) {
    cp5.getController("setL").setVisible(true);
    cp5.getController("setLM").setVisible(true);
    cp5.getController("setM").setVisible(true);
    cp5.getController("setH").setVisible(true);
  } else {
    cp5.getController("setL").setVisible(false);
    cp5.getController("setLM").setVisible(false);
    cp5.getController("setM").setVisible(false);
    cp5.getController("setH").setVisible(false);
  }
}



void modelist(int n) {
  try {
    arduino.write(254);
  }

  catch(Exception e) {
  }
  switch(n) {
  case 0: // Default
    setButtonsVisible(false);
    setSideButtonsVisible(false);
    setGradientButtonsVisible(false);
    FadeOn = false;
    RandomOn = false;
    VelocityOn = false;
    AnimationOn = false;
    SplitOn = false;
    GradientOn = false;
    break;
  case 1: // Fade
    setButtonsVisible(false);
    setSideButtonsVisible(false);
    setGradientButtonsVisible(false);
    FadeOn = true;
    RandomOn = false;
    VelocityOn = false;
    AnimationOn = false;
    SplitOn = false;
    GradientOn = false;
    break;
  case 2: // Random
    setButtonsVisible(false);
    setSideButtonsVisible(false);
    setGradientButtonsVisible(false);
    RandomOn = true;
    FadeOn = false;
    VelocityOn = false;
    AnimationOn = false;
    SplitOn = false;
    GradientOn = false;
    break;
  case 3: // Fade & Random
    setButtonsVisible(false);
    setSideButtonsVisible(false);
    setGradientButtonsVisible(false);
    FadeOn = true;
    RandomOn = true;
    VelocityOn = false;
    AnimationOn = false;
    SplitOn = false;
    GradientOn = false;
    break;
  case 4: // Velocity
    setButtonsVisible(true);
    setSideButtonsVisible(false);
    setGradientButtonsVisible(false);
    VelocityOn = true;
    FadeOn = false;
    RandomOn = false;
    AnimationOn = false;
    SplitOn = false;
    GradientOn = false;
    break;
  case 5: // Split
    setButtonsVisible(false);
    setSideButtonsVisible(true);
    setGradientButtonsVisible(false);
    SplitOn = true;
    VelocityOn = false;
    FadeOn = false;
    RandomOn = false;
    AnimationOn = false;
    GradientOn = false;
    break;
  case 6: // Gradient
    setButtonsVisible(false);
    setSideButtonsVisible(false);
    setGradientButtonsVisible(true);
    GradientOn = true;
    SplitOn = false;
    VelocityOn = false;
    FadeOn = false;
    RandomOn = false;
    AnimationOn = false;

    break;
  case 7: // Animation
    setButtonsVisible(false);
    setSideButtonsVisible(false);
    setGradientButtonsVisible(false);
    AnimationOn = true;
    FadeOn = false;
    RandomOn = false;
    VelocityOn = false;
    SplitOn = false;
    GradientOn = false;
    break;
  }
  println("Selected mode: " + m.get(n));
}
void Open() {
  try {
    myBus = new MidiBus(this, midiName, 0);
    arduino = new Serial(this, portName, 9600);
    arduino.write(254);
    println("Selected Midi Port: " + midiName);
    println("Selected Serial Port: " + portName);
    showMessageDialog(null, "Connected!");
  }
  catch (Exception NoDevicesSelected) {
    showMessageDialog(null, "Devices needed not selected or device busy!");
  }
}
//comment the close void before uploadin to github
   //or compiling to exe.
void Close() {
 try {
 myBus.dispose();
 arduino.write(254);
 arduino.stop();
 println("Device closed: " + portName);
 println("Device closed: " + midiName);
 showMessageDialog(null, "Disconnected!");
 }
 catch (Exception NoDevicesOpen) {
 showMessageDialog(null, "No devices connected!");
 }
 }
void Refresh() {
  // Clear the midilist and comlist ArrayList objects
  midilist.clear();
  String[] newComList = new String[0];
  // Assign the new array to the comlist variable
  comlist = newComList;
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
  cp5.get(ScrollableList.class, "comlist").addItems(comlist);
}
/*
void Exit()
{
  try {
    arduino.write(254);
    arduino.stop();
  }
  catch (Exception e)
  {
  }
  exit();
}
*/

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
void animationLoop()
{
  try
  {
    arduino.write(253);
  }
  catch (Exception e)
  {
  }
}

void draw() {
  background(0);
  presetText = "Piano: ";
  switch (numberselected) {
  case 174:
    presetText += "174 leds ";
    break;
  }
  if (firstNoteSelected == 21 && lastNoteSelected == 108) {
    presetText += "88 Keys";
  } else if (firstNoteSelected == 28 && lastNoteSelected == 103) {
    presetText += "76 Keys";
  } else if (firstNoteSelected == 28 && lastNoteSelected == 100) {
    presetText += "73 Keys";
  } else if (firstNoteSelected == 36 && lastNoteSelected == 96) {
    presetText += "61 Keys";
  } else if (firstNoteSelected == 36 && lastNoteSelected == 84) {
    presetText += "49 Keys";
  }
  fill(255);
  text(presetText, 370, 15);
  if (AnimationOn) {
    animationLoop();
  }
  //white keys
  // Initial x-coordinate of the first key
  int x = 0;
  for (int i = 0; i < whiteKeys.length; i++) {
    if (Keys[whiteKeys[i]][0] == 1) {
      fill(255, 0, 0);
    } else {
      fill(255);
    }
    // Draw the key at the current x-coordinate
    rect(x+15, 64, 15, 70);
    fill(0);
    // Move the x-coordinate to the right by 10 pixels
    // to prepare for the next key
    x += 15;
  }
  //black keys
  for (int i = 0; i < blackKeys.length; i++) {
    if (Keys[blackKeys[i]][1] == 1) {
      fill(255, 0, 0);
    } else {
      fill(0);
    }
    // Use the x-coordinate from the list to draw each key
    rect(keyXCoordinates[i]+15, 65, 8, 40);
  }
  // led strip white keys
  int y = 0;
  for (int i = 0; i < leds.length; i++) {
    fill(Keys[whiteKeys[i]][0] == 1 ? Red : 0, Keys[whiteKeys[i]][0] == 1 ? Green : 0, Keys[whiteKeys[i]][0] == 1 ? Blue : 0);
    rect(y + 15, 54, 15, 10);
    y += 15;
  }
  // led strip black keys
  for (int i = 0; i < ledsBlack.length; i++) {
    fill(Keys[blackKeys[i]][1] == 1 ? Red : 0, Keys[blackKeys[i]][1] == 1 ? Green : 0, Keys[blackKeys[i]][1] == 1 ? Blue : 0);
    rect(keyYCoordinates[i] + 15, 54, 8, 10);
  }

  // highlight piano size L&R boxes
  fill(0, 127);
  rect(15, 64, rectASizeX, 70);
  rect(rectBX, 64, rectBSizeX, 70);
  // block led strip with black color
  fill(0);
  rect(15, 54, rectASizeX, 10);
  rect(rectBX, 54, rectBSizeX, 10);
  // color bar
  fill(Red, Green, Blue);
  rect(799, 64, 100, 10);
}
