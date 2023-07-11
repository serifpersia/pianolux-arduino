package com.serifpersia.pianoled.ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;
import javax.swing.JRadioButton;

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

	public pnl_Colors() {

		initComboBoxParam();
		initFieldParam();
		fieldActions();
		init();
		sliderActions();

	}

	private void initFieldParam() {

		txt_R = new JTextField("255");
		txt_R.setForeground(new Color(204, 204, 204));
		txt_R.setHorizontalAlignment(SwingConstants.CENTER);
		txt_R.setFont(new Font("Poppins", Font.PLAIN, 18));
		txt_R.setColumns(10);

		txt_G = new JTextField("0");
		txt_G.setForeground(new Color(204, 204, 204));
		txt_G.setHorizontalAlignment(SwingConstants.CENTER);
		txt_G.setFont(new Font("Poppins", Font.PLAIN, 18));
		txt_G.setColumns(10);

		txt_B = new JTextField("0");
		txt_B.setForeground(new Color(204, 204, 204));
		txt_B.setHorizontalAlignment(SwingConstants.CENTER);
		txt_B.setFont(new Font("Poppins", Font.PLAIN, 18));
		txt_B.setColumns(10);

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

	private void init() {
		setBackground(new Color(50, 50, 50));

		JLabel lb_Colors = new JLabel("Colors");
		lb_Colors.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Colors.setForeground(new Color(204, 204, 204));
		lb_Colors.setFont(new Font("Poppins", Font.PLAIN, 35));

		JLabel lb_Preset = new JLabel("Preset");
		lb_Preset.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Preset.setForeground(new Color(204, 204, 204));
		lb_Preset.setFont(new Font("Poppins", Font.PLAIN, 24));

		JPanel colorPickerPanel = new JPanel();

		JLabel lb_RGB = new JLabel("RGB");
		lb_RGB.setForeground(new Color(204, 204, 204));
		lb_RGB.setHorizontalAlignment(SwingConstants.CENTER);
		lb_RGB.setFont(new Font("Poppins", Font.PLAIN, 18));

		JLabel lb_RGB_1 = new JLabel("Idle");
		lb_RGB_1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_RGB_1.setForeground(new Color(204, 204, 204));
		lb_RGB_1.setFont(new Font("Poppins", Font.PLAIN, 18));

		sld_idle = new JSlider(5, 240, 10);
		sld_idle.setFont(new Font("Poppins", Font.PLAIN, 10));
		sld_idle.setSnapToTicks(true);
		sld_idle.setMinorTickSpacing(5);
		sld_idle.setPaintLabels(true);
		sld_idle.setForeground(new Color(204, 204, 204));
		sld_idle.setMajorTickSpacing(60);

		rdb_IdleOn = new JRadioButton("On");
		rdb_IdleOn.setFont(new Font("Poppins", Font.PLAIN, 11));
		rdb_IdleOn.setHorizontalAlignment(SwingConstants.CENTER);
		rdb_IdleOn.setSelected(false);

		GroupLayout gl_right = new GroupLayout(this);
		gl_right.setHorizontalGroup(gl_right.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_right.createSequentialGroup().addContainerGap()
						.addComponent(lb_RGB_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE).addGap(4)
						.addComponent(rdb_IdleOn).addGap(3)
						.addComponent(sld_idle, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lb_RGB, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE).addGap(5)
						.addComponent(txt_R, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE).addGap(5)
						.addComponent(txt_G, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE).addGap(5)
						.addComponent(txt_B, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(30, Short.MAX_VALUE))
				.addGroup(
						gl_right.createSequentialGroup().addGap(85)
								.addComponent(lb_Preset, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE).addGap(71)
								.addComponent(cb_ColorPresets, GroupLayout.PREFERRED_SIZE, 215,
										GroupLayout.PREFERRED_SIZE)
								.addGap(30))
				.addGroup(gl_right.createSequentialGroup().addContainerGap()
						.addComponent(lb_Colors, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE).addContainerGap())
				.addGroup(gl_right.createSequentialGroup().addGap(25)
						.addComponent(colorPickerPanel, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_right.setVerticalGroup(gl_right.createParallelGroup(Alignment.LEADING).addGroup(gl_right
				.createSequentialGroup().addGap(10)
				.addComponent(lb_Colors, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(5)
				.addGroup(gl_right.createParallelGroup(Alignment.LEADING)
						.addComponent(cb_ColorPresets, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(lb_Preset, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(5).addComponent(colorPickerPanel, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
				.addGap(5)
				.addGroup(gl_right.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_right.createParallelGroup(Alignment.BASELINE)
								.addComponent(lb_RGB_1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdb_IdleOn, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_right.createParallelGroup(Alignment.BASELINE)
								.addComponent(lb_RGB, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_R, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_G, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_B, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addComponent(sld_idle, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(32, Short.MAX_VALUE)));
		colorPickerPanel.setLayout(new BorderLayout(0, 0));

		colorPickerPanel.add(colorPicker, BorderLayout.CENTER);
		setLayout(gl_right);

	}

	private void fieldActions() {
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
