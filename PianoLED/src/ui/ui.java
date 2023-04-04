package ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ui extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ui frame = new ui();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	/**
	 * Create the frame.
	 */
	public ui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0,930, 600);
		setLocationRelativeTo(null);

		// Main content pane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(21, 25, 28));
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			int x, y;

			@Override
			public void mouseDragged(MouseEvent e) {
				// Get the new mouse position
				int newX = e.getXOnScreen();
				int newY = e.getYOnScreen();

				// Calculate the distance the mouse has moved
				int deltaX = newX - x;
				int deltaY = newY - y;

				// Move the window by the same distance
				setLocation(getX() + deltaX, getY() + deltaY);

				// Update the stored mouse position
				x = newX;
				y = newY;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// Store the initial mouse position
				x = e.getXOnScreen();
				y = e.getYOnScreen();
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// X label
		JLabel lbX = new JLabel("X");
		lbX.setBounds(905, 5, 30, 30);
		lbX.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lbX.setOpaque(true);
		lbX.setBackground(new Color(21, 25, 28));
		lbX.setForeground(Color.WHITE);
		lbX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		contentPane.add(lbX);

		// New panel at the left bottom side
		JPanel LeftPane = new JPanel();
		LeftPane.setBounds(0, 0, 110, 600);
		LeftPane.setBackground(Color.BLACK);
		contentPane.add(LeftPane);

		// LivePlay Button
		JButton LivePlay = new JButton("LivePlay");
		LivePlay.setFont(new Font("Montserrat", Font.BOLD, 14));
		LivePlay.setBounds(0, 250, 110, 50);
		LivePlay.setBackground(Color.BLACK);
		LivePlay.setForeground(Color.WHITE);
		LivePlay.setFocusable(false);
		LivePlay.setBorderPainted(false);
		LivePlay.setOpaque(true); // Set opaque to true
		LivePlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("LivePlay clicked");
			}
		});
		LeftPane.setLayout(null);
		LeftPane.add(LivePlay);

		// Learn Button
		JButton Learn = new JButton("Learn");
		Learn.setBounds(0, 300, 110, 50);
		Learn.setFont(new Font("Montserrat", Font.BOLD, 14));
		Learn.setBackground(Color.BLACK);
		Learn.setForeground(Color.WHITE);
		Learn.setFocusable(false);
		Learn.setBorderPainted(false);
		Learn.setOpaque(true); // Set opaque to true
		Learn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Learn clicked");
			}
		});
		LeftPane.add(Learn);

		// Controls Button
		JButton Controls = new JButton("Controls");
		Controls.setBounds(0, 350, 110, 50);
		Controls.setFont(new Font("Montserrat", Font.BOLD, 14));
		Controls.setBackground(Color.BLACK);
		Controls.setForeground(Color.WHITE);
		Controls.setFocusable(false);
		Controls.setBorderPainted(false);
		Controls.setOpaque(true); // Set opaque to true
		Controls.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Controls clicked");
			}
		});
		LeftPane.add(Controls);

		// Home Button
		JButton Home = new JButton("");
		Home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Load the image and scale it to fit the button
		ImageIcon homeIcon = new ImageIcon(ui.class.getResource("/icons/home.png"));
		Image img = homeIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

		Home.setIcon(new ImageIcon(img));
		Home.setBounds(0, 550, 100, 50);
		Home.setBackground(Color.BLACK);
		Home.setFocusable(false);
		Home.setBorderPainted(false);
		Home.setOpaque(true); // Set opaque to true
		Home.addMouseListener(new MouseAdapter() { // Add mouse listener to change background color
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Color test");
			}

		});
		LeftPane.add(Home);
	}

}