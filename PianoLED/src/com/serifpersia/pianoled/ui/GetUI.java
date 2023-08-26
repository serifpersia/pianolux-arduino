package com.serifpersia.pianoled.ui;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class GetUI {

	static int counter = 0;

	private static int leftMinPitch = 21;
	private static int rightMaxPitch = 108;
	public static int leftMaxPitch = leftMinPitch + (rightMaxPitch - leftMinPitch) / 2;

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

	static List<String> colorNames = Arrays.asList("Full Spectrum", "White", "Red", "Green", "Blue", "Yellow", "Orange",
			"Purple", "Pink", "Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold", "Custom");

	static List<String> animationNames = Arrays.asList("RainbowColors", "RainbowStripeColor", "OceanColors",
			"CloudColors", "LavaColors", "ForestColors", "PartyColors");

	static List<String> modes = Arrays.asList("Default", "Splash", "Random", "Gradient", "Velocity", "Split",
			"Animation", "Visualizer", "MultiColor");

	static List<String> gradientSides = Arrays.asList("2 Side Gradient", "3 Side Gradient", "4 Side Gradient",
			"5 Side Gradient", "6 Side Gradient", "7 Side Gradient", "8 Side Gradient");

	public static Color selectedColor = Color.RED;

	static Color customColor = Color.BLACK;

	static Color[] presetColors = { Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, new Color(255, 100, 0), // Yellow
			new Color(255, 35, 0), // Orange
			new Color(128, 0, 255), // Purple
			new Color(255, 35, 35), // Pink
			new Color(0, 255, 255), // Teal
			new Color(128, 255, 0), // Lime
			Color.CYAN, Color.MAGENTA, new Color(255, 25, 25), // Peach
			new Color(160, 128, 255), // Lavender
			new Color(128, 192, 192), // Turquoise
			new Color(255, 80, 0), // Gold
			customColor };

	public static Color[] multiColors = { new Color(140, 0, 255), // A
			new Color(230, 0, 255), // A#
			new Color(255, 0, 145), // B
			new Color(255, 0, 20), // C
			new Color(255, 15, 0), // C#
			new Color(255, 75, 0), // D
			new Color(255, 120, 0), // D#
			new Color(255, 155, 0), // E
			new Color(255, 220, 0), // F
			new Color(160, 255, 0), // F#
			new Color(35, 255, 0), // G
			new Color(15, 0, 255), // G#
	};

	static List<String> scaleKeyNames = Arrays.asList("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#");

	static List<String> scaleNames = Arrays.asList("Major", "Minor");

	static List<String> ledVisualizerEffectsName = Arrays.asList("Hue", "Heat Wave", "SpectrumFlow", "ColorfulWave",
			"BouncingBalls");

	public static String getModeName(int n) {
		return modes.get(n);
	}

	public static String getGradientName(int n) {
		return gradientSides.get(n);
	}

	public static int getFirstNoteSelected() {
		return firstNoteSelected;
	}

	public static int getLastNoteSelected() {
		return lastNoteSelected;
	}

	public static int getNumPianoKeys() {
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

	public static void setDefaults(int splashLenght, int brightness, int fade) {
		pnl_Controls.defaultMaxSplashLengthVal = splashLenght;
		pnl_Controls.defaultBrighntessVal = brightness;
		pnl_Controls.defaultFadeVal = fade;

		pnl_Controls.sld_SplashMaxLenght.setValue(splashLenght);
		pnl_Controls.sld_Brightness.setValue(pnl_Controls.defaultBrighntessVal);
		pnl_Controls.sld_Fade.setValue(pnl_Controls.defaultFadeVal);
	}

	public static void resetColor(int n) {
		pnl_Colors.cb_ColorPresets.setSelectedIndex(n);
	}

}