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
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;

	private JComboBox<Object> LEDEffects;

	public static Color selectedColor = Color.RED;
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
			new Color(255, 80, 0) // Gold
	};

	List<String> modeEffectsNames = Arrays.asList("Default", "Splash", "Random", "Gradient", "Velocity", "Split",
			"Animation");

	List<String> colorNames = Arrays.asList("Full Spectrum", "White", "Red", "Green", "Blue", "Yellow", "Orange",
			"Purple", "Pink", "Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold");

	List<String> animationNames = Arrays.asList("RainbowColors", "RainbowStripeColor", "OceanColors", "CloudColors",
			"LavaColors", "ForestColors", "PartyColors");
	static JTextField txtR;
	static JTextField txtG;
	static JTextField txtB;

	public JComboBox<Object> getModes() {
		return LEDEffects;
	}

	public ControlsPanel() {

		modesController = new ModesController();
		pianoController = new PianoController();

		setBackground(new Color(21, 25, 28));
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
		MainPane.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel LeftPane = new JPanel();
		LeftPane.setBackground(new Color(21, 25, 28));
		MainPane.add(LeftPane);
		LeftPane.setLayout(new GridLayout(15, 0, 0, 0));

		JLabel lbLEDEffect = new JLabel("LED Effect");
		lbLEDEffect.setHorizontalAlignment(SwingConstants.LEFT);
		lbLEDEffect.setForeground(Color.WHITE);
		lbLEDEffect.setFont(new Font("Tahoma", Font.BOLD, 25));
		LeftPane.add(lbLEDEffect);

		JComboBox<?> LEDEffects = new JComboBox<Object>(modeEffectsNames.toArray(new String[0]));
		LEDEffects.setFont(new Font("Tahoma", Font.BOLD, 25));
		LEDEffects.setBackground(new Color(255, 255, 255));
		LeftPane.add(LEDEffects);
		LEDEffects.addActionListener(e -> {
			int selectedIndex = LEDEffects.getSelectedIndex();
			modesController.modeSelect(selectedIndex);
		});

		JLabel lbAnimation = new JLabel("Animation");
		lbAnimation.setHorizontalAlignment(SwingConstants.LEFT);
		lbAnimation.setForeground(Color.WHITE);
		lbAnimation.setFont(new Font("Tahoma", Font.BOLD, 25));

		LeftPane.add(lbAnimation);

		JComboBox<?> Animations = new JComboBox<Object>(animationNames.toArray(new String[0]));
		Animations.setFont(new Font("Tahoma", Font.BOLD, 25));
		Animations.setBackground(new Color(255, 255, 255));
		Animations.setBounds(10, 171, 200, 25);
		LeftPane.add(Animations);
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
		LeftPane.add(lbBrightness);

		sliderBrightness = new JSlider();
		sliderBrightness.setBackground(new Color(21, 25, 28));
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
				System.out.println("Brightness Slider value: " + BrightnessVal);
			}
		});

		JLabel lbFade = new JLabel("Fade");
		lbFade.setBounds(10, 280, 200, 30);
		lbFade.setHorizontalAlignment(SwingConstants.LEFT);
		lbFade.setForeground(Color.WHITE);
		lbFade.setFont(new Font("Montserrat", Font.BOLD, 30));
		LeftPane.add(lbFade);

		sliderFade = new JSlider();
		sliderFade.setBackground(new Color(21, 25, 28));
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
				System.out.println("Fade Slider value: " + FadeVal);
			}
		});

		JLabel lbSplashLength = new JLabel("Splash Length");
		lbSplashLength.setBounds(10, 357, 226, 30);
		lbSplashLength.setHorizontalAlignment(SwingConstants.LEFT);
		lbSplashLength.setForeground(Color.WHITE);
		lbSplashLength.setFont(new Font("Montserrat", Font.BOLD, 30));
		LeftPane.add(lbSplashLength);

		sliderMaxSplashLengthVal = new JSlider();
		sliderMaxSplashLengthVal.setBackground(new Color(21, 25, 28));
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
				System.out.println("Fade Slider value: " + SplashLength);
			}
		});

		JLabel lbBgLight = new JLabel("Background Light");
		lbBgLight.setHorizontalAlignment(SwingConstants.LEFT);
		lbBgLight.setForeground(Color.WHITE);
		lbBgLight.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbBgLight.setBounds(10, 434, 290, 35);
		LeftPane.add(lbBgLight);

		String[] BgOptions = { "No", "Yes", };
		JComboBox<Object> BgLight = new JComboBox<Object>(BgOptions);
		BgLight.setFont(new Font("Tahoma", Font.BOLD, 25));
		BgLight.setBackground(new Color(255, 255, 255));
		BgLight.setBounds(10, 480, 200, 25);
		LeftPane.add(BgLight);
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
		lbSetBg.setForeground(new Color(255, 255, 255));
		lbSetBg.setFont(new Font("Tahoma", Font.BOLD, 25));
		lbSetBg.setFocusable(false);
		lbSetBg.setBorderPainted(false);
		lbSetBg.setBackground(new Color(52, 152, 219));
		lbSetBg.setBounds(10, 529, 115, 25);
		LeftPane.add(lbSetBg);
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
		LeftPane.add(lbFixLED);

		String[] fixLedOptions = { "No", "Yes", };
		JComboBox<?> FixLed = new JComboBox<Object>(fixLedOptions);
		FixLed.setForeground(new Color(0, 0, 0));
		FixLed.setFont(new Font("Tahoma", Font.BOLD, 25));
		FixLed.setBackground(new Color(255, 255, 255));
		FixLed.setBounds(10, 600, 200, 25);
		LeftPane.add(FixLed);
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

		JPanel RightPane = new JPanel();
		RightPane.setBackground(new Color(21, 25, 28));
		MainPane.add(RightPane);
		RightPane.setLayout(new GridLayout(3, 1, 0, 0));

		JLabel lbColors = new JLabel("Colors");
		lbColors.setHorizontalAlignment(SwingConstants.CENTER);
		lbColors.setForeground(Color.WHITE);
		lbColors.setFont(new Font("Tahoma", Font.BOLD, 25));
		lbColors.setBounds(555, 171, 240, 30);
		RightPane.add(lbColors);

		JComboBox<?> colorPresets = new JComboBox<Object>(colorNames.toArray(new String[0]));
		colorPresets.setBackground(new Color(21, 25, 28));
		colorPresets.setForeground(new Color(255, 255, 255));
		colorPresets.setFont(new Font("Tahoma", Font.BOLD, 25));
		colorPresets.setBounds(555, 230, 240, 25);
		RightPane.add(colorPresets);

		JPanel ColorPickerPane = new JPanel();
		RightPane.add(ColorPickerPane);
		ColorPickerPane.setLayout(new GridLayout(0, 1, 0, 0));

		ColorPicker colorPicker = new ColorPicker();
		ColorPickerPane.add(colorPicker);

		JLabel lbColor_R = new JLabel("R");
		lbColor_R.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_R.setForeground(Color.WHITE);
		lbColor_R.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_R.setBounds(196, 11, 22, 37);
		colorPicker.add(lbColor_R);

		JLabel lbColor_G = new JLabel("G");
		lbColor_G.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_G.setForeground(Color.WHITE);
		lbColor_G.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_G.setBounds(196, 68, 23, 37);
		colorPicker.add(lbColor_G);

		JLabel lbColor_B = new JLabel("B");
		lbColor_B.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_B.setForeground(Color.WHITE);
		lbColor_B.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_B.setBounds(196, 116, 23, 37);
		colorPicker.add(lbColor_B);

		txtR = new JTextField("255");
		txtR.setHorizontalAlignment(SwingConstants.CENTER);
		txtR.setColumns(10);
		txtR.setBounds(230, 20, 86, 20);
		colorPicker.add(txtR);
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
		txtG.setBounds(230, 76, 86, 20);
		colorPicker.add(txtG);
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
					System.out.println(color);
				} catch (NumberFormatException ex) {
					// handle exception
				}
			}
		});

		txtB = new JTextField("255");
		txtB.setHorizontalAlignment(SwingConstants.CENTER);
		txtB.setColumns(10);
		txtB.setBounds(229, 123, 86, 20);
		colorPicker.add(txtB);
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
					System.out.println(color);
				} catch (NumberFormatException ex) {
					// handle exception
				}
			}
		});
		
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

		String[] reverseOptions = { "Reverse LED Strip - No", "Reverse LED Strip -Yes", };
		JComboBox<Object> ReverseLed = new JComboBox<Object>(reverseOptions);
		ReverseLed.setFont(new Font("Tahoma", Font.BOLD, 14));
		ReverseLed.setBounds(206, 155, 196, 25);
		colorPicker.add(ReverseLed);

		JButton btnL = new JButton("L");
		btnL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PianoController.splitLeftColor = selectedColor;
				PianoController.LeftSideGColor = selectedColor;
			}
		});
		btnL.setForeground(Color.WHITE);
		btnL.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnL.setFocusable(false);
		btnL.setBorderPainted(false);
		btnL.setBackground(new Color(52, 152, 219));
		btnL.setBounds(326, 16, 50, 40);
		colorPicker.add(btnL);

		JButton btnM = new JButton("M");
		btnM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PianoController.MiddleSideColor = selectedColor;

			}
		});
		btnM.setForeground(Color.WHITE);
		btnM.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnM.setFocusable(false);
		btnM.setBorderPainted(false);
		btnM.setBackground(new Color(52, 152, 219));
		btnM.setBounds(326, 60, 50, 40);
		colorPicker.add(btnM);

		JButton btnR = new JButton("R");
		btnR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PianoController.splitRightColor = selectedColor;
				PianoController.RightSideGColor = selectedColor;
			}
		});
		btnR.setForeground(Color.WHITE);
		btnR.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnR.setFocusable(false);
		btnR.setBorderPainted(false);
		btnR.setBackground(new Color(52, 152, 219));
		btnR.setBounds(326, 104, 50, 40);
		colorPicker.add(btnR);
		ReverseLed.addActionListener(e -> {
			String selectedOption = (String) ReverseLed.getSelectedItem();
			if (selectedOption.equals("Reverse LED Strip -Yes")) {
				PianoController.stripReverse = true;
				PianoController.stripReverse(PianoController.stripReverse);

				System.out.println("Yes Reverse " + PianoController.stripReverse);
			} else {
				PianoController.stripReverse = false;
				PianoController.stripReverse(PianoController.stripReverse);
				System.out.println("No Reverse " + PianoController.stripReverse);
			}
		});

		JPanel PianoPane = new JPanel();
		PianoPane.setBackground(new Color(21, 25, 28));
		add(PianoPane, BorderLayout.SOUTH);
		PianoPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnLeftArrow = new JButton("<");
		btnLeftArrow.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnLeftArrow.setBackground(new Color(52, 152, 219));
		btnLeftArrow.setForeground(Color.WHITE);
		btnLeftArrow.setFocusable(false);
		btnLeftArrow.setBorderPainted(false);
		PianoPane.add(btnLeftArrow);
		btnLeftArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (GetUI.counter <= 0) {
					return;
				}
				GetUI.counter--;
				GetUI.setKeyboardSize(GetUI.counter);
				lbPianoSize.setText("Piano: " + GetUI.getNumPianoKeys() + " Keys");
				BottomPanel.piano.repaint();
			}
		});

		JButton btnRightArrow = new JButton(">");
		btnRightArrow.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnRightArrow.setBackground(new Color(52, 152, 219));
		btnRightArrow.setForeground(Color.WHITE);
		btnRightArrow.setFocusable(false);
		btnRightArrow.setBorderPainted(false);
		PianoPane.add(btnRightArrow);
		btnRightArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GetUI.counter >= 4) {
					return;
				}
				GetUI.counter++;

				GetUI.setKeyboardSize(GetUI.counter);
				lbPianoSize.setText("Piano: " + GetUI.getNumPianoKeys() + " Keys");
				BottomPanel.piano.repaint();
			}
		});

		lbPianoSize = new JLabel("Piano: " + GetUI.getNumPianoKeys() + " Keys");
		lbPianoSize.setHorizontalAlignment(SwingConstants.CENTER);
		lbPianoSize.setFont(new Font("Tahoma", Font.BOLD, 30));
		lbPianoSize.setForeground(Color.WHITE);
		PianoPane.add(lbPianoSize);
	}
}