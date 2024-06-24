<div align="center">

![image](https://github.com/serifpersia/pianolux-arduino/assets/62844718/cd276a04-e559-4db1-a491-e5beed61d65e)


  [![Release](https://img.shields.io/github/release/serifpersia/pianolux-arduino.svg?style=flat-square)](https://github.com/serifpersia/pianolux-arduino/releases)
  [![License](https://img.shields.io/github/license/serifpersia/pianolux-arduino?color=blue&style=flat-square)](https://raw.githubusercontent.com/serifpersia/pianolux-arduino/master/LICENSE)
  [![Discord](https://img.shields.io/discord/1077195120950120458.svg?colorB=blue&label=discord&style=flat-square)](https://discord.gg/MAypyD7k86)
</div>
**PianoLux** is a user-friendly Java-based GUI application that empowers you to control an LED strip using an Arduino. This versatile tool is designed for use with 88, 76, 73, or other key-sized pianos, where you can choose between a 2m/144 or 2m/72 LED strip. For smaller keyboards, a 1m LED strip is sufficient.

## Demo
<div align="center">

https://github.com/serifpersia/pianolux-arduino/assets/62844718/6342ae71-eb57-4ae7-88c2-e7f4cdfcc62f

</div>

## MIDI Visualization

https://github.com/serifpersia/pianolux-arduino/assets/62844718/d9a4a2fe-31fc-486c-be36-b1c9e21925f8

## Join Our Community

Be part of the PianoLux Discord Server Community where you can connect with fellow users, ask questions, and share your experiences:

[![Discord Server](https://discordapp.com/api/guilds/1077195120950120458/widget.png?style=banner2)](https://discord.gg/MAypyD7k86)

## LED Strip Compatibility

PianoLux is compatible with officially supported strips by FastLED library default are NeoPixels/WS2812B. Density must be 144 which is supported on all modes of the app and 72 density for few basic modes. You can find list of FastLED supported leds below:

*Main arduino code ino has to be modified to align with strip you will use

|LED Type| Configuration|
|---------------------|-|
| Clockless Types     |
| WS2812              |
| WS2852              |
| WS2812B             |
| SK6812              |RGB non RGBW variant|
| WS2811              |
| WS2813              |
| WS2811_400          |
| GW6205              |
| GW6205_400          |
| LPD1886             |
| LPD1886_8BIT        |
| Clocked (SPI) Types |
| LPD6803             |
| LPD8806             |
| WS2801              |
| WS2803              |
| SM16716             |
| P9813               | 
| DOTSTAR             |
| APA102              |
| SK9822              |
| 12V| Strips like WS2815 configured as WS2812 GRB or RGB|
| RGBW Not Supported  |SK6812W or any other RGBW|

*Make sure you check latest FastLED github repo for updates on supported led chipsets

PianoLux currently requires native USB Arduino boards with an Atmega32U4 CPU, such as:

- Arduino Leonardo
- Pro Micro

Some arm32 based native usb boards will also work latest arduino code needs to be adapted to work with them.
- Arduino Due
- *Serial lines should be replaced with SerialUSB and native usb port should be used
- Arduino Zero

ESP32 platform boards also are supported
- ESP32 S2/S3

## Download

You can download the latest release of PianoLux here:

 [![Release](https://img.shields.io/github/release/serifpersia/pianolux-arduino.svg?style=flat-square)](https://github.com/serifpersia/pianolux-arduino/releases)

## Connecting the LED Strip and Arduino

To set up PianoLux, follow the connection diagram below:

![LED Strip + Arduino Leonardo Connection Diagram](https://user-images.githubusercontent.com/62844718/221054671-316bdee3-8a36-4753-bfb5-a574059c51ca.png)

## Enhance Brightness with External Power

For maximum brightness, consider using an external power source. Here's how you can do it:

1. Use a minimum 3A 5V-capable USB charger.
2. Cut a spare USB cable and connect the positive (usually red) and negative (usually black) wires to the LED strip's red and white wires, respectively.
Don't forget to link this ground connection to arduino's ground pin. This to to avoid ground loop issues since we are using two power sources we need to link both grounds
together.
4. Adjust the current limit to match the power supply's current rating in mA (1A = 1000mA, so 3A = 3000mA).

![External Power Setup](https://github.com/serifpersia/pianolux-arduino/assets/62844718/767c5a59-e80c-4aa8-97db-f6af03f68f24.png)

## Mounting the LED Strip

For an 88-key piano, align the third LED with the first black key, as shown below:

![image](https://user-images.githubusercontent.com/62844718/235168165-9b97120a-66ed-44f5-a7fb-11cc164cf945.png)

## Instructions for Windows OS

To use PianoLux on Windows, follow these steps:

1. Run the included `Download Java_Script.bat` file to download and install the necessary Java JRE for running the PianoLux app.

2. You will need the Arduino IDE application to upload the `.ino` file to your Arduino board. Ensure that you install the Arduino drivers and the FastLED library. If you're using Arduino IDE 1 or haven't installed the AVR Boards drivers, you may need to manually select the Arduino COM port as the "Refresh" button may not work. To install drivers, use Arduino IDE 2 and follow these steps. For other IDEs, refer to online resources on how to load drivers from the ARDUINO IDE folder in the Device Manager.

![Arduino IDE and Drivers](https://github.com/serifpersia/pianolux-arduino/assets/62844718/67236214-f701-4f23-bba4-663ad9c5babd.png)

4. Once you see the "Upload Complete" prompt, you are ready to use the PianoLux app.

For Windows users who encounter conflicts when using both PianoLux and other MIDI programs concurrently, consider following this guide to split one MIDI connection into two virtual ones using [loopMIDI](https://tristancalderbank.com/2020/08/19/how-to-use-the-same-midi-device-on-windows-across-multiple-programs-at-the-same-time/).

### 32 Bit
Use 32 bit zip(x86) and 32 bit versions of JRE and Arduino IDE
- [JRE](https://github.com/adoptium/temurin18-binaries/releases/download/jdk-18.0.2.1%2B1/OpenJDK18U-jre_x86-32_windows_hotspot_18.0.2.1_1.msi)
- [Arduino IDE](https://downloads.arduino.cc/arduino-1.8.19-windows.exe)

## Instructions for Linux & MacOS

To use PianoLux on Linux, follow these steps:

- Install Arduino IDE 2 and upload the PianoLux code to your Arduino board.

- Install the `jre17` package, depending on your Linux distribution:

  - For Arch-based distros: `sudo pacman -S jre17-openjdk`
  - For Debian-based distros: `sudo apt install openjdk-17-jdk`

- To run the app:

  - Download and extract the Linux zip file.
  - Open the Terminal, navigate to the extracted PianoLux directory, and run the following command: `sudo java -jar "pianolux jar name.jar"`

To use PianoLux on MacOS, follow these links:
- Use amd64 zip for x64 based(Intel) or arm64 zip for arm based M series Apple machines
- [Terminal Setup](https://phoenixnap.com/kb/change-zsh-to-bash-mac#:~:text=Zsh%20replaced%20Bash%20as%20macOS's,helpful%20in%20the%20macOS%20terminal)
- [Java Setup](https://www.youtube.com/watch?v=RfIiBMJqvp8)
