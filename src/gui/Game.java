package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import cfg.*;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import java.util.Random;

@SuppressWarnings("serial")
public class Game extends JFrame implements MouseListener, ActionListener {
	
	private CFGPanel p;
	private JTextArea console;
	private int points = 0;
	private Node curr;
	private HashSet<Node> selected = new HashSet<Node>();
	private JLabel pts;
	private JLabel node;
	private JLabel ques2;
	private boolean ques_dom = true;
	private Random r = new Random();
	private JButton next;
	private JButton submit;
	private Const.Mode mode = Const.Mode.QUES;
	
	private JPanel windows;
	private Container start;
	private Container game;
	private Container exit;
	
	public Game() {
		frameInit();
		setTitle("Dominance Game");
		setSize(Const.SIZE_X, Const.SIZE_Y);
		
		start = new Container();
		game = new Container();
		exit = new Container();
		
		windows = new JPanel(new CardLayout());
		windows.setBackground(Color.black);
		initStartWindow();
		
		windows.add(start, Const.BEGIN);
		windows.add(game, Const.START);
		windows.add(exit, Const.QUIT);
		
		setContentPane(windows);
		CardLayout cl = (CardLayout)windows.getLayout();
		cl.show(windows, Const.BEGIN);
	}
	
	private void initStartWindow() {
		start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("DOMINANCE");
		title.setFont(Const.F);
		title.setForeground(Color.red);
		
		JButton startButton = new JButton(Const.START);
		startButton.addActionListener(this);
		
		start.add(Box.createVerticalGlue());
		addCenter(title, start);
		start.add(Box.createVerticalGlue());
		addCenter(startButton, start);
		start.add(Box.createVerticalGlue());
	}

	private void initGameWindow() {
		GroupLayout g = new GroupLayout(game);
		game.setLayout(g);
		
		// Set up CFG display
		p = new CFGPanel(CFG.readFromFile(Const.DEFAULT_PATH + "cfg1.txt"));
		p.addMouseListener(this);
		curr = p.getCFG().randomNode();
		
		// Create game play panel
		JPanel play = new JPanel();
		play.setLayout(new BoxLayout(play, BoxLayout.Y_AXIS));
		submit = new JButton(Const.SUB);
		submit.addActionListener(this);
		next = new JButton(Const.NEXT);
		next.addActionListener(this);
		next.setEnabled(false);
		JButton quit = new JButton(Const.QUIT);
		quit.addActionListener(this);
		
		JLabel score = new JLabel(Const.SCORE);
		score.setFont(Const.F);
		pts = new JLabel(Integer.toString(points));
		pts.setFont(Const.F);
		JLabel ques1 = new JLabel(Const.QUES1);
		ques1.setFont(Const.F);
		ques2 = new JLabel(Const.QUES2_DOM);
		ques2.setFont(Const.F);
		ques2 = new JLabel(Const.QUES2_DOM);
		ques2.setFont(Const.F);
		JLabel ques3 = new JLabel(Const.BY);
		ques3.setFont(Const.F);
		node = new JLabel(curr.getLabel());
		node.setFont(Const.F);
		
		play.add(Box.createVerticalGlue());
		addCenter(ques1, play);
		addCenter(ques2, play);
		addCenter(ques3, play);
		play.add(Box.createRigidArea(new Dimension(0, 5)));
		addCenter(node, play);
		play.add(Box.createVerticalGlue());
		addCenter(submit, play);
		play.add(Box.createRigidArea(new Dimension(0, 10)));
		addCenter(next, play);
		play.add(Box.createRigidArea(new Dimension(0, 10)));
		addCenter(quit, play);
		play.add(Box.createVerticalGlue());
		addCenter(score, play);
		play.add(Box.createRigidArea(new Dimension(0, 5)));
		addCenter(pts, play);
		play.add(Box.createVerticalGlue());
		
		//Console creation
		console = new JTextArea("");
		console.setLineWrap(true);
		JScrollPane s = new JScrollPane(console);
		s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		s.setMaximumSize(new Dimension(Const.SIZE_X, 50));
		s.setMinimumSize(new Dimension(0, 15));
		
		// Setup group layout
		SequentialGroup hgroup = g.createSequentialGroup();
		SequentialGroup h1 = g.createSequentialGroup();
		h1.addComponent(p); h1.addComponent(play);
		ParallelGroup h2 = g.createParallelGroup();
		h2.addGroup(h1);
		h2.addComponent(s);
		hgroup.addGroup(h2);
		g.setHorizontalGroup(hgroup);
		
		SequentialGroup vgroup = g.createSequentialGroup();
		ParallelGroup v1 = g.createParallelGroup();
		v1.addComponent(p);
		v1.addComponent(play);
		vgroup.addGroup(v1);
		vgroup.addComponent(s);
		g.setVerticalGroup(vgroup);
		
		g.setAutoCreateGaps(false);
		g.setAutoCreateContainerGaps(false);
	}
	
