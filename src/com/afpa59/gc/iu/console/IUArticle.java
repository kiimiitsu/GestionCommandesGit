package com.afpa59.gc.iu.console;

import java.io.IOException;
import java.util.Scanner;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceEntite;
import com.afpa59.gc.services.commun.ServiceEntiteBase;

public class IUArticle extends IUEntiteBase{
	
	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * contructeur par defaut
	 */
	public IUArticle(){
		
	}
	
	/**
	 * constructeur avec parametres
	 * @param se
	 * @param scanner
	 */
	public IUArticle(ServiceEntite se, Scanner scanner){
		super(se, scanner);
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
		System.out.println("Entrez le libelle de l'article :");
		String libelle = getScanner().nextLine();
		System.out.println("Entrez le prix de l'article : ");
		float prix = getScanner().nextFloat();
		
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
		System.out.println("Quel article souhaitez-vous modifier?");
		int id = getScanner().nextInt();
		try{
			Article article = new Article((Article) getService().rechercherParId(id));
			
			System.out.println("Indiquez le nouveau libelle : ");
			this.getScanner().nextLine(); //vidage
			String libelle = getScanner().nextLine();
			
			System.out.println("Indiquez le nouveau prix : ");
			float prix = getScanner().nextFloat();
			
			article.setLibelle(libelle);
			article.setPrix(prix);
			getService().modifier(id, article);
		}catch(ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}
	}

}
