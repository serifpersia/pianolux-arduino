package com.serifpersia.pianoled.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import java.awt.CardLayout;

@SuppressWarnings("serial")
public class pnl_Guide extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;

	private JPanel pnl_A;
	private JPanel pnl_A_sharp;
	private JPanel pnl_B;
	private JPanel pnl_C;
	private JPanel pnl_C_sharp;
	private JPanel pnl_D;
	private JPanel pnl_D_sharp;
	private JPanel pnl_E;
	private JPanel pnl_F;
	private JPanel pnl_F_sharp;
	private JPanel pnl_G;
	private JPanel pnl_G_sharp;

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

		setBackground(new Color(50, 50, 50));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridLayout(2, 0, 0, 0));

		JPanel pnl_Top = new JPanel();
		pnl_Top.setLayout(new GridLayout(3, 2, 0, 0));
		pnl_Top.setBackground(new Color(50, 50, 50));
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
		pnl_Bottom.setBorder(new EmptyBorder(10, 0, 0, 0));
		pnl_Bottom.setBackground(new Color(50, 50, 50));

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

		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		topPanel.setLayout(new GridLayout(0, 2, 0, 0));
		topPanel.setBackground(new Color(50, 50, 50));

		cb_ScaleKey = new JComboBox<Object>(GetUI.scaleKeyNames.toArray(new String[0]));
		cb_ScaleKey.putClientProperty("JComponent.roundRect", true);

		cb_ScaleKey.setForeground(new Color(204, 204, 204));
		cb_ScaleKey.setFont(new Font("Poppins", Font.PLAIN, 21));

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
		topPanel.add(cb_ScaleKey);

		btn_Load = new JButton("Load");
		btn_Load.setForeground(new Color(204, 204, 204));
		btn_Load.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Load.setFocusable(false);

		topPanel.add(btn_Load);

		pnl_Top.add(topPanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		bottomPanel.setLayout(new GridLayout(0, 2, 0, 0));
		bottomPanel.setBackground(new Color(50, 50, 50));

		cb_Scale = new JComboBox<Object>(GetUI.scaleNames.toArray(new String[0]));
		cb_Scale.putClientProperty("JComponent.roundRect", true);

		cb_Scale.setForeground(new Color(204, 204, 204));
		cb_Scale.setFont(new Font("Poppins", Font.PLAIN, 21));

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

		bottomPanel.add(cb_Scale);

		btn_Save = new JButton("Save");
		btn_Save.setForeground(new Color(204, 204, 204));
		btn_Save.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Save.setFocusable(false);
		bottomPanel.add(btn_Save);

		pnl_Top.add(bottomPanel);

		JPanel ResetPanel = new JPanel();
		ResetPanel.setBackground(new Color(50, 50, 50));
		pnl_Top.add(ResetPanel);
		ResetPanel.setLayout(new GridLayout(0, 1, 0, 0));

		btn_Reset = new JButton("Reset");
		btn_Reset.setForeground(new Color(204, 204, 204));
		btn_Reset.setFont(new Font("Poppins", Font.PLAIN, 21));
		btn_Reset.setFocusable(false);

		btn_Reset.setActionCommand("btn_Save");

		ResetPanel.add(btn_Reset);

	}

	private JPanel createPanelWithButtons(String[] buttonLabels, int[] buttonIndices) {
		JPanel pianoUI = new JPanel();
		pianoUI.setBackground(new Color(50, 50, 50));
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
		button.setFocusable(false);
		button.setBorderPainted(false);

		return button;
	}

	private JButton createWhiteButton(String buttonText, int buttonIndex) {
		JButton button = new JButton(buttonText);
		Color defaultBackground = UIManager.getColor("Button.background");
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buttonStates[buttonIndex] = !buttonStates[buttonIndex];

				if (buttonStates[buttonIndex]) {
					button.setBackground(Color.RED);
				} else {
					button.setBackground(defaultBackground);
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
		button.setFont(new Font("Poppins", Font.BOLD, 0));
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

		Color defaultBackground = UIManager.getColor("Button.background");
		Color defaultForeground = UIManager.getColor("Button.foreground");

		for (Component component : components) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;
				if (resetWhite) {
					if (!button.getText().startsWith("C#") && !button.getText().startsWith("D#")
							&& !button.getText().startsWith("F#") && !button.getText().startsWith("G#")
							&& !button.getText().startsWith("A#")) {

						button.setBackground(defaultBackground);
						button.setForeground(defaultForeground);
					}
				} else {
					if (button.getText().startsWith("C#") || button.getText().startsWith("D#")
							|| button.getText().startsWith("F#") || button.getText().startsWith("G#")
							|| button.getText().startsWith("A#")) {
						button.setBackground(Color.BLACK);
						button.setForeground(defaultForeground);
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
