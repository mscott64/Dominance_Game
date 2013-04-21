package gui;

import java.applet.*;
import java.awt.*;

import cfg.CFG;
import cfg.Const;

public class TestApplet extends Applet {
	private static final long serialVersionUID = 1L;

	public void init() {
		setSize(Const.PANEL_X, Const.PANEL_Y);
		setBackground(Color.black);
	}
	
	public void paint(Graphics g) {
		CFG c = CFG.readFromFile("cfg12.txt");
		if(c != null)
			c.draw(g);
	}
	
	
}
