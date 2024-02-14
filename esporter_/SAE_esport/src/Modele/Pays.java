package Modele;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import DAO.DAOPays;

/**
 * La classe {@code Pays} représente un pays
 * @author Nail Lamarti
 */
public class Pays {
	
	/**
	 * Identifiant du pays
	 */
    int id;
    
    /**
     * Nom du pays
     */
    String nom;
    
    /**
     * Lien du drapeau du pays
     */
    String drapeau;

    /**
     * DAO du pays
     */
    private static DAOPays dao = DAOPays.getDAO();

    /**
     * Constructeur de la classe {@code Pays}
     * @param nom Le nom du pays
     * @param drapeau Le lien du drapeau du pays
     */
    public Pays(String nom, String drapeau) {
        try {
            this.setNom(nom);
            this.setDrapeau(drapeau);
			dao.add(this);
            this.setId();
		} catch (SQLException e) {
			System.err.println("Failed DAOPays creation/adding in Pays constructor");
		}
    }

    /**
     * Constructeur de la classe {@code Pays}
     * @param id Identifiant du pays
     * @param nom Nom du pays
     * @param drapeau Lien du drapeau du pays
     */
    public Pays(int id, String nom, String drapeau) {
    	this.id = id;
    	this.nom = nom;
    	this.drapeau = drapeau;
    }

    /**
     * Getter de l'identifiant du pays
     * @return L'identifiant du pays
     */
    public int getId() {
        return id;
    }

    /**
     * Getter du nom du pays
     * @return Le nom du pays
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter du lien du drapeau du pays
     * @return Le lien du drapeau du pays
     */
    public String getDrapeau() {
        return drapeau;
    }

    /**
     * Setter de l'identifiant du pays
     * @throws SQLException
     */
    private void setId() throws SQLException {
    	this.id = dao.getLastId();
    }

    /**
     * Setter du nom du pays
     * @param nom Le nom du pays
     */
    public void setNom(String nom) {
    	this.nom = nom;
    }

    /**
     * Setter du lien du drapeau du pays
     * @param drapeau Le lien du drapeau du pays
     */
    public void setDrapeau(String drapeau) {
    	this.drapeau = drapeau;
    }

	/**
	 * Methode d'affichage d'un pays
	 * @return Le {@code String} correspondant au pays
	 */
    @Override
    public String toString() {
        return nom;
    }

    /**
     * Methode de comparaison de deux pays
     * @param o Le pays à comparer
     * @return {@code true} si les pays sont identiques, {@code false} sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pays pays = (Pays) o;
        return Objects.equals(nom, pays.nom) && Objects.equals(drapeau, pays.drapeau);
    }

    /**
     * Methode de hachage d'un pays
     * @return Le code de hachage du pays
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, drapeau);
    }

    /**
     * Methode d'ajout d'un pays
     * @param pays Le pays à ajouter
     * @return {@code true} si le pays a été ajouté, {@code false} sinon
     */
	public static boolean update(Pays pays) {
		try {
			return dao.update(pays);
		} catch (SQLException e) {
			System.err.println("Failed to update Pays in update " + Pays.class);
			return false;
		}
	}

    /**
     * Methode de suppression d'un pays
     * @param id L'identifiant du pays à supprimer
     * @return {@code true} si le pays a été supprimé, {@code false} sinon
     */
	public static boolean delete(int id) {
		try {
			return dao.delete(id);
		} catch (SQLException e) {
			System.err.println("Failed to delete Pays in delete " + Pays.class);
			return false;
		}
	}

    /**
     * Methode de récupération d'un pays
     * @param id L'identifiant du pays à récupérer
     * @return Le pays récupéré
     */
	public static Pays getById(int id) {
		try {
			return dao.getById(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Pays in getById " + Pays.class);
			return null;
		}
	}

    /**
     * Methode de récupération de tous les pays
     * @return La liste des pays récupérés
     */
	public static List<Pays> getAll() {
		try {
			return dao.getAll();
		} catch (SQLException e) {
			System.err.println("Failed to get Pays in getAll " + Pays.class);
			return null;
		}
	}
}
