package Modele;

/**
 * L'énumération {@code TypeMatch} représente les différents types de match possibles dans un tournoi.
 * @author Nail Lamarti
 */
public enum TypeMatch {

	// 25 points pour une victoire, 15 points pour une défaite, multiplié par 10 pour les matchs de poule
	// pour le calcul des points obtenus par une équipe dans un tournoi
	POOL(250, 150),
	PETITE_FINALE(30, 15),
	FINALE(200, 100);
	
	private int pointsGagnant;
	private int pointsPerdant;

	/**
	 * Constructeur de l'énumération {@code TypeMatch}
	 * @param pointsGagnant nombre de points gagnés par le gagnant
	 * @param pointsPerdant nombre de points gagnés par le perdant
	 */
	private TypeMatch(int pointsGagnant, int pointsPerdant) {
		this.pointsGagnant = pointsGagnant;
		this.pointsPerdant = pointsPerdant;
	}

	/**
	 * Getter du nombre de points gagnés par le gagnant
	 * @return le nombre de points gagnés par le gagnant
	 */
	public int getPointsGagnant() {
		return this.pointsGagnant;
	}

	/**
	 * Getter du nombre de points gagnés par le perdant
	 * @return le nombre de points gagnés par le perdant
	 */
	public int getPointsPerdant() {
		return this.pointsPerdant;
	}

	/**
	 * Getter du nombre de points gagnés par une équipe en fonction du résultat
	 * @param resultat le résultat du match
	 * @return le nombre de points gagnés par une équipe en fonction du résultat
	 */
	public int getPointsByResultat(Resultat resultat) {
		if (resultat == Resultat.VICTOIRE) {
			return this.pointsGagnant;
		} else if (resultat == Resultat.DEFAITE) {
			return this.pointsPerdant;
		} else {
			return 0;
		}
	}
}
