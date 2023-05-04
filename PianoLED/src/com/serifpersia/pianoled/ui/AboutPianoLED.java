package com.serifpersia.pianoled.ui;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.Updater;

import javax.swing.BorderFactory;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class AboutPianoLED extends JPanel {

	static Updater updator = new Updater();
	private ImageIcon pianoLEDIcon;

	public AboutPianoLED() {

		setBackground(new Color(21, 25, 28));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 300, 0 };
		gridBagLayout.rowHeights = new int[] { 200, 36, 81, 33, 71, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		pianoLEDIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/AppIcon.png")).getImage()
				.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(21, 25, 28));
		GridBagConstraints gbc_buttonPane = new GridBagConstraints();
		gbc_buttonPane.insets = new Insets(0, 0, 5, 0);
		gbc_buttonPane.fill = GridBagConstraints.BOTH;
		gbc_buttonPane.gridx = 0;
		gbc_buttonPane.gridy = 0;
		add(buttonPane, gbc_buttonPane);
		buttonPane.setLayout(new BorderLayout(0, 0));

		JLabel exitX = new JLabel("X");
		exitX.setHorizontalAlignment(SwingConstants.RIGHT);
		exitX.setBackground(new Color(21, 25, 28));
		exitX.setForeground(new Color(255, 255, 255));
		exitX.setFont(new Font("Tahoma", Font.BOLD, 25));
		exitX.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Add an empty border on the right side
		buttonPane.add(exitX);
		exitX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getTopLevelAncestor().setVisible(false);
			}
		});

		JButton btnPianoLED = new JButton("");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 0;
		gbc_button.gridy = 1;
		btnPianoLED.setIcon(pianoLEDIcon);
		btnPianoLED.setBackground(new Color(21, 25, 28));
		btnPianoLED.setFocusable(false);
		btnPianoLED.setBorderPainted(false);
		add(btnPianoLED, gbc_button);

		JLabel lbPianoLED = new JLabel("PianoLED");
		lbPianoLED.setHorizontalAlignment(SwingConstants.CENTER);
		lbPianoLED.setForeground(new Color(255, 255, 255));
		lbPianoLED.setFont(new Font("Tahoma", Font.BOLD, 30));
		GridBagConstraints gbc_lbPianoLED = new GridBagConstraints();
		gbc_lbPianoLED.insets = new Insets(0, 0, 5, 0);
		gbc_lbPianoLED.gridx = 0;
		gbc_lbPianoLED.gridy = 2;
		add(lbPianoLED, gbc_lbPianoLED);

		JLabel lbPianoLEDVersion = new JLabel("Version " + updator.VersionTag);
		lbPianoLEDVersion.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbPianoLEDVersion.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lbPianoLEDVersion = new GridBagConstraints();
		gbc_lbPianoLEDVersion.insets = new Insets(0, 0, 5, 0);
		gbc_lbPianoLEDVersion.gridx = 0;
		gbc_lbPianoLEDVersion.gridy = 3;
		add(lbPianoLEDVersion, gbc_lbPianoLEDVersion);

		JLabel lbCopyright = new JLabel("Copyright © 2023 serifpersia");
		lbCopyright.setForeground(Color.WHITE);
		lbCopyright.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lbCopyright = new GridBagConstraints();
		gbc_lbCopyright.gridx = 0;
		gbc_lbCopyright.gridy = 4;
		add(lbCopyright, gbc_lbCopyright);

		exitX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getTopLevelAncestor().setVisible(false);
			}
		});

		// Add key listener to panel
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Check if the pressed key is ESC
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					// Set the visibility of the panel to false
					getTopLevelAncestor().setVisible(false);
				}
			}
		});
		// Set focusable to true to receive key events
		this.setFocusable(true);
	}
}
