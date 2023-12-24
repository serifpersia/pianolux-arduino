package com.serifpersia.pianolux.liveplay;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.serifpersia.pianolux.PianoLux;
import com.serifpersia.pianolux.learn.PianoMidiConsumer;
import com.serifpersia.pianolux.ui.DrawPiano;
import com.serifpersia.pianolux.ui.GetUI;
import com.serifpersia.pianolux.ui.TopPanel;

@SuppressWarnings("serial")
public class LiveRoll extends JPanel implements PianoMidiConsumer {

	public static int PIANO_ROLL_HEIGHT_IN_SEC = 2;
	private Color NOTE_COLOR = new Color(145, 225, 66);
	private DrawPiano piano;
	private LivePlayPanel livePanel;

	private LinkedList<NoteWithTime> notes;
	private long startTime;

	private boolean showMessage = true;
	private long lastActivityTime;
	private Timer inactivityTimer;

	private HashSet<Integer> activeNotes;

	public static ImageIcon testIcon;

	public static float bgImage_Opacity = 1;

	public LiveRoll(PianoLux pianoLux, LivePlayPanel livePlayPanel) {
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);

		this.piano = pianoLux.getDrawPiano();
		this.livePanel = livePlayPanel;
		pianoLux.getPianoController().addPianoReceiverConsumer(this);
		setDoubleBuffered(true); // Enable double buffering

		start();

		activeNotes = new HashSet<>();

		inactivityTimer = new Timer(3000, e -> checkInactivity());
		inactivityTimer.start();
	}

	private void checkInactivity() {
		if (activeNotes.isEmpty() && hasInactivityPassed(3000)) {
			showMessage = true;
			repaint();
		}
	}

	public LinkedList<NoteWithTime> getNotes() {
		return notes;
	}

	public void start() {
		startTime = System.currentTimeMillis();
		lastActivityTime = startTime;
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

		if (livePanel.isBGSelected()) {
			drawImage(g);
		}
		if (notes != null && notes.size() > 0) {
			drawNotes(g, elapsedTime);
		}
	}

	private long getElapsedTime() {
		return System.currentTimeMillis() - startTime;
	}

	// Visualisation
	public void drawNotes(Graphics g, long elapsedTime) {
		for (NoteWithTime note : notes) {
			drawNote(g, note, elapsedTime, 0.9, 1.5);
		}
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

		if (piano.isBlackKey(note.getPitch())) {
			// Use the custom color with darkened version for black keys
			int darkerRed = (int) (GetUI.selectedColor.getRed() * 0.7); // Reduce red component by 30%
			int darkerGreen = (int) (GetUI.selectedColor.getGreen() * 0.7); // Reduce green component by 30%
			int darkerBlue = (int) (GetUI.selectedColor.getBlue() * 0.7); // Reduce blue component by 30%
			NOTE_COLOR = new Color(darkerRed, darkerGreen, darkerBlue);
		} else {
			// Use the custom color for white keys
			NOTE_COLOR = GetUI.selectedColor;
		}
		double scaleFactor = piano.isBlackKey(note.getPitch()) ? blackKeyScaleFactor : whiteKeyScaleFactor;

		// Calculate the new width with the appropriate scaleFactor
		int newWidth = (int) (w * scaleFactor);

		// Calculate the new x coordinate to keep the same center position
		int newX = x + (w - newWidth) / 2;

		// Draw the note rectangle
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(NOTE_COLOR);
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

	private void drawImage(Graphics g) {
		Image iconImage = testIcon.getImage();

		// Cast Graphics object to Graphics2D to access advanced rendering options
		Graphics2D g2d = (Graphics2D) g;

		// Create an AlphaComposite instance with the desired opacity
		AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, bgImage_Opacity);

		// Save the current composite and set the new composite with the desired opacity
		AlphaComposite oldComposite = (AlphaComposite) g2d.getComposite();
		g2d.setComposite(alphaComposite);

		// Draw the icon image with the adjusted opacity
		g2d.drawImage(iconImage, 0, 0, getWidth(), getHeight(), null);

		// Restore the original composite
		g2d.setComposite(oldComposite);
	}

	@Override
	public void onPianoKeyOn(int pitch, int velocity) {
		lastActivityTime = System.currentTimeMillis();
		showMessage = false;
		addNoteWithOpenEnd(pitch, velocity);
		activeNotes.add(pitch);
		inactivityTimer.restart();
	}

	@Override
	public void onPianoKeyOff(int pitch) {
		lastActivityTime = System.currentTimeMillis();
		closeNote(pitch);
		activeNotes.remove(pitch);
		inactivityTimer.restart();
	}

	private boolean hasInactivityPassed(long inactivityTime) {
		return (System.currentTimeMillis() - lastActivityTime) > inactivityTime;
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

	@Override
	public void paint(Graphics g) {
		String message1 = "Awaiting Midi Input...";
		String message2 = "Press F key to exit/enter fullscreen mode!";
		String message3 = "Move mouse cursor to right edge to view parameters!";

		super.paint(g);
		if (TopPanel.isLivePlay && showMessage) {
			// Draw the message in the center of the frame.
			Font font = new Font("Poppins", Font.PLAIN, 24);
			g.setColor(Color.WHITE);
			g.setFont(font);

			int x1 = (getWidth() - g.getFontMetrics().stringWidth(message1)) / 2;
			int x2 = (getWidth() - g.getFontMetrics().stringWidth(message2)) / 2;
			int x3 = (getWidth() - g.getFontMetrics().stringWidth(message3)) / 2;
			int y = getHeight() / 2;

			g.drawString(message1, x1, y);
			g.drawString(message2, x2, y + 45);
			g.drawString(message3, x3, y + 90);
		}
	}

}
