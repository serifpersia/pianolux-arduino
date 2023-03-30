class Note {
  int pitch;
  int velocity;
  int start;
  int end;

  Note(int pitch, int velocity, int start, int end) {
    this.pitch = pitch;
    this.velocity = velocity;
    this.start = start;
    this.end = end;
  }
}

public LinkedList<Note> readMidi(File midiFile) {
  LinkedList<Note> fileNotes = new LinkedList<Note>();
  try {
    // Load the MIDI file
    Sequence sequence = MidiSystem.getSequence(midiFile);

    // Iterate over each track in the sequence
    for (Track track : sequence.getTracks()) {
      // Iterate over each event in the track
      for (int i = 0; i < track.size(); i++) {
        MidiEvent event = track.get(i);
        if (event.getMessage() instanceof ShortMessage) {
          ShortMessage message = (ShortMessage) event.getMessage();
          if (message.getCommand() == ShortMessage.NOTE_ON && message.getData2() != 0 ) {
            int noteValue = message.getData1();
            int noteVelocity = message.getData2();
            int noteStart = (int) event.getTick();
            int noteEnd = 0;
            for (int j = i + 1; j < track.size(); j++) {
              MidiEvent endEvent = track.get(j);
              if (endEvent.getMessage() instanceof ShortMessage) {
                ShortMessage endMessage = (ShortMessage) endEvent.getMessage();
                if (endMessage.getData1() == noteValue &&
                  (endMessage.getCommand() == ShortMessage.NOTE_OFF || endMessage.getData2() == 0)) {
                  noteEnd = (int) endEvent.getTick();
                  break;
                }
              }
            }
            if (noteEnd == 0) {
              // Note off event not found, set end time to the end of the track
              noteEnd = (int) sequence.getTickLength();
            }
            // Create a new Note object and add it to the list
            Note note = new Note(noteValue, noteVelocity, noteStart, noteEnd);
            fileNotes.add(note);
          }
        }
      }
    }
  }
  catch (Exception e) {
    e.printStackTrace();
    return null;
  }

  Collections.sort(fileNotes, new Comparator<Note>() {
    @Override
      public int compare(Note n1, Note n2) {
      return Integer.compare(n1.start, n2.start);
    }
  }
  );

  return fileNotes;
}
