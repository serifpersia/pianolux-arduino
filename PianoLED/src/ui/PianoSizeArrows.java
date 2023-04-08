package ui;

public class PianoSizeArrows {

	static int counter = 0;

	static int stripLedNum = 176;
	static int firstNoteSelected = 21;
	static int lastNoteSelected = 108;

	static int getNumPianoKeys() {
		return lastNoteSelected - firstNoteSelected + 1;
	}

	int getStripLedNum() {
		return stripLedNum;
	}

	static int rectASizeX = 0;
	static int rectBSizeX = 0;
	static int rectBX = 795;

	static void setKeyboardSize(int counter) {
		System.out.println("counter: " + counter);
		if (counter <= 0) {
			rectASizeX = 0;
			rectBSizeX = 0;
			stripLedNum = 176;
			firstNoteSelected = 21;
			lastNoteSelected = 108;
		}
		if (counter == 1) {
			rectASizeX = 60;
			rectBSizeX = -45;
			stripLedNum = 152;
			firstNoteSelected = 28;
			lastNoteSelected = 103;
		}

		if (counter == 2) {
			rectASizeX = 60;
			rectBSizeX = -75;
			stripLedNum = 146;
			firstNoteSelected = 28;
			lastNoteSelected = 100;
		}
		if (counter == 3) {
			rectASizeX = 135;
			rectBSizeX = -105;
			stripLedNum = 122;
			firstNoteSelected = 36;
			lastNoteSelected = 96;
		}
		if (counter == 4) {
			rectASizeX = 135;
			rectBSizeX = -210;
			stripLedNum = 98;
			firstNoteSelected = 36;
			lastNoteSelected = 84;
		}
		System.out.println("Selected number led: " + stripLedNum);
		System.out.println("Selected first note: " + firstNoteSelected);
		System.out.println("Selected last note: " + lastNoteSelected);
	}
}
