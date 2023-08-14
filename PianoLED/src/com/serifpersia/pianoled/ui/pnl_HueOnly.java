package com.serifpersia.pianoled.ui;

import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class pnl_HueOnly extends JPanel {

	private float hue = 0f;
	private float saturation = 1f;
	private float brightness = 1f;

	private BufferedImage huePanel;

	private int hueRectWidth;
	private int hueRectHeight;
	private int HuerectX;
	private int HuerectY;

	public pnl_HueOnly() {
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

		if (x >= HuerectX && x < HuerectX + hueRectWidth && y >= HuerectY && y < HuerectY + hueRectHeight) {
			float normalizedHue = (float) (x - HuerectX) / (float) hueRectWidth;
			if (hue != normalizedHue) {
				hue = normalizedHue;
				hueUpdated = true;
			}
		}

		if (hueUpdated) {
			updateSelectedColor();
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawHuePanel(g);
	}

	private void drawHuePanel(Graphics g) {
		int width = getWidth();

		int padding = 25;
		int bottomPadding = 10;

		int rectWidth = width - 2 * padding;
		int rectHeight = 25;

		if (huePanel == null || huePanel.getWidth() != rectWidth || huePanel.getHeight() != rectHeight) {
			huePanel = new BufferedImage(rectWidth, rectHeight, BufferedImage.TYPE_INT_RGB);
		}

		Graphics2D hueGraphics = huePanel.createGraphics();

		Color currentHueColor = Color.getHSBColor(hue, 1.0f, 1.0f);
		hueGraphics.setColor(currentHueColor);
		hueGraphics.fillRect(0, 0, rectWidth, rectHeight);

		hueGraphics.dispose();

		HuerectX = padding;
		HuerectY = 0 + bottomPadding;

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
	}
}
