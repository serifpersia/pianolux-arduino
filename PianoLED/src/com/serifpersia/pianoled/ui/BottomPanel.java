package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoLED;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	DrawPiano piano = new DrawPiano();
	public static JPanel cardPanel;
	public static CardLayout cardLayout;
	public static JPanel webcamPane;

	public BottomPanel(PianoLED pianoLED) {

		init();

	}

	private void init() {

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

		cardPanel.add(pianoPane, "pianoPane");

		// Add the webcam panel to the card panel
		webcamPane = new JPanel();

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
