package com.serifpersia.pianolux;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import jssc.SerialPort;
import jssc.SerialPortException;

public class Arduino {

	SerialPort serialPort;

	public Arduino(PianoLux pianoLux, String port, int baudrate) {
		serialPort = new SerialPort(port);

		try {
			serialPort.openPort();
			serialPort.setParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	// command start with 3 bytes: COMMAND_BYTE1 | COMMAND_BYTE2 | EFFECT_COMMAND
	// then goes effect bytes
	final static byte COMMAND_BYTE1 = (byte) 0;
	final static byte COMMAND_BYTE2 = (byte) 1;

	final static byte COMMAND_BLACKOUT = (byte) 2;
	final static byte COMMAND_SET_BRIGHTNESS = (byte) 3;
	final static byte COMMAND_SET_GLOBAL_COLOR = (byte) 4;

	final static byte COMMAND_FADE_RATE = (byte) 5;
	final static byte COMMAND_STRIP_DIRECTION = (byte) 6;

	final static byte COMMAND_NOTE_ON_DEFAULT = (byte) 7;
	final static byte COMMAND_NOTE_OFF = (byte) 8;

	final static byte COMMAND_SPLASH = (byte) 9;
	final static byte COMMAND_SPLASH_MAX_LENGTH = (byte) 10;

	final static byte COMMAND_VELOCITY = (byte) 11;

	final static byte COMMAND_ANIMATION = (byte) 12;

	final static byte COMMAND_SET_BG = (byte) 13;
	final static byte COMMAND_SET_GUIDE = (byte) 14;

	final static byte COMMAND_SET_LED_VISUALIZER = (byte) 15;
	final static byte COMMAND_NOTE_ON = (byte) 16;

	public ByteArrayOutputStream commandUpdateColor(Color c) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_SET_GLOBAL_COLOR);
		message.write((byte) c.getRed());
		message.write((byte) c.getGreen());
		message.write((byte) c.getBlue());
		return message;
	}

