package com.tingmin;

import java.awt.Color;
import java.awt.Graphics;

import com.tingmin.TankFather.Direction;

public class Missiles {
	int x;
	int y;
  int speed = 100;
	Direction dir;
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
  public void draw(Graphics g) {
    Color c = g.getColor();
    g.setColor(Color.black);
    g.fillOval(x,y,5,5);
    g.setColor(c);
    move();
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
  public void adjustFirePoint() {
    switch(dir) {
    case UP:
      y -= 20;
      break;
    case R:
      x += 20;
      break;
    case DOWN:
      y += 20;
      break;
    case LEFT:
      x -= 20;
      break;
    } 
  }
	
}
