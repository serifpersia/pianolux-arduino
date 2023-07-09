package com.serifpersia.pianoled.ui;

import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.learn.LearnPanel;

import java.awt.CardLayout;

@SuppressWarnings("serial")
public class RightPanel extends JPanel {

	public RightPanel(PianoLED pianoLED) {
		setLayout(new CardLayout(0, 0));

		// UI
		// Create an object of DashboardPanel
		DashboardPanel dashboardPanel = new DashboardPanel(pianoLED);

		ControlsPanel controlsPanel = new ControlsPanel(pianoLED);
		LearnPanel learnPanel = new LearnPanel(pianoLED);

		pianoLED.getPianoController().addPianoMidiConsumer(learnPanel);

		// Add the panels objects to the RightPanel

		add(dashboardPanel, "Dashboard");
		add(controlsPanel, "Controls");
		add(learnPanel, "Learn");

		// Show the Dashboard panel
		CardLayout cl = (CardLayout) (getLayout());
		cl.show(this, "Dashboard");
	}

}
