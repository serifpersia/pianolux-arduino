package com.serifpersia.pianoled;

import processing.core.PApplet;
import processing.core.PConstants;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import themidibus.MidiBus;

public class PianoRoll {
	private static final int DELAY_AFTER_NOTE_FINISHED = 1500;
	PianoLED app;
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

	public static int REWIND_FRAGMENT_SEC = 5;

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
	int blackKeyHeight = 45;

	double pianoRollTickHeightMult = 100;
	int tickResolution = 1000;
	int pianoRollTop = 100;
	int pianoRollBottom;
	int pianoRollSide = 80;
	int pianoRollHeight;

	int paddingBottom = 10;
	float blackKeyHeightRatio = 0.7f;

	long currentTick;
	private int midiNumTracks;
	private String midiFileName;
	
	int firstNote = 0;
	private OutMidiReceiver myMidiReceiver;

	public PianoRoll(PianoLED app) {
		this.app = app;
		app.getSurface().setSize(PianoRoll.PIANO_ROLL_HEIGHT, PianoRoll.PIANO_ROLL_WIDTH);
		pianoRollBottom = app.height - paddingBottom - whiteKeyHeight;
		pianoRollHeight = pianoRollBottom - pianoRollTop;
	}

	public void setOutputDevice(MidiDevice midiOutDevice) throws MidiUnavailableException {
		this.midiOutDevice = midiOutDevice;
		myMidiReceiver = new OutMidiReceiver(midiOutDevice);
		if (sequencer != null) {
			sequencer.getTransmitter().setReceiver(myMidiReceiver);
		}
	}
	
	class OutMidiReceiver implements Receiver {
		
		boolean[] disableMidiTrack = new boolean[128];
		private Receiver receiver;


		public OutMidiReceiver(MidiDevice midiDevice) throws MidiUnavailableException
		{
			this.receiver = midiDevice.getReceiver();
		}
		
		public void toggleTrackToPlay(int track, boolean on)
		{
			disableMidiTrack[track] = !on;
		}
		
		boolean isTrackPlaying(int trackNum)
		{
			return !disableMidiTrack[trackNum];
		}
		
	    @Override
	    public void send(MidiMessage message, long timeStamp) {
	    	if( receiver == null)
	    		return;
	    	
	        if (message instanceof ShortMessage) {
	            ShortMessage shortMessage = (ShortMessage) message;
	            int trackNumber = shortMessage.getChannel();
	            if (!disableMidiTrack[trackNumber]) {
	                // Process the message for the desired track
	            	receiver.send(message, timeStamp);
	            }
	        } else if (message instanceof MetaMessage) {
	            // Process meta messages for all tracks
	        	receiver.send(message, timeStamp);
	        }
	    }

	    @Override
	    public void close() {
	        // Close the receiver
	    	receiver.close();
	    }
	};
	
