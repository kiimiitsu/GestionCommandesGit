package com.afpa59.gc.donnees;


public class Article extends Entite{

	private String libelle;
	private float prix;
	
	/************************************* CONSTRUCTEURS *********************************/
	/**
	 * contructeur par défaut
	 */
	public Article(){
		
	}
	
	/**
	 * constructeur
	 * @param id
	 * @param libelle
	 * @param prix
	 */
	public Article(int id, String libelle, float prix){
		super(id);
		this.libelle = libelle;
		this.prix = prix;
	}
	
	/**
	 * constructeur par copie
	 */
	public Article(Article article){
		super(article);
		this.libelle = article.libelle;
		this.prix = article.prix;
	}
	/*********************************** GETTER ***********************************************/
	/**
	 * @return libelle
	 */
	public String getLibelle(){
		return this.libelle;
	}
	
	/**
	 * @return prix
	 */
	public float getPrix(){
		return this.prix;
	}
	
	/*********************************** SETTER *********************************************/
	/**
	 * @param libelle
	 */
	public void setLibelle(String libelle){
		this.libelle = libelle;
	}
	
	/**
	 * @param prix
	 */
	public void setPrix(float prix){
		this.prix = prix;
	}
	
}
