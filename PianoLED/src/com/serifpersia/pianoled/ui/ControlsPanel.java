package com.serifpersia.pianoled.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.PianoLED;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class ControlsPanel extends JPanel {

	private pnl_Colors pnl_Right;
	static JPanel pnl_GradientPreview;

	private pnl_Gradient GradientPanel;
	private pnl_Guide pnl_Guide;

	private pnl_Controls pnl_Controls;
	private JLabel lb_Left;
	private JLabel lb_Controls;
	private JLabel lb_Right;

	private JPanel pnl_Left;
	CardLayout cardLayout = new CardLayout();

	public ControlsPanel(PianoLED pianoLED) {

		// Create CardLayout for pnl_Left

		pnl_Left = new JPanel();
		pnl_Left.setLayout(cardLayout);

		GradientPanel = new pnl_Gradient(pianoLED);
		pnl_Guide = new pnl_Guide(pianoLED);
		pnl_Controls = new pnl_Controls(pianoLED);

		pnl_Right = new pnl_Colors();
		init();
		topLabelCardAction();
	}

	private void init() {

		setLayout(new BorderLayout(0, 0));

		// Add panels to pnl_Left with corresponding names
		pnl_Left.add(pnl_Controls, "Controls");
		pnl_Left.add(GradientPanel, "Gradient");
		pnl_Left.add(pnl_Guide, "GuideControls");

		JLabel lblNewLabel = new JLabel("Controls");
		lblNewLabel.setForeground(new Color(204, 204, 204));
		lblNewLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, BorderLayout.NORTH);

		JPanel innerPanel = new JPanel();
		innerPanel.setBackground(new Color(25, 25, 25));
		add(innerPanel, BorderLayout.CENTER);

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

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 48, 5));
		topPanel.setBackground(new Color(50, 50, 50));

		lb_Controls = new JLabel("Controls");
		lb_Controls.setForeground(new Color(204, 204, 204));
		lb_Controls.setFont(new Font("Poppins", Font.PLAIN, 21));

		lb_Left = new JLabel("Gradient Controls");
		lb_Left.setForeground(new Color(128, 128, 128));
		lb_Left.setFont(new Font("Poppins", Font.PLAIN, 21));

		lb_Right = new JLabel("Guide Controls");
		lb_Right.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 27));
		lb_Right.setForeground(new Color(128, 128, 128));
		lb_Right.setFont(new Font("Poppins", Font.PLAIN, 21));

		topPanel.add(lb_Left);
		topPanel.add(lb_Controls);
		topPanel.add(lb_Right);

		GroupLayout gl_innerPanel = new GroupLayout(innerPanel);
		gl_innerPanel.setHorizontalGroup(gl_innerPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 1004, Short.MAX_VALUE)
				.addGroup(gl_innerPanel.createSequentialGroup().addContainerGap()
						.addComponent(pnl_Left, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE).addGap(8)
						.addComponent(pnl_Right, GroupLayout.PREFERRED_SIZE, 476, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(19, Short.MAX_VALUE)));
		gl_innerPanel.setVerticalGroup(gl_innerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_innerPanel.createSequentialGroup()
						.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE).addGap(10)
						.addGroup(gl_innerPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(pnl_Right, 0, 0, Short.MAX_VALUE)
								.addComponent(pnl_Left, GroupLayout.PREFERRED_SIZE, 328, Short.MAX_VALUE))
						.addGap(9)));

		innerPanel.setLayout(gl_innerPanel);

		// Function to handle all label clicks

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
