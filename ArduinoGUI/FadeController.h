//FadeController.h
#ifndef FadeController_h
#define FadeController_h

#include <Arduino.h>

class FadeController {

public:
  FadeController(int fadeRate);
  void fade();
private:
  int fadeRate;
};

#endif
