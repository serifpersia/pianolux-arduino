package com.serifpersia.pianoled;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.serifpersia.pianoled.ui.BottomPanel;
import com.serifpersia.pianoled.ui.LeftPanel;
import com.serifpersia.pianoled.ui.RightPanel;
import com.serifpersia.pianoled.ui.TopPanel;

import java.awt.Color;

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

		// Window dragging
		addMouseMotionListener(new MouseMotionAdapter() {
			int x, y;

			@Override
			public void mouseDragged(MouseEvent e) {
				// Get the new mouse position
				int newX = e.getXOnScreen();
				int newY = e.getYOnScreen();

				// Calculate the distance the mouse has moved
				int deltaX = newX - x;
				int deltaY = newY - y;

				// Move the window by the same distance
				if (!TopPanel.isMaximized) {
					setLocation(getX() + deltaX, getY() + deltaY);
				}
				// Update the stored mouse position
				x = newX;
				y = newY;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// Store the initial mouse position
				x = e.getXOnScreen();
				y = e.getYOnScreen();
			}
		});

		BottomPanel bottomPanel = new BottomPanel();
		bottomPanel.setBackground(new Color(0, 0, 0));
		TopPanel topPanel = new TopPanel(this);
		RightPanel rightPanel = new RightPanel();
		LeftPanel leftPanel = new LeftPanel(rightPanel);

		getContentPane().add(leftPanel, BorderLayout.WEST);

		// Add the top/bottom panels to the frame's NORTH/SOUTH region of the rightPanel
		JPanel rightPanelWrapper = new JPanel(new BorderLayout());
		rightPanelWrapper.add(topPanel, BorderLayout.NORTH);
		rightPanelWrapper.add(rightPanel, BorderLayout.CENTER);
		rightPanelWrapper.add(bottomPanel, BorderLayout.SOUTH);
		getContentPane().add(rightPanelWrapper, BorderLayout.CENTER);

		// Add a ComponentListener to the parent JPanel to detect size changes
		rightPanelWrapper.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Calculate the new preferred size based on the parent's size
				Dimension parentSize = rightPanelWrapper.getSize();
				double newHeight = parentSize.height * 0.12;

				// Update the child JPanel's preferred size and revalidate the layout
				bottomPanel.setPreferredSize(new Dimension(bottomPanel.getWidth(), (int) newHeight));
				bottomPanel.revalidate();
			}
		});
	}

}
