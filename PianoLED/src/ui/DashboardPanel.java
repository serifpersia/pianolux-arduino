package ui;

import com.serifpersia.pianoled.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import jssc.SerialPortList;
import themidibus.MidiBus;

import javax.sound.midi.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {

	DrawPiano piano = new DrawPiano();

	private JLabel lbDashboard;
	private JLabel lbConnections;
	private JLabel lbSerialDevices;
	private JLabel lbMidiDevices;
	private JComboBox<String> SerialList;
	private JComboBox<String> MidiList;
	private JButton openButton;

	String[] portNames = SerialPortList.getPortNames();
	String portName;
	private Arduino arduino;

	private MidiBus myBusIn;
	String midiName;

	private MidiDevice.Info getDeviceInfo(String deviceName) {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : infos) {
			if (info.getName().equals(deviceName)) {
				return info;
			}
		}
		return null;
	}

	private String[] getMidiDevices() {
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

	private void openMidi() {
		String deviceName = (String) MidiList.getSelectedItem();
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

	static boolean useFixedMapping = false;
	
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

			// if (!AnimationOn) {
			// if (RandomOn) {
			// message = arduino.commandSetColor(
			// new Color((int) random(1, 250), (int) random(1, 250), (int) random(1, 250)),
			// notePushed);
			// } else if (VelocityOn) {
			// message = arduino.commandVelocity(velocity, notePushed, selectedColor);
			// } else if (SplitOn) {
			// println("Left Side Color: " + pitch + " " + ui.getLeftMinPitch() + " " +
			// ui.getLeftMaxPitch());
			// if (pitch >= ui.getLeftMinPitch() && pitch <= ui.getLeftMaxPitch() - 1) {
			// println("Left Side Color: " + pitch + " " + ui.getLeftMinPitch() + " " +
			// ui.getLeftMaxPitch());
			// message = arduino.commandSetColor(splitLeftColor, notePushed);
			// } else if (pitch > ui.getLeftMaxPitch() - 1 && pitch <=
			// ui.getRightMaxPitch()) {
			// println("Right Side Color");
			// message = arduino.commandSetColor(splitRightColor, notePushed);
			// }
			// } else if (GradientOn) {
			// int numSteps = ui.getStripLedNum() - 1;
			// int step = notePushed - 1;
			// float ratio = (float) step / (float) numSteps;
			//
			// int startColor = LeftSideGColor.getRGB();
			// int endColor = RightSideGColor.getRGB();
			//
			// int currentColor;
			// if (MiddleSideGColor == Color.BLACK) {
			// currentColor = lerpColor(startColor, endColor, ratio);
			// } else {
			// int middleColor = MiddleSideGColor.getRGB();
			// float leftRatio = ratio * 0.5f;
			// float rightRatio = (ratio - 0.5f) * 2f;
			//
			// int leftColor = lerpColor(startColor, middleColor, leftRatio);
			// int rightColor = lerpColor(middleColor, endColor, rightRatio);
			//
			// currentColor = lerpColor(leftColor, rightColor, ratio);
			// }
			//
			// message = arduino.commandSetColor(new Color(currentColor), notePushed);
			// } else if (SplashOn) {
			// message = arduino.commandSplash(velocity, notePushed,
			// ui.getSplashColor().getRGB());
			// } else {
			if (arduino != null) {
				message = arduino.commandSetColor(ControlsPanel.selectedColor, notePushed);
			}

			if (message != null) {
				arduino.sendToArduino(message);
			}
			// }
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
			// if (!AnimationOn) {
			arduino.sendCommandKeyOff(notePushed);
			// }
		} catch (Exception e) {
		}
	}

	private void closeMidi() {
		String deviceName = (String) MidiList.getSelectedItem();
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

	public DashboardPanel() {
		setBackground(new Color(21, 25, 28));
		setLayout(null);

		lbDashboard = new JLabel("Dashboard");
		lbDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lbDashboard.setForeground(Color.WHITE);
		lbDashboard.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbDashboard.setBounds(10, 5, 175, 43);
		add(lbDashboard);

		lbConnections = new JLabel("Connections");
		lbConnections.setHorizontalAlignment(SwingConstants.CENTER);
		lbConnections.setForeground(Color.WHITE);
		lbConnections.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbConnections.setBounds(0, 70, 210, 60);
		add(lbConnections);

		lbSerialDevices = new JLabel("Serial");
		lbSerialDevices.setBounds(0, 190, 210, 30);
		lbSerialDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbSerialDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbSerialDevices.setForeground(new Color(255, 255, 255));
		add(lbSerialDevices);

		SerialList = new JComboBox<>(portNames);
		SerialList.setBounds(10, 229, 200, 25);
		add(SerialList);

		lbMidiDevices = new JLabel("Midi");
		lbMidiDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbMidiDevices.setForeground(Color.WHITE);
		lbMidiDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbMidiDevices.setBounds(0, 365, 210, 30);
		add(lbMidiDevices);

		MidiList = new JComboBox<String>(getMidiDevices());
		MidiList.setBounds(10, 405, 200, 25);
		add(MidiList);

		// Open Connections Button
		openButton = new JButton("Open");
		openButton.setFont(new Font("Montserrat", Font.PLAIN, 25));
		openButton.setBounds(47, 452, 117, 41);
		openButton.setBackground(Color.WHITE);
		openButton.setForeground(Color.BLACK);
		openButton.setFocusable(false);
		openButton.setBorderPainted(false);
		openButton.setOpaque(true); // Set opaque to true
		add(openButton);
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (openButton.getText().equals("Open")) {
					try {
						portName = (String) SerialList.getSelectedItem();
						midiName = (String) MidiList.getSelectedItem();
						openMidi();
						arduino = new Arduino((PianoLED) SwingUtilities.getWindowAncestor(openButton), portName,
								115200);
						System.out.println("Serial device opened: " + portName);

						openButton.setBackground(Color.GREEN);
						openButton.setForeground(Color.WHITE);
						openButton.setText("Close");

						if (arduino != null) {
							arduino.sendCommandBlackOut();
							arduino.sendCommandFadeRate((int) 255);
						}
					} catch (Exception OpenError) {
					}
				} else {
					closeMidi();
					if (arduino != null) {
						arduino.sendCommandBlackOut();
						arduino.stop();
					}

					System.out.println("Serial device closed: " + portName);
					openButton.setBackground(Color.WHITE);
					openButton.setForeground(Color.BLACK);
					openButton.setText("Open");
				}
			}
		});
	}
}