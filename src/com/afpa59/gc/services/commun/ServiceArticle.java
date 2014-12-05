package com.afpa59.gc.services.commun;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.donnees.Entite;


public class ServiceArticle extends ServiceEntiteBase{
	
	private static ServiceArticle serviceArticle = null;

	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * constructeur par defaut
	 */
	private ServiceArticle() {
		super(null);
		serviceArticle = this;
	}
	
	/*********************************** METHODES DE CLASSE *******************************/
	/**
	 * @return l'instance de la classe
	 */
	public static ServiceArticle getInstance(){
		if(serviceArticle==null)
			new ServiceArticle();
		return serviceArticle;
	}
	
	/************************************* METHODES *************************************/
	public void setTableName(){
		setTableName("article");
	}
	
	/**
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException{
		Article article = new Article((Article) entite);
		super.creer(article);
	}
	
	/**
	 * affiche l'article de id = 'id'
	 * @param id
	 */
	@Override
	public void visualiser(int id) throws ObjetInexistantException{
		Article aVoir = (Article)this.rechercherParId(id);
		
		System.out.println("Id = "+aVoir.getId()
					+" \nLibelle = "+aVoir.getLibelle()
					+" \nprix = "+aVoir.getPrix());
	}

	/**
	 * @param id
	 * @paramentite
	 */
	@Override
	public void modifier(int id, Entite entite) throws ObjetInexistantException {
		Article article = (Article) entite;
		super.modifier(id, article);
		
	}

	/**
	 * Retourne la visualisation d'un article
	 * @param entite
	 */
	@Override
	public void visualiser(Entite entite){
		Article article = (Article) entite;
		System.out.println("Id = "+article.getId()+" nom = "+article.getLibelle()+" prix = "+article.getPrix());
	}
	
	/**
	 * rechercher les articles par libelle
	 * @param libelle
	 * @return
	 * @throws ObjetInexistantException 
	 */
	public List<Entite> rechercherParLibelle(String libelle) throws ObjetInexistantException{
		return chercherEntite(new Critere(){
			public boolean critere(Entite e){
				return (((Article) e).getLibelle().equals(libelle)) ;
			}
		});
	}
	
	/**
	 * retourne la chaine correspondant à l'entité
	 */
	@Override
	public LinkedHashMap<String, String> getFields(Entite entite){
		Article article = (Article)entite;
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();

		fields.put("id", article.getId()+"");
		fields.put("libelle", article.getLibelle());
		fields.put("prix", article.getPrix()+"");

		return fields;
	}
	
	/**
	 * retourne l'entité correspondante à la source
	 */
	@Override
	public Entite lireEntite(Object source){
		Article article = new Article();
		
		int id = 0;
		String libelle = null;
		float prix = 0;
		
		switch (getServiceType()) {
			case FICHIER:
				StringTokenizer st = (StringTokenizer) source;
				id = Integer.parseInt(st.nextToken());
				libelle = st.nextToken();
				prix = Float.parseFloat(st.nextToken());
				break;
			case JDBC: //valable pour les 2 cas
			case JDBC_BASE:
				ResultSet rs = (ResultSet) source;
				try {
					id = rs.getInt("id");
					libelle = rs.getString("libelle");
					prix = rs.getFloat("prix");
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
	
			case JPA:
		
				break;
	
			default:
				
				break;
		}
		article = new Article(id, libelle, prix);
		return article;
	}

}
