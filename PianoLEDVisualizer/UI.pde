ControlP5 cp5;

final static int MIN_FADE_RATE = 1;
final static int MAX_FADE_RATE = 250;
final static int DEFAULT_FADE_RATE = 50;

int[][] Keys = new int[88][2];
int[][] Leds = new int[88][2];
int rectASizeX = 0;
int rectBSizeX = 0;
int rectBX = 795;
// List of white keys in a 88-key piano
int whiteKeys[] = {0, 2, 3, 5, 7, 8, 10, 12, 14, 15
  , 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36, 38, 39, 41, 43
  , 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79
  , 80, 82, 84, 86, 87};
int[] blackKeys = {1, 4, 6, 9, 11, 13, 16, 18, 21, 23, 25, 28, 30, 33, 35,
  37, 40, 42, 45, 47, 49, 52, 54, 57, 59, 61, 64, 66, 69, 71, 73, 76, 78,
  81, 83, 85};
// Create a list of x-coordinates for each key
int[] keyXCoordinates = {11, 40, 56, 86, 101, 116, 145, 161, 191, 206, 221,
  251, 266, 296, 311, 326, 356, 371, 401, 416, 431, 461, 476, 506, 521,
  536, 566, 581, 611, 626, 641, 671, 686, 715, 731, 746}; // Add the missing x-coordinate
// List of white keys in a 88-key piano
int leds[] = {0, 2, 3, 5, 7, 8, 10, 12, 14, 15
  , 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36, 38, 39, 41, 43
  , 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79
  , 80, 82, 84, 86, 87};
int[] ledsBlack = {1, 4, 6, 9, 11, 13, 16, 18, 21, 23, 25, 28, 30, 33, 35,
  37, 40, 42, 45, 47, 49, 52, 54, 57, 59, 61, 64, 66, 69, 71, 73, 76, 78,
  81, 83, 85};
// Create a list of x-coordinates for each key
int[] keyYCoordinates = {11, 40, 56, 86, 101, 116, 145, 161, 191, 206, 221,
  251, 266, 296, 311, 326, 356, 371, 401, 416, 431, 461, 476, 506, 521,
  536, 566, 581, 611, 626, 641, 671, 686, 715, 731, 746}; // Add the missing x-coordinate

int Red = TOP_COLOR, Green = TOP_COLOR,
  Blue = TOP_COLOR, Brightness = TOP_COLOR,
  FadeOnVal = TOP_COLOR, velRedL = TOP_COLOR,
  velGreenL = TOP_COLOR, velBlueL = TOP_COLOR,
  velRedLM = TOP_COLOR, velGreenLM = TOP_COLOR,
  velBlueLM = TOP_COLOR, velRedM = TOP_COLOR,
  velGreenM = TOP_COLOR, velBlueM = TOP_COLOR,
  velRedH = TOP_COLOR, velGreenH = TOP_COLOR, velBlueH = TOP_COLOR,
  splitLeftRed = TOP_COLOR, splitLeftGreen = TOP_COLOR, splitLeftBlue = TOP_COLOR,
  splitRightRed = TOP_COLOR, splitRightGreen = TOP_COLOR, splitRightBlue = TOP_COLOR,
  LeftSideGRed = TOP_COLOR, LeftSideGGreen = TOP_COLOR, LeftSideGBlue = 0,
  RightSideGRed = TOP_COLOR, RightSideGGreen = TOP_COLOR, RightSideGBlue = TOP_COLOR;

