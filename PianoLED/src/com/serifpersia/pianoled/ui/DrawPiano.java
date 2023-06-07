package com.serifpersia.pianoled.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPiano extends JPanel {
	public final static Map<Integer, Double> blackKeyOffset = Map.of(1, -0.7, 3, -0.5, 6, -0.5, 8, -0.5, 10, -0.5); // starts
																													// with
																													// C
	public final static int NUM_KEYS = 88;
	public final static int NUM_WHITE_KEYS = 52;
	public final static int FIRST_KEY_PITCH_OFFSET = 21;
	public final static int MIDDLE_C_PITCH = 60;
	public final static double pianoWidthToHeightRatio = 122 / 15 * 2;
	public final static double pianoBlackToWhiteWidhtRatio = 0.5;
	public final static double pianoBlackToWhiteHeightRatio = 0.65;

	private int[] keysPressed = new int[NUM_KEYS];
	private int[] keysXPos = new int[NUM_KEYS];

	public void setPianoKey(int pitch, int on) {
		keysPressed[pitch - FIRST_KEY_PITCH_OFFSET] = on;
		repaint();
	}

	public void resetPianoKeys() {
		keysPressed = new int[NUM_KEYS];
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int whiteKeyWidth = getWhiteKeyWidth();
		int whiteKeyHeight = getWhiteKeyHeight();

		int blackKeyWidth = getBlackKeyWidth();
		int blackKeyHeight = getBlackKeyHeight();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(new Font("Arial", Font.PLAIN, 10));

		int currentX = getStartX();

		// Draw white keys
		for (int i = 0; i < NUM_KEYS; i++) {
			int pitch = i + FIRST_KEY_PITCH_OFFSET;

			if (!isBlackKey(pitch)) {
				if (keysPressed[i] == 1) {
					g.setColor(GetUI.selectedColor);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect(currentX, 0, whiteKeyWidth, whiteKeyHeight);
				g.setColor(new Color(100, 100, 100));
				g.drawRect(currentX, 0, whiteKeyWidth, whiteKeyHeight);
				keysXPos[i] = currentX;
				currentX += whiteKeyWidth;

				if (i == MIDDLE_C_PITCH - FIRST_KEY_PITCH_OFFSET)
					g.drawString("C", currentX - whiteKeyWidth / 2 - 3, whiteKeyHeight - 10);
			}
		}

		// Reset currentX for drawing black keys
		currentX = getStartX();

		// Draw black keys
		for (int i = 0; i < NUM_KEYS; i++) {
			int pitch = i + FIRST_KEY_PITCH_OFFSET;

			if (isBlackKey(pitch)) {
				currentX += (int) (blackKeyWidth * getBlackKeyOffset(pitch));
				if (keysPressed[i] == 1) {
					g.setColor(GetUI.selectedColor);
				} else {
					g.setColor(Color.BLACK);
				}
				g.fillRect(currentX, 0, blackKeyWidth, blackKeyHeight);
				g.setColor(new Color(100, 100, 100));
				g.draw3DRect(currentX, 0, blackKeyWidth, blackKeyHeight, true);
				keysXPos[i] = currentX;

				currentX -= (int) (blackKeyWidth * getBlackKeyOffset(pitch));
			} else {
				currentX += whiteKeyWidth;
			}
		}

		// Highlight Piano Size
//		g.setColor(new Color(0, 0, 0, 127)); // set the color of the rectangles to black with 50% transparency
//		g.fillRect(0, 0, GetUI.rectASizeX, 70); // draw the first rectangle
//		g.fillRect(GetUI.rectBX, 0, GetUI.rectBSizeX, 70); // draw the second rectangle
	}

	private int getStartX() {
		return (getWidth() - getPianoWidth()) / 2;
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
		return getPianoWidth() / NUM_WHITE_KEYS;
	}

	public int getPianoWidth() {
		int w = getWidth();
		int h = getHeight();
		if (w / h > pianoWidthToHeightRatio) {
			w = (int) (h * pianoWidthToHeightRatio);
		}
		// taking into account rounding
		return w / NUM_WHITE_KEYS * NUM_WHITE_KEYS;
	}

	void pianoKeyAction(int x, int y, boolean released) {
		int key = findClickedKey(x, y);
		if (key >= 0) {
			if (released) {
				keysPressed[key] = 1;
			} else {
				keysPressed[key] = 0;
			}
			repaint();
		}
	}

	private int findClickedKey(int x, int y) {
		for (int i = 0; i < NUM_KEYS; i++) {
			int pitch = i + FIRST_KEY_PITCH_OFFSET;
			int keyWidth = isBlackKey(pitch) ? getBlackKeyWidth() : getWhiteKeyWidth();
			int keyHeight = isBlackKey(pitch) ? getBlackKeyHeight() : getWhiteKeyHeight();

			if (x >= keysXPos[i] && x <= keysXPos[i] + keyWidth && y >= 0 && y <= keyHeight) {
				GetUI.leftMaxPitch = pitch;
				return i;
			}
		}
		return -1; // No key found
	}

	public float getKeyXPos(int pitch) {
		return keysXPos[pitch - FIRST_KEY_PITCH_OFFSET];
	}

	public boolean isBlackKey(int pitch) {
		return blackKeyOffset.keySet().contains(pitch % 12);
	}

	private Double getBlackKeyOffset(int pitch) {
		return blackKeyOffset.get(pitch % 12);
	}

	public float getKeyWidth(int pitch) {
		return isBlackKey(pitch) ? getBlackKeyWidth() : getWhiteKeyWidth();
	}

	public float getKeyHeight(int pitch) {
		return isBlackKey(pitch) ? getBlackKeyHeight() : getWhiteKeyHeight();
	}

	public int getNumKeys() {
		return NUM_KEYS;
	}
}