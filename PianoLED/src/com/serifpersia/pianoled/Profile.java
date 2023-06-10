package com.serifpersia.pianoled;

import java.io.BufferedWriter;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;

import javax.swing.JRadioButton;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.serifpersia.pianoled.ui.ColorPickerPanel;
import com.serifpersia.pianoled.ui.ControlsPanel;

public class Profile {

	public Profile() {

	}

	public static String saveFields() {
		int r = Integer.parseInt(ControlsPanel.txt_R.getText());
		int g = Integer.parseInt(ControlsPanel.txt_G.getText());
		int b = Integer.parseInt(ControlsPanel.txt_B.getText());
		return String.format("%d,%d,%d", r, g, b);
	}

	public static String saveRadioButtonState(ButtonGroup bgGroup) {
		JRadioButton selectedRadioButton = null;
		Enumeration<AbstractButton> btnEnum = bgGroup.getElements();
		while (btnEnum.hasMoreElements()) {
			AbstractButton button = btnEnum.nextElement();
			if (button.isSelected() && button instanceof JRadioButton) {
				selectedRadioButton = (JRadioButton) button;
				break;
			}
		}
		String selectedBgButton = selectedRadioButton != null ? selectedRadioButton.getText() : "";
		return selectedBgButton;
	}

	public static String saveGradients() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 8; i++) {
			sb.append("side").append(i).append("=").append(getColorString(getSideColor(i))).append(";");
		}
		return sb.toString();
	}

	private static Color getSideColor(int sideNumber) {
		switch (sideNumber) {
		case 1:
			return ControlsPanel.colors[0];
		case 2:
			return ControlsPanel.colors[1];
		case 3:
			return ControlsPanel.colors[2];
		// Add cases for the remaining sides (4 to 8)
		case 4:
			return ControlsPanel.colors[3];
		case 5:
			return ControlsPanel.colors[4];
		case 6:
			return ControlsPanel.colors[5];
		case 7:
			return ControlsPanel.colors[6];
		case 8:
			return ControlsPanel.colors[7];
		default:
			throw new IllegalArgumentException("Invalid side number: " + sideNumber);
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

	private static void setColorForSide(Color color, int sideNumber) {
		switch (sideNumber) {
		case 1:
			PianoController.side1 = color;
			break;
		case 2:
			PianoController.side2 = color;
			break;
		case 3:
			PianoController.side3 = color;
			break;
		// Add cases for the remaining sides (4 to 8)
		case 4:
			PianoController.side4 = color;
			break;
		case 5:
			PianoController.side5 = color;
			break;
		case 6:
			PianoController.side6 = color;
			break;
		case 7:
			PianoController.side7 = color;
			break;
		case 8:
			PianoController.side8 = color;
			break;
		default:
			throw new IllegalArgumentException("Invalid side number: " + sideNumber);
		}
	}

	private static int saveColorPreset() {
		int selectedColorIndex = (int) ControlsPanel.cb_ColorPresets.getSelectedIndex();
		return selectedColorIndex;
	}

	private static String saveSliders() {
		int brightnessSliderVal = ControlsPanel.sld_Brightness.getValue();
		int fadeSliderVal = ControlsPanel.sld_Fade.getValue();
		int splashMaxLenghtVal = ControlsPanel.sld_SplashMaxLenght.getValue();

		return brightnessSliderVal + "," + fadeSliderVal + "," + splashMaxLenghtVal;
	}

	private static int savedLEDMode() {
		int selectedLEDModeIndex = (int) ControlsPanel.cb_LED_Mode.getSelectedIndex();
		return selectedLEDModeIndex;
	}

	public static int saveAnimation() {
		int selectedIndexAnimation = (int) ControlsPanel.cb_LED_Animations.getSelectedIndex();
		return selectedIndexAnimation;
	}

	public static void loadProfile(PianoLED pianoLED) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LED Profile", "led");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showOpenDialog(pianoLED);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("ColorPreset = ")) {
						int index = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());
						ControlsPanel.cb_ColorPresets.setSelectedIndex(index);
					} else if (line.startsWith("Slider = ")) {
						String[] sliderValues = line.substring(line.indexOf("=") + 1).trim().split(",");
						if (sliderValues.length == 3) {
							int brightness = Integer.parseInt(sliderValues[0]);
							int fade = Integer.parseInt(sliderValues[1]);
							int splashlenght = Integer.parseInt(sliderValues[2]);
							// Set the slider values to the read values
							ControlsPanel.sld_Brightness.setValue(brightness);
							ControlsPanel.sld_Fade.setValue(fade);
							ControlsPanel.sld_SplashMaxLenght.setValue(splashlenght);

						}
					} else if (line.startsWith("LED Mode = ")) {
						int index = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());
						ControlsPanel.cb_LED_Mode.setSelectedIndex(index);
					} else if (line.startsWith("Animation = ")) {
						int index = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());
						ControlsPanel.cb_LED_Animations.setSelectedIndex(index);
					} // Assuming this code is inside a method or a block
					else if (line.startsWith("Gradient = ")) {
						String[] gradientValues = line.substring(line.indexOf("=") + 1).trim().split(";");
						if (gradientValues.length == 8) {
							for (int i = 0; i < gradientValues.length; i++) {
								setColorFromValue(gradientValues[i], i + 1);
							}
						}

					} else if (line.startsWith("Background Light = ")) {
						String state = line.substring(line.indexOf("=") + 1).trim();
						Enumeration<AbstractButton> buttons = ControlsPanel.bgGroup.getElements();
						while (buttons.hasMoreElements()) {
							AbstractButton button = buttons.nextElement();
							if (button instanceof JRadioButton && button.getText().equals(state)) {
								button.doClick();
								break;
							}
						}
					} else if (line.startsWith("Fixed LED = ")) {
						String state = line.substring(line.indexOf("=") + 1).trim();
						Enumeration<AbstractButton> buttons = ControlsPanel.fixLEDGroup.getElements();
						while (buttons.hasMoreElements()) {
							AbstractButton button = buttons.nextElement();
							if (button instanceof JRadioButton && button.getText().equals(state)) {
								button.doClick();
								break;
							}
						}
					} else if (line.startsWith("Reverse LED = ")) {
						String state = line.substring(line.indexOf("=") + 1).trim();
						Enumeration<AbstractButton> buttons = ControlsPanel.reverseLEDGroup.getElements();
						while (buttons.hasMoreElements()) {
							AbstractButton button = buttons.nextElement();
							if (button instanceof JRadioButton && button.getText().equals(state)) {
								button.doClick();
								break;
							}
						}
					} else if (line.startsWith("Custom Color = ")) {
						String[] rgb = line.substring(line.indexOf("=") + 1).trim().split(",");
						int r = Integer.parseInt(rgb[0]);
						int g = Integer.parseInt(rgb[1]);
						int b = Integer.parseInt(rgb[2]);
						ControlsPanel.txt_R.setText(Integer.toString(r));
						ControlsPanel.txt_G.setText(Integer.toString(g));
						ControlsPanel.txt_B.setText(Integer.toString(b));
						ColorPickerPanel colorPicker = new ColorPickerPanel();
						colorPicker.repaint();
					}
				}

			} catch (IOException error) {
				error.printStackTrace();
			}
		}

	}

	public static void saveProfile(PianoLED pianoLED) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LED Profile", "led");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showSaveDialog(pianoLED);
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
				writer.write("//PianoLED Profile Config File");
				writer.newLine();
				writer.newLine();
				writer.write(
						"//LED Mode: Default - 0, Splash - 1, Random - 2, Gradient - 3, Velocity - 4, Split - 5, Animation - 6");
				writer.newLine();
				writer.newLine();
				writer.write(
						"//Animation: RainbowColors - 0, RainbowStripeColor - 1, OceanColors - 2, CloudColors - 3, LavaColors - 4, ForestColors - 5, PartyColors - 6");
				writer.newLine();
				writer.newLine();
				writer.write(
						"//ColorPreset: Full Spectrum(Black) - 0, White - 1, Red - 2, Green - 3, Blue - 4, Yellow - 5, Orange - 6, Purple - 7, Pink - 8, Teal - 9, Lime - 10, Cyan - 11, Magenta - 12, Peach - 13, Lavender - 14, Turquoise - 15, Gold - 16, Custom - 17");
				writer.newLine();
				writer.newLine();
				writer.write("//Slider: brightness(0-255), fade(0-255),splashlenght(4-16)");
				writer.newLine();
				writer.newLine();
				writer.write(
						"//Gradient: leftSide(red amount - (0-255), green amount (0-255), blue amount (0-255); middleSide(red amount - (0-255), green amount (0-255), blue amount (0-255); rightSide(red amount - (0-255), green amount (0-255), blue amount (0-255)");
				writer.newLine();
				writer.newLine();
				writer.write("//Background Light,Fixed LED,Reverse LED = On or Off");
				writer.newLine();
				writer.newLine();
				writer.write("//Custom Color: red amount(0-255), green amount(0-255),blue amount(0-255)");
				writer.newLine();
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
				writer.write("Background Light = " + saveRadioButtonState(ControlsPanel.bgGroup));
				writer.newLine();
				writer.write("Fixed LED = " + saveRadioButtonState(ControlsPanel.fixLEDGroup));
				writer.newLine();
				writer.write("Reverse LED = " + saveRadioButtonState(ControlsPanel.reverseLEDGroup));
				writer.newLine();
				writer.write("Custom Color = " + saveFields());
				writer.newLine();
			} catch (IOException error) {
				error.printStackTrace();
			}
		}
	}

}
