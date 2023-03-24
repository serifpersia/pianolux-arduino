ControlP5 cp5;

int MIN_FADE_RATE = 0;
int MAX_FADE_RATE = 255;
int DEFAULT_FADE_RATE = 255;

color RED = color(255, 0, 0);
color DARK_GREEN = color(0, 200, 0);
color GREEN = color(0, 255, 0);
color LAVENDER = color(192, 128, 255);
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
int DEF_BRIGHT = 127;

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
List<String> splashColorNames = Arrays.asList("Full Spectrum", "Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink", "Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold", "Manual");
List<String> animationNames = Arrays.asList("RainbowColors", "RainbowStripeColor", "OceanColors", "CloudColors", "LavaColors", "ForestColors", "PartyColors");



ControlP5 buildUI()
{
  ControlP5 cp5 = new ControlP5(this);

  addSlider(cp5, "Brightness", "  B", EFFECT_CONTROLS_X-4, 65, 10, 69, MIN_BRIGHT, MAX_BRIGHT, DEF_BRIGHT, BLUE, BLACK, RED)
    //.setLabelVisible(false)
    ;


  addSlider( cp5, "FadeOnVal", "  F", EFFECT_CONTROLS_X-15, 65, 10, 69, MIN_FADE_RATE, MAX_FADE_RATE, DEFAULT_FADE_RATE, DARK_GREEN, BLACK, RED)
    //.setLabelVisible(false)
    ;

  addButton(cp5, "CheckForUpdate", "Update", 620, 20, 45, 25);

  addButton(cp5, "setLeftSideG", "Set LG", 705, 140, 30, 15).hide();
  addButton(cp5, "setMiddleSideG", "Set MG", 735, 140, 30, 15).hide();
  addButton(cp5, "setRightSideG", "Set RG", 765, 140, 30, 15).hide();

  addButton(cp5, "setLeftSide", "Set L", 735, 140, 30, 15).hide();
  addButton(cp5, "setRightSide", "Set R", 765, 140, 30, 15).hide();

  addButton(cp5, "setBG", "Set BG", 670, 26, 30, 15).hide();

  addButton(cp5, "LoadMidi", "Load Midi File", EFFECT_CONTROLS_X+15, 45, 60, 15).hide();
  addButton(cp5, "StopMidi", "Stop Midi File", EFFECT_CONTROLS_X+15, 60, 60, 15).hide();

  addButton(cp5, "Open", null, 725, 45, 50, 15);
  addButton(cp5, "Refresh", null, 775, 45, 50, 15 );

  addColorWheel(cp5, "Color", EFFECT_CONTROLS_X+15, 45, 100);

  addAnimationControls(cp5);

  addScrollableList(cp5, "midiout", "Midi Output Device", null, -1, EFFECT_CONTROLS_X+15, 30, 100, 110, 15, 15);
   // .close();

  addScrollableList(cp5, "midi", "Midi Device", null, -1, 725, 30, 100, 110, 15, 15)
    .close();
  addScrollableList(cp5, "comlist", "Arduino Port", null, -1, 725, 15, 100, 110, 15, 15)
    .close();

  addButton(cp5, "leftArrow", "<", 380, 25, 30, 15, APP_COLOR_FG, BLUE, APP_COLOR_ACT);
  addButton(cp5, "rightArrow", ">", 415, 25, 30, 15, APP_COLOR_FG, BLUE, APP_COLOR_ACT);
  //  addButton(cp5, "AdvanceUser", null, 15, 15, 60, 15);

  addScrollableList(cp5, "colorlist", "Color Preset", colorNames, 0, EFFECT_CONTROLS_X+15, 30, 100, 100, 15, 15);
  addScrollableList(cp5, "modelist", "Mode", m, 0, EFFECT_CONTROLS_X+15, 15, 100, 100, 15, 15).bringToFront();


  addToggle(cp5, "BGColor", " BG", 700, 25, 15, 15, RED, WHITE, GREEN);
  addToggle(cp5, "stripDirection", "Reverse", 425, 42, 10, 8, RED, WHITE, GREEN).getCaptionLabel().alignX(ControlP5.CENTER);
  addToggle(cp5, "Fix", "Fix LED", 390, 42, 10, 8, RED, WHITE, GREEN).getCaptionLabel().alignX(ControlP5.CENTER);

  int SPLASH_CONTROL_X = EFFECT_CONTROLS_X+6;
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

  addSlider( cp5, "splashMaxLength", "  L", EFFECT_CONTROLS_X+7, 65, 10, 69, SPLASH_MIN_LEN, SPLASH_MAX_LEN, SPLASH_DEFAULT_LEN, LAVENDER, BLACK, RED).hide();
  y += SPLASH_CONTROL_Y_STEP;

  addScrollableList(cp5, "splashColor", "Color", splashColorNames, 0, EFFECT_CONTROLS_X+15, 30, 100, 100, 15, 15).hide();
  y += SPLASH_CONTROL_Y_STEP;

  addSlider( cp5, "splashTailFade", "Tail Fade Rate", x, y, SPLASH_MIN_TAIL_FADE, SPLASH_MAX_TAIL_FADE, SPLASH_DAFAULT_TAIL_FADE).hide();
  y += SPLASH_CONTROL_Y_STEP;

  //addSlider( cp5, "splashHeadFade", "Head Fade Rate", x, y, SPLASH_MIN_HEAD_FADE, SPLASH_MAX_HEAD_FADE, SPLASH_DAFAULT_HEAD_FADE).hide();
  //y += SPLASH_CONTROL_Y_STEP;

  addSlider( cp5, "splashVelocityBrightnessImpact", "Velocity To Bright", x, y, SPLASH_MIN_VELO_BRI, SPLASH_MAX_VELO_BRI, SPLASH_DAFAULT_VELO_BRI).hide();
  y += SPLASH_CONTROL_Y_STEP;

  addSlider( cp5, "splashVelocitySpeedImpact", "Velocity To Speed", x, y, SPLASH_MIN_VELO_SPEED, SPLASH_MAX_VELO_SPEED, SPLASH_DAFAULT_VELO_SPEED).hide();
  y += SPLASH_CONTROL_Y_STEP;
}

