package com.serifpersia.pianolux;

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
import javax.sql.rowset.serial.SerialException;

import com.serifpersia.pianolux.learn.PianoMidiConsumer;
import com.serifpersia.pianolux.learn.PianoReceiver;
import com.serifpersia.pianolux.ui.DashboardPanel;
import com.serifpersia.pianolux.ui.GetUI;
import com.serifpersia.pianolux.ui.pnl_Gradient_MultiColor;

import jssc.SerialPortException;
import jssc.SerialPortList;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;

public class PianoController implements PianoMidiConsumer {

	private PianoLux pianoLux;

	public static Arduino arduino;
	public String portName;
	public String[] portNames = SerialPortList.getPortNames();

	public static Color splitLeftColor = Color.RED;
	public static Color splitRightColor = Color.BLUE;
	public static int BG_BRIGHTNESS = 128;

	public boolean useFixedMapping = false;
	public boolean stripReverse = false; // default value
	public boolean bgToggle = false; // default value
	public boolean guideToggle = false; // default value
	public boolean use72LEDSMap = false;
	public boolean useOctaveShift = false;

	public int transposition = 0; // default value

	// Helper method to interpolate a color component value
	private int interpolateColorComponent(int start, int end, double progress) {
		return (int) (start * (1 - progress) + end * progress);
	}

	private List<PianoMidiConsumer> consumers = new ArrayList<>();
	private List<PianoMidiConsumer> sequenceConsumers = new LinkedList<>();

	private PianoReceiver pianoReceiver;

	private MidiDevice device = null;

	public PianoController(PianoLux pianoLux) {
		this.pianoLux = pianoLux;
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
		midiNote = midiNote - transposition;
		int totalNotes = highestNote - lowestNote;
		int notesPerLED = (int) Math.ceil((double) totalNotes / stripLEDNumber);
		int ledIndex = (midiNote - lowestNote) / notesPerLED + 1;

		// Ensure the LED index is within the valid range
		ledIndex = Math.max(0, Math.min(stripLEDNumber, ledIndex));

		return ledIndex;
	}

	// Map function maps pitch first last note and number of leds
	public int mapMidiNoteToLED(int midiNote, int lowestNote, int highestNote, int stripLEDNumber) {
		midiNote = midiNote - transposition;
		int outMax = stripLEDNumber;
		int mappedLED = (midiNote - lowestNote) * outMax / (highestNote - lowestNote);
		return mappedLED;
	}

	public int mapMidiNoteToLEDFixed(int midiNote, int lowestNote, int highestNote, int stripLEDNumber) {
		midiNote = midiNote - transposition;
		int outMax = stripLEDNumber;
		int mappedLED = (midiNote - lowestNote) * outMax / (highestNote - lowestNote);

		if (midiNote >= 57) {
			mappedLED -= 1;
		}

		if (midiNote >= 93) {
			mappedLED -= 1;
		}
		return mappedLED;
	}

	public void noteOn(int channel, int pitch, int velocity) {
		int notePushed = getNotePushed(pitch);
		pianoLux.setPianoKey(pitch, 1);

		if (!ModesController.AnimationOn && !ModesController.VisualizerOn) {
			try {

				switch (ModesController.getCurrentMode()) {
				case 0:
					arduino.sendCommandNoteOnWithoutColor(notePushed);
					break;
				case 1:
					arduino.sendCommandSplash(velocity, notePushed);
					break;
				case 2:
					arduino.sendToArduino(generateRandomColorMessage(notePushed));
					break;
				case 3:
					arduino.sendToArduino(generateGradientColorMessage(notePushed));
					break;
				case 4:
					arduino.sendCommandVelocity(velocity, notePushed);
					break;
				case 5:
					arduino.sendToArduino(generateSplitColorMessage(pitch, notePushed));
					break;

				case 8:
					arduino.sendToArduino(generateMultiColorMessage(pitch, notePushed));
					break;
				}
				notifyConsumers(pitch, velocity);

			} catch (Exception e) {

			}
		}

	}

	public void noteOff(int channel, int pitch, int velocity) {
		int notePushed = getNotePushed(pitch);
		pianoLux.setPianoKey(pitch, 0);

		try {
			if (!ModesController.AnimationOn && !ModesController.VisualizerOn) {
				arduino.sendCommandNoteOff(notePushed);
			}
		} catch (Exception e) {
			System.out.println("Error sending command: " + e);
		}
	}

