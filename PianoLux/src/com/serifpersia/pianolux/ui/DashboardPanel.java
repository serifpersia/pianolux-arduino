package com.serifpersia.pianolux.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.serifpersia.pianolux.PianoController;
import com.serifpersia.pianolux.PianoLux;
import com.serifpersia.pianolux.Updater;

import javax.sound.midi.MidiDevice.Info;

import jssc.SerialPortList;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {

	public static JLabel lb_Version;

	private Updater updater = new Updater();

	public static JComboBox<?> cbSerialDevices;
	public static JComboBox<?> cbMidiDevices;
	public static JComboBox<?> cbBranch;

	private JButton btnUpdate;
	private JButton btnOpen;
	private JButton btnRefresh;

	private PianoController pianoController;

	public DashboardPanel(PianoLux pianoLux) {

		pianoController = pianoLux.getPianoController();

		init();
		buttonActions();
	}

	private void init() {
		setLayout(new BorderLayout(0, 0));

		DefaultComboBoxModel<Object> defaultSerialDevicesModel = new DefaultComboBoxModel<>(pianoController.portNames);
		ArrayList<Info> midiDevices = pianoController.getMidiDevices();
		DefaultComboBoxModel<Info> defaultMidiDevices = new DefaultComboBoxModel<>(
				midiDevices.toArray(new Info[midiDevices.size()]));

		JPanel MainPanel = new JPanel();
		MainPanel.setBorder(new EmptyBorder(125, 0, 125, 0));
		add(MainPanel, BorderLayout.CENTER);
		MainPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		MainPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(4, 0, 0, 0));

		JPanel SerialPanel = new JPanel();
		panel.add(SerialPanel);

		JLabel Serial = new JLabel("Serial");
		Serial.setBorder(new EmptyBorder(0, 0, 0, 10));
		Serial.setHorizontalAlignment(SwingConstants.LEFT);
		Serial.setForeground(new Color(204, 204, 204));
		Serial.setFont(new Font("Poppins", Font.PLAIN, 28));
		SerialPanel.add(Serial);

		cbSerialDevices = new JComboBox<>(defaultSerialDevicesModel);
		cbSerialDevices.putClientProperty("JComponent.roundRect", true);
		cbSerialDevices.setForeground(new Color(204, 204, 204));
		cbSerialDevices.setFont(new Font("Poppins", Font.PLAIN, 18));
		SerialPanel.add(cbSerialDevices);

		JPanel MIDIPanel = new JPanel();
		panel.add(MIDIPanel);

		JLabel lblMidi = new JLabel("MIDI");
		lblMidi.setBorder(new EmptyBorder(0, 0, 0, 10));
		lblMidi.setHorizontalAlignment(SwingConstants.LEFT);
		lblMidi.setForeground(new Color(204, 204, 204));
		lblMidi.setFont(new Font("Poppins", Font.PLAIN, 28));
		MIDIPanel.add(lblMidi);

		cbMidiDevices = new JComboBox<>(defaultMidiDevices);
		cbMidiDevices.putClientProperty("JComponent.roundRect", true);
		cbMidiDevices.setForeground(new Color(204, 204, 204));
		cbMidiDevices.setFont(new Font("Poppins", Font.PLAIN, 18));
		MIDIPanel.add(cbMidiDevices);

		JPanel ButtonsPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) ButtonsPanel.getLayout();
		flowLayout.setHgap(20);
		panel.add(ButtonsPanel);

		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Poppins", Font.PLAIN, 16));
		btnUpdate.setFocusable(false);
		ButtonsPanel.add(btnUpdate);

		btnOpen = new JButton("Connect");
		btnOpen.setBackground(new Color(231, 76, 60));
		btnOpen.setForeground(Color.WHITE);
		btnOpen.setFont(new Font("Poppins", Font.PLAIN, 16));
		btnOpen.setFocusable(false);

		ButtonsPanel.add(btnOpen);

		btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Poppins", Font.PLAIN, 16));
		btnRefresh.setFocusable(false);
		ButtonsPanel.add(btnRefresh);

		lb_Version = new JLabel("Current Version");
		lb_Version.setFont(new Font("Poppins", Font.PLAIN, 18));
		lb_Version.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lb_Version);

	}

	private void buttonActions() {
		ActionListener buttonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "btnUpdate":
					if (updater.getOs().contains("win")) {
						updater.getUpdate();
					}
					break;

				case "btnOpen":
					if (btnOpen.getText().equals("Connect")) {
						try {

							pianoController.openSerial();
							pianoController.openMidi();
							btnOpen.setBackground(new Color(46, 204, 113));
							btnOpen.setForeground(Color.WHITE);
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
		btnOpen.addActionListener(buttonListener);
		btnRefresh.addActionListener(buttonListener);

		btnUpdate.setActionCommand("btnUpdate");
		btnOpen.setActionCommand("btnOpen");
		btnRefresh.setActionCommand("btnRefresh");
	}
}
