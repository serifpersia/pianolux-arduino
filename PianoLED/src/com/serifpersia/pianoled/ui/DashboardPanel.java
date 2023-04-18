package com.serifpersia.pianoled.ui;

import com.serifpersia.pianoled.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {

	private Updater updater = new Updater();

	PianoController pianoController = new PianoController();
	static JComboBox<?> SerialList;
	public static JComboBox<?> MidiList;
	private JButton openButton;
	private JButton updateButton;

	public DashboardPanel() {

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

		JPanel MainPane = new JPanel();
		MainPane.setBackground(new Color(21, 25, 28));
		add(MainPane, BorderLayout.CENTER);
		MainPane.setLayout(new GridLayout(3, 2, 0, 0));

		JLabel lbSerial = new JLabel("Serial");
		lbSerial.setHorizontalAlignment(SwingConstants.CENTER);
		lbSerial.setForeground(Color.WHITE);
		lbSerial.setFont(new Font("Tahoma", Font.BOLD, 25));
		MainPane.add(lbSerial);

		SerialList = new JComboBox<Object>(PianoController.portNames);
		SerialList.setBackground(new Color(21, 25, 28));
		SerialList.setForeground(Color.WHITE);
		SerialList.setFont(new Font("Tahoma", Font.BOLD, 25));
		SerialList.setFocusable(false);
		MainPane.add(SerialList);

		JLabel lbMidi = new JLabel("Midi");
		lbMidi.setHorizontalAlignment(SwingConstants.CENTER);
		lbMidi.setForeground(Color.WHITE);
		lbMidi.setFont(new Font("Tahoma", Font.BOLD, 25));
		MainPane.add(lbMidi);

		MidiList = new JComboBox<Object>(PianoController.getMidiDevices());
		MidiList.setBackground(new Color(21, 25, 28));
		MidiList.setForeground(Color.WHITE);
		MidiList.setFont(new Font("Tahoma", Font.BOLD, 25));
		MidiList.setFocusable(false);
		MainPane.add(MidiList);

		updateButton = new JButton("Update");
		updateButton.setHorizontalAlignment(SwingConstants.CENTER);
		updateButton.setBackground(new Color(52, 152, 219));
		updateButton.setForeground(Color.WHITE);
		updateButton.setFocusable(false);
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		MainPane.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updater.checkUpdates();
			}
		});
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 25));

		MainPane.add(updateButton);

		openButton = new JButton("Open");
		openButton.setHorizontalAlignment(SwingConstants.CENTER);
		openButton.setBackground(new Color(231, 76, 60));
		openButton.setForeground(Color.WHITE);
		openButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		openButton.setFocusable(false);
		openButton.setOpaque(true); // Set opaque to true
		MainPane.add(openButton);
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (openButton.getText().equals("Open")) {
					try {
						PianoController.portName = (String) SerialList.getSelectedItem();
						PianoController.midiName = (String) MidiList.getSelectedItem();

						pianoController.openMidi();
						PianoController.arduino = new Arduino((PianoLED) SwingUtilities.getWindowAncestor(openButton),
								PianoController.portName, 115200);
						System.out.println("Serial device opened: " + PianoController.portName);

						openButton.setBackground(new Color(46, 204, 113));
						openButton.setText("Close");

						if (PianoController.arduino != null) {
							PianoController.arduino.sendCommandBlackOut();
							PianoController.arduino.sendCommandFadeRate(255);
						}

					} catch (Exception OpenError) {
					}
				} else {
					PianoController.closeMidi();
					if (PianoController.arduino != null) {
						PianoController.arduino.sendCommandBlackOut();
						PianoController.setLedBG(false);
						PianoController.arduino.stop();
					}
					System.out.println("Serial device closed: " + PianoController.portName);
					openButton.setBackground(new Color(231, 76, 60));
					openButton.setText("Open");
				}
			}
		});

	}
}