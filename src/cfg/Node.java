package cfg;

import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Node {
	
	private Pos pos;
	private ArrayList<Node> children;
	private String label;
	private ArrayList<Node> dom;
	private ArrayList<Node> postdom;
	private boolean isSelected;
	
	public Node() {
		pos = new Pos();
		children = new ArrayList<Node>();
		label = "";
		dom = new ArrayList<Node>();
		postdom = new ArrayList<Node>();
		isSelected = false;
	}
	
	public Node(String label, int x, int y) {
		pos = new Pos(x, y);
		children = new ArrayList<Node>();
		this.label = label;
		dom = new ArrayList<Node>();
		postdom = new ArrayList<Node>();
		isSelected = false;
	}
	
	public Node(String label, int x, int y, ArrayList<Node> children, 
			ArrayList<Node> dom, ArrayList<Node> postdom) {
		pos = new Pos(x, y);
		this.children = children;
		this.label = label;
		this.dom = dom; 
		this.postdom = postdom;
		isSelected = false;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void selected() {
		isSelected = true;
	}
	
	public void notSelected() {
		isSelected = false;
	}
	
	public int getX() {
		return pos.getX();
	}
	
	public int getY() {
		return pos.getY();
	}
	
	public Pos getPosition() {
		return pos;
	}
	
	public void setXPosition(int x) {
		pos.setX(x);
	}
	
	public void setYPosition(int y) {
		pos.setY(y);
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}
	
	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
	
	public void addChild(Node n) {
		children.add(n);
	}
	
	public void removeChild(Node n) {
		children.remove(n);
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public ArrayList<Node> getDominance() {
		return dom;
	}
	
	public void setDominance(ArrayList<Node> dom) {
		this.dom = dom;
	}
	
	public void addDominance(Node n) {
		dom.add(n);
	}
	
	public void removeDominance(Node n) {
		dom.remove(n);
	}
	
	public boolean isInDominance(Node n) {
		return dom.contains(n);
	}
	
	public ArrayList<Node> getPostdominance() {
		return postdom;
	}
	
	public void setPostdominance(ArrayList<Node> postdom) {
		this.postdom = postdom;
	}
	
	public void addPostdominance(Node n) {
		postdom.add(n);
	}
	
	public void removePostdominance(Node n) {
		postdom.remove(n);
	}
	
	public boolean isInPostdominance(Node n) {
		return postdom.contains(n);
	}
	
	public boolean equals(Node n) {
		Pos n_pos = n.getPosition();
		String n_label = n.getLabel();
		return n_pos.equals(pos) && n_label.equals(label);
	}
	
	public boolean inBounds(Pos p) {
		int x_low = pos.getX();
		int x_high = pos.getX() + Const.NODE_SIZE;
		
		int y_low = pos.getY();
		int y_high = pos.getY() + Const.NODE_SIZE;
		
		return x_low < p.getX() && p.getX() < x_high && y_low < p.getY() && p.getY() < y_high;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillOval(pos.getX(), pos.getY(), Const.NODE_SIZE, Const.NODE_SIZE);
		for(Node n: children) // draws well-behaved edges
			drawArrow(n, g2);
		g2.setColor(Color.black);
		Pos s_pos = getStringPosition(g2);
		g2.drawString(label, s_pos.getX(), s_pos.getY());
	}
	
	public void draw(Graphics g, Color c_node) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(c_node);
		g2.fillOval(pos.getX(), pos.getY(), Const.NODE_SIZE, Const.NODE_SIZE);
		g2.setFont(Const.F);
		g2.setColor(Color.black);
		Pos s_pos = getStringPosition(g2);
		g2.drawString(label, s_pos.getX(), s_pos.getY());
	}
	
	@Override
	public String toString() {
		return label;
	}
	
	
	
	private Pos getStringPosition(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(label, g);
		int x = pos.getX() + Const.CEN - (int)r.getWidth()/2;
		int y = pos.getY() + Const.CEN + fm.getDescent();
		return new Pos(x, y);
	}
	
	private void drawArrow(Node n, Graphics2D g) {
		int n_x, n_y, x, y;
		if(n.getY() == pos.getY()) { // horizontal line
			n_x = n.getX();
			n_y = n.getY() + Const.CEN;
			x = pos.getX() + Const.NODE_SIZE;
			y = pos.getY() + Const.CEN;
		} else {
			n_x = n.getX() + Const.CEN;
			n_y = n.getY();
			x = pos.getX() + Const.CEN;
			y = pos.getY() + Const.NODE_SIZE;
		}
		
		int dx = n_x - x;
		int dy = n_y - y;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);
		
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] {len, len-Const.ARR_X, len-Const.ARR_X, len},
                new int[] {0, -Const.ARR_Y/2, Const.ARR_Y/2, 0}, 4);
		at.setToIdentity();
		g.setTransform(at);
	}
}
