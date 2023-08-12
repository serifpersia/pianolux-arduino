package com.serifpersia.pianoled.ui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.serifpersia.pianoled.Updater;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class AboutPianoLED extends JPanel {

	static Updater updator = new Updater();

	private ImageIcon pianoLEDIcon;
	private ImageIcon exitIcon;

	public AboutPianoLED() {

		setLayout(new BorderLayout(0, 0));

		this.setFocusable(true);

		// Add key listener to panel
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Check if the pressed key is ESC
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					// Set the visibility of the panel to false
					((Window) getRootPane().getParent()).dispose();
				}
			}
		});

		pianoLEDIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/PianoLED.png")).getImage()
				.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

		exitIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/exit.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));

		// Set focusable to true to receive key events

		JPanel pnl_topButtonPanel = new JPanel();
		pnl_topButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		add(pnl_topButtonPanel, BorderLayout.NORTH);

		JButton exitButton = new JButton("");
		exitButton.setBackground(new Color(25, 25, 25));
		exitButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		exitButton.setBorderPainted(false);
		exitButton.setIcon(exitIcon);

		exitButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				getTopLevelAncestor().setVisible(false);
			}
		});

		exitButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				((Window) getRootPane().getParent()).dispose();
			}
		});

		pnl_topButtonPanel.add(exitButton);

		JPanel pnl_aboutPianoLED = new JPanel();
		add(pnl_aboutPianoLED, BorderLayout.CENTER);
		pnl_aboutPianoLED.setLayout(new BorderLayout(0, 0));

		JLabel pianoLEDBLogo = new JLabel("");
		pianoLEDBLogo.setHorizontalAlignment(SwingConstants.CENTER);
		pianoLEDBLogo.setIcon(pianoLEDIcon);
		pnl_aboutPianoLED.add(pianoLEDBLogo, BorderLayout.CENTER);

		JPanel pnl__copyrightLabel = new JPanel();
		pnl_aboutPianoLED.add(pnl__copyrightLabel, BorderLayout.SOUTH);
		pnl__copyrightLabel.setLayout(new GridLayout(3, 0, 0, 0));

		JLabel lb_pianoLED = new JLabel("PianoLED ");
		lb_pianoLED.setForeground(Color.WHITE);
		lb_pianoLED.setHorizontalAlignment(SwingConstants.CENTER);
		lb_pianoLED.setFont(new Font("Poppins", Font.BOLD, 40));
		pnl__copyrightLabel.add(lb_pianoLED);

		JLabel lb_version = new JLabel("Version " + updator.VersionTag);
		lb_version.setHorizontalAlignment(SwingConstants.CENTER);
		lb_version.setForeground(Color.WHITE);
		lb_version.setFont(new Font("Poppins", Font.BOLD, 24));
		pnl__copyrightLabel.add(lb_version);

		JLabel lb_copyright = new JLabel("Copyright © 2023 serifpersia");
		lb_copyright.setForeground(Color.WHITE);
		lb_copyright.setHorizontalAlignment(SwingConstants.CENTER);
		lb_copyright.setFont(new Font("Poppins", Font.BOLD, 18));
		pnl__copyrightLabel.add(lb_copyright);
	}
}
