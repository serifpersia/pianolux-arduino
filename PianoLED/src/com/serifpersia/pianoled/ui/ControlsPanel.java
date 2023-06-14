package com.serifpersia.pianoled.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import com.serifpersia.pianoled.ModesController;
import com.serifpersia.pianoled.PianoController;
import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.Profile;
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
import javax.swing.JTabbedPane;
import java.awt.CardLayout;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;
	private ColorPickerPanel colorPicker = new ColorPickerPanel();

	static int defaultBrighntessVal = 255;
	static int defaultFadeVal = 0;
	static int defaultMaxSplashLengthVal = 8;

	private JButton btn_Load;
	private JButton btn_Save;
	private JButton btn_LeftArrow;
	private JButton btn_RightArrow;
	private JButton btnSet_BG;

	public static JRadioButton rdbtn_BG_Off;
	public static JRadioButton rdbtn_BG_On;
	public static JRadioButton rdbtn_FixLED_Off;
	public static JRadioButton rdbtn_FixLED_On;
	public static JRadioButton rdbtn_Reverse_Off;
	public static JRadioButton rdbtn_Reverse_On;

	public static JComboBox<?> cb_LED_Mode;
	public static JComboBox<?> cb_LED_Animations;
	public static JComboBox<?> cb_ColorPresets;

	public static JComboBox<?> cb_GradientSideList;

	public static JSlider sld_Brightness;
	public static JSlider sld_Fade;
	public static JSlider sld_SplashMaxLenght;

	private JLabel lbl_PianoSize;

	public static JTextField txt_R;
	public static JTextField txt_G;
	public static JTextField txt_B;

	public static ButtonGroup bgGroup;
	public static ButtonGroup fixLEDGroup;
	public static ButtonGroup reverseLEDGroup;
	private JLabel lb_Gradient2_SetSide1;
	private JButton btn_Gradient2_SetSide1;
	private JLabel lb_Gradient2_SetSide2;
	private JButton btn_Gradient2_SetSide2;
	private JLabel lb_Gradient3_SetSide1;
	private JButton btn_Gradient3_SetSide1;
	private JLabel lb_Gradient3_SetSide2;
	private JButton btn_Gradient3_SetSide2;
	private JLabel lb_Gradient3_SetSide3;
	private JButton btn_Gradient3_SetSide3;
	private JLabel lb_Gradient4_SetSide1;
	private JButton btn_Gradient4_SetSide1;
	private JLabel lb_Gradient4_SetSide2;
	private JButton btn_Gradient4_SetSide2;
	private JLabel lb_Gradient4_SetSide3;
	private JButton btn_Gradient4_SetSide3;
	private JLabel lb_Gradient4_SetSide4;
	private JButton btn_Gradient4_SetSide4;
	private JLabel lb_Gradient5_SetSide1;
	private JButton btn_Gradient5_SetSide1;
	private JLabel lb_Gradient5_SetSide2;
	private JButton btn_Gradient5_SetSide2;
	private JLabel lb_Gradient5_SetSide3;
	private JButton btn_Gradient5_SetSide3;
	private JLabel lb_Gradient5_SetSide4;
	private JButton btn_Gradient5_SetSide4;
	private JLabel lb_Gradient5_SetSide5;
	private JButton btn_Gradient5_SetSide5;
	private JLabel lb_Gradient6_SetSide1;
	private JButton btn_Gradient6_SetSide1;
	private JLabel lb_Gradient6_SetSide2;
	private JButton btn_Gradient6_SetSide2;
	private JLabel lb_Gradient6_SetSide3;
	private JButton btn_Gradient6_SetSide3;
	private JLabel lb_Gradient6_SetSide4;
	private JButton btn_Gradient6_SetSide4;
	private JLabel lb_Gradient6_SetSide5;
	private JButton btn_Gradient6_SetSide5;
	private JLabel lb_Gradient6_SetSide6;
	private JButton btn_Gradient6_SetSide6;
	private JButton btn_Gradient7_SetSide7;
	private JLabel lb_Gradient7_SetSide7;
	private JButton btn_Gradient7_SetSide6;
	private JLabel lb_Gradient7_SetSide6;
	private JButton btn_Gradient7_SetSide5;
	private JLabel lb_Gradient7_SetSide5;
	private JButton btn_Gradient7_SetSide4;
	private JLabel lb_Gradient7_SetSide4;
	private JButton btn_Gradient7_SetSide3;
	private JLabel lb_Gradient7_SetSide3;
	private JButton btn_Gradient7_SetSide2;
	private JLabel lb_Gradient7_SetSide2;
	private JButton btn_Gradient7_SetSide1;
	private JLabel lb_Gradient7_SetSide1;
	private JLabel lb_Gradient8_SetSide1;
	private JButton btn_Gradient8_SetSide1;
	private JLabel lb_Gradient8_SetSide2;
	private JButton btn_Gradient8_SetSide2;
	private JLabel lb_Gradient8_SetSide3;
	private JButton btn_Gradient8_SetSide3;
	private JLabel lb_Gradient8_SetSide4;
	private JButton btn_Gradient8_SetSide4;
	private JLabel lb_Gradient8_SetSide5;
	private JButton btn_Gradient8_SetSide5;
	private JLabel lb_Gradient8_SetSide6;
	private JButton btn_Gradient8_SetSide6;
	private JLabel lb_Gradient8_SetSide7;
	private JButton btn_Gradient8_SetSide7;
	private JLabel lb_Gradient8_SetSide8;
	private JButton btn_Gradient8_SetSide8;

	private int divisionCount = 2; // Specify the number of divisions
	public static Color[] colors = new Color[8]; // Declare and initialize the colors array
	private JPanel pnl_GradientPreview;
	private JSlider bg_slider;

	private void initializeColors() {

		colors[0] = Color.RED;
		colors[1] = Color.GREEN;
		colors[2] = Color.BLUE;
		colors[3] = Color.RED;
		colors[4] = Color.GREEN;
		colors[5] = Color.BLUE;
		colors[6] = Color.RED;
		colors[7] = Color.GREEN;
	}

	public ControlsPanel(PianoLED pianoLED) {
		pianoController = pianoLED.getPianoController();
		modesController = new ModesController(pianoLED);
		init(pianoLED);
		initializeColors();
	}

	private void init(PianoLED pianoLED) {

		setBackground(new Color(0, 0, 0));
		setLayout(new BorderLayout(0, 0));

		JPanel pnlControls = new JPanel();
		pnlControls.setBackground(new Color(231, 76, 60));
		add(pnlControls, BorderLayout.NORTH);
		pnlControls.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("<");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		pnlControls.add(lblNewLabel, BorderLayout.WEST);

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
		pnl_Left.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pnl_Left.add(tabbedPane, BorderLayout.CENTER);

		JPanel pnl_Controls = new JPanel();
		pnl_Controls.setBackground(new Color(0, 0, 0));
		tabbedPane.addTab("Controls", null, pnl_Controls, null);
		pnl_Controls.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel pnl_LeftLabels = new JPanel();
		pnl_LeftLabels.setBackground(new Color(0, 0, 0));
		pnl_Controls.add(pnl_LeftLabels);
		pnl_LeftLabels.setLayout(new GridLayout(11, 0, 0, 0));

		JLabel lblNewLabel_1_1 = new JLabel("Profile:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("LED Mode:");
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("Animation:");
		lblNewLabel_1_3.setForeground(Color.WHITE);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_4 = new JLabel("Brightness:");
		lblNewLabel_1_4.setForeground(Color.WHITE);
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1_4);

		JLabel lblNewLabel_1_5 = new JLabel("Fade:");
		lblNewLabel_1_5.setForeground(Color.WHITE);
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1_5);

		JLabel lblNewLabel_1_6 = new JLabel("Splash Lenght:");
		lblNewLabel_1_6.setForeground(Color.WHITE);
		lblNewLabel_1_6.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1_6);

		JLabel lblNewLabel_1_7 = new JLabel("BG Light:");
		lblNewLabel_1_7.setForeground(Color.WHITE);
		lblNewLabel_1_7.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1_7);

		JLabel lblNewLabel_1 = new JLabel("BG Brightness");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1);

		JLabel lblNewLabel_1_8 = new JLabel("Fix LEDs:");
		lblNewLabel_1_8.setForeground(Color.WHITE);
		lblNewLabel_1_8.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1_8);

		JLabel lblNewLabel_1_9 = new JLabel("Reverse LEDs:");
		lblNewLabel_1_9.setForeground(Color.WHITE);
		lblNewLabel_1_9.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lblNewLabel_1_9);

		lbl_PianoSize = new JLabel("Piano: " + GetUI.getNumPianoKeys() + " Keys");
		lbl_PianoSize.setForeground(new Color(255, 255, 255));
		lbl_PianoSize.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_LeftLabels.add(lbl_PianoSize);

		JPanel pnl_RightControls = new JPanel();
		pnl_RightControls.setBackground(new Color(0, 0, 0));
		pnl_Controls.add(pnl_RightControls);
		pnl_RightControls.setLayout(new GridLayout(11, 0, 0, 0));

		JPanel pnl_ProfileButtons = new JPanel();
		pnl_ProfileButtons.setBackground(Color.BLACK);
		pnl_RightControls.add(pnl_ProfileButtons);
		pnl_ProfileButtons.setLayout(new GridLayout(1, 0, 0, 0));

		btn_Load = new JButton("Load");
		btn_Load.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_ProfileButtons.add(btn_Load);

		btn_Save = new JButton("Save");
		btn_Save.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_ProfileButtons.add(btn_Save);

		JPanel pnl_LEDModes = new JPanel();
		pnl_LEDModes.setBackground(Color.BLACK);
		pnl_RightControls.add(pnl_LEDModes);
		pnl_LEDModes.setLayout(new GridLayout(1, 0, 0, 0));

		cb_LED_Mode = new JComboBox<Object>(GetUI.modeEffectsNames.toArray(new String[0]));
		cb_LED_Mode.setFont(new Font("Tahoma", Font.BOLD, 25));
		cb_LED_Mode.addActionListener(e -> {
			int selectedIndex = cb_LED_Mode.getSelectedIndex();
			modesController.modeSelect(selectedIndex);
		});
		pnl_LEDModes.add(cb_LED_Mode);

		JPanel pnl_Animations = new JPanel();
		pnl_Animations.setBackground(Color.BLACK);
		pnl_RightControls.add(pnl_Animations);
		pnl_Animations.setLayout(new GridLayout(1, 0, 0, 0));

		cb_LED_Animations = new JComboBox<Object>(GetUI.animationNames.toArray(new String[0]));
		cb_LED_Animations.setFont(new Font("Tahoma", Font.BOLD, 25));
		cb_LED_Animations.addActionListener(e -> {
			if (ModesController.AnimationOn) {
				int selectedIndex = cb_LED_Animations.getSelectedIndex();
				System.out.println(selectedIndex);
				pianoController.animationlist(selectedIndex);
			}
		});
		pnl_Animations.add(cb_LED_Animations);

		JPanel pnl_BSlder = new JPanel();
		pnl_BSlder.setBackground(Color.BLACK);
		pnl_RightControls.add(pnl_BSlder);
		pnl_BSlder.setLayout(new GridLayout(1, 0, 0, 0));

		sld_Brightness = new JSlider(0, 255, defaultBrighntessVal);
		sld_Brightness.setBackground(Color.BLACK);
		pnl_BSlder.add(sld_Brightness);

		JPanel panel_1_4 = new JPanel();
		panel_1_4.setBackground(Color.BLACK);
		pnl_RightControls.add(panel_1_4);
		panel_1_4.setLayout(new GridLayout(1, 0, 0, 0));

		sld_Fade = new JSlider(0, 255, defaultFadeVal);
		sld_Fade.setBackground(Color.BLACK);
		panel_1_4.add(sld_Fade);

		JPanel panel_1_5 = new JPanel();
		panel_1_5.setBackground(Color.BLACK);
		pnl_RightControls.add(panel_1_5);
		panel_1_5.setLayout(new GridLayout(1, 0, 0, 0));

		sld_SplashMaxLenght = new JSlider(4, 16, defaultMaxSplashLengthVal);
		sld_SplashMaxLenght.setBackground(Color.BLACK);
		panel_1_5.add(sld_SplashMaxLenght);

		JPanel panel_1_6 = new JPanel();
		panel_1_6.setBackground(Color.BLACK);
		pnl_RightControls.add(panel_1_6);
		panel_1_6.setLayout(new GridLayout(1, 0, 0, 0));

		bg_slider = new JSlider(10, 100);
		bg_slider.setBackground(Color.BLACK);
		bg_slider.setForeground(Color.WHITE);
		bg_slider.setSnapToTicks(true);
		bg_slider.setMajorTickSpacing(10);
		bg_slider.setPaintTicks(true);
		bg_slider.setPaintLabels(true);
		bg_slider.setValue(10);

		bg_slider.setLabelTable(bg_slider.createStandardLabels(10));
		pnl_RightControls.add(bg_slider);
		rdbtn_BG_On = new JRadioButton("On");
		rdbtn_BG_On.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_BG_On.setBackground(new Color(0, 0, 0));
		rdbtn_BG_On.setForeground(new Color(255, 255, 255));
		rdbtn_BG_On.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1_6.add(rdbtn_BG_On);

		rdbtn_BG_Off = new JRadioButton("Off");
		rdbtn_BG_Off.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_BG_Off.setBackground(new Color(0, 0, 0));
		rdbtn_BG_Off.setForeground(new Color(255, 255, 255));
		rdbtn_BG_Off.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1_6.add(rdbtn_BG_Off);

		btnSet_BG = createButton("Set BG");
		panel_1_6.add(btnSet_BG);

		JPanel panel_1_7 = new JPanel();
		panel_1_7.setBackground(Color.BLACK);
		pnl_RightControls.add(panel_1_7);
		panel_1_7.setLayout(new GridLayout(1, 0, 0, 0));

		rdbtn_FixLED_On = new JRadioButton("On");
		rdbtn_FixLED_On.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_FixLED_On.setForeground(Color.WHITE);
		rdbtn_FixLED_On.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtn_FixLED_On.setBackground(Color.BLACK);

		panel_1_7.add(rdbtn_FixLED_On);

		rdbtn_FixLED_Off = new JRadioButton("Off");
		rdbtn_FixLED_Off.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_FixLED_Off.setForeground(Color.WHITE);
		rdbtn_FixLED_Off.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtn_FixLED_Off.setBackground(Color.BLACK);
		panel_1_7.add(rdbtn_FixLED_Off);

		JPanel panel_1_8 = new JPanel();
		panel_1_8.setBackground(Color.BLACK);
		pnl_RightControls.add(panel_1_8);
		panel_1_8.setLayout(new GridLayout(1, 0, 0, 0));

		rdbtn_Reverse_On = new JRadioButton("On");
		rdbtn_Reverse_On.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_Reverse_On.setForeground(Color.WHITE);
		rdbtn_Reverse_On.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtn_Reverse_On.setBackground(Color.BLACK);
		panel_1_8.add(rdbtn_Reverse_On);

		rdbtn_Reverse_Off = new JRadioButton("Off");
		rdbtn_Reverse_Off.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_Reverse_Off.setForeground(Color.WHITE);
		rdbtn_Reverse_Off.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtn_Reverse_Off.setBackground(Color.BLACK);

		panel_1_8.add(rdbtn_Reverse_Off);

		JPanel panel_1_9 = new JPanel();
		panel_1_9.setBackground(Color.BLACK);
		pnl_RightControls.add(panel_1_9);
		panel_1_9.setLayout(new GridLayout(1, 0, 0, 0));

		btn_LeftArrow = new JButton("<");
		btn_LeftArrow.setForeground(Color.WHITE);
		btn_LeftArrow.setBackground(new Color(52, 152, 219));
		btn_LeftArrow.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_LeftArrow.setFocusable(false);
		btn_LeftArrow.setBorderPainted(false);
		btn_LeftArrow.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				btn_LeftArrow.setBackground(new Color(231, 76, 60));
			}

			public void mouseExited(MouseEvent e) {
				btn_LeftArrow.setBackground(new Color(52, 152, 219));
			}

		});

		panel_1_9.add(btn_LeftArrow);

		btn_RightArrow = new JButton(">");
		btn_RightArrow.setForeground(Color.WHITE);
		btn_RightArrow.setBackground(new Color(52, 152, 219));
		btn_RightArrow.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_RightArrow.setFocusable(false);
		btn_RightArrow.setBorderPainted(false);
		btn_RightArrow.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				btn_RightArrow.setBackground(new Color(231, 76, 60));
			}

			public void mouseExited(MouseEvent e) {
				btn_RightArrow.setBackground(new Color(52, 152, 219));
			}

		});
		panel_1_9.add(btn_RightArrow);

		JLabel lbl_LEDMode = new JLabel("LED Mode:");
		lbl_LEDMode.setForeground(new Color(255, 255, 255));
		lbl_LEDMode.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_Profile = new JLabel("Profile:");
		lbl_Profile.setForeground(new Color(255, 255, 255));
		lbl_Profile.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_Fade = new JLabel("Fade:");
		lbl_Fade.setForeground(Color.WHITE);
		lbl_Fade.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_Brightness = new JLabel("Brightness");
		lbl_Brightness.setForeground(Color.WHITE);
		lbl_Brightness.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_Animation = new JLabel("Animation:");
		lbl_Animation.setForeground(Color.WHITE);
		lbl_Animation.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_Splash = new JLabel("Splash:");
		lbl_Splash.setForeground(Color.WHITE);
		lbl_Splash.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_BGLight = new JLabel("BG Light:");
		lbl_BGLight.setForeground(Color.WHITE);
		lbl_BGLight.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_FixLED = new JLabel("Fix LED");
		lbl_FixLED.setForeground(Color.WHITE);
		lbl_FixLED.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lbl_FixLED_1 = new JLabel("Reverse LED:");
		lbl_FixLED_1.setForeground(Color.WHITE);
		lbl_FixLED_1.setFont(new Font("Tahoma", Font.BOLD, 22));

		JPanel pnl_GradientControls = new JPanel();
		pnl_GradientControls.setBackground(new Color(0, 0, 0));
		tabbedPane.addTab("Gradient Controls", null, pnl_GradientControls, null);
		pnl_GradientControls.setLayout(new BorderLayout(0, 0));

		cb_GradientSideList = new JComboBox<Object>(GetUI.gradientSides.toArray(new String[0]));
		cb_GradientSideList.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnl_GradientControls.add(cb_GradientSideList, BorderLayout.NORTH);
		cb_GradientSideList.addActionListener(e -> {
			int selectedIndex = cb_GradientSideList.getSelectedIndex();
			int divisionValue = selectedIndex + 2;
			divisionCount = divisionValue;
			pnl_GradientPreview.repaint();
			modesController.gradientSideSelect(selectedIndex);
		});

		JPanel GradientCard = new JPanel();
		GradientCard.setBackground(new Color(255, 0, 0));
		pnl_GradientControls.add(GradientCard, BorderLayout.CENTER);
		GradientCard.setLayout(new CardLayout(0, 0));

		cb_GradientSideList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) GradientCard.getLayout();
				String selectedGradient = (String) cb_GradientSideList.getSelectedItem();

				if (selectedGradient != null) {
					cardLayout.show(GradientCard, selectedGradient);
				}
			}
		});

		// Create panels for each gradient
		JPanel gradient2Side = new JPanel();
		gradient2Side.setBackground(Color.BLACK);
		GradientCard.add(gradient2Side, "2 Side Gradient");
		gradient2Side.setLayout(new GridLayout(2, 0, 0, 0));

		lb_Gradient2_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient2_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient2_SetSide1.setForeground(Color.WHITE);
		lb_Gradient2_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient2Side.add(lb_Gradient2_SetSide1);

		btn_Gradient2_SetSide1 = new JButton("Set");
		btn_Gradient2_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient2Side.add(btn_Gradient2_SetSide1);

		lb_Gradient2_SetSide2 = new JLabel("Side 2");
		lb_Gradient2_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient2_SetSide2.setForeground(Color.WHITE);
		lb_Gradient2_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient2Side.add(lb_Gradient2_SetSide2);

		btn_Gradient2_SetSide2 = new JButton("Set");
		btn_Gradient2_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient2Side.add(btn_Gradient2_SetSide2);

		JPanel gradient3Side = new JPanel();
		gradient3Side.setBackground(Color.BLACK);
		GradientCard.add(gradient3Side, "3 Side Gradient");
		gradient3Side.setLayout(new GridLayout(3, 0, 0, 0));

		lb_Gradient3_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient3_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient3_SetSide1.setForeground(Color.WHITE);
		lb_Gradient3_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient3Side.add(lb_Gradient3_SetSide1);

		btn_Gradient3_SetSide1 = new JButton("Set");
		btn_Gradient3_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient3Side.add(btn_Gradient3_SetSide1);

		lb_Gradient3_SetSide2 = new JLabel("Side 2");
		lb_Gradient3_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient3_SetSide2.setForeground(Color.WHITE);
		lb_Gradient3_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient3Side.add(lb_Gradient3_SetSide2);

		btn_Gradient3_SetSide2 = new JButton("Set");
		btn_Gradient3_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient3Side.add(btn_Gradient3_SetSide2);

		lb_Gradient3_SetSide3 = new JLabel("Side 3");
		lb_Gradient3_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient3_SetSide3.setForeground(Color.WHITE);
		lb_Gradient3_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient3Side.add(lb_Gradient3_SetSide3);

		btn_Gradient3_SetSide3 = new JButton("Set");
		btn_Gradient3_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient3Side.add(btn_Gradient3_SetSide3);

		JPanel gradient4Side = new JPanel();
		gradient4Side.setBackground(Color.BLACK);
		GradientCard.add(gradient4Side, "4 Side Gradient");
		gradient4Side.setLayout(new GridLayout(4, 2, 0, 0));

		lb_Gradient4_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient4_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient4_SetSide1.setForeground(Color.WHITE);
		lb_Gradient4_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient4Side.add(lb_Gradient4_SetSide1);

		btn_Gradient4_SetSide1 = new JButton("Set");
		btn_Gradient4_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient4Side.add(btn_Gradient4_SetSide1);

		lb_Gradient4_SetSide2 = new JLabel("Side 2");
		lb_Gradient4_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient4_SetSide2.setForeground(Color.WHITE);
		lb_Gradient4_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient4Side.add(lb_Gradient4_SetSide2);

		btn_Gradient4_SetSide2 = new JButton("Set");
		btn_Gradient4_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient4Side.add(btn_Gradient4_SetSide2);

		lb_Gradient4_SetSide3 = new JLabel("Side 3");
		lb_Gradient4_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient4_SetSide3.setForeground(Color.WHITE);
		lb_Gradient4_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient4Side.add(lb_Gradient4_SetSide3);

		btn_Gradient4_SetSide3 = new JButton("Set");
		btn_Gradient4_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient4Side.add(btn_Gradient4_SetSide3);

		lb_Gradient4_SetSide4 = new JLabel("Side 4");
		lb_Gradient4_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient4_SetSide4.setForeground(Color.WHITE);
		lb_Gradient4_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient4Side.add(lb_Gradient4_SetSide4);

		btn_Gradient4_SetSide4 = new JButton("Set");
		btn_Gradient4_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient4Side.add(btn_Gradient4_SetSide4);

		JPanel gradient5Side = new JPanel();
		gradient5Side.setBackground(Color.BLACK);
		GradientCard.add(gradient5Side, "5 Side Gradient");
		gradient5Side.setLayout(new GridLayout(5, 0, 0, 0));

		lb_Gradient5_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient5_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide1.setForeground(Color.WHITE);
		lb_Gradient5_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(lb_Gradient5_SetSide1);

		btn_Gradient5_SetSide1 = new JButton("Set");
		btn_Gradient5_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(btn_Gradient5_SetSide1);

		lb_Gradient5_SetSide2 = new JLabel("Side 2");
		lb_Gradient5_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide2.setForeground(Color.WHITE);
		lb_Gradient5_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(lb_Gradient5_SetSide2);

		btn_Gradient5_SetSide2 = new JButton("Set");
		btn_Gradient5_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(btn_Gradient5_SetSide2);

		lb_Gradient5_SetSide3 = new JLabel("Side 3");
		lb_Gradient5_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide3.setForeground(Color.WHITE);
		lb_Gradient5_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(lb_Gradient5_SetSide3);

		btn_Gradient5_SetSide3 = new JButton("Set");
		btn_Gradient5_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(btn_Gradient5_SetSide3);

		lb_Gradient5_SetSide4 = new JLabel("Side 4");
		lb_Gradient5_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide4.setForeground(Color.WHITE);
		lb_Gradient5_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(lb_Gradient5_SetSide4);

		btn_Gradient5_SetSide4 = new JButton("Set");
		btn_Gradient5_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(btn_Gradient5_SetSide4);

		lb_Gradient5_SetSide5 = new JLabel("Side 5");
		lb_Gradient5_SetSide5.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide5.setForeground(Color.WHITE);
		lb_Gradient5_SetSide5.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(lb_Gradient5_SetSide5);

		btn_Gradient5_SetSide5 = new JButton("Set");
		btn_Gradient5_SetSide5.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient5Side.add(btn_Gradient5_SetSide5);

		JPanel gradient6Side = new JPanel();
		gradient6Side.setBackground(Color.BLACK);
		GradientCard.add(gradient6Side, "6 Side Gradient");
		gradient6Side.setLayout(new GridLayout(6, 0, 0, 0));

		lb_Gradient6_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient6_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide1.setForeground(Color.WHITE);
		lb_Gradient6_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(lb_Gradient6_SetSide1);

		btn_Gradient6_SetSide1 = new JButton("Set");
		btn_Gradient6_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(btn_Gradient6_SetSide1);

		lb_Gradient6_SetSide2 = new JLabel("Side 2");
		lb_Gradient6_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide2.setForeground(Color.WHITE);
		lb_Gradient6_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(lb_Gradient6_SetSide2);

		btn_Gradient6_SetSide2 = new JButton("Set");
		btn_Gradient6_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(btn_Gradient6_SetSide2);

		lb_Gradient6_SetSide3 = new JLabel("Side 3");
		lb_Gradient6_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide3.setForeground(Color.WHITE);
		lb_Gradient6_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(lb_Gradient6_SetSide3);

		btn_Gradient6_SetSide3 = new JButton("Set");
		btn_Gradient6_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(btn_Gradient6_SetSide3);

		lb_Gradient6_SetSide4 = new JLabel("Side 4");
		lb_Gradient6_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide4.setForeground(Color.WHITE);
		lb_Gradient6_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(lb_Gradient6_SetSide4);

		btn_Gradient6_SetSide4 = new JButton("Set");
		btn_Gradient6_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(btn_Gradient6_SetSide4);

		lb_Gradient6_SetSide5 = new JLabel("Side 5");
		lb_Gradient6_SetSide5.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide5.setForeground(Color.WHITE);
		lb_Gradient6_SetSide5.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(lb_Gradient6_SetSide5);

		btn_Gradient6_SetSide5 = new JButton("Set");
		btn_Gradient6_SetSide5.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(btn_Gradient6_SetSide5);

		lb_Gradient6_SetSide6 = new JLabel("Side 6");
		lb_Gradient6_SetSide6.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide6.setForeground(Color.WHITE);
		lb_Gradient6_SetSide6.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(lb_Gradient6_SetSide6);

		btn_Gradient6_SetSide6 = new JButton("Set");
		btn_Gradient6_SetSide6.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient6Side.add(btn_Gradient6_SetSide6);

		JPanel gradient7Side = new JPanel();
		gradient7Side.setBackground(Color.BLACK);
		GradientCard.add(gradient7Side, "7 Side Gradient");
		gradient7Side.setLayout(new GridLayout(7, 0, 0, 0));

		lb_Gradient7_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient7_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide1.setForeground(Color.WHITE);
		lb_Gradient7_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(lb_Gradient7_SetSide1);

		btn_Gradient7_SetSide1 = new JButton("Set");
		btn_Gradient7_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(btn_Gradient7_SetSide1);

		lb_Gradient7_SetSide2 = new JLabel("Side 2");
		lb_Gradient7_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide2.setForeground(Color.WHITE);
		lb_Gradient7_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(lb_Gradient7_SetSide2);

		btn_Gradient7_SetSide2 = new JButton("Set");
		btn_Gradient7_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(btn_Gradient7_SetSide2);

		lb_Gradient7_SetSide3 = new JLabel("Side 3");
		lb_Gradient7_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide3.setForeground(Color.WHITE);
		lb_Gradient7_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(lb_Gradient7_SetSide3);

		btn_Gradient7_SetSide3 = new JButton("Set");
		btn_Gradient7_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(btn_Gradient7_SetSide3);

		lb_Gradient7_SetSide4 = new JLabel("Side 4");
		lb_Gradient7_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide4.setForeground(Color.WHITE);
		lb_Gradient7_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(lb_Gradient7_SetSide4);

		btn_Gradient7_SetSide4 = new JButton("Set");
		btn_Gradient7_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(btn_Gradient7_SetSide4);

		lb_Gradient7_SetSide5 = new JLabel("Side 5");
		lb_Gradient7_SetSide5.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide5.setForeground(Color.WHITE);
		lb_Gradient7_SetSide5.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(lb_Gradient7_SetSide5);

		btn_Gradient7_SetSide5 = new JButton("Set");
		btn_Gradient7_SetSide5.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(btn_Gradient7_SetSide5);

		lb_Gradient7_SetSide6 = new JLabel("Side 6");
		lb_Gradient7_SetSide6.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide6.setForeground(Color.WHITE);
		lb_Gradient7_SetSide6.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(lb_Gradient7_SetSide6);

		btn_Gradient7_SetSide6 = new JButton("Set");
		btn_Gradient7_SetSide6.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(btn_Gradient7_SetSide6);

		lb_Gradient7_SetSide7 = new JLabel("Side 7");
		lb_Gradient7_SetSide7.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide7.setForeground(Color.WHITE);
		lb_Gradient7_SetSide7.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(lb_Gradient7_SetSide7);

		btn_Gradient7_SetSide7 = new JButton("Set");
		btn_Gradient7_SetSide7.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient7Side.add(btn_Gradient7_SetSide7);

		JPanel gradient8Side = new JPanel();
		gradient8Side.setBackground(Color.BLACK);
		GradientCard.add(gradient8Side, "8 Side Gradient");
		gradient8Side.setLayout(new GridLayout(8, 1, 0, 0));

		lb_Gradient8_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient8_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide1.setForeground(Color.WHITE);
		lb_Gradient8_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(lb_Gradient8_SetSide1);

		btn_Gradient8_SetSide1 = new JButton("Set");
		btn_Gradient8_SetSide1.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(btn_Gradient8_SetSide1);

		lb_Gradient8_SetSide2 = new JLabel("Side 2");
		lb_Gradient8_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide2.setForeground(Color.WHITE);
		lb_Gradient8_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(lb_Gradient8_SetSide2);

		btn_Gradient8_SetSide2 = new JButton("Set");
		btn_Gradient8_SetSide2.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(btn_Gradient8_SetSide2);

		lb_Gradient8_SetSide3 = new JLabel("Side 3");
		lb_Gradient8_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide3.setForeground(Color.WHITE);
		lb_Gradient8_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(lb_Gradient8_SetSide3);

		btn_Gradient8_SetSide3 = new JButton("Set");
		btn_Gradient8_SetSide3.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(btn_Gradient8_SetSide3);

		lb_Gradient8_SetSide4 = new JLabel("Side 4");
		lb_Gradient8_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide4.setForeground(Color.WHITE);
		lb_Gradient8_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(lb_Gradient8_SetSide4);

		btn_Gradient8_SetSide4 = new JButton("Set");
		btn_Gradient8_SetSide4.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(btn_Gradient8_SetSide4);

		lb_Gradient8_SetSide5 = new JLabel("Side 5");
		lb_Gradient8_SetSide5.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide5.setForeground(Color.WHITE);
		lb_Gradient8_SetSide5.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(lb_Gradient8_SetSide5);

		btn_Gradient8_SetSide5 = new JButton("Set");
		btn_Gradient8_SetSide5.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(btn_Gradient8_SetSide5);

		lb_Gradient8_SetSide6 = new JLabel("Side 6");
		lb_Gradient8_SetSide6.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide6.setForeground(Color.WHITE);
		lb_Gradient8_SetSide6.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(lb_Gradient8_SetSide6);

		btn_Gradient8_SetSide6 = new JButton("Set");
		btn_Gradient8_SetSide6.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(btn_Gradient8_SetSide6);

		lb_Gradient8_SetSide7 = new JLabel("Side 7");
		lb_Gradient8_SetSide7.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide7.setForeground(Color.WHITE);
		lb_Gradient8_SetSide7.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(lb_Gradient8_SetSide7);

		btn_Gradient8_SetSide7 = new JButton("Set");
		btn_Gradient8_SetSide7.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(btn_Gradient8_SetSide7);

		lb_Gradient8_SetSide8 = new JLabel("Side 8");
		lb_Gradient8_SetSide8.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide8.setForeground(Color.WHITE);
		lb_Gradient8_SetSide8.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(lb_Gradient8_SetSide8);

		btn_Gradient8_SetSide8 = new JButton("Set");
		btn_Gradient8_SetSide8.setFont(new Font("Tahoma", Font.BOLD, 25));
		gradient8Side.add(btn_Gradient8_SetSide8);

		// Group the radio buttons together
		bgGroup = new ButtonGroup();
		bgGroup.add(rdbtn_BG_Off);
		bgGroup.add(rdbtn_BG_On);

		// Group the radio buttons together
		fixLEDGroup = new ButtonGroup();
		fixLEDGroup.add(rdbtn_FixLED_Off);
		fixLEDGroup.add(rdbtn_FixLED_On);

		// Group the radio buttons together
		reverseLEDGroup = new ButtonGroup();
		reverseLEDGroup.add(rdbtn_Reverse_Off);
		reverseLEDGroup.add(rdbtn_Reverse_On);

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

		cb_ColorPresets = new JComboBox<Object>(GetUI.colorNames.toArray(new String[0]));
		cb_ColorPresets.setFont(new Font("Tahoma", Font.BOLD, 25));
		cb_ColorPresets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedColorName = (String) cb_ColorPresets.getSelectedItem();
				int index = GetUI.colorNames.indexOf(selectedColorName);
				if (index >= 1) {
					GetUI.selectedColor = GetUI.presetColors[index - 1];
					updateColorSelected();
				}
				if (index == 0) {
					GetUI.selectedColor = Color.BLACK;
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

		JPanel pnl_TextFields = new JPanel();
		pnl_TextFields.setBackground(new Color(0, 0, 0));
		GroupLayout gl_pnl_Center = new GroupLayout(pnl_Center);
		gl_pnl_Center.setHorizontalGroup(gl_pnl_Center.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnl_Center.createSequentialGroup().addContainerGap()
						.addGroup(gl_pnl_Center.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_pnl_Center.createSequentialGroup()
										.addComponent(lbl_Profile_1, GroupLayout.PREFERRED_SIZE, 182,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cb_ColorPresets, 0, 299, Short.MAX_VALUE))
								.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
								.addComponent(lbl_ColorPicker, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
								.addComponent(pnl_ColorPicker, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 485,
										Short.MAX_VALUE)
								.addComponent(pnl_TextFields, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 485,
										Short.MAX_VALUE))
						.addContainerGap()));
		gl_pnl_Center.setVerticalGroup(gl_pnl_Center.createParallelGroup(Alignment.LEADING).addGroup(gl_pnl_Center
				.createSequentialGroup().addContainerGap().addComponent(lblNewLabel_2).addGap(18)
				.addGroup(gl_pnl_Center.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Profile_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(cb_ColorPresets, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(lbl_ColorPicker, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(pnl_ColorPicker, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(pnl_TextFields, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE).addGap(12)));

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

		pnl_TextFields.add(lbl_R);
		pnl_TextFields.add(txt_R);
		pnl_TextFields.add(lbl_G);
		pnl_TextFields.add(txt_G);
		pnl_TextFields.add(lbl_B);
		pnl_TextFields.add(txt_B);

		pnl_Center.setLayout(gl_pnl_Center);

		pnl_GradientPreview = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				int width = getWidth(); // Use the full width of the panel
				int height = getHeight(); // Take the full height of the panel

				int colorCount = Math.min(divisionCount, colors.length); // Determine the number of colors to use

				for (int i = 0; i < colorCount; i++) { // Iterate over the colors
					int x1 = i * width / divisionCount; // Calculate the starting x-coordinate of the current part
					int x2 = (i + 1) * width / divisionCount; // Calculate the ending x-coordinate of the current part

					Color color = colors[i % colors.length]; // Get the color of the current part

					g.setColor(color);
					g.fillRect(x1, 0, x2 - x1, height);

					if (i < colorCount - 1) { // Apply gradient between divisions
						Color colorLeft = colors[i];
						Color colorRight = colors[i + 1];

						for (int x = x1; x < x2; x++) { // Iterate over the pixels within the division
							float ratio = (float) (x - x1) / (float) (x2 - x1); // Calculate the ratio of the current
																				// pixel within the division
							Color interpolatedColor = interpolateColor(colorLeft, colorRight, ratio); // Interpolate the
																										// color

							g.setColor(interpolatedColor);
							g.drawLine(x, 0, x, height); // Draw a vertical line with the interpolated color
						}
					}
				}

			}
		};
		pnl_GradientPreview.setBackground(new Color(0, 0, 0));

		add(pnl_GradientPreview, BorderLayout.SOUTH);

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
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
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
		cb_ColorPresets.setSelectedIndex(GetUI.colorNames.size() - 1);
	}

	private void updateColorSelected() {
		int r = parseInt(txt_R);
		int g = parseInt(txt_G);
		int b = parseInt(txt_B);

		setTextFieldValue(txt_R, GetUI.selectedColor.getRed());
		setTextFieldValue(txt_G, GetUI.selectedColor.getGreen());
		setTextFieldValue(txt_B, GetUI.selectedColor.getBlue());

		GetUI.customColor = new Color(r, g, b);

		float[] hsb = Color.RGBtoHSB(GetUI.selectedColor.getRed(), GetUI.selectedColor.getGreen(),
				GetUI.selectedColor.getBlue(), null);
		colorPicker.setCustomColor(hsb[0], hsb[1], hsb[2]);
		colorPicker.repaint();
		GetUI.presetColors[GetUI.presetColors.length - 1] = GetUI.customColor;
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
				case "btn_Load":
					Profile.loadProfile(pianoLED);
					break;
				case "btn_Save":
					Profile.saveProfile(pianoLED);
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

				case "btn_Gradienet2_SetSide1":

					colors[0] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet2_SetSide2":

					colors[1] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;

				case "btn_Gradienet3_SetSide1":

					colors[0] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

				case "btn_Gradienet3_SetSide2":

					colors[1] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet3_SetSide3":

					colors[2] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;

				case "btn_Gradienet4_SetSide1":

					colors[0] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet4_SetSide2":

					colors[1] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet4_SetSide3":

					colors[2] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet4_SetSide4":

					colors[3] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;

				case "btn_Gradient5_SetSide1":

					colors[0] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient5_SetSide2":

					colors[1] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient5_SetSide3":

					colors[2] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient5_SetSide4":

					colors[3] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient5_SetSide5":

					colors[4] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;

				case "btn_Gradient6_SetSide1":

					colors[0] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide2":

					colors[1] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide3":

					colors[2] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide4":

					colors[3] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide5":

					colors[4] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide6":

					colors[5] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;

				case "btn_Gradient7_SetSide1":

					colors[0] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide2":

					colors[1] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide3":

					colors[2] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide4":

					colors[3] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide5":

					colors[4] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide6":

					colors[5] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide7":

					colors[6] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;

				case "btn_Gradient8_SetSide1":

					colors[0] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide2":

					colors[1] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide3":

					colors[2] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide4":

					colors[3] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide5":

					colors[4] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide6":

					colors[5] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide7":

					colors[6] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide8":

					colors[7] = GetUI.selectedColor;
					pnl_GradientPreview.repaint();

					break;
				default:
					break;
				}
			}
		};

		btn_Load.addActionListener(buttonListener);
		btn_Save.addActionListener(buttonListener);
		btn_LeftArrow.addActionListener(buttonListener);
		btn_RightArrow.addActionListener(buttonListener);
		btnSet_BG.addActionListener(buttonListener);

		btn_Gradient2_SetSide1.addActionListener(buttonListener);
		btn_Gradient2_SetSide2.addActionListener(buttonListener);

		btn_Gradient3_SetSide1.addActionListener(buttonListener);
		btn_Gradient3_SetSide2.addActionListener(buttonListener);
		btn_Gradient3_SetSide3.addActionListener(buttonListener);

		btn_Gradient4_SetSide1.addActionListener(buttonListener);
		btn_Gradient4_SetSide2.addActionListener(buttonListener);
		btn_Gradient4_SetSide3.addActionListener(buttonListener);
		btn_Gradient4_SetSide4.addActionListener(buttonListener);

		btn_Gradient5_SetSide1.addActionListener(buttonListener);
		btn_Gradient5_SetSide2.addActionListener(buttonListener);
		btn_Gradient5_SetSide3.addActionListener(buttonListener);
		btn_Gradient5_SetSide4.addActionListener(buttonListener);
		btn_Gradient5_SetSide5.addActionListener(buttonListener);

		btn_Gradient6_SetSide1.addActionListener(buttonListener);
		btn_Gradient6_SetSide2.addActionListener(buttonListener);
		btn_Gradient6_SetSide3.addActionListener(buttonListener);
		btn_Gradient6_SetSide4.addActionListener(buttonListener);
		btn_Gradient6_SetSide5.addActionListener(buttonListener);
		btn_Gradient6_SetSide6.addActionListener(buttonListener);

		btn_Gradient7_SetSide1.addActionListener(buttonListener);
		btn_Gradient7_SetSide2.addActionListener(buttonListener);
		btn_Gradient7_SetSide3.addActionListener(buttonListener);
		btn_Gradient7_SetSide4.addActionListener(buttonListener);
		btn_Gradient7_SetSide5.addActionListener(buttonListener);
		btn_Gradient7_SetSide6.addActionListener(buttonListener);
		btn_Gradient7_SetSide7.addActionListener(buttonListener);

		btn_Gradient8_SetSide1.addActionListener(buttonListener);
		btn_Gradient8_SetSide2.addActionListener(buttonListener);
		btn_Gradient8_SetSide3.addActionListener(buttonListener);
		btn_Gradient8_SetSide4.addActionListener(buttonListener);
		btn_Gradient8_SetSide5.addActionListener(buttonListener);
		btn_Gradient8_SetSide6.addActionListener(buttonListener);
		btn_Gradient8_SetSide7.addActionListener(buttonListener);
		btn_Gradient8_SetSide8.addActionListener(buttonListener);

		btn_Load.setActionCommand("btn_Load");
		btn_Save.setActionCommand("btn_Save");
		btn_LeftArrow.setActionCommand("btn_LeftArrow");
		btn_RightArrow.setActionCommand("btn_RightArrow");
		btnSet_BG.setActionCommand("btnSet_BG");

		btn_Gradient2_SetSide1.setActionCommand("btn_Gradienet2_SetSide1");
		btn_Gradient2_SetSide2.setActionCommand("btn_Gradienet2_SetSide2");

		btn_Gradient3_SetSide1.setActionCommand("btn_Gradienet3_SetSide1");
		btn_Gradient3_SetSide2.setActionCommand("btn_Gradienet3_SetSide2");
		btn_Gradient3_SetSide3.setActionCommand("btn_Gradienet3_SetSide3");

		btn_Gradient4_SetSide1.setActionCommand("btn_Gradienet4_SetSide1");
		btn_Gradient4_SetSide2.setActionCommand("btn_Gradienet4_SetSide2");
		btn_Gradient4_SetSide3.setActionCommand("btn_Gradienet4_SetSide3");
		btn_Gradient4_SetSide4.setActionCommand("btn_Gradienet4_SetSide4");

		btn_Gradient5_SetSide1.setActionCommand("btn_Gradient5_SetSide1");
		btn_Gradient5_SetSide2.setActionCommand("btn_Gradient5_SetSide2");
		btn_Gradient5_SetSide3.setActionCommand("btn_Gradient5_SetSide3");
		btn_Gradient5_SetSide4.setActionCommand("btn_Gradient5_SetSide4");
		btn_Gradient5_SetSide5.setActionCommand("btn_Gradient5_SetSide5");

		btn_Gradient6_SetSide1.setActionCommand("btn_Gradient6_SetSide1");
		btn_Gradient6_SetSide2.setActionCommand("btn_Gradient6_SetSide2");
		btn_Gradient6_SetSide3.setActionCommand("btn_Gradient6_SetSide3");
		btn_Gradient6_SetSide4.setActionCommand("btn_Gradient6_SetSide4");
		btn_Gradient6_SetSide5.setActionCommand("btn_Gradient6_SetSide5");
		btn_Gradient6_SetSide6.setActionCommand("btn_Gradient6_SetSide6");

		btn_Gradient7_SetSide1.setActionCommand("btn_Gradient7_SetSide1");
		btn_Gradient7_SetSide2.setActionCommand("btn_Gradient7_SetSide2");
		btn_Gradient7_SetSide3.setActionCommand("btn_Gradient7_SetSide3");
		btn_Gradient7_SetSide4.setActionCommand("btn_Gradient7_SetSide4");
		btn_Gradient7_SetSide5.setActionCommand("btn_Gradient7_SetSide5");
		btn_Gradient7_SetSide6.setActionCommand("btn_Gradient7_SetSide6");
		btn_Gradient7_SetSide7.setActionCommand("btn_Gradient7_SetSide7");

		btn_Gradient8_SetSide1.setActionCommand("btn_Gradient8_SetSide1");
		btn_Gradient8_SetSide2.setActionCommand("btn_Gradient8_SetSide2");
		btn_Gradient8_SetSide3.setActionCommand("btn_Gradient8_SetSide3");
		btn_Gradient8_SetSide4.setActionCommand("btn_Gradient8_SetSide4");
		btn_Gradient8_SetSide5.setActionCommand("btn_Gradient8_SetSide5");
		btn_Gradient8_SetSide6.setActionCommand("btn_Gradient8_SetSide6");
		btn_Gradient8_SetSide7.setActionCommand("btn_Gradient8_SetSide7");
		btn_Gradient8_SetSide8.setActionCommand("btn_Gradient8_SetSide8");
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
					int FadeVal = 255 - sld_Fade.getValue();
					sld_Fade.setToolTipText(Integer.toString(FadeVal));
					pianoController.FadeRate(FadeVal);
				} else if (source == sld_SplashMaxLenght) {
					int SplashLength = sld_SplashMaxLenght.getValue();
					sld_SplashMaxLenght.setToolTipText(Integer.toString(SplashLength));
					pianoController.SplashLengthRate(SplashLength);
				}

				else if (source == bg_slider) {
					int bgValue = bg_slider.getValue();
					bg_slider.setToolTipText(Integer.toString(bgValue));
					System.out.println("Slider Value: " + bgValue);
					PianoController.BG_BRIGHTNESS = bgValue;
					System.out.println("BG Light Value: " + PianoController.BG_BRIGHTNESS);
					if (pianoController.bgToggle)
						pianoController.setBG();
				}
			}
		};

		sld_Brightness.addChangeListener(sliderChangeListener);
		sld_Fade.addChangeListener(sliderChangeListener);
		sld_SplashMaxLenght.addChangeListener(sliderChangeListener);
		bg_slider.addChangeListener(sliderChangeListener);

	}

	private Color interpolateColor(Color color1, Color color2, float ratio) {
		float[] hsbColor1 = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
		float[] hsbColor2 = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);

		float hue = hsbColor1[0] * (1 - ratio) + hsbColor2[0] * ratio;
		float saturation = hsbColor1[1] * (1 - ratio) + hsbColor2[1] * ratio;
		float brightness = hsbColor1[2] * (1 - ratio) + hsbColor2[2] * ratio;

		return Color.getHSBColor(hue, saturation, brightness);
	}

}
