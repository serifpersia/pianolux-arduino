package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoLED;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	DrawPiano piano = new DrawPiano();

	public BottomPanel(PianoLED pianoLED) {
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
	}

	public DrawPiano getPiano() {
		return piano;
	}
}