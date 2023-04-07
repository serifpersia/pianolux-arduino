package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.PianoLED;

import javax.swing.JTextField;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	public static Color selectedColor = Color.WHITE;

	private static final int PANEL_SIZE = 200;
	private static final int HUE_PANEL_WIDTH = 20;

	private float hue;
	private float saturation;
	private float brightness;

	private Rectangle2D huePanel;
	private Rectangle2D colorPanel;
	private Rectangle2D newPanel;

	public ControlsPanel() {
		setBackground(new Color(21, 25, 28));
		setLayout(null);

		huePanel = new Rectangle2D.Float(PANEL_SIZE + 20, 0, HUE_PANEL_WIDTH, PANEL_SIZE);
		colorPanel = new Rectangle2D.Float(0, 0, PANEL_SIZE, PANEL_SIZE);
		newPanel = new Rectangle2D.Float(0, PANEL_SIZE + 10, PANEL_SIZE, PANEL_SIZE / 10);

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

		// ColorChooser Panel
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (huePanel.contains(e.getPoint())) {
				} else if (colorPanel.contains(e.getPoint())) {
					updateSaturationAndBrightness(e);
				}
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (huePanel.contains(e.getPoint())) {
					updateHue(e);
				} else if (colorPanel.contains(e.getPoint())) {
					updateSaturationAndBrightness(e);
				}
				repaint();
			}
		});
	}

	private void updateHue(MouseEvent e) {
		hue = (float) (e.getY() / (float) PANEL_SIZE);
	}

	private void updateSaturationAndBrightness(MouseEvent e) {
		saturation = (float) (e.getX() / (float) PANEL_SIZE);
		brightness = 1 - (float) (e.getY() / (float) PANEL_SIZE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();

		// draw hue panel
		for (int y = 0; y < PANEL_SIZE; y++) {
			float h = y / (float) PANEL_SIZE;
			g2d.setColor(Color.getHSBColor(hue, h, 1f));
			g2d.fillRect((int) huePanel.getX(), y, HUE_PANEL_WIDTH, 1);
		}
		// draw hue indicator
		g2d.setColor(Color.BLACK);
		int hy = (int) (hue * PANEL_SIZE);
		int hx = (int) huePanel.getMaxX() - 20;
		g2d.fillRect(hx, hy - 1, HUE_PANEL_WIDTH, 2);

		// draw color panel
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int x = 0; x < PANEL_SIZE; x++) {
			for (int y = 0; y < PANEL_SIZE; y++) {
				float s = x / (float) PANEL_SIZE;
				float b = 1f - y / (float) PANEL_SIZE;

				g2d.setColor(Color.getHSBColor(hue, s, b));
				g2d.fillRect(x, y, 1, 1);
			}
		}
		// draw color indicator
		g2d.setColor(Color.WHITE);
		int cx = (int) (saturation * PANEL_SIZE);
		int cy = (int) ((1 - brightness) * PANEL_SIZE);
		g2d.drawOval(cx - 5, cy - 5, 10, 10);

		g2d.dispose();

	}
}
