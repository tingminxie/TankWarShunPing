/* make sure that each enemytank is a thread
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
  Vector<MyTank> tanks = new Vector<MyTank>();
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public MainPanel() {
//TODO
		myTank = new MyTank(300,300,Direction.DOWN,Type.GOOD,OwnColor.RED,this);
    for(int i=0;i<3;i++) {
      MyTank tmp = new MyTank(100*(i+1),100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this); 
      tanks.add(tmp);
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
    myTank.draw(g);
    for (int i=0;i<tanks.size();i++){
      tanks.get(i).draw(g);
      for(int j=0;j<tanks.get(i).getMissiles().size();j++){
    	  Missiles m = tanks.get(i).getMissiles().get(j);
    	  if(m.isLive()) {
    		  m.draw(g); 
    	  }else {
    		  tanks.get(i).getMissiles().remove(j);
    	  }
      }
    }
	  
    for(int i=0;i<myTank.getMissiles().size();i++) {
    	Missiles m = myTank.getMissiles().get(i);
    	if(m.isLive()) {
    		m.draw(g);
    	}else {
    		myTank.getMissiles().remove(i);
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
