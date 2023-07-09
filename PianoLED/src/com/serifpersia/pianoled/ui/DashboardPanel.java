package com.serifpersia.pianoled.ui;

import com.serifpersia.pianoled.*;

import jssc.SerialPortList;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.midi.MidiDevice.Info;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {

	private Updater updater = new Updater();

	public static JComboBox<?> cbSerialDevices;
	public static JComboBox<?> cbMidiDevices;
	public static JComboBox<?> cbBranch;

	private JButton btnUpdate;
	private JButton btnClean;
	private JButton btnOpen;
	private JButton btnRefresh;
	private PianoController pianoController;
	public static JLabel lb_Version;

	public DashboardPanel(PianoLED pianoLED) {
		setLayout(new BorderLayout(0, 0));
		setBackground(new Color(21, 21, 21));

		pianoController = pianoLED.getPianoController();
		init();
	}

	private void init() {

		DefaultComboBoxModel<Object> defaultSerialDevicesModel = new DefaultComboBoxModel<>(pianoController.portNames);

		ArrayList<Info> midiDevices = pianoController.getMidiDevices();

		DefaultComboBoxModel<Info> defaultMidiDevices = new DefaultComboBoxModel<>(
				midiDevices.toArray(new Info[midiDevices.size()]));

		JPanel pnlDashboard = new JPanel();
		pnlDashboard.setBackground(new Color(21, 21, 21));
		add(pnlDashboard, BorderLayout.NORTH);
		pnlDashboard.setLayout(new BorderLayout(0, 0));

		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setBackground(new Color(21, 21, 21));
		lblDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashboard.setForeground(new Color(204, 204, 204));
		lblDashboard.setFont(new Font("Poppins", Font.PLAIN, 35));
		pnlDashboard.add(lblDashboard);

		JLabel lblDashboard_1 = new JLabel("<");
		lblDashboard_1.setBackground(new Color(21, 21, 21));
		lblDashboard_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashboard_1.setForeground(Color.WHITE);
		lblDashboard_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		// lblDashboard_1.setBackground(Color.BLACK);
		pnlDashboard.add(lblDashboard_1, BorderLayout.WEST);

		JPanel pnlButtons = new JPanel();
		pnlButtons.setForeground(new Color(77, 77, 77));
		pnlButtons.setBackground(new Color(21, 21, 21));
		add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new GridLayout(0, 4, 0, 0));

		btnUpdate = createButton("Update");
		btnClean = createButton("Clean");
		btnOpen = createButton("Connect");
		btnOpen.setBackground(new Color(231, 76, 60));
		btnRefresh = createButton("Refresh");

		pnlButtons.add(btnUpdate);
		pnlButtons.add(btnClean);
		pnlButtons.add(btnOpen);
		pnlButtons.add(btnRefresh);

		buttonActions();

		JPanel pnlMain = new JPanel();
		pnlMain.setForeground(new Color(21, 21, 21));
		pnlMain.setBackground(new Color(21, 21, 21));
		add(pnlMain, BorderLayout.CENTER);
		pnlMain.setLayout(new GridLayout(0, 4, 0, 0));

		JPanel pnlLeftSpace = new JPanel();
		pnlLeftSpace.setForeground(new Color(21, 21, 21));
		pnlLeftSpace.setBackground(new Color(51, 51, 51));
		pnlMain.add(pnlLeftSpace);
		pnlLeftSpace.setLayout(new BorderLayout(0, 0));

		lb_Version = new JLabel("Current Version:");
		lb_Version.setBackground(new Color(21, 21, 21));
		lb_Version.setHorizontalAlignment(SwingConstants.LEFT);
		lb_Version.setFont(new Font("Poppins", Font.PLAIN, 16));
		lb_Version.setForeground(new Color(204, 204, 204));
		pnlLeftSpace.add(lb_Version, BorderLayout.SOUTH);

		JPanel pnlLeftSide = new JPanel();
		pnlLeftSide.setForeground(new Color(21, 21, 21));
		pnlLeftSide.setBackground(new Color(51, 51, 51));
		pnlMain.add(pnlLeftSide);
		pnlLeftSide.setLayout(new GridLayout(3, 0, 0, 0));

		JLabel lblSerialDevice = new JLabel("Serial Device:");
		lblSerialDevice.setForeground(new Color(204, 204, 204));
		lblSerialDevice.setBackground(new Color(21, 21, 21));
		lblSerialDevice.setFont(new Font("Poppins", Font.PLAIN, 21));
		lblSerialDevice.setHorizontalAlignment(SwingConstants.CENTER);
		pnlLeftSide.add(lblSerialDevice);

		JLabel lblMidiDevice = new JLabel("MIDI Device:");
		lblMidiDevice.setForeground(new Color(204, 204, 204));
		lblMidiDevice.setBackground(new Color(21, 21, 21));
		lblMidiDevice.setFont(new Font("Poppins", Font.PLAIN, 21));
		lblMidiDevice.setHorizontalAlignment(SwingConstants.CENTER);
		pnlLeftSide.add(lblMidiDevice);

		JLabel lblUpdateBranch = new JLabel("Branch:");
		lblUpdateBranch.setForeground(new Color(204, 204, 204));
		lblUpdateBranch.setBackground(new Color(21, 21, 21));
		lblUpdateBranch.setFont(new Font("Poppins", Font.PLAIN, 21));
		lblUpdateBranch.setHorizontalAlignment(SwingConstants.CENTER);
		pnlLeftSide.add(lblUpdateBranch);

		JPanel pnlRightSide = new JPanel();
		pnlRightSide.setForeground(new Color(21, 21, 21));
		pnlRightSide.setBackground(new Color(51, 51, 51));
		pnlMain.add(pnlRightSide);
		pnlRightSide.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel pnlSerialDevice = new JPanel();
		pnlSerialDevice.setForeground(new Color(21, 21, 21));
		pnlSerialDevice.setBackground(new Color(51, 51, 51));
		pnlRightSide.add(pnlSerialDevice);
		pnlSerialDevice.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel pnlEmpty1 = new JPanel();
		pnlEmpty1.setForeground(new Color(21, 21, 21));
		pnlEmpty1.setBackground(new Color(51, 51, 51));
		pnlSerialDevice.add(pnlEmpty1);

		cbSerialDevices = new JComboBox<>(defaultSerialDevicesModel);
		cbSerialDevices.setBackground(new Color(77, 77, 77));
		cbSerialDevices.setForeground(new Color(204, 204, 204));
		cbSerialDevices.setFont(new Font("Poppins", Font.PLAIN, 21));
		cbSerialDevices.setFocusable(false); // Set the JComboBox as non-focusable

		// Customize the JComboBox UI
		cbSerialDevices.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				return new JButton() {
					@Override
					public void paint(Graphics g) {
						// Do nothing to remove the arrow button painting
					}

					@Override
					public boolean contains(int x, int y) {
						// Override contains() method to hide the button when the mouse is over it
						return false;
					}
				};
			}
		});

		pnlSerialDevice.add(cbSerialDevices);

		JPanel pnlEmpty2 = new JPanel();
		pnlEmpty2.setForeground(new Color(21, 21, 21));
		pnlEmpty2.setBackground(new Color(51, 51, 51));
		pnlSerialDevice.add(pnlEmpty2);
		pnlEmpty2.setLayout(new BorderLayout(0, 0));

		JPanel pnlMidiDevice = new JPanel();
		pnlMidiDevice.setForeground(new Color(21, 21, 21));
		pnlMidiDevice.setBackground(new Color(51, 51, 51));
		pnlRightSide.add(pnlMidiDevice);
		pnlMidiDevice.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel pnlEmpty3 = new JPanel();
		pnlEmpty3.setForeground(new Color(21, 21, 21));
		pnlEmpty3.setBackground(new Color(51, 51, 51));
		pnlMidiDevice.add(pnlEmpty3);
		pnlEmpty3.setLayout(new BorderLayout(0, 0));

		cbMidiDevices = new JComboBox<>(defaultMidiDevices);
		cbMidiDevices.setBackground(new Color(77, 77, 77));
		cbMidiDevices.setForeground(new Color(204, 204, 204));
		cbMidiDevices.setFont(new Font("Poppins", Font.PLAIN, 21));
		cbMidiDevices.setFocusable(false); // Set the JComboBox as non-focusable

		// Customize the JComboBox UI
		cbMidiDevices.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				return new JButton() {
					@Override
					public void paint(Graphics g) {
						// Do nothing to remove the arrow button painting
					}

					@Override
					public boolean contains(int x, int y) {
						// Override contains() method to hide the button when the mouse is over it
						return false;
					}
				};
			}
		});
		pnlMidiDevice.add(cbMidiDevices);

		JPanel pnlEmpty4 = new JPanel();
		pnlEmpty4.setForeground(new Color(21, 21, 21));
		pnlEmpty4.setBackground(new Color(51, 51, 51));
		pnlMidiDevice.add(pnlEmpty4);

		JPanel pnlBranch = new JPanel();
		pnlBranch.setForeground(new Color(21, 21, 21));
		pnlBranch.setBackground(new Color(51, 51, 51));
		pnlRightSide.add(pnlBranch);
		pnlBranch.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel pnlEmpty5 = new JPanel();
		pnlEmpty5.setForeground(new Color(21, 21, 21));
		pnlEmpty5.setBackground(new Color(51, 51, 51));
		pnlBranch.add(pnlEmpty5);

		String[] branch = { "stable", "beta" };

		cbBranch = new JComboBox<Object>(branch);
		cbBranch.setBackground(new Color(77, 77, 77));
		cbBranch.setForeground(new Color(204, 204, 204));
		cbBranch.setFont(new Font("Poppins", Font.PLAIN, 21));
		cbBranch.setFocusable(false); // Set the JComboBox as non-focusable
		cbBranch.setSelectedItem("stable");
		// Customize the JComboBox UI
		cbBranch.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				return new JButton() {
					@Override
					public void paint(Graphics g) {
						// Do nothing to remove the arrow button painting
					}

					@Override
					public boolean contains(int x, int y) {
						// Override contains() method to hide the button when the mouse is over it
						return false;
					}
				};
			}
		});

		pnlBranch.add(cbBranch);

		JPanel pnlEmpty6 = new JPanel();
		pnlEmpty6.setForeground(new Color(21, 21, 21));
		pnlEmpty6.setBackground(new Color(51, 51, 51));
		pnlBranch.add(pnlEmpty6);

		JPanel pnlRightSpace = new JPanel();
		pnlRightSpace.setForeground(new Color(21, 21, 21));
		pnlRightSpace.setBackground(new Color(51, 51, 51));
		pnlMain.add(pnlRightSpace);
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Poppins", Font.PLAIN, 30));
		button.setBackground(new Color(21, 21, 21));
		button.setForeground(new Color(204, 204, 204));
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				if (!text.equals("Connect")) {
					button.setBackground(new Color(231, 76, 60));
				}
			}

			public void mouseExited(MouseEvent e) {
				if (!button.isSelected() && !text.equals("Connect")) {
					button.setBackground(new Color(21, 21, 21));
				}
			}

		});
		return button;
	}

	private void buttonActions() {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "btnUpdate":
					updater.getUpdate();
					break;
				case "btnClean":
					int result = JOptionPane.showOptionDialog(null, "Do you want to clean up?", "Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (result == JOptionPane.YES_OPTION) {
						// Get the current working directory
						String currentDir = System.getProperty("user.dir");

						// Construct file objects for data and lib folders and for any .exe files
						File dataFolder = new File(currentDir + File.separator + "data");
						File libFolder = new File(currentDir + File.separator + "lib");
						File[] exeFiles = new File(currentDir).listFiles((dir, name) -> name.endsWith(".exe"));

						// Try to delete the folders and files if they exist
						boolean deleted = false;
						if (dataFolder.exists()) {
							deleteFolder(dataFolder);
							deleted = true;
						}
						if (libFolder.exists()) {
							deleteFolder(libFolder);
							deleted = true;
						}
						for (File exeFile : exeFiles) {
							if (exeFile.exists()) {
								exeFile.delete();
								deleted = true;
							}
						}
						if (deleted) {
							JOptionPane.showMessageDialog(null, "PianoLED directory cleaned!");
							System.exit(0);
						} else {
							JOptionPane.showMessageDialog(null, "No old files found, no need to clean!");
						}
					}

					break;
				case "btnOpen":
					if (btnOpen.getText().equals("Connect")) {
						try {

							pianoController.openSerial();
							pianoController.openMidi();
							btnOpen.setBackground(new Color(46, 204, 113));
							btnOpen.setText("Close");

						} catch (Exception OpenError) {
						}
					} else {
						pianoController.closeSerial();
						pianoController.closeMidi();

						btnOpen.setBackground(new Color(231, 76, 60));
						btnOpen.setText("Connect");
					}
					break;
				case "btnRefresh":
					pianoController.portNames = SerialPortList.getPortNames();
					@SuppressWarnings("unchecked")
					DefaultComboBoxModel<Object> serialDevicesModel = (DefaultComboBoxModel<Object>) cbSerialDevices
							.getModel();
					serialDevicesModel.removeAllElements();
					serialDevicesModel.addAll(new ArrayList<>(Arrays.asList(pianoController.portNames)));

					@SuppressWarnings("unchecked")
					DefaultComboBoxModel<Object> midiDevicesModel = (DefaultComboBoxModel<Object>) cbMidiDevices
							.getModel();
					midiDevicesModel.removeAllElements();
					midiDevicesModel.addAll(pianoController.getMidiDevices());

					if (updater.getOs().contains("win")) {
						pianoController.findPortNameOnWindows("Arduino");
					} else {
						pianoController.findPortNameOnLinux("ttyACM");
					}
					pianoController.refreshSerialList();
					pianoController.refreshMidiList();
					break;
				default:
					break;
				}
			}
		};

		btnUpdate.addActionListener(buttonListener);
		btnClean.addActionListener(buttonListener);
		btnOpen.addActionListener(buttonListener);
		btnRefresh.addActionListener(buttonListener);

		btnUpdate.setActionCommand("btnUpdate");
		btnClean.setActionCommand("btnClean");
		btnOpen.setActionCommand("btnOpen");
		btnRefresh.setActionCommand("btnRefresh");
	}

	private void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteFolder(file);
				} else {
					file.delete();
				}
			}
		}
		folder.delete();
	}
}