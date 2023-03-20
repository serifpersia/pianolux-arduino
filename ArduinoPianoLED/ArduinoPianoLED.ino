//PianoLED

#include <FastLED.h>
#include "FadingRunEffect.h"
#include "FadeController.h"
#define NO_HARDWARE_PIN_SUPPORT
#define FASTLED_RMT_MAX_CHANNELS 1

#define NUM_LEDS 176  // how many leds do you want to control
#define DATA_PIN 5    // your LED strip data pin
#define MAX_POWER_MILLIAMPS 450

int STRIP_DIRECTION = 0; //0 - left-to-right
const int MAX_VELOCITY = 128;

const int COMMAND_BYTE1 = 111;
const int COMMAND_BYTE2 = 222;

int MODE;
const int COMMAND_SET_COLOR = 255;
const int COMMAND_FADE_RATE = 254;
const int COMMAND_ANIMATION = 253;
const int COMMAND_BLACKOUT = 252;
const int COMMAND_SPLASH = 251;
const int COMMAND_SET_BRIGHTNESS = 250;
const int COMMAND_KEY_OFF = 249;
const int COMMAND_SPLASH_MAX_LENGTH = 248;
const int COMMAND_SET_BG = 247;
const int COMMAND_VELOCITY = 246;
const int COMMAND_STRIP_DIRECTION = 245;

int buffer[10];  // declare buffer as an array of 10 integers
int bufIdx = 0;  // initialize bufIdx to zero
int generalBrightness = buffer[++bufIdx];
int animationIndex;


int DEFAULT_BRIGHTNESS = 200;

int SPLASH_HEAD_FADE_RATE = 5;
int splashMaxLength = 8;

boolean bgOn = false;
CRGB bgColor = CRGB::Black;


unsigned long currentTime = 0;
unsigned long previousTime = 0;
unsigned long previousFadeTime = 0;

unsigned long interval = 20;      // general refresh in milliseconds
unsigned long fadeInterval = 20;  // general fade interval in milliseconds
int generalFadeRate = 5;          // general fade rate, bigger value - quicker fade (configurable via App)


//Animation select variables
uint8_t hue = 0;

#define UPDATES_PER_SECOND 100

CRGBPalette16 currentPalette;
TBlendType currentBlending;

extern CRGBPalette16 myRedWhiteBluePalette;
extern const TProgmemPalette16 myRedWhiteBluePalette_p PROGMEM;


boolean keysOn[NUM_LEDS];

CRGB leds[NUM_LEDS];
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
    delay(3);
    leds[i] = CHSV(0, 0, 0);
    FastLED.show();
  }
}

void setup() {
  Serial.begin(115200);
  Serial.setTimeout(10);
  FastLED.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);  // GRB ordering
  // FastLED.addLeds<NEOPIXEL, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2812, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2852, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2812B, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2811, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2813, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<APA104, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2811_400, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2801, DATA_PIN, CLOCK_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2803, DATA_PIN, CLOCK_PIN, RGB>(leds, NUM_LEDS);
  FastLED.setMaxPowerInVoltsAndMilliamps(5, MAX_POWER_MILLIAMPS);  // set power limit
  FastLED.setBrightness(DEFAULT_BRIGHTNESS);
  StartupAnimation();
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
void setBG(CRGB colorToSet) {
  // if (colorToSet == CRGB(0)) {
  //  fadeInterval = 20;
  // } else {
  //   fadeInterval = 100;
  // }

  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = colorToSet;
  }
  bgColor = colorToSet;
  FastLED.show();
}

boolean debug = false;
void debugLightOn(int n) {
  if (debug) {
    leds[n] = leds[n].LightBlue;
    FastLED.show();
  }
}

