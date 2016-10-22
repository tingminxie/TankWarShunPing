package com.tingmin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

import com.tingmin.TankFather.Direction;
import com.tingmin.TankFather.OwnColor;
import com.tingmin.TankFather.Type;

public class GameRecord implements Serializable {
	MainPanel panel;
	public GameRecord(MainPanel panel) {
		this.panel = panel;
	}

	public void drawRecord(Graphics g) {
	    Color c = g.getColor();
	    //fill all frame with yellow
	    g.setColor(Color.yellow);
	    g.fillRect(0, 0, TankWar01Test.WIDTH, TankWar01Test.HEIGHT);
	    //fill battle field with green
	    g.setColor(Color.green);
	    g.fillRect(0, 0, panel.WIDTH, panel.HEIGHT);
	    
	    //draw game record and corresponding tanks.
	    g.setColor(Color.red);
	    g.setFont(new Font("Times New Roman",Font.BOLD,20));
	    
	    //show enemy left
	    new EnemyTank(850,50,Direction.UP,Type.BAD,OwnColor.BLUE,panel).draw(g);;
	    g.drawString("" + panel.enemyTanks.size(),900,70);
	    
	    //show total enemies killed in this round of game
	    g.drawString("Total Enemy killed: ",80,650);
	    new EnemyTank(30,630,Direction.UP,Type.BAD,OwnColor.BLUE,panel).draw(g);;
	    g.drawString("" +panel.ENEMY_KILLED,340,650);
	    
	    //show my tank left
	    new MyTank(850,100,Direction.UP,Type.GOOD,OwnColor.RED,panel).draw(g);;
	    g.drawString("" + panel.myTankLife,900,120);
	    g.setColor(c);

	}
}
