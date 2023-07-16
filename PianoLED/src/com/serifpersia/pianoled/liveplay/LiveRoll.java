package com.serifpersia.pianoled.liveplay;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.learn.PianoMidiConsumer;
import com.serifpersia.pianoled.ui.DrawPiano;

@SuppressWarnings("serial")
public class LiveRoll extends JPanel implements PianoMidiConsumer {

	public static final int PIANO_ROLL_HEIGHT_IN_SEC = 2;
	private static final Color WHITE_NOTE_COLOR = new Color(122, 164, 212);
	private static final Color BLACK_NOTE_COLOR = new Color(145, 225, 66);
	private DrawPiano piano;
	private LivePlayPanel livePanel;

	private LinkedList<NoteWithTime> notes;
	private long startTime;

	public LiveRoll(PianoLED pianoLED, LivePlayPanel livePlayPanel) {
		this.piano = pianoLED.getDrawPiano();
		this.livePanel = livePlayPanel;
		pianoLED.getPianoController().addPianoReceiverConsumer(this);
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

		drawPianoRoll(g, elapsedTime);
		if (notes != null && notes.size() > 0)
		{
			drawNotes(g, notes, elapsedTime);
			if(livePanel.isShowInfoSelected())
			{
				int y = 20;
				int n = 0;
				for (NoteWithTime note : notes) {
					drawText(g, 20, y+n*20, note.getPitch()+ " " + note.getStart()+ " " + note.getEnd()+ " " + note.getVelocity() + " Elapsed: "+getElapsedTime());
					n++;
				}
			}
		}
	}

	private long getElapsedTime() {
		return System.currentTimeMillis() - startTime;
	}

	// Visualisation
	public void drawNotes(Graphics g, LinkedList<NoteWithTime> notes, long elapsedTime) {
		notes.forEach(note -> drawNote(g, note, elapsedTime));
	}

	public void drawNote(Graphics g, NoteWithTime note, long elapsedTime) {
		if( note.getEnd() > 0 && note.getEnd() < elapsedTime - PIANO_ROLL_HEIGHT_IN_SEC * 1000)
		{
			// note not visible
			return;
		}
		long noteElapsedTime = note.getStart() - elapsedTime;

		int x = (int) piano.getKeyXPos(note.getPitch());
		int w = (int) piano.getKeyWidth(note.getPitch());
		int y = (int) getTickY(noteElapsedTime);
		int h = note.getEnd() == -1 ? getHeight()*1000 : (int) timeMsToPixels(note.getEnd() - note.getStart());

		// Draw the note rectangle
		if (piano.isBlackKey(note.getPitch())) {
			g.setColor(WHITE_NOTE_COLOR);
		} else {
			g.setColor(BLACK_NOTE_COLOR);
		}
//		y = 50;
		g.fillRoundRect(x, y, w, h, 5, 5);

		g.setColor(Color.GRAY);
//		 g.drawRoundRect(x, y - h, w, h, 5, 5);

		if (livePanel.isShowInfoSelected()) {
			drawText(g, x, y - 25, "" + note.getPitch());
			drawText(g, x, y - 15, "" + note.getStart() + "(" + noteElapsedTime + ")");
			drawText(g, x, y - 5, "" + note.getEnd());
			drawText(g, x, y + 15, "y: "+y+" H:" + h);
		}
	}

	private long timeMsToPixels(long msTime) {
		return Math.round(msTime * getMsHeight());
	}

	private double getMsHeight() {
		
		return ((double)getHeight())/(PIANO_ROLL_HEIGHT_IN_SEC*1000);
	}

	public double getTickY(long noteElapsedTime) {
		return getHeight() + timeMsToPixels(noteElapsedTime);
	}

	public void drawPianoRoll(Graphics g, long currentTime) {

		// g.setColor(Color.BLACK);
		g.setColor(Color.BLUE); // debug color to differentiate liveplay from learn
		g.fillRect(0, 0, getWidth(), getHeight());
		

		if (livePanel.isShowInfoSelected()) {
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

		}

		if (livePanel.isShowGridSelected()) {
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

}

	private void drawText(Graphics g, int x, int y, String text) {
		g.drawChars(text.toCharArray(), 0, text.length(), x, y);
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