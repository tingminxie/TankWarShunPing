package com.tingmin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import com.tingmin.TankFather.Direction;

public class Missiles implements Runnable {
  int x;
  int y;
  int speed = 5;
  Direction dir;
  MainPanel panel;
  private boolean live = true;
  public static final int WIDTH = 5;
  public static final int HEIGHT = 5;

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
  public Missiles(int x, int y,Direction dir,MainPanel panel) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.panel = panel;
  }
  public Rectangle getRect() {
    return new Rectangle(x,y,WIDTH,HEIGHT);
  }
  public boolean hitMissile(Vector<Missiles> missiles) {
    for(int i=0;i<missiles.size();i++){
      Missiles m = missiles.get(i);
      if(this.isLive() && m.isLive() && this != m && this.getRect().intersects(m.getRect())) {
        this.setLive(false);
        m.setLive(false);
        return true;
      }
    }
    
    return false;
  }
 
  public boolean hitTank(TankFather t) {
    if (this.isLive() && t.isLive() && this.getRect().intersects(t.getRect())) {
      this.setLive(false);
      t.setLive(false);
      Bomb b = new Bomb(t.x,t.y,this.panel);
      this.panel.bombs.add(b);
      return true;
    }
    return false;
  }
  public void hitTanks(Vector<EnemyTank> tanks) {
    for (int i=0;i<tanks.size();i++) {
      TankFather tmp = tanks.get(i);
      hitTank(tmp);
    }
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
    g.fillOval(x,y,WIDTH,HEIGHT);
//    g.drawRect(x,y,WIDTH,HEIGHT);
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
