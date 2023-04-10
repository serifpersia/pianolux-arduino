package ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPiano extends JPanel {

	private static DrawPiano piano;

	private static int[][] Keys = new int[88][2];
	static int whiteKeyPitches[] = { 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55,
			57, 59, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95, 96, 98, 100,
			101, 103, 105, 107, 108 };
// List of white keys in a 88-key piano
	private static int whiteKeys[] = { 0, 2, 3, 5, 7, 8, 10, 12, 14, 15, 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36,
			38, 39, 41, 43, 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79, 80, 82,
			84, 86, 87 };
	private static int[] blackKeys = { 1, 4, 6, 9, 11, 13, 16, 18, 21, 23, 25, 28, 30, 33, 35, 37, 40, 42, 45, 47, 49,
			52, 54, 57, 59, 61, 64, 66, 69, 71, 73, 76, 78, 81, 83, 85 };
// Create a list of x-coordinates for each key
	private static int[] keyXCoordinates = { 11, 40, 56, 86, 101, 116, 145, 161, 191, 206, 221, 251, 266, 296, 311, 326,
			356, 371, 401, 416, 431, 461, 476, 506, 521, 536, 566, 581, 611, 626, 641, 671, 686, 715, 731, 746 };

	public DrawPiano() {
		piano = this;
	}

	public static void setPianoKey(int pitch, int on) {
		Keys[pitch - 21][1] = on;
		Keys[pitch - 21][0] = on;
		piano.repaint();
	}

	public static void resetPianoKeys() {
		Keys = new int[88][2];
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// white keys
		int x = 0;
		for (int i = 0; i < whiteKeys.length; i++) {
			if (Keys[whiteKeys[i]][0] == 1) {
				g.setColor(ControlsPanel.selectedColor);
			} else {
				g.setColor(Color.WHITE);
			}
			g.fillRect(x, 0, 15, 70);
			g.setColor(new Color(100, 100, 100));
			g.drawRect(x, 0, 15, 70);
			x += 15;
		}

		// black keys
		for (int i = 0; i < blackKeys.length; i++) {
			if (Keys[blackKeys[i]][1] == 1) {
				g.setColor(ControlsPanel.selectedColor);
			} else {
				g.setColor(Color.BLACK);
			}
			g.fillRect(keyXCoordinates[i], 0, 8, 40);
			g.setColor(new Color(100, 100, 100));
			g.drawRect(keyXCoordinates[i], 0, 8, 40);
		}

		// Highlight Piano Size
		g.setColor(new Color(0, 0, 0, 127)); // set the color of the rectangles to black with 50% transparency
		g.fillRect(0, 0, GetUI.rectASizeX, 70); // draw the first rectangle
		g.fillRect(GetUI.rectBX, 0, GetUI.rectBSizeX, 70); // draw the second rectangle
	}

	void pianoKeyAction(int x, int y, boolean released) {
		for (int i = 0; i < DrawPiano.whiteKeys.length; i++) {
			// Check if the mouse click was inside a white key
			if (x > i * 15 && x < (i + 1) * 15 && y >= 0 && y <= 133) {
				if (released) {
					DrawPiano.Keys[DrawPiano.whiteKeys[i]][0] = 1;
				} else {
					DrawPiano.Keys[DrawPiano.whiteKeys[i]][0] = 0;
				}
				repaint();
			}
		}
	}
}