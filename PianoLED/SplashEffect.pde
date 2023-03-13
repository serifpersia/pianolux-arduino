void splashMaxLength(float value)
{
  sendCommandSplashMaxLength((int)value);
}

void setSplashDefaults(int splashLen, int fadeRate, int splashColor,int brightness)
{
  cp5.getController("splashMaxLength").setValue(splashLen);
  cp5.getController("FadeOnVal").setValue(fadeRate);
  cp5.get(ScrollableList.class, "splashColors").setValue(splashColor);
  cp5.getController("Brightness").setValue(brightness);
}
