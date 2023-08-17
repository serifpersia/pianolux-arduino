package com.serifpersia.pianoled.ui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.serifpersia.pianoled.ModesController;
import com.serifpersia.pianoled.PianoController;
import com.serifpersia.pianoled.PianoLED;
import jAudioFeatureExtractor.jAudioTools.FFT;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class pnl_AudioReact extends JPanel {

	private static final int FREQ_HIGHEST = 2000;
	private static final int FREQ_LOWEST = 100;
	private static final int SAMPLE_RATE = 41000;
	private static final int ANALOG_MIN_VALUE = 0;
	private static final int ANALOG_MAX_VALUE = 1023;
	private static final double SCALING_FACTOR = (ANALOG_MAX_VALUE - ANALOG_MIN_VALUE) / (double) Short.MAX_VALUE;
	private int AUDIO_BUFFER_SIZE = 2048; // Increased buffer size for audio data

	private TargetDataLine line;
	private boolean capturing = false;

	private PianoController pianoController;

	public static JComboBox<?> cb_AudioReactLEDEffect;
	private JComboBox<String> cb_AudioDevice;
	private JButton btnStart;;

	private BlockingQueue<Integer> audioQueue = new LinkedBlockingQueue<>(); // Added aud

	static int defaultFadeVal = 200;

	static int defaultResponseVal = 2048;
	static int defaultFreqBandsVal = 16;

	private JSlider sld_Fade;
	private JSlider sld_Response;
	private JSlider sld_FreqBands;
	private JButton btnChangeHue;
	private double[] freqs;
//	int numBuckets = 16;

	public pnl_AudioReact(PianoLED pianoLED) {
		setBackground(new Color(50, 50, 50));

		pianoController = pianoLED.getPianoController();
		new ModesController(pianoLED);

		init();
		populateAudioInputDevices();
		buttonActions();
		sliderActions();
	}

	private void drawFreq(Graphics g, double[] freqsToDraw) {
		if (freqsToDraw == null || freqsToDraw.length == 0)
			return;

		int clipHeight = g.getClip().getBounds().height;
		int clipWidth = g.getClip().getBounds().width;
		int barWidth = clipWidth / freqsToDraw.length;
		if (barWidth == 0)
			barWidth = 1;
		
		int spacing = 3;
		if( barWidth*freqsToDraw.length > clipWidth)
			spacing = 1;
		
		for (int i = 0; i < freqsToDraw.length; i++) {
			double barHeight = freqsToDraw[i] / SCALING_FACTOR * clipHeight;
			int x = (int) (i * (barWidth + spacing));
			int y =  (int) (clipHeight - barHeight);
			g.setColor(Color.RED);
			g.fillRect(x, y, barWidth, (int) barHeight);
		}

	}

	private void init() {
		setBorder(new EmptyBorder(10, 10, 10, 10));

		setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(50, 50, 50));
		add(panel);
		panel.setLayout(new GridLayout(6, 0, 0, 0));

		JPanel freqPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawFreq(g, freqs);
			}
		};
		freqPanel.setBackground(Color.BLACK);
		add(freqPanel);

		Timer timer = new Timer(8, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				freqPanel.repaint();
			}
		});
		timer.start();

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel_1.setBackground(new Color(50, 50, 50));
		panel.add(panel_1);

		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblAudioDevice = new JLabel("Audio Device");
		lblAudioDevice.setHorizontalAlignment(SwingConstants.CENTER);
		lblAudioDevice.setForeground(new Color(208, 208, 208));
		lblAudioDevice.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_1.add(lblAudioDevice);

		cb_AudioDevice = new JComboBox<String>();
		cb_AudioDevice.putClientProperty("JComponent.roundRect", true);
		cb_AudioDevice.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_1.add(cb_AudioDevice);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel_2.setBackground(new Color(50, 50, 50));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblReactLedEffect_1 = new JLabel("React LED Effect");
		lblReactLedEffect_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblReactLedEffect_1.setForeground(new Color(208, 208, 208));
		lblReactLedEffect_1.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_2.add(lblReactLedEffect_1);

		cb_AudioReactLEDEffect = new JComboBox<Object>(GetUI.ledVisualizerEffectsName.toArray(new String[0]));
		cb_AudioReactLEDEffect.putClientProperty("JComponent.roundRect", true);
		cb_AudioReactLEDEffect.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_2.add(cb_AudioReactLEDEffect);

		cb_AudioReactLEDEffect.addActionListener(e -> {
			if (ModesController.VisualizerOn) {
				int selectedIndex = cb_AudioReactLEDEffect.getSelectedIndex();
				pianoController.setLedVisualizerEffect(selectedIndex, 0);
			}
		});

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(50, 50, 50));
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lb_Fade = new JLabel("Fade");
		lb_Fade.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Fade.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_3.add(lb_Fade);

		sld_Fade = new JSlider(0, 255, defaultFadeVal);
		sld_Fade.setBackground(new Color(77, 77, 77));
		sld_Fade.setForeground(new Color(128, 128, 128));
		panel_3.add(sld_Fade);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(50, 50, 50));
		panel.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lb_Response = new JLabel("Response");
		lb_Response.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Response.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_4.add(lb_Response);

		sld_Response = new JSlider(128, 4096, defaultResponseVal);
		sld_Response.setBackground(new Color(77, 77, 77));
		sld_Response.setForeground(new Color(128, 128, 128));
		sld_Response.setSnapToTicks(true);
		sld_Response.setMajorTickSpacing(128);
		panel_4.add(sld_Response);

		JPanel panel_fb = new JPanel();
		panel_fb.setBackground(new Color(50, 50, 50));
		panel.add(panel_fb);
		panel_fb.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lb_FreqBands = new JLabel("Freq Bands");
		lb_FreqBands.setHorizontalAlignment(SwingConstants.CENTER);
		lb_FreqBands.setFont(new Font("Poppins", Font.PLAIN, 21));
		panel_fb.add(lb_FreqBands);

		sld_FreqBands = new JSlider(1, 128, defaultFreqBandsVal);
		sld_FreqBands.setBackground(new Color(77, 77, 77));
		sld_FreqBands.setForeground(new Color(128, 128, 128));
		sld_Response.setMajorTickSpacing(32);
		panel_fb.add(sld_FreqBands);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		panel_5.setBackground(new Color(50, 50, 50));
		panel.add(panel_5);

		btnChangeHue = new JButton("Change Hue");
		btnChangeHue.setFont(new Font("Poppins", Font.PLAIN, 24));
		btnChangeHue.setFocusable(false);
		panel_5.add(btnChangeHue);

		btnStart = new JButton("Start");
		btnStart.setFont(new Font("Poppins", Font.PLAIN, 24));
		btnStart.setBackground(new Color(231, 76, 60));
		panel_5.add(btnStart);

	}

	private void sliderActions() {
		ChangeListener sliderChangeListener = new ChangeListener() {
			Timer buttonClickTimer = new Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < 2; i++) {
						btnStart.doClick();
					}
					buttonClickTimer.stop();
				}
			});

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (source == sld_Fade) {
					int FadeVal = 255 - sld_Fade.getValue();
					sld_Fade.setToolTipText(Integer.toString(FadeVal));
					pianoController.FadeRate(FadeVal);
				} else if (source == sld_Response) {
					int ResponseVal = 4224 - sld_Response.getValue();
					sld_Response.setToolTipText(Integer.toString(ResponseVal));
					AUDIO_BUFFER_SIZE = ResponseVal;
					// Restart the timer every time the slider is changed
					buttonClickTimer.restart();
				}
			}
		};

		sld_Response.addChangeListener(sliderChangeListener);
		sld_Fade.addChangeListener(sliderChangeListener);
	}

	private void populateAudioInputDevices() {

		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info mixerInfo : mixerInfos) {
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			for (Line.Info lineInfo : lineInfos) {
				if (lineInfo.getLineClass().equals(TargetDataLine.class)) {
					cb_AudioDevice.addItem(mixerInfo.getName());
					break;
				}
			}
		}

	}

	private void buttonActions() {
		ActionListener buttonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {

				case "btnStart":
					if (btnStart.getText().equals("Start")) {

						String selectedDevice = (String) cb_AudioDevice.getSelectedItem();
						// String selectedPort = (String) comPortComboBox.getSelectedItem();
						if (selectedDevice != null) {
							Mixer.Info selectedMixerInfo = getMixerInfoByName(selectedDevice);
							startAudioCapture(selectedMixerInfo);
						}

						btnStart.setBackground(new Color(46, 204, 113));
						btnStart.setForeground(Color.WHITE);
						btnStart.setText("Close");

					} else {

						stopAudioCapture();

						btnStart.setBackground(new Color(231, 76, 60));
						btnStart.setText("Start");
					}
					break;

				case "btnChangeHue":
					float[] hsb = Color.RGBtoHSB(GetUI.selectedColor.getRed(), GetUI.selectedColor.getGreen(),
							GetUI.selectedColor.getBlue(), null);

					// Convert the hue value (hsb[0]) from float [0, 1] to int [0, 255]
					int hueInt = (int) (hsb[0] * 255);

					// Call the function with the integer hue value
					pianoController.setLedVisualizerEffect(0, hueInt);
					System.out.println(hueInt);
					break;

				default:
					break;
				}
			}
		};

		btnChangeHue.addActionListener(buttonListener);
		btnStart.addActionListener(buttonListener);

		btnChangeHue.setActionCommand("btnChangeHue");
		btnStart.setActionCommand("btnStart");

	}

	private Mixer.Info getMixerInfoByName(String name) {
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info mixerInfo : mixerInfos) {
			if (mixerInfo.getName().equals(name)) {
				return mixerInfo;
			}
		}
		return null;
	}

	private void startAudioCapture(Mixer.Info selectedMixerInfo) {
		try {

			if (capturing) {
				System.out.println("Already capturing audio.");
				return;
			}

			Mixer mixer = AudioSystem.getMixer(selectedMixerInfo);
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			line = null;
			for (Line.Info lineInfo : lineInfos) {
				if (lineInfo.getLineClass().equals(TargetDataLine.class)) {
					line = (TargetDataLine) mixer.getLine(lineInfo);
					break;
				}
			}

			if (line == null) {
				System.err.println("Line not supported");
				return;
			}

			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLE_RATE, 8, 1, 1, 41000, false); // 16
																													// kHz,
																													// 8
																													// bits
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				System.err.println("Line not supported");
				return;
			}

			line.open(format, AUDIO_BUFFER_SIZE); // Set the buffer size for the audio line
			line.start();
			capturing = true;

			System.out.println("Listening with Device: " + selectedMixerInfo.getName());

			SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
				@Override
				protected Void doInBackground() throws Exception {
					byte[] buffer = new byte[AUDIO_BUFFER_SIZE];
					int bytesRead;

					while (capturing) {
						bytesRead = line.read(buffer, 0, buffer.length);
						FFT fft = new FFT(formatAudioData(buffer.clone(), bytesRead), null, false, true);

						double[] magnitudes = fft.getMagnitudeSpectrum();
						double[] bins = fft.getBinLabels(SAMPLE_RATE); // the sample rate used is
						double[] filteredFreqs = filterFreqs(magnitudes, bins);
						double[] freqBuckets = putMagToBuckets(filteredFreqs, sld_FreqBands.getValue());
						setFreq(freqBuckets);

						int audioInputValue = processAudioData(buffer, bytesRead);
						if (audioInputValue > 4)
							audioQueue.put(audioInputValue); // Put audio data into the queue
					}
					return null;
				}

				private double[] filterFreqs(double[] magnitudes, double[] bins) {
					int lowFreq = -1;
					for (int i = 0; i < bins.length; i++) {
						if(lowFreq == -1 && bins[i] > FREQ_LOWEST)
						{
							lowFreq = i;
						}
						
						if(bins[i] > FREQ_HIGHEST)
						{
							return Arrays.copyOfRange(magnitudes, lowFreq, i);
						}
					}
					return magnitudes;
				}

				private double[] putMagToBuckets(double[] magnitudes, int numBuckets) {
					if( numBuckets > magnitudes.length )
						numBuckets = magnitudes.length;
					double[] buckets = new double[numBuckets];
					double bucketValue = 0.;

					int avgBucketSize = magnitudes.length / numBuckets;
					int bucketNum = 0;
					int bucketSize = 0;
					for (int i = 0; i < magnitudes.length; i++) {
						bucketValue += magnitudes[i] * magnitudes[i];
						bucketSize++; 
						if (i == magnitudes.length - 1
								|| (i > 0 && (i % avgBucketSize == 0) && bucketNum != (numBuckets - 1))) {
							buckets[bucketNum++] = Math.sqrt(bucketValue/bucketSize);
							bucketValue = 0.;
							bucketSize = 0;
						}
					}
					return buckets;
				}

				@Override
				protected void done() {
				}
			};

			worker.execute();

			// Create a separate thread for non-blocking serial communication
			new Thread(() -> {
				while (capturing) {
					try {
						int audioValue = audioQueue.take(); // Take audio data from the queue
						if (audioValue > 3) {
							pianoController.sendAudioDataToArduino(audioValue);
						}

					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						e.printStackTrace();
					}
				}
			}).start();

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	protected void setFreq(double[] freqBuckets) {
		this.freqs = freqBuckets;

	}

	private double[] formatAudioData(byte[] audioData, int bytesRead) {
		int numSamples = bytesRead / 2; // Each sample is 16 bits (2 bytes)
		double[] samples = new double[numSamples];

		for (int i = 0; i < numSamples; i++) {
			short sample = (short) ((audioData[i * 2 + 1] << 8) | audioData[i * 2]);
			samples[i] = sample / (double) Short.MAX_VALUE;
		}

		return samples;
	}

	private int processAudioData(byte[] audioData, int bytesRead) {
		long sum = 0;

		for (int i = 0; i < bytesRead - 1; i += 2) {
			short sample = (short) ((audioData[i + 1] << 8) | audioData[i]);
			sum += sample * sample;
		}

		double rms = Math.sqrt(sum / (bytesRead / 2));
		

		return ANALOG_MIN_VALUE + (int) (SCALING_FACTOR * rms);
	}

	private void stopAudioCapture() {
		capturing = false;
		if (line != null) {
			line.stop();
			line.close();
		}
	}
}
