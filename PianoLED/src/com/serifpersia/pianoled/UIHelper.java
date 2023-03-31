package com.serifpersia.pianoled;

import java.awt.Color;
import java.util.List;

import controlP5.Button;
import controlP5.ColorWheel;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.ScrollableList;
import controlP5.Slider;
import controlP5.Toggle;

public class UIHelper {

	Color APP_COLOR_FG = Color.RED;
	Color APP_COLOR_BG = Color.BLACK;
	Color APP_COLOR_ACT = Color.RED;

	Color SLIDER_COLOR_FG = Color.GRAY;
	Color SLIDER_COLOR_BG = Color.BLACK;
	Color SLIDER_COLOR_ACT = Color.GRAY;

	private ControlP5 cp5;

	public UIHelper(PianoLED pianoLED, ControlP5 _cp5) {
		this.cp5 = _cp5;
	}

	public Button addButton(String name, String label, int x, int y, int w, int h) {
		return addButton(name, label, x, y, w, h, APP_COLOR_FG, APP_COLOR_BG, APP_COLOR_ACT);
	}

	public Button addButton(String name, String label, int x, int y, int w, int h, Color fg, Color bg, Color act) {
		Button b = cp5.addButton(name).setPosition(x, y);

		setControlerColors(b, fg, bg, act);

		if (x > 0 && y > 0)
			b.setSize(w, h);
		if (label != null)
			b.setLabel(label);

		return b;
	}

	@SuppressWarnings("rawtypes")
	private <T extends Controller> void setControlerColors(T c, Color fg, Color bg, Color act) {
		if (fg == null)
			fg = this.APP_COLOR_FG;
		c.setColorForeground(fg.getRGB());

		if (bg == null)
			bg = this.APP_COLOR_BG;
		c.setColorBackground(bg.getRGB());

		if (act == null)
			act = this.APP_COLOR_ACT;
		c.setColorActive(act.getRGB());
	}

	public ScrollableList addScrollableList(String name, String label, List<String> items, int defItem, int x, int y,
			int w, int h, int barH, int itemH) {
		return addScrollableList(name, label, items, defItem, x, y, w, h, barH, itemH, APP_COLOR_FG, APP_COLOR_BG,
				APP_COLOR_ACT);
	}

	public ScrollableList addScrollableList(String name, String label, List<String> items, int defItem, int x, int y,
			int w, int h, int barH, int itemH, Color fg, Color bg, Color act) {
		ScrollableList l = cp5.addScrollableList(name).setPosition(x, y);

		if (items != null)
			l.addItems(items);

		if (barH > 0)
			l.setBarHeight(barH);
		if (itemH > 0)
			l.setItemHeight(itemH);

		if (defItem < 0)
			defItem = 0;
		l.setValue(defItem);

		setControlerColors(l, fg, bg, act);

		if (x > 0 && y > 0)
			l.setSize(w, h);
		if (label != null)
			l.setLabel(label);

		return l;
	}

	public Slider addSlider(String name, String label, int x, int y, float min, float max, float def) {
		return addSlider(name, label, x, y, 0, 0, min, max, def, SLIDER_COLOR_FG, SLIDER_COLOR_BG, SLIDER_COLOR_ACT);
	}

	public Slider addSlider(String name, String label, int x, int y, int h, int w, float min, float max, float def) {
		return addSlider(name, label, x, y, h, w, min, max, def, SLIDER_COLOR_FG, SLIDER_COLOR_BG, SLIDER_COLOR_ACT);
	}

	public Slider addSlider(String name, String label, int x, int y, int h, int w, float min, float max, float def,
			Color fg, Color bg, Color act) {
		Slider s = cp5.addSlider(name).setCaptionLabel(label).setPosition(x, y).setRange(min, max).setValue(def);

		s.setColorForeground(fg.getRGB());
		s.setColorBackground(bg.getRGB());
		s.setColorActive(act.getRGB());

		if (h > 0 && w >= 0)
			s.setSize(h, w);
		return s;
	}

	public ColorWheel addColorWheel(String name, int x, int y, int d) {
		ColorWheel colorWheel = cp5.addColorWheel(name, x, y, d);
		colorWheel.setPosition(x, y);
		return colorWheel;
	}

	public Toggle addToggle(String name, String label, int x, int y, int w, int h, Color fg, Color bg, Color act) {
		Toggle t = cp5.addToggle(name).setPosition(x, y);

		setControlerColors(t, fg, bg, act);

		if (x > 0 && y > 0)
			t.setSize(w, h);
		if (label != null)
			t.setLabel(label);

		return t;
	}

	public Controller<?> getController(String name) {
		return cp5.getController(name);
	}

	public <T> T get(Class<T> class1, String name) {

		return cp5.get(class1, name);
	}
}
