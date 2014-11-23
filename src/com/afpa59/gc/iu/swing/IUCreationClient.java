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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.fichier.ServiceClient;

@SuppressWarnings("serial")
public class IUCreationClient extends JFrame{

	private ServiceClient service;
	private String mode;
	
	public IUCreationClient(String titre, int w, int h, ServiceClient sc) {
		super(titre);
		this.mode="creation";
		this.service = sc;
		this.setLayout(new BorderLayout());
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(400, 200));
		
		this.setVisible(true);
	}

	public IUCreationClient(String titre, int w, int h,ServiceClient sc, Entite objet) {
		super(titre);
		this.mode="modification";
		this.service = sc;
		this.setLayout(new BorderLayout());
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(400, 200));

		this.setVisible(true);
	}

	
}
