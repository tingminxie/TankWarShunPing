package com.tingmin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JPanel;

import com.tingmin.TankFather.Direction;
import com.tingmin.TankFather.OwnColor;
import com.tingmin.TankFather.Type;

public class MainPanel extends JPanel implements Runnable , Serializable {
    TankWar01Test mainFrame; 
    Vector<MyTank> myTanks = new Vector<MyTank>(); 
    Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
    Vector<Bomb>  bombs = new Vector<Bomb>();
    int myTankLife = 5;
    public static final int ENEMYTANK_LIFE = 5;
    int enemyKilled = 0;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    GameRecord gameRecord; 
    boolean win;
    int round = 1;
    boolean newGame = true;
    public boolean isNewGame() {
		return newGame;
	}

	public void setNewGame(boolean newGame) {
		this.newGame = newGame;
	}

	public MainPanel(TankWar01Test mainFrame){
      this.mainFrame = mainFrame;
//      this.setBackground(Color.green);
      myTanks.add(new MyTank(500,300,Direction.UP,Type.GOOD,OwnColor.RED,this.round));
      EnemyTank tmp = new EnemyTank(100,100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this.round); 
      enemyTanks.add(tmp);
      new Thread(tmp).start();
/*
      for(int i=0;i<ENEMYTANK_LIFE;i++) {
        EnemyTank tmp = new EnemyTank(30*(i+1),100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this.round); 
        enemyTanks.add(tmp);
      }
*/  
         this.gameRecord = new GameRecord(1,mainFrame);
    }

    public void launchPanel(){
      myTanks.get(0).setPanel(this);
      for(int i=0;i<enemyTanks.size();i++) {
        enemyTanks.get(i).setPanel(this);
        enemyTanks.get(i).setSpeed(5);
      }
    //createToolTip().setTipText("hello");
    }
    public String toString() {
      return getClass().getName()+"["+paramString()+"]";
    }
  
  public void startEnemyThread() {
      if (enemyTanks.size()>0) {
        for(int i=0;i<enemyTanks.size();i++) {
          new Thread(enemyTanks.get(i)).start();
        }
      }
    }
  
  public void run() {
	int times = 0;
    int enemyLife = ENEMYTANK_LIFE;
    
    while(true){
      try{
        Thread.sleep(50);
        times ++;
      }catch (Exception e) {
        e.printStackTrace();
      }
      repaint();
      if(newGame && times*50%3000 == 0 && enemyLife>1 ) {
        EnemyTank tmp = new EnemyTank(100,100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this.round); 
        enemyTanks.add(tmp);
        new Thread(tmp).start();
        tmp.setPanel(this);
        tmp.setSpeed(5);
        times = 0;
        enemyLife --;
      }
    }
  }
     public void paint(Graphics g){
       Color c = g.getColor();
     //fill all frame with yellow
        g.setColor(Color.yellow);
        g.fillRect(0, 0, TankWar01Test.WIDTH, TankWar01Test.HEIGHT);
        //fill battle field with green
        g.setColor(Color.green);
        g.fillRect(0, 0, WIDTH, HEIGHT);

      gameRecord.drawRecord(g);

      if (myTankLife>0) {
        MyTank myTank = myTanks.get(0);
        if (!myTank.isLive()){
        myTanks.remove(0);
        myTanks.add(new MyTank(myTank.oldX,myTank.oldY,Direction.UP,Type.GOOD,OwnColor.RED,this.round));
        myTanks.get(0).setPanel(this);
        myTankLife--;
        }else /*if (myTank.isLive())*/ {
          myTank.draw(g);
          myTank.autoMove();
          for(int i=0;i<myTank.getMissiles().size();i++) {
            Missiles m = myTank.getMissiles().get(i);
            if (enemyTanks.size() != 0) {
               m.hitTanks(enemyTanks);
               for(int j=0;j<enemyTanks.size();j++) {
                 Vector<Missiles> missiles = enemyTanks.get(j).getMissiles();
                 m.hitMissile(missiles);
               }    
            }
            if(!m.isLive()) {
               myTank.getMissiles().remove(m);
            } else {
               m.draw(g);
            }
          }
        }
      }  else{ 
//i lose
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman",Font.BOLD,50));
        g.drawString("Game Over!",300,300);
      }
      if(enemyTanks.size()>0) {
      for (int i=0;i<enemyTanks.size();i++){
        EnemyTank tmp = enemyTanks.get(i);
        if (!tmp.isLive()){
          enemyTanks.remove(tmp);
          enemyKilled ++;
        }else {
          tmp.draw(g);
          for(int r=0;r<tmp.getMissiles().size();r++){
            Missiles m = tmp.getMissiles().get(r);
            for(int j=0;j<enemyTanks.size();j++){
              Vector<Missiles> missiles = enemyTanks.get(j).getMissiles();
              m.hitMissile(missiles);
            }
            m.hitTank(myTanks.get(0));
            if(!m.isLive()) {
              tmp.getMissiles().remove(m);
            }else {
              m.draw(g);
            }
          }
        }
      }
      }else { 
  // i win 
        win = true;
        mainFrame.win = true;
//        System.out.println(""+mainFrame.win + " newGame: " + newGame);
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman",Font.BOLD,40));
        g.drawString("Congratulations!",200,200);
        g.drawString("press 'Enter' to next round of game!",80,300);
      }
      if(bombs.size()!=0) {
        for(int i=0;i<bombs.size();i++) {
          bombs.get(i).draw(g);
        }
      }
    }
    public void keyReleased(KeyEvent e) {
      if (myTankLife>0) {
        if (myTanks.get(0).pause ) { 
          return;
        }else {
          myTanks.get(0).keyReleased(e);
        }
      }
    }

    public void keyPressed(KeyEvent e) {
      if (myTankLife>0){ 
        if (myTanks.get(0).pause ) { 
          return;
        }else {
           myTanks.get(0).keyPressed(e);
        }
      }
    }

  }


