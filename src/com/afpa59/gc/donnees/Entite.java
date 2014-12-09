package com.afpa59.gc.donnees;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Entite {
	
	@Id @GeneratedValue (strategy=GenerationType.TABLE)
	private int id;
	
	/*------------------------------------------ CONSTRUCTEURS ------------------------------------------*/
	/**
	 * contructeur par defaut
	 */
	public Entite(){}
	
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
		
	/*------------------------------------------ GETTER ------------------------------------------*/
	/**
	 * @return id
	 */
	public int getId(){
		return this.id;
	}
	
	/*------------------------------------------ SETTER ------------------------------------------*/
	/**
	 * @param id
	 */
	public void setId(int id){
		this.id = id;
	}
}
