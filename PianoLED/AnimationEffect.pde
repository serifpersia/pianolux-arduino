void setAnimationDefaults(int fadeRate, int brightness)
{
  cp5.getController("FadeOnVal").setValue(fadeRate);
  cp5.getController("Brightness").setValue(brightness);
}
