package com.afpa59.gc.services.commun;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.outils.BASETYPE;
import com.afpa59.gc.services.fichier.ServiceEntiteFichier;
import com.afpa59.gc.services.jdbc.ServiceEntiteJDBC;
import com.afpa59.gc.services.jdbcBase.ServiceEntiteJDBCBase;

public abstract class ServiceEntiteBase implements ServiceEntite{
	
	private ServiceEntite service;
	private String tableName;
	private Entite entiteParent;
	
	private BASETYPE serviceType = BASETYPE.JDBC;
	
	/*----------------------------- CONSTRUCTEUR -----------------------------------------*/
	/**
	 * contrcuteur par défaut
	 */
	public ServiceEntiteBase(){
		
	}
	
	/**
	 * constructeur avec paramètres
	 * @param entiteParent
	 */
	public ServiceEntiteBase(Entite entiteParent){
		this.setParent(entiteParent); //a overrider si la fille a un parent
		this.service = getInstance(this);
	}
	/*------------------------------ GETTER -------------------------------------------*/
	/**
	 * @return service
	 */
	public ServiceEntite getService() {
		return service;
	}

	/**
	 * @return tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @return serviceType
	 */
	public BASETYPE getServiceType() {
		return serviceType;
	}
	
	/**
	 * @return entiteParent
	 */
	public Entite getParent(){
		return this.entiteParent;
	}
	/*------------------------------ SETTER ------------------------------------------*/
	/**
	 * @param service
	 */
	public void setService(ServiceEntite service) {
		this.service = service;
	}

	/**
	 * @param table
	 */
	public void setTableName(String table) {
		this.tableName = table;
	}
	
	/**
	 * @param serviceType
	 */
	public void setServiceType(BASETYPE serviceType) {
		this.serviceType = serviceType;
	}
	
	/**
	 * @param entiteParent
	 */
	public void setParent(Entite entiteParent){
		this.entiteParent = entiteParent;
	}
	/*------------------------------- METHODES ----------------------------------------*/

	/**
	 * retourne une instance de service selon le type de persistance
	 * @param serviceDemandeur
	 * @return
	 */
	public ServiceEntite getInstance(ServiceEntite serviceDemandeur){
		switch (serviceType) {
			case FICHIER:
				return new ServiceEntiteFichier(serviceDemandeur);
			case JDBC_BASE:
				return new ServiceEntiteJDBCBase(serviceDemandeur);
			case JDBC:
				return new ServiceEntiteJDBC(serviceDemandeur);
			default:
				return null;
		}
	}
	
	/*-------------------------- METHODES COMMUNES -----------------*/
	/**
	 * retourne la visualisation de toutes les entités
	 */
	public final void visualiser(){ 
		if(this.getEntites().isEmpty()){
			System.out.println("Il n'y a aucun élément a afficher!");
		}else{
			for(Entite entite : this.getEntites()){
				this.visualiser(entite);
			}
		}
	}

	
	/*---------- METHODES DEFINIES DANS LA FILLE ------------*/

	
	public abstract void visualiser(int id) throws ObjetInexistantException; 

	public abstract LinkedHashMap<String, String> getFields(Entite entite);
	
	public abstract Entite lireEntite(Object source);
	
	
	
	/*----------- METHODES FAISANT APPEL AU SERVICE CONCERNE----------------------*/
	
	@Override
	public void creer(Entite entite) throws IOException{
		service.creer(entite);
	}

	@Override
	public void modifier(int id, Entite entite) throws ObjetInexistantException {
		service.modifier(id, entite);
		
	}

	@Override
	public void supprimer(int id) throws ObjetInexistantException {
		service.supprimer(id);
	}

	@Override
	public List<Entite> chercherEntite(Critere c) throws ObjetInexistantException {
		return service.chercherEntite(c);
	}
	@Override
	public Entite rechercherParId(int id) throws ObjetInexistantException {
		return service.rechercherParId(id);
	}
	
	@Override	
	public List<Entite> getEntites() {
		return service.getEntites();
	}

	@Override
	public void sauvegardeEntites(boolean bSUite) throws IOException { // ? propre au fichier
		service.sauvegardeEntites(bSUite);
	}
	@Override
	public void finaliser(boolean first) throws IOException {
		service.finaliser(first);
	}
	
	/*------------------------------- METHODES PROPRES AU FICHIER (A SUPPRIMER ET LAISSER DANS LE FICHIER ----------------------------------------*/

	@Override
	public void charger() {
		service.charger();
	}
	public boolean isFirstRecord(){
		return service.isFirstRecord();
	}
	public void setFirstRecord(boolean firstRecord){
		service.setFirstRecord(firstRecord);
	}
	
	public void setEntites(List<Entite> entites) {
		service.setEntites(entites);
	}

	public void setCompteur(int compteur) {
		service.setCompteur(compteur);
	}
	
	public int getCompteur() {
		return service.getCompteur();
	}
	
}
