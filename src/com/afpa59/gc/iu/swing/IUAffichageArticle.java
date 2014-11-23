package com.afpa59.gc.iu.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

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
import com.afpa59.gc.services.fichier.ServiceArticle;

@SuppressWarnings("serial")
public class IUAffichageArticle extends JFrame{

	private ServiceArticle service;
	
	public IUAffichageArticle(String titre, int w, int h){
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
		
		JPanel panSaisie = new JPanel();
		panSaisie.setLayout(new GridLayout(4, 2, 20, 10));
		panSaisie.setBorder(new EmptyBorder(25, 25, 25, 25));
		JLabel labelId = new JLabel("id : ");
		JLabel labelNom = new JLabel("nom : ");
		JLabel labelPrenom = new JLabel("prénom : ");
		JLabel labelAdresse = new JLabel("adresse : ");
		
		JTextField txtId = new JTextField(10);
		txtId.setEditable(false);
		JTextField txtNom = new JTextField(10);
		JTextField txtPrenom = new JTextField(10);
		JTextField txtAdresse = new JTextField(10);
		
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
			public void actionPerformed(ActionEvent e) {
				Client client = new Client();
				client.setId(service.getCompteur());
				client.setNom(txtNom.getText());
				client.setPrenom(txtPrenom.getText());
				client.setAdresse(txtAdresse.getText());
				
				try {
					service.creer(client);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(IUAffichageArticle.this, "L'article a bien été créé.");
				txtId.setText("");
				txtNom.setText("");
				txtPrenom.setText("");
				txtAdresse.setText("");
			}
		});
		
		JPanel panOk = new JPanel();
		panOk.add(bouton);
		
		global.add(panSaisie,BorderLayout.NORTH);
		global.add(panOk, BorderLayout.CENTER);
		getContentPane().add(global);
		
	}
	
}
