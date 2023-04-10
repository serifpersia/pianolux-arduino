package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	static DrawPiano piano = new DrawPiano();

	public void setPiano(DrawPiano piano) {
		BottomPanel.piano = piano;
	}

	public BottomPanel() {
		setBackground(Color.YELLOW);
		setLayout(null);
		setPreferredSize(new Dimension(getWidth(), 90)); // Set the height to 50 pixels

		piano.setBounds(15, 0, 780, 70);
		add(piano);
	}

}
