/* 1. make three round of game
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
  PanelRound2 p2;
//  PanelRound3 p3;
  StartPanel startPanel;
  int oldRound = 0;
  boolean win;
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
      if (e.getKeyCode() == KeyEvent.VK_ENTER && win) {
        nextRound();
      }
      if( e.getKeyChar() != KeyEvent.VK_ENTER && oldRound == 1 ) {
        p.keyPressed(e);
      }
      if(e.getKeyCode() != KeyEvent.VK_ENTER && oldRound == 2) {
        p2.keyPressed(e);
      }
    }
    public void keyReleased(KeyEvent e) {
      if(p != null) {
        p.keyReleased(e);
      }else if (p2 != null) {
        p2.keyReleased(e);
      } 
    }
  } 
  public void nextRound(){
    if (this.oldRound == 1 ) {
      this.remove(p);
      this.p2 = new PanelRound2(this);
      this.oldRound = p2.round;
      p2.launchPanel();
      this.win = false;
      this.add(p2);
      this.addKeyListener((KeyListener) new MyKeyMonitor());
      this.setVisible(true);
      new Thread(p2).start();
      p2.startEnemyThread();
    }   

  }

//TODO how to make a new round of game?
  public void newGame() {
      if (p!=null) {
        this.remove(p);
      }
      if(p2!=null) {
    	  this.remove(p2);
      }
      if (startPanel!=null) {
        this.remove(startPanel);
      }
      this.p = new MainPanel(this);
      p.launchPanel();
      this.oldRound = p.round;
      this.win = false;
      this.add(p);
      this.addKeyListener((KeyListener) new MyKeyMonitor());
      this.setVisible(true);
      new Thread(p).start();
      p.startEnemyThread();
  }
//open a saved game
  /*public void newRound() {
    int round = p.round;
    switch
  }*/
  public void oldGame() {
// this.add(p) later when p is resume by readObject() method
      JFileChooser fileOpen = new JFileChooser();
      fileOpen.setDialogTitle("please select file to open ..");
      int result = fileOpen.showOpenDialog(null);
      fileOpen.setVisible(true);
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

////////////////////////////////////////////////////////////////////////////////////////////
