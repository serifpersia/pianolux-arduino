/*
  PianoLux is an open-source project that aims to provide MIDI-based LED control to the masses.
  It is developed by a one-person team, yours truly, known as serifpersia, or Scarlett.

  If you modify this code and redistribute the PianoLux project, please ensure that you
  don't remove this disclaimer or appropriately credit the original author of the project
  by linking to the project's source on GitHub: github.com/serifpersia/pianoiled-arduino/
  Failure to comply with these terms would constitute a violation of the project's
  MIT license under which PianoLux is released.

  Copyright © 2023 Serif Rami, also known as serifpersia.

  Contributors: hasimov, also known as Arthist.

*/
//PianoLux

#include <FastLED.h>
#include "FadingRunEffect.h"
#include "FadeController.h"

#define IS_DUE 0  // Set to 1 if using Arduino Due, otherwise 0

#if IS_DUE
#define SERIAL_PORT SerialUSB
#else
#define SERIAL_PORT Serial
#endif

#define MAX_NUM_LEDS 176          // how many leds do you want to control
#define DATA_PIN 5               // your LED strip data pin use pwm ones symbol ~ next to pin hole on Arduino board
#define MAX_POWER_MILLIAMPS 450   //define current limit if you are using 5V pin from Arduino dont touch this

int NUM_LEDS = 176;               // how many leds do you want to control

int STRIP_DIRECTION = 0;  //0 - left-to-right
const int MAX_VELOCITY = 128;



CRGB globalColor = CRGB::Red;

//COMMAND_SET_COLOR
const byte COMMAND_BYTE1 = 0;
const byte COMMAND_BYTE2 = 1;

const byte COMMAND_BLACKOUT = 2;
const byte COMMAND_SET_BRIGHTNESS = 3;
const byte COMMAND_SET_GLOBAL_COLOR = 4;

const byte COMMAND_FADE_RATE = 5;
const byte COMMAND_STRIP_DIRECTION = 6;

const byte COMMAND_NOTE_ON_DEFAULT = 7;
const byte COMMAND_NOTE_OFF = 8;

const byte COMMAND_SPLASH = 9;
const byte COMMAND_SPLASH_MAX_LENGTH = 10;

const byte COMMAND_VELOCITY = 11;

const byte COMMAND_ANIMATION = 12;

const byte COMMAND_SET_BG = 13;
const byte COMMAND_SET_GUIDE = 14;

const byte COMMAND_SET_LED_VISUALIZER = 15;
const byte COMMAND_NOTE_ON = 16;

byte  MODE;

long react = 0;
int bufferSize;
int buffer[10];  // declare buffer as an array of 10 integers
int bufIdx = 0;  // initialize bufIdx to zero
int generalBrightness = buffer[++bufIdx];
int animationIndex;
int selectedEffect;
int colorHue = 0;

int DEFAULT_BRIGHTNESS = 255;

int SPLASH_HEAD_FADE_RATE = 5;
int splashMaxLength = 8;

boolean bgOn = false;
CRGB bgColor = CRGB::Black;
CRGB guideColor = CRGB::Black;


unsigned long currentTime = 0;
unsigned long previousTime = 0;
unsigned long previousFadeTime = 0;

unsigned long interval = 20;      // general refresh in milliseconds
unsigned long fadeInterval = 20;  // general fade interval in milliseconds
int generalFadeRate = 255;          // general fade rate, bigger value - quicker fade (configurable via App)


const int OCTAVE_LEDS = 23;           // Number of LEDs in one octave
const int OCTAVE_COUNT = 8;           // Number of octaves
const int LED_SKIP = 1;               // Number of LEDs to skip before starting the next octave pattern

int scalePatternLength;
int scalePattern[12];

int scaleKeyIndex;
const int majorScale[] = { 0, 4, 8, 10, 14, 18, 21 };


//Animation select variables
uint8_t hue = 0;

#define UPDATES_PER_SECOND 100

CRGBPalette16 currentPalette;
TBlendType currentBlending;

