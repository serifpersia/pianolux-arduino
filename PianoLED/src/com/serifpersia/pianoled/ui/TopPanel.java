package com.serifpersia.pianoled.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.Updater;

import java.awt.FlowLayout;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {

	private RightPanel rightPanel;
	private ImageIcon dashboardIcon;

	private ImageIcon pianoLEDIcon;
	private ImageIcon controlsIcon;
	private ImageIcon liveplayIcon;
	private ImageIcon learnIcon;
	private ImageIcon exitIcon;

	private JFrame AboutPianoLEDDialog;
	private JButton learnButton;
	private JButton exitButton;

	static Updater updator = new Updater();

	public static boolean learnOn = false;

	private Point initialClick;
	private JButton livePlayButton;

	public TopPanel(RightPanel rightPanel, PianoLED pianoLED) {
		this.rightPanel = rightPanel;

		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setBackground(new Color(25, 25, 25));

		JButton AboutButton = createButton(pianoLED, "", "");

		AboutButton.addActionListener(e -> showAboutPianoLEDDialog());

		add(AboutButton);

		JLabel lb_Title = new JLabel("PianoLED" + updator.VersionTag);
		lb_Title.setFont(new Font("Poppins", Font.PLAIN, 16));
		add(lb_Title);

		lb_Title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 636));

		JButton dashboardButton = createButton(pianoLED, "", "Dashboard");

		JButton controlsButton = createButton(pianoLED, "", "Controls");

		livePlayButton = createButton(pianoLED, "", "LivePlay");

		learnButton = createButton(pianoLED, "", "Learn");

		exitButton = createButton(pianoLED, "", "exitButton");

		exitButton.addActionListener(e -> exit(pianoLED));

		pianoLEDIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/PianoLED.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		dashboardIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/home.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		controlsIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/controls.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		liveplayIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/liveplay.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		learnIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/learn.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		exitIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/exit.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));

		AboutButton.setIcon(pianoLEDIcon);
		dashboardButton.setIcon(dashboardIcon);
		controlsButton.setIcon(controlsIcon);
		livePlayButton.setIcon(liveplayIcon);
		learnButton.setIcon(learnIcon);
		exitButton.setIcon(exitIcon);

		add(dashboardButton);
		add(controlsButton);
		add(livePlayButton);
		add(learnButton);

		add(exitButton);

		dragMouse(pianoLED);

	}

	private JButton createButton(PianoLED pianoLED, String text, String cardName) {
		JButton button = new JButton(text);

		button.setFont(new Font("Poppins", Font.PLAIN, 26));
		button.setBackground(new Color(25, 25, 25));
		button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		button.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
				cardLayout.show(rightPanel, cardName);

				if (button == learnButton) {
					System.out.println("in learn mode");
					learnOn = true;
				} else if (button == livePlayButton) {
					pianoLED.toggleFullScreen();
				} else {
					learnOn = false;
				}
			}

		});

		return button;
	}

	private void showAboutPianoLEDDialog() {

		AboutPianoLED aboutPanel = new AboutPianoLED();

		if (AboutPianoLEDDialog == null) { // Check if the frame has already been created
			AboutPianoLEDDialog = new JFrame("AboutPianoLED");
			AboutPianoLEDDialog.setUndecorated(true);
			AboutPianoLEDDialog.getContentPane().add(aboutPanel);
			AboutPianoLEDDialog.setSize(300, 400);
			AboutPianoLEDDialog.setLocationRelativeTo(null);
		}
		AboutPianoLEDDialog.setVisible(true);
	}

	private void dragMouse(JFrame frame) {
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}
		});

		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				int thisX = frame.getLocation().x;
				int thisY = frame.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int x = thisX + xMoved;
				int y = thisY + yMoved;
				frame.setLocation(x, y);
			}
		});
	}

	private void exit(PianoLED pianoLED) {
		WindowEvent windowClosing = new WindowEvent(pianoLED, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
	}

}
