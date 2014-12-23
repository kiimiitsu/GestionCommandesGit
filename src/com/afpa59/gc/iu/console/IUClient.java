package com.afpa59.gc.iu.console;

import java.io.IOException;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.outils.Clavier;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceClient;

public class IUClient extends IUEntiteBase {
	
	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * constructeur par defaut
	 */
	public IUClient(){
		setService(ServiceClient.getInstance());
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
		String nom = Clavier.readString("Entrez le nom :");
		String prenom = Clavier.readString("Entrez le prénom : ");
		String adresse = Clavier.readString("Entrez l'adresse : ");

		Client client = new Client();
		client.setNom(nom);
		client.setPrenom(prenom);
		client.setAdresse(adresse);
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
		int id = Clavier.readInt("Quel client souhaitez-vous modifier?");
		try {
			Client client = new Client((Client) getService().rechercherParId(id));
			String nom = Clavier.readString("Indiquez le nouveau nom : ");
			
			String prenom = Clavier.readString("Indiquez le nouveau prénom : ");
			
			String adresse = Clavier.readString("Indiquez la nouvelle adresse : ");
			
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setAdresse(adresse);
			
			getService().modifier(id, client);
		} catch (ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}
	}
}
