package com.serifpersia.pianolux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.serifpersia.pianolux.ui.BottomPanel;
import com.serifpersia.pianolux.ui.CenterPanel;
import com.serifpersia.pianolux.ui.DrawPiano;
import com.serifpersia.pianolux.ui.TopPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

@SuppressWarnings("serial")
public class PianoLux extends JFrame implements NativeKeyListener {

	private PianoController pianoController = new PianoController(this);
	private BottomPanel bottomPanel = new BottomPanel(this);
	private CenterPanel centerPanel = new CenterPanel(this);
	public TopPanel topPanel = new TopPanel(centerPanel, this);
	private HandleDiscordPPC handleDiscordPPC = new HandleDiscordPPC();
	static Updater updator = new Updater();

	private boolean isFullScreen = false;

	private double bottomPanelHeightPercentage = 0.15; // Default value is 15%
	private JPanel centerPanelWrapper;

	public static void main(String[] args) {
		try {
			FlatDarkLaf.setup();
			UIManager.put("Panel.background", new Color(25, 25, 25));
			UIManager.put("Button.arc", 999);
			UIManager.put("Button.foreground", new Color(204, 204, 204));
			UIManager.put("Button.hoverBackground", new Color(200, 0, 0));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(() -> {
			new PianoLux();
		});
	}

	public PianoLux() {

		// Register the PianoLux instance as a native key listener
		GlobalScreen.addNativeKeyListener(this);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}

		// Set JNativeHook's logging level to WARNING
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);

		Updater updater = new Updater();
		updater.getVersion();
		updater.deleteSetupFileAndPianoLuxvFiles();

		init();

	}

	private void init() {
		setSize(990, 575);
		setLocationRelativeTo(null);
		// setResizable(false);
		setTitle("PianoLux " + updator.VersionTag);
		setIconImage(new ImageIcon(getClass().getResource("/icons/PianoLux.png")).getImage());

		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(topPanel, BorderLayout.NORTH);

		// Add the top/bottom panels to the frame's NORTH/SOUTH region of the rightPanel
		centerPanelWrapper = new JPanel(new BorderLayout());

		centerPanelWrapper.add(centerPanel, BorderLayout.CENTER);
		centerPanelWrapper.add(bottomPanel, BorderLayout.SOUTH);
		getContentPane().add(centerPanelWrapper, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Application is closing. Releasing resources...");
				pianoController.dispose();
			}
		});

		// Add a ComponentListener to the parent JPanel to detect size changes
		centerPanelWrapper.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Calculate the new preferred size based on the parent's size and the updated
				// percentage
				Dimension parentSize = centerPanelWrapper.getSize();
				double newHeight = parentSize.height * bottomPanelHeightPercentage;

				// Update the child JPanel's preferred size and revalidate the layout
				bottomPanel.setPreferredSize(new Dimension(bottomPanel.getWidth(), (int) newHeight));
				bottomPanel.revalidate();
			}

		});

		setVisible(true);
		handleDiscordPPC.startupDiscordRPC();

	}

	public void updateBottomPanelHeightPercentage(double newPercentage) {
		bottomPanelHeightPercentage = newPercentage;

		// Calculate the new preferred size based on the parent's size and the updated
		// percentage
		Dimension parentSize = centerPanelWrapper.getSize();
		double newHeight = parentSize.height * bottomPanelHeightPercentage;

		// Update the child JPanel's preferred size and revalidate the layout
		bottomPanel.setPreferredSize(new Dimension(bottomPanel.getWidth(), (int) newHeight));
		bottomPanel.revalidate();
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

	// NativeKeyListener methods
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		// Check if the application is in focus before toggling fullscreen
		if (isActive() && e.getKeyCode() == NativeKeyEvent.VC_F) {
			toggleFullScreen();
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// Empty implementation, but required by the interface
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// Empty implementation, but required by the interface
	}

	public void toggleFullScreen() {
		if (isFullScreen) {
			// Restore the application to normal size and position
			setExtendedState(JFrame.NORMAL);
			topPanel.setVisible(true);
		} else {
			// Maximize the application to full screen
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			topPanel.setVisible(false);
		}
		isFullScreen = !isFullScreen;
	}

}
