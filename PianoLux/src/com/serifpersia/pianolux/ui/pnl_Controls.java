package com.serifpersia.pianolux.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.serifpersia.pianolux.ModesController;
import com.serifpersia.pianolux.PianoController;
import com.serifpersia.pianolux.PianoLux;
import com.serifpersia.pianolux.Profile;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class pnl_Controls extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;
	static int defaultBrighntessVal = 255;
	static int defaultFadeVal = 0;
	static int defaultMaxSplashLengthVal = 8;
	private JButton btn_Load;
	private JButton btn_Save;
	public static JComboBox<?> cb_LED_Mode;
	public static JComboBox<?> cb_LED_Animations;
	public static JSlider sld_Brightness;
	public static JSlider sld_Fade;
	public static JSlider sld_SplashMaxLenght;
	public static JSlider bg_slider;
	public static JSlider sld_transposition;
	private JButton btn_SetBG;
	private JButton btn_rightArrow;
	private JButton btn_leftArrow;
	public static JLabel lbl_PianoSize;
	private ImageIcon toggle_on_Icon;
	private ImageIcon toggle_off_Icon;
	public static JToggleButton bgToggle;
	public static JToggleButton fixToggle;
	public static JToggleButton reverseToggle;
	public static JToggleButton guideToggle;
	public static JToggleButton oneToOneRatioToggle;
	public static JToggleButton shiftbyOctavesToggle;

	public pnl_Controls(PianoLux pianoLux) {

		pianoController = pianoLux.getPianoController();
		modesController = new ModesController(pianoLux);

		initButtonsParam();
		initComboboxParam();
		initSliderParam();
		init();
		buttonActions(pianoLux);
		sliderActions();
		toggleButtonsAction();

	}

	private void init() {
		setLayout(new BorderLayout(0, 0));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(0, 2, 0, 0));
		innerPanel.setBackground(new Color(50, 50, 50));
		add(innerPanel, BorderLayout.CENTER);

		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
		leftPanel.setLayout(new GridLayout(11, 0, 0, 0));
		leftPanel.setBackground(new Color(50, 50, 50));

		JLabel Profile = new JLabel("Profile");
		Profile.setBorder(new EmptyBorder(0, 0, 10, 0));
		Profile.setHorizontalAlignment(SwingConstants.LEFT);
		Profile.setForeground(new Color(204, 204, 204));
		Profile.setFont(new Font("Poppins", Font.PLAIN, 21));
		leftPanel.add(Profile);

		JLabel LED_Mode = new JLabel("LED Mode");
		LED_Mode.setBorder(new EmptyBorder(0, 0, 10, 0));
		LED_Mode.setHorizontalAlignment(SwingConstants.LEFT);
		LED_Mode.setForeground(new Color(204, 204, 204));
		LED_Mode.setFont(new Font("Poppins", Font.PLAIN, 21));
		leftPanel.add(LED_Mode);

		JLabel Animation = new JLabel("Animation");
		Animation.setBorder(new EmptyBorder(0, 0, 10, 0));
		Animation.setHorizontalAlignment(SwingConstants.LEFT);
		Animation.setForeground(new Color(204, 204, 204));
		Animation.setFont(new Font("Poppins", Font.PLAIN, 21));
		leftPanel.add(Animation);

		JLabel Brightness = new JLabel("Brightness");
		Brightness.setHorizontalAlignment(SwingConstants.LEFT);
		Brightness.setForeground(new Color(204, 204, 204));
		Brightness.setFont(new Font("Poppins", Font.PLAIN, 21));
		leftPanel.add(Brightness);

		JLabel Fade = new JLabel("Fade");
		Fade.setHorizontalAlignment(SwingConstants.LEFT);
		Fade.setForeground(new Color(204, 204, 204));
		Fade.setFont(new Font("Poppins", Font.PLAIN, 21));
		leftPanel.add(Fade);

		JLabel SplashLength = new JLabel("Splash Length");
		SplashLength.setHorizontalAlignment(SwingConstants.LEFT);
		SplashLength.setForeground(new Color(204, 204, 204));
		SplashLength.setFont(new Font("Poppins", Font.PLAIN, 21));
		leftPanel.add(SplashLength);

		JLabel BG_Brightness = new JLabel("BG LED Brightness");
		BG_Brightness.setHorizontalAlignment(SwingConstants.LEFT);
		BG_Brightness.setForeground(new Color(204, 204, 204));
		BG_Brightness.setFont(new Font("Poppins", Font.PLAIN, 21));
		leftPanel.add(BG_Brightness);

		JLabel Transposition = new JLabel("Transposition");
		Transposition.setHorizontalAlignment(SwingConstants.LEFT);
		Transposition.setForeground(new Color(204, 204, 204));
		Transposition.setFont(new Font("Poppins", Font.PLAIN, 21));
		leftPanel.add(Transposition);

		lbl_PianoSize = new JLabel("Piano " + GetUI.getNumPianoKeys() + " Keys");
		lbl_PianoSize.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_PianoSize.setForeground(new Color(204, 204, 204));
		lbl_PianoSize.setFont(new Font("Poppins", Font.PLAIN, 21));

		leftPanel.add(lbl_PianoSize);

		JPanel leftPanelModifiers_upper = new JPanel();
		FlowLayout flowLayout = (FlowLayout) leftPanelModifiers_upper.getLayout();
		flowLayout.setVgap(-10);
		flowLayout.setHgap(0);
		leftPanelModifiers_upper.setBackground(new Color(50, 50, 50));

		JLabel FixLED = new JLabel("Fix LED");
		FixLED.setHorizontalAlignment(SwingConstants.LEFT);
		FixLED.setForeground(new Color(204, 204, 204));
		FixLED.setFont(new Font("Poppins", Font.PLAIN, 21));
		FixLED.setBorder(new EmptyBorder(0, 0, 0, 73));

		leftPanelModifiers_upper.add(FixLED);
		leftPanelModifiers_upper.add(fixToggle);

		leftPanel.add(leftPanelModifiers_upper);

		JPanel leftPanelModifiers_bottom = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) leftPanelModifiers_bottom.getLayout();
		flowLayout_1.setVgap(-10);
		flowLayout_1.setHgap(0);
		leftPanelModifiers_bottom.setBackground(new Color(50, 50, 50));

		JLabel ReverseLED = new JLabel("Reverse LED");
		ReverseLED.setHorizontalAlignment(SwingConstants.LEFT);
		ReverseLED.setForeground(new Color(204, 204, 204));
		ReverseLED.setFont(new Font("Poppins", Font.PLAIN, 21));
		ReverseLED.setBorder(new EmptyBorder(0, 0, 0, 15));

		leftPanelModifiers_bottom.add(ReverseLED);
		leftPanelModifiers_bottom.add(reverseToggle);

		leftPanel.add(leftPanelModifiers_bottom);

		innerPanel.add(leftPanel);

		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		rightPanel.setLayout(new GridLayout(11, 0, 0, 0));
		rightPanel.setBackground(new Color(50, 50, 50));

		JPanel ProfileButtons = new JPanel();
		ProfileButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		ProfileButtons.setLayout(new GridLayout(0, 2, 0, 0));
		ProfileButtons.setBackground(new Color(50, 50, 50));

		rightPanel.add(ProfileButtons);

		ProfileButtons.add(btn_Load);

		ProfileButtons.add(btn_Save);

		JPanel pnlLED_Mode = new JPanel();
		pnlLED_Mode.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		pnlLED_Mode.setLayout(new BorderLayout(0, 0));
		pnlLED_Mode.setBackground(new Color(50, 50, 50));

		pnlLED_Mode.add(cb_LED_Mode);
		rightPanel.add(pnlLED_Mode);

		JPanel pnlAnimation = new JPanel();
		pnlAnimation.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		pnlAnimation.setLayout(new BorderLayout(0, 0));
		pnlAnimation.setBackground(new Color(50, 50, 50));

		pnlAnimation.add(cb_LED_Animations);
		rightPanel.add(pnlAnimation);

		rightPanel.add(sld_Brightness);
		rightPanel.add(sld_Fade);
		rightPanel.add(sld_SplashMaxLenght);
		rightPanel.add(bg_slider);

		JPanel TranspositioNPanel = new JPanel();
		TranspositioNPanel.setLayout(new BorderLayout());
		TranspositioNPanel.setBackground(new Color(50, 50, 50));

		// Assuming sld_transposition and shiftbyOctavesToggle are already initialized
		TranspositioNPanel.add(sld_transposition, BorderLayout.CENTER);
		TranspositioNPanel.add(shiftbyOctavesToggle, BorderLayout.EAST);

		rightPanel.add(TranspositioNPanel);

		JPanel ButtonsPanel = new JPanel();
		ButtonsPanel.setLayout(new GridLayout(0, 4, 0, 0));
		ButtonsPanel.setBackground(new Color(50, 50, 50));

		ButtonsPanel.add(btn_leftArrow);
		ButtonsPanel.add(btn_rightArrow);
		ButtonsPanel.add(oneToOneRatioToggle);
		ButtonsPanel.add(btn_SetBG);

		rightPanel.add(ButtonsPanel);

		JPanel rightPanelModifiers_upper = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) rightPanelModifiers_upper.getLayout();
		flowLayout_2.setVgap(-10);
		flowLayout_2.setHgap(0);
		rightPanelModifiers_upper.setBackground(new Color(50, 50, 50));

		JLabel BGLED = new JLabel("BG LED");
		BGLED.setHorizontalAlignment(SwingConstants.LEFT);
		BGLED.setForeground(new Color(204, 204, 204));
		BGLED.setFont(new Font("Poppins", Font.PLAIN, 21));
		BGLED.setBorder(new EmptyBorder(0, 0, 0, 46));

		rightPanelModifiers_upper.add(BGLED);
		rightPanelModifiers_upper.add(bgToggle);

		rightPanel.add(rightPanelModifiers_upper);

		JPanel rightPanelModifiers_bottom = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) rightPanelModifiers_bottom.getLayout();
		flowLayout_3.setVgap(-10);
		flowLayout_3.setHgap(0);
		rightPanelModifiers_bottom.setBackground(new Color(50, 50, 50));

		JLabel GuideLED = new JLabel("Guide LED");
		GuideLED.setHorizontalAlignment(SwingConstants.LEFT);
		GuideLED.setForeground(new Color(204, 204, 204));
		GuideLED.setFont(new Font("Poppins", Font.PLAIN, 21));
		GuideLED.setBorder(new EmptyBorder(0, 0, 0, 15));

		rightPanelModifiers_bottom.add(GuideLED);
		rightPanelModifiers_bottom.add(guideToggle);

		rightPanel.add(rightPanelModifiers_bottom);

		innerPanel.add(rightPanel);

	}

	private void initButtonsParam() {

		btn_Save = new JButton("Save");
		btn_Save.setForeground(new Color(204, 204, 204));
		btn_Save.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Save.setFocusable(false);

		btn_Load = new JButton("Load");
		btn_Load.setForeground(new Color(204, 204, 204));
		btn_Load.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Load.setFocusable(false);

		btn_leftArrow = new JButton("<");
		btn_leftArrow.setForeground(new Color(204, 204, 204));
		btn_leftArrow.setFont(new Font("Poppins", Font.PLAIN, 16));
		btn_leftArrow.setFocusable(false);

		btn_rightArrow = new JButton(">");
		btn_rightArrow.setForeground(new Color(204, 204, 204));
		btn_rightArrow.setFont(new Font("Poppins", Font.PLAIN, 16));
		btn_rightArrow.setFocusable(false);

		oneToOneRatioToggle = new JToggleButton("1:1");
		oneToOneRatioToggle.setForeground(new Color(204, 204, 204));
		oneToOneRatioToggle.setFont(new Font("Poppins", Font.PLAIN, 16));
		oneToOneRatioToggle.setFocusable(false);

		shiftbyOctavesToggle = new JToggleButton("Oct");
		shiftbyOctavesToggle.setForeground(new Color(204, 204, 204));
		shiftbyOctavesToggle.setFont(new Font("Poppins", Font.PLAIN, 16));
		shiftbyOctavesToggle.setFocusable(false);

		btn_SetBG = new JButton("BG");
		btn_SetBG.setForeground(new Color(204, 204, 204));
		btn_SetBG.setFont(new Font("Poppins", Font.PLAIN, 16));
		btn_SetBG.setFocusable(false);

		bgToggle = new JToggleButton("");
		fixToggle = new JToggleButton("");
		reverseToggle = new JToggleButton("");
		guideToggle = new JToggleButton("");

		toggle_on_Icon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/toggle_on.png")).getImage()
				.getScaledInstance(50, 50, Image.SCALE_SMOOTH));

		toggle_off_Icon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/toggle_off.png")).getImage()
				.getScaledInstance(50, 50, Image.SCALE_SMOOTH));

		fixToggle.setIcon(toggle_off_Icon);
		reverseToggle.setIcon(toggle_off_Icon);
		bgToggle.setIcon(toggle_off_Icon);
		guideToggle.setIcon(toggle_off_Icon);

		fixToggle.setBackground(new Color(50, 50, 50));
		fixToggle.setBorderPainted(false);
		fixToggle.setFocusable(false);

		reverseToggle.setBackground(new Color(50, 50, 50));
		reverseToggle.setBorderPainted(false);
		reverseToggle.setFocusable(false);

		bgToggle.setBackground(new Color(50, 50, 50));
		bgToggle.setBorderPainted(false);
		bgToggle.setFocusable(false);

		guideToggle.setBackground(new Color(50, 50, 50));
		guideToggle.setBorderPainted(false);
		guideToggle.setFocusable(false);

		oneToOneRatioToggle.setBorderPainted(false);
		oneToOneRatioToggle.setFocusable(false);

	}

	private void initComboboxParam() {

		cb_LED_Mode = new JComboBox<Object>(GetUI.modes.toArray(new String[0]));
		cb_LED_Mode.putClientProperty("JComponent.roundRect", true);
		cb_LED_Mode.setForeground(new Color(204, 204, 204));
		cb_LED_Mode.setFont(new Font("Poppins", Font.PLAIN, 21));

		cb_LED_Mode.addActionListener(e -> {
			int selectedIndex = cb_LED_Mode.getSelectedIndex();
			modesController.modeSelect(selectedIndex);
		});

		cb_LED_Animations = new JComboBox<Object>(GetUI.animationNames.toArray(new String[0]));
		cb_LED_Animations.putClientProperty("JComponent.roundRect", true);
		cb_LED_Animations.setForeground(new Color(204, 204, 204));
		cb_LED_Animations.setFont(new Font("Poppins", Font.PLAIN, 21));

		cb_LED_Animations.addActionListener(e -> {
			if (ModesController.AnimationOn) {
				int selectedIndex = cb_LED_Animations.getSelectedIndex();
				pianoController.animationlist(selectedIndex);
			}
		});

	}

	private void initSliderParam() {

		sld_Brightness = new JSlider(0, 255, defaultBrighntessVal);
		sld_Brightness.setBackground(new Color(77, 77, 77));
		sld_Brightness.setForeground(new Color(128, 128, 128));
		sld_Brightness.setMajorTickSpacing(51);

		sld_Fade = new JSlider(0, 255, defaultFadeVal);
		sld_Fade.setBackground(new Color(77, 77, 77));
		sld_Fade.setForeground(new Color(128, 128, 128));
		sld_Fade.setMajorTickSpacing(51);

		sld_SplashMaxLenght = new JSlider(4, 16, defaultMaxSplashLengthVal);
		sld_SplashMaxLenght.setBackground(new Color(77, 77, 77));
		sld_SplashMaxLenght.setForeground(new Color(128, 128, 128));
		sld_SplashMaxLenght.setMajorTickSpacing(2);

		bg_slider = new JSlider(10, 100);
		bg_slider.setBackground(new Color(77, 77, 77));
		bg_slider.setForeground(new Color(128, 128, 128));
		bg_slider.setSnapToTicks(true);
		bg_slider.setMajorTickSpacing(10);
		bg_slider.setValue(50);

		sld_transposition = new JSlider(-6, 6, 0);
		sld_transposition.setBackground(new Color(77, 77, 77));
		sld_transposition.setForeground(new Color(128, 128, 128));
		sld_transposition.setMinorTickSpacing(1);
		sld_transposition.setPaintTicks(true);
		sld_transposition.setSnapToTicks(true);
		sld_transposition.setInverted(true);

	}

	private void buttonActions(PianoLux pianoLux) {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "btn_Load":
					Profile.loadProfile(pianoLux);
					break;
				case "btn_Save":
					Profile.saveProfile(pianoLux);
					break;
				case "btn_LeftArrow":

					if (GetUI.counter <= 0) {
						return;
					}
					GetUI.counter--;
					GetUI.setKeyboardSize(GetUI.counter);
					lbl_PianoSize.setText("Piano " + GetUI.getNumPianoKeys() + " Keys");
					pianoLux.getDrawPiano().repaint();
					break;
				case "btn_RightArrow":
					if (GetUI.counter >= 4) {
						return;
					}
					GetUI.counter++;

					GetUI.setKeyboardSize(GetUI.counter);
					lbl_PianoSize.setText("Piano " + GetUI.getNumPianoKeys() + " Keys");
					pianoLux.getDrawPiano().repaint();
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

		btn_Load.addActionListener(buttonListener);
		btn_Save.addActionListener(buttonListener);
		btn_leftArrow.addActionListener(buttonListener);
		btn_rightArrow.addActionListener(buttonListener);
		btn_SetBG.addActionListener(buttonListener);

		btn_Load.setActionCommand("btn_Load");
		btn_Save.setActionCommand("btn_Save");
		btn_leftArrow.setActionCommand("btn_LeftArrow");
		btn_rightArrow.setActionCommand("btn_RightArrow");
		btn_SetBG.setActionCommand("btnSet_BG");

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
				} else if (source == sld_transposition) {
					int Transposition = sld_transposition.getValue();
					if (pianoController.useOctaveShift) {
						Transposition *= 12;
					}
					int invertSliderValue = invertSlider(Transposition);
					sld_transposition.setToolTipText(Integer.toString(invertSliderValue));
					pianoController.transposition = Transposition;
					System.out.println(Transposition);
				} else if (source == bg_slider) {
					int bgValue = (int) (bg_slider.getValue() * 2.55); // Map the 0-100 range to 0-255
					bg_slider.setToolTipText(Integer.toString(bg_slider.getValue())); // Show the value in the 0-100
																						// range in the tooltip
					PianoController.BG_BRIGHTNESS = bgValue;
					if (pianoController.bgToggle)
						pianoController.setBG();
				}
			}
		};

		sld_Brightness.addChangeListener(sliderChangeListener);
		sld_Fade.addChangeListener(sliderChangeListener);
		sld_SplashMaxLenght.addChangeListener(sliderChangeListener);
		bg_slider.addChangeListener(sliderChangeListener);
		sld_transposition.addChangeListener(sliderChangeListener);
	}

	// Define a function to invert the slider value
	private int invertSlider(int invertedValue) {
		// Invert the value
		return -invertedValue;
	}

	private void toggleButtonsAction() {
		ActionListener tgl_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource(); // Get the source of the event

				if (source == bgToggle) {
					if (bgToggle.isSelected()) {
						bgToggle.setIcon(toggle_on_Icon);
						pianoController.bgToggle = true;
						pianoController.setLedBG(pianoController.bgToggle);
					} else {
						bgToggle.setIcon(toggle_off_Icon);
						pianoController.bgToggle = false;
						pianoController.setLedBG(pianoController.bgToggle);
					}
				} else if (source == fixToggle) {
					if (fixToggle.isSelected()) {
						fixToggle.setIcon(toggle_on_Icon);
						pianoController.useFixedMapping = true;
					} else {
						fixToggle.setIcon(toggle_off_Icon);
						pianoController.useFixedMapping = false;
					}
				} else if (source == reverseToggle) {
					if (reverseToggle.isSelected()) {
						reverseToggle.setIcon(toggle_on_Icon);
						pianoController.stripReverse = true;
						pianoController.stripReverse(pianoController.stripReverse);
					} else {
						reverseToggle.setIcon(toggle_off_Icon);
						pianoController.stripReverse = false;
						pianoController.stripReverse(pianoController.stripReverse);
					}
				}

				else if (source == guideToggle) {
					if (guideToggle.isSelected()) {
						guideToggle.setIcon(toggle_on_Icon);
						pianoController.guideToggle = true;
						pianoController.setLedGuide(pianoController.guideToggle);
						pianoController.setLedGuide(pianoController.guideToggle);

					} else {
						guideToggle.setIcon(toggle_off_Icon);
						pianoController.guideToggle = false;
						pianoController.setLedGuide(pianoController.guideToggle);
						pianoController.setLedGuide(pianoController.guideToggle);
					}
				}

				else if (source == oneToOneRatioToggle) {
					if (oneToOneRatioToggle.isSelected()) {

						pianoController.use72LEDSMap = true;
					} else {
						pianoController.use72LEDSMap = false;
					}
				} else if (source == shiftbyOctavesToggle) {
					if (shiftbyOctavesToggle.isSelected()) {

						pianoController.useOctaveShift = true;
					} else {
						pianoController.useOctaveShift = false;
					}
				}

			}
		};

		bgToggle.addActionListener(tgl_listener);
		fixToggle.addActionListener(tgl_listener);
		reverseToggle.addActionListener(tgl_listener);
		guideToggle.addActionListener(tgl_listener);
		oneToOneRatioToggle.addActionListener(tgl_listener);
		shiftbyOctavesToggle.addActionListener(tgl_listener);

	}
}
