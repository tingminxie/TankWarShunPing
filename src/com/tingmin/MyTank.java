package com.tingmin;

import java.awt.event.KeyEvent;

import com.tingmin.TankFather.Direction;
import com.tingmin.TankFather.OwnColor;
import com.tingmin.TankFather.Type;

public class MyTank extends TankFather {
  boolean stop = true;
  boolean keyUp,keyDown,keyRight,keyLeft;
  public MyTank(int x,int y,Direction dir, Type type,OwnColor ownColor){
    super(x,y,dir,type,ownColor);
  }
  public MyTank(int x, int y, Direction dir, Type type, OwnColor ownColor, int round) {
    super(x, y, dir, type, ownColor);
    this.round = round;
  }
  public void setPanel(MainPanel panel) {
    this.panel = panel; 
  }
  public void setPanel2(PanelRound2 panel2){
    this.panel2 = panel2;
  }
  /*public MyTank(int x, int y, Direction dir, Type type, OwnColor ownColor, MainPanel panel,int round) {
  super(x, y, dir, type, ownColor);
  this.panel = panel;
  this.round = round;
  }
  public MyTank(int x, int y, Direction dir, Type type, OwnColor ownColor, PanelRound2 panel,int round) {
    super(x, y, dir, type, ownColor);
    this.panel2 = panel;
    this.round = round;
    }*/
  public void keyPressed(KeyEvent e) {
    
      int keyCode = e.getKeyCode();
      switch (keyCode) {
      case KeyEvent.VK_UP:
        locateDir(Direction.UP);
        keyUp = true;
        break;
      case KeyEvent.VK_RIGHT:
        locateDir(Direction.R);
        keyRight = true;
        break;
      case KeyEvent.VK_DOWN:
        locateDir(Direction.DOWN);
        keyDown = true;
        break;
      case KeyEvent.VK_LEFT:
        locateDir(Direction.LEFT);
        keyLeft = true;
        break;
      }
      if(keyCode == KeyEvent.VK_SPACE) {
        fire();
      }
   }
  public void keyReleased(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_UP) {
      if(keyUp && !keyRight && !keyDown && !keyLeft) {
      stop = true;
      }
      keyUp = false;
    }else if (keyCode == KeyEvent.VK_DOWN) { 
      if(!keyUp && !keyRight && keyDown && !keyLeft) {
      stop = true;
      }
      keyDown = false;
    }else if (keyCode == KeyEvent.VK_RIGHT) {
      if(!keyUp && keyRight && !keyDown && !keyLeft) {
      stop = true;
      }
      keyRight = false;
    }else if (keyCode == KeyEvent.VK_LEFT) {
      if(!keyUp && !keyRight && !keyDown && keyLeft) {
      stop = true;
      }
      keyLeft = false;
    }
  }
 
 public void locateDir(Direction dir) {
   stop = false;
   setDir(dir);
   
 }
  public void autoMove() {
    if(stop) {
      return;
    }else {
      move();
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
      Missiles m = null;
	    if(this.round == 1){
	      m = new Missiles(x,y,dir,this.panel);
	    }else if (this.round == 2) {
	      m = new Missiles(x,y,dir,this.panel2);
	    }
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
    	  //System.out.println("round 1 panel:" + panel);
          this.collideWith(panel.enemyTanks);
        //if(panel.myTankLife > 0 && panel.myTanks.get(0).isLive()) {
           // this.collideWith(panel.enemyTanks);
          //this.collideWith(panel.myTanks.get(0));
        //}
      }else if (round == 2) {
        this.collideWith(panel2.enemyTanks);
       // if(panel2.myTankLife > 0 && panel2.myTanks.get(0).isLive()) {
           //this.collideWith(panel2.enemyTanks);
          //this.collideWith(panel2.myTanks.get(0));
       // }

      }
    }
}
