package com.afpa59.gc.services.fichier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.commun.Critere;
import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceEntite;

public class ServiceEntiteFichier implements ServiceEntite{
	
	
	private ServiceEntite serviceDemandeur;
	
	private List<Entite> entites;
	private int compteur;
	private File file;
	private boolean firstRecord = true; // indique si le fichier doit ou non �tre effac� avant d'�tre modifi�
	
	/******************************* CONSTRUCTEUR ***************************************/
	/**
	 * constructeur par defaut
	 */
	public ServiceEntiteFichier() {
		this.entites = new ArrayList<Entite>();
		this.compteur = 1;
	}
	
	/**
	 * constructeur
	 * @param serviceDemandeur
	 */
	public ServiceEntiteFichier(ServiceEntite serviceDemandeur){
		this();
		this.serviceDemandeur = serviceDemandeur;
		configFile();
	}
	
	
	public static ServiceEntite getInstance(ServiceEntite serviceDemandeur){
		
		return serviceDemandeur;
	}
	/********************************** GETTER ************************************/
	/**
	 * @return les entites du service
	 */
	public List<Entite> getEntites() {
		return entites;
	}

	/**
	 * @return le compteur id
	 */
	public int getCompteur() {
		return compteur;
	}

	/**
	 * @return file
	 */
	public File getFile(){
		return this.file;
	}
	
	/**
	 * @return isFirstRecord
	 */
	public boolean getIsFirstRecord(){
		return this.firstRecord;
	}
	
	/************************************ SETTER ************************************/
	/** 
	 * @param entites
	 */
	public void setEntites(List<Entite> entites) {
		this.entites = entites;
	}

	/**
	 * @param compteur
	 */
	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}
	
	/**
	 * @param file
	 */
	public void setFile(File file){
		this.file = file;
	}
	 /**
	  * @param firstRecord
	  */
	public void setFirstRecord(boolean firstRecord){
		this.firstRecord = firstRecord;
	}
	/************************************ METHODES *****************************/
	/**
	 * ajoute une entit� � la liste
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException {
		this.getEntites().add(entite);
		this.compteur++;
	}
	
	/**
	 * modifie un objet par entite
	 * @param id
	 * @param entite
	 */
	@Override
	public void modifier(int id, Entite entite) throws ObjetInexistantException {
		int index = this.entites.indexOf(this.rechercherParId(id));
		this.entites.remove(index);
		this.entites.add(index, entite);
	}

	/**
	 * supprime l'objet avec l'id 'id'
	 * @param id
	 */
	@Override
	public void supprimer(int id) throws ObjetInexistantException {
		this.entites.remove(this.rechercherParId(id));
	}

	/**
	 * fonction de recherche d'entite avec n'importe quel critere
	 * @param critere
	 */
	@Override
	public List<Entite> chercherEntite(Critere c) throws ObjetInexistantException {
		List<Entite> match = new ArrayList<Entite>();
		ListIterator<Entite> iterator = this.entites.listIterator();
		
		while (iterator.hasNext()){
			Entite entite = iterator.next();
			if(c.critere(entite)){
				match.add(entite);
			}
		}
		if(match.isEmpty()){
			throw new ObjetInexistantException("L'objet auquel vous tentez d'acc�der est inexistant !");
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
		return entite; //id unique, retourne l'unique �l�ment de la recherche.
		
	}
	
	
	@Override
	public void sauvegardeEntites(boolean bSuite) throws IOException{
		if(bSuite==false){
			getFile().delete();
		}
		PrintWriter printWriter;
		printWriter = new PrintWriter(new FileWriter(getFile(), true));
		for(Entite entite:this.getEntites()){
			String entiteToString = "";
			String[] fields = serviceDemandeur.getFields(entite);
			for(int i = 0; i< fields.length;i++){
				entiteToString+=fields[i];
				if(i!=(fields.length-1)){
					entiteToString+=";";
				}
			}
			
			printWriter.println(entiteToString);
		}
		printWriter.close();

	}

	
	@Override
	public void charger(){
		if(getFile().exists()){
			
			BufferedReader br;
			try {
				
				br = new BufferedReader(new FileReader(getFile()));
				String s;
				while((s=br.readLine())!=null){
					StringTokenizer st = new StringTokenizer(s, ";");
					Entite entite = serviceDemandeur.lireEntite(st);
					if(entite!=null){
						this.getEntites().add(entite);
					}
				}
				br.close();
			} catch (IOException e) {
				System.out.println(e);
				br = null;
			}

			if(!entites.isEmpty()){ //on v�rifie qu'on ne rentre pas dans une liste vide
				this.compteur = this.entites.get(this.entites.size()-1).getId()+1;
			}
		}
	}
	
	public boolean isFirstRecord(){
		return firstRecord;
	}

	public void configFile(){
		serviceDemandeur.setTableName();
		String fileName = serviceDemandeur.getTableName()+".txt";
		this.setFile(new File(fileName));
	}
	
	/*---------------------------------------- NON IMPLEMENTE --------------------------------------------------*/
	

	@Override
	public void visualiser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visualiser(Entite entite) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visualiser(int id) throws ObjetInexistantException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getFields(Entite entite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entite lireEntite(Object source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTableName() {
		// TODO Auto-generated method stub
		
	}

}
