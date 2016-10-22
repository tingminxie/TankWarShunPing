package com.tingmin;

import java.awt.event.KeyEvent;

import com.tingmin.TankFather.Direction;

public class MyTank extends TankFather {
  boolean stop = true;
  boolean flagUp = false;
  boolean flagDown = false;
  boolean flagR = false;
  boolean flagLEFT = false;
  
  public MyTank(int x, int y, Direction dir, Type type, OwnColor ownColor, MainPanel panel) {
	super(x, y, dir, type, ownColor, panel);
  }

  public void keyPressed(KeyEvent e) {
	  
      int keyCode = e.getKeyCode();
      switch (keyCode) {
      case KeyEvent.VK_UP:
        locateDir(Direction.UP);
        break;
      case KeyEvent.VK_RIGHT:
        locateDir(Direction.R);
        break;
      case KeyEvent.VK_DOWN:
        locateDir(Direction.DOWN);
        break;
      case KeyEvent.VK_LEFT:
        locateDir(Direction.LEFT);
        break;
      }
      if(keyCode == KeyEvent.VK_SPACE) {
        fire();
      }
   }
  public void keyReleased(KeyEvent e) {
    
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
  public void win() {
	  stop = true;
  }
  public void lose() {
	  
  }
}
