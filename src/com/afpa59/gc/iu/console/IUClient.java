package com.afpa59.gc.iu.console;

import java.io.IOException;
import java.util.Scanner;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.services.fichier.ObjetInexistantException;
import com.afpa59.gc.services.fichier.ServiceEntite;
import com.afpa59.gc.services.fichier.ServiceEntiteBase;

public class IUClient extends IUEntiteBase {
	
	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * constructeur par defaut
	 */
	public IUClient(){
		
	}
	
	/**
	 * constructeur avec parrametres
	 * @param se
	 * @param scanner
	 */
	public IUClient(ServiceEntite se, Scanner scanner){
		super(se,scanner);
	}
	
	/*********************************** METHODES ******************************************/
	/**
	 * affiche entete du menu
	 */
	@Override
	public void afficheHeader(){
		System.out.println("\n******MENU DE GESTION DES CLIENTS******");
		super.afficheHeader();
	}
	
	/**
	 * creer articles
	 * @throws IOException 
	 */
	@Override
	public void creer() throws IOException{
		System.out.println(" - Créer un client -");
		System.out.println("Entrez le nom :");
		String nom = getScanner().nextLine();
		System.out.println("Entrez le prénom : ");
		String prenom = getScanner().nextLine();
		System.out.println("Entrez l'adresse : ");
		String adresse = getScanner().nextLine();
		int id = ((ServiceEntiteBase) getService()).getCompteur();
		Client client = new Client(id, nom, prenom, adresse);
		getService().creer(client);
	}
	
	/**
	 * modifier un article
	 */
	@Override
	public void modifier(){
		System.out.println(" - Modifier un client -");
		getService().visualiser();
		System.out.println("Quel client souhaitez-vous modifier?");
		int id = getScanner().nextInt();
		try {
			Client client = new Client((Client) getService().rechercherParId(id));
			System.out.println("Indiquez le nouveau nom : ");
			getScanner().nextLine();//vidage de la ligne
			String nom = getScanner().nextLine();
			System.out.println("Indiquez le nouveau prénom : ");
			String prenom = getScanner().nextLine();
			System.out.println("Indiquez la nouvelle adresse : ");
			String adresse = getScanner().nextLine();
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setAdresse(adresse);
			
			getService().modifier(id, client);
		} catch (ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}
	}
}
