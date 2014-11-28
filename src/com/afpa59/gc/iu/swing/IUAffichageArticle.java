package com.afpa59.gc.iu.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceClient;

@SuppressWarnings("serial")
public class IUAffichageArticle extends JFrame{

private ServiceArticle service;
	
	private Mode mode;
	
	private Article article;
	private JLabel labelId;
	private JLabel labelLibelle;
	private JLabel labelPrix;
	
	private JTextField txtId;
	private JTextField txtLibelle;
	private JTextField txtPrix;
	
	private JButton butOk;
	
	public IUAffichageArticle(String titre, int w, int h, Mode mode, Article article){
		super(titre);
		
		this.service = ServiceArticle.getInstance();
		this.mode = mode;
		this.article = article;
		
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
				titre.setText(" Création d'un article ");
				break;
			case MODIFICATION:
				titre.setText(" Modification d'un article ");
				break;
			case VISUALISATION:
				titre.setText(" Visualiser un article ");
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
		labelLibelle = new JLabel("Libellé : ");
		labelPrix = new JLabel("Prix : ");
		
		
		switch(mode){
		
			case CREATION:
				txtId = new JTextField(10);
				txtId.setEditable(false);
				txtLibelle = new JTextField(10);
				txtPrix = new JTextField(10);
				
				butOk = new JButton("Créer un article");
				
				break;
			case MODIFICATION:
				if(article!=null){
					txtId = new JTextField(article.getId()+"");
					txtId.setEditable(false);
					txtLibelle = new JTextField(article.getLibelle());
					txtPrix = new JTextField(article.getPrix()+"");
					
					butOk = new JButton("Modifier l'article");
				}
				break;
			case VISUALISATION:
				txtId = new JTextField(article.getId()+"");
				txtId.setEditable(false);
				txtLibelle = new JTextField(article.getLibelle());
				txtLibelle.setEditable(false);
				txtPrix = new JTextField(article.getPrix()+"");
				txtPrix.setEditable(false);
				
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
		global.add(labelLibelle, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		global.add(labelPrix, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		//gbc.gridwidth = 2;
		gbc.weightx = 0.5;
		global.add(txtId, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		global.add(txtLibelle, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		global.add(txtPrix, gbc);
		
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
		Article article = new Article();
		article.setId(service.getCompteur());
		article.setLibelle(txtLibelle.getText());
		article.setPrix(Float.parseFloat(txtPrix.getText()));
		
		try {
			service.creer(article);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		JOptionPane.showMessageDialog(IUAffichageArticle.this, "L'article a bien été créé.");
		this.dispose();
		new IUAffichageArticle("Visualisation", 500, 400, Mode.VISUALISATION, article);
	}

	public void actionUpdate(ActionEvent event){
		Article article = this.article;
		article.setLibelle(txtLibelle.getText());
		article.setPrix(Float.parseFloat(txtPrix.getText()));
		try {
			service.modifier(article.getId(), article);
		} catch (ObjetInexistantException e1) {
			System.out.println(e1.getMessage());
		}
		JOptionPane.showMessageDialog(IUAffichageArticle.this, "L'article a bien été modifié.");
		this.dispose();
		new IUAffichageArticle("Visualisation", 500, 400, Mode.VISUALISATION, article);
		
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
