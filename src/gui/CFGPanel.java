package gui;

import java.awt.Graphics;
import javax.swing.*;
import java.util.*;

import cfg.*;

@SuppressWarnings("serial")
public class CFGPanel extends JPanel {
	
	private HashMap<Integer,ArrayList<Question>> ques;
	private HashSet<Question> questions;
	private CFG curr;
	private Random r;
	
	public CFGPanel() {
		ques = new HashMap<Integer, ArrayList<Question>>();
		questions = new HashSet<Question>();
		curr = new CFG();
		r = new Random();
		for(int i = 1; i <= Const.LEVELS; i++) 
			ques.put(i, new ArrayList<Question>());
	}
	
	public CFG getCFG() {
		return curr;
	}
	
	public void reset() {
		for(Question q : questions) { 
			ques.get(q.getLevel()).add(q);
		}
		questions.clear();
	}
	
	public void init() {
		for(int i = 1; i <= Const.NUM_CFGS; i++) {
			CFG c = CFG.readFromFile(Const.DEFAULT_PATH + "cfg" + i + ".txt");
			for(int j = 1; j <= Const.LEVELS; j++) {
				ArrayList<Node> dom = c.getDom(j);
				for(Node n : dom) {
					Question q = new Question(c, n, Const.IS_DOM, j);
					ques.get(j).add(q);
				}
				ArrayList<Node> postdom = c.getPostdom(j);
				for(Node n : postdom) {
					Question q = new Question(c, n, Const.IS_POSTDOM, j);
					ques.get(j).add(q);
				}
			}
		}
		
	}
	
	public Question randomQuestion(int level) {
		ArrayList<Question> ques;
		ques = this.ques.get(level);
		
		if(ques.size() < 1)
			throw(new Error("No cfgs with that level"));
		
		Question q = ques.remove(r.nextInt(ques.size()));
		questions.add(q);
		curr = q.getCfg();
		return q;
	}
	
	public void redraw() {
		Graphics g = getGraphics();
		g.fillRect(0, 0, Const.PANEL_X, Const.PANEL_Y);
		if(curr != null)
			curr.draw(g);	
	}
	
	public void printStats() {
		for(int i = 1; i <= Const.LEVELS; i++)
			System.out.println("Level " + i + " has " + ques.get(i).size() + " questions.");
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(curr != null)
			curr.draw(g);
	}	
}
