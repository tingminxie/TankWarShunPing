package com.tingmin;

import javax.swing.*;



import java.awt.*;

public class TankWar01 extends JFrame{
	MyPanel p = null;
	public TankWar01(){
		p = new MyPanel();
		this.add(p);
		this.setSize(800,800);
		this.setLocation(100, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		new TankWar01();
	}

}
class MyPanel extends JPanel {
	Hero hero = null;
	int type;
	int direction;
	public MyPanel(){
		hero = new Hero(50,50);
		
		
	}
	public void paint(Graphics g) {
		super.paint(g);
		Color c = g.getColor();
		g.setColor(Color.green);
		g.fillRect(0,0,800,600);
		//hero.draw(g);
		drawTank(hero.getX(),hero.getY(),g,0,1);
		
		g.setColor(c);

	}
	public void drawTank(int x,int y,Graphics g,int direction,int type) {//TODO change to drawTank(Tank t,Graphics g)
		Color c = g.getColor();
		switch (type) {
			case 0: 
			g.setColor(Color.blue);
			break;
			case 1:
			g.setColor(Color.red);
			break;
		} 
		switch (direction) {
		case 0:
			g.fill3DRect(x, y, 5, 30,true);
			g.fill3DRect(x+5, y+5, 10, 20,true);
			g.fill3DRect(x+15, y, 5, 30,true);
			g.setColor(Color.BLACK);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y-5);
			break;
		}
		
		g.setColor(c);
	}
	
}

class Tank {
	int x = 0;
	int y = 0;
	
	public int getX() {
		return x;
	}
	public int getTankType() {
		return 0;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Tank(int x,int y) {
		this.x = x;
		this.y = y;
	}
}
class Hero extends Tank {
	

	public Hero(int x,int y) {
		super(x,y);
	}
	
	public void draw(Graphics g) {
		
		
		
	}
}