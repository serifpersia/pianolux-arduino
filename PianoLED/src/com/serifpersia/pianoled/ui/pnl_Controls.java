package com.serifpersia.pianoled.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.serifpersia.pianoled.ModesController;
import com.serifpersia.pianoled.PianoController;
import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.Profile;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class pnl_Controls extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;

	private JButton btn_Load;
	private JButton btn_Save;

	public static JComboBox<?> cb_LED_Mode;
	public static JComboBox<?> cb_LED_Animations;

	static int defaultBrighntessVal = 255;
	static int defaultFadeVal = 0;
	static int defaultMaxSplashLengthVal = 8;

	public static JSlider sld_Brightness;
	public static JSlider sld_Fade;
	public static JSlider sld_SplashMaxLenght;
	public static JSlider bg_slider;

	private ImageIcon toggle_on_Icon;
	private ImageIcon toggle_off_Icon;

	public static JToggleButton bgToggle;
	public static JToggleButton fixToggle;
	public static JToggleButton reverseToggle;
	public static JToggleButton guideToggle;
	public static JToggleButton LEDS72_Toggle;

	private JButton btn_SetBG;
	private JButton btn_rightArrow;
	private JButton btn_leftArrow;

	private JLabel lbl_PianoSize;

	public pnl_Controls(PianoLED pianoLED) {

		pianoController = pianoLED.getPianoController();
		modesController = new ModesController(pianoLED);

		initButtonsParam();
		initComboboxParam();
		initSliderParam();
		init();
		buttonActions(pianoLED);
		sliderActions();
		toggleButtonsAction();

	}

	private void init() {

		setBackground(new Color(50, 50, 50));

		JLabel lblNewLabel_1 = new JLabel("Profile");
		lblNewLabel_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1.setFont(new Font("Poppins", Font.PLAIN, 21));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(50, 50, 50));

		JLabel lblNewLabel_1_1 = new JLabel("LED Mode");
		lblNewLabel_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1.setFont(new Font("Poppins", Font.PLAIN, 21));

		JLabel lblNewLabel_1_1_1 = new JLabel("Animation");
		lblNewLabel_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 21));

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Brightness");
		lblNewLabel_1_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 21));

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Fade");
		lblNewLabel_1_1_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 21));

		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Splash Length");
		lblNewLabel_1_1_1_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 21));

		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("BG LED Brightness");
		lblNewLabel_1_1_1_1_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 21));

		lbl_PianoSize = new JLabel("Piano: " + GetUI.getNumPianoKeys() + " Keys");
		lbl_PianoSize.setForeground(new Color(204, 204, 204));
		lbl_PianoSize.setFont(new Font("Poppins", Font.PLAIN, 21));

		JPanel PianoPanel = new JPanel();
		PianoPanel.setBackground(new Color(50, 50, 50));
		PianoPanel.setLayout(new GridLayout(0, 4, 0, 0));

		PianoPanel.add(btn_leftArrow);

		PianoPanel.add(btn_rightArrow);

		JPanel modifiers_Panel = new JPanel();
		modifiers_Panel.setBackground(new Color(50, 50, 50));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(50, 50, 50));

		GroupLayout gl_left = new GroupLayout(this);
		gl_left.setHorizontalGroup(
			gl_left.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_left.createSequentialGroup()
					.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_left.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 473, Short.MAX_VALUE))
						.addGroup(gl_left.createSequentialGroup()
							.addContainerGap()
							.addComponent(modifiers_Panel, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
						.addGroup(gl_left.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_PianoSize, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_1_1)
								.addComponent(lblNewLabel_1_1_1)
								.addComponent(lblNewLabel_1_1_1_1)
								.addComponent(lblNewLabel_1_1_1_1_1)
								.addComponent(lblNewLabel_1_1_1_1_1_1)
								.addComponent(lblNewLabel_1_1_1_1_1_1_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_left.createParallelGroup(Alignment.LEADING, false)
								.addComponent(bg_slider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(sld_SplashMaxLenght, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(sld_Fade, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(sld_Brightness, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cb_LED_Animations, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cb_LED_Mode, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
								.addComponent(PianoPanel, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE))))
					.addGap(22))
		);
		gl_left.setVerticalGroup(
			gl_left.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_left.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(cb_LED_Mode, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(cb_LED_Animations, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(sld_Brightness, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(sld_Fade, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(sld_SplashMaxLenght, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1_1_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(bg_slider, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_left.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_PianoSize, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(PianoPanel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(modifiers_Panel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblNewLabel_1_1_1_1_1_2_1_1 = new JLabel("Reverse LED");
		lblNewLabel_1_1_1_1_1_2_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_2_1_1.setFont(new Font("Dialog", Font.PLAIN, 20));
		panel_3.add(lblNewLabel_1_1_1_1_1_2_1_1);

		panel_3.add(reverseToggle);

		JLabel lblNewLabel_1_1_1_1_1_2_4_1 = new JLabel("Guide LED");
		lblNewLabel_1_1_1_1_1_2_4_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_2_4_1.setFont(new Font("Dialog", Font.PLAIN, 20));
		panel_3.add(lblNewLabel_1_1_1_1_1_2_4_1);

		panel_3.add(guideToggle);
		modifiers_Panel.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblNewLabel_1_1_1_1_1_2_1 = new JLabel("Fix LED");
		lblNewLabel_1_1_1_1_1_2_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_2_1.setFont(new Font("Dialog", Font.PLAIN, 20));
		modifiers_Panel.add(lblNewLabel_1_1_1_1_1_2_1);

		modifiers_Panel.add(fixToggle);

		JLabel lblNewLabel_1_1_1_1_1_2_4 = new JLabel("BG LED");
		lblNewLabel_1_1_1_1_1_2_4.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_2_4.setFont(new Font("Dialog", Font.PLAIN, 20));
		modifiers_Panel.add(lblNewLabel_1_1_1_1_1_2_4);

		modifiers_Panel.add(bgToggle);

		PianoPanel.add(btn_SetBG);

		PianoPanel.add(LEDS72_Toggle);
		buttonPanel.setLayout(new GridLayout(0, 2, 0, 0));

		buttonPanel.add(btn_Load);

		buttonPanel.add(btn_Save);
		setLayout(gl_left);

	}

	private void initButtonsParam() {

		btn_Save = new JButton("Save");
		btn_Save.setForeground(new Color(204, 204, 204));
		btn_Save.setFont(new Font("Poppins", Font.PLAIN, 24));
		btn_Save.setFocusable(false);

		btn_Load = new JButton("Load");
		btn_Load.setForeground(new Color(204, 204, 204));
		btn_Load.setFont(new Font("Poppins", Font.PLAIN, 24));
		btn_Load.setFocusable(false);

		btn_leftArrow = new JButton("<");
		btn_leftArrow.setForeground(new Color(204, 204, 204));
		btn_leftArrow.setFont(new Font("Poppins", Font.PLAIN, 24));
		btn_leftArrow.setFocusable(false);

		btn_rightArrow = new JButton(">");
		btn_rightArrow.setForeground(new Color(204, 204, 204));
		btn_rightArrow.setFont(new Font("Poppins", Font.PLAIN, 24));
		btn_rightArrow.setFocusable(false);

		btn_SetBG = new JButton("BG");
		btn_SetBG.setForeground(new Color(204, 204, 204));
		btn_SetBG.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_SetBG.setFocusable(false);

		LEDS72_Toggle = new JToggleButton("72");
		LEDS72_Toggle.setForeground(new Color(204, 204, 204));
		LEDS72_Toggle.setFont(new Font("Poppins", Font.PLAIN, 21));
		LEDS72_Toggle.setFocusable(false);

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


		LEDS72_Toggle.setBorderPainted(false);
		LEDS72_Toggle.setFocusable(false);

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
				}

				else if (source == bg_slider) {
					int bgValue = bg_slider.getValue();
					bg_slider.setToolTipText(Integer.toString(bgValue));
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

				else if (source == LEDS72_Toggle) {
					if (LEDS72_Toggle.isSelected()) {

						pianoController.use72LEDSMap = true;
					} else {
						pianoController.use72LEDSMap = false;
					}
				}

			}
		};

		bgToggle.addActionListener(tgl_listener);
		fixToggle.addActionListener(tgl_listener);
		reverseToggle.addActionListener(tgl_listener);
		guideToggle.addActionListener(tgl_listener);
		LEDS72_Toggle.addActionListener(tgl_listener);

	}
}
