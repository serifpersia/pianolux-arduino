package com.serifpersia.pianoled;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.serifpersia.pianoled.ui.BottomPanel;
import com.serifpersia.pianoled.ui.DrawPiano;
import com.serifpersia.pianoled.ui.LeftPanel;
import com.serifpersia.pianoled.ui.RightPanel;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class PianoLED extends JFrame {

	private PianoController pianoController = new PianoController(this);
	private BottomPanel bottomPanel = new BottomPanel(this);
	private RightPanel rightPanel = new RightPanel(this);
	private LeftPanel leftPanel = new LeftPanel(rightPanel);

	static Updater updator = new Updater();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(() -> {
			new PianoLED();
		});
	}

	public PianoLED() {
		init();
	}

	private void init() {
		setSize(950, 650);
		setLocationRelativeTo(null);
		setTitle("PianoLED " + updator.VersionTag);
		setIconImage(new ImageIcon(getClass().getResource("/icons/PianoLED.png")).getImage());

		// setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add key listener to panel
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Check if the pressed key is ESC
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					// Set the visibility of the panel to false
					System.exit(0);
				}
			}
		});

		// qgetContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(leftPanel, BorderLayout.WEST);

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