FadeController* fadeCtrl = new FadeController();
//Main loop
void loop() {

  currentTime = millis();

  int bufferSize = Serial.available();
  byte buffer[bufferSize];
  Serial.readBytes(buffer, bufferSize);
  boolean commandByte1Arrived = false;
  boolean commandByte2Arrived = false;
  for (int bufIdx = 0; bufIdx < bufferSize; bufIdx++) {
    int command = (unsigned int)buffer[bufIdx];
    switch (command) {
      case COMMAND_BYTE1:
        {
          debugLightOn(1);
          commandByte1Arrived = true;
          break;
        }
      case COMMAND_BYTE2:
        {
          debugLightOn(2);
          if (commandByte1Arrived) {
            commandByte2Arrived = true;
          }
          commandByte1Arrived = false;
          break;
        }
      case COMMAND_SPLASH:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(3);
          int velocity = buffer[++bufIdx];
          int note = buffer[++bufIdx];
          int r = buffer[++bufIdx];
          int g = buffer[++bufIdx];
          int b = buffer[++bufIdx];
          CHSV hsv = rgb2hsv_approximate(CRGB(r, g, b));
          keysOn[note - 1] = true;
          addEffect(new FadingRunEffect(splashMaxLength, note - 1, hsv, SPLASH_HEAD_FADE_RATE, velocity));
          MODE = COMMAND_SPLASH;
          break;
        }
      case COMMAND_FADE_RATE:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(4);
          generalFadeRate = buffer[++bufIdx];
          break;
        }
      case COMMAND_SPLASH_MAX_LENGTH:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(5);
          splashMaxLength = buffer[++bufIdx];
          break;
        }
      case COMMAND_SET_BRIGHTNESS:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(5);
          generalBrightness = buffer[++bufIdx];
          FastLED.setBrightness(generalBrightness);
          break;
        }
      case COMMAND_SET_COLOR:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(6);
          MODE = COMMAND_SET_COLOR;
          byte redVal = buffer[++bufIdx];
          byte greenVal = buffer[++bufIdx];
          byte blueVal = buffer[++bufIdx];
          int note = (int)buffer[++bufIdx];
          keysOn[note - 1] = true;
          controlLeds(note - 1, redVal, greenVal, blueVal);
          MODE = COMMAND_SET_COLOR;
          break;
        }
      case COMMAND_BLACKOUT:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(7);
          fill_solid(leds, NUM_LEDS, bgColor);
          MODE = COMMAND_BLACKOUT;
          break;
        }
      case COMMAND_ANIMATION:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(8);
          animationIndex = buffer[++bufIdx];
          MODE = COMMAND_ANIMATION;
          break;
        }
      case COMMAND_KEY_OFF:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(9);
          int note = (int)buffer[++bufIdx];
          keysOn[note - 1] = false;
          break;
        }
      case COMMAND_SET_BG:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(10);
          int h = (int)buffer[++bufIdx];
          int s = (int)buffer[++bufIdx];
          int b = (int)buffer[++bufIdx];
          setBG(CHSV(h, s, b));
          break;
        }

      case COMMAND_VELOCITY:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(11);
          int velocity = buffer[++bufIdx];
          int note = buffer[++bufIdx];
          CRGB rgb;
          keysOn[note - 1] = true;
          setColorFromVelocity(velocity, rgb);
          controlLeds(note - 1, rgb.r, rgb.g, rgb.b);
          MODE = COMMAND_VELOCITY;
          break;
        }
      case COMMAND_STRIP_DIRECTION:
        {
          commandByte1Arrived = false;
          if (!commandByte2Arrived) break;
          debugLightOn(11);
          STRIP_DIRECTION = buffer[++bufIdx];
          break;
        }
      default:
        {
          break;
        }
    }
  }

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
    Animatons(animationIndex);
    static uint8_t startIndex = 0;
    startIndex = startIndex + 1; /* motion speed */

    FillLEDsFromPaletteColors(startIndex);
  }
  FastLED.show();
}
int ledNum(int i)
{
  return STRIP_DIRECTION == 0 ? i : (NUM_LEDS-1) - i;
}


void controlLeds(int ledNo, int redVal, int greenVal, int blueVal) {
  if (ledNo < 0 || ledNo > NUM_LEDS) {
    return;
  }
  leds[ledNum(ledNo)].setRGB(redVal, greenVal, blueVal);
  FastLED.show();
}

float distance(CRGB color1, CRGB color2) {
  return sqrt(pow(color1.r - color2.r, 2) + pow(color1.g - color2.g, 2) + pow(color1.b - color2.b, 2));
}

void setColorFromVelocity(int velocity, CRGB& rgb) {
  // Map velocity to hue value
  int hue = map(velocity, 0, 127, 0, 255);

  // Convert hue to RGB color
  CHSV hsv(hue, 255, 255);
  rgb = hsv;
}
