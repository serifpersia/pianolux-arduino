
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class LeftPanel extends JPanel {

	private RightPanel rightPanel;

	public LeftPanel(RightPanel rightPanel) {
		this.rightPanel = rightPanel;

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(100, getHeight()));
		setLayout(new GridLayout(4, 1));

		JButton livePlayButton = createButton("LivePlay", "LivePlay");
		JButton controlsButton = createButton("Controls", "Controls");
		JButton learnButton = createButton("Learn", "Learn");
		JButton dashboardButton = createButton("", "Dashboard");
		// Set the Dashboard button icon
		ImageIcon DashboardIcon = new ImageIcon(PianoLED.class.getResource("/icons/home.png"));
		Image img = DashboardIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		dashboardButton.setIcon(new ImageIcon(img));

		add(livePlayButton);
		add(controlsButton);
		add(learnButton);
		add(dashboardButton);
	}

	private JButton createButton(String text, String cardName) {
		JButton button = new JButton(text);
		button.setFont(new Font("Montserrat", Font.BOLD, 14));
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.setOpaque(true);
		button.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				button.setBackground(Color.WHITE);
				button.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent e) {
				if (!button.isSelected()) {
					button.setBackground(Color.BLACK);
					button.setForeground(Color.WHITE);
				}
			}

			public void mousePressed(MouseEvent e) {
				button.setBackground(Color.WHITE);
				button.setForeground(Color.BLACK);
				button.setSelected(true);
				CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
				cardLayout.show(rightPanel, cardName);
				for (Component c : getComponents()) {
					if (c instanceof JButton && c != button) {
						((JButton) c).setSelected(false);
						((JButton) c).setBackground(Color.BLACK);
						((JButton) c).setForeground(Color.WHITE);
					}
				}
			}
		});
		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		return button;
	}

}
