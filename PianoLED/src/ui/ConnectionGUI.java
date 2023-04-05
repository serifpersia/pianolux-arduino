package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import javax.sound.midi.*;
import themidibus.MidiBus;

public class ConnectionGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> serialPorts;
	private JComboBox<String> midiDevices;
	private MidiBus myBusIn;

	private volatile SerialPort serialPort;

	private JButton openButton;
	private JButton closeButton;

	public ConnectionGUI() {
		// Set up the window
		setTitle("Connection GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 150);

		// Set up the components
		JLabel serialLabel = new JLabel("Serial device:");
		JLabel midiLabel = new JLabel("MIDI device:");
		serialPorts = new JComboBox<String>(getSerialPorts());
		midiDevices = new JComboBox<String>(getMidiDevices());
		openButton = new JButton("Open");
		closeButton = new JButton("Close");

		// Add action listeners to the buttons
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSerial();
				openMidi();
			}
		});
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeSerial();
				closeMidi();
			}
		});

		// Set up the layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		add(serialLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(serialPorts, c);
		c.gridx = 0;
		c.gridy = 1;
		add(midiLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(midiDevices, c);
		c.gridx = 0;
		c.gridy = 2;
		add(openButton, c);
		c.gridx = 1;
		c.gridy = 2;
		add(closeButton, c);
	}

	private String[] getSerialPorts() {
		String[] portNames = SerialPortList.getPortNames();
		if (portNames.length == 0) {
			return new String[] { "No serial ports available" };
		} else {
			return portNames;
		}
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

	private void openSerial() {
		String portName = (String) serialPorts.getSelectedItem();
		this.serialPort = new SerialPort(portName);
		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			System.out.println("Serial port " + portName + " opened successfully.");
		} catch (Exception ex) {
			System.err.println("Error opening serial port " + portName + ": " + ex.getMessage());
		}
	}

	private void closeSerial() {
		if (serialPort == null) {
			System.err.println("Serial port is not open.");
			return;
		}
		String portName = serialPort.getPortName();
		try {
			serialPort.closePort();
			System.out.println("Serial port " + portName + " closed successfully.");
		} catch (SerialPortException ex) {
			System.err.println("Error closing serial port " + portName + ": " + ex.getMessage());
		}
	}

	private void openMidi() {
		String deviceName = (String) midiDevices.getSelectedItem();
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

	private void closeMidi() {
	    String deviceName = (String) midiDevices.getSelectedItem();
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


	private MidiDevice.Info getDeviceInfo(String deviceName) {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : infos) {
			if (info.getName().equals(deviceName)) {
				return info;
			}
		}
		return null;
	}

	
	public void noteOn(int channel, int pitch, int velocity) {
	    System.out.println(pitch);
	}
	
	public static void main(String[] args) {
		ConnectionGUI gui = new ConnectionGUI();
		gui.setVisible(true);
	}
}
