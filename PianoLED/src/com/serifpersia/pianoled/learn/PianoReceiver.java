package com.serifpersia.pianoled.learn;

import java.util.ArrayList;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import com.serifpersia.pianoled.learn.PianoMidiConsumer;

import com.serifpersia.pianoled.Log;

public class PianoReceiver implements Receiver {
	ArrayList<PianoMidiConsumer> consumers = new ArrayList<>();
	
	public void addConsumer(PianoMidiConsumer consumer)
	{
		this.consumers.add(consumer);
	}
	
	@Override
	public void send(MidiMessage midiMessage, long timeStamp) {
		if (midiMessage instanceof ShortMessage message) {
			int pitch = message.getData1();
			int velocity = message.getData2();
			if (message.getCommand() == ShortMessage.NOTE_ON && message.getData2() != 0) {
				for (PianoMidiConsumer consumer : consumers) {
					consumer.onPianoKeyOn(pitch, velocity);
				}

			} else if (message.getCommand() == ShortMessage.NOTE_OFF
					|| message.getCommand() == ShortMessage.NOTE_ON && message.getData2() == 0) {
				for (PianoMidiConsumer consumer : consumers) {
					consumer.onPianoKeyOff(pitch);
				}
			} else {
				Log.debug("Command " + message.getCommand() + ": " + pitch + " " + velocity);
			}
		} else if (midiMessage instanceof MetaMessage) {
			MetaMessage metaMessage = (MetaMessage) midiMessage;
			byte[] data = metaMessage.getData();
			Log.debug("Meta Command " + metaMessage.getType() + ": " + data[0] + " " + data[1]);
		} else if (midiMessage instanceof SysexMessage) {
			SysexMessage sysexMessage = (SysexMessage) midiMessage;
			byte[] data = sysexMessage.getData();
			Log.debug("Sysex Command " + (data[0] & 0xFF) + " " + (data[1] & 0xFF));
		} else {
			byte[] msg = midiMessage.getMessage();
			Log.debug("Unknown Command " + (msg[0] & 0xFF) + " : " + (msg[1] & 0xFF) + " " + (msg[2] & 0xFF));
		}
	}

	@Override
	public void close() {
	}
}
