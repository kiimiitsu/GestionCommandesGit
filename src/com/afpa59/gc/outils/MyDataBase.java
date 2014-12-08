package com.afpa59.gc.outils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
	
	private static Connection connexion = null;
	
	private MyDataBase(String base){
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connexion = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/"+base);
		} catch (ClassNotFoundException | SQLException e) {
		}
	}
	
	public static Connection getConnection(String base){
		if(connexion==null){
			new MyDataBase(base);
		}
		return connexion;
	}
	
	public static void closeConnection() throws SQLException{
		connexion.close();
	}
}
