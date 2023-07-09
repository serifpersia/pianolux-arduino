package com.serifpersia.pianoled;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.serifpersia.pianoled.ui.pnl_Guide;

public class GuideProfile {

	public GuideProfile() {
	}

	private static int saveScaleKey() {
		return pnl_Guide.cb_ScaleKey.getSelectedIndex();
	}

	private static String saveScale() {
		int[] scalePattern = pnl_Guide.getScalePattern();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < scalePattern.length; i++) {
			sb.append(scalePattern[i]).append(" ");
		}

		return sb.toString().trim();
	}

	public static void loadProfile(PianoLED pianoLED) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Guide Custom Scale", "scale");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showOpenDialog(pianoLED);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("Scale Key = ")) {
						int index = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());
						pnl_Guide.cb_ScaleKey.setSelectedIndex(index);
					} else if (line.startsWith("Scale = ")) {
						String[] scalePatternStrings = line.substring(line.indexOf("=") + 1).trim().split(" ");
						int[] scalePattern = new int[scalePatternStrings.length];
						for (int i = 0; i < scalePatternStrings.length; i++) {
							scalePattern[i] = Integer.parseInt(scalePatternStrings[i]);
						}
						ModesController.scalePattern = scalePattern;
					}

				}
			} catch (IOException error) {
				error.printStackTrace();
			}
		}
	}

	public static void saveProfile(PianoLED pianoLED) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Guide Custom Scale", "scale");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showSaveDialog(pianoLED);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String filePath = fileToSave.getAbsolutePath();
			if (!filePath.endsWith(".scale")) {
				filePath += ".scale";
				fileToSave = new File(filePath);
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
				writer.write("Guide Custom Scale: " + fileToSave.getName());
				writer.newLine();
				writer.newLine();
				writer.write("Date/Time: " + LocalDateTime.now().toString());
				writer.newLine();
				writer.newLine();
				writer.write("//PianoLED Guide Custom Scale Config File");
				writer.newLine();
				writer.newLine();
				writer.write(
						"//Scale Key Index: A - 0, A# - 1, B - 2, C - 3, C# - 4, D - 5, D# - 6, E - 7, F - 8, F# - 9, G - 10, G# - 11");
				writer.newLine();
				writer.newLine();
				writer.write("Scale Key = " + saveScaleKey());
				writer.newLine();
				writer.write("Scale = " + saveScale());
				writer.newLine();
			} catch (IOException error) {
				error.printStackTrace();
			}
		}
	}

}
