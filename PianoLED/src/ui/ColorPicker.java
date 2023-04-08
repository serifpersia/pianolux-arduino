package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import java.awt.RenderingHints;

@SuppressWarnings("serial")
public class ColorPicker extends JPanel {

	private Rectangle2D huePanel;
	private Rectangle2D colorPanel;
	// private Rectangle2D newPanel;

	public static Color colorPickerColor = Color.WHITE;

	private static final int PANEL_SIZE = 200;
	private static final int HUE_PANEL_WIDTH = 20;

	public float hue;
	public float saturation;
	public float brightness;

	public ColorPicker() {
		setBackground(new Color(21, 25, 28));
		setLayout(null);

		huePanel = new Rectangle2D.Float(PANEL_SIZE + 20, 0, HUE_PANEL_WIDTH, PANEL_SIZE);
		colorPanel = new Rectangle2D.Float(0, 0, PANEL_SIZE, PANEL_SIZE);
		// newPanel = new Rectangle2D.Float(0, PANEL_SIZE + 10, PANEL_SIZE, PANEL_SIZE /
		// 10);

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
				colorPickerColor = Color.getHSBColor(hue, saturation, brightness);
				ControlsPanel.selectedColor = ColorPicker.colorPickerColor;
				// System.out.println(colorPickerColor);
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
