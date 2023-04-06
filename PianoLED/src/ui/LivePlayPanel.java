package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Image;
import javax.swing.ImageIcon;

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
