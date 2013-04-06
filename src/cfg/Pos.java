package cfg;

public class Pos {

	private int x;
	private int y;
	
	public Pos() {
		x = 0;
		y = 0;
	}
	
	public Pos(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(Pos p) {
		int p_x = p.getX();
		int p_y = p.getY();
		return p_x == x && p_y == y;
	}
}
