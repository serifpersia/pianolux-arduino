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
import com.serifpersia.pianoled.ui.LeftPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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

@SuppressWarnings("serial")
public class LearnPanel extends JPanel implements MidiPlayerConsumer, PianoMidiConsumer {

	private static final String DEFAULT_MIDI_RESOURCE = "/midi/clair_de_lune.mid";
	private static final int PLAYER_REWIND_SEC = 5;
	private static final Font SLIDING_PANEL_FONT = new Font("Tahoma", Font.BOLD, 16);
	private static final Color SLIDING_PANEL_BG = Color.BLACK;
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

	public LearnPanel(PianoLED pianoLED) {
		this.pianoLED = pianoLED;
		setBackground(SLIDING_PANEL_BG);
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

		midiFileName = addMidiFileNameLabel(controlsPane, 1);
		addLoadMidiFileControl(controlsPane, 0, midiFileName);
		addMidiOutListControl(controlsPane, 2);
		addPlaybackControls(controlsPane, 3);
		addUIControls(controlsPane, 3);

	}

	private void addLoadMidiFileControl(JPanel controlsPane, int gridy, JLabel midiFileName) {
	}

	private void setMidiFileNameLabel(String name) {
		midiFileName.setText(name);
	}

	private JLabel addMidiFileNameLabel(JPanel controlsPane, int gridy) {
		JButton lbLoadMidi = new JButton("Load Midi File");
		GridBagConstraints gbc_lbLoadMidi = new GridBagConstraints();
		lbLoadMidi.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbLoadMidi.setBackground(new Color(231, 76, 60));
		lbLoadMidi.setForeground(Color.WHITE);
		lbLoadMidi.setFocusable(false);
		lbLoadMidi.setBorderPainted(false);
		lbLoadMidi.setToolTipText("Load MIDI File");
		gbc_lbLoadMidi.gridwidth = 4;
		gbc_lbLoadMidi.insets = new Insets(0, 0, 5, 0);
		gbc_lbLoadMidi.gridx = 0;
		gbc_lbLoadMidi.gridy = 1;
		controlsPane.add(lbLoadMidi, gbc_lbLoadMidi);
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
		JLabel midiFileName = new JLabel("");
		GridBagConstraints gbc_lbMidiFileName = new GridBagConstraints();
		gbc_lbMidiFileName.gridwidth = 4;
		gbc_lbMidiFileName.insets = new Insets(0, 0, 5, 0);
		gbc_lbMidiFileName.gridx = 0;
		gbc_lbMidiFileName.gridy = 2;
		midiFileName.setFont(SLIDING_PANEL_FONT);
		midiFileName.setForeground(Color.WHITE);
		controlsPane.add(midiFileName, gbc_lbMidiFileName);
		return midiFileName;
	}

	private int addMidiOutListControl(JPanel controlsPane, int gridy) {
		MidiOutList = new JComboBox<Object>(pianoLED.getPianoController().getMidiOutDevices());
		MidiOutList.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		MidiOutList.setBackground(new Color(255, 255, 255));
		MidiOutList.setForeground(new Color(0, 0, 0));
		MidiOutList.setFocusable(false);
		MidiOutList.setToolTipText("MIDI Output Device");
		gbc_comboBox.gridwidth = 4;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 3;
		controlsPane.add(MidiOutList, gbc_comboBox);
		MidiOutList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMidiPlayerOutputDevice();
			}

		});
		return gridy;
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
		Font midiControlsFont = new Font("Symbola", Font.BOLD, 16);

		JButton lbStart = new JButton("⏮");
		GridBagConstraints gbc_lbStart = new GridBagConstraints();
		lbStart.setBackground(new Color(52, 152, 219));
		lbStart.setForeground(Color.WHITE);
		lbStart.setFocusable(false);
		lbStart.setBorderPainted(false);
		lbStart.setToolTipText("Restart Playback");
		gbc_lbStart.anchor = GridBagConstraints.NORTH;
		gbc_lbStart.insets = new Insets(0, 0, 5, 5);
		gbc_lbStart.gridx = 0;
		gbc_lbStart.gridy = 4;
		lbStart.setFont(midiControlsFont);
		controlsPane.add(lbStart, gbc_lbStart);
		lbStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				midiPlayer.rewind();
			}
		});

		JButton lbGoBack = new JButton("⏪");
		GridBagConstraints gbc_lbGoBack = new GridBagConstraints();
		lbGoBack.setBackground(new Color(52, 152, 219));
		lbGoBack.setForeground(Color.WHITE);
		lbGoBack.setFocusable(false);
		lbGoBack.setBorderPainted(false);
		lbGoBack.setToolTipText("Previous");
		gbc_lbGoBack.anchor = GridBagConstraints.NORTH;
		gbc_lbGoBack.insets = new Insets(0, 0, 5, 5);
		gbc_lbGoBack.gridx = 1;
		gbc_lbGoBack.gridy = 4;
		lbGoBack.setFont(midiControlsFont);
		controlsPane.add(lbGoBack, gbc_lbGoBack);
		lbGoBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				midiPlayer.rewind(-PLAYER_REWIND_SEC);
			}
		});

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
				midiPlayer.playPause();
				if (midiPlayer.isPaused()) {
					setPlaybackButtonStop();
				} else {
					setPlaybackButtonPlay();
				}
			}

		});

		JButton lbGoForwards = new JButton("⏩");
		GridBagConstraints gbc_lbGoForwards = new GridBagConstraints();
		lbGoForwards.setBackground(new Color(52, 152, 219));
		lbGoForwards.setForeground(Color.WHITE);
		lbGoForwards.setFocusable(false);
		lbGoForwards.setBorderPainted(false);
		lbGoForwards.setToolTipText("Next");
		gbc_lbGoForwards.anchor = GridBagConstraints.NORTH;
		gbc_lbGoForwards.insets = new Insets(0, 0, 5, 0);
		gbc_lbGoForwards.gridx = 3;
		gbc_lbGoForwards.gridy = 4;
		lbGoForwards.setFont(midiControlsFont);
		controlsPane.add(lbGoForwards, gbc_lbGoForwards);
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
		if (commandKeys.containsKey(pitch) && LeftPanel.learnOn) {
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