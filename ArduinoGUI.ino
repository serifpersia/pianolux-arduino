//PianoLED 
//Code by serifpersia
//Last modified 03/05/2023

#include <FastLED.h>
#define NUM_LEDS 176  // how many leds do you want to control
#define DATA_PIN 5    // your LED strip data pin
#define MAX_POWER_MILLIAMPS 450

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

CRGB leds[NUM_LEDS];

void setup() {
  Serial.begin(9600);
  Serial.setTimeout(10);
  FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);  // GRB ordering is assumed
  // FastLED.addLeds<WS2812, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2852, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2812B, DATA_PIN, RGB>(leds, NUM_LEDS);  // GRB ordering is typical
  // FastLED.addLeds<WS2811, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2813, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<APA104, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2811_400, DATA_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2801, DATA_PIN, CLOCK_PIN, RGB>(leds, NUM_LEDS);
  // FastLED.addLeds<WS2803, DATA_PIN, CLOCK_PIN, RGB>(leds, NUM_LEDS);
  FastLED.setBrightness(brightVal);
  FastLED.setMaxPowerInVoltsAndMilliamps(5, MAX_POWER_MILLIAMPS);
}
void loop() {
  // serial communication
  while (Serial.available() > 0) {
    inByte = Serial.read();
    // if byte is 255, then the next 3 bytes will be new rgb value
    if (inByte == 255) {
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