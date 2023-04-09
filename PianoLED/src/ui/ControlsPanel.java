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

	List<String> colorNames = Arrays.asList("White", "Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink",
			"Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold");

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
		lbLEDEffect.setBounds(10, 50, 200, 45);
		add(lbLEDEffect);

		LEDEffects = new JComboBox<Object>();
		LEDEffects.setBounds(10, 100, 200, 25);
		LEDEffects.addItem("Default");
		LEDEffects.addItem("Splash");
		LEDEffects.addItem("Random");
		LEDEffects.addItem("Gradient");
		LEDEffects.addItem("Velocity");
		LEDEffects.addItem("Split");
		LEDEffects.addItem("Animation");
		add(LEDEffects);
		LEDEffects.addActionListener(e -> {
			int selectedIndex = LEDEffects.getSelectedIndex();
			modesController.modeSelect(selectedIndex);
		});

		JLabel lbPianoSize = new JLabel("Piano: " + GetUI.getNumPianoKeys() + " Keys");
		lbPianoSize.setBounds(560, 555, 235, 45);
		lbPianoSize.setHorizontalAlignment(SwingConstants.LEFT);
		lbPianoSize.setForeground(Color.WHITE);
		lbPianoSize.setFont(new Font("Montserrat", Font.BOLD, 30));
		add(lbPianoSize);

		JLabel lbFixLED = new JLabel("Fix LED Strip");
		lbFixLED.setBounds(10, 555, 200, 45);
		lbFixLED.setHorizontalAlignment(SwingConstants.CENTER);
		lbFixLED.setForeground(Color.WHITE);
		lbFixLED.setFont(new Font("Montserrat", Font.BOLD, 30));
		add(lbFixLED);

		String[] options = { "No", "Yes", };
		JComboBox<?> FixLed = new JComboBox<Object>(options);
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

		JButton lbLeftArrow = new JButton("<");

		lbLeftArrow.setOpaque(true);
		lbLeftArrow.setForeground(Color.BLACK);
		lbLeftArrow.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbLeftArrow.setFocusable(false);
		lbLeftArrow.setBorderPainted(false);
		lbLeftArrow.setBackground(Color.WHITE);
		lbLeftArrow.setBounds(600, 611, 72, 41);
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
		lbRightArrow.setBounds(682, 611, 72, 41);
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
		lbColors.setBounds(482, 95, 209, 47);
		add(lbColors);

		JComboBox<?> colorPresets = new JComboBox<Object>(colorNames.toArray(new String[0]));
		colorPresets.setBounds(475, 150, 240, 25);
		add(colorPresets);
		colorPresets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedColorName = (String) colorPresets.getSelectedItem();
				int index = colorNames.indexOf(selectedColorName);
				if (index >= 0) {
					selectedColor = presetColors[index];
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
			}
		});

		JLabel lbColor_R = new JLabel("R:");
		lbColor_R.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_R.setForeground(Color.WHITE);
		lbColor_R.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_R.setBounds(339, 482, 92, 46);
		add(lbColor_R);

		JLabel lbColor_G = new JLabel("G:");
		lbColor_G.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_G.setForeground(Color.WHITE);
		lbColor_G.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_G.setBounds(474, 482, 92, 46);
		add(lbColor_G);

		JLabel lbColor_B = new JLabel("B:");
		lbColor_B.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_B.setForeground(Color.WHITE);
		lbColor_B.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_B.setBounds(599, 482, 92, 46);
		add(lbColor_B);

		txtR = new JTextField("255");
		txtR.setColumns(10);
		txtR.setBounds(413, 487, 79, 35);
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
		txtG.setColumns(10);
		txtG.setBounds(540, 487, 79, 35);
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
		txtB.setColumns(10);
		txtB.setBounds(675, 487, 79, 35);
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

		JLabel lbBrightness = new JLabel("Brightness");
		lbBrightness.setBounds(10, 136, 200, 45);
		lbBrightness.setHorizontalAlignment(SwingConstants.LEFT);
		lbBrightness.setForeground(Color.WHITE);
		lbBrightness.setFont(new Font("Montserrat", Font.BOLD, 30));
		add(lbBrightness);

		sliderBrightness = new JSlider();
		sliderBrightness.setBounds(10, 192, 200, 25);
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
		lbFade.setBounds(10, 230, 200, 45);
		lbFade.setHorizontalAlignment(SwingConstants.LEFT);
		lbFade.setForeground(Color.WHITE);
		lbFade.setFont(new Font("Montserrat", Font.BOLD, 30));
		add(lbFade);

		sliderFade = new JSlider();
		sliderFade.setBounds(10, 284, 200, 25);
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
		lbSplashLength.setBounds(10, 320, 226, 45);
		lbSplashLength.setHorizontalAlignment(SwingConstants.LEFT);
		lbSplashLength.setForeground(Color.WHITE);
		lbSplashLength.setFont(new Font("Montserrat", Font.BOLD, 30));
		add(lbSplashLength);

		sliderMaxSplashLengthVal = new JSlider();
		sliderMaxSplashLengthVal.setBounds(10, 376, 200, 25);
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

		colorPicker.setBounds(475, 250, 240, 200);
		add(colorPicker);

		piano.setBounds(0, 670, 795, 70);
		piano.setBackground(new Color(21, 25, 28));
		add(piano);

		piano.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// Call the pianoKeyAction method with the mouse coordinates and the pressed
				// flag set to true
				piano.pianoKeyAction(e.getX(), e.getY(), true);
			}

			public void mouseReleased(MouseEvent e) {
				// Call the pianoKeyAction method with the mouse coordinates and the pressed
				// flag set to false
				piano.pianoKeyAction(e.getX(), e.getY(), false);
			}
		});
	}
}
