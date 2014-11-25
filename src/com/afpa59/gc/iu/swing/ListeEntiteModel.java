package com.afpa59.gc.iu.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.afpa59.gc.donnees.Entite;

public abstract class ListeEntiteModel extends AbstractTableModel{

	private final List<Entite> entites = new ArrayList<Entite>();
	private final List<String> entetes = new ArrayList<String>();
	
	/*-------------------------- CONSTRUCTEUR --------------------------*/
	/**
	 * constructeur
	 * @param entites
	 */
	public ListeEntiteModel(List<Entite> entites) {
		super();
		for(Entite entite:entites){
			this.entites.add(entite);
		}
		entetes.add("id");
	}
	
	/*-------------------------- GETTER ---------------------------------*/
	/**
	 * @return entites
	 */
	public List<Entite> getEntites() {
		return this.entites;
	}
	
	/**
	 * @return les entetes
	 */
	public List<String> getEntetes(){
		return this.entetes;
	}
	/*-------------------------- METHODES ---------------------------------*/
	/**
	 * @param colIndex l'index de la colonne 
	 */
	public String getColumnName(int colIndex){
		return entetes.get(colIndex);
	}
	
	/**
	 * retourne le nombre de colonnes
	 */
	@Override
	public int getColumnCount() {
		return entetes.size(); 
	}
	
	/**
	 * retourne le nombre de lignes dans le modele
	 */
	@Override
	public int getRowCount() {
		return entites.size();
	}

	/**
	 * indique que les cellules ne sont pas editables
	 * @param row
	 * @param col
	 */
	@Override
    public boolean isCellEditable(int row, int col){
    	return false;
    }
	
	/**
	 * ajoute une entite au modèle
	 * @param entite
	 */
	public void ajouterEntite(Entite entite) {
        entites.add(entite);
 
        fireTableRowsInserted(entites.size()-1, entites.size()-1);
    }
 
	/**
	 * supprime une entite au modèle
	 * @param row
	 */
    public void supprimerEntite(int row) {
        entites.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    /*
    public void modifierEntite(int row){
    	fireTableRowsUpdated(row, row);
    }*/


}
