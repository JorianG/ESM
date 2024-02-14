package Modele;

/**
 * L'énumération {@code Poste} représente les différents postes qui constituent une équipe.
 * <p>
 * Contient aussi le {@code String} du lien vers l'image associée au poste.
 * @author Nail Lamarti
 */
public enum Poste {
	
	TOPLANE("top"),
	MIDLANE("mid"),
	JUNGLE("jungle"),
	ADC("adc"),
	SUPPORT("support");
	
	private String img;
	
	/**
	 * Constructeur de l'énumération
	 * @param lien Le lien vers l'image associée au poste
	 */
	private Poste(String lien) {
		img = lien;
	}
	
	/**
	 * Getter du lien vers l'image du poste
	 * @return Le {@code String} correspondant au lien
	 */
	public String getImage() {
		return img;
	}

	/**
	 * Setter du chemin vers l'image du poste
	 * @param img Le chemin vers l'image du poste
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * Vérifie si le poste est égal à un autre poste
	 * @param poste Le poste à comparer
	 * @return {@code true} si les postes sont égaux, {@code false} sinon
	 */
	public boolean equals(Poste poste) {
		return this.name().equals(poste.name());
	}

	/**
	 * Methode d'affichage d'un poste
	 * @return Le {@code String} correspondant au poste
	 */
	@Override
	public String toString() {
		return this.name();
	}
}
