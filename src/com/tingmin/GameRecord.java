package com.tingmin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

import com.tingmin.TankFather.Direction;
import com.tingmin.TankFather.OwnColor;
import com.tingmin.TankFather.Type;

public class GameRecord implements Serializable {
	TankWar01Test mainFrame;
	int round;

	public GameRecord(int round, TankWar01Test mainFrame) {
		this.round = round;
		this.mainFrame = mainFrame;
	}

	public void drawRecord(Graphics g) {

		Color c = g.getColor();

		// draw game record and corresponding tanks.
		g.setColor(Color.red);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));

		if (round == 1) {
			//show Total enemy
			new EnemyTank(850, 20, Direction.UP, Type.BAD, OwnColor.BLUE).draw(g);
			g.drawString("Total enemies:", 900, 40);
			g.drawString("" + mainFrame.p.ENEMYTANK_LIFE, 1080, 40);
			// show enemy left
			new EnemyTank(850, 60, Direction.UP, Type.BAD, OwnColor.BLUE).draw(g);
			g.drawString("" + mainFrame.p.enemyTanks.size(), 900, 80);

			// show total enemies killed in this round of game
			g.drawString("Total Enemy killed: ", 80, 650);
			new EnemyTank(30, 630, Direction.UP, Type.BAD, OwnColor.BLUE).draw(g);
			g.drawString("" + mainFrame.p.enemyKilled, 340, 650);

			// show my tank left
			new MyTank(850, 100, Direction.UP, Type.GOOD, OwnColor.RED).draw(g);
			g.drawString("" + mainFrame.p.myTankLife, 900, 120);
			g.setColor(c);
		} else if (round == 2) {
			//show Total enemy
			new EnemyTank(850, 20, Direction.UP, Type.BAD, OwnColor.BLUE).draw(g);
			g.drawString("Total enemies:", 900, 40);
			g.drawString("" + mainFrame.p2.ENEMYTANK_LIFE, 1080, 40);
			// show enemy left
			new EnemyTank(850, 60, Direction.UP, Type.BAD, OwnColor.BLUE).draw(g);
			g.drawString("" + mainFrame.p2.enemyTanks.size(), 900, 80);

			// show total enemies killed in this round of game
			g.drawString("Total Enemy killed: ", 80, 650);
			new EnemyTank(30, 630, Direction.UP, Type.BAD, OwnColor.BLUE).draw(g);
			g.drawString("" + mainFrame.p2.enemyKilled, 340, 650);

			// show my tank left
			new MyTank(850, 100, Direction.UP, Type.GOOD, OwnColor.RED).draw(g);
			g.drawString("" + mainFrame.p2.myTankLife, 900, 120);
			g.setColor(c);

		}

	}
}
