package com.afpa59.gc.services.fichier;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Entite;


public class ServiceClient extends ServiceEntiteBase{

	
	private static ServiceClient serviceClient=null;
	
	/**************************************** CONTRUCTEURS *********************************************/
	/**
	 * contructeur par défaut
	 */
	private ServiceClient(){
		setFile(new File("clients.txt"));
		serviceClient = this;
		charger();
	}
	
	/*********************************** METHODES DE CLASSE *******************************/
	/**
	 * @return l'instance de la classe
	 */
	public static ServiceClient getInstance(){
		if(serviceClient==null){
			new ServiceClient();
		}
		return serviceClient;
	}
	
	/*********************************** METHODES ******************************************/
	/**
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException{
		Client client = new Client((Client) entite);
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
				+" \nPrénom = "+((Client) aVoir).getPrenom()
				+" \nAdresse = "+((Client) aVoir).getAdresse()
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
	 * affiche tous les clients
	 */
	@Override
	public void visualiser(){
		ListIterator<Entite> iterator = getEntites().listIterator();
		while(iterator.hasNext()){
			int i = iterator.nextIndex();
			Client client = (Client) iterator.next();
			System.out.println((i+1)+": Id = "+client.getId()
					+" Nom = "+client.getNom()
					+" Prénom = "+client.getPrenom()
					+" Adresse = "+client.getAdresse()
			);
		}
	}
	
	/**
	 * fonction de recherche par nom
	 * @param nom
	 * @return
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
	 * retourne la chaine correspondant à l'entité
	 */
	@Override
	public String getEnregistrement(Entite entite){
		Client client = (Client)entite;
		return client.getId()+";"+client.getNom()+";"+client.getPrenom()+";"+client.getAdresse();
	}

	/**
	 * retourne l'entité correspondante au StringTokenizer
	 */
	@Override
	public Entite lireEntite(StringTokenizer st) {
		Client client = new Client();
		
		client.setId(Integer.parseInt(st.nextToken()));
		client.setNom(st.nextToken());
		client.setPrenom(st.nextToken());
		client.setAdresse(st.nextToken());
		
		return client;
	}

}
