package com.tingmin;

import java.awt.Color;
import java.awt.Graphics;

import com.tingmin.TankFather.Direction;

public class Missiles implements Runnable {
	int x;
	int y;
  int speed = 50;
	Direction dir;
  private boolean live = true;
	public boolean isLive() {
	return live;
}
public void setLive(boolean live) {
	this.live = live;
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
	public Missiles(int x, int y,Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
  public void run() {
    while(true) {
      try {
        Thread.sleep(50);
      } catch(Exception e) {
        e.printStackTrace();
      }
      move();
      if(x<0 || x>800 || y<0 || y>600) {
    	  live = false;
          break;
        }
    }
    
    
  }
  public void draw(Graphics g) {
    Color c = g.getColor();
    g.setColor(Color.black);
    g.fillOval(x,y,5,5);
    g.setColor(c);
  }
  public void move() {
	  switch(dir) {
      case UP:
        y-=speed;
        break;
      case R:
        x+=speed;
        break;
      case DOWN:
        y+=speed;
        break;
      case LEFT:
        x-=speed;
        break;
      }
      
  }
	
}
