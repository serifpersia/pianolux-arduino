package com.serifpersia.pianoled.learn;

public class Note {
	int pitch;
	int velocity;
	int start;
	int end;

	public int getPitch() {
		return pitch;
	}

	public int getVelocity() {
		return velocity;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	Note(int pitch, int velocity, int start, int end) {
		this.pitch = pitch;
		this.velocity = velocity;
		this.start = start;
		this.end = end;
	}
}
