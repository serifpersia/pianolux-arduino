package com.serifpersia.pianoled.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LeftPanel extends JPanel {

	private RightPanel rightPanel;
	private JButton dashboardButton;

	private JButton learnButton;
	private ImageIcon dashboardIcon;
	// private ImageIcon dashboardDarkIcon;

	private ImageIcon pianoLEDIcon;
	private JFrame AboutPianoLEDDialog;

	public static boolean learnOn = false;

	public LeftPanel(RightPanel rightPanel) {
		this.rightPanel = rightPanel;

		setPreferredSize(new Dimension(100, getHeight()));
		setLayout(new GridLayout(5, 1));
		JButton AboutButton = new JButton("");
		dashboardButton = createButton("", "Dashboard");
		JButton livePlayButton = createButton("LivePlay", "LivePlay");
		JButton controlsButton = createButton("Controls", "Controls");
		learnButton = createButton("Learn", "Learn");

		dashboardIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/home.png")).getImage()
				.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		// dashboardDarkIcon = new ImageIcon(new
		// ImageIcon(getClass().getResource("/icons/home_dark.png")).getImage()
		// .getScaledInstance(32, 32, Image.SCALE_SMOOTH));

		dashboardButton.setIcon(dashboardIcon);

		pianoLEDIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/AppIcon.png")).getImage()
				.getScaledInstance(45, 45, Image.SCALE_SMOOTH));

		AboutButton.setIcon(pianoLEDIcon);

		AboutButton.setBackground(Color.BLACK);
		AboutButton.setFocusable(false);
		AboutButton.setBorderPainted(false);
		AboutButton.addActionListener(e -> showAboutPianoLEDDialog());

		add(dashboardButton);
		add(controlsButton);
		add(livePlayButton);
		add(learnButton);
		add(AboutButton);

	}

	private JButton createButton(String text, String cardName) {
		JButton button = new JButton(text);
		button.setFont(new Font("Tahoma", Font.BOLD, 18));
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(231, 76, 60));
				button.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				if (!button.isSelected()) {
					button.setBackground(Color.BLACK);
					button.setForeground(Color.WHITE);
				}
			}

			public void mousePressed(MouseEvent e) {
				button.setBackground(new Color(231, 76, 60));
				button.setForeground(Color.WHITE);
				button.setSelected(true);
				CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
				cardLayout.show(rightPanel, cardName);
				for (Component c : getComponents()) {
					if (c instanceof JButton && c != button) {
						((JButton) c).setSelected(false);
						((JButton) c).setBackground(Color.BLACK);
						((JButton) c).setForeground(Color.WHITE);
					}
				}
				if (button == dashboardButton) {
					dashboardButton.setIcon(dashboardIcon);
				} else {
					dashboardButton.setIcon(dashboardIcon);
				}

				if (button == learnButton) {
					System.out.println("in learn mode");
					learnOn = true;
				} else {
					learnOn = false;
				}
			}

		});

		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		return button;
	}

	private void showAboutPianoLEDDialog() {

		AboutPianoLED aboutPanel = new AboutPianoLED();

		if (AboutPianoLEDDialog == null) { // Check if the frame has already been created
			AboutPianoLEDDialog = new JFrame("AboutPianoLED");
			AboutPianoLEDDialog.setUndecorated(true);
			AboutPianoLEDDialog.add(aboutPanel);
			AboutPianoLEDDialog.setSize(300, 400);
			AboutPianoLEDDialog.setLocationRelativeTo(null);
		}
		AboutPianoLEDDialog.setVisible(true);
	}

}
