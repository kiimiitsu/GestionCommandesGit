package com.afpa59.gc.donnees;

public class Entite {
	
	private int id;
	
	/********************************** CONSTRUCTEURS *********************************/
	/**
	 * contructeur par defaut
	 */
	public Entite(){
		
	}
	
	/**
	 * constructeur avec paramètres
	 * @param id
	 */
	public Entite(int id){
		this.id = id;
	}
	
	/**
	 * constructeur par copie
	 * @param entite
	 */
	public Entite(Entite entite){
		this.id = entite.id;
	}
		
	
	/***************************** GETTER ************************************/
	/**
	 * @return id
	 */
	public int getId(){
		return this.id;
	}
	
	/****************************** SETTER ************************************/
	/**
	 * @param id
	 */
	public void setId(int id){
		this.id = id;
	}
}
