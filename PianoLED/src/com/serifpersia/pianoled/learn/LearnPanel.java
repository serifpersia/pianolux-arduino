package com.serifpersia.pianoled.learn;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiDevice.Info;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.PianoLED;
import com.serifpersia.pianoled.ui.DrawPiano;
import com.serifpersia.pianoled.ui.TopPanel;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class LearnPanel extends JPanel implements MidiPlayerConsumer, PianoMidiConsumer {

	private static final String DEFAULT_MIDI_RESOURCE = "/midi/clair_de_lune.mid";
	private static final int PLAYER_REWIND_SEC = 5;
	private static final Font SLIDING_PANEL_FONT = new Font("Poppins", Font.PLAIN, 16);
	private static final int REFRESH_RATE_MS = 16;
	public static JComboBox<?> MidiOutList;
	private MidiPlayer midiPlayer;
	private PianoRoll pianoRoll;
	private JLabel midiFileName;
	private JPanel slideControlsPane = new JPanel();
	private JButton lbPlayMidi = new JButton("▶");
	private JCheckBox gridToggle;
	private PianoLED pianoLED;
	private JCheckBox infoToggle;
//	private long commandKey1Arrived;

//	private static final int COMMAND_MAX_SPAN_MS = 2000;
	private static final int COMMAND_KEY1 = DrawPiano.FIRST_KEY_PITCH_OFFSET + 86;
	private Map<Integer, String> commandKeys = Map.of(COMMAND_KEY1 - 2, "Back", COMMAND_KEY1, "PlayPause",
			COMMAND_KEY1 + 1, "Forward");
	private JButton lbLoadMidi;
	private JLabel midiFileName_1;
	private JButton lbStart;
	private JButton lbGoBack;
	private JButton lbGoForwards;

	public LearnPanel(PianoLED pianoLED) {
		this.pianoLED = pianoLED;
		setBackground(Color.BLACK);
		setLayout(new BorderLayout(0, 0));
		pianoRoll = new PianoRoll(pianoLED, this);
		add(pianoRoll);
		addSlidingControlPanel();

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pianoRoll.repaint();
			}
		};

		Timer timer = new Timer(REFRESH_RATE_MS, taskPerformer);
		timer.start();
		try (InputStream midiSample = getClass().getResourceAsStream(DEFAULT_MIDI_RESOURCE)) {
			initMidiPlayer(midiSample.readAllBytes(), DEFAULT_MIDI_RESOURCE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initMidiPlayer(byte[] midiInput, String midiName) {
		String deviceName = (String) MidiOutList.getSelectedItem();
		midiPlayer = new MidiPlayer(midiInput, midiName, getMidiDeviceByName(deviceName));
		midiPlayer.addConsumer(LearnPanel.this);
		pianoRoll.start(midiPlayer);
		setMidiFileNameLabel(midiName);
	}

	public void initMidiPlayer(File selectedFile) {
		try (FileInputStream fileStream = new FileInputStream(selectedFile)) {
			initMidiPlayer(fileStream.readAllBytes(), selectedFile.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addSlidingControlPanel() {
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
				} else {
					// Mouse is not near the right border of LearnPanel
					slideControlsPane.setVisible(false);
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

		JPanel controlsPane = new JPanel();
		slideControlsPane.add(controlsPane, BorderLayout.EAST);

		midiFileName = addMidiFileNameLabel(controlsPane, 1);
		addLoadMidiFileControl(controlsPane, 0, midiFileName);
		addMidiOutListControl(controlsPane, 2);
		addPlaybackControls(controlsPane, 3);
		addUIControls(controlsPane, 3);
		GroupLayout gl_controlsPane = new GroupLayout(controlsPane);
		gl_controlsPane.setHorizontalGroup(gl_controlsPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_controlsPane.createSequentialGroup().addContainerGap().addGroup(gl_controlsPane
						.createParallelGroup(Alignment.LEADING)
						.addComponent(infoToggle, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addComponent(gridToggle, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addComponent(MidiOutList, 0, 210, Short.MAX_VALUE)
						.addGroup(gl_controlsPane.createSequentialGroup()
								.addComponent(lbStart, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addGap(5)
								.addComponent(lbGoBack, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addGap(5)
								.addComponent(lbPlayMidi, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addGap(5)
								.addComponent(lbGoForwards, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
						.addComponent(lbLoadMidi, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(midiFileName_1, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
						.addContainerGap()));
		gl_controlsPane.setVerticalGroup(gl_controlsPane.createParallelGroup(Alignment.LEADING).addGroup(gl_controlsPane
				.createSequentialGroup().addGap(20).addComponent(lbLoadMidi).addGap(10).addComponent(midiFileName_1)
				.addGap(10)
				.addComponent(
						MidiOutList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(10)
				.addGroup(gl_controlsPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbStart, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbPlayMidi, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbGoBack, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbGoForwards, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGap(10).addComponent(gridToggle, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addGap(10).addComponent(infoToggle, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addGap(164)));
		controlsPane.setLayout(gl_controlsPane);

	}

	private void addLoadMidiFileControl(JPanel controlsPane, int gridy, JLabel midiFileName) {
	}

	private void setMidiFileNameLabel(String name) {
		midiFileName.setText(name);
	}

	private JLabel addMidiFileNameLabel(JPanel controlsPane, int gridy) {
		lbLoadMidi = new JButton("Load Midi File");
		lbLoadMidi.setFont(new Font("Poppins", Font.PLAIN, 16));
		lbLoadMidi.setToolTipText("Load MIDI File");
		lbLoadMidi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Load MIDI");
				fileChooser.setFileFilter(new FileNameExtensionFilter("MIDI files", "mid", "midi"));

				// Show the file chooser dialog and check the user's choice
				int returnValue = fileChooser.showOpenDialog(LearnPanel.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					initMidiPlayer(selectedFile);
				}
			}
		});
		midiFileName_1 = new JLabel("");
		midiFileName_1.setFont(SLIDING_PANEL_FONT);
		return midiFileName_1;
	}

	private int addMidiOutListControl(JPanel controlsPane, int gridy) {
		MidiOutList = new JComboBox<Object>(pianoLED.getPianoController().getMidiOutDevices());
		MidiOutList.putClientProperty("JComponent.roundRect", true);
		MidiOutList.setFont(new Font("Poppins", Font.PLAIN, 12));
		MidiOutList.setToolTipText("MIDI Output Device");
		MidiOutList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMidiPlayerOutputDevice();
			}

		});
		return gridy;
	}

	private int addUIControls(JPanel controlsPane, int gridy) {
		gridToggle = new JCheckBox("Show Grid");
		gridToggle.setFont(SLIDING_PANEL_FONT);

		infoToggle = new JCheckBox("Tech Info");
		;
		infoToggle.setFont(SLIDING_PANEL_FONT);
		return gridy;
	}

	private void setMidiPlayerOutputDevice() {
		String deviceName = (String) MidiOutList.getSelectedItem();
		System.out.println("Midi Out Device: " + deviceName);
		try {
			if (midiPlayer != null)
				midiPlayer.setOutputDevice(getMidiDeviceByName(deviceName));
		} catch (MidiUnavailableException e1) {
			System.out.println("Error connecting to Midi Out Device: " + deviceName);
			e1.printStackTrace();
		}
	}

	private MidiDevice getMidiDeviceByName(String deviceName) {
		MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();
		for (Info info : deviceInfo) {
			if (info.getName().equalsIgnoreCase(deviceName)) {
				try {
					return MidiSystem.getMidiDevice(info);
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	private void addPlaybackControls(JPanel controlsPane, int gridy) {
		Font midiControlsFont = new Font("Dialog", Font.PLAIN, 14);

		lbStart = new JButton("⏮");
		lbStart.setBackground(new Color(52, 152, 219));
		lbStart.setForeground(Color.WHITE);
		lbStart.setFocusable(false);
		lbStart.setBorderPainted(false);
		lbStart.setToolTipText("Restart Playback");
		lbStart.setFont(midiControlsFont);
		lbStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				midiPlayer.rewind();
			}
		});

		lbGoBack = new JButton("⏪");
		lbGoBack.setBackground(new Color(52, 152, 219));
		lbGoBack.setForeground(Color.WHITE);
		lbGoBack.setFocusable(false);
		lbGoBack.setBorderPainted(false);
		lbGoBack.setToolTipText("Previous");
		lbGoBack.setFont(midiControlsFont);
		lbGoBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				midiPlayer.rewind(-PLAYER_REWIND_SEC);
			}
		});
		lbPlayMidi.setBackground(new Color(52, 152, 219));
		lbPlayMidi.setForeground(Color.WHITE);
		lbPlayMidi.setFocusable(false);
		lbPlayMidi.setBorderPainted(false);
		lbPlayMidi.setToolTipText("Play/Pause");
		lbPlayMidi.setFont(midiControlsFont);
		lbPlayMidi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				midiPlayer.playPause();
				if (midiPlayer.isPaused()) {
					setPlaybackButtonStop();
				} else {
					setPlaybackButtonPlay();
				}
			}

		});

		lbGoForwards = new JButton("⏩");
		lbGoForwards.setBackground(new Color(52, 152, 219));
		lbGoForwards.setForeground(Color.WHITE);
		lbGoForwards.setFocusable(false);
		lbGoForwards.setBorderPainted(false);
		lbGoForwards.setToolTipText("Next");
		lbGoForwards.setFont(midiControlsFont);
		lbGoForwards.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				midiPlayer.rewind(5);
			}
		});
	}

	private void setPlaybackButtonStop() {
		lbPlayMidi.setText("▶");
	}

	private void setPlaybackButtonPlay() {
		lbPlayMidi.setText("⏸");
	}

	@Override
	public void onPlaybackFinished() {
		setPlaybackButtonStop();
	}

	@Override
	public void onNoteOn(int channel, int pitch, int velocity) {
		pianoLED.getPianoController().noteOn(channel, pitch, velocity);
	}

	@Override
	public void onNoteOff(int pitch) {
		pianoLED.getPianoController().noteOff(0, pitch, 0);
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

	@Override
	public void onPianoKeyOn(int pitch, int velocity) {
		// catching commands
		if (commandKeys.containsKey(pitch) && TopPanel.learnOn) {
//			if (System.currentTimeMillis() - commandKey1Arrived < COMMAND_MAX_SPAN_MS) {
			switch (commandKeys.get(pitch)) {
			case "Back":
				midiPlayer.rewind(-PLAYER_REWIND_SEC);
				midiPlayer.muteKey(pitch);
				break;
			case "PlayPause":
				midiPlayer.playPause();
				midiPlayer.muteKey(pitch);
				break;
			case "Forward":
				midiPlayer.rewind(PLAYER_REWIND_SEC);
				midiPlayer.muteKey(pitch);
				break;
			default:
				break;
			}
//				commandKey1Arrived = 0;
//			} else if (pitch == COMMAND_KEY1) {
//				commandKey1Arrived = System.currentTimeMillis();
//			}
		}

	}

	@Override
	public void onPianoKeyOff(int pitch) {
		// TODO Auto-generated method stub

	}
}