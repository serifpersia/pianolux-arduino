void splashMaxLength(float value)
{
  sendCommandSplashMaxLength((int)value);
}

void setSplashDefaults(int splashLen, int fadeRate)
{
  cp5.getController("splashMaxLength").setValue(splashLen);
  cp5.getController("FadeOnVal").setValue(fadeRate);
}

  
