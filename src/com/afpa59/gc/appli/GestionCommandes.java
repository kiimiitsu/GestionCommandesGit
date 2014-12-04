package com.afpa59.gc.appli;
import java.io.IOException;
import java.util.Scanner;

import com.afpa59.gc.iu.console.IUGenerale;
import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceClient;
import com.afpa59.gc.services.commun.ServiceCommande;

public class GestionCommandes {
	
	public static void main(String[] args){

		Scanner scanner = new Scanner(System.in);
		ServiceArticle sa = ServiceArticle.getInstance();
		ServiceClient sc = ServiceClient.getInstance();
		ServiceCommande sCom = ServiceCommande.getInstance();
		//swing
		//new IUGenerale("Application de gestion des commandes", 1200, 800);
		
		try {
			IUGenerale mainMenu = new IUGenerale(sa, sc, sCom, scanner);
			mainMenu.afficheMenu();
			scanner.close();
			
			/*ServiceEntiteJDBCBase.deleteTables();*/
			/*sa.sauvegardeEntites(false);
			sc.sauvegardeEntites(false);
			sCom.sauvegardeEntites(false);*/
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	
}
