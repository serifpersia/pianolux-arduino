//FadeController.cpp
#include "FadeController.h"
FadeController ::FadeController() {
}

// Set the duration of the transition and the number of steps
int transitionTime = 1000;  // in milliseconds
int steps = 100;
int BG_FADE_OFFSET = 0;

void FadeController ::fade(int fadeRate) {
  for (int i = 0; i < NUM_LEDS; i++) {
    int effectiveSplashRate = fadeRate;
    if (keysOn[i]) {
      if (MODE == COMMAND_SPLASH) effectiveSplashRate = 1;
      else effectiveSplashRate = 0;
    }
    int ledNo = ledNum(i);
    if (effectiveSplashRate > 0) {
      if (bgColor == CRGB(0)) {
        leds[ledNo].fadeToBlackBy(effectiveSplashRate);
      } else {
        if (leds[ledNo] != bgColor) {
          nblend(leds[ledNo], bgColor, effectiveSplashRate + BG_FADE_OFFSET);
          if (distance(leds[ledNo], bgColor) < 5) {
            leds[ledNo] = bgColor;
          }
        }
      }
    }
  }
}
