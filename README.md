![Png](https://user-images.githubusercontent.com/62844718/211154579-a25f3100-93aa-43c5-8c7b-9ea73b72fbee.png)

Simple Processing(Java) based GUI that controls WS2812B LED strip using Arudino

To run the app you would need to install latest java 32-bit 

and use the provided Arduino sketch .ino file and upload that your arduino(arduino leonardo recommended).

Make sure you use pwm pin 5 for data or modify
the code for your pwm pin number.

Start the app and select your Midi input device, arduino port, type of piano(size of keys, currently supporting 88,76,61 and 49). 
Default is 88, make sure to change the preset with arrows on the screen.
Click Open.Default color is white(sliders rgb sliders are maxed out) and the brightness is set to half(Use higher brightness 
at your own risk. If you're powering the leds of off arduino's 5V pins make sure your not maxing out the current usage.
Also make sure you don't leave your arduino plugged in to the usb port if your not using it!

Chose your prefered color using rgb sliders and change brightness of the leds with the brightness slider aboe the rgb sliders.

If you have a specific use case you and already defined presets don't work for you, you 
can use advance user mode to manually select each variable

To get a working config in Andvance User mode use this formula:
First key(21-108)
Last key (21-108)
Number of leds(1-174)

Example: 61 piano keys what number of leds is needed and what are the first and last keys?
61 piano has 61 keys. To set correct number of leds multiply your number of keys on your piano
For 61 piano thats 61 * 2 = 122 Thats the number of leds needed for 61 key piano.

To find midi first and last midi note number here is an image that shows every possible piano key and its midi note number:



![midi chart](https://user-images.githubusercontent.com/62844718/206138883-35bb5a70-2aed-457f-ab51-72d7f7806af9.png)



