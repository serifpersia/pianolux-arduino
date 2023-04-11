package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.serifpersia.pianoled.Updater;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	static DrawPiano piano = new DrawPiano();

	static Updater updator = new Updater();

	public void setPiano(DrawPiano piano) {
		BottomPanel.piano = piano;
	}

	public BottomPanel() {
		setBackground(Color.BLUE);
		setLayout(null);
		setPreferredSize(new Dimension(getWidth(), 120)); // Set the height to 50 pixels

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

		JLabel version = new JLabel("PianoLED" + updator.VersionTag);
		version.setBounds(5, 72, 150, 15);
		version.setFont(new Font("Montserrat", Font.BOLD, 15));
		version.setForeground(Color.WHITE);
		add(version);
	}

}
