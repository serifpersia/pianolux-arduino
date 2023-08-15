package com.serifpersia.pianoled.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
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
import javax.swing.border.EmptyBorder;

import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.Updater;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {

	private CenterPanel centerPanel;
	private ImageIcon dashboardIcon;

	private ImageIcon pianoLEDIcon;
	private ImageIcon controlsIcon;
	private ImageIcon liveplayIcon;
	private ImageIcon learnIcon;

	private ImageIcon maximizeIcon;
	private ImageIcon minimizeIcon;
	private ImageIcon exitIcon;

	private JFrame AboutPianoLEDDialog;
	private JButton learnButton;

	private JButton minimizeButton;
	private JButton maximizeButton;
	private JButton exitButton;

	static Updater updator = new Updater();

	public static boolean learnOn = false;

	private Point initialClick;
	private JButton livePlayButton;
	private JButton controlsButton;
	public static boolean isLivePlay = false;

	public TopPanel(CenterPanel rightPanel, PianoLED pianoLED) {
		this.centerPanel = rightPanel;
		setBackground(new Color(25, 25, 25));
		setLayout(new GridLayout(0, 3, 0, 0));

		JPanel pnl_pianoledControls = new JPanel();
		pnl_pianoledControls.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JButton AboutButton = createButton(pianoLED, "", "");

		AboutButton.addActionListener(e -> showAboutPianoLEDDialog());

		pnl_pianoledControls.add(AboutButton);

		JLabel lb_Title = new JLabel("PianoLED" + updator.VersionTag);
		lb_Title.setFont(new Font("Poppins", Font.PLAIN, 16));
		lb_Title.setBorder(new EmptyBorder(0, 5, 0, 0));
		pnl_pianoledControls.add(lb_Title);

		add(pnl_pianoledControls);

		JPanel pnl_panelsControls = new JPanel();
		pnl_panelsControls.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

		JButton dashboardButton = createButton(pianoLED, "", "Dashboard");

		controlsButton = createButton(pianoLED, "", "Controls");

		livePlayButton = createButton(pianoLED, "", "LivePlay");

		learnButton = createButton(pianoLED, "", "Learn");

		pnl_panelsControls.add(dashboardButton);
		pnl_panelsControls.add(controlsButton);
		pnl_panelsControls.add(livePlayButton);
		pnl_panelsControls.add(learnButton);

		add(pnl_panelsControls);

		JPanel pnl_frameControls = new JPanel();
		pnl_frameControls.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		maximizeButton = createButton(pianoLED, "", "maximizeButton");
		minimizeButton = createButton(pianoLED, "", "minimizeButton");
		exitButton = createButton(pianoLED, "", "exitButton");

		pnl_frameControls.add(minimizeButton);
		pnl_frameControls.add(maximizeButton);
		pnl_frameControls.add(exitButton);

		minimizeButton.addActionListener(e -> minimize(pianoLED));
		maximizeButton.addActionListener(e -> maximize(pianoLED));
		exitButton.addActionListener(e -> exit(pianoLED));

		add(pnl_frameControls);

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

		minimizeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/minimize.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		maximizeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/maximize.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));

		exitIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/exit.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));

		AboutButton.setIcon(pianoLEDIcon);
		dashboardButton.setIcon(dashboardIcon);
		controlsButton.setIcon(controlsIcon);
		livePlayButton.setIcon(liveplayIcon);
		learnButton.setIcon(learnIcon);

		minimizeButton.setIcon(minimizeIcon);
		maximizeButton.setIcon(maximizeIcon);
		exitButton.setIcon(exitIcon);

		dragMouse(pianoLED);
	}

	private JButton createButton(PianoLED pianoLED, String text, String cardName) {
		JButton button = new JButton(text);

		button.setFont(new Font("Poppins", Font.PLAIN, 26));
		button.setBackground(new Color(25, 25, 25));
		button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		button.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
				cardLayout.show(centerPanel, cardName);

				if (button == learnButton) {
					learnOn = true;
				} else if (button == livePlayButton) {
					// pianoLED.toggleFullScreen();
					isLivePlay = true;
				} else if (button == controlsButton) {
					pianoLED.updateBottomPanelHeightPercentage(0.15);
				} else {
					learnOn = false;
					isLivePlay = false;
				}
			}

		});

		return button;
	}

	private void showAboutPianoLEDDialog() {

		AboutPianoLED aboutPanel = new AboutPianoLED();

		if (AboutPianoLEDDialog == null) { // Check if the frame has already been created
			AboutPianoLEDDialog = new JFrame("About");

			AboutPianoLEDDialog.setIconImage(new ImageIcon(getClass().getResource("/icons/PianoLED.png")).getImage());

			AboutPianoLEDDialog.setUndecorated(true);
			AboutPianoLEDDialog.getContentPane().add(aboutPanel);
			AboutPianoLEDDialog.setSize(300, 400);
			AboutPianoLEDDialog.setLocationRelativeTo(null);
		}
		AboutPianoLEDDialog.setVisible(true);
	}

	private void dragMouse(JFrame frame) {
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
			}
		});

		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				int xMoved = e.getXOnScreen() - initialClick.x;
				int yMoved = e.getYOnScreen() - initialClick.y;

				frame.setLocation(xMoved, yMoved);
			}
		});
	}

	private void minimize(PianoLED pianoLED) {
		pianoLED.setState(Frame.ICONIFIED); // Minimize the frame
	}

	private void maximize(PianoLED pianoLED) {
		int state = pianoLED.getExtendedState();
		if ((state & Frame.MAXIMIZED_BOTH) != 0) {
			// Window is currently maximized, so restore to normal size
			pianoLED.setExtendedState(state & ~Frame.MAXIMIZED_BOTH);
		} else {
			// Window is currently in normal state, so maximize it
			pianoLED.setExtendedState(state | Frame.MAXIMIZED_BOTH);
		}
	}

	private void exit(PianoLED pianoLED) {
		WindowEvent windowClosing = new WindowEvent(pianoLED, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
	}
}
