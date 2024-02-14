package Modele;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import DAO.DAOEquipe;
import vue.Observable;

/**
 * La classe {@code Equipe} représente une équipe de joueurs.
 * @author Nail Lamarti
 */
public class Equipe extends Observable {

	/**
	 * Liste des clés des observateurs requis pour cette classe
	 */
	private static final String[] OBSERVERSKEYS = {"Saison", "Equipe", "Tournoi", "Joueur", "Match"};
	
	/**
	 * Identifiant de l'équipe
	 */
	private int id;
	
	/**
	 * Nom de l'équipe
	 */
	private String nom;
	
	/**
	 * Code de l'équipe
	 */
	private String code;
	
	/**
	 * Lien vers l'image de l'équipe
	 */
	private String img;

	/**
	 * Lien vers le Pays de l'équipe
	 */
	private Pays pays;
	
	/**
	 * Set de {@code Joueur} faisant partis de l'équipe
	 */
	private Set<Joueur> joueurs = new HashSet<Joueur>();

	/**
	 * Classement mondial de l'équipe
	 */
	private Integer worldRank;

	/**
	 * DAO de l'équipe
	 */
	private static DAOEquipe dao = DAOEquipe.getDAO();

	/**
	 * Constructeur de la classe Equipe avec ajout dans la base de données et notification des observateurs
	 * @param nom Le nom de l'équipe
	 * @param code Le code de l'équipe
	 * @param img Le lien vers l'image de l'équipe
	 * @param pays Le pays de l'équipe
	 * @param worldRank Le classement mondial de l'équipe
	 */
	public Equipe(String nom, String code, String img, Pays pays, int worldRank) {
		super();
		Observable.attach(OBSERVERSKEYS);
		try {
			this.pays = pays;
			this.nom = nom;
			this.code = code;
			this.img = img;
			this.worldRank = worldRank;
			boolean b = dao.add(this);
			if (b) {
				Observable.notifyStaticObservers();
				this.setId();
			}
		} catch (SQLException e) {
			System.err.println("Failed DAOEquipe creation/adding in Equipe constructor");
		}
	}

	/**
	 * Constructeur de la classe Equipe sans ajout dans la base de données
	 * @param id L'identifiant de l'équipe
	 * @param nom Le nom de l'équipe
	 * @param code Le code de l'équipe
	 * @param img Le lien vers l'image de l'équipe
	 * @param pays Le pays de l'équipe
	 * @param worldRank Le classement mondial de l'équipe
	 */
	public Equipe(int id, String nom, String code, String img, Pays pays, int worldRank) {
		dao = DAOEquipe.getDAO();
		this.pays = pays;
		this.nom = nom;
		this.code = code;
		this.img = img;
		this.worldRank = worldRank;
		this.id = id;
	}
	
	/**
	 * Getter de l'identifiant de l'équipe
	 * @return L'identifiant de l'équipe
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter du nom de l'équipe
	 * @return Le nom de l'équipe
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Getter du code de l'équipe
	 * @return Le code de l'équipe
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Getter du nom complet de l'équipe
	 * @return Le nom complet de l'équipe
	 */
	public String getFullName () {return "[ "+ code+" ] "+ nom ;};

	/**
	 * Getter du lien vers l'image de l'équipe
	 * @return Le {@code String} contanant le path vers l'image de l'équipe
	 */
	public String getImage() {
		return img;
	}

	/**
	 * Getter du pays de l'équipe
	 * @return Le pays de l'équipe
	 */
	public Pays getPays() {
		return pays;
	}
	
	/**
	 * Getter du set de joueurs de l'équipe
	 * @return Le Set qui contient les joueurs de l'équipe
	 */
	public Set<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	*	Getter du classement mondial de l'équipe
	 * @return Le classement mondial de l'équipe
	*/
	public Integer getWorldRank() {
		return this.worldRank;
	}

	/**
	 * Setter de l'identifiant de l'équipe
	 * @throws SQLException Exception SQL si l'identifiant n'est pas trouvé
	 * @see DAOEquipe#getLastId()
	 */
	private void setId() throws SQLException {
		this.id = dao.getLastId();
	}

