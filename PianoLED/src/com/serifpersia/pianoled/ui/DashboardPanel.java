package com.serifpersia.pianoled.ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoController;
import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.Updater;

import jssc.SerialPortList;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

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

	public DashboardPanel(PianoLED pianoLED) {
		setLayout(new BorderLayout(0, 0));

		pianoController = pianoLED.getPianoController();
		init();
		buttonActions();
	}

	private void init() {

		DefaultComboBoxModel<Object> defaultSerialDevicesModel = new DefaultComboBoxModel<>(pianoController.portNames);

		ArrayList<Info> midiDevices = pianoController.getMidiDevices();

		DefaultComboBoxModel<Info> defaultMidiDevices = new DefaultComboBoxModel<>(
				midiDevices.toArray(new Info[midiDevices.size()]));

		JLabel lblNewLabel = new JLabel("Dashboard");
		lblNewLabel.setForeground(new Color(204, 204, 204));
		lblNewLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(25, 25, 25));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);

		lb_Version = new JLabel("Current Version");
		lb_Version.setVerticalAlignment(SwingConstants.BOTTOM);
		lb_Version.setHorizontalAlignment(SwingConstants.LEFT);
		lb_Version.setForeground(new Color(204, 204, 204));
		lb_Version.setFont(new Font("Poppins", Font.PLAIN, 16));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
						.addComponent(lb_Version, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(93, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel_1.createSequentialGroup().addContainerGap(472, Short.MAX_VALUE).addComponent(lb_Version)
						.addGap(5)));
		panel_1.setLayout(gl_panel_1);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);

		JPanel pnl_MainControls = new JPanel();

		panel_2.add(pnl_MainControls);

		cbSerialDevices = new JComboBox<>(defaultSerialDevicesModel);
		cbSerialDevices.setFont(new Font("Poppins", Font.PLAIN, 13));
		cbSerialDevices.setForeground(new Color(204, 204, 204));
		cbSerialDevices.putClientProperty("JComponent.roundRect", true);

		JLabel lb_Serial = new JLabel("Serial");
		lb_Serial.setFont(new Font("Poppins", Font.PLAIN, 34));
		lb_Serial.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Serial.setForeground(new Color(204, 204, 204));

		JLabel lb_Midi = new JLabel("MIDI");
		lb_Midi.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Midi.setForeground(new Color(204, 204, 204));
		lb_Midi.setFont(new Font("Poppins", Font.PLAIN, 34));

		cbMidiDevices = new JComboBox<>(defaultMidiDevices);
		cbMidiDevices.setFont(new Font("Poppins", Font.PLAIN, 12));
		cbMidiDevices.setForeground(new Color(204, 204, 204));
		cbMidiDevices.putClientProperty("JComponent.roundRect", true);

		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Poppins", Font.PLAIN, 16));
		btnUpdate.setFocusable(false);

		btnOpen = new JButton("Connect");
		btnOpen.setBackground(new Color(231, 76, 60));
		btnOpen.setForeground(Color.WHITE);
		btnOpen.setFont(new Font("Poppins", Font.PLAIN, 16));
		btnOpen.setFocusable(false);

		btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Poppins", Font.PLAIN, 16));
		btnRefresh.setFocusable(false);

		GroupLayout gl_pnl_MainControls = new GroupLayout(pnl_MainControls);
		gl_pnl_MainControls.setHorizontalGroup(gl_pnl_MainControls.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_MainControls.createSequentialGroup().addGap(20).addGroup(gl_pnl_MainControls
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_MainControls.createSequentialGroup()
								.addComponent(btnUpdate, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnOpen, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE).addGap(10)
								.addComponent(btnRefresh, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
						.addGroup(gl_pnl_MainControls.createSequentialGroup().addComponent(lb_Serial)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(cbSerialDevices, 0, 207, Short.MAX_VALUE))
						.addGroup(gl_pnl_MainControls.createSequentialGroup()
								.addComponent(lb_Midi, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(cbMidiDevices, 0, 207, Short.MAX_VALUE)))
						.addGap(4)));
		gl_pnl_MainControls.setVerticalGroup(gl_pnl_MainControls.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_MainControls.createSequentialGroup().addGap(5)
						.addGroup(gl_pnl_MainControls.createParallelGroup(Alignment.LEADING)
								.addComponent(cbSerialDevices, GroupLayout.PREFERRED_SIZE, 35,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lb_Serial, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addGap(10)
						.addGroup(
								gl_pnl_MainControls.createParallelGroup(Alignment.LEADING)
										.addComponent(lb_Midi, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(cbMidiDevices, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE))
						.addGap(10)
						.addGroup(gl_pnl_MainControls.createParallelGroup(Alignment.LEADING)
								.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnOpen, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(39, Short.MAX_VALUE)));
		pnl_MainControls.setLayout(gl_pnl_MainControls);

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);

	}

	private void buttonActions() {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "btnUpdate":
					updater.getUpdate();
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
