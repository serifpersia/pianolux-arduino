package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		piano.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// Call the pianoKeyAction method with the mouse coordinates and the pressed
				// flag set to true
				piano.pianoKeyAction(e.getX(), e.getY(), true);
			}

			public void mouseReleased(MouseEvent e) {
				// Call the pianoKeyAction method with the mouse coordinates and the pressed
				// flag set to false
				piano.pianoKeyAction(e.getX(), e.getY(), false);
			}
		});
	}

}
