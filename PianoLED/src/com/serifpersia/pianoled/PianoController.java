package com.serifpersia.pianoled;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import com.serifpersia.pianoled.ui.ControlsPanel;
import com.serifpersia.pianoled.ui.DashboardPanel;
import com.serifpersia.pianoled.ui.DrawPiano;
import com.serifpersia.pianoled.ui.GetUI;

import jssc.SerialPortList;
import themidibus.MidiBus;

import java.util.Random;
import processing.core.PApplet;

public class PianoController {

	PApplet pApplet = new PApplet();

	public static Arduino arduino;
	public static String portName;
	public static String[] portNames = SerialPortList.getPortNames();

	private static MidiBus myBusIn;
	
	public static String midiName;

	public static Color splitLeftColor = Color.RED;
	public static Color splitRightColor = Color.BLUE;

	public static Color LeftSideGColor = Color.RED;
	public static Color MiddleSideColor = Color.GREEN;
	public static Color RightSideGColor = Color.BLUE;

	private static MidiDevice.Info getDeviceInfo(String deviceName) {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : infos) {
			if (info.getName().equals(deviceName)) {
				return info;
			}
		}
		return null;
	}
	
	public static String[] getMidiOutDevices() {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		ArrayList<String> deviceNames = new ArrayList<String>();
		for (MidiDevice.Info info : infos) {
			MidiDevice device = null;
			try {
				device = MidiSystem.getMidiDevice(info);
				if (device.getMaxReceivers() != 0) {
					deviceNames.add(info.getName());
				}
			} catch (MidiUnavailableException ex) {
				System.err.println("Error getting MIDI device " + info.getName() + ": " + ex.getMessage());
			} finally {
				if (device != null) {
					device.close();
				}
			}
		}
		if (deviceNames.isEmpty()) {
			return new String[] { "No MIDI out devices available" };
		} else {
			return deviceNames.toArray(new String[deviceNames.size()]);
		}
	}
	

	public static String[] getMidiDevices() {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		ArrayList<String> deviceNames = new ArrayList<String>();
		for (MidiDevice.Info info : infos) {
			MidiDevice device = null;
			try {
				device = MidiSystem.getMidiDevice(info);
				if (device.getMaxTransmitters() != 0) {
					deviceNames.add(info.getName());
				}
			} catch (MidiUnavailableException ex) {
				System.err.println("Error getting MIDI device " + info.getName() + ": " + ex.getMessage());
			} finally {
				if (device != null) {
					device.close();
				}
			}
		}
		if (deviceNames.isEmpty()) {
			return new String[] { "No MIDI input devices available" };
		} else {
			return deviceNames.toArray(new String[deviceNames.size()]);
		}
	}

	public void openMidi() {
		String deviceName = (String) DashboardPanel.MidiList.getSelectedItem();
		MidiDevice device = null;
		try {
			device = MidiSystem.getMidiDevice(getDeviceInfo(deviceName));
			device.open();
			System.out.println("MIDI device " + deviceName + " opened successfully.");

			// Create a new instance of MidiBus and assign it to myBusIn
			myBusIn = new MidiBus(this, deviceName, 0);
		} catch (MidiUnavailableException ex) {
			System.err.println("Error opening MIDI device " + deviceName + ": " + ex.getMessage());
		}
	}

	public static void closeMidi() {
		String deviceName = (String) DashboardPanel.MidiList.getSelectedItem();
		MidiDevice device = null;
		try {
			device = MidiSystem.getMidiDevice(getDeviceInfo(deviceName));
			if (myBusIn != null) {
				myBusIn.dispose();
			}
			device.close();
			System.out.println("MIDI device " + deviceName + " closed successfully.");
		} catch (MidiUnavailableException ex) {
			System.err.println("Error closing MIDI device " + deviceName + ": " + ex.getMessage());
		}
	}

	public static boolean useFixedMapping = false;
	public static boolean stripReverse = false; // default value
	public static boolean bgToggle = false; // default value

	// Map function maps pitch first last note and number of leds
	public int mapMidiNoteToLED(int midiNote, int lowestNote, int highestNote, int stripLEDNumber, int outMin) {
		int outMax = outMin + stripLEDNumber - 1; // highest LED number
		int mappedLED = (midiNote - lowestNote) * (outMax - outMin) / (highestNote - lowestNote);
		return mappedLED + outMin;
	}

	public int mapMidiNoteToLEDFixed(int midiNote, int lowestNote, int highestNote, int stripLEDNumber, int outMin) {
		int outMax = outMin + stripLEDNumber - 1; // highest LED number
		int mappedLED = (midiNote - lowestNote) * (outMax - outMin) / (highestNote - lowestNote);

		if (midiNote >= 57) {
			mappedLED -= 1;
		}

		if (midiNote >= 93) {
			mappedLED -= 1;
		}
		return mappedLED + outMin;
	}

	public void noteOn(int channel, int pitch, int velocity) {
		int notePushed;
		if (useFixedMapping) {
			notePushed = mapMidiNoteToLEDFixed(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum(), 1);
		} else {
			notePushed = mapMidiNoteToLED(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum(), 1);
		}

		DrawPiano.setPianoKey(pitch, 1);
		try {
			ByteArrayOutputStream message = null;

			if (!ModesController.AnimationOn) {
				if (ModesController.RandomOn) {
					Random rand = new Random();
					message = arduino.commandSetColor(
							new Color(rand.nextInt(250) + 1, rand.nextInt(250) + 1, rand.nextInt(250) + 1), notePushed);
				} else if (ModesController.VelocityOn) {
					message = arduino.commandVelocity(velocity, notePushed, ControlsPanel.selectedColor);
				} else if (ModesController.SplitOn) {
					System.out.println("Left Side Color: " + pitch + " " + GetUI.getLeftMinPitch() + " "
							+ GetUI.getLeftMaxPitch());
					if (pitch >= GetUI.getLeftMinPitch() && pitch <= GetUI.getLeftMaxPitch() - 1) {
						System.out.println("Left Side Color: " + pitch + " " + GetUI.getLeftMinPitch() + " "
								+ GetUI.getLeftMaxPitch());
						message = arduino.commandSetColor(splitLeftColor, notePushed);
					} else if (pitch > GetUI.getLeftMaxPitch() - 1 && pitch <= GetUI.getRightMaxPitch()) {
						System.out.println("Right Side Color");
						message = arduino.commandSetColor(splitRightColor, notePushed);
					}
				} else if (ModesController.GradientOn) {
					int numSteps = GetUI.getStripLedNum() - 1;
					int step = notePushed - 1;
					float ratio = (float) step / (float) numSteps;

					int startColor = LeftSideGColor.getRGB();
					int endColor = RightSideGColor.getRGB();

					int currentColor;
					if (MiddleSideColor == Color.BLACK) {
						currentColor = pApplet.lerpColor(startColor, endColor, ratio);
					} else {
						int middleColor = MiddleSideColor.getRGB();
						float leftRatio = ratio * 0.5f;
						float rightRatio = (ratio - 0.5f) * 2f;

						int leftColor = pApplet.lerpColor(startColor, middleColor, leftRatio);
						int rightColor = pApplet.lerpColor(middleColor, endColor, rightRatio);

						currentColor = pApplet.lerpColor(leftColor, rightColor, ratio);
					}

					message = arduino.commandSetColor(new Color(currentColor), notePushed);
				} else if (ModesController.SplashOn) {
					message = arduino.commandSplash(velocity, notePushed, PianoController.getSplashColor());

				} else {
					if (arduino != null)
						message = arduino.commandSetColor(ControlsPanel.selectedColor, notePushed);
				}

				if (message != null) {
					arduino.sendToArduino(message);
				}
			}
		} catch (Exception e) {
			System.out.println("Error sending command: " + e);
		}
	}

	public void noteOff(int channel, int pitch, int velocity) {
		int notePushed;
		if (useFixedMapping) {
			notePushed = mapMidiNoteToLEDFixed(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum(), 1);
		} else {
			notePushed = mapMidiNoteToLED(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum(), 1);
		}
		DrawPiano.setPianoKey(pitch, 0);
		try {
			if (!ModesController.AnimationOn) {
				arduino.sendCommandKeyOff(notePushed);
			}
		} catch (Exception e) {
		}
	}

	public void FadeRate(int value) {
		if (arduino != null) {
			arduino.sendCommandFadeRate(value);
		}
	}

	public void BrightnessRate(int value) {
		if (arduino != null) {
			arduino.sendCommandBrightness(value);
		}
	}

	public static Color getSplashColor() {
		return new Color(ControlsPanel.selectedColor.getRGB());
	}

	public void SplashLengthRate(int value) {
		if (arduino != null) {
			arduino.sendCommandSplashMaxLength(value);
		}
	}

	public static void stripReverse(boolean on) {
		arduino.sendCommandStripDirection(on ? 1 : 0, GetUI.getStripLedNum());
	}

	public static void setLedBG(boolean on) {
		int BG_HUE = 100;
		int BG_SATURATION = 0;
		int BG_BRIGHTNESS = 20;

		if (on) {
			if (arduino != null)
				arduino.sendCommandSetBG(BG_HUE, BG_SATURATION, BG_BRIGHTNESS);
		} else {
			arduino.sendCommandSetBG(0, 0, 0);
		}
	}

	public static void setBG() {
		int red = ControlsPanel.selectedColor.getRed();
		int green = ControlsPanel.selectedColor.getGreen();
		int blue = ControlsPanel.selectedColor.getBlue();

		float[] hsbValues = Color.RGBtoHSB(red, green, blue, null);
		int hue = (int) (hsbValues[0] * 255);
		int saturation = (int) (hsbValues[1] * 255);
		int brightness = 30;

		if (arduino != null)
			arduino.sendCommandSetBG(hue, saturation, brightness);
	}

	public void animationlist(int n) {
		if (arduino != null)
			arduino.sendCommandAnimation(n);
	}

	public static void dispose() {
		// Dispose your app here
		try {
			if (myBusIn != null) {
				myBusIn.dispose();
			}

//			if (myBusOut != null) {
//				myBusOut.dispose();
//			}

			if (arduino != null) {
				arduino.sendCommandBlackOut();
				arduino.sendCommandSetBG(0, 0, 0);
				arduino.stop();
			}
		} catch (Exception e) {
			System.out.println("Error while exiting: " + e);
		}
	}
}
