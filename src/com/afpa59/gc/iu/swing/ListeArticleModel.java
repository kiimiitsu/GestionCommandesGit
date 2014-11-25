package com.afpa59.gc.iu.swing;

import java.util.List;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.donnees.Entite;

public class ListeArticleModel extends ListeEntiteModel {

	/*-------------------------- CONSTRUCTEUR --------------------------*/
	/**
	 * constructeur
	 * @param entites
	 */
	public ListeArticleModel(List<Entite> entites) {
		super(entites);
		getEntetes().add("Libelle");
		getEntetes().add("Prix");		
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
	            return ((Article) getEntites().get(row)).getLibelle();
	        case 2:
	            return ((Article) getEntites().get(row)).getPrix();
	        default:
	            return null; //Ne devrait jamais arriver
	    }
	}
}
