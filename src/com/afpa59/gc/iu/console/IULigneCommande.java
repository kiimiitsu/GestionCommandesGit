package com.afpa59.gc.iu.console;

import java.io.IOException;
import java.util.Scanner;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.donnees.LigneCommande;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceEntite;
import com.afpa59.gc.services.commun.ServiceEntiteBase;

public class IULigneCommande extends IUEntiteBase{

	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * constructeur par d�faut
	 */
	public IULigneCommande() {
		
	}
	
	/**
	 * constructeur avec param�tres
	 * @param se
	 * @param scanner
	 * @param sa
	 */
	public IULigneCommande(ServiceEntite se, Scanner scanner){
		super(se, scanner);
	}
	
	/*********************************** METHODES ******************************************/
	public ServiceArticle getServiceArticle(){
		return ServiceArticle.getInstance();
	}
	
	/**
	 * affiche entete du menu
	 */
	@Override
	public void afficheHeader(){
		System.out.println("\n******MENU DE GESTION LIGNE COMMANDE******");
		super.afficheHeader();
	}
	
	/**
	 * menu de cr�ation ligne de commande
	 * @throws IOException 
	 */
	@Override
	public void creer() throws IOException {
		String choix = null;
		do{
			System.out.println("***************Ins�rer une ligne de commande***************");
			System.out.println("Code produit : ");
			int code = getScanner().nextInt();
			
			try {
				Article article = (Article) getServiceArticle().rechercherParId(code); // lance une exception si l'article n'existe pas
				System.out.println("Quantit� : ");
				int qte = getScanner().nextInt();
				
				LigneCommande lc = new LigneCommande();
				lc.setArticle(article);
				lc.setQuantite(qte);
				getService().creer(lc);
			} catch (ObjetInexistantException e) {
				System.out.println(e.getMessage());
			}
			
			getScanner().nextLine(); //vidage
			System.out.println("Continuer? O/N");
			choix = getScanner().nextLine();
		}while(choix.equalsIgnoreCase("o"));
	}

	/**
	 * menu de modification d'une ligne de commande
	 */
	@Override
	public void modifier() {
		System.out.println(" - Modifier une ligne de commande -");
		getService().visualiser();
		
		System.out.println("Quel ligne souhaitez-vous modifier?");
		int id = getScanner().nextInt();
		
		try {
			LigneCommande lc =(LigneCommande) getService().rechercherParId(id); //lance une exception si ligne inexistante
			
			System.out.println("Indiquez le nouveau code : ");
			int code = getScanner().nextInt();
			Article article = (Article) getServiceArticle().rechercherParId(code); //lance une exception si l'article entr� n'existe pas

			System.out.println("Indiquez la nouvelle quantit� : ");
			int qte = getScanner().nextInt();
			
			getScanner().nextLine();//vidage
			
			lc.setArticle(article);
			lc.setQuantite(qte);
			getService().modifier(id, lc);
		} catch (ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}		
	}

}
