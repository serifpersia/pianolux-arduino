package com.serifpersia.pianoled.learn;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
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
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import themidibus.MidiBus;

public class MidiPlayer {

	private static final int DEFAULT_BEATS_PER_MEASURE = 4;

	private List<MidiPlayerConsumer> consumers = new ArrayList<>();

	private LinkedList<Note> notes;

	MidiDevice midiOutDevice;
	MidiBus myBus;
	Sequencer sequencer;
	Sequence sequence;
	File midiFile;
	private int midiNumTracks;

	double ticksPerSec;

	private OutMidiReceiver myMidiReceiver;

	private int firstPlayingNote;

	private ArrayList<Integer> bars;

	private byte beatsPerMeasure = DEFAULT_BEATS_PER_MEASURE;

	public MidiPlayer(File _midiFile, MidiDevice _midiOutDevice) {
		midiFile = _midiFile;
		midiOutDevice = _midiOutDevice;
		notes = readMidi(midiFile);
		setupSequencer(midiFile);
		bars = calcBars();
	}

	private ArrayList<Integer> calcBars() {
		int ticksPerQuarterNote = sequence.getResolution();

		// default measure - 4/4
		int ticksPerBar = ticksPerQuarterNote * beatsPerMeasure;

		long ticks = sequence.getTickLength();

		ArrayList<Integer> bars = new ArrayList<>();
		for (int tick = ticksPerBar; tick <= ticks; tick += ticksPerBar) {
			bars.add(tick);
		}

		return bars;
	}

	public String getFileName() {
		return this.midiFile.getName();
	}

	public LinkedList<Note> getNotes() {
		return notes;
	}

	public void addConsumer(MidiPlayerConsumer consumer) {
		this.consumers.add(consumer);
	}

