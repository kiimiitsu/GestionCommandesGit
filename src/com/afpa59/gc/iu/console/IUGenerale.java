package com.afpa59.gc.iu.console;

import java.io.IOException;
import java.util.Scanner;

import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceClient;
import com.afpa59.gc.services.commun.ServiceCommande;

public class IUGenerale {
	
	private ServiceArticle sa;
	private ServiceClient sc;
	private ServiceCommande sCom;
	private Scanner scanner;
	
	/****************************************** CONSTRUCTEURS ************************************/
	/**
	 * contructeur par défaut
	 */
	public IUGenerale(){
		
	}
	
	/**
	 * constructeur avec paramètres
	 * @param scanner
	 */
	public IUGenerale(ServiceArticle sa, ServiceClient sc, ServiceCommande sCom, Scanner scanner){
		this.sa = sa;
		this.sc = sc;
		this.sCom = sCom;
		this.scanner = scanner;
	}
	
	/***************************************** METHODES *************************************/
	/**
	 * affiche le menu principal
	 * @throws IOException 
	 */
	public void afficheMenu() throws IOException{
		int choix;
		do{
			System.out.println("\n******MENU DE GESTION DE L'APPLICATION******");
			System.out.println("1 - Gestion des articles"
					+ "\n2 - Gestion des clients"
					+ "\n3 - Gestion des commandes"
					+ "\n0 - Sortie");
			
			choix = scanner.nextInt();
			
			switch (choix) {
			case 1:
				IUArticle menuArticle = new IUArticle(sa, scanner);
				menuArticle.afficheMenu();
				break;
			case 2:
				IUClient menuClient = new IUClient(sc, scanner);
				menuClient.afficheMenu();
				break;
			case 3:
				IUCommande menuCommande = new IUCommande(sCom, scanner);
				menuCommande.afficheMenu();
				break;	
			case 0:
				System.out.println("****************** FERMETURE APPLICATION ************************");
				this.sa.finaliser();
				this.sc.finaliser();;
				this.sCom.finaliser();
				this.scanner.close();
				break;
			default:
				System.out.println("Je n'ai pas compris votre choix, veuillez réessayer.");
				break;
			}
	
			
		}while(0!=choix);
	}
}
