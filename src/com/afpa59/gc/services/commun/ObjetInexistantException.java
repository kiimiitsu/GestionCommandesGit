package com.afpa59.gc.services.commun;

@SuppressWarnings("serial")
public class ObjetInexistantException extends Exception {
	
	public ObjetInexistantException(){
	}

	public ObjetInexistantException(String s){
		super(s);
	}
}
