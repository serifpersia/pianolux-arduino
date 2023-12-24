package com.serifpersia.pianolux.ui;

import javax.swing.JPanel;

import com.serifpersia.pianolux.PianoLux;
import com.serifpersia.pianolux.learn.LearnPanel;
import com.serifpersia.pianolux.liveplay.LivePlayPanel;

import java.awt.CardLayout;

@SuppressWarnings("serial")
public class CenterPanel extends JPanel {

	public CenterPanel(PianoLux pianoLux) {
		setLayout(new CardLayout(0, 0));

		DashboardPanel dashboardPanel = new DashboardPanel(pianoLux);
		ControlsPanel controlsPanel = new ControlsPanel(pianoLux);
		LivePlayPanel livePanel = new LivePlayPanel(pianoLux);
		LearnPanel learnPanel = new LearnPanel(pianoLux);

		pianoLux.getPianoController().addPianoMidiConsumer(learnPanel);

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
