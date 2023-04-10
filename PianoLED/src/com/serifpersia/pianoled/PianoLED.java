package com.serifpersia.pianoled;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.LeftPanel;
import ui.RightPanel;
import ui.BottomPanel;

import java.io.File;

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
							// Delete files here
							File file1 = new File("somefile tbt");
							file1.delete();

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

	    RightPanel rightPanel = new RightPanel();
	    getContentPane().add(rightPanel, BorderLayout.CENTER);

	    LeftPanel leftPanel = new LeftPanel(rightPanel);
	    getContentPane().add(leftPanel, BorderLayout.WEST);

	    BottomPanel bottomPanel = new BottomPanel();
	    getContentPane().add(bottomPanel, BorderLayout.SOUTH);

	    // Remove the bottomPanel from the frame's SOUTH region
	    getContentPane().remove(bottomPanel);

	    // Add the bottomPanel to the frame's SOUTH region of the rightPanel
	    JPanel rightPanelWrapper = new JPanel(new BorderLayout());
	    rightPanelWrapper.add(rightPanel, BorderLayout.CENTER);
	    rightPanelWrapper.add(bottomPanel, BorderLayout.SOUTH);
	    getContentPane().add(rightPanelWrapper, BorderLayout.CENTER);

	    pack();
	}

}
