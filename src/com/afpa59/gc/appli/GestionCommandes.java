package com.afpa59.gc.appli;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JPanel;

import com.afpa59.gc.iu.swing.IUGenerale;
import com.afpa59.gc.services.fichier.ServiceArticle;
import com.afpa59.gc.services.fichier.ServiceClient;
import com.afpa59.gc.services.fichier.ServiceCommande;

public class GestionCommandes {
	
	public static void main(String[] args){

		Scanner scanner = new Scanner(System.in);
		ServiceArticle sa = ServiceArticle.getInstance();
		ServiceClient sc = ServiceClient.getInstance();
		ServiceCommande sCom = ServiceCommande.getInstance();
		
		//swing
		new IUGenerale("Application de gestion des commandes", 1200, 800);
		/*
		try {
			IUGenerale mainMenu = new IUGenerale(sa, sc, sCom, scanner);
			mainMenu.afficheMenu();
			scanner.close();
			sa.sauvegardeEntites(false);
			sc.sauvegardeEntites(false);
			sCom.sauvegardeEntites(false);
		}catch(IOException e){
			System.out.println(e.getMessage());
		}*/
	}
	
	
}
