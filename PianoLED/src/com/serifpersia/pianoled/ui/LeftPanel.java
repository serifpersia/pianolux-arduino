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
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LeftPanel extends JPanel {

	private RightPanel rightPanel;
	private JButton dashboardButton;
	private ImageIcon dashboardIcon;
	// private ImageIcon dashboardDarkIcon;

	public LeftPanel(RightPanel rightPanel) {
		this.rightPanel = rightPanel;

		setPreferredSize(new Dimension(100, getHeight()));
		setLayout(new GridLayout(4, 1));

		JButton livePlayButton = createButton("LivePlay", "LivePlay");
		JButton controlsButton = createButton("Controls", "Controls");
		JButton learnButton = createButton("Learn", "Learn");

		dashboardIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/home.png")).getImage()
				.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		// dashboardDarkIcon = new ImageIcon(new
		// ImageIcon(getClass().getResource("/icons/home_dark.png")).getImage()
		// .getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		dashboardButton = createButton("", "Dashboard");
		dashboardButton.setIcon(dashboardIcon);

		add(dashboardButton);
		add(controlsButton);
		add(livePlayButton);
		add(learnButton);

	}

	private JButton createButton(String text, String cardName) {
		JButton button = new JButton(text);
		button.setFont(new Font("Montserrat", Font.BOLD, 14));
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.setOpaque(true);
		button.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(200, 63, 73));
				button.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				if (!button.isSelected()) {
					button.setBackground(Color.BLACK);
					button.setForeground(Color.WHITE);
				}
			}

			public void mousePressed(MouseEvent e) {
				button.setBackground(new Color(200, 63, 73));
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
			}

		});

		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		return button;
	}

}
