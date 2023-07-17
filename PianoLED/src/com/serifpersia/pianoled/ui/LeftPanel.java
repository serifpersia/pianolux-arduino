package com.serifpersia.pianoled.ui;

import java.awt.CardLayout;
import java.awt.Color;
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
		JButton AboutButton = createButton("", "");
		dashboardButton = createButton("", "Dashboard");
		JButton controlsButton = createButton("Controls", "Controls");

		JButton livePlayButton = createButton("LivePlay", "LivePlay");

		learnButton = createButton("Learn", "Learn");

		dashboardIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/home.png")).getImage()
				.getScaledInstance(32, 32, Image.SCALE_SMOOTH));

		dashboardButton.setIcon(dashboardIcon);

		pianoLEDIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/PianoLED.png")).getImage()
				.getScaledInstance(75, 75, Image.SCALE_SMOOTH));

		AboutButton.setIcon(pianoLEDIcon);

		AboutButton.addActionListener(e -> showAboutPianoLEDDialog());

		add(dashboardButton);
		add(controlsButton);
		add(livePlayButton);
		add(learnButton);
		add(AboutButton);

	}

	private JButton createButton(String text, String cardName) {
		JButton button = new JButton(text);

		button.setFont(new Font("Poppins", Font.PLAIN, 16));
		button.setBackground(new Color(25,25,25));
	

		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		button.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
				cardLayout.show(rightPanel, cardName);

				if (button == learnButton) {
					System.out.println("in learn mode");
					learnOn = true;
				} else {
					learnOn = false;
				}
			}

		});

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
