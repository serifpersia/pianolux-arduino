package com.serifpersia.pianoled.liveplay;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;

import javax.sound.midi.MidiDevice;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.PianoLED;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class LivePlayPanel extends JPanel{

	private static final Font SLIDING_PANEL_FONT = new Font("Tahoma", Font.BOLD, 16);
	private static final Color SLIDING_PANEL_BG = Color.BLACK;
	private static final int REFRESH_RATE_MS = 16;
	public static JComboBox<?> MidiOutList;
//	private MidiRecorder midiRecorder;
	private LiveRoll liveRoll;
	private JPanel slideControlsPane = new JPanel();
	private JCheckBox gridToggle;
	private PianoLED pianoLED;
	private JCheckBox infoToggle;

	public LivePlayPanel(PianoLED pianoLED) {
		this.pianoLED = pianoLED;
		setBackground(SLIDING_PANEL_BG);
		setLayout(new BorderLayout(0, 0));
		liveRoll = new LiveRoll(pianoLED, this);
		add(liveRoll);
		addSlidingControlPanel();

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				liveRoll.repaint();
			}
		};

		Timer timer = new Timer(REFRESH_RATE_MS, taskPerformer);
		timer.start();
	}

	private void initMidiRecorder(MidiDevice midiInDevice) {
//		midiRecorder = new MidiRecorder(midiInDevice);
//		midiRecorder.addConsumer(LivePlayPanel.this);
		liveRoll.start();
 	}

	private void addSlidingControlPanel() {
		slideControlsPane.setBackground(new Color(231, 76, 60));
		slideControlsPane.setVisible(false);
		add(slideControlsPane, BorderLayout.EAST);
		slideControlsPane.setLayout(new BorderLayout(0, 0));
		// Add mouse listener to slideControlsPane
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int width = getWidth();
				if (width - x <= 205) {
					// Mouse is near the right border of LearnPanel
					slideControlsPane.setVisible(true);
				} else if (x <= 100) {
					pianoLED.leftPanel.setVisible(true);
				} else {
					// Mouse is not near the right border of LearnPanel
					slideControlsPane.setVisible(false);
					pianoLED.leftPanel.setVisible(false);
				}
			}
		});

		slideControlsPane.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int width = slideControlsPane.getWidth();
				if (x <= 0 || x >= width || e.getY() <= 0 || e.getY() >= slideControlsPane.getHeight()) {
					// Mouse is out of slideControlsPane
					slideControlsPane.setVisible(false);
				}
			}
		});

		// Add hotkey to toggle visibility of slideControlsPane
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK),
				"toggleControls");
		getActionMap().put("toggleControls", new AbstractAction() {
			boolean controlsVisible = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				controlsVisible = !controlsVisible;
				slideControlsPane.setVisible(controlsVisible);
				if (controlsVisible) {
					System.out.println("Ctrl+C(Controls_Visible)");
				} else {
					System.out.println("Ctrl+C(Controls_Hidden)");
				}
			}
		});

		JLabel lbControls = new JLabel("Controls");
		lbControls.setHorizontalAlignment(SwingConstants.CENTER);
		lbControls.setForeground(Color.WHITE);
		lbControls.setFont(new Font("Tahoma", Font.BOLD, 40));
		slideControlsPane.add(lbControls, BorderLayout.NORTH);

		JPanel controlsPane = new JPanel();
		controlsPane.setBackground(SLIDING_PANEL_BG);
		slideControlsPane.add(controlsPane, BorderLayout.EAST);
		GridBagLayout gbl_controlsPane = new GridBagLayout();
		gbl_controlsPane.columnWidths = new int[] { 170 };
		gbl_controlsPane.rowHeights = new int[] { 77, -18, -33, 0 };
		gbl_controlsPane.columnWeights = new double[] { 1.0 };
		gbl_controlsPane.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		controlsPane.setLayout(gbl_controlsPane);

		addUIControls(controlsPane, 3);

	}

	private int addUIControls(JPanel controlsPane, int gridy) {
		gridToggle = new JCheckBox("Show Grid");
		gridToggle.setHorizontalAlignment(SwingConstants.CENTER);
		gridToggle.setBackground(SLIDING_PANEL_BG);
		gridToggle.setForeground(Color.WHITE);
		gridToggle.setFont(SLIDING_PANEL_FONT);
		
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 0);
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.gridx = 0;
				gbc.gridy = 1;
				controlsPane.add(gridToggle, gbc);
				gbc.gridy = gridy + 1;

		infoToggle = new JCheckBox("Tech Info");
		infoToggle.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_InfoToggle = new GridBagConstraints();
		infoToggle.setBackground(SLIDING_PANEL_BG);
		infoToggle.setForeground(Color.WHITE);
		infoToggle.setFont(SLIDING_PANEL_FONT);
		gbc_InfoToggle.gridx = 0;
		gbc_InfoToggle.gridy = 2;
		controlsPane.add(infoToggle, gbc_InfoToggle);
		return gridy;
	}





	public boolean drawLines() {

		return false;
	}

	public boolean isShowGridSelected() {

		return gridToggle.isSelected();
	}

	public boolean isShowInfoSelected() {

		return infoToggle.isSelected();
	}

}