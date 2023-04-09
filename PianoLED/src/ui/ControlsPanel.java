package ui;

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

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	ColorPicker colorPicker = new ColorPicker();

	DrawPiano piano = new DrawPiano();

	private ModesController modesController;
	private PianoController pianoController;

	private JComboBox<Object> LEDEffects;

	public static Color selectedColor = Color.RED;
	static JTextField txtR;
	static JTextField txtG;
	static JTextField txtB;

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
			new Color(255, 80, 0) // Gold
	};

	List<String> modeEffectsNames = Arrays.asList("Default", "Splash", "Random", "Gradient", "Velocity", "Split",
			"Animation");

	List<String> colorNames = Arrays.asList("Full Spectrum", "White", "Red", "Green", "Blue", "Yellow", "Orange",
			"Purple", "Pink", "Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold");

	List<String> animationNames = Arrays.asList("RainbowColors", "RainbowStripeColor", "OceanColors", "CloudColors",
			"LavaColors", "ForestColors", "PartyColors");

	public void setPiano(DrawPiano piano) {
		this.piano = piano;
	}

	public JComboBox<Object> getModes() {
		return LEDEffects;
	}

	public ControlsPanel() {

		modesController = new ModesController();
		pianoController = new PianoController();

		setBackground(new Color(21, 25, 28));
		setLayout(null);

		JLabel lbControls = new JLabel("Controls");
		lbControls.setBounds(630, 10, 200, 45);
		lbControls.setHorizontalAlignment(SwingConstants.CENTER);
		lbControls.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbControls.setForeground(new Color(255, 255, 255));
		add(lbControls);

		JLabel lbLEDEffect = new JLabel("LED Effect");
		lbLEDEffect.setHorizontalAlignment(SwingConstants.LEFT);
		lbLEDEffect.setForeground(Color.WHITE);
		lbLEDEffect.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbLEDEffect.setBounds(10, 55, 200, 30);
		add(lbLEDEffect);

		JComboBox<?> LEDEffects = new JComboBox<Object>(modeEffectsNames.toArray(new String[0]));
		LEDEffects.setBounds(10, 94, 200, 25);
		add(LEDEffects);
		LEDEffects.addActionListener(e -> {
			int selectedIndex = LEDEffects.getSelectedIndex();
			modesController.modeSelect(selectedIndex);
		});

		JLabel lbAnimation = new JLabel("Animation");
		lbAnimation.setHorizontalAlignment(SwingConstants.LEFT);
		lbAnimation.setForeground(Color.WHITE);
		lbAnimation.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbAnimation.setBounds(10, 130, 200, 30);
		add(lbAnimation);

		JComboBox<?> Animations = new JComboBox<Object>(animationNames.toArray(new String[0]));
		Animations.setBounds(10, 171, 200, 25);
		add(Animations);
		Animations.addActionListener(e -> {
			if (ModesController.AnimationOn) {
				int selectedIndex = Animations.getSelectedIndex();
				System.out.println(selectedIndex);
				pianoController.animationlist(selectedIndex);
			}
		});

		JLabel lbBrightness = new JLabel("Brightness");
		lbBrightness.setBounds(10, 203, 200, 30);
		lbBrightness.setHorizontalAlignment(SwingConstants.LEFT);
		lbBrightness.setForeground(Color.WHITE);
		lbBrightness.setFont(new Font("Montserrat", Font.BOLD, 30));
		add(lbBrightness);

		sliderBrightness = new JSlider();
		sliderBrightness.setBounds(10, 244, 200, 25);
		sliderBrightness.setMinimum(0);
		sliderBrightness.setMaximum(255);
		sliderBrightness.setValue(defaultBrighntessVal);
		add(sliderBrightness);
		sliderBrightness.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				BrightnessVal = sliderBrightness.getValue();
				sliderBrightness.setToolTipText(Integer.toString(BrightnessVal));
				pianoController.BrightnessRate(BrightnessVal);
				System.out.println("Brightness Slider value: " + BrightnessVal);
			}
		});

		JLabel lbFade = new JLabel("Fade");
		lbFade.setBounds(10, 280, 200, 30);
		lbFade.setHorizontalAlignment(SwingConstants.LEFT);
		lbFade.setForeground(Color.WHITE);
		lbFade.setFont(new Font("Montserrat", Font.BOLD, 30));
		add(lbFade);

		sliderFade = new JSlider();
		sliderFade.setBounds(10, 321, 200, 25);
		sliderFade.setMinimum(0);
		sliderFade.setMaximum(255);
		sliderFade.setValue(defaultFadeVal);
		add(sliderFade);
		sliderFade.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				FadeVal = sliderFade.getValue();
				sliderFade.setToolTipText(Integer.toString(FadeVal));
				pianoController.FadeRate(FadeVal);
				System.out.println("Fade Slider value: " + FadeVal);
			}
		});

		JLabel lbSplashLength = new JLabel("Splash Length");
		lbSplashLength.setBounds(10, 357, 226, 30);
		lbSplashLength.setHorizontalAlignment(SwingConstants.LEFT);
		lbSplashLength.setForeground(Color.WHITE);
		lbSplashLength.setFont(new Font("Montserrat", Font.BOLD, 30));
		add(lbSplashLength);

		sliderMaxSplashLengthVal = new JSlider();
		sliderMaxSplashLengthVal.setBounds(10, 398, 200, 25);
		sliderMaxSplashLengthVal.setMinimum(5);
		sliderMaxSplashLengthVal.setMaximum(15);
		sliderMaxSplashLengthVal.setValue(defaultMaxSplashLengthVal);
		add(sliderMaxSplashLengthVal);
		sliderMaxSplashLengthVal.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SplashLength = sliderMaxSplashLengthVal.getValue();
				sliderFade.setToolTipText(Integer.toString(SplashLength));
				pianoController.SplashLengthRate(SplashLength);
				System.out.println("Fade Slider value: " + SplashLength);
			}
		});

		JLabel lbBgLight = new JLabel("Background Light");
		lbBgLight.setHorizontalAlignment(SwingConstants.LEFT);
		lbBgLight.setForeground(Color.WHITE);
		lbBgLight.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbBgLight.setBounds(10, 434, 290, 35);
		add(lbBgLight);

		String[] BgOptions = { "No", "Yes", };
		JComboBox<Object> BgLight = new JComboBox<Object>(BgOptions);
		BgLight.setBounds(10, 480, 200, 25);
		add(BgLight);
		BgLight.addActionListener(e -> {
			String selectedOption = (String) BgLight.getSelectedItem();
			if (selectedOption.equals("Yes")) {
				PianoController.bgToggle = true;
				PianoController.setLedBG(PianoController.bgToggle);
				System.out.println("Yes BG");
			} else {
				PianoController.bgToggle = false;
				PianoController.setLedBG(PianoController.bgToggle);
				System.out.println("No BG");
			}
		});

		JButton lbSetBg = new JButton("SetBG");
		lbSetBg.setOpaque(true);
		lbSetBg.setForeground(Color.BLACK);
		lbSetBg.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbSetBg.setFocusable(false);
		lbSetBg.setBorderPainted(false);
		lbSetBg.setBackground(Color.WHITE);
		lbSetBg.setBounds(10, 529, 115, 25);
		add(lbSetBg);
		lbSetBg.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (PianoController.bgToggle) {
					PianoController.setBG();
				}
			}
		});

		JLabel lbFixLED = new JLabel("Fix LED Strip");
		lbFixLED.setBounds(10, 565, 200, 30);
		lbFixLED.setHorizontalAlignment(SwingConstants.CENTER);
		lbFixLED.setForeground(Color.WHITE);
		lbFixLED.setFont(new Font("Montserrat", Font.BOLD, 30));
		add(lbFixLED);

		String[] fixLedOptions = { "No", "Yes", };
		JComboBox<?> FixLed = new JComboBox<Object>(fixLedOptions);
		FixLed.setBounds(10, 600, 200, 25);
		add(FixLed);
		FixLed.addActionListener(e -> {
			String selectedOption = (String) FixLed.getSelectedItem();
			if (selectedOption.equals("Yes")) {
				PianoController.useFixedMapping = true;
				System.out.println(PianoController.useFixedMapping);
			} else {
				PianoController.useFixedMapping = false;
				System.out.println(PianoController.useFixedMapping);
			}
		});
		JLabel lbColor_R = new JLabel("R");
		lbColor_R.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_R.setForeground(Color.WHITE);
		lbColor_R.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_R.setBounds(580, 473, 50, 25);
		add(lbColor_R);

		JLabel lbColor_G = new JLabel("G");
		lbColor_G.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_G.setForeground(Color.WHITE);
		lbColor_G.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_G.setBounds(640, 473, 49, 25);
		add(lbColor_G);

		JLabel lbColor_B = new JLabel("B");
		lbColor_B.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_B.setForeground(Color.WHITE);
		lbColor_B.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_B.setBounds(699, 473, 51, 25);
		add(lbColor_B);

		txtR = new JTextField("255");
		txtR.setHorizontalAlignment(SwingConstants.CENTER);
		txtR.setColumns(10);
		txtR.setBounds(580, 510, 50, 25);
		add(txtR);
		txtR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int r = Integer.parseInt(txtR.getText());
					int g = Integer.parseInt(txtG.getText());
					int b = Integer.parseInt(txtB.getText());
					Color color = new Color(r, g, b);
					float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
					colorPicker.setHue(hsb[0]);
					colorPicker.setSaturation(hsb[1]);
					colorPicker.setBrightness(hsb[2]);
					colorPicker.repaint();
					System.out.println(color);
				} catch (NumberFormatException ex) {
					// handle exception
				}
			}
		});

		txtG = new JTextField("255");
		txtG.setHorizontalAlignment(SwingConstants.CENTER);
		txtG.setColumns(10);
		txtG.setBounds(640, 510, 50, 25);
		add(txtG);
		txtG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int r = Integer.parseInt(txtR.getText());
					int g = Integer.parseInt(txtG.getText());
					int b = Integer.parseInt(txtB.getText());
					Color color = new Color(r, g, b);
					float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
					colorPicker.setHue(hsb[0]);
					colorPicker.setSaturation(hsb[1]);
					colorPicker.setBrightness(hsb[2]);
					colorPicker.repaint();

				} catch (NumberFormatException ex) {
					// handle exception
				}
			}
		});

		txtB = new JTextField("255");
		txtB.setHorizontalAlignment(SwingConstants.CENTER);
		txtB.setColumns(10);
		txtB.setBounds(700, 510, 50, 25);
		add(txtB);
		txtB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int r = Integer.parseInt(txtR.getText());
					int g = Integer.parseInt(txtG.getText());
					int b = Integer.parseInt(txtB.getText());
					Color color = new Color(r, g, b);
					float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
					colorPicker.setHue(hsb[0]);
					colorPicker.setSaturation(hsb[1]);
					colorPicker.setBrightness(hsb[2]);
					colorPicker.repaint();
				} catch (NumberFormatException ex) {
					// handle exception
				}
			}
		});

		JLabel lblReverseLedStrip = new JLabel("Reverse LED Strip");
		lblReverseLedStrip.setHorizontalAlignment(SwingConstants.CENTER);
		lblReverseLedStrip.setForeground(Color.WHITE);
		lblReverseLedStrip.setFont(new Font("Montserrat", Font.BOLD, 17));
		lblReverseLedStrip.setBounds(630, 565, 165, 30);
		add(lblReverseLedStrip);

		String[] reverseOptions = { "No", "Yes", };
		JComboBox<Object> ReverseLed = new JComboBox<Object>(reverseOptions);
		ReverseLed.setBounds(630, 600, 165, 25);
		add(ReverseLed);
		ReverseLed.addActionListener(e -> {
			String selectedOption = (String) ReverseLed.getSelectedItem();
			if (selectedOption.equals("Yes")) {
				PianoController.stripReverse = true;
				PianoController.stripReverse(PianoController.stripReverse);

				System.out.println("Yes Reverse " + PianoController.stripReverse);
			} else {
				PianoController.stripReverse = false;
				PianoController.stripReverse(PianoController.stripReverse);
				System.out.println("No Reverse " + PianoController.stripReverse);
			}
		});

		JLabel lbPianoSize = new JLabel("Piano: " + GetUI.getNumPianoKeys() + " Keys");
		lbPianoSize.setBounds(300, 605, 211, 30);
		lbPianoSize.setHorizontalAlignment(SwingConstants.LEFT);
		lbPianoSize.setForeground(Color.WHITE);
		lbPianoSize.setFont(new Font("Montserrat", Font.BOLD, 27));
		add(lbPianoSize);

		JButton lbLeftArrow = new JButton("<");
		lbLeftArrow.setOpaque(true);
		lbLeftArrow.setForeground(Color.BLACK);
		lbLeftArrow.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbLeftArrow.setFocusable(false);
		lbLeftArrow.setBorderPainted(false);
		lbLeftArrow.setBackground(Color.WHITE);
		lbLeftArrow.setBounds(330, 640, 70, 25);
		add(lbLeftArrow);
		lbLeftArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (GetUI.counter <= 0) {
					return;
				}
				GetUI.counter--;
				GetUI.setKeyboardSize(GetUI.counter);
				lbPianoSize.setText("Piano: " + GetUI.getNumPianoKeys() + " Keys");
				piano.repaint();
			}
		});

		JButton lbRightArrow = new JButton(">");
		lbRightArrow.setOpaque(true);
		lbRightArrow.setForeground(Color.BLACK);
		lbRightArrow.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbRightArrow.setFocusable(false);
		lbRightArrow.setBorderPainted(false);
		lbRightArrow.setBackground(Color.WHITE);
		lbRightArrow.setBounds(410, 640, 70, 25);
		add(lbRightArrow);
		lbRightArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GetUI.counter >= 4) {
					return;
				}
				GetUI.counter++;

				GetUI.setKeyboardSize(GetUI.counter);
				lbPianoSize.setText("Piano: " + GetUI.getNumPianoKeys() + " Keys");
				piano.repaint();
			}
		});

		JLabel lbColors = new JLabel("Colors");
		lbColors.setHorizontalAlignment(SwingConstants.CENTER);
		lbColors.setForeground(Color.WHITE);
		lbColors.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColors.setBounds(555, 171, 240, 30);
		add(lbColors);

		JComboBox<?> colorPresets = new JComboBox<Object>(colorNames.toArray(new String[0]));
		colorPresets.setBounds(555, 230, 240, 25);
		add(colorPresets);
		colorPresets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedColorName = (String) colorPresets.getSelectedItem();
				int index = colorNames.indexOf(selectedColorName);
				if (index >= 1) {
					selectedColor = presetColors[index - 1];
					System.out.println("Preset Color selected: " + selectedColorName);

					// Update text fields with RGB values of selected color
					txtR.setText(String.valueOf(selectedColor.getRed()));
					txtG.setText(String.valueOf(selectedColor.getGreen()));
					txtB.setText(String.valueOf(selectedColor.getBlue()));

					// Update color picker with HSB values of selected color
					float[] hsb = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(),
							selectedColor.getBlue(), null);
					colorPicker.setHue(hsb[0]);
					colorPicker.setSaturation(hsb[1]);
					colorPicker.setBrightness(hsb[2]);
					colorPicker.repaint();
				}
				if (index == 0) {
					selectedColor = Color.BLACK;
				}
			}
		});

		colorPicker.setBounds(555, 271, 240, 200);
		add(colorPicker);

		piano.setBounds(0, 670, 795, 70);
		piano.setBackground(new Color(21, 25, 28));
		add(piano);
	}
}
