package com.serifpersia.pianoled.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorPickerPanel extends JPanel {

	private static final int OVAL_SIZE = 35;
	private static final int ARC_THICKNESS = 35;
	private static final int SPACING = 1;
	private static final double PANEL_WIDTH_FACTOR = 1.85;
	private static final double PANEL_HEIGHT_FACTOR = 1.85;
	private static final int INDICATOR_SIZE = 12;
	static Color colorPickerColor = Color.WHITE;
	private int ovalAngle = 0; // Current angle of the oval

	private float hue = 0f;
	private float saturation = 1f;
	private float brightness = 1f;

	private Rectangle2D colorPanel;
	private BufferedImage colorPanelImage;

	public ColorPickerPanel() {
		setBackground(Color.BLACK);
		setLayout(null);
		setDoubleBuffered(true); // Enable double buffering

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int centerX = getWidth() / 2;
				int centerY = getHeight() / 2;

				if (colorPanel.contains(e.getPoint())) {
					updateSaturationAndBrightness(e);
					repaint();
				} else {
					double dx = e.getX() - centerX;
					double dy = e.getY() - centerY;
					double angle = Math.toDegrees(Math.atan2(dy, dx));
					if (angle < 0) {
						angle += 360;
					}
					ovalAngle = (int) angle;

					updateHue();
				}
				updateSelcetedColor();
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		int spacing = SPACING;
		int arcThickness = ARC_THICKNESS;
		int arcSize = Math.min(centerX, centerY) * 2 - arcThickness;
		int arcX = centerX - arcSize / 2;
		int arcY = centerY - arcSize / 2;
		int circleSize = arcSize - 2 * spacing;

		// Create a new BufferedImage for the arcs
		BufferedImage arcsImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2dArcs = arcsImage.createGraphics();

		for (int angle = 0; angle < 360; angle++) {
			float hue = angle / 360.0f;
			g2dArcs.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));
			int startAngle = -angle;
			g2dArcs.setStroke(new BasicStroke(arcThickness));
			g2dArcs.drawArc(arcX + spacing, arcY + spacing, circleSize, circleSize, startAngle, 1);
		}

		g2dArcs.dispose();

		// Draw the arcs onto the main BufferedImage
		g2d.drawImage(arcsImage, 0, 0, null);

		// Draw the hue indicator
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(0));
		int ovalRadius = (int) (circleSize / 2 - spacing - OVAL_SIZE / 2 * -0.05f);
		int ovalX = centerX + (int) (ovalRadius * Math.cos(Math.toRadians(ovalAngle)));
		int ovalY = centerY + (int) (ovalRadius * Math.sin(Math.toRadians(ovalAngle)));

		g2d.drawOval(ovalX - OVAL_SIZE / 2, ovalY - OVAL_SIZE / 2, OVAL_SIZE, OVAL_SIZE);

		// Draw the color panel
		int panelWidth = (int) (circleSize / PANEL_WIDTH_FACTOR);
		int panelHeight = (int) (circleSize / PANEL_HEIGHT_FACTOR);
		int panelX = centerX - panelWidth / 2;
		int panelY = centerY - panelHeight / 2;

		colorPanel = new Rectangle2D.Float(panelX, panelY, panelWidth, panelHeight);

		if (colorPanelImage == null || colorPanelImage.getWidth() != panelWidth
				|| colorPanelImage.getHeight() != panelHeight) {
			colorPanelImage = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
		}

		for (int x = 0; x < colorPanelImage.getWidth(); x++) {
			for (int y = 0; y < colorPanelImage.getHeight(); y++) {
				float saturation = (float) x / (float) colorPanelImage.getWidth();
				float brightness = 1.0f - ((float) y / (float) colorPanelImage.getHeight());
				Color color = Color.getHSBColor(hue, saturation, brightness);
				colorPanelImage.setRGB(x, y, color.getRGB());
			}
		}
		g2d.drawImage(colorPanelImage, (int) colorPanel.getX(), (int) colorPanel.getY(), null);

		// Draw the color panel indicator
		g2d.setColor(Color.WHITE);
		int cx = (int) colorPanel.getX() + (int) (saturation * panelWidth);
		int cy = (int) colorPanel.getY() + (int) ((1 - brightness) * panelHeight);
		g2d.drawOval(cx - INDICATOR_SIZE, cy - INDICATOR_SIZE, INDICATOR_SIZE * 2, INDICATOR_SIZE * 2);

		g2d.dispose();
	}

	private void updateHue() {
		hue = (float) ovalAngle / 360.0f;
		// System.out.println("Hue: " + hue);
	}

	private void updateSaturationAndBrightness(MouseEvent e) {
		double x = e.getX() - colorPanel.getX();
		double y = e.getY() - colorPanel.getY();
		saturation = (float) (x / colorPanel.getWidth());
		brightness = 1.0f - (float) (y / colorPanel.getHeight());
	}

	private void updateSelcetedColor() {
		colorPickerColor = Color.getHSBColor(hue, saturation, brightness);
		GetUI.selectedColor = colorPickerColor;
		ControlsPanel.txt_R.setText(Integer.toString(colorPickerColor.getRed()));
		ControlsPanel.txt_G.setText(Integer.toString(colorPickerColor.getGreen()));
		ControlsPanel.txt_B.setText(Integer.toString(colorPickerColor.getBlue()));
	}

	public void setCustomColor(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
		GetUI.selectedColor = Color.getHSBColor(hue, saturation, brightness);
	}
}
