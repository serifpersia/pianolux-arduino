package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.PianoLED;

import javax.swing.JTextField;
import java.awt.Image;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	public static Color selectedColor = Color.WHITE;

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

		JLabel lbPianoSize = new JLabel("Piano Keys");
		lbPianoSize.setHorizontalAlignment(SwingConstants.CENTER);
		lbPianoSize.setForeground(Color.WHITE);
		lbPianoSize.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbPianoSize.setBounds(1, 429, 209, 47);
		add(lbPianoSize);

		JButton lbLeftArrow = new JButton("<");
		lbLeftArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		lbLeftArrow.setOpaque(true);
		lbLeftArrow.setForeground(Color.BLACK);
		lbLeftArrow.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbLeftArrow.setFocusable(false);
		lbLeftArrow.setBorderPainted(false);
		lbLeftArrow.setBackground(Color.WHITE);
		lbLeftArrow.setBounds(27, 487, 72, 41);
		add(lbLeftArrow);

		JButton lbRightArrow = new JButton(">");
		lbRightArrow.setOpaque(true);
		lbRightArrow.setForeground(Color.BLACK);
		lbRightArrow.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbRightArrow.setFocusable(false);
		lbRightArrow.setBorderPainted(false);
		lbRightArrow.setBackground(Color.WHITE);
		lbRightArrow.setBounds(114, 487, 72, 41);
		add(lbRightArrow);

		JLabel lbColors = new JLabel("Colors");
		lbColors.setHorizontalAlignment(SwingConstants.CENTER);
		lbColors.setForeground(Color.WHITE);
		lbColors.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColors.setBounds(482, 95, 209, 47);
		add(lbColors);

		JComboBox<Object> ColorPresets = new JComboBox<Object>();
		ColorPresets.setBounds(474, 153, 219, 25);
		add(ColorPresets);

		JButton lbColorPicker = new JButton("");// Load the image and scale it to fit the button
		ImageIcon ColorWheel = new ImageIcon(PianoLED.class.getResource("/icons/Color.png"));
		Image wheelImg = ColorWheel.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
		lbColorPicker.setIcon(new ImageIcon(wheelImg));

		lbColorPicker.setOpaque(false);
		lbColorPicker.setForeground(Color.BLACK);
		lbColorPicker.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbColorPicker.setFocusable(false);
		lbColorPicker.setBorderPainted(false);
		lbColorPicker.setBackground(Color.WHITE);
		lbColorPicker.setBounds(385, 189, 386, 263);
		add(lbColorPicker);

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

		JTextField txtR = new JTextField();
		txtR.setBounds(413, 487, 79, 35);
		add(txtR);
		txtR.setColumns(10);

		JTextField txtG = new JTextField();
		txtG.setColumns(10);
		txtG.setBounds(540, 487, 79, 35);
		add(txtG);

		JButton lbSet = new JButton("Set");
		lbSet.setOpaque(true);
		lbSet.setForeground(Color.BLACK);
		lbSet.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbSet.setFocusable(false);
		lbSet.setBorderPainted(false);
		lbSet.setBackground(Color.WHITE);
		lbSet.setBounds(500, 600, 100, 50);
		add(lbSet);
		lbSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = Integer.parseInt(txtR.getText());
				int g = Integer.parseInt(txtG.getText());
				int b = 0; // You can set a default value for blue, or add a text field for it

				selectedColor = new Color(r, g, b);

				System.out.println(selectedColor);
			}
		});

		JTextField txtB = new JTextField();
		txtB.setColumns(10);
		txtB.setBounds(675, 487, 79, 35);
		add(txtB);
		lbColorPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedColor = JColorChooser.showDialog(ControlsPanel.this, "Choose a color", Color.WHITE);

				System.out.println(selectedColor);

			}
		});
	}
}
