package com.serifpersia.pianoled;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import com.serifpersia.pianoled.learn.PianoMidiConsumer;
import com.serifpersia.pianoled.learn.PianoReceiver;
import com.serifpersia.pianoled.ui.DashboardPanel;
import com.serifpersia.pianoled.ui.GetUI;
import com.serifpersia.pianoled.ui.pnl_Gradient;

import jssc.SerialPortException;
import jssc.SerialPortList;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;

public class PianoController implements PianoMidiConsumer {

	private PianoLED pianoLED;

	public Arduino arduino;
	public String portName;
	public String[] portNames = SerialPortList.getPortNames();

	public static Color splitLeftColor = Color.RED;
	public static Color splitRightColor = Color.BLUE;
	public static int BG_BRIGHTNESS = 50;

	public boolean useFixedMapping = false;
	public boolean stripReverse = false; // default value
	public boolean bgToggle = false; // default value
	public boolean guideToggle = false; // default value
	public boolean use72LEDSMap = false;

	// Helper method to interpolate a color component value
	private int interpolateColorComponent(int start, int end, double progress) {
		return (int) (start * (1 - progress) + end * progress);
	}

	private List<PianoMidiConsumer> consumers = new ArrayList<>();
	private List<PianoMidiConsumer> sequenceConsumers = new LinkedList<>();

	private PianoReceiver pianoReceiver;

	private MidiDevice device = null;

	public PianoController(PianoLED pianoLED) {
		this.pianoLED = pianoLED;
		sequenceConsumers.add(this);
	}

	public void addPianoMidiConsumer(PianoMidiConsumer consumer) {
		this.consumers.add(consumer);
	}

