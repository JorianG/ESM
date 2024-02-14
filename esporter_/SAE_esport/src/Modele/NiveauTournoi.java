package Modele;

/**
 * L'énumération {@code NiveauTournoi} représente les différents niveaux de tournoi.
 * <p>
 * Contient aussi le {@code double} de points associé au niveau.
 * @author Nail Lamarti
 */
public enum NiveauTournoi {
	
	LOCAL(1.0),
	REGIONAL(1.5),
	NATIONAL(2.0),
	INTERNATIONAL(2.25),
	INTERNATIONAL_CLASSE(3.0);
	
	/**
	 * Multiplicateur de points
	 */
	private double multiplicateur;
	
	/**
	 * Constructeur de l'énumération
	 * @param mult Le multiplicateur de points associé au niveau du tournoi
	 */
	private NiveauTournoi(double mult) {
		multiplicateur = mult;
	}
	
	/**
	 * Getter du multiplicateur de points
	 * @return Le multiplicateur correspondant à l'élément
	 */
	public double getMultiplicateur() {
		return multiplicateur;
	}
}
