package com.afpa59.gc.services.commun;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.afpa59.gc.donnees.Entite;


public interface ServiceEntite {
	//CRUD des entités
	public void creer(Entite entite) throws IOException; 
	public void visualiser();
	public void visualiser(Entite entite);
	public void visualiser(int id) throws ObjetInexistantException;
	public void modifier(int id, Entite entite) throws ObjetInexistantException;
	public void supprimer(int id) throws ObjetInexistantException;
	
	public List<Entite> getEntites();
	
	public String getTableName();
	public void setTableName();
	
	public Entite lireEntite(Object source);
	public void sauvegardeEntites(boolean bSUite) throws IOException;
	public HashMap<String,String> getFields(Entite entite);
	public void charger();
	
	//Autres actions sur les entités
	public List<Entite> chercherEntite(Critere c) throws ObjetInexistantException ;
	public Entite rechercherParId(int id) throws ObjetInexistantException;
	
	//Actions de sauvegarde et chargement des entités en fichier
	
	
	
	
	public boolean isFirstRecord();
	public void setFirstRecord(boolean firstRecord);
	public void setEntites(List<Entite> entites);
	public void setCompteur(int compteur);
	public int getCompteur();
	
}
