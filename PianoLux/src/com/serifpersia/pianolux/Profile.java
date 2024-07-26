package com.serifpersia.pianolux;

import java.io.BufferedWriter;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.swing.JToggleButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.serifpersia.pianolux.ui.GetUI;
import com.serifpersia.pianolux.ui.pnl_Colors;
import com.serifpersia.pianolux.ui.pnl_Controls;
import com.serifpersia.pianolux.ui.pnl_Gradient_MultiColor;

public class Profile {

	public Profile() {

	}

	public static String saveFields() {
		int r = Integer.parseInt(pnl_Colors.txt_R.getText());
		int g = Integer.parseInt(pnl_Colors.txt_G.getText());
		int b = Integer.parseInt(pnl_Colors.txt_B.getText());
		return String.format("%d,%d,%d", r, g, b);
	}

	public static String saveToggleButtonState(JToggleButton bgToggle) {
		if (bgToggle.isSelected()) {
			return "ON";
		} else {
			return "OFF";
		}
	}

	public static String saveGradients() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 8; i++) {
			sb.append("side").append(i).append("=").append(getColorString(getSideColor(i))).append(";");
		}
		return sb.toString();
	}

	public static String saveMultiColors() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 12; i++) {
			sb.append("multiColor").append(i).append("=").append(getColorString(getMultiColorColor(i))).append(";");
		}
		return sb.toString();
	}

	private static Color getSideColor(int sideNumber) {
		switch (sideNumber) {
		case 1:
			return pnl_Gradient_MultiColor.colors[0];
		case 2:
			return pnl_Gradient_MultiColor.colors[1];
		case 3:
			return pnl_Gradient_MultiColor.colors[2];
		case 4:
			return pnl_Gradient_MultiColor.colors[3];
		case 5:
			return pnl_Gradient_MultiColor.colors[4];
		case 6:
			return pnl_Gradient_MultiColor.colors[5];
		case 7:
			return pnl_Gradient_MultiColor.colors[6];
		case 8:
			return pnl_Gradient_MultiColor.colors[7];
		default:
			throw new IllegalArgumentException("Invalid side number: " + sideNumber);
		}
	}

	private static Color getMultiColorColor(int noteNumber) {
		switch (noteNumber) {
		case 1:
			return GetUI.multiColors[0];
		case 2:
			return GetUI.multiColors[1];
		case 3:
			return GetUI.multiColors[2];
		case 4:
			return GetUI.multiColors[3];
		case 5:
			return GetUI.multiColors[4];
		case 6:
			return GetUI.multiColors[5];
		case 7:
			return GetUI.multiColors[6];
		case 8:
			return GetUI.multiColors[7];
		case 9:
			return GetUI.multiColors[8];
		case 10:
			return GetUI.multiColors[9];
		case 11:
			return GetUI.multiColors[10];
		case 12:
			return GetUI.multiColors[11];
		default:
			throw new IllegalArgumentException("Invalid note number: " + noteNumber);
		}
	}

	private static String getColorString(Color color) {
		return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
	}

	private static void setColorFromValue(String value, int sideNumber) {
		String[] colorValues = value.substring(value.indexOf("=") + 1).split(",");
		int red = Integer.parseInt(colorValues[0]);
		int green = Integer.parseInt(colorValues[1]);
		int blue = Integer.parseInt(colorValues[2]);
		setColorForSide(new Color(red, green, blue), sideNumber);
	}

	private static void setMultiColorFromValue(String value, int noteNumber) {
		String[] multiColorValues = value.substring(value.indexOf("=") + 1).split(",");
		int red = Integer.parseInt(multiColorValues[0]);
		int green = Integer.parseInt(multiColorValues[1]);
		int blue = Integer.parseInt(multiColorValues[2]);
		setMultiColorForNote(new Color(red, green, blue), noteNumber);
	}

	private static void setColorForSide(Color color, int sideNumber) {
		switch (sideNumber) {
		case 1:
			pnl_Gradient_MultiColor.colors[0] = color;
			break;
		case 2:
			pnl_Gradient_MultiColor.colors[1] = color;
			break;
		case 3:
			pnl_Gradient_MultiColor.colors[2] = color;
			break;
		case 4:
			pnl_Gradient_MultiColor.colors[3] = color;
			break;
		case 5:
			pnl_Gradient_MultiColor.colors[4] = color;
			break;
		case 6:
			pnl_Gradient_MultiColor.colors[5] = color;
			break;
		case 7:
			pnl_Gradient_MultiColor.colors[6] = color;
			break;
		case 8:
			pnl_Gradient_MultiColor.colors[7] = color;
			break;
		default:
			throw new IllegalArgumentException("Invalid side number: " + sideNumber);
		}
	}

	private static void setMultiColorForNote(Color color, int noteNumber) {
		switch (noteNumber) {
		case 1:
			GetUI.multiColors[0] = color;
			break;
		case 2:
			GetUI.multiColors[1] = color;
			break;
		case 3:
			GetUI.multiColors[2] = color;
			break;
		case 4:
			GetUI.multiColors[3] = color;
			break;
		case 5:
			GetUI.multiColors[4] = color;
			break;
		case 6:
			GetUI.multiColors[5] = color;
			break;
		case 7:
			GetUI.multiColors[6] = color;
			break;
		case 8:
			GetUI.multiColors[7] = color;
			break;
		case 9:
			GetUI.multiColors[8] = color;
			break;
		case 10:
			GetUI.multiColors[9] = color;
			break;
		case 11:
			GetUI.multiColors[10] = color;
			break;
		case 12:
			GetUI.multiColors[11] = color;
			break;
		default:
			throw new IllegalArgumentException("Invalid note number: " + noteNumber);
		}
	}

	private static int saveColorPreset() {
		int selectedColorIndex = (int) pnl_Colors.cb_ColorPresets.getSelectedIndex();
		return selectedColorIndex;
	}

	private static int saveKeyRange() {
		int selectedKeyRange = (int) GetUI.counter;
		return selectedKeyRange;
	}

	private static String saveSliders() {
		int brightnessSliderVal = pnl_Controls.sld_Brightness.getValue();
		int fadeSliderVal = pnl_Controls.sld_Fade.getValue();
		int splashMaxLenghtVal = pnl_Controls.sld_SplashMaxLenght.getValue();
		int bgSliderVal = pnl_Controls.bg_slider.getValue();
		int transpositionSliderVal = pnl_Controls.sld_transposition.getValue();

		return brightnessSliderVal + "," + fadeSliderVal + "," + splashMaxLenghtVal + "," + bgSliderVal + ","
				+ transpositionSliderVal;
	}

	private static int savedLEDMode() {
		int selectedLEDModeIndex = (int) pnl_Controls.cb_LED_Mode.getSelectedIndex();
		return selectedLEDModeIndex;
	}

	public static int saveAnimation() {
		int selectedIndexAnimation = (int) pnl_Controls.cb_LED_Animations.getSelectedIndex();
		return selectedIndexAnimation;
	}

	public static void loadProfile(PianoLux pianoLux) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LED Profile", "led");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showOpenDialog(pianoLux);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("ColorPreset = ")) {
						int index = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());
						pnl_Colors.cb_ColorPresets.setSelectedIndex(index);
					} else if (line.startsWith("Slider = ")) {
						String[] sliderValues = line.substring(line.indexOf("=") + 1).trim().split(",");
						if (sliderValues.length == 5) {
							int brightness = Integer.parseInt(sliderValues[0]);
							int fade = Integer.parseInt(sliderValues[1]);
							int splashlenght = Integer.parseInt(sliderValues[2]);
							int bgVal = Integer.parseInt(sliderValues[3]);
							int transpositionVal = Integer.parseInt(sliderValues[4]);

							// Set the slider values to the read values
							pnl_Controls.sld_Brightness.setValue(brightness);
							pnl_Controls.sld_Fade.setValue(fade);
							pnl_Controls.sld_SplashMaxLenght.setValue(splashlenght);
							pnl_Controls.bg_slider.setValue(bgVal);
							pnl_Controls.sld_transposition.setValue(transpositionVal);

						}
					} else if (line.startsWith("LED Mode = ")) {
						int index = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());
						pnl_Controls.cb_LED_Mode.setSelectedIndex(index);
					} else if (line.startsWith("Animation = ")) {
						int index = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());
						pnl_Controls.cb_LED_Animations.setSelectedIndex(index);
					} // Assuming this code is inside a method or a block
					else if (line.startsWith("Gradient = ")) {
						String[] gradientValues = line.substring(line.indexOf("=") + 1).trim().split(";");
						if (gradientValues.length == 8) {
							for (int i = 0; i < gradientValues.length; i++) {
								setColorFromValue(gradientValues[i], i + 1);
							}
						}
					} else if (line.startsWith("MultiColor = ")) {
						String[] multiColorValues = line.substring(line.indexOf("=") + 1).trim().split(";");
						if (multiColorValues.length == 12) {
							for (int i = 0; i < multiColorValues.length; i++) {
								setMultiColorFromValue(multiColorValues[i], i + 1);
							}
						}
					} else if (line.startsWith("Background Light = ")) {
						String state = line.substring(line.indexOf("=") + 1).trim();
						boolean isToggleOn = state.equals("ON");
						if (isToggleOn) {
							pnl_Controls.bgToggle.doClick();
						}
					} else if (line.startsWith("Fixed LED = ")) {
						String state = line.substring(line.indexOf("=") + 1).trim();
						boolean isToggleOn = state.equals("ON");
						if (isToggleOn) {
							pnl_Controls.fixToggle.doClick();
						}
					} else if (line.startsWith("Reverse LED = ")) {
						String state = line.substring(line.indexOf("=") + 1).trim();
						boolean isToggleOn = state.equals("ON");
						if (isToggleOn) {
							pnl_Controls.reverseToggle.doClick();
						}
					} else if (line.startsWith("Custom Color = ")) {
						String[] rgb = line.substring(line.indexOf("=") + 1).trim().split(",");
						int r = Integer.parseInt(rgb[0]);
						int g = Integer.parseInt(rgb[1]);
						int b = Integer.parseInt(rgb[2]);

						Color color = new Color(r, g, b);

						GetUI.selectedColor = color;

						float[] hsb = Color.RGBtoHSB(GetUI.selectedColor.getRed(), GetUI.selectedColor.getGreen(),
								GetUI.selectedColor.getBlue(), null);
						pnl_Colors.colorPicker.setCustomColor(hsb[0], hsb[1], hsb[2]);
						pnl_Colors.colorPicker.repaint();

					} else if (line.startsWith("Key Range = ")) {
						int index = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());
						GetUI.counter = index;
						GetUI.setKeyboardSize(index);
						pnl_Controls.lbl_PianoSize.setText("Piano " + GetUI.getNumPianoKeys() + " Keys");
						pianoLux.getDrawPiano().repaint();
						System.out.println(index);
						System.out.println(GetUI.counter);
					}
				}

			} catch (IOException error) {
				error.printStackTrace();
			}
		}

	}

	public static void saveProfile(PianoLux pianoLux) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LED Profile", "led");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showSaveDialog(pianoLux);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String filePath = fileToSave.getAbsolutePath();
			if (!filePath.endsWith(".led")) {
				filePath += ".led";
				fileToSave = new File(filePath);
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
				writer.write("Profile Name: " + fileToSave.getName());
				writer.newLine();
				writer.newLine();
				writer.write("Date/Time: " + LocalDateTime.now().toString());
				writer.newLine();
				writer.newLine();
				writer.write("//PianoLux Profile Config File");
				writer.newLine();
				writer.write("LED Mode = " + savedLEDMode());
				writer.newLine();
				writer.write("Animation = " + saveAnimation());
				writer.newLine();
				writer.write("ColorPreset = " + saveColorPreset());
				writer.newLine();
				writer.write("Slider = " + saveSliders());
				writer.newLine();
				writer.write("Gradient = " + saveGradients());
				writer.newLine();
				writer.write("MultiColor = " + saveMultiColors());
				writer.newLine();
				writer.write("Background Light = " + saveToggleButtonState(pnl_Controls.bgToggle));
				writer.newLine();
				writer.write("Fixed LED = " + saveToggleButtonState(pnl_Controls.fixToggle));
				writer.newLine();
				writer.write("Reverse LED = " + saveToggleButtonState(pnl_Controls.reverseToggle));
				writer.newLine();
				writer.write("Custom Color = " + saveFields());
				writer.newLine();
				writer.write("Key Range = " + saveKeyRange());
			} catch (IOException error) {
				error.printStackTrace();
			}
		}
	}

}
