package com.serifpersia.pianoled;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.serifpersia.pianoled.ui.BottomPanel;
import com.serifpersia.pianoled.ui.DrawPiano;
import com.serifpersia.pianoled.ui.RightPanel;
import com.serifpersia.pianoled.ui.TopPanel;

@SuppressWarnings("serial")
public class PianoLED extends JFrame {

	private PianoController pianoController = new PianoController(this);
	private BottomPanel bottomPanel = new BottomPanel(this);
	private RightPanel rightPanel = new RightPanel(this);
	public TopPanel topPanel = new TopPanel(rightPanel);

	static Updater updator = new Updater();

	public static void main(String[] args) {
		try {
			FlatDarkLaf.setup();
			UIManager.put("Panel.background", new Color(25, 25, 25));
			UIManager.put("Button.arc", 999);
			UIManager.put("Button.foreground", new Color(204, 204, 204));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(() -> {
			new PianoLED();
		});
	}

	public PianoLED() {

		Updater updater = new Updater();
		updater.getVersion();
		updater.deleteSetupFile();
		init();
	}

	private void init() {
		setSize(1004, 640);
		setLocationRelativeTo(null);
		// setResizable(false);
		setTitle("PianoLED " + updator.VersionTag);
		setIconImage(new ImageIcon(getClass().getResource("/icons/AppIcon.png")).getImage());

		// setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Add the top/bottom panels to the frame's NORTH/SOUTH region of the rightPanel
		JPanel rightPanelWrapper = new JPanel(new BorderLayout());

		rightPanelWrapper.add(rightPanel, BorderLayout.CENTER);
		rightPanelWrapper.add(bottomPanel, BorderLayout.SOUTH);
		getContentPane().add(rightPanelWrapper, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Application is closing. Releasing resources...");
				pianoController.dispose();
			}
		});

		// Add a ComponentListener to the parent JPanel to detect size changes
		rightPanelWrapper.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Calculate the new preferred size based on the parent's size
				Dimension parentSize = rightPanelWrapper.getSize();
				double newHeight = parentSize.height * 0.16;

				// Update the child JPanel's preferred size and revalidate the layout
				bottomPanel.setPreferredSize(new Dimension(bottomPanel.getWidth(), (int) newHeight));
				bottomPanel.revalidate();
			}
		});

		setVisible(true);

		add(topPanel, BorderLayout.NORTH);

	}

	public PianoController getPianoController() {
		return this.pianoController;
	}

	public DrawPiano getDrawPiano() {
		return bottomPanel.getPiano();
	}

	public void setPianoKey(int pitch, int on) {
		bottomPanel.getPiano().setPianoKey(pitch, on);
	}

}