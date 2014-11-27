package com.afpa59.gc.services.fichier;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Commande;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.donnees.LigneCommande;

public class ServiceCommande extends ServiceEntiteBase{
	
	private static ServiceCommande serviceCommande = null;

	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * contructeur par défaut
	 */
	private ServiceCommande(){
		setFile(new File("commandes.txt"));
		serviceCommande = this;
		charger();
	}
	
	/*********************************** METHODES DE CLASSE *******************************/
	/**
	 * @return l'instance de la classe
	 */
	public static ServiceCommande getInstance(){
		if(serviceCommande==null)
			new ServiceCommande();
		return serviceCommande;
	}
	
	/*********************************** METHODES ******************************************/
	/**
	 * @return l'instance de ServiceClient
	 */
	public ServiceClient getServiceClient(){
		return ServiceClient.getInstance();
	}
	
	/**
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException{
		Commande commande = new Commande((Commande) entite);
		super.creer(commande);
	}
	
	/**
	 * affiche toutes les commandes
	 */
	@Override
	public void visualiser() {
		for(Entite e : getEntites()){
			Commande commande = (Commande)e;
			try {
				System.out.println("Commande n° "+commande.getId()
						+" | Client : "+commande.getClient().getNom()
						+" "+commande.getClient().getPrenom()
						+" | Montant : "+total(commande.getId()));
			} catch (ObjetInexistantException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}

	/**
	 * affiche la commande d'id = id
	 * @param id
	 */
	@Override
	public void visualiser(int id) throws ObjetInexistantException {
		Entite aVoir = this.rechercherParId(id);

		Commande commande = (Commande)aVoir;
		List<LigneCommande> listeLC = commande.getLignesCommande();
		System.out.println("Commande n° "+commande.getId()
				+"\nClient : "
				+"\nid = "+commande.getClient().getId()
				+" nom = "+commande.getClient().getNom()
				+" prenom = "+commande.getClient().getPrenom()
				+" adresse = "+commande.getClient().getAdresse()
				+"\nContenu : "
		);
		
		if(listeLC==null){
			System.out.println("Il n'y a pas de ligne de commande actuellement.");
		}else{
			for(LigneCommande lc:listeLC){
				System.out.println("id = "+lc.getId()
						+ " article = "+lc.getArticle().getId()
						+ ". "+lc.getArticle().getLibelle()
						+" qté : "+lc.getQte()
				);
			}
		}
		
	}
	
	/**
	 * retourne le total de la commande
	 * @param id
	 * @return
	 * @throws ObjetInexistantException
	 */
	public float total(int id) throws ObjetInexistantException{
		float total = 0;
		
		Commande commande = (Commande) this.rechercherParId(id);
		for(LigneCommande lc: commande.getLignesCommande()){
			total+=(lc.getArticle().getPrix()*lc.getQte());
		}
		return total;
	}

	/**
	 * retourne la chaine correspondant à l'entité
	 */
	@Override
	public String getEnregistrement(Entite entite) {
		Commande commande = (Commande)entite;
		ServiceLigneCommande sLC = new ServiceLigneCommande(commande);
		try {
			sLC.sauvegardeEntites(!isFirstRecord());
			this.setFirstRecord(false);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return commande.getId()+";"+commande.getClient().getId();
	}

	/**
	 * retourne l'entité correspondante au StringTokenizer
	 */
	@Override
	public Entite lireEntite(StringTokenizer st) {
		Commande commande = new Commande();
		commande.setId(Integer.parseInt(st.nextToken()));

		try {
			Entite entite = getServiceClient().rechercherParId(Integer.parseInt(st.nextToken()));
			commande.setClient((Client)entite);
		} catch (ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}

		//On récupère les lignes de commandes dans le fichier
		ServiceLigneCommande sLC = new ServiceLigneCommande(commande);
		sLC.charger();
		
		//ajoute les lignes à la commande
		for(Entite ligne:sLC.getEntites()){
			commande.getLignesCommande().add((LigneCommande) ligne);
		}
		
		return commande;
	}
	
	/**
	 * fonction de recherche par client
	 * @param nom
	 * @return
	 * @throws ObjetInexistantException
	 */
	public List<Entite> rechercherParClient(String nom) throws ObjetInexistantException{
		return chercherEntite(new Critere(){
			public boolean critere(Entite e){
				return ((Commande)e).getClient().getNom().equalsIgnoreCase(nom);
			}
		});
	}
}
