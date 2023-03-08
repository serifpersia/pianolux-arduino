ControlP5 cp5;

int MIN_FADE_RATE = 1;
int MAX_FADE_RATE = 250;
int DEFAULT_FADE_RATE = 50;

color RED = color(255, 0, 0);
color GREEN = color(0, 255, 0);
color BLUE = color(0, 0, 255);
color BLACK = color(0, 0, 0);
color WHITE = color(255, 255, 255);
color CYAN = color(0, 255, 255);
color YELLOW = color(255, 255, 0);
color MAGENTA = color(255, 0, 255);

color APP_COLOR_FG = RED;
color APP_COLOR_BG = BLACK;
color APP_COLOR_ACT = RED;

color SLIDER_COLOR_FG = CYAN;
color SLIDER_COLOR_BG = BLACK;
color SLIDER_COLOR_ACT = CYAN;

int MIN_BRIGHT = 10;
int MAX_BRIGHT = 255;
int DEF_BRIGHT = 1898;

int MIN_COLOR = 0;
int MAX_COLOR = 255;
int DEF_COLOR = MAX_COLOR;
int EFFECT_CONTROLS_X = 800;

ControlP5 buildUI()
{
  ControlP5 cp5 = new ControlP5(this);

  addSlider( cp5, "Brightness", "B", EFFECT_CONTROLS_X, 75, MIN_BRIGHT, MAX_BRIGHT, DEF_BRIGHT);

  addSlider( cp5, "Red", "R", EFFECT_CONTROLS_X, 95, 0, 0, MIN_COLOR, MAX_COLOR, MAX_COLOR, -1, -1, RED, -1, RED);
  addSlider( cp5, "Green", "G", EFFECT_CONTROLS_X, 105, 0, 0, MIN_COLOR, MAX_COLOR, MAX_COLOR, -1, -1, GREEN, -1, GREEN);
  addSlider( cp5, "Blue", "B", EFFECT_CONTROLS_X, 115, 0, 0, MIN_COLOR, MAX_COLOR, MAX_COLOR, -1, -1, BLUE, -1, BLUE);
  addSlider( cp5, "FadeOnVal", "F", EFFECT_CONTROLS_X, 125, MIN_FADE_RATE, MAX_FADE_RATE, DEFAULT_FADE_RATE);

  addButton(cp5, "setLeftSideG", "Set LG", 735, 140, 30, 15).hide();
  addButton(cp5, "setRightSideG", "Set RG", 765, 140, 30, 15).hide();

  addButton(cp5, "setLeftSide", "Set L", 735, 140, 30, 15).hide();
  addButton(cp5, "setRightSide", "Set R", 765, 140, 30, 15).hide();


  addButton(cp5, "setL", "Set L", 675, 140, 30, 15).hide();
  addButton(cp5, "setM", "Set M", 705, 140, 30, 15).hide();
  addButton(cp5, "setLM", "Set LM", 735, 140, 30, 15).hide();
  addButton(cp5, "setH", "Set H", 765, 140, 30, 15).hide();

  addButton(cp5, "P174", "174 Preset", 200, 15, 60, 15);
  addButton(cp5, "Open", null, EFFECT_CONTROLS_X, 47, 50, 15);
  addButton(cp5, "Refresh", null, 850, 47, 50, 15 );

  addScrollableList(cp5, "midi", "Midi Device", null, 0, EFFECT_CONTROLS_X, 30, 100, 110, 15, 15);
  addScrollableList(cp5, "comlist", "Arduino Port", null, 0, EFFECT_CONTROLS_X, 15, 100, 110, 15, 15);

  addButton(cp5, "leftArrow", "<", 380, 25, 30, 15, APP_COLOR_FG, BLUE, APP_COLOR_ACT);
  addButton(cp5, "rightArrow", ">", 415, 25, 30, 15, APP_COLOR_FG, BLUE, APP_COLOR_ACT);
  addButton(cp5, "AdvanceUser", null, 15, 15, 0, 0);
  //addButton(cp5, "Exit", null, 140, 90, 50, 15);

  addScrollableList(cp5, "modelist", "Mode", m, 0, 695, 15, 100, 140, 15, 15);

  int SPLASH_CONTROL_X = EFFECT_CONTROLS_X;
  int SPLASH_CONTROL_Y = 90;

  addSplashControls(cp5, SPLASH_CONTROL_X, SPLASH_CONTROL_Y);

  return cp5;
}

