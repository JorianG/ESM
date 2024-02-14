package Modele;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import DAO.DAOArbitre;
import vue.Observable;

/**
 * La classe {@code Arbitre} représente une instance d'un arbitre.
 * @author Nail Lamarti
 */
public class Arbitre extends Observable {

	/**
	 * Liste des clés des observateurs requis pour la classe Arbitre
	 */
	private static final String[] OBSERVERSKEYS = {"Saison", "Arbitre", "Tournoi", "Match"};

	/**
	 * Identifiant de l'arbitre
	 */
	private int id;
	
	/**
	 * Nom de l'arbitre
	 */
	private String nom;
	
	/**
	 * Prenom de l'arbitre
	 */
	private String prenom;
	
	/**
	 * Lien vers l'image de l'arbitre
	 */
	private String img;

	/**
	 * DAO de l'arbitre
	 */
	private static DAOArbitre dao = DAOArbitre.getDAO();
	
	/**
	 * Constructeur de la classe Arbitre avec ajout dans la base de données
	 * @param nom Le nom de l'arbitre
	 * @param prenom Le prénom de l'arbitre
	 * @param img Le lien vers la photo de l'arbitre
	 */
	public Arbitre(String nom, String prenom, String img) {
		super();
		Observable.attach(OBSERVERSKEYS);
		try {
			this.setNom(nom);
			this.setPrenom(prenom);
			this.setImage(img);
			boolean b = dao.add(this);
			if (b) {
				Observable.notifyStaticObservers();
				this.setId();
			}
		} catch (SQLException e) {
			System.err.println("Failed DAOArbitre creation/adding in Arbitre constructor");
		}
	}

	/**
	 * Constructeur de la classe Arbitre sans ajout dans la base de données
	 * @param id L'identifiant de l'arbitre
	 * @param nom Le nom de l'arbitre
	 * @param prenom Le prénom de l'arbitre
	 * @param img Le lien vers la photo de l'arbitre
	 */
	public Arbitre(int id, String nom, String prenom, String img) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.img = img;
	}
	
	/**
	 * Getter sur l'identifiant de l'arbitre
	 * @return L'identifiant de l'arbitre
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter sur le nom de l'arbitre
	 * @return Le nom de l'arbitre
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Getter sur le prenom de l'arbitre
	 * @return Le prenom de l'arbitre
	 */
	public String getPrenom() {
		return prenom;
	}
	
	/**
	 * Getter sur l'image de l'arbitre
	 * @return Le path de la photo de l'arbitre
	 */
	public String getImage() {
		return img;
	}
	
	/**
	 * Setter de l'identifiant de l'arbitre
	 * @throws SQLException Exception SQL si l'identifiant n'est pas trouvé
	 * @see DAOArbitre#getLastId()
	 */
	private void setId() throws SQLException {
		this.id = dao.getLastId();
	}
	
	/**
	 * Setter du nom de l'arbitre
	 * @param nom Le nom de l'arbitre
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Setter du prénom de l'arbitre
	 * @param prenom Le prénom de l'arbitre
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	/**
	 * Setter de l'image de l'arbitre
	 * @param img Le lien vers la photo de l'arbitre
	 */
	public void setImage(String img) {
		this.img = img;
	}

	/**
	 * Methode d'affichage d'un arbitre
	 * @return Le {@code String} correspondant à l'arbitre
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + id + ") " + nom + " " + prenom;
	}

	/**
	 * Hashcode d'un arbitre
	 * @return Le hashcode de l'arbitre
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	/**
	 * Methode d'egalité entre deux arbitres
	 * @param o L'objet à comparer avec l'arbitre
	 * @return {@code true} si les deux arbitres sont égaux, {@code false} sinon
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Arbitre arbitre = (Arbitre) o;
		return Objects.equals(nom, arbitre.nom) && Objects.equals(prenom, arbitre.prenom) && Objects.equals(img, arbitre.img);
	}

	/**
	 * Methode de mise à jour d'un arbitre
	 * @param value L'arbitre à mettre à jour
	 * @return {@code true} si l'arbitre a été mis à jour, {@code false} sinon
	 * @see DAOArbitre#update(Arbitre)
	 */
	public static boolean update(Arbitre value) {
		try {
			boolean b = dao.update(value);
			Observable.attach(OBSERVERSKEYS);
			if (b) {
				Observable.notifyStaticObservers();
			}
			return b;
		} catch (SQLException e) {
			System.err.println("Failed to update arbitre in update " + Arbitre.class);
			return false;
		}
	}

	/**
	 * Methode de suppression d'un arbitre
	 * @param id L'identifiant de l'arbitre à supprimer
	 * @return {@code true} si l'arbitre a été supprimé, {@code false} sinon
	 * @see DAO.DAOSimplified#delete(Object[])
     */
	public static boolean delete(int id) {
		try {
			boolean b = dao.delete(id);
			Observable.attach(OBSERVERSKEYS);
			if (b) {
				Observable.notifyStaticObservers();
			}
			return b;
		} catch (SQLException e) {
			System.err.println("Failed to delete arbitre in delete " + Arbitre.class);
			return false;
		}
	}

	/**
	 * Methode de récupération d'un arbitre par son identifiant
	 * @param id L'identifiant de l'arbitre à récupérer
	 * @return L'arbitre correspondant à l'identifiant
	 * @see DAO.DAOSimplified#getById(Object[])
	 */
	public static Arbitre getById(int id) {
		try {
			return dao.getById(id);
		} catch (SQLException e) {
			System.err.println("Failed to get arbitre in getById " + Arbitre.class);
			return null;
		}
	}

	/**
	 * Methode de récupération de tous les arbitres
	 * @return La liste de tous les arbitres
	 * @see DAO.DAOSimplified#getAll()
	 */
	public static List<Arbitre> getAll() {
		try {
			return dao.getAll();
		} catch (SQLException e) {
			System.err.println("Failed to get arbitre in getAll " + Arbitre.class);
			return null;
		}
	}

	/**
	 * Methode de récupération d'un arbitre par son nom
	 * @param nom Le nom de l'arbitre à récupérer
	 * @return La liste des arbitres correspondant au nom
	 */
	public static List<Arbitre> getByNom(String nom) {
    	try {
			return dao.getByNom(nom);
		} catch (SQLException e) {
			System.err.println("Failed to get Equipe in getAll " + Arbitre.class);
			return null;
		}
    }
}
