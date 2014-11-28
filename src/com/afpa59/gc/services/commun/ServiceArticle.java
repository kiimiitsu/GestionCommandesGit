package com.afpa59.gc.services.commun;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.donnees.Entite;


public class ServiceArticle extends ServiceEntiteBase{
	
	private static ServiceArticle serviceArticle = null;

	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * constructeur par defaut
	 */
	private ServiceArticle() {
		setFile(new File("articles.txt"));
		serviceArticle = this;
		charger();
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
	 * affiche tous les articles
	 */
	@Override
	public void visualiser(){
		
		ListIterator<Entite> iterator = this.getEntites().listIterator();
		while(iterator.hasNext()){
			int i = iterator.nextIndex();
			Article article = (Article) iterator.next();
			System.out.println((i+1)+": id = "+article.getId()+" nom = "+article.getLibelle()+" prix = "+article.getPrix());
		}
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
	public String getEnregistrement(Entite entite){
		Article article = (Article)entite;
		return article.getId()+";"+article.getLibelle()+";"+article.getPrix();
	}
	
	/**
	 * retourne l'entité correspondante au StringTokenizer
	 */
	@Override
	public Entite lireEntite(StringTokenizer st){
		Article article = new Article();
		
		article.setId(Integer.parseInt(st.nextToken()));
		article.setLibelle(st.nextToken());
		article.setPrix(Float.parseFloat(st.nextToken()));
		
		return article;
	}
}