void addSplashControls(ControlP5 cp5, int x, int y)
{
  int SPLASH_MIN_LEN = 5;
  int SPLASH_MAX_LEN = 15;
  int SPLASH_DEFAULT_LEN = 8;

  int SPLASH_MIN_TAIL_FADE = 1;
  int SPLASH_MAX_TAIL_FADE = 50;
  int SPLASH_DAFAULT_TAIL_FADE = 15;

  int SPLASH_MIN_HEAD_FADE = 1;
  int SPLASH_MAX_HEAD_FADE = 30;
  int SPLASH_DAFAULT_HEAD_FADE = 5;

  int SPLASH_MIN_VELO_BRI = 5;
  int SPLASH_MAX_VELO_BRI = 15;
  int SPLASH_DAFAULT_VELO_BRI = 10;

  int SPLASH_MIN_VELO_SPEED = 5;
  int SPLASH_MAX_VELO_SPEED = 15;
  int SPLASH_DAFAULT_VELO_SPEED = 10;

  int SPLASH_CONTROL_Y_STEP = 20;
  int captionAlignX = CENTER;
  int captionAlignY = ControlP5.TOP_OUTSIDE;

  addSlider( cp5, "splashMaxLength", "Max Splash Length", x, y, SPLASH_MIN_LEN, SPLASH_MAX_LEN, SPLASH_DEFAULT_LEN, captionAlignX, captionAlignY).hide();
  y += SPLASH_CONTROL_Y_STEP;
  addSlider( cp5, "splashTailFade", "Tail Fade Rate", x, y, SPLASH_MIN_TAIL_FADE, SPLASH_MAX_TAIL_FADE, SPLASH_DAFAULT_TAIL_FADE, captionAlignX, captionAlignY).hide();
  y += SPLASH_CONTROL_Y_STEP;
  //addSlider( cp5, "splashHeadFade", "Head Fade Rate", x, y, SPLASH_MIN_HEAD_FADE, SPLASH_MAX_HEAD_FADE, SPLASH_DAFAULT_HEAD_FADE, captionAlignX , captionAlignY).hide();
  //y += SPLASH_CONTROL_Y_STEP;
  addSlider( cp5, "splashVelocityBrightnessImpact", "Velocity To Bright", x, y, SPLASH_MIN_VELO_BRI, SPLASH_MAX_VELO_BRI, SPLASH_DAFAULT_VELO_BRI, captionAlignX, captionAlignY).hide();
  y += SPLASH_CONTROL_Y_STEP;
  addSlider( cp5, "splashVelocitySpeedImpact", "Velocity To Speed", x, y, SPLASH_MIN_VELO_SPEED, SPLASH_MAX_VELO_SPEED, SPLASH_DAFAULT_VELO_SPEED, captionAlignX, captionAlignY).hide();
  y += SPLASH_CONTROL_Y_STEP;
}


int[][] Keys = new int[88][2];
int[][] Leds = new int[88][2];
int rectASizeX = 0;
int rectBSizeX = 0;
int rectBX = 795;

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

void hideAllControls()
{
  hideLegacyControls();
  hideSplashControls();
}
void showDefaultControls()
{
  setControllersVisible(getDefaultControllers(), true);
}

void hideLegacyControls()
{
  setControllersVisible(getLegacyControllers(), false);
}

void showSplashControls()
{
  setControllersVisible(getSplashControllers(), true);
}

void hideSplashControls()
{
  setControllersVisible(getSplashControllers(), false);
}

void showGradientButtons()
{
  setGradientButtonsVisible(true);
}

void hideGradientButtons()
{
  setGradientButtonsVisible(false);
}

void showSideButtons()
{
  setSideButtonsVisible(true);
}

void hideSideButtons()
{
  setSideButtonsVisible(false);
}

void showButtons()
{
  setButtonsVisible(true);
}

void hideButtons()
{
  setButtonsVisible(false);
}


void setGradientButtonsVisible(boolean visible)
{
  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("setLeftSideG"));
  cl.add(cp5.getController("setRightSideG"));
  setControllersVisible(cl, visible);
}

void setSideButtonsVisible(boolean visible)
{
  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("setLeftSide"));
  cl.add(cp5.getController("setRightSide"));
  setControllersVisible(cl, visible);
}

void setButtonsVisible(boolean showButtons) {
  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("setL"));
  cl.add(cp5.getController("setLM"));
  cl.add(cp5.getController("setM"));
  cl.add(cp5.getController("setH"));

  setControllersVisible(cl, showButtons);
}

List<Controller> getDefaultControllers()
{
  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("Red"));
  cl.add(cp5.getController("Green"));
  cl.add(cp5.getController("Blue"));
  cl.add(cp5.getController("FadeOnVal"));
  return cl;
}

List<Controller> getLegacyControllers()
{
  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("Red"));
  cl.add(cp5.getController("Green"));
  cl.add(cp5.getController("Blue"));
  cl.add(cp5.getController("FadeOnVal"));

  cl.add(cp5.getController("setLeftSideG"));
  cl.add(cp5.getController("setRightSideG"));
  cl.add(cp5.getController("setLeftSide"));
  cl.add(cp5.getController("setRightSide"));
  cl.add(cp5.getController("setL"));
  cl.add(cp5.getController("setLM"));
  cl.add(cp5.getController("setM"));
  cl.add(cp5.getController("setH"));
  return cl;
}

List<Controller> getSplashControllers()
{
  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("splashMaxLength"));
  cl.add(cp5.getController("FadeOnVal"));
  cl.add(cp5.getController("splashHeadFade"));
  //cl.add(cp5.getController("splashVelocityBrightnessImpact"));
  //cl.add(cp5.getController("splashVelocitySpeedImpact"));
  return cl;
}

void setControllersVisible(List<Controller> cl, boolean visible) {
  for (Controller c : cl)
  {
    if ( c!=null)
    {
      c.setVisible(visible);
    }
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
