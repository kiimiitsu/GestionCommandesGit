package com.afpa59.gc.donnees;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
@Entity
public class LigneCommande extends Entite{

	@ManyToOne
	private Commande commande;
	private Article article;
	private int quantite;
	
	/*------------------------------------------* CONSTRUCTEURS ------------------------------------------*/
	/**
	 * constructeur par defaut
	 */
	public LigneCommande(){}
	
	/**
	 * constructeur  avec paramètres
	 * @param article
	 * @param quantite
	 */
	public LigneCommande(int id, Article article, int quantite){
		super(id);
		this.article = article;
		this.quantite = quantite;
	}
	
	/**
	 * constructeur par copie
	 * @param lc
	 */
	public LigneCommande(LigneCommande lc){
		super(lc);
		this.commande = lc.commande;
		this.article = lc.article;
		this.quantite = lc.quantite;
	}
	
	/*------------------------------------------ GETTER ------------------------------------------*/
	/**
	 * @return article
	 */
	public Article getArticle() {
		return article;
	}
	
	/**
	 * @return qte
	 */
	public int getQuantite() {
		return quantite;
	}
	
	/**
	 * @return la commande atach�e � la ligne
	 */
	public Commande getCommande(){
		return commande;
	}
	/*------------------------------------------ SETTER ------------------------------------------*/
	/**
	 * @param article
	 */
	public void setArticle(Article article) {
		this.article = article;
	}
	
	/**
	 * @param quantite
	 */
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
	/**
	 * @param commande
	 */
	public void setCommande(Commande commande){
		this.commande = commande;
	}
	
}
