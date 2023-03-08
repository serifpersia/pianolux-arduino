//FadingRunEffect.cpp
#include "FadingRunEffect.h"
FadingRunEffect ::FadingRunEffect(int effectLen, int startPosition, int hue, int saturation, int headFadeRate, int velocity) {
  // Initialize the effect parameters
  this->effectLen = effectLen;
  this->startPosition = startPosition;
  this->step = 0;
  this->hue = hue;
  this->saturation = saturation;
  this->headFadeRate = headFadeRate;
  this->lastUpdate = millis();
  this->velocity = velocity;
}

int MAX_VALUE = 255;
int LOWEST_BRIGHTNESS = 50;
int LOWEST_SATURATION = 100;
int SPLASH_VELOCITY_COEFF = 1.5;

int FadingRunEffect ::getSaturation(int velocity) {
  return adjustValue(velocity, LOWEST_SATURATION, MAX_VALUE);
}

int FadingRunEffect ::getBrightness(int velocity) {
  return adjustValue(velocity, LOWEST_BRIGHTNESS, MAX_VALUE);
}

int FadingRunEffect ::adjustValue(int value, int lowerThreshold, int maxValue) {
  return lowerThreshold + (value * (maxValue - lowerThreshold) / maxValue);
}

int calcOffset(int step, int velocity)
{
  int offset = step*velocity/MAX_VELOCITY*SPLASH_VELOCITY_COEFF;
  if( offset > step)
  {
    return step;
  }
  else
  {
    return offset;
  }
}

void FadingRunEffect ::setHeadLED(int step) {
  if (step > effectLen) {
    return;
  }
  int pos1 = this->startPosition + calcOffset(step, velocity);
  int pos2 = this->startPosition - calcOffset(step, velocity);
  if (isOnStrip(pos1)) {
    leds[pos1] += CHSV(getHueForPos(startPosition), getSaturation(velocity), getBrightness(velocity));
    leds[pos1].fadeToBlackBy(headFadeRate);
  }

  if (pos1 != pos2 && isOnStrip(pos2)) {
    leds[pos2] += CHSV(getHueForPos(startPosition), getSaturation(velocity), getBrightness(velocity));
    leds[pos2].fadeToBlackBy(headFadeRate);
  }
}

// Calculate the color of the LED at a given position
void FadingRunEffect ::nextStep() {
  setHeadLED(this->step);
  this->step++;
}
int FadingRunEffect ::getSteps() {
  return effectLen;
}

boolean FadingRunEffect ::finished() {
  return step > getSteps();
}