/* missile disappear when hit with each other
 * tanks move within the panel
 * fire tank
 * myTank can fire missiles when moving around.//not done
 */
package com.tingmin;
import javax.swing.*;

import com.tingmin.TankFather.Direction;
import com.tingmin.TankFather.OwnColor;
import com.tingmin.TankFather.Type;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class TankWar01Test extends JFrame {
	
	MainPanel p;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public static void main(String[] args) {
		new TankWar01Test();
	}

	public TankWar01Test()  {
		this.p = new MainPanel();
		this.add(p);
    this.addKeyListener((KeyListener) new MyKeyMonitor());
		this.setTitle("TestPanel");
		this.setLocation(300, 100);
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
    new Thread(p).start();
	}
  private class MyKeyMonitor extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      p.keyPressed(e);
    }
  } 
}

class MainPanel extends JPanel implements Runnable {
  MyTank myTank;
  Vector<TankFather> enemyTanks = new Vector<TankFather>();
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public MainPanel() {
//TODO
		myTank = new MyTank(500,300,Direction.DOWN,Type.GOOD,OwnColor.RED,this);
    for(int i=0;i<3;i++) {
      TankFather tmp = new EnemyTank(100*(i+1),100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this); 
      enemyTanks.add(tmp);
      new Thread(tmp).start();
    }
    
//    this.setBackground(Color.green);
    
	}
	  public void run() {
      while(true){
        try{
          Thread.sleep(50);
        }catch (Exception e) {
          e.printStackTrace();
        }
        repaint();
      }
	  }
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.green);
		g.fillRect(0, 0, WIDTH, HEIGHT);
//TODO make myTank to Vector<TankFather> myTanks 
    if (myTank.isLive()) {
      myTank.draw(g);
      for(int i=0;i<myTank.getMissiles().size();i++) {
        Missiles m = myTank.getMissiles().get(i);
        if(!m.isLive()) {
          myTank.getMissiles().remove(m);
        } else {
          if (enemyTanks.size() != 0) {
            m.hitTanks(enemyTanks);
            for(int j=0;j<enemyTanks.size();j++) {
              for(int h=0;h<enemyTanks.get(j).getMissiles().size();h++){
                Missiles tmp = enemyTanks.get(j).getMissiles().get(h);
                m.hitMissile(tmp);//TODO
              }
            }
          }
        m.draw(g);
        }
      }
    } 
    for (int i=0;i<enemyTanks.size();i++){
      TankFather tmp = enemyTanks.get(i);
      if (!tmp.isLive()){
        enemyTanks.remove(tmp);
      }else {
        tmp.draw(g);
        for(int j=0;j<tmp.getMissiles().size();j++){
    	    Missiles m = tmp.getMissiles().get(j);
    	    if(!m.isLive()) {
    		    tmp.getMissiles().remove(m);
    	    }else {
      		  m.draw(g);
      		  m.hitTank(myTank);
    		 // hitTanks();//good enemyTanks
          }
        }
      }
    }
	  
    g.setColor(c);
	}
/*
  public void update(Graphics g) {
    Color c = g.getColor();
    myTank.move();
    g.setColor(c);
  }*/
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    switch (keyCode) {
    case KeyEvent.VK_UP:
      myTank.setDir(Direction.UP);
      myTank.move();
      break;
    case KeyEvent.VK_RIGHT:
      myTank.setDir(Direction.R);
      myTank.move();
      break;
    case KeyEvent.VK_DOWN:
      myTank.setDir(Direction.DOWN);
      myTank.move();
      break;
    case KeyEvent.VK_LEFT:
      myTank.setDir(Direction.LEFT);
      myTank.move();
      break;
    }
    if(keyCode == KeyEvent.VK_SPACE) {
      myTank.fire();
    }
    
//    repaint();
  }
}
