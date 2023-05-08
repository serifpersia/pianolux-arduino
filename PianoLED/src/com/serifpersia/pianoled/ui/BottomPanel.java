package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoLED;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	DrawPiano piano = new DrawPiano();
	public static JPanel cardPanel;
	public static CardLayout cardLayout;
	public static JPanel webcamPane;

	public BottomPanel(PianoLED pianoLED) {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		// Create a panel with card layout
		cardPanel = new JPanel();
		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);

		// Add the piano panel to the card panel
		JPanel pianoPane = new JPanel();
		pianoPane.setBackground(Color.BLACK);
		pianoPane.setLayout(new BorderLayout());
		pianoPane.add(piano, BorderLayout.CENTER);
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

		cardPanel.add(pianoPane, "pianoPane");

		// Add the webcam panel to the card panel
		webcamPane = new JPanel();
		webcamPane.setLayout(new BorderLayout(0, 0));
		cardPanel.add(webcamPane, "webcamPane");

		// Add the card panel to the bottom panel
		add(cardPanel, BorderLayout.CENTER);

		// Set the default card to be the piano panel
		cardLayout.show(cardPanel, "pianoPane");

	}

	public DrawPiano getPiano() {
		return piano;
	}
}
