package com.serifpersia.pianolux.learn;

public interface PianoMidiConsumer {
	void onPianoKeyOn(int pitch, int velocity);
	void onPianoKeyOff(int pitch);
}
