
![pianoprogram](https://user-images.githubusercontent.com/62844718/206133011-e5568ce3-793f-4fdc-acb7-45eb76dd7924.png)

Simple Processing(Java) based GUI that controls WS2912B LED strip using Arudino

To run the app you would need to install latest java 32-bit 

and use the provided Arduino sketch .ino file and upload that your arduino.

Make sure you use pwm pin 5 for data or modify
the code for your pin number.

Start the app and select your Midi input, arduino port, type of piano(size of keys, currently supporting 88,76,61 and 49). 
Click Open and initially press change color to start testing if note is pressed an led should light up...

Now that you tested and confirmed it works you can change the color with the sliders and also brightness

Click change color to confirm color and brightness.

If you have a specific use case you and already defined presets don't work for you you 
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
