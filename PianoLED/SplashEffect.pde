void splashMaxLength(float value)
{
  sendCommandSplashMaxLength((int)value);
}

void setSplashDefaults(int splashLen, int fadeRate, int splashColor,int brightness)
{
  cp5.getController("splashMaxLength").setValue(splashLen);
  cp5.getController("FadeOnVal").setValue(fadeRate);
  cp5.get(ScrollableList.class, "splashColor").setValue(splashColor);
  cp5.getController("Brightness").setValue(brightness);
}

void splashColor(int n)
{
  int first = 0;
  int last = splashColorNames.size()-1;
  if (n > first && n < last) {
    cp5.get(ColorWheel.class, "Color").setRGB(presetColors[n-1]);
  }
}

color getSplashColor() {
  int n = (int)cp5.get(ScrollableList.class, "splashColor").getValue();
  int first = 0;
  color splashColor;
  int last = splashColorNames.size()-1;
  if (n == first) {
    // Full Spectrum mode
    splashColor = color(0,0,0);
    println("Selected color: Full Spectrum");
  } else if (n > first && n < last) {
    // Preset color mode
    splashColor = presetColors[n-1];
    println("Selected color: " + colorNames.get(n-1));
  } else if (n == last) {
    //Manual
    splashColor = cp5.get(ColorWheel.class, "Color").getRGB();
  } else {
    // Invalid color mode
    println("Invalid color selection: " + n);
    return 0;
  }
  return splashColor;
}
