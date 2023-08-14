package com.serifpersia.pianoled;

import com.serifpersia.pianoled.ui.GetUI;
import com.serifpersia.pianoled.ui.pnl_Colors;
import com.serifpersia.pianoled.ui.pnl_Guide;

public class ModesController {

	PianoLED pianoLED;
	static boolean BGColor = false;

	public static boolean VelocityOn = false;

	public static boolean RandomOn = false;

	public static boolean SplitOn = false;

	public static boolean GradientOn = false;

	public static boolean SplashOn = false;

	public static boolean AnimationOn = false;

	public static boolean VisualizerOn = false;

	public static boolean Gradient2Side = true;

	public static boolean Gradient3Side = false;

	public static boolean Gradient4Side = false;

	public static boolean Gradient5Side = false;

	public static boolean Gradient6Side = false;

	public static boolean Gradient7Side = false;

	public static boolean Gradient8Side = false;

	public static int scaleKeyLedIndex;
	public static int[] defaultMajorScalePattern = { 0, 4, 8, 10, 14, 18, 22 };
	public static int[] defaultMinorScalePattern = { 0, 4, 6, 10, 14, 16, 20 };
	public static int[] scalePattern = defaultMajorScalePattern;

	public static int currentArray = 7;

	public ModesController(PianoLED pianoLED) {
		this.pianoLED = pianoLED;
	}

	private void disableAllModes() {
		RandomOn = false;
		VelocityOn = false;
		AnimationOn = false;
		SplitOn = false;
		GradientOn = false;
		SplashOn = false;
		VisualizerOn = false;

	}

	private void disableGradient() {
		Gradient2Side = false;
		Gradient3Side = false;
		Gradient4Side = false;
		Gradient5Side = false;
		Gradient6Side = false;
		Gradient7Side = false;
		Gradient8Side = false;
	}

	public void modeSelect(int n) {
		if (pianoLED.getPianoController().arduino != null)
			pianoLED.getPianoController().arduino.sendCommandBlackOut();

		disableAllModes();

		switch (GetUI.getModeName(n)) {
		case "Default":
			GetUI.setDefaults(8, 255, 0);
			GetUI.resetColor(2);
			break;
		case "Splash":
			GetUI.setDefaults(8, 255, 153);
			SplashOn = true;
			GetUI.resetColor(0);
			break;
		case "Random":
			GetUI.setDefaults(8, 255, 0);
			RandomOn = true;
			break;
		case "Gradient":
			GetUI.setDefaults(8, 255, 0);
			GradientOn = true;
			break;
		case "Velocity":
			GetUI.setDefaults(8, 255, 100);
			VelocityOn = true;
			break;
		case "Split":
			GetUI.setDefaults(8, 255, 0);
			SplitOn = true;
			break;
		case "Animation":
			GetUI.setDefaults(8, 255, 255);
			AnimationOn = true;
			pianoLED.getPianoController().arduino.sendCommandAnimation(0);
			break;
		case "Visualizer":
			GetUI.setDefaults(8, 255, 200);
			VisualizerOn = true;
			pianoLED.getPianoController().arduino.sendCommandSetLedVisualizer(0, 0);
			break;
		}
	}

	public void gradientSideSelect(int n) {
		if (pianoLED.getPianoController().arduino != null)
			pianoLED.getPianoController().arduino.sendCommandBlackOut();

		disableGradient();

		switch (GetUI.getGradientName(n)) {
		case "2 Side Gradient":
			Gradient2Side = true;
			break;
		case "3 Side Gradient":
			Gradient3Side = true;
			break;
		case "4 Side Gradient":
			Gradient4Side = true;
			break;
		case "5 Side Gradient":
			Gradient5Side = true;
			break;
		case "6 Side Gradient":
			Gradient6Side = true;
			break;
		case "7 Side Gradient":
			Gradient7Side = true;
			break;
		case "8 Side Gradient":
			Gradient8Side = true;
			break;
		default:
			break;
		}
	}

	public void ScaleKeySelect(String scaleName) {
		if (pianoLED.getPianoController().arduino != null)
			pianoLED.getPianoController().arduino.sendCommandBlackOut();

		switch (scaleName) {
		case "A":
			scaleKeyLedIndex = 0;
			break;
		case "A#":
			scaleKeyLedIndex = 2;
			break;
		case "B":
			scaleKeyLedIndex = 4;
			break;
		case "C":
			scaleKeyLedIndex = 6;
			break;
		case "C#":
			scaleKeyLedIndex = 8;
			break;
		case "D":
			scaleKeyLedIndex = 10;
			break;
		case "D#":
			scaleKeyLedIndex = 12;
			break;
		case "E":
			scaleKeyLedIndex = 14;
			break;
		case "F":
			scaleKeyLedIndex = 16;
			break;
		case "F#":
			scaleKeyLedIndex = 18;
			break;
		case "G":
			scaleKeyLedIndex = 20;
			break;
		case "G#":
			scaleKeyLedIndex = 22;
			break;
		default:
			break;
		}
	}

	public void ScaleSelect(String scaleName) {
		if (pianoLED.getPianoController().arduino != null)
			pianoLED.getPianoController().arduino.sendCommandBlackOut();

		switch (scaleName) {
		case "Major":
			currentArray = 7;
			scalePattern = defaultMajorScalePattern;

			System.out.println(scalePattern);
			break;

		case "Minor":
			currentArray = 7;
			scalePattern = defaultMinorScalePattern;
			break;

		default:
			break;
		}
	}

	public void setCustomScale() {
		if (pianoLED.getPianoController().arduino != null)
			pianoLED.getPianoController().arduino.sendCommandBlackOut();

		scalePattern = pnl_Guide.getScalePattern();
		currentArray = pnl_Guide.getCurrentArray();

	}

	public static int getGradientSideCount() {
		if (Gradient2Side) {
			return 1;
		} else if (Gradient3Side) {
			return 2;
		} else if (Gradient4Side) {
			return 3;
		} else if (Gradient5Side) {
			return 4;
		} else if (Gradient6Side) {
			return 5;
		} else if (Gradient7Side) {
			return 6;
		} else if (Gradient8Side) {
			return 7;
		} else {
			return -1; // No gradient side count selected
		}
	}

}
