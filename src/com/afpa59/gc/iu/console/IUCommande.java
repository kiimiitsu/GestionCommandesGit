package com.afpa59.gc.iu.console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Commande;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.donnees.LigneCommande;
import com.afpa59.gc.outils.BASETYPE;
import com.afpa59.gc.outils.Clavier;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceClient;
import com.afpa59.gc.services.commun.ServiceCommande;
import com.afpa59.gc.services.commun.ServiceEntite;
import com.afpa59.gc.services.commun.ServiceEntiteBase;
import com.afpa59.gc.services.commun.ServiceLigneCommande;


public class IUCommande extends IUEntiteBase{

	
	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * constructeur par d�faut
	 */
	public IUCommande() {
		setService(ServiceCommande.getInstance());
	}
	
	/*********************************** METHODES ******************************************/
	/**
	 * @return l'instance de ServiceClient
	 */
	public ServiceClient getServiceClient(){
		return ServiceClient.getInstance();
	}
	
	/**
	 * affiche entete du menu
	 */
	@Override
	public void afficheHeader(){
		System.out.println("\n******MENU DE GESTION DES COMMANDES******");
		super.afficheHeader();
	}
	
	/**
	 * cr�ation d'une commande
	 * @throws IOException 
	 */
	@Override
	public void creer() throws IOException {
		System.out.println(" - Créer une commande -");
		System.out.println("Entrez les informations client :");
		Client client=null;
		
		do{
			//Informations client
			String exist = Clavier.readString("Le client existe-t-il? O/N");
			
			if(exist.equalsIgnoreCase("o")){
				String nom = Clavier.readString("Saisissez son nom : ");
				
				try {
					//récupère la liste des clients correspondant et l'affiche
					List<Entite> listeClient = getServiceClient().rechercherParNom(nom);
					
					for(Entite c:listeClient){
						getServiceClient().visualiser(c.getId());					
					}
					//choisir le bon client
					int idClient = Clavier.readInt("Quel est son id?");
					//et l'affecter
					client = (Client) getServiceClient().rechercherParId(idClient);
					
				} catch (ObjetInexistantException e) {
					System.out.println(e.getMessage());
				}
					
			}else if(exist.equalsIgnoreCase("n")){
				//création d'un nouveau client
				new IUClient().creer();
				client = (Client) getServiceClient().getEntites().get(getServiceClient().getEntites().size()-1);
				
			} else{
				System.out.println("Je n'ai pas compris votre choix. Veuillez r�essayer.");
			}
			
		}while(client==null);
		
		//création de la commande
		Commande commande = new Commande();
		commande.setClient(client);
		
		
		getService().creer(commande);
		
		if(((ServiceEntiteBase) getService()).getServiceType()==BASETYPE.JDBC){
			int id = ((ServiceEntiteBase) getService()).getCompteur();
			commande.setId(id);
		}
		
		//appel au service lignecommande pour remplir la commande
		ServiceLigneCommande sLC = new ServiceLigneCommande(commande);
		new IULigneCommande(sLC).creer();
		
		List<LigneCommande> lLC = new ArrayList<LigneCommande>();
		for(Entite e:sLC.getEntites()){
			lLC.add((LigneCommande)e);
		}
		commande.setLignesCommande(lLC);
	}

	/**
	 * modifie une commande
	 */
	@Override
	public void modifier() {
		System.out.println(" - Modifier une commande -");
		
		getService().visualiser();
		
		int id = Clavier.readInt("Quel commande souhaitez-vous modifier?");
		
		try { 
			Commande commande = (Commande) getService().rechercherParId(id);
			
			ServiceLigneCommande sLC = new ServiceLigneCommande(commande);
			
			new IULigneCommande(sLC).afficheMenu();
			
			List<LigneCommande> lLC = new ArrayList<LigneCommande>();
			
			for(Entite e:sLC.getEntites()){
				lLC.add((LigneCommande)e);
			}
			commande.setLignesCommande(lLC);
			getService().modifier(id, commande);
		} catch (ObjetInexistantException | IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
