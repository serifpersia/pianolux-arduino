package com.serifpersia.pianoled.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class pnl_HueOnly extends JPanel {

	public static JLabel lb_Version;

	public static JComboBox<?> cbSerialDevices;
	public static JComboBox<?> cbMidiDevices;
	public static JComboBox<?> cbBranch;

	private float hue = 0f;
	private float saturation = 1f;
	private float brightness = 1f;

	private BufferedImage huePanel;

	private int hueRectWidth; // Width of the hue panel rectangle
	private int hueRectHeight; // Height of the hue panel rectangle

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
		if (x >= HuerectX && x < HuerectX + hueRectWidth && y >= HuerectY && y < HuerectY + hueRectHeight) {
			float normalizedHue = (float) (x - HuerectX) / (float) hueRectWidth;
			hue = normalizedHue;

		}
		updateSelcetedColor();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawHuePanel(g);
	}

	private void drawHuePanel(Graphics g) {
		int width = getWidth();
		getHeight();

		int padding = 25; // Set the desired padding
		int bottomPadding = 10; // Set the desired padding

		// Calculate the dimensions with spacing
		int rectWidth = width - 2 * padding;
		int rectHeight = 25;

		// Check if huePanel needs to be created or resized
		if (huePanel == null || huePanel.getWidth() != rectWidth || huePanel.getHeight() != rectHeight) {
			// Create a new BufferedImage for the hue panel
			huePanel = new BufferedImage(rectWidth, rectHeight, BufferedImage.TYPE_INT_RGB);
		}

		Graphics2D hueGraphics = huePanel.createGraphics();

		// Draw the current hue value on the hue panel
		Color currentHueColor = Color.getHSBColor(hue, 1.0f, 1.0f);
		hueGraphics.setColor(currentHueColor);
		hueGraphics.fillRect(0, 0, rectWidth, rectHeight);

		hueGraphics.dispose();

		HuerectX = padding;
		HuerectY = 0 + bottomPadding;

		// Draw the BufferedImage onto the JPanel
		g.drawImage(huePanel, HuerectX, HuerectY, null);

		// Calculate the dimensions of the hue panel rectangle
		hueRectWidth = rectWidth;
		hueRectHeight = rectHeight;

		// Draw the white border circle
		int ovalWidth = 36;
		int ovalHeight = rectHeight + 12;
		int ovalX = HuerectX - ovalWidth / 2;
		int ovalY = HuerectY - 5;
		int hueOvalX = (int) (ovalX + (hue * rectWidth));

		int borderThickness = 5;

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the filled hue oval
		g2d.setColor(Color.getHSBColor(hue, saturation, brightness));
		g2d.fillOval(hueOvalX, ovalY, ovalWidth, ovalHeight);

		// Draw the white border circle
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(borderThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.drawOval(hueOvalX, ovalY, ovalWidth, ovalHeight);

		g2d.dispose();
	}

	private void updateSelcetedColor() {
		Color colorPickerColor = Color.getHSBColor(hue, saturation, brightness);
		GetUI.selectedColor = colorPickerColor;
		pnl_Colors.txt_R.setText(Integer.toString(colorPickerColor.getRed()));
		pnl_Colors.txt_G.setText(Integer.toString(colorPickerColor.getGreen()));
		pnl_Colors.txt_B.setText(Integer.toString(colorPickerColor.getBlue()));
	}

	public void setCustomColor(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
		GetUI.selectedColor = Color.getHSBColor(hue, saturation, brightness);
	}
}