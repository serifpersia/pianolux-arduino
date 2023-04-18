package com.serifpersia.pianoled.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPiano extends JPanel {

	public static final double pianoWidthToHeightRatio = 122 / 15 * 2;
	public static final double pianoBlackToWhiteWidhtRatio = 0.55;
	public static final double pianoBlackToWhiteHeightRatio = 0.65;

	private static DrawPiano piano;

	private static int[] Keys = new int[88];

	// List of white keys in a 88-key piano
	private static int whiteKeys[] = { 0, 2, 3, 5, 7, 8, 10, 12, 14, 15, 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36,
			38, 39, 41, 43, 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79, 80, 82,
			84, 86, 87 };

	private static int[] blackKeysIdx = { 0, 2, 3, 5, 6 };

	public DrawPiano() {
		piano = this;
	}

	public static void setPianoKey(int pitch, int on) {
		Keys[pitch - 21] = on;
		piano.repaint();
	}

	public static void resetPianoKeys() {
		Keys = new int[88];
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int whiteKeyWidth = getWhiteKeyWidth();
		int whiteKeyHeight = getWhiteKeyHeight();

		int blackKeyWidth = getBlackKeyWidth();
		int blackKeyHeight = getBlackKeyHeight();

		g.setFont(new Font("Arial", Font.PLAIN, 10));

		// white keys
		int x = getStartX();
		for (int i = 0; i < whiteKeys.length; i++) {
			if (Keys[whiteKeys[i]] == 1) {
				g.setColor(ControlsPanel.selectedColor);
			} else {
				g.setColor(Color.WHITE);
			}
			g.fillRect(x, 0, whiteKeyWidth, whiteKeyHeight);
			g.setColor(new Color(100, 100, 100));
			g.drawRect(x, 0, whiteKeyWidth, whiteKeyHeight);

			if (whiteKeys[i] == 60 - 21)
				g.drawString("C", x + whiteKeyWidth / 2 - 3, whiteKeyHeight - 10);

//			g.drawString(""+i, x+whiteKeyWidth/2-3, whiteKeyHeight-20);

			x += whiteKeyWidth;
		}

		// black keys
		x = getStartX();
		for (int i = 0; i < whiteKeys.length - 1; i++) {
			// draw black keys
			if (Arrays.binarySearch(blackKeysIdx, i % 7) > -1) {
				if (whiteKeys[i] + 1 < Keys.length && Keys[whiteKeys[i] + 1] == 1) {
					g.setColor(ControlsPanel.selectedColor);
				} else {
					g.setColor(Color.BLACK);
				}
				g.fillRect(x + whiteKeyWidth - blackKeyWidth / 2, 0, blackKeyWidth, blackKeyHeight);
				g.setColor(new Color(100, 100, 100));
				g.drawRect(x + whiteKeyWidth - blackKeyWidth / 2, 0, blackKeyWidth, blackKeyHeight);
			}

			x += whiteKeyWidth;
		}

		// Highlight Piano Size
//		g.setColor(new Color(0, 0, 0, 127)); // set the color of the rectangles to black with 50% transparency
//		g.fillRect(0, 0, GetUI.rectASizeX, 70); // draw the first rectangle
//		g.fillRect(GetUI.rectBX, 0, GetUI.rectBSizeX, 70); // draw the second rectangle
	}

	private int getStartX() {
		return (getWidth() - getPianoWidth()) / 2 + 10;
	}

	public int getWhiteKeyHeight() {
		return getHeight();
	}

	public int getBlackKeyWidth() {
		return (int) (getWhiteKeyWidth() * pianoBlackToWhiteWidhtRatio);
	}

	public int getBlackKeyHeight() {
		return (int) (getWhiteKeyHeight() * pianoBlackToWhiteHeightRatio);
	}

	public int getWhiteKeyWidth() {
		return getPianoWidth() / whiteKeys.length;
	}

	public int getPianoWidth() {
		int w = getWidth();
		int h = getHeight();
		if (w / h > pianoWidthToHeightRatio) {
			w = (int) (h * pianoWidthToHeightRatio);
		}
		return w;
	}

	void pianoKeyAction(int x, int y, boolean released) {
		int whiteKeyWidth = getWhiteKeyWidth();
		int whiteKeyHeight = getWhiteKeyHeight();
		for (int i = 0; i < DrawPiano.whiteKeys.length; i++) {
			// Check if the mouse click was inside a white key
			if (x > i * whiteKeyWidth && x < (i + 1) * whiteKeyWidth && y >= 0 && y <= whiteKeyHeight) {
				if (released) {
					DrawPiano.Keys[DrawPiano.whiteKeys[i]] = 1;
				} else {
					DrawPiano.Keys[DrawPiano.whiteKeys[i]] = 0;
				}
				repaint();
			}
		}
	}
}