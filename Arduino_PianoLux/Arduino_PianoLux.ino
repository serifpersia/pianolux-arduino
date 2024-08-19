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

#define MAX_NUM_LEDS 176          // how many leds do you want to control
#define DATA_PIN 5               // your LED strip data pin use pwm ones symbol ~ next to pin hole on Arduino board
#define MAX_POWER_MILLIAMPS 400   //define current limit if you are using 5V pin from Arduino dont touch this

int NUM_LEDS = 176;               // how many leds do you want to control

int STRIP_DIRECTION = 0;  // 0 - left-to-right
const int MAX_VELOCITY = 128;

CRGB globalColor = CRGB::Red;

byte  MODE;

#define BUFFER_SIZE 18  // Adjust based on the largest expected message size

#define COMMAND_NOTE_ON_WITHOUT_COLOR 1
#define COMMAND_NOTE_ON_WITH_COLOR 2
#define COMMAND_NOTE_OFF 3
#define COMMAND_SET_GLOBAL_COLOR 4
#define COMMAND_SET_BRIGHTNESS 5
#define COMMAND_SET_FADE_RATE 6
#define COMMAND_BLACKOUT 7
#define COMMAND_SPLASH 8
#define COMMAND_SPLASH_MAX_LENGTH 9
#define COMMAND_VELOCITY 10
#define COMMAND_SET_ANIMATION 11
#define COMMAND_SET_LED_VISUALIZER 12
#define COMMAND_SET_STRIP_DIRECTION 13
#define COMMAND_SET_BG 14
#define COMMAND_SET_GUIDE 15
#define COMMAND_LED_VISUALIZER_AUDIO 16

byte buffer[BUFFER_SIZE];
byte bufferIndex = 0;
enum State { WAIT_FOR_COMMAND, WAIT_FOR_NOTE_ON_WITHOUT_COLOR,
             WAIT_FOR_NOTE_ON_WITH_COLOR, WAIT_FOR_NOTE_OFF,
             WAIT_FOR_BRIGHTNESS, WAIT_FOR_GLOBAL_COLOR,
             WAIT_FOR_FADE_RATE, WAIT_FOR_BLACKOUT,
             WAIT_FOR_SPLASH, WAIT_FOR_SPLASH_MAX_LENGTH,
             WAIT_FOR_VELOCITY, WAIT_FOR_ANIMATION,
             WAIT_FOR_LED_VISUALIZER, WAIT_FOR_SET_STRIP_DIRECTION,
             WAIT_FOR_SET_BG, WAIT_FOR_SET_GUIDE,
             WAIT_FOR_LED_VISUALIZER_AUDIO
           };
State currentState = WAIT_FOR_COMMAND;

long react = 0;
int animationIndex;
int selectedEffect;
int colorHue = 0;

int DEFAULT_BRIGHTNESS = 255;

int SPLASH_HEAD_FADE_RATE = 5;
int splashMaxLength = 8;

boolean bgOn = false;
boolean guideOn  = false;

CRGB bgColor = CRGB::Black;
CRGB guideColor = CRGB::Black;


unsigned long currentTime = 0;
unsigned long previousTime = 0;
unsigned long previousFadeTime = 0;

unsigned long interval = 20;
unsigned long fadeInterval = 20;
int generalFadeRate = 255;


const int OCTAVE_LEDS = 23; // Number of LEDs in one octave
const int OCTAVE_COUNT = 8; // Number of octaves
const int LED_SKIP = 1;     // Number of LEDs to skip before starting the next octave pattern

int scalePatternLength;
int scalePattern[12];

int scaleKeyIndex;
//const int majorScale[] = { 0, 4, 8, 10, 14, 18, 21 };


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
  return static_cast<long>(-pos) * 255 / NUM_LEDS;
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
  Serial.begin(115200);
  Serial.setTimeout(10);
  FastLED.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);               // GRB ordering
  // FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);                // GRB ordering is typical
  // FastLED.addLeds<WS2812, DATA_PIN, RGB>(leds, NUM_LEDS);             // GRB ordering is typical
  // FastLED.addLeds<WS2852, DATA_PIN, RGB>(leds, NUM_LEDS);             // GRB ordering is typical
  // FastLED.addLeds<WS2812B, DATA_PIN, RGB>(leds, NUM_LEDS);            // GRB ordering is typical
  // FastLED.addLeds<WS2811, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2813, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<APA104, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2811_400, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2801, DATA_PIN, CLOCK_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2803, DATA_PIN, CLOCK_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<SK6812, DATA_PIN, RGB>(leds, NUM_LEDS);              // GRB ordering is typical
  FastLED.setMaxPowerInVoltsAndMilliamps(5, MAX_POWER_MILLIAMPS);         // set power limit
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


