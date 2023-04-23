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
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class LivePlayPanel extends JPanel {

	private JPanel slideControlsPane = new JPanel();
	JComboBox<String> CameraList = new JComboBox<>();
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
		gbl_controlsPane.rowHeights = new int[] { 116, 80, 0, 0 };
		gbl_controlsPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_controlsPane.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
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

		JButton btnOpenCamera = new JButton("Open Camera");
		btnOpenCamera.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		btnOpenCamera.setBackground(new Color(52, 152, 219));
		btnOpenCamera.setForeground(Color.WHITE);
		btnOpenCamera.setFocusable(false);
		btnOpenCamera.setBorderPainted(false);
		btnOpenCamera.setToolTipText("Open Webcam");
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		controlsPane.add(btnOpenCamera, gbc_btnNewButton);
		btnOpenCamera.addActionListener(e -> showCropDialog());

	}

	private void showCropDialog() {
		cropDialog = new JFrame("Crop Dialog");
		cropDialog.setUndecorated(true);

		// Get the selected webcam
		String selectedCamera = (String) CameraList.getSelectedItem();
		Webcam webcam = Webcam.getWebcamByName(selectedCamera);

		// Set the resolution of the webcam
		Dimension size = new Dimension(640, 480);
		webcam.setViewSize(size);
		webcam.open();

		// Create a panel to display the webcam feed
		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setPreferredSize(size);
		cropDialog.getContentPane().add(panel);

		// Create a button to close the dialog and release the webcam
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(e -> {
			webcam.close();
			cropDialog.dispose();
		});
		cropDialog.getContentPane().add(btnDone, BorderLayout.SOUTH);

		cropDialog.pack();
		cropDialog.setLocationRelativeTo(null); // Center the dialog on the screen
		cropDialog.setVisible(true);
	}

}
