package ui;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.serifpersia.pianoled.PianoController;

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
public class LearnPanel extends JPanel {

	public static JComboBox<?> MidiOutList;

	public LearnPanel() {
		setBackground(new Color(21, 25, 28));
		setLayout(new BorderLayout(0, 0));

		JPanel slideControlsPane = new JPanel();
		slideControlsPane.setBackground(Color.RED);
		slideControlsPane.setVisible(false);
		add(slideControlsPane, BorderLayout.EAST);
		slideControlsPane.setLayout(new BorderLayout(0, 0));
		// Add mouse listener to slideControlsPane
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int width = getWidth();
				if (width - x <= 150) {
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
		lbControls.setForeground(Color.WHITE);
		lbControls.setFont(new Font("Tahoma", Font.BOLD, 40));
		slideControlsPane.add(lbControls, BorderLayout.NORTH);

		JPanel controlsPane = new JPanel();
		controlsPane.setBackground(Color.BLACK);
		slideControlsPane.add(controlsPane, BorderLayout.EAST);
		GridBagLayout gbl_controlsPane = new GridBagLayout();
		gbl_controlsPane.columnWidths = new int[] { 25, 0, 0, 0, 0 };
		gbl_controlsPane.rowHeights = new int[] { 209, 0, 0, 209, 209, 0 };
		gbl_controlsPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_controlsPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		controlsPane.setLayout(gbl_controlsPane);

		JButton lbLoadMidi = new JButton("Load Midi File");
		GridBagConstraints gbc_lbLoadMidi = new GridBagConstraints();
		gbc_lbLoadMidi.gridwidth = 4;
		gbc_lbLoadMidi.insets = new Insets(0, 0, 5, 0);
		gbc_lbLoadMidi.gridx = 0;
		gbc_lbLoadMidi.gridy = 0;
		controlsPane.add(lbLoadMidi, gbc_lbLoadMidi);

		JButton lbStart = new JButton("Start");
		lbStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		MidiOutList = new JComboBox<Object>(PianoController.getMidiOutDevices());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 4;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		controlsPane.add(MidiOutList, gbc_comboBox);
		MidiOutList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Midi Out Device: " + MidiOutList.getSelectedItem());
			}
		});

		GridBagConstraints gbc_lbStart = new GridBagConstraints();
		gbc_lbStart.anchor = GridBagConstraints.NORTHEAST;
		gbc_lbStart.insets = new Insets(0, 0, 5, 5);
		gbc_lbStart.gridx = 0;
		gbc_lbStart.gridy = 3;
		controlsPane.add(lbStart, gbc_lbStart);

		JButton lbGoBack = new JButton("-3");
		GridBagConstraints gbc_lbGoBack = new GridBagConstraints();
		gbc_lbGoBack.anchor = GridBagConstraints.NORTH;
		gbc_lbGoBack.insets = new Insets(0, 0, 5, 5);
		gbc_lbGoBack.gridx = 1;
		gbc_lbGoBack.gridy = 3;
		controlsPane.add(lbGoBack, gbc_lbGoBack);

		JButton lbPlayMidi = new JButton(">");
		GridBagConstraints gbc_lbPlayMidi = new GridBagConstraints();
		gbc_lbPlayMidi.anchor = GridBagConstraints.NORTH;
		gbc_lbPlayMidi.insets = new Insets(0, 0, 5, 5);
		gbc_lbPlayMidi.gridx = 2;
		gbc_lbPlayMidi.gridy = 3;
		controlsPane.add(lbPlayMidi, gbc_lbPlayMidi);

		JButton lbGoForwards = new JButton("+3");
		GridBagConstraints gbc_lbGoForwards = new GridBagConstraints();
		gbc_lbGoForwards.anchor = GridBagConstraints.NORTH;
		gbc_lbGoForwards.insets = new Insets(0, 0, 5, 0);
		gbc_lbGoForwards.gridx = 3;
		gbc_lbGoForwards.gridy = 3;
		controlsPane.add(lbGoForwards, gbc_lbGoForwards);

	}
}