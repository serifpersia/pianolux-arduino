package ui;

import com.serifpersia.pianoled.Arduino;

public class ModesController {
	private Arduino arduino;

	static boolean BGColor = false, VelocityOn = false, RandomOn = false, SplitOn = false, GradientOn = false,
			SplashOn = false, AnimationOn = false;

	public void disableAllModes() {
		RandomOn = false;
		VelocityOn = false;
		AnimationOn = false;
		SplitOn = false;
		GradientOn = false;
		SplashOn = false;
	}

	public void modeSelect(int n) {
		if (arduino != null)
			arduino.sendCommandBlackOut();

		switch (GetUI.getModeName(n)) {
		case "Default":
			disableAllModes();
			// GetUI.hideAllControls();
			// GetUI.showDefaultControls();
			// GetUI.setDefaults(255, 127);
			break;
		case "Splash":
			disableAllModes();
			// GetUI.hideAllControls();
			// GetUI.showSplashControls();
			// GetUI.setSplashDefaults(11, 110, 0, 127);
			SplashOn = true;
			break;
		case "Random":
			disableAllModes();
			// GetUI.hideAllControls();
			// GetUI.setDefaults(255, 127);
			// GetUI.showRandomControls();
			RandomOn = true;
			break;
		case "Gradient":
			disableAllModes();
			// GetUI.hideAllControls();
			// GetUI.setDefaults(255, 127);
			// GetUI.showGradientControls();
			// GradientOn = true;
			break;
		case "Velocity":
			disableAllModes();
			// GetUI.hideAllControls();
			// GetUI.setDefaults(255, 127);
			// GetUI.showVelocityControls();
			// VelocityOn = true;
			break;
		case "Split":
			disableAllModes();
			// GetUI.hideAllControls();
			// GetUI.setDefaults(255, 127);
			// GetUI.showSplitControls();
			SplitOn = true;
			break;
		case "Animation":
			disableAllModes();
			// GetUI.hideAllControls();
			// GetUI.showAnimationControls();
			// GetUI.setAnimationDefaults(0, 127);
			AnimationOn = true;
			break;
		}
		System.out.println("Selected mode: " + GetUI.getModeName(n));
	}
}
