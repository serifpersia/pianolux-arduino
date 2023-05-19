package com.serifpersia.pianoled.liveplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.ui.BottomPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class LivePlayPanel extends JPanel {

	private JPanel slideControlsPane = new JPanel();
	private JPanel controlsPane;

	private JComboBox<String> CameraList = new JComboBox<>();
	private JComboBox<?> camResList = new JComboBox<>();

	private JButton btnOpenCamera;
	private JButton btnFlip_Y;
	private JButton btnFlip_X;

	private boolean mirrorX = false;
	private boolean mirrorY = false;

	private JSlider leftCrop_XSlider;
	private JSlider rightCrop_XSlider;
	private JSlider topCrop_YSlider;
	private JSlider bottomCrop_YSlider;

	private WebcamPanel webcamPanel;
	private Webcam webcam;

	public LivePlayPanel(PianoLED pianoLED) {
		setBackground(new Color(0, 0, 0));
		setLayout(new BorderLayout(0, 0));

		addSlidingControlPanel();
	}

	private void addSlidingControlPanel() {
		addSlidingPanel();

		addWebcamPanel();

		addControls();
	}

	private void addControls() {
		controlsPane = new JPanel();
		controlsPane.setBackground(Color.BLACK);
		slideControlsPane.add(controlsPane, BorderLayout.CENTER);
		GridBagLayout gbl_controlsPane = new GridBagLayout();
		gbl_controlsPane.columnWidths = new int[] { 26, 13, 0 };
		gbl_controlsPane.rowHeights = new int[] { 142, 0, 35, 0, -9, 15, 0, 19, 12, 0 };
		gbl_controlsPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_controlsPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		controlsPane.setLayout(gbl_controlsPane);

		addListOfCameras();
		addListOfResolutions();
		addWebcamControls();
	}

	private void addWebcamPanel() {
		JLabel lbCamControls = new JLabel("Webcam");
		lbCamControls.setHorizontalAlignment(SwingConstants.CENTER);
		lbCamControls.setForeground(Color.WHITE);
		lbCamControls.setFont(new Font("Tahoma", Font.BOLD, 40));
		slideControlsPane.add(lbCamControls, BorderLayout.NORTH);
	}

	private void addSlidingPanel() {
		slideControlsPane.setBackground(new Color(231, 76, 60));
		slideControlsPane.setVisible(false);
		add(slideControlsPane, BorderLayout.EAST);
		slideControlsPane.setLayout(new BorderLayout(0, 0));
		// Add mouse listener to slideControlsPane
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int width = getWidth();
				if (width - x <= 205) {
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

	private void addListOfCameras() {

		CameraList = new JComboBox<String>();
		GridBagConstraints gbc_CameraList = new GridBagConstraints();
		CameraList.setForeground(new Color(0, 0, 0));
		CameraList.setFont(new Font("Tahoma", Font.BOLD, 15));
		CameraList.setBackground(new Color(255, 255, 255));
		CameraList.setToolTipText("Webcam device");
		gbc_CameraList.gridwidth = 2;
		gbc_CameraList.insets = new Insets(0, 0, 5, 0);
		gbc_CameraList.fill = GridBagConstraints.HORIZONTAL;
		gbc_CameraList.gridx = 0;
		gbc_CameraList.gridy = 1;
		controlsPane.add(CameraList, gbc_CameraList);
		// Add all available webcams to the CameraList JComboBox
		for (Webcam webcam : Webcam.getWebcams()) {
			CameraList.addItem(webcam.getName());
		}
	}

	private void addListOfResolutions() {
		String[] resCamList = { "320x240", "640x480", "800x600", "1024x768", "1280x720", "1280x960", "1920x1080" };
		{
			camResList = new JComboBox<Object>(resCamList);
			GridBagConstraints gbc_resCamList = new GridBagConstraints();
			camResList.setForeground(new Color(0, 0, 0));
			camResList.setFont(new Font("Tahoma", Font.BOLD, 15));
			camResList.setBackground(new Color(255, 255, 255));
			camResList.setToolTipText("Webcam resolution");
			gbc_resCamList.gridwidth = 2;
			gbc_resCamList.insets = new Insets(0, 0, 5, 0);
			gbc_resCamList.fill = GridBagConstraints.HORIZONTAL;
			gbc_resCamList.gridx = 0;
			gbc_resCamList.gridy = 2;
			controlsPane.add(camResList, gbc_resCamList);
		}
	}

	private void addWebcamControls() {

		btnOpenCamera = new JButton("Open Camera");
		btnOpenCamera.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_btnOpenCamera = new GridBagConstraints();
		btnOpenCamera.setBackground(new Color(52, 152, 219));
		btnOpenCamera.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnOpenCamera.setForeground(Color.WHITE);
		btnOpenCamera.setFocusable(false);
		btnOpenCamera.setBorderPainted(false);
		btnOpenCamera.setToolTipText("Open/Close Webcam");
		gbc_btnOpenCamera.gridwidth = 2;
		gbc_btnOpenCamera.insets = new Insets(0, 0, 5, 0);
		gbc_btnOpenCamera.gridx = 0;
		gbc_btnOpenCamera.gridy = 3;
		controlsPane.add(btnOpenCamera, gbc_btnOpenCamera);
		btnOpenCamera.addActionListener(e -> {
			if (btnOpenCamera.getText().equals("Open Camera")) {
				// Perform first action
				btnOpenCamera.setText("Close Camera");
				btnOpenCamera.setBackground(new Color(46, 204, 113));
				addWebcamFeedPane();

			} else {
				removeWebcamFeedPane();
				btnOpenCamera.setText("Open Camera");
				btnOpenCamera.setBackground(new Color(231, 76, 60));
			}
		});

		btnFlip_X = new JButton("Flip X");
		GridBagConstraints gbc_btnFlip_X = new GridBagConstraints();
		btnFlip_X.setBackground(new Color(52, 152, 219));
		btnFlip_X.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnFlip_X.setForeground(Color.WHITE);
		btnFlip_X.setFocusable(false);
		btnFlip_X.setBorderPainted(false);
		btnFlip_X.setToolTipText("Flip Webcam X");
		gbc_btnFlip_X.insets = new Insets(0, 0, 5, 5);
		gbc_btnFlip_X.gridx = 0;
		gbc_btnFlip_X.gridy = 4;
		controlsPane.add(btnFlip_X, gbc_btnFlip_X);
		btnFlip_X.addActionListener(e -> {
			if (btnFlip_X.getText().equals("Flip X")) {
				mirrorX = !mirrorX; // toggle the value of mirrorX
				btnFlip_X.setText("UnFlip X");
				btnFlip_X.setBackground(new Color(231, 76, 60));
			} else {
				mirrorX = !mirrorX; // toggle the value of mirrorX
				btnFlip_X.setText("Flip X");
				btnFlip_X.setBackground(new Color(52, 152, 219));
			}
		});

		btnFlip_Y = new JButton("Flip Y");
		GridBagConstraints gbc_btnFlip_Y = new GridBagConstraints();
		btnFlip_Y.setBackground(new Color(52, 152, 219));
		btnFlip_Y.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnFlip_Y.setForeground(Color.WHITE);
		btnFlip_Y.setFocusable(false);
		btnFlip_Y.setBorderPainted(false);
		btnFlip_Y.setToolTipText("Flip Webcam Y");
		gbc_btnFlip_Y.insets = new Insets(0, 0, 5, 0);
		gbc_btnFlip_Y.gridx = 1;
		gbc_btnFlip_Y.gridy = 4;
		controlsPane.add(btnFlip_Y, gbc_btnFlip_Y);
		btnFlip_Y.addActionListener(e -> {
			if (btnFlip_Y.getText().equals("Flip Y")) {
				mirrorY = !mirrorY; // toggle the value of mirrorY
				btnFlip_Y.setText("UnFlip Y");
				btnFlip_Y.setBackground(new Color(231, 76, 60));
			} else {
				mirrorY = !mirrorY; // toggle the value of mirrorY
				btnFlip_Y.setText("Flip Y");
				btnFlip_Y.setBackground(new Color(52, 152, 219));
			}
		});

		leftCrop_XSlider = new JSlider();
		GridBagConstraints gbc_leftCrop_XSlider = new GridBagConstraints();
		leftCrop_XSlider.setBackground(Color.BLACK);
		leftCrop_XSlider.setForeground(Color.WHITE);
		leftCrop_XSlider.setValue(0);
		gbc_leftCrop_XSlider.gridwidth = 2;
		gbc_leftCrop_XSlider.insets = new Insets(0, 0, 5, 0);
		gbc_leftCrop_XSlider.gridx = 0;
		gbc_leftCrop_XSlider.gridy = 5;
		controlsPane.add(leftCrop_XSlider, gbc_leftCrop_XSlider);
		leftCrop_XSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int leftCrop_XSliderVal = leftCrop_XSlider.getValue();
				leftCrop_XSlider.setToolTipText(Integer.toString(leftCrop_XSliderVal));
			}
		});

		rightCrop_XSlider = new JSlider();
		GridBagConstraints gbc_RightCrop_XSlider = new GridBagConstraints();
		rightCrop_XSlider.setBackground(Color.BLACK);
		rightCrop_XSlider.setForeground(Color.WHITE);
		rightCrop_XSlider.setValue(0);
		gbc_RightCrop_XSlider.gridwidth = 2;
		gbc_RightCrop_XSlider.insets = new Insets(0, 0, 5, 0);
		gbc_RightCrop_XSlider.gridx = 0;
		gbc_RightCrop_XSlider.gridy = 6;
		controlsPane.add(rightCrop_XSlider, gbc_RightCrop_XSlider);
		rightCrop_XSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int rightCrop_XSliderVal = rightCrop_XSlider.getValue();
				rightCrop_XSlider.setToolTipText(Integer.toString(rightCrop_XSliderVal));
			}
		});

		topCrop_YSlider = new JSlider();
		GridBagConstraints gbc_topCrop_YSlider = new GridBagConstraints();
		topCrop_YSlider.setBackground(Color.BLACK);
		topCrop_YSlider.setForeground(Color.WHITE);
		topCrop_YSlider.setValue(0);
		gbc_topCrop_YSlider.gridwidth = 2;
		gbc_topCrop_YSlider.insets = new Insets(0, 0, 5, 0);
		gbc_topCrop_YSlider.gridx = 0;
		gbc_topCrop_YSlider.gridy = 7;
		controlsPane.add(topCrop_YSlider, gbc_topCrop_YSlider);
		topCrop_YSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int topCrop_YSliderVal = topCrop_YSlider.getValue();
				topCrop_YSlider.setToolTipText(Integer.toString(topCrop_YSliderVal));
			}
		});

		bottomCrop_YSlider = new JSlider();
		GridBagConstraints gbc_bottomCrop_YSlider = new GridBagConstraints();
		bottomCrop_YSlider.setBackground(Color.BLACK);
		bottomCrop_YSlider.setForeground(Color.WHITE);
		bottomCrop_YSlider.setValue(0);
		gbc_bottomCrop_YSlider.gridwidth = 2;
		gbc_bottomCrop_YSlider.gridx = 0;
		gbc_bottomCrop_YSlider.gridy = 8;
		controlsPane.add(bottomCrop_YSlider, gbc_bottomCrop_YSlider);
		bottomCrop_YSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int bottomCrop_YSliderVal = bottomCrop_YSlider.getValue();
				bottomCrop_YSlider.setToolTipText(Integer.toString(bottomCrop_YSliderVal));
			}
		});
	}

	private void addWebcamFeedPane() {
		// Create a panel to display the webcam feed and add it to the center
		String selectedCamera = (String) CameraList.getSelectedItem();
		webcam = Webcam.getWebcamByName(selectedCamera);
		String selectedResolution = (String) camResList.getSelectedItem();
		String[] resolutionParts = selectedResolution.split("x");
		int width = Integer.parseInt(resolutionParts[0]);
		int height = Integer.parseInt(resolutionParts[1]);
		Dimension size = new Dimension(width, height);
		webcam.setViewSize(size);
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
					int cropLeft = (int) (image.getWidth() * (leftCrop_XSlider.getValue() / 200.0));
					int cropRight = (int) (image.getWidth() * (rightCrop_XSlider.getValue() / 200.0));
					int cropTop = (int) (image.getHeight() * (topCrop_YSlider.getValue() / 200.0));
					int cropBottom = (int) (image.getHeight() * (bottomCrop_YSlider.getValue() / 200.0));

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

}
