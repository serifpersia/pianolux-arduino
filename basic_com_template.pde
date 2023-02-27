//import libs to for use
import processing.serial.*;
import javax.sound.midi.*;
import themidibus.*;
import static javax.swing.JOptionPane.*;
import java.util.ArrayList;
import controlP5.*;
//define midi bus
MidiBus myBus;
/*define cp5 object(visual interface object used to create
 toggles,buttons sliders etc...)
 */
ControlP5 cp5;
//serial com objects
Serial arduino;
String portName;
String comlist[] = Serial.list();

//midi com objects
// Create an ArrayList to hold the names of the MIDI devices
ArrayList<String> midilist = new ArrayList<String>();
String midiName;

void setup() {
  //window size
  size(130, 175);
  //visual lib cp5 objects
  cp5 = new ControlP5(this);
  cp5.addButton("Open")
    .setPosition(0, 75)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setSize(50, 15);
  cp5.addButton("Refresh")
    .setSize(50, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setPosition(0, 95);
  cp5.addScrollableList("midi")
    .close()
    .setPosition(0, 55)
    .setItemHeight(15)
    .setBarHeight(15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .getCaptionLabel().set("Midi Device");
  cp5.addScrollableList("comlist")
    .close()
    .setItemHeight(15)
    .setBarHeight(15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setPosition(0, 40)
    .getCaptionLabel().set("Arduino Port");
    /*
  cp5.addButton("Close")
    .setSize(50, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setPosition(0, 0);
*/
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
}
//get midi device function
void midi(int n) {
  try {
    // Set the midiName variable to the name of the selected MIDI device
    midiName = midilist.get(n);
    println("Selected midi device: " + midiName);
  }
  catch (Exception NoDevicesAvailable) {
    println("No midi devices available or selected!");
  }
}
void noteOn(int channel, int pitch, int velocity) {
  try {
    //Note to self.Setup 4 different colors for 4 different velocity ranges
    println("Note on: " + pitch);
  }
  catch (Exception e) 
  {
  }
}

void noteOff(int channel, int pitch, int velocity) {
  try {
    println("Note off: " + pitch);
  }
  catch (Exception e)
  {
  }
}
void comlist(int n) {
  portName = Serial.list()[n];
  println("Selected serialdevice: " + portName);
}
//Map function maps pitch first last note and number of leds
int MAP(int au32_IN, int au32_INmin, int au32_INmax, int au32_OUTmin, int au32_OUTmax)
{
  return ((((au32_IN - au32_INmin)*(au32_OUTmax - au32_OUTmin))/(au32_INmax - au32_INmin)) + au32_OUTmin);
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
/*
void Close() {
 Toggle toggle = (Toggle) cp5.getController("animationToggle");
 // Set the value of the toggle to false
 toggle.setValue(false);
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
 */
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
void draw() {
  background(0);
}

//Use arduino.write(); to send serial data to serial device(arduino)
