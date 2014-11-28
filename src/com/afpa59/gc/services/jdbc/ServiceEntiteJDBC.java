package com.afpa59.gc.services.jdbc;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.commun.Critere;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceEntite;

public class ServiceEntiteJDBC implements ServiceEntite{
	
	private List<Entite> entites;
	private Connection connexion;
	private String table;
	
	private ServiceEntite serviceDemandeur;
	
	/*----------------------------------- CONSTRUCTEUR -----------------------------------*/
	/**
	 * constructeur par defaut
	 */
	public ServiceEntiteJDBC() {
		this.entites = new ArrayList<Entite>();
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
	/**
	 * @return les entites du service
	 */
	public List<Entite> getEntites() {
		return entites;
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

	public void setTable(String table) {
		this.table = table;
	}
	/*----------------------------------- METHODES -----------------------------------*/
	
	public void configTable(){
		this.setTable(table);
	}
	
	/**
	 * ajoute une entité à la liste
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException {
		this.getEntites().add(entite);
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


	}
	
	@Override
	public void charger(){
		Statement stmt;
		try {
			stmt = this.connexion.createStatement();
			String sql = "SELECT * FROM "+table;
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Entite entite = serviceDemandeur.lireEntite(rs);
				System.out.println(rs.getInt("id"));
				if(entite!=null){
					this.getEntites().add(entite);
				}
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	/*------------------------------------------NON IMPLEMENTE ----------------------------------------------*/
	@Override
	public void visualiser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visualiser(Entite entite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visualiser(int id) throws ObjetInexistantException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTableName() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEnregistrement(Entite entite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entite lireEntite(Object source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFirstRecord() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFirstRecord(boolean firstRecord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCompteur(int compteur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCompteur() {
		// TODO Auto-generated method stub
		return 0;
	}
}
