/* 1.open and save game.
 * 2.show file chooser dialog, do nothing when popdown Cancel button
 * 
 * next version: make three round of game
 * next version:make bricks and walls
 * next version:make blood box
 * next version:make different enemyTank with different color and different life value
 * player can set game round itself from menu.
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
  public static final int WIDTH = 1200;
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
    this.setLocation(50, 50);
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
//open a saved game
  public void oldGame() {
// this.add(p) later when p is resume by readObject() method
      JFileChooser fileOpen = new JFileChooser();
      fileOpen.setDialogTitle("please select file to open ..");
      int result = fileOpen.showOpenDialog(null);
      fileOpen.setVisible(true);
//TODO now
//      fileOpen.addActionListener(this);
      FileInputStream fileRead = null;
//      DataInputStream dataRead = null;
      ObjectInputStream objectRead = null;
      String fileName = null;
      /*if(result == JFileChooser.CANCEL_OPTION) {
        System.out.println("cancel was selected");
      }else*/ if(result == JFileChooser.APPROVE_OPTION){
        try {
          fileName = fileOpen.getSelectedFile().getAbsolutePath();
            fileRead = new FileInputStream(fileName);
  //          dataRead = new DataInputStream(fileRead);
            objectRead = new ObjectInputStream(fileRead);
            //read object from file 
            MainPanel savedPanel = (MainPanel)objectRead.readObject();
            if(p!=null) {
              this.remove(p);
            }
            if(startPanel!=null) {
              this.remove(startPanel);
            }
            this.p = savedPanel;
            this.add(p);
            this.setVisible(true);
            this.addKeyListener((KeyListener) new MyKeyMonitor());
            new Thread(p).start();
            p.startEnemyThread();
            for(int i=0;i<p.enemyTanks.size();i++) {
              EnemyTank eTank = p.enemyTanks.get(i);
              for(int j=0;j<eTank.missiles.size();j++) {
                Missiles m = eTank.missiles.get(j);
                new Thread(m).start();
              }
            }
        }catch(Exception e) {
          e.printStackTrace();
        }finally{
          try {
            //dataRead.close();
            objectRead.close();
            fileRead.close();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
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
      int result = fileSave.showSaveDialog(null);
      fileSave.setVisible(true);
      FileOutputStream fileWrite = null;
//      DataOutputStream dataWrite = null;
      ObjectOutputStream objectWrite = null;
      String fileName = null;
      /*if(result == JFileChooser.CANCEL_OPTION) {
        System.out.println("cancel was selected");
      }else */if(result == JFileChooser.APPROVE_OPTION){
        try {
          
          fileName = fileSave.getSelectedFile().getAbsolutePath();
          if(fileName==null) {
            return;
          }else {
          fileWrite = new FileOutputStream(fileName);
  //        dataWrite = new DataOutputStream(fileWrite);
          objectWrite = new ObjectOutputStream(fileWrite);

  // write object to selected file
          objectWrite.writeObject(p);
  /*
          dataWrite.writeInt(p.enemyTanks.size());
          dataWrite.writeInt(p.ENEMY_KILLED);
          dataWrite.writeInt(p.myTankLife);
          if(p.enemyTanks.size()>0) {
            for(int i=0;i<p.enemyTanks.size();i++) {
              EnemyTank tmp = p.enemyTanks.get(i);
              objectWrite.writeObject(tmp);
            }
          }
          if(p.myTankLife>0) {
            objectWrite.writeObject(p.myTanks.get(0));
          }
  */
          }
        }catch(Exception e) {
          e.printStackTrace();
        }finally{
          try {
  //          dataWrite.close();
            objectWrite.close();
            fileWrite.close();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
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
  int myTankLife = 5;
  public static final int ENEMYTANK_LIFE = 5;
  int ENEMY_KILLED = 0;
  static final int WIDTH = 800;
  static final int HEIGHT = 600;
  int round = 0;//TODO try to make 3 round
  private GameRecord gameRecord = new GameRecord(this);

  public MainPanel(/*TankWar01Test mainFrame*/int round){
//    this.mainFrame = mainFrame;
    this.round = round;
    launchPanel(round);
//    this.setBackground(Color.green);
  }

//construct each round of game for panel.
  public void launchPanel(int round) {
    switch(round) {
      case 1:
        myTanks.add(new MyTank(500,300,Direction.UP,Type.GOOD,OwnColor.RED,this));
        for(int i=0;i<ENEMYTANK_LIFE;i++) {
          EnemyTank tmp = new EnemyTank(30*(i+1),100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this); 
          enemyTanks.add(tmp);
        }
        break;
//TODO 
      case 2:
        myTanks.add(new MyTank(500,300,Direction.DOWN,Type.GOOD,OwnColor.RED,this));
        for(int i=0;i<ENEMYTANK_LIFE*2;i++) {
          EnemyTank tmp = new EnemyTank(30*(i+1),100,Direction.DOWN,Type.BAD,OwnColor.BLUE,this); 
          enemyTanks.add(tmp);
        }
        break;
      case 3:
        break;
    }
  }
  public void startEnemyThread() {
    if (enemyTanks.size()>0) {
      for(int i=0;i<enemyTanks.size();i++) {
        new Thread(enemyTanks.get(i)).start();
      }
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
        myTanks.add(new MyTank(myTank.oldX,myTank.oldY,Direction.UP,Type.GOOD,OwnColor.RED,this));
        myTanks.remove(0);
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
      g.setColor(Color.red);
      g.setFont(new Font("Times New Roman",Font.BOLD,40));
      g.drawString("Congratulations!",200,200);
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
