package com.serifpersia.pianoled;

import com.serifpersia.pianoled.ui.GetUI;

public class ModesController {

	PianoLED pianoLED;
	static boolean BGColor = false;

	public static boolean VelocityOn = false;

	public static boolean RandomOn = false;

	public static boolean SplitOn = false;

	public static boolean GradientOn = false;

	public static boolean SplashOn = false;

	public static boolean AnimationOn = false;

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
	}

	public void modeSelect(int n) {
		if (pianoLED.getPianoController().arduino != null)
			pianoLED.getPianoController().arduino.sendCommandBlackOut();

		switch (GetUI.getModeName(n)) {
		case "Default":
			disableAllModes();
			break;
		case "Splash":
			disableAllModes();
			SplashOn = true;
			break;
		case "Random":
			disableAllModes();
			RandomOn = true;
			break;
		case "Gradient":
			disableAllModes();
			GradientOn = true;
			break;
		case "Velocity":
			disableAllModes();
			VelocityOn = true;
			break;
		case "Split":
			disableAllModes();
			SplitOn = true;
			break;
		case "Animation":
			disableAllModes();
			AnimationOn = true;
			break;
		}
	}
}
