package gui;

import cfg.*;

public class Question {
	
	private CFG cfg;
	private Node node;
	private boolean isDom;
	private int level;
	
	public Question() {
		cfg = null;
		node = null;
		isDom = false;
		level = -1;
	}
	
	public Question(CFG cfg, Node node, boolean isDom, int level) {
		this.cfg = cfg;
		this.node = node;
		this.isDom = isDom;
		this.level = level;
	}
	
	public CFG getCfg() {
		return cfg;
	}
	
	public void setCFG(CFG c) {
		cfg = c;
	}
	
	public Node getNode() {
		return node;
	}
	
	public void setNode(Node n) {
		node = n;
	}
	
	public boolean isDom() {
		return isDom;
	}
	
	public void setDom(boolean d) {
		isDom = d;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int l) {
		level = l;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		
		if(o == null || o.getClass() != this.getClass())
			return false;
		
		Question q = (Question) o;
		return q.getCfg().equals(cfg) && q.getNode().equals(node) && q.isDom() == isDom;
	}

}
