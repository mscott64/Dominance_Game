package cfg;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Edge {
	ArrayList<Integer> xPoints;
	ArrayList<Integer> yPoints;
	private int nPoints;
	
	public Edge() {
		xPoints = new ArrayList<Integer>();
		yPoints = new ArrayList<Integer>();
		nPoints = 0;
	}
	
	public void addX(int x) {
		xPoints.add(x);
		nPoints++;
	}
	
	public void addY(int y) {
		yPoints.add(y);
	}
	
	public int[] getXPoints() {
		int[] array = new int[nPoints - 1];
		for(int i = 0; i < nPoints - 1; i++) {
			array[i] = xPoints.get(i);
		}
		return array;
	}
	
	public int[] getYPoints() {
		int[] array = new int[nPoints - 1];
		for(int i = 0; i < nPoints - 1; i++) {
			array[i] = yPoints.get(i);
		}
		return array;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		if(nPoints > 1 && yPoints.size() == nPoints) {
			int[] x = getXPoints();
			int[] y = getYPoints();
			g.drawPolyline(x, y, nPoints - 1);
			if(nPoints > 1)
			drawArrow((Graphics2D)g);
		}
	}
	
	private void drawArrow(Graphics2D g) {
		int x1 = xPoints.get(nPoints - 2);
		int y1 = yPoints.get(nPoints - 2);
		int x2 = xPoints.get(nPoints - 1);
		int y2 = yPoints.get(nPoints - 1);
		int dx = x2 - x1;
		int dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);
		
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] {len, len-Const.ARR_X, len-Const.ARR_X, len},
                new int[] {0, -Const.ARR_Y/2, Const.ARR_Y/2, 0}, 4);
		at.setToIdentity();
		g.setTransform(at);
	}

}
