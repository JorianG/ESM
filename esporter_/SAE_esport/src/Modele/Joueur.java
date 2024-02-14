package Modele;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import DAO.DAOJoueur;
import vue.Observable;

/**
 * La classe {@code Joueur} représente une équipe de joueurs.
 * @author Nail Lamarti
 */
public class Joueur extends Observable {


    /**
     * Liste des clés des observateurs requis par la classe {@code Joueur}
     */
    private static final String[] OBSERVERSKEYS = {"Equipe", "Joueur"};

	/**
	 * Identifiant du joueur
	 */
    int id;
    
    /**
     * Nom du joueur
     */
    String nom;
    
    /**
     * Prénom  du joueur
     */
    String prenom;
    
    /**
     * Pseudonyme du joueur
     */
    String pseudo;
    
    /**
     * Lien vers la photo du joueur
     */
    String img;

    /**
     * Equipe du joueur
     */
    Equipe equipe;

    /**
     * Identifiant de l'équipe du joueur
     */
    Optional<Integer> id_equipe;

    /**
     * Poste occupé par le joueur
     */
    Poste poste;
    
    /**
     * Pays du joueur
     */
    Pays pays;

    /**
     * DAO de la classe {@code Joueur}
     */
    private static DAOJoueur dao = DAOJoueur.getDAO();

    /**
     * Constructeur de la classe {@code Joueur} avec ajout dans la base de données et notification des observateurs
     * @param nom Le nom du joueur
     * @param prenom Le prénom du joueur
     * @param pseudo Le pseudonymme du joueur
     * @param img Le lien vers la photo du joueur
     * @param poste Le poste occupé par le joueur
     * @param pays Le pays du joueur
     */
    public Joueur(String nom, String prenom, String pseudo, String img, Poste poste, Pays pays, Optional<Integer> id_equipe) {
        super();
        Observable.attach(OBSERVERSKEYS);
        try {
            this.nom = nom;
            this.prenom = prenom;
            this.pseudo = pseudo;
            this.img = img;
            this.poste = poste;
            this.pays = pays;
            this.id_equipe = id_equipe;
            this.equipe = id_equipe.map(Equipe::getById).orElse(null);
			boolean b = dao.add(this);
            if (b) {
                Observable.notifyStaticObservers();
                this.setId();
            }
		} catch (SQLException e) {
			System.err.println("Failed DAOJoueur creation/adding in Joueur constructor");
		}
    }

