ControlP5 cp5;

int MIN_FADE_RATE = 0;
int MAX_FADE_RATE = 255;
int DEFAULT_FADE_RATE = 255;

color RED = color(255, 0, 0);
color GREEN = color(0, 255, 0);
color BLUE = color(0, 0, 255);
color BLACK = color(0, 0, 0);
color WHITE = color(255, 255, 255);
color GREY = color(150, 150, 150);
color YELLOW = color(255, 255, 0);
color MAGENTA = color(255, 0, 255);

color APP_COLOR_FG = RED;
color APP_COLOR_BG = BLACK;
color APP_COLOR_ACT = RED;

color SLIDER_COLOR_FG = GREY;
color SLIDER_COLOR_BG = BLACK;
color SLIDER_COLOR_ACT = GREY;

int MIN_BRIGHT = 0;
int MAX_BRIGHT = 255;
int DEF_BRIGHT = 255;

int MIN_COLOR = 0;
int MAX_COLOR = 255;
int DEF_COLOR = MAX_COLOR;
int EFFECT_CONTROLS_X = 811;

int Red = MAX_COLOR, Green = MAX_COLOR,
  Blue = MAX_COLOR, Brightness = MAX_COLOR,
  FadeOnVal = MAX_COLOR, velRedL = MAX_COLOR,
  velGreenL = MAX_COLOR, velBlueL = MAX_COLOR,
  velRedLM = MAX_COLOR, velGreenLM = MAX_COLOR,
  velBlueLM = MAX_COLOR, velRedM = MAX_COLOR,
  velGreenM = MAX_COLOR, velBlueM = MAX_COLOR,
  velRedH = MAX_COLOR, velGreenH = MAX_COLOR, velBlueH = MAX_COLOR,
  splitLeftRed = MAX_COLOR, splitLeftGreen = MAX_COLOR, splitLeftBlue = MAX_COLOR,
  splitRightRed = MAX_COLOR, splitRightGreen = MAX_COLOR, splitRightBlue = MAX_COLOR,
  LeftSideGRed = MAX_COLOR, LeftSideGGreen = MAX_COLOR, LeftSideGBlue = 0,
  RightSideGRed = MAX_COLOR, RightSideGGreen = MAX_COLOR, RightSideGBlue = MAX_COLOR,
  MiddleSideGRed = MAX_COLOR, MiddleSideGGreen = MAX_COLOR, MiddleSideGBlue = MAX_COLOR;

int splashRed = 0;
int splashGreen = 0;
int splashBlue = 0;

int[] presetColors = {
  color(255, 0, 0), // Red
  color(0, 255, 0), // Green
  color(0, 0, 255), // Blue
  color(255, 255, 0), // Yellow
  color(255, 128, 0), // Orange
  color(128, 0, 255), // Purple
  color(255, 0, 255), // Pink
  color(0, 255, 255), // Teal
  color(128, 255, 0), // Lime
  color(0, 255, 128), // Cyan
  color(255, 0, 128), // Magenta
  color(255, 128, 128), // Peach
  color(192, 128, 255), // Lavender
  color(128, 192, 192), // Turquoise
  color(255, 215, 0)   // Gold
};

List<String> colorNames = Arrays.asList("Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink", "Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold");


