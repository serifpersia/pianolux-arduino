/*package com.serifpersia.pianoled;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controlP5.ColorWheel;
import controlP5.ControlP5;
import controlP5.Controller;mport controlP5.ScrollableList;
import controlP5.Toggle;
import processing.core.PApplet;
import processing.core.PFont;

public class UI {

	private static final String SPLASH_COLOR_LIST = "splashColor";
	public static final String COLOR_WHEEL = "ColorWheel";
	public static final int DEFAULT_WIDTH = 930;
	public static final int DEFAULT_HEIGHT = 160;

	public static final int EFFECT_CONTROLS_X = 811;

	int MIN_FADE_RATE = 0;
	int MAX_FADE_RATE = 255;
	int DEFAULT_FADE_RATE = 255;

	int MIN_BRIGHT = 0;
	int MAX_BRIGHT = 255;
	int DEF_BRIGHT = 127;

	Color[] presetColors = {
			new Color(255, 255, 254), //White
			Color.RED,
			Color.GREEN, 
			Color.BLUE, 
			Color.YELLOW, 
			Color.ORANGE, 
			new Color(128, 0, 255), // Purple
			Color.PINK, 
			new Color(0, 255, 255), // Teal
			new Color(128, 255, 0), // Lime
			Color.CYAN,
			Color.MAGENTA,
			new Color(255, 128, 128), // Peach
			new Color(192, 128, 255), // Lavender
			new Color(128, 192, 192), // Turquoise
			new Color(255, 215, 0) // Gold
	};
	
	List<String> modes = Arrays.asList("Default", "Splash", "Random", "Gradient", "Velocity", "Split", "Animation",
			"Piano Roll");
	List<String> colorNames = Arrays.asList("White", "Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink",
			"Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold");
	List<String> splashColorNames = Arrays.asList("Full Spectrum", "White", "Red", "Green", "Blue", "Yellow", "Orange",
			"Purple", "Pink", "Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold", "Manual");
	List<String> animationNames = Arrays.asList("RainbowColors", "RainbowStripeColor", "OceanColors", "CloudColors",
			"LavaColors", "ForestColors", "PartyColors");

	private UIHelper uiHelper;
	private PianoLED app;

	public UI(PianoLED pianoLED) {
		this.app = pianoLED;
		this.uiHelper = new UIHelper(new ControlP5(pianoLED));
	}

	String getModeName(int n) {
		return modes.get(n);
	}

	JFrame newWindowFrame;

	// Instruction Buttton
	public void Instructions() {
		if (newWindowFrame == null) {
			// create a new window frame
			newWindowFrame = new JFrame("Instructions");
			newWindowFrame.setSize(600, 600);
			newWindowFrame.setLocationRelativeTo(null);
			newWindowFrame.setResizable(false);
			// create a JTextArea object and add it to a JScrollPane
			JTextArea textArea = new JTextArea("Short Instructions for PianoLED\n\n"
					+ "Your Arduino and MIDI devices should be auto selected all you have to do is click open\n"
					+ "to start using PianoLED.\n"
					+ "\nMake sure you upload the ArduinoPianoLED.ino file before using PianoLED app.\n"
					+ "\nFixLED: Fixes the leds position if you your strip is soldered together and not a single strip.\n"
					+ "\nReverse: Reverses the led strip directions useful if the default is not ideal for your setup.\n"
					+ "\nBG: Turns on background color for all modes,color and brightness adjustable.\n"
					+ "\nAvailable modes:\n"
					+ "- Default: Single led based control,fade rate, brightness & color can be adjusted\n"
					+ " with ui sliders,color wheel and premade color presets.\n"
					+ "- Splash: Multiple leds light up,fade rate, brightness,tail length & color can be adjusted.\n"
					+ "- Random: Single led based control, this time random colors only, fade & brightness still\n"
					+ "adjustable.\n"
					+ "- Gradient: Three new buttons Set LG,MG,RG each setting color for corresponding sides.\n"
					+ "Make sure you select color & set that as side color with LG,MG.RG buttons.If middle side\n"
					+ "color is not set the gradient will work in two sides color mode.\n"
					+ "\n- Velocity: Based on how hard the user is playing the notes,the single led will light up\n"
					+ "differently.\n"
					+ "- Split: Splits the led strip into two sides. The split point can be set by clicking on the key\n"
					+ "in the piano UI.\n"
					+ "\nMake sure you select color first,set the sides color with Set L, R & chose your split point.\n"
					+ "\nAnimation: Led strip lights up depending on what animation preset you selected.\n"
					+ "\nThat's all if you need more help check out the discord server for help & updates:\n"
					+ "discord: https://discord.com/invite/S6xmuX4Hx5\n"
					+ "\nPianoLED © 2023 by serifpersia. All rights reserved\n");
			textArea.setBackground(java.awt.Color.BLACK);
			textArea.setForeground(java.awt.Color.WHITE);
			textArea.setFont(new Font("Arial", Font.PLAIN, 14));
			JScrollPane scrollPane = new JScrollPane(textArea);
			newWindowFrame.add(scrollPane);
			newWindowFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					newWindowFrame.dispose();
					newWindowFrame = null;
				}
			});
			newWindowFrame.setVisible(true);
		}
	}

	public UIHelper buildUI() {

		uiHelper.addToggle("BGColor2", " BG", 90, 25, 15, 15, Color.RED, Color.WHITE, Color.GREEN);

		uiHelper.addSlider("Brightness", "  B", EFFECT_CONTROLS_X - 4, 65, 10, 69, MIN_BRIGHT, MAX_BRIGHT, DEF_BRIGHT,
				Color.BLUE, Color.BLACK, Color.RED);

		uiHelper.addSlider("FadeOnVal", "  F", EFFECT_CONTROLS_X - 15, 65, 10, 69, MIN_FADE_RATE, MAX_FADE_RATE,
				DEFAULT_FADE_RATE, Color.GREEN, Color.BLACK, Color.RED);

		uiHelper.addButton("CheckForUpdate", "Update", 620, 20, 45, 25);

		uiHelper.addButton("setLeftSideG", "Set LG", 705, 140, 30, 15).hide();
		uiHelper.addButton("setMiddleSideG", "Set MG", 735, 140, 30, 15).hide();
		uiHelper.addButton("setRightSideG", "Set RG", 765, 140, 30, 15).hide();

		uiHelper.addButton("setLeftSide", "Set L", 735, 140, 30, 15).hide();
		uiHelper.addButton("setRightSide", "Set R", 765, 140, 30, 15).hide();

		uiHelper.addButton("setBG", "Set BG", 670, 26, 30, 15).hide();

		uiHelper.addButton("Open", null, 725, 45, 50, 15);
		uiHelper.addButton("Refresh", null, 775, 45, 50, 15);

		addAnimationControls();

		uiHelper.addButton("leftArrow", "<", 380, 25, 30, 15, null, Color.BLUE, null);
		uiHelper.addButton("rightArrow", ">", 415, 25, 30, 15, null, Color.BLUE, null);

		// addButton(cp5, "AdvanceUser", null, 15, 15, 60, 15);
		uiHelper.addColorWheel(COLOR_WHEEL, EFFECT_CONTROLS_X + 15, 45, 100);

		int SPLASH_CONTROL_X = EFFECT_CONTROLS_X + 6;
		int SPLASH_CONTROL_Y = 60;
		addSplashControls(SPLASH_CONTROL_X, SPLASH_CONTROL_Y);
		addPianoRollControls(EFFECT_CONTROLS_X + 10, 30);
		uiHelper.addButton("Instructions", null, 15, 15, 60, 15);

		uiHelper.addToggle("BGColor", " BG", 700, 25, 15, 15, Color.RED, Color.WHITE, Color.GREEN);
		uiHelper.addToggle("stripDirection", "Reverse", 425, 42, 10, 8, Color.RED, Color.WHITE, Color.GREEN)
				.getCaptionLabel().alignX(ControlP5.CENTER);
		uiHelper.addToggle("Fix", "Fix LED", 390, 42, 10, 8, Color.RED, Color.WHITE, Color.GREEN).getCaptionLabel()
				.alignX(ControlP5.CENTER);

		uiHelper.addScrollableList("comlist", "Arduino Port", null, -1, 725, 15, 100, 110, 15, 15).close();
		uiHelper.addScrollableList("midi", "Midi Device", null, -1, 725, 30, 100, 110, 15, 15).close();
		uiHelper.addScrollableList("colorlist", "Color Preset", colorNames, 0, EFFECT_CONTROLS_X + 15, 30, 100, 100, 15,
				15);
		uiHelper.addScrollableList("modelist", "Mode", modes, 0, EFFECT_CONTROLS_X + 15, 15, 100, 100, 15, 15)
				.bringToFront();

		return uiHelper;
	}

	public void addPianoRollControls(int origX, int origY) {
		int x = origX;
		int y = origY;
		int h = 25;
		int w = 25;
		PFont font = app.createFont("Arial", 12);

		uiHelper.addScrollableList("midiout", "Midi Output Device", null, -1, EFFECT_CONTROLS_X + 15, y, 100, 110, 15,
				15);
		y += 25;
		uiHelper.addButton("PianoRollLoadMidi", "Load Midi File", EFFECT_CONTROLS_X + 15, y, 100, 15).hide();
		y += 25;
		uiHelper.addButton("PianoRollRewind", "|<<", x, y, h, w, Color.RED, Color.BLUE, Color.GREEN).hide()
				.getCaptionLabel().setFont(font);
		x += w + 2;
		//uiHelper.addButton("PianoRollBackwardFragment", "-" + PianoRoll.REWIND_FRAGMENT_SEC, x, y, h, w, Color.RED,
		//		Color.BLUE, Color.GREEN).hide().getCaptionLabel().setFont(font);
		x += w + 2;
		uiHelper.addButton("PianoRollPlayPause", ">", x, y, h, w, Color.RED, Color.BLUE, Color.GREEN).hide()
				.getCaptionLabel().setFont(font);
		x += w + 2;
		//uiHelper.addButton("PianoRollForwardFragment", "+" + PianoRoll.REWIND_FRAGMENT_SEC, x, y, h, w, Color.RED,
		//		Color.BLUE, Color.GREEN).hide().getCaptionLabel().setFont(font);
		x = origX;
		y += h + 5;
		// addToggle(cp5, "PianoRollFollowKey", "Teacher Mode", x, y, 15, 15, RED,
		// WHITE, GREEN).hide();

		uiHelper.getController("midiout").bringToFront();
	}

	public void addSplashControls(int x, int y) {
		int SPLASH_MIN_LEN = 5;
		int SPLASH_MAX_LEN = 15;
		int SPLASH_DEFAULT_LEN = 8;

		int SPLASH_MIN_TAIL_FADE = 1;
		int SPLASH_MAX_TAIL_FADE = 50;
		int SPLASH_DAFAULT_TAIL_FADE = 15;

		int SPLASH_MIN_VELO_BRI = 5;
		int SPLASH_MAX_VELO_BRI = 15;
		int SPLASH_DAFAULT_VELO_BRI = 10;

		int SPLASH_MIN_VELO_SPEED = 5;
		int SPLASH_MAX_VELO_SPEED = 15;
		int SPLASH_DAFAULT_VELO_SPEED = 10;

		int SPLASH_CONTROL_Y_STEP = 13;

		uiHelper.addSlider("splashMaxLength", "  L", EFFECT_CONTROLS_X + 7, 65, 10, 69, SPLASH_MIN_LEN, SPLASH_MAX_LEN,
				SPLASH_DEFAULT_LEN, Color.BLUE, Color.BLACK, Color.RED).hide();
		y += SPLASH_CONTROL_Y_STEP;

		uiHelper.addScrollableList(SPLASH_COLOR_LIST, "Color", splashColorNames, 0, EFFECT_CONTROLS_X + 15, 30, 100,
				100, 15, 15).hide();
		y += SPLASH_CONTROL_Y_STEP;

		uiHelper.addSlider("splashTailFade", "Tail Fade Rate", x, y, SPLASH_MIN_TAIL_FADE, SPLASH_MAX_TAIL_FADE,
				SPLASH_DAFAULT_TAIL_FADE).hide();
		y += SPLASH_CONTROL_Y_STEP;

		// addSlider("splashHeadFade", "Head Fade Rate", x, y,
		// SPLASH_MIN_HEAD_FADE, SPLASH_MAX_HEAD_FADE, SPLASH_DAFAULT_HEAD_FADE).hide();
		// y += SPLASH_CONTROL_Y_STEP;

		uiHelper.addSlider("splashVelocityBrightnessImpact", "Velocity To Bright", x, y, SPLASH_MIN_VELO_BRI,
				SPLASH_MAX_VELO_BRI, SPLASH_DAFAULT_VELO_BRI).hide();
		y += SPLASH_CONTROL_Y_STEP;

		uiHelper.addSlider("splashVelocitySpeedImpact", "Velocity To Speed", x, y, SPLASH_MIN_VELO_SPEED,
				SPLASH_MAX_VELO_SPEED, SPLASH_DAFAULT_VELO_SPEED).hide();
		y += SPLASH_CONTROL_Y_STEP;
	}

	public void setSplashColor(int n) {
		int last = splashColorNames.size() - 1;

		Color colorToSet = Color.BLACK;
		if (n == 0) { // spectrum
			colorToSet = Color.BLACK;
		} else if (n == last) {
			colorToSet = null;
		} else {
			colorToSet = presetColors[n - 1];
		}

		ColorWheel splashControl = getController(ColorWheel.class, COLOR_WHEEL);
		if (splashControl != null && colorToSet != null) {
			getController(ColorWheel.class, COLOR_WHEEL).setRGB(colorToSet.getRGB());
		}
	}

	public Color getSplashColor() {
//		int n = (int) getController(ScrollableList.class, SPLASH_COLOR_LIST).getValue();
		return  new Color(getController(ColorWheel.class, COLOR_WHEEL).getRGB());
	}

	public void addAnimationControls() {
		uiHelper.addScrollableList("animationlist", "Animations", animationNames, 0, EFFECT_CONTROLS_X + 15, 30, 100,
				100, 15, 15).hide();
	}

	public void setAnimationDefaults(int fadeRate, int brightness) {
		getController("FadeOnVal").setValue(fadeRate);
		getController("Brightness").setValue(brightness);
		getController("Brightness").setPosition(EFFECT_CONTROLS_X - 15, 65);
	}

	public void setDefaults(int fadeRate, int brightness) {
		getController("FadeOnVal").setValue(fadeRate);
		getController("Brightness").setValue(brightness);
		getController("Brightness").setPosition(EFFECT_CONTROLS_X - 4, 65);
	}

	public void hideAllControls() {
		hideBGControls();
		hideDefaultControls();
		hideSplashControls();
		hideRandomControls();
		hideGradientControls();
		hideVelocityControls();
		hideSplitControls();
		hideAnimationControls();
		hidePianoRollControls();
	}

	// BG Controls
	public void showBGControls() {
		setControllersVisible(getBGControllers(), true);
	}

	public void hideBGControls() {
		setControllersVisible(getBGControllers(), false);
	}

	// Default Controls
	public void showDefaultControls() {
		setControllersVisible(getDefaultControllers(), true);
	}

	public void hideDefaultControls() {
		setControllersVisible(getDefaultControllers(), false);
	}

	// Splash Controls
	public void showSplashControls() {
		setControllersVisible(getSplashControllers(), true);
		uiHelper.getController("modelist");
	}

	public void hideSplashControls() {
		setControllersVisible(getSplashControllers(), false);
	}

	// Random Controls
	public void showRandomControls() {
		setControllersVisible(getRandomControllers(), true);
	}

	public void hideRandomControls() {
		setControllersVisible(getRandomControllers(), false);
	}

	// Gradient Controls
	public void showGradientControls() {
		setControllersVisible(getGradinetControllers(), true);
	}

	public void hideGradientControls() {
		setControllersVisible(getGradinetControllers(), false);
	}

	// Velocity Controls
	public void showVelocityControls() {
		setControllersVisible(getVelocityControllers(), true);
	}

	public void hideVelocityControls() {
		setControllersVisible(getVelocityControllers(), false);
	}

	// Split Controls
	public void showSplitControls() {
		setControllersVisible(getSplitControllers(), true);
	}

	public void hideSplitControls() {
		setControllersVisible(getSplitControllers(), false);
	}

	// Animation Controls
	public void showAnimationControls() {
		setControllersVisible(getAnimationControllers(), true);
	}

	public void hideAnimationControls() {
		setControllersVisible(getAnimationControllers(), false);
	}

	// LearnMidi Controls
	public void showPianoRollControls() {
		setControllersVisible(getPianoRollControllers(), true);
	}

	public void hidePianoRollControls() {
		setControllersVisible(getPianoRollControllers(), false);
	}

	// BG List
	@SuppressWarnings("rawtypes")
	public List<Controller> getBGControllers() {
		List<Controller> cl = new ArrayList<>();
		cl.add(uiHelper.getController("setBG"));

		return cl;
	}

	// Default List
	@SuppressWarnings("rawtypes")
	public List<Controller> getDefaultControllers() {
		List<Controller> cl = new ArrayList<>();
		cl.add(uiHelper.getController("Brightness"));
		cl.add(uiHelper.getController("FadeOnVal"));
		cl.add(uiHelper.getController(COLOR_WHEEL));
		cl.add(uiHelper.getController("colorlist"));

		return cl;
	}

	// Splash List
	@SuppressWarnings("rawtypes")
	public List<Controller> getSplashControllers() {
		List<Controller> cl = new ArrayList<>();
		cl.add(uiHelper.getController("splashMaxLength"));
		cl.add(uiHelper.getController("FadeOnVal"));
		cl.add(uiHelper.getController("Brightness"));
		cl.add(uiHelper.getController("splashHeadFade"));
		cl.add(uiHelper.getController(SPLASH_COLOR_LIST));
		cl.add(uiHelper.getController(COLOR_WHEEL));

		// cl.add(ui.getController("splashVelocityBrightnessImpact"));
		// cl.add(ui.getController("splashVelocitySpeedImpact"));
		return cl;
	}

	// Random List
	@SuppressWarnings("rawtypes")
	public List<Controller> getRandomControllers() {
		List<Controller> cl = new ArrayList<>();

		cl.add(uiHelper.getController("Brightness"));
		cl.add(uiHelper.getController("FadeOnVal"));
		return cl;
	}

	// Gradient List
	@SuppressWarnings("rawtypes")
	public List<Controller> getGradinetControllers() {
		List<Controller> cl = new ArrayList<>();

		cl.add(uiHelper.getController("Brightness"));
		cl.add(uiHelper.getController("FadeOnVal"));
		cl.add(uiHelper.getController("colorlist"));
		cl.add(uiHelper.getController(COLOR_WHEEL));
		cl.add(uiHelper.getController("setLeftSideG"));
		cl.add(uiHelper.getController("setMiddleSideG"));
		cl.add(uiHelper.getController("setRightSideG"));

		return cl;
	}

	// Velocity List
	@SuppressWarnings("rawtypes")
	public List<Controller> getVelocityControllers() {
		List<Controller> cl = new ArrayList<>();

		cl.add(uiHelper.getController("Brightness"));
		cl.add(uiHelper.getController("FadeOnVal"));

		return cl;
	}

	// Split List
	@SuppressWarnings("rawtypes")
	public List<Controller> getSplitControllers() {
		List<Controller> cl = new ArrayList<>();

		cl.add(uiHelper.getController("Brightness"));
		cl.add(uiHelper.getController("FadeOnVal"));

		cl.add(uiHelper.getController("colorlist"));
		cl.add(uiHelper.getController("Color"));

		cl.add(uiHelper.getController("setLeftSide"));
		cl.add(uiHelper.getController("setRightSide"));
		return cl;
	}

	// Animation List
	@SuppressWarnings("rawtypes")
	public List<Controller> getAnimationControllers() {
		List<Controller> cl = new ArrayList<>();

		cl.add(uiHelper.getController("Brightness"));
		cl.add(uiHelper.getController("animationlist"));

		return cl;
	}

	// PianoRoll List
	@SuppressWarnings("rawtypes")
	public List<Controller> getPianoRollControllers() {
		List<Controller> cl = new ArrayList<>();

		cl.add(uiHelper.getController("midiout"));
		cl.add(uiHelper.getController("PianoRollLoadMidi"));
		cl.add(uiHelper.getController("PianoRollRewind"));
		cl.add(uiHelper.getController("PianoRollBackwardFragment"));
		cl.add(uiHelper.getController("PianoRollPlayPause"));
		cl.add(uiHelper.getController("PianoRollForwardFragment"));
		cl.add(uiHelper.getController("PianoRollFollowKey"));

		uiHelper.getController("midiout");
		return cl;
	}

	@SuppressWarnings("rawtypes")
	public void setControllersVisible(List<Controller> cl, boolean visible) {
		if (cl == null)
			return;
		for (Controller c : cl) {
			if (c != null) {
				c.setVisible(visible);
			}
		}
	}

	public Controller<?> getController(String name) {
		return uiHelper.getController(name);
	}

	public <T> T getController(Class<T> class1, String name) {

		return uiHelper.getController(class1, name);
	}

	public Color getPresetColors(int n) {
		return presetColors[n];
	}

	public void setSplashColorsToManual() {
		getController(ScrollableList.class, SPLASH_COLOR_LIST).setValue(splashColorNames.size() - 1);
	}

	public void setDefaultMode() {
		getController("modelist").setValue(0);
	}

	int[][] Keys = new int[88][2];
	int rectASizeX = 0;
	int rectBSizeX = 0;
	int rectBX = 795;

	void drawPiano() {
		// white keys
		// Initial x-coordinate of the first key
		int x = 0;
		for (int i = 0; i < whiteKeys.length; i++) {
			if (Keys[whiteKeys[i]][0] == 1) {
				app.fill(255, 0, 0);
			} else {
				app.fill(255);
			}
			// Draw the key at the current x-coordinate
			app.rect(x + 15, 64, 15, 70);
			app.fill(0);
			// Move the x-coordinate to the right by 10 pixels
			// to prepare for the next key
			x += 15;
		}
		// black keys
		for (int i = 0; i < blackKeys.length; i++) {
			if (Keys[blackKeys[i]][1] == 1) {
				app.fill(255, 0, 0);
			} else {
				app.fill(0);
			}
			// Use the x-coordinate from the list to draw each key
			app.rect(keyXCoordinates[i] + 15, 65, 8, 40);
		}
		// highlight piano size L&R boxes
		app.fill(0, 127);
		app.rect(15, 64, rectASizeX, 70);
		app.rect(rectBX, 64, rectBSizeX, 70);
		// block led strip with black color
		app.fill(0);
		app.rect(15, 54, rectASizeX, 10);
		app.rect(rectBX, 54, rectBSizeX, 10);
	}

	int stripLedNum = 176;
	int firstNoteSelected = 21;
	int lastNoteSelected = 108;

	int getStripLedNum() {
		return stripLedNum;
	}

	void setKeyboardSize(int counter) {
		PApplet.println("counter: " + counter);
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
		PApplet.println("Selected number led: " + stripLedNum);
		PApplet.println("Selected first note: " + firstNoteSelected);
		PApplet.println("Selected last note: " + lastNoteSelected);
	}

	int whiteKeyPitches[] = { 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55, 57,
			59, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95, 96, 98, 100, 101,
			103, 105, 107, 108 };
// List of white keys in a 88-key piano
	int whiteKeys[] = { 0, 2, 3, 5, 7, 8, 10, 12, 14, 15, 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36, 38, 39, 41,
			43, 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79, 80, 82, 84, 86,
			87 };
	int[] blackKeys = { 1, 4, 6, 9, 11, 13, 16, 18, 21, 23, 25, 28, 30, 33, 35, 37, 40, 42, 45, 47, 49, 52, 54, 57, 59,
			61, 64, 66, 69, 71, 73, 76, 78, 81, 83, 85 };
// Create a list of x-coordinates for each key
	int[] keyXCoordinates = { 11, 40, 56, 86, 101, 116, 145, 161, 191, 206, 221, 251, 266, 296, 311, 326, 356, 371, 401,
			416, 431, 461, 476, 506, 521, 536, 566, 581, 611, 626, 641, 671, 686, 715, 731, 746 };

	public int getNumPianoKeys() {
		return lastNoteSelected - firstNoteSelected + 1;
	}

	int leftMinPitch = 21;
	int leftMaxPitch;
	int rightMaxPitch = 108;

	void pianoKeyAction(int x, int y, boolean released) {
		for (int i = 0; i < whiteKeys.length; i++) {
			// Check if the mouse click was inside a white key
			if (x > i * 15 + 15 && x < (i + 1) * 15 + 15 && y > 64 && y < 134) {
				leftMaxPitch = whiteKeyPitches[i];
				if (released) {
					Keys[whiteKeys[i]][0] = 0;
				} else {
					Keys[whiteKeys[i]][0] = 1;
					PApplet.println("Left Max Pitch: " + leftMaxPitch);
				}
			}
		}
	}

	public void setSplashDefaults(int splashLen, int fadeRate, int splashColor, int brightness) {
		getController("splashMaxLength").setValue(splashLen);
		getController("FadeOnVal").setValue(fadeRate);
		getController(ScrollableList.class, SPLASH_COLOR_LIST).setValue(splashColor);
		getController("Brightness").setValue(brightness);
	}

	public int getLastNoteSelected() {
		return lastNoteSelected;
	}

	public int getFirstNoteSelected() {
		return firstNoteSelected;
	}

	public int getLeftMinPitch() {
		return leftMinPitch;
	}

	public int getLeftMaxPitch() {
		return leftMaxPitch;
	}

	public int getRightMaxPitch() {
		return rightMaxPitch;
	}

	public void setPianoKey(int pitch, int on) {
		Keys[pitch - 21][0] = on;
		Keys[pitch - 21][1] = on;
	}

	public void setColorWheelValue(Color selectedColor) {
		getController(ColorWheel.class, COLOR_WHEEL).setRGB(selectedColor.getRGB());
	}

	public Color getColorWheelValue() {
		return new Color(getController(ColorWheel.class, COLOR_WHEEL).getRGB());
	}

	public String getButtonCaption(String buttonName) {
		return getController(buttonName).getCaptionLabel().getText();
	}

	public void setButtonCaption(String buttonName, String caption) {
		getController(buttonName).getCaptionLabel().setText(caption);
	}

	public void setButtonBG(String buttonName, Color color) {
		getController(buttonName).setColorBackground(color.getRGB());
	}

	public boolean getToggleState(String toggleName) {
		return getController(Toggle.class, toggleName).getState();
	}

	public float getControllerValue(String string) {
		return getController("FadeOnVal").getValue();
	}
}*/