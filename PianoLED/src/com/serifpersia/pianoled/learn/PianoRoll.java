package com.serifpersia.pianoled.learn;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.ui.DrawPiano;

@SuppressWarnings("serial")
public class PianoRoll extends JPanel {

	public static final int PIANO_ROLL_HEIGHT_IN_SEC = 3;
	private static final Color WHITE_NOTE_COLOR = new Color(145, 225, 66);
	private static final Color BLACK_NOTE_COLOR = new Color(122, 164, 212);
	private static final int TEXT_HEIGHT = 15;
	private MidiPlayer player;
	private long currentTick;
	private DrawPiano piano;
	private PianoLED pianoLED;
	private double pianoRollHeightInTicks;
	private double pianoRollTickHeight;
	private LinkedList<Note> currentNotes;
	private boolean debug = false;
	private boolean drawLines;
	private LearnPanel learnPanel;

	public PianoRoll(PianoLED pianoLED, LearnPanel learnPanel) {
		this.piano = pianoLED.getDrawPiano();
		this.pianoLED = pianoLED;
		this.learnPanel = learnPanel;
	}

	public void start(MidiPlayer player) {
		this.player = player;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		double ticksPerSecond = 0;
		if (player != null) {
			ticksPerSecond = player.getTicksPerSecond();
			this.pianoRollHeightInTicks = ticksPerSecond * PIANO_ROLL_HEIGHT_IN_SEC;
			this.currentNotes = player.getCurrentNotes((int) (pianoRollHeightInTicks));
			this.currentTick = getCurrentTick();
		}
		this.pianoRollTickHeight = getHeight() / getHeightInTicks();

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

		int x = (int)piano.getKeyXPos(note.getPitch());
		int w = (int)piano.getKeyWidth(note.pitch);
		int y = (int)getTickY(ticksUntilPlayed);
		int h = (int)((note.end - note.start) * pianoRollTickHeight);

		// Draw the note rectangle
		if (piano.isBlackKey(note.pitch)) {
			g.setColor(WHITE_NOTE_COLOR);
		} else {
			g.setColor(BLACK_NOTE_COLOR);	
		}
		g.fillRoundRect(x, y-h, w, h, 5, 5);

		g.setColor(Color.GRAY);
		g.drawRoundRect(x, y-h, w, h, 5, 5);

		if (debug) {
			drawText(g, x, y-25, ""+note.pitch);
			drawText(g, x, y-15, ""+note.start+"("+ticksUntilPlayed+")");
			drawText(g, x, y-5, ""+note.end);
		}
	}

	public double getTickY(long ticksUntilPlayed) {
		return (getHeightInTicks() - ticksUntilPlayed) * pianoRollTickHeight;
	}

	public void drawPianoRoll(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (debug) {
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
		
		if( learnPanel.drawLines() )
		{
			
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