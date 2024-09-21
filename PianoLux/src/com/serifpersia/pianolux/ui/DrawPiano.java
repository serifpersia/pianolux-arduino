package com.serifpersia.pianolux.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPiano extends JPanel {
	public final static Map<Integer, Double> blackKeyOffset = Map.of(1, -0.7, 3, -0.5, 6, -0.5, 8, -0.5, 10, -0.5);

	public final static int NUM_KEYS = 88;
	public final static int NUM_WHITE_KEYS = 52;
	public final static int FIRST_KEY_PITCH_OFFSET = 21;
	public final static int MIDDLE_C_PITCH = 60;
	public final static double pianoWidthToHeightRatio = 122 / 15 * 2;
	public final static double pianoBlackToWhiteWidhtRatio = 0.6;
	public final static double pianoBlackToWhiteHeightRatio = 0.65;

	private Color whiteKeyColor = new Color(246, 246, 246);
	private Color blackKeyColor = new Color(54, 54, 54);
	private Color whiteKeyHighlightColor = new Color(130, 130, 130);

	private int[] key = new int[NUM_KEYS];
	private int[] keysXPos = new int[NUM_KEYS];

	private int hoveredKeyIndex = -1;
	private boolean isMousePressed = false;

	public static Map<Integer, Integer> changesMap = new HashMap<>();

	public DrawPiano() {
		for (int i = 0; i < NUM_KEYS; i++) {
			changesMap.put(i, 0);
		}

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				isMousePressed = true;
				updateHoveredIndex(e.getX(), e.getY());

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (hoveredKeyIndex != -1) {
					openDialogForKey(hoveredKeyIndex);
				}
				isMousePressed = false;
				hoveredKeyIndex = -1;

				repaint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				updateHoveredIndex(e.getX(), e.getY());
			}
		});
	}

	private void openDialogForKey(int keyIndex) {
		Integer[] options = new Integer[25];
		for (int i = -12; i <= 12; i++) {
			options[i + 12] = i;
		}

		JPanel panel = new JPanel();

		JComboBox<Integer> comboBox = new JComboBox<>(options);

		int currentOffset = DrawPiano.getOffsetForKey(keyIndex);
		comboBox.setSelectedItem(currentOffset);
		panel.add(comboBox);

		comboBox.addActionListener(e -> {
			applyOffsetToKey(keyIndex, (int) comboBox.getSelectedItem());

		});

		JButton resetButton = new JButton("Reset All");
		resetButton.addActionListener(e -> {
			comboBox.setSelectedIndex(12);
			resetAllOffsets();
		});
		panel.add(resetButton);

		JOptionPane.showConfirmDialog(this, panel, "Select Offset", JOptionPane.DEFAULT_OPTION);

	}

	private void applyOffsetToKey(int keyIndex, int selectedOffset) {
		changesMap.put(keyIndex, selectedOffset);
		repaint();
	}

	private void resetAllOffsets() {
		for (int i = 0; i < NUM_KEYS; i++) {
			changesMap.put(i, 0);
		}
		repaint();
	}

	public static int getOffsetForKey(int keyIndex) {
		int offset = changesMap.getOrDefault(keyIndex, 0);
		return offset;
	}

	private void updateHoveredIndex(int x, int y) {
		int newKeyIndex = findKeyLocation(x, y);

		if (newKeyIndex != hoveredKeyIndex) {
			hoveredKeyIndex = newKeyIndex;
			repaint();
		}
	}

	public void setPianoKey(int pitch, int on) {
		key[pitch - FIRST_KEY_PITCH_OFFSET] = on;
		repaint();
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
		g.setFont(new Font("Poppins", Font.PLAIN, 10));

		int currentX = getStartX();

		for (int i = 0; i < NUM_KEYS; i++) {
			int pitch = i + FIRST_KEY_PITCH_OFFSET;

			if (!isBlackKey(pitch)) {

				if (pnl_Colors.keyColor_toggle.isSelected()) {
					g.setColor((i == hoveredKeyIndex && isMousePressed) || key[i] == 1 ? GetUI.selectedColor
							: whiteKeyColor);
				} else {
					g.setColor((i == hoveredKeyIndex && isMousePressed) || key[i] == 1 ? whiteKeyHighlightColor
							: whiteKeyColor);
				}

				g.fillRect(currentX, 0, whiteKeyWidth, whiteKeyHeight);
				g.setColor(new Color(15, 15, 15));
				g.drawRect(currentX, 0, whiteKeyWidth, whiteKeyHeight);
				keysXPos[i] = currentX;
				currentX += whiteKeyWidth;

				if (i == MIDDLE_C_PITCH - FIRST_KEY_PITCH_OFFSET)
					g.drawString("C", currentX - whiteKeyWidth / 2 - 3, whiteKeyHeight - 10);
			}
		}

		currentX = getStartX();

		for (int i = 0; i < NUM_KEYS; i++) {
			int pitch = i + FIRST_KEY_PITCH_OFFSET;

			if (isBlackKey(pitch)) {
				currentX += (int) (blackKeyWidth * getBlackKeyOffset(pitch));

				if (pnl_Colors.keyColor_toggle.isSelected()) {
					g.setColor((i == hoveredKeyIndex && isMousePressed) || key[i] == 1 ? GetUI.selectedColor
							: blackKeyColor);
				} else {

					g.setColor((i == hoveredKeyIndex && isMousePressed) || key[i] == 1 ? Color.BLACK : blackKeyColor);
				}

				g.fillRect(currentX, 0, blackKeyWidth, blackKeyHeight);

				g.setColor(blackKeyColor);
				g.draw3DRect(currentX, 0, blackKeyWidth, blackKeyHeight, true);

				keysXPos[i] = currentX;

				currentX -= (int) (blackKeyWidth * getBlackKeyOffset(pitch));
			} else {
				currentX += whiteKeyWidth;
			}
		}

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
		return w / NUM_WHITE_KEYS * NUM_WHITE_KEYS;
	}

	private int findKeyLocation(int x, int y) {
		for (int i = 0; i < NUM_KEYS; i++) {
			int pitch = i + FIRST_KEY_PITCH_OFFSET;
			int keyWidth = isBlackKey(pitch) ? getBlackKeyWidth() : getWhiteKeyWidth();
			int keyHeight = isBlackKey(pitch) ? getBlackKeyHeight() : getWhiteKeyHeight();

			if (x >= keysXPos[i] && x <= keysXPos[i] + keyWidth && y >= 0 && y <= keyHeight) {
				return i;
			}
		}
		return -1;
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
