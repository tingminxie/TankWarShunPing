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

public class PanelRound2 extends JPanel implements Runnable , Serializable{
      TankWar01Test mainFrame;
    Vector<MyTank> myTanks = new Vector<MyTank>(); 
    Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
    Vector<Bomb>  bombs = new Vector<Bomb>();
    int myTankLife = 5;
    public static final int ENEMYTANK_LIFE = 10;
    int enemyKilled = 0;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    private GameRecord gameRecord;
    boolean win;
    int round = 2;
    boolean newGame = true;
    public boolean isNewGame() {
		return newGame;
	}

	public void setNewGame(boolean newGame) {
		this.newGame = newGame;
	}

    public PanelRound2(TankWar01Test mainFrame){
      this.mainFrame = mainFrame;
//      this.setBackground(Color.green);
      myTanks.add(new MyTank(30,30,Direction.UP,Type.GOOD,OwnColor.RED,this.round));
//      EnemyTank tmp = new EnemyTank(100,100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this.round); 
//      enemyTanks.add(tmp);
//      new Thread(tmp).start();
/*
      for(int i=0;i<ENEMYTANK_LIFE;i++) {
        EnemyTank tmp = new EnemyTank(30*(i+1),100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this.round); 
        enemyTanks.add(tmp);
      }
*/  
    this.gameRecord = new GameRecord(this.round,mainFrame);
    }
    public void launchPanel(){
      myTanks.get(0).setPanel2(this);
//      for(int i=0;i<enemyTanks.size();i++) {
//        enemyTanks.get(i).setPanel2(this);
//        enemyTanks.get(i).setSpeed(5);
//      }
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
      while(true ){
        try{
          Thread.sleep(50);
          times ++;
        }catch (Exception e) {
          e.printStackTrace();
        }
        repaint();
        if(newGame && times*50%3000 == 0 && enemyLife>0 ) {
        EnemyTank tmp = new EnemyTank(300,300,Direction.R,Type.BAD,OwnColor.BLUE,this.round); 
        enemyTanks.add(tmp);
        new Thread(tmp).start();
        tmp.setPanel2(this);
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
        if (myTank.isLive()) {
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
        }else{
          myTanks.remove(0);
          myTanks.add(new MyTank(myTank.oldX,myTank.oldY,Direction.UP,Type.GOOD,OwnColor.RED,this.round));
          myTanks.get(0).setPanel2(this);
          myTankLife--;
        }
      }  else{ 
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
      }else if(enemyKilled == ENEMYTANK_LIFE){ 
  //TODO i win 
        win = true;
//        mainFrame.win = true;
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman",Font.BOLD,40));
        g.drawString("Congratulations!",200,200);
        g.drawString("You have completed all the round!",80,300);
      }else if(enemyKilled == 0) {
    	  int times = 2;
    	  if(times%2 == 0) {
    		  g.setColor(Color.red);
    	        g.setFont(new Font("Times New Roman",Font.BOLD,40));
    	        g.drawString("coming",200,200);
    	        times ++;
    	  }
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
