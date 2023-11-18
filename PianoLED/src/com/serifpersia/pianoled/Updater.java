package com.serifpersia.pianoled;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Iterator;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.serifpersia.pianoled.ui.DashboardPanel;

public class Updater {
	String owner = "serifpersia";
	String repo = "pianoled-arduino";
	String fileName;
	String downloadUrl;
	String saveDir = System.getProperty("user.dir") + "/";
	String destinationFolderPath = System.getProperty("user.dir") + "/";
	String appPath = System.getProperty("user.dir");
	String os = System.getProperty("os.name").toLowerCase();

	private boolean debugJSONOff = false;
	public String VersionTag = "v4.3.5";

	public String getDownloadUrl(JsonNode latestRelease, String fileName) throws IOException {
		return latestRelease.findValue("browser_download_url").asText();
	}

	File folder = new File(appPath);
	File[] listOfFiles = folder.listFiles();

	public void getVersion() {
		String apiUrl = String.format("https://api.github.com/repos/%s/%s/releases/latest", owner, repo);
		if (!debugJSONOff) {
			try {
				// Use Jackson JSON parser to parse the JSON response
				URL url = new URL(apiUrl);
				JsonMapper mapper = new JsonMapper();
				JsonNode rootNode = mapper.readTree(url);

				String latestReleaseTag = rootNode.path("tag_name").asText();

				String labelText = "<html>Current Version: " + VersionTag + "<br/>Latest Version: " + latestReleaseTag
						+ "</html>";
				DashboardPanel.lb_Version.setText(labelText);

				System.out.println(latestReleaseTag);
			} catch (JsonProcessingException e) {
				JOptionPane.showMessageDialog(null, "Error occurred while parsing JSON response.", "Version",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (SocketTimeoutException e) {
				JOptionPane.showMessageDialog(null, "Connection to GitHub API timed out.", "Version",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, "No network connection available to get latest Version.", "Version",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error occurred while checking the latest version.", "Version",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	public void getUpdate() {

		int choice = JOptionPane.showConfirmDialog(null, "Do you want to update?", "Update", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			checkForUpdates();
		}

	}

	private void checkForUpdates() {
		String apiUrl = String.format("https://api.github.com/repos/%s/%s/releases/latest", owner, repo);
		try {
			// Use Jackson JSON parser to parse the JSON response
			URL url = new URL(apiUrl);
			JsonMapper mapper = new JsonMapper();
			JsonNode rootNode = mapper.readTree(url);

			String latestReleaseTag = rootNode.path("tag_name").asText();

			// Compare the latest release tag with the local version tag
			if (VersionTag != null && VersionTag.equals(latestReleaseTag)) {
				JOptionPane.showMessageDialog(null, "You already have the latest version.", "Update",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Prompt user to download update
			int choice = JOptionPane.showConfirmDialog(null, "New update available. Do you want to install it?",
					"Download Update", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (choice == JOptionPane.YES_OPTION) {

				downloadAndInstallUpdate();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error occurred while checking for updates.", "Update",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void downloadAndInstallUpdate() {
		String apiUrl = String.format("https://api.github.com/repos/%s/%s/releases/latest", owner, repo);

		try {
			// Use Jackson JSON parser to parse the JSON response
			URL url = new URL(apiUrl);
			JsonMapper mapper = new JsonMapper();
			JsonNode rootNode = mapper.readTree(url);

			// Get the assets array from the JSON response
			JsonNode assetsNode = rootNode.path("assets");

			// Check operating system and set download file name
			String osName = System.getProperty("os.name").toLowerCase();
			String downloadFileName = osName.contains("win") ? "PianoLED_Setup.exe" : "PianoLED-linux-amd64.zip";

			// Iterate over the assets and download the appropriate file
			Iterator<JsonNode> it = assetsNode.elements();
			while (it.hasNext()) {
				JsonNode asset = it.next();
				String fileName = asset.path("name").asText();

				if (!fileName.equals(downloadFileName)) {
					continue;
				}

				// Download file
				URL fileUrl = new URL(asset.path("browser_download_url").asText());
				URLConnection conn = fileUrl.openConnection();
				conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
				conn.setRequestProperty("User-Agent", "Java");
				int contentLength = conn.getContentLength();

				// Create progress bar
				JProgressBar progressBar = new JProgressBar();
				progressBar.setStringPainted(true);

				// Create dialog to show progress bar
				JDialog dialog = new JDialog();
				dialog.add(progressBar);
				dialog.setTitle("Downloading update...");
				dialog.setSize(300, 75);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);

				// Download file in a separate thread
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
						FileOutputStream fileOutputStream = new FileOutputStream(downloadFileName);

						byte[] dataBuffer = new byte[1024];
						int bytesRead;
						int totalBytesRead = 0;
						while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
							fileOutputStream.write(dataBuffer, 0, bytesRead);
							totalBytesRead += bytesRead;
							int percentCompleted = (int) ((totalBytesRead / (float) contentLength) * 100);

							// Update progress bar on EDT
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									progressBar.setValue(percentCompleted);
								}
							});
						}

						fileOutputStream.close();
						in.close();

						return null;
					}

					@Override
					protected void done() {
						installFile(saveDir + downloadFileName, destinationFolderPath);
						dialog.dispose();
						String Message = "Update Downloaded!"
								+ " Install the new version of PianoLED. Downloaded setup installer will be deleted the next time you launch PianoLED!"
								+ " Upload Arduino code before using PianoLED!";
						JOptionPane.showMessageDialog(null, Message, "Message", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
				};
				worker.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void installFile(String filePath, String destinationFolderPath) {
		try {
			// Use ProcessBuilder to execute the downloaded file
			ProcessBuilder processBuilder = new ProcessBuilder(filePath);
			processBuilder.directory(new File(destinationFolderPath));
			processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteSetupFileAndPianoLEDvFiles() {
		String setupFileName = "PianoLED_Setup.exe";
		File setupFile = new File(setupFileName);

		if (setupFile.exists()) {
			boolean deleted = setupFile.delete();
			if (deleted) {
				System.out.println("Deleted " + setupFileName);
			} else {
				System.out.println("Failed to delete " + setupFileName);
			}
		}

		// Get the current working directory
		String currentDirectory = System.getProperty("user.dir");

		// Get all files in the directory
		File[] filesInDirectory = new File(currentDirectory).listFiles();

		if (filesInDirectory != null) {
			for (File file : filesInDirectory) {
				if (file.getName().startsWith("PianoLEDv") && file.getName().endsWith(".exe")) {
					boolean deletedPianoLEDvFile = file.delete();
					if (deletedPianoLEDvFile) {
						System.out.println("Deleted " + file.getName());
					} else {
						System.out.println("Failed to delete " + file.getName());
					}
				}
			}
		}
	}

	public String getOs() {
		return os;
	}
}