void addAnimationControls(ControlP5 cp5)
{
  addScrollableList(cp5, "animationlist", "Animations", animationNames, 0, EFFECT_CONTROLS_X+15, 30, 100, 100, 15, 15).hide();
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

void hideAllControls()
{
  hideBGControls();
  hideDefaultControls();
  hideSplashControls();
  hideRandomControls();
  hideGradientControls();
  hideVelocityControls();
  hideSplitControls();
  hideAnimationControls();
  hideLearnMidiControls();
}

//BG Controls
void showBGControls()
{
  setControllersVisible(getBGControllers(), true);
}
void hideBGControls()
{
  setControllersVisible(getBGControllers(), false);
}
//Default Controls
void showDefaultControls()
{
  setControllersVisible(getDefaultControllers(), true);
}
void hideDefaultControls()
{
  setControllersVisible(getDefaultControllers(), false);
}
//Splash Controls
void showSplashControls()
{
  setControllersVisible(getSplashControllers(), true);
  cp5.getController("modelist").bringToFront();
}

void hideSplashControls()
{
  setControllersVisible(getSplashControllers(), false);
}
//Random Controls
void showRandomControls()
{
  setControllersVisible(getRandomControllers(), true);
}

void hideRandomControls()
{
  setControllersVisible(getRandomControllers(), false);
}
//Gradient Controls
void showGradientControls()
{
  setControllersVisible(getGradinetControllers(), true);
}
void hideGradientControls()
{
  setControllersVisible(getGradinetControllers(), false);
}
//Velocity Controls
void showVelocityControls()
{
  setControllersVisible(getVelocityControllers(), true);
}
void hideVelocityControls()
{
  setControllersVisible(getVelocityControllers(), false);
}
//Split Controls
void showSplitControls()
{
  setControllersVisible(getSplitControllers(), true);
}
void hideSplitControls()
{
  setControllersVisible(getSplitControllers(), false);
}
//Animation Controls
void showAnimationControls()
{
  setControllersVisible(getAnimationControllers(), true);
}
void hideAnimationControls()
{
  setControllersVisible(getAnimationControllers(), false);
}

//LearnMidi Controls
void showLearnMidiControls()
{
  setControllersVisible(getLearnMidiControllers(), true);
}
void hideLearnMidiControls()
{
  setControllersVisible(getLearnMidiControllers(), false);
}

void Color(color rgb)
{
  Red = (int)red(rgb);
  Green = (int)green(rgb);
  Blue = (int)blue(rgb);
  cp5.get(ScrollableList.class, "splashColor").setValue(splashColorNames.size()-1);
  println("Colors: RED" + Red + ", GREEN" + Green + ", BLUE" + Blue);
}

//BG List
List<Controller> getBGControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("setBG"));

  return cl;
}
//Default List
List<Controller> getDefaultControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("FadeOnVal"));
  cl.add(cp5.getController("Color"));
  cl.add(cp5.getController("colorlist"));

  return cl;
}
//Splash List
List<Controller> getSplashControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();
  cl.add(cp5.getController("splashMaxLength"));
  cl.add(cp5.getController("FadeOnVal"));
  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("splashHeadFade"));
  cl.add(cp5.getController("splashColor"));
  cl.add(cp5.getController("Color"));

  //cl.add(cp5.getController("splashVelocityBrightnessImpact"));
  //cl.add(cp5.getController("splashVelocitySpeedImpact"));
  return cl;
}
//Random List
List<Controller> getRandomControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();

  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("FadeOnVal"));
  return cl;
}
//Gradient List
List<Controller> getGradinetControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();

  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("FadeOnVal"));
  cl.add(cp5.getController("colorlist"));
  cl.add(cp5.getController("Color"));
  cl.add(cp5.getController("setLeftSideG"));
  cl.add(cp5.getController("setMiddleSideG"));
  cl.add(cp5.getController("setRightSideG"));

  return cl;
}
//Velocity List
List<Controller> getVelocityControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();

  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("FadeOnVal"));

  return cl;
}

//Split List
List<Controller> getSplitControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();

  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("FadeOnVal"));

  cl.add(cp5.getController("colorlist"));
  cl.add(cp5.getController("Color"));

  cl.add(cp5.getController("setLeftSide"));
  cl.add(cp5.getController("setRightSide"));
  return cl;
}
//Animation List
List<Controller> getAnimationControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();

  cl.add(cp5.getController("Brightness"));
  cl.add(cp5.getController("animationlist"));

  return cl;
}
//LearnMidi List
List<Controller> getLearnMidiControllers()
{
  if (cp5 == null) return null;

  List<Controller> cl = new ArrayList<>();

  cl.add(cp5.getController("LoadMidi"));
  cl.add(cp5.getController("StopMidi"));
  
  cl.add(cp5.getController("midiout"));



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
  String VersionAppTag = "PianoLED: " + "v3.7";
  fill(255);
  text(presetText, 375, 15);
  text(VersionAppTag, 15, 150);

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
  // highlight piano size L&R boxes
  fill(0, 127);
  rect(15, 64, rectASizeX, 70);
  rect(rectBX, 64, rectBSizeX, 70);
  // block led strip with black color
  fill(0);
  rect(15, 54, rectASizeX, 10);
  rect(rectBX, 54, rectBSizeX, 10);
}
