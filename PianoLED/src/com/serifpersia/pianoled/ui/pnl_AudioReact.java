package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.serifpersia.pianoled.ModesController;
import com.serifpersia.pianoled.PianoController;
import com.serifpersia.pianoled.PianoLED;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class pnl_AudioReact extends JPanel {

	private ModesController modesController;
	private PianoController pianoController;
	private JComboBox cb_AudioReactLEDEffect;
	private JComboBox cb_AudioDevice;

	public pnl_AudioReact(PianoLED pianoLED) {
		setBackground(new Color(50, 50, 50));

		pianoController = pianoLED.getPianoController();
		modesController = new ModesController(pianoLED);

		init();
		buttonActions(pianoLED);

	}

	private void init() {
		setBorder(new EmptyBorder(10, 10, 10, 10));

		setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(50, 50, 50));
		add(panel);
		panel.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel_1.setBackground(new Color(50, 50, 50));
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblAudioDevice = new JLabel("Audio Device");
		lblAudioDevice.setHorizontalAlignment(SwingConstants.CENTER);
		lblAudioDevice.setForeground(new Color(208, 208, 208));
		lblAudioDevice.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_1.add(lblAudioDevice);

		cb_AudioDevice = new JComboBox();
		cb_AudioDevice.setFont(new Font("Poppins", Font.PLAIN, 23));
		panel_1.add(cb_AudioDevice);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel_2.setBackground(new Color(50, 50, 50));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblReactLedEffect_1 = new JLabel("React LED Effect");
		lblReactLedEffect_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblReactLedEffect_1.setForeground(new Color(208, 208, 208));
		lblReactLedEffect_1.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_2.add(lblReactLedEffect_1);

		cb_AudioReactLEDEffect = new JComboBox();
		cb_AudioReactLEDEffect.setFont(new Font("Poppins", Font.PLAIN, 23));
		panel_2.add(cb_AudioReactLEDEffect);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel.add(btnNewButton);
	}

	private void buttonActions(PianoLED pianoLED) {

	}
}
