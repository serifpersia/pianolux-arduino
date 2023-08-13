package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class pnl_Colors extends JPanel {

	private ColorPickerPanel colorPicker = new ColorPickerPanel();

	private JSlider sld_idle;

	public static JComboBox<?> cb_ColorPresets;

	public static JTextField txt_R;
	public static JTextField txt_G;
	public static JTextField txt_B;

	public static int idleTime = 10;
	public static JRadioButton rdb_IdleOn;
	public static JToggleButton keyColor_toggle;

	public pnl_Colors() {

		initComboBoxParam();
		initFieldParam();
		init();
		sliderActions();

	}

	private void sliderActions() {
		ChangeListener sliderChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (source == sld_idle) {
					int idleVal = sld_idle.getValue();
					sld_idle.setToolTipText(Integer.toString(idleVal));
					idleTime = idleVal;
				}
			}
		};

		sld_idle.addChangeListener(sliderChangeListener);

	}

	private void initComboBoxParam() {

		cb_ColorPresets = new JComboBox<Object>(GetUI.colorNames.toArray(new String[0]));
		cb_ColorPresets.putClientProperty("JComponent.roundRect", true);
		cb_ColorPresets.setForeground(new Color(204, 204, 204));
		cb_ColorPresets.setFont(new Font("Poppins", Font.PLAIN, 21));
		cb_ColorPresets.setSelectedIndex(2);

		// Add action listener to handle selection changes
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

	}

	private void initFieldParam() {
		txt_R = new JTextField("255");
		txt_G = new JTextField("0");
		txt_B = new JTextField("0");

		txt_R.setHorizontalAlignment(SwingConstants.CENTER);
		txt_R.setBackground(new Color(70, 70, 70));
		txt_R.setForeground(new Color(204, 204, 204));
		txt_R.setFont(new Font("Poppins", Font.PLAIN, 14));
		txt_R.setColumns(2);

		txt_G.setHorizontalAlignment(SwingConstants.CENTER);
		txt_G.setBackground(new Color(70, 70, 70));
		txt_G.setForeground(new Color(204, 204, 204));
		txt_G.setFont(new Font("Poppins", Font.PLAIN, 14));
		txt_G.setColumns(2);

		txt_B.setHorizontalAlignment(SwingConstants.CENTER);
		txt_B.setBackground(new Color(70, 70, 70));
		txt_B.setForeground(new Color(204, 204, 204));
		txt_B.setFont(new Font("Poppins", Font.PLAIN, 14));
		txt_B.setColumns(2);

		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source == txt_R || source == txt_G || source == txt_B) {
					updateColorSelected();
					updateColorPreset();
				}
			}
		};

		txt_R.addActionListener(actionListener);
		txt_G.addActionListener(actionListener);
		txt_B.addActionListener(actionListener);
	}

	private void init() {
		setLayout(new BorderLayout(0, 0));
		setBackground(new Color(50, 50, 50));
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblNewLabel = new JLabel("Colors");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(204, 204, 204));
		lblNewLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
		add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(50, 50, 50));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(50, 50, 50));
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblPreset = new JLabel("Preset");
		lblPreset.setBorder(new EmptyBorder(0, 0, 0, 50));
		lblPreset.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreset.setForeground(new Color(204, 204, 204));
		lblPreset.setFont(new Font("Poppins", Font.PLAIN, 24));
		panel_1.add(lblPreset);

		panel_1.add(cb_ColorPresets);

		panel.add(colorPicker, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(50, 50, 50));
		add(panel_2, BorderLayout.SOUTH);

		keyColor_toggle = new JToggleButton("C");
		keyColor_toggle.setFont(new Font("Poppins", Font.PLAIN, 14));
		keyColor_toggle.setFocusable(false);
		keyColor_toggle.setBorderPainted(false);
		
		panel_2.add(keyColor_toggle);

		rdb_IdleOn = new JRadioButton("Idle");
		rdb_IdleOn.setBackground(new Color(50, 50, 50));
		rdb_IdleOn.setForeground(new Color(204, 204, 204));
		rdb_IdleOn.setFont(new Font("Poppins", Font.PLAIN, 14));
		rdb_IdleOn.setSelected(false);

		panel_2.add(rdb_IdleOn);

		sld_idle = new JSlider(0, 240, 10);
		sld_idle.setBackground(new Color(70, 70, 70));
		sld_idle.setForeground(new Color(204, 204, 204));
		sld_idle.setFont(new Font("Poppins", Font.PLAIN, 14));
		sld_idle.setSnapToTicks(true);
		sld_idle.setMajorTickSpacing(60);
		sld_idle.setMinorTickSpacing(5);
		sld_idle.setPaintLabels(true);
		sld_idle.setValue(5);

		panel_2.add(sld_idle);

		JLabel lblNewLabel_1 = new JLabel("RGB");
		lblNewLabel_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1.setFont(new Font("Poppins", Font.PLAIN, 14));
		panel_2.add(lblNewLabel_1);

		panel_2.add(txt_R);
		panel_2.add(txt_G);
		panel_2.add(txt_B);
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

}
