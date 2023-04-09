package com.serifpersia.pianoled;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ui.LeftPanel;
import ui.RightPanel;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 950, 800);
		RightPanel rightPanel = new RightPanel();
		getContentPane().add(rightPanel, BorderLayout.CENTER);

		LeftPanel leftPanel = new LeftPanel(rightPanel);
		getContentPane().add(leftPanel, BorderLayout.WEST);
	}
}
