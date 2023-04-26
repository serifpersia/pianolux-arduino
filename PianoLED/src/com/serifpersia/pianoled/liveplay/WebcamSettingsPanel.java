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
import javax.swing.JSlider;

import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class WebcamSettingsPanel extends JPanel {

	static boolean mirrorX = false;
	static boolean mirrorY = false;

	static JSlider leftCrop_XSlider = new JSlider();
	static JSlider rightCrop_XSlider = new JSlider();
	static JSlider topCrop_YSlider = new JSlider();
	static JSlider bottomCrop_YSlider = new JSlider();

	public WebcamSettingsPanel() {

		setBackground(Color.BLACK);
		setLayout(new GridLayout(0, 1, 0, 0));

		JPanel lbPanel = new JPanel();
		lbPanel.setBackground(new Color(0, 0, 0));
		add(lbPanel);
		lbPanel.setLayout(new BorderLayout(0, 0));

		JLabel webcamSettingsLabel = new JLabel("Settings");
		webcamSettingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lbPanel.add(webcamSettingsLabel);
		webcamSettingsLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		webcamSettingsLabel.setForeground(Color.WHITE);
		webcamSettingsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		addCropButtons();

		JPanel ButtonsPanel = new JPanel();
		add(ButtonsPanel);
		ButtonsPanel.setLayout(new GridLayout(0, 1, 0, 0));

		affFlipButtons(ButtonsPanel);

		addDoneButton(ButtonsPanel);

	}

	private void addCropButtons() {
		JPanel transformControlsPane = new JPanel();
		transformControlsPane.setBackground(Color.BLACK);
		transformControlsPane.setForeground(Color.WHITE);

		add(transformControlsPane);
		transformControlsPane.setLayout(new GridLayout(8, 0, 0, 0));

		JLabel LeftCrop_X = new JLabel("Left Crop X");
		LeftCrop_X.setFont(new Font("Tahoma", Font.BOLD, 15));
		LeftCrop_X.setHorizontalAlignment(SwingConstants.LEFT);
		LeftCrop_X.setForeground(new Color(255, 255, 255));
		transformControlsPane.add(LeftCrop_X);

		leftCrop_XSlider = new JSlider();
		leftCrop_XSlider.setBackground(Color.BLACK);
		leftCrop_XSlider.setValue(0); // set default value to 0
		transformControlsPane.add(leftCrop_XSlider);

		JLabel RightCrop_X = new JLabel("Right Crop X");
		RightCrop_X.setFont(new Font("Tahoma", Font.BOLD, 15));
		RightCrop_X.setHorizontalAlignment(SwingConstants.LEFT);
		RightCrop_X.setForeground(new Color(255, 255, 255));
		transformControlsPane.add(RightCrop_X);

		rightCrop_XSlider = new JSlider();
		rightCrop_XSlider.setBackground(Color.BLACK);
		rightCrop_XSlider.setValue(0); // set default value to 0
		transformControlsPane.add(rightCrop_XSlider);

		JLabel TopCrop_Y = new JLabel("Top Crop Y");
		TopCrop_Y.setFont(new Font("Tahoma", Font.BOLD, 15));
		TopCrop_Y.setHorizontalAlignment(SwingConstants.LEFT);
		TopCrop_Y.setForeground(new Color(255, 255, 255));
		transformControlsPane.add(TopCrop_Y);

		topCrop_YSlider = new JSlider();
		topCrop_YSlider.setBackground(Color.BLACK);
		topCrop_YSlider.setValue(0); // set default value to 0
		transformControlsPane.add(topCrop_YSlider);

		JLabel BottomCrop_Y = new JLabel("Bottom Crop Y");
		BottomCrop_Y.setFont(new Font("Tahoma", Font.BOLD, 15));
		BottomCrop_Y.setHorizontalAlignment(SwingConstants.LEFT);
		BottomCrop_Y.setForeground(new Color(255, 255, 255));
		transformControlsPane.add(BottomCrop_Y);

		bottomCrop_YSlider = new JSlider();
		bottomCrop_YSlider.setBackground(Color.BLACK);
		bottomCrop_YSlider.setValue(0); // set default value to 0
		transformControlsPane.add(bottomCrop_YSlider);
	}
	
	public static int getLeftCrop()
	{
		return leftCrop_XSlider.getValue();
	}

	public static int getRightCrop()
	{
		return rightCrop_XSlider.getValue();
	}

	public static int getTopCrop()
	{
		return topCrop_YSlider.getValue();
	}

	public static int getBottomCrop()
	{
		return bottomCrop_YSlider.getValue();
	}

	private void addDoneButton(JPanel ButtonsPanel) {
		JButton btnDone = new JButton("Done");
		btnDone.setForeground(Color.WHITE);
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnDone.setBackground(new Color(52, 152, 219));
		ButtonsPanel.add(btnDone);
		btnDone.addActionListener(e -> {
			getTopLevelAncestor().setVisible(false);
		});
	}

	private void affFlipButtons(JPanel ButtonsPanel) {
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
	}
}
