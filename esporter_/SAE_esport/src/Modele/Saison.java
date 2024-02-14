package Modele;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import DAO.DAOSaison;
import vue.APP;
import vue.Observable;


/**
 * La classe {@code Saison} représente une saison
 * @author Nail Lamarti
 */
public class Saison extends Observable {

	/**
	 * Liste des clés des observateurs requis par la classe {@code Saison}
	 */
	public static final String[] OBSERVERSKEYS = {"Saison"};

	/**
	 * Année de la saison en cours
	 */
	private int annnee;

	/**
	 * Nom de la saison
	 */
	private String nom;

	/**
	 * Lien vers le logo de la saison
	 */
	private String logo;

	/**
	 * Si la saison est archivée ou non
	 */
	private boolean archived;

	/**
	 * Liste des tournois de la saison
	 */
	private Set<Tournoi> tournois;

	/**
	 * Liste des tournois de la saison
	 */
	private Set<Arbitre> poolArbitres;

	/**
	 * Classement de la saison
	 */
	private HashMap<Equipe, Integer> classement;

	/**
	 * DAO de la classe {@code Saison}
	 */
	private static final DAOSaison dao = DAOSaison.getDAO();

	/**
	 * Constructeur de la classe {@code Saison} avec possibilité d'insertion dans la base de données et de notification des observateurs
	 * @param annee Année de la saison
	 * @param nom Nom de la saison
	 * @param logo Lien vers le logo de la saison
	 * @param arbitres Liste des arbitres de la saison
	 * @param classement Classement de la saison
	 * @param inserting {@code true} si on veut insérer la saison dans la base de données, {@code false} sinon
	 */
	public Saison(int annee, String nom, String logo, Set<Arbitre> arbitres, HashMap<Equipe, Integer> classement, boolean archived, boolean inserting) {
		super();
		if (inserting) {
			Observable.attach(OBSERVERSKEYS);
			try {
				this.setAnnee(annee);
				this.setNom(nom);
				this.setLogo(logo);
				this.setTournois(new HashSet<>());
				this.setPoolArbitres(arbitres);
				this.setClassement(classement);
				this.setArchived(archived);
				boolean b = dao.add(this);
				if (b) {
					Observable.notifyStaticObservers();
				}
			} catch (SQLException e) {
				System.err.println("Failed DAOSaison creation/adding in Saison constructor");
			}
		} else {
			this.setAnnee(annee);
			this.setNom(nom);
			this.setLogo(logo);
			this.setTournois(new HashSet<>());
			this.setPoolArbitres(arbitres);
			this.setClassement(classement);
			this.setArchived(archived);
		}
	}

	/**
	 * Getter de l'année de la saison
	 * @return L'année de la saison
	 */
	public int getAnnee() {
		return annnee;
	}

	/**
	 * Getter du nom de la saison
	 * @return Le nom de la saison
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * Getter du lien vers le logo de la saison
	 * @return Le lien vers le logo de la saison
	 */
	public String getLogo() {
		return this.logo;
	}

	/**
	 * Getter de la liste des tournois de la saison
	 * @return La liste des tournois de la saison
	 */
	public Set<Arbitre> getPoolArbitres() {
		return this.poolArbitres;
	}

	/**
	 * Getter du classement de la saison
	 * @return Le classement de la saison
	 */
	public HashMap<Equipe, Integer> getClassement() {
		return this.classement;
	}

	/**
	 * Setter du nom de la saison
	 * @param nom Le nom de la saison
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Setter du lien vers le logo de la saison
	 * @param logo Le lien vers le logo de la saison
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * Setter de l'année de la saison
	 * @param annee L'année de la saison
	 */
	private void setAnnee(int annee) {
		this.annnee = annee;
	}

	/**
	 * Setter de la liste des tournois de la saison
	 * @param tournois La liste des tournois de la saison
	 */
	public void setTournois(Set<Tournoi> tournois) {
		this.tournois = tournois;
	}

	/**
	 * Setter de la liste des arbitres de la saison
	 * @param poolArbitres La liste des arbitres de la saison
	 */
	public void setPoolArbitres(Set<Arbitre> poolArbitres) {
		this.poolArbitres = poolArbitres;
	}

	/**
	 * Setter du classement de la saison
	 * @param classement Le classement de la saison
	 */
	public void setClassement(HashMap<Equipe, Integer> classement) {
		this.classement = classement;
	}

	/**
	 * Ajoute un tournoi dans la saison
	 * @param tournoi Object of class {@code Tournoi}
	 */
	public void addTournoi(Tournoi tournoi) {
		tournois.add(tournoi);
	}

	/**
	 * Récupère un nombre aléatoire d'arbitres dans la liste des arbitres de la saison de manière aléatoire
	 * @return Un {@code Set} d'arbitres
	 */
	public Set<Arbitre> getArbitresRandom() {
		Set<Arbitre> arbitres = new HashSet<>();
		Random rand = new Random();
		int i = 0;
		while (i < 4) {
			int n = rand.nextInt(this.poolArbitres.size());
			int j = 0;
			for (Arbitre arbitre : this.poolArbitres) {
				if (j == n) {
					arbitres.add(arbitre);
					i++;
				}
				j++;
			}
		}
		return arbitres;
	}

