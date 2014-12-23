package com.afpa59.gc.services.commun;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Entite;


public class ServiceClient extends ServiceEntiteBase{

	
	private static ServiceClient serviceClient=null;
	
	/*------------------------------------------ CONTRUCTEURS ------------------------------------------*/
	/**
	 * contructeur par d�faut
	 */
	private ServiceClient(){
		super(null);
		serviceClient = this;
	}
	
	/*------------------------------------------ METHODES DE CLASSE ------------------------------------------*/
	/**
	 * @return l'instance de la classe
	 */
	public synchronized static ServiceClient getInstance(){
		if(serviceClient==null){
			new ServiceClient();
		}
		return serviceClient;
	}
	
	/*------------------------------------------ METHODES ------------------------------------------*/
	/**
	 * param�tre le nom de la table / fichier
	 */
	@Override
	public void setTableName() {
		this.setTableName("Client");	
	}
	
	/**
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException{
		Client client = (Client) entite;
		super.creer(client);
	}
	
	/**
	 * @param id
	 */
	@Override
	public void visualiser(int id) throws ObjetInexistantException{
		Entite aVoir = this.rechercherParId(id);

		System.out.println("Id = "+aVoir.getId()
				+" \nNom = "+((Client)aVoir).getNom()
				+" \nPr�nom = "+((Client) aVoir).getPrenom()
				+" \nAdresse = "+((Client) aVoir).getAdresse()
		);
		
	}

	/**
	 * affiche le client en param�tre
	 * @param entite
	 */
	@Override
	public void visualiser(Entite entite){
		Client client = (Client) entite;
		System.out.println("Id = "+client.getId()
				+" Nom = "+client.getNom()
				+" Prénom = "+client.getPrenom()
				+" Adresse = "+client.getAdresse()
		);
	}
	
	/**
	 * @param id
	 * @param entite
	 */
	@Override
	public void modifier(int id, Entite entite)throws ObjetInexistantException {
		Client client = (Client) entite;
		super.modifier(id, client);
	}
	
	/**
	 * fonction de recherche par nom
	 * @param nom
	 * @return la liste des entites correspondantes
	 * @throws ObjetInexistantException
	 */
	public List<Entite> rechercherParNom(String nom) throws ObjetInexistantException{
		return chercherEntite(new Critere(){
			public boolean critere(Entite e){
				return (((Client) e).getNom().equalsIgnoreCase(nom)) ;
			}
		});
	}
	
	/**
	 * fonction de recherche par prenom
	 * @param prenom
	 * @return
	 * @throws ObjetInexistantException
	 */
	public List<Entite> rechercherParPrenom(String prenom) throws ObjetInexistantException{
		return chercherEntite(new Critere(){
			public boolean critere(Entite e){
				return (((Client) e).getPrenom().equalsIgnoreCase(prenom)) ;
			}
		});
	}

	/**
	 * retourne les champs correspondant � l'entit�
	 * @param entite
	 * @return fields
	 */
	@Override
	public LinkedHashMap<String, String> getFields(Entite entite){
		Client client = (Client)entite;
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();

		fields.put("id", client.getId()+"");
		fields.put("nom", client.getNom());
		fields.put("prenom", client.getPrenom());
		fields.put("adresse", client.getAdresse());
		
		return fields;
	}

	/**
	 * retourne l'entit� correspondante au StringTokenizer
	 * @param source
	 * @return client
	 */
	@Override
	public Entite lireEntite(Object source) {
		Client client = new Client();
		int id = 0;
		String nom = null;
		String prenom = null;
		String adresse = null;
		
		switch (getServiceType()) {
			case FICHIER:
				StringTokenizer st = (StringTokenizer) source;
				id = Integer.parseInt(st.nextToken());
				nom = st.nextToken();
				prenom = st.nextToken();
				adresse = st.nextToken();
				break;
			case JDBC: //valable pour les 2 cas
			case JDBC_BASE:
				ResultSet rs = (ResultSet) source;
				try {
					id = rs.getInt("id");
					nom = rs.getString("nom");
					prenom = rs.getString("prenom");
					adresse = rs.getString("adresse");
										
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
	
			case JPA:
					return (Entite) source;
	
			default:
				break;
		}
		client = new Client(id, nom, prenom, adresse);
		return client;
	}

}
