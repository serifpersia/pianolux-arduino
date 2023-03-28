import themidibus.*; //<>// //<>// //<>//
import javax.sound.midi.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
class PianoRoll
{
  File midiFile;
  MidiDevice midiOutDevice;
  MidiBus myBus;
  Sequencer sequencer;
  Sequence sequence;

  boolean isPlaying = false;

  //////////////
  LinkedList<Note> notes;
  Note currentNote;

  //List<Note> notesDisplayed = new boolean[512];
  boolean[] keysOn = new boolean[128];
  double pianoRollTickHeight;
  double ticksPerSec;
  int delayOffsetp;
  ///////////

  int pianoRollPaddingLeft = 15;
  //88 keys
  int numKeys = 88;
  int numWhiteKeys = 52;
  int firstPianoKeyPitch = 22; //A0 at 88keys

  boolean pause = false;

  int whiteKeyWidth = 15;
  int whiteKeyHeight = 70;
  int blackKeyWidth = 8;
  int blackKeyHeight = 40;


  double pianoRollTickHeightMult = 100;
  int tickResolution = 1000;
  int pianoRollTop = 100;
  int pianoRollBottom;
  int pianoRollSide = 80;
  int pianoRollHeight;

  int paddingBottom = 10;
  float blackKeyHeightRatio = 0.7;

  long currentTick;

  public PianoRoll(File midiFile, MidiDevice midiOutDevice)
  {
    this.midiFile = midiFile;
    this.midiOutDevice = midiOutDevice;
    pianoRollBottom = height-paddingBottom-whiteKeyHeight;
    pianoRollHeight = pianoRollBottom - pianoRollTop;

    notes = readMidi(midiFile);
    setupSequencer();
  }

  public void setFollowKey(boolean on)
  {
    //TBD
  }

  public void draw() {
    background(0);
    drawVisualization();
  }

  int firstNote = 0;

  //Visualisation
  void drawVisualization() {
    // Draw piano roll
    drawPianoRoll();
    drawPianoKeys();
    
    int delayOffset = (int)(pianoRollHeight/pianoRollTickHeight);
    
    currentTick = sequencer.getTickPosition();

    int n = notes.size();
    for (int noteNum = firstNote; noteNum < n; noteNum++)
    { //<>//
      Note note = notes.get(noteNum);
      
      // note is too far yet to display 
      if (note.start > currentTick+delayOffset)
        break;

      // note finished
      if (note.end < currentTick)
      {
        firstNote = noteNum;
        continue;
      }

      drawNote(note);
    }

    drawBorders();
  }
  
  void drawBorders()
  {
    stroke(0);
    rect(0, height-paddingBottom, width, height);
  }

  void drawPianoKeys() {
    int numWhiteKeys = 52; // There are 52 white keys on an 88-key piano

    // Draw white keys
    fill(255);
    for (int i = 0; i < numWhiteKeys; i++) {
      int pitch = whiteKeyToPitch(i);
      int posX = pianoRollPaddingLeft + i * whiteKeyWidth;
      if (keysOn[pitch])
      {
        fill(0, 100, 0);
      } else
        fill(255);

      stroke(200, 200, 200);
      rect(posX, pianoRollBottom, whiteKeyWidth, whiteKeyHeight);
      fill(255);
      if ( i == 23 ) //C4 in 88 key
      {
        fill(128);
        text("C", posX+whiteKeyWidth/2-5, pianoRollBottom+whiteKeyHeight-10);
        fill(255);
      }
      if ((i-2)%7==0) {
        stroke(100, 100, 100);
        line(posX, pianoRollTop, posX, pianoRollBottom);
      }
    }

    // Draw black keys
    int[] blackKeyIndices = {0, 2, 3, 5, 6};
    int numBlackKeys = 0;
    for (int i = 0; i < numWhiteKeys; i++) {
      //int keyIndex = i + firstMidiKey;
      if (Arrays.binarySearch(blackKeyIndices, i % 7) >= 0) {
        int whiteKeyPosX = pianoRollPaddingLeft + i * whiteKeyWidth;
        int blackKeyPosX = whiteKeyPosX + whiteKeyWidth * 3 / 4;
        fill(0);
        rect(blackKeyPosX, pianoRollBottom, blackKeyWidth, blackKeyHeight);
        numBlackKeys++;
      }
      if (numBlackKeys == 36) { // We have drawn all the black keys
        break;
      }
    }
  }

  int whiteKeyToPitch(int whiteKeyNum)
  {
    int[] whiteKeysIndex = {0, 1, 1, 2, 3, 3, 4};
    return whiteKeyNum/7*7 + whiteKeysIndex[whiteKeyNum%7];
  }

  int midiToPianoPitch(int pitch)
  {
    //if(numKeys = 88)
    return pitch-firstPianoKeyPitch+1;
  }

  void drawNote(Note note) {
    long timeElapsedTicks = note.start-currentTick;

    // Calculate the position and dimensions of the note rectangle
    float xOffset = isBlack(note.pitch) ? 1 : 2;

    float x = midiPitchToXPosition(note.pitch)+xOffset;
    float w = isBlack(note.pitch) ? blackKeyWidth-2 : whiteKeyWidth-4;

    float y = getTickY(timeElapsedTicks);
    float h = (float)((note.end-note.start) * pianoRollTickHeight);

    int transperancy = min(note.velocity+50, 255);
    // Draw the note rectangle
    if ( isBlack(note.pitch) )
      fill(0, 0, 255, transperancy);
    else
      fill(0, 255, 0, transperancy);
    //noStroke();
    float cornerRadius = 30;
    rectMode(CORNERS);
    rect(x, y, x+w, y-h, cornerRadius);
    rectMode(CORNER);

    fill(0);
  }

