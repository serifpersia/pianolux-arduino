package com.serifpersia.pianoled.ui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.serifpersia.pianoled.ModesController;
import com.serifpersia.pianoled.PianoController;
import com.serifpersia.pianoled.PianoLED;
import jAudioFeatureExtractor.jAudioTools.FFT;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class pnl_AudioReact extends JPanel {

	private static final int SAMPLE_RATE = 4000;
	private static final int ANALOG_MIN_VALUE = 0;
	private static final int ANALOG_MAX_VALUE = 1023;
	private static final int AUDIO_BUFFER_SIZE = 256; // Increased buffer size for audio data

	private TargetDataLine line;
	private boolean capturing = false;

	private PianoController pianoController;

	public static JComboBox<?> cb_AudioReactLEDEffect;
	private JComboBox<String> cb_AudioDevice;
	private JButton btnStart;;

	private BlockingQueue<Integer> audioQueue = new LinkedBlockingQueue<>(); // Added aud

	public pnl_AudioReact(PianoLED pianoLED) {
		setBackground(new Color(50, 50, 50));

		pianoController = pianoLED.getPianoController();
		new ModesController(pianoLED);

		init();
		populateAudioInputDevices();
		buttonActions();

	}

	private void init() {
		setBorder(new EmptyBorder(10, 10, 10, 10));

		setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(50, 50, 50));
		add(panel);
		panel.setLayout(new GridLayout(3, 0, 0, 0));

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
				pianoController.setLedVisualizerEffect(selectedIndex);
			}
		});

		btnStart = new JButton("Start");
		btnStart.setFont(new Font("Poppins", Font.PLAIN, 21));
		btnStart.setBackground(new Color(231, 76, 60));
		panel.add(btnStart);
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
				default:
					break;
				}
			}
		};

		btnStart.addActionListener(buttonListener);

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

			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLE_RATE, 8, 1, 1, 16000, false);
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
						FFT fft = new FFT(formatAudioData(buffer, bytesRead), null, false, true);

						double[] magnitudes = fft.getMagnitudeSpectrum();
						double[] magBuckets = putMagToBuckets(magnitudes, 3);
						double[] bins = fft.getBinLabels(SAMPLE_RATE); // the sample rate used is required for frequency bins
						
						if( !isSilent(magnitudes))
							System.out.println("Mags: "+magBuckets[0]+" "+magBuckets[1]+" "+magBuckets[2]);


						int audioInputValue = processAudioData(buffer, bytesRead);
						if (audioInputValue > 4)
							audioQueue.put(audioInputValue); // Put audio data into the queue
					}
					return null;
				}

				private boolean isSilent(double[] magnitudes) {
					for (int i = 0; i < magnitudes.length; i++) {
						if( magnitudes[i] > 0.01 )
							return false;
					}
					return true;
				}

				private double[] putMagToBuckets(double[] magnitudes, int numBuckets) {
					double[] buckets = new double[numBuckets];
					double bucketValue = 0.;
					
					int bucketSize = magnitudes.length/numBuckets;
					int bucketNum = 0; 
					for( int i = 0; i < magnitudes.length; i++ )
					{
						bucketValue += magnitudes[i] * magnitudes[i];
						if( i == magnitudes.length-1 || ( i > 0 && (i % bucketSize == 0)))
						{
							buckets[bucketNum++] = Math.sqrt(bucketValue);
							bucketValue = 0.;
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
						if (audioValue > 3)
							pianoController.sendAudioDataToArduino(audioValue);
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
		double scalingFactor = (ANALOG_MAX_VALUE - ANALOG_MIN_VALUE) / (double) Short.MAX_VALUE;

		return ANALOG_MIN_VALUE + (int) (scalingFactor * rms);
	}

	private void stopAudioCapture() {
		capturing = false;
		if (line != null) {
			line.stop();
			line.close();
		}
	}
}
