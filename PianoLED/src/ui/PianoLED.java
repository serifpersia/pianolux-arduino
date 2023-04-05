package ui;

//import processing.core.PApplet;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import java.awt.event.MouseMotionAdapter;
import java.io.ByteArrayOutputStream;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;

//Serial & Midi imports
import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import themidibus.MidiBus;

import javax.sound.midi.*;

@SuppressWarnings({ "serial", "unused" })
public class PianoLED extends JFrame {

	private JPanel contentPane;
	private final JPanel leftPanel;
	private final JPanel rightPanel;
	private final CardLayout cardLayout;

	private static final long serialVersionUID = 1L;
	private JComboBox<String> serialPorts;
	private JComboBox<String> midiDropdownList;
	private JButton openButton;
	private MidiBus myBusIn;

	private volatile SerialPort serialPort;

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
		String deviceName = (String) midiDropdownList.getSelectedItem();
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
		String deviceName = (String) midiDropdownList.getSelectedItem();
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PianoLED frame = new PianoLED();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	int[][] Keys = new int[88][2];
	int whiteKeyPitches[] = { 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55, 57,
			59, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95, 96, 98, 100, 101,
			103, 105, 107, 108 };
// List of white keys in a 88-key piano
	int whiteKeys[] = { 0, 2, 3, 5, 7, 8, 10, 12, 14, 15, 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36, 38, 39, 41,
			43, 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79, 80, 82, 84, 86,
			87 };
	int[] blackKeys = { 1, 4, 6, 9, 11, 13, 16, 18, 21, 23, 25, 28, 30, 33, 35, 37, 40, 42, 45, 47, 49, 52, 54, 57, 59,
			61, 64, 66, 69, 71, 73, 76, 78, 81, 83, 85 };
// Create a list of x-coordinates for each key
	int[] keyXCoordinates = { 11, 40, 56, 86, 101, 116, 145, 161, 191, 206, 221, 251, 266, 296, 311, 326, 356, 371, 401,
			416, 431, 461, 476, 506, 521, 536, 566, 581, 611, 626, 641, 671, 686, 715, 731, 746 };

