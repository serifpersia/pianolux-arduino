package com.serifpersia.pianoled.liveplay;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import com.serifpersia.pianoled.learn.MidiPlayerConsumer;

public class MidiRecorder {

	private List<MidiPlayerConsumer> consumers = new ArrayList<>();

	Sequencer sequencer;
	private int midiNumTracks;

	public MidiRecorder(MidiDevice midiInDevice) {
		setupSequencer(midiInDevice, 24);
	}

	public long getTickLength() {
		return sequencer.getSequence().getTickLength();
	}

	public void addConsumer(MidiPlayerConsumer consumer) {
		this.consumers.add(consumer);
	}

	// MIDI handling
	private void setupSequencer(MidiDevice midiInDevice, Integer ppq) {
		try {
			if (!midiInDevice.isOpen()) {
				midiInDevice.open();
			}

			sequencer = MidiSystem.getSequencer(false);
			sequencer.open();
			// clear all existing transmitters
			sequencer.getTransmitters().forEach(Transmitter::close);
			midiInDevice.getTransmitter().setReceiver(sequencer.getReceiver());

			Sequence sequence = new Sequence(Sequence.PPQ, ppq);
			Track currentTrack = sequence.createTrack();
			sequencer.setSequence(sequence);
			sequencer.setTickPosition(0);
			sequencer.recordEnable(currentTrack, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void startStopRecording() {
		if (sequencer.isRecording()) {
			sequencer.stopRecording();
		} else {
			sequencer.startRecording();
		}
	}

	public void stopRecording() {
		if (sequencer != null) {
			if (sequencer.isRecording()) {
				sequencer.isRecording();
			}
		}
	}

	public boolean isRecording() {
		return sequencer.isRecording();
	}

	public long getCurrentTick() {
		return sequencer.getTickPosition();
	}

	public double getTicksPerSecond() {
		int ticksPerQuarterNote = sequencer.getSequence().getResolution();
		double quarterNoteLengthInSec = sequencer.getTempoInMPQ() / 100000;
		return ticksPerQuarterNote * quarterNoteLengthInSec;
	}

	public int getNumTracks() {
		return midiNumTracks;
	}
}