	public void toggleMidiTrack(int trackNum, boolean on)
	{
		myMidiReceiver.toggleTrackToPlay(trackNum, on);
	}

	
	public void loadMidiFile(File midiFile) {
		notes = readMidi(midiFile);
		this.midiFileName = midiFile.getName();
		this.firstNote = 0;
		PApplet.println("Read " + notes.size() + " notes from " + midiFile);
		app.ui.showPianoRollTracks(this.midiNumTracks);
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

				if (myMidiReceiver.isTrackPlaying(note.trackNum)) {

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
				app.text("C", posX + whiteKeyWidth / 2 - 2, pianoRollBottom + whiteKeyHeight - 6);
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

		int textBlockX = UI.EFFECT_CONTROLS_X;

		if (this.midiFileName != null) {
			app.text(this.midiFileName, textBlockX, 300);
			app.text("Tracks:", textBlockX, 320);
		}

		if (debug) {

			long ticks = sequence == null ? 0 : sequence.getTickLength();
			app.line(pianoRollSide, pianoRollBottom, app.width - pianoRollSide, pianoRollBottom);
			app.text(currentTick + " / " + ticks, textBlockX, pianoRollBottom);

			int textBlockY = pianoRollBottom - 50;
			app.text("midiNumTracks: " + midiNumTracks, textBlockX, textBlockY);
			textBlockY += 15;
			app.text("numNotes: " + (notes == null ? 0 : notes.size()), textBlockX, textBlockY);
			textBlockY += 15;
			app.text("firstNote: " + firstNote, textBlockX, textBlockY);

		}

		app.stroke(0);
	}

	public boolean isPaused() {
		return sequencer == null || !sequencer.isRunning();
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
			boolean connected = true;
			if (midiOutDevice != null) {
				openMidiDevice(midiOutDevice);
				if (midiOutDevice.isOpen()) {
					connected = false;
				}
			}

			sequencer = MidiSystem.getSequencer(connected);
			sequencer.open();
			sequence = MidiSystem.getSequence(midiFile);
			sequencer.setSequence(sequence);

			sequencer.getTransmitter().setReceiver(new MidiLEDReceiver());
			setOutputDevice(midiOutDevice);
			
			int ticksPerBeat = sequence.getResolution();
			double bpm = sequencer.getTempoInBPM();
			ticksPerSec = ticksPerBeat * bpm / 60;
			pianoRollTickHeight = (60.0f / ticksPerBeat) / bpm * pianoRollTickHeightMult;

			sequencer.addMetaEventListener(new MetaEventListener() {
				@Override
				public void meta(MetaMessage meta) {
					if (meta.getType() == 0x2F) {
						app.togglePianoRollButton();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openMidiDevice(MidiDevice outDevice) {
		if (outDevice == null)
			return;
		if (!outDevice.isOpen()) {
			try {
				outDevice.open();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}
		}
	}

	private void closeMidiDevice(MidiDevice outDevice) {
		if (outDevice == null)
			return;
		if (outDevice.isOpen()) {
			outDevice.close();
		}
	}

	class MidiLEDReceiver implements Receiver {
		@Override
		public void send(MidiMessage midiMessage, long timeStamp) {
			if (midiMessage instanceof ShortMessage message) {
				int pitch = message.getData1();
				int velocity = message.getData2();
				if (message.getCommand() == ShortMessage.NOTE_ON && message.getData2() != 0) {
					app.noteOn(0, pitch, velocity);
				} else if (message.getCommand() == ShortMessage.NOTE_OFF
						|| message.getCommand() == ShortMessage.NOTE_ON && message.getData2() == 0) {
					if (sequencer.isRunning())
						app.noteOff(0, pitch, velocity);
				} else {
					PApplet.println("Command " + message.getCommand() + ": " + pitch + " " + velocity);
				}
			}
		}

		@Override
		public void close() {
			stop();
		}
	}

	public void rewind() {
		if (sequencer != null) {
			sequencer.setTickPosition(0);
		}
		firstNote = 0;
	}

	public void rewind(int sec) {
		if (sequencer != null) {
			long currentTick = sequencer.getTickPosition();
			long rewindTick = (long) (currentTick + ticksPerSec * sec);

			// Make sure the rewindTick is not negative
			if (rewindTick < 0) {
				rewindTick = 0;
			}

			firstNote = 0;
			sequencer.setTickPosition(rewindTick);
		}
	}

	public void close() {
		if (sequencer != null && sequencer.isRunning())
			sequencer.close();
		if (myBus != null)
			myBus.close();
		if (midiOutDevice != null)
			midiOutDevice.close();
		firstNote = 0;
	}

	public void startStop() {
		if (sequencer != null) {
			if (sequencer.isRunning()) {
				sequencer.stop();
			} else {
				sequencer.start();
			}
		}
	}

	public void stop() {
		if (sequencer != null) {
			if (sequencer.isRunning()) {
				sequencer.stop();
			}
		}
	}

	class Note {
		int pitch;
		int velocity;
		int start;
		int end;
		int trackNum;

		Note(int pitch, int velocity, int start, int end, int trackNum) {
			this.pitch = pitch;
			this.velocity = velocity;
			this.start = start;
			this.end = end;
			this.trackNum = trackNum;
		}

		public int getTrack() {
			return trackNum;
		}
	}

	private LinkedList<Note> readMidi(File midiFile) {
		LinkedList<Note> fileNotes = new LinkedList<Note>();
		try {
			// Load the MIDI file
			Sequence sequence = MidiSystem.getSequence(midiFile);
			// Iterate over each track in the sequence
			int trackNum = 0;
			for (Track track : sequence.getTracks()) {
				int notesInTrack = 0;
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
							fileNotes.add(new Note(noteValue, noteVelocity, noteStart, noteEnd, trackNum));
							notesInTrack++;
						}
					}
				}
				if( notesInTrack > 0 )
					trackNum++;
			}
			this.midiNumTracks = trackNum;
			
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
