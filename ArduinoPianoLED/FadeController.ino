//FadeController.cpp
#include "FadeController.h"
FadeController ::FadeController() {
}

// Set the duration of the transition and the number of steps
int transitionTime = 1000;  // in milliseconds
int steps = 100;
int BG_FADE_OFFSET = 100;

void FadeController ::fade(int fadeRate) {
  for (int i = 0; i < NUM_LEDS; i++) {
    int effectiveSplashRate = fadeRate;
    if (keysOn[i]) {
      if (MODE == COMMAND_SPLASH) effectiveSplashRate = 1;
      else effectiveSplashRate = 0;
    }

    if (effectiveSplashRate > 0) {
      if (bgColor == CRGB(0)) {
        leds[i].fadeToBlackBy(effectiveSplashRate);
      } else {
        if (leds[i] != bgColor) {
          nblend(leds[i], bgColor, effectiveSplashRate + BG_FADE_OFFSET);
          if (distance(leds[i], bgColor) < 5) {
            leds[i] = bgColor;
          }
        }
      }
    }
  }
}
