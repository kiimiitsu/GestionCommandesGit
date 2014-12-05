package com.afpa59.gc.services.jdbc;

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
import java.util.Map.Entry;

import org.omg.CORBA.portable.ValueBase;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.outils.MyDataBase;
import com.afpa59.gc.services.commun.Critere;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceEntite;

public class ServiceEntiteJDBC implements ServiceEntite{
	
	private Connection connexion;
	private String table;
	
	private ServiceEntite serviceDemandeur;
	
	/*----------------------------------- CONSTRUCTEUR -----------------------------------*/
	/**
	 * constructeur par defaut
	 */
	public ServiceEntiteJDBC() {
		this.connexion = MyDataBase.getConnection();
	}
	
	/**
	 * constructeur
	 * @param serviceDemandeur
	 */
	public ServiceEntiteJDBC(ServiceEntite serviceDemandeur){
		this();
		this.serviceDemandeur = serviceDemandeur;
		configTable();
	}
	
	/*----------------------------------- GETTER -----------------------------------*/
		
	public String getTable() {
		return table;
	}
	
	/*----------------------------------- SETTER -----------------------------------*/

	public void setTable(String table) {
		this.table = table;
	}
	/*----------------------------------- METHODES -----------------------------------*/
	
	public void configTable(){
		serviceDemandeur.setTableName();
		this.setTable(serviceDemandeur.getTableName());
	}
	
	@Override
	public List<Entite> getEntites() {
		List<Entite> entites = new ArrayList<Entite>();
		Statement stmt;
		try {
			stmt = MyDataBase.getConnection().createStatement();
			String sql = "SELECT * FROM "+table;
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Entite entite = serviceDemandeur.lireEntite(rs);
				if(entite!=null){
					entites.add(entite);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return entites;
	}
	
	/**
	 * ajoute une entité à la liste
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException {
		Statement stmt;
		try {
			HashMap<String, String> fields = serviceDemandeur.getFields(entite);
			fields.remove("id");
			
			stmt = this.connexion.createStatement();
			
			String insertQuery = "INSERT INTO "+table+"(";
			
			int i = 0;
			
			// les champs
			for(String s:fields.keySet()){
				insertQuery+=s;
			
				if(i!=fields.size()-1){
					insertQuery+=",";
				}
				i++;
			}
			
			insertQuery+=") VALUES (";
			
			i = 0;
			
			// les valeurs
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
				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * modifie un objet par entite
	 * @param id
	 * @param entite
	 */
	@Override
	public void modifier(int id, Entite entite) throws ObjetInexistantException {
		
		Statement stmt;
		try {
			HashMap<String, String> fields = serviceDemandeur.getFields(entite);
			fields.remove("id");
			
			stmt = this.connexion.createStatement();
			
			String updateQuery = "UPDATE "+table+" SET ";
			
			int i = 0;

			for(Entry<String, String> entry:fields.entrySet()){
				
				updateQuery+=entry.getKey()+"='"+entry.getValue()+"'";
				if(i!=fields.size()-1){
					updateQuery+=",";
				}
				
				i++;
			}

			updateQuery+=" WHERE id="+id;
			System.out.println(updateQuery);
			stmt.execute(updateQuery);
				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * supprime l'objet avec l'id 'id'
	 * @param id
	 */
	@Override
	public void supprimer(int id) throws ObjetInexistantException {
		Statement stmt;
		try {
			stmt = this.connexion.createStatement();
			String deleteSql = "DELETE FROM "+table+" WHERE id="+id;
			System.out.println(deleteSql);
			stmt.execute(deleteSql);
				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * fonction de recherche d'entite avec n'importe quel critere
	 * @param critere
	 */
	@Override
	public List<Entite> chercherEntite(Critere c) throws ObjetInexistantException {
		
		List<Entite> match = new ArrayList<Entite>();
		ListIterator<Entite> iterator = this.getEntites().listIterator();
		
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
		if(this.getEntites().isEmpty()){
			System.out.println("Il n'y a aucun élément a afficher!");
		}else{
			for(Entite entite : this.getEntites()){
				serviceDemandeur.visualiser(entite);
			}
		}
	}
	
	@Override
	public void finaliser(){
		
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

	@Override
	public void setCompteur(int compteur) {}

	@Override
	public int getCompteur() {return 0;}

	@Override
	public void setEntites(List<Entite> entites) {}
	
	@Override
	public void sauvegardeEntites(boolean bSuite) throws IOException{}
	
	@Override
	public void charger(){}

}
