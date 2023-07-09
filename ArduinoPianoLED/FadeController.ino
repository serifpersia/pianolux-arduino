
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


/*
//FadeController.cpp
#include "FadeController.h"
FadeController ::FadeController() {
}

// Set the duration of the transition and the number of steps
int transitionTime = 1000;  // in milliseconds
int steps = 100;
int BG_FADE_OFFSET = 0;
int FadescalePattern[7];
int FadestartingIndex = scaleKeyIndex;

  void
  FadeController::fade(int fadeRate) {
  for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
    int octaveOffset = octave * (OCTAVE_LEDS + LED_SKIP);

    // Apply offsets for specific octaves
    if (octave == 3) {
      octaveOffset -= LED_OFFSET_4TH_OCTAVE;
    } else if (octave == 4) {
      octaveOffset -= LED_OFFSET_5TH_OCTAVE;
    } else if (octave == 5) {
      octaveOffset -= LED_OFFSET_6TH_OCTAVE;
    } else if (octave == 6) {
      octaveOffset -= LED_OFFSET_7TH_OCTAVE;
    } else if (octave == 7) {
      octaveOffset -= LED_OFFSET_8TH_OCTAVE;
    }

    for (int i = 0; i < 7; i++) {
      int ledIndex = octaveOffset + FadescalePattern[i] + FadestartingIndex;

      // Display only the first LED for the eighth octave
      if (octave == 7 && i > 0) {
        break;
      }

      if (ledIndex < NUM_LEDS) {
        int effectiveSplashRate = fadeRate;
        if (keysOn[ledIndex]) {
          if (MODE == COMMAND_SPLASH)
            effectiveSplashRate = 1;
          else
            effectiveSplashRate = 0;
        }

        if (effectiveSplashRate > 0) {
          if (bgColor == CRGB(0)) {
            leds[ledIndex].fadeToBlackBy(effectiveSplashRate);
          } else {
            if (leds[ledIndex] != bgColor) {
              nblend(leds[ledIndex], bgColor, effectiveSplashRate + BG_FADE_OFFSET);
              if (distance(leds[ledIndex], bgColor) < 5) {
                leds[ledIndex] = bgColor;
              }
            }
          }
        }
      }
    }
  }
}
*/