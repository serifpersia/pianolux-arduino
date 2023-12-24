package com.serifpersia.pianolux.learn;

import java.util.ArrayList;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import com.serifpersia.pianolux.ui.pnl_Colors;
import com.serifpersia.pianolux.ui.pnl_Controls;

public class PianoReceiver implements Receiver {

	private long lastInputTime = System.currentTimeMillis();
	private boolean isNormalModeActivated = false;

	ArrayList<PianoMidiConsumer> consumers = new ArrayList<>();

	public void addConsumer(PianoMidiConsumer consumer) {
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
				lastInputTime = System.currentTimeMillis(); // Update the last input time
				if (!isNormalModeActivated && pnl_Colors.rdb_IdleOn.isSelected()) {
					isNormalModeActivated = true;
					normalMode(); // Call normalMode() only once
				}
			} else if (message.getCommand() == ShortMessage.NOTE_OFF
					|| message.getCommand() == ShortMessage.NOTE_ON && message.getData2() == 0) {
				for (PianoMidiConsumer consumer : consumers) {
					consumer.onPianoKeyOff(pitch);
				}
			} else {
				if (message.getCommand() == ShortMessage.NOTE_OFF
						|| message.getCommand() == ShortMessage.NOTE_ON && message.getData2() == 0) {
					System.out.println("Command " + message.getCommand() + ": " + pitch + " " + velocity);
				}
			}
		} else if (midiMessage instanceof MetaMessage) {
			MetaMessage metaMessage = (MetaMessage) midiMessage;
			byte[] data = metaMessage.getData();
			System.out.println("Meta Command " + metaMessage.getType() + ": " + data[0] + " " + data[1]);
		} else if (midiMessage instanceof SysexMessage) {
			SysexMessage sysexMessage = (SysexMessage) midiMessage;
			byte[] data = sysexMessage.getData();
			System.out.println("Sysex Command " + (data[0] & 0xFF) + " " + (data[1] & 0xFF));
		} else {
			byte[] msg = midiMessage.getMessage();
			System.out.println("Unknown Command " + (msg[0] & 0xFF) + " : " + (msg[1] & 0xFF) + " " + (msg[2] & 0xFF));
		}

		// Check for no input timeout
		long currentTime = System.currentTimeMillis();
		long timeSinceLastInput = currentTime - lastInputTime;

		if (timeSinceLastInput > pnl_Colors.idleTime * 1000 && isNormalModeActivated) {
			System.out.println("No MIDI messages received.");
			idleMode();
			isNormalModeActivated = false;
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	private void idleMode() {
		pnl_Controls.cb_LED_Mode.setSelectedIndex(6);
		pnl_Controls.cb_LED_Animations.setSelectedIndex(pnl_Controls.cb_LED_Animations.getSelectedIndex());
	}

	private void normalMode() {
		pnl_Controls.cb_LED_Mode.setSelectedIndex(0);
		pnl_Controls.cb_LED_Animations.setSelectedIndex(pnl_Controls.cb_LED_Animations.getSelectedIndex());
	}

}
