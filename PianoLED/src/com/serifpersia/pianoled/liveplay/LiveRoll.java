package com.serifpersia.pianoled.liveplay;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.learn.PianoMidiConsumer;
import com.serifpersia.pianoled.ui.DrawPiano;
import com.serifpersia.pianoled.ui.GetUI;

@SuppressWarnings("serial")
public class LiveRoll extends JPanel implements PianoMidiConsumer {

	public static int PIANO_ROLL_HEIGHT_IN_SEC = 2;
	private Color NOTE_COLOR = new Color(145, 225, 66);
	private DrawPiano piano;
	private LivePlayPanel livePanel;

	private LinkedList<NoteWithTime> notes;
	private long startTime;

	public LiveRoll(PianoLED pianoLED, LivePlayPanel livePlayPanel) {

		setBackground(Color.BLACK);

		this.piano = pianoLED.getDrawPiano();
		this.livePanel = livePlayPanel;
		pianoLED.getPianoController().addPianoReceiverConsumer(this);
		setDoubleBuffered(true); // Enable double buffering
		start();
	}

	public LinkedList<NoteWithTime> getNotes() {
		return notes;
	}

	public void start() {
		startTime = System.currentTimeMillis();
		resetNotes();
	}

	private void resetNotes() {
		notes = new LinkedList<>();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		long elapsedTime = getElapsedTime();
		if (livePanel.isShowGridSelected()) {
			drawGrid(g);
		}
		if (notes != null && notes.size() > 0) {
			drawNotes(g, new LinkedList<NoteWithTime>(notes), elapsedTime);
		}
	}

	private long getElapsedTime() {
		return System.currentTimeMillis() - startTime;
	}

	// Visualisation
	public void drawNotes(Graphics g, LinkedList<NoteWithTime> notes, long elapsedTime) {
		notes.forEach(note -> drawNote(g, note, elapsedTime, 0.9, 1.5));
	}

	public void drawNote(Graphics g, NoteWithTime note, long elapsedTime, double whiteKeyScaleFactor,
			double blackKeyScaleFactor) {
		if (note.getEnd() > 0 && note.getEnd() < elapsedTime - PIANO_ROLL_HEIGHT_IN_SEC * 1000) {
// note not visible
			return;
		}
		long noteElapsedTime = note.getStart() - elapsedTime;

		int x = (int) piano.getKeyXPos(note.getPitch());
		int w = (int) piano.getKeyWidth(note.getPitch());
		int y = (int) getTickY(noteElapsedTime);
		int h = note.getEnd() == -1 ? getHeight() * 4000 : (int) timeMsToPixels(note.getEnd() - note.getStart());

		if (livePanel.isCustomColorSelected()) {
			NOTE_COLOR = GetUI.selectedColor;
		} else {
			NOTE_COLOR = new Color(145, 225, 66);
		}

		double scaleFactor = piano.isBlackKey(note.getPitch()) ? blackKeyScaleFactor : whiteKeyScaleFactor;

// Calculate the new width with the appropriate scaleFactor
		int newWidth = (int) (w * scaleFactor);

// Calculate the new x coordinate to keep the same center position
		int newX = x + (w - newWidth) / 2;

// Calculate the darker shade of the NOTE_COLOR for black keys
		Color mainColor;
		if (piano.isBlackKey(note.getPitch())) {
			int darkerRed = (int) (NOTE_COLOR.getRed() * 0.7); // Reduce red component by 30%
			int darkerGreen = (int) (NOTE_COLOR.getGreen() * 0.7); // Reduce green component by 30%
			int darkerBlue = (int) (NOTE_COLOR.getBlue() * 0.7); // Reduce blue component by 30%
			mainColor = new Color(darkerRed, darkerGreen, darkerBlue);
		} else {
			mainColor = NOTE_COLOR; // For white keys, use the original color
		}

// Draw the note rectangle
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(mainColor);
		g2d.fillRoundRect(newX, y, newWidth, h, 10, 10);
	}

	private long timeMsToPixels(long msTime) {
		return Math.round(msTime * getMsHeight());
	}

	private double getMsHeight() {

		return ((double) getHeight()) / (PIANO_ROLL_HEIGHT_IN_SEC * 1000);
	}

	public double getTickY(long noteElapsedTime) {
		return getHeight() + timeMsToPixels(noteElapsedTime);
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

	}

	@Override
	public void onPianoKeyOn(int pitch, int velocity) {
		addNoteWithOpenEnd(pitch, velocity);
	}

	@Override
	public void onPianoKeyOff(int pitch) {
		closeNote(pitch);
	}

	private void closeNote(int pitch) {
		NoteWithTime lastNote = findLastNote(pitch);
		if (lastNote != null) {
			lastNote.setEnd(getElapsedTime());
		}
	}

	private NoteWithTime findLastNote(int pitch) {
		Iterator<NoteWithTime> lit = notes.descendingIterator();
		while (lit.hasNext()) {
			NoteWithTime note = lit.next();
			if (note.getPitch() == pitch) {
				return note;
			}
		}
		return null;
	}

	private void addNoteWithOpenEnd(int pitch, int velocity) {
		notes.add(new NoteWithTime(pitch, velocity, getElapsedTime(), -1, 0));
	}
}