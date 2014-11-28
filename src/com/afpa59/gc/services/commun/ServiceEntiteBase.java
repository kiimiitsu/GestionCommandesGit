package com.afpa59.gc.services.commun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.outils.BDD;
import com.afpa59.gc.services.fichier.ServiceEntiteFichier;

public abstract class ServiceEntiteBase implements ServiceEntite{
	
	private ServiceEntite service;
	private static BDD serviceType = BDD.FICHIER;
	
	
	/*----------------------------- CONSTRUCTEUR -----------------------------------------*/
	public ServiceEntiteBase(){
		this.service = getInstance(this);
	}
	
	/*------------------------------ GETTER -------------------------------------------*/
	public ServiceEntite getService() {
		return service;
	}

	/*------------------------------ SETTER ------------------------------------------*/
	public void setService(ServiceEntite service) {
		this.service = service;
	}


	/*------------------------------- METHODES ----------------------------------------*/

	public static ServiceEntite getInstance(ServiceEntite serviceDemandeur){
		switch (serviceType) {
		case FICHIER:
			return new ServiceEntiteFichier(serviceDemandeur);

		default:
			return null;
		}
			
	}
	
	/*---------- METHODES DEFINIES DANS LA FILLE ------------*/
	public abstract void visualiser();

	public abstract void visualiser(int id) throws ObjetInexistantException; 

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
	/*------------------------------- METHODES PROPRES AU FICHIER----------------------------------------*/
	
	public void setFile(File file){
		service.setFile(file);
	}
	public List<Entite> getEntites() {
		return service.getEntites();
	}

	@Override
	public void sauvegardeEntites(boolean bSUite) throws IOException {
		service.sauvegardeEntites(bSUite);
	}


	@Override
	public Entite lireEntite(StringTokenizer st) {
		return service.lireEntite(st);
	}


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
	
	@Override
	public String getEnregistrement(Entite entite){
		return service.getEnregistrement(entite);
	}

}
