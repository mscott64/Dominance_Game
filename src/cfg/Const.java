package cfg;

import java.awt.Font;

public interface Const {
	
	public static final int NODE_SIZE = 50;
	public static final int CEN = NODE_SIZE / 2;
	public static final int ARR_X = 12;
	public static final int ARR_Y = 8;
	
	/* CFG file reader constants */
	public enum Info {INIT, EDGE, CHILD, DOM, POSTDOM};
	public static final String NODES = "NODES";
	public static final String EDGES = "EDGES";
	public static final String CHILDREN = "CHILDREN";
	public static final String DOMINANCE = "DOMINANCE";
	public static final String POSTDOMINANCE = "POSTDOMINANCE";
	public static final String DEFAULT_PATH = "C:\\Users\\Michelle\\workspace\\DominanceGame\\resources\\";
	
	/* Window size */
	public static final int SIZE_X = 650;
	public static final int SIZE_Y = 650;
	public static final int PANEL_X = 500;
	public static final int PANEL_Y = 600;
	public static final int CEN_X = SIZE_X / 2;
	public static final int CEN_Y = SIZE_Y / 2;
	
	/* Buttons & Labels */
	public static final String SUB = "Submit"; 
	public static final String NEXT = "Next";
	public static final String SCORE = "Score";
	public static final String START = "Start";
	public static final String QUES1 = " Select the node(s) ";
	public static final String QUES2_DOM = "dominated";
	public static final String QUES2_PDOM = "postdominated";
	public static final String BY = " by node: ";
	public static final String QUIT = "Quit";
	public static final String EXIT = "Exit";
	
	public enum Mode {START, QUES, REV, QUIT};
	
	public static final Font F = new Font(Font.SERIF, Font.PLAIN, 22);
}
