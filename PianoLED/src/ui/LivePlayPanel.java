package ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class LivePlayPanel extends JPanel {

	public LivePlayPanel() {
		setBackground(new Color(21, 25, 28));
		setLayout(null);

		JLabel lbLivePlay = new JLabel("LivePlay");
		lbLivePlay.setHorizontalAlignment(SwingConstants.CENTER);
		lbLivePlay.setForeground(Color.WHITE);
		lbLivePlay.setFont(new Font("Montserrat", Font.BOLD, 50));
		lbLivePlay.setBounds(0, 0, 220, 62);
		add(lbLivePlay);

	}
}
