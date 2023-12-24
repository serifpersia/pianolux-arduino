package com.serifpersia.pianolux.learn;

public class Note {
	int pitch;
	int velocity;
	int start;
	int end;
	int trackNum;

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

	public int getTrackNum() {
		return trackNum;
	}

	Note(int pitch, int velocity, int start, int end, int trackNum) {
		this.pitch = pitch;
		this.velocity = velocity;
		this.start = start;
		this.end = end;
		this.trackNum = trackNum;
	}
	
	public boolean isPlayingAt(long tick)
	{
		return start < tick && end > tick;
	}

	public boolean isBefore(long tick) {
		return start > tick;
	}

	public boolean isAfter(long tick) {
		return end < tick; 
	}
}
