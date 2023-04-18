package com.serifpersia.pianoled.ui;

import javax.swing.JPanel;
import java.awt.CardLayout;

@SuppressWarnings("serial")
public class RightPanel extends JPanel {

	public RightPanel() {
		setLayout(new CardLayout(0, 0));

		// UI
		// Create an object of DashboardPanel
		DashboardPanel dashboardPanel = new DashboardPanel();
		LivePlayPanel livePlayPanel = new LivePlayPanel();
		ControlsPanel controlsPanel = new ControlsPanel();
		LearnPanel learnPanel = new LearnPanel(controlsPanel);

		// Add the panels objects to the RightPanel
		add(dashboardPanel, "Dashboard");
		add(livePlayPanel, "LivePlay");
		add(controlsPanel, "Controls");
		add(learnPanel, "Learn");

		// Show the Dashboard panel
		CardLayout cl = (CardLayout) (getLayout());
		cl.show(this, "Dashboard");
	}

}
