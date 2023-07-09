package com.serifpersia.pianoled.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.serifpersia.pianoled.GuideProfile;
import com.serifpersia.pianoled.ModesController;
import com.serifpersia.pianoled.PianoController;
import com.serifpersia.pianoled.PianoLED;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.CardLayout;

@SuppressWarnings("serial")
public class pnl_Guide extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;

	JPanel pnl_A;
	JPanel pnl_A_sharp;
	JPanel pnl_B;
	JPanel pnl_C;
	JPanel pnl_C_sharp;
	JPanel pnl_D;
	JPanel pnl_D_sharp;
	JPanel pnl_E;
	JPanel pnl_F;
	JPanel pnl_F_sharp;
	JPanel pnl_G;
	JPanel pnl_G_sharp;

	public static JComboBox<Object> cb_ScaleKey;
	public static JComboBox<Object> cb_Scale;

	private JButton btn_Load;
	private JButton btn_Save;

	private static int[] scalePattern;

	boolean[] buttonStates = new boolean[23];
	private JButton btn_Reset;

	private static int currentArray;

	public static int[] getScalePattern() {
		return scalePattern;
	}

	public static int getCurrentArray() {
		return currentArray;
	}

	public pnl_Guide(PianoLED pianoLED) {
		pianoController = pianoLED.getPianoController();
		modesController = new ModesController(pianoLED);

		init();
		buttonActions(pianoLED);
	}

	private void init() {

		setBackground(new Color(51, 51, 51));
		setLayout(new GridLayout(2, 0, 0, 0));

		JPanel pnl_Top = new JPanel();
		pnl_Top.setBackground(new Color(51, 51, 51));
		add(pnl_Top);

		// A Scale
		String[] A_buttonLabels = { "", "A#", "", "", "C#", "", "D#", "", "", "F#", "", "G#", "A", "", "B", "C", "",
				"D", "", "E", "F", "", "G", "" };

		// A# Scale
		String[] A_sharp_buttonLabels = { "A#", "", "", "C#", "", "D#", "", "", "F#", "", "G#", "", "", "B", "C", "",
				"D", "", "E", "F", "", "G", "", "A" };

		// B Scale
		String[] B_buttonLabels = { "", "", "C#", "", "D#", "", "", "F#", "", "G#", "", "A#", "B", "C", "", "D", "",
				"E", "F", "", "G", "", "A", "" };

		// C Scale
		String[] C_buttonLabels = { "", "C#", "", "D#", "", "", "F#", "", "G#", "", "A#", "", "C", "", "D", "", "E",
				"F", "", "G", "", "A", "", "B" };

		// C# Scale
		String[] C_sharp_buttonLabels = { "C#", "", "D#", "", "", "F#", "", "G#", "", "A#", "", "", "", "D", "", "E",
				"F", "", "G", "", "A", "", "B", "C" };

		// D Scale
		String[] D_buttonLabels = { "", "D#", "", "", "F#", "", "G#", "", "A#", "", "", "C#", "D", "", "E", "F", "",
				"G", "", "A", "", "B", "C", "" };

		// D# Scale
		String[] D_sharp_buttonLabels = { "D#", "", "", "F#", "", "G#", "", "A#", "", "", "C#", "", "", "E", "F", "",
				"G", "", "A", "", "B", "C", "", "D" };

		// E Scale
		String[] E_buttonLabels = { "", "", "F#", "", "G#", "", "A#", "", "", "C#", "", "D#", "E", "F", "", "G", "",
				"A", "", "B", "C", "", "D", "" };

		// F Scale
		String[] F_buttonLabels = { "", "F#", "", "G#", "", "A#", "", "", "C#", "", "D#", "", "F", "", "G", "", "A", "",
				"B", "C", "", "D", "", "E" };

		// F# Scale
		String[] F_sharp_buttonLabels = { "F#", "", "G#", "", "A#", "", "", "C#", "", "D#", "", "", "", "G", "", "A",
				"", "B", "C", "", "D", "", "E", "F" };

		// G Scale
		String[] G_buttonLabels = { "", "G#", "", "A#", "", "", "C#", "", "D#", "", "", "F#", "G", "", "A", "", "B",
				"C", "", "D", "", "E", "F", "" };

		// G# Scale
		String[] G_sharp_buttonLabels = { "G#", "", "A#", "", "", "C#", "", "D#", "", "", "F#", "", "", "A", "", "B",
				"C", "", "D", "", "E", "F", "", "G" };

		int[] A_buttonIndices = { -1, 2, -1, -1, 8, -1, 12, -1, -1, 18, -1, 22, 0, -1, 4, 6, -1, 10, -1, 14, 16, -1, 20,
				-1 };
		int[] A_sharp_buttonIndices = { 0, -1, -1, 6, -1, 10, 12, -1, 16, -1, 20, -1, -1, 2, 4, -1, 8, -1, 12, 14, -1,
				18, -1, 22 };

		int[] C_buttonIndices = { -1, 2, -1, 6, -1, -1, 12, -1, 16, -1, 20, -1, 0, -1, 4, -1, 8, 10, -1, 14, -1, 18, -1,
				22 };
		int[] B_buttonIndices = { -1, -1, 4, -1, 8, -1, -1, 14, -1, 18, -1, 22, 0, 2, -1, 6, -1, 10, 12, -1, 16, -1, 20,
				-1 };
		int[] C_sharp_buttonIndices = { 0, -1, 4, -1, -1, 10, -1, 14, -1, 18, -1, -1, -1, 2, -1, 6, 8, -1, 12, -1, 16,
				-1, 20, 22 };
		int[] D_buttonIndices = { -1, 2, -1, -1, 8, -1, 12, -1, 16, -1, -1, 22, 0, -1, 4, 6, -1, 10, -1, 14, -1, 18, 20,
				-1 };
		int[] D_sharp_buttonIndices = { 0, -1, -1, 6, -1, 10, -1, 14, -1, -1, 20, -1, -1, 2, 4, -1, 8, -1, 12, -1, 16,
				18, -1, 22 };
		int[] E_buttonIndices = { 0, -1, 4, -1, 8, -1, 12, -1, -1, 18, -1, 22, 0, 2, -1, 6, -1, 10, -1, 14, 16, -1, 20,
				-1 };
		int[] F_buttonIndices = { -1, 2, -1, 6, -1, 10, 12, -1, 16, -1, 20, -1, 0, -1, 4, -1, 8, -1, 12, 14, -1, 18, -1,
				22 };
		int[] F_sharp_buttonIndices = { 0, -1, 4, -1, 8, -1, -1, 14, -1, 18, -1, -1, -1, 2, -1, 6, -1, 10, 12, -1, 16,
				-1, 20, 22 };
		int[] G_buttonIndices = { -1, 2, -1, 6, -1, -1, 12, -1, 16, -1, -1, 22, 0, -1, 4, -1, 8, 10, -1, 14, -1, 18, 20,
				-1 };
		int[] G_sharp_buttonIndices = { 0, -1, 4, -1, -1, 10, -1, 14, -1, -1, 20, -1, -1, 2, -1, 6, 8, -1, 12, -1, 16,
				18, -1, 22 };

		pnl_A = createPanelWithButtons(A_buttonLabels, A_buttonIndices);

		pnl_A_sharp = createPanelWithButtons(A_sharp_buttonLabels, A_sharp_buttonIndices);

		pnl_B = createPanelWithButtons(B_buttonLabels, B_buttonIndices);

		pnl_C = createPanelWithButtons(C_buttonLabels, C_buttonIndices);

		pnl_C_sharp = createPanelWithButtons(C_sharp_buttonLabels, C_sharp_buttonIndices);

		pnl_D = createPanelWithButtons(D_buttonLabels, D_buttonIndices);

		pnl_D_sharp = createPanelWithButtons(D_sharp_buttonLabels, D_sharp_buttonIndices);

		pnl_E = createPanelWithButtons(E_buttonLabels, E_buttonIndices);

		pnl_F = createPanelWithButtons(F_buttonLabels, F_buttonIndices);

		pnl_F_sharp = createPanelWithButtons(F_sharp_buttonLabels, F_sharp_buttonIndices);

		pnl_G = createPanelWithButtons(G_buttonLabels, G_buttonIndices);

		pnl_G_sharp = createPanelWithButtons(G_sharp_buttonLabels, G_sharp_buttonIndices);

		JPanel pnl_Bottom = new JPanel(new CardLayout());
		pnl_Bottom.setBackground(new Color(51, 51, 51));

		pnl_Bottom.add(pnl_A, "A");
		pnl_Bottom.add(pnl_A_sharp, "A#");
		pnl_Bottom.add(pnl_B, "B");
		pnl_Bottom.add(pnl_C, "C");
		pnl_Bottom.add(pnl_C_sharp, "C#");
		pnl_Bottom.add(pnl_D, "D");
		pnl_Bottom.add(pnl_D_sharp, "D#");
		pnl_Bottom.add(pnl_E, "E");
		pnl_Bottom.add(pnl_F, "F");
		pnl_Bottom.add(pnl_F_sharp, "F#");
		pnl_Bottom.add(pnl_G, "G");
		pnl_Bottom.add(pnl_G_sharp, "G#");

		add(pnl_Bottom);

		cb_ScaleKey = new JComboBox<Object>(GetUI.scaleKeyNames.toArray(new String[0]));
		cb_ScaleKey.setBackground(new Color(77, 77, 77));
		cb_ScaleKey.setForeground(new Color(204, 204, 204));
		cb_ScaleKey.setFont(new Font("Poppins", Font.PLAIN, 21));
		cb_ScaleKey.setFocusable(false); // Set the JComboBox as non-focusable

		// Customize the JComboBox UI
		cb_ScaleKey.setUI(new BasicComboBoxUI() {
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
		cb_ScaleKey.addActionListener(e -> {
			String selectedScaleKeyName = (String) cb_ScaleKey.getSelectedItem();

			modesController.ScaleKeySelect(selectedScaleKeyName);

			// Switch panels based on selected index
			CardLayout cardLayout = (CardLayout) pnl_Bottom.getLayout();
			cardLayout.show(pnl_Bottom, selectedScaleKeyName);

			pianoController.guideToggle = false;
			pianoController.setLedGuide(pianoController.guideToggle);
			pianoController.setLedGuide(pianoController.guideToggle);

			pianoController.guideToggle = true;
			pianoController.setLedGuide(pianoController.guideToggle);
			pianoController.setLedGuide(pianoController.guideToggle);

		});

		cb_Scale = new JComboBox<Object>(GetUI.scaleNames.toArray(new String[0]));
		cb_Scale.setBackground(new Color(77, 77, 77));
		cb_Scale.setForeground(new Color(204, 204, 204));
		cb_Scale.setFont(new Font("Poppins", Font.PLAIN, 21));
		cb_Scale.setFocusable(false); // Set the JComboBox as non-focusable

		// Customize the JComboBox UI
		cb_Scale.setUI(new BasicComboBoxUI() {
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
		cb_Scale.addActionListener(e -> {
			String selectedScaleName = (String) cb_Scale.getSelectedItem();

			modesController.ScaleSelect(selectedScaleName);

			pianoController.guideToggle = false;
			pianoController.setLedGuide(pianoController.guideToggle);
			pianoController.setLedGuide(pianoController.guideToggle);

			pianoController.guideToggle = true;
			pianoController.setLedGuide(pianoController.guideToggle);
			pianoController.setLedGuide(pianoController.guideToggle);
		});

		btn_Load = new JButton("Load");
		btn_Load.setForeground(new Color(204, 204, 204));
		btn_Load.setBackground(new Color(77, 77, 77));
		btn_Load.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Load.setFocusable(false);
		btn_Load.setBorderPainted(false);

		btn_Save = new JButton("Save");
		btn_Save.setForeground(new Color(204, 204, 204));
		btn_Save.setBackground(new Color(77, 77, 77));
		btn_Save.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Save.setFocusable(false);
		btn_Save.setBorderPainted(false);

		btn_Save.setFont(new Font("Tahoma", Font.BOLD, 20));

		btn_Reset = new JButton("Reset");
		btn_Reset.setForeground(new Color(204, 204, 204));
		btn_Reset.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Reset.setFocusable(false);
		btn_Reset.setBorderPainted(false);
		btn_Reset.setBackground(new Color(77, 77, 77));
		btn_Reset.setActionCommand("btn_Save");
		GroupLayout gl_pnl_Top = new GroupLayout(pnl_Top);
		gl_pnl_Top
				.setHorizontalGroup(gl_pnl_Top.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_Top.createSequentialGroup().addContainerGap()
								.addGroup(gl_pnl_Top.createParallelGroup(Alignment.LEADING)
										.addComponent(cb_ScaleKey, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_Scale, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(156)
								.addGroup(gl_pnl_Top.createParallelGroup(Alignment.TRAILING)
										.addComponent(btn_Load, GroupLayout.PREFERRED_SIZE, 130,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_Save, GroupLayout.PREFERRED_SIZE, 130,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_Reset, GroupLayout.PREFERRED_SIZE, 130,
												GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));
		gl_pnl_Top.setVerticalGroup(gl_pnl_Top.createParallelGroup(Alignment.LEADING).addGroup(gl_pnl_Top
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_pnl_Top.createParallelGroup(Alignment.LEADING, false).addComponent(btn_Load)
						.addComponent(cb_ScaleKey, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
				.addGap(0)
				.addGroup(gl_pnl_Top.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_pnl_Top.createSequentialGroup().addGap(26).addComponent(cb_Scale,
								GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnl_Top.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED, 1, Short.MAX_VALUE)
								.addComponent(btn_Save, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btn_Reset)))
				.addGap(7)));
		pnl_Top.setLayout(gl_pnl_Top);

	}

	private JPanel createPanelWithButtons(String[] buttonLabels, int[] buttonIndices) {
		JPanel pianoUI = new JPanel();
		pianoUI.setBackground(new Color(51, 51, 51));
		pianoUI.setLayout(new GridLayout(2, buttonLabels.length / 2, 0, 0));

		for (int i = 0; i < buttonLabels.length; i++) {
			String labelText = buttonLabels[i];
			int buttonIndex = buttonIndices[i];

			if (labelText.startsWith("C#") || labelText.startsWith("D#") || labelText.startsWith("F#")
					|| labelText.startsWith("G#") || labelText.startsWith("A#")) {
				pianoUI.add(createBlackButton(labelText, buttonIndex));
			} else if (!labelText.isEmpty()) {
				pianoUI.add(createWhiteButton(labelText, buttonIndex));
			} else {
				pianoUI.add(createSpaceButtons(""));
			}
		}

		return pianoUI;
	}

	private JButton createSpaceButtons(String buttonText) {
		JButton button = new JButton(buttonText);
		button.setBackground(Color.WHITE);
		button.setFocusable(false);
		button.setBorderPainted(false);

		return button;
	}

	private JButton createWhiteButton(String buttonText, int buttonIndex) {
		JButton button = new JButton(buttonText);
		button.setBackground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buttonStates[buttonIndex] = !buttonStates[buttonIndex];

				if (buttonStates[buttonIndex]) {
					button.setBackground(Color.RED);
					button.setForeground(Color.WHITE);
				} else {
					button.setBackground(Color.WHITE);
					button.setForeground(Color.BLACK);
				}

				updateButtonStates();
				modesController.setCustomScale();

				pianoController.guideToggle = false;
				pianoController.setLedGuide(pianoController.guideToggle);
				pianoController.setLedGuide(pianoController.guideToggle);

				pianoController.guideToggle = true;
				pianoController.setLedGuide(pianoController.guideToggle);
				pianoController.setLedGuide(pianoController.guideToggle);
			}
		});
		return button;
	}

	private JButton createBlackButton(String buttonText, int buttonIndex) {
		JButton button = new JButton(buttonText);
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 10));
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buttonStates[buttonIndex] = !buttonStates[buttonIndex];

				if (buttonStates[buttonIndex]) {
					button.setBackground(Color.RED);

				} else {
					button.setBackground(Color.BLACK);
				}

				updateButtonStates();
				modesController.setCustomScale();
				pianoController.guideToggle = false;
				pianoController.setLedGuide(pianoController.guideToggle);
				pianoController.setLedGuide(pianoController.guideToggle);

				pianoController.guideToggle = true;
				pianoController.setLedGuide(pianoController.guideToggle);
				pianoController.setLedGuide(pianoController.guideToggle);
			}
		});
		return button;
	}

	private void updateButtonStates() {
		// Count the number of toggled buttons
		int count = 0;
		for (int i = 0; i < buttonStates.length; i++) {
			if (buttonStates[i]) {
				count++;
			}
		}

		// Populate the array with the indices of the toggled buttons
		scalePattern = new int[count];
		int index = 0;
		for (int i = 0; i < buttonStates.length; i++) {
			if (buttonStates[i]) {
				scalePattern[index] = i;
				index++;
			}
		}

		currentArray = scalePattern.length;

	}

	private void resetButtons(boolean resetWhite) {
		// Reset button states to off toggle state
		for (int i = 0; i < buttonStates.length; i++) {
			buttonStates[i] = false;
		}

		// Reset button appearance to default in all panels
		resetButtonsInPanel(pnl_A, resetWhite);
		resetButtonsInPanel(pnl_A_sharp, !resetWhite);
		resetButtonsInPanel(pnl_B, resetWhite);
		resetButtonsInPanel(pnl_C, resetWhite);
		resetButtonsInPanel(pnl_C_sharp, !resetWhite);
		resetButtonsInPanel(pnl_D, resetWhite);
		resetButtonsInPanel(pnl_D_sharp, !resetWhite);
		resetButtonsInPanel(pnl_E, resetWhite);
		resetButtonsInPanel(pnl_F, resetWhite);
		resetButtonsInPanel(pnl_F_sharp, !resetWhite);
		resetButtonsInPanel(pnl_G, resetWhite);
		resetButtonsInPanel(pnl_G_sharp, !resetWhite);

		updateButtonStates();
		modesController.setCustomScale();

		pianoController.guideToggle = false;
		pianoController.setLedGuide(pianoController.guideToggle);
		pianoController.setLedGuide(pianoController.guideToggle);

		pianoController.guideToggle = true;
		pianoController.setLedGuide(pianoController.guideToggle);
		pianoController.setLedGuide(pianoController.guideToggle);
	}

	private void resetButtonsInPanel(JPanel panel, boolean resetWhite) {
		Component[] components = panel.getComponents();
		for (Component component : components) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;
				if (resetWhite) {
					if (!button.getText().startsWith("C#") && !button.getText().startsWith("D#")
							&& !button.getText().startsWith("F#") && !button.getText().startsWith("G#")
							&& !button.getText().startsWith("A#")) {
						button.setBackground(Color.WHITE);
						button.setForeground(Color.BLACK);
					}
				} else {
					if (button.getText().startsWith("C#") || button.getText().startsWith("D#")
							|| button.getText().startsWith("F#") || button.getText().startsWith("G#")
							|| button.getText().startsWith("A#")) {
						button.setBackground(Color.BLACK);
						button.setForeground(Color.WHITE);
					}
				}
			}
		}
	}

	private void buttonActions(PianoLED pianoLED) {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "btn_Load":
					GuideProfile.loadProfile(pianoLED);

					pianoController.guideToggle = false;
					pianoController.setLedGuide(pianoController.guideToggle);
					pianoController.setLedGuide(pianoController.guideToggle);

					pianoController.guideToggle = true;
					pianoController.setLedGuide(pianoController.guideToggle);
					pianoController.setLedGuide(pianoController.guideToggle);
					break;
				case "btn_Save":
					GuideProfile.saveProfile(pianoLED);
					break;

				case "btn_Reset":
					// Reset white buttons
					resetButtons(true);

					// Reset black buttons
					resetButtons(false);
					break;
				default:
					break;
				}
			}
		};

		btn_Load.addActionListener(buttonListener);
		btn_Save.addActionListener(buttonListener);
		btn_Reset.addActionListener(buttonListener);

		btn_Load.setActionCommand("btn_Load");
		btn_Save.setActionCommand("btn_Save");
		btn_Reset.setActionCommand("btn_Reset");

	}
}