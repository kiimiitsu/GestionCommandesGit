package com.afpa59.gc.iu.console;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

import com.afpa59.gc.outils.Clavier;
import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceClient;
import com.afpa59.gc.services.commun.ServiceCommande;

public class IUGenerale {

	private ServiceArticle sa;
	private ServiceClient sc;
	private ServiceCommande sCom;

	/****************************************** CONSTRUCTEURS ************************************/
	/**
	 * contructeur par défaut
	 */
	public IUGenerale(){

	}

	/**
	 * constructeur avec param�tres
	 * @param scanner
	 */
	public IUGenerale(ServiceArticle sa, ServiceClient sc, ServiceCommande sCom){
		this.sa = sa;
		this.sc = sc;
		this.sCom = sCom;
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

			choix = Clavier.readInt("");

			switch (choix) {
			case 1:
				IUArticle menuArticle = new IUArticle();
				menuArticle.afficheMenu();
				break;
			case 2:
				IUClient menuClient = new IUClient();
				menuClient.afficheMenu();
				break;
			case 3:
				IUCommande menuCommande = new IUCommande();
				menuCommande.afficheMenu();
				break;	
			case 0:
				System.out.println("****************** FERMETURE APPLICATION ************************");
				break;
			default:
				System.out.println("Je n'ai pas compris votre choix, veuillez r�essayer.");
				break;
			}


		}while(0!=choix);
	}
}
