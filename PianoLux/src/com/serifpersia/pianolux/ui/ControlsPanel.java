package com.serifpersia.pianolux.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

import com.serifpersia.pianolux.PianoLux;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	private JPanel pnl_Labels;
	private JLabel lb_Left;
	private JLabel lb_Controls;
	private JLabel lb_Right;
	private CardLayout cl_LeftPanel = new CardLayout();
	private JPanel LeftPanel;
	private pnl_Controls pnl_Controls;
	private JPanel rightInner_pnl = new pnl_Colors();
	private pnl_Guide pnl_Guide;
	private pnl_AudioReact pnl_AudioReact;
	static JPanel pnl_GradientPreview;
	private pnl_Gradient_MultiColor GradientPanel;
	private JLabel lb_AudioReact;

	public ControlsPanel(PianoLux pianoLux) {

		GradientPanel = new pnl_Gradient_MultiColor(pianoLux);
		pnl_Controls = new pnl_Controls(pianoLux);
		pnl_Guide = new pnl_Guide(pianoLux);
		pnl_AudioReact = new pnl_AudioReact(pianoLux);

		init();
		initTopLabels();
		topLabelCardAction();
	}

	private void init() {

		setLayout(new BorderLayout(0, 0));

		JPanel pnl_ParentSplit = new JPanel();
		add(pnl_ParentSplit, BorderLayout.CENTER);
		pnl_ParentSplit.setLayout(new GridLayout(0, 2, 0, 0));

		LeftPanel = new JPanel();
		LeftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		LeftPanel.setLayout(cl_LeftPanel);

		pnl_ParentSplit.add(LeftPanel);

		JPanel RightPanel = new JPanel();

		RightPanel.setLayout(new BorderLayout(0, 0));
		RightPanel.setBorder(new EmptyBorder(10, 0, 10, 10));

		RightPanel.add(rightInner_pnl, BorderLayout.CENTER);

		pnl_ParentSplit.add(RightPanel);

		pnl_Labels = new JPanel();

		add(pnl_Labels, BorderLayout.NORTH);

		pnl_GradientPreview = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				int width = getWidth(); // Use the full width of the panel
				int height = getHeight(); // Take the full height of the panel

				int colorCount = Math.min(GradientPanel.divisionCount, pnl_Gradient_MultiColor.colors.length); // Determine
																												// the
				// number of colors
				// to use

				for (int i = 0; i < colorCount; i++) { // Iterate over the colors
					int x1 = i * width / GradientPanel.divisionCount; // Calculate the starting x-coordinate of the
																		// current part
					int x2 = (i + 1) * width / GradientPanel.divisionCount; // Calculate the ending x-coordinate of the
																			// current part

					Color color = pnl_Gradient_MultiColor.colors[i % pnl_Gradient_MultiColor.colors.length]; // Get the
																												// color
																												// of
																												// the
																												// current
					// part

					g.setColor(color);
					g.fillRect(x1, 0, x2 - x1, height);

					if (i < colorCount - 1) { // Apply gradient between divisions
						Color colorLeft = pnl_Gradient_MultiColor.colors[i];
						Color colorRight = pnl_Gradient_MultiColor.colors[i + 1];

						for (int x = x1; x < x2; x++) { // Iterate over the pixels within the division
							float ratio = (float) (x - x1) / (float) (x2 - x1); // Calculate the ratio of the current
																				// pixel within the division
							Color interpolatedColor = interpolateColor(colorLeft, colorRight, ratio); // Interpolate
																										// the
							// color

							g.setColor(interpolatedColor);
							g.drawLine(x, 0, x, height); // Draw a vertical line with the interpolated color
						}
					}
				}

			}
		};
		add(pnl_GradientPreview, BorderLayout.SOUTH);

	}

	private Color interpolateColor(Color color1, Color color2, float ratio) {
		float[] hsbColor1 = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
		float[] hsbColor2 = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);

		float hue = hsbColor1[0] * (1 - ratio) + hsbColor2[0] * ratio;
		float saturation = hsbColor1[1] * (1 - ratio) + hsbColor2[1] * ratio;
		float brightness = hsbColor1[2] * (1 - ratio) + hsbColor2[2] * ratio;

		return Color.getHSBColor(hue, saturation, brightness);
	}

	private void initTopLabels() {

		pnl_Labels.setLayout(new FlowLayout(FlowLayout.CENTER, 48, 5));
		pnl_Labels.setBackground(new Color(50, 50, 50));

		lb_Controls = new JLabel("Controls");
		lb_Controls.setForeground(new Color(204, 204, 204));
		lb_Controls.setFont(new Font("Poppins", Font.PLAIN, 21));

		lb_Left = new JLabel("Gradient/MultiColor Controls");
		lb_Left.setForeground(new Color(128, 128, 128));
		lb_Left.setFont(new Font("Poppins", Font.PLAIN, 21));

		lb_Right = new JLabel("Guide Controls");
		lb_Right.setForeground(new Color(128, 128, 128));
		lb_Right.setFont(new Font("Poppins", Font.PLAIN, 21));

		lb_AudioReact = new JLabel("Visualizer");
		lb_AudioReact.setForeground(Color.GRAY);
		lb_AudioReact.setFont(new Font("Poppins", Font.PLAIN, 21));

		pnl_Labels.add(lb_Controls);
		pnl_Labels.add(lb_Left);
		pnl_Labels.add(lb_Right);
		pnl_Labels.add(lb_AudioReact);

		// Add panels to pnl_Left with corresponding names
		LeftPanel.add(pnl_Controls, "Controls");
		LeftPanel.add(GradientPanel, "Gradient");
		LeftPanel.add(pnl_Guide, "GuideControls");
		LeftPanel.add(pnl_AudioReact, "AudioReactControls");

	}

	private void topLabelCardAction() {
		// Function to handle all label clicks
		MouseAdapter labelClickListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JLabel clickedLabel = (JLabel) e.getSource();

				// Reset label colors to default
				lb_Left.setForeground(new Color(128, 128, 128));
				lb_Controls.setForeground(new Color(128, 128, 128));
				lb_Right.setForeground(new Color(128, 128, 128));
				lb_AudioReact.setForeground(new Color(128, 128, 128));

				// Set clicked label color to highlighted
				clickedLabel.setForeground(new Color(204, 204, 204));

				// Switch panels based on the clicked label
				if (clickedLabel == lb_Left) {
					cl_LeftPanel.show(LeftPanel, "Gradient");
				} else if (clickedLabel == lb_Controls) {
					cl_LeftPanel.show(LeftPanel, "Controls");
				} else if (clickedLabel == lb_Right) {
					cl_LeftPanel.show(LeftPanel, "GuideControls");
				} else if (clickedLabel == lb_AudioReact) {
					cl_LeftPanel.show(LeftPanel, "AudioReactControls");
				}
			}
		};

		// Add click listeners to labels
		lb_Left.addMouseListener(labelClickListener);
		lb_Controls.addMouseListener(labelClickListener);
		lb_Right.addMouseListener(labelClickListener);
		lb_AudioReact.addMouseListener(labelClickListener);
	}
}
