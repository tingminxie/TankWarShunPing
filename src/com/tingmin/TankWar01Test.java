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
  StartPanel startPanel;
  MainPanel p;
  PanelRound2 p2;
//  PanelRound3 p3;
  int round = 0;
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

//create and add startPanel
    this.startPanel = new StartPanel();
    this.add(startPanel);
    new Thread(startPanel).start();

    this.setTitle("TankWar");
    this.setLocation(50, 50);
    this.setSize(WIDTH, HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
//monitor key event on frame
  private class MyKeyMonitor extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER && win) {
        nextRound();
      }
      if( e.getKeyChar() != KeyEvent.VK_ENTER && round == 1 ) {
        p.keyPressed(e);
      }
      if(e.getKeyCode() != KeyEvent.VK_ENTER && round == 2) {
        p2.keyPressed(e);
      }
    }
    public void keyReleased(KeyEvent e) {
      if(round == 1) {
        p.keyReleased(e);
      }else if (round == 2) {
        p2.keyReleased(e);
      } 
    }
  } 

  public void nextRound(){
    if (this.round == 1 ) {
      this.remove(p);
      this.p2 = new PanelRound2(this);
      this.round = p2.round;//or this.round = 2;
      this.win = false;
      this.add(p2);
      this.addKeyListener((KeyListener) new MyKeyMonitor());
      this.setVisible(true);
      //System.out.println("p2 is valid?" +p2.isValid());
      p2.launchPanel();
      new Thread(p2).start();
      p2.startEnemyThread();
    }   

  }

  public void newGame() {
      if (this.round == 1) {
        this.remove(p);
      }
      if(this.round == 2) {
    	  this.remove(p2);
      }
      if (this.round == 0) {
        this.remove(startPanel);
      }
      this.p = new MainPanel(this);
      this.round = p.round;
      this.win = false;
      this.add(p);
//      p.revalidate();
//      p.repaint();
      this.addKeyListener((KeyListener) new MyKeyMonitor());
      this.setVisible(true);
//      System.out.println("MainPanel after startPanel:" + p);
      p.launchPanel();
//      System.out.println("is valid?" +p.isValid());
      new Thread(p).start();
      p.startEnemyThread();
  }

  public void oldGame() {
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
//            MainPanel savedPanel = (MainPanel)objectRead.readObject();
            if(this.round == 0) {
              this.remove(startPanel);
            }
            if(this.round == 1) {
              this.remove(p);
            }
            if(this.round == 2) {
              this.remove(p2);
            }

            if(objectRead.readObject().equals(p)) {
              MainPanel savedPanel = (MainPanel)objectRead.readObject();
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
            }else if (objectRead.readObject().equals(p2)){
              PanelRound2 savedPanel = (PanelRound2)objectRead.readObject();
              this.p2 = savedPanel;
              this.add(p2);
              this.setVisible(true);
              this.addKeyListener((KeyListener) new MyKeyMonitor());
              new Thread(p2).start();
              p2.startEnemyThread();
              for(int i=0;i<p2.enemyTanks.size();i++) {
                EnemyTank eTank = p2.enemyTanks.get(i);
                for(int j=0;j<eTank.missiles.size();j++) {
                  Missiles m = eTank.missiles.get(j);
                  new Thread(m).start();
                }
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

  public void saveGame() {
    if (this.round == 0) {
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
            if(this.round == 1) {
              objectWrite.writeObject(p);
            }else if (this.round == 2) {
              objectWrite.writeObject(p2);
            }/*else if (this.round == 3) {
              objectWrite.writeObject(p3);
            }*/
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
    if (this.round == 0) {
      return;
    } else if (this.round == 1){
      for(int i=0;i<p.enemyTanks.size();i++) {
        p.enemyTanks.get(i).speed = 0;
        p.enemyTanks.get(i).pause = true;
      }
      if (p.myTanks.size()>0) {
        p.myTanks.get(0).speed = 0;
        p.myTanks.get(0).pause = true;
      }
    }else if (this.round == 2){
      for(int i=0;i<p2.enemyTanks.size();i++) {
        p2.enemyTanks.get(i).speed = 0;
        p2.enemyTanks.get(i).pause = true;
      }
      if (p2.myTanks.size()>0) {
        p2.myTanks.get(0).speed = 0;
        p2.myTanks.get(0).pause = true;
      }
    }
  }
  public void continueGame() {
    if (this.round == 0) {
      return;
    } else if (this.round == 1){
      for(int i=0;i<p.enemyTanks.size();i++) {
        p.enemyTanks.get(i).speed = 3;
        p.enemyTanks.get(i).pause = false;
      }
      if (p.myTanks.size()>0) {
        p.myTanks.get(0).speed = 3;
        p.myTanks.get(0).pause = false;
      }
    }else if (this.round == 2) {
      for(int i=0;i<p2.enemyTanks.size();i++) {
        p2.enemyTanks.get(i).speed = 3;
        p2.enemyTanks.get(i).pause = false;
      }
      if (p2.myTanks.size()>0) {
        p2.myTanks.get(0).speed = 3;
        p2.myTanks.get(0).pause = false;
      }
    }
  }

  public void exitGame() {
      System.exit(0);
  }

// monitor action on menu item
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

