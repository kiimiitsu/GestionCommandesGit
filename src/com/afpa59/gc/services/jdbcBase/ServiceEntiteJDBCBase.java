package com.afpa59.gc.services.jdbcBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.outils.MyDataBase;
import com.afpa59.gc.services.commun.Critere;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceEntite;

public class ServiceEntiteJDBCBase implements ServiceEntite{
	
	private List<Entite> entites;
	private int compteur;
	private Connection connexion;
	private String table;
	
	private ServiceEntite serviceDemandeur;
	
	/*----------------------------------- CONSTRUCTEUR -----------------------------------*/
	/**
	 * constructeur par defaut
	 */
	public ServiceEntiteJDBCBase() {
		this.entites = new ArrayList<Entite>();
		this.compteur = 1;
		this.connexion = MyDataBase.getConnection();
	}
	
	/**
	 * constructeur
	 * @param serviceDemandeur
	 */
	public ServiceEntiteJDBCBase(ServiceEntite serviceDemandeur){
		this();
		this.serviceDemandeur = serviceDemandeur;
		configTable();
	}
	
	/*----------------------------------- GETTER -----------------------------------*/
	/**
	 * @return les entites du service
	 */
	public List<Entite> getEntites() {
		return entites;
	}
	
	/**
	 * @return le compteur id
	 */
	public int getCompteur() {
		return compteur;
	}
	
	public String getTable() {
		return table;
	}
	
	/*----------------------------------- SETTER -----------------------------------*/
	/** 
	 * @param entites
	 */
	public void setEntites(List<Entite> entites) {
		this.entites = entites;
	}
	
	/**
	 * @param compteur
	 */
	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}

	public void setTable(String table) {
		this.table = table;
	}
	/*----------------------------------- METHODES -----------------------------------*/
	
	public void configTable(){
		serviceDemandeur.setTableName();
		this.setTable(serviceDemandeur.getTableName());
	}
	
	/**
	 * ajoute une entité à la liste
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException {
		this.getEntites().add(entite);
		this.compteur++;
	}
	
	/**
	 * modifie un objet par entite
	 * @param id
	 * @param entite
	 */
	@Override
	public void modifier(int id, Entite entite) throws ObjetInexistantException {
		int index = this.entites.indexOf(this.rechercherParId(id));
		this.entites.remove(index);
		this.entites.add(index, entite);
	}

	/**
	 * supprime l'objet avec l'id 'id'
	 * @param id
	 */
	@Override
	public void supprimer(int id) throws ObjetInexistantException {
		this.entites.remove(this.rechercherParId(id));
	}

	/**
	 * fonction de recherche d'entite avec n'importe quel critere
	 * @param critere
	 */
	@Override
	public List<Entite> chercherEntite(Critere c) throws ObjetInexistantException {
		List<Entite> match = new ArrayList<Entite>();
		ListIterator<Entite> iterator = this.entites.listIterator();
		
		while (iterator.hasNext()){
			Entite entite = iterator.next();
			if(c.critere(entite)){
				match.add(entite);
			}
		}
		if(match.isEmpty()){
			throw new ObjetInexistantException("L'objet auquel vous tentez d'accéder est inexistant !");
		}
		return match;
	}
	
	/**
	 * fonction de recherche par id
	 * @param id
	 */
	@Override
	public Entite rechercherParId(int id) throws ObjetInexistantException{
		List<Entite> resultats = chercherEntite(new Critere(){
													public boolean critere(Entite e){
														return (e.getId() == id) ;
													}
												});
		Entite entite = resultats.get(0);
		return entite; //id unique, retourne l'unique élément de la recherche.
	}
	
	
	@Override
	public void sauvegardeEntites(boolean bSuite) throws IOException{
		Statement stmt;
		try {
			stmt = this.connexion.createStatement();
			
			for(Entite entite:this.getEntites()){
				String insertQuery = "INSERT INTO "+table+" VALUES (";
				LinkedHashMap<String,String> fields = serviceDemandeur.getFields(entite);
				int i = 0;
				for(String s: fields.values()){
					insertQuery+="'"+s+"'";
					
					if(i!=(fields.size()-1)){
						insertQuery+=",";
					}
					i++;
				}
				
				
				insertQuery += ")";
				System.out.println(insertQuery);
				stmt.execute(insertQuery);
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
	
	@Override
	public void charger(){
		Statement stmt;
		try {
			stmt = this.connexion.createStatement();
			String sql = "SELECT * FROM "+table;
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Entite entite = serviceDemandeur.lireEntite(rs);
				if(entite!=null){
					this.getEntites().add(entite);
				}
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		if(!entites.isEmpty()){ //on vérifie qu'on ne rentre pas dans une liste vide
			this.compteur = this.entites.get(this.entites.size()-1).getId()+1;
		}
		
	}

	public static void deleteTables(){
		Statement stmt;
		try {
			stmt = MyDataBase.getConnection().createStatement();
			String drop = "TRUNCATE TABLE ligneCommande; TRUNCATE TABLE commande; TRUNCATE TABLE article; TRUNCATE TABLE client;";
			stmt.execute(drop);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void visualiser() {
		for(Entite e : getEntites()){
			serviceDemandeur.visualiser(e);
		}
	}
	
	@Override
	public void finaliser() throws IOException{
		deleteTables();
		sauvegardeEntites(false);
	}
	
	/*------------------------------------------NON IMPLEMENTE ----------------------------------------------*/
	

	@Override
	public void visualiser(Entite entite) {}

	@Override
	public void visualiser(int id) throws ObjetInexistantException {}

	@Override
	public String getTableName() {return null;}

	@Override
	public void setTableName() {}

	@Override
	public LinkedHashMap<String, String> getFields(Entite entite) {return null;}

	@Override
	public Entite lireEntite(Object source) {return null;}

	@Override
	public boolean isFirstRecord() {return false;}

	@Override
	public void setFirstRecord(boolean firstRecord) {}

}
