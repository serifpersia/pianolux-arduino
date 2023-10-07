package com.serifpersia.pianoled.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.serifpersia.pianoled.ModesController;
import com.serifpersia.pianoled.PianoLED;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class pnl_Gradient_MultiColor extends JPanel {

	private ModesController modesController;

	public static JComboBox<?> cb_GradientSideList;

	private JLabel lb_Gradient2_SetSide1;
	private JButton btn_Gradient2_SetSide1;
	private JLabel lb_Gradient2_SetSide2;
	private JButton btn_Gradient2_SetSide2;
	private JLabel lb_Gradient3_SetSide1;
	private JButton btn_Gradient3_SetSide1;
	private JLabel lb_Gradient3_SetSide2;
	private JButton btn_Gradient3_SetSide2;
	private JLabel lb_Gradient3_SetSide3;
	private JButton btn_Gradient3_SetSide3;
	private JLabel lb_Gradient4_SetSide1;
	private JButton btn_Gradient4_SetSide1;
	private JLabel lb_Gradient4_SetSide2;
	private JButton btn_Gradient4_SetSide2;
	private JLabel lb_Gradient4_SetSide3;
	private JButton btn_Gradient4_SetSide3;
	private JLabel lb_Gradient4_SetSide4;
	private JButton btn_Gradient4_SetSide4;
	private JLabel lb_Gradient5_SetSide1;
	private JButton btn_Gradient5_SetSide1;
	private JLabel lb_Gradient5_SetSide2;
	private JButton btn_Gradient5_SetSide2;
	private JLabel lb_Gradient5_SetSide3;
	private JButton btn_Gradient5_SetSide3;
	private JLabel lb_Gradient5_SetSide4;
	private JButton btn_Gradient5_SetSide4;
	private JLabel lb_Gradient5_SetSide5;
	private JButton btn_Gradient5_SetSide5;
	private JLabel lb_Gradient6_SetSide1;
	private JButton btn_Gradient6_SetSide1;
	private JLabel lb_Gradient6_SetSide2;
	private JButton btn_Gradient6_SetSide2;
	private JLabel lb_Gradient6_SetSide3;
	private JButton btn_Gradient6_SetSide3;
	private JLabel lb_Gradient6_SetSide4;
	private JButton btn_Gradient6_SetSide4;
	private JLabel lb_Gradient6_SetSide5;
	private JButton btn_Gradient6_SetSide5;
	private JLabel lb_Gradient6_SetSide6;
	private JButton btn_Gradient6_SetSide6;
	private JButton btn_Gradient7_SetSide7;
	private JLabel lb_Gradient7_SetSide7;
	private JButton btn_Gradient7_SetSide6;
	private JLabel lb_Gradient7_SetSide6;
	private JButton btn_Gradient7_SetSide5;
	private JLabel lb_Gradient7_SetSide5;
	private JButton btn_Gradient7_SetSide4;
	private JLabel lb_Gradient7_SetSide4;
	private JButton btn_Gradient7_SetSide3;
	private JLabel lb_Gradient7_SetSide3;
	private JButton btn_Gradient7_SetSide2;
	private JLabel lb_Gradient7_SetSide2;
	private JButton btn_Gradient7_SetSide1;
	private JLabel lb_Gradient7_SetSide1;
	private JLabel lb_Gradient8_SetSide1;
	private JButton btn_Gradient8_SetSide1;
	private JLabel lb_Gradient8_SetSide2;
	private JButton btn_Gradient8_SetSide2;
	private JLabel lb_Gradient8_SetSide3;
	private JButton btn_Gradient8_SetSide3;
	private JLabel lb_Gradient8_SetSide4;
	private JButton btn_Gradient8_SetSide4;
	private JLabel lb_Gradient8_SetSide5;
	private JButton btn_Gradient8_SetSide5;
	private JLabel lb_Gradient8_SetSide6;
	private JButton btn_Gradient8_SetSide6;
	private JLabel lb_Gradient8_SetSide7;
	private JButton btn_Gradient8_SetSide7;
	private JLabel lb_Gradient8_SetSide8;
	private JButton btn_Gradient8_SetSide8;

	int divisionCount = 2; // Specify the number of divisions

	public static Color[] colors = new Color[8]; // Declare and initialize the colors array

	private ImageIcon setIcon;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel lb_Gradient2_SetSide1_1;
	private JPanel panel_2;
	private JButton btn_SetA;
	private JButton btn_SetASharp;
	private JButton btn_SetB;
	private JButton btn_SetC;
	private JButton btn_SetCSharp;
	private JButton btn_SetD;
	private JButton btn_SetDSharp;
	private JButton btn_SetE;
	private JButton btn_SetF;
	private JButton btn_SetFSharp;
	private JButton btn_SetG;
	private JButton btn_SetGSharp;

	private void initializeColors() {

		colors[0] = Color.RED;
		colors[1] = Color.GREEN;
		colors[2] = Color.BLUE;
		colors[3] = Color.RED;
		colors[4] = Color.GREEN;
		colors[5] = Color.BLUE;
		colors[6] = Color.RED;
		colors[7] = Color.GREEN;
	}

	public pnl_Gradient_MultiColor(PianoLED pianoLED) {

		modesController = new ModesController(pianoLED);

		initButtons();
		init();
		buttonActions(pianoLED);
		initializeColors();
	}

	private void initButtons() {

		setIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/Set.png")).getImage().getScaledInstance(90,
				45, Image.SCALE_SMOOTH));

		btn_Gradient2_SetSide1 = new JButton("");
		btn_Gradient2_SetSide2 = new JButton("");

		btn_Gradient3_SetSide1 = new JButton("");
		btn_Gradient3_SetSide2 = new JButton("");
		btn_Gradient3_SetSide3 = new JButton("");

		btn_Gradient4_SetSide1 = new JButton("");
		btn_Gradient4_SetSide2 = new JButton("");
		btn_Gradient4_SetSide3 = new JButton("");
		btn_Gradient4_SetSide4 = new JButton("");

		btn_Gradient5_SetSide1 = new JButton("");
		btn_Gradient5_SetSide2 = new JButton("");
		btn_Gradient5_SetSide3 = new JButton("");
		btn_Gradient5_SetSide4 = new JButton("");
		btn_Gradient5_SetSide5 = new JButton("");

		btn_Gradient6_SetSide1 = new JButton("");
		btn_Gradient6_SetSide2 = new JButton("");
		btn_Gradient6_SetSide3 = new JButton("");
		btn_Gradient6_SetSide4 = new JButton("");
		btn_Gradient6_SetSide5 = new JButton("");
		btn_Gradient6_SetSide6 = new JButton("");

		btn_Gradient7_SetSide7 = new JButton("");
		btn_Gradient7_SetSide6 = new JButton("");
		btn_Gradient7_SetSide5 = new JButton("");
		btn_Gradient7_SetSide4 = new JButton("");
		btn_Gradient7_SetSide3 = new JButton("");
		btn_Gradient7_SetSide2 = new JButton("");
		btn_Gradient7_SetSide1 = new JButton("");

		btn_Gradient8_SetSide1 = new JButton("");
		btn_Gradient8_SetSide2 = new JButton("");
		btn_Gradient8_SetSide3 = new JButton("");
		btn_Gradient8_SetSide4 = new JButton("");
		btn_Gradient8_SetSide5 = new JButton("");
		btn_Gradient8_SetSide6 = new JButton("");
		btn_Gradient8_SetSide7 = new JButton("");
		btn_Gradient8_SetSide8 = new JButton("");

		btn_Gradient2_SetSide1.setIcon(setIcon);
		btn_Gradient2_SetSide2.setIcon(setIcon);

		btn_Gradient3_SetSide1.setIcon(setIcon);
		btn_Gradient3_SetSide2.setIcon(setIcon);
		btn_Gradient3_SetSide3.setIcon(setIcon);

		btn_Gradient4_SetSide1.setIcon(setIcon);
		btn_Gradient4_SetSide2.setIcon(setIcon);
		btn_Gradient4_SetSide3.setIcon(setIcon);
		btn_Gradient4_SetSide4.setIcon(setIcon);

		btn_Gradient5_SetSide1.setIcon(setIcon);
		btn_Gradient5_SetSide2.setIcon(setIcon);
		btn_Gradient5_SetSide3.setIcon(setIcon);
		btn_Gradient5_SetSide4.setIcon(setIcon);
		btn_Gradient5_SetSide5.setIcon(setIcon);

		btn_Gradient6_SetSide1.setIcon(setIcon);
		btn_Gradient6_SetSide2.setIcon(setIcon);
		btn_Gradient6_SetSide3.setIcon(setIcon);
		btn_Gradient6_SetSide4.setIcon(setIcon);
		btn_Gradient6_SetSide5.setIcon(setIcon);
		btn_Gradient6_SetSide6.setIcon(setIcon);

		btn_Gradient7_SetSide7.setIcon(setIcon);
		btn_Gradient7_SetSide6.setIcon(setIcon);
		btn_Gradient7_SetSide5.setIcon(setIcon);
		btn_Gradient7_SetSide4.setIcon(setIcon);
		btn_Gradient7_SetSide3.setIcon(setIcon);
		btn_Gradient7_SetSide2.setIcon(setIcon);
		btn_Gradient7_SetSide1.setIcon(setIcon);

		btn_Gradient8_SetSide1.setIcon(setIcon);
		btn_Gradient8_SetSide2.setIcon(setIcon);
		btn_Gradient8_SetSide3.setIcon(setIcon);
		btn_Gradient8_SetSide4.setIcon(setIcon);
		btn_Gradient8_SetSide5.setIcon(setIcon);
		btn_Gradient8_SetSide6.setIcon(setIcon);
		btn_Gradient8_SetSide7.setIcon(setIcon);
		btn_Gradient8_SetSide8.setIcon(setIcon);

	}

	private void init() {
		setBackground(new Color(51, 51, 51));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0, 0));

		JPanel pnl_GradientControls = new JPanel();
		pnl_GradientControls.setBackground(new Color(51, 51, 51));
		add(pnl_GradientControls);
		pnl_GradientControls.setLayout(new BorderLayout(0, 0));

		cb_GradientSideList = new JComboBox<Object>(GetUI.gradientSides.toArray(new String[0]));
		cb_GradientSideList.putClientProperty("JComponent.roundRect", true);
		cb_GradientSideList.setForeground(new Color(204, 204, 204));
		cb_GradientSideList.setFont(new Font("Poppins", Font.PLAIN, 30));

		pnl_GradientControls.add(cb_GradientSideList, BorderLayout.NORTH);

		cb_GradientSideList.addActionListener(e -> {
			int selectedIndex = cb_GradientSideList.getSelectedIndex();
			int divisionValue = selectedIndex + 2;
			divisionCount = divisionValue;
			ControlsPanel.pnl_GradientPreview.repaint();
			modesController.gradientSideSelect(selectedIndex);
		});

		JPanel GradientCard = new JPanel();
		GradientCard.setBackground(new Color(51, 51, 51));
		pnl_GradientControls.add(GradientCard, BorderLayout.CENTER);
		GradientCard.setLayout(new CardLayout(0, 0));

		cb_GradientSideList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) GradientCard.getLayout();
				String selectedGradient = (String) cb_GradientSideList.getSelectedItem();

				if (selectedGradient != null) {
					cardLayout.show(GradientCard, selectedGradient);
				}
			}
		});

		// Create panels for each gradient
		JPanel gradient2Side = new JPanel();
		gradient2Side.setBackground(new Color(51, 51, 51));
		GradientCard.add(gradient2Side, "2 Side Gradient");
		gradient2Side.setLayout(new GridLayout(2, 0, 0, 0));

		lb_Gradient2_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient2_SetSide1.setBackground(new Color(204, 204, 204));
		lb_Gradient2_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient2_SetSide1.setForeground(Color.WHITE);
		lb_Gradient2_SetSide1.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient2Side.add(lb_Gradient2_SetSide1);

		btn_Gradient2_SetSide1.setBackground(new Color(51, 51, 51));
		btn_Gradient2_SetSide1.setBorderPainted(false);
		btn_Gradient2_SetSide1.setFocusable(false);
		gradient2Side.add(btn_Gradient2_SetSide1);

		lb_Gradient2_SetSide2 = new JLabel("Side 2");
		lb_Gradient2_SetSide2.setBackground(new Color(204, 204, 204));
		lb_Gradient2_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient2_SetSide2.setForeground(Color.WHITE);
		lb_Gradient2_SetSide2.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient2Side.add(lb_Gradient2_SetSide2);

		btn_Gradient2_SetSide2.setBackground(new Color(51, 51, 51));
		btn_Gradient2_SetSide2.setBorderPainted(false);
		btn_Gradient2_SetSide2.setFocusable(false);
		gradient2Side.add(btn_Gradient2_SetSide2);

		JPanel gradient3Side = new JPanel();
		gradient3Side.setBackground(new Color(51, 51, 51));
		GradientCard.add(gradient3Side, "3 Side Gradient");
		gradient3Side.setLayout(new GridLayout(3, 0, 0, 0));

		lb_Gradient3_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient3_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient3_SetSide1.setForeground(Color.WHITE);
		lb_Gradient3_SetSide1.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient3Side.add(lb_Gradient3_SetSide1);

		btn_Gradient3_SetSide1.setBackground(new Color(51, 51, 51));
		btn_Gradient3_SetSide1.setBorderPainted(false);
		btn_Gradient3_SetSide1.setFocusable(false);
		gradient3Side.add(btn_Gradient3_SetSide1);

		lb_Gradient3_SetSide2 = new JLabel("Side 2");
		lb_Gradient3_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient3_SetSide2.setForeground(Color.WHITE);
		lb_Gradient3_SetSide2.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient3Side.add(lb_Gradient3_SetSide2);

		btn_Gradient3_SetSide2.setBackground(new Color(51, 51, 51));
		btn_Gradient3_SetSide2.setBorderPainted(false);
		btn_Gradient3_SetSide2.setFocusable(false);
		gradient3Side.add(btn_Gradient3_SetSide2);

		lb_Gradient3_SetSide3 = new JLabel("Side 3");
		lb_Gradient3_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient3_SetSide3.setForeground(Color.WHITE);
		lb_Gradient3_SetSide3.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient3Side.add(lb_Gradient3_SetSide3);

		btn_Gradient3_SetSide3.setBackground(new Color(51, 51, 51));
		btn_Gradient3_SetSide3.setBorderPainted(false);
		btn_Gradient3_SetSide3.setFocusable(false);
		gradient3Side.add(btn_Gradient3_SetSide3);

		JPanel gradient4Side = new JPanel();
		gradient4Side.setBackground(new Color(51, 51, 51));
		GradientCard.add(gradient4Side, "4 Side Gradient");
		gradient4Side.setLayout(new GridLayout(4, 2, 0, 0));

		lb_Gradient4_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient4_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient4_SetSide1.setForeground(Color.WHITE);
		lb_Gradient4_SetSide1.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient4Side.add(lb_Gradient4_SetSide1);

		btn_Gradient4_SetSide1.setBackground(new Color(51, 51, 51));
		btn_Gradient4_SetSide1.setBorderPainted(false);
		btn_Gradient4_SetSide1.setFocusable(false);
		gradient4Side.add(btn_Gradient4_SetSide1);

		lb_Gradient4_SetSide2 = new JLabel("Side 2");
		lb_Gradient4_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient4_SetSide2.setForeground(Color.WHITE);
		lb_Gradient4_SetSide2.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient4Side.add(lb_Gradient4_SetSide2);

		btn_Gradient4_SetSide2.setBackground(new Color(51, 51, 51));
		btn_Gradient4_SetSide2.setBorderPainted(false);
		btn_Gradient4_SetSide2.setFocusable(false);
		gradient4Side.add(btn_Gradient4_SetSide2);

		lb_Gradient4_SetSide3 = new JLabel("Side 3");
		lb_Gradient4_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient4_SetSide3.setForeground(Color.WHITE);
		lb_Gradient4_SetSide3.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient4Side.add(lb_Gradient4_SetSide3);

		btn_Gradient4_SetSide3.setBackground(new Color(51, 51, 51));
		btn_Gradient4_SetSide3.setBorderPainted(false);
		btn_Gradient4_SetSide3.setFocusable(false);
		gradient4Side.add(btn_Gradient4_SetSide3);

		lb_Gradient4_SetSide4 = new JLabel("Side 4");
		lb_Gradient4_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient4_SetSide4.setForeground(Color.WHITE);
		lb_Gradient4_SetSide4.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient4Side.add(lb_Gradient4_SetSide4);

		btn_Gradient4_SetSide4.setBackground(new Color(51, 51, 51));
		btn_Gradient4_SetSide4.setBorderPainted(false);
		btn_Gradient4_SetSide4.setFocusable(false);
		gradient4Side.add(btn_Gradient4_SetSide4);

		JPanel gradient5Side = new JPanel();
		gradient5Side.setBackground(new Color(51, 51, 51));
		GradientCard.add(gradient5Side, "5 Side Gradient");
		gradient5Side.setLayout(new GridLayout(5, 0, 0, 0));

		lb_Gradient5_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient5_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide1.setForeground(Color.WHITE);
		lb_Gradient5_SetSide1.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient5Side.add(lb_Gradient5_SetSide1);

		btn_Gradient5_SetSide1.setBackground(new Color(51, 51, 51));
		btn_Gradient5_SetSide1.setBorderPainted(false);
		btn_Gradient5_SetSide1.setFocusable(false);
		gradient5Side.add(btn_Gradient5_SetSide1);

		lb_Gradient5_SetSide2 = new JLabel("Side 2");
		lb_Gradient5_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide2.setForeground(Color.WHITE);
		lb_Gradient5_SetSide2.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient5Side.add(lb_Gradient5_SetSide2);

		btn_Gradient5_SetSide2.setBackground(new Color(51, 51, 51));
		btn_Gradient5_SetSide2.setBorderPainted(false);
		btn_Gradient5_SetSide2.setFocusable(false);
		gradient5Side.add(btn_Gradient5_SetSide2);

		lb_Gradient5_SetSide3 = new JLabel("Side 3");
		lb_Gradient5_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide3.setForeground(Color.WHITE);
		lb_Gradient5_SetSide3.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient5Side.add(lb_Gradient5_SetSide3);

		btn_Gradient5_SetSide3.setBackground(new Color(51, 51, 51));
		btn_Gradient5_SetSide3.setBorderPainted(false);
		btn_Gradient5_SetSide3.setFocusable(false);
		gradient5Side.add(btn_Gradient5_SetSide3);

		lb_Gradient5_SetSide4 = new JLabel("Side 4");
		lb_Gradient5_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide4.setForeground(Color.WHITE);
		lb_Gradient5_SetSide4.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient5Side.add(lb_Gradient5_SetSide4);

		btn_Gradient5_SetSide4.setBackground(new Color(51, 51, 51));
		btn_Gradient5_SetSide4.setBorderPainted(false);
		btn_Gradient5_SetSide4.setFocusable(false);
		gradient5Side.add(btn_Gradient5_SetSide4);

		lb_Gradient5_SetSide5 = new JLabel("Side 5");
		lb_Gradient5_SetSide5.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient5_SetSide5.setForeground(Color.WHITE);
		lb_Gradient5_SetSide5.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient5Side.add(lb_Gradient5_SetSide5);

		btn_Gradient5_SetSide5.setBackground(new Color(51, 51, 51));
		btn_Gradient5_SetSide5.setBorderPainted(false);
		btn_Gradient5_SetSide5.setFocusable(false);
		gradient5Side.add(btn_Gradient5_SetSide5);

		JPanel gradient6Side = new JPanel();
		gradient6Side.setBackground(new Color(51, 51, 51));
		GradientCard.add(gradient6Side, "6 Side Gradient");
		gradient6Side.setLayout(new GridLayout(6, 0, 0, 0));

		lb_Gradient6_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient6_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide1.setForeground(Color.WHITE);
		lb_Gradient6_SetSide1.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient6Side.add(lb_Gradient6_SetSide1);

		btn_Gradient6_SetSide1.setBackground(new Color(51, 51, 51));
		btn_Gradient6_SetSide1.setBorderPainted(false);
		btn_Gradient6_SetSide1.setFocusable(false);
		gradient6Side.add(btn_Gradient6_SetSide1);

		lb_Gradient6_SetSide2 = new JLabel("Side 2");
		lb_Gradient6_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide2.setForeground(Color.WHITE);
		lb_Gradient6_SetSide2.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient6Side.add(lb_Gradient6_SetSide2);

		btn_Gradient6_SetSide2.setBackground(new Color(51, 51, 51));
		btn_Gradient6_SetSide2.setBorderPainted(false);
		btn_Gradient6_SetSide2.setFocusable(false);
		gradient6Side.add(btn_Gradient6_SetSide2);

		lb_Gradient6_SetSide3 = new JLabel("Side 3");
		lb_Gradient6_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide3.setForeground(Color.WHITE);
		lb_Gradient6_SetSide3.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient6Side.add(lb_Gradient6_SetSide3);

		btn_Gradient6_SetSide3.setBackground(new Color(51, 51, 51));
		btn_Gradient6_SetSide3.setBorderPainted(false);
		btn_Gradient6_SetSide3.setFocusable(false);
		gradient6Side.add(btn_Gradient6_SetSide3);

		lb_Gradient6_SetSide4 = new JLabel("Side 4");
		lb_Gradient6_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide4.setForeground(Color.WHITE);
		lb_Gradient6_SetSide4.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient6Side.add(lb_Gradient6_SetSide4);

		btn_Gradient6_SetSide4.setBackground(new Color(51, 51, 51));
		btn_Gradient6_SetSide4.setBorderPainted(false);
		btn_Gradient6_SetSide4.setFocusable(false);
		gradient6Side.add(btn_Gradient6_SetSide4);

		lb_Gradient6_SetSide5 = new JLabel("Side 5");
		lb_Gradient6_SetSide5.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide5.setForeground(Color.WHITE);
		lb_Gradient6_SetSide5.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient6Side.add(lb_Gradient6_SetSide5);

		btn_Gradient6_SetSide5.setBackground(new Color(51, 51, 51));
		btn_Gradient6_SetSide5.setBorderPainted(false);
		btn_Gradient6_SetSide5.setFocusable(false);
		gradient6Side.add(btn_Gradient6_SetSide5);

		lb_Gradient6_SetSide6 = new JLabel("Side 6");
		lb_Gradient6_SetSide6.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient6_SetSide6.setForeground(Color.WHITE);
		lb_Gradient6_SetSide6.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient6Side.add(lb_Gradient6_SetSide6);

		btn_Gradient6_SetSide6.setBackground(new Color(51, 51, 51));
		btn_Gradient6_SetSide6.setBorderPainted(false);
		btn_Gradient6_SetSide6.setFocusable(false);
		gradient6Side.add(btn_Gradient6_SetSide6);

		JPanel gradient7Side = new JPanel();
		gradient7Side.setBackground(new Color(51, 51, 51));
		GradientCard.add(gradient7Side, "7 Side Gradient");
		gradient7Side.setLayout(new GridLayout(7, 0, 0, 0));

		lb_Gradient7_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient7_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide1.setForeground(Color.WHITE);
		lb_Gradient7_SetSide1.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient7Side.add(lb_Gradient7_SetSide1);

		btn_Gradient7_SetSide1.setBackground(new Color(51, 51, 51));
		btn_Gradient7_SetSide1.setBorderPainted(false);
		btn_Gradient7_SetSide1.setFocusable(false);
		gradient7Side.add(btn_Gradient7_SetSide1);

		lb_Gradient7_SetSide2 = new JLabel("Side 2");
		lb_Gradient7_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide2.setForeground(Color.WHITE);
		lb_Gradient7_SetSide2.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient7Side.add(lb_Gradient7_SetSide2);

		btn_Gradient7_SetSide2.setBackground(new Color(51, 51, 51));
		btn_Gradient7_SetSide2.setBorderPainted(false);
		btn_Gradient7_SetSide2.setFocusable(false);
		gradient7Side.add(btn_Gradient7_SetSide2);

		lb_Gradient7_SetSide3 = new JLabel("Side 3");
		lb_Gradient7_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide3.setForeground(Color.WHITE);
		lb_Gradient7_SetSide3.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient7Side.add(lb_Gradient7_SetSide3);

		btn_Gradient7_SetSide3.setBackground(new Color(51, 51, 51));
		btn_Gradient7_SetSide3.setBorderPainted(false);
		btn_Gradient7_SetSide3.setFocusable(false);
		gradient7Side.add(btn_Gradient7_SetSide3);

		lb_Gradient7_SetSide4 = new JLabel("Side 4");
		lb_Gradient7_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide4.setForeground(Color.WHITE);
		lb_Gradient7_SetSide4.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient7Side.add(lb_Gradient7_SetSide4);

		btn_Gradient7_SetSide4.setBackground(new Color(51, 51, 51));
		btn_Gradient7_SetSide4.setBorderPainted(false);
		btn_Gradient7_SetSide4.setFocusable(false);
		gradient7Side.add(btn_Gradient7_SetSide4);

		lb_Gradient7_SetSide5 = new JLabel("Side 5");
		lb_Gradient7_SetSide5.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide5.setForeground(Color.WHITE);
		lb_Gradient7_SetSide5.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient7Side.add(lb_Gradient7_SetSide5);

		btn_Gradient7_SetSide5.setBackground(new Color(51, 51, 51));
		btn_Gradient7_SetSide5.setBorderPainted(false);
		btn_Gradient7_SetSide5.setFocusable(false);
		gradient7Side.add(btn_Gradient7_SetSide5);

		lb_Gradient7_SetSide6 = new JLabel("Side 6");
		lb_Gradient7_SetSide6.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide6.setForeground(Color.WHITE);
		lb_Gradient7_SetSide6.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient7Side.add(lb_Gradient7_SetSide6);

		btn_Gradient7_SetSide6.setBackground(new Color(51, 51, 51));
		btn_Gradient7_SetSide6.setBorderPainted(false);
		btn_Gradient7_SetSide6.setFocusable(false);
		gradient7Side.add(btn_Gradient7_SetSide6);

		lb_Gradient7_SetSide7 = new JLabel("Side 7");
		lb_Gradient7_SetSide7.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient7_SetSide7.setForeground(Color.WHITE);
		lb_Gradient7_SetSide7.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient7Side.add(lb_Gradient7_SetSide7);

		btn_Gradient7_SetSide7.setBackground(new Color(51, 51, 51));
		btn_Gradient7_SetSide7.setBorderPainted(false);
		btn_Gradient7_SetSide7.setFocusable(false);
		gradient7Side.add(btn_Gradient7_SetSide7);

		JPanel gradient8Side = new JPanel();
		gradient8Side.setBackground(new Color(51, 51, 51));
		GradientCard.add(gradient8Side, "8 Side Gradient");
		gradient8Side.setLayout(new GridLayout(8, 1, 0, 0));

		lb_Gradient8_SetSide1 = new JLabel("Side 1 ");
		lb_Gradient8_SetSide1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide1.setForeground(Color.WHITE);
		lb_Gradient8_SetSide1.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient8Side.add(lb_Gradient8_SetSide1);

		btn_Gradient8_SetSide1.setBackground(new Color(51, 51, 51));
		btn_Gradient8_SetSide1.setBorderPainted(false);
		btn_Gradient8_SetSide1.setFocusable(false);
		gradient8Side.add(btn_Gradient8_SetSide1);

		lb_Gradient8_SetSide2 = new JLabel("Side 2");
		lb_Gradient8_SetSide2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide2.setForeground(Color.WHITE);
		lb_Gradient8_SetSide2.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient8Side.add(lb_Gradient8_SetSide2);

		btn_Gradient8_SetSide2.setBackground(new Color(51, 51, 51));
		btn_Gradient8_SetSide2.setBorderPainted(false);
		btn_Gradient8_SetSide2.setFocusable(false);
		gradient8Side.add(btn_Gradient8_SetSide2);

		lb_Gradient8_SetSide3 = new JLabel("Side 3");
		lb_Gradient8_SetSide3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide3.setForeground(Color.WHITE);
		lb_Gradient8_SetSide3.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient8Side.add(lb_Gradient8_SetSide3);

		btn_Gradient8_SetSide3.setBackground(new Color(51, 51, 51));
		btn_Gradient8_SetSide3.setBorderPainted(false);
		btn_Gradient8_SetSide3.setFocusable(false);
		gradient8Side.add(btn_Gradient8_SetSide3);

		lb_Gradient8_SetSide4 = new JLabel("Side 4");
		lb_Gradient8_SetSide4.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide4.setForeground(Color.WHITE);
		lb_Gradient8_SetSide4.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient8Side.add(lb_Gradient8_SetSide4);

		btn_Gradient8_SetSide4.setBackground(new Color(51, 51, 51));
		btn_Gradient8_SetSide4.setBorderPainted(false);
		btn_Gradient8_SetSide4.setFocusable(false);
		gradient8Side.add(btn_Gradient8_SetSide4);

		lb_Gradient8_SetSide5 = new JLabel("Side 5");
		lb_Gradient8_SetSide5.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide5.setForeground(Color.WHITE);
		lb_Gradient8_SetSide5.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient8Side.add(lb_Gradient8_SetSide5);

		btn_Gradient8_SetSide5.setBackground(new Color(51, 51, 51));
		btn_Gradient8_SetSide5.setBorderPainted(false);
		btn_Gradient8_SetSide5.setFocusable(false);
		gradient8Side.add(btn_Gradient8_SetSide5);

		lb_Gradient8_SetSide6 = new JLabel("Side 6");
		lb_Gradient8_SetSide6.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide6.setForeground(Color.WHITE);
		lb_Gradient8_SetSide6.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient8Side.add(lb_Gradient8_SetSide6);

		btn_Gradient8_SetSide6.setBackground(new Color(51, 51, 51));
		btn_Gradient8_SetSide6.setBorderPainted(false);
		btn_Gradient8_SetSide6.setFocusable(false);
		gradient8Side.add(btn_Gradient8_SetSide6);

		lb_Gradient8_SetSide7 = new JLabel("Side 7");
		lb_Gradient8_SetSide7.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide7.setForeground(Color.WHITE);
		lb_Gradient8_SetSide7.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient8Side.add(lb_Gradient8_SetSide7);

		btn_Gradient8_SetSide7.setBackground(new Color(51, 51, 51));
		btn_Gradient8_SetSide7.setBorderPainted(false);
		btn_Gradient8_SetSide7.setFocusable(false);
		gradient8Side.add(btn_Gradient8_SetSide7);

		lb_Gradient8_SetSide8 = new JLabel("Side 8");
		lb_Gradient8_SetSide8.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient8_SetSide8.setForeground(Color.WHITE);
		lb_Gradient8_SetSide8.setFont(new Font("Poppins", Font.PLAIN, 21));
		gradient8Side.add(lb_Gradient8_SetSide8);

		btn_Gradient8_SetSide8.setBackground(new Color(51, 51, 51));
		btn_Gradient8_SetSide8.setBorderPainted(false);
		btn_Gradient8_SetSide8.setFocusable(false);
		gradient8Side.add(btn_Gradient8_SetSide8);

		panel = new JPanel();
		panel.setBackground(new Color(50, 50, 50));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(2, 0, 0, 0));

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(50, 50, 50));
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		lb_Gradient2_SetSide1_1 = new JLabel("Fade Slider");
		lb_Gradient2_SetSide1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Gradient2_SetSide1_1.setForeground(Color.WHITE);
		lb_Gradient2_SetSide1_1.setFont(new Font("Poppins", Font.PLAIN, 21));
		lb_Gradient2_SetSide1_1.setBackground(new Color(204, 204, 204));
	//	panel_1.add(lb_Gradient2_SetSide1_1);

		new JSlider();

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(50, 50, 50));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 0, 0, 0));

		btn_SetA = new JButton("A");
		btn_SetA.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetA);

		btn_SetASharp = new JButton("A#");
		btn_SetASharp.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetASharp);

		btn_SetB = new JButton("B");
		btn_SetB.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetB);

		btn_SetC = new JButton("C");
		btn_SetC.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetC);

		btn_SetCSharp = new JButton("C#");
		btn_SetCSharp.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetCSharp);

		btn_SetD = new JButton("D");
		btn_SetD.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetD);

		btn_SetDSharp = new JButton("D#");
		btn_SetDSharp.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetDSharp);

		btn_SetE = new JButton("E");
		btn_SetE.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetE);

		btn_SetF = new JButton("F");
		btn_SetF.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetF);

		btn_SetFSharp = new JButton("F#");
		btn_SetFSharp.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetFSharp);

		btn_SetG = new JButton("G");
		btn_SetG.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetG);

		btn_SetGSharp = new JButton("G#");
		btn_SetGSharp.setFont(new Font("Poppins", Font.PLAIN, 12));
		panel_2.add(btn_SetGSharp);
	}

	Color interpolateColor(Color color1, Color color2, float ratio) {
		float[] hsbColor1 = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
		float[] hsbColor2 = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);

		float hue = hsbColor1[0] * (1 - ratio) + hsbColor2[0] * ratio;
		float saturation = hsbColor1[1] * (1 - ratio) + hsbColor2[1] * ratio;
		float brightness = hsbColor1[2] * (1 - ratio) + hsbColor2[2] * ratio;

		return Color.getHSBColor(hue, saturation, brightness);
	}

	private void buttonActions(PianoLED pianoLED) {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {

				case "btn_Gradienet2_SetSide1":

					colors[0] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet2_SetSide2":

					colors[1] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;

				case "btn_Gradienet3_SetSide1":

					colors[0] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

				case "btn_Gradienet3_SetSide2":

					colors[1] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet3_SetSide3":

					colors[2] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;

				case "btn_Gradienet4_SetSide1":

					colors[0] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet4_SetSide2":

					colors[1] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet4_SetSide3":

					colors[2] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradienet4_SetSide4":

					colors[3] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;

				case "btn_Gradient5_SetSide1":

					colors[0] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient5_SetSide2":

					colors[1] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient5_SetSide3":

					colors[2] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient5_SetSide4":

					colors[3] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient5_SetSide5":

					colors[4] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;

				case "btn_Gradient6_SetSide1":

					colors[0] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide2":

					colors[1] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide3":

					colors[2] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide4":

					colors[3] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide5":

					colors[4] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient6_SetSide6":

					colors[5] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;

				case "btn_Gradient7_SetSide1":

					colors[0] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide2":

					colors[1] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide3":

					colors[2] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide4":

					colors[3] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide5":

					colors[4] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide6":

					colors[5] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient7_SetSide7":

					colors[6] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;

				case "btn_Gradient8_SetSide1":

					colors[0] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide2":

					colors[1] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide3":

					colors[2] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide4":

					colors[3] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide5":

					colors[4] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide6":

					colors[5] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide7":

					colors[6] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();

					break;
				case "btn_Gradient8_SetSide8":

					colors[7] = GetUI.selectedColor;
					ControlsPanel.pnl_GradientPreview.repaint();
					break;
				case "btn_SetA":
					GetUI.multiColors[0] = GetUI.selectedColor;
					break;
				case "btn_SetASharp":
					GetUI.multiColors[1] = GetUI.selectedColor;
					break;
				case "btn_SetB":
					GetUI.multiColors[2] = GetUI.selectedColor;
					break;
				case "btn_SetC":
					GetUI.multiColors[3] = GetUI.selectedColor;
					break;
				case "btn_SetCSharp":
					GetUI.multiColors[4] = GetUI.selectedColor;
					break;
				case "btn_SetD":
					GetUI.multiColors[5] = GetUI.selectedColor;
					break;
				case "btn_SetDSharp":
					GetUI.multiColors[6] = GetUI.selectedColor;
					break;
				case "btn_SetE":
					GetUI.multiColors[7] = GetUI.selectedColor;
					break;
				case "btn_SetF":
					GetUI.multiColors[8] = GetUI.selectedColor;
					break;
				case "btn_SetFSharp":
					GetUI.multiColors[9] = GetUI.selectedColor;
					break;
				case "btn_SetG":
					GetUI.multiColors[10] = GetUI.selectedColor;
					break;
				case "btn_SetGSharp":
					GetUI.multiColors[11] = GetUI.selectedColor;
					break;
				default:
					break;
				}
			}
		};

		btn_Gradient2_SetSide1.addActionListener(buttonListener);
		btn_Gradient2_SetSide2.addActionListener(buttonListener);

		btn_Gradient3_SetSide1.addActionListener(buttonListener);
		btn_Gradient3_SetSide2.addActionListener(buttonListener);
		btn_Gradient3_SetSide3.addActionListener(buttonListener);

		btn_Gradient4_SetSide1.addActionListener(buttonListener);
		btn_Gradient4_SetSide2.addActionListener(buttonListener);
		btn_Gradient4_SetSide3.addActionListener(buttonListener);
		btn_Gradient4_SetSide4.addActionListener(buttonListener);

		btn_Gradient5_SetSide1.addActionListener(buttonListener);
		btn_Gradient5_SetSide2.addActionListener(buttonListener);
		btn_Gradient5_SetSide3.addActionListener(buttonListener);
		btn_Gradient5_SetSide4.addActionListener(buttonListener);
		btn_Gradient5_SetSide5.addActionListener(buttonListener);

		btn_Gradient6_SetSide1.addActionListener(buttonListener);
		btn_Gradient6_SetSide2.addActionListener(buttonListener);
		btn_Gradient6_SetSide3.addActionListener(buttonListener);
		btn_Gradient6_SetSide4.addActionListener(buttonListener);
		btn_Gradient6_SetSide5.addActionListener(buttonListener);
		btn_Gradient6_SetSide6.addActionListener(buttonListener);

		btn_Gradient7_SetSide1.addActionListener(buttonListener);
		btn_Gradient7_SetSide2.addActionListener(buttonListener);
		btn_Gradient7_SetSide3.addActionListener(buttonListener);
		btn_Gradient7_SetSide4.addActionListener(buttonListener);
		btn_Gradient7_SetSide5.addActionListener(buttonListener);
		btn_Gradient7_SetSide6.addActionListener(buttonListener);
		btn_Gradient7_SetSide7.addActionListener(buttonListener);

		btn_Gradient8_SetSide1.addActionListener(buttonListener);
		btn_Gradient8_SetSide2.addActionListener(buttonListener);
		btn_Gradient8_SetSide3.addActionListener(buttonListener);
		btn_Gradient8_SetSide4.addActionListener(buttonListener);
		btn_Gradient8_SetSide5.addActionListener(buttonListener);
		btn_Gradient8_SetSide6.addActionListener(buttonListener);
		btn_Gradient8_SetSide7.addActionListener(buttonListener);
		btn_Gradient8_SetSide8.addActionListener(buttonListener);

		btn_SetA.addActionListener(buttonListener);
		btn_SetASharp.addActionListener(buttonListener);
		btn_SetB.addActionListener(buttonListener);
		btn_SetC.addActionListener(buttonListener);
		btn_SetCSharp.addActionListener(buttonListener);
		btn_SetD.addActionListener(buttonListener);
		btn_SetDSharp.addActionListener(buttonListener);
		btn_SetE.addActionListener(buttonListener);
		btn_SetF.addActionListener(buttonListener);
		btn_SetFSharp.addActionListener(buttonListener);
		btn_SetG.addActionListener(buttonListener);
		btn_SetGSharp.addActionListener(buttonListener);

		btn_Gradient2_SetSide1.setActionCommand("btn_Gradienet2_SetSide1");
		btn_Gradient2_SetSide2.setActionCommand("btn_Gradienet2_SetSide2");

		btn_Gradient3_SetSide1.setActionCommand("btn_Gradienet3_SetSide1");
		btn_Gradient3_SetSide2.setActionCommand("btn_Gradienet3_SetSide2");
		btn_Gradient3_SetSide3.setActionCommand("btn_Gradienet3_SetSide3");

		btn_Gradient4_SetSide1.setActionCommand("btn_Gradienet4_SetSide1");
		btn_Gradient4_SetSide2.setActionCommand("btn_Gradienet4_SetSide2");
		btn_Gradient4_SetSide3.setActionCommand("btn_Gradienet4_SetSide3");
		btn_Gradient4_SetSide4.setActionCommand("btn_Gradienet4_SetSide4");

		btn_Gradient5_SetSide1.setActionCommand("btn_Gradient5_SetSide1");
		btn_Gradient5_SetSide2.setActionCommand("btn_Gradient5_SetSide2");
		btn_Gradient5_SetSide3.setActionCommand("btn_Gradient5_SetSide3");
		btn_Gradient5_SetSide4.setActionCommand("btn_Gradient5_SetSide4");
		btn_Gradient5_SetSide5.setActionCommand("btn_Gradient5_SetSide5");

		btn_Gradient6_SetSide1.setActionCommand("btn_Gradient6_SetSide1");
		btn_Gradient6_SetSide2.setActionCommand("btn_Gradient6_SetSide2");
		btn_Gradient6_SetSide3.setActionCommand("btn_Gradient6_SetSide3");
		btn_Gradient6_SetSide4.setActionCommand("btn_Gradient6_SetSide4");
		btn_Gradient6_SetSide5.setActionCommand("btn_Gradient6_SetSide5");
		btn_Gradient6_SetSide6.setActionCommand("btn_Gradient6_SetSide6");

		btn_Gradient7_SetSide1.setActionCommand("btn_Gradient7_SetSide1");
		btn_Gradient7_SetSide2.setActionCommand("btn_Gradient7_SetSide2");
		btn_Gradient7_SetSide3.setActionCommand("btn_Gradient7_SetSide3");
		btn_Gradient7_SetSide4.setActionCommand("btn_Gradient7_SetSide4");
		btn_Gradient7_SetSide5.setActionCommand("btn_Gradient7_SetSide5");
		btn_Gradient7_SetSide6.setActionCommand("btn_Gradient7_SetSide6");
		btn_Gradient7_SetSide7.setActionCommand("btn_Gradient7_SetSide7");

		btn_Gradient8_SetSide1.setActionCommand("btn_Gradient8_SetSide1");
		btn_Gradient8_SetSide2.setActionCommand("btn_Gradient8_SetSide2");
		btn_Gradient8_SetSide3.setActionCommand("btn_Gradient8_SetSide3");
		btn_Gradient8_SetSide4.setActionCommand("btn_Gradient8_SetSide4");
		btn_Gradient8_SetSide5.setActionCommand("btn_Gradient8_SetSide5");
		btn_Gradient8_SetSide6.setActionCommand("btn_Gradient8_SetSide6");
		btn_Gradient8_SetSide7.setActionCommand("btn_Gradient8_SetSide7");
		btn_Gradient8_SetSide8.setActionCommand("btn_Gradient8_SetSide8");

		btn_SetA.setActionCommand("btn_SetA");
		btn_SetASharp.setActionCommand("btn_SetASharp");
		btn_SetB.setActionCommand("btn_SetB");
		btn_SetC.setActionCommand("btn_SetC");
		btn_SetCSharp.setActionCommand("btn_SetCSharp");
		btn_SetD.setActionCommand("btn_SetD");
		btn_SetDSharp.setActionCommand("btn_SetDSharp");
		btn_SetE.setActionCommand("btn_SetE");
		btn_SetF.setActionCommand("btn_SetF");
		btn_SetFSharp.setActionCommand("btn_SetFSharp");
		btn_SetG.setActionCommand("btn_SetG");
		btn_SetGSharp.setActionCommand("btn_SetGSharp");

	}

}
