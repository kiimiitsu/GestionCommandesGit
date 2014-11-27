package com.afpa59.gc.iu.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.afpa59.gc.services.fichier.ServiceClient;

@SuppressWarnings("serial")
public class IUClient extends IUEntiteBase{

	/*------------------------------------- CONSTRUCTEUR -------------------------------------*/
	/**
	 * constructeur avec paramètres
	 * @param titre
	 * @param w
	 * @param h
	 */
	public IUClient(String titre, int w, int h) {
		super(titre, w, h);
	}
	
	/*------------------------------------- METHODES -------------------------------------*/
	public JPanel getTitlePanel(){
		JPanel titlePanel = new JPanel();
		JLabel titre = new JLabel(" Gestions des clients ");
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
		new IUAffichageClient("Création d'un client", 500, 300, Mode.CREATION, null);
		
	}
	@Override
	public void visualiser() {
		new IURechercheClient("Visualiser un client", 500, 500, Mode.VISUALISATION);
		
	}
	
	@Override
	public void lister(){
		new IUAffichageListeClient("Liste des clients", 800, 600, Mode.OTHER, ServiceClient.getInstance().getEntites());
	}
	@Override
	public void modifier() {
		new IURechercheClient("Modifier un client", 500, 500, Mode.MODIFICATION);
	}
	@Override
	public void supprimer() {
		new IURechercheClient("Supprimer un client", 500, 500, Mode.SUPPRESSION);
		
	}


}
