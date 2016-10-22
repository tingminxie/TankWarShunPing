package com.tingmin;

import java.awt.Color;
import java.awt.Graphics;


public class TankFather {

	int x;
	int y;
  enum Direction {UP,RU,R,RD,DOWN,LD,LEFT,LU};
  protected Direction dir;
	enum Type {GOOD,BAD};
	protected Type type;
  enum OwnColor {RED,BLUE};
  protected OwnColor ownColor;
  int speed = 5;

	public TankFather(int x,int y,Direction dir, Type type,OwnColor ownColor) {
		this.x = x;
		this.y = y;
    this.dir = dir;
    this.type = type;
    this.ownColor = ownColor;
	}
	public Type getType() {
	return type;
  }
  public void setType(Type type) {
    this.type = type;
  }
  public int getSpeed() {
    return speed;
  }
  public void setSpeed(int speed) {
    this.speed = speed;
  }
	public Direction getDir() {
		return dir;
	}
	public void setDir(Direction dir) {
		this.dir = dir;
	}
	public int getX() {
		return x;
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
	public void draw(Graphics g) {
		
	}
  public void move() {
  }
  public Missiles fire() {
	  return null;
  }
	
}
class MyTank extends TankFather {
	private MainPanel panel;

	public MyTank(int x, int y, Direction dir, Type type, OwnColor ownColor, MainPanel panel) {
		super(x,y,dir,type,ownColor);
		this.panel = panel;
	}

	public void draw(Graphics g) {//TODO 8 pictures
		Color c = g.getColor();
//    if (type == Type.GOOD) {
    switch(type) {
    case GOOD:
      g.setColor(Color.red);
      break;
    case BAD:
      g.setColor(Color.blue);
    }

		switch (dir) {
		case UP:
			g.fill3DRect(x, y, 5, 30,true);
			g.draw3DRect(x+5, y+5, 10, 20,true);
			g.fill3DRect(x+15, y, 5, 30,true);
			g.setColor(Color.BLACK);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y-5);
			break;
		case R:
			g.fill3DRect(x-5, y+5, 30, 5,true);
			g.draw3DRect(x, y+10, 20, 10,true);
			g.fill3DRect(x-5, y+20, 30, 5,true);
			g.setColor(Color.BLACK);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+35, y+15);
			break;
		case DOWN:
			g.fill3DRect(x, y, 5, 30,true);
			g.draw3DRect(x+5, y+5, 10, 20,true);
			g.fill3DRect(x+15, y, 5, 30,true);
			g.setColor(Color.BLACK);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y+35);
			break;
		case LEFT:
			g.fill3DRect(x-5, y+5, 30, 5,true);
			g.draw3DRect(x, y+10, 20, 10,true);
			g.fill3DRect(x-5, y+20, 30, 5,true);
			g.setColor(Color.BLACK);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x-10, y+15);
			break;
		}
//		}
		g.setColor(c);
	}
		
  public void move() {
    switch (dir) {
    case UP:
      y -= speed;
      break;
    case R:
      x += speed;
      break;
    case DOWN:
      y += speed;
      break;
    case LEFT:
      x -= speed;
      break;
    }
  }
//TODO create class Missiles
  public Missiles fire() {
    int x = this.x + 7;
    int y = this.y + 12;
    Missiles m = new Missiles(x,y,dir);
    m.adjustFirePoint();
    panel.missiles.add(m);
    return m;
    
  }
	
}
/*
class EnemyTank extends TankFather {
	private MainPanel panel;

	public EnemyTank(int x, int y, Direction dir, Type type,OwnColor ownColor, MainPanel panel) {
		super(x,y,dir,type,ownColor);
		this.panel = panel;
	}

	public void draw(Graphics g) {//TODO 8 pictures
		Color c = g.getColor();
//    if (type == Type.BAD) {
    g.setColor(Color.blue);
		switch (dir) {
		case UP:
			g.fill3DRect(x, y, 5, 30,true);
			g.draw3DRect(x+5, y+5, 10, 20,true);
			g.fill3DRect(x+15, y, 5, 30,true);
			g.setColor(Color.BLACK);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y-5);
			break;
		case R:
			g.fill3DRect(x-5, y+5, 30, 5,true);
			g.draw3DRect(x, y+10, 20, 10,true);
			g.fill3DRect(x-5, y+20, 30, 5,true);
			g.setColor(Color.BLACK);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+35, y+15);
			break;
		case DOWN:
			g.fill3DRect(x, y, 5, 30,true);
			g.draw3DRect(x+5, y+5, 10, 20,true);
			g.fill3DRect(x+15, y, 5, 30,true);
			g.setColor(Color.BLACK);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y+35);
			break;
		case LEFT:
			g.fill3DRect(x-5, y+5, 30, 5,true);
			g.draw3DRect(x, y+10, 20, 10,true);
			g.fill3DRect(x-5, y+20, 30, 5,true);
			g.setColor(Color.BLACK);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x-10, y+15);
			break;
		}
//    }	
		g.setColor(c);
	}
		
  public void move() {
    switch (dir) {
    case UP:
      y -= speed;
      break;
    case R:
      x += speed;
      break;
    case DOWN:
      y += speed;
      break;
    case LEFT:
      x -= speed;
      break;
    }
  }
	
}*/
