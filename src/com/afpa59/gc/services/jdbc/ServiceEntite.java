package com.afpa59.gc.services.jdbc;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.fichier.Critere;
import com.afpa59.gc.services.fichier.ObjetInexistantException;


public interface ServiceEntite {
	//CRUD des entités
	public void creer(Entite entite) throws IOException; 
	public void visualiser();
	public void visualiser(int id) throws ObjetInexistantException;
	public void modifier(int id, Entite entite) throws ObjetInexistantException;
	public void supprimer(int id) throws ObjetInexistantException;
	
	//Autres actions sur les entités
	public List<Entite> chercherEntite(Critere c) throws ObjetInexistantException ;
	public Entite rechercherParId(int id) throws ObjetInexistantException;
	
	//Actions de sauvegarde et chargement des entités en fichier
	public void sauvegardeEntites(boolean bSUite) throws IOException;
	public String getEnregistrement(Entite entite);
	public Entite lireEntite(StringTokenizer st);
	public void charger();
}
