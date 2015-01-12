package com.afpa59.gc.iu.console;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import com.afpa59.gc.outils.Clavier;

public class IUConfig {

	public void configServiceType(){
		int choix;
		String serviceType;
		
		Properties prop = new Properties();
		OutputStream output = null;
	 
		System.out.println("--------------- Choix de la base : -------------------");
		System.out.println("1 - Fichier"
				+ "\n2 - JDBC Tableau"
				+ "\n3 - JDBC Enregistrement"
				+ "\n4 - JPA");
		choix = Clavier.readInt("");
		switch (choix) {
			case 1:
				serviceType = "FICHIER";
				break;
			case 2:
				serviceType = "JDBC_BASE";
				break;
			case 3:
				serviceType = "JDBC";
				break;
			case 4:
				serviceType = "JPA";
				break;
			default:
				serviceType = "JPA";
				break;
		}
		
		try {
			 
			output = new FileOutputStream("datas/config.properties");
	 
			// set the properties value
			prop.setProperty("basetype", serviceType);
	 
			// save properties to project root folder
			prop.store(output, null);
	 
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
	}
	
	
}
