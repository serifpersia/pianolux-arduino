//FadeController.cpp
#include "FadeController.h"
FadeController ::FadeController() {
}

// Set the duration of the transition and the number of steps
int transitionTime = 1000;  // in milliseconds
int steps = 100;

void FadeController ::fade(int fadeRate) {
  for (int i = 0; i < NUM_LEDS; i++) {
    if (bgColor == CRGB(0)) {
      leds[i].fadeToBlackBy(fadeRate);
    } else {
      if (leds[i] != bgColor) {
        nblend(leds[i], bgColor, fadeRate+30);
        if (distance(leds[i], bgColor) < 5) {
          leds[i] = bgColor;
        }
      }
    }
  }
}
