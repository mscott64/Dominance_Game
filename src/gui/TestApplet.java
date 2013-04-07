package gui;

import java.applet.*;
import java.awt.*;

import cfg.CFG;

public class TestApplet extends Applet {
	private static final long serialVersionUID = 1L;

	public void init() {
		setSize(SIZE_X, SIZE_Y);
		setBackground(Color.black);
	}
	
	public void paint(Graphics g) {
		CFG c = CFG.readFromFile(DEFAULT_PATH + "cfg1.txt");
		if(c != null)
			c.draw(g);
	}
	
	private static final int SIZE_X = 300;
	private static final int SIZE_Y = 650;
	private static final String DEFAULT_PATH = "C:\\Users\\Michelle\\workspace\\DominanceGame\\src\\CFG_Examples\\";
}
