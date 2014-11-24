package com.afpa59.gc.iu.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Entite;

public class ListeClientModel extends AbstractTableModel{

	private final List<Entite> entites = new ArrayList<Entite>();
	private final String[] entetes = {"id", "Nom", "Prénom", "Adresse"};
	
	public ListeClientModel(List<Entite> entites) {
		super();
		for(Entite entite:entites){
			this.entites.add(entite);
		}
	}
	
	@Override
	public int getColumnCount() {
		return entetes.length; 
	}

	@Override
	public int getRowCount() {
		return entites.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		switch(col){
	        case 0:
	            return entites.get(row).getId();
	        case 1:
	            return ((Client) entites.get(row)).getNom();
	        case 2:
	            return ((Client) entites.get(row)).getPrenom();
	        case 3:
	            return ((Client) entites.get(row)).getAdresse();
	        
	        default:
	            return null; //Ne devrait jamais arriver
	    }
	}
	
	public void addClient(Client client) {
        entites.add(client);
 
        fireTableRowsInserted(entites.size()-1, entites.size()-1);
    }
 
    public void removeClient(int row) {
        entites.remove(row);
 
        fireTableRowsDeleted(row, row);
    }

}
