package com.afpa59.gc.iu.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.afpa59.gc.services.commun.ServiceArticle;

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
		new IUAffichageArticle("Création d'un article", 500, 300, Mode.CREATION, null);
	}
	
	@Override
	public void visualiser() {
		new IURechercheArticle("Visualiser un article", 500, 500, Mode.VISUALISATION);
	}
	
	@Override
	public void lister(){
		new IUAffichageListeArticle("Liste des articles", 800, 600, Mode.OTHER, ServiceArticle.getInstance().getEntites());
	}
	
	@Override
	public void modifier() {
		new IURechercheArticle("Modifier un article", 500, 500, Mode.MODIFICATION);
	}
	
	@Override
	public void supprimer() {
		new IURechercheArticle("Supprimer un article", 500, 500, Mode.SUPPRESSION);
		
	}

}
