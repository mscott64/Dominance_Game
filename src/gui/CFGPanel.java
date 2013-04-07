package gui;

import java.awt.Graphics;
import javax.swing.*;

import cfg.CFG;
import cfg.Const;

@SuppressWarnings("serial")
public class CFGPanel extends JPanel {
	
	private CFG c;
	
	public CFGPanel() {
		c = null;
	}
	
	public CFGPanel(CFG c) {
		this.c = c;
	}
	
	public void setCFG(CFG c) {
		this.c = c;
	}
	
	public CFG getCFG() {
		return c;
	}
	
	public void redraw() {
		Graphics g = getGraphics();
		g.fillRect(0, 0, Const.PANEL_X, Const.PANEL_Y);
		if(c != null)
			c.draw(g);	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(c != null)
			c.draw(g);
	}	
	
	@Override
	public void update(Graphics g) {
		if(c != null)
			c.draw(g);
	}
}
