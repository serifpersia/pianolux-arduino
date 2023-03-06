//PianoLED  
//Code by serifpersia 
//Last modified 03/05/2023 
 
#include <FastLED.h>
#include "FadingRunEffect.h"
#include "FadeController.h"

#define NO_HARDWARE_PIN_SUPPORT
#define FASTLED_RMT_MAX_CHANNELS 1

#define NUM_LEDS 176  // how many leds do you want to control 
#define DATA_PIN 5    // your LED strip data pin 
#define MAX_POWER_MILLIAMPS 450 

int BRIGHTNESS = 200;
const unsigned long interval = 30;  // Update interval in milliseconds

unsigned long currentTime = 0;
unsigned long previousTime = 0;
int FADE_RATE = 50;
int TAIL_LEN = 10;
int dir = 1;
int run = 0;

CRGB leds[NUM_LEDS];
boolean fadeMap[NUM_LEDS];
int NOTES = 12;

int getHueForPos(int pos) {
  return pos * 255 / NUM_LEDS;
}

int getNote(int key) {
  return key % NOTES;
}
int getSaturationForPos(int pos) {
  return 255 - pos * 155 / NUM_LEDS;
}

boolean isOnStrip(int pos) {
  return pos >= 0 && pos < NUM_LEDS;
}

void test() {
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CHSV(getHueForPos(i), 255, 255);
    FastLED.show();
    delay(3);
    leds[i] = CHSV(0, 0, 0);
    FastLED.show();
  }
}

void setup() {
  Serial.begin(9600);
  Serial.setTimeout(10);
  FastLED.addLeds<WS2812B, DATA_PIN>(leds, NUM_LEDS);              // GRB ordering
  FastLED.setMaxPowerInVoltsAndMilliamps(5, MAX_POWER_MILLIAMPS);  // set power limit
  FastLED.setBrightness(BRIGHTNESS);
  test();
}

#define MAX_EFFECTS 128

FadingRunEffect* effects[MAX_EFFECTS];
int numEffects = 0;

// Add a new effect
int addEffect(FadingRunEffect* effect) {
  if (numEffects < MAX_EFFECTS) {
    effects[numEffects] = effect;
    numEffects++;
  }
  return;
}

// Remove an effect
void removeEffect(FadingRunEffect* effect) {
  for (int i = 0; i < numEffects; i++) {
    if (effects[i] == effect) {
      // Shift the remaining effects down
      for (int j = i; j < numEffects - 1; j++) {
        effects[j] = effects[j + 1];
      }
      numEffects--;
      break;
    }
  }
}
int redVal = 255;
int greenVal = 255;
int blueVal = 255;
int brightVal = 127;
int fadeOnVal = 70;
int num = 0;
bool setColors = false;
bool setBright = false;
bool ToggleRGBAnimation = false;
bool FadeOn = false;
bool receiveNotes = false;
int colorSetCounter = 0;
int brightnessSetCounter = 0;
int inByte;

uint8_t hue = 0;

boolean debug = false;
FadeController* fadeCtrl = new FadeController(FADE_RATE);
void loop() {
  boolean receiveNotes = false;
  boolean receiveVelocity = false;
  boolean receiveNotesFR = false;
  boolean receiveVelocityFR = false;
  currentTime = millis();

  int note = 0;
  int velocity = 0;
  if (currentTime - previousTime >= interval) {
    while (Serial.available() > 0) {
      inByte = Serial.read();
      FastLED.show();
      if (debug) leds[0] = leds[0].Azure;
      if (inByte == 249) {
        if (debug) leds[1] = leds[0].Azure;
        receiveVelocityFR = true;
      } else if (inByte == 254) {
        fill_solid(leds, NUM_LEDS, CRGB::Black);
      } else if (receiveVelocityFR) {
        if (debug) leds[2] = leds[0].Azure;
        velocity = inByte;
        receiveVelocityFR = false;
        receiveNotesFR = true;
      } else if (receiveNotesFR) {
        if (debug) leds[3] = leds[0].Azure;
        note = inByte;
        receiveVelocityFR = false;
        receiveNotesFR = false;
        addEffect(new FadingRunEffect(TAIL_LEN, note, 0, 0, FADE_RATE, velocity));
      } else if (inByte == 255) {
        setColors = true;
        colorSetCounter = 0;
      } else if (inByte == 254) {
        ToggleRGBAnimation = false;
        FadeOn = false;
        fill_solid(leds, NUM_LEDS, CRGB::Black);
        FastLED.show();
      } else if (inByte == 253) {
        ToggleRGBAnimation = true;
        for (int i = 0; i < NUM_LEDS; i++) {
          leds[i] = CHSV(hue, 255, 255);
        }
        EVERY_N_MILLISECONDS(15) {
          hue++;
        }
        FastLED.show();
      } else if (inByte == 252) {
        FadeOn = true;
      } else if (setColors) {
        switch (colorSetCounter) {
          case 0:
            redVal = inByte;
            break;
          case 1:
            greenVal = inByte;
            break;
          case 2:
            FastLED.setBrightness(inByte);
            break;
          case 3:
            fadeOnVal = inByte;
            break;
          case 4:
            blueVal = inByte;
            setColors = false;
            receiveNotes = true;
            break;
        }
        colorSetCounter++;
      } else if (receiveNotes) {
        controlLeds(inByte);
      }
    }

    for (int i = 0; i < numEffects; i++) {
      if (effects[i]->finished()) {
        delete effects[i];
        removeEffect(effects[i]);
      } else {
        effects[i]->nextStep();
      }
    }

    fadeCtrl->fade();
    FastLED.show();
    previousTime = currentTime;
  }
}


void controlLeds(int note) {
  note -= 1;
  if (!ToggleRGBAnimation && !leds[note]) {
    leds[note].setRGB(redVal, greenVal, blueVal);
  } else if (FadeOn) {
    CRGB currentColor = leds[note];  // get current color of LED
    for (int fadeValue = brightVal; fadeValue >= 0; fadeValue -= fadeOnVal) {
      // Scale down the current color by the fade value
      currentColor.fadeToBlackBy(fadeValue);
      leds[note] = currentColor;
      FastLED.show();
    }
    leds[note] = CRGB::Black;
    FastLED.show();
  } else {
    leds[note] = CRGB::Black;
  }
  FastLED.show();
}
