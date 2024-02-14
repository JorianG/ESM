package Modele;

/**
 * L'énumération {@code Resultat} qui définit l'issue d'un match.
 * <p>
 * Contient aussi le {@code int} de points gagnés selon l'issue.
 * @author Nail Lamarti
 */
public enum Resultat {

	PAS_FINI(-1, 0),
	VICTOIRE(1, 3),
	DEFAITE(0, 1);
	
	/**
	 * Valeur de l'issue du matchS
	 */
	private int valeur;
	
	/**
	 * Nombre de points gagnés
	 */
	private int points;
	
	/**
	 * Constructeur de l'énumération
	 * 
	 * @param valeur La valeur de l'issue du match.
	 * @param points Le nombre de points associé à l'issue du match.
	 */
	private Resultat(int valeur, int points) {
		this.valeur = valeur;
		this.points = points;
	}
	
	/**
	 * Getter de la valeur de l'issue
	 * 
	 * @return La valeur correspondant à l'issue
	 */
	public int getValeur() {
		return this.valeur;
	}
	
	/**
	 * Getter du {@code Resultat} correspondant à la valeur donnée
	 * 
	 * @return Le {@code Resultat} correspondant à la valeur
	 */
	public static Resultat get(int valeur) {
		if (valeur == 1) {
			return VICTOIRE;
		} else if (valeur == 0) {
			return DEFAITE;
		} else if (valeur == -1) {
			return PAS_FINI;
		} else {
			return null;
		}
	}
	
	/**
	 * Getter du nombre de points
	 * 
	 * @return Le nombre de points correspondant à l'issue
	 */
	public int getPoints() {
		return this.points;
	}
	
	
	/**
	 * Getter du nombre de points en fonction de la valeur donnée
	 * 
	 * @return Le nombre de points correspondant à l'issue
	 */
	public int getPtsValeur(int valeur) {
		if (valeur == 1) {
			return VICTOIRE.getPoints();
		} else if (valeur == 0) {
			return DEFAITE.getPoints();
		} else {
			return 0;
		}
	}

	/**
	 * Methode d'affichage d'un résultat
	 * @return Le {@code String} correspondant au résultat
	 */
	@Override
	public String toString() {
		if (this.valeur == 1) {
			return "Victoire";
		} else if (this.valeur == 0) {
			return "Défaite";
		} else {
			return "Pas fini";
		}
	}
}
