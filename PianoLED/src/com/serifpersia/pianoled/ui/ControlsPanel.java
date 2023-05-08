package com.serifpersia.pianoled.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.ModesController;
import com.serifpersia.pianoled.PianoController;
import com.serifpersia.pianoled.PianoLED;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;
	ColorPickerPanel colorPicker = new ColorPickerPanel();

	private JComboBox<Object> LEDEffects;
	static JComboBox<?> colorPresets;

	public static Color selectedColor = Color.RED;
	public Color customColor = Color.BLACK;
	JLabel lbPianoSize = new JLabel();

	static int BrightnessVal;
	static int FadeVal;
	static int SplashLength;

	static JSlider sliderBrightness;
	static JSlider sliderFade;
	static JSlider sliderMaxSplashLengthVal;

	static int defaultBrighntessVal = 255;
	static int defaultFadeVal = 255;
	static int defaultMaxSplashLengthVal = 8;

	Color[] presetColors = { Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, new Color(255, 100, 0), // Yellow
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

	List<String> modeEffectsNames = Arrays.asList("Default", "Splash", "Random", "Gradient", "Velocity", "Split",
			"Animation");

	static List<String> colorNames = Arrays.asList("Full Spectrum", "White", "Red", "Green", "Blue", "Yellow", "Orange",
			"Purple", "Pink", "Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold", "Custom");

	List<String> animationNames = Arrays.asList("RainbowColors", "RainbowStripeColor", "OceanColors", "CloudColors",
			"LavaColors", "ForestColors", "PartyColors");
	static JTextField txtR;
	static JTextField txtG;
	static JTextField txtB;

	public JComboBox<Object> getModes() {
		return LEDEffects;
	}

	public ControlsPanel(PianoLED pianoLED) {

		pianoController = pianoLED.getPianoController();
		modesController = new ModesController(pianoLED);

		setBackground(Color.BLACK);
		setLayout(new BorderLayout(0, 0));

		JPanel ControlsPane = new JPanel();
		ControlsPane.setBackground(new Color(231, 76, 60));
		add(ControlsPane, BorderLayout.NORTH);

		JLabel lbControls = new JLabel("Controls");
		lbControls.setHorizontalAlignment(SwingConstants.CENTER);
		lbControls.setForeground(Color.WHITE);
		lbControls.setFont(new Font("Tahoma", Font.BOLD, 30));
		ControlsPane.add(lbControls);

		JPanel MainPane = new JPanel();
		MainPane.setBackground(Color.BLUE);
		add(MainPane, BorderLayout.CENTER);
		MainPane.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel LeftPane = new JPanel();
		LeftPane.setBackground(Color.BLACK);
		MainPane.add(LeftPane);
		LeftPane.setLayout(new GridLayout(16, 0, 0, 0));

		JLabel lbLEDEffect = new JLabel("LED Effect");
		lbLEDEffect.setHorizontalAlignment(SwingConstants.LEFT);
		lbLEDEffect.setForeground(Color.WHITE);
		lbLEDEffect.setFont(new Font("Tahoma", Font.BOLD, 30));
		LeftPane.add(lbLEDEffect);

		JComboBox<?> LEDEffects = new JComboBox<Object>(modeEffectsNames.toArray(new String[0]));
		LEDEffects.setForeground(new Color(255, 255, 255));
		LEDEffects.setFont(new Font("Tahoma", Font.BOLD, 25));
		LEDEffects.setBackground(Color.BLACK);
		LeftPane.add(LEDEffects);
		LEDEffects.addActionListener(e -> {
			int selectedIndex = LEDEffects.getSelectedIndex();
			modesController.modeSelect(selectedIndex);
		});

		JLabel lbAnimation = new JLabel("Animation");
		lbAnimation.setBackground(Color.BLACK);
		lbAnimation.setHorizontalAlignment(SwingConstants.LEFT);
		lbAnimation.setForeground(new Color(255, 255, 255));
		lbAnimation.setFont(new Font("Tahoma", Font.BOLD, 30));

		LeftPane.add(lbAnimation);

		JComboBox<?> Animations = new JComboBox<Object>(animationNames.toArray(new String[0]));
		Animations.setForeground(new Color(255, 255, 255));
		Animations.setFont(new Font("Tahoma", Font.BOLD, 25));
		Animations.setBackground(Color.BLACK);
		Animations.setBounds(10, 171, 200, 25);
		LeftPane.add(Animations);
		Animations.addActionListener(e -> {
			if (ModesController.AnimationOn) {
				int selectedIndex = Animations.getSelectedIndex();
				pianoController.animationlist(selectedIndex);
			}
		});

		JLabel lbBrightness = new JLabel("Brightness");
		lbBrightness.setBounds(10, 203, 200, 30);
		lbBrightness.setHorizontalAlignment(SwingConstants.LEFT);
		lbBrightness.setForeground(Color.WHITE);
		lbBrightness.setFont(new Font("Tahoma", Font.BOLD, 30));
		LeftPane.add(lbBrightness);

		sliderBrightness = new JSlider();
		sliderBrightness.setBackground(Color.BLACK);
		sliderBrightness.setBounds(10, 244, 200, 25);
		sliderBrightness.setMinimum(0);
		sliderBrightness.setMaximum(255);
		sliderBrightness.setValue(defaultBrighntessVal);
		LeftPane.add(sliderBrightness);
		sliderBrightness.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				BrightnessVal = sliderBrightness.getValue();
				sliderBrightness.setToolTipText(Integer.toString(BrightnessVal));
				pianoController.BrightnessRate(BrightnessVal);
			}
		});

		JLabel lbFade = new JLabel("Fade");
		lbFade.setBounds(10, 280, 200, 30);
		lbFade.setHorizontalAlignment(SwingConstants.LEFT);
		lbFade.setForeground(Color.WHITE);
		lbFade.setFont(new Font("Tahoma", Font.BOLD, 30));
		LeftPane.add(lbFade);

		sliderFade = new JSlider();
		sliderFade.setBackground(Color.BLACK);
		sliderFade.setBounds(10, 321, 200, 25);
		sliderFade.setMinimum(0);
		sliderFade.setMaximum(255);
		sliderFade.setValue(defaultFadeVal);
		LeftPane.add(sliderFade);
		sliderFade.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				FadeVal = sliderFade.getValue();
				sliderFade.setToolTipText(Integer.toString(FadeVal));
				pianoController.FadeRate(FadeVal);
			}
		});

		JLabel lbSplashLength = new JLabel("Splash Length");
		lbSplashLength.setBounds(10, 357, 226, 30);
		lbSplashLength.setHorizontalAlignment(SwingConstants.LEFT);
		lbSplashLength.setForeground(Color.WHITE);
		lbSplashLength.setFont(new Font("Tahoma", Font.BOLD, 30));
		LeftPane.add(lbSplashLength);

		sliderMaxSplashLengthVal = new JSlider();
		sliderMaxSplashLengthVal.setBackground(Color.BLACK);
		sliderMaxSplashLengthVal.setBounds(10, 398, 200, 25);
		sliderMaxSplashLengthVal.setMinimum(5);
		sliderMaxSplashLengthVal.setMaximum(15);
		sliderMaxSplashLengthVal.setValue(defaultMaxSplashLengthVal);
		LeftPane.add(sliderMaxSplashLengthVal);
		sliderMaxSplashLengthVal.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SplashLength = sliderMaxSplashLengthVal.getValue();
				sliderFade.setToolTipText(Integer.toString(SplashLength));
				pianoController.SplashLengthRate(SplashLength);
			}
		});

		String[] BgOptions = { "No", "Yes", };

		JLabel lbBgLight = new JLabel("Background Light");
		lbBgLight.setHorizontalAlignment(SwingConstants.LEFT);
		lbBgLight.setForeground(Color.WHITE);
		lbBgLight.setFont(new Font("Tahoma", Font.BOLD, 30));
		lbBgLight.setBounds(10, 434, 290, 35);
		LeftPane.add(lbBgLight);
		JComboBox<Object> BgLight = new JComboBox<Object>(BgOptions);
		BgLight.setForeground(new Color(255, 255, 255));
		BgLight.setFont(new Font("Tahoma", Font.BOLD, 25));
		BgLight.setBackground(Color.BLACK);
		BgLight.setBounds(10, 480, 200, 25);
		LeftPane.add(BgLight);
		BgLight.addActionListener(e -> {
			String selectedOption = (String) BgLight.getSelectedItem();
			if (selectedOption.equals("Yes")) {
				pianoController.bgToggle = true;
				pianoController.setLedBG(pianoController.bgToggle);
			} else {
				pianoController.bgToggle = false;
				pianoController.setLedBG(pianoController.bgToggle);
			}
		});

		JButton lbSetBg = new JButton("SetBG");
		lbSetBg.setOpaque(true);
		lbSetBg.setForeground(new Color(255, 255, 255));
		lbSetBg.setFont(new Font("Tahoma", Font.BOLD, 25));
		lbSetBg.setFocusable(false);
		lbSetBg.setBorderPainted(false);
		lbSetBg.setBackground(new Color(231, 76, 60));
		lbSetBg.setBounds(10, 529, 115, 25);
		LeftPane.add(lbSetBg);
		lbSetBg.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (pianoController.bgToggle) {
					pianoController.setBG();
				}
			}
		});

		String[] fixLedOptions = { "Fix LED Strip - No", "Fix LED Strip - Yes", };
		JComboBox<?> FixLed = new JComboBox<Object>(fixLedOptions);
		FixLed.setForeground(new Color(255, 255, 255));
		FixLed.setFont(new Font("Tahoma", Font.BOLD, 25));
		FixLed.setBackground(Color.BLACK);
		FixLed.setBounds(10, 600, 200, 25);
		LeftPane.add(FixLed);
		FixLed.addActionListener(e -> {
			String selectedOption = (String) FixLed.getSelectedItem();
			if (selectedOption.equals("Fix LED Strip - Yes")) {
				pianoController.useFixedMapping = true;
			} else {
				pianoController.useFixedMapping = false;
			}
		});

		String[] reverseOptions = { "Reverse LED Strip - No", "Reverse LED Strip - Yes", };
		JComboBox<Object> ReverseLed = new JComboBox<Object>(reverseOptions);
		ReverseLed.setFont(new Font("Tahoma", Font.BOLD, 25));
		ReverseLed.setForeground(new Color(255, 255, 255));
		ReverseLed.setBackground(new Color(0, 0, 0));
		LeftPane.add(ReverseLed);
		ReverseLed.addActionListener(e -> {
			String selectedOption = (String) ReverseLed.getSelectedItem();
			if (selectedOption.equals("Reverse LED Strip - Yes")) {
				pianoController.stripReverse = true;
				pianoController.stripReverse(pianoController.stripReverse);
			} else {
				pianoController.stripReverse = false;
				pianoController.stripReverse(pianoController.stripReverse);
			}
		});

		JPanel PianoSizeButtonsPane = new JPanel();
		PianoSizeButtonsPane.setBackground(new Color(0, 0, 0));
		LeftPane.add(PianoSizeButtonsPane);
		PianoSizeButtonsPane.setLayout(new GridLayout(1, 2, 0, 0));

		JButton btnLeftArrow = new JButton("<");
		PianoSizeButtonsPane.add(btnLeftArrow);
		btnLeftArrow.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnLeftArrow.setBackground(new Color(52, 152, 219));
		btnLeftArrow.setForeground(Color.WHITE);
		btnLeftArrow.setFocusable(false);
		btnLeftArrow.setBorderPainted(false);
		btnLeftArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (GetUI.counter <= 0) {
					return;
				}
				GetUI.counter--;
				GetUI.setKeyboardSize(GetUI.counter);
				lbPianoSize.setText("Piano: " + GetUI.getNumPianoKeys() + " Keys");
				pianoLED.getDrawPiano().repaint();
			}
		});

		JButton btnRightArrow = new JButton(">");
		PianoSizeButtonsPane.add(btnRightArrow);
		btnRightArrow.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnRightArrow.setBackground(new Color(52, 152, 219));
		btnRightArrow.setForeground(Color.WHITE);
		btnRightArrow.setFocusable(false);
		btnRightArrow.setBorderPainted(false);

		JPanel RightPane = new JPanel();
		RightPane.setBackground(new Color(0, 0, 0));
		MainPane.add(RightPane);
		RightPane.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel topColorsPane = new JPanel();
		topColorsPane.setBackground(new Color(0, 0, 0));
		RightPane.add(topColorsPane);
		topColorsPane.setLayout(new BorderLayout(0, 0));

		JLabel lbColor = new JLabel("Color");
		lbColor.setFont(new Font("Tahoma", Font.BOLD, 40));
		lbColor.setForeground(new Color(255, 255, 255));
		lbColor.setHorizontalAlignment(SwingConstants.CENTER);
		topColorsPane.add(lbColor, BorderLayout.CENTER);

		colorPresets = new JComboBox<Object>(colorNames.toArray(new String[0]));
		colorPresets.setForeground(new Color(255, 255, 255));
		colorPresets.setBackground(new Color(0, 0, 0));
		colorPresets.setFont(new Font("Tahoma", Font.BOLD, 25));
		topColorsPane.add(colorPresets, BorderLayout.SOUTH);
		colorPresets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedColorName = (String) colorPresets.getSelectedItem();
				int index = colorNames.indexOf(selectedColorName);
				if (index >= 1) {
					selectedColor = presetColors[index - 1];
					updateColorSelected();
				}
				if (index == 0) {
					selectedColor = Color.BLACK;
				}
			}
		});

		JPanel bottomColorPane = new JPanel();
		bottomColorPane.setBackground(new Color(0, 0, 0));
		RightPane.add(bottomColorPane);
		bottomColorPane.setLayout(new BorderLayout(0, 0));

		JPanel bottomLeftPane = new JPanel();
		bottomLeftPane.setBackground(new Color(0, 0, 0));
		bottomColorPane.add(bottomLeftPane, BorderLayout.CENTER);
		bottomLeftPane.setLayout(new BorderLayout(0, 0));

		bottomLeftPane.add(colorPicker);

		JPanel bottomRightPane = new JPanel();
		bottomRightPane.setBackground(new Color(0, 0, 0));
		bottomColorPane.add(bottomRightPane, BorderLayout.EAST);
		bottomRightPane.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel RL = new JPanel();
		RL.setBackground(new Color(0, 0, 0));
		bottomRightPane.add(RL);
		RL.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel("R");
		lblNewLabel.setBackground(new Color(255, 0, 0));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RL.add(lblNewLabel);

		txtR = new JTextField("255");
		txtR.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtR.setForeground(new Color(255, 255, 255));
		txtR.setBackground(new Color(0, 0, 0));
		txtR.setHorizontalAlignment(SwingConstants.CENTER);
		RL.add(txtR);
		txtR.setColumns(10);
		txtR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateColorSelected();
				updateColorPreset();

			}
		});

		JButton btnLeftGradient = new JButton("L");
		btnLeftGradient.setForeground(new Color(255, 255, 255));
		btnLeftGradient.setBackground(new Color(52, 152, 219));
		btnLeftGradient.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftGradient.setBorderPainted(false);
		btnLeftGradient.setFocusable(false);
		RL.add(btnLeftGradient);
		btnLeftGradient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				PianoController.LeftSideColor = selectedColor;
				PianoController.splitLeftColor = selectedColor;

			}
		});

		JPanel GM = new JPanel();
		GM.setBackground(new Color(0, 0, 0));
		bottomRightPane.add(GM);

		JLabel lblNewLabel_1 = new JLabel("G");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setBackground(Color.RED);
		GM.add(lblNewLabel_1);

		txtG = new JTextField("255");
		txtG.setHorizontalAlignment(SwingConstants.CENTER);
		txtG.setForeground(new Color(255, 255, 255));
		txtG.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtG.setColumns(10);
		txtG.setBackground(new Color(0, 0, 0));
		GM.add(txtG);
		txtG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateColorSelected();
				updateColorPreset();

			}
		});

		JButton btnMiddleGradient = new JButton("M");
		btnMiddleGradient.setForeground(Color.WHITE);
		btnMiddleGradient.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnMiddleGradient.setBackground(new Color(52, 152, 219));
		btnMiddleGradient.setBorderPainted(false);
		btnMiddleGradient.setFocusable(false);
		GM.add(btnMiddleGradient);
		btnMiddleGradient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				PianoController.MiddleSideColor = selectedColor;

			}
		});

		JPanel BR = new JPanel();
		BR.setBackground(new Color(0, 0, 0));
		bottomRightPane.add(BR);

		JLabel lblNewLabel_1_1 = new JLabel("B");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1_1.setBackground(Color.RED);
		BR.add(lblNewLabel_1_1);

		txtB = new JTextField("255");
		txtB.setHorizontalAlignment(SwingConstants.CENTER);
		txtB.setForeground(new Color(255, 255, 255));
		txtB.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtB.setColumns(10);
		txtB.setBackground(new Color(0, 0, 0));
		BR.add(txtB);
		txtB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateColorSelected();
				updateColorPreset();

			}
		});

		JButton btnRightGradient = new JButton("R");
		btnRightGradient.setForeground(Color.WHITE);
		btnRightGradient.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnRightGradient.setBackground(new Color(52, 152, 219));
		btnRightGradient.setBorderPainted(false);
		btnRightGradient.setFocusable(false);
		BR.add(btnRightGradient);
		btnRightGradient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				PianoController.RightSideColor = selectedColor;
				PianoController.splitRightColor = selectedColor;

			}
		});

		btnRightArrow.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (GetUI.counter >= 4) {
					return;
				}
				GetUI.counter++;

				GetUI.setKeyboardSize(GetUI.counter);
				lbPianoSize.setText("Piano: " + GetUI.getNumPianoKeys() + " Keys");
				pianoLED.getDrawPiano().repaint();
			}
		});

		JPanel PianoSizePane = new JPanel();
		PianoSizePane.setBackground(new Color(0, 0, 0));

		add(PianoSizePane, BorderLayout.SOUTH);
		PianoSizePane.setLayout(new GridLayout(1, 0, 0, 0));

		lbPianoSize = new JLabel("Piano: " + GetUI.getNumPianoKeys() + " Keys");
		lbPianoSize.setHorizontalAlignment(SwingConstants.CENTER);
		lbPianoSize.setForeground(Color.WHITE);
		lbPianoSize.setFont(new Font("Tahoma", Font.BOLD, 30));
		PianoSizePane.add(lbPianoSize);
	}

	static void updateColorPreset() {
		colorPresets.setSelectedIndex(colorNames.size() - 1);
	}

	private void updateColorSelected() {
		int r = parseInt(txtR);
		int g = parseInt(txtG);
		int b = parseInt(txtB);

		setTextFieldValue(txtR, selectedColor.getRed());
		setTextFieldValue(txtG, selectedColor.getGreen());
		setTextFieldValue(txtB, selectedColor.getBlue());

		customColor = new Color(r, g, b);

		float[] hsb = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), null);
		colorPicker.setCustomColor(hsb[0], hsb[1], hsb[2]);
		colorPicker.repaint();
		presetColors[presetColors.length - 1] = customColor;
	}

	private int parseInt(JTextField textField) {
		return Integer.parseInt(textField.getText());
	}

	private void setTextFieldValue(JTextField textField, int value) {
		textField.setText(String.valueOf(value));
	}

}