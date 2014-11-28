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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceClient;
import com.afpa59.gc.services.commun.ServiceCommande;

@SuppressWarnings("serial")
public class IUGenerale extends JFrame{

	private ServiceArticle sa;
	private ServiceClient sc;
	private ServiceCommande sCom;
	
	public IUGenerale(String titre, int w, int h) {
		super(titre);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(600, 400));
		this.setLayout(new BorderLayout());
		getContentPane().add(getTitlePanel(), BorderLayout.NORTH);
		getContentPane().add(getPanelMenu(), BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public JPanel getTitlePanel(){
		JPanel titlePanel = new JPanel();
		JLabel titre = new JLabel(" Système de gestion de commandes ");
		titre.setBorder(BorderFactory.createLineBorder(new Color(92, 224, 194)));
		titre.setOpaque(true);
		titre.setBackground(Color.WHITE);
		titre.setForeground(new Color(92, 224, 194));
		titre.setFont(new Font("Calibri", Font.BOLD, 40));
		titlePanel.add(titre);
		return titlePanel;
	}
	
	public JPanel getPanelMenu(){
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JButton butArticles = new JButton("Articles");
		butArticles.addActionListener(new ChangeEntiteListener(0));
		JButton butClients = new JButton("Clients");
		butClients.addActionListener(new ChangeEntiteListener(1));
		JButton butCommandes = new JButton("Commandes");
		butCommandes.addActionListener(new ChangeEntiteListener(2));
		JButton butQuitter = new JButton("Quitter");
		butQuitter.addActionListener(new ChangeEntiteListener(3));
		
		JPanel panDessin = new PanelImage("bebe.jpg");
		gbc.gridx=0;
		gbc.gridheight=4;
		gbc.weightx=0.4;
		gbc.fill=GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panDessin, gbc);
		
		gbc.insets=new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridheight=1;
		gbc.weighty=0.25;
		gbc.weightx=0.6;
		
		gbc.gridx=1;
		gbc.gridy=0;
		mainPanel.add(butArticles, gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		mainPanel.add(butClients, gbc);
		
		gbc.gridx=1;
		gbc.gridy=2;
		mainPanel.add(butCommandes, gbc);
		
		gbc.gridx=1;
		gbc.gridy=3;
		mainPanel.add(butQuitter, gbc);
		
		return mainPanel;
	}


	class ChangeEntiteListener implements ActionListener{

		private int index;
		
		public ChangeEntiteListener(int index){
			this.index = index;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (this.index) {
			case 0:
				new IUArticle("Gestion des articles", 1200, 800);
				break;
			case 1:
				new IUClient("Gestion des clients", 1200, 800);
				break;
			case 2:
				new IUCommande("Gestion des commandes", 1200, 800);
				break;
			case 3:
				System.exit(0);
				break;
			default:
				break;
			}			
		}
	
	}
	
}
	
