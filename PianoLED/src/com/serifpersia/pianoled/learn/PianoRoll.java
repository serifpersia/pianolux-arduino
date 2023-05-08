package com.serifpersia.pianoled.learn;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.ui.DrawPiano;

@SuppressWarnings("serial")
public class PianoRoll extends JPanel {

	public static final int PIANO_ROLL_HEIGHT_IN_SEC = 1;
	private static final Color WHITE_NOTE_COLOR = new Color(122, 164, 212);
	private static final Color BLACK_NOTE_COLOR = new Color(145, 225, 66);
	private static final int TEXT_HEIGHT = 15;
	private MidiPlayer player;
	private long currentTick;
	private DrawPiano piano;
	private int pianoRollHeightInTicks;
	private double pianoRollTickHeight;
	private LinkedList<Note> currentNotes;
	private LearnPanel learnPanel;
	double ticksPerSecond = 0;

	public PianoRoll(PianoLED pianoLED, LearnPanel learnPanel) {
		this.piano = pianoLED.getDrawPiano();
		this.learnPanel = learnPanel;
	}

	public void start(MidiPlayer player) {
		this.player = player;
		ticksPerSecond = player.getTicksPerSecond();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (player != null) {
			this.pianoRollHeightInTicks = (int) (ticksPerSecond * PIANO_ROLL_HEIGHT_IN_SEC);
			this.currentTick = getCurrentTick();
			this.currentNotes = player.getNotesInInterval(this.currentTick, this.currentTick + pianoRollHeightInTicks);
			this.pianoRollTickHeight = getHeight() / getHeightInTicks();
		}

		drawPianoRoll(g);
		if (currentNotes != null && currentNotes.size() > 0)
			drawNotes(g, currentNotes);
	}

	// Visualisation
	public void drawNotes(Graphics g, LinkedList<Note> notes) {
		notes.forEach(note -> drawNote(g, note));
	}

	public void drawNote(Graphics g, Note note) {
		long ticksUntilPlayed = note.start - currentTick;

		int x = (int) piano.getKeyXPos(note.getPitch());
		int w = (int) piano.getKeyWidth(note.pitch);
		int y = (int) getTickY(ticksUntilPlayed);
		int h = (int) ((note.end - note.start) * pianoRollTickHeight);

		// Draw the note rectangle
		if (piano.isBlackKey(note.pitch)) {
			g.setColor(WHITE_NOTE_COLOR);
		} else {
			g.setColor(BLACK_NOTE_COLOR);
		}
		g.fillRoundRect(x, y - h, w, h, 5, 5);

		g.setColor(Color.GRAY);
		// g.drawRoundRect(x, y - h, w, h, 5, 5);

		if (learnPanel.isShowInfoSelected()) {
			drawText(g, x, y - 25, "" + note.pitch);
			drawText(g, x, y - 15, "" + note.start + "(" + ticksUntilPlayed + ")");
			drawText(g, x, y - 5, "" + note.end);
		}
	}

	public double getTickY(long ticksUntilPlayed) {
		return (getHeightInTicks() - ticksUntilPlayed) * pianoRollTickHeight;
	}

	public void drawPianoRoll(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (learnPanel.isShowInfoSelected()) {
			g.setColor(Color.WHITE);
			int h = getHeight();
			int w = getWidth();
			for (int i = 1; i < PIANO_ROLL_HEIGHT_IN_SEC; i++) {
				int y = h - i * h / 5;
				g.drawLine(0, y, w, y);
				drawText(g, w - 50, y - 10, "-" + i + " sec");
			}

			int boxW = 200;
			int boxH = 300;
			int x = w - boxW;
			int y = 10;
			g.drawRoundRect(x, y, boxW, boxH, 10, 10);
			x += 10;
			y += 10;

			y += TEXT_HEIGHT;
			drawText(g, x, y, "Tick: " + currentTick + " / " + getMidiLengthInTicks());
			y += TEXT_HEIGHT;
			drawText(g, x, y, "Tracks: " + getMidiNumTracks());
		}

		if (learnPanel.isShowGridSelected()) {
			drawGrid(g);
		}
	}

	private void drawGrid(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		for (int pitch = DrawPiano.FIRST_KEY_PITCH_OFFSET; pitch <= piano.getNumKeys()
				+ DrawPiano.FIRST_KEY_PITCH_OFFSET; pitch++) {
			if (pitch % 12 == 0) {
				float x = piano.getKeyXPos(pitch);
				g.drawLine((int) x, 0, (int) x, getHeight());
			}
		}

		ArrayList<Integer> bars = player.getBars();
		int barNum = 1;
		for (Integer bar : bars) {
			if (bar > this.currentTick && bar < this.currentTick + pianoRollHeightInTicks) {
				int y = (int) getTickY(bar - this.currentTick);
				g.drawLine(0, y, getWidth(), y);
				drawText(g, 10, y - 10, "" + barNum);
			}
			barNum++;
		}
	}

	private int getMidiNumTracks() {
		if (player != null)
			return player.getNumTracks();
		else
			return 0;
	}

	private long getMidiLengthInTicks() {
		if (player != null)
			return player.sequence.getTickLength();
		else
			return 0;
	}

	private void drawText(Graphics g, int x, int y, String text) {
		g.drawChars(text.toCharArray(), 0, text.length(), x, y);
	}

	private long getCurrentTick() {
		if (player != null)
			return player.getCurrentTick();
		else
			return 0;
	}

	public double getHeightInTicks() {
		return pianoRollHeightInTicks;
	}
}