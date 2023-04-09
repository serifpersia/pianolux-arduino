package ui;

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

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {

	DrawPiano piano = new DrawPiano();
	PianoController pianoController = new PianoController();

	private JLabel lbDashboard;
	private JLabel lbConnections;
	private JLabel lbSerialDevices;
	private JLabel lbMidiDevices;
	static JComboBox<?> SerialList;
	public static JComboBox<?> MidiList;
	private JButton openButton;

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

		SerialList = new JComboBox<Object>(PianoController.portNames);
		SerialList.setBounds(10, 229, 200, 25);
		add(SerialList);

		lbMidiDevices = new JLabel("Midi");
		lbMidiDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbMidiDevices.setForeground(Color.WHITE);
		lbMidiDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbMidiDevices.setBounds(0, 365, 210, 30);
		add(lbMidiDevices);

		MidiList = new JComboBox<Object>(PianoController.getMidiDevices());
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
						PianoController.portName = (String) SerialList.getSelectedItem();
						PianoController.midiName = (String) MidiList.getSelectedItem();

						pianoController.openMidi();
						PianoController.arduino = new Arduino((PianoLED) SwingUtilities.getWindowAncestor(openButton),
								PianoController.portName, 115200);
						System.out.println("Serial device opened: " + PianoController.portName);

						openButton.setBackground(Color.GREEN);
						openButton.setForeground(Color.WHITE);
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
					openButton.setBackground(Color.WHITE);
					openButton.setForeground(Color.BLACK);
					openButton.setText("Open");
				}
			}
		});
	}
}