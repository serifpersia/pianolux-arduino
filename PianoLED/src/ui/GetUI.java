package ui;

import java.util.Arrays;
import java.util.List;

public class GetUI {

	static int counter = 0;

	private static int leftMinPitch = 21;
	private static int rightMaxPitch = 108;
	private static int leftMaxPitch = leftMinPitch + (rightMaxPitch - leftMinPitch) / 2;

	public static int getLeftMinPitch() {
		return leftMinPitch;
	}

	public static int getLeftMaxPitch() {
		return leftMaxPitch;
	}

	public static int getRightMaxPitch() {
		return rightMaxPitch;
	}

	private static int stripLedNum = 176;
	private static int firstNoteSelected = 21;
	private static int lastNoteSelected = 108;

	private static List<String> modes = Arrays.asList("Default", "Splash", "Random", "Gradient", "Velocity", "Split",
			"Animation", "Piano Roll");

	static String getModeName(int n) {
		return modes.get(n);
	}

	public static int getFirstNoteSelected() {
		return firstNoteSelected;
	}

	public static int getLastNoteSelected() {
		return lastNoteSelected;
	}

	static int getNumPianoKeys() {
		return lastNoteSelected - firstNoteSelected + 1;
	}

	public static int getStripLedNum() {
		return stripLedNum;
	}

	static int rectASizeX = 0;
	static int rectBSizeX = 0;
	static int rectBX = 795;

	static void setKeyboardSize(int counter) {
		System.out.println("counter: " + counter);
		if (counter <= 0) {
			rectASizeX = 0;
			rectBSizeX = 0;
			stripLedNum = 176;
			firstNoteSelected = 21;
			lastNoteSelected = 108;
		}
		if (counter == 1) {
			rectASizeX = 60;
			rectBSizeX = -45;
			stripLedNum = 152;
			firstNoteSelected = 28;
			lastNoteSelected = 103;
		}

		if (counter == 2) {
			rectASizeX = 60;
			rectBSizeX = -75;
			stripLedNum = 146;
			firstNoteSelected = 28;
			lastNoteSelected = 100;
		}
		if (counter == 3) {
			rectASizeX = 135;
			rectBSizeX = -105;
			stripLedNum = 122;
			firstNoteSelected = 36;
			lastNoteSelected = 96;
		}
		if (counter == 4) {
			rectASizeX = 135;
			rectBSizeX = -210;
			stripLedNum = 98;
			firstNoteSelected = 36;
			lastNoteSelected = 84;
		}
		System.out.println("Selected number led: " + stripLedNum);
		System.out.println("Selected first note: " + firstNoteSelected);
		System.out.println("Selected last note: " + lastNoteSelected);
	}

	public static void setDefaults(int brightness, int fade) {
		ControlsPanel.defaultBrighntessVal = brightness;
		ControlsPanel.defaultFadeVal = fade;

		ControlsPanel.sliderBrightness.setValue(ControlsPanel.defaultBrighntessVal);
		ControlsPanel.sliderFade.setValue(ControlsPanel.defaultFadeVal);
	}

	public static void setSplashDefaults(int brightness, int fade, int maxSplashLength) {
		ControlsPanel.defaultBrighntessVal = brightness;
		ControlsPanel.defaultFadeVal = fade;
		ControlsPanel.defaultFadeVal = fade;
		ControlsPanel.defaultMaxSplashLengthVal = maxSplashLength;

		ControlsPanel.sliderBrightness.setValue(ControlsPanel.defaultBrighntessVal);
		ControlsPanel.sliderFade.setValue(ControlsPanel.defaultFadeVal);
		ControlsPanel.sliderMaxSplashLengthVal.setValue(ControlsPanel.defaultMaxSplashLengthVal);

	}
}
