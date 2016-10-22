package com.tingmin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.io.File;

import javax.imageio.ImageIO;

public class Bomb {
  int x;
  int y;
  boolean live = true;
  MainPanel panel;
  public static final int TIMES = 4;
  Image image1,image2,image3,image4;
  
  /*Image image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.png"));
  Image image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.png"));
  Image image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.png"));
  Image image4 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb4.png"));
  */
  public Bomb(int x, int y, MainPanel panel) {
    this.x = x;
    this.y = y;
    this.panel = panel;
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
    panel.bombs.remove(this);
  }
}
