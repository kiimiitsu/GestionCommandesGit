package com.afpa59.gc.outils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
	
	private static Connection connexion = null;
	
	private MyDataBase(){
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connexion = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/gestioncommandes");
		} catch (ClassNotFoundException | SQLException e) {
		}
	}
	
	public static Connection getConnection(){
		if(connexion==null){
			new MyDataBase();
		}
		return connexion;
	}
	
	public static void closeConnection() throws SQLException{
		connexion.close();
	}
}
