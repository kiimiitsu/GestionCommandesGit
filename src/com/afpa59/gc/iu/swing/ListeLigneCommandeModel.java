package com.afpa59.gc.iu.swing;

import java.util.List;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.donnees.LigneCommande;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceLigneCommande;

public class ListeLigneCommandeModel extends ListeEntiteModel {

	private ServiceLigneCommande service;
	
	/*-------------------------- CONSTRUCTEUR --------------------------*/
	/**
	 * constructeur
	 * @param entites
	 */
	public ListeLigneCommandeModel(List<Entite> entites, ServiceLigneCommande sLC) {
		super(entites);
		getEntetes().add("Code Article");
		getEntetes().add("Libelle article");
		getEntetes().add("Quantite");
		getEntetes().add("Sous total");
		
		this.service = sLC;
	}
	
	/*-------------------------- METHODES ---------------------------------*/
	/**
	 * retourne la valeur à la ligne row et dans la colonne col
	 */
	@Override
	public Object getValueAt(int row, int col) {
		int id = getEntites().get(row).getId();
		switch(col){
	        case 0:
	            return getEntites().get(row).getId();
	        case 1:
	            return ((LigneCommande)getEntites().get(row)).getArticle().getId();
	        case 2:
	            return ((LigneCommande)getEntites().get(row)).getArticle().getLibelle();
	        case 3:
				return ((LigneCommande)getEntites().get(row)).getQte();
	        case 4:
			try {
				return service.sousTotal(id)+"";
			} catch (ObjetInexistantException e) {
				System.out.println(e.getMessage());
			}
	        default:
	            return null; //Ne devrait jamais arriver
	    }
	}
}
