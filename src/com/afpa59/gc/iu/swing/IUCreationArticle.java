package com.afpa59.gc.iu.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.services.fichier.ServiceArticle;

@SuppressWarnings("serial")
public class IUCreationArticle extends JFrame {

	private ServiceArticle service;
	
	public IUCreationArticle(String titre, int w, int h) {
		super(titre);
		this.service = ServiceArticle.getInstance();
		this.setLayout(new BorderLayout());
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(400, 200));
		init();
		this.setVisible(true);
	}

	public void init(){
		JPanel global = new JPanel();
		global.setLayout(new BorderLayout());
		JLabel labelId = new JLabel("id : ");
		JLabel labelNom = new JLabel("nom : ");
		JLabel labelPrix = new JLabel("prix : ");
		JTextField txtId = new JTextField(10);
		txtId.setEditable(false);
		JTextField txtNom = new JTextField(10);
		JTextField txtPrix = new JTextField(10);
		
		JPanel panSaisie = new JPanel();
		panSaisie.setLayout(new GridLayout(3, 2, 20, 10));
		panSaisie.setBorder(new EmptyBorder(25, 25, 25, 25));
		panSaisie.add(labelId);
		panSaisie.add(txtId);
		panSaisie.add(labelNom);
		panSaisie.add(txtNom);
		panSaisie.add(labelPrix);
		panSaisie.add(txtPrix);
		
		
		JButton bouton = new JButton("Valider");
		bouton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Article article = new Article();
				article.setId(service.getCompteur());
				article.setLibelle(txtNom.getText());
				article.setPrix(Float.parseFloat(txtPrix.getText()));
				
				try {
					service.creer(article);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				txtId.setText("");
				txtNom.setText("");
				txtPrix.setText("");
			}
			
		});
		
		JPanel panOk = new JPanel();
		panOk.add(bouton);
		
		global.add(panSaisie, BorderLayout.NORTH);
		global.add(panOk, BorderLayout.CENTER);
		getContentPane().add(global);
	}
}
