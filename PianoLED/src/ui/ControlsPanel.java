package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	DrawPiano pianoKeyboard = new DrawPiano();

	ColorPicker colorPicker = new ColorPicker();

	static Color selectedColor = Color.RED;
	static JTextField txtR;
	static JTextField txtG;
	static JTextField txtB;

	Color[] presetColors = { Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, new Color(255, 100, 0), // Yellow
			new Color(255, 35, 0), // Orange
			new Color(128, 0, 255), // Purple
			new Color(255, 35, 35), // Pink
			new Color(0, 255, 255), // Teal
			new Color(128, 255, 0), // Lime
			Color.CYAN, Color.MAGENTA, new Color(255, 25, 25), // Peach
			new Color(160, 128, 255), // Lavender
			new Color(128, 192, 192), // Turquoise
			new Color(255, 80, 0) // Gold
	};

	List<String> colorNames = Arrays.asList("White", "Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink",
			"Teal", "Lime", "Cyan", "Magenta", "Peach", "Lavender", "Turquoise", "Gold");

	public ControlsPanel() {

		setBackground(new Color(21, 25, 28));
		setLayout(null);

		JLabel lbControls = new JLabel("Controls");
		lbControls.setBounds(630, 10, 175, 30);
		lbControls.setHorizontalAlignment(SwingConstants.CENTER);
		lbControls.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbControls.setForeground(new Color(255, 255, 255));
		add(lbControls);

		JLabel lbLEDEffect = new JLabel("LED Effect");
		lbLEDEffect.setHorizontalAlignment(SwingConstants.CENTER);
		lbLEDEffect.setForeground(Color.WHITE);
		lbLEDEffect.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbLEDEffect.setBounds(1, 95, 209, 47);
		add(lbLEDEffect);

		JComboBox<Object> LEDEffects = new JComboBox<Object>();
		LEDEffects.setBounds(10, 153, 200, 25);
		add(LEDEffects);

		JLabel lbPianoSize = new JLabel("Piano: " + PianoSizeArrows.getNumPianoKeys() + " Keys");
		lbPianoSize.setHorizontalAlignment(SwingConstants.CENTER);
		lbPianoSize.setForeground(Color.WHITE);
		lbPianoSize.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbPianoSize.setBounds(0, 430, 250, 50);
		add(lbPianoSize);

		JButton lbLeftArrow = new JButton("<");

		lbLeftArrow.setOpaque(true);
		lbLeftArrow.setForeground(Color.BLACK);
		lbLeftArrow.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbLeftArrow.setFocusable(false);
		lbLeftArrow.setBorderPainted(false);
		lbLeftArrow.setBackground(Color.WHITE);
		lbLeftArrow.setBounds(27, 487, 72, 41);
		add(lbLeftArrow);
		lbLeftArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (PianoSizeArrows.counter <= 0) {
					return;
				}
				PianoSizeArrows.counter--;
				PianoSizeArrows.setKeyboardSize(PianoSizeArrows.counter);
				lbPianoSize.setText("Piano: " + PianoSizeArrows.getNumPianoKeys() + " Keys");
			}
		});

		JButton lbRightArrow = new JButton(">");
		lbRightArrow.setOpaque(true);
		lbRightArrow.setForeground(Color.BLACK);
		lbRightArrow.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbRightArrow.setFocusable(false);
		lbRightArrow.setBorderPainted(false);
		lbRightArrow.setBackground(Color.WHITE);
		lbRightArrow.setBounds(114, 487, 72, 41);
		add(lbRightArrow);
		lbRightArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (PianoSizeArrows.counter >= 4) {
					return;
				}
				PianoSizeArrows.counter++;

				PianoSizeArrows.setKeyboardSize(PianoSizeArrows.counter);
				lbPianoSize.setText("Piano: " + PianoSizeArrows.getNumPianoKeys() + " Keys");
			}
		});

		JLabel lbColors = new JLabel("Colors");
		lbColors.setHorizontalAlignment(SwingConstants.CENTER);
		lbColors.setForeground(Color.WHITE);
		lbColors.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColors.setBounds(482, 95, 209, 47);
		add(lbColors);

		JComboBox<String> colorPresets = new JComboBox<>(colorNames.toArray(new String[0]));
		colorPresets.setBounds(475, 150, 240, 25);
		add(colorPresets);
		colorPresets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedColorName = (String) colorPresets.getSelectedItem();
				int index = colorNames.indexOf(selectedColorName);
				if (index >= 0) {
					selectedColor = presetColors[index];
					System.out.println("Preset Color selected: " + selectedColorName);

					// Update text fields with RGB values of selected color
					txtR.setText(String.valueOf(selectedColor.getRed()));
					txtG.setText(String.valueOf(selectedColor.getGreen()));
					txtB.setText(String.valueOf(selectedColor.getBlue()));

					// Update color picker with HSB values of selected color
					float[] hsb = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(),
							selectedColor.getBlue(), null);
					colorPicker.setHue(hsb[0]);
					colorPicker.setSaturation(hsb[1]);
					colorPicker.setBrightness(hsb[2]);
					colorPicker.repaint();
				}
			}
		});

		JLabel lbColor_R = new JLabel("R:");
		lbColor_R.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_R.setForeground(Color.WHITE);
		lbColor_R.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_R.setBounds(339, 482, 92, 46);
		add(lbColor_R);

		JLabel lbColor_G = new JLabel("G:");
		lbColor_G.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_G.setForeground(Color.WHITE);
		lbColor_G.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_G.setBounds(474, 482, 92, 46);
		add(lbColor_G);

		JLabel lbColor_B = new JLabel("B:");
		lbColor_B.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_B.setForeground(Color.WHITE);
		lbColor_B.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_B.setBounds(599, 482, 92, 46);
		add(lbColor_B);

		txtR = new JTextField("255");
		txtR.setColumns(10);
		txtR.setBounds(413, 487, 79, 35);
		add(txtR);
		txtR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int r = Integer.parseInt(txtR.getText());
					int g = Integer.parseInt(txtG.getText());
					int b = Integer.parseInt(txtB.getText());
					Color color = new Color(r, g, b);
					float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
					colorPicker.setHue(hsb[0]);
					colorPicker.setSaturation(hsb[1]);
					colorPicker.setBrightness(hsb[2]);
					colorPicker.repaint();
				} catch (NumberFormatException ex) {
					// handle exception
				}
			}
		});

		txtG = new JTextField("255");
		txtG.setColumns(10);
		txtG.setBounds(540, 487, 79, 35);
		add(txtG);
		txtG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int r = Integer.parseInt(txtR.getText());
					int g = Integer.parseInt(txtG.getText());
					int b = Integer.parseInt(txtB.getText());
					Color color = new Color(r, g, b);
					float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
					colorPicker.setHue(hsb[0]);
					colorPicker.setSaturation(hsb[1]);
					colorPicker.setBrightness(hsb[2]);
					colorPicker.repaint();
				} catch (NumberFormatException ex) {
					// handle exception
				}
			}
		});

		txtB = new JTextField("255");
		txtB.setColumns(10);
		txtB.setBounds(675, 487, 79, 35);
		add(txtB);
		txtB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int r = Integer.parseInt(txtR.getText());
					int g = Integer.parseInt(txtG.getText());
					int b = Integer.parseInt(txtB.getText());
					Color color = new Color(r, g, b);
					float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
					colorPicker.setHue(hsb[0]);
					colorPicker.setSaturation(hsb[1]);
					colorPicker.setBrightness(hsb[2]);
					colorPicker.repaint();
				} catch (NumberFormatException ex) {
					// handle exception
				}
			}
		});

		colorPicker.setBounds(475, 250, 240, 200);
		add(colorPicker);

		pianoKeyboard.setBounds(0, 650, 810, 70);
		pianoKeyboard.setBackground(new Color(21, 25, 29));
		add(pianoKeyboard);
		pianoKeyboard.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// Call the pianoKeyAction method with the mouse coordinates and the pressed
				// flag set to true
				pianoKeyAction(e.getX(), e.getY(), true);
			}

			public void mouseReleased(MouseEvent e) {
				// Call the pianoKeyAction method with the mouse coordinates and the pressed
				// flag set to false
				pianoKeyAction(e.getX(), e.getY(), false);
			}
		});
	}

	void pianoKeyAction(int x, int y, boolean released) {
		for (int i = 0; i < DrawPiano.whiteKeys.length; i++) {
			// Check if the mouse click was inside a white key
			if (x > i * 15 + 15 && x < (i + 1) * 15 + 15 && y >= 0 && y <= 133) {
				if (released) {
					DrawPiano.Keys[DrawPiano.whiteKeys[i]][0] = 1;
				} else {
					DrawPiano.Keys[DrawPiano.whiteKeys[i]][0] = 0;
				}
				pianoKeyboard.repaint();
			}
		}
	}
}
