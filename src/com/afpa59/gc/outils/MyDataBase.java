package com.afpa59.gc.outils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.server.Server;

public class MyDataBase {
	
	private static Server hsql = null;
	private static Connection connexion = null;
	
	
	private MyDataBase(String base){
		hsql = new Server();
		hsql.setLogWriter(null);
		hsql.setSilent(true);
		hsql.setDatabaseName(0, base);
		hsql.setDatabasePath(0, "datas/"+base);
		hsql.start();
		
		
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
	
	public static void closeServer() throws SQLException{
		if(hsql!=null)
			hsql.stop();
	}
}
