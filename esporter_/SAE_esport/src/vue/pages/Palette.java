package vue.pages;

import java.awt.Color;

/**
 * Enumération des couleurs utilisées dans l'application
 * @author Nail Lamarti
 *
 */
public enum Palette {
	BACKGROUND(36, 47, 54), FOREGROUND(38, 60, 51), OBJECT(50, 151, 27), FLUO(63, 255, 0), DEL_BACKGROUND(156, 39, 41), DEL_FOREGROUND(79, 19, 20), DEL_CLICK(2, 0, 0);

	private int rouge;
	private int vert;
	private int bleu;
	
	/**
	 * Constructeur des éléments de l'énumération
	 * @param r Le taux de rouge
	 * @param g Le taux de vert
	 * @param b Le taux de Bleu
	 */
	private Palette(int r, int g, int b) {
		rouge = r;
		vert = g;
		bleu = b;
	}
	
	/**
	 * Fonction pour récupérer une couleur
	 * @return La couleur correspondante
	 */
	public Color getColor() {
		return new Color(rouge, vert, bleu);
	}
}
