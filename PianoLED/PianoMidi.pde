import javax.sound.midi.*;

void playMidi() {
  //myBus.sendNoteOn(1, 60, 127);

//  MidiDevice device = null;

//  // Find the MIDI device by name
//  MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
//  for (MidiDevice.Info info : infos) {
//    if (info.getName().toLowerCase().contains("piano")) {
//      try {
//        device = MidiSystem.getMidiDevice(info);
//        device.open();
//        println("Opened "+info.getName());
//        continue;
//      }
//      catch (MidiUnavailableException e) {
//        e.printStackTrace();
//      }
//      break;
//    }
//  }

  
//  // Create a receiver and connect it to the device
//  Receiver receiver = null;
//  try {
//    println("MaxTransmitters:"+device.getMaxTransmitters() + " MaxReceivers:"+device.getMaxReceivers());
//    receiver = device.getReceiver(); //<>//
//  } catch (MidiUnavailableException e) {
//    e.printStackTrace();
//  }
  
  // Open the sequencer
  Sequencer sequencer = null;
  try {
    sequencer = MidiSystem.getSequencer();
    sequencer.open();
  }
  catch (MidiUnavailableException e) {
    e.printStackTrace();
  }

  // Read the MIDI file
  Sequence sequence = null;
  try {
    sequence = MidiSystem.getSequence(new File("C:\\Projects\\pianoled-arduino-artur\\PianoLED\\shape.mid"));
  }
  catch (InvalidMidiDataException | IOException e) {
    e.printStackTrace();
  }

  // Set the sequence to the sequencer
  try {
    sequencer.setSequence(sequence);
  }
  catch (InvalidMidiDataException e) {
    e.printStackTrace();
  }

  
  //// Start playing the sequence and send the MIDI messages to the piano
  //sequencer.start();
  //while (sequencer.isRunning()) {
  //  // Iterate over the tracks in the sequence
  //  for (Track track : sequence.getTracks()) {
  //    // Iterate over the MIDI events in the track
  //    for (int i = 0; i < track.size(); i++) {
  //      MidiEvent event = track.get(i);
  //      MidiMessage message = event.getMessage();
  //      if (message instanceof ShortMessage) {
  //        ShortMessage sm = (ShortMessage) message;
  //        // Check if the message is a note on message
  //        if (sm.getCommand() == ShortMessage.NOTE_ON) {
  //          // Send the message to the receiver
  //          //receiver.send(sm, -1);
  //        }
  //      }
  //    }
  //  }
  //}

  //// Close the receiver and sequencer
  ////receiver.close();
  //sequencer.stop();
  //sequencer.close();
}
