package com.serifpersia.pianolux.liveplay;

public class NoteWithTime {
	int pitch;
	int velocity;
	long start;
	long end;
	int trackNum;

	public int getPitch() {
		return pitch;
	}

	public int getVelocity() {
		return velocity;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public int getTrackNum() {
		return trackNum;
	}

	public NoteWithTime(int pitch, int velocity, long start, long end, int trackNum) {
		this.pitch = pitch;
		this.velocity = velocity;
		this.start = start;
		this.end = end;
		this.trackNum = trackNum;
	}
	
	public boolean isPlayingAt(long time)
	{
		return start < time && end > time;
	}

	public boolean isBefore(long time) {
		return start > time;
	}

	public boolean isAfter(long time) {
		return end < time; 
	}
	
	public void setEnd(long _end)
	{
		this.end = _end;
	}

}
