package com.afpa59.gc.iu.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.afpa59.gc.services.fichier.ServiceArticle;

@SuppressWarnings("serial")
public class IUArticle extends IUEntiteBase{
	
	public IUArticle(String titre, int w, int h) {
		super(titre, w, h);

	}
	/*-------------------------------------- METHODES -------------------------------------*/
	public JPanel getTitlePanel(){
		JPanel titlePanel = new JPanel();
		JLabel titre = new JLabel(" Gestions des articles ");
		titre.setBorder(BorderFactory.createLineBorder(new Color(92, 224, 194)));
		titre.setOpaque(true);
		titre.setBackground(Color.WHITE);
		titre.setForeground(new Color(92, 224, 194));
		titre.setFont(new Font("Calibri", Font.BOLD, 40));
		titlePanel.add(titre);
		return titlePanel;
	}
	
	@Override
	public void creer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visualiser() {
		
		
	}
	@Override
	public void lister(){
		
	}
	@Override
	public void modifier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void supprimer() {
		// TODO Auto-generated method stub
		
	}

}
