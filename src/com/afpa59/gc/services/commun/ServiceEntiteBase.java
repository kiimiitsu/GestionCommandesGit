package com.afpa59.gc.services.commun;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.outils.BDD;
import com.afpa59.gc.services.fichier.ServiceEntiteFichier;
import com.afpa59.gc.services.jdbc.ServiceEntiteJDBC;
import com.afpa59.gc.services.jdbcBase.ServiceEntiteJDBCBase;

public abstract class ServiceEntiteBase implements ServiceEntite{
	
	private ServiceEntite service;
	private String tableName;
	private Entite entiteParent;
	
	private BDD serviceType = BDD.JDBC_BASE;
	
	/*----------------------------- CONSTRUCTEUR -----------------------------------------*/
	public ServiceEntiteBase(){
		
	}
	
	public ServiceEntiteBase(Entite entiteParent){
		this.setParent(entiteParent); //a overrider si la fille a un parent
		this.service = getInstance(this);
	}
	/*------------------------------ GETTER -------------------------------------------*/
	public ServiceEntite getService() {
		return service;
	}

	public String getTableName() {
		return tableName;
	}

	public BDD getServiceType() {
		return serviceType;
	}
	
	public Entite getParent(){
		return this.entiteParent;
	}
	/*------------------------------ SETTER ------------------------------------------*/
	public void setService(ServiceEntite service) {
		this.service = service;
	}

	public void setTableName(String table) {
		this.tableName = table;
	}
	
	public void setServiceType(BDD serviceType) {
		this.serviceType = serviceType;
	}
	
	public void setParent(Entite entiteParent){
		this.entiteParent = entiteParent;
	}
	/*------------------------------- METHODES ----------------------------------------*/

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
	public void visualiser(){ //a voir
		service.visualiser();
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
	public void finaliser() throws IOException {

		service.finaliser();

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
