package com.tingmin;

import java.io.Serializable;
import java.util.Random;

import javax.swing.JPanel;

import com.tingmin.TankFather.Direction;
import com.tingmin.TankFather.OwnColor;
import com.tingmin.TankFather.Type;

public class EnemyTank extends TankFather implements Runnable , Serializable{
  
  private static Random r = new Random();
  private int step = 5;
  protected Direction oldDir;
  MainPanel panel;
  PanelRound2 panel2;
  int round;
  public EnemyTank(int x, int y, Direction dir, Type type,OwnColor ownColor) {
    super(x,y,dir,type,ownColor);
  }
  public EnemyTank(int x, int y, Direction dir, Type type,OwnColor ownColor, int round) {
	    super(x,y,dir,type,ownColor);
	    this.oldDir = dir;
	    this.round = round;
  }
  public void setPanel(MainPanel panel) {
	  this.panel = panel; 
  }
  public void setPanel2(PanelRound2 panel2){
	  this.panel2 = panel2;
  }
 /* public EnemyTank(int x, int y, Direction dir, Type type,OwnColor ownColor, MainPanel panel, int round) {
    super(x,y,dir,type,ownColor);
    this.panel = panel;
    this.oldDir = dir;
    this.round = round;
  }
  public EnemyTank(int x, int y, Direction dir, Type type,OwnColor ownColor, PanelRound2 panel, int round) {
	    super(x,y,dir,type,ownColor);
	    this.panel2 = panel2;
	    this.oldDir = dir;
	    this.round = round;
	  }*/
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
  if (pause) {
    dir = oldDir;
    return;
  }else {
    Direction[] dirs = Direction.values();
    if(step == 0) {
      step = r.nextInt(10) + 5;
      int n = r.nextInt(dirs.length);
      dir = dirs[n];
      fire();
    }else {
    step--;
    }
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
	    Missiles m = new Missiles(x,y,dir,this.panel);
	    missiles.add(m);
	    new Thread(m).start();
	    return m;
	    
	  }
  public void move() {
	    this.oldX = x;
	    this.oldY = y;
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
	    if (x<=0) {
	      x = 0;
	    }
	    if (y<=-5) {
	      y = -5;
	    }
	    if ((x + 20) >=800) {
	      x = 780;
	    }
	    if ((y + 25) >=600) {
	      y = 575;
	    }
	//TODO
	    if(round == 1) {
	    	
        this.collideWith(panel.enemyTanks);
        if(panel.myTankLife > 0) {
          this.collideWith(panel.myTanks.get(0));
        }
      }else
      if(round == 2) {
    	  System.out.println("round in panel2: " + round);
    	  System.out.println("panel2.enemyTankssize:"+ panel2.enemyTanks.size());
        this.collideWith(panel2.enemyTanks);
        if(panel2.myTankLife > 0) {
          this.collideWith(panel2.myTanks.get(0));
        }
      }
	  }

    //  public Missiles fire() {
//    if (missiles.size()<3) {
//      return super.fire();
//    }
//    return null;
    //  }
  
    }


