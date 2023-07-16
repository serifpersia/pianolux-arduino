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
	private JButton lbPlayMidi = new JButton("▶");
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
		gbl_controlsPane.columnWidths = new int[] { 25, 0, 0, 0 };
		gbl_controlsPane.rowHeights = new int[] { 143, 0, -14, 30, 94, 36, 0 };
		gbl_controlsPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0 };
		gbl_controlsPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		controlsPane.setLayout(gbl_controlsPane);

		addPlaybackControls(controlsPane);
		addUIControls(controlsPane, 3);

	}

	private int addUIControls(JPanel controlsPane, int gridy) {
		gridToggle = new JCheckBox("Show Grid");
		gridToggle.setBackground(SLIDING_PANEL_BG);
		gridToggle.setForeground(Color.WHITE);
		gridToggle.setFont(SLIDING_PANEL_FONT);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 4;
		gbc.insets = new Insets(5, 5, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 4;
		controlsPane.add(gridToggle, gbc);

		infoToggle = new JCheckBox("Tech Info");
		GridBagConstraints gbc_InfoToggle = new GridBagConstraints();
		infoToggle.setBackground(SLIDING_PANEL_BG);
		infoToggle.setForeground(Color.WHITE);
		infoToggle.setFont(SLIDING_PANEL_FONT);
		gbc_InfoToggle.insets = new Insets(0, 0, 0, 5);
		gbc_InfoToggle.gridwidth = 2;
		gbc_InfoToggle.gridx = 0;
		gbc_InfoToggle.gridy = 5;
		controlsPane.add(infoToggle, gbc_InfoToggle);
		gbc.gridy = gridy + 1;
		return gridy;
	}


	private void addPlaybackControls(JPanel controlsPane) {
		Font midiControlsFont = new Font("Symbola", Font.BOLD, 16);

//		JButton lbStart = new JButton("⏮");
//		GridBagConstraints gbc_lbStart = new GridBagConstraints();
//		lbStart.setBackground(new Color(52, 152, 219));
//		lbStart.setForeground(Color.WHITE);
//		lbStart.setFocusable(false);
//		lbStart.setBorderPainted(false);
//		lbStart.setToolTipText("Restart Playback");
//		gbc_lbStart.anchor = GridBagConstraints.NORTH;
//		gbc_lbStart.insets = new Insets(0, 0, 5, 5);
//		gbc_lbStart.gridx = 0;
//		gbc_lbStart.gridy = 4;
//		lbStart.setFont(midiControlsFont);
//		controlsPane.add(lbStart, gbc_lbStart);
//		lbStart.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				midiRecorder.rewind();
//			}
//		});

//		JButton lbGoBack = new JButton("⏪");
//		GridBagConstraints gbc_lbGoBack = new GridBagConstraints();
//		lbGoBack.setBackground(new Color(52, 152, 219));
//		lbGoBack.setForeground(Color.WHITE);
//		lbGoBack.setFocusable(false);
//		lbGoBack.setBorderPainted(false);
//		lbGoBack.setToolTipText("Previous");
//		gbc_lbGoBack.anchor = GridBagConstraints.NORTH;
//		gbc_lbGoBack.insets = new Insets(0, 0, 5, 5);
//		gbc_lbGoBack.gridx = 1;
//		gbc_lbGoBack.gridy = 4;
//		lbGoBack.setFont(midiControlsFont);
//		controlsPane.add(lbGoBack, gbc_lbGoBack);
//		lbGoBack.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				midiRecorder.rewind(-PLAYER_REWIND_SEC);
//			}
//		});

		GridBagConstraints gbc_lbPlayMidi = new GridBagConstraints();
		lbPlayMidi.setBackground(new Color(52, 152, 219));
		lbPlayMidi.setForeground(Color.WHITE);
		lbPlayMidi.setFocusable(false);
		lbPlayMidi.setBorderPainted(false);
		lbPlayMidi.setToolTipText("Play/Pause");
		gbc_lbPlayMidi.anchor = GridBagConstraints.NORTH;
		gbc_lbPlayMidi.insets = new Insets(0, 0, 5, 5);
		gbc_lbPlayMidi.gridx = 2;
		gbc_lbPlayMidi.gridy = 4;
		lbPlayMidi.setFont(midiControlsFont);
		controlsPane.add(lbPlayMidi, gbc_lbPlayMidi);
		lbPlayMidi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				liveRoll.start();
//				if( midiRecorder == null )
//				{
//					initMidiRecorder(pianoLED.getPianoController().getMidiDevice());
//				}
// 				midiRecorder.startStopRecording();
//				if (midiRecorder.isRecording()) {
//					setPlaybackButtonPlay();
//				} else {
//					setPlaybackButtonStop();
//				}
			}
		});
//
//		JButton lbGoForwards = new JButton("⏩");
//		GridBagConstraints gbc_lbGoForwards = new GridBagConstraints();
//		lbGoForwards.setBackground(new Color(52, 152, 219));
//		lbGoForwards.setForeground(Color.WHITE);
//		lbGoForwards.setFocusable(false);
//		lbGoForwards.setBorderPainted(false);
//		lbGoForwards.setToolTipText("Next");
//		gbc_lbGoForwards.anchor = GridBagConstraints.NORTH;
//		gbc_lbGoForwards.insets = new Insets(0, 0, 5, 0);
//		gbc_lbGoForwards.gridx = 3;
//		gbc_lbGoForwards.gridy = 4;
//		lbGoForwards.setFont(midiControlsFont);
//		controlsPane.add(lbGoForwards, gbc_lbGoForwards);
//		lbGoForwards.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				midiRecorder.rewind(5);
//			}
//		});
	}

	private void setPlaybackButtonStop() {
		lbPlayMidi.setText("▶");
	}

	private void setPlaybackButtonPlay() {
		lbPlayMidi.setText("⏸");
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