	/**
	 * Setter du nom de l'équipe
	 * @param nom Le nom de l'équipe
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Setter du code de l'équipe
	 * @param code Le code de l'équipe
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Setter du lien vers l'image de l'équipe
	 * @param img Le path vers l'image de l'équipe
	 */
	public void setImage(String img) {
		this.img = img;
	}

	/**
	 * Setter du pays de l'équipe
	 * @param pays Le pays de l'équipe
	 */
	public void setPays(Pays pays) {
		this.pays = pays;
	}

	/**
	 * Setter du classement mondial de l'équipe
	 * @param world_rank Le classement mondial de l'équipe
	 */
	public void setWorldRank(Integer world_rank) {
		this.worldRank = world_rank;
	}

	/**
	 * Fonction pour ajouter un {@code Joueur} à l'équipe
	 * @param joueur Le joueur à ajouter
	 */
	public void addJoueur(Joueur joueur) {
		joueurs.add(joueur);
	}

	/**
	 * Fonction pour supprimer un {@code Joueur} de l'équipe
	 * @param joueur Le joueur à supprimer
	 */
	public void remJoueur(Joueur joueur) {
		joueurs.remove(joueur);
	}

	/**
	 * Methode d'affichage d'une équipe
	 * @return Le {@code String} correspondant à l'équipe
	 */
	@Override
	public String toString() {
		String str = "Equipe " + nom + " (" + id + ") :\n";
		for (Joueur j : joueurs) {
			str += "\t" + j.toString()+ "\n";
		}
		return str;
	}

	/**
	 * Methode de hashage d'une équipe
	 * @return Le hash correspondant à l'équipe
	 */
	@Override
	public int hashCode() {
		return (this.nom + this.id).hashCode();
	}

	/**
	 * Methode de comparaison de deux équipes
	 * @param o L'équipe à comparer
	 * @return true si les deux équipes sont identiques, false sinon
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Equipe equipe = (Equipe) o;
		return id == equipe.id;
	}

	/**
	 * Methode permettant de mettre à jour une équipe dans la base de données et de notifier les observateurs
	 * @param equipe équipe à metrre à jour
	 * @return true si l'équipe a été mise à jour, false sinon
	 */
	public static boolean update(Equipe equipe) {
		try {
			boolean b = dao.update(equipe);
			Observable.attach(OBSERVERSKEYS);
			if (b) {
				Observable.notifyStaticObservers();
			}
			return b;
		} catch (SQLException e) {
			System.err.println("Failed to update Equipe in update " + Equipe.class);
			return false;
		}
	}

	/**
	 * Methode permettant de supprimer une équipe de la base de données et de notifier les observateurs
	 * @param id identifiant de l'équipe à supprimer
	 * @return true si l'équipe a été supprimée, false sinon
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
			System.err.println("Failed to delete Equipe in delete " + Equipe.class);
			return false;
		}
	}

	/**
	 * Methode permettant de récupérer une équipe de la base de données
	 * @param id identifiant de l'équipe à récupérer
	 * @return l'équipe récupérée
	 */
	public static Equipe getById(int id) {
		try {
			return dao.getById(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Equipe in getById " + Equipe.class);
			return null;
		}
	}

	/**
	 * Methode permettant de récupérer toutes les équipes de la base de données
	 * @return la liste des équipes récupérées
	 */
	public static List<Equipe> getAll() {
		try {
			return dao.getAll();
		} catch (SQLException e) {
			System.err.println("Failed to get Equipe in getAll " + Equipe.class);
			return null;
		}
	}

	/**
	 * Methode permettant de récupérer une ou plusieurs équipes de la base de données à partir d'un nom
	 * @param nom nom de l'équipe à récupérer
	 * @return la liste des équipes récupérées
	 */
    public static List<Equipe> getByNom(String nom) {
    	try {
			return dao.getByNom(nom);
		} catch (SQLException e) {
			System.err.println("Failed to get Equipe in getAll " + Equipe.class);
			return null;
		}
    }

	/**
	 * Methode permettant de récupérer le dernier identifiant d'équipe de la base de données
	 * @return le dernier identifiant d'équipe récupéré
	 */
	public static int getLastId() {
		try {
			return dao.getLastId();
		} catch (SQLException e) {
			System.err.println("Failed to get Equipe in getLastId " + Equipe.class);
			return -1;
		}
	}
}
