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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.fichier.ObjetInexistantException;
import com.afpa59.gc.services.fichier.ServiceClient;

public class IURechercheClient extends JFrame{
	
	private JLabel labelId;
	private JLabel labelOu;
	private JLabel labelNom;
	private JLabel labelErreurs;
	private JTextField txtId;
	private JTextField txtNom;
	
	private JButton butRecherche;
	private Mode mode;
	
	/*----------------------------- CONSTRUCTEUR ------------------------------------------*/
	/**
	 * contructeur
	 * @param titre
	 * @param w
	 * @param h
	 */
	public IURechercheClient(String titre, int w, int h, Mode mode){
		super(titre);
		
		this.mode = mode;
		
		this.setLayout(new BorderLayout());
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(800, 600));
		getContentPane().add(getTitlePanel(), BorderLayout.NORTH);
		getContentPane().add(getMainPanel(), BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	/*-------------------------------- METHODES ------------------------------------------*/
	private JPanel getTitlePanel() {
		JPanel titlePanel = new JPanel();
		JLabel titre = new JLabel(" Rechercher un client ");
		titre.setBorder(BorderFactory.createLineBorder(new Color(92, 224, 194)));
		titre.setOpaque(true);
		titre.setBackground(Color.WHITE);
		titre.setForeground(new Color(92, 224, 194));
		titre.setFont(new Font("Arial", Font.BOLD, 40));
		titlePanel.add(titre);
		return titlePanel;
		}
	
	private JPanel getMainPanel() {
		
		JPanel global = new JPanel();
		global.setLayout(new GridBagLayout());
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		//gbc.ipady = 10;
		
		labelId = new JLabel("Rechercher par id: ");
		labelOu = new JLabel(" ou ");
		labelNom = new JLabel("Rechercher par nom : ");
		labelErreurs = new JLabel();
		labelErreurs.setHorizontalAlignment(JLabel.CENTER);
		
		txtId = new JTextField(10);
		txtNom = new JTextField(10);
		
		butRecherche = new JButton("Rechercher");
		addListeners();
		
		gbc.insets = new Insets(10, 20, 10, 20);
		gbc.ipadx=10;
		gbc.ipady=10;
		gbc.gridy=2;
		gbc.gridwidth=3;
		global.add(labelErreurs, gbc);
		
		gbc.gridwidth=1;
		
		gbc.gridy=0;
		gbc.gridx=0;
		global.add(labelId, gbc);
		
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.anchor= GridBagConstraints.CENTER;
		global.add(labelOu, gbc);
		
		gbc.gridy=0;
		gbc.gridx=2;
		global.add(labelNom, gbc);

		gbc.ipadx=10;
		gbc.ipady=10;
		gbc.gridy=1;
		gbc.gridx=0;
		global.add(txtId, gbc);
		
		gbc.gridy=1;
		gbc.gridx=2;
		global.add(txtNom, gbc);
		
		gbc.ipadx=5;
		gbc.ipady=5;
		gbc.gridy=3;
		gbc.gridx=1;
		global.add(butRecherche, gbc);
		
		return global;
	}

	public void addListeners(){
		butRecherche.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					actionRecherche(event);
				} catch (ObjetInexistantException e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}
	
	public void actionRecherche(ActionEvent event) throws ObjetInexistantException{
		int id= 0;
		String getId = txtId.getText();
		String getNom = txtNom.getText();
		
		if(getId.equals("") && getNom.equals("")){ //les 2 champs sont vides
			setLabelErreur("Veuillez renseigner un champ");
		}else if(!getId.equals("") && !getNom.equals("")){//les 2 champs sont remplis
			setLabelErreur("Veuillez ne renseigner qu'un seul des deux champs");
		}else if(!getId.equals("")){ //id est rempli
			
			if(getId.matches("^\\d+$")){ //si l'id est bien un entier
				id = Integer.parseInt(getId);
				Client client = (Client)ServiceClient.getInstance().rechercherParId(id);
				
				if(mode==Mode.SUPPRESSION){
					if(JOptionPane.showConfirmDialog(this, "Etes-vous sur?")==JOptionPane.YES_OPTION){
						ServiceClient.getInstance().supprimer(id);
					}
				}else{
					new IUAffichageClient("Affichage d'un client", 400, 400, this.mode, client);
				}
				this.dispose();
			}else{
				setLabelErreur("L'id doit �tre un entier!");
			}
			
		}else{
			List<Entite> clients = ServiceClient.getInstance().rechercherParNom(getNom);
			this.dispose();
			new IUAffichageListeClient("R�sultat de la recherche", 500, 500, this.mode,clients);
		}
			
	}
	
	public void setLabelErreur(String message){
		labelErreurs.setForeground(Color.RED);
		labelErreurs.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
		labelErreurs.setText(message);
	}
}
