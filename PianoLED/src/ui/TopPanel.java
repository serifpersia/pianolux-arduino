package ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {

	public TopPanel(JFrame parentFrame) {
		setBackground(new Color(21, 25, 28));
		setPreferredSize(new Dimension(getWidth(), 40)); // Set the height to 50 pixels
		setLayout(new BorderLayout(0, 0));

		JPanel buttonsPanel = new JPanel(); // Create a panel to hold the buttons
		buttonsPanel.setOpaque(false); // Make the panel transparent

		JLabel minimize = new JLabel("-");
		minimize.setHorizontalAlignment(SwingConstants.LEFT);
		minimize.setForeground(new Color(255, 255, 255));
		minimize.setFont(new Font("Tahoma", Font.BOLD, 25));
		minimize.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); // Add an empty border on the right side
		buttonsPanel.add(minimize);
		minimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parentFrame.setState(Frame.ICONIFIED); // Minimize the parent frame

			}
		});

		JLabel maximize = new JLabel("□");
		maximize.setHorizontalAlignment(SwingConstants.CENTER);
		maximize.setForeground(new Color(255, 255, 255));
		maximize.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 35));
		maximize.setBorder(BorderFactory.createEmptyBorder(-10, 0, 0, 20)); // Add an empty border on the right side
		buttonsPanel.add(maximize);
		maximize.addMouseListener(new MouseAdapter() {
			boolean isMaximized = false; // Add a boolean flag to keep track of the window state

			@Override
			public void mouseClicked(MouseEvent e) {
				if (isMaximized) {
					parentFrame.setExtendedState(JFrame.NORMAL); // Restore the window size
					isMaximized = false; // Update the flag
				} else {
					parentFrame.setExtendedState(Frame.MAXIMIZED_BOTH); // Maximize the window
					isMaximized = true; // Update the flag
				}
			}
		});

		JLabel exitX = new JLabel("X");
		exitX.setHorizontalAlignment(SwingConstants.RIGHT);
		exitX.setForeground(new Color(255, 255, 255));
		exitX.setFont(new Font("Tahoma", Font.BOLD, 25));
		exitX.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Add an empty border on the right side
		buttonsPanel.add(exitX);
		exitX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		add(buttonsPanel, BorderLayout.EAST); // Add the buttonsPanel to the EAST position
	}

}
