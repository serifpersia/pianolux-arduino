package com.serifpersia.pianoled.ui;

import com.serifpersia.pianoled.*;

import jssc.SerialPortList;

import java.awt.Color;
import java.awt.Font;
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

	public DashboardPanel(PianoLED pianoLED) {
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.BLACK);

		pianoController = pianoLED.getPianoController();
		init();
	}

	private void init() {

		DefaultComboBoxModel<Object> defaultSerialDevicesModel = new DefaultComboBoxModel<>(pianoController.portNames);

		ArrayList<Info> midiDevices = pianoController.getMidiDevices();

		DefaultComboBoxModel<Info> defaultMidiDevices = new DefaultComboBoxModel<>(
				midiDevices.toArray(new Info[midiDevices.size()]));

		JPanel pnlDashboard = new JPanel();
		pnlDashboard.setBackground(new Color(231, 76, 60));
		add(pnlDashboard, BorderLayout.NORTH);

		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setBackground(new Color(0, 0, 0));
		lblDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashboard.setForeground(Color.WHITE);
		lblDashboard.setFont(new Font("Tahoma", Font.BOLD, 40));
		pnlDashboard.add(lblDashboard);

		JPanel pnlButtons = new JPanel();
		pnlButtons.setBackground(new Color(0, 0, 0));
		add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new GridLayout(0, 4, 0, 0));

		btnUpdate = createButton("Update");
		btnClean = createButton("Clean");
		btnOpen = createButton("Open");
		btnOpen.setBackground(new Color(231, 76, 60));
		btnRefresh = createButton("Refresh");

		pnlButtons.add(btnUpdate);
		pnlButtons.add(btnClean);
		pnlButtons.add(btnOpen);
		pnlButtons.add(btnRefresh);

		buttonActions();

		JPanel pnlMain = new JPanel();
		pnlMain.setBackground(new Color(0, 0, 0));
		add(pnlMain, BorderLayout.CENTER);
		pnlMain.setLayout(new GridLayout(0, 4, 0, 0));

		JPanel pnlLeftSpace = new JPanel();
		pnlLeftSpace.setBackground(new Color(0, 0, 0));
		pnlMain.add(pnlLeftSpace);

		JPanel pnlLeftSide = new JPanel();
		pnlLeftSide.setBackground(new Color(0, 0, 0));
		pnlMain.add(pnlLeftSide);
		pnlLeftSide.setLayout(new GridLayout(3, 0, 0, 0));

		JLabel lblSerialDevice = new JLabel("Serial Device:");
		lblSerialDevice.setForeground(new Color(255, 255, 255));
		lblSerialDevice.setBackground(new Color(0, 0, 0));
		lblSerialDevice.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblSerialDevice.setHorizontalAlignment(SwingConstants.CENTER);
		pnlLeftSide.add(lblSerialDevice);

		JLabel lblMidiDevice = new JLabel("MIDI Device:");
		lblMidiDevice.setForeground(new Color(255, 255, 255));
		lblMidiDevice.setBackground(new Color(0, 0, 0));
		lblMidiDevice.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblMidiDevice.setHorizontalAlignment(SwingConstants.CENTER);
		pnlLeftSide.add(lblMidiDevice);

		JLabel lblUpdateBranch = new JLabel("Branch:");
		lblUpdateBranch.setForeground(new Color(255, 255, 255));
		lblUpdateBranch.setBackground(new Color(0, 0, 0));
		lblUpdateBranch.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblUpdateBranch.setHorizontalAlignment(SwingConstants.CENTER);
		pnlLeftSide.add(lblUpdateBranch);

		JPanel pnlRightSide = new JPanel();
		pnlRightSide.setBackground(new Color(0, 0, 0));
		pnlMain.add(pnlRightSide);
		pnlRightSide.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel pnlSerialDevice = new JPanel();
		pnlSerialDevice.setBackground(new Color(0, 0, 0));
		pnlRightSide.add(pnlSerialDevice);
		pnlSerialDevice.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel pnlEmpty1 = new JPanel();
		pnlEmpty1.setBackground(new Color(0, 0, 0));
		pnlSerialDevice.add(pnlEmpty1);

		cbSerialDevices = new JComboBox<>(defaultSerialDevicesModel);
		cbSerialDevices.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbSerialDevices.setFocusable(false);
		pnlSerialDevice.add(cbSerialDevices);

		JPanel pnlEmpty2 = new JPanel();
		pnlEmpty2.setBackground(new Color(0, 0, 0));
		pnlSerialDevice.add(pnlEmpty2);
		pnlEmpty2.setLayout(new BorderLayout(0, 0));

		JPanel pnlMidiDevice = new JPanel();
		pnlMidiDevice.setBackground(new Color(0, 0, 0));
		pnlRightSide.add(pnlMidiDevice);
		pnlMidiDevice.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel pnlEmpty3 = new JPanel();
		pnlEmpty3.setBackground(new Color(0, 0, 0));
		pnlMidiDevice.add(pnlEmpty3);
		pnlEmpty3.setLayout(new BorderLayout(0, 0));

		cbMidiDevices = new JComboBox<>(defaultMidiDevices);
		cbMidiDevices.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbMidiDevices.setFocusable(false);
		pnlMidiDevice.add(cbMidiDevices);

		JPanel pnlEmpty4 = new JPanel();
		pnlEmpty4.setBackground(new Color(0, 0, 0));
		pnlMidiDevice.add(pnlEmpty4);

		JPanel pnlBranch = new JPanel();
		pnlBranch.setBackground(new Color(0, 0, 0));
		pnlRightSide.add(pnlBranch);
		pnlBranch.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel pnlEmpty5 = new JPanel();
		pnlEmpty5.setBackground(new Color(0, 0, 0));
		pnlBranch.add(pnlEmpty5);

		String[] branch = { "stable", "beta" };

		cbBranch = new JComboBox<Object>(branch);
		cbBranch.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbBranch.setFocusable(isFocusable());
		cbBranch.setSelectedItem("stable");
		pnlBranch.add(cbBranch);

		JPanel pnlEmpty6 = new JPanel();
		pnlEmpty6.setBackground(new Color(0, 0, 0));
		pnlBranch.add(pnlEmpty6);

		JPanel pnlRightSpace = new JPanel();
		pnlRightSpace.setBackground(new Color(0, 0, 0));
		pnlMain.add(pnlRightSpace);
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Tahoma", Font.BOLD, 45));
		button.setBackground(new Color(52, 152, 219));
		button.setForeground(Color.WHITE);
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				if (!text.equals("Open")) {
					button.setBackground(new Color(231, 76, 60));
				}
			}

			public void mouseExited(MouseEvent e) {
				if (!button.isSelected() && !text.equals("Open")) {
					button.setBackground(new Color(52, 152, 219));
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
					if (btnOpen.getText().equals("Open")) {
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
						btnOpen.setText("Open");
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