extern CRGBPalette16 myRedWhiteBluePalette;
extern const TProgmemPalette16 myRedWhiteBluePalette_p PROGMEM;


boolean keysOn[MAX_NUM_LEDS];

CRGB leds[MAX_NUM_LEDS];
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

void StartupAnimation() {
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CHSV(getHueForPos(i), 255, 255);
    FastLED.show();
    leds[i] = CHSV(0, 0, 0);
  }
  FastLED.show();
}

void setup() {

  SERIAL_PORT.begin(115200);

#if IS_DUE
  while (!SERIAL_PORT);
#endif

  SERIAL_PORT.setTimeout(10);

  FastLED.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);  // GRB ordering
  // FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2812, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2852, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2812B, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2811, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2813, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<APA104, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2811_400, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2801, DATA_PIN, CLOCK_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2803, DATA_PIN, CLOCK_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<SK6812, DATA_PIN, RGB>(leds, NUM_LEDS);          // GRB ordering is typical
  FastLED.setMaxPowerInVoltsAndMilliamps(5, MAX_POWER_MILLIAMPS);  // set power limit
  StartupAnimation();
}

#define MAX_EFFECTS 128

FadingRunEffect* effects[MAX_EFFECTS];
int numEffects = 0;

// Add a new effect
void addEffect(FadingRunEffect* effect) {
  if (numEffects < MAX_EFFECTS) {
    effects[numEffects] = effect;
    numEffects++;
  }
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
void setBG(CRGB colorToSet) {

  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = colorToSet;
  }
  bgColor = colorToSet;
  FastLED.show();
}

void setGuide(CRGB guideColorToSet, int startingIndex, const int scalePattern[], int scalePatternLength) {
  for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
    int octaveOffset = octave * (OCTAVE_LEDS + LED_SKIP);

    for (int i = 0; i < scalePatternLength; i++) {
      int ledIndex = octaveOffset + scalePattern[i] + startingIndex;

      // Display only the first LED for the eighth octave
      if (octave == 7 && i > 0) {
        break;
      }

      if (ledIndex < NUM_LEDS) {
        leds[ledIndex] = guideColorToSet;
      }
    }
  }

  guideColor = guideColorToSet;
  FastLED.show();
}

