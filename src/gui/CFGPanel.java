package gui;

import java.awt.Graphics;
import javax.swing.*;
import java.util.*;

import cfg.CFG;
import cfg.Node;
import cfg.Const;

@SuppressWarnings("serial")
public class CFGPanel extends JPanel {
	
	private HashMap<Integer,ArrayList<CFG>> graphs_dom;
	private HashMap<Integer,ArrayList<CFG>> graphs_postdom;
	private CFG curr;
	private int count;
	private Random r;
	
	
	public CFGPanel() {
		graphs_dom = new HashMap<Integer, ArrayList<CFG>>();
		graphs_postdom = new HashMap<Integer, ArrayList<CFG>>();
		curr = new CFG();
		count = 0;
		r = new Random();
		for(int i = 0; i < Const.LEVELS; i++) {
			graphs_dom.put(i+1, new ArrayList<CFG>());
			graphs_postdom.put(i+1, new ArrayList<CFG>());
		}
	}
	
	public CFG getCFG() {
		return curr;
	}
	
	public void init() {
		for(int i = 1; i <= Const.NUM_CFGS; i++) {
			CFG c = CFG.readFromFile(Const.DEFAULT_PATH + "cfg" + i + ".txt");
			for(int j = 1; j <= Const.LEVELS; j++) {
				if(c.hasLevel(j, true))
					graphs_dom.get(j).add(c);
				if(c.hasLevel(j, false))
					graphs_postdom.get(j).add(c);
			}
					
		}
		
	}
	
	public Node randomNode() {
		
		int level = r.nextInt(Const.LEVELS);
		ArrayList<CFG> cfgs;
		if(r.nextBoolean())
			cfgs = graphs_dom.get(level+1);
		else
			cfgs = graphs_postdom.get(level+1);
		int cfg = r.nextInt(cfgs.size());
		curr = cfgs.get(cfg);
		return curr.randomNode();
	}
	
	public Node randomNode(int level, boolean isDom) {
		ArrayList<CFG> cfgs;
		if(isDom)
			cfgs = graphs_dom.get(level);
		else
			cfgs = graphs_postdom.get(level);
		
		if(cfgs.size() < 1)
			throw(new Error("No cfgs with that level"));
		
		int cfg = r.nextInt(cfgs.size());
		curr = cfgs.get(cfg);
		return curr.randomNode(level, isDom);
		
	}
	
	public void redraw() {
		Graphics g = getGraphics();
		g.fillRect(0, 0, Const.PANEL_X, Const.PANEL_Y);
		if(curr != null)
			curr.draw(g);	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(curr != null)
			curr.draw(g);
	}	
}
