package com.tingmin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.io.File;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Bomb implements Serializable {
  int x;
  int y;
  boolean live = true;
  MainPanel panel;
  PanelRound2 panel2;
  int round = 0;
  public static final int TIMES = 8;
  Image image1,image2,image3,image4;
  
  public Bomb(int x,int y) {
    this.x = x;
    this.y = y;
  }
  public Bomb(int x, int y, MainPanel panel) {
    this(x,y);
    this.panel = panel;
    this.round = panel.round;
    try{
      image1 = ImageIO.read(new File("bomb1.png"));
      image2 = ImageIO.read(new File("bomb2.png"));
      image3 = ImageIO.read(new File("bomb3.png"));
      image4 = ImageIO.read(new File("bomb4.png"));
      
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
  public Bomb(int x, int y, PanelRound2 panel2) {
      this(x,y);
      this.panel2 = panel2;
      this.round = panel2.round;
      try{
        image1 = ImageIO.read(new File("bomb1.png"));
        image2 = ImageIO.read(new File("bomb2.png"));
        image3 = ImageIO.read(new File("bomb3.png"));
        image4 = ImageIO.read(new File("bomb4.png"));
        
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  public void draw(Graphics g) {
    Color c = g.getColor();
    for(int i=0;i<TIMES;i++) {
      
      g.drawImage(image1, x, y, 50, 50,null);
    }
    for(int i=0;i<TIMES;i++) {
      g.drawImage(image2, x, y, 50, 50,null);
    }
    for(int i=0;i<TIMES;i++) {
      g.drawImage(image3, x, y, 50, 50,null);
    }
    for(int i=0;i<TIMES;i++) {
      g.drawImage(image4, x, y, 50, 50,null);
    }
    g.setColor(c);
    if(this.round ==1) {
      panel.bombs.remove(this);
    }else if(this.round == 2) {
      panel2.bombs.remove(this);
    }
  }
}
