package com.serifpersia.pianoled;

import java.io.File;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;

public class HandleDiscordPPC {

	private Core discordCore;
	private Thread discordThread;
	private Timer imageUpdateTimer;
	private String[] imageKeys = { "base", "red", "green", "blue" };
	private int currentImageIndex = 0;
	private Activity activity;

	public void startupDiscordRPC() {

		// Initialize the Core
		Core.init(new File("discordRPC/lib/x86_64/discord_game_sdk.dll"));

		// Set parameters for the Core
		try (CreateParams params = new CreateParams()) {
			params.setClientID(1159541420051415070L);
			params.setFlags(CreateParams.getDefaultFlags());

			// Create the Core
			discordCore = new Core(params);

			// Create the initial Activity
			activity = new Activity();
			activity.setDetails("Playing Piano");
			activity.setState("with LEDs");
			activity.timestamps().setStart(Instant.now()); // Set the start time

			discordCore.activityManager().updateActivity(activity);

			// Create a thread to run Discord SDK callbacks
			discordThread = new Thread(() -> {
				// Run callbacks forever
				while (true) {
					discordCore.runCallbacks();
					try {
						// Sleep a bit to save CPU
						Thread.sleep(16);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});

			// Start the Discord thread
			discordThread.start();

			// Create a timer to update the large image every 100ms
			imageUpdateTimer = new Timer();
			imageUpdateTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					updateLargeImage();
				}
			}, 0, 150);
		}
	}

	private void updateLargeImage() {
		// Update the large image key
		String currentImageKey = imageKeys[currentImageIndex];
		activity.assets().setLargeImage(currentImageKey);

		// Update the current activity to apply the image change
		discordCore.activityManager().updateActivity(activity);

		// Increment the image index, loop back to the beginning if necessary
		currentImageIndex = (currentImageIndex + 1) % imageKeys.length;
	}
}
