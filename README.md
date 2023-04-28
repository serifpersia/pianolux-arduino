# PianoLED V4

![image](https://user-images.githubusercontent.com/62844718/234608908-e98a4594-59cd-4b58-804d-13177a0c4584.png)



This app is a simple Java based GUI that controls a WS2812B LED strip using an Arduino. The LED strip must be 2m/144 for 88 keys/76/73, and for smaller key sizes 1m/144 is sufficient.

## Donations
If you want to help out on future development of the PianoLED project or just want to give some appreciation for the project consider donating. You can email @ `ramiserifpersia@gmail.com` & join the discord server where you can get the special role by helping out

## PianoLED Discord Server Community 
`https://discord.gg/S6xmuX4Hx5`

## Connecting the LED strip and Arduino
Here is how you should connect the LED strip and Arduino.

![LED Strip+Arduino Leonardo Connection Diagram](https://user-images.githubusercontent.com/62844718/221054671-316bdee3-8a36-4753-bfb5-a574059c51ca.png)

## Mounting the LED strip
For 88 keys piano aling the 3rd led with first black key

![image](https://user-images.githubusercontent.com/62844718/235168165-9b97120a-66ed-44f5-a7fb-11cc164cf945.png)

## Instructions for Windows OS
To use the app, you need to install the latest Java OpenJRE version and use the provided Arduino sketch .ino file for uploading to Arduino. 

1. Download the latest Java OpenJRE version from https://adoptium.net/temurin/releases/.
2. Select your OS, 64bit, JRE, and the latest version (the app currently needs at least version 17).
3. To Upload/Flash the .ino file, you need Arduino IDE application. Make sure to install the Arduino drivers when you launch Arduino IDE and FastLED library. 
4. With Upload Complete prompt, you are ready to use the PianoLED app.

Follow this guide if you can't run both PianoLED app and your VST or other programs that use midi. Some piano's USB midi support only connecting to one software, by following this guide, you can split one midi connection into two virtual ones, and that should let you use both: https://tristancalderbank.com/2020/08/19/how-to-use-the-same-midi-device-on-windows-across-multiple-programs-at-the-same-time/

## Instructions for Linux
- Install jre17 package:
  - For Arch, use the command: `sudo pacman -S jre17-openjdk`
- To run the app:
  - Download and extract the Linux zip file.
  - Change directory to the extracted directory.
  - Run the app by double-clicking on the PianoLED file, execute, or simply run it with the command `sudo ./PianoLED` in the terminal.

