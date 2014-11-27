package com.afpa59.gc.iu.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Entite;

public class ListeClientModel extends ListeEntiteModel{

	
	/*-------------------------- CONSTRUCTEUR --------------------------*/
	/**
	 * constructeur
	 * @param entites
	 */
	public ListeClientModel(List<Entite> entites) {
		super(entites);
		getEntetes().add("Nom");
		getEntetes().add("Prénom");
		getEntetes().add("Adresse");
		
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
	            return ((Client) getEntites().get(row)).getNom();
	        case 2:
	            return ((Client) getEntites().get(row)).getPrenom();
	        case 3:
	            return ((Client) getEntites().get(row)).getAdresse();
	        
	        default:
	            return null; //Ne devrait jamais arriver
	    }
	}

}
