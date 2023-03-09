
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


//Buttons
Button addButton(ControlP5 cp5, String name, String label, int x, int y, int w, int h)
{
  return addButton(cp5, name, label, x, y, w, h, APP_COLOR_FG, APP_COLOR_BG, APP_COLOR_ACT);
}

Button addButton(ControlP5 cp5, String name, String label, int x, int y, int w, int h, color fg, color bg, color act)
{
  Button b = cp5.addButton(name)
    .setPosition(x, y);

  b.setColorForeground(fg);
  b.setColorBackground(bg);
  b.setColorActive(act);

  if ( x > 0 && y > 0 ) b.setSize(w, h);
  if ( label != null ) b.setLabel(label);

  return b;
}

// SCrollable List
ScrollableList addScrollableList(ControlP5 cp5, String name, String label, List items, int defItem, int x, int y, int w, int h, int barH, int itemH)
{
  return addScrollableList(cp5, name, label, items, defItem, x, y, w, h, barH, itemH, APP_COLOR_FG, APP_COLOR_BG, APP_COLOR_ACT);
}

ScrollableList addScrollableList(ControlP5 cp5, String name, String label, List items, int defItem, int x, int y, int w, int h, int barH, int itemH, color fg, color bg, color act)
{
  ScrollableList l = cp5.addScrollableList(name)
    .setPosition(x, y);

  if ( items != null) l.addItems(items);

  if ( barH > 0) l.setBarHeight(barH);
  if ( itemH > 0) l.setItemHeight(itemH);

  if ( defItem < 0) defItem = 0;
  l.setValue(defItem);

  l.setColorForeground(fg);
  l.setColorBackground(bg);
  l.setColorActive(act);

  if ( x > 0 && y > 0 ) l.setSize(w, h);
  if ( label != null ) l.setLabel(label);

  return l;
}

//Slider
Slider addSlider( ControlP5 cp5, String name, String label, int x, int y, float min, float max, float def)
{
  return addSlider( cp5, name, label, x, y, min, max, def, -1, -1);
}

Slider addSlider( ControlP5 cp5, String name, String label, int x, int y, float min, float max, float def, int alignX, int alignY)
{
  return addSlider( cp5, name, label, x, y, 0, 0, min, max, def, alignX, alignY, SLIDER_COLOR_FG, SLIDER_COLOR_BG, SLIDER_COLOR_ACT);
}

Slider addSlider( ControlP5 cp5, String name, String label, int x, int y, int h, int w, float min, float max, float def, int alignX, int alignY, color fg, color bg, color act)
{
  Slider s = cp5.addSlider(name)
    .setCaptionLabel(label)
    .setPosition(x, y)
    .setRange(min, max)
    .setValue(def);

  s.setColorForeground(fg);
  s.setColorBackground(bg);
  s.setColorActive(act);

  if ( h > 0 && w >= 0 ) s.setSize(h, w);

  if ( alignX >= 0 && alignY >= 0 )
  {
    s.getCaptionLabel().align(alignX, alignY).setPaddingY(1);
  }
  return s;
}
