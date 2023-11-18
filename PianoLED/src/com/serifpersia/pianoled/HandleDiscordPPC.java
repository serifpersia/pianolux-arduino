package com.serifpersia.pianoled;

import java.time.Instant;
import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityButton;
import de.jcm.discordgamesdk.activity.ActivityButtonsMode;

public class HandleDiscordPPC {

	private Core discordCore;
	private Activity activity;
	
	//Set to true to use discord rich presence loop
	private boolean useDiscord = false;

	public void startupDiscordRPC() {

		// Initialize the Core
		if (useDiscord) {
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
				activity.assets().setLargeImage("pianoled");
				activity.timestamps().setStart(Instant.now()); // Set the start time
				// Create a custom button with the desired label and URL
				ActivityButton socialsButton = new ActivityButton("Try PianoLED!", "https://linktr.ee/serifpersia");

				activity.addButton(socialsButton);

				// Set the button display mode to custom buttons
				activity.setActivityButtonsMode(ActivityButtonsMode.BUTTONS);

				discordCore.activityManager().updateActivity(activity);

				new Thread(() -> {
					// Run callbacks forever
					while (true) {
						discordCore.runCallbacks();
						try {
							// Sleep a bit to save CPU
							Thread.sleep(20000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});

			}
		}
	}
}
