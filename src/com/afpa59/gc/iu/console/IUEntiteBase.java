package com.afpa59.gc.iu.console;

import java.io.IOException;
import java.util.Scanner;

import javax.persistence.PersistenceException;

import com.afpa59.gc.services.commun.ObjetInexistantException;
import com.afpa59.gc.services.commun.ServiceEntite;

public abstract class IUEntiteBase implements IUEntite {
	
	private ServiceEntite service;
	private Scanner scanner;
	
	/************************************* CONTRUCTEURS ***********************************/
	/**
	 * contructeur par d�faut
	 */
	public IUEntiteBase(){
		
	}
	
	/**
	 * constructeur avec param�tres
	 * @param se
	 * @param scanner
	 */
	public IUEntiteBase(ServiceEntite se, Scanner scanner){
		this.service = se;
		this.scanner = scanner;
	}
	
	/************************************* GETTER *****************************************/
	/**
	 * recupere le ServiceEntite
	 * @return
	 */
	public ServiceEntite getService() {
		return service;
	}
	
	/**
	 * recupere le scanner
	 * @return
	 */
	public Scanner getScanner() {
			return scanner;
	}
	
	/************************************* SETTER *****************************************/
	/**
	 * definit le ServiceEntite
	 * @param se
	 */
	public void setService(ServiceEntite se) {
		this.service = se;
	}

	/**
	 * definit le scanner
	 * @param scanner
	 */
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	/*********************************** METHODES ******************************************/
	/**
	 * affiche le menu de gestion
	 * @throws IOException 
	 */
	@Override
	public void afficheMenu() throws IOException{
		int choix = 0;
		do{
			afficheHeader();
			
			choix = this.scanner.nextInt();
			scanner.nextLine();//vidage
			switch (choix) {
				case 1:
					creer();
					break;
				case 2:
					visualiser();
					break;
				case 3:
					service.visualiser();
					break;
				case 4:
					modifier();
					break;
				case 5:
					supprimer();
					break;
				case 0:
					System.out.println("Retour menu pr�c�dent");
					break;
				default:
					System.out.println("Je n'ai pas compris votre choix, veuillez r�essayer.");
					break;
			}
		}while(choix!=0);
	}
	
	/**
	 * affiche le header de chaque menu
	 */
	@Override
	public void afficheHeader() {
		System.out.println("1 - Créer"
				+ "\n2 - Visualiser"
				+ "\n3 - Visualiser tout"
				+ "\n4 - Modifier"
				+ "\n5 - Supprimer"
				+ "\n0 - Retour");
		
	}

	/**
	 * menu de demande de visualisation d'un objet
	 */
	@Override
	public void visualiser(){
		System.out.println(" - Visualisation -");
		System.out.println("Quel objet souhaitez-vous visualiser?");
		int id = scanner.nextInt();
		try {
			service.visualiser(id);
		} catch (ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * menu de suppression d'un objet
	 */
	@Override
	public void supprimer(){
		System.out.println(" - Supprimer -");
		service.visualiser();
		System.out.println("Quel objet souhaitez-vous supprimer?");
		int id = scanner.nextInt();
		try {
			service.supprimer(id);
		} catch (ObjetInexistantException | PersistenceException e) {
			System.out.println(e.getMessage());
		}
	}
}