	private int getNotePushed(int pitch) {
		if (useFixedMapping && !use72LEDSMap) {
			return mapMidiNoteToLEDFixed(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum());
		} else if (use72LEDSMap && !useFixedMapping) {
			return mapMidiNoteToLED72(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum());
		} else {
			return mapMidiNoteToLED(pitch, GetUI.getFirstNoteSelected(), GetUI.getLastNoteSelected(),
					GetUI.getStripLedNum());
		}
	}

	private ByteArrayOutputStream generateRandomColorMessage(int notePushed) {
		Random rand = new Random();
		int randomHue = rand.nextInt(360);
		return arduino.commandNoteOnWithColor(Color.getHSBColor(randomHue / 360.0f, 1.0f, 1.0f), notePushed);
	}

	private ByteArrayOutputStream generateSplitColorMessage(int pitch, int notePushed) {
		if (pitch >= GetUI.getLeftMinPitch() && pitch <= GetUI.getLeftMaxPitch() - 1) {
			return arduino.commandNoteOnWithColor(splitLeftColor, notePushed);
		} else if (pitch > GetUI.getLeftMaxPitch() - 1 && pitch <= GetUI.getRightMaxPitch()) {
			return arduino.commandNoteOnWithColor(splitRightColor, notePushed);
		}
		return null;
	}

	private ByteArrayOutputStream generateGradientColorMessage(int notePushed) {
		int numSteps = GetUI.getStripLedNum();
		int step = notePushed;

		int sideCount = ModesController.getGradientSideCount();
		int segmentSize = numSteps / sideCount;
		int segmentIndex = step / segmentSize;

		double progress = (double) (step % segmentSize) / segmentSize;

		Color currentColor = interpolateGradientColor(segmentIndex, progress);

		return arduino.commandNoteOnWithColor(currentColor, notePushed);
	}

	private ByteArrayOutputStream generateMultiColorMessage(int pitch, int notePushed) {
		// Calculate the index of the note group based on pitch
		int noteGroupIndex = (pitch - 21) % 12;

		// Use the calculated index to get the corresponding color
		Color color = GetUI.multiColors[noteGroupIndex];

		return arduino.commandNoteOnWithColor(color, notePushed);
	}

	private Color interpolateGradientColor(int segmentIndex, double progress) {
		Color[] colors = pnl_Gradient_MultiColor.colors;
		int colorCount = colors.length;

		int segmentStart = segmentIndex;
		int segmentEnd = (segmentIndex + 1) % colorCount;

		return interpolateColor(colors[segmentStart], colors[segmentEnd], progress);
	}

	private Color interpolateColor(Color start, Color end, double progress) {
		int r = interpolateColorComponent(start.getRed(), end.getRed(), progress);
		int g = interpolateColorComponent(start.getGreen(), end.getGreen(), progress);
		int b = interpolateColorComponent(start.getBlue(), end.getBlue(), progress);
		return new Color(r, g, b);
	}

	private void notifyConsumers(int pitch, int velocity) {
		for (PianoMidiConsumer consumer : consumers) {
			consumer.onPianoKeyOn(pitch, velocity);
		}
	}

	public static void FadeRate(int value) {
		arduino.sendCommandFadeRate(value);
	}

	public static void BrightnessRate(int value) {
		arduino.sendCommandBrightness(value);
	}

	public static void SplashLengthRate(int value) {
		if (arduino != null) {
			arduino.sendCommandSplashMaxLength(value);
		}
	}

	public void stripReverse(boolean on) {
		arduino.sendCommandStripDirection(on ? 1 : 0, GetUI.getStripLedNum());
	}

	public void setLedBG(boolean on) {
		int BG_HUE = 0;
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
		int red = GetUI.selectedColor.getRed();
		int green = GetUI.selectedColor.getGreen();
		int blue = GetUI.selectedColor.getBlue();

		float[] hsbValues = Color.RGBtoHSB(red, green, blue, null);
		int hue = (int) (hsbValues[0] * 255);

		if (arduino != null)
			arduino.sendCommandAnimation(n, hue);
	}

	public void setLedVisualizerEffect(int effect, int colorHue) {
		arduino.sendCommandSetLedVisualizer(effect, colorHue);
	}

	public void dispose() {
		// Dispose your app here
		try {
			if (arduino != null) {
				arduino.sendCommandBlackOut();
				// arduino.sendCommandSetBG(0, 0, 0);
				// arduino.sendCommandSetGuide(7, 0, 0, 0, 0,
				// ModesController.defaultMajorScalePattern);
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

	public static void sendGlobalColorToArduino(Color c) {
		arduino.sendCommandSetGlobalColor(c);
	}
}
