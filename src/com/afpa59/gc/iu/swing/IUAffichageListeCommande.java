package com.afpa59.gc.iu.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.afpa59.gc.donnees.Commande;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceCommande;

public class IUAffichageListeCommande extends JFrame{
	
	private List<Entite> entites;
	private Mode mode;
	
	private JTable table;
	private JButton bouton;
	
	private ListeCommandeModel model;
	
	public IUAffichageListeCommande(String titre, int w, int h, Mode mode, List<Entite> commandes){
		super(titre);
		
		this.entites = commandes;
		this.mode = mode;
		
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(400, 200));

		this.setLayout(new BorderLayout());
		getContentPane().add(getMainPanel(), BorderLayout.CENTER);
		getContentPane().add(getButtonsPanel(), BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	
	public JScrollPane getMainPanel(){
		model = new ListeCommandeModel(entites);
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new EmptyBorder(10,10,10,10));
		return scrollPane;
	}
	
	public JPanel getButtonsPanel(){
		JPanel panButton = new JPanel();
		
		switch(mode){
			case VISUALISATION:
				bouton = new JButton("Visualiser");
				
				break;
			case MODIFICATION:
				bouton = new JButton("Modifier");
				break;
			case SUPPRESSION:
				bouton = new JButton("Supprimer");
				break;
			default: 
				bouton = new JButton("Fermer");
				break;
		}
		
		addListeners();
		
		if(bouton!=null){
			panButton.add(bouton);
		}
		return panButton;
	}
	
	public void rowSelected(){
		
		int index = table.getSelectedRow();
		if(index!=-1){
			int id =  (int) table.getModel().getValueAt(index, 0);
			try {
				Commande commande = (Commande) ServiceCommande.getInstance().rechercherParId(id);
				new IUAffichageCommande("Affichage commande", 500, 500, this.mode, commande);
				dispose();
			} catch (ObjetInexistantException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void actionSupprimer(){
		int index = table.getSelectedRow();
		if(index!=-1){
			int id =  (int) table.getModel().getValueAt(index, 0);
			try {
				ServiceCommande.getInstance().rechercherParId(id);
				if(JOptionPane.showConfirmDialog(this, "Etes-vous sur?")==JOptionPane.YES_OPTION){
					ServiceCommande.getInstance().supprimer(id);
				}
				model.supprimerEntite(index);
				
			} catch (ObjetInexistantException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void addListeners(){
		bouton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch (mode) {
				case VISUALISATION:
					rowSelected();
					break;
				case MODIFICATION:
					rowSelected();
						break;
				case SUPPRESSION:
					actionSupprimer();
					break;
				case OTHER:
					dispose();
					break;

				default:
					break;
				}
			}
		});
	}
	
	
}