void loop() {
  while (Serial.available()) {
    byte incomingByte = Serial.read();

    // Add byte to buffer
    buffer[bufferIndex++] = incomingByte;

    // Process based on current state
    switch (currentState) {
      case WAIT_FOR_COMMAND:
        if (bufferIndex >= 1) {
          byte command = buffer[0];
          switch (command) {
            case COMMAND_NOTE_ON_WITHOUT_COLOR:
              currentState = WAIT_FOR_NOTE_ON_WITHOUT_COLOR;
              break;

            case COMMAND_NOTE_ON_WITH_COLOR:
              currentState = WAIT_FOR_NOTE_ON_WITH_COLOR;
              break;

            case COMMAND_NOTE_OFF:
              currentState = WAIT_FOR_NOTE_OFF;
              break;

            case COMMAND_SET_GLOBAL_COLOR:
              currentState = WAIT_FOR_GLOBAL_COLOR;
              break;

            case COMMAND_SET_BRIGHTNESS:
              currentState = WAIT_FOR_BRIGHTNESS;
              break;

            case COMMAND_SET_FADE_RATE:
              currentState = WAIT_FOR_FADE_RATE;
              break;

            case COMMAND_BLACKOUT:
              currentState = WAIT_FOR_BLACKOUT;
              break;

            case COMMAND_SPLASH:
              currentState = WAIT_FOR_SPLASH;
              break;

            case COMMAND_SPLASH_MAX_LENGTH:
              currentState = WAIT_FOR_SPLASH_MAX_LENGTH;
              break;

            case COMMAND_VELOCITY:
              currentState = WAIT_FOR_VELOCITY;
              break;

            case COMMAND_SET_ANIMATION:
              currentState = WAIT_FOR_ANIMATION;
              break;

            case COMMAND_SET_LED_VISUALIZER:
              currentState = WAIT_FOR_LED_VISUALIZER;
              break;

            case COMMAND_SET_STRIP_DIRECTION:
              currentState = WAIT_FOR_SET_STRIP_DIRECTION;
              break;

            case COMMAND_SET_BG:
              currentState = WAIT_FOR_SET_BG;
              break;

            case COMMAND_SET_GUIDE:
              currentState = WAIT_FOR_SET_GUIDE;
              break;

            case COMMAND_LED_VISUALIZER_AUDIO:
              currentState = WAIT_FOR_LED_VISUALIZER_AUDIO;
              break;

            default:
              // Handle unknown commands or errors
              currentState = WAIT_FOR_COMMAND;
              bufferIndex = 0;
              break;
          }
        }
        break;

      case WAIT_FOR_NOTE_ON_WITHOUT_COLOR:
        if (bufferIndex >= 2) {
          handleNoteOn(buffer[1]);
          MODE = COMMAND_NOTE_ON_WITHOUT_COLOR;
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_NOTE_ON_WITH_COLOR:
        if (bufferIndex >= 5) {
          globalColor.setRGB(buffer[1], buffer[2], buffer[3]);
          handleNoteOn(buffer[4]);
          MODE = COMMAND_NOTE_ON_WITH_COLOR;

          // Reset state and buffer index
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_NOTE_OFF:
        if (bufferIndex >= 2) {
          handleNoteOff(buffer[1]);
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_BRIGHTNESS:
        if (bufferIndex >= 2) {
          FastLED.setBrightness(buffer[1]);
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_GLOBAL_COLOR:
        if (bufferIndex >= 4) {
          globalColor.setRGB(buffer[1], buffer[2], buffer[3]);
          MODE = COMMAND_SET_GLOBAL_COLOR;
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_FADE_RATE:
        if (bufferIndex >= 2) {
          generalFadeRate = buffer[1];
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_BLACKOUT:
        if (bufferIndex >= 1) {
          fill_solid(leds, NUM_LEDS, bgColor);
          MODE = COMMAND_BLACKOUT;
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_SPLASH:
        if (bufferIndex >= 3) {
          int velocity = buffer[1];
          int note = buffer[2];
          CHSV hsv = rgb2hsv_approximate(globalColor);
          keysOn[note] = true;
          addEffect(new FadingRunEffect(splashMaxLength, note, hsv, SPLASH_HEAD_FADE_RATE, velocity));
          MODE = COMMAND_SPLASH;
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_SPLASH_MAX_LENGTH:
        if (bufferIndex >= 2) {
          splashMaxLength = buffer[1];
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_VELOCITY:
        if (bufferIndex >= 3) {
          int velocity = buffer[1];
          int note = buffer[2];
          CRGB rgb;
          setColorFromVelocity(velocity, rgb);
          keysOn[note] = true;
          controlLeds(note, rgb);
          MODE = COMMAND_VELOCITY;
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_ANIMATION:
        if (bufferIndex >= 3) {
          animationIndex = buffer[1];
          hue = buffer[2];
          MODE = COMMAND_SET_ANIMATION;
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_LED_VISUALIZER:
        if (bufferIndex >= 3) {
          selectedEffect = buffer[1];
          colorHue = buffer[2];
          MODE = COMMAND_SET_LED_VISUALIZER;
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_SET_STRIP_DIRECTION:
        if (bufferIndex >= 3) {
          STRIP_DIRECTION = buffer[1];
          NUM_LEDS = buffer[2];
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_SET_BG:
        if (bufferIndex >= 4) {
          int h = (int)buffer[1];
          int s = (int)buffer[2];
          int b = (int)buffer[3];
          setBG(CHSV(h, s, b));
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      // TO DO: Guide not working, fix or remove
      case WAIT_FOR_SET_GUIDE:
        if (bufferIndex >= 6 + scalePatternLength) {
          // Read data
          scalePatternLength = (int)buffer[1];
          int h = (int)buffer[2];
          int s = (int)buffer[3];
          int b = (int)buffer[4];
          int scaleKeyIndex = (int)buffer[5];

          // Ensure the scalePattern array has enough space
          // Assuming scalePattern is already allocated with max size of 12
          for (int i = 0; i < scalePatternLength; i++) {
            scalePattern[i] = (int)buffer[6 + i];
          }

          // setGuide(CHSV(h, s, b), scaleKeyIndex, scalePattern, scalePatternLength);
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

      case WAIT_FOR_LED_VISUALIZER_AUDIO:
        if (bufferIndex >= 3) { // Ensure we have enough bytes
          int audioInputValue = (buffer[2] << 8) | buffer[1];
          react = map(audioInputValue, 0, 1023, 0, NUM_LEDS);
          currentState = WAIT_FOR_COMMAND;
          bufferIndex = 0;
        }
        break;

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
  if (MODE == COMMAND_SET_ANIMATION) {

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

void handleNoteOff(byte note)
{
  keysOn[note] = false;
}

void handleNoteOn(byte note)
{
  keysOn[note] = true;
  controlLeds(note, globalColor);
}

void controlLeds(int ledNo, CRGB color) {
  if (ledNo < 0 || ledNo >= NUM_LEDS) {
    return;
  }

  // Guide unused until fix or removal

  if (guideOn) {
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
  }

  leds[ledNum(ledNo)] = color;

  FastLED.show();
}

float distance(CRGB color1, CRGB color2) {
  return sqrt(pow(color1.r - color2.r, 2) + pow(color1.g - color2.g, 2) + pow(color1.b - color2.b, 2));
}

void setColorFromVelocity(int velocity, CRGB & rgb) {
  static int previousVelocity = 0;
  int smoothedVelocity = (velocity + previousVelocity * 3) / 4;

  previousVelocity = smoothedVelocity;
  int hue = map(smoothedVelocity, 16, 80, 75, 255);

  hue = constrain(hue, 75, 255);
  int brightness = map(smoothedVelocity, 16, 80, 65, 255);
  brightness = constrain(brightness, 65, 255);
  int saturation = 255;

  rgb = CHSV(hue, saturation, brightness);
}
