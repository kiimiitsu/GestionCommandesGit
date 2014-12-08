package com.afpa59.gc.services.commun;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.afpa59.gc.donnees.Article;
import com.afpa59.gc.donnees.Commande;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.donnees.LigneCommande;


public class ServiceLigneCommande extends ServiceEntiteBase{


	private Commande commande = null;
	
	/*------------------------------------------ CONSTRUCTEUR ------------------------------------------*/
	/**
	 * constructeur par défaut
	 */
	public ServiceLigneCommande() {
		
	}
	
	/**
	 * constructeur avec paramètres
	 * @param commande
	 */
	public ServiceLigneCommande(Commande commande){
		super(commande);
		this.commande = commande;
		
		// Chargement des entités du service avec les lignes de la commande
		
		if(!commande.getLignesCommande().isEmpty()) {

			List<Entite> liste = new ArrayList<Entite>();

			liste.addAll(commande.getLignesCommande()); // nécessaire pour la conversion ligne de commande -> entité
			
			this.setEntites(liste);
			
			int lastId = liste.get(liste.size()-1).getId();
			this.setCompteur(lastId+1);
		}
		
	}
	
	/*------------------------------------------ METHODES ------------------------------------------*/
	/**
	 * redéfinie pour indiquer la commande
	 * @param entiteParent
	 */
	@Override 
	public void setParent(Entite entiteParent){
		this.commande = (Commande) entiteParent;
	}
	
	/**
	 * paramètre le nom de la table / fichier
	 */
	@Override
	public void setTableName() {
		this.setTableName("ligneCommande");
	}
	
	/**
	 * @return l'instance de ServiceCommande
	 */
	public ServiceCommande getServiceCommande(){
		return ServiceCommande.getInstance();
	}
	
	/**
	 * @return l'instance de ServiceArticle
	 */
	public ServiceArticle getServiceArticle(){
		return ServiceArticle.getInstance();
	}

	/**
	 * @param entite
	 * @throws IOException 
	 */
	@Override
	public void creer(Entite entite) throws IOException{
		LigneCommande ligneCommande = new LigneCommande((LigneCommande) entite);
		ligneCommande.setCommande(commande);
		super.creer(ligneCommande);
	}
	
	/**
	 * affiche toutes les lignes commandes
	 */
	@Override
	public void visualiser(Entite entite) {
		
		LigneCommande lc = (LigneCommande)entite;
		
		System.out.println("Commande n° "+lc.getCommande().getId()
				+": Id = "+lc.getId()
				+" article : "+lc.getArticle().getId()
				+". "+lc.getArticle().getLibelle()
				+" quantite : "+lc.getQte()
		);
	}

	/**
	 * @param id
	 */
	@Override
	public void visualiser(int id) throws ObjetInexistantException {
		Entite aVoir = this.rechercherParId(id);

		System.out.println("Commande n° "+((LigneCommande) aVoir).getCommande().getId()
				+ "Id = "+aVoir.getId()
				+" article : "+((LigneCommande) aVoir).getArticle().getId()
				+". "+((LigneCommande) aVoir).getArticle().getLibelle()
				+" quantite : "+((LigneCommande) aVoir).getQte()
		);
	}

	/**
	 * @param id
	 * @param entite
	 */
	@Override
	public void modifier(int id, Entite entite)throws ObjetInexistantException {
		LigneCommande lc = (LigneCommande) entite;
		super.modifier(id, lc);
	}

	/**
	 * retourne la chaine correspondant à l'entité
	 * @param entite
	 */
	@Override
	public LinkedHashMap<String, String> getFields(Entite entite){
		LigneCommande lc = (LigneCommande) entite;
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		
		fields.put("commande_id", lc.getCommande().getId()+"");
		fields.put("id", lc.getId()+"");
		fields.put("article_id", lc.getArticle().getId()+"");
		fields.put("quantite", lc.getQte()+"");
		
		return fields;
	}

	/**
	 * construit la ligne de commande en fonction d'un StringTokenizer
	 * @param stringtokenizer
	 */
	@Override
	public Entite lireEntite(Object source) {
		LigneCommande ligne = new LigneCommande();
		
		int id = 0;
		int idCommande = 0;
		Article article = null;
		int quantite = 0;
		
		switch (getServiceType()) {
		case FICHIER:
			StringTokenizer st = (StringTokenizer) source;
			idCommande = Integer.parseInt(st.nextToken());

			if(idCommande == commande.getId()){
	
				id = Integer.parseInt(st.nextToken());
				 
				try {
					article = (Article) getServiceArticle().rechercherParId(Integer.parseInt(st.nextToken()));
				} catch (ObjetInexistantException e) {
					System.out.println(e.getMessage());
				}
				
				quantite = Integer.parseInt(st.nextToken());
			} else return null;
			break;
		case JDBC: //valable pour les 2 cas
		case JDBC_BASE:
			ResultSet rs = (ResultSet) source;
			try {
				
				idCommande = rs.getInt("commande_id");
				
				if(commande.getId()==idCommande){
					id = rs.getInt("id");
					try {
						article = (Article)getServiceArticle().rechercherParId(rs.getInt("article_id"));
					} catch (ObjetInexistantException e) {
						System.out.println(e.getMessage());
					}
					
					quantite = rs.getInt("quantite");
				}else return null;
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			break;

		case JPA:
	
			break;

		default:
			break;
	}
		ligne.setCommande(commande);
		ligne.setId(id);
		ligne.setArticle(article);
		ligne.setQte(quantite);
		
		return ligne;
	}
	
	/**
	 * retourne le total de la ligne de commande concernée
	 * @param id
	 * @return
	 * @throws ObjetInexistantException
	 */
	public float sousTotal(int id) throws ObjetInexistantException{
		float tot = 0;
		LigneCommande ligne = (LigneCommande) this.rechercherParId(id);
		Article article = (Article) ServiceArticle.getInstance().rechercherParId(ligne.getArticle().getId());
		tot = (article.getPrix()*ligne.getQte());
		
		return tot;
	}

	/*public List<Entite> rechercherParCommande() throws ObjetInexistantException{
		return chercherEntite(new Critere(){
			public boolean critere(Entite e){
				return (((LigneCommande) e).getCommande().getId()==commande.getId()) ;
			}
		});
	}*/
	
}
