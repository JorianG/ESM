package vue.pages;

import javax.swing.*;

/**
 * Classe RoundedButton qui permet de créer des boutons arrondis
 */
public class RoundedButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de l'objet RoundedButton
	 * @param label Label du bouton
	 */
	public RoundedButton(String label) {
		super(label);
		putClientProperty( "JButton.buttonType", "roundRect" );
		setBorderPainted(false);
		if (label.contains("Annuler") || label.contains("Quitter") || label.contains("Supprimer") || label.contains("Terminer")) {
			setBackground(Palette.DEL_BACKGROUND.getColor());
			setForeground(Palette.DEL_FOREGROUND.getColor());
		}
	}

	/**
	 * Constructeur de l'objet RoundedButton
	 */
	public RoundedButton() {
		super();
		putClientProperty( "JButton.buttonType", "roundRect" );
		setBorderPainted(false);
	}
}
