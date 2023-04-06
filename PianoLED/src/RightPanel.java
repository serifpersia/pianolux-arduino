import java.awt.Color;
import javax.swing.JPanel;
import java.awt.CardLayout;

@SuppressWarnings("serial")
public class RightPanel extends JPanel {

	private JPanel Dashboard;
	private JPanel LivePlay;
	private JPanel Controls;
	private JPanel Learn;

	public RightPanel() {

		// Dash Panel
		Dashboard = new JPanel();
		Dashboard.setBackground(new Color(21, 25, 28));

		// LivePlay Panel
		LivePlay = new JPanel();
		LivePlay.setBackground(Color.BLUE);

		// Controls Panel
		Controls = new JPanel();
		Controls.setBackground(Color.YELLOW);

		// Learn Panel
		Learn = new JPanel();
		Learn.setBackground(Color.GREEN);
		setLayout(new CardLayout(0, 0));

		add(Dashboard, "Dashboard");
		Dashboard.setLayout(null);

		add(LivePlay, "LivePlay");
		add(Controls, "Controls");
		add(Learn, "Learn");

		// UI
		// Create an object of DashboardPanel
		DashboardPanel dashboardPanel = new DashboardPanel();
		LivePlayPanel livePlayPanel = new LivePlayPanel();
		ControlsPanel controlsPanel = new ControlsPanel();
		LearnPanel learnPanel = new LearnPanel();

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
