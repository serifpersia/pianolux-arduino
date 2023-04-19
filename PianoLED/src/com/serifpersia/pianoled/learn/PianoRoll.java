package com.serifpersia.pianoled.learn;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import com.serifpersia.pianoled.ui.DrawPiano;

@SuppressWarnings("serial")
public class PianoRoll extends JPanel {

	private MidiPlayer player;
	private long currentTick;
	private DrawPiano piano;

	public PianoRoll(MidiPlayer player, DrawPiano piano) {
		this.player = player;
		this.piano = piano;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.currentTick = player.getCurrentTick();
		drawPianoRoll(g);
//		g.setFont(new Font("Arial", Font.PLAIN, 10));
	}

	// Visualisation
	public void drawNotes(Graphics g, LinkedList<Note> notes) {
		notes.forEach(this::drawNote);
	}

	public void drawNote(Note note) {
		long timeElapsedTicks = note.start - currentTick;

		// Calculate the position and dimensions of the note rectangle
//		float xOffset = isBlack(note.pitch) ? 1 : 2;

//		float x = piano.getKeyX(note.getPitch());
//		float w = isBlack(note.pitch) ? blackKeyWidth - 2 : whiteKeyWidth - 4;
//
//		float y = getTickY(timeElapsedTicks);
//		float h = (float) ((note.end - note.start) * pianoRollTickHeight);
//
//		// Draw the note rectangle
//		if (isBlack(note.pitch)) {
//			// removed transparency
//			app.stroke(122, 164, 212);
//			app.fill(122, 164, 212);
//		} else {
//			// removed transparency
//			app.stroke(145, 225, 66);
//			app.fill(145, 225, 66);
//		}
//		// noStroke();
//		float cornerRadius = 30;
//		app.rectMode(PConstants.CORNERS);
//		app.rect(x, y, x + w, y - h, cornerRadius);
//		if (debug)
//			app.text(note.pitch, x, y - 25);
//		if (debug)
//			app.text(note.start, x, y - 15);
//		if (debug)
//			app.text(note.end, x, y - 5);
//		app.rectMode(PConstants.CORNER);
//
//		app.fill(0);
	}

	public void drawPianoRoll(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());

//		int textBlockX = getWidth()-100;
//		boolean debug = true;
//		if (debug) {

//			long ticks = sequence == null ? 0 : sequence.getTickLength();
//			app.line(pianoRollSide, pianoRollBottom, app.width - pianoRollSide, pianoRollBottom);
//			app.text(currentTick + " / " + ticks, textBlockX, pianoRollBottom);
//
//			int textBlockY = pianoRollBottom - 50;
//			app.text("midiNumTracks: " + midiNumTracks, textBlockX, textBlockY);
//			textBlockY += 15;
//			app.text("numNotes: " + (notes == null ? 0 : notes.size()), textBlockX, textBlockY);
//			textBlockY += 15;
//			app.text("firstNote: " + firstNote, textBlockX, textBlockY);

//		}

//		app.stroke(0);
	}
//
//	public void drawBorders() {
//		app.stroke(0);
//		app.rect(0, app.height - paddingBottom, app.width, app.height);
//	}

}