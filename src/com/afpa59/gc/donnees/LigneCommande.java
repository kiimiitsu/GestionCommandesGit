package com.afpa59.gc.donnees;

public class LigneCommande extends Entite{

	private Commande commande;
	private Article article;
	private int qte;
	
	/*------------------------------------------* CONSTRUCTEURS ------------------------------------------*/
	/**
	 * constructeur par defaut
	 */
	public LigneCommande(){}
	
	/**
	 * constructeur  avec paramètres
	 * @param article
	 * @param qte
	 */
	public LigneCommande(int id, Article article, int qte){
		super(id);
		this.article = article;
		this.qte = qte;
	}
	
	/**
	 * constructeur par copie
	 * @param lc
	 */
	public LigneCommande(LigneCommande lc){
		super(lc);
		this.commande = lc.commande;
		this.article = lc.article;
		this.qte = lc.qte;
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
	public int getQte() {
		return qte;
	}
	
	/**
	 * @return la commande atachée à la ligne
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
	 * @param qte
	 */
	public void setQte(int qte) {
		this.qte = qte;
	}
	
	/**
	 * @param commande
	 */
	public void setCommande(Commande commande){
		this.commande = commande;
	}
	
}
