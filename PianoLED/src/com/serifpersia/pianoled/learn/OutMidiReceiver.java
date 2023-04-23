package com.serifpersia.pianoled.learn;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class OutMidiReceiver implements Receiver {
	
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
    	if( receiver == null )
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
