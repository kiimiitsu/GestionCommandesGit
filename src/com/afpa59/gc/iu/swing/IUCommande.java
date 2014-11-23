package com.afpa59.gc.iu.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.afpa59.gc.services.fichier.ServiceCommande;

@SuppressWarnings("serial")
public class IUCommande extends IUEntiteBase{
	
	private ServiceCommande service;
	
	public IUCommande(String titre, int w, int h){
		super(titre,w,h);
	}

	public JPanel getTitlePanel(){
		JPanel titlePanel = new JPanel();
		JLabel titre = new JLabel(" Gestions des commandes ");
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
		new IUAffichageArticle("Créer un article", 800, 600);
		
	}

	@Override
	public void visualiser() {
		// TODO Auto-generated method stub
		
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
