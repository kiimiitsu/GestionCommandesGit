package com.afpa59.gc.appli;
import java.io.IOException;
import java.util.Scanner;

import com.afpa59.gc.iu.console.IUGenerale;
import com.afpa59.gc.outils.BASETYPE;
import com.afpa59.gc.services.commun.ServiceArticle;
import com.afpa59.gc.services.commun.ServiceClient;
import com.afpa59.gc.services.commun.ServiceCommande;
import com.afpa59.gc.services.jdbcBase.ServiceEntiteJDBCBase;

public class GestionCommandes {
	
	public static void main(String[] args){

		ServiceArticle sa = ServiceArticle.getInstance();
		ServiceClient sc = ServiceClient.getInstance();
		ServiceCommande sCom = ServiceCommande.getInstance();
		//swing
		//new IUGenerale("Application de gestion des commandes", 1200, 800);
		
		try {
			IUGenerale mainMenu = new IUGenerale(sa, sc, sCom);
			mainMenu.afficheMenu();

			sa.finaliser(true);
			sc.finaliser(false);
			sCom.finaliser(false);
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
}