FadeController* fadeCtrl = new FadeController();
//Main loop
void loop() {

  byte bufferSize = SERIAL_PORT.available();
  byte buffer[bufferSize];
  SERIAL_PORT.readBytes(buffer, bufferSize);

  boolean commandByte1Arrived = false;
  boolean commandByte2Arrived = false;
  for (int bufIdx = 0; bufIdx < bufferSize; bufIdx++) {
    byte command = buffer[bufIdx];
    switch (command) {
      case COMMAND_BYTE1:
        {
          commandByte1Arrived = true;
          break;
        }
      case COMMAND_BYTE2:
        {
          if (commandByte1Arrived) {
            commandByte2Arrived = true;
          }
          commandByte1Arrived = false;
          break;
        }
      case COMMAND_BLACKOUT:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          fill_solid(leds, NUM_LEDS, bgColor);
          MODE = COMMAND_BLACKOUT;
          break;
        }
      case COMMAND_SET_BRIGHTNESS:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          generalBrightness = buffer[++bufIdx];
          FastLED.setBrightness(generalBrightness);
          break;
        }
      case COMMAND_SET_GLOBAL_COLOR:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          int r = (int)buffer[++bufIdx];
          int g = (int)buffer[++bufIdx];
          int b = (int)buffer[++bufIdx];

          globalColor.setRGB(r, g, b);
          MODE = COMMAND_SET_GLOBAL_COLOR;
          break;
        }
      case COMMAND_FADE_RATE:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          generalFadeRate = buffer[++bufIdx];
          break;
        }
      case COMMAND_STRIP_DIRECTION:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          STRIP_DIRECTION = buffer[++bufIdx];
          NUM_LEDS = buffer[++bufIdx];
          break;
        }
      case COMMAND_NOTE_ON_DEFAULT:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          int note = (int)buffer[++bufIdx];
          keysOn[note - 1] = true;
          controlLeds(note - 1, globalColor);
          MODE = COMMAND_NOTE_ON_DEFAULT;
          break;
        }
      case COMMAND_NOTE_ON:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          byte r = buffer[++bufIdx];
          byte g = buffer[++bufIdx];
          byte b = buffer[++bufIdx];
          int note = (int)buffer[++bufIdx];
          keysOn[note - 1] = true;
          globalColor.setRGB(r, g, b);
          controlLeds(note - 1, globalColor);
          MODE = COMMAND_NOTE_ON;
          break;
        }

      case COMMAND_NOTE_OFF:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          int note = (int)buffer[++bufIdx];
          keysOn[note - 1] = false;
          break;
        }

      case COMMAND_SPLASH:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          int velocity = buffer[++bufIdx];
          int note = buffer[++bufIdx];
          CHSV hsv = rgb2hsv_approximate(globalColor);
          keysOn[note - 1] = true;
          addEffect(new FadingRunEffect(splashMaxLength, note - 1, hsv, SPLASH_HEAD_FADE_RATE, velocity));
          MODE = COMMAND_SPLASH;
          break;
        }

      case COMMAND_SPLASH_MAX_LENGTH:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          splashMaxLength = buffer[++bufIdx];
          break;
        }
      case COMMAND_VELOCITY:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          int velocity = buffer[++bufIdx];
          int note = buffer[++bufIdx];
          CRGB rgb;
          keysOn[note - 1] = true;
          setColorFromVelocity(velocity, rgb);
          controlLeds(note - 1, rgb);
          MODE = COMMAND_VELOCITY;
          break;
        }
      case COMMAND_ANIMATION:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          animationIndex = buffer[++bufIdx];
          hue = buffer[++bufIdx];
          MODE = COMMAND_ANIMATION;
          break;
        }
      case COMMAND_SET_BG:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          int h = (int)buffer[++bufIdx];
          int s = (int)buffer[++bufIdx];
          int b = (int)buffer[++bufIdx];
          setBG(CHSV(h, s, b));
          break;
        }
      case COMMAND_SET_GUIDE:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;

          // Get the number of elements in the scalePattern array
          scalePatternLength = (int)buffer[++bufIdx];

          int h = (int)buffer[++bufIdx];
          int s = (int)buffer[++bufIdx];
          int b = (int)buffer[++bufIdx];
          scaleKeyIndex = (int)buffer[++bufIdx];

          // Receive the numbers and populate the scalePattern array
          for (int i = 0; i < scalePatternLength; i++) {
            scalePattern[i] = (int)buffer[++bufIdx];
          }

          setGuide(CHSV(h, s, b), scaleKeyIndex, scalePattern, scalePatternLength);
          break;
        }

      case COMMAND_SET_LED_VISUALIZER:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;

          selectedEffect = buffer[++bufIdx];
          colorHue = buffer[++bufIdx];

          MODE = COMMAND_SET_LED_VISUALIZER;
          break;
        }
      default:
        {
          if ((int)buffer >= 2) {
            int audioInputValue = buffer[0] | (buffer[1] << 8);
            react = map(audioInputValue, 0, 1023, 0, NUM_LEDS);
          }
          break;
        }

    }
  }

  currentTime = millis();

  //slowing it down with interval
  if (currentTime - previousTime >= interval) {
    for (int i = 0; i < numEffects; i++) {
      if (effects[i]->finished()) {
        delete effects[i];
        removeEffect(effects[i]);
      } else {
        effects[i]->nextStep();
      }
    }
    previousTime = currentTime;
  }

  if (currentTime - previousFadeTime >= fadeInterval) {
    if (numEffects > 0 || generalFadeRate > 0) {
      fadeCtrl->fade(generalFadeRate);
    }
    previousFadeTime = currentTime;
  }
  //Animation
  if (MODE == COMMAND_ANIMATION) {

    if (animationIndex == 7) {
      // If the selected animation is 7, run the sineWave() animation
      sineWave();
    } else if (animationIndex == 8) {
      // If the selected animation is 7, run the sineWave() animation
      sparkleDots();
    } else if (animationIndex == 9) {
      // If the selected animation is 7, run the sineWave() animation
      Snake();
    } else {
      Animatons(animationIndex);
      static uint8_t startIndex = 0;
      startIndex = startIndex + 1; /* motion speed */
      FillLEDsFromPaletteColors(startIndex);
    }
  }

  if (MODE == COMMAND_SET_LED_VISUALIZER) {
    Visualizer(selectedEffect);
  }

  FastLED.show();
}
int ledNum(int i) {
  return STRIP_DIRECTION == 0 ? i : (NUM_LEDS - 1) - i;
}


