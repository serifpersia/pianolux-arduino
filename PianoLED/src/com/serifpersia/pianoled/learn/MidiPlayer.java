package com.serifpersia.pianoled.learn;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

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
import javax.sound.midi.Transmitter;

import themidibus.MidiBus;

public class MidiPlayer {

	private MidiPlayerConsumer consumer;

	private LinkedList<Note> notes;

	MidiDevice midiOutDevice;
	MidiBus myBus;
	Sequencer sequencer;
	Sequence sequence;
	File midiFile;

	double ticksPerSec;

	private OutMidiReceiver myMidiReceiver;

	public MidiPlayer(File _midiFile, MidiDevice _midiOutDevice) {
		midiFile = _midiFile;
		midiOutDevice = _midiOutDevice;
		notes = readMidi(midiFile);
		setupSequencer(midiFile);
	}

	public String getFileName() {
		return this.midiFile.getName();
	}

	public LinkedList<Note> getNotes() {
		return notes;
	}

	public void setConsumer(MidiPlayerConsumer consumer) {
		this.consumer = consumer;
	}

	// MIDI handling
	private void setupSequencer(File midiFile) {
		try {

			sequencer = MidiSystem.getSequencer(false);
			sequencer.open();
			sequence = MidiSystem.getSequence(midiFile);
			sequencer.setSequence(sequence);

			//clear all existing transmitters
			sequencer.getTransmitters().forEach(Transmitter::close);

			sequencer.getTransmitter().setReceiver(new MidiEventsProcessor());
			if (midiOutDevice != null) {
				openMidiDevice(midiOutDevice);
				setOutputDevice(midiOutDevice);
			}

			int ticksPerBeat = sequence.getResolution();
			double bpm = sequencer.getTempoInBPM();
			ticksPerSec = ticksPerBeat * bpm / 60;
//			pianoRollTickHeight = (60.0f / ticksPerBeat) / bpm * pianoRollTickHeightMult;

			sequencer.addMetaEventListener(new MetaEventListener() {
				@Override
				public void meta(MetaMessage meta) {
					if (meta.getType() == 0x2F) {
						if (consumer != null)
							consumer.onPlaybackFinished();
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

		System.out.println("Read " + fileNotes.size() + " notes.");

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
					consumer.onNoteOn(0, pitch, velocity);
				} else if (message.getCommand() == ShortMessage.NOTE_OFF
						|| message.getCommand() == ShortMessage.NOTE_ON && message.getData2() == 0) {
					consumer.onNoteOff(pitch);
				} else {
					System.out.println("Command " + message.getCommand() + ": " + pitch + " " + velocity);
				}
			}
		}

		@Override
		public void close() {
			stop();
		}
	}

}
