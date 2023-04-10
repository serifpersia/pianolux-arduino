package com.serifpersia.pianoled;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.LeftPanel;
import ui.RightPanel;
import ui.TopPanel;
import ui.BottomPanel;

@SuppressWarnings("serial")
public class PianoLED extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PianoLED frame = new PianoLED();
					frame.setSize(950, 800);
					frame.setLocationRelativeTo(null);
					frame.setTitle("PianoLED");
					frame.setIconImage(new ImageIcon(getClass().getResource("/icons/PianoLED.png")).getImage());
					frame.setVisible(true);

					// Register a shutdown hook
					Runtime.getRuntime().addShutdownHook(new Thread() {
						public void run() {
							// Execute the dispose method
							PianoController.dispose();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PianoLED() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		TopPanel topPanel = new TopPanel(this);
		getContentPane().add(topPanel, BorderLayout.NORTH);

		RightPanel rightPanel = new RightPanel();
		getContentPane().add(rightPanel, BorderLayout.CENTER);

		LeftPanel leftPanel = new LeftPanel(rightPanel);
		getContentPane().add(leftPanel, BorderLayout.WEST);

		BottomPanel bottomPanel = new BottomPanel();
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		// Remove the bottomPanel from the frame's SOUTH region
		getContentPane().remove(bottomPanel);
		getContentPane().remove(topPanel);

		// Add the top/bottom panels to the frame's NORTH/SOUTH region of the rightPanel
		JPanel rightPanelWrapper = new JPanel(new BorderLayout());
		rightPanelWrapper.add(topPanel, BorderLayout.NORTH);
		rightPanelWrapper.add(rightPanel, BorderLayout.CENTER);
		rightPanelWrapper.add(bottomPanel, BorderLayout.SOUTH);
		getContentPane().add(rightPanelWrapper, BorderLayout.CENTER);
	}

}
