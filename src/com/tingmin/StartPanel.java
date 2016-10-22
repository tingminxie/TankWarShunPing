package com.tingmin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.tingmin.TankFather.Direction;
import com.tingmin.TankFather.OwnColor;
import com.tingmin.TankFather.Type;

public class StartPanel extends JPanel implements Runnable {
		  int show = 0;
		  int round = 0;
		// add a audio class for startPanel
		  public void paint(Graphics g) {
		    Color c = g.getColor();
		    g.setColor(Color.gray);
		    g.fillRect(0, 0, 800, 600);
		    g.setColor(Color.green);
		    g.setFont(new Font("Times New Roman",Font.BOLD,40));
		    if(show%2 == 0) {
		      g.drawString("Tank battle",250,250);
		      new EnemyTank(300,300,Direction.UP,Type.BAD,OwnColor.BLUE).draw(g);
		      new MyTank(400,300,Direction.UP,Type.GOOD,OwnColor.RED).draw(g);
		    }
		    g.setColor(c);
		  } 
		  public void run() {
		    while(true) {
		      try {
		        Thread.sleep(500);
		      }catch(Exception e) {
		        e.printStackTrace();
		      }
		      show++;
		      this.repaint();
		    }
		  }

		}

