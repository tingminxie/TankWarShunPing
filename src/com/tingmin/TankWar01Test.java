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
    new Thread(new MyThread()).start();
	}
  private class MyKeyMonitor extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      p.keyPressed(e);
    }
  } 
  class MyThread implements Runnable {
	  public void run() {
      while(true){
      try{
        Thread.sleep(500);
        p.repaint();
      }catch (Exception e) {
        e.printStackTrace();
      }
      }
	  }
	}
}

class MainPanel extends JPanel {
  TankFather myTank;
  Vector<TankFather> tanks = new Vector<TankFather>();
  Vector<Missiles> missiles = new Vector<Missiles>();
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public MainPanel() {
		myTank = new MyTank(300,300,Direction.UP,Type.GOOD,OwnColor.RED,this);
    tanks.add(new MyTank(100,100,Direction.UP,Type.BAD,OwnColor.BLUE,this));
    tanks.add(new MyTank(200,100,Direction.UP,Type.BAD,OwnColor.BLUE,this));
    tanks.add(new MyTank(300,100,Direction.UP,Type.BAD,OwnColor.BLUE,this));
//    this.setBackground(Color.green);
    
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.green);
		g.fillRect(0, 0, WIDTH, HEIGHT);
    myTank.draw(g);
    for (int i=0;i<tanks.size();i++){
      tanks.get(i).draw(g);
    }
	  g.setColor(c);
    for(int i=0;i<missiles.size();i++) {
      missiles.get(i).draw(g);
    }
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
    case KeyEvent.VK_SPACE:
      myTank.fire();
      break;
    }
//    repaint();
  }
}
