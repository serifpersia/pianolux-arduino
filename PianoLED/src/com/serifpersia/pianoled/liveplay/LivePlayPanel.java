package com.serifpersia.pianoled.liveplay;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.serifpersia.pianoled.PianoLED;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import javax.swing.JButton;
import com.serifpersia.pianoled.ui.BottomPanel;

@SuppressWarnings("serial")
public class LivePlayPanel extends JPanel {

	private static final int REFRESH_RATE_MS = 16;

	private LiveRoll liveRoll;
	private JPanel slideControlsPane = new JPanel();
	private JLabel lb_Controls;
	private JCheckBox gridToggle;
	private JCheckBox infoToggle = new JCheckBox("Tech Info");
	private JSlider sld_Speed;
	private JCheckBox customColor_Toggle;

	private JComboBox<String> CameraList;
	private JButton btnOpenCamera;

	private boolean mirrorX = false;
	private boolean mirrorY = false;

	private JButton btnFlip_X;
	private JButton btnFlip_Y;

	private JSlider leftCrop_XSlider;
	private JSlider rightCrop_XSlider;
	private JSlider topCrop_YSlider;
	private JSlider bottomCrop_YSlider;

	private WebcamPanel webcamPanel;
	private Webcam webcam;

	public LivePlayPanel(PianoLED pianoLED) {
		setBackground(Color.BLACK);

		setLayout(new BorderLayout(0, 0));
		liveRoll = new LiveRoll(pianoLED, this);
		add(liveRoll);
		addSlidingControlPanel();

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				liveRoll.repaint();
			}
		};

		Timer timer = new Timer(REFRESH_RATE_MS, taskPerformer);
		timer.start();

		sliderActions();
		buttonActions();
	}

	private void addSlidingControlPanel() {
		add(slideControlsPane, BorderLayout.EAST);

		lb_Controls = new JLabel("Controls");
		lb_Controls.setFont(new Font("Poppins", Font.PLAIN, 35));
		lb_Controls.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_1 = new JLabel("Speed");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Poppins", Font.PLAIN, 21));

		sld_Speed = new JSlider(1, 5, 4);
		sld_Speed.setForeground(new Color(128, 128, 128));

		gridToggle = new JCheckBox("Show Grid");
		gridToggle.setFont(new Font("Poppins", Font.PLAIN, 16));

		customColor_Toggle = new JCheckBox("Use Custom Color");
		customColor_Toggle.setFont(new Font("Poppins", Font.PLAIN, 16));

		CameraList = new JComboBox<>();
		CameraList.putClientProperty("JComponent.roundRect", true);

		for (Webcam webcam : Webcam.getWebcams()) {
			CameraList.addItem(webcam.getName());
		}

		btnOpenCamera = new JButton("Open Camera");
		btnOpenCamera.setFont(new Font("Poppins", Font.PLAIN, 12));
		btnOpenCamera.setBackground(new Color(231, 76, 60));
		btnOpenCamera.setForeground(Color.WHITE);
		btnOpenCamera.setFocusable(false);
		btnOpenCamera.setBorderPainted(false);
		btnOpenCamera.setToolTipText("Open/Close Webcam");

		btnFlip_X = new JButton("Flip X");
		btnFlip_X.setFont(new Font("Poppins", Font.PLAIN, 12));

		btnFlip_Y = new JButton("Flip Y");
		btnFlip_Y.setFont(new Font("Poppins", Font.PLAIN, 12));

		leftCrop_XSlider = new JSlider();
		leftCrop_XSlider.setForeground(new Color(128, 128, 128));
		leftCrop_XSlider.setValue(0);

		rightCrop_XSlider = new JSlider();
		rightCrop_XSlider.setForeground(new Color(128, 128, 128));
		rightCrop_XSlider.setValue(0);

		topCrop_YSlider = new JSlider();
		topCrop_YSlider.setForeground(new Color(128, 128, 128));
		topCrop_YSlider.setValue(0);

		bottomCrop_YSlider = new JSlider();
		bottomCrop_YSlider.setForeground(new Color(128, 128, 128));
		bottomCrop_YSlider.setValue(0);

		GroupLayout gl_slideControlsPane = new GroupLayout(slideControlsPane);
		gl_slideControlsPane.setHorizontalGroup(gl_slideControlsPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_slideControlsPane.createSequentialGroup().addContainerGap().addGroup(gl_slideControlsPane
						.createParallelGroup(Alignment.LEADING)
						.addComponent(sld_Speed, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(lb_Controls, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(gridToggle, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(customColor_Toggle, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(CameraList, 0, 176, Short.MAX_VALUE)
						.addComponent(btnOpenCamera, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addGroup(gl_slideControlsPane.createSequentialGroup()
								.addComponent(btnFlip_X, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(btnFlip_Y, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
						.addComponent(leftCrop_XSlider, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
						.addComponent(rightCrop_XSlider, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
						.addComponent(topCrop_YSlider, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
						.addComponent(bottomCrop_YSlider, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		gl_slideControlsPane.setVerticalGroup(gl_slideControlsPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_slideControlsPane.createSequentialGroup().addGap(10).addComponent(lb_Controls).addGap(10)
						.addComponent(lblNewLabel_1).addGap(10)
						.addComponent(sld_Speed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(gridToggle).addGap(10).addComponent(customColor_Toggle).addGap(10)
						.addComponent(CameraList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addGap(10)
						.addComponent(btnOpenCamera).addGap(10)
						.addGroup(gl_slideControlsPane.createParallelGroup(Alignment.BASELINE).addComponent(btnFlip_X)
								.addComponent(btnFlip_Y))
						.addGap(10)
						.addComponent(leftCrop_XSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(rightCrop_XSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(topCrop_YSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(bottomCrop_YSlider, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(10, Short.MAX_VALUE)));
		slideControlsPane.setLayout(gl_slideControlsPane);

		// Add mouse listener to slideControlsPane
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int width = getWidth();
				if (width - x <= 200) {
					// Mouse is near the right border of LearnPanel
					slideControlsPane.setVisible(true);
				} else {
					// Mouse is not near the right border of LearnPanel
					slideControlsPane.setVisible(false);
				}
			}
		});

		slideControlsPane.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int width = slideControlsPane.getWidth();
				if (x <= 0 || x >= width || e.getY() <= 0 || e.getY() >= slideControlsPane.getHeight()) {
					// Mouse is out of slideControlsPane
					slideControlsPane.setVisible(false);
				}
			}
		});

	}

	public boolean drawLines() {

		return false;
	}

	public boolean isShowGridSelected() {

		return gridToggle.isSelected();
	}

	public boolean isShowInfoSelected() {

		return infoToggle.isSelected();
	}

	public boolean isCustomColorSelected() {

		return customColor_Toggle.isSelected();
	}

	private void sliderActions() {
		ChangeListener sliderChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (source == sld_Speed) {
					int invertedSpeedVal = 6 - sld_Speed.getValue(); // Invert the value
					sld_Speed.setToolTipText(Integer.toString(sld_Speed.getValue()));
					LiveRoll.PIANO_ROLL_HEIGHT_IN_SEC = invertedSpeedVal;
				} else if (source == leftCrop_XSlider) {
					int leftCrop_XSliderVal = leftCrop_XSlider.getValue();
					leftCrop_XSlider.setToolTipText(Integer.toString(leftCrop_XSliderVal));
				} else if (source == rightCrop_XSlider) {
					int rightCrop_XSliderVal = rightCrop_XSlider.getValue();
					rightCrop_XSlider.setToolTipText(Integer.toString(rightCrop_XSliderVal));
				} else if (source == topCrop_YSlider) {
					int topCrop_YSliderVal = topCrop_YSlider.getValue();
					topCrop_YSlider.setToolTipText(Integer.toString(topCrop_YSliderVal));
				} else if (source == bottomCrop_YSlider) {
					int bottomCrop_YSliderVal = bottomCrop_YSlider.getValue();
					bottomCrop_YSlider.setToolTipText(Integer.toString(bottomCrop_YSliderVal));
				}
			}
		};

		sld_Speed.addChangeListener(sliderChangeListener);

	}

	private void addWebcamFeedPane() {
		// Create a panel to display the webcam feed and add it to the center
		String selectedCamera = (String) CameraList.getSelectedItem();
		webcam = Webcam.getWebcamByName(selectedCamera);
		Dimension[] resolutions = webcam.getViewSizes();
		Dimension maxSize = resolutions[resolutions.length - 1]; // Get the highest resolution available
		webcam.setViewSize(maxSize);
		webcam.open();

		webcamPanel = new WebcamPanel(webcam) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				AffineTransform transform = new AffineTransform();

				if (mirrorX) {
					transform.concatenate(AffineTransform.getScaleInstance(-1, 1));
					transform.concatenate(AffineTransform.getTranslateInstance(-getWidth(), 0));
				}

				if (mirrorY) {
					transform.concatenate(AffineTransform.getScaleInstance(1, -1));
					transform.concatenate(AffineTransform.getTranslateInstance(0, -getHeight()));
				}

				g2.setTransform(transform);

				BufferedImage image = getImage();
				if (image != null) {
					// Calculate the crop dimensions
					int cropLeft = (int) (image.getWidth() * (leftCrop_XSlider.getValue() / 125.0));
					int cropRight = (int) (image.getWidth() * (rightCrop_XSlider.getValue() / 125.0));
					int cropTop = (int) (image.getHeight() * (topCrop_YSlider.getValue() / 125.0));
					int cropBottom = (int) (image.getHeight() * (bottomCrop_YSlider.getValue() / 125.0));

					// Calculate the cropped image dimensions
					int cropWidth = image.getWidth() - cropLeft - cropRight;
					int cropHeight = image.getHeight() - cropTop - cropBottom;

					// Crop the image
					BufferedImage croppedImage = image.getSubimage(cropLeft, cropTop, cropWidth, cropHeight);

					// Draw the cropped image to the panel
					g.drawImage(croppedImage, 0, 0, getWidth(), getHeight(), null);
				}
			}
		};

		BottomPanel.cardLayout.show(BottomPanel.cardPanel, "webcamPane");
		BottomPanel.webcamPane.add(webcamPanel);
		BottomPanel.webcamPane.revalidate();
	}

	private void removeWebcamFeedPane() {
		webcam.close();
		BottomPanel.cardLayout.show(BottomPanel.cardPanel, "pianoPane");
		BottomPanel.webcamPane.remove(webcamPanel);
		BottomPanel.webcamPane.revalidate();
	}

	private void buttonActions() {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "btnOpenCamera":
					if (btnOpenCamera.getText().equals("Open Camera")) {
						try {
							// Attempt to open the camera
							// If successful, proceed with the action
							addWebcamFeedPane();

							// Update the button appearance
							btnOpenCamera.setText("Close Camera");
							btnOpenCamera.setBackground(new Color(46, 204, 113));
						} catch (Exception ex) {
							// If opening the camera fails, catch the exception
							// and display an error message to the user
							JOptionPane.showMessageDialog(null,
									"Failed to open camera. Make sure you have a working webcam connected!");
						}

					} else {

						removeWebcamFeedPane();
						// Update the button appearance
						btnOpenCamera.setText("Open Camera");
						btnOpenCamera.setBackground(new Color(231, 76, 60));
						break;
					}

				case "btnFlip_X":
					if (btnFlip_X.getText().equals("Flip X")) {
						mirrorX = !mirrorX; // toggle the value of mirrorX
						btnFlip_X.setText("UnFlip X");
					} else {
						mirrorX = !mirrorX; // toggle the value of mirrorX
						btnFlip_X.setText("Flip X");
					}
					break;
				case "btnFlip_Y":
					if (btnFlip_Y.getText().equals("Flip Y")) {
						mirrorY = !mirrorY; // toggle the value of mirrorY
						btnFlip_Y.setText("UnFlip Y");
					} else {
						mirrorY = !mirrorY; // toggle the value of mirrorY
						btnFlip_Y.setText("Flip Y");
					}
					break;
				default:
					break;
				}
			}
		};

		btnOpenCamera.addActionListener(buttonListener);
		btnFlip_X.addActionListener(buttonListener);
		btnFlip_Y.addActionListener(buttonListener);

		btnOpenCamera.setActionCommand("btnOpenCamera");
		btnFlip_X.setActionCommand("btnFlip_X");
		btnFlip_Y.setActionCommand("btnFlip_Y");
	}
}