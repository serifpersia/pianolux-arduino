package com.serifpersia.pianoled;

import java.awt.Color;

import com.serifpersia.pianoled.ui.ControlsPanel;
import com.serifpersia.pianoled.ui.GetUI;

public class ModesController {

	static boolean BGColor = false;

	public static boolean VelocityOn = false;

	public static boolean RandomOn = false;

	public static boolean SplitOn = false;

	public static boolean GradientOn = false;

	public static boolean SplashOn = false;

	public static boolean AnimationOn = false;

	private void disableAllModes() {
		RandomOn = false;
		VelocityOn = false;
		AnimationOn = false;
		SplitOn = false;
		GradientOn = false;
		SplashOn = false;
	}

	public void modeSelect(int n) {
		if (PianoController.arduino != null)
			PianoController.arduino.sendCommandBlackOut();

		switch (GetUI.getModeName(n)) {
		case "Default":
			disableAllModes();
			GetUI.setDefaults(255, 255);
			ControlsPanel.selectedColor = Color.RED;
			break;
		case "Splash":
			disableAllModes();
			GetUI.setSplashDefaults(255, 80, 8);
			ControlsPanel.selectedColor = Color.BLACK;
			SplashOn = true;
			break;
		case "Random":
			disableAllModes();
			GetUI.setDefaults(255, 255);
			RandomOn = true;
			break;
		case "Gradient":
			disableAllModes();
			GetUI.setDefaults(255, 255);
			GradientOn = true;
			break;
		case "Velocity":
			disableAllModes();
			GetUI.setDefaults(255, 255);
			VelocityOn = true;
			break;
		case "Split":
			disableAllModes();
			GetUI.setDefaults(255, 255);
			SplitOn = true;
			break;
		case "Animation":
			disableAllModes();
			GetUI.setDefaults(255, 0);
			AnimationOn = true;
			break;
		}
		System.out.println("Selected mode: " + GetUI.getModeName(n));
	}
}
