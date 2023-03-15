void setDefaultDefaults(int fadeRate, int brightness)
{
  cp5.getController("FadeOnVal").setValue(fadeRate);
  cp5.getController("Brightness").setValue(brightness);
  cp5.getController("Brightness").setPosition(EFFECT_CONTROLS_X-4, 65);
}
