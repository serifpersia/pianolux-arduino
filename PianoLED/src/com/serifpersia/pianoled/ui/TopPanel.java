package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import com.serifpersia.pianoled.Updater;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {

	private JFrame AboutPianoLEDDialog;

	private ImageIcon pianoLEDIcon;

	static Updater updator = new Updater();

	public static boolean isMaximized = false;

	private long lastClickTime = 0;

	public TopPanel(JFrame parentFrame) {
		setBackground(new Color(21, 25, 28));
		setPreferredSize(new Dimension(getWidth(), 40)); // Set the height to 50 pixels
		setLayout(new BorderLayout(0, 0));

		pianoLEDIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/AppIcon.png")).getImage()
				.getScaledInstance(45, 45, Image.SCALE_SMOOTH));

		// Window max/windowed based on double click
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				long clickTime = System.currentTimeMillis();
				if (clickTime - lastClickTime <= 300 && isMaximized) {

					parentFrame.setExtendedState(JFrame.NORMAL);
					isMaximized = false; // Update the flag
				} else if (clickTime - lastClickTime <= 300 && !isMaximized) {

					parentFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
					isMaximized = true;
				}
				lastClickTime = clickTime;
			}
		});

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

		JPanel iconPanel = new JPanel();
		add(iconPanel, BorderLayout.WEST);
		iconPanel.setLayout(new BorderLayout(0, 0));

		JButton btnPianoLED = new JButton("");
		btnPianoLED.setIcon(pianoLEDIcon);
		btnPianoLED.setBackground(new Color(21, 25, 28));
		btnPianoLED.setFocusable(false);
		btnPianoLED.setBorderPainted(false);
		btnPianoLED.setPreferredSize(new Dimension(100, 0)); // Set preferred size
		iconPanel.add(btnPianoLED);
		btnPianoLED.addActionListener(e -> showAboutPianoLEDDialog());

		JLabel version = new JLabel("PianoLED" + updator.VersionTag);
		version.setHorizontalAlignment(SwingConstants.LEFT);
		version.setBounds(5, 72, 150, 15);
		version.setFont(new Font("Tahoma", Font.BOLD, 20));
		version.setForeground(Color.WHITE);
		add(version);

		JPanel buttonsPanel = new JPanel(); // Create a panel to hold the buttons
		buttonsPanel.setOpaque(false); // Make the panel transparent

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

	private void showAboutPianoLEDDialog() {

		AboutPianoLEDDialog = new JFrame("AboutPianoLED");
		AboutPianoLEDDialog.setUndecorated(true);
		

		AboutPianoLED aboutPanel = new AboutPianoLED();

		AboutPianoLEDDialog.add(aboutPanel);

		AboutPianoLEDDialog.setSize(300, 400);
		AboutPianoLEDDialog.setLocationRelativeTo(null);
		AboutPianoLEDDialog.setVisible(true);
	}

}
