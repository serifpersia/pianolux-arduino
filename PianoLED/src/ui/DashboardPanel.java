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
import java.awt.BorderLayout;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {

	private Updater updater = new Updater();

	PianoController pianoController = new PianoController();

	private JLabel lbDashboard;
	static JComboBox<?> SerialList;
	public static JComboBox<?> MidiList;
	private JButton openButton;
	private JButton updateButton;

	public DashboardPanel() {
		setBackground(new Color(21, 25, 28));
		setLayout(new BorderLayout(0, 0));

		lbDashboard = new JLabel("Dashboard");
		lbDashboard.setHorizontalAlignment(SwingConstants.LEFT);
		lbDashboard.setForeground(Color.WHITE);
		lbDashboard.setFont(new Font("Tahoma", Font.BOLD, 30));
		add(lbDashboard, BorderLayout.NORTH);

		JPanel Container = new JPanel();
		Container.setBackground(new Color(21, 25, 28));
		add(Container, BorderLayout.CENTER);
		Container.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel LeftPane = new JPanel();
		LeftPane.setBackground(new Color(231, 76, 60));
		Container.add(LeftPane);
		LeftPane.setLayout(new BorderLayout(0, 0));

		JLabel lbConnect = new JLabel("Connect");
		lbConnect.setHorizontalAlignment(SwingConstants.CENTER);
		lbConnect.setForeground(Color.WHITE);
		lbConnect.setFont(new Font("Tahoma", Font.BOLD, 30));
		LeftPane.add(lbConnect, BorderLayout.NORTH);

		JPanel connectionPanel = new JPanel();
		connectionPanel.setBackground(new Color(21, 25, 28));
		LeftPane.add(connectionPanel, BorderLayout.CENTER);
		connectionPanel.setLayout(new GridLayout(3, 2, 0, 0));

		JLabel lbSerial = new JLabel("Serial");
		lbSerial.setHorizontalAlignment(SwingConstants.CENTER);
		lbSerial.setForeground(Color.WHITE);
		lbSerial.setFont(new Font("Tahoma", Font.BOLD, 25));
		connectionPanel.add(lbSerial);

		SerialList = new JComboBox<Object>(PianoController.portNames);
		SerialList.setBackground(new Color(21, 25, 28));
		SerialList.setForeground(Color.WHITE);
		SerialList.setFont(new Font("Tahoma", Font.BOLD, 25));
		SerialList.setFocusable(false);
		connectionPanel.add(SerialList);

		JLabel lbMidi = new JLabel("Midi");
		lbMidi.setHorizontalAlignment(SwingConstants.CENTER);
		lbMidi.setForeground(Color.WHITE);
		lbMidi.setFont(new Font("Tahoma", Font.BOLD, 25));
		connectionPanel.add(lbMidi);

		MidiList = new JComboBox<Object>(PianoController.getMidiDevices());
		MidiList.setBackground(new Color(21, 25, 28));
		MidiList.setForeground(Color.WHITE);
		MidiList.setFont(new Font("Tahoma", Font.BOLD, 25));
		MidiList.setFocusable(false);
		connectionPanel.add(MidiList);

		openButton = new JButton("Open");
		openButton.setHorizontalAlignment(SwingConstants.CENTER);
		openButton.setBackground(new Color(231, 76, 60));
		openButton.setForeground(Color.WHITE);
		openButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		openButton.setFocusable(false);
		openButton.setOpaque(true); // Set opaque to true
		connectionPanel.add(openButton);
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

		updateButton = new JButton("Update");
		updateButton.setHorizontalAlignment(SwingConstants.CENTER);
		updateButton.setBackground(new Color(52, 152, 219));
		updateButton.setForeground(Color.WHITE);
		updateButton.setFocusable(false);
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		connectionPanel.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updater.checkUpdates();
			}
		});
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		connectionPanel.add(updateButton);
	}
}