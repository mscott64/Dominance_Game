package cfg;

import java.awt.Graphics;
import java.io.*;
import java.util.*;

public class CFG {
	
	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	private HashMap<Integer, ArrayList<Node>> dom;
	private HashMap<Integer, ArrayList<Node>> postdom;
	
	public CFG() {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		dom = new HashMap<Integer, ArrayList<Node>>();
		postdom = new HashMap<Integer, ArrayList<Node>>();
		for(int i = 1; i <= Const.LEVELS; i++) {
			dom.put(i, new ArrayList<Node>());
			postdom.put(i, new ArrayList<Node>());
		}
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
	
	public void addDom(Node n, int level) {
		dom.get(level).add(n);
	}
	
	public void addPostdom(Node n, int level) {
		postdom.get(level).add(n);
	}
	
	public ArrayList<Node> getDom(int level) {
		return dom.get(level);
	}
	
	public ArrayList<Node> getPostdom(int level) {
		return postdom.get(level);
	}
	
	public Node getNode(String label) {
		for(Node n : nodes) {
			if(n.getLabel().equals(label))
				return n;
		}
		return null;
	}
	
	public Node getNode(Pos p) {
		for(Node n: nodes) {
			if(n.inBounds(p))
				return n;
		}
		return null;
	}
	
	public void draw(Graphics g) {
		g.setFont(Const.F);
		for (Node n : nodes) 
			n.draw(g);
		for (Edge e : edges)
			e.draw(g);
	}
	
	public static CFG readFromFile(String filename) {
		CFG c = new CFG();
		String line;
		BufferedReader br;
		int loc = 0;
		Const.Info i = Const.Info.INIT;
		Node n = null;
		Edge e = null;
		try {
			InputStream in = CFG.class.getClassLoader().getResourceAsStream(filename);
			if(in == null) throw(new IOException());
			br = new BufferedReader(new InputStreamReader(in));
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				if(i == Const.Info.INIT) 
					n = new Node();
				if(i == Const.Info.EDGE)
					e = new Edge();
				
				while(st.hasMoreTokens()) {
					String tok = st.nextToken();
					
					// Signal words to change info type
					if(tok.equalsIgnoreCase(Const.NODES)) {
						i = Const.Info.INIT;
						continue;
					} else if(tok.equalsIgnoreCase(Const.EDGES)) {
						i = Const.Info.EDGE;
						continue;
					} else if(tok.equalsIgnoreCase(Const.CHILDREN)) {
						i = Const.Info.CHILD;
						continue;
					} else if(tok.equalsIgnoreCase(Const.DOMINANCE)) {
						i = Const.Info.DOM;
						continue;
					} else if(tok.equalsIgnoreCase(Const.POSTDOMINANCE)) {
						i = Const.Info.POSTDOM;
						continue;
					}
					
					switch(i) {
					case INIT:
						if(loc == 0) {
							n.setLabel(tok);
						} else if(loc == 1) {
							n.setXPosition(Integer.parseInt(tok));
						} else if(loc == 2) {
							n.setYPosition(Integer.parseInt(tok));
						} 
						loc++;
						break;
					case EDGE:
						if(loc % 2 == 0) {
							e.addX(Integer.parseInt(tok));
						} else {
							e.addY(Integer.parseInt(tok));
						}
						loc++;
						break;
					case CHILD:
						if(loc == 0) {
							n = c.getNode(tok);
						} else {
							n.addChild(c.getNode(tok));
						}
						loc++;
						break;
					case DOM:
						if(loc == 0) {
							n = c.getNode(tok);
						} else if(loc == 1) {
							c.addDom(n, Integer.parseInt(tok));
						} else {
							n.addDominance(c.getNode(tok));
						}
						loc++;
						break;
					case POSTDOM:
						if(loc == 0) {
							n = c.getNode(tok);
						} else if(loc == 1) {
							c.addPostdom(n, Integer.parseInt(tok));
						} else {
							n.addPostdominance(c.getNode(tok));
						}
						loc++;
						break;
					default:
							
					}					
				}
				
				if(i == Const.Info.INIT && loc == 3) 
					c.addNode(n);
				if(i == Const.Info.EDGE && loc > 1)
					c.addEdge(e);
				loc = 0;
			}
			br.close();
		} catch (IOException error) {
			System.out.println("Unable to read file.");
			return c;
		}
		
		return c;
	}
}
