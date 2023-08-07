#include "Visualizer.h"


// Variables for the Colorful Wave effect
#define WAVE_FREQUENCY 2
#define WAVE_AMPLITUDE 20

// Variables for the Bouncing Balls effect
#define NUM_BALLS 1
#define BALL_RADIUS 21
#define BALL_SPEED 8

// STANDARD VISUALIZER VARIABLES
int loop_max = 0;
int k = 255;    // COLOR WHEEL POSITION
int decay = 0;  // HOW MANY MS BEFORE ONE LIGHT DECAY
int decay_check = 0;
long pre_react = 0;   // NEW SPIKE CONVERSION
long react = 0;       // NUMBER OF LEDs BEING LIT
long post_react = 0;  // OLD SPIKE CONVERSION

// RAINBOW WAVE SETTINGS
int wheel_speed = 3;
int selectedAnimationIndex;

// Function prototypes for animation effects
CRGB SpectrumFlow(int pos);
CRGB BouncingBalls(int pos);
CRGB Wave(int pos);
CRGB ColorfulWave(int pos);


CRGB SpectrumFlow(int pos) {
  int hue = pos + k * NUM_LEDS / 255;
  int audio_level = react * 255 / NUM_LEDS;
  hue = constrain(hue + audio_level, 0, 255);
  return CHSV(hue, 255, 255);
}

CRGB BouncingBalls(int pos) {
  int hue = pos + k * NUM_LEDS / 255;
  int audio_level = react * 255 / NUM_LEDS;
  int brightness = audio_level;
  int ballPos[NUM_BALLS];
  for (int i = 0; i < NUM_BALLS; i++) {
    ballPos[i] = (millis() / BALL_SPEED + i * NUM_LEDS / NUM_BALLS) % NUM_LEDS;
  }
  int minDist = NUM_LEDS;
  for (int i = 0; i < NUM_BALLS; i++) {
    int dist = abs(ballPos[i] - pos);
    if (dist < minDist) {
      minDist = dist;
    }
  }
  int ballBrightness = map(minDist, 0, BALL_RADIUS, brightness, 0);
  return CHSV(hue, 255, ballBrightness);
}

CRGB Wave(int pos) {
  int hue = pos * 255 / NUM_LEDS;
  int audio_level = react * 255 / NUM_LEDS;
  hue = constrain(hue + audio_level, 0, 255);
  return CHSV(hue, 255, 255);
}

CRGB ColorfulWave(int pos) {
  int hue = (pos * WAVE_FREQUENCY + k) * 255 / NUM_LEDS;
  int audio_level = react * 255 / NUM_LEDS;
  int brightness = audio_level;
  int offset = pos * WAVE_AMPLITUDE / NUM_LEDS;
  return CHSV(hue, 255, brightness - offset);
}

// Function pointer array for different animation effects
CRGB (*animationFunctions[])
(int pos) = { SpectrumFlow, BouncingBalls, Wave, ColorfulWave };

void Visualizer(int selectedAnimation) {
  if (Serial.available() > 0) {
    String data = Serial.readStringUntil('\n');
    int audioInputValue = data.toInt();
    react = map(audioInputValue, 0, 1023, 0, NUM_LEDS);
  }

  animation();

  k = k - wheel_speed;
  if (k < 0)
    k = 255;

  decay_check++;
  if (decay_check > decay) {
    decay_check = 0;
    if (react > 0)
      react--;
  }
}

void animation() {
  int center = NUM_LEDS / 2;
  int scroll_position = (millis() / wheel_speed) % 256;

  int audio_threshold = 0;    // You can adjust this value based on your experimentation
  float scalingFactor = 1.1;  // Adjust this value to control the boost level

  if (react <= audio_threshold) {
    fill_solid(leds, NUM_LEDS, CRGB::Black);
  } else {
    int boostedReact = react * scalingFactor;

    CRGB(*currentAnimation)
    (int pos) = animationFunctions[selectedAnimationIndex];

    for (int i = center; i < NUM_LEDS; i++) {
      if (i < center + boostedReact)
        leds[i] = currentAnimation(i - center);
      else
        leds[i] = CRGB::Black;
    }

    for (int i = center - 1; i >= 0; i--) {
      if (center - i < boostedReact)
        leds[i] = currentAnimation(center - i);
      else
        leds[i] = CRGB::Black;
    }
  }

  FastLED.show();
}
