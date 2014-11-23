package com.afpa59.gc.iu.swing;

import javax.swing.JPanel;

public interface IUEntite {

	public JPanel getTitlePanel();
	public JPanel getMainPanel();
	
	public void creer();
	public void visualiser();
	public void lister();
	public void modifier();
	public void supprimer();
}
