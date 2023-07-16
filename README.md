# PianoLED V4

![image](https://github.com/serifpersia/pianoled-arduino/assets/62844718/9be23577-c90b-41eb-a175-f5083e14973c)




This app is a simple Java based GUI that controls  LED strip using an Arduino. The LED strip must be 2m/144 or 72/2m for 88 keys/76/73, and for smaller key sizes 1m is sufficient.
LED strip support:
Any 5V adressable strip supported by FastLED library for Arduino 72 and 144 density/m 
Exampeles:
5v Adressable LED strips
WS2812B/WS2812/WS2811
SK6812 

PianoLED reqiures native usb Arduino boards like Atmega32U4 cpu boards like:
Arduino Leonardo,Pro Micro,Zero
Future plans is to include ESP boards as well!

## Download [PianoLED](https://github.com/serifpersia/pianoled-arduino/releases)


## PianoLED Discord Server Community 
`https://discord.gg/S6xmuX4Hx5`

## Connecting the LED strip and Arduino
Here is how you should connect the LED strip and Arduino.

![LED Strip+Arduino Leonardo Connection Diagram](https://user-images.githubusercontent.com/62844718/221054671-316bdee3-8a36-4753-bfb5-a574059c51ca.png)

## More Brightness? Use of External Power
For simplest setup you can use minimum 3A 5V capable USB charger, cut a spare usb cable and connect positive and negative to led strip's red and white wire red being positive and white being negative, usually usb cables red is positive and black negative rest are data wires.
You also have to change the current limit to power supply current rating in mA, 1Amp is 1000mA so 3A is 3000mA

![image](https://github.com/serifpersia/pianoled-arduino/assets/62844718/648d5af4-b8b6-4892-b512-6710904e2728)

Here is how you connect external power

![externa power](https://github.com/serifpersia/pianoled-arduino/assets/62844718/767c5a59-e80c-4aa8-97db-f6af03f68f24)


## Mounting the LED strip
For 88 keys piano aling the 3rd led with first black key

![image](https://user-images.githubusercontent.com/62844718/235168165-9b97120a-66ed-44f5-a7fb-11cc164cf945.png)

## Instructions for Windows OS
To use the app, you need to install the latest Java OpenJRE version and use the provided Arduino sketch .ino file for uploading to Arduino. 

1.Run included Download Java_Script.bat file to download and install Java JRE needed to run the PianoLED app.

2. To Upload/Flash the .ino file, you need Arduino IDE application. Make sure to install the Arduino drivers when you launch Arduino IDE and FastLED library. If you use Arduino IDE 1 or you didnt install AVR Boards drivers you won't be able to use the Refresh button, manual selection of arduino com port will work tho. To install drivers make sure you have Arduino IDE 2 and do ctrl+shift+B and click on second icon and click install button. For the other IDE look on google how to load drivers from ARDUINO IDE folder  Device Manager

![image](https://github.com/serifpersia/pianoled-arduino/assets/62844718/67236214-f701-4f23-bba4-663ad9c5babd)


4. With Upload Complete prompt, you are ready to use the PianoLED app.

Follow this guide if you can't run both PianoLED app and your VST or other programs that use midi. Some piano's USB midi support only connecting to one software, by following this guide, you can split one midi connection into two virtual ones, and that should let you use both: [loopMIDI](https://tristancalderbank.com/2020/08/19/how-to-use-the-same-midi-device-on-windows-across-multiple-programs-at-the-same-time/)


## Instructions for Linux
- Install jre17 package:
  - For Arch, use the command: `sudo pacman -S jre17-openjdk`
- To run the app:
  - Download and extract the Linux zip file.
  -Open Terminal, cd to extracted PianoLED directory and run  `sudo java -jar "pianoled jar name.jar"` command.
  
## Donations
If you want to help out on future development of the PianoLED project or just want to give some appreciation for the project consider donating. You can email @ `ramiserifpersia@gmail.com` & join the discord server where you can get the special role by helping out
