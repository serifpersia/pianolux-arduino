package com.serifpersia.pianoled;

import processing.core.PApplet;
import processing.core.PConstants;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import themidibus.MidiBus;

public class PianoRoll {
	private static final int DELAY_AFTER_NOTE_FINISHED = 1500;
	PApplet app;
	MidiDevice midiOutDevice;
	MidiBus myBus;
	Sequencer sequencer;
	Sequence sequence;

	public static final int PIANO_ROLL_HEIGHT = 960;
	public static final int PIANO_ROLL_WIDTH = 600;

	boolean debug = false;

	//////////////
	LinkedList<Note> notes;
	Note currentNote;

	public static int REWIND_FRAGMENT_SEC = 3;

	// List<Note> notesDisplayed = new boolean[512];
	boolean[] keysOn = new boolean[128];
	double pianoRollTickHeight;
	double ticksPerSec;
	int delayOffsetp;
	///////////

	int pianoRollPaddingLeft = 15;
	// 88 keys
	int numKeys = 88;
	int numWhiteKeys = 52;
	int firstPianoKeyPitch = 22; // A0 at 88keys

	boolean pause = false;

	int whiteKeyWidth = 15;
	int whiteKeyHeight = 70;
	int blackKeyWidth = 8;
	int blackKeyHeight = 40;

	double pianoRollTickHeightMult = 100;
	int tickResolution = 1000;
	int pianoRollTop = 100;
	int pianoRollBottom;
	int pianoRollSide = 80;
	int pianoRollHeight;

	int paddingBottom = 10;
	float blackKeyHeightRatio = 0.7f;

	long currentTick;

	public PianoRoll(PApplet app) {
		this.app = app;
		app.getSurface().setSize(PianoRoll.PIANO_ROLL_HEIGHT, PianoRoll.PIANO_ROLL_WIDTH);
		pianoRollBottom = app.height - paddingBottom - whiteKeyHeight;
		pianoRollHeight = pianoRollBottom - pianoRollTop;
	}

	public void setOutputDevice(MidiDevice midiOutDevice) {
		this.midiOutDevice = midiOutDevice;
	}

	public void loadMidiFile(File midiFile) {
		notes = readMidi(midiFile);
		PApplet.println("Read "+notes.size()+" notes from "+midiFile);
		setupSequencer(midiFile);
	}

	public void setFollowKey(boolean on) {
		// TBD
	}

	public void draw() {
		app.background(0);
		app.frameRate(120);
		drawVisualization();
	}

	int firstNote = 0;

	// Visualisation
	public void drawVisualization() {
		// Draw piano roll
		drawPianoRoll();

		int delayBeforeNotePLayed = (int) (pianoRollHeight / pianoRollTickHeight);

		for (int i = 0; i < keysOn.length; i++) {
			keysOn[i] = false;
		}

		if (sequencer != null) {
			currentTick = sequencer.getTickPosition();
			int n = notes.size();
			for (int noteNum = firstNote; noteNum < n; noteNum++) {
				Note note = notes.get(noteNum);

				if (note.start < currentTick && note.end > currentTick) {
					if (debug)
						PApplet.println("Key On: " + note.pitch + " " + note.start + " " + note.end);
					keysOn[note.pitch - firstPianoKeyPitch + 1] = true;
				}

				// note is too far yet to display
				if (note.start > currentTick + delayBeforeNotePLayed)
					break;

				// note finished
				if (note.end < currentTick - DELAY_AFTER_NOTE_FINISHED) {
					firstNote = noteNum;
					continue;
				}

				drawNote(note);
			}
		}

		drawPianoKeys();
		drawBorders();
	}

	public void drawBorders() {
		app.stroke(0);
		app.rect(0, app.height - paddingBottom, app.width, app.height);
	}

