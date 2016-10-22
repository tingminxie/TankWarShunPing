package com.tingmin;

import java.io.Serializable;
import java.util.Random;

import com.tingmin.TankFather.Direction;
import com.tingmin.TankFather.OwnColor;
import com.tingmin.TankFather.Type;

public class EnemyTank extends TankFather implements Runnable , Serializable{
	
		  private static Random r = new Random();
		  private int step = 5;
      protected Direction oldDir;
		  public EnemyTank(int x, int y, Direction dir, Type type,OwnColor ownColor, MainPanel panel) {
		    super(x,y,dir,type,ownColor);
		    this.panel = panel;
		    this.oldDir = dir;
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
			if (pause) {
				dir = oldDir;
				return;
			}else {
		    Direction[] dirs = Direction.values();
		    if(step == 0) {
		      step = r.nextInt(10) + 5;
		      int n = r.nextInt(dirs.length);
		      dir = dirs[n];
		      fire();//TODO
		    }else {
		    step--;
		    }
			}
		  }

		//  public Missiles fire() {
//		    if (missiles.size()<3) {
//		      return super.fire();
//		    }
//		    return null;
		//  }
		  
		}


