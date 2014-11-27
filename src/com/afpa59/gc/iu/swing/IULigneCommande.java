package com.afpa59.gc.iu.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.afpa59.gc.services.fichier.ServiceCommande;

public class IULigneCommande extends IUEntiteBase {

	public IULigneCommande(String titre, int w, int h){
		super(titre,w,h);
	}

	public JPanel getTitlePanel(){
		JPanel titlePanel = new JPanel();
		JLabel titre = new JLabel(" Gestions des lignes de commandes ");
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
		new IUAffichageLigneCommande("Création d'une ligne de commande", 500, 300, Mode.CREATION, null, null);
	}
	
	@Override
	public void visualiser() {
		new IURechercheLigneCommande("Visualiser une ligne de commande", 500, 500, Mode.VISUALISATION);
	}
	
	@Override
	public void lister(){
		//new IUAffichageListeLigneCommande("Liste des ligne de commandes", 800, 600, Mode.OTHER, ServiceCommande.getInstance().getEntites());
	}
	
	@Override
	public void modifier() {
		new IURechercheLigneCommande("Modifier une ligne de commande", 500, 500, Mode.MODIFICATION);
	}
	
	@Override
	public void supprimer() {
		new IURechercheLigneCommande("Supprimer une ligne de commande", 500, 500, Mode.SUPPRESSION);
		
	}
}