	public void drawPianoKeys() {
		int numWhiteKeys = 52; // There are 52 white keys on an 88-key piano

		// Draw white keys
		app.fill(255);
		for (int i = 0; i < numWhiteKeys; i++) {
			int posX = pianoRollPaddingLeft + i * whiteKeyWidth;
			int pitch = whiteKeyToPitch(i);
			if (keysOn[pitch])
				app.fill(145, 225, 66);
			else
				app.fill(255);

			app.stroke(127, 127, 127);
			app.rect(posX, pianoRollBottom, whiteKeyWidth, whiteKeyHeight);
			app.fill(255);
			if (i == 23) // C4 in 88 key
			{
				app.fill(128);
				app.text("C", posX + whiteKeyWidth / 2 - 5, pianoRollBottom + whiteKeyHeight - 10);
				app.fill(255);
			}
			if ((i - 2) % 7 == 0) {
				app.stroke(100, 100, 100);
				app.line(posX, pianoRollTop, posX, pianoRollBottom);
			}
		}

		// Draw black keys
		int[] blackKeyIndices = { 0, 2, 3, 5, 6 };
		int numBlackKeys = 0;
		for (int i = 0; i < numWhiteKeys; i++) {
			if (Arrays.binarySearch(blackKeyIndices, i % 7) >= 0) {
				int pitch = whiteKeyToPitch(i) + 1;
				if (keysOn[pitch])
					app.fill(122, 164, 212);
				else
					app.fill(0);

				int whiteKeyPosX = pianoRollPaddingLeft + i * whiteKeyWidth;
				int blackKeyPosX = whiteKeyPosX + whiteKeyWidth * 3 / 4;
				app.rect(blackKeyPosX, pianoRollBottom, blackKeyWidth, blackKeyHeight);
				numBlackKeys++;
			}
			if (numBlackKeys == 36) { // We have drawn all the black keys
				break;
			}
		}
	}

	public int whiteKeyToPitch(int whiteKeyNum) {
		int[] whiteKeysIndex = { 0, 2, 3, 5, 7, 8, 10 };
		int octave = whiteKeyNum / 7;
		int numInOctave = whiteKeyNum % 7;
		return octave * 12 + whiteKeysIndex[numInOctave];
	}

	public int midiToPianoPitch(int pitch) {
		// if(numKeys = 88)
		return pitch - firstPianoKeyPitch + 1;
	}

	public void drawNote(Note note) {
		long timeElapsedTicks = note.start - currentTick;

		// Calculate the position and dimensions of the note rectangle
		float xOffset = isBlack(note.pitch) ? 1 : 2;

		float x = midiPitchToXPosition(note.pitch) + xOffset;
		float w = isBlack(note.pitch) ? blackKeyWidth - 2 : whiteKeyWidth - 4;

		float y = getTickY(timeElapsedTicks);
		float h = (float) ((note.end - note.start) * pianoRollTickHeight);

		// Draw the note rectangle
		if (isBlack(note.pitch)) {
			// removed transparency
			app.stroke(122, 164, 212);
			app.fill(122, 164, 212);
		} else {
			// removed transparency
			app.stroke(145, 225, 66);
			app.fill(145, 225, 66);
		}
		// noStroke();
		float cornerRadius = 30;
		app.rectMode(PConstants.CORNERS);
		app.rect(x, y, x + w, y - h, cornerRadius);
		if (debug)
			app.text(note.pitch, x, y - 25);
		if (debug)
			app.text(note.start, x, y - 15);
		if (debug)
			app.text(note.end, x, y - 5);
		app.rectMode(PConstants.CORNER);

		app.fill(0);
	}

	public float getTickY(long tick) {
		return (float) (pianoRollBottom - tick * pianoRollTickHeight);
	}

	public void drawPianoRoll() {
		app.stroke(100, 100, 100);
		app.fill(255);

		if (debug) {
			app.line(pianoRollSide, pianoRollBottom, app.width - pianoRollSide, pianoRollBottom);
			app.text(currentTick, app.width - 150, pianoRollBottom);
		}

		app.stroke(0);
	}

	public boolean isPaused() {
		return !sequencer.isRunning();
	}

