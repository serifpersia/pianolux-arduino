import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import javax.sound.sampled.*;
import javax.swing.*;

import com.serifpersia.pianoled.ModesController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class AudioReactDemo extends JFrame {

	private static final int ANALOG_MIN_VALUE = 0;
	private static final int ANALOG_MAX_VALUE = 1023;
	private static final int AUDIO_BUFFER_SIZE = 1024; // Increased buffer size for audio data

	private JComboBox<String> deviceComboBox;
	private JComboBox<String> comPortComboBox;
	private JComboBox<String> cb_LedReactEffects;

	private JButton connectButton;
	private JButton disconnectButton;
	private JButton sendAudioButton;
	private JButton stopSendButton;

	private TargetDataLine line;
	private boolean capturing = false;
	private SerialPort serialPort = null;

	public AudioReactDemo() {
		setTitle("Audio Transformer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(400, 200));
		setLayout(new FlowLayout());

		deviceComboBox = new JComboBox<>();
		listRecordingDevices();
		add(deviceComboBox);

		cb_LedReactEffects = new JComboBox<>(new String[] { "Light Effect 0", "Light Effect 1", "Light Effect 2",
				"Light Effect 3", "Light Effect 4" });
		add(cb_LedReactEffects);

		cb_LedReactEffects.addActionListener(e -> {

			int selectedIndex = cb_LedReactEffects.getSelectedIndex();
			sendToArduinoSelectEffectIndex(selectedIndex);

		});

		comPortComboBox = new JComboBox<>();
		listSerialPorts();
		add(comPortComboBox);

		connectButton = new JButton("Start");
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedPort = (String) comPortComboBox.getSelectedItem();
				if (selectedPort != null) {
					startSerialCommunication(selectedPort);
				}
			}
		});
		add(connectButton);

		disconnectButton = new JButton("Stop");
		disconnectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopSerialCommunication();
			}
		});
		add(disconnectButton);

		sendAudioButton = new JButton("Send");
		sendAudioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedDevice = (String) deviceComboBox.getSelectedItem();
				if (selectedDevice != null) {
					Mixer.Info selectedMixerInfo = getMixerInfoByName(selectedDevice);
					startAudioCapture(selectedMixerInfo);
				}
			}
		});
		add(sendAudioButton);

		stopSendButton = new JButton("Don't Send");
		stopSendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopAudioCapture();
			}
		});
		add(stopSendButton);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void sendToArduinoSelectEffectIndex(int selectedIndex) {
		try {
			serialPort.writeInt(selectedIndex);
			System.out.println("Sending: " + selectedIndex);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void listRecordingDevices() {
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info mixerInfo : mixerInfos) {
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			for (Line.Info lineInfo : lineInfos) {
				if (lineInfo.getLineClass().equals(TargetDataLine.class)) {
					deviceComboBox.addItem(mixerInfo.getName());
					break;
				}
			}
		}
	}

	private void listSerialPorts() {
		String[] portNames = SerialPortList.getPortNames();
		for (String portName : portNames) {
			comPortComboBox.addItem(portName);
		}
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

			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 41000, 16, 1, 2, 41000, false);
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
						int audioInputValue = processAudioData(buffer, bytesRead);
						publish(audioInputValue);
					}
					return null;
				}

				@Override
				protected void process(java.util.List<Integer> chunks) {
					int audioInputValue = chunks.get(chunks.size() - 1);
					System.out.println("Audio Input Value: " + audioInputValue);
					if (audioInputValue > 0) {
						sendToArduino(audioInputValue);
					}
				}

				@Override
				protected void done() {
				}
			};

			worker.execute();

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void startSerialCommunication(String portName) {
		try {
			if (serialPort != null && serialPort.isOpened()) {
				System.out.println("Serial port is already open.");
				return;
			}

			serialPort = new SerialPort(portName);
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			System.out.println("Serial communication established with port: " + portName);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	private void stopAudioCapture() {
		capturing = false;
		if (line != null) {
			line.stop();
			line.close();
		}
	}

	private void stopSerialCommunication() {
		if (serialPort != null && serialPort.isOpened()) {
			try {
				serialPort.closePort();
				System.out.println("Serial communication closed.");
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
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


	private double map(double value, double inMin, double inMax, double outMin, double outMax) {
		return (value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}

	private void sendToArduino(int data) {
		try {
			if (serialPort != null && serialPort.isOpened()) {
				byte[] dataBytes = new byte[2];
				dataBytes[0] = (byte) (data & 0xFF);
				dataBytes[1] = (byte) ((data >> 8) & 0xFF);
				serialPort.writeBytes(dataBytes);
			}
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AudioReactDemo();
			}
		});
	}
}
