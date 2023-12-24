// Variables for the Colorful Wave effect
#define WAVE_FREQUENCY 2
#define WAVE_AMPLITUDE 20

// Variables for the Bouncing Balls effect
#define NUM_BALLS 1
#define BALL_RADIUS 21
#define BALL_SPEED 8

int k = 255;
int wheel_speed = 3;


// Function prototypes for animation effects
CRGB One_Color(int pos);
CRGB Heeat_Wave(int pos);
CRGB SpectrumFlow(int pos);
CRGB ColorfulWave(int pos);
CRGB BouncingBalls(int pos);

// Function pointer array for different animation effects
CRGB(*animationFunctions[])
(int pos) = { One_Color, Heeat_Wave, SpectrumFlow, ColorfulWave, BouncingBalls };

// Selected animation index (Change this to select different animations)
int selectedAnimationIndex;


CRGB One_Color(int pos) {
  int audio_level = react * 255 / NUM_LEDS;
  return CHSV(colorHue, 255, 255);
}


CRGB Heeat_Wave(int pos) {
  int hue = pos * 255 / NUM_LEDS;
  int audio_level = react * 255 / NUM_LEDS;
  hue = constrain(hue + audio_level, 0, 255);
  return CHSV(hue, 255, 255);
}

CRGB SpectrumFlow(int pos) {
  int hue = pos + k * NUM_LEDS / 255;
  int audio_level = react * 255 / NUM_LEDS;
  hue = constrain(hue + audio_level, 0, 255);
  return CHSV(hue, 255, 255);
}

CRGB ColorfulWave(int pos) {
  int hue = (pos * WAVE_FREQUENCY + k) * 255 / NUM_LEDS;
  int audio_level = react * 255 / NUM_LEDS;
  int offset = pos * WAVE_AMPLITUDE / NUM_LEDS;

  // Calculate the brightness ensuring it's at least 50
  int calculated_brightness = audio_level - offset;
  int brightness = max(calculated_brightness, 50);

  return CHSV(hue, 255, brightness);
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


void animation() {
  int center = NUM_LEDS / 2;
  int scroll_position = (millis() / wheel_speed) % 256;

  int boostedReact = react;
  CRGB(*currentAnimation)
  (int pos) = animationFunctions[selectedAnimationIndex];

  for (int i = 0; i < NUM_LEDS; i++) {
    if (abs(i - center) < boostedReact) {
      leds[i] = currentAnimation(abs(i - center));
    } else {
      leds[i].fadeToBlackBy(generalFadeRate);  // Fades the current LED to black gradually
    }
  }
}

void Visualizer(int effectIndex) {
  selectedAnimationIndex = effectIndex;
  animation();

  k = (k - wheel_speed + 256) % 256;

  static int decay_check = 0;
  decay_check = (decay_check + 1) % 1;
  if (decay_check == 0 && react > 0) {
    react--;
  }
}
