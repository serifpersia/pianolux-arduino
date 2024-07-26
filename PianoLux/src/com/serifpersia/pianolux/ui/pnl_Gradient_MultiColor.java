package com.serifpersia.pianolux.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.serifpersia.pianolux.ModesController;
import com.serifpersia.pianolux.PianoLux;

@SuppressWarnings("serial")
public class pnl_Gradient_MultiColor extends JPanel {

	private ModesController modesController;
	public static JComboBox<?> cb_GradientSideList;
	private JPanel pnl_GradientControls;
	private JPanel GradientCard;
	private JButton[][] gradientButtons = new JButton[8][];
	private JButton[] setButtons;
	private String[] setCommands = { "btn_SetA", "btn_SetASharp", "btn_SetB", "btn_SetC", "btn_SetCSharp", "btn_SetD",
			"btn_SetDSharp", "btn_SetE", "btn_SetF", "btn_SetFSharp", "btn_SetG", "btn_SetGSharp" };
	public int divisionCount = 2;
	public static Color[] colors = new Color[8];
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;

	public pnl_Gradient_MultiColor(PianoLux pianoLux) {
		modesController = new ModesController(pianoLux);
		initializeColors();
		initComponents();
		buttonActions();
	}

	private void initializeColors() {
		Color[] predefinedColors = { Color.RED, Color.GREEN, Color.BLUE };
		for (int i = 0; i < colors.length; i++) {
			colors[i] = predefinedColors[i % predefinedColors.length];
		}
	}

	private void initComponents() {
		setBackground(new Color(51, 51, 51));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0, 0));

		initButtons();

		pnl_GradientControls = createPanel(new BorderLayout(0, 0), new Color(51, 51, 51));
		add(pnl_GradientControls, BorderLayout.CENTER);

		cb_GradientSideList = createComboBox(GetUI.gradientSides.toArray(new String[0]), 30);
		cb_GradientSideList.addActionListener(e -> {
			divisionCount = cb_GradientSideList.getSelectedIndex() + 2;
			ControlsPanel.pnl_GradientPreview.repaint();
			modesController.gradientSideSelect(cb_GradientSideList.getSelectedIndex());
			updateCardLayout();
		});
		pnl_GradientControls.add(cb_GradientSideList, BorderLayout.NORTH);

		GradientCard = createPanel(new CardLayout(0, 0), new Color(51, 51, 51));
		pnl_GradientControls.add(GradientCard, BorderLayout.CENTER);

		ImageIcon setIcon = createScaledIcon("/icons/Set.png", 90, 45);
		for (int i = 2; i <= 8; i++) {
			GradientCard.add(createGradientPanel(i, i - 2, setIcon), i + " Side Gradient");
		}

		panel = createPanel(new GridLayout(2, 0, 0, 0), new Color(50, 50, 50));
		add(panel, BorderLayout.SOUTH);

		panel_1 = createPanel(new GridLayout(0, 2, 0, 0), new Color(50, 50, 50));
		panel.add(panel_1);

		panel_2 = createPanel(new GridLayout(2, 0, 0, 0), new Color(50, 50, 50));
		panel.add(panel_2);

		setButtons = new JButton[setCommands.length];
		String[] buttonLabels = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
		for (int i = 0; i < setButtons.length; i++) {
			setButtons[i] = createButton(buttonLabels[i], null, setCommands[i]);
			panel_2.add(setButtons[i]);
		}
	}

	private void buttonActions() {
		ActionListener buttonListener = e -> {
			JButton sourceButton = (JButton) e.getSource();
			for (int i = 0; i < gradientButtons.length; i++) {
				for (int j = 0; j < gradientButtons[i].length; j++) {
					if (sourceButton == gradientButtons[i][j]) {
						if (j < colors.length) {
							colors[j] = GetUI.selectedColor;
							ControlsPanel.pnl_GradientPreview.repaint();
						}
						return;
					}
				}
			}
		};

		for (int i = 0; i < gradientButtons.length; i++) {
			for (int j = 0; j < gradientButtons[i].length; j++) {
				gradientButtons[i][j].setActionCommand("btn_Gradient" + (i + 2) + "_SetSide" + (j + 1));
				gradientButtons[i][j].addActionListener(buttonListener);
			}
		}

		for (JButton setButton : setButtons) {
			setButton.addActionListener(this::handleButtonAction);
		}
	}

	private void handleButtonAction(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		int index = Arrays.asList(setCommands).indexOf(actionCommand);
		if (index >= 0 && index < GetUI.multiColors.length) {
			GetUI.multiColors[index] = GetUI.selectedColor;
		}
	}

	private void initButtons() {
		ImageIcon setIcon = createScaledIcon("/icons/Set.png", 90, 45);
		int[] buttonCounts = { 2, 3, 4, 5, 6, 7, 8, 8 };
		for (int i = 0; i < buttonCounts.length; i++) {
			gradientButtons[i] = new JButton[buttonCounts[i]];
			for (int j = 0; j < buttonCounts[i]; j++) {
				// Use the createButton method with icon and no action command
				gradientButtons[i][j] = createButton("", setIcon, null);
			}
		}
	}

	private JPanel createGradientPanel(int sideCount, int index, ImageIcon icon) {
		JPanel panel = createPanel(new GridLayout(sideCount, 2, 0, 0), new Color(51, 51, 51));
		for (int i = 0; i < sideCount; i++) {
			panel.add(createLabel("Side " + (i + 1), SwingConstants.CENTER, Color.WHITE,
					new Font("Poppins", Font.PLAIN, 21)));
			JButton button = createButton("", icon, null); // Use the createButton method with icon and no action
															// command
			panel.add(button);
			gradientButtons[index][i] = button;
		}
		return panel;
	}

	private void updateCardLayout() {
		CardLayout cardLayout = (CardLayout) GradientCard.getLayout();
		String selectedGradient = (String) cb_GradientSideList.getSelectedItem();
		if (selectedGradient != null) {
			cardLayout.show(GradientCard, selectedGradient);
		}
	}

	private JPanel createPanel(LayoutManager layout, Color background) {
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		panel.setBackground(background);
		return panel;
	}

	private JButton createButton(String text, ImageIcon icon, String actionCommand) {
		JButton button = new JButton(text);
		if (icon != null) {
			button.setIcon(icon);
		}
		button.setBackground(new Color(51, 51, 51));
		button.setBorderPainted(false);
		button.setFocusable(false);
		button.setActionCommand(actionCommand);
		return button;
	}

	private JComboBox<?> createComboBox(String[] items, int fontSize) {
		JComboBox<?> comboBox = new JComboBox<>(items);
		comboBox.putClientProperty("JComponent.roundRect", true);
		comboBox.setForeground(new Color(204, 204, 204));
		comboBox.setFont(new Font("Poppins", Font.PLAIN, fontSize));
		return comboBox;
	}

	private JLabel createLabel(String text, int horizontalAlignment, Color foreground, Font font) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(horizontalAlignment);
		label.setForeground(foreground);
		label.setFont(font);
		return label;
	}

	private ImageIcon createScaledIcon(String path, int width, int height) {
		return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(width, height,
				Image.SCALE_SMOOTH));
	}
}
