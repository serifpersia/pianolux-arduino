package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.Updater;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {

	static Updater updator = new Updater();

	public static boolean isMaximized = false;

	public TopPanel(JFrame parentFrame) {
		setBackground(new Color(21, 25, 28));
		setPreferredSize(new Dimension(getWidth(), 40)); // Set the height to 50 pixels
		setLayout(new BorderLayout(0, 0));

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
					int frameX = parentFrame.getX();
					int frameY = parentFrame.getY();
					parentFrame.setLocation(frameX + deltaX, frameY + deltaY);
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

		JPanel buttonsPanel = new JPanel(); // Create a panel to hold the buttons
		buttonsPanel.setOpaque(false); // Make the panel transparent

		JLabel version = new JLabel("PianoLED" + updator.VersionTag);
		version.setHorizontalAlignment(SwingConstants.LEFT);
		version.setBounds(5, 72, 150, 15);
		version.setFont(new Font("Tahoma", Font.BOLD, 20));
		version.setForeground(Color.WHITE);
		add(version);

		JLabel minimize = new JLabel("-");
		minimize.setHorizontalAlignment(SwingConstants.LEFT);
		minimize.setForeground(new Color(255, 255, 255));
		minimize.setFont(new Font("Tahoma", Font.BOLD, 25));
		minimize.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); // Add an empty border on the right side
		buttonsPanel.add(minimize);
		minimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parentFrame.setState(Frame.ICONIFIED); // Minimize the parent frame

			}
		});

		JLabel maximize = new JLabel("□");
		maximize.setHorizontalAlignment(SwingConstants.CENTER);
		maximize.setForeground(new Color(255, 255, 255));
		maximize.setFont(new Font("Tahoma", Font.BOLD, 35));
		maximize.setBorder(BorderFactory.createEmptyBorder(-10, 0, 0, 20)); // Add an empty border on the right side
		buttonsPanel.add(maximize);
		maximize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isMaximized) {
					parentFrame.setExtendedState(JFrame.NORMAL); // Restore the window size
					isMaximized = false; // Update the flag
				} else {
					parentFrame.setExtendedState(Frame.MAXIMIZED_BOTH); // Maximize the window
					isMaximized = true; // Update the flag
				}
			}
		});

		JLabel exitX = new JLabel("X");
		exitX.setHorizontalAlignment(SwingConstants.RIGHT);
		exitX.setForeground(new Color(255, 255, 255));
		exitX.setFont(new Font("Tahoma", Font.BOLD, 25));
		exitX.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Add an empty border on the right side
		buttonsPanel.add(exitX);
		exitX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		add(buttonsPanel, BorderLayout.EAST); // Add the buttonsPanel to the EAST position
	}

}
