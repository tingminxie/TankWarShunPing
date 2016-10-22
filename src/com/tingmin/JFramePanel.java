package com.tingmin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tingmin.TankFather.Direction;

public class JFramePanel extends JFrame {
	JPanel jp;
	public static void main(String[] args) {
		new JFramePanel();
	}
	public JFramePanel() {
		jp = new Mp(this);
		this.add(jp);
		this.addWindowListener((WindowListener) jp);
		this.setSize(600, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
class Mp extends JPanel implements WindowListener {
	JFramePanel jfp;
	public Mp(JFramePanel jfp) {
		super();
		this.jfp = jfp;
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g) {
		/*g.drawArc(230, 30, 50, 100, 90, 60);
		g.drawRect(230, 30, 50, 100);
		g.drawOval(230, 30, 50, 100);
		g.drawArc(230, 30, 50, 100, 30, 60);
		g.fillOval(245, 50, 5, 10);
		g.fillOval(260, 50, 5, 10);
		g.drawArc(215, 45, 80, 120, 120, 300);
		
		g.drawArc(250,100, 150,200, 110, 50);
		g.drawArc(110, 100,150,200,20,50);*/
		
//		g.drawRoundRect(10, 10, 100, 60, 30, 15);
//		g.drawRect(100, 100, 100, 80);
//		g.drawArc(25, 25, 120, 120, 45, 360);
//		g.drawRect(25, 25, 120, 120);
//		g.dispose();
//		System.out.println("paint method is called");
	//draw image on myPanel
//	Image im = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/zhajiangmian.jpg"));
//	g.drawOval(90, 90, 100,100);
//	g.drawImage(im,0,0,500,334,this);
//		g.setColor(Color.blue);
//		g.setFont(new Font("DialogInput",Font.BOLD,30));
//		g.drawString("DialogInput", 100,300);
//		g.setFont(new Font("Dialog",Font.BOLD,30));
//		g.drawString("Dialog", 100,50);
//		g.setFont(new Font("Monospaced",Font.BOLD,30));
//		g.drawString("Monospaced", 100,100);
//		g.setFont(new Font("Serif",Font.BOLD,30));
//		g.drawString("Serif", 100,150);
//		g.setFont(new Font("SansSerif",Font.BOLD,30));
//		g.drawString("SansSerif", 100,200);
//		g.setFont(new Font("Arial Bold",Font.ITALIC,30));
//		g.drawString("Arial Bold", 100,250);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		System.out.println("windowActivated");
		//jfp.dispose();
		//jfp.setVisible(false);
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		System.out.println("windowClosed");
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.println("windowClosing");
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		System.out.println("windowDeactivated");
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		System.out.println("windowDeiconified");
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		System.out.println("windowIconified");
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		System.out.println("windowOpened");
	}
}