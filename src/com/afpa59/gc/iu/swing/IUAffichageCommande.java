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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Commande;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceClient;
import com.afpa59.gc.services.commun.ServiceCommande;
import com.afpa59.gc.services.commun.ServiceLigneCommande;

@SuppressWarnings("serial")
public class IUAffichageCommande extends JFrame{
	
private ServiceCommande service;
	
	private Mode mode;
	
	private Commande commande;
	private JLabel labelId;
	private JLabel labelIdClient;
	
	private JTextField txtId;
	private JTextField txtIdClient;
	
	private JButton butOk;
	
	public IUAffichageCommande(String titre, int w, int h, Mode mode, Commande commande){
		super(titre);
		
		this.service = ServiceCommande.getInstance();
		this.mode = mode;
		this.commande = commande;
		
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
				titre.setText(" Création d'une commande ");
				break;
			case MODIFICATION:
				titre.setText(" Modification d'une commande ");
				break;
			case VISUALISATION:
				titre.setText(" Visualiser une commande ");
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
		labelIdClient = new JLabel("Id du client : ");
		
		switch(mode){
		
			case CREATION:
				txtId = new JTextField(10);
				txtId.setEditable(false);
				txtIdClient = new JTextField(10);
				
				butOk = new JButton("Saisir la commande");
				
				break;
			case MODIFICATION:
				if(commande!=null){
					txtId = new JTextField(commande.getId()+"");
					txtId.setEditable(false);
					txtIdClient = new JTextField(commande.getClient().getId());
					txtIdClient.setEditable(false);
					butOk = new JButton("Modifier les lignes commande");
				}
				break;
			case VISUALISATION:
				
				txtId = new JTextField(commande.getId()+"");
				txtId.setEditable(false);
				txtIdClient = new JTextField(commande.getClient().getId()+"");
				txtIdClient.setEditable(false);
				
				butOk = new JButton("Visualiser les lignes de commande");
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
		global.add(labelIdClient, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		//gbc.gridwidth = 2;
		gbc.weightx = 0.5;
		global.add(txtId, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		global.add(txtIdClient, gbc);


		
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
		Commande commande = new Commande();
		commande.setId(service.getCompteur());
		Client client;
		try {
			client = (Client) ServiceClient.getInstance().rechercherParId(Integer.parseInt(txtIdClient.getText()));
			commande.setClient(client);
		} catch (ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}
		
		
		try {
			service.creer(commande);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		JOptionPane.showMessageDialog(IUAffichageCommande.this, "La commande a bien été créé.");
		ServiceLigneCommande sLC = new ServiceLigneCommande(commande);
		new IUAffichageLigneCommande("Saisie des lignes de commande", 500, 500, Mode.CREATION, null, sLC);
		//new IUAffichageCommande("Visualisation", 500, 400, Mode.VISUALISATION, client);
	}

	public void actionUpdate(ActionEvent event){
		/*Client client = this.client;
		client.setNom(txtNom.getText());
		client.setPrenom(txtPrenom.getText());
		client.setAdresse(txtAdresse.getText());
		try {
			service.modifier(client.getId(), client);
		} catch (ObjetInexistantException e1) {
			System.out.println(e1.getMessage());
		}
		JOptionPane.showMessageDialog(IUAffichageCommande.this, "Le client a bien été modifié.");
		this.dispose();
		//new IUAffichageCommande("Visualisation", 500, 400, Mode.VISUALISATION, client);
		*/
	}
	
	public void actionVisualiser(){
		ServiceLigneCommande sLC = new ServiceLigneCommande(commande);
		new IUAffichageListeLigneCommande("Liste des saisies", 500, 500, Mode.VISUALISATION, sLC);
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
	
	public void addListenerAlternatif(){
		txtIdClient.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(!txtIdClient.getText().equals("")){
					try {
						List<Entite> result = ServiceClient.getInstance().rechercherParNom(txtIdClient.getText());
						String[] tabResult = new String[result.size()];
						for(int i = 0; i<result.size(); i++){
							Client client = (Client) result.get(i);
							tabResult[i] = client.getId()+" "+client.getNom()+" "+client.getPrenom();
						}
						
						
						String clientChoisi = (String) JOptionPane.showInputDialog(IUAffichageCommande.this,
								"Choisissez le client : ",
								"Client",
								JOptionPane.QUESTION_MESSAGE,
								null,
								tabResult,
								tabResult[0]);
						if(clientChoisi!=null){
							StringTokenizer st = new StringTokenizer(clientChoisi, " ");
							int id = Integer.parseInt(st.nextToken());
							Client client = (Client) ServiceClient.getInstance().rechercherParId(id);
							
							//butOk.setText("Valider");
							new IULigneCommande("Ligne de commande", 500, 500);
						}
					} catch (ObjetInexistantException e1) {
						System.out.println(e1.getMessage());
					}
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("entré");
				
			}
		});
	}
}
