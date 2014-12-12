package com.afpa59.gc.donnees;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

@MappedSuperclass
public class Entite {
	
	@TableGenerator(name="CUST_GENERATOR",
					table="GENERATOR_TABLE",
					pkColumnName="PRIMARY_KEY_COLUMN",
					valueColumnName="VALUE_COLUMN",
					pkColumnValue="CUST_ID",
					allocationSize=1)
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="CUST_GENERATOR")
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