    /**
     * Constructeur de la classe {@code Joueur}
     * @param id Identifiant du joueur
     * @param nom Nom du joueur
     * @param prenom Prénom du joueur
     * @param pseudo Pseudonyme du joueur
     * @param img Chemin vers la photo du joueur
     * @param poste Poste occupé par le joueur
     * @param pays Pays du joueur
     * @param id_equipe Identifiant de l'équipe du joueur
     */
    public Joueur(int id, String nom, String prenom, String pseudo, String img, Poste poste, Pays pays, Optional<Integer> id_equipe) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.pseudo = pseudo;
		this.img = img;
		this.poste = poste;
		this.pays = pays;
        this.id_equipe = id_equipe;
        this.equipe = id_equipe.map(Equipe::getById).orElse(null);
    }

    /**
     * Getter de l'identifiant du joueur
     * @return L'identifiant du joueur
     */
    public int getId() {
        return id;
    }

    /**
     * Getter du nom du joueur
     * @return Le nom du joueur
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Getter du prénom du joueur
     * @return Le prénom du joueur
     */
    public String getPrenom() {
        return prenom;
    }
    
    /**
     * Getter du pseudonyme du joueur
     * @return Le pseudonyme du joueur
     */
    public String getPseudo() {
        return pseudo;
    }
    
    /**
     * Getter du lien vers la photo du joueur
     * @return Le lien vers la photo du joueur
     */
    public String getImage() {
        return img;
    }

    /**
     * Getter de l'identifiant de l'équipe du joueur
     *
     * @return L'identifiant de l'équipe du joueur
     */
    public Integer getId_equipe() {
        if (this.id_equipe.isPresent()) {
        	return id_equipe.get();
        }else {
        	return null;
        }
    }

    /**
     * Getter du {@code Poste} du joueur
     * @return Le {@code Poste} attribué au joueur
     */
    public Poste getPoste() {
    	return poste;
    }
    
    /**
     * Getter du pays du joueur
     * @return Le {@code Pays} du joueur
     */
    public Pays getPays() {
    	return pays;
    }

    /**
     * Getter de l'équipe du joueur
     * @return L'équipe du joueur
     */
    public Equipe getEquipe() {
    	return equipe;
    }

    /**
     * Setter de l'équipe du joueur
     */
    public void setEquipe() {
    	this.equipe = Equipe.getById(id_equipe.get());
    }
    
    /**
     * Setter de l'identifiant du joueur
     * @throws SQLException 
     */
    private void setId() throws SQLException {
    	this.id = dao.getLastId();
    }

    /**
     * Setter du nom du joueur
     * @param nom Le nom du joueur
     */
    public void setNom(String nom) {
    	this.nom = nom;
    }
    
    /**
     * Setter du prénom du joueur
     * @param prenom Le prénom du joueur
     */
    public void setPrenom(String prenom) {
    	this.prenom = prenom;
    }
    
    /**
     * Setter du pseudonyme du joueur
     * @param pseudo Le pseudonyme du joueur
     */
    public void setPseudo(String pseudo) {
    	this.pseudo = pseudo;
    }
    
    /**
     * Setter du lien vers la photo du joueur
     * @param img Le lien vers la photo du joueur
     */
    public void setImage(String img) {
    	this.img = img;
    }

    /**
     * Setter de l'identifiant de l'équipe du joueur
     * @param idEquipe L'identifiant de l'équipe du joueur
     */
    public void setId_equipe(Optional<Integer> idEquipe) {
    	this.id_equipe = idEquipe;
    }

    /**
     * Setter du poste du joueur
     * @param poste Le {@code Poste} du joueur
     */
    public void setPoste(Poste poste) {
    	this.poste = poste;
    }
    
    /**
     * Setter du pays du joueur
     * @param pays Le pays du joueur
     */
    public void setPays(Pays pays) {
    	this.pays = pays;
    }

	/**
	 * Methode d'affichage d'un joueur
	 * @return Le {@code String} correspondant au joueur
	 */
    @Override
    public String toString() {
        return "(" + id + ") [" + pseudo + "] " + nom + " " + prenom;
    }

    /**
     * Methode de hashage d'un joueur
     * @return Le hash correspondant au joueur
     */
    @Override
    public int hashCode() {
    	return this.toString().hashCode();
    }

    /**
     * Methode d'égalité entre deux joueurs
     * @param o L'objet à comparer avec le joueur
     * @return Vrai si les deux joueurs sont égaux, faux sinon
     */
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return this.id == joueur.id;
    }

    /**
     * Methode d'affichage du nom complet d'un joueur
     * @return Le nom complet du joueur
     */
    public String getFullName() {
    	return this.prenom+" '"+this.pseudo+"' "+this.nom;
    }

    /**
     * Methode de mise à jour d'un joueur dans la base de données et notification des observateurs
     * @param j Le joueur à mettre à jour
     * @return Vrai si la mise à jour a été effectuée, faux sinon
     */
	public static boolean update(Joueur j) {
		try {
            boolean b = dao.update(j);
            Observable.attach(OBSERVERSKEYS);
            if (b) {
                Observable.notifyStaticObservers();
            }
			return b;
		} catch (SQLException e) {
			System.err.println("Failed to update Joueur in update " + Joueur.class);
			return false;
		}
	}

    /**
     * Methode de suppression d'un joueur dans la base de données et notification des observateurs
     * @param id L'identifiant du joueur à supprimer
     * @return Vrai si la suppression a été effectuée, faux sinon
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
			System.err.println("Failed to delete Joueur in delete " + Joueur.class);
			return false;
		}
	}

    /**
     * Methode de récupération d'un joueur dans la base de données
     * @param id L'identifiant du joueur à récupérer
     * @return Le joueur récupéré
     */
	public static Joueur getById(int id) {
		try {
			return dao.getById(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Joueur in getById " + Joueur.class);
			return null;
		}
	}

    /**
     * Methode de récupération de tous les joueurs dans la base de données
     * @return La liste des joueurs récupérés
     */
	public static List<Joueur> getAll() {
		try {
			return dao.getAll();
		} catch (SQLException e) {
			System.err.println("Failed to get Joueur in getAll " + Joueur.class);
			return null;
		}
	}

    /**
     * Methode de récupération de tous les joueurs sans équipe dans la base de données
     * @return La liste des joueurs récupérés
     */
    public static List<Joueur> getAllSansEquipe() {
        try {
			return dao.getAllSansEquipe();
		} catch (SQLException e) {
			System.err.println("Failed to get Joueur in getAllSansEquipe " + Joueur.class);
			return null;
		}
    }
    
    /**
     * Methode de récupération de tous les joueurs d'une équipe dans la base de données
     * @param equipe L'équipe dont on veut récupérer les joueurs
     * @return La liste des joueurs récupérés
     */
    public static List<Joueur> getByEquipe(Equipe equipe) {
    	try {
			return dao.getByEquipe(equipe);
		} catch (SQLException e) {
			System.err.println("Failed to get Joueur in getAllByEquipe " + Joueur.class);
			return null;
		}
    }
}
