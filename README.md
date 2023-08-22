# PianoLED V4

![PianoLED Logo](https://github.com/serifpersia/pianoled-arduino/assets/62844718/5aca8f1d-1b2d-4312-9fdf-8bb4c028a0b3.png)

**PianoLED V4** is a user-friendly Java-based GUI application that empowers you to control an LED strip using an Arduino. This versatile tool is designed for use with 88, 76, 73, or other key-sized pianos, where you can choose between a 2m/144 or 2m/72 LED strip. For smaller keyboards, a 1m LED strip is sufficient.

## Join Our Community

Be part of the PianoLED Discord Server Community where you can connect with fellow users, ask questions, and share your experiences: [Join PianoLED Discord](https://discord.gg/S6xmuX4Hx5)

## LED Strip Compatibility

PianoLED is compatible with a wide range of 5V addressable LED strips supported by the FastLED library for Arduino. It offers support for both 72 and 144 density/m strips. Compatible strips include:

- WS2812B
- WS2812
- WS2811
- SK6812

PianoLED currently requires native USB Arduino boards with an Atmega32U4 CPU, such as:

- Arduino Leonardo
- Pro Micro
- Arduino Zero

Future plans include support for ESP boards as well!

## Download

You can download the latest release of PianoLED from the [GitHub Releases](https://github.com/serifpersia/pianoled-arduino/releases) page.

## Connecting the LED Strip and Arduino

To set up PianoLED, follow the connection diagram below:

![LED Strip + Arduino Leonardo Connection Diagram](https://user-images.githubusercontent.com/62844718/221054671-316bdee3-8a36-4753-bfb5-a574059c51ca.png)

## Enhance Brightness with External Power

For maximum brightness, consider using an external power source. Here's how you can do it:

1. Use a minimum 3A 5V-capable USB charger.
2. Cut a spare USB cable and connect the positive (usually red) and negative (usually black) wires to the LED strip's red and white wires, respectively.
3. Adjust the current limit to match the power supply's current rating in mA (1A = 1000mA, so 3A = 3000mA).

![External Power Setup](https://github.com/serifpersia/pianoled-arduino/assets/62844718/767c5a59-e80c-4aa8-97db-f6af03f68f24.png)

## Mounting the LED Strip

For an 88-key piano, align the third LED with the first black key, as shown below:

![image](https://user-images.githubusercontent.com/62844718/235168165-9b97120a-66ed-44f5-a7fb-11cc164cf945.png)

## Instructions for Windows OS

To use PianoLED on Windows, follow these steps:

1. Run the included `Download Java_Script.bat` file to download and install the necessary Java JRE for running the PianoLED app.

2. You will need the Arduino IDE application to upload the `.ino` file to your Arduino board. Ensure that you install the Arduino drivers and the FastLED library. If you're using Arduino IDE 1 or haven't installed the AVR Boards drivers, you may need to manually select the Arduino COM port as the "Refresh" button may not work. To install drivers, use Arduino IDE 2 and follow these steps. For other IDEs, refer to online resources on how to load drivers from the ARDUINO IDE folder in the Device Manager.

![Arduino IDE and Drivers](https://github.com/serifpersia/pianoled-arduino/assets/62844718/67236214-f701-4f23-bba4-663ad9c5babd.png)

4. Once you see the "Upload Complete" prompt, you are ready to use the PianoLED app.

For Windows users who encounter conflicts when using both PianoLED and other MIDI programs concurrently, consider following this guide to split one MIDI connection into two virtual ones using [loopMIDI](https://tristancalderbank.com/2020/08/19/how-to-use-the-same-midi-device-on-windows-across-multiple-programs-at-the-same-time/).

## Instructions for Linux

To use PianoLED on Linux, follow these steps:

- Install Arduino IDE 2 and upload the PianoLED code to your Arduino board.

- Install the `jre17` package, depending on your Linux distribution:

  - For Arch-based distros: `sudo pacman -S jre17-openjdk`
  - For Debian-based distros: `sudo apt install openjdk-17-jdk`

- To run the app:

  - Download and extract the Linux zip file.
  - Open the Terminal, navigate to the extracted PianoLED directory, and run the following command: `sudo java -jar "pianoled jar name.jar"`

## Support the Project

If you'd like to support future development of the PianoLED project or simply show your appreciation, consider making a donation. You can reach out via email at `ramiserifpersia@gmail.com` and join the Discord server, where you can receive a special role for contributing to the project. Your support helps us keep the project vibrant and evolving. Thank you!
