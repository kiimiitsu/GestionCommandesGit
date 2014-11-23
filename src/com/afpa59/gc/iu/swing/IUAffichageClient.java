package com.afpa59.gc.iu.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Entite;
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
		global.setLayout(new BorderLayout());
		
		JPanel panSaisie = new JPanel();
		panSaisie.setLayout(new GridLayout(4, 2, 20, 10));
		panSaisie.setBorder(new EmptyBorder(25, 25, 25, 25));
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
				break;
			case MODIFICATION:
				
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
				break;
			default:
				break;
		}
		
		
		
		panSaisie.add(labelId);
		panSaisie.add(txtId);
		panSaisie.add(labelNom);
		panSaisie.add(txtNom);
		panSaisie.add(labelPrenom);
		panSaisie.add(txtPrenom);
		panSaisie.add(labelAdresse);
		panSaisie.add(txtAdresse);
		
		JButton bouton = new JButton("Valider");
		bouton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				actionCreer(event);
			}
		});
		
		JPanel panOk = new JPanel();
		panOk.add(bouton);
		
		global.add(panSaisie,BorderLayout.NORTH);
		global.add(panOk, BorderLayout.CENTER);
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
		txtId.setText("");
		txtNom.setText("");
		txtPrenom.setText("");
		txtAdresse.setText("");
	}

}
