package com.serifpersia.pianoled;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import processing.core.PApplet;

public class Updater {
	String owner = "serifpersia";
	String repo = "pianoled-arduino";
	String fileName;
	String downloadUrl;
	String saveDir = System.getProperty("user.dir") + "/";
	String destinationFolderPath = System.getProperty("user.dir") + "/";
	String appPath = System.getProperty("user.dir");
	String os = System.getProperty("os.name").toLowerCase();

	public String VersionTag = "v4.0";
	String VersionFile;
	
	public String getDownloadUrl(JSONObject release, String fileName) {
		JSONArray assets = release.getJSONArray("assets");
		for (int i = 0; i < assets.length(); i++) {
			JSONObject asset = assets.getJSONObject(i);
			if (asset.getString("name").equals(fileName)) {
				return asset.getString("browser_download_url");
			}
		}
		return null;
	}

	File folder = new File(appPath);
	File[] listOfFiles = folder.listFiles();
	File versionFile = null;

	public void checkUpdates() {
		// Show confirmation dialog to check for updates
		int confirm = JOptionPane.showOptionDialog(null, "Do you want to check for updates?", "Update Checker",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (confirm == JOptionPane.YES_OPTION) {
			String releaseUrl = String.format("https://api.github.com/repos/%s/%s/releases/latest", owner, repo);
			JSONObject latestRelease = getLatestRelease(releaseUrl);

			if (latestRelease == null) {
				JOptionPane.showMessageDialog(null, "Unable to retrieve latest release information.", "Update",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Compare the latest release tag with the local version tag
			if (VersionTag != null && VersionTag.equals(latestRelease.getString("tag_name"))) {
				JOptionPane.showMessageDialog(null, "You already have the latest version.", "Update",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			if (VersionTag == null) {
				String message = "Unable to retrieve local app information";
				JOptionPane.showMessageDialog(null, message, "Update", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Show confirmation dialog to download the latest release
			confirm = JOptionPane.showOptionDialog(null, "A new update is available. Do you want to download it?",
					"Update Checker", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (confirm == JOptionPane.YES_OPTION) {
				// Download and extract the latest release
				String downloadUrl, fileName;
				if (os.contains("win")) {
					fileName = "PianoLED-windows-amd64.zip";
					PApplet.println("File to download: " + fileName);
				} else {
					fileName = "PianoLED-linux-amd64.zip";
				}
				downloadUrl = getDownloadUrl(latestRelease, fileName);

				try {
					// Download the file with a progress bar
					URL url = new URL(downloadUrl);
					URLConnection conn = url.openConnection();
					conn.connect();
					int contentLength = conn.getContentLength();
					BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
					FileOutputStream out = new FileOutputStream(saveDir + fileName);
					BufferedOutputStream bout = new BufferedOutputStream(out, 1024);
					byte[] data = new byte[1024];
					int x = 0;
					int bytesRead = 0;

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

					while ((bytesRead = in.read(data, 0, 1024)) >= 0) {
						bout.write(data, 0, bytesRead);
						x += bytesRead;
						int percentCompleted = (int) ((x / (float) contentLength) * 100);

						// Update progress bar
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								progressBar.setValue(percentCompleted);
							}
						});
					}
					bout.close();
					in.close();
					extractZipFile(saveDir + fileName, destinationFolderPath);
					dialog.dispose(); // Close progress bar dialog
					String restartMessage = "The app has been updated to " + latestRelease.getString("tag_name")
							+ ". Delete everything except Arudino folder & new PianoLED!";
					JOptionPane.showMessageDialog(null, restartMessage, "Update", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void checkLocalVersion() {

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
				if (fileName.matches(".*v\\d+\\.\\d+.*")) {
					VersionTag = fileName.replaceAll(".*(v\\d+\\.\\d+).*", "$1");
					versionFile = listOfFiles[i];
					break;
				}
			}
		}
		System.out.println("VersionTag: " + VersionTag);
	}
	
	public JSONObject getLatestRelease(String url) {
		try {
			// String authToken = ""; // replace with your PAT
			URL apiLink = new URL(url);
			URLConnection conn = apiLink.openConnection();
			conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
			conn.setRequestProperty("User-Agent", "Java");
			// conn.setRequestProperty("Authorisation", "token " + authToken); // set the
			// authorisation header with your PAT
			BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
			byte[] dataBuffer = new byte[1024];
			int bytesRead;
			StringBuilder responseBuilder = new StringBuilder();
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				responseBuilder.append(new String(dataBuffer, 0, bytesRead));
			}
			in.close();
			return new JSONObject(responseBuilder.toString());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	public void extractZipFile(String zipFilePath, String destinationFolderPath) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			byte[] buffer = new byte[1024];

			while (zipEntry != null) {
				String fileName = zipEntry.getName();
				File newFile = new File(destinationFolderPath + fileName);
				System.out.println("Extracting file: " + newFile.getAbsolutePath());

				if (zipEntry.isDirectory()) {
					// Create the directory
					newFile.mkdirs();
				} else {
					// Create all non-existing parent directories
					new File(newFile.getParent()).mkdirs();

					// Write the file contents
					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while ((len = zipInputStream.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
				}

				zipEntry = zipInputStream.getNextEntry();
			}

			zipInputStream.closeEntry();
			zipInputStream.close();

			System.out.println("Zip file extracted to: " + destinationFolderPath);

			// Delete the zip file
			File zipFile = new File(zipFilePath);
			if (zipFile.delete()) {
				System.out.println("Zip file deleted successfully");
			} else {
				System.err.println("Failed to delete zip file");
			}
		} catch (IOException e) {
			System.err.println("Error extracting zip file: " + e.getMessage());
		}
	}

	public String getOs() {
		return os;
	}
}