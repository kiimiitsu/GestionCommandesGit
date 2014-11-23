package com.afpa59.gc.donnees;


public class Client extends Entite{

	private String nom;
	private String prenom;
	private String adresse;
	
	/******************************** CONSTRUCTEURS *****************************/
	/**
	 * constructeur par défaut
	 */
	public Client(){
		
	}
	
	/**
	 * constructeur avec parametres
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param adresse
	 */
	public Client(int id, String nom, String prenom, String adresse){
		super(id);
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
	}
	
	/**
	 * constructeur par copie
	 * @param client
	 */
	public Client(Client client){
		super(client);
		this.nom = client.nom;
		this.prenom = client.prenom;
		this.adresse = client.adresse;
	}
	
	/********************************* GETTER *********************************/
	/**
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * @return prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @return adresse
	 */
	public String getAdresse() {
		return adresse;
	}
	
	/************************************ SETTER ********************************/
	/**
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	/**
	 * @param adresse
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}	
}
