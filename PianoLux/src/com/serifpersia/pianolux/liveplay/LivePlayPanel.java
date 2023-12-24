package com.serifpersia.pianolux.liveplay;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JSlider;
import javax.swing.JButton;

import com.serifpersia.pianolux.PianoLux;
import com.serifpersia.pianolux.ui.BottomPanel;
import com.serifpersia.pianolux.ui.pnl_HueOnly;

import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class LivePlayPanel extends JPanel {

	private static final int REFRESH_RATE_MS = 16;

	private LiveRoll liveRoll;
	private JPanel slideControlsPane = new JPanel();
	private JCheckBox gridToggle;
	private JCheckBox infoToggle = new JCheckBox("Tech Info");
	private JSlider sld_Speed;

	private JComboBox<String> CameraList;
	private JButton btnOpenCamera;

	private boolean mirrorX = false;
	private boolean mirrorY = false;

	private JButton btnFlip_X;
	private JButton btnFlip_Y;

	private WebcamPanel webcamPanel;
	private Webcam webcam;

	private pnl_HueOnly huePanel = new pnl_HueOnly();

	private JSlider sld_resizePianoPanel;

	private double scaleFactor = 1.0; // Initial scale factor (no zoom)
	private int translationX = 0; // Initial translation along X axis
	private int translationY = 0; // Initial translation along Y axis
	private int lastMouseX;
	private int lastMouseY;
	private JLabel lb_Transparency;
	private JCheckBox useBGToggle;
	private JButton btnChoseImage;
	private JSlider sld_setTransaprency;

	public LivePlayPanel(PianoLux pianoLux) {
		setBackground(Color.BLACK);

		setLayout(new BorderLayout(0, 0));
		liveRoll = new LiveRoll(pianoLux, this);
		add(liveRoll);
		addSlidingControlPanel();

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				liveRoll.repaint();
			}
		};

		Timer timer = new Timer(REFRESH_RATE_MS, taskPerformer);
		timer.start();

		sliderActions(pianoLux);
		buttonActions();
	}

	private void addSlidingControlPanel() {

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane(slideControlsPane);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setUnitIncrement(20); // Adjust this value as needed
		verticalScrollBar.setBlockIncrement(40); // Adjust this value as needed

		panel.add(scrollPane);

		JLabel lb_Speed = new JLabel("Speed");
		lb_Speed.setHorizontalAlignment(SwingConstants.LEFT);
		lb_Speed.setFont(new Font("Poppins", Font.PLAIN, 21));

		sld_Speed = new JSlider(1, 5, 4);
		sld_Speed.setForeground(new Color(128, 128, 128));

		gridToggle = new JCheckBox("Show Grid");
		gridToggle.setFont(new Font("Poppins", Font.PLAIN, 16));

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

		JPanel colorHuePane = new JPanel();
		colorHuePane.setBackground(Color.BLACK);

		sld_resizePianoPanel = new JSlider();
		sld_resizePianoPanel.setForeground(new Color(128, 128, 128));
		sld_resizePianoPanel.setMinimum(15); // Corresponds to 0.15 * 100
		sld_resizePianoPanel.setMaximum(30); // Corresponds to 0.3 * 100
		sld_resizePianoPanel.setValue(15); // Initial value within the range

		JLabel lb_Height = new JLabel("Height");
		lb_Height.setHorizontalAlignment(SwingConstants.LEFT);
		lb_Height.setFont(new Font("Poppins", Font.PLAIN, 21));

		useBGToggle = new JCheckBox("Use BG");
		useBGToggle.setFont(new Font("Poppins", Font.PLAIN, 16));

		btnChoseImage = new JButton("Chose Image");
		btnChoseImage.setToolTipText("Chose BG Image");
		btnChoseImage.setForeground(Color.WHITE);
		btnChoseImage.setFont(new Font("Poppins", Font.PLAIN, 12));
		btnChoseImage.setFocusable(false);

		lb_Transparency = new JLabel("Transparency ");
		lb_Transparency.setHorizontalAlignment(SwingConstants.LEFT);
		lb_Transparency.setFont(new Font("Poppins", Font.PLAIN, 21));

		sld_setTransaprency = new JSlider();
		sld_setTransaprency.setValue(0);
		sld_setTransaprency.setMinimum(0);
		sld_setTransaprency.setForeground(Color.GRAY);

		GroupLayout gl_slideControlsPane = new GroupLayout(slideControlsPane);
		gl_slideControlsPane.setHorizontalGroup(gl_slideControlsPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_slideControlsPane.createSequentialGroup().addContainerGap().addGroup(gl_slideControlsPane
						.createParallelGroup(Alignment.LEADING)
						.addComponent(sld_Speed, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
						.addComponent(lb_Speed, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
						.addComponent(gridToggle, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
						.addComponent(CameraList, 0, 190, Short.MAX_VALUE)
						.addComponent(btnOpenCamera, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
						.addGroup(gl_slideControlsPane.createSequentialGroup()
								.addComponent(btnFlip_X, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(btnFlip_Y, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
						.addComponent(colorHuePane, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
						.addComponent(lb_Height, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(sld_resizePianoPanel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(useBGToggle, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChoseImage, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(lb_Transparency, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(sld_setTransaprency, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		gl_slideControlsPane.setVerticalGroup(gl_slideControlsPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_slideControlsPane.createSequentialGroup().addGap(20)
						.addComponent(colorHuePane, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(lb_Speed).addGap(5)
						.addComponent(sld_Speed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(gridToggle).addGap(10)
						.addComponent(CameraList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addGap(10)
						.addComponent(btnOpenCamera).addGap(10)
						.addGroup(gl_slideControlsPane.createParallelGroup(Alignment.BASELINE).addComponent(btnFlip_X)
								.addComponent(btnFlip_Y))
						.addGap(10).addComponent(lb_Height, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(sld_resizePianoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(useBGToggle, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(btnChoseImage, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(lb_Transparency, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(sld_setTransaprency, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(10, Short.MAX_VALUE)));
		colorHuePane.setLayout(new BorderLayout(0, 0));
		huePanel.setBackground(Color.BLACK);

		colorHuePane.add(huePanel, BorderLayout.CENTER);
		slideControlsPane.setLayout(gl_slideControlsPane);

		// Add mouse listener to slideControlsPane
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int width = getWidth();
				if (width - x <= 200) {
					// Mouse is near the right border of LearnPanel
					panel.setVisible(true);
				} else {
					// Mouse is not near the right border of LearnPanel
					panel.setVisible(false);
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

	public boolean isBGSelected() {

		return useBGToggle.isSelected();
	}

	public boolean isShowInfoSelected() {

		return infoToggle.isSelected();
	}

	private void sliderActions(PianoLux pianoLux) {
		ChangeListener sliderChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (source == sld_Speed) {
					int invertedSpeedVal = 6 - sld_Speed.getValue(); // Invert the value
					sld_Speed.setToolTipText(Integer.toString(sld_Speed.getValue()));
					LiveRoll.PIANO_ROLL_HEIGHT_IN_SEC = invertedSpeedVal;
				} else if (source == sld_resizePianoPanel) {
					int sld_resizePianoPanelVal = sld_resizePianoPanel.getValue();
					sld_resizePianoPanel.setToolTipText(Integer.toString(sld_resizePianoPanelVal));
					double percentage = sld_resizePianoPanelVal / 100.0; // Convert to a decimal value (0.15 to 0.3)
					pianoLux.updateBottomPanelHeightPercentage(percentage);
				}

				else if (source == sld_setTransaprency) {
					int sld_setTransaprencyVal = sld_setTransaprency.getValue();
					sld_resizePianoPanel.setToolTipText(Integer.toString(sld_setTransaprencyVal));

					// Calculate opacity inversely (0 to 100 to 0.1)
					float opacity = (float) (1.0 - sld_setTransaprencyVal / 100.0);

					LiveRoll.bgImage_Opacity = opacity;
				}

			}
		};

		sld_Speed.addChangeListener(sliderChangeListener);
		sld_resizePianoPanel.addChangeListener(sliderChangeListener);
		sld_setTransaprency.addChangeListener(sliderChangeListener);

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
			private static final long serialVersionUID = 1L; // Add serial version UID

			@Override
			public void addNotify() {
				super.addNotify();
				addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						lastMouseX = e.getX();
						lastMouseY = e.getY();
					}
				});

				addMouseMotionListener(new MouseAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						if (scaleFactor > 0) {
							int deltaX = e.getX() - lastMouseX;
							int deltaY = e.getY() - lastMouseY;

							// Update the translation values based on the mouse drag
							translationX += deltaX;
							translationY += deltaY;
							repaint();

							lastMouseX = e.getX();
							lastMouseY = e.getY();
						}
					}
				});
			}

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;

				// Calculate the center of the panel
				int centerX = getWidth() / 2;
				int centerY = getHeight() / 2;

				AffineTransform transform = new AffineTransform();

				// Translate to the center
				transform.translate(centerX, centerY);

				// Apply scaling
				transform.scale(scaleFactor, scaleFactor);

				// Apply translation (panning)
				transform.translate(translationX, translationY);

				// Translate back to the original position
				transform.translate(-centerX, -centerY);

				if (mirrorX) {
					transform.concatenate(AffineTransform.getScaleInstance(-1, 1));
					transform.concatenate(AffineTransform.getTranslateInstance(-getWidth(), 0));
				}

				if (mirrorY) {
					transform.concatenate(AffineTransform.getScaleInstance(1, -1));
					transform.concatenate(AffineTransform.getTranslateInstance(0, -getHeight()));
				}
				// Apply transformations to the graphics context
				g2.setTransform(transform);

				BufferedImage image = getImage();

				// Draw the cropped image to the panel
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};

		webcamPanel.addMouseWheelListener(e -> {
			int notches = e.getWheelRotation();
			double scaleFactorIncrement = 0.025; // You can adjust the increment as needed
			double maxScaleFactor = 6.0; // Adjust this value to set the maximum zoom level

			if (notches < 0) {
				double tempScaleFactor = scaleFactor + scaleFactorIncrement;
				scaleFactor = Math.min(tempScaleFactor, maxScaleFactor); // Limit zooming in
			} else {
				double tempScaleFactor = scaleFactor - scaleFactorIncrement;
				if (tempScaleFactor >= 1.0) { // Only limit zooming out if it's still greater than or equal to the
												// original scale
					scaleFactor = tempScaleFactor;
				} else { // Reset translation when zooming out beyond the original scale
					scaleFactor = 1.0;
					translationX = 0;
					translationY = 0;
				}
			}

			webcamPanel.repaint();
		});

		// Get the screen's current resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Set the dimensions of the webcamPanel to match the screen resolution
		webcamPanel.setPreferredSize(screenSize);

		BottomPanel.webcamPane.add(webcamPanel);
		BottomPanel.cardLayout.show(BottomPanel.cardPanel, "webcamPane");
	}

	private void removeWebcamFeedPane() {
		webcam.close();
		BottomPanel.cardLayout.show(BottomPanel.cardPanel, "pianoPane");
		BottomPanel.webcamPane.remove(webcamPanel);
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
					}
					break;

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
				case "btnChoseImage":
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Load BG Image File");
					fileChooser.setFileFilter(new FileNameExtensionFilter("jpeg, jpg, png", "jpeg", "jpg", "png"));

					// Show the file chooser dialog and check the user's choice
					int returnValue = fileChooser.showOpenDialog(LivePlayPanel.this);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						ImageIcon newIcon = new ImageIcon(selectedFile.getAbsolutePath());
						LiveRoll.testIcon = newIcon;
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
		btnChoseImage.addActionListener(buttonListener);

		btnOpenCamera.setActionCommand("btnOpenCamera");
		btnFlip_X.setActionCommand("btnFlip_X");
		btnFlip_Y.setActionCommand("btnFlip_Y");
		btnChoseImage.setActionCommand("btnChoseImage");
	}
}