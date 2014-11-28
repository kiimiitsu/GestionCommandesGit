package com.afpa59.gc.iu.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.afpa59.gc.services.commun.ServiceEntite;


public abstract class IUEntiteBase extends JFrame implements IUEntite{

	private ServiceEntite service;
	
	/**
	 * constructeur
	 * @param titre
	 * @param w
	 * @param h
	 */
	public IUEntiteBase (String titre, int w, int h){
		super(titre);
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(600, 400));

		this.setLayout(new BorderLayout());
		getContentPane().add(getTitlePanel(), BorderLayout.NORTH);
		getContentPane().add(getMainPanel(), BorderLayout.CENTER);
		this.setVisible(true);
	}

	/*------------------------------------- GETTER -----------------------------------------*/
	public ServiceEntite getService() {
		return service;
	}

	/*--------------------------------------- SETTER ------------------------------------------*/
	public void setService(ServiceEntite service) {
		this.service = service;
	}

	/*---------------------------------- METHODES -------------------------------------------*/
	
	public JPanel getMainPanel(){
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JPanel panDessin = new PanelImage("bebe1.jpg");
		gbc.gridx=0;
		gbc.gridheight=5;
		gbc.weightx=0.4;
		gbc.fill=GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panDessin, gbc);
		
		JButton butCreer = new JButton("Créer");
		butCreer.addActionListener(new ChangeActionListener(0));
		JButton butVisualiser = new JButton("Visualiser");
		butVisualiser.addActionListener(new ChangeActionListener(1));
		JButton butListe = new JButton("Liste");
		butListe.addActionListener(new ChangeActionListener(2));
		JButton butModifier = new JButton("Modifier");
		butModifier.addActionListener(new ChangeActionListener(3));
		JButton butSupprimer = new JButton("Supprimer");
		butSupprimer.addActionListener(new ChangeActionListener(4));
		
		gbc.insets=new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridheight=1;
		gbc.weighty=0.20;
		gbc.weightx=0.6;
		
		gbc.gridx=1;
		gbc.gridy=0;
		mainPanel.add(butCreer, gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		mainPanel.add(butVisualiser, gbc);
		
		gbc.gridx=1;
		gbc.gridy=2;
		mainPanel.add(butListe, gbc);
		
		gbc.gridx=1;
		gbc.gridy=3;
		mainPanel.add(butModifier, gbc);
		
		gbc.gridx=1;
		gbc.gridy=4;
		mainPanel.add(butSupprimer, gbc);
		
		return mainPanel;
	}
	
	/**
	 * inner class 
	 * ActionListener
	 */
	class ChangeActionListener implements ActionListener{

		private int index;
		
		public ChangeActionListener(int index){
			this.index = index;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (this.index) {
			case 0:
				creer();
				break;
			case 1:
				visualiser();
				break;
			case 2:
				lister();
				break;
			case 3:
				modifier();
				break;
			case 4: 
				supprimer();
				break;
			default:
				break;
			}			
		}
	}
}
