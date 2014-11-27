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
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Commande;
import com.afpa59.gc.donnees.LigneCommande;
import com.afpa59.gc.services.fichier.ObjetInexistantException;
import com.afpa59.gc.services.fichier.ServiceArticle;
import com.afpa59.gc.services.fichier.ServiceClient;
import com.afpa59.gc.services.fichier.ServiceLigneCommande;

public class IUAffichageLigneCommande extends JFrame{

	private ServiceLigneCommande service;
	
	private Mode mode;
	
	private LigneCommande ligne;
	
	private JLabel labelId;
	private JLabel labelCodeArticle;
	private JLabel labelQuantite;
	
	private JTextField txtId;
	private JTextField txtCodeArticle;
	private JTextField txtQuantite;
	
	private JButton butOk;
	
	public IUAffichageLigneCommande(String titre, int w, int h, Mode mode, LigneCommande ligne, ServiceLigneCommande sLC){
		super(titre);
		
		this.mode = mode;
		this.ligne = ligne;
		this.service = sLC;
		
		this.setLayout(new BorderLayout());
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(800, 600));
		getContentPane().add(getTitlePanel(), BorderLayout.NORTH);
		getContentPane().add(getMainPanel(), BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public JPanel getTitlePanel(){
		JPanel titlePanel = new JPanel();
		JLabel titre = new JLabel();
		switch (mode) {
			case CREATION:
				titre.setText(" Création d'une ligne de commande ");
				break;
			case MODIFICATION:
				titre.setText(" Modification d'une ligne de commande ");
				break;
			case VISUALISATION:
				titre.setText(" Visualiser une ligne de commande ");
				break;
			default:
				break;
		}
		
		titre.setBorder(BorderFactory.createLineBorder(new Color(92, 224, 194)));
		titre.setOpaque(true);
		titre.setBackground(Color.WHITE);
		titre.setForeground(new Color(92, 224, 194));
		titre.setFont(new Font("Arial", Font.BOLD, 30));
		titlePanel.add(titre);
		return titlePanel;
	}

	public JPanel getMainPanel(){
		JPanel global = new JPanel();
		global.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 10;
		
		labelId = new JLabel("Id : ");
		labelCodeArticle = new JLabel("Code : ");
		labelQuantite = new JLabel("Quantité : ");
		
		switch(mode){
		
			case CREATION:
				txtId = new JTextField(10);
				txtId.setEditable(false);
				txtCodeArticle = new JTextField(10);
				txtQuantite = new JTextField(10);
				
				butOk = new JButton("Valider la saisie");
				
				break;
			case MODIFICATION:
				if(ligne!=null){
					txtId = new JTextField(ligne.getId()+"");
					txtId.setEditable(false);
					txtCodeArticle = new JTextField(ligne.getArticle().getId());
					txtQuantite = new JTextField(ligne.getQte());
					
					butOk = new JButton("Modifier la ligne");
				}
				break;
			case VISUALISATION:
				txtId = new JTextField(ligne.getId()+"");
				txtId.setEditable(false);
				txtCodeArticle = new JTextField(ligne.getArticle().getId()+"");
				txtCodeArticle.setEditable(false);
				txtQuantite = new JTextField(ligne.getQte()+"");
				txtQuantite.setEditable(false);
				
				butOk = new JButton("Fermer");
				break;
			default:
				break;
		}
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		//mise en place des éléments
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.insets = new Insets(5, 100, 5, 100);
		gbc.anchor = GridBagConstraints.LINE_END;
		global.add(labelId, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		global.add(labelCodeArticle, gbc);

		
		gbc.gridx = 0;
		gbc.gridy = 3;
		global.add(labelQuantite, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		//gbc.gridwidth = 2;
		gbc.weightx = 0.5;
		global.add(txtId, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		global.add(txtCodeArticle, gbc);

		
		gbc.gridx = 1;
		gbc.gridy = 3;
		global.add(txtQuantite, gbc);

		
		addListeners();
		gbc.gridx = 1; 
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(50, 10, 10, 10);
		global.add(butOk, gbc);
		return global;
	}
	
	public void actionCreer(ActionEvent event){
		LigneCommande ligne = new LigneCommande();
		ligne.setId(service.getCompteur());
		Article article;
		try {
			article = (Article) ServiceArticle.getInstance().rechercherParId(Integer.parseInt(txtCodeArticle.getText()));
			ligne.setArticle(article);
			ligne.setQte(Integer.parseInt(txtQuantite.getText()));
			
			service.creer(ligne);
			JOptionPane.showMessageDialog(IUAffichageLigneCommande.this, "La ligne a bien été créé.");
		} catch (ObjetInexistantException | IOException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "L'objet n'existe pas");
		}

		
		
		//this.dispose();
	}

	public void actionUpdate(ActionEvent event){
		/*Client client = this.client;
		client.setNom(txtCodeArticle.getText());
		client.setPrenom(txtLibelleArticle.getText());
		client.setAdresse(txtQuantite.getText());
		try {
			service.modifier(client.getId(), client);
		} catch (ObjetInexistantException e1) {
			System.out.println(e1.getMessage());
		}
		JOptionPane.showMessageDialog(IUAffichageClient.this, "Le client a bien été modifié.");
		this.dispose();
		new IUAffichageClient("Visualisation", 500, 400, Mode.VISUALISATION, client);
		*/
	}
	
	public void actionVisualiser(){
		this.dispose();
	}
	
	public void addListeners(){
		butOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				switch (mode) {
				case CREATION:
					actionCreer(event);
					break;

				case MODIFICATION:
					actionUpdate(event);
					break;
				case VISUALISATION:
					actionVisualiser();
					break;
				default:
					break;
				}
			}
		});
	}
}
