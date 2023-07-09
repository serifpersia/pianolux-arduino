package com.serifpersia.pianoled.ui;

import java.awt.Color;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class pnl_Colors extends JPanel {

	private ColorPickerPanel colorPicker = new ColorPickerPanel();

	public static JComboBox<?> cb_ColorPresets;

	public static JTextField txt_B;
	public static JTextField txt_G;
	public static JTextField txt_R;

	public pnl_Colors() {
		init();
	}

	private void init() {
		setBackground(new Color(51, 51, 51));
		setLayout(new BorderLayout(0, 0));

		JLabel lb_Colors = new JLabel("Colors");
		lb_Colors.setForeground(new Color(204, 204, 204));
		lb_Colors.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Colors.setFont(new Font("Poppins", Font.PLAIN, 28));
		add(lb_Colors, BorderLayout.NORTH);

		JPanel pnl_MainColors = new JPanel();
		pnl_MainColors.setBackground(new Color(51, 51, 51));
		add(pnl_MainColors, BorderLayout.CENTER);

		JPanel pnl_Presets = new JPanel();
		pnl_Presets.setBackground(new Color(51, 51, 51));

		JPanel pnl_Colorpicker = new JPanel();
		pnl_Colorpicker.setBackground(new Color(51, 51, 51));

		JLabel lblNewLabel = new JLabel("RGB");
		lblNewLabel.setForeground(new Color(204, 204, 204));
		lblNewLabel.setFont(new Font("Gotham Rounded Book", Font.PLAIN, 21));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		txt_R = new JTextField("255");
		txt_R.setHorizontalAlignment(SwingConstants.CENTER);
		txt_R.setBackground(new Color(77, 77, 77));
		txt_R.setForeground(new Color(204, 204, 204));
		txt_R.setFont(new Font("Poppins", Font.PLAIN, 21));

		txt_G = new JTextField("0");
		txt_G.setHorizontalAlignment(SwingConstants.CENTER);
		txt_G.setBackground(new Color(77, 77, 77));
		txt_G.setForeground(new Color(204, 204, 204));
		txt_G.setFont(new Font("Poppins", Font.PLAIN, 21));

		txt_B = new JTextField("0");
		txt_B.setBackground(new Color(77, 77, 77));
		txt_B.setForeground(new Color(204, 204, 204));
		txt_B.setFont(new Font("Poppins", Font.PLAIN, 21));
		txt_B.setHorizontalAlignment(SwingConstants.CENTER);

		txt_R.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateColorSelected();
				updateColorPreset();
			}
		});

		txt_G.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateColorSelected();
				updateColorPreset();
			}
		});

		txt_B.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateColorSelected();
				updateColorPreset();
			}
		});

		GroupLayout gl_pnl_MainColors = new GroupLayout(pnl_MainColors);
		gl_pnl_MainColors.setHorizontalGroup(
			gl_pnl_MainColors.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_MainColors.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnl_MainColors.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnl_MainColors.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_R, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_G, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_B, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addGap(35))
						.addGroup(gl_pnl_MainColors.createSequentialGroup()
							.addComponent(pnl_Colorpicker, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_pnl_MainColors.createSequentialGroup()
							.addComponent(pnl_Presets, GroupLayout.PREFERRED_SIZE, 403, Short.MAX_VALUE)
							.addGap(37))))
		);
		gl_pnl_MainColors.setVerticalGroup(
			gl_pnl_MainColors.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_MainColors.createSequentialGroup()
					.addGap(29)
					.addComponent(pnl_Presets, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pnl_Colorpicker, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
					.addGroup(gl_pnl_MainColors.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_MainColors.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_pnl_MainColors.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(txt_R, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(txt_G, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(txt_B, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 28, Short.MAX_VALUE)))
						.addGroup(gl_pnl_MainColors.createSequentialGroup()
							.addGap(21)
							.addComponent(lblNewLabel)))
					.addGap(24))
		);
		pnl_Colorpicker.setLayout(new BorderLayout(0, 0));

		pnl_Colorpicker.add(colorPicker, BorderLayout.CENTER);

		pnl_Presets.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lb_Preset = new JLabel("Preset");
		lb_Preset.setForeground(new Color(204, 204, 204));
		lb_Preset.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Preset.setFont(new Font("Poppins", Font.PLAIN, 21));
		pnl_Presets.add(lb_Preset);

		cb_ColorPresets = new JComboBox<Object>(GetUI.colorNames.toArray(new String[0]));
		cb_ColorPresets.setBackground(new Color(77, 77, 77));
		cb_ColorPresets.setForeground(new Color(204, 204, 204));
		cb_ColorPresets.setFont(new Font("Poppins", Font.PLAIN, 21));
		cb_ColorPresets.setFocusable(false); // Set the JComboBox as non-focusable
		cb_ColorPresets.setSelectedIndex(2);

		// Customize the JComboBox UI
		cb_ColorPresets.setUI(new BasicComboBoxUI() {
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

		pnl_Presets.add(cb_ColorPresets);

		pnl_MainColors.setLayout(gl_pnl_MainColors);
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
