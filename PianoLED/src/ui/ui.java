package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

//Serial & Midi imports
import java.util.ArrayList;
import jssc.SerialPortList;
import javax.sound.midi.*;
import themidibus.MidiBus;

@SuppressWarnings("serial")
public class ui extends JFrame {

	private JPanel contentPane;
	private final JPanel leftPanel;
	private final JPanel rightPanel;
	private final CardLayout cardLayout;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ui frame = new ui();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the frame.

	public ui() {
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
		ImageIcon homeIcon = new ImageIcon(ui.class.getResource("/icons/home.png"));
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
		JPanel Dashboard = new JPanel();
		Dashboard.setBackground(new Color(21, 25, 28));
		// HomePanel.setBackground(Color.YELLOW);
		rightPanel.add(Dashboard, "1");
		Dashboard.setLayout(null);

		String[] portNames = SerialPortList.getPortNames();
		JComboBox<?> serialDropdownList = new JComboBox<Object>(portNames);
		serialDropdownList.setBounds(10, 229, 200, 25);
		serialDropdownList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) serialDropdownList.getSelectedItem();
				System.out.println("Selected item: " + selected);
			}
		});
		Dashboard.add(serialDropdownList);

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
		
		//Serial Devices
		JLabel lbSerialDevices = new JLabel("Serial");
		lbSerialDevices.setBounds(0, 190, 210, 30);
		lbSerialDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbSerialDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbSerialDevices.setForeground(new Color(255, 255, 255));
		Dashboard.add(lbSerialDevices);

		//Midi Devices
		JLabel lbMidiDevices = new JLabel("Midi");
		lbMidiDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbMidiDevices.setForeground(Color.WHITE);
		lbMidiDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbMidiDevices.setBounds(0, 365, 210, 30);
		Dashboard.add(lbMidiDevices);

		ArrayList<String> midilist = new ArrayList<String>();
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

		String[] midiNames = midilist.toArray(new String[0]);
		JComboBox<String> midiDropdownList = new JComboBox<>(midiNames);
		midiDropdownList.setBounds(10, 405, 200, 25);
		Dashboard.add(midiDropdownList);
		midiDropdownList.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selected = (String) midiDropdownList.getSelectedItem();
		        System.out.println("Selected MIDI input device: " + selected);
		    }
		});

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
