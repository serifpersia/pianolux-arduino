package com.serifpersia.pianoled.liveplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class LivePlayPanel extends JPanel {

	private JPanel slideControlsPane = new JPanel();
	private JComboBox<String> CameraList = new JComboBox<>();
	private JComboBox<?> camResList = new JComboBox<>();
	private JFrame cropDialog;

	public LivePlayPanel() {
		setBackground(new Color(0, 0, 0));
		setLayout(new BorderLayout(0, 0));

		addSlidingControlPanel();
	}

	private void addSlidingControlPanel() {
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
				if (width - x <= 150) {
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

		// Add hotkey to toggle visibility of slideControlsPane
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK),
				"toggleControls");
		getActionMap().put("toggleControls", new AbstractAction() {
			boolean controlsVisible = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				controlsVisible = !controlsVisible;
				slideControlsPane.setVisible(controlsVisible);
				if (controlsVisible) {
					System.out.println("Ctrl+C(Controls_Visible)");
				} else {
					System.out.println("Ctrl+C(Controls_Hidden)");
				}
			}
		});

		JLabel lbCamControls = new JLabel("Webcam");
		lbCamControls.setHorizontalAlignment(SwingConstants.CENTER);
		lbCamControls.setForeground(Color.WHITE);
		lbCamControls.setFont(new Font("Tahoma", Font.BOLD, 40));
		slideControlsPane.add(lbCamControls, BorderLayout.NORTH);

		JPanel controlsPane = new JPanel();
		controlsPane.setBackground(new Color(21, 25, 28));
		slideControlsPane.add(controlsPane, BorderLayout.CENTER);
		GridBagLayout gbl_controlsPane = new GridBagLayout();
		gbl_controlsPane.columnWidths = new int[] { 0, 0 };
		gbl_controlsPane.rowHeights = new int[] { 116, 54, 0, 0, 0 };
		gbl_controlsPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_controlsPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		controlsPane.setLayout(gbl_controlsPane);

		CameraList = new JComboBox<String>();
		CameraList.setForeground(new Color(255, 255, 255));
		CameraList.setFont(new Font("Tahoma", Font.BOLD, 15));
		CameraList.setBackground(new Color(21, 25, 28));
		CameraList.setToolTipText("Webcam device");
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		controlsPane.add(CameraList, gbc_comboBox);

		// Add all available webcams to the CameraList JComboBox
		for (Webcam webcam : Webcam.getWebcams()) {
			CameraList.addItem(webcam.getName());
		}

		String[] resCamList = { "160x120", "320x240", "640x480", "800x600", "1024x768", "1280x720", "1280x960",
				"1920x1080" };

		camResList = new JComboBox<Object>(resCamList);
		camResList.setForeground(new Color(255, 255, 255));
		camResList.setFont(new Font("Tahoma", Font.BOLD, 15));
		camResList.setBackground(new Color(21, 25, 28));
		camResList.setToolTipText("Webcam resolution");
		GridBagConstraints gbc_camResList = new GridBagConstraints();
		gbc_camResList.insets = new Insets(0, 0, 5, 0);
		gbc_camResList.fill = GridBagConstraints.HORIZONTAL;
		gbc_camResList.gridx = 0;
		gbc_camResList.gridy = 2;
		controlsPane.add(camResList, gbc_camResList);

		JButton btnOpenCamera = new JButton("Open Camera");
		btnOpenCamera.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		btnOpenCamera.setBackground(new Color(52, 152, 219));
		btnOpenCamera.setForeground(Color.WHITE);
		btnOpenCamera.setFocusable(false);
		btnOpenCamera.setBorderPainted(false);
		btnOpenCamera.setToolTipText("Open Webcam");
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		controlsPane.add(btnOpenCamera, gbc_btnNewButton);
		btnOpenCamera.addActionListener(e -> showWebcamDialog());

	}

	private void showWebcamDialog() {

	    cropDialog = new JFrame("Webcam Settings");
	    cropDialog.setUndecorated(true);

	    // Create a panel to display the webcam feed and add it to the center
	    String selectedCamera = (String) CameraList.getSelectedItem();
	    Webcam webcam = Webcam.getWebcamByName(selectedCamera);
	    String selectedResolution = (String) camResList.getSelectedItem();
	    String[] resolutionParts = selectedResolution.split("x");
	    int width = Integer.parseInt(resolutionParts[0]);
	    int height = Integer.parseInt(resolutionParts[1]);
	    Dimension size = new Dimension(width, height);
	    webcam.setViewSize(size);
	    webcam.open();

	    WebcamPanel panel = new WebcamPanel(webcam);
	    panel.setPreferredSize(size);

	    cropDialog.getContentPane().add(panel, BorderLayout.CENTER);
	    
	    // Create a panel to display webcam settings and add it to the east
	    WebcamSettingsPanel settingsPanel = new WebcamSettingsPanel();
	    cropDialog.getContentPane().add(settingsPanel, BorderLayout.EAST);

	    // Set the size and location of the dialog
	    cropDialog.setSize(800, 600);
	    cropDialog.setLocationRelativeTo(null);
	    cropDialog.setVisible(true);
	}
}

/*
		// Create a panel for webcam settings and add it to the east
		JPanel webcamSettingsPane = new JPanel(new BorderLayout());
		JLabel webcamSettingsLabel = new JLabel("Webcam Settings");
		webcamSettingsLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		webcamSettingsLabel.setForeground(Color.WHITE);
		webcamSettingsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Add some padding to the bottom

		webcamSettingsPane.setBackground(Color.BLACK);
		webcamSettingsPane.add(webcamSettingsLabel, BorderLayout.NORTH);

		// Create a panel for transform controls and add it to the center of
		// webcamSettingsPane
		JPanel transformControlsPane = new JPanel(new GridLayout(4, 2));
		transformControlsPane.setBackground(Color.BLACK);
		transformControlsPane.setForeground(Color.WHITE);

		String[] labelTexts = { "Left X Crop:", "Right X Crop:", "Top Y Crop:", "Bottom Y Crop:" };
		JTextField[] textFields = { new JTextField(), new JTextField(), new JTextField(), new JTextField() };

		for (int i = 0; i < labelTexts.length; i++) {
			JLabel label = new JLabel(labelTexts[i]);
			label.setForeground(Color.WHITE);
			transformControlsPane.add(label);
			transformControlsPane.add(textFields[i]);
		}

		webcamSettingsPane.add(transformControlsPane, BorderLayout.CENTER);

		// Create a button to close the dialog and release the webcam, and add it to the
		// south
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(e -> {
			webcam.close();
			cropDialog.dispose();
		});
		webcamSettingsPane.add(btnDone, BorderLayout.SOUTH);

		cropDialog.getContentPane().add(webcamSettingsPane, BorderLayout.EAST);
		*/

