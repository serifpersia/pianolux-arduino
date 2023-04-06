package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {

	private JLabel lbDashboard;
	private JLabel lbConnections;
	private JLabel lbSerialDevices;
	private JLabel lbMidiDevices;
	private JComboBox<String> SerialList;
	private JComboBox<String> MidiList;
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

		SerialList = new JComboBox<>();
		SerialList.setBounds(10, 229, 200, 25);
		add(SerialList);

		lbMidiDevices = new JLabel("Midi");
		lbMidiDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbMidiDevices.setForeground(Color.WHITE);
		lbMidiDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbMidiDevices.setBounds(0, 365, 210, 30);
		add(lbMidiDevices);

		MidiList = new JComboBox<>();
		MidiList.setBounds(10, 405, 200, 25);
		add(MidiList);

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
					openButton.setBackground(Color.GREEN);
					openButton.setForeground(Color.WHITE);
					openButton.setText("Close");
				} else {
					openButton.setBackground(Color.WHITE);
					openButton.setForeground(Color.BLACK);
					openButton.setText("Open");
				}
			}
		});
		add(openButton);

		DrawPiano pianoKeyboard = new DrawPiano();
		pianoKeyboard.setBounds(0, 160, 810, 600);
		pianoKeyboard.setBackground(new Color(21, 25, 29));
		add(pianoKeyboard);

	}
}