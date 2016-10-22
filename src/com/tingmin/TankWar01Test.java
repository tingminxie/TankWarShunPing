/* 1.open and save game.
 * 2.pause and continue the game
 * 3.keep moving when key pressed,and not stop when fire enemy tank.  
 * 4.create class myTank.
 *//* 1.open and save game.
 * 2.next version: make three round of game
 * 3.next version:make bricks and walls
 * 4.next version:make blood box
 * 5.next version:make different enemyTank with different color and different life value
 * 
 */
package com.tingmin;
import javax.swing.*;

import com.tingmin.TankFather.Direction;
import com.tingmin.TankFather.OwnColor;
import com.tingmin.TankFather.Type;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class TankWar01Test extends JFrame  implements Serializable,ActionListener {
  
  JMenuBar frameMB;
  JMenu fileM,setM;
  JMenuItem newMI,openMI,saveMI,pauseMI,continueMI,exitMI;
  MainPanel p;
  StartPanel startPanel;
  public static final int WIDTH = 1000;
  public static final int HEIGHT = 800;

  public static void main(String[] args) {
    new TankWar01Test();
  }


  public TankWar01Test()  {
    frameMB = new JMenuBar();
    this.setJMenuBar(frameMB);
    
    fileM = new JMenu("Game");
    fileM.setMnemonic('G');
    frameMB.add(fileM);
    setM = new JMenu("Set");
    setM.setMnemonic('S');
    frameMB.add(setM);

    newMI = new JMenuItem("New(N)");
    newMI.setMnemonic('N');
    newMI.addActionListener(this);
    newMI.setActionCommand("newgame");
    openMI = new JMenuItem("Open(O)");
    openMI.setMnemonic('O');
    openMI.addActionListener(this);
    openMI.setActionCommand("open");
    saveMI = new JMenuItem("Save(S)");
    saveMI.setMnemonic('S');
    saveMI.addActionListener(this);
    saveMI.setActionCommand("save");
    pauseMI = new JMenuItem("Pause(P)");
    pauseMI.setMnemonic('P');
    pauseMI.addActionListener(this);
    pauseMI.setActionCommand("pause");
    continueMI = new JMenuItem("Continue(C)");
    continueMI.setMnemonic('C');
    continueMI.addActionListener(this);
    continueMI.setActionCommand("continue");
    exitMI = new JMenuItem("Exit(E)");
    exitMI.setMnemonic('E');
    exitMI.addActionListener(this);
    exitMI.setActionCommand("exit");
    fileM.add(newMI);
    fileM.add(openMI);
    fileM.add(saveMI);
    fileM.add(pauseMI);
    fileM.add(continueMI);
    fileM.add(exitMI);

    this.startPanel = new StartPanel();
    this.add(startPanel);
    new Thread(startPanel).start();
    this.setTitle("TankWar");
    this.setLocation(300, 100);
    this.setSize(WIDTH, HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
  private class MyKeyMonitor extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      p.keyPressed(e);
    }
    public void keyReleased(KeyEvent e) {
      p.keyReleased(e);
    }
  } 

//TODO how to make a new round of game?
  public void newGame() {
      if (p!=null) {
        this.remove(p);
      }
//TODO add 3 round
      this.p = new MainPanel(1);
      if (startPanel!=null) {
        this.remove(startPanel);
      }
      this.add(p);
      this.addKeyListener((KeyListener) new MyKeyMonitor());
      this.setVisible(true);
      new Thread(p).start();
      p.startEnemyThread();
  }

  public void oldGame() {
      if(p!=null) {
        this.remove(p);
      }// this.add(p) later when p is resume by readObject() method
      JFileChooser fileOpen = new JFileChooser();
      fileOpen.setDialogTitle("please select file to open ..");
      fileOpen.showOpenDialog(null);
      fileOpen.setVisible(true);
      FileInputStream fileRead = null;
      DataInputStream dataRead = null;
      ObjectInputStream objectRead = null;
      try {
        String fileName = fileOpen.getSelectedFile().getAbsolutePath();
        int savedEnemyTankLife,savedEnemyKilled,savedMyTankLife;//savedBombNo 
        Vector<EnemyTank> savedEnemyTanks = new Vector<EnemyTank>();
        Vector<MyTank> savedMyTanks = new Vector<MyTank>();
        Vector<Bomb> savedBombs = new Vector<Bomb>();

        fileRead = new FileInputStream(fileName);
        dataRead = new DataInputStream(fileRead);
        objectRead = new ObjectInputStream(fileRead);
//read object from file 
        savedEnemyTankLife = dataRead.readInt();
        savedEnemyKilled = dataRead.readInt();
        savedMyTankLife = dataRead.readInt();
        if (savedEnemyTankLife>0) {
          for(int i=0;i<savedEnemyTankLife;i++) {
            EnemyTank tmp = (EnemyTank)objectRead.readObject();
            savedEnemyTanks.add(tmp); 
            for(int j=0;j<tmp.missiles.size();j++) {
              Missiles m = tmp.missiles.get(j);
              new Thread(m).start();
            }
            new Thread(tmp).start();
          }
          p.enemyTanks = savedEnemyTanks;//1
        }
        if (savedMyTankLife>0) {
          MyTank tmp = (MyTank)objectRead.readObject();
          savedMyTanks.add(tmp);
          p.myTanks = savedMyTanks;//5
        }
        
        p.ENEMY_KILLED = savedEnemyKilled;//2
//        p.bombs = savedBombs;//3 TODO no bombs being added after opening an old file
        p.TANK_LIFE = savedMyTankLife;//4 

        new Thread(p).start();
         
      }catch(Exception a) {
        a.printStackTrace();
      }finally{
        try {
    	    fileRead.close();
    	    objectRead.close();
 	      } catch (IOException e1) {
		      e1.printStackTrace();
	      }
      }

  }
//try to save mainPanel as a object
  public void saveGame() {
      if (p == null) {
        return;
      } else {
        JFileChooser fileSave = new JFileChooser();
        fileSave.setDialogTitle("please select file to save ..");
        fileSave.showSaveDialog(null);
        fileSave.setVisible(true);
        FileOutputStream fileWrite = null;
        DataOutputStream dataWrite = null;
        ObjectOutputStream objectWrite = null;
        try {
          String fileName = fileSave.getSelectedFile().getAbsolutePath();
          fileWrite = new FileOutputStream(fileName);
          dataWrite = new DataOutputStream(fileWrite);
          objectWrite = new ObjectOutputStream(fileWrite);

  // write object to selected file
          dataWrite.writeInt(p.enemyTanks.size());
          dataWrite.writeInt(p.ENEMY_KILLED);
          dataWrite.writeInt(p.TANK_LIFE);
          if(p.enemyTanks.size()>0) {
            for(int i=0;i<p.enemyTanks.size();i++) {
              EnemyTank tmp = p.enemyTanks.get(i);
              objectWrite.writeObject(tmp);
            }
          }
          if(p.TANK_LIFE>0) {
            objectWrite.writeObject(p.myTanks.get(0));
          }
        }catch(Exception a) {
          a.printStackTrace();
        }finally{
          try {
            fileWrite.close();
            objectWrite.close();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      }

  }
  public void pauseGame() {
    if (p == null) {
      return;
    } else {
      for(int i=0;i<p.enemyTanks.size();i++) {
        p.enemyTanks.get(i).speed = 0;
        p.enemyTanks.get(i).pause = true;
      }
      if (p.myTanks.size()>0) {
        p.myTanks.get(0).speed = 0;
        p.myTanks.get(0).pause = true;
      }
    }
  }
  public void continueGame() {
    if (p == null) {
      return;
    } else {
      for(int i=0;i<p.enemyTanks.size();i++) {
        p.enemyTanks.get(i).speed = 3;
        p.enemyTanks.get(i).pause = false;
      }
      if (p.myTanks.size()>0) {
        p.myTanks.get(0).speed = 3;
        p.myTanks.get(0).pause = false;
      }
    }
  }
  public void exitGame() {
      System.exit(0);
  }
  public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand().equals("newgame")) {
      newGame();
    }else if(e.getActionCommand().equals("open")) {
      oldGame();
    }else if(e.getActionCommand().equals("save")) {
      saveGame();
    }else if(e.getActionCommand().equals("pause")) {
      pauseGame();
    }else if(e.getActionCommand().equals("continue")) {
      continueGame();
    }else if(e.getActionCommand().equals("exit")) {
      exitGame();
    }
  }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class StartPanel extends JPanel implements Runnable {
  int show = 0;
  public void paint(Graphics g) {
    Color c = g.getColor();
    g.setColor(Color.gray);
    g.fillRect(0, 0, 800, 600);
    g.setColor(Color.green);
    g.setFont(new Font("Times New Roman",Font.BOLD,40));
    if(show%2 == 0) {
      g.drawString("round 1",300,250);
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

////////////////////////////////////////////////////////////////////////////////////////////
class MainPanel extends JPanel implements Runnable , Serializable {
//  TankWar01Test mainFrame;
  Vector<MyTank> myTanks = new Vector<MyTank>(); 
  Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
  Vector<Bomb>  bombs = new Vector<Bomb>();
  int TANK_LIFE = 5;
  public static final int ENEMYTANK_LIFE = 5;
  int ENEMY_KILLED = 0;
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  int round = 0;//TODO try to make 3 round

  public MainPanel(/*TankWar01Test mainFrame*/int round){
//    this.mainFrame = mainFrame;
    this.round = round;
    launchPanel(round);
//    this.setBackground(Color.green);
  }
  public void launchPanel(int round) {
    switch(round) {
      case 1:
        myTanks.add(new MyTank(500,300,Direction.DOWN,Type.GOOD,OwnColor.RED,this));
        for(int i=0;i<ENEMYTANK_LIFE;i++) {
          EnemyTank tmp = new EnemyTank(30*(i+1),100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this); 
          enemyTanks.add(tmp);
        }
        break;
      case 2:
        break;
      case 3:
        break;
    }
  }
  public void startEnemyThread() {
    for(int i=0;i<ENEMYTANK_LIFE;i++) {
      new Thread(enemyTanks.get(i)).start();
    }
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
    g.setColor(Color.yellow);
    g.fillRect(0, 0, TankWar01Test.WIDTH, TankWar01Test.HEIGHT);
    g.setColor(Color.green);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    g.setColor(Color.red);
    g.setFont(new Font("Times New Roman",Font.BOLD,20));
    g.drawString("EnemyTank: " + enemyTanks.size(),810,25);
    g.drawString("Enemykilled: " +ENEMY_KILLED,810,50);
    g.drawString("MyTank: " + this.TANK_LIFE,810,75);

    if (this.TANK_LIFE>0) {
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
        myTanks.add(new MyTank(myTank.oldX,myTank.oldY,Direction.DOWN,Type.GOOD,OwnColor.RED,this));
        myTanks.remove(0);
        this.TANK_LIFE--;
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
        ENEMY_KILLED ++;
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
//TODO i win 
      g.setFont(new Font("Times New Roman",Font.BOLD,40));
      g.drawString("Congratulations!",200,200);
//      mainFrame.restartGame();
    }
    if(bombs.size()!=0) {
        for(int i=0;i<bombs.size();i++) {
        	
          bombs.get(i).draw(g);
        }
      }
    
    g.setColor(c);
  }
  public void keyReleased(KeyEvent e) {
    if (this.TANK_LIFE>0) {
      if (myTanks.get(0).pause ) { 
        return;
      }else {
        myTanks.get(0).keyReleased(e);
      }
    }
  }

  public void keyPressed(KeyEvent e) {
    if (this.TANK_LIFE>0){ 
      if (myTanks.get(0).pause ) { 
        return;
      }else {
         myTanks.get(0).keyPressed(e);
      }
    }
  }

}
