package com.serifpersia.pianoled.ui;

import com.serifpersia.pianoled.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {

	private Updater updater = new Updater();

	public static JComboBox<?> SerialList;
	public static JComboBox<?> MidiList;
	public static JComboBox<?> BranchList;

	private JButton openButton;
	private JButton updateButton;
	private JButton cleanUpButton;
	private JButton refreshButton;
	PianoController pianoController;

	public DashboardPanel(PianoLED pianoLED) {

		pianoController = pianoLED.getPianoController();
		setBackground(new Color(21, 25, 28));
		setLayout(new BorderLayout(0, 0));

		JPanel ConnectPane = new JPanel();
		ConnectPane.setBackground(new Color(231, 76, 60));

		add(ConnectPane, BorderLayout.NORTH);

		JLabel lbConnect = new JLabel("Connect");
		lbConnect.setHorizontalAlignment(SwingConstants.CENTER);
		lbConnect.setForeground(Color.WHITE);
		lbConnect.setFont(new Font("Tahoma", Font.BOLD, 30));
		ConnectPane.add(lbConnect);

		JPanel ConnectionsPane = new JPanel();
		ConnectionsPane.setBackground(new Color(21, 25, 28));
		add(ConnectionsPane, BorderLayout.CENTER);
		ConnectionsPane.setLayout(new GridLayout(4, 1, 0, 0));

		JPanel SerialPane = new JPanel();
		SerialPane.setBackground(new Color(21, 25, 28));
		ConnectionsPane.add(SerialPane);
		SerialPane.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lbSerialDevices = new JLabel("Serial Devices");
		lbSerialDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbSerialDevices.setForeground(Color.WHITE);
		lbSerialDevices.setFont(new Font("Tahoma", Font.BOLD, 30));
		SerialPane.add(lbSerialDevices);

		SerialList = new JComboBox<Object>(pianoController.portNames);
		SerialList.setBackground(new Color(21, 25, 28));
		SerialList.setForeground(Color.WHITE);
		SerialList.setFont(new Font("Tahoma", Font.BOLD, 30));
		SerialList.setFocusable(false);
		SerialPane.add(SerialList);

		JPanel MidiPane = new JPanel();
		MidiPane.setBackground(new Color(21, 25, 28));
		ConnectionsPane.add(MidiPane);
		MidiPane.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lbMidiDeviecs = new JLabel("MIDI Devices");
		lbMidiDeviecs.setForeground(new Color(255, 255, 255));
		lbMidiDeviecs.setFont(new Font("Tahoma", Font.BOLD, 30));
		lbMidiDeviecs.setHorizontalAlignment(SwingConstants.CENTER);
		MidiPane.add(lbMidiDeviecs);

		class MidiInfoBoxModel extends DefaultComboBoxModel<Info> {
			public MidiInfoBoxModel(ArrayList<Info> arrayList) {
				super(arrayList.toArray(new Info[0]));
			}

			@Override
			public Info getSelectedItem() {
				return (Info) super.getSelectedItem();
			}
		}

		MidiInfoBoxModel boxModel = new MidiInfoBoxModel(pianoController.getMidiDevices());
		MidiList = new JComboBox<Info>(boxModel);
		MidiList.setBackground(new Color(21, 25, 28));
		MidiList.setForeground(Color.WHITE);
		MidiList.setFont(new Font("Tahoma", Font.BOLD, 30));
		MidiList.setFocusable(false);
		MidiPane.add(MidiList);

		JPanel BranchPane = new JPanel();
		BranchPane.setBackground(new Color(21, 25, 28));
		ConnectionsPane.add(BranchPane);
		BranchPane.setLayout(new GridLayout(1, 2, 0, 0));

		JLabel lbBranch = new JLabel("Update Branch");
		lbBranch.setForeground(new Color(255, 255, 255));
		lbBranch.setHorizontalAlignment(SwingConstants.CENTER);
		lbBranch.setFont(new Font("Tahoma", Font.BOLD, 30));
		BranchPane.add(lbBranch);

		String[] branch = { "stable", "beta" };

		BranchList = new JComboBox<Object>(branch);
		BranchList.setBackground(new Color(21, 25, 28));
		BranchList.setForeground(Color.WHITE);
		BranchList.setFont(new Font("Tahoma", Font.BOLD, 30));
		BranchList.setFocusable(false);
		BranchList.setSelectedItem("Stable"); // Set the default option to "Stable"
		BranchPane.add(BranchList);

		JPanel ButtonsPane = new JPanel();
		ButtonsPane.setBackground(new Color(21, 25, 28));
		ConnectionsPane.add(ButtonsPane);
		ButtonsPane.setLayout(new GridLayout(1, 1, 0, 0));

		cleanUpButton = new JButton("Clean");
		ButtonsPane.add(cleanUpButton);
		cleanUpButton.setBackground(new Color(156, 136, 255));
		cleanUpButton.setForeground(Color.WHITE);
		cleanUpButton.setFocusable(false);
		cleanUpButton.setBorderPainted(false);
		cleanUpButton.setFont(new Font("Tahoma", Font.BOLD, 30));
		cleanUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});

		updateButton = new JButton("Update");
		updateButton.setHorizontalAlignment(SwingConstants.CENTER);
		updateButton.setBackground(new Color(52, 152, 219));
		updateButton.setForeground(Color.WHITE);
		updateButton.setFocusable(false);
		updateButton.setBorderPainted(false);
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 30));
		ButtonsPane.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updater.getUpdate();
			}
		});

		openButton = new JButton("Open");
		openButton.setHorizontalAlignment(SwingConstants.CENTER);
		openButton.setBackground(new Color(231, 76, 60));
		openButton.setForeground(Color.WHITE);
		openButton.setFont(new Font("Tahoma", Font.BOLD, 30));
		openButton.setFocusable(false);
		openButton.setBorderPainted(false);
		ButtonsPane.add(openButton);
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (openButton.getText().equals("Open")) {
					try {
						pianoController.portName = (String) SerialList.getSelectedItem();
						pianoController.openMidi();
						pianoController.arduino = new Arduino((PianoLED) SwingUtilities.getWindowAncestor(openButton),
								pianoController.portName, 115200);
						System.out.println("Serial device opened: " + pianoController.portName);

						openButton.setBackground(new Color(46, 204, 113));
						openButton.setText("Close");

						if (pianoController.arduino != null) {
							pianoController.arduino.sendCommandBlackOut();
							pianoController.arduino.sendCommandFadeRate(255);
						}

					} catch (Exception OpenError) {
					}
				} else {
					pianoController.closeMidi();
					if (pianoController.arduino != null) {
						pianoController.arduino.sendCommandBlackOut();
						pianoController.setLedBG(false);
						pianoController.arduino.stop();
					}
					System.out.println("Serial device closed: " + pianoController.portName);
					openButton.setBackground(new Color(231, 76, 60));
					openButton.setText("Open");
				}
			}
		});

		refreshButton = new JButton("Refresh");
		refreshButton.setHorizontalAlignment(SwingConstants.CENTER);
		refreshButton.setForeground(Color.WHITE);
		refreshButton.setFont(new Font("Tahoma", Font.BOLD, 30));
		refreshButton.setFocusable(false);
		refreshButton.setBorderPainted(false);
		refreshButton.setBackground(new Color(39, 60, 117));
		ButtonsPane.add(refreshButton);

		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (updater.getOs().contains("win")) {
					pianoController.findPortNameOnWindows("Arduino");
				} else {
					pianoController.findPortNameOnLinux("ttyACM");
				}
				pianoController.refreshSerialList();
				pianoController.refreshMidiList();
			}
		});
	}
}