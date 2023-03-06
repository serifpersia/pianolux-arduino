//FadeController.cpp
#include "FadeController.h"
FadeController ::FadeController(int fadeRate) {
  this->fadeRate = fadeRate;
}

void FadeController ::fade() {
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i].fadeToBlackBy(fadeRate);
  }
}
