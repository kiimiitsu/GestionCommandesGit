package com.afpa59.gc.services.commun;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
		super(null);
		serviceCommande = this;
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
	
	@Override
	public void setTableName() {
		this.setTableName("commande");
	}
	
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
	public void visualiser(Entite entite) {
		Commande commande = (Commande)entite;
		try {
			System.out.println("Commande n° "+commande.getId()
					+" | Client : "+commande.getClient().getNom()
					+" "+commande.getClient().getPrenom()
					+" | Montant : "+total(commande.getId()));
		} catch (ObjetInexistantException e1) {
			System.out.println(e1.getMessage());
		}
		
	}

	/**
	 * affiche la commande d'id = id
	 * @param id
	 */
	@Override
	public void visualiser(int id) throws ObjetInexistantException {
		Entite aVoir = this.rechercherParId(id);

		visualiser(aVoir);
		
		Commande commande = (Commande)aVoir;
		List<LigneCommande> listeLC = commande.getLignesCommande();
		
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
		List<LigneCommande> lignes =commande.getLignesCommande();

		for(LigneCommande lc: lignes){
			total+=(lc.getArticle().getPrix()*lc.getQte());
		}
		
		return total;
	}

	/**
	 * retourne les champs correspondant à l'entité
	 */
	@Override
	public LinkedHashMap<String, String> getFields(Entite entite) {
		Commande commande = (Commande)entite;
		ServiceLigneCommande sLC = new ServiceLigneCommande(commande);
		try {
			sLC.sauvegardeEntites(!isFirstRecord());
			this.setFirstRecord(false);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();

		fields.put("id", commande.getId()+"");
		fields.put("client_id", commande.getClient().getId()+"");
		
		return fields;
	}

	/**
	 * retourne l'entité correspondante au StringTokenizer
	 */
	@Override
	public Entite lireEntite(Object source) {
		Commande commande = new Commande();
		int id = 0;
		Client client = null;
		
		
		switch (getServiceType()) {
			case FICHIER:
				StringTokenizer st = (StringTokenizer) source;
				id = Integer.parseInt(st.nextToken());
	
				try {
					client = (Client) getServiceClient().rechercherParId(Integer.parseInt(st.nextToken()));
					
				} catch (ObjetInexistantException e) {
					System.out.println(e.getMessage());
				}
				
				break;
			case JDBC: //valable pour les 2 cas
			case JDBC_BASE:
				ResultSet rs = (ResultSet) source;
				try {
					id = rs.getInt("id");
					int idClient = rs.getInt("client_id");
					client = (Client) getServiceClient().rechercherParId(idClient);

				} catch (SQLException |ObjetInexistantException e) {
					System.out.println(e.getMessage());
				}
				break;
	
			case JPA:
		
				break;
	
			default:
				break;
		}
		commande.setId(id);
		commande.setClient(client);
		
		
		//On récupère les lignes de commandes dans le fichier
		ServiceLigneCommande sLC = new ServiceLigneCommande(commande);
		
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