  float getTickY(long tick)
  {
    return (float)(pianoRollBottom-tick * pianoRollTickHeight-10);
  }

  void drawPianoRoll()
  {
    long offset = (long)((currentTick % tickResolution) * pianoRollTickHeight);

    stroke(100, 100, 100);
    fill(255);

    for (long tick = currentTick; tick >= 0; tick -= tickResolution) {
      float y = (float)(getTickY(tick-currentTick) + offset);
      if ( y > pianoRollBottom )
        break;

      //line(pianoRollSide, y, width-pianoRollSide, y);
      text(String.valueOf(tick-currentTick % tickResolution), 10, y+5);
    }

    //line(pianoRollSide, pianoRollTop, width-pianoRollSide, pianoRollTop);
    //text(currentTick, 10, pianoRollTop+5);

    stroke(0);
  }

  boolean isPaused()
  {
    return !sequencer.isRunning();
  }

  boolean isBlack(int midiPitch)
  {
    List<Integer> midiBlackKeys = Arrays.asList(1, 3, 6, 8, 10); //C
    return midiBlackKeys.contains(midiPitch % 12);
  }

  float midiPitchToXPosition(int midiPitch) {
    
    int pianoPitch = midiPitch - firstPianoKeyPitch + 1;

    int[] whiteKeyIndices = {0, 0, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6}; //A0
    int octave = pianoPitch / 12;
    int whiteKeyId = octave * 7 + whiteKeyIndices[pianoPitch % 12];

    int offsetX = 0;
    if ( isBlack(midiPitch) )
    {
      offsetX += whiteKeyWidth*3/4;
    }

    return pianoRollPaddingLeft + whiteKeyId * whiteKeyWidth + offsetX; //<>//
  }


  //MIDI handling
  void setupSequencer() {
    try {
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequence = MidiSystem.getSequence(midiFile);
      sequencer.setSequence(sequence);

      int ticksPerBeat = sequence.getResolution();
      double bpm = sequencer.getTempoInBPM();
      ticksPerSec = ticksPerBeat * bpm / 60;

      pianoRollTickHeight = (60.0 / ticksPerBeat) / bpm * pianoRollTickHeightMult;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void rewind()
  {
    sequencer.setTickPosition(0);
    firstNote = 0;
  }

  public void rewind(int sec)
  {
    long currentTick = sequencer.getTickPosition();
    long rewindTick = (long) (currentTick + ticksPerSec * sec);

    // Make sure the rewindTick is not negative
    if (rewindTick < 0) {
      rewindTick = 0;
    }

    firstNote = 0;
    sequencer.setTickPosition(rewindTick);
  }

  void pause()
  {
    //pause = !pause;
    if ( sequencer.isRunning() )
    {
      sequencer.stop();
    } else
    {
      sequencer.start();
    }
  }
}


void PianoRollPlayPause()
{
  if ( pianoRoll != null ) pianoRoll.pause();
  Button button = cp5.get(Button.class, "PianoRollPlayPause");
  if ( pianoRoll.isPaused() )
  {
    button.getCaptionLabel().setText(">");
    button.setColorBackground(BLUE);
  } else
  {
    button.getCaptionLabel().setText("||");
    button.setColorBackground(GREEN);
  }
}

void PianoRollRewind()
{
  if ( pianoRoll != null ) pianoRoll.rewind();
}
int REWIND_FRAGMENT_SEC = 3;

void PianoRollForwardFragment()
{
  if ( pianoRoll != null ) pianoRoll.rewind(REWIND_FRAGMENT_SEC);
}

void PianoRollBackwardFragment()
{
  if ( pianoRoll != null ) pianoRoll.rewind(-REWIND_FRAGMENT_SEC);
}


void PianoRollLoadMidi() {
  // Use a file chooser dialog box to get the MIDI file to play
  JFileChooser chooser = new JFileChooser(); //<>//
  chooser.setCurrentDirectory(new File("."));

  // Add a file filter to only allow MIDI files
  FileFilter filter = new FileFilter() {
    public boolean accept(File file) {
      String filename = file.getName().toLowerCase();
      return filename.endsWith(".mid") || filename.endsWith(".midi") || file.isDirectory();
    }

    public String getDescription() {
      return "MIDI files (*.mid, *.midi)";
    }
  };
  chooser.setFileFilter(filter);

  int result = chooser.showOpenDialog(null);
  if (result == JFileChooser.APPROVE_OPTION) {
    File midiFile = chooser.getSelectedFile();

    try {

      // Get the selected MIDI output device
      int midiOutIndex = (int) cp5.get(ScrollableList.class, "midiout").getValue();
      MidiDevice.Info[] midiOutDeviceInfo = MidiSystem.getMidiDeviceInfo();
      MidiDevice midiOutDevice = MidiSystem.getMidiDevice(midiOutDeviceInfo[midiOutIndex]);
      surface.setSize(PIANO_ROLL_HEIGHT, PIANO_ROLL_WIDTH);
      pianoRoll = new PianoRoll(midiFile, midiOutDevice);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
