package Modele;

/**
 * L'énumération {@code PlacesTournoi} représente les différentes places dans un tournoi.
 */
public enum PlacesTournoi {
	
	PREMIER(200),
	DEUXIEME(150),
	TROISIEME(30),
	QUATRIEME(15);
	
	/**
	 * Nombre de points gagnés
	 */
	private int points;
	
	/**
	 * Constructeur de l'énumération
	 * @param pts Le nombre de points associé à la position dans le classement du tournoi.
	 */
	private PlacesTournoi(int pts) {
		points = pts;
	}
	
	/**
	 * Getter du nombre de points
	 * @return Le nombre de points correspondant à la position dans le classement
	 */
	public int getPoints() {
		return points;
	}
	
	
}
