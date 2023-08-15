package com.serifpersia.pianoled.ui;

import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.learn.LearnPanel;
import com.serifpersia.pianoled.liveplay.LivePlayPanel;

import java.awt.CardLayout;

@SuppressWarnings("serial")
public class CenterPanel extends JPanel {

	public CenterPanel(PianoLED pianoLED) {
		setLayout(new CardLayout(0, 0));

		DashboardPanel dashboardPanel = new DashboardPanel(pianoLED);
		ControlsPanel controlsPanel = new ControlsPanel(pianoLED);
		LivePlayPanel livePanel = new LivePlayPanel(pianoLED);
		LearnPanel learnPanel = new LearnPanel(pianoLED);

		pianoLED.getPianoController().addPianoMidiConsumer(learnPanel);

		// Add the panels objects to the RightPanel
		add(dashboardPanel, "Dashboard");
		add(controlsPanel, "Controls");
		add(livePanel, "LivePlay");
		add(learnPanel, "Learn");

		// Show the Dashboard panel
		CardLayout cl = (CardLayout) (getLayout());
		cl.show(this, "Dashboard");
	}

}
