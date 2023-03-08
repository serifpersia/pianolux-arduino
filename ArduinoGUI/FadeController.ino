//FadeController.cpp
#include "FadeController.h"
FadeController ::FadeController() {
}

void FadeController ::fade(int fadeRate) {
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i].fadeToBlackBy(fadeRate);
  }
}
