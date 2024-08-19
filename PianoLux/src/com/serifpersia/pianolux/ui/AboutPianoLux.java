package com.serifpersia.pianolux.ui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.serifpersia.pianolux.Updater;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class AboutPianoLux extends JPanel {

	static Updater updator = new Updater();

	private ImageIcon pianoLuxIcon;
	private ImageIcon exitIcon;

	public AboutPianoLux() {

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

		pianoLuxIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/PianoLux.png")).getImage()
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

		JPanel pnl_aboutPianoLux = new JPanel();
		add(pnl_aboutPianoLux, BorderLayout.CENTER);
		pnl_aboutPianoLux.setLayout(new BorderLayout(0, 0));

		JLabel pianoLuxLogo = new JLabel("");
		pianoLuxLogo.setHorizontalAlignment(SwingConstants.CENTER);
		pianoLuxLogo.setIcon(pianoLuxIcon);
		pnl_aboutPianoLux.add(pianoLuxLogo, BorderLayout.CENTER);

		JPanel pnl__copyrightLabel = new JPanel();
		pnl_aboutPianoLux.add(pnl__copyrightLabel, BorderLayout.SOUTH);
		pnl__copyrightLabel.setLayout(new GridLayout(3, 0, 0, 0));

		JLabel lb_pianoLux = new JLabel("PianoLux ");
		lb_pianoLux.setForeground(Color.WHITE);
		lb_pianoLux.setHorizontalAlignment(SwingConstants.CENTER);
		lb_pianoLux.setFont(new Font("Poppins", Font.BOLD, 40));
		pnl__copyrightLabel.add(lb_pianoLux);

		JLabel lb_version = new JLabel("Version " + updator.VersionTag);
		lb_version.setHorizontalAlignment(SwingConstants.CENTER);
		lb_version.setForeground(Color.WHITE);
		lb_version.setFont(new Font("Poppins", Font.BOLD, 24));
		pnl__copyrightLabel.add(lb_version);

		int currentYear = LocalDate.now().getYear();
		JLabel lb_copyright = new JLabel("Copyright © " + currentYear + " serifpersia");
		lb_copyright.setForeground(Color.WHITE);
		lb_copyright.setHorizontalAlignment(SwingConstants.CENTER);
		lb_copyright.setFont(new Font("Poppins", Font.BOLD, 18));
		pnl__copyrightLabel.add(lb_copyright);
	}
}