ControlP5 buildUI()
{
  ControlP5 cp5 = new ControlP5(this);

  cp5.addSlider("Brightness")
    .setPosition(800, 75)
    .setColorForeground(color(255))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setSize(99, 10)
    .setLabel("B")
    .setRange(10, 255)
    .setValue(188)
    .setNumberOfTickMarks(5)
    ;
  cp5.addSlider("Red")
    .setPosition(800, 95)
    .setCaptionLabel("R")
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setRange(0, 255)
    ;
  cp5.addSlider("Green")
    .setPosition(800, 105)
    .setCaptionLabel("G")
    .setColorForeground(color(0, 255, 0))
    .setColorBackground(color(0))
    .setColorActive(color(0, 255, 0))
    .setRange(0, 255)
    ;
  cp5.addSlider("Blue")
    .setCaptionLabel("B")
    .setPosition(800, 115)
    .setColorForeground(color(0, 0, 255))
    .setColorBackground(color(0))
    .setColorActive(color(0, 0, 255))
    .setRange(0, 255)
    ;
  cp5.addSlider("FadeOnVal")
    .setCaptionLabel("F")
    .setPosition(800, 125)
    .setColorForeground(color(0, 255, 255))
    .setColorBackground(color(0))
    .setColorActive(color(0, 255, 255))
    .setRange(MIN_FADE_RATE, MAX_FADE_RATE)
    .setValue(DEFAULT_FADE_RATE)
    ;
  cp5.addButton("setLeftSideG")
    .setPosition(735, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set LG")
    ;
  cp5.addButton("setRightSideG")
    .setPosition(765, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set RG")
    ;
  cp5.addButton("setLeftSide")
    .setPosition(735, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set L")
    ;
  cp5.addButton("setRightSide")
    .setPosition(765, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set R")
    ;
  cp5.addButton("setL")
    .setPosition(675, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set L")
    ;
  cp5.addButton("setLM")
    .setPosition(705, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set LM");
  cp5.addButton("setM")
    .setPosition(735, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set M")
    ;
  cp5.addButton("setH")
    .setPosition(765, 140)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("Set H")
    ;
  cp5.addButton("P174")
    .setPosition(200, 15)
    .setSize(60, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setLabel("174 Preset");
  cp5.addButton("Open")
    .setPosition(800, 47)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setSize(50, 15)
    ;
  cp5.addButton("Refresh")
    .setSize(50, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setPosition(850, 47)
    ;
  cp5.addScrollableList("midi")
    .close()
    .setPosition(800, 30)
    .setSize(100, 100)
    .setItemHeight(15)
    .setBarHeight(15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .getCaptionLabel().set("Midi Device");
  cp5.addScrollableList("comlist")
    .close()
    .setPosition(800, 15)
    .setSize(100, 100)
    .setItemHeight(15)
    .setBarHeight(15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .getCaptionLabel().set("Arduino Port")
    ;

  cp5.addButton("leftArrow")
    .setPosition(380, 25)
    .setSize(30, 15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0, 0, 255))
    .setColorActive(color(255, 0, 0))
    .setLabel("<")
    ;
  cp5.addButton("rightArrow")
    .setPosition(415, 25)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0, 0, 255))
    .setColorActive(color(255, 0, 0))
    .setSize(30, 15)
    .setLabel(">");
  cp5.addButton("AdvanceUser")
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .setPosition(15, 15)
    ;
  /*
  cp5.addButton("Exit")
   .setPosition(140, 90)
   .setColorForeground(color(255, 0, 0))
   .setColorBackground(color(0))
   .setColorActive(color(255, 0, 0))
   .setSize(50, 15);
   */
  cp5.addScrollableList("modelist")
    .setPosition(695, 15)
    .setSize(100, 100)
    .setBarHeight(15)
    .setItemHeight(15)
    .setColorForeground(color(255, 0, 0))
    .setColorBackground(color(0))
    .setColorActive(color(255, 0, 0))
    .addItems(m)
    .setValue(0)
    .getCaptionLabel().set("Mode");
  return cp5;
}

void leftArrow()
{
  if (counter <=0)
  {
    numberselected = 176;
    firstNoteSelected = 21;
    lastNoteSelected = 108;
    return;
  }
  counter--; // increment counter each time button is pressed
  println("counter: " + counter);
  if (counter == 3)
  {
    rectASizeX = 135;
    rectBSizeX+= 105;
    numberselected = 122;
    firstNoteSelected = 36;
    lastNoteSelected = 96;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 2)
  {
    rectASizeX-= 75;
    rectBSizeX+= 30;
    numberselected = 146;
    firstNoteSelected = 28;
    lastNoteSelected = 100;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 1)
  {
    rectASizeX= 60;
    rectBSizeX+= 30;
    numberselected = 152;
    firstNoteSelected = 28;
    lastNoteSelected = 103;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 0)
  {
    rectASizeX -= 60;
    rectBSizeX-=60;
    numberselected = 176;
    firstNoteSelected = 21;
    lastNoteSelected = 108;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 0)
  {
    rectASizeX = 0;
    rectBSizeX = 0;
  }
}
void rightArrow()
{
  if (counter >=4)
  {
    return;
  }
  counter++; // increment counter each time button is pressed
  println("counter: " + counter);
  if (counter == 0)
  {
    rectASizeX = 0;
    rectBSizeX = 0;
    numberselected = 176;
    firstNoteSelected = 21;
    lastNoteSelected = 108;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 1)
  {
    rectASizeX+=60;
    rectBSizeX-=45;
    numberselected = 152;
    firstNoteSelected = 28;
    lastNoteSelected = 103;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }

  if (counter == 2)
  {
    rectASizeX=+60;
    rectBSizeX-=30;
    numberselected = 146;
    firstNoteSelected = 28;
    lastNoteSelected = 100;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 3)
  {
    rectASizeX= 135;
    rectBSizeX-=30;
    numberselected = 122;
    firstNoteSelected = 36;
    lastNoteSelected = 96;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
  if (counter == 4)
  {
    rectASizeX= 135;
    rectBSizeX-=105;
    numberselected = 98;
    firstNoteSelected = 36;
    lastNoteSelected = 84;
    println("Selected number led: " + numberselected);
    println("Selected first note: " + firstNoteSelected);
    println("Selected last note: " + lastNoteSelected);
  }
}

void P174()
{
  numberselected = 174;
  firstNoteSelected = 21;
  lastNoteSelected = 108;
}

void setGradientButtonsVisible(boolean showGradientButtons)
{
  if (showGradientButtons) {
    cp5.getController("setLeftSideG").setVisible(true);
    cp5.getController("setRightSideG").setVisible(true);
  } else {
    cp5.getController("setLeftSideG").setVisible(false);
    cp5.getController("setRightSideG").setVisible(false);
  }
}

void setSideButtonsVisible(boolean showSideButtons)
{
  if (showSideButtons) {
    cp5.getController("setLeftSide").setVisible(true);
    cp5.getController("setRightSide").setVisible(true);
  } else {
    cp5.getController("setLeftSide").setVisible(false);
    cp5.getController("setRightSide").setVisible(false);
  }
}
void setButtonsVisible(boolean showButtons) {
  if (showButtons) {
    cp5.getController("setL").setVisible(true);
    cp5.getController("setLM").setVisible(true);
    cp5.getController("setM").setVisible(true);
    cp5.getController("setH").setVisible(true);
  } else {
    cp5.getController("setL").setVisible(false);
    cp5.getController("setLM").setVisible(false);
    cp5.getController("setM").setVisible(false);
    cp5.getController("setH").setVisible(false);
  }
}

void draw() {
  background(0);
  presetText = "Piano: ";
  switch (numberselected) {
  case 174:
    presetText += "174 leds ";
    break;
  }
  if (firstNoteSelected == 21 && lastNoteSelected == 108) {
    presetText += "88 Keys";
  } else if (firstNoteSelected == 28 && lastNoteSelected == 103) {
    presetText += "76 Keys";
  } else if (firstNoteSelected == 28 && lastNoteSelected == 100) {
    presetText += "73 Keys";
  } else if (firstNoteSelected == 36 && lastNoteSelected == 96) {
    presetText += "61 Keys";
  } else if (firstNoteSelected == 36 && lastNoteSelected == 84) {
    presetText += "49 Keys";
  }
  fill(255);
  text(presetText, 370, 15);
  if (AnimationOn) {
    animationLoop();
  }
  //white keys
  // Initial x-coordinate of the first key
  int x = 0;
  for (int i = 0; i < whiteKeys.length; i++) {
    if (Keys[whiteKeys[i]][0] == 1) {
      fill(255, 0, 0);
    } else {
      fill(255);
    }
    // Draw the key at the current x-coordinate
    rect(x+15, 64, 15, 70);
    fill(0);
    // Move the x-coordinate to the right by 10 pixels
    // to prepare for the next key
    x += 15;
  }
  //black keys
  for (int i = 0; i < blackKeys.length; i++) {
    if (Keys[blackKeys[i]][1] == 1) {
      fill(255, 0, 0);
    } else {
      fill(0);
    }
    // Use the x-coordinate from the list to draw each key
    rect(keyXCoordinates[i]+15, 65, 8, 40);
  }
  // led strip white keys
  int y = 0;
  for (int i = 0; i < leds.length; i++) {
    fill(Keys[whiteKeys[i]][0] == 1 ? Red : 0, Keys[whiteKeys[i]][0] == 1 ? Green : 0, Keys[whiteKeys[i]][0] == 1 ? Blue : 0);
    rect(y + 15, 54, 15, 10);
    y += 15;
  }
  // led strip black keys
  for (int i = 0; i < ledsBlack.length; i++) {
    fill(Keys[blackKeys[i]][1] == 1 ? Red : 0, Keys[blackKeys[i]][1] == 1 ? Green : 0, Keys[blackKeys[i]][1] == 1 ? Blue : 0);
    rect(keyYCoordinates[i] + 15, 54, 8, 10);
  }

  // highlight piano size L&R boxes
  fill(0, 127);
  rect(15, 64, rectASizeX, 70);
  rect(rectBX, 64, rectBSizeX, 70);
  // block led strip with black color
  fill(0);
  rect(15, 54, rectASizeX, 10);
  rect(rectBX, 54, rectBSizeX, 10);
  // color bar
  fill(Red, Green, Blue);
  rect(799, 64, 100, 10);
}
