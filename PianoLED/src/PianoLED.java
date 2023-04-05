
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PianoLED extends JFrame {

	private JPanel contentPane;
	private final JPanel leftPanel;
	private final JPanel rightPanel;
	private final CardLayout cardLayout;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PianoLED frame = new PianoLED();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	int[][] Keys = new int[88][2];
	int whiteKeyPitches[] = { 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55, 57,
			59, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95, 96, 98, 100, 101,
			103, 105, 107, 108 };
// List of white keys in a 88-key piano
	int whiteKeys[] = { 0, 2, 3, 5, 7, 8, 10, 12, 14, 15, 17, 19, 20, 22, 24, 26, 27, 29, 31, 32, 34, 36, 38, 39, 41,
			43, 44, 46, 48, 50, 51, 53, 55, 56, 58, 60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79, 80, 82, 84, 86,
			87 };
	int[] blackKeys = { 1, 4, 6, 9, 11, 13, 16, 18, 21, 23, 25, 28, 30, 33, 35, 37, 40, 42, 45, 47, 49, 52, 54, 57, 59,
			61, 64, 66, 69, 71, 73, 76, 78, 81, 83, 85 };
// Create a list of x-coordinates for each key
	int[] keyXCoordinates = { 11, 40, 56, 86, 101, 116, 145, 161, 191, 206, 221, 251, 266, 296, 311, 326, 356, 371, 401,
			416, 431, 461, 476, 506, 521, 536, 566, 581, 611, 626, 641, 671, 686, 715, 731, 746 };
	private JTextField txtR;
	private JTextField txtG;
	private JTextField txtB;

	public PianoLED() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 930, 600);
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

		// X label
		JLabel lbX = new JLabel("X");
		lbX.setBounds(5, 5, 30, 30);
		lbX.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbX.setOpaque(true);
		lbX.setBackground(Color.BLACK);
		lbX.setForeground(Color.WHITE);
		lbX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		leftPanel = new JPanel();
		leftPanel.setBounds(0, 0, 110, 600);
		leftPanel.setBackground(Color.BLACK);
		contentPane.add(lbX);
		contentPane.add(leftPanel);

		JButton LivePlay = new JButton("LivePlay");
		LivePlay.setFont(new Font("Montserrat", Font.BOLD, 14));
		LivePlay.setBounds(0, 250, 110, 50);
		LivePlay.setBackground(Color.BLACK);
		LivePlay.setForeground(Color.WHITE);
		LivePlay.setFocusable(false);
		LivePlay.setBorderPainted(false);
		LivePlay.setOpaque(true); // Set opaque to true
		LivePlay.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				LivePlay.setBackground(Color.WHITE);
				LivePlay.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent e) {
				LivePlay.setBackground(Color.BLACK);
				LivePlay.setForeground(Color.WHITE);
			}

			public void mousePressed(MouseEvent e) {
				LivePlay.setBackground(Color.WHITE);
				cardLayout.show(rightPanel, "2");
			}
		});

		leftPanel.add(LivePlay);

		JButton Learn = new JButton("Learn");
		Learn.setBounds(0, 300, 110, 50);
		Learn.setFont(new Font("Montserrat", Font.BOLD, 14));
		Learn.setBackground(Color.BLACK);
		Learn.setForeground(Color.WHITE);
		Learn.setFocusable(false);
		Learn.setBorderPainted(false);
		Learn.setOpaque(true); // Set opaque to true
		Learn.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				Learn.setBackground(Color.WHITE);
				Learn.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent e) {
				Learn.setBackground(Color.BLACK);
				Learn.setForeground(Color.WHITE);
			}

			public void mousePressed(MouseEvent e) {
				cardLayout.show(rightPanel, "3");
			}
		});

		leftPanel.add(Learn);

		JButton Controls = new JButton("Controls");
		Controls.setBounds(0, 350, 110, 50);
		Controls.setFont(new Font("Montserrat", Font.BOLD, 14));
		Controls.setBackground(Color.BLACK);
		Controls.setForeground(Color.WHITE);
		Controls.setFocusable(false);
		Controls.setBorderPainted(false);
		Controls.setOpaque(true); // Set opaque to true
		Controls.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				Controls.setBackground(Color.WHITE);
				Controls.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent e) {
				Controls.setBackground(Color.BLACK);
				Controls.setForeground(Color.WHITE);
			}

			public void mousePressed(MouseEvent e) {
				cardLayout.show(rightPanel, "4");
			}
		});
		leftPanel.setLayout(null);
		leftPanel.add(Controls);

		JButton homeButton = new JButton("");
		homeButton.setBounds(0, 550, 110, 50);

		// Load the image and scale it to fit the button
		ImageIcon homeIcon = new ImageIcon(PianoLED.class.getResource("/icons/home.png"));
		Image img = homeIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

		homeButton.setIcon(new ImageIcon(img));
		homeButton.setBackground(Color.BLACK);
		homeButton.setFocusable(false);
		homeButton.setBorderPainted(false);
		homeButton.setOpaque(true); // Set opaque to true
		homeButton.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				// homeButton.setBackground(Color.WHITE);
				// homeButton.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent e) {
				// homeButton.setBackground(Color.BLACK);
				// homeButton.setForeground(Color.WHITE);
			}

			public void mousePressed(MouseEvent e) {
				cardLayout.show(rightPanel, "1");
			}
		});

		leftPanel.add(homeButton);

		rightPanel = new JPanel();
		rightPanel.setBounds(110, 0, 930, 600);
		rightPanel.setBackground(new Color(21, 25, 28));
		cardLayout = new CardLayout();
		rightPanel.setLayout(cardLayout);
		contentPane.add(rightPanel);

		// Dashboard Panel
		JPanel Dashboard = new JPanel() {
			// Draw Piano Keyboard
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				// white keys
				int x = 0;
				for (int i = 0; i < whiteKeys.length; i++) {
					if (Keys[whiteKeys[i]][0] == 1) {
						g.setColor(new Color(255, 0, 0));
					} else {
						g.setColor(Color.WHITE);
					}
					g.fillRect(x + 15, 510, 15, 70);
					g.setColor(Color.BLACK);
					g.drawRect(x + 15, 510, 15, 70);
					x += 15;
				}

				for (int i = 0; i < blackKeys.length; i++) {
					if (Keys[blackKeys[i]][1] == 1) {
						g.setColor(new Color(255, 0, 0));
					} else {
						g.setColor(Color.BLACK);
					}
					g.fillRect(keyXCoordinates[i] + 15, 510, 8, 40);
					g.setColor(Color.WHITE);
				}
			}
		};
		Dashboard.setBackground(new Color(21, 25, 28));
		rightPanel.add(Dashboard, "1");
		Dashboard.setLayout(null);

		JComboBox<?> SerialList = new JComboBox<Object>();
		SerialList.setBounds(10, 229, 200, 25);
		Dashboard.add(SerialList);

		JLabel lbDashboard = new JLabel("Dashboard");
		lbDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lbDashboard.setForeground(Color.WHITE);
		lbDashboard.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbDashboard.setBounds(630, 10, 175, 30);
		Dashboard.add(lbDashboard);

		JLabel lbConnections = new JLabel("Connections");
		lbConnections.setHorizontalAlignment(SwingConstants.CENTER);
		lbConnections.setForeground(Color.WHITE);
		lbConnections.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbConnections.setBounds(0, 70, 210, 60);
		Dashboard.add(lbConnections);

		// Serial Devices
		JLabel lbSerialDevices = new JLabel("Serial");
		lbSerialDevices.setBounds(0, 190, 210, 30);
		lbSerialDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbSerialDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbSerialDevices.setForeground(new Color(255, 255, 255));
		Dashboard.add(lbSerialDevices);

		// Midi Devices
		JLabel lbMidiDevices = new JLabel("Midi");
		lbMidiDevices.setHorizontalAlignment(SwingConstants.CENTER);
		lbMidiDevices.setForeground(Color.WHITE);
		lbMidiDevices.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbMidiDevices.setBounds(0, 365, 210, 30);
		Dashboard.add(lbMidiDevices);

		JComboBox<?> MidiList = new JComboBox<Object>();
		MidiList.setBounds(10, 405, 200, 25);
		Dashboard.add(MidiList);

		// Define the JButton for opening/closing
		JButton openButton = new JButton("Open");
		openButton.setFont(new Font("Montserrat", Font.PLAIN, 25));
		openButton.setBounds(47, 452, 117, 41);
		openButton.setBackground(Color.WHITE);
		openButton.setForeground(Color.BLACK);
		openButton.setFocusable(false);
		openButton.setBorderPainted(false);
		openButton.setOpaque(true); // Set opaque to true
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (openButton.getBackground() == Color.WHITE) {
					openButton.setBackground(Color.GREEN);
					openButton.setForeground(Color.WHITE);
					openButton.setText("Close");
				} else {
					openButton.setBackground(Color.WHITE);
					openButton.setForeground(Color.BLACK);
					openButton.setText("Open");
				}
			}
		});

		Dashboard.add(openButton);

		// LivePlay Panel
		JPanel LivePlayPanel = new JPanel();
		LivePlayPanel.setBackground(new Color(21, 25, 28));
		rightPanel.add(LivePlayPanel, "2");
		LivePlayPanel.setLayout(null);

		JLabel lbLivePlay = new JLabel("LivePlay");
		lbLivePlay.setBounds(630, 10, 175, 30);
		lbLivePlay.setHorizontalAlignment(SwingConstants.CENTER);
		lbLivePlay.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbLivePlay.setForeground(new Color(255, 255, 255));
		LivePlayPanel.add(lbLivePlay);

		// Learn Panel
		JPanel LearnPanel = new JPanel();
		LearnPanel.setBackground(new Color(21, 25, 28));
		rightPanel.add(LearnPanel, "3");
		LearnPanel.setLayout(null);

		JLabel lbLearn = new JLabel("Learn");
		lbLearn.setBounds(630, 10, 175, 30);
		lbLearn.setHorizontalAlignment(SwingConstants.CENTER);
		lbLearn.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbLearn.setForeground(new Color(255, 255, 255));
		LearnPanel.add(lbLearn);

		// Controls Panel
		JPanel ControlsPanel = new JPanel();
		ControlsPanel.setBackground(new Color(21, 25, 28));
		rightPanel.add(ControlsPanel, "4");
		ControlsPanel.setLayout(null);

		JLabel lbControls = new JLabel("Controls");
		lbControls.setBounds(630, 10, 175, 30);
		lbControls.setHorizontalAlignment(SwingConstants.CENTER);
		lbControls.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbControls.setForeground(new Color(255, 255, 255));
		ControlsPanel.add(lbControls);

		JLabel lbLEDEffect = new JLabel("LED Effect");
		lbLEDEffect.setHorizontalAlignment(SwingConstants.CENTER);
		lbLEDEffect.setForeground(Color.WHITE);
		lbLEDEffect.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbLEDEffect.setBounds(1, 95, 209, 47);
		ControlsPanel.add(lbLEDEffect);

		JComboBox<Object> LEDEffects = new JComboBox<Object>();
		LEDEffects.setBounds(10, 153, 200, 25);
		ControlsPanel.add(LEDEffects);

		JLabel lbPianoSize = new JLabel("Piano Keys");
		lbPianoSize.setHorizontalAlignment(SwingConstants.CENTER);
		lbPianoSize.setForeground(Color.WHITE);
		lbPianoSize.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbPianoSize.setBounds(1, 429, 209, 47);
		ControlsPanel.add(lbPianoSize);

		JButton lbLeftArrow = new JButton("<");
		lbLeftArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		lbLeftArrow.setOpaque(true);
		lbLeftArrow.setForeground(Color.BLACK);
		lbLeftArrow.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbLeftArrow.setFocusable(false);
		lbLeftArrow.setBorderPainted(false);
		lbLeftArrow.setBackground(Color.WHITE);
		lbLeftArrow.setBounds(27, 487, 72, 41);
		ControlsPanel.add(lbLeftArrow);

		JButton lbRightArrow = new JButton(">");
		lbRightArrow.setOpaque(true);
		lbRightArrow.setForeground(Color.BLACK);
		lbRightArrow.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbRightArrow.setFocusable(false);
		lbRightArrow.setBorderPainted(false);
		lbRightArrow.setBackground(Color.WHITE);
		lbRightArrow.setBounds(114, 487, 72, 41);
		ControlsPanel.add(lbRightArrow);

		JLabel lbColors = new JLabel("Colors");
		lbColors.setHorizontalAlignment(SwingConstants.CENTER);
		lbColors.setForeground(Color.WHITE);
		lbColors.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColors.setBounds(482, 95, 209, 47);
		ControlsPanel.add(lbColors);

		JComboBox<Object> ColorPresets = new JComboBox<Object>();
		ColorPresets.setBounds(474, 153, 219, 25);
		ControlsPanel.add(ColorPresets);
		
		JButton lbColorPicker = new JButton("");// Load the image and scale it to fit the button
		ImageIcon ColorWheel = new ImageIcon(PianoLED.class.getResource("/icons/Color.png"));
		Image wheelImg = ColorWheel.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
		lbColorPicker.setIcon(new ImageIcon(wheelImg));
		
		lbColorPicker.setOpaque(false);
		lbColorPicker.setForeground(Color.BLACK);
		lbColorPicker.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lbColorPicker.setFocusable(false);
		lbColorPicker.setBorderPainted(false);
		lbColorPicker.setBackground(Color.WHITE);
		lbColorPicker.setBounds(385, 189, 386, 263);
		ControlsPanel.add(lbColorPicker);
		
		JLabel lbColor_R = new JLabel("R:");
		lbColor_R.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_R.setForeground(Color.WHITE);
		lbColor_R.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_R.setBounds(339, 482, 92, 46);
		ControlsPanel.add(lbColor_R);
		
		JLabel lbColor_G = new JLabel("G:");
		lbColor_G.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_G.setForeground(Color.WHITE);
		lbColor_G.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_G.setBounds(474, 482, 92, 46);
		ControlsPanel.add(lbColor_G);
		
		JLabel lbColor_B = new JLabel("B:");
		lbColor_B.setHorizontalAlignment(SwingConstants.CENTER);
		lbColor_B.setForeground(Color.WHITE);
		lbColor_B.setFont(new Font("Montserrat", Font.BOLD, 30));
		lbColor_B.setBounds(599, 482, 92, 46);
		ControlsPanel.add(lbColor_B);
		
		txtR = new JTextField();
		txtR.setBounds(413, 487, 79, 35);
		ControlsPanel.add(txtR);
		txtR.setColumns(10);
		
		txtG = new JTextField();
		txtG.setColumns(10);
		txtG.setBounds(540, 487, 79, 35);
		ControlsPanel.add(txtG);
		
		txtB = new JTextField();
		txtB.setColumns(10);
		txtB.setBounds(675, 487, 79, 35);
		ControlsPanel.add(txtB);
		lbColorPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color selectedColor = JColorChooser.showDialog(PianoLED.this, "Choose a color", Color.WHITE);
				if (selectedColor != null) {
					System.out.println(selectedColor);
				}
			}
		});

	}
}
