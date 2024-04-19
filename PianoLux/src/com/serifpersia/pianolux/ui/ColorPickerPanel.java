package com.serifpersia.pianolux.ui;

import javax.swing.JPanel;

import com.serifpersia.pianolux.PianoController;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class ColorPickerPanel extends JPanel {

	private float hue = 0f;
	private float saturation = 1f;
	private float brightness = 1f;

	private BufferedImage colorPanel;
	private BufferedImage huePanel;

	private int hueRectWidth;
	private int hueRectHeight;
	private int HuerectX;
	private int HuerectY;
	private int ColorrectX;
	private int ColorrectY;

	public ColorPickerPanel() {
		setBackground(new Color(50, 50, 50));

		MouseAdapter mouseAdapter = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				handleClick(e.getX(), e.getY());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				handleDrag(e.getX(), e.getY());
			}
		};

		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
	}

	private void handleClick(int x, int y) {
		handleDrag(x, y);
	}

	private void handleDrag(int x, int y) {
		boolean hueUpdated = false;
		boolean colorUpdated = false;

		if (x >= HuerectX && x < HuerectX + hueRectWidth && y >= HuerectY && y < HuerectY + hueRectHeight) {
			float normalizedHue = (float) (x - HuerectX) / (float) hueRectWidth;
			if (hue != normalizedHue) {
				hue = normalizedHue;
				hueUpdated = true;
			}

		} else if (x >= ColorrectX && x < ColorrectX + colorPanel.getWidth() && y >= ColorrectY
				&& y < ColorrectY + colorPanel.getHeight()) {
			int colorX = x - ColorrectX;
			int colorY = y - ColorrectY;

			float normalizedSaturation = (float) colorX / (float) colorPanel.getWidth();
			float normalizedBrightness = 1.0f - ((float) colorY / (float) colorPanel.getHeight());

			if (saturation != normalizedSaturation || brightness != normalizedBrightness) {
				saturation = normalizedSaturation;
				brightness = normalizedBrightness;
				colorUpdated = true;
			}
		}

		if (hueUpdated || colorUpdated) {
			updateSelectedColor();
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawColorPanel(g);
		drawHuePanel(g);
	}

	private void drawColorPanel(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		int padding = 25;
		int bottomPadding = 50;

		int rectWidth = width - 2 * padding;
		int rectHeight = height - 2 * padding;

		ColorrectX = padding;
		ColorrectY = padding;

		if (colorPanel == null || colorPanel.getWidth() != rectWidth
				|| colorPanel.getHeight() != rectHeight - bottomPadding) {
			colorPanel = new BufferedImage(rectWidth, rectHeight - bottomPadding, BufferedImage.TYPE_INT_ARGB);
		}

		for (int x = 0; x < rectWidth; x++) {
			for (int y = 0; y < rectHeight - bottomPadding; y++) {
				float saturation = (float) x / (float) rectWidth;
				float brightness = 1.0f - ((float) y / (float) (rectHeight - bottomPadding));
				Color color = Color.getHSBColor(hue, saturation, brightness);
				colorPanel.setRGB(x, y, color.getRGB());
			}
		}

		g.drawImage(colorPanel, ColorrectX, ColorrectY, null);

		int ovalWidth = 30;
		int ovalHeight = 30;
		int ovalX = ColorrectX + (int) (saturation * rectWidth) - ovalWidth / 2;
		int ovalY = ColorrectY + (int) ((1.0f - brightness) * (rectHeight - bottomPadding)) - ovalHeight / 2;

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawOval(ovalX, ovalY, ovalWidth, ovalHeight);
		g2d.dispose();
	}

	private void drawHuePanel(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		int padding = 25;
		int bottomPadding = 50;

		int rectWidth = width - 2 * padding;
		int rectHeight = 25;

		if (huePanel == null || huePanel.getWidth() != rectWidth || huePanel.getHeight() != rectHeight) {
			huePanel = new BufferedImage(rectWidth, rectHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D hueGraphics = huePanel.createGraphics();

			for (int x = 0; x < rectWidth; x++) {
				float hueValue = (float) x / (float) rectWidth;
				Color hueColor = Color.getHSBColor(hueValue, 1.0f, 1.0f);
				hueGraphics.setColor(hueColor);
				hueGraphics.fillRect(x, 0, 1, rectHeight);
			}

			hueGraphics.dispose();
		}

		HuerectX = padding;
		HuerectY = height - bottomPadding;

		g.drawImage(huePanel, HuerectX, HuerectY, null);

		hueRectWidth = rectWidth;
		hueRectHeight = rectHeight;

		int ovalWidth = 36;
		int ovalHeight = rectHeight + 12;
		int ovalX = HuerectX - ovalWidth / 2;
		int ovalY = HuerectY - 5;
		int hueOvalX = (int) (ovalX + (hue * rectWidth));

		int borderThickness = 5;

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.getHSBColor(hue, saturation, brightness));
		g2d.fillOval(hueOvalX, ovalY, ovalWidth, ovalHeight);

		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(borderThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.drawOval(hueOvalX, ovalY, ovalWidth, ovalHeight);

		g2d.dispose();
	}

	private void updateSelectedColor() {
		Color colorPickerColor = Color.getHSBColor(hue, saturation, brightness);
		GetUI.selectedColor = colorPickerColor;
		pnl_Colors.txt_R.setText(Integer.toString(colorPickerColor.getRed()));
		pnl_Colors.txt_G.setText(Integer.toString(colorPickerColor.getGreen()));
		pnl_Colors.txt_B.setText(Integer.toString(colorPickerColor.getBlue()));

		PianoController.sendUpdatedColorToArduino(GetUI.selectedColor);
	}

	public void setCustomColor(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
		updateSelectedColor();
		repaint();
	}
}