	/**
	 * Retourne si la saison est archivée ou non
	 * @return {@code true} si la saison est archivée, {@code false} sinon
	 */
	public boolean isArchived() {
		return archived;
	}

	/**
	 * Setter de l'attribut archived
	 * @param archived {@code true} si la saison est archivée, {@code false} sinon
	 */
	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	/**
	 * Methode d'affichage d'une saison
	 * @return Le {@code String} correspondant à la saison
	 */
	@Override
	public String toString() {
		return "Saison " + nom;
	}

	/**
	 * Methode de comparaison de deux saisons
	 * @param o Object of class {@code Saison}
	 * @return {@code true} si les deux saisons sont égales, {@code false} sinon
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Saison saison = (Saison) o;
		return annnee == saison.annnee;
	}

	/**
	 * Methode de hashage d'une saison
	 * @return Le hash correspondant à la saison
	 */
	@Override
	public int hashCode() {
		return Objects.hash(annnee);
	}

	/**
	 * Récupère le tournoi en cours dans la saison
	 * @return Le tournoi en cours dans la saison
	 */
	public static Tournoi getCurrentTournoi() {
		for (Tournoi t : Objects.requireNonNull(Tournoi.getBySaison(APP.SELECTEDYEAR))) {
			if (t.isCurrent()) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Méthode de mise à jour d'une saison dans la base de données et de notification des observateurs
	 * @param saison {@code Saison} à mettre à jour
	 * @return {@code true} si la mise à jour a été effectuée, {@code false} sinon
	 */
	public static boolean update(Saison saison) {
		try {
			boolean b = dao.update(saison);
			Observable.attach(OBSERVERSKEYS);
			if (b) {
				Observable.notifyStaticObservers();
			}
			return b;
		} catch (SQLException e) {
			System.err.println("Failed to update Saison in update " + Saison.class);
			return false;
		}
	}

	/**
	 * Méthode de suppression d'une saison dans la base de données et de notification des observateurs
	 * @param id Identifiant de la {@code Saison} à supprimer
	 * @return {@code true} si la suppression a été effectuée, {@code false} sinon
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
			System.err.println("Failed to delete Saison in delete " + Saison.class);
			return false;
		}
	}

	public static boolean destroy(int id) {
        try {
            return dao.destroy(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * Méthode de récupération d'une saison dans la base de données
	 * @param id Identifiant de la {@code Saison} à récupérer
	 * @return La {@code Saison} correspondant à l'identifiant
	 */
	public static Saison getById(int id) {
		try {
			return dao.getById(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Saison in getById " + Saison.class);
			return null;
		}
	}

	/**
	 * Méthode de récupération de toutes les saisons dans la base de données
	 * @return La liste des {@code Saison} correspondant à la requête
	 */
	public static List<Saison> getAll() {
		try {
			return dao.getAll();
		} catch (SQLException e) {
			System.err.println("Failed to get Saison in getAll " + Saison.class);
			return null;
		}
	}

	/**
	 * Méthode de récupération des équipes d'une saison dans la base de données
	 * @param id Identifiant de la {@code Saison} à récupérer
	 * @return La liste des {@code Equipe} correspondant à la requête
	 */
	public static HashMap<Equipe, Integer> getEquipeBySaison(int id) {
		try {
			return dao.getEquipeBySaison(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Equipe by Saison in getEquipeBySaison " + Saison.class);
			return null;
		}
	}

	/**
	 * Méthode de récupération des points d'une équipe dans une saison dans la base de données
	 * @param saison Saison
	 * @param equipe Equipe
	 * @return Le nombre de points de l'équipe dans la saison
	 */
	public static int getPointsEquipe(Saison saison, Equipe equipe) {
		try {
			return dao.getPointsEquipe(saison, equipe);
		} catch (SQLException e) {
			System.err.println("Failed to get Points Equipe in getPointsEquipe " + Saison.class);
			return -1;
		}
	}

	/**
	 * Méthode de récupération des arbitres d'une saison dans la base de données
	 * @param id Identifiant de la {@code Saison} à récupérer
	 * @return La liste des {@code Arbitre} correspondant à la requête
	 */
	public static Set<Arbitre> getArbitreInSaison(int id) {
		try {
			return dao.getArbitreInSaison(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Arbitre in Saison in getArbitreInSaison " + Saison.class);
			return null;
		}
	}

	/**
	 * Méthode de récupération des futurs matchs d'une saison dans la base de données
	 * @return La liste des {@code Match} correspondant à la requête
	 */
	public Set<Match> getMatchsFuturs() {
		Set<Match> m = new HashSet<>();
		for (Tournoi t : Objects.requireNonNull(Tournoi.getBySaison(this.getAnnee()))){
			m.addAll(t.getListMatchs().stream().filter(match -> match.getDateDebutMatch().isAfter(new CustomDateTime(LocalDateTime.now()))).collect(Collectors.toList()));
		}
		return m;
	}
}
