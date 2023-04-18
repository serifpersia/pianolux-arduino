package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.serifpersia.pianoled.Updater;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	static DrawPiano piano = new DrawPiano();

	static Updater updator = new Updater();

	public BottomPanel() {
		setBackground(new Color(21, 25, 28));
		setLayout(new BorderLayout());

		add(piano, BorderLayout.CENTER);
		piano.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// Call the pianoKeyAction method with the mouse coordinates and the pressed
				// flag set to true
				piano.pianoKeyAction(e.getX(), e.getY(), true);
			}

			public void mouseReleased(MouseEvent e) {
				// Call the pianoKeyAction method with the mouse coordinates and the pressed
				// flag set to false
				piano.pianoKeyAction(e.getX(), e.getY(), false);
			}
		});

		JLabel version = new JLabel("PianoLED" + updator.VersionTag);
		version.setBounds(5, 72, 150, 15);
		version.setFont(new Font("Montserrat", Font.BOLD, 15));
		version.setForeground(Color.WHITE);
		add(version, BorderLayout.SOUTH);
	}

}
