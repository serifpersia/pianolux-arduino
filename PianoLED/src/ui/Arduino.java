/*package ui;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import jssc.SerialPort;
import jssc.SerialPortException;

import processing.core.PApplet;

public class Arduino {

	private PianoLED app;

	private SerialPort serialPort;

	public Arduino(PianoLED pianoLED, String port, int baudrate) {
		this.app = pianoLED;
		serialPort = new SerialPort(port);
		try {
			serialPort.openPort();
            serialPort.setParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE); 
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	// command start with 3 bytes: COMMAND_BYTE1 | COMMAND_BYTE2 | EFFECT_COMMAND
	// then goes effect bytes
	final static byte COMMAND_BYTE1 = (byte) 111;
	final static byte COMMAND_BYTE2 = (byte) 222;

	final static byte COMMAND_SET_COLOR = (byte) 255;
	final static byte COMMAND_FADE_RATE = (byte) 254;
	final static byte COMMAND_ANIMATION = (byte) 253;
	final static byte COMMAND_BLACKOUT = (byte) 252;
	final static byte COMMAND_SPLASH = (byte) 251;
	final static byte COMMAND_SET_BRIGHTNESS = (byte) 250;
	final static byte COMMAND_KEY_OFF = (byte) 249;
	final static byte COMMAND_SPLASH_MAX_LENGTH = (byte) 248;
	final static byte COMMAND_SET_BG = (byte) 247;
	final static byte COMMAND_VELOCITY = (byte) 246;
	final static byte COMMAND_STRIP_DIRECTION = (byte) 245;

	public ByteArrayOutputStream commandSetColor(Color c, int note) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_SET_COLOR);
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

	public ByteArrayOutputStream commandSplash(int velocity, int note, int sc) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_SPLASH);
		message.write((byte) velocity);
		message.write((byte) note);
		//message.write((byte) app.red(sc));
		///message.write((byte) app.green(sc));
		//message.write((byte) app.blue(sc));
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

	public ByteArrayOutputStream commandAnimation(int animationIndex) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_ANIMATION);
		message.write((byte) animationIndex);
		return message;
	}

	public ByteArrayOutputStream commandBlackOut() {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_BLACKOUT);
		return message;
	}

	public ByteArrayOutputStream commandKeyOff(int note) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		message.write((byte) COMMAND_BYTE1);
		message.write((byte) COMMAND_BYTE2);
		message.write((byte) COMMAND_KEY_OFF);
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

	public void sendCommandAnimation(int animationIndex) {
		sendToArduino(commandAnimation(animationIndex));
	}

	public void sendCommandSplash(int velocity, int note, int sc) {
		sendToArduino(commandSplash(velocity, note, sc));
	}

	public void sendCommandBlackOut() {
		sendToArduino(commandBlackOut());
	}

	public void sendCommandBrightness(int value) {
		sendToArduino(commandSetBrightness(value));
	}

	public void sendCommandKeyOff(int value) {
		sendToArduino(commandKeyOff(value));
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

	public void sendToArduino(ByteArrayOutputStream msg) {
		if (serialPort != null && serialPort.isOpened()) {
			byte[] bytes = msg.toByteArray();
			printArray(bytes);
			try {
				serialPort.writeBytes(bytes);
			} catch (SerialPortException e) {
				PApplet.println(e);
			}
		}
	}

	public void stop() {
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			e.printStackTrace();
			PApplet.print(e);
		}
	}

	public void printArray(byte[] bytes) {
		PApplet.print("Message:");
		for (byte b : bytes) {
			int unsignedValue = b & 0xFF;
			PApplet.print(unsignedValue + " ");
		}
		PApplet.println();
	}
}
*/