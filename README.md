![github](https://user-images.githubusercontent.com/62844718/219884748-a75d5ed3-9e97-40cb-a85f-8a5ebbb8724b.png)

Watch the tutorial on Youtube: https://www.youtube.com/watch?v=bsYGRT0vvdY
Follow this guide if you can't run both pianoled software and your vst(some piano's usb midi support only connecting to one software,
by following this guide you can split one midi connection into two virtual ones and that should let you use both),link:
https://tristancalderbank.com/2020/08/19/how-to-use-the-same-midi-device-on-windows-across-multiple-programs-at-the-same-time/

This app is a simple Processing (Java) based GUI that controls a WS2812B LED strip using an Arduino. 
The LED strip must be 2m/144 for 88 keys/76/73, and for smaller key sizes 1m/144 is sufficient. 

To use the app, you need to install the latest 32-bit version of Java and use the provided Arduino sketch .ino file.
Make sure to install the FastLED library and upload the code to your Arduino (Leonardo recommended).

After starting the app, select your MIDI input device, Arduino port, and type of piano (currently supporting 88, 76, 73, 61, and 49 keys).
You can choose your preferred color using the RGB sliders and change the brightness of the LEDs with the brightness slider. 

If you have a specific use case and the presets don't work for you, you can use advanced user mode to manually select each variable.

In advanced user mode, use the following formula to get a working config: First key (21-108), last key (21-108), number of LEDs (1-174).
For example, to find the number of LEDs needed for a 61-key piano, multiply the number of keys on your piano (61) by 2, which equals 122.

Readme file included in the zip file provies detailed information on how to use the software(Random mode,Velocity mode...)

To find the first and last MIDI note number, refer to an image that shows every possible piano key and its MIDI note number.

![midi chart](https://user-images.githubusercontent.com/62844718/206138883-35bb5a70-2aed-457f-ab51-72d7f7806af9.png)



