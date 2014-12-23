package com.afpa59.gc.iu.console;

import java.io.IOException;
import java.util.Scanner;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.donnees.LigneCommande;
import com.afpa59.gc.outils.Clavier;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceEntite;
import com.afpa59.gc.services.commun.ServiceEntiteBase;

public class IULigneCommande extends IUEntiteBase{

	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * constructeur par défaut
	 */
	public IULigneCommande() {
		
	}
	
	/**
	 * constructeur avec param�tres
	 * @param se
	 * @param scanner
	 * @param sa
	 */
	public IULigneCommande(ServiceEntite se){
		setService(se);
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
			System.out.println("***************Insérer une ligne de commande***************");
			int code = Clavier.readInt("Code produit : ");
			
			try {
				Article article = (Article) getServiceArticle().rechercherParId(code); // lance une exception si l'article n'existe pas
				int qte = Clavier.readInt("Quantité : ");
				
				LigneCommande lc = new LigneCommande();
				lc.setArticle(article);
				lc.setQuantite(qte);
				getService().creer(lc);
			} catch (ObjetInexistantException e) {
				System.out.println(e.getMessage());
			}
			
			choix = Clavier.readString("Continuer? O/N");
		}while(choix.equalsIgnoreCase("o"));
	}

	/**
	 * menu de modification d'une ligne de commande
	 */
	@Override
	public void modifier() {
		System.out.println(" - Modifier une ligne de commande -");
		getService().visualiser();
		
		int id = Clavier.readInt("Quel ligne souhaitez-vous modifier?");
		
		try {
			LigneCommande lc =(LigneCommande) getService().rechercherParId(id); //lance une exception si ligne inexistante
			
			int code = Clavier.readInt("Indiquez le nouveau code : ");
			Article article = (Article) getServiceArticle().rechercherParId(code); //lance une exception si l'article entr� n'existe pas

			int qte = Clavier.readInt("Indiquez la nouvelle quantité : ");
			
			lc.setArticle(article);
			lc.setQuantite(qte);
			getService().modifier(id, lc);
		} catch (ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}		
	}

}
