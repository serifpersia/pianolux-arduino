package com.serifpersia.pianoled;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

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

	public String VersionTag = "v4.0.3";
	String VersionFile;

	public String getDownloadUrl(JsonNode latestRelease, String fileName) throws IOException {
		return latestRelease.findValue("browser_download_url").asText();
	}

	File folder = new File(appPath);
	File[] listOfFiles = folder.listFiles();
	File versionFile = null;

	public void getUpdate() {
		String branchName = (String) DashboardPanel.BranchList.getSelectedItem();
		if (branchName.equals("stable")) {

			int choice = JOptionPane.showConfirmDialog(null, "Do you want to update?", "Update",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (choice == JOptionPane.YES_OPTION) {
				checkForUpdates();
			}
		} else {
			downloadFilesFromBranch(owner, repo, "beta");
		}
	}

	public void checkForUpdates() {
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

	public void downloadAndInstallUpdate() {
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
			String downloadFileName = osName.contains("win") ? "PianoLED-windows-amd64.zip"
					: "PianoLED-linux-amd64.zip";

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
						extractZipFile(saveDir + downloadFileName, destinationFolderPath);
						dialog.dispose();
						String Message = "Update Complete!"
								+ " Press Clean button in the new version of the app before using it!";
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

	public void downloadFilesFromBranch(String owner, String repo, String branchName) {
		try {
			String branchUrl = String.format("https://api.github.com/repos/%s/%s/branches/%s", owner, repo, branchName);
			JsonNode branchJson = getLatestRelease(branchUrl);

			if (branchJson == null) {
				JOptionPane.showMessageDialog(null, "Unable to retrieve branch information.", "Update",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			String commitSha = branchJson.findValue("commit").findValue("sha").asText();
			String treeUrl = String.format("https://api.github.com/repos/%s/%s/git/trees/%s?recursive=1", owner, repo,
					commitSha);
			JsonNode treeJson = getLatestRelease(treeUrl);

			if (treeJson == null) {
				JOptionPane.showMessageDialog(null, "Unable to retrieve tree information.", "Message",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			List<JsonNode> fileList = treeJson.findValues("path");
			for (JsonNode fileNode : fileList) {
				String fileUrl = String.format("https://raw.githubusercontent.com/%s/%s/%s/%s", owner, repo, branchName,
						fileNode.asText());
				URL url = new URL(fileUrl);
				String[] parts = fileNode.asText().split("/");
				fileName = parts[parts.length - 1];

				String osName = System.getProperty("os.name").toLowerCase();
				String downloadFileName = osName.contains("win") ? "PianoLED-windows-beta.zip"
						: "PianoLED-linux-beta.zip";

				if (!fileName.equals(downloadFileName)) {
					continue;
				}

				URLConnection conn = url.openConnection();
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
						extractZipFile(saveDir + fileName, destinationFolderPath);
						dialog.dispose();
						JOptionPane.showMessageDialog(null, "Beta update completed successfully.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
				};
				worker.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkLocalVersion() {

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
				if (fileName.matches(".*v\\d+\\.\\d+\\.\\d+.*")) {
					VersionTag = fileName.replaceAll(".*(v\\d+\\.\\d+\\.\\d+).*", "$1");
					versionFile = listOfFiles[i];
					break;
				}
			}
		}
		System.out.println("VersionTag: " + VersionTag);
	}

	public JsonNode getLatestRelease(String url) {
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
			JsonMapper jsonMapper = new JsonMapper();
			return jsonMapper.readTree(responseBuilder.toString());
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