	public PianoLED() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 930, 600);
		setLocationRelativeTo(null);

		// Main content pane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(21, 25, 28));
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			int x, y;

			@Override
			public void mouseDragged(MouseEvent e) {
				// Get the new mouse position
				int newX = e.getXOnScreen();
				int newY = e.getYOnScreen();

				// Calculate the distance the mouse has moved
				int deltaX = newX - x;
				int deltaY = newY - y;

				// Move the window by the same distance
				setLocation(getX() + deltaX, getY() + deltaY);

				// Update the stored mouse position
				x = newX;
				y = newY;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// Store the initial mouse position
				x = e.getXOnScreen();
				y = e.getYOnScreen();
			}
		});

		// X label
		JLabel lbX = new JLabel("X");
		lbX.setBounds(5, 5, 30, 30);
		lbX.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbX.setOpaque(true);
		lbX.setBackground(Color.BLACK);
		lbX.setForeground(Color.WHITE);
		lbX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		leftPanel = new JPanel();
		leftPanel.setBounds(0, 0, 110, 600);
		leftPanel.setBackground(Color.BLACK);
		contentPane.add(lbX);
		contentPane.add(leftPanel);

		JButton LivePlay = new JButton("LivePlay");
		LivePlay.setFont(new Font("Montserrat", Font.BOLD, 14));
		LivePlay.setBounds(0, 250, 110, 50);
		LivePlay.setBackground(Color.BLACK);
		LivePlay.setForeground(Color.WHITE);
		LivePlay.setFocusable(false);
		LivePlay.setBorderPainted(false);
		LivePlay.setOpaque(true); // Set opaque to true
		LivePlay.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				LivePlay.setBackground(Color.WHITE);
				LivePlay.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent e) {
				LivePlay.setBackground(Color.BLACK);
				LivePlay.setForeground(Color.WHITE);
			}

			public void mousePressed(MouseEvent e) {
				LivePlay.setBackground(Color.WHITE);
				cardLayout.show(rightPanel, "2");
			}
		});

		leftPanel.add(LivePlay);

		JButton Learn = new JButton("Learn");
		Learn.setBounds(0, 300, 110, 50);
		Learn.setFont(new Font("Montserrat", Font.BOLD, 14));
		Learn.setBackground(Color.BLACK);
		Learn.setForeground(Color.WHITE);
		Learn.setFocusable(false);
		Learn.setBorderPainted(false);
		Learn.setOpaque(true); // Set opaque to true
		Learn.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				Learn.setBackground(Color.WHITE);
				Learn.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent e) {
				Learn.setBackground(Color.BLACK);
				Learn.setForeground(Color.WHITE);
			}

			public void mousePressed(MouseEvent e) {
				cardLayout.show(rightPanel, "3");
			}
		});

		leftPanel.add(Learn);

		JButton Controls = new JButton("Controls");
		Controls.setBounds(0, 350, 110, 50);
		Controls.setFont(new Font("Montserrat", Font.BOLD, 14));
		Controls.setBackground(Color.BLACK);
		Controls.setForeground(Color.WHITE);
		Controls.setFocusable(false);
		Controls.setBorderPainted(false);
		Controls.setOpaque(true); // Set opaque to true
		Controls.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				Controls.setBackground(Color.WHITE);
				Controls.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent e) {
				Controls.setBackground(Color.BLACK);
				Controls.setForeground(Color.WHITE);
			}

			public void mousePressed(MouseEvent e) {
				cardLayout.show(rightPanel, "4");
			}
		});
		leftPanel.setLayout(null);
		leftPanel.add(Controls);

		JButton homeButton = new JButton("");
		homeButton.setBounds(0, 550, 110, 50);

		// Load the image and scale it to fit the button
		ImageIcon homeIcon = new ImageIcon(PianoLED.class.getResource("/icons/home.png"));
		Image img = homeIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

		homeButton.setIcon(new ImageIcon(img));
		homeButton.setBackground(Color.BLACK);
		homeButton.setFocusable(false);
		homeButton.setBorderPainted(false);
		homeButton.setOpaque(true); // Set opaque to true
		homeButton.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				// homeButton.setBackground(Color.WHITE);
				// homeButton.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent e) {
				// homeButton.setBackground(Color.BLACK);
				// homeButton.setForeground(Color.WHITE);
			}

			public void mousePressed(MouseEvent e) {
				cardLayout.show(rightPanel, "1");
			}
		});

		leftPanel.add(homeButton);

		rightPanel = new JPanel();
		rightPanel.setBounds(110, 0, 930, 600);
		rightPanel.setBackground(new Color(21, 25, 28));
		cardLayout = new CardLayout();
		rightPanel.setLayout(cardLayout);
		contentPane.add(rightPanel);

		// Dashboard Panel
		JPanel Dashboard = new JPanel() {
			// Draw Piano Keyboard
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				// white keys
				int x = 0;
				for (int i = 0; i < whiteKeys.length; i++) {
					if (Keys[whiteKeys[i]][0] == 1) {
						g.setColor(new Color(255, 0, 0));
					} else {
						g.setColor(Color.WHITE);
					}
					g.fillRect(x + 15, 510, 15, 70);
					g.setColor(Color.BLACK);
					g.drawRect(x + 15, 510, 15, 70);
					x += 15;
				}

				for (int i = 0; i < blackKeys.length; i++) {
					if (Keys[blackKeys[i]][1] == 1) {
						g.setColor(new Color(255, 0, 0));
					} else {
						g.setColor(Color.BLACK);
					}
					g.fillRect(keyXCoordinates[i] + 15, 510, 8, 40);
					g.setColor(Color.WHITE);
				}
			}
		};
		Dashboard.setBackground(new Color(21, 25, 28));
		rightPanel.add(Dashboard, "1");
		Dashboard.setLayout(null);

		serialPorts = new JComboBox<String>(getSerialPorts());
		serialPorts.setBounds(10, 229, 200, 25);
		Dashboard.add(serialPorts);

		JLabel lbDashboard = new JLabel("Dashboard");
		lbDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lbDashboard.setForeground(Color.WHITE);
		lbDashboard.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbDashboard.setBounds(630, 10, 175, 30);
		Dashboard.add(lbDashboard);

		JLabel lbConnections = new JLabel("Connections");
		lbConnections.setHorizontalAlignment(SwingConstants.CENTER);
		lbConnections.setForeground(Color.WHITE);
		lbConnections.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbConnections.setBounds(0, 70, 210, 60);
		Dashboard.add(lbConnections);

		// Serial Devices
		JLabel lbSerialDevices = new JLabel("Serial");
		lbSerialDevices.setBounds(0, 190, 210, 30);
		lbSerialDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbSerialDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbSerialDevices.setForeground(new Color(255, 255, 255));
		Dashboard.add(lbSerialDevices);

		// Midi Devices
		JLabel lbMidiDevices = new JLabel("Midi");
		lbMidiDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbMidiDevices.setForeground(Color.WHITE);
		lbMidiDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbMidiDevices.setBounds(0, 365, 210, 30);
		Dashboard.add(lbMidiDevices);

		midiDropdownList = new JComboBox<String>(getMidiDevices());
		midiDropdownList.setBounds(10, 405, 200, 25);
		Dashboard.add(midiDropdownList);

		// Define the JButton for opening/closing
		openButton = new JButton("Open");
		openButton.setFont(new Font("Montserrat", Font.PLAIN, 25));
		openButton.setBounds(47, 452, 117, 41);
		openButton.setBackground(Color.WHITE);
		openButton.setForeground(Color.BLACK);
		openButton.setFocusable(false);
		openButton.setBorderPainted(false);
		openButton.setOpaque(true); // Set opaque to true
		openButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (openButton.getBackground() == Color.WHITE) {
		            openSerial();
		            openMidi();
		            openButton.setBackground(Color.GREEN);
		            openButton.setForeground(Color.WHITE);
		            openButton.setText("Close");
		        } else {
		            closeSerial();
		            closeMidi();
		            openButton.setBackground(Color.WHITE);
		            openButton.setForeground(Color.BLACK);
		            openButton.setText("Open");
		        }
		    }
		});
		Dashboard.add(openButton);

		// LivePlay Panel
		JPanel LivePlayPanel = new JPanel();
		LivePlayPanel.setBackground(new Color(21, 25, 28));
		rightPanel.add(LivePlayPanel, "2");
		LivePlayPanel.setLayout(null);

		JLabel lbLivePlay = new JLabel("LivePlay");
		lbLivePlay.setBounds(630, 10, 175, 30);
		lbLivePlay.setHorizontalAlignment(SwingConstants.CENTER);
		lbLivePlay.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbLivePlay.setForeground(new Color(255, 255, 255));
		LivePlayPanel.add(lbLivePlay);

		// Learn Panel
		JPanel LearnPanel = new JPanel();
		LearnPanel.setBackground(new Color(21, 25, 28));
		rightPanel.add(LearnPanel, "3");
		LearnPanel.setLayout(null);

		JLabel lbLearn = new JLabel("Learn");
		lbLearn.setBounds(630, 10, 175, 30);
		lbLearn.setHorizontalAlignment(SwingConstants.CENTER);
		lbLearn.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbLearn.setForeground(new Color(255, 255, 255));
		LearnPanel.add(lbLearn);

		// Controls Panel
		JPanel ControlsPanel = new JPanel();
		ControlsPanel.setBackground(new Color(21, 25, 28));
		rightPanel.add(ControlsPanel, "4");
		ControlsPanel.setLayout(null);

		JLabel lbControls = new JLabel("Controls");
		lbControls.setBounds(630, 10, 175, 30);
		lbControls.setHorizontalAlignment(SwingConstants.CENTER);
		lbControls.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbControls.setForeground(new Color(255, 255, 255));
		ControlsPanel.add(lbControls);

	}
}
