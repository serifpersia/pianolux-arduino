package com.serifpersia.pianoled.liveplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class WebcamSettingsPanel extends JPanel {

	static boolean mirrorX = false;
	static boolean mirrorY = false;

	public WebcamSettingsPanel() {

		setBackground(Color.BLACK);
		setLayout(new GridLayout(0, 1, 0, 0));

		JPanel lbPanel = new JPanel();
		lbPanel.setBackground(new Color(0, 0, 0));
		add(lbPanel);
		lbPanel.setLayout(new BorderLayout(0, 0));

		JLabel webcamSettingsLabel = new JLabel("Webcam Settings");
		webcamSettingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lbPanel.add(webcamSettingsLabel);
		webcamSettingsLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		webcamSettingsLabel.setForeground(Color.WHITE);
		webcamSettingsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		JPanel transformControlsPane = new JPanel(new GridLayout(4, 2));
		transformControlsPane.setBackground(Color.BLACK);
		transformControlsPane.setForeground(Color.WHITE);

		String[] labelTexts = { "Left X Crop:", "Right X Crop:", "Top Y Crop:", "Bottom Y Crop:" };
		String[] fieldNames = { "LX_Crop", "RX_Crop", "TPY_Crop", "BTY_Crop" };
		JTextField[] textFields = { new JTextField("0"), new JTextField("0"), new JTextField("0"),
				new JTextField("0") };

		for (int i = 0; i < labelTexts.length; i++) {
			JLabel label = new JLabel(labelTexts[i]);
			label.setFont(new Font("Tahoma", Font.BOLD, 15));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setForeground(Color.WHITE);
			transformControlsPane.add(label);

			JTextField textField = textFields[i];
			final String fieldName = fieldNames[i];
			textField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(fieldName + ": " + textField.getText());
				}
			});
			transformControlsPane.add(textField);
		}

		add(transformControlsPane);

		JPanel ButtonsPanel = new JPanel();
		add(ButtonsPanel);
		ButtonsPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel topButtonsPane = new JPanel();
		topButtonsPane.setBackground(new Color(0, 0, 0));
		ButtonsPanel.add(topButtonsPane);
		topButtonsPane.setLayout(new GridLayout(0, 2, 0, 0));

		JButton btnFlip_X = new JButton("Flip X");
		btnFlip_X.setBackground(new Color(231, 76, 60));
		btnFlip_X.setForeground(new Color(255, 255, 255));
		btnFlip_X.setFont(new Font("Tahoma", Font.BOLD, 20));
		topButtonsPane.add(btnFlip_X);
		btnFlip_X.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mirrorX = !mirrorX; // toggle the value of mirrorX
			}
		});

		JButton btnFlip_Y = new JButton("Flip Y");
		btnFlip_Y.setForeground(new Color(255, 255, 255));
		btnFlip_Y.setBackground(new Color(231, 76, 60));
		btnFlip_Y.setFont(new Font("Tahoma", Font.BOLD, 20));
		topButtonsPane.add(btnFlip_Y);
		btnFlip_Y.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mirrorY = !mirrorY; // toggle the value of mirrorX
			}
		});

		JButton btnDone = new JButton("Done");
		btnDone.setForeground(Color.WHITE);
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnDone.setBackground(new Color(52, 152, 219));
		ButtonsPanel.add(btnDone);
		btnDone.addActionListener(e -> {
			getTopLevelAncestor().setVisible(false);
		});

	}
}
