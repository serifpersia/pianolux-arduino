package com.serifpersia.pianoled.ui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.serifpersia.pianoled.ModesController;
import com.serifpersia.pianoled.PianoController;
import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.Profile;

import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class pnl_Controls extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;

	private JButton btn_Load;
	private JButton btn_Save;

	public static JComboBox<?> cb_LED_Mode;
	public static JComboBox<?> cb_LED_Animations;

	public static JSlider sld_Brightness;
	public static JSlider sld_Fade;
	public static JSlider sld_SplashMaxLenght;
	private JSlider bg_slider;

	static int defaultBrighntessVal = 255;
	static int defaultFadeVal = 0;
	static int defaultMaxSplashLengthVal = 8;

	public static JToggleButton bgToggle;
	public static JToggleButton fixToggle;
	public static JToggleButton reverseToggle;
	public static JToggleButton guideToggle;

	private JButton btn_leftArrow;
	private JButton btn_rightArrow;
	private JButton btn_SetBG;

	private ImageIcon toggle_on_Icon;
	private ImageIcon toggle_off_Icon;
	private JLabel lbl_PianoSize;

	public pnl_Controls(PianoLED pianoLED) {

		pianoController = pianoLED.getPianoController();
		modesController = new ModesController(pianoLED);

		bgToggle = new JToggleButton("");
		fixToggle = new JToggleButton("");
		reverseToggle = new JToggleButton("");
		guideToggle = new JToggleButton("");

		toggle_on_Icon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/toggle_on.png")).getImage()
				.getScaledInstance(75, 75, Image.SCALE_SMOOTH));

		toggle_off_Icon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/toggle_off.png")).getImage()
				.getScaledInstance(75, 75, Image.SCALE_SMOOTH));

		bgToggle.setIcon(toggle_off_Icon);
		fixToggle.setIcon(toggle_off_Icon);
		reverseToggle.setIcon(toggle_off_Icon);
		guideToggle.setIcon(toggle_off_Icon);

		init();
		toggleButtonsAction();
		buttonActions(pianoLED);
		sliderActions();
	}

	private void init() {
		setBackground(new Color(51, 51, 51));

		JLabel lblNewLabel = new JLabel("Profile");
		lblNewLabel.setForeground(new Color(204, 204, 204));
		lblNewLabel.setFont(new Font("Poppins", Font.PLAIN, 26));

		btn_Save = new JButton("Save");
		btn_Save.setForeground(new Color(204, 204, 204));
		btn_Save.setBackground(new Color(77, 77, 77));
		btn_Save.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Save.setFocusable(false);
		btn_Save.setBorderPainted(false);

		btn_Load = new JButton("Load");
		btn_Load.setForeground(new Color(204, 204, 204));
		btn_Load.setBackground(new Color(77, 77, 77));
		btn_Load.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Load.setFocusable(false);
		btn_Load.setBorderPainted(false);

		JLabel lblNewLabel_1 = new JLabel("LED Mode");
		lblNewLabel_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1.setFont(new Font("Poppins", Font.PLAIN, 26));

		cb_LED_Mode = new JComboBox<Object>(GetUI.modes.toArray(new String[0]));
		cb_LED_Mode.setBackground(new Color(77, 77, 77));
		cb_LED_Mode.setForeground(new Color(204, 204, 204));
		cb_LED_Mode.setFont(new Font("Poppins", Font.PLAIN, 21));
		cb_LED_Mode.setFocusable(false); // Set the JComboBox as non-focusable

		// Customize the JComboBox UI
		cb_LED_Mode.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				return new JButton() {
					@Override
					public void paint(Graphics g) {
						// Do nothing to remove the arrow button painting
					}

					@Override
					public boolean contains(int x, int y) {
						// Override contains() method to hide the button when the mouse is over it
						return false;
					}
				};
			}
		});
		cb_LED_Mode.addActionListener(e -> {
			int selectedIndex = cb_LED_Mode.getSelectedIndex();
			modesController.modeSelect(selectedIndex);
		});

		JLabel lblNewLabel_1_1 = new JLabel("Animation");
		lblNewLabel_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1.setFont(new Font("Poppins", Font.PLAIN, 26));

		JLabel lblNewLabel_1_1_1 = new JLabel("Brightness");
		lblNewLabel_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 26));

		sld_Brightness = new JSlider(0, 255, defaultBrighntessVal);
		sld_Brightness.setBackground(new Color(77, 77, 77));
		sld_Brightness.setForeground(new Color(128, 128, 128));
		sld_Brightness.setFocusable(false);
		sld_Brightness.setMajorTickSpacing(51);

		sld_Fade = new JSlider(0, 255, defaultFadeVal);
		sld_Fade.setBackground(new Color(77, 77, 77));
		sld_Fade.setForeground(new Color(128, 128, 128));
		sld_Fade.setFocusable(false);
		sld_Fade.setMajorTickSpacing(51);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Fade");
		lblNewLabel_1_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 26));

		sld_SplashMaxLenght = new JSlider(4, 16, defaultMaxSplashLengthVal);
		sld_SplashMaxLenght.setBackground(new Color(77, 77, 77));
		sld_SplashMaxLenght.setForeground(new Color(128, 128, 128));
		sld_SplashMaxLenght.setMajorTickSpacing(2);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Splash Length");
		lblNewLabel_1_1_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 26));

		bg_slider = new JSlider(10, 100);
		bg_slider.setBackground(new Color(77, 77, 77));
		bg_slider.setForeground(new Color(128, 128, 128));
		bg_slider.setSnapToTicks(true);
		bg_slider.setMajorTickSpacing(10);
		bg_slider.setValue(10);

		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("BG Brightness");
		lblNewLabel_1_1_1_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 26));

		lbl_PianoSize = new JLabel("Piano: " + GetUI.getNumPianoKeys() + " Keys");
		lbl_PianoSize.setForeground(new Color(204, 204, 204));
		lbl_PianoSize.setFont(new Font("Poppins", Font.PLAIN, 26));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 128));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(51, 51, 51));
		cb_LED_Animations = new JComboBox<Object>(GetUI.animationNames.toArray(new String[0]));
		cb_LED_Animations.setBackground(new Color(77, 77, 77));
		cb_LED_Animations.setForeground(new Color(204, 204, 204));
		cb_LED_Animations.setFont(new Font("Poppins", Font.PLAIN, 21));
		cb_LED_Animations.setFocusable(false);

		// Customize the JComboBox UI
		cb_LED_Animations.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				return new JButton() {
					@Override
					public void paint(Graphics g) {
						// Do nothing to remove the arrow button painting
					}

					@Override
					public boolean contains(int x, int y) {
						// Override contains() method to hide the button when the mouse is over it
						return false;
					}
				};
			}
		});

		cb_LED_Animations.addActionListener(e -> {
			if (ModesController.AnimationOn) {
				int selectedIndex = cb_LED_Animations.getSelectedIndex();
				pianoController.animationlist(selectedIndex);
			}
		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)).addGroup(groupLayout
								.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout
												.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
														.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
																.createParallelGroup(Alignment.LEADING)
																.addGroup(groupLayout.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(lblNewLabel_1_1_1_1)
																		.addPreferredGap(ComponentPlacement.RELATED))
																.addGroup(groupLayout.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(lblNewLabel_1_1_1_1_1_1,
																				GroupLayout.PREFERRED_SIZE, 183,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(ComponentPlacement.RELATED))
																.addGroup(groupLayout.createSequentialGroup().addGap(8)
																		.addComponent(
																				lblNewLabel, GroupLayout.DEFAULT_SIZE,
																				267, Short.MAX_VALUE)
																		.addPreferredGap(ComponentPlacement.RELATED))
																.addGroup(groupLayout.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(lblNewLabel_1_1_1_1_1)
																		.addGap(86)))
														.addGroup(groupLayout.createSequentialGroup().addContainerGap()
																.addComponent(lblNewLabel_1_1_1,
																		GroupLayout.PREFERRED_SIZE, 147,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)))
												.addGroup(groupLayout.createSequentialGroup().addContainerGap()
														.addComponent(lblNewLabel_1_1).addPreferredGap(
																ComponentPlacement.RELATED)))
										.addGroup(groupLayout.createSequentialGroup().addContainerGap()
												.addComponent(lblNewLabel_1)
												.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(sld_Brightness, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
										.addComponent(bg_slider, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
										.addComponent(sld_Fade, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
										.addGroup(groupLayout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_Load, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btn_Save, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED))
										.addComponent(cb_LED_Mode, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(sld_SplashMaxLenght, GroupLayout.DEFAULT_SIZE, 285,
												Short.MAX_VALUE)
										.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(cb_LED_Animations, 0, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))))
				.addGap(40))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(lbl_PianoSize).addContainerGap(394, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false).addGroup(groupLayout
								.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btn_Load, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_Save, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE))
								.addGap(18))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 44,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10)))
						.addGap(12)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
								.createSequentialGroup()
								.addComponent(cb_LED_Mode, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(cb_LED_Animations,
										GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
								.addComponent(
										lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
								.createSequentialGroup().addGap(7)
								.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
								.addGap(4)
								.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE)
								.addGap(4))
								.addGroup(groupLayout.createSequentialGroup().addGap(12)
										.addComponent(sld_Brightness, GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(sld_Fade, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
										.addGap(6)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblNewLabel_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 42,
												Short.MAX_VALUE)
										.addGap(6))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(sld_SplashMaxLenght, GroupLayout.DEFAULT_SIZE, 42,
												Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblNewLabel_1_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 42,
												Short.MAX_VALUE)
										.addGap(3))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(bg_slider, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
										.addGap(9))))
						.addGroup(
								groupLayout.createSequentialGroup().addGap(73)
										.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 40,
												GroupLayout.PREFERRED_SIZE)
										.addGap(227)))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGap(6))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lbl_PianoSize, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)));
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));

		btn_leftArrow = new JButton("<");
		btn_leftArrow.setForeground(new Color(204, 204, 204));
		btn_leftArrow.setBackground(new Color(77, 77, 77));
		btn_leftArrow.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_leftArrow.setFocusable(false);
		btn_leftArrow.setBorderPainted(false);
		panel_1.add(btn_leftArrow);

		btn_rightArrow = new JButton(">");
		btn_rightArrow.setForeground(new Color(204, 204, 204));
		btn_rightArrow.setBackground(new Color(77, 77, 77));
		btn_rightArrow.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_rightArrow.setFocusable(false);
		btn_rightArrow.setBorderPainted(false);

		panel_1.add(btn_rightArrow);

		btn_SetBG = new JButton("SetBG");
		btn_SetBG.setForeground(new Color(204, 204, 204));
		btn_SetBG.setBackground(new Color(77, 77, 77));
		btn_SetBG.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_SetBG.setFocusable(false);
		btn_SetBG.setBorderPainted(false);

		panel_1.add(btn_SetBG);

		panel.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(51, 51, 51));
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 4, 0, 0));

		JLabel lblNewLabel_1_1_1_1_1_1_1_1 = new JLabel("Fix LED");
		lblNewLabel_1_1_1_1_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1_1_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_3.add(lblNewLabel_1_1_1_1_1_1_1_1);

		fixToggle.setBorderPainted(false);
		fixToggle.setFocusable(false);

		panel_3.add(fixToggle);

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_2 = new JLabel("BG LED");
		lblNewLabel_1_1_1_1_1_1_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1_1_1_1_1_2.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_1_1_1_2.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_3.add(lblNewLabel_1_1_1_1_1_1_1_1_2);

		bgToggle.setBorderPainted(false);
		bgToggle.setFocusable(false);

		panel_3.add(bgToggle);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 51, 51));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 4, 0, 0));

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_1 = new JLabel("Reverse LED");
		lblNewLabel_1_1_1_1_1_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1_1_1_1_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_1_1_1_1.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_2.add(lblNewLabel_1_1_1_1_1_1_1_1_1);

		reverseToggle.setBorderPainted(false);
		reverseToggle.setFocusable(false);

		panel_2.add(reverseToggle);

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_2_1 = new JLabel("Guide LED");
		lblNewLabel_1_1_1_1_1_1_1_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1_1_1_1_1_2_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1_1_1_1_1_1_1_2_1.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_2.add(lblNewLabel_1_1_1_1_1_1_1_1_2_1);

		guideToggle.setBorderPainted(false);
		guideToggle.setFocusable(false);

		panel_2.add(guideToggle);
		setLayout(groupLayout);
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
			}
		};

		bgToggle.addActionListener(tgl_listener);
		fixToggle.addActionListener(tgl_listener);
		reverseToggle.addActionListener(tgl_listener);
		guideToggle.addActionListener(tgl_listener);
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
}