void controlLeds(int ledNo, CRGB color) {
  if (ledNo < 0 || ledNo >= NUM_LEDS) {
    return;
  }
  // Check if guideColor is black
  if (guideColor != CRGB::Black) {
    // Check if the current LED index is in the scalePattern across all octaves
    boolean ledInScalePattern = false;
    for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
      int octaveOffset = octave * (OCTAVE_LEDS + LED_SKIP);



      for (int i = 0; i < scalePatternLength; i++) {
        int ledIndex = octaveOffset + scalePattern[i] + scaleKeyIndex;

        // Display only the first LED for the eighth octave
        if (octave == 7 && i > 0) {
          break;
        }

        if (ledIndex == ledNo) {
          ledInScalePattern = true;
          break;
        }
      }

      if (ledInScalePattern) {
        break;
      }
    }

    if (!ledInScalePattern) {
      // Find the nearest LEDs inside the pattern
      int prevLedIndex = -1;
      int nextLedIndex = -1;

      for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
        int octaveOffset = octave * (OCTAVE_LEDS + LED_SKIP);



        for (int i = 0; i < scalePatternLength; i++) {
          int ledIndex = octaveOffset + scalePattern[i] + scaleKeyIndex;

          // Display only the first LED for the eighth octave
          if (octave == 7 && i > 0) {
            break;
          }

          if (ledIndex < ledNo && (prevLedIndex == -1 || ledIndex > prevLedIndex)) {
            prevLedIndex = ledIndex;
          }

          if (ledIndex > ledNo && (nextLedIndex == -1 || ledIndex < nextLedIndex)) {
            nextLedIndex = ledIndex;
          }
        }
      }

      // Light up the nearest LEDs with blue color
      if (prevLedIndex != -1) {
        leds[ledNum(prevLedIndex)].setRGB(0, 0, 255);  // Blue color
      }

      if (nextLedIndex != -1) {
        leds[ledNum(nextLedIndex)].setRGB(0, 0, 255);  // Blue color
      }

      FastLED.show();
      return;
    }
  }
  leds[ledNum(ledNo)] = color;
  FastLED.show();
}


float distance(CRGB color1, CRGB color2) {
  return sqrt(pow(color1.r - color2.r, 2) + pow(color1.g - color2.g, 2) + pow(color1.b - color2.b, 2));
}

void setColorFromVelocity(int velocity, CRGB& rgb) {
  static int previousVelocity = 0;

  // Calculate the smoothed velocity as a weighted average
  int smoothedVelocity = (velocity + previousVelocity * 3) / 4;
  previousVelocity = smoothedVelocity;

  // Map smoothed velocity to hue value (green is 0° and red is 120° in HSV color space)
  int hue = map(smoothedVelocity, 16, 80, 75, 255);

  // Clamp the hue value within the valid range
  hue = constrain(hue, 75, 255);

  // Map smoothed velocity to brightness value (higher velocity means higher brightness)
  int brightness = map(smoothedVelocity, 16, 80, 65, 255);

  // Clamp the brightness value within the valid range
  brightness = constrain(brightness, 65, 255);

  // Set saturation to a fixed value (e.g., 255 for fully saturated color)
  int saturation = 255;

  // Convert HSV values to RGB color
  rgb = CHSV(hue, saturation, brightness);
}
