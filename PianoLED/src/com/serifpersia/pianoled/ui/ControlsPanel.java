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
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;
	private ColorPickerPanel colorPicker = new ColorPickerPanel();

	static int defaultBrighntessVal = 255;
	static int defaultFadeVal = 255;
	static int defaultMaxSplashLengthVal = 8;

	public static Color selectedColor = Color.RED;
	private Color customColor = Color.BLACK;

	private JButton btn_Load;
	private JButton btn_Save;
	private JButton btn_LeftArrow;
	private JButton btn_RightArrow;
	private JButton btnSet_BG;
	private JButton btn_LeftSide;
	private JButton btn_MiddleSide;
	private JButton btn_RightSide;
	private JRadioButton rdbtn_BG_Off;
	private JRadioButton rdbtn_BG_On;
	private JRadioButton rdbtn_FixLED_Off;
	private JRadioButton rdbtn_FixLED_On;
	private JRadioButton rdbtn_Reverse_Off;
	private JRadioButton rdbtn_Reverse_On;

	private JComboBox<?> cb_ColorPresets;

	static JSlider sld_Brightness;
	static JSlider sld_Fade;
	static JSlider sld_SplashMaxLenght;

	private JLabel lbl_PianoSize;

	static JTextField txt_R;
	static JTextField txt_G;
	static JTextField txt_B;

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

	public ControlsPanel(PianoLED pianoLED) {
		pianoController = pianoLED.getPianoController();
		modesController = new ModesController(pianoLED);
		init(pianoLED);
	}

	private void init(PianoLED pianoLED) {

		setBackground(new Color(0, 0, 0));
		setLayout(new BorderLayout(0, 0));

		JPanel pnlControls = new JPanel();
		pnlControls.setBackground(new Color(231, 76, 60));
		add(pnlControls, BorderLayout.NORTH);

		JLabel lblControls = new JLabel("Controls");
		lblControls.setBackground(new Color(0, 0, 0));
		lblControls.setHorizontalAlignment(SwingConstants.CENTER);
		lblControls.setForeground(Color.WHITE);
		lblControls.setFont(new Font("Tahoma", Font.BOLD, 40));
		pnlControls.add(lblControls);

		JPanel pnlMain = new JPanel();
		pnlMain.setBackground(new Color(0, 0, 0));
		add(pnlMain, BorderLayout.CENTER);
		pnlMain.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel pnl_Left = new JPanel();
		pnl_Left.setBackground(new Color(0, 0, 0));
		pnlMain.add(pnl_Left);

		JLabel lbl_Profile = new JLabel("Profile:");
		lbl_Profile.setForeground(new Color(255, 255, 255));
		lbl_Profile.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_LEDMode = new JLabel("LED Mode:");
		lbl_LEDMode.setForeground(new Color(255, 255, 255));
		lbl_LEDMode.setFont(new Font("Tahoma", Font.BOLD, 25));

		btn_Load = new JButton("Load");
		btn_Load.setFont(new Font("Tahoma", Font.BOLD, 25));

		btn_Save = new JButton("Save");
		btn_Save.setFont(new Font("Tahoma", Font.BOLD, 25));

		JComboBox<?> cb_LED_Modes = new JComboBox<Object>(modeEffectsNames.toArray(new String[0]));
		cb_LED_Modes.setFont(new Font("Tahoma", Font.BOLD, 25));
		cb_LED_Modes.addActionListener(e -> {
			int selectedIndex = cb_LED_Modes.getSelectedIndex();
			modesController.modeSelect(selectedIndex);
		});

		JLabel lbl_Animation = new JLabel("Animation:");
		lbl_Animation.setForeground(Color.WHITE);
		lbl_Animation.setFont(new Font("Tahoma", Font.BOLD, 25));

		JComboBox<?> cb_LED_Animations = new JComboBox<Object>(animationNames.toArray(new String[0]));
		cb_LED_Animations.setFont(new Font("Tahoma", Font.BOLD, 25));
		cb_LED_Animations.addActionListener(e -> {
			if (ModesController.AnimationOn) {
				int selectedIndex = cb_LED_Animations.getSelectedIndex();
				pianoController.animationlist(selectedIndex);
			}
		});

		JLabel lbl_Brightness = new JLabel("Brightness");
		lbl_Brightness.setForeground(Color.WHITE);
		lbl_Brightness.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_Fade = new JLabel("Fade:");
		lbl_Fade.setForeground(Color.WHITE);
		lbl_Fade.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_Splash = new JLabel("Splash:");
		lbl_Splash.setForeground(Color.WHITE);
		lbl_Splash.setFont(new Font("Tahoma", Font.BOLD, 25));

		sld_Brightness = new JSlider(0, 255, defaultBrighntessVal);
		sld_Brightness.setBackground(Color.BLACK);

		sld_Fade = new JSlider(0, 255, defaultFadeVal);
		sld_Fade.setBackground(Color.BLACK);

		sld_SplashMaxLenght = new JSlider(4, 16, defaultMaxSplashLengthVal);
		sld_SplashMaxLenght.setBackground(Color.BLACK);

		JLabel lbl_BGLight = new JLabel("BG Light:");
		lbl_BGLight.setForeground(Color.WHITE);
		lbl_BGLight.setFont(new Font("Tahoma", Font.BOLD, 25));

		rdbtn_BG_Off = new JRadioButton("Off");
		rdbtn_BG_Off.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_BG_Off.setBackground(new Color(0, 0, 0));
		rdbtn_BG_Off.setForeground(new Color(255, 255, 255));
		rdbtn_BG_Off.setFont(new Font("Tahoma", Font.BOLD, 16));

		rdbtn_BG_On = new JRadioButton("On");
		rdbtn_BG_On.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_BG_On.setBackground(new Color(0, 0, 0));
		rdbtn_BG_On.setForeground(new Color(255, 255, 255));
		rdbtn_BG_On.setFont(new Font("Tahoma", Font.BOLD, 16));

		btnSet_BG = createButton("Set BG");

		JLabel lbl_FixLED = new JLabel("Fix LED");
		lbl_FixLED.setForeground(Color.WHITE);
		lbl_FixLED.setFont(new Font("Tahoma", Font.BOLD, 25));

		rdbtn_FixLED_Off = new JRadioButton("Off");
		rdbtn_FixLED_Off.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_FixLED_Off.setForeground(Color.WHITE);
		rdbtn_FixLED_Off.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtn_FixLED_Off.setBackground(Color.BLACK);

		rdbtn_FixLED_On = new JRadioButton("On");
		rdbtn_FixLED_On.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_FixLED_On.setForeground(Color.WHITE);
		rdbtn_FixLED_On.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtn_FixLED_On.setBackground(Color.BLACK);

		JLabel lbl_FixLED_1 = new JLabel("Reverse LED:");
		lbl_FixLED_1.setForeground(Color.WHITE);
		lbl_FixLED_1.setFont(new Font("Tahoma", Font.BOLD, 22));

		rdbtn_Reverse_Off = new JRadioButton("Off");
		rdbtn_Reverse_Off.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_Reverse_Off.setForeground(Color.WHITE);
		rdbtn_Reverse_Off.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtn_Reverse_Off.setBackground(Color.BLACK);

		rdbtn_Reverse_On = new JRadioButton("On");
		rdbtn_Reverse_On.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_Reverse_On.setForeground(Color.WHITE);
		rdbtn_Reverse_On.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtn_Reverse_On.setBackground(Color.BLACK);

		lbl_PianoSize = new JLabel("Piano: " + GetUI.getNumPianoKeys() + " Keys");
		lbl_PianoSize.setForeground(Color.WHITE);
		lbl_PianoSize.setFont(new Font("Tahoma", Font.BOLD, 18));

		btn_LeftArrow = createButton("<");
		btn_RightArrow = createButton(">");
		// Group the radio buttons together
		ButtonGroup bgGroup = new ButtonGroup();
		bgGroup.add(rdbtn_BG_Off);
		bgGroup.add(rdbtn_BG_On);

		// Group the radio buttons together
		ButtonGroup fixLEDGroup = new ButtonGroup();
		fixLEDGroup.add(rdbtn_FixLED_Off);
		fixLEDGroup.add(rdbtn_FixLED_On);

		// Group the radio buttons together
		ButtonGroup reverseLEDGroup = new ButtonGroup();
		reverseLEDGroup.add(rdbtn_Reverse_Off);
		reverseLEDGroup.add(rdbtn_Reverse_On);

		GroupLayout gl_pnl_Left = new GroupLayout(pnl_Left);
		gl_pnl_Left.setHorizontalGroup(gl_pnl_Left.createParallelGroup(Alignment.LEADING).addGroup(gl_pnl_Left
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnl_Left
						.createSequentialGroup()
						.addGroup(gl_pnl_Left.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lbl_LEDMode, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lbl_Profile, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 147,
										Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnl_Left.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnl_Left.createSequentialGroup()
										.addComponent(btn_Load, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn_Save, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
								.addComponent(cb_LED_Modes, 0, 219, Short.MAX_VALUE))
						.addGap(2))
						.addGroup(gl_pnl_Left.createSequentialGroup().addGroup(gl_pnl_Left
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnl_Left.createSequentialGroup()
										.addComponent(lbl_Fade, GroupLayout.PREFERRED_SIZE, 147,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(sld_Fade, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
								.addGroup(gl_pnl_Left.createSequentialGroup()
										.addComponent(lbl_Brightness, GroupLayout.PREFERRED_SIZE, 147,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(sld_Brightness, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
								.addGroup(gl_pnl_Left.createSequentialGroup()
										.addComponent(lbl_Animation, GroupLayout.PREFERRED_SIZE, 147,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cb_LED_Animations, 0, 219, Short.MAX_VALUE))
								.addGroup(gl_pnl_Left.createSequentialGroup()
										.addGroup(gl_pnl_Left.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(lbl_BGLight, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lbl_Splash, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														147, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_pnl_Left.createParallelGroup(Alignment.LEADING)
												.addComponent(sld_SplashMaxLenght, GroupLayout.DEFAULT_SIZE, 219,
														Short.MAX_VALUE)
												.addGroup(gl_pnl_Left.createSequentialGroup()
														.addComponent(rdbtn_BG_Off, GroupLayout.PREFERRED_SIZE, 51,
																Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(rdbtn_BG_On, GroupLayout.DEFAULT_SIZE, 55,
																Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(btnSet_BG, GroupLayout.DEFAULT_SIZE, 109,
																Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED)))))
								.addGap(2))
						.addGroup(gl_pnl_Left.createSequentialGroup()
								.addGroup(gl_pnl_Left.createParallelGroup(Alignment.LEADING)
										.addComponent(lbl_FixLED_1, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
										.addComponent(lbl_FixLED, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_pnl_Left.createParallelGroup(Alignment.LEADING)
										.addComponent(rdbtn_Reverse_Off, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
										.addComponent(rdbtn_FixLED_Off, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
								.addGap(49)
								.addGroup(gl_pnl_Left.createParallelGroup(Alignment.TRAILING)
										.addComponent(rdbtn_Reverse_On, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
										.addComponent(rdbtn_FixLED_On, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)))
						.addGroup(gl_pnl_Left.createSequentialGroup()
								.addComponent(lbl_PianoSize, GroupLayout.PREFERRED_SIZE, 147,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn_LeftArrow, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn_RightArrow, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))));
		gl_pnl_Left.setVerticalGroup(gl_pnl_Left.createParallelGroup(Alignment.LEADING).addGroup(gl_pnl_Left
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(8).addComponent(lbl_Profile,
								GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
						.addComponent(btn_Save, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btn_Load, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGap(6)
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(8).addComponent(lbl_LEDMode,
								GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(1).addComponent(cb_LED_Modes,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.BASELINE)
						.addComponent(cb_LED_Animations, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(8).addComponent(lbl_Animation,
								GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbl_Brightness, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(5).addComponent(sld_Brightness,
								GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)))
				.addGap(0)
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(6).addComponent(lbl_Fade,
								GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(5).addComponent(sld_Fade,
								GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(6).addComponent(lbl_Splash,
								GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(5)
								.addComponent(sld_SplashMaxLenght, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
				.addGap(6)
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(3).addComponent(lbl_BGLight,
								GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(7).addComponent(rdbtn_BG_Off,
								GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(6).addComponent(rdbtn_BG_On,
								GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
						.addGroup(gl_pnl_Left.createSequentialGroup().addGap(3).addComponent(btnSet_BG,
								GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_FixLED, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
						.addGroup(gl_pnl_Left.createSequentialGroup()
								.addGroup(gl_pnl_Left.createParallelGroup(Alignment.BASELINE)
										.addComponent(rdbtn_FixLED_Off, GroupLayout.PREFERRED_SIZE, 29, Short.MAX_VALUE)
										.addComponent(rdbtn_FixLED_On, GroupLayout.PREFERRED_SIZE, 29, Short.MAX_VALUE))
								.addGap(2)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtn_Reverse_On, GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE)
						.addGroup(gl_pnl_Left.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_FixLED_1, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
								.addGroup(gl_pnl_Left.createSequentialGroup().addGap(2).addComponent(rdbtn_Reverse_Off,
										GroupLayout.PREFERRED_SIZE, 32, Short.MAX_VALUE))))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_pnl_Left.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_PianoSize, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
						.addComponent(btn_LeftArrow, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(btn_RightArrow, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addGap(38)));
		pnl_Left.setLayout(gl_pnl_Left);

		JPanel pnl_Right = new JPanel();
		pnl_Right.setBackground(new Color(0, 0, 0));
		pnlMain.add(pnl_Right);
		pnl_Right.setLayout(new BorderLayout(0, 0));

		JPanel pnl_Center = new JPanel();
		pnl_Center.setBackground(new Color(0, 0, 0));
		pnl_Right.add(pnl_Center, BorderLayout.CENTER);

		JLabel lblNewLabel_2 = new JLabel("Colors");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_Profile_1 = new JLabel("Color Preset:");
		lbl_Profile_1.setForeground(Color.WHITE);
		lbl_Profile_1.setFont(new Font("Tahoma", Font.BOLD, 25));

		cb_ColorPresets = new JComboBox<Object>(colorNames.toArray(new String[0]));
		cb_ColorPresets.setFont(new Font("Tahoma", Font.BOLD, 25));
		cb_ColorPresets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedColorName = (String) cb_ColorPresets.getSelectedItem();
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

		JLabel lbl_ColorPicker = new JLabel("Color Picker");
		lbl_ColorPicker.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ColorPicker.setForeground(Color.WHITE);
		lbl_ColorPicker.setFont(new Font("Tahoma", Font.BOLD, 40));

		JPanel pnl_ColorPicker = new JPanel();
		pnl_ColorPicker.setBackground(new Color(0, 0, 0));
		pnl_ColorPicker.setLayout(new BorderLayout(0, 0));

		pnl_ColorPicker.add(colorPicker);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));

		JPanel pnl_TextFields = new JPanel();
		pnl_TextFields.setBackground(new Color(0, 0, 0));
		GroupLayout gl_pnl_Center = new GroupLayout(pnl_Center);
		gl_pnl_Center.setHorizontalGroup(gl_pnl_Center.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnl_Center
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_pnl_Center.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_Center.createSequentialGroup()
								.addComponent(lbl_Profile_1, GroupLayout.PREFERRED_SIZE, 182,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cb_ColorPresets, 0, 176, Short.MAX_VALUE))
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
						.addComponent(lbl_ColorPicker, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_pnl_Center.createSequentialGroup().addGroup(gl_pnl_Center
								.createParallelGroup(Alignment.TRAILING)
								.addComponent(pnl_TextFields, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 343,
										Short.MAX_VALUE)
								.addGroup(gl_pnl_Center.createSequentialGroup()
										.addComponent(pnl_ColorPicker, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
										.addGap(18).addComponent(panel, GroupLayout.PREFERRED_SIZE, 46,
												GroupLayout.PREFERRED_SIZE)))
								.addGap(19)))
				.addContainerGap()));
		gl_pnl_Center.setVerticalGroup(gl_pnl_Center.createParallelGroup(Alignment.LEADING).addGroup(gl_pnl_Center
				.createSequentialGroup().addContainerGap().addComponent(lblNewLabel_2).addGap(18)
				.addGroup(gl_pnl_Center.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Profile_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(cb_ColorPresets, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(lbl_ColorPicker, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_pnl_Center.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
						.addComponent(pnl_ColorPicker, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(pnl_TextFields, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE).addGap(12)));
		panel.setLayout(new GridLayout(3, 0, 0, 0));

		btn_LeftSide = createButton("L");
		btn_MiddleSide = createButton("M");
		btn_RightSide = createButton("R");

		JLabel lbl_R = createColorSelectionLabel("R", 20);
		txt_R = createColorSelectionTextField("255", 18, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateColorSelected();
				updateColorPreset();
			}
		});

		JLabel lbl_G = createColorSelectionLabel("G", 20);
		txt_G = createColorSelectionTextField("255", 19, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateColorSelected();
				updateColorPreset();
			}
		});

		JLabel lbl_B = createColorSelectionLabel("B", 20);
		txt_B = createColorSelectionTextField("255", 18, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateColorSelected();
				updateColorPreset();
			}
		});

		panel.add(btn_LeftSide);
		panel.add(btn_MiddleSide);
		panel.add(btn_RightSide);

		pnl_TextFields.add(lbl_R);
		pnl_TextFields.add(txt_R);
		pnl_TextFields.add(lbl_G);
		pnl_TextFields.add(txt_G);
		pnl_TextFields.add(lbl_B);
		pnl_TextFields.add(txt_B);

		pnl_Center.setLayout(gl_pnl_Center);

		// Set a titled border with the title text
		// TitledBorder titledBorder = BorderFactory.createTitledBorder("Slider Title");
		sliderActions();
		radioButtonsActions();
		buttonActions(pianoLED);
	}

	private JButton createButton(String buttonText) {
		JButton button = new JButton(buttonText);
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(52, 152, 219));
		button.setFont(new Font("Tahoma", Font.BOLD, 16));
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(231, 76, 60));
			}

			public void mouseExited(MouseEvent e) {
				button.setBackground(new Color(52, 152, 219));
			}

		});
		return button;
	}

	private JLabel createColorSelectionLabel(String text, int fontSize) {
		JLabel label = new JLabel(text);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		return label;
	}

	private JTextField createColorSelectionTextField(String text, int fontSize, ActionListener actionListener) {
		JTextField textField = new JTextField(text);
		textField.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		textField.setColumns(3);
		textField.addActionListener(actionListener);
		return textField;
	}

	private void updateColorPreset() {
		cb_ColorPresets.setSelectedIndex(colorNames.size() - 1);
	}

	private void updateColorSelected() {
		int r = parseInt(txt_R);
		int g = parseInt(txt_G);
		int b = parseInt(txt_B);

		setTextFieldValue(txt_R, selectedColor.getRed());
		setTextFieldValue(txt_G, selectedColor.getGreen());
		setTextFieldValue(txt_B, selectedColor.getBlue());

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

	private void radioButtonsActions() { // Add an ActionListener to the radio buttons
		ActionListener rdb_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "On":
					if (e.getSource() == rdbtn_BG_On) {
						pianoController.bgToggle = true;
						pianoController.setLedBG(pianoController.bgToggle);
					} else if (e.getSource() == rdbtn_FixLED_On) {
						// Handle FixLED On
						pianoController.useFixedMapping = true;
					} else if (e.getSource() == rdbtn_Reverse_On) {
						// Handle Reverse On
						pianoController.stripReverse = true;
						pianoController.stripReverse(pianoController.stripReverse);
					}
					break;
				case "Off":
					if (e.getSource() == rdbtn_BG_Off) {
						pianoController.bgToggle = false;
						pianoController.setLedBG(pianoController.bgToggle);
					} else if (e.getSource() == rdbtn_FixLED_Off) {
						// Handle FixLED Off
						pianoController.useFixedMapping = false;
					} else if (e.getSource() == rdbtn_Reverse_Off) {
						// Handle Reverse Off
						pianoController.stripReverse = false;
						pianoController.stripReverse(pianoController.stripReverse);
					}
					break;
				default:
					// Handle other cases
					break;
				}
			}
		};
		rdbtn_BG_Off.addActionListener(rdb_listener);
		rdbtn_BG_On.addActionListener(rdb_listener);
		rdbtn_FixLED_Off.addActionListener(rdb_listener);
		rdbtn_FixLED_On.addActionListener(rdb_listener);
		rdbtn_Reverse_Off.addActionListener(rdb_listener);
		rdbtn_Reverse_On.addActionListener(rdb_listener);

	}

	private void buttonActions(PianoLED pianoLED) {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "btn_LeftSide":
					PianoController.LeftSideColor = selectedColor;
					PianoController.splitLeftColor = selectedColor;
					break;
				case "btn_MiddleSide":
					PianoController.MiddleSideColor = selectedColor;
					break;
				case "btn_RightSide":
					PianoController.RightSideColor = selectedColor;
					PianoController.splitRightColor = selectedColor;
					break;
				case "btn_Load":
					// TODO: Add logic for btn_Load click event
					break;
				case "btn_Save":
					// TODO: Add logic for btn_Save click event
					break;
				case "btn_LeftArrow":

					if (GetUI.counter <= 0) {
						return;
					}
					GetUI.counter--;
					GetUI.setKeyboardSize(GetUI.counter);
					lbl_PianoSize.setText("Piano: " + GetUI.getNumPianoKeys() + " Keys");
					pianoLED.getDrawPiano().repaint();
					break;
				case "btn_RightArrow":
					if (GetUI.counter >= 4) {
						return;
					}
					GetUI.counter++;

					GetUI.setKeyboardSize(GetUI.counter);
					lbl_PianoSize.setText("Piano: " + GetUI.getNumPianoKeys() + " Keys");
					pianoLED.getDrawPiano().repaint();
					break;
				case "btnSet_BG":
					if (pianoController.bgToggle) {
						pianoController.setBG();
					}
					break;
				default:
					break;
				}
			}
		};

		btn_LeftSide.addActionListener(buttonListener);
		btn_MiddleSide.addActionListener(buttonListener);
		btn_RightSide.addActionListener(buttonListener);
		btn_Load.addActionListener(buttonListener);
		btn_Save.addActionListener(buttonListener);
		btn_LeftArrow.addActionListener(buttonListener);
		btn_RightArrow.addActionListener(buttonListener);
		btnSet_BG.addActionListener(buttonListener);

		btn_LeftSide.setActionCommand("btn_LeftSide");
		btn_MiddleSide.setActionCommand("btn_MiddleSide");
		btn_RightSide.setActionCommand("btn_RightSide");
		btn_Load.setActionCommand("btn_Load");
		btn_Save.setActionCommand("btn_Save");
		btn_LeftArrow.setActionCommand("btn_LeftArrow");
		btn_RightArrow.setActionCommand("btn_RightArrow");
		btnSet_BG.setActionCommand("btnSet_BG");

	}

	private void sliderActions() {
		ChangeListener sliderChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (source == sld_Brightness) {
					int BrightnessVal = sld_Brightness.getValue();
					sld_Brightness.setToolTipText(Integer.toString(BrightnessVal));
					pianoController.BrightnessRate(BrightnessVal);
				} else if (source == sld_Fade) {
					int FadeVal = sld_Fade.getValue();
					sld_Fade.setToolTipText(Integer.toString(FadeVal));
					pianoController.FadeRate(FadeVal);
				} else if (source == sld_SplashMaxLenght) {
					int SplashLength = sld_SplashMaxLenght.getValue();
					sld_SplashMaxLenght.setToolTipText(Integer.toString(SplashLength));
					pianoController.SplashLengthRate(SplashLength);
				}
			}
		};

		sld_Brightness.addChangeListener(sliderChangeListener);
		sld_Fade.addChangeListener(sliderChangeListener);
		sld_SplashMaxLenght.addChangeListener(sliderChangeListener);

	}
}