	public ByteArrayOutputStream commandDefaultNoteOn(int note) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_NOTE_ON_DEFAULT);
		message.write((byte) note);
		return message;
	}

	public ByteArrayOutputStream commandNoteOn(Color c, int note) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_NOTE_ON);
		message.write((byte) c.getRed());
		message.write((byte) c.getGreen());
		message.write((byte) c.getBlue());
		message.write((byte) note);
		return message;
	}

	public ByteArrayOutputStream commandSetBrightness(int brightness) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_SET_BRIGHTNESS);
		message.write((byte) brightness);
		return message;
	}

	public ByteArrayOutputStream commandSplash(int velocity, int note) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_SPLASH);
		message.write((byte) velocity);
		message.write((byte) note);
		return message;
	}

	public ByteArrayOutputStream commandFadeRate(int fadeRate) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_FADE_RATE);
		message.write((byte) fadeRate);
		return message;
	}

	public ByteArrayOutputStream commandAnimation(int animationIndex, int hue) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_ANIMATION);
		message.write((byte) animationIndex);
		message.write((byte) hue);
		return message;
	}

	public ByteArrayOutputStream commandBlackOut() {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_BLACKOUT);
		return message;
	}

	public ByteArrayOutputStream commandNoteOff(int note) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_NOTE_OFF);
		message.write((byte) note);
		return message;
	}

	public ByteArrayOutputStream commandSplashMaxLength(int value) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_SPLASH_MAX_LENGTH);
		message.write((byte) value);
		return message;
	}

	public ByteArrayOutputStream commandSetBG(int hue, int saturation, int brightness) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_SET_BG);
		message.write((byte) hue);
		message.write((byte) saturation);
		message.write((byte) brightness);
		return message;
	}

	public ByteArrayOutputStream commandVelocity(int velocity, int note, Color c) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_VELOCITY);
		message.write((byte) velocity);
		message.write((byte) note);
		message.write((byte) c.getRed());
		message.write((byte) c.getGreen());
		message.write((byte) c.getBlue());
		return message;
	}

	public ByteArrayOutputStream commandStripDirection(int direction, int numLeds) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_STRIP_DIRECTION);
		message.write((byte) direction);
		message.write((byte) numLeds);
		return message;
	}

	public ByteArrayOutputStream commandAudioData(int data) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		byte[] dataBytes = new byte[2];
		dataBytes[0] = (byte) (data & 0xFF);
		dataBytes[1] = (byte) ((data >> 8) & 0xFF);
		message.write(dataBytes, 0, 2);
		return message;
	}

	public ByteArrayOutputStream commandSetGuide(int currentArray, int hue, int saturation, int brightness,
			int scaleKeyIndex, int[] scalePattern) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_SET_GUIDE);

		message.write((byte) currentArray);
		message.write((byte) hue);
		message.write((byte) saturation);
		message.write((byte) brightness);
		message.write((byte) scaleKeyIndex);

		for (int i = 0; i < scalePattern.length; i++) {
			message.write((byte) scalePattern[i]);
		}

		return message;
	}

	public ByteArrayOutputStream commandSetLedVisualizer(int effect, int colorHue) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_SET_LED_VISUALIZER);

		message.write((byte) effect);
		message.write((byte) colorHue);
		return message;
	}

	public void sendCommandUpdateColor(Color c) {
		sendToArduino(commandUpdateColor(c));
	}

	public void sendCommandAudioData(int audioData) {
		sendToArduino(commandAudioData(audioData));
	}

	public void sendCommandAnimation(int animationIndex, int hue) {
		sendToArduino(commandAnimation(animationIndex, hue));
	}

	public void sendCommandSplash(int velocity, int note) {
		sendToArduino(commandSplash(velocity, note));
	}

	public void sendCommandBlackOut() {
		sendToArduino(commandBlackOut());
	}

	public void sendCommandBrightness(int value) {
		sendToArduino(commandSetBrightness(value));
	}

	public void sendCommandNoteOff(int value) {
		sendToArduino(commandNoteOff(value));
	}

	public void sendCommandFadeRate(int value) {
		sendToArduino(commandFadeRate(value));
	}

	public void sendCommandSplashMaxLength(int value) {
		sendToArduino(commandSplashMaxLength(value));
	}

	public void sendCommandSetBG(int hue, int saturation, int brightness) {
		sendToArduino(commandSetBG(hue, saturation, brightness));
	}

	public void sendCommandVelocity(int velocity, int note, Color c) {
		sendToArduino(commandVelocity(velocity, note, c));
	}

	public void sendCommandStripDirection(int direction, int numLeds) {
		sendToArduino(commandStripDirection(direction, numLeds));
	}

	public void sendCommandSetGuide(int currentArray, int hue, int saturation, int brightness, int scaleKeyIndex,
			int[] scalePattern) {
		sendToArduino(commandSetGuide(currentArray, hue, saturation, brightness, scaleKeyIndex, scalePattern));
	}

	public void sendCommandSetLedVisualizer(int effect, int colorHue) {
		sendToArduino(commandSetLedVisualizer(effect, colorHue));

	}

	public void sendToArduinoAudio(byte[] data) throws SerialPortException {
		if (serialPort != null && serialPort.isOpened()) {
			serialPort.writeBytes(data);
		}
	}

	public void sendToArduino(ByteArrayOutputStream msg) {
		if (serialPort != null && serialPort.isOpened()) {
			byte[] bytes = msg.toByteArray();
			printArray(bytes);
			try {
				serialPort.writeBytes(bytes);
			} catch (SerialPortException e) {
				System.out.println(e);
			}
		}
	}

	public void stop() {
		if (serialPort != null && serialPort.isOpened()) {
			try {
				serialPort.closePort();
			} catch (SerialPortException e) {
				e.printStackTrace();
				System.out.print(e);
			}
		}
	}

	public void printArray(byte[] bytes) {
		System.out.print("Message:");
		for (byte b : bytes) {
			int unsignedValue = b & 0xFF;
			System.out.print(unsignedValue + " ");
		}
		System.out.println();
	}
}