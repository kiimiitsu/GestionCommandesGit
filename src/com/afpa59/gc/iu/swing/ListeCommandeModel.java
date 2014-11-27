package com.afpa59.gc.iu.swing;

import java.util.List;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Commande;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.fichier.ObjetInexistantException;
import com.afpa59.gc.services.fichier.ServiceCommande;

public class ListeCommandeModel extends ListeEntiteModel {

	/*-------------------------- CONSTRUCTEUR --------------------------*/
	/**
	 * constructeur
	 * @param entites
	 */
	public ListeCommandeModel(List<Entite> entites) {
		super(entites);
		getEntetes().add("Client");
		getEntetes().add("Nombre de lignes");
		getEntetes().add("Montant total");
		
	}
	
	/*-------------------------- METHODES ---------------------------------*/
	/**
	 * retourne la valeur à la ligne row et dans la colonne col
	 */
	@Override
	public Object getValueAt(int row, int col) {
		switch(col){
	        case 0:
	            return getEntites().get(row).getId();
	        case 1:
	        	Client client = ((Commande) getEntites().get(row)).getClient();
	            return client.getNom()+" "+client.getPrenom();
	        case 2:
	            return ((Commande) getEntites().get(row)).getLignesCommande().size();
	        case 3:
			try {
				return ServiceCommande.getInstance().total((getEntites().get(row)).getId());
			} catch (ObjetInexistantException e) {
				System.out.println(e.getMessage());
			}
	        default:
	            return null; //Ne devrait jamais arriver
	    }
	}
}