	public void findPortNameOnWindows(String deviceName) {
		String[] cmd = { "cmd", "/c",
				"wmic path Win32_PnPEntity where \"Caption like '%(COM%)'\" get Caption /format:table" };
		portName = null;
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains(deviceName)) {
					String[] tokens = line.split("\\s+");
					portName = tokens[tokens.length - 1].replaceAll("[()]", "");
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void refreshSerialList() {
		// Get the index of the portName in the portNames array
		int index = Arrays.asList(portNames).indexOf(portName);

		// Select the corresponding item in the SerialList JComboBox object
		if (index >= 0) {
			DashboardPanel.cbSerialDevices.setSelectedIndex(index);
		}
	}

	public void refreshMidiList() {
		ArrayList<MidiDevice.Info> deviceNames = getMidiDevices();
		int selectedIndex = -1;

		// Look for the first device that contains "midi" or "piano" in its name
		for (int i = 0; i < deviceNames.size(); i++) {
			String name = deviceNames.get(i).getName().toLowerCase();
			if (name.contains("midi") || name.contains("piano")) {
				selectedIndex = i;
				break;
			}
		}

		// Select the corresponding item in the MidiList JComboBox object
		if (selectedIndex >= 0) {
			DashboardPanel.cbMidiDevices.setSelectedIndex(selectedIndex);
		}
	}

	public void findPortNameOnLinux(String deviceName) {
		String[] cmd = { "sh", "-c", "dmesg | grep " + deviceName };
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
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public String[] getMidiOutDevices() {
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

	public ArrayList<Info> getMidiDevices() {
		Info[] infos = MidiSystem.getMidiDeviceInfo();
		ArrayList<Info> deviceNames = new ArrayList<Info>();
		for (MidiDevice.Info info : infos) {
			MidiDevice device = null;
			try {
				device = MidiSystem.getMidiDevice(info);
				if (device.getMaxTransmitters() != 0) {
					deviceNames.add(info);
				}
			} catch (MidiUnavailableException ex) {
				System.err.println("Error getting MIDI device " + info.getName() + ": " + ex.getMessage());
			} finally {
				if (device != null) {
					device.close();
				}
			}
		}
		return deviceNames;
	}

	public void openSerial() {
		String portName = DashboardPanel.cbSerialDevices.getSelectedItem().toString();
		arduino = new Arduino(null, portName, 115200); // set the baudrate to match your arduino code
		if (arduino != null) {
			arduino.sendCommandBlackOut();
			arduino.sendCommandFadeRate(255);
		}
	}

	public void closeSerial() {
		try {

			if (arduino != null) {
				arduino.sendCommandBlackOut();
				arduino.sendCommandSetBG(0, 0, 0);
				arduino.sendCommandSetGuide(7, 0, 0, 0, 0, ModesController.defaultMajorScalePattern);

			}
			arduino.serialPort.closePort();

		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	public void openMidi() {
		Info deviceInfo = (Info) DashboardPanel.cbMidiDevices.getSelectedItem();
		try {
			device = MidiSystem.getMidiDevice(deviceInfo);
			device.open();
			pianoReceiver = new PianoReceiver();
			sequenceConsumers.forEach(pianoReceiver::addConsumer);
			device.getTransmitter().setReceiver(pianoReceiver);
		} catch (MidiUnavailableException ex) {
			System.err.println("Error opening MIDI device " + deviceInfo + ": " + ex.getMessage());
		}
	}

	public void addPianoReceiverConsumer(PianoMidiConsumer consumer) {
		sequenceConsumers.add(consumer);
	}

	public MidiDevice getMidiDevice() {
		return device;
	}

	public void closeMidi() {
		Info deviceInfo = (Info) DashboardPanel.cbMidiDevices.getSelectedItem();
		MidiDevice device = null;
		try {
			device = MidiSystem.getMidiDevice(deviceInfo);
			device.close();
		} catch (MidiUnavailableException ex) {
			System.err.println("Error closing MIDI device " + deviceInfo + ": " + ex.getMessage());
		}
	}

	public int mapMidiNoteToLED72(int midiNote, int lowestNote, int highestNote, int stripLEDNumber) {
		int totalNotes = highestNote - lowestNote;
		int notesPerLED = (int) Math.ceil((double) totalNotes / stripLEDNumber);
		int ledIndex = (midiNote - lowestNote) / notesPerLED + 1;

		// Ensure the LED index is within the valid range
		ledIndex = Math.max(0, Math.min(stripLEDNumber, ledIndex));

		return ledIndex;
	}

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
		if (useFixedMapping && use72LEDSMap == false) {
			notePushed = mapMidiNoteToLEDFixed(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum(), 1);
		}

		else if (use72LEDSMap && useFixedMapping == false) {

			notePushed = mapMidiNoteToLED72(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum());
		} else {
			notePushed = mapMidiNoteToLED(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum(), 1);

		}

		pianoLED.setPianoKey(pitch, 1);
		try {
			ByteArrayOutputStream message = null;

			if (!ModesController.AnimationOn && !ModesController.VisualizerOn) {
				if (ModesController.RandomOn) {
					Random rand = new Random();
					int randomHue = rand.nextInt(360); // Generate a random hue value between 0 and 359
					message = arduino.commandSetColor(Color.getHSBColor(randomHue / 360.0f, 1.0f, 1.0f), notePushed);
				} else if (ModesController.VelocityOn) {
					message = arduino.commandVelocity(velocity, notePushed, Color.RED);
				} else if (ModesController.SplitOn) {
					if (pitch >= GetUI.getLeftMinPitch() && pitch <= GetUI.getLeftMaxPitch() - 1) {
						message = arduino.commandSetColor(splitLeftColor, notePushed);
					} else if (pitch > GetUI.getLeftMaxPitch() - 1 && pitch <= GetUI.getRightMaxPitch()) {
						message = arduino.commandSetColor(splitRightColor, notePushed);
					}
				} else if (ModesController.GradientOn) {
					int numSteps = GetUI.getStripLedNum();
					int step = notePushed;

					int sideCount = ModesController.getGradientSideCount();
					int segmentSize = numSteps / sideCount;
					int segmentIndex = step / segmentSize;

					double progress = (double) (step % segmentSize) / segmentSize;

					Color currentColor = null;

					int r, g, b;

					switch (segmentIndex) {
					case 0:
						r = interpolateColorComponent(pnl_Gradient.colors[0].getRed(), pnl_Gradient.colors[1].getRed(),
								progress);
						g = interpolateColorComponent(pnl_Gradient.colors[0].getGreen(),
								pnl_Gradient.colors[1].getGreen(), progress);
						b = interpolateColorComponent(pnl_Gradient.colors[0].getBlue(),
								pnl_Gradient.colors[1].getBlue(), progress);
						currentColor = new Color(r, g, b);
						break;
					case 1:
						r = interpolateColorComponent(pnl_Gradient.colors[1].getRed(), pnl_Gradient.colors[2].getRed(),
								progress);
						g = interpolateColorComponent(pnl_Gradient.colors[1].getGreen(),
								pnl_Gradient.colors[2].getGreen(), progress);
						b = interpolateColorComponent(pnl_Gradient.colors[1].getBlue(),
								pnl_Gradient.colors[2].getBlue(), progress);
						currentColor = new Color(r, g, b);
						break;
					case 2:
						r = interpolateColorComponent(pnl_Gradient.colors[2].getRed(), pnl_Gradient.colors[3].getRed(),
								progress);
						g = interpolateColorComponent(pnl_Gradient.colors[2].getGreen(),
								pnl_Gradient.colors[3].getGreen(), progress);
						b = interpolateColorComponent(pnl_Gradient.colors[2].getBlue(),
								pnl_Gradient.colors[3].getBlue(), progress);
						currentColor = new Color(r, g, b);
						break;
					case 3:
						r = interpolateColorComponent(pnl_Gradient.colors[3].getRed(), pnl_Gradient.colors[4].getRed(),
								progress);
						g = interpolateColorComponent(pnl_Gradient.colors[3].getGreen(),
								pnl_Gradient.colors[4].getGreen(), progress);
						b = interpolateColorComponent(pnl_Gradient.colors[3].getBlue(),
								pnl_Gradient.colors[4].getBlue(), progress);
						currentColor = new Color(r, g, b);
						break;
					case 4:
						r = interpolateColorComponent(pnl_Gradient.colors[4].getRed(), pnl_Gradient.colors[5].getRed(),
								progress);
						g = interpolateColorComponent(pnl_Gradient.colors[4].getGreen(),
								pnl_Gradient.colors[5].getGreen(), progress);
						b = interpolateColorComponent(pnl_Gradient.colors[4].getBlue(),
								pnl_Gradient.colors[5].getBlue(), progress);
						currentColor = new Color(r, g, b);
						break;
					case 5:
						r = interpolateColorComponent(pnl_Gradient.colors[5].getRed(), pnl_Gradient.colors[6].getRed(),
								progress);
						g = interpolateColorComponent(pnl_Gradient.colors[5].getGreen(),
								pnl_Gradient.colors[6].getGreen(), progress);
						b = interpolateColorComponent(pnl_Gradient.colors[5].getBlue(),
								pnl_Gradient.colors[6].getBlue(), progress);
						currentColor = new Color(r, g, b);
						break;
					case 6:
						r = interpolateColorComponent(pnl_Gradient.colors[6].getRed(), pnl_Gradient.colors[7].getRed(),
								progress);
						g = interpolateColorComponent(pnl_Gradient.colors[6].getGreen(),
								pnl_Gradient.colors[7].getGreen(), progress);
						b = interpolateColorComponent(pnl_Gradient.colors[6].getBlue(),
								pnl_Gradient.colors[7].getBlue(), progress);
						currentColor = new Color(r, g, b);
						break;
					case 7:
						r = interpolateColorComponent(pnl_Gradient.colors[7].getRed(), pnl_Gradient.colors[0].getRed(),
								progress);
						g = interpolateColorComponent(pnl_Gradient.colors[7].getGreen(),
								pnl_Gradient.colors[0].getGreen(), progress);
						b = interpolateColorComponent(pnl_Gradient.colors[7].getBlue(),
								pnl_Gradient.colors[0].getBlue(), progress);
						currentColor = new Color(r, g, b);
						break;
					}

					message = arduino.commandSetColor(currentColor, notePushed);
				} else if (ModesController.SplashOn) {
					message = arduino.commandSplash(velocity, notePushed, getSplashColor());

				} else {
					if (arduino != null)
						message = arduino.commandSetColor(GetUI.selectedColor, notePushed);
				}

				if (message != null) {
					arduino.sendToArduino(message);
				}
			}

			for (PianoMidiConsumer consumer : consumers) {
				consumer.onPianoKeyOn(pitch, velocity);
			}

		} catch (Exception e) {
			System.out.println("Error sending command: " + e);
		}
	}

	public void noteOff(int channel, int pitch, int velocity) {
		int notePushed;
		if (useFixedMapping && use72LEDSMap == false) {
			notePushed = mapMidiNoteToLEDFixed(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum(), 1);
		}

		else if (use72LEDSMap && useFixedMapping == false) {

			notePushed = mapMidiNoteToLED72(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum());
		} else {
			notePushed = mapMidiNoteToLED(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum(), 1);

		}
		pianoLED.setPianoKey(pitch, 0);
		try

		{
			if (!ModesController.AnimationOn && !ModesController.VisualizerOn) {
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

	public Color getSplashColor() {
		return new Color(GetUI.selectedColor.getRGB());
	}

	public void SplashLengthRate(int value) {
		if (arduino != null) {
			arduino.sendCommandSplashMaxLength(value);
		}
	}

	public void stripReverse(boolean on) {
		arduino.sendCommandStripDirection(on ? 1 : 0, GetUI.getStripLedNum());
	}

	public void setLedBG(boolean on) {
		int BG_HUE = 100;
		int BG_SATURATION = 0;

		if (on) {
			if (arduino != null)
				arduino.sendCommandSetBG(BG_HUE, BG_SATURATION, BG_BRIGHTNESS);
		} else {
			arduino.sendCommandSetBG(0, 0, 0);
		}
	}

	public void setBG() {
		int red = GetUI.selectedColor.getRed();
		int green = GetUI.selectedColor.getGreen();
		int blue = GetUI.selectedColor.getBlue();

		float[] hsbValues = Color.RGBtoHSB(red, green, blue, null);
		int hue = (int) (hsbValues[0] * 255);
		int saturation = (int) (hsbValues[1] * 255);
		int brightness = BG_BRIGHTNESS;

		if (arduino != null)
			arduino.sendCommandSetBG(hue, saturation, brightness);
	}

	public void setLedGuide(boolean on) {
		int red = GetUI.selectedColor.getRed();
		int green = GetUI.selectedColor.getGreen();
		int blue = GetUI.selectedColor.getBlue();

		int scaleKeyIndex = ModesController.scaleKeyLedIndex;
		int[] scalePattern = ModesController.scalePattern;
		int currentArray = ModesController.currentArray;

		float[] hsbValues = Color.RGBtoHSB(red, green, blue, null);
		int hue = (int) (hsbValues[0] * 255);
		int saturation = (int) (hsbValues[1] * 255);
		int brightness = 100;

		if (on) {
			if (arduino != null)
				arduino.sendCommandSetGuide(currentArray, hue, saturation, brightness, scaleKeyIndex, scalePattern);
		} else {
			arduino.sendCommandSetGuide(currentArray, 0, 0, 0, scaleKeyIndex, scalePattern);
		}
	}

	public void animationlist(int n) {
		if (arduino != null)
			arduino.sendCommandAnimation(n);
	}

	public void setLedVisualizerEffect(int n) {
		arduino.sendCommandSetLedVisualizer(n);
	}

	public void dispose() {
		// Dispose your app here
		try {
			if (arduino != null) {
				arduino.sendCommandBlackOut();
				arduino.sendCommandSetBG(0, 0, 0);
				arduino.sendCommandSetGuide(7, 0, 0, 0, 0, ModesController.defaultMajorScalePattern);
				arduino.stop();
			}
		} catch (Exception e) {
			System.out.println("Error while exiting: " + e);
		}
	}

	@Override
	public void onPianoKeyOn(int pitch, int velocity) {
		noteOn(0, pitch, velocity);
	}

	@Override
	public void onPianoKeyOff(int pitch) {
		noteOff(0, pitch, 0);
	}

	public void sendAudioDataToArduino(int data) {
		arduino.sendCommandAudioData(data);
	}
}
