//FadingRunEffect.h
#ifndef FadingRunEffect_h
#define FadingRunEffect_h

#include <Arduino.h>

class FadingRunEffect {

public:
  FadingRunEffect(int effectLen, int startPosition, int hue, int saturation, int fadeRate, int velocity);
  void nextStep();
  boolean finished();
  int getSaturation(int velocity);
  int getBrightness(int velocity);
  void setHeadLED(int step);
  int getSteps();
  int adjustValue(int value, int lowerThreshold, int maxValue);

private:
  int effectLen;
  int startPosition;
  int step;
  int hue;
  int saturation;
  int velocity;
  int fadeRate;
  unsigned long lastUpdate;
};

#endif
