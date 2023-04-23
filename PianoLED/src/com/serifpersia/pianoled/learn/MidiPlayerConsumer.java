package com.serifpersia.pianoled.learn;

public interface MidiPlayerConsumer {
	void onPlaybackFinished();
	void onNoteOn(int channel, int pitch, int velocity);
	void onNoteOff(int pitch);
}
