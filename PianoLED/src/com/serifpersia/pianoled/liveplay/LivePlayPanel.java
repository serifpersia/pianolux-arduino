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
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.PianoLED;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class LivePlayPanel extends JPanel {

	private static final Font SLIDING_PANEL_FONT = new Font("Tahoma", Font.BOLD, 16);
	private static final int REFRESH_RATE_MS = 16;
	public static JComboBox<?> MidiOutList;
//	private MidiRecorder midiRecorder;
	private LiveRoll liveRoll;
	private JPanel slideControlsPane = new JPanel();
	private JCheckBox gridToggle;
	private PianoLED pianoLED;
	private JCheckBox infoToggle;
	private JCheckBox customColor_Toggle;
	private JPanel controlsPane_1;

	public LivePlayPanel(PianoLED pianoLED) {
		this.pianoLED = pianoLED;
		setBackground(Color.BLACK);
		
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
		lbControls.setFont(new Font("Poppins", Font.PLAIN, 35));
		slideControlsPane.add(lbControls, BorderLayout.NORTH);

		controlsPane_1 = new JPanel();
		slideControlsPane.add(controlsPane_1, BorderLayout.EAST);

		addUIControls(controlsPane_1, 3);
		GroupLayout gl_controlsPane_1 = new GroupLayout(controlsPane_1);
		gl_controlsPane_1.setHorizontalGroup(
			gl_controlsPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_controlsPane_1.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_controlsPane_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(gridToggle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
						.addComponent(customColor_Toggle, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 164, Short.MAX_VALUE))
					.addGap(10))
		);
		gl_controlsPane_1.setVerticalGroup(
			gl_controlsPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_controlsPane_1.createSequentialGroup()
					.addGap(20)
					.addComponent(customColor_Toggle, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(gridToggle, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(157))
		);
		controlsPane_1.setLayout(gl_controlsPane_1);

	}

	private int addUIControls(JPanel controlsPane, int gridy) {

		customColor_Toggle = new JCheckBox("Use CustomColor");
		customColor_Toggle.setFont(new Font("Poppins", Font.PLAIN, 16));

		gridToggle = new JCheckBox("Show Grid");
		gridToggle.setFont(new Font("Poppins", Font.PLAIN, 16));

		infoToggle = new JCheckBox("Tech Info");
		infoToggle.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_InfoToggle = new GridBagConstraints();
		infoToggle.setFont(SLIDING_PANEL_FONT);
		gbc_InfoToggle.gridx = 0;
		gbc_InfoToggle.gridy = 3;
		// controlsPane.add(infoToggle, gbc_InfoToggle);
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

	public boolean isCustomColorSelected() {

		return customColor_Toggle.isSelected();
	}

}