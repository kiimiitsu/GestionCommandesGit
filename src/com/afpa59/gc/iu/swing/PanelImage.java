package com.afpa59.gc.iu.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelImage extends JPanel{

	private Image image;
	
	public PanelImage(String image){
		this.image = Toolkit.getDefaultToolkit().getImage("images/"+image);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0,0, this.getWidth(), this.getHeight(),this);
	}

	
}
