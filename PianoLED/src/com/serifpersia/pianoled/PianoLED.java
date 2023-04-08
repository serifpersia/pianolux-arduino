package com.serifpersia.pianoled;

import processing.core.PApplet;

import jssc.SerialPortException;
import jssc.SerialPortList;
import processing.core.*;
import themidibus.MidiBus;
import javax.sound.midi.*;
import javax.sound.midi.MidiDevice.Info;

import static javax.swing.JOptionPane.*;
import java.util.*;
import java.util.regex.*;
import controlP5.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.util.ArrayList;
import java.awt.Color;
import java.io.BufferedReader;

public class PianoLED extends PApplet {
	public UI ui;
	private Updater updater = new Updater();
	private Arduino arduino;

	Sequencer sequencer;
	boolean midiPlay = false;
	PianoRoll pianoRoll;

	int MAX_VALUE = 255;

	Color selectedColor;
	Color splitLeftColor = Color.RED;
	Color splitRightColor = Color.BLUE;

	Color LeftSideGColor = Color.RED;
	Color MiddleSideGColor = Color.GREEN;
	Color RightSideGColor = Color.BLUE;

	int counter = 0;
	boolean useFixedMapping = false;

	boolean BGColor = false, VelocityOn = false, RandomOn = false, SplitOn = false, GradientOn = false,
			SplashOn = false, AnimationOn = false;

	// Create an ArrayList to hold the names of the MIDI devices
	ArrayList<String> midilist = new ArrayList<String>();
	ArrayList<String> midioutlist = new ArrayList<String>();

	String portName;
	String midiName; // midi input device
	String comlist[];

	MidiBus myBusIn;

	public void settings() {
		size(UI.DEFAULT_WIDTH, UI.DEFAULT_HEIGHT + 700);
	}

	private void setDefaultSize() {
		this.getSurface().setSize(UI.DEFAULT_WIDTH, UI.DEFAULT_HEIGHT);
	}

	public void setup() {
		setDefaultSize();
		PImage icon = loadImage("PianoLED.png"); // replace with the name and extension of your icon file
		surface.setIcon(icon);
		surface.setTitle("PianoLED");

		// Centre the window
		centreWindow();

		ui = new UI(this);
		ui.buildUI();

		ui.setDefaultMode();
		selectedColor = Color.RED;
		ui.setColorWheelValue(selectedColor);

		selectedColor = ui.getColorWheelValue();

		Refresh();

		updater.checkLocalVersion();
	}

	public void centreWindow() {
		int centerX = displayWidth / 2 - width / 2;
		int centerY = displayHeight / 2 - height / 2;
		surface.setLocation(centerX, centerY);
	}

	public void setSystemFileDownload() {
	}

	public void Instructions() {
		ui.showInstructions();
	}

	// button update
	public void checkForUpdates() {
		updater.checkUpdates();
	}

	public void midi(int n) {
		try {
			// Set the midiName variable to the name of the selected MIDI device
			midiName = midilist.get(n);
			println("Selected midi input device: " + midiName);
		} catch (Exception NoDevicesAvailable) {
			println("No devices Available. plugin devices into your computer first!");
		}
	}

	public void midiout(int n) {
		try {
			println("Selected midi output device: " + midioutlist.get(n) + "(" + n + ")");

			if (pianoRoll != null) {
				pianoRoll.setOutputDevice(getMidiDeviceByName(midioutlist.get(n)));
			}
		} catch (Exception NoDevicesAvailable) {
			println("No devices Available. plugin devices into your computer first!");
		}
	}

