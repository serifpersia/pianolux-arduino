
//command start with 3 bytes: COMMAND_BYTE1 | COMMAND_BYTE2 | EFFECT_COMMAND
//then goes effect bytes

final static byte COMMAND_BYTE1 = (byte)111;
final static byte COMMAND_BYTE2 = (byte)222;

final static byte COMMAND_SET_COLOR = (byte)255;
final static byte COMMAND_FADE_RATE = (byte)254;
final static byte COMMAND_ANIMATION = (byte)253;
final static byte COMMAND_BLACKOUT = (byte)252;
final static byte COMMAND_SPLASH = (byte)251;
final static byte COMMAND_SET_BRIGHTNESS = (byte)250;

ByteArrayOutputStream commandSetColor(int r, int g, int b, int note)
{
  ByteArrayOutputStream message = new ByteArrayOutputStream();
  message.write((byte)COMMAND_BYTE1);
  message.write((byte)COMMAND_BYTE2);
  message.write((byte)COMMAND_SET_COLOR);
  message.write((byte)r);
  message.write((byte)g);
  message.write((byte)b);
  message.write((byte)note);
  return message;
}

ByteArrayOutputStream commandSetBrightness(int brightness)
{
  ByteArrayOutputStream message = new ByteArrayOutputStream();
  message.write((byte)COMMAND_BYTE1);
  message.write((byte)COMMAND_BYTE2);
  message.write((byte)COMMAND_SET_BRIGHTNESS);
  message.write((byte)brightness);
  return message;
}

ByteArrayOutputStream commandSplash(int velocity, int note)
{
  ByteArrayOutputStream message = new ByteArrayOutputStream();
  message.write((byte)COMMAND_BYTE1);
  message.write((byte)COMMAND_BYTE2);
  message.write((byte)COMMAND_SPLASH);
  message.write((byte)velocity);
  message.write((byte)note);
  return message;
}

ByteArrayOutputStream commandFadeRate(int fadeRate)
{
  ByteArrayOutputStream message = new ByteArrayOutputStream();
  message.write((byte)COMMAND_BYTE1);
  message.write((byte)COMMAND_BYTE2);
  message.write((byte)COMMAND_FADE_RATE);
  message.write((byte)fadeRate);
  return message;
}

ByteArrayOutputStream commandAnimation()
{
  ByteArrayOutputStream message = new ByteArrayOutputStream();
  message.write((byte)COMMAND_BYTE1);
  message.write((byte)COMMAND_BYTE2);
  message.write((byte)COMMAND_ANIMATION);
  return message;
}

ByteArrayOutputStream commandBlackOut()
{
  ByteArrayOutputStream message = new ByteArrayOutputStream();
  message.write((byte)COMMAND_BYTE1);
  message.write((byte)COMMAND_BYTE2);
  message.write((byte)COMMAND_BLACKOUT);
  return message;
}

void sendCommandBlackOut()
{
  sendToArduino(commandBlackOut());
}

void sendCommandBrightness(int value)
{
  sendToArduino(commandSetBrightness(value));
}

void sendCommandFadeRate(int value)
{
  sendToArduino(commandFadeRate(value));
}

void animationLoop()
{
  sendToArduino(commandAnimation());
}

void sendToArduino(byte val)
{
  println("Arduino command: "+(int)(val & 0xFF));
  arduino.write(val);
}

void sendToArduino(ByteArrayOutputStream msg)
{
  byte[] bytes = msg.toByteArray();
  printArray(bytes);
  arduino.write(bytes);
}
