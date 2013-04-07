package gui;

import java.awt.Color;
import javax.swing.*;

import cfg.CFG;

@SuppressWarnings("serial")
public class TestFrame extends JFrame {
	
	public TestFrame() {
		frameInit();
		setBackground(Color.black);
		setSize(SIZE_X, SIZE_Y);
		
		CFGPanel p = new CFGPanel();
		p.setCFG(CFG.readFromFile(DEFAULT_PATH + "cfg1.txt"));
		//p.addMouseListener(p);
		add(p);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TestFrame f = new TestFrame();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			}
		});
	}
	
	private static final int SIZE_X = 500;
	private static final int SIZE_Y = 650;
	private static final String DEFAULT_PATH = "C:\\Users\\Michelle\\workspace\\DominanceGame\\src\\CFG_Examples\\";
	
}
