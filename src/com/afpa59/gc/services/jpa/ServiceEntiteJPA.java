package com.afpa59.gc.services.jpa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.commun.Critere;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceEntite;

public class ServiceEntiteJPA implements ServiceEntite{
	
	private String table;
	EntityManagerFactory emf;
	
	private ServiceEntite serviceDemandeur;
	
	/*----------------------------------- CONSTRUCTEUR -----------------------------------*/
	/**
	 * constructeur par defaut
	 */
	public ServiceEntiteJPA() {
		emf = Persistence.createEntityManagerFactory("gc");
		
	}
	
	/**
	 * constructeur
	 * @param serviceDemandeur
	 */
	public ServiceEntiteJPA(ServiceEntite serviceDemandeur){
		this();
		this.serviceDemandeur = serviceDemandeur;
		configTable();
	}
	
	/*----------------------------------- GETTER -----------------------------------*/
		
	public String getTable() {
		return table;
	}
	
	/*----------------------------------- SETTER -----------------------------------*/

	public void setTable(String table) {
		this.table = table;
	}
	/*----------------------------------- METHODES -----------------------------------*/
	
	public void configTable(){
		serviceDemandeur.setTableName();
		this.setTable(serviceDemandeur.getTableName());
	}
	
	@Override
	public List<Entite> getEntites() {
		List<Entite> entites = new ArrayList<Entite>();
		
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Query maRequete = em.createQuery("select a from "+table+" as a "); //JPQL, Mettre le nom de la classe!
		
		List<Entite> results = new ArrayList<Entite>(); 
		results.addAll(maRequete.getResultList());
		
		Iterator<Entite> it = results.listIterator();
		while(it.hasNext()){
			Entite entite = serviceDemandeur.lireEntite(it.next());
			if(entite!=null){
				entites.add(entite);
			}
		}
		et.commit();
		em.close();
		return entites;
	}
	
	@Override
	public int getCompteur() {
		return 0;
	}
	
	/**
	 * ajoute une entit� � la liste
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		em.persist(entite);
		em.flush();
		et.commit();
		em.close();
	}
	
	/**
	 * modifie un objet par entite
	 * @param id
	 * @param entite
	 */
	@Override
	public void modifier(int id, Entite entite) throws ObjetInexistantException {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		em.merge(entite);
		
		em.flush();
		et.commit();
		em.close();
	}

	/**
	 * supprime l'objet avec l'id 'id'
	 * @param id
	 */
	@Override
	public void supprimer(int id) throws ObjetInexistantException, PersistenceException {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Entite entite = serviceDemandeur.rechercherParId(id);
		
		Entite remove = em.find(entite.getClass(), id);

		em.remove(remove);   // lève une exception si est référencé ailleurs
		
		em.flush();
		et.commit();
		em.close();
	}

	/**
	 * fonction de recherche d'entite avec n'importe quel critere
	 * @param critere
	 */
	@Override
	public List<Entite> chercherEntite(Critere c) throws ObjetInexistantException {
		
		List<Entite> match = new ArrayList<Entite>();
		ListIterator<Entite> iterator = this.getEntites().listIterator();
		
		while (iterator.hasNext()){
			Entite entite = iterator.next();
			if(c.critere(entite)){
				match.add(entite);
			}
		}
		if(match.isEmpty()){
			throw new ObjetInexistantException("L'objet auquel vous tentez d'accéder est inexistant !");
		}
		return match;
	}
	
	/**
	 * fonction de recherche par id
	 * @param id
	 */
	@Override
	public Entite rechercherParId(int id) throws ObjetInexistantException{
		List<Entite> resultats = chercherEntite(new Critere(){
													public boolean critere(Entite e){
														return (e.getId() == id) ;
													}
												});
		Entite entite = resultats.get(0);
		return entite; //id unique, retourne l'unique élément de la recherche.
	}
	
	@Override
	public void finaliser(boolean first){
		
	}
	
	/*------------------------------------------NON IMPLEMENTE ----------------------------------------------*/

	@Override
	public void visualiser(Entite entite) {}

	@Override
	public void visualiser(int id) throws ObjetInexistantException {}

	@Override
	public String getTableName() {return null;}

	@Override
	public void setTableName() {}

	@Override
	public LinkedHashMap<String, String> getFields(Entite entite) {return null;}

	@Override
	public Entite lireEntite(Object source) {return null;}

	@Override
	public boolean isFirstRecord() {return false;}

	@Override
	public void setFirstRecord(boolean firstRecord) {}

	@Override
	public void setCompteur(int compteur) {}

	

	@Override
	public void setEntites(List<Entite> entites) {}
	
	@Override
	public void sauvegardeEntites(boolean bSuite) throws IOException{}
	
	@Override
	public void charger(){}

	@Override
	public void visualiser() {
		// TODO Auto-generated method stub
		
	}

}