	public boolean isBlack(int midiPitch) {
		List<Integer> midiBlackKeys = Arrays.asList(1, 3, 6, 8, 10); // C
		return midiBlackKeys.contains(midiPitch % 12);
	}

	public float midiPitchToXPosition(int midiPitch) {

		int pianoPitch = midiPitch - firstPianoKeyPitch + 1;

		int[] whiteKeyIndices = { 0, 0, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6 }; // A0
		int octave = pianoPitch / 12;
		int whiteKeyId = octave * 7 + whiteKeyIndices[pianoPitch % 12];

		int offsetX = 0;
		if (isBlack(midiPitch)) {
			offsetX += whiteKeyWidth * 3 / 4;
		}

		return pianoRollPaddingLeft + whiteKeyId * whiteKeyWidth + offsetX;
	}

	// MIDI handling
	private void setupSequencer(File midiFile) {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = MidiSystem.getSequence(midiFile);
			sequencer.setSequence(sequence);

			int ticksPerBeat = sequence.getResolution();
			double bpm = sequencer.getTempoInBPM();
			ticksPerSec = ticksPerBeat * bpm / 60;

			pianoRollTickHeight = (60.0f / ticksPerBeat) / bpm * pianoRollTickHeightMult;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rewind() {
		sequencer.setTickPosition(0);
		firstNote = 0;
	}

	public void rewind(int sec) {
		long currentTick = sequencer.getTickPosition();
		long rewindTick = (long) (currentTick + ticksPerSec * sec);

		// Make sure the rewindTick is not negative
		if (rewindTick < 0) {
			rewindTick = 0;
		}

		firstNote = 0;
		sequencer.setTickPosition(rewindTick);
	}

	public void pause() {
		// pause = !pause;
		if (sequencer.isRunning()) {
			sequencer.stop();
		} else {
			sequencer.start();
		}
	}

	class Note {
		int pitch;
		int velocity;
		int start;
		int end;

		Note(int pitch, int velocity, int start, int end) {
			this.pitch = pitch;
			this.velocity = velocity;
			this.start = start;
			this.end = end;
		}
	}

	private LinkedList<Note> readMidi(File midiFile) {
		LinkedList<Note> fileNotes = new LinkedList<Note>();
		try {
			// Load the MIDI file
			Sequence sequence = MidiSystem.getSequence(midiFile);

			// Iterate over each track in the sequence
			for (Track track : sequence.getTracks()) {
				// Iterate over each event in the track
				for (int i = 0; i < track.size(); i++) {
					MidiEvent event = track.get(i);
					if (event.getMessage() instanceof ShortMessage) {
						ShortMessage message = (ShortMessage) event.getMessage();
						if (message.getCommand() == ShortMessage.NOTE_ON && message.getData2() != 0) {
							int noteValue = message.getData1();
							int noteVelocity = message.getData2();
							int noteStart = (int) event.getTick();
							int noteEnd = 0;
							for (int j = i + 1; j < track.size(); j++) {
								MidiEvent endEvent = track.get(j);
								if (endEvent.getMessage() instanceof ShortMessage) {
									ShortMessage endMessage = (ShortMessage) endEvent.getMessage();
									if (endMessage.getData1() == noteValue
											&& (endMessage.getCommand() == ShortMessage.NOTE_OFF
													|| endMessage.getData2() == 0)) {
										noteEnd = (int) endEvent.getTick();
										break;
									}
								}
							}
							if (noteEnd == 0) {
								// Note off event not found, set end time to the end of the track
								noteEnd = (int) sequence.getTickLength();
							}
							// Create a new Note object and add it to the list
							Note note = new Note(noteValue, noteVelocity, noteStart, noteEnd);
							fileNotes.add(note);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Collections.sort(fileNotes, new Comparator<Note>() {
			@Override
			public int compare(Note n1, Note n2) {
				return Integer.compare(n1.start, n2.start);
			}
		});

		return fileNotes;
	}
}
