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

import javax.security.auth.callback.TextInputCallback;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.services.fichier.ObjetInexistantException;
import com.afpa59.gc.services.fichier.ServiceClient;

@SuppressWarnings("serial")
public class IUAffichageClient extends JFrame{
	
	private ServiceClient service;
	
	private Mode mode;
	
	private Client client;
	private JLabel labelId;
	private JLabel labelNom;
	private JLabel labelPrenom;
	private JLabel labelAdresse;
	
	private JTextField txtId;
	private JTextField txtNom;
	private JTextField txtPrenom;
	private JTextField txtAdresse;
	
	private JButton butOk;
	
	public IUAffichageClient(String titre, int w, int h, Mode mode, Client client){
		super(titre);
		
		this.service = ServiceClient.getInstance();
		this.mode = mode;
		this.client = client;
		
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
				titre.setText(" Création d'un client ");
				break;
			case MODIFICATION:
				titre.setText(" Modification d'un client ");
				break;
			case VISUALISATION:
				titre.setText(" Visualiser un client ");
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
		
		labelId = new JLabel("id : ");
		labelNom = new JLabel("nom : ");
		labelPrenom = new JLabel("prénom : ");
		labelAdresse = new JLabel("adresse : ");
		
		switch(mode){
		
			case CREATION:
				txtId = new JTextField(10);
				txtId.setEditable(false);
				txtNom = new JTextField(10);
				txtPrenom = new JTextField(10);
				txtAdresse = new JTextField(10);
				
				butOk = new JButton("Créer un client");
				
				break;
			case MODIFICATION:
				if(client!=null){
					txtId = new JTextField(client.getId()+"");
					txtId.setEditable(false);
					txtNom = new JTextField(client.getNom());
					txtPrenom = new JTextField(client.getPrenom());
					txtAdresse = new JTextField(client.getAdresse());
					
					butOk = new JButton("Modifier le client");
				}
				break;
			case VISUALISATION:
				txtId = new JTextField(client.getId()+"");
				txtId.setEditable(false);
				txtNom = new JTextField(client.getNom());
				txtNom.setEditable(false);
				txtPrenom = new JTextField(client.getPrenom());
				txtPrenom.setEditable(false);
				txtAdresse = new JTextField(client.getAdresse());
				txtAdresse.setEditable(false);
				
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
		global.add(labelNom, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		global.add(labelPrenom, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		global.add(labelAdresse, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		//gbc.gridwidth = 2;
		gbc.weightx = 0.5;
		global.add(txtId, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		global.add(txtNom, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		global.add(txtPrenom, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		global.add(txtAdresse, gbc);

		
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
		Client client = new Client();
		client.setId(service.getCompteur());
		client.setNom(txtNom.getText());
		client.setPrenom(txtPrenom.getText());
		client.setAdresse(txtAdresse.getText());
		
		try {
			service.creer(client);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		JOptionPane.showMessageDialog(IUAffichageClient.this, "Le client a bien été créé.");
		this.dispose();
		new IUAffichageClient("Visualisation", 500, 400, Mode.VISUALISATION, client);
	}

	public void actionUpdate(ActionEvent event){
		Client client = this.client;
		client.setNom(txtNom.getText());
		client.setPrenom(txtPrenom.getText());
		client.setAdresse(txtAdresse.getText());
		try {
			service.modifier(client.getId(), client);
		} catch (ObjetInexistantException e1) {
			System.out.println(e1.getMessage());
		}
		JOptionPane.showMessageDialog(IUAffichageClient.this, "Le client a bien été modifié.");
		this.dispose();
		new IUAffichageClient("Visualisation", 500, 400, Mode.VISUALISATION, client);
		
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