	public void noteOn(int channel, int pitch, int velocity) {
		int notePushed;
		if (useFixedMapping) {
			notePushed = mapMidiNoteToLEDFixed(pitch, ui.getFirstNoteSelected(), ui.getLastNoteSelected(),
					ui.getStripLedNum(), 1);
		} else {
			notePushed = mapMidiNoteToLED(pitch, ui.getFirstNoteSelected(), ui.getLastNoteSelected(),
					ui.getStripLedNum(), 1);
		}

		ui.setPianoKey(pitch, 1);
		try {
			ByteArrayOutputStream message = null;

			if (!AnimationOn) {
				if (RandomOn) {
					message = arduino.commandSetColor(
							new Color((int) random(1, 250), (int) random(1, 250), (int) random(1, 250)), notePushed);
				} else if (VelocityOn) {
					message = arduino.commandVelocity(velocity, notePushed, selectedColor);
				} else if (SplitOn) {
					println("Left Side Color: " + pitch + " " + ui.getLeftMinPitch() + " " + ui.getLeftMaxPitch());
					if (pitch >= ui.getLeftMinPitch() && pitch <= ui.getLeftMaxPitch() - 1) {
						println("Left Side Color: " + pitch + " " + ui.getLeftMinPitch() + " " + ui.getLeftMaxPitch());
						message = arduino.commandSetColor(splitLeftColor, notePushed);
					} else if (pitch > ui.getLeftMaxPitch() - 1 && pitch <= ui.getRightMaxPitch()) {
						println("Right Side Color");
						message = arduino.commandSetColor(splitRightColor, notePushed);
					}
				} else if (GradientOn) {
					int numSteps = ui.getStripLedNum() - 1;
					int step = notePushed - 1;
					float ratio = (float) step / (float) numSteps;

					int startColor = LeftSideGColor.getRGB();
					int endColor = RightSideGColor.getRGB();

					int currentColor;
					if (MiddleSideGColor == Color.BLACK) {
						currentColor = lerpColor(startColor, endColor, ratio);
					} else {
						int middleColor = MiddleSideGColor.getRGB();
						float leftRatio = ratio * 0.5f;
						float rightRatio = (ratio - 0.5f) * 2f;

						int leftColor = lerpColor(startColor, middleColor, leftRatio);
						int rightColor = lerpColor(middleColor, endColor, rightRatio);

						currentColor = lerpColor(leftColor, rightColor, ratio);
					}

					message = arduino.commandSetColor(new Color(currentColor), notePushed);
				} else if (SplashOn) {
					message = arduino.commandSplash(velocity, notePushed, ui.getSplashColor().getRGB());
				} else {
					if (arduino != null)
						message = arduino.commandSetColor(selectedColor, notePushed);
				}

				if (message != null) {
					arduino.sendToArduino(message);
				}
			}
		} catch (Exception e) {
			showMessageDialog(null, "Error sending command: " + e);
			println(e);
		}
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

	public void noteOff(int channel, int pitch, int velocity) {
		int notePushed;
		if (useFixedMapping) {
			notePushed = mapMidiNoteToLEDFixed(pitch, ui.getFirstNoteSelected(), ui.getLastNoteSelected(),
					ui.getStripLedNum(), 1);
		} else {
			notePushed = mapMidiNoteToLED(pitch, ui.getFirstNoteSelected(), ui.getLastNoteSelected(),
					ui.getStripLedNum(), 1);
		}
		ui.setPianoKey(pitch, 0);
		try {
			if (!AnimationOn) {
				arduino.sendCommandKeyOff(notePushed);
			}
		} catch (Exception e) {
		}
	}

	public void comlist(int n) throws SerialPortException {
		String[] portNames = SerialPortList.getPortNames();
		portName = portNames[n];
		System.out.println("Selected serial device: " + portName);
	}

	public void disableAllModes() {
		RandomOn = false;
		VelocityOn = false;
		AnimationOn = false;
		SplitOn = false;
		GradientOn = false;
		SplashOn = false;
	}

	public void colorlist(int n) {
		selectedColor = ui.getPresetColors(n);
		ui.setColorWheelValue(selectedColor);
		println("Selected color: " + selectedColor);
	}

	public void animationlist(int n) {
		if (arduino != null)
			arduino.sendCommandAnimation(n);
	}

	public void BGColor(boolean on) {
		setLedBG(on);
	}

	private void setLedBG(boolean on) {
		println("Set BG: " + on);
		int BG_HUE = 100;
		int BG_SATURATION = 0;
		int BG_BRIGHTNESS = 20;

		if (on) {
			if (arduino != null)
				arduino.sendCommandSetBG(BG_HUE, BG_SATURATION, BG_BRIGHTNESS);
			ui.showBGControls();
		} else {
			arduino.sendCommandSetBG(0, 0, 0);
			ui.hideBGControls();
		}
	}

	public void stripDirection(boolean on) {
		arduino.sendCommandStripDirection(on ? 1 : 0, ui.getStripLedNum());
	}

	public void TeacherFollowKey(boolean on) {
		pianoRoll.setFollowKey(on);
	}

	public void Fix() {
		useFixedMapping = !useFixedMapping; // toggle the state
	}

	public void setBG() {
		int red = selectedColor.getRed();
		int green = selectedColor.getGreen();
		int blue = selectedColor.getBlue();

		float[] hsbValues = Color.RGBtoHSB(red, green, blue, null);
		int hue = (int) (hsbValues[0] * 255);
		int saturation = (int) (hsbValues[1] * 255);
		int brightness = 20;

		if (arduino != null)
			arduino.sendCommandSetBG(hue, saturation, brightness);
	}

	public void modelist(int n) {
		if (arduino != null)
			arduino.sendCommandBlackOut();

		switch (ui.getModeName(n)) {
		case "Default":
			disableAllModes();
			ui.hideAllControls();
			ui.showDefaultControls();
			ui.setDefaults(255, 127);
			break;
		case "Splash":
			disableAllModes();
			ui.hideAllControls();
			ui.showSplashControls();
			ui.setSplashDefaults(11, 110, 0, 127);
			SplashOn = true;
			break;
		case "Random":
			disableAllModes();
			ui.hideAllControls();
			ui.setDefaults(255, 127);
			ui.showRandomControls();
			RandomOn = true;
			break;
		case "Gradient":
			disableAllModes();
			ui.hideAllControls();
			ui.setDefaults(255, 127);
			ui.showGradientControls();
			GradientOn = true;
			break;
		case "Velocity":
			disableAllModes();
			ui.hideAllControls();
			ui.setDefaults(255, 127);
			ui.showVelocityControls();
			VelocityOn = true;
			break;
		case "Split":
			disableAllModes();
			ui.hideAllControls();
			ui.setDefaults(255, 127);
			ui.showSplitControls();
			SplitOn = true;
			break;
		case "Animation":
			disableAllModes();
			ui.hideAllControls();
			ui.showAnimationControls();
			ui.setAnimationDefaults(0, 127);
			AnimationOn = true;
			break;
		}
		println("Selected mode: " + ui.getModeName(n));
	}

	public void PianoRollToggle(boolean on) {
		if (on) {
			ui.showPianoRollControls();
			pianoRoll = new PianoRoll(this);
			// Centre the window
//			centreWindow();
		} else {
			togglePianoRollButton(false);
			pianoRoll.close();
			pianoRoll = null;
			ui.hidePianoRollControls();
			ui.resetPianoKeys();
			setDefaultSize();
//			centreWindow();
		}
	}

	public void Track1(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(0, on);
	}

	public void Track2(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(1, on);
	}

	public void Track3(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(2, on);
	}

	public void Track4(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(3, on);
	}

	public void Track5(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(4, on);
	}

	public void Track6(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(5, on);
	}

	public void Track7(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(6, on);
	}

	public void Track8(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(7, on);
	}

	public void Track9(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(8, on);
	}

	public void Track10(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(9, on);
	}

	public void Track11(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(10, on);
	}

	public void Track12(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(11, on);
	}

	public void Track13(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(12, on);
	}

	public void Track14(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(13, on);
	}

	public void Track15(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(14, on);
	}

	public void Track16(boolean on) {
		if (pianoRoll != null)
			pianoRoll.toggleMidiTrack(15, on);
	}

	public void Open() {

		if (ui.getButtonCaption("Open").equals("Open")) {
			try {
				myBusIn = new MidiBus(this, midiName, 0);
				println("Midi Input Port Open: " + midiName);

				arduino = new Arduino(this, portName, 115200);
				println("Arduino Port Open : " + portName);

				ui.setButtonCaption("Open", "Close");
				ui.setButtonBG("Open", Color.GREEN);

				if (arduino != null)
					arduino.sendCommandBlackOut();

				setLedBG(ui.getToggleState("BGColor"));

				if (arduino != null)
					arduino.sendCommandFadeRate((int) ui.getControllerValue("FadeOnVal"));

				stripDirection(ui.getToggleState("stripDirection"));
			} catch (Exception e) {
				println("Error opening serial port: " + e.getMessage());
			}
		} else {
			myBusIn.dispose();
			if (arduino != null) {
				arduino.sendCommandBlackOut();
				arduino.stop();
			}
			setLedBG(false);

			println("Device closed: " + portName);
			println("Device closed: " + midiName);

			ui.setButtonCaption("Open", "Open");
			ui.setButtonBG("Open", Color.BLACK);
		}
	}

//was float
	public void FadeOnVal(int value) {
		if (arduino != null)
			arduino.sendCommandFadeRate((int) value);
	}

//was float
	public void Brightness(int value) {
		if (arduino != null)
			arduino.sendCommandBrightness((int) value);
	}

	public void Refresh() {

		if (updater.getOs().contains("win")) {
			findPortNameOnWindows("Arduino");
		} else {
			findPortNameOnLinux("ttyACM");
		}
		refreshComList();
		refreshMidiList();
		refreshMidiOutList();
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
					println("Device found: " + line);
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			println("Error: " + e.getMessage());
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
					println("Device found: " + portName);
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			println("Error: " + e.getMessage());
		}
	}

