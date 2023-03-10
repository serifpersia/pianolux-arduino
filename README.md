![image](https://user-images.githubusercontent.com/62844718/224165626-9ffef153-2cf1-4f94-aa93-438d422b8a8b.png)
This app is a simple Processing (Java) based GUI that controls a WS2812B LED strip using an Arduino. 
The LED strip must be 2m/144 for 88 keys/76/73, and for smaller key sizes 1m/144 is sufficient. 

Here is how you should connect the LED strip and Arduino.

![LED Strip+Arduino Leonardo Connection Diagram](https://user-images.githubusercontent.com/62844718/221054671-316bdee3-8a36-4753-bfb5-a574059c51ca.png)

Windows OS
To use the app, you need to install the latest Java OpenJRE version and use the provided Arduino sketch .ino file for uploading to Arduino
https://adoptium.net/temurin/releases/ Select your OS, 64bit, JRE and lates version(the app currently needs at least version 17)

To Upload/Flash the .ino file you need Arduino IDE application 

Make sure to install the Arduino drivers when you launch Arduino IDE and FastLED library. With Upload Complete prompt you are ready to use the PianoLED app.

Follow this guide if you can't run both PianoLED app and your vst or other programs that use midi(some piano's usb midi support only connecting to one software,
by following this guide you can split one midi connection into two virtual ones and that should let you use both),link:
https://tristancalderbank.com/2020/08/19/how-to-use-the-same-midi-device-on-windows-across-multiple-programs-at-the-same-time/

Arch Linux
Running the PianoLED App
install jre17 package:
sudo pacman -S jre17-openjdk
To run the app:
-download and extract the linux zip file
-cd into the extracted directory
-make ethe file executable by you or anyone
-run the app by double clicking on the PianoLED file, Execute, or simply run it with
./PianoLED command
