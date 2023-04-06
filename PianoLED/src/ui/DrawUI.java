package ui;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawUI extends JPanel {

	int[][] Keys = new int[88][2];
	int whiteKeyPitches[] = { 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55, 57,
			59, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95, 96, 98, 100, 101,
			103, 105, 107, 108 };
// List of white keys in a 88-key piano
	int whiteKeys[] = { 0, 2, 3, 5, 7, 8, 10, 12, 14, 15, 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36, 38, 39, 41,
			43, 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79, 80, 82, 84, 86,
			87 };
	int[] blackKeys = { 1, 4, 6, 9, 11, 13, 16, 18, 21, 23, 25, 28, 30, 33, 35, 37, 40, 42, 45, 47, 49, 52, 54, 57, 59,
			61, 64, 66, 69, 71, 73, 76, 78, 81, 83, 85 };
// Create a list of x-coordinates for each key
	int[] keyXCoordinates = { 11, 40, 56, 86, 101, 116, 145, 161, 191, 206, 221, 251, 266, 296, 311, 326, 356, 371, 401,
			416, 431, 461, 476, 506, 521, 536, 566, 581, 611, 626, 641, 671, 686, 715, 731, 746 };

	public DrawUI() {
		 super();
	}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// white keys
			int x = 0;
			for (int i = 0; i < whiteKeys.length; i++) {
				if (Keys[whiteKeys[i]][0] == 1) {
					g.setColor(new Color(255, 0, 0));
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect(x + 15, 510, 15, 70);
				g.setColor(Color.BLACK);
				g.drawRect(x + 15, 510, 15, 70);
				x += 15;
			}

			for (int i = 0; i < blackKeys.length; i++) {
				if (Keys[blackKeys[i]][1] == 1) {
					g.setColor(new Color(255, 0, 0));
				} else {
					g.setColor(Color.BLACK);
				}
				g.fillRect(keyXCoordinates[i] + 15, 510, 8, 40);
				g.setColor(Color.WHITE);
			}
		
	}
	
}