	public void refreshComList() {
		comlist = SerialPortList.getPortNames();
		ui.getController(ScrollableList.class, "comlist").clear();
		ui.getController(ScrollableList.class, "comlist").addItems(comlist);

		int portIndex = Arrays.asList(comlist).indexOf(portName);
		if (portIndex >= 0) {
			ui.getController(ScrollableList.class, "comlist").setValue(portIndex);
		}
	}

	public void refreshMidiList() {
		midilist.clear();

		MidiDevice.Info[] info_midiIn = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : info_midiIn) {
			try {
				MidiDevice device = MidiSystem.getMidiDevice(info);
				if (device.getMaxTransmitters() != 0) {
					midilist.add(info.getName());
				}
				device.close();
			} catch (MidiUnavailableException e) {
				// Handle the exception
			}
		}

		int defaultValue = findDefaultMidi(midilist, Arrays.asList("piano", "midi"));
		setListWithDefault("midi", midilist, defaultValue);
	}

	public void refreshMidiOutList() {
		midioutlist.clear();

		MidiDevice.Info[] info_midiIn = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : info_midiIn) {
			try {
				MidiDevice device = MidiSystem.getMidiDevice(info);
				if (device.getMaxReceivers() != 0) {
					midioutlist.add(info.getName());
				}
				device.close();
			} catch (MidiUnavailableException e) {
				// Handle the exception
			}
		}

		int defaultValue = findDefaultMidi(midioutlist, Arrays.asList("piano"));
		setListWithDefault("midiout", midioutlist, defaultValue);
	}

	private void setListWithDefault(String listName, ArrayList<String> list, int defaultValue) {
		ui.getController(ScrollableList.class, listName).clear();
		ui.getController(ScrollableList.class, listName).addItems(list);
		ui.getController(ScrollableList.class, listName).setValue(defaultValue);
	}

	public int findDefaultMidi(List<String> values, List<String> keywords) {
		int index = 0;
		for (String value : values) {
			for (String keyword : keywords) {
				if (value.toLowerCase().contains(keyword)) {
					return index;
				}
			}
			index++;
		}
		return 0;
	}

	public void CheckForUpdate() {
		checkForUpdates();
	}

	public void setLeftSide() {
		splitLeftColor = selectedColor;
	}

	public void setRightSide() {
		splitRightColor = selectedColor;
	}

	public void setLeftSideG() {
		LeftSideGColor = selectedColor;
	}

	public void setMiddleSideG() {
		MiddleSideGColor = selectedColor;
	}

	public void setRightSideG() {
		RightSideGColor = selectedColor;
	}

	public void mousePressed() {
		ui.pianoKeyAction(mouseX, mouseY, false);
	}

	public void mouseReleased() {
		ui.pianoKeyAction(mouseX, mouseY, true);
	}

	public void keyPressed() {

		if (key == 'l') {
			PianoRollLoadMidi();
		} else if (key == ' ') {
			PianoRollPlayPause();
		} else if (key == CODED) {
			if (keyCode == UP) {
				PianoRollRewind();
			} else if (keyCode == LEFT) {
				PianoRollBackwardFragment();
			} else if (keyCode == RIGHT) {
				PianoRollForwardFragment();
			}
		}
	}

	public void PianoRollPlayPause() {
		if (pianoRoll != null)
			pianoRoll.startStop();

		togglePianoRollButton();
	}

	public void togglePianoRollButton() {
		if (pianoRoll == null || pianoRoll.isPaused()) {
			togglePianoRollButton(false);
		} else {
			togglePianoRollButton(true);
		}

	}

	public void togglePianoRollButton(boolean on) {
		Button button = ui.getController(Button.class, "PianoRollPlayPause");
		if (on) {
			button.getCaptionLabel().setText("||");
			button.setColorBackground(Color.GREEN.getRGB());
		} else {
			button.getCaptionLabel().setText(">");
			button.setColorBackground(Color.BLUE.getRGB());
		}
	}

	public void PianoRollRewind() {
		if (pianoRoll != null)
			pianoRoll.rewind();
	}

	public void PianoRollForwardFragment() {
		if (pianoRoll != null)
			pianoRoll.rewind(PianoRoll.REWIND_FRAGMENT_SEC);
	}

	public void PianoRollBackwardFragment() {
		if (pianoRoll != null)
			pianoRoll.rewind(-PianoRoll.REWIND_FRAGMENT_SEC);
	}

	public void PianoRollLoadMidi() {
		// Use a file chooser dialog box to get the MIDI file to play
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));

		// Add a file filter to only allow MIDI files
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				String filename = file.getName().toLowerCase();
				return filename.endsWith(".mid") || filename.endsWith(".midi") || file.isDirectory();
			}

			public String getDescription() {
				return "MIDI files (*.mid, *.midi)";
			}
		};
		chooser.setFileFilter(filter);

		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File midiFile = chooser.getSelectedFile();

			try {
				// Get the selected MIDI output device
				pianoRoll.stop();
				togglePianoRollButton(false);
				int midiOutIndex = (int) ui.getController(ScrollableList.class, "midiout").getValue();
				pianoRoll.setOutputDevice(getMidiDeviceByName(midioutlist.get(midiOutIndex)));
				pianoRoll.loadMidiFile(midiFile);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void ColorWheel(int rgbColor) {
		selectedColor = new Color(rgbColor);
		ui.setSplashColorsToManual();
	}

	MidiDevice getMidiDeviceByName(String deviceName) throws MidiUnavailableException {
		MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();
		for (Info info : deviceInfo) {
			if (info.getName().equalsIgnoreCase(deviceName))
				return MidiSystem.getMidiDevice(info);
		}

		return null;
	}

	public void dispose() {
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
			println("Error while exiting: " + e);
		}
		exit();
	}

	public void splashMaxLength(float value) {
		if (arduino != null)
			arduino.sendCommandSplashMaxLength((int) value);
	}

	public void splashColor(int n) {
		ui.setSplashColor(n);
	}

	int[][] Leds = new int[88][2];

	public void rightArrow() {
		if (counter >= 4) {
			return;
		}
		counter++;

		ui.setKeyboardSize(counter);
	}

	public void leftArrow() {
		if (counter <= 0) {
			return;
		}
		counter--;
		ui.setKeyboardSize(counter);
	}

	public void draw() {
		background(0);

		fill(255);
		text("Piano: " + ui.getNumPianoKeys() + " Keys", 375, 15);
		text("PianoLED: " + "v3.7", 15, 150);

		ui.drawPiano();

		if (pianoRoll != null) {
			pianoRoll.draw();
		}
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "com.serifpersia.pianoled.PianoLED" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
