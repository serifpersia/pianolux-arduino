void splashMaxLength(float value)
{
  sendCommandSplashMaxLength((int)value);
}

void setSplashDefaults(int splashLen, int fadeRate, int splashColor)
{
  cp5.getController("splashMaxLength").setValue(splashLen);
  cp5.getController("FadeOnVal").setValue(fadeRate);
  cp5.get(ScrollableList.class, "splashColor");
  
  
}
