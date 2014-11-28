package com.afpa59.gc.services.commun;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.outils.BDD;
import com.afpa59.gc.services.fichier.ServiceEntiteFichier;
import com.afpa59.gc.services.jdbc.ServiceEntiteJDBC;

public abstract class ServiceEntiteBase implements ServiceEntite{
	
	private ServiceEntite service;
	private String tableName;
	
	private BDD serviceType = BDD.JDBC;
	
	/*----------------------------- CONSTRUCTEUR -----------------------------------------*/
	public ServiceEntiteBase(){
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

	/*------------------------------- METHODES ----------------------------------------*/

	public ServiceEntite getInstance(ServiceEntite serviceDemandeur){
		switch (serviceType) {
		case FICHIER:
			return new ServiceEntiteFichier(serviceDemandeur);
		case JDBC:
			return new ServiceEntiteJDBC(serviceDemandeur);
		default:
			return null;
		}
	}
	
	/*-------------------------- METHODES COMMUNES -----------------*/
	public void visualiser(){
		for(Entite e : getEntites()){
			visualiser(e);
		}
	}

	/*---------- METHODES DEFINIES DANS LA FILLE ------------*/

	
	public abstract void visualiser(int id) throws ObjetInexistantException; 

	public abstract String getEnregistrement(Entite entite);
	
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
