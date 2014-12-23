package com.afpa59.gc.iu.console;

import java.io.IOException;
import java.util.Scanner;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.outils.Clavier;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceEntite;
import com.afpa59.gc.services.commun.ServiceEntiteBase;

public class IUArticle extends IUEntiteBase{
	
	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * contructeur par defaut
	 */
	public IUArticle(){
		setService(ServiceArticle.getInstance());
	}


	/*********************************** METHODES ******************************************/
	/**
	 * affiche entete du menu
	 */
	@Override
	public void afficheHeader(){
		System.out.println("\n******MENU DE GESTION DES ARTICLES******");
		super.afficheHeader();
	}
	
	/**
	 * Creer l'article
	 * @throws IOException 
	 */
	@Override
	public void creer() throws IOException{
		System.out.println(" - Créer un article -");
		String libelle = Clavier.readString("Entrez le libelle de l'article :");
		
		float prix = Clavier.readFloat("Entrez le prix de l'article : ");
		
		Article article = new Article();
		article.setLibelle(libelle);
		article.setPrix(prix);
		getService().creer(article);
	}
	
	/**
	 * modifie l'article
	 */
	@Override
	public void modifier(){
		System.out.println(" - Modifier un article -");
		getService().visualiser();
		int id = Clavier.readInt("Quel article souhaitez-vous modifier?");
		try{
			Article article = new Article((Article) getService().rechercherParId(id));
			
			String libelle = Clavier.readString("Indiquez le nouveau libelle : ");
			
			float prix = Clavier.readFloat("Indiquez le nouveau prix : ");
			
			article.setLibelle(libelle);
			article.setPrix(prix);
			getService().modifier(id, article);
		}catch(ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}
	}

}
