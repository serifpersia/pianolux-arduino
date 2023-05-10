package com.serifpersia.pianoled.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorPickerPanel extends JPanel {

	private static final Color BACKGROUND_COLOR = new Color(21, 25, 28);
	private static final int HUE_PANEL_WIDTH = 35;
	private static final int COLOR_PANEL_SPACING = 15; // set the size of the gap

	static Color colorPickerColor = Color.WHITE;

	private float hue = 0f;
	private float saturation = 1f;
	private float brightness = 1f;

	private Rectangle2D colorPanel;
	private Rectangle2D huePanel;
	private BufferedImage colorPanelImage;

	public ColorPickerPanel() {
		setBackground(BACKGROUND_COLOR);
		setLayout(null);

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (huePanel.contains(e.getPoint())) {
					updateHue(e);
				} else if (colorPanel.contains(e.getPoint())) {
					updateSaturationAndBrightness(e);
				}
				updateSelcetedColor();
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();

		Graphics2D g2d = (Graphics2D) g;

		// draw hue panel
		huePanel = new Rectangle2D.Float(width - HUE_PANEL_WIDTH, 0, HUE_PANEL_WIDTH, height);
		for (int i = 0; i < height; i++) {
			float hue = i / (float) height; // increment hue from 0 to 1
			g2d.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));
			g2d.fillRect((int) huePanel.getX(), i, (int) huePanel.getWidth(), 1);
		}

		// draw hue indicator
		g2d.setColor(Color.WHITE);
		int hy = (int) (hue * huePanel.getHeight());
		int hx = (int) huePanel.getX();
		g2d.fillRect(hx, hy, HUE_PANEL_WIDTH, 5);

		// Draw the color panel
		colorPanel = new Rectangle2D.Float(0, 0, width - HUE_PANEL_WIDTH - COLOR_PANEL_SPACING, height);
		colorPanelImage = new BufferedImage((int) colorPanel.getWidth(), (int) colorPanel.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < colorPanelImage.getWidth(); x++) {
			for (int y = 0; y < colorPanelImage.getHeight(); y++) {
				float saturation = (float) x / (float) colorPanelImage.getWidth();
				float brightness = 1.0f - ((float) y / (float) colorPanelImage.getHeight());
				Color color = Color.getHSBColor(hue, saturation, brightness);
				colorPanelImage.setRGB(x, y, color.getRGB());
			}
		}
		g2d.drawImage(colorPanelImage, (int) colorPanel.getX(), (int) colorPanel.getY(), null);

		// draw color panel indicator
		g2d.setColor(Color.WHITE);
		// add lines here
		int cx = (int) (saturation * colorPanel.getWidth());
		int cy = (int) ((1 - brightness) * colorPanel.getHeight());
		g2d.drawOval(cx - 10, cy - 10, 20, 20);

		g2d.dispose();

	}

	private void updateHue(MouseEvent e) {
		hue = e.getY() / (float) huePanel.getHeight();
	}

	private void updateSaturationAndBrightness(MouseEvent e) {
		saturation = (float) (e.getX() / (float) colorPanel.getWidth());
		brightness = 1 - (float) (e.getY() / (float) colorPanel.getHeight());
	}

	public void setCustomColor(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
		ControlsPanel.selectedColor = Color.getHSBColor(hue, saturation, brightness);
	}

	private void updateSelcetedColor() {
		colorPickerColor = Color.getHSBColor(hue, saturation, brightness);
		ControlsPanel.selectedColor = colorPickerColor;
		ControlsPanel.txt_R.setText(Integer.toString(colorPickerColor.getRed()));
		ControlsPanel.txt_G.setText(Integer.toString(colorPickerColor.getGreen()));
		ControlsPanel.txt_B.setText(Integer.toString(colorPickerColor.getBlue()));
	}
}
