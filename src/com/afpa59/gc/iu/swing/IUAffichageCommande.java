package com.afpa59.gc.iu.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.afpa59.gc.donnees.Commande;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.fichier.ObjetInexistantException;
import com.afpa59.gc.services.fichier.ServiceCommande;

@SuppressWarnings("serial")
public class IUAffichageCommande extends JFrame{
	
	private ServiceCommande service;
	
	public IUAffichageCommande(String titre, int w, int h){
		super(titre);
//		this.service = sCom;
		this.setLayout(new BorderLayout());
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(400, 200));
		try {
			init();
		} catch (ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}
		this.setVisible(true);
	}
	
	public void init() throws ObjetInexistantException{
		JPanel global = new JPanel();
		global.setLayout(new BorderLayout());
		global.add(getMainPanel(), BorderLayout.CENTER);
		global.add(getBoutonPanel(), BorderLayout.SOUTH);
		
		getContentPane().add(global);
	}
	
	public JScrollPane getMainPanel() throws ObjetInexistantException{
		List<Entite> liste = this.service.getEntites();
		String[] entetes = {"Commande n°", "Client", "Nombre de produits", "Montant"};
		Object[][] datas = new Object[liste.size()][entetes.length];
		for(int i = 0; i<liste.size(); i++){
			Commande commande = (Commande)liste.get(i);
			datas[i][0]= commande.getId();
			datas[i][1] = commande.getClient().getId()+"."+commande.getClient().getNom()+" "+commande.getClient().getPrenom();
			datas[i][2] = commande.getLignesCommande().size();
			datas[i][3] = this.service.total(commande.getId());
		}
		
		JTable table = new JTable(datas, entetes);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new EmptyBorder(10,10,10,10));
		return scrollPane;
	}
	
	public JPanel getBoutonPanel(){
		JPanel buttonPane = new JPanel();
		
		
		return buttonPane;
	}
	
}
