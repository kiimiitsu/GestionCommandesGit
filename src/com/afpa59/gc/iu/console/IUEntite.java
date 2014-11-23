package com.afpa59.gc.iu.console;

import java.io.IOException;

public interface IUEntite {

	public void afficheMenu() throws IOException;
	public void afficheHeader();
	public void creer() throws IOException;
	public void visualiser();
	public void modifier();
	public void supprimer();
	
}
