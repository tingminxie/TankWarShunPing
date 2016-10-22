package com.tingmin;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
import java.awt.Rectangle;


public class TankFather implements Runnable {

  protected int x;
  protected int y;
  protected MainPanel panel;

  enum Direction {UP,R,DOWN,LEFT};
  protected Direction dir;

  enum Type {GOOD,BAD};
  protected Type type;

  enum OwnColor {RED,BLUE};
  protected OwnColor ownColor;

  protected int speed = 3;
  protected boolean live = true;
  protected Vector<Missiles> missiles = new Vector<Missiles>();

  public TankFather(int x,int y,Direction dir, Type type,OwnColor ownColor) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.type = type;
    this.ownColor = ownColor;
  }

  public TankFather(int x, int y, Direction dir, Type type,OwnColor ownColor, MainPanel panel) {
    this(x,y,dir,type,ownColor);
    this.panel = panel;
  }

  public boolean isLive() {
    return live;
  }

  public void setLive(boolean live) {
    this.live = live;
  }

  public Vector<Missiles> getMissiles() {
  return missiles;
}

  public void setMissiles(Vector<Missiles> missiles) {
    this.missiles = missiles;
  }

  public void run() {

  }

  public Rectangle getRect() {
   Rectangle r = null;
    switch (dir) {
    case UP:
    case DOWN:
      r = new Rectangle(x,y,20,30);
      break;
    case LEFT:
    case R:
      r = new Rectangle(x,y,30,20);
      break;
    }
    return r;
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
    if (x<0) {
      x = 0;
    }
    if (y<0) {
      y = 0;
    }
    if ((x + 30) >800) {
      x = 770;
    }
    if ((y + 55) >600) {
      y = 545;
    }
  }

  public Missiles fire() {

    int x = this.x;
    int y = this.y;
    
    switch(dir) {
    case UP:
      x += 7;
      y -= 8;
      break;
    case R:
      x += 27;
      y += 12;
      break;
    case DOWN:
      x += 7;
      y += 32;
      break;
    case LEFT:
      x -= 13;
      y += 12;
      break;
    } 
    Missiles m = new Missiles(x,y,dir,this.panel);//TODO
    missiles.add(m);
    new Thread(m).start();
    return m;
    
  }
  
    
  
}
/*
class MyTank extends TankFather {
  MainPanel panel;
  public MyTank(int x, int y, Direction dir, Type type,OwnColor ownColor, MainPanel panel) {
    super(x,y,dir,type,ownColor);
    this.panel = panel;
  }
}*/

class EnemyTank extends TankFather implements Runnable {  /////////////////////////////////////////////
  private static Random r = new Random();
  private int step = 5;

  public EnemyTank(int x, int y, Direction dir, Type type,OwnColor ownColor, MainPanel panel) {
    super(x,y,dir,type,ownColor);
    this.panel = panel;
  }
  /////////////////////////////////////////
  public void run() { 
    while(live) {
      try {
        Thread.sleep(50);
      }catch(Exception e) {
        e.printStackTrace();
      }
      randomDir();
      move();
    }
  }
  /////////////////////////////////////////
  public void randomDir () { 
    Direction[] dirs = Direction.values();
    if(step == 0) {
      step = r.nextInt(10) + 5;
      int n = r.nextInt(dirs.length-1);
      dir = dirs[n];
      fire();//TODO
    }else {
    step--;
    }
  }

//  public Missiles fire() {
//    if (missiles.size()<3) {
//      return super.fire();
//    }
//    return null;
//  }
  
}