	private void initExitWindow() {
		exit.setLayout(new BoxLayout(exit, BoxLayout.Y_AXIS));
		JLabel score = new JLabel(Const.SCORE);
		score.setFont(Const.F);
		score.setForeground(Color.red);
		JLabel points = new JLabel("" + this.points);
		points.setFont(Const.F);
		points.setForeground(Color.red);
		JButton exitButton = new JButton(Const.EXIT);
		exitButton.addActionListener(this);
		
		exit.add(Box.createVerticalGlue());
		addCenter(score, exit);
		addCenter(points, exit);
		exit.add(Box.createVerticalGlue());
		addCenter(exitButton, exit);
		exit.add(Box.createVerticalGlue());
	}
	
	private void addCenter(JComponent c, Container con) {
		c.setAlignmentX(Component.CENTER_ALIGNMENT);
		con.add(c);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Game g = new Game();
				g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				g.setVisible(true);
			}
		});
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		Node n = p.getCFG().getNode(new Pos(x, y));
		if(n != null) {
			if(mode == Const.Mode.QUES) {
				if(n.isSelected()) {	
					n.notSelected();
					n.draw(p.getGraphics(), Color.white);
					selected.remove(n);
				} else {
					n.selected();
					n.draw(p.getGraphics(), Color.gray);
					selected.add(n);
				}
			} else if(mode == Const.Mode.REV) {
				if(n.equals(curr) && ques_dom) {
					console.append("\n A node always dominates itself.");
				} else if(n.equals(curr) && !ques_dom) {
					console.append("\n A node never postdominates itself.");
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals(Const.SUB)) {
			if(ques_dom) 
				checkDominance();
			else
				checkPostdominance();
			pts.setText(Integer.toString(points));
			next.setEnabled(true);
			submit.setEnabled(false);
			selected.clear();
			mode = Const.Mode.REV;
		} else if(arg0.getActionCommand().equals(Const.NEXT)) {
			ques_dom = r.nextBoolean();
			if(ques_dom)
				ques2.setText(Const.QUES2_DOM);
			else
				ques2.setText(Const.QUES2_PDOM);
			curr = p.getCFG().randomNode();
			node.setText(curr.getLabel());
			p.redraw();
			next.setEnabled(false);
			submit.setEnabled(true);
			console.append("\n");
			mode = Const.Mode.QUES;
		} else if(arg0.getActionCommand().equals(Const.START)) {
			mode = Const.Mode.QUES;
			initGameWindow();
			CardLayout cl = (CardLayout)windows.getLayout();
			cl.show(windows, Const.START);
		} else if(arg0.getActionCommand().equals(Const.EXIT)) {
			System.exit(0);
		} else if(arg0.getActionCommand().equals(Const.QUIT)) {
			initExitWindow();
			CardLayout cl = (CardLayout)windows.getLayout();
			cl.show(windows, Const.QUIT);
		}
	}

	private void checkDominance() {
		for(Node n : selected) {
			n.notSelected();
			if(curr.isInDominance(n)) {
				points += 10;
				n.draw(p.getGraphics(), Color.green);
			} else {
				points -= 5;
				n.draw(p.getGraphics(), Color.red);
			}
		}
		for(Node n : curr.getDominance()) {
			if(!selected.contains(n))
				n.draw(p.getGraphics(), Color.yellow);
		}
		if(curr.getDominance().isEmpty() && selected.isEmpty()) {
			points += 25;
			console.append("\n" + "That's right! No nodes are dominated by node " + curr.getLabel());
		}
	}
	
	private void checkPostdominance() {
		for(Node n : selected) {
			n.notSelected();
			if(curr.isInPostdominance(n)) {
				points += 10;
				n.draw(p.getGraphics(), Color.green);
			} else {
				points -= 5;
				n.draw(p.getGraphics(), Color.red);
			}
		}
		for(Node n : curr.getPostdominance()) {
			if(!selected.contains(n))
				n.draw(p.getGraphics(), Color.yellow);
		}
		if(curr.getPostdominance().isEmpty() && selected.isEmpty()) {
			points += 25;
			console.append("\n" + "That's right! No nodes are postdominated by node " + curr.getLabel());
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