	// MIDI handling
	private void setupSequencer(File midiFile) {
		try {

			sequencer = MidiSystem.getSequencer(false);
			sequencer.open();
			sequence = MidiSystem.getSequence(midiFile);
			sequencer.setSequence(sequence);

			// clear all existing transmitters
			sequencer.getTransmitters().forEach(Transmitter::close);

			sequencer.getTransmitter().setReceiver(new MidiEventsProcessor());
			if (midiOutDevice != null) {
				openMidiDevice(midiOutDevice);
				setOutputDevice(midiOutDevice);
			}

			int ticksPerBeat = sequence.getResolution();
			double bpm = sequencer.getTempoInBPM();
			ticksPerSec = ticksPerBeat * bpm / 60;

			sequencer.addMetaEventListener(new MetaEventListener() {
				@Override
				public void meta(MetaMessage meta) {
					if (meta.getType() == 0x2F) {
						consumers.forEach(MidiPlayerConsumer::onPlaybackFinished);
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
					else if (event.getMessage() instanceof MetaMessage) {
						MetaMessage metaMessage = (MetaMessage) event.getMessage();
						if (metaMessage.getType() == 0x58) {
							byte[] data = metaMessage.getData();
							beatsPerMeasure = data[0];
						}
					}

				}
				if (notesInTrack > 0)
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

	public void setOutputDevice(MidiDevice midiOutDevice) throws MidiUnavailableException {
		this.midiOutDevice = midiOutDevice;
		if (!midiOutDevice.isOpen())
			midiOutDevice.open();
		myMidiReceiver = new OutMidiReceiver(midiOutDevice);
		if (sequencer != null) {
			sequencer.getTransmitter().setReceiver(myMidiReceiver);
		}
	}

	public void rewind() {
		sequencer.setTickPosition(0);
	}

	public void rewind(int sec) {
		long currentTick = sequencer.getTickPosition();
		long rewindTick = (long) (currentTick + ticksPerSec * sec);

		// Make sure the rewindTick is not negative
		if (rewindTick < 0) {
			rewindTick = 0;
		}

		if (sec < 0)
			firstPlayingNote = 0;

		sequencer.setTickPosition(rewindTick);
	}

	public void playPause() {
		if (sequencer.isRunning()) {
			sequencer.stop();
		} else {
			sequencer.start();
		}
	}

	public void stop() {
		if (sequencer != null) {
			if (sequencer.isRunning()) {
				sequencer.stop();
			}
		}
	}

	public boolean isPaused() {
		return !sequencer.isRunning();
	}

	class MidiEventsProcessor implements Receiver {
		@Override
		public void send(MidiMessage midiMessage, long timeStamp) {
			if (midiMessage instanceof ShortMessage message) {
				int pitch = message.getData1();
				int velocity = message.getData2();
				if (message.getCommand() == ShortMessage.NOTE_ON && message.getData2() != 0) {
					for (MidiPlayerConsumer consumer : consumers) {
						consumer.onNoteOn(0, pitch, velocity);
					}

				} else if (message.getCommand() == ShortMessage.NOTE_OFF
						|| message.getCommand() == ShortMessage.NOTE_ON && message.getData2() == 0) {
					for (MidiPlayerConsumer consumer : consumers) {
						consumer.onNoteOff(pitch);
					}
				} else {
					System.out.println("Command " + message.getCommand() + ": " + pitch + " " + velocity);
				}
			}
			else if (midiMessage instanceof MetaMessage) {
				MetaMessage metaMessage = (MetaMessage) midiMessage;
				byte[] data = metaMessage.getData();
				System.out.println("Meta Command " + metaMessage.getType() + ": " + data[0] + " " + data[1]);
				if (metaMessage.getType() == 0x58) {
					beatsPerMeasure = data[0];
//					int denominator = (int) Math.pow(2, data[1]);
				}
			}
			else if (midiMessage instanceof SysexMessage) {
				SysexMessage sysexMessage = (SysexMessage) midiMessage;
				byte[] data = sysexMessage.getData();
				System.out.println("Sysex Command " + (data[0] & 0xFF) + " " + (data[1] & 0xFF));
			}
			else
			{
				byte[] msg = midiMessage.getMessage();
				System.out.println("Unknown Command " +(msg[0] & 0xFF)+" : "+(msg[1] & 0xFF)+" "+(msg[2] & 0xFF));
			}
		}

		@Override
		public void close() {
			stop();
		}
	}

	public long getCurrentTick() {
		return sequencer.getTickPosition();
	}

	public double getTicksPerSecond() {
		int ticksPerQuarterNote = sequence.getResolution();
		double quarterNoteLengthInSec = sequencer.getTempoInMPQ() / 100000;
		return ticksPerQuarterNote * quarterNoteLengthInSec;
	}

	public LinkedList<Note> getNotesInInterval(long startTick, long endTick) {
		LinkedList<Note> currentNotes = new LinkedList<>();

		boolean firstPlayingNoteFound = false;
		int n = notes.size();
		for (int noteNum = firstPlayingNote; noteNum < n; noteNum++) {
			Note note = notes.get(noteNum);

			if (myMidiReceiver.isTrackPlaying(note.trackNum)) {

				// rest of notes are too early to display
				if (note.isBefore(endTick))
					break;

				// note finished
				if (note.isAfter(startTick)) {
					continue;
				}

				if (note.isPlayingAt(startTick)) {
					if (!firstPlayingNoteFound) {
						firstPlayingNote = noteNum;
						firstPlayingNoteFound = true;
					}
				}
				currentNotes.add(note);
			}
		}
		return currentNotes;
	}

	public int getNumTracks() {
		return midiNumTracks;
	}

	public ArrayList<Integer> getBars() {
		return bars;
	}

	public byte getBeatsPerMeasure() {
		return beatsPerMeasure;
	}

	public void muteKey(int pitch) {
		try {
			ShortMessage msg = new ShortMessage(ShortMessage.NOTE_OFF, pitch, 0);
			myMidiReceiver.send(msg, pitch);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
}