ControlP5 buildUI()
{
  ControlP5 cp5 = new ControlP5(this);

  addSlider(cp5, "Brightness", "  B", EFFECT_CONTROLS_X, 65, 14, 69, MIN_BRIGHT, MAX_BRIGHT, DEF_BRIGHT, -1, -1, YELLOW, BLACK, RED)
    //.setLabelVisible(false)
    ;


  addSlider( cp5, "FadeOnVal", "  F", EFFECT_CONTROLS_X-15, 65, 14, 69, MIN_FADE_RATE, MAX_FADE_RATE, DEFAULT_FADE_RATE, -1, -1, GREY, BLACK, RED)
    //.setLabelVisible(false)
    ;

  addButton(cp5, "setLeftSideG", "Set LG", 705, 140, 30, 15).hide();
  addButton(cp5, "setMiddleSideG", "Set MG", 735, 140, 30, 15).hide();
  addButton(cp5, "setRightSideG", "Set RG", 765, 140, 30, 15).hide();

  addButton(cp5, "setLeftSide", "Set L", 735, 140, 30, 15).hide();
  addButton(cp5, "setRightSide", "Set R", 765, 140, 30, 15).hide();

  addButton(cp5, "setL", "Set L", 675, 140, 30, 15).hide();
  addButton(cp5, "setM", "Set M", 705, 140, 30, 15).hide();
  addButton(cp5, "setLM", "Set LM", 735, 140, 30, 15).hide();
  addButton(cp5, "setH", "Set H", 765, 140, 30, 15).hide();

  addButton(cp5, "P174", "174 Preset", 305, 25, 60, 15);

  addButton(cp5, "Open", null, 725, 45, 50, 15);
  addButton(cp5, "Refresh", null, 775, 45, 50, 15 );

  addColorWheel(cp5, "Color", EFFECT_CONTROLS_X+15, 45, 100);

  addScrollableList(cp5, "midi", "Midi Device", null, -1, 725, 30, 100, 110, 15, 15)
    .close();
  addScrollableList(cp5, "comlist", "Arduino Port", null, -1, 725, 15, 100, 110, 15, 15)
    .close();

  addButton(cp5, "leftArrow", "<", 380, 25, 30, 15, APP_COLOR_FG, BLUE, APP_COLOR_ACT);
  addButton(cp5, "rightArrow", ">", 415, 25, 30, 15, APP_COLOR_FG, BLUE, APP_COLOR_ACT);
  addButton(cp5, "AdvanceUser", null, 15, 15, 60, 15);

  addScrollableList(cp5, "colorlist", "Color Preset", colorNames, 0, EFFECT_CONTROLS_X+15, 30, 100, 100, 15, 15);
  addScrollableList(cp5, "modelist", "Mode", m, 0, EFFECT_CONTROLS_X+15, 15, 100, 100, 15, 15).bringToFront();

  addToggle(cp5, "BGColor", " BG", 700, 25, 15, 15, RED, WHITE, GREEN);

  int SPLASH_CONTROL_X = EFFECT_CONTROLS_X+15;
  int SPLASH_CONTROL_Y = 60;

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

  int SPLASH_CONTROL_Y_STEP = 13;
  int captionAlignX = CENTER;
  int captionAlignY = ControlP5.TOP_OUTSIDE;

  addSlider( cp5, "splashMaxLength", "Max Splash Length", x, y, SPLASH_MIN_LEN, SPLASH_MAX_LEN, SPLASH_DEFAULT_LEN, captionAlignX, captionAlignY).hide();
  y += SPLASH_CONTROL_Y_STEP;

  List<String> splashColors = Arrays.asList("Full Spectrum", "Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink", "Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold");
  addScrollableList(cp5, "splashColors", "Color", splashColors, 0, EFFECT_CONTROLS_X+15, 30, 100, 100, 15, 15).hide();
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
  }
  if (counter == 1)
  {
    rectASizeX+=60;
    rectBSizeX-=45;
    numberselected = 152;
    firstNoteSelected = 28;
    lastNoteSelected = 103;
  }

  if (counter == 2)
  {
    rectASizeX=+60;
    rectBSizeX-=30;
    numberselected = 146;
    firstNoteSelected = 28;
    lastNoteSelected = 100;
  }
  if (counter == 3)
  {
    rectASizeX= 135;
    rectBSizeX-=30;
    numberselected = 122;
    firstNoteSelected = 36;
    lastNoteSelected = 96;
  }
  if (counter == 4)
  {
    rectASizeX= 135;
    rectBSizeX-=105;
    numberselected = 98;
    firstNoteSelected = 36;
    lastNoteSelected = 84;
  }
  println("Selected number led: " + numberselected);
  println("Selected first note: " + firstNoteSelected);
  println("Selected last note: " + lastNoteSelected);
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
  cp5.getController("modelist").bringToFront();
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
  cl.add(cp5.getController("setMiddleSideG"));
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

void Color()
{
  ColorWheel cw = cp5.get(ColorWheel.class, "Color");
  Red = cw.r();
  Green = cw.g();
  Blue = cw.b();
  println("Colors: RED" + Red + ", GREEN" + Green + ", BLUE" + Blue);
}

List<Controller> getDefaultControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("Red"));
  cl.add(cp5.getController("Green"));
  cl.add(cp5.getController("Blue"));
  cl.add(cp5.getController("FadeOnVal"));
  cl.add(cp5.getController("Color"));
  cl.add(cp5.getController("colorlist"));

  return cl;
}

List<Controller> getLegacyControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("Red"));
  cl.add(cp5.getController("Green"));
  cl.add(cp5.getController("Blue"));
  cl.add(cp5.getController("Color"));
  cl.add(cp5.getController("colorlist"));

  cl.add(cp5.getController("FadeOnVal"));

  cl.add(cp5.getController("setLeftSideG"));
  cl.add(cp5.getController("setRightSideG"));
  cl.add(cp5.getController("setLeftSide"));
  cl.add(cp5.getController("setMiddleSideG"));
  cl.add(cp5.getController("setRightSide"));
  cl.add(cp5.getController("setL"));
  cl.add(cp5.getController("setLM"));
  cl.add(cp5.getController("setM"));
  cl.add(cp5.getController("setH"));
  return cl;
}

List<Controller> getSplashControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("splashMaxLength"));
  cl.add(cp5.getController("FadeOnVal"));
  cl.add(cp5.getController("splashHeadFade"));
  cl.add(cp5.getController("splashColors"));

  //cl.add(cp5.getController("splashVelocityBrightnessImpact"));
  //cl.add(cp5.getController("splashVelocitySpeedImpact"));
  return cl;
}

void setControllersVisible(List<Controller> cl, boolean visible) {
  if ( cl == null) return;
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
  //Piano type
  //PianoLED version tag uncomment when compiling to exe
  // VersionTag = "PianoLED V3.5";
  fill(255);
  text(presetText, 375, 15);
  //text(VersionTag, 15, 150);

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
}
