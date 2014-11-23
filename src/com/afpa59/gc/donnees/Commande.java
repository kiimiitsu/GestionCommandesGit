package com.afpa59.gc.donnees;

import java.util.ArrayList;
import java.util.List;

public class Commande extends Entite{

	private Client client;
	private List<LigneCommande> lignesCommande;
	

	/******************************* CONSTRUCTEURS *******************************/
	/**
	 * contructeur par défaut
	 */
	public Commande(){
		this.lignesCommande = new ArrayList<LigneCommande>();
	}
	
	/**
	 * constructeur avec parametres
	 * @param client
	 * @param lc
	 */
	public Commande(Client client, List<LigneCommande> lc){
		this.client = new Client(client);
		this.lignesCommande = new ArrayList<LigneCommande>(lc);
	}

	/**
	 * constructeur par copie
	 */
	public Commande(Commande commande){
		super(commande);
		this.client = commande.getClient();
		this.lignesCommande = commande.getLignesCommande();
	}
	/*************************** GETTER ************************************/
	/**
	 * @return le client
	 */
	public Client getClient() {
		return client;
	}
	
	/**
	 * @return les lignes de commande
	 */
	public List<LigneCommande> getLignesCommande() {
		return lignesCommande;
	}
	
	/**************************** SETTER ***********************************/
	/**
	 * @param client
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	
	/**
	 * @param lignesCommande
	 */
	public void setLignesCommande(List<LigneCommande> lignesCommande) {
		this.lignesCommande = lignesCommande;
	}

}
