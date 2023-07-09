package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.PianoLED;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	private pnl_Gradient GradientPanel;
	private pnl_Controls pnl_Controls;
	private pnl_Guide pnl_Guide;

	private pnl_Colors pnl_Right;
	static JPanel pnl_GradientPreview;

	public ControlsPanel(PianoLED pianoLED) {
		pnl_Controls = new pnl_Controls(pianoLED);
		GradientPanel = new pnl_Gradient(pianoLED);
		pnl_Guide = new pnl_Guide(pianoLED);
		pnl_Right = new pnl_Colors();

		init();
	}

	private void init() {
		setBackground(new Color(26, 26, 26));
		setLayout(new BorderLayout(0, 0));

		JLabel lb_PanelControls = new JLabel("Controls");
		lb_PanelControls.setHorizontalAlignment(SwingConstants.CENTER);
		lb_PanelControls.setForeground(new Color(204, 204, 204));
		lb_PanelControls.setFont(new Font("Poppins", Font.PLAIN, 35));
		add(lb_PanelControls, BorderLayout.NORTH);

		JPanel pnl_Main = new JPanel();
		pnl_Main.setBackground(new Color(26, 26, 26));
		add(pnl_Main, BorderLayout.CENTER);

		JPanel pnl_ctrlLabels = new JPanel();
		pnl_ctrlLabels.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		pnl_ctrlLabels.setBackground(new Color(51, 51, 51));

		JPanel pnl_Left = new JPanel();
		pnl_Left.setBackground(new Color(51, 51, 51));

		GroupLayout gl_pnl_Main = new GroupLayout(pnl_Main);
		gl_pnl_Main.setHorizontalGroup(gl_pnl_Main.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_Main.createSequentialGroup().addContainerGap()
						.addGroup(gl_pnl_Main.createParallelGroup(Alignment.LEADING)
								.addComponent(pnl_ctrlLabels, GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE)
								.addGroup(gl_pnl_Main.createSequentialGroup()
										.addComponent(pnl_Left, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(pnl_Right, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)))
						.addContainerGap()));
		gl_pnl_Main.setVerticalGroup(gl_pnl_Main.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_Main.createSequentialGroup()
						.addComponent(pnl_ctrlLabels, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_pnl_Main.createParallelGroup(Alignment.TRAILING)
								.addComponent(pnl_Left, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
								.addComponent(pnl_Right, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
						.addContainerGap()));

		JLabel lb_Controls = new JLabel("Controls");
		lb_Controls.setForeground(new Color(204, 204, 204));
		lb_Controls.setFont(new Font("Poppins", Font.PLAIN, 21));

		JLabel lb_Left = new JLabel("Gradient Controls");
		lb_Left.setForeground(new Color(128, 128, 128));
		lb_Left.setFont(new Font("Poppins", Font.PLAIN, 21));

		JLabel lb_Right = new JLabel("Guide Controls");
		lb_Right.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 35));
		lb_Right.setForeground(new Color(128, 128, 128));
		lb_Right.setFont(new Font("Poppins", Font.PLAIN, 21));

		pnl_ctrlLabels.add(lb_Left);
		pnl_ctrlLabels.add(lb_Controls);
		pnl_ctrlLabels.add(lb_Right);

		pnl_Main.setLayout(gl_pnl_Main);

		// Create CardLayout for pnl_Left
		CardLayout cardLayout = new CardLayout();
		pnl_Left.setLayout(cardLayout);

		// Add panels to pnl_Left with corresponding names
		pnl_Left.add(pnl_Controls, "Controls");
		pnl_Left.add(GradientPanel, "Gradient");
		pnl_Left.add(pnl_Guide, "GuideControls");

		pnl_GradientPreview = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				int width = getWidth(); // Use the full width of the panel
				int height = getHeight(); // Take the full height of the panel

				int colorCount = Math.min(GradientPanel.divisionCount, pnl_Gradient.colors.length); // Determine the
																									// number of colors
																									// to use

				for (int i = 0; i < colorCount; i++) { // Iterate over the colors
					int x1 = i * width / GradientPanel.divisionCount; // Calculate the starting x-coordinate of the
																		// current part
					int x2 = (i + 1) * width / GradientPanel.divisionCount; // Calculate the ending x-coordinate of the
																			// current part

					Color color = pnl_Gradient.colors[i % pnl_Gradient.colors.length]; // Get the color of the current
																						// part

					g.setColor(color);
					g.fillRect(x1, 0, x2 - x1, height);

					if (i < colorCount - 1) { // Apply gradient between divisions
						Color colorLeft = pnl_Gradient.colors[i];
						Color colorRight = pnl_Gradient.colors[i + 1];

						for (int x = x1; x < x2; x++) { // Iterate over the pixels within the division
							float ratio = (float) (x - x1) / (float) (x2 - x1); // Calculate the ratio of the current
																				// pixel within the division
							Color interpolatedColor = GradientPanel.interpolateColor(colorLeft, colorRight, ratio); // Interpolate
																													// the
							// color

							g.setColor(interpolatedColor);
							g.drawLine(x, 0, x, height); // Draw a vertical line with the interpolated color
						}
					}
				}

			}
		};
		pnl_GradientPreview.setBackground(new Color(0, 0, 0));
		add(pnl_GradientPreview, BorderLayout.SOUTH);

		// Function to handle all label clicks
		MouseAdapter labelClickListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JLabel clickedLabel = (JLabel) e.getSource();

				// Reset label colors to default
				lb_Left.setForeground(new Color(128, 128, 128));
				lb_Controls.setForeground(new Color(128, 128, 128));
				lb_Right.setForeground(new Color(128, 128, 128));

				// Set clicked label color to highlighted
				clickedLabel.setForeground(new Color(204, 204, 204));

				// Switch panels based on the clicked label
				if (clickedLabel == lb_Left) {
					cardLayout.show(pnl_Left, "Gradient");
				} else if (clickedLabel == lb_Controls) {
					cardLayout.show(pnl_Left, "Controls");
				} else if (clickedLabel == lb_Right) {
					cardLayout.show(pnl_Left, "GuideControls");
				}
			}
		};

		// Add click listeners to labels
		lb_Left.addMouseListener(labelClickListener);
		lb_Controls.addMouseListener(labelClickListener);
		lb_Right.addMouseListener(labelClickListener);
	}
}
