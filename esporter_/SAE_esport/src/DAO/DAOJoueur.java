package DAO;

import Modele.Equipe;
import Modele.Joueur;
import Modele.Pays;
import Modele.Poste;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe permettant de définir les méthodes spécifiques d'un DAO pour une Equipe
 */
public class DAOJoueur extends DAOSimplified<Joueur, Integer> {

    /**
     * Instance de la classe (patron singleton)
     */
	private static DAOJoueur instance;

    /**
     * Constructeur de la classe
     * @throws SQLException exception SQL si la connexion à la base de données échoue
     */
    private DAOJoueur() throws SQLException {
    }

    /**
     * Methode permettant de récupérer l'instance de la classe (patron singleton)
     * @return l'instance de la classe
     */
    public static synchronized DAOJoueur getDAO() {
    	if (instance == null) {
    		try {
				instance = new DAOJoueur();
			} catch (SQLException e) {
				System.err.println("Erreur instance DAOJoueur");
			}
    	}
    	return instance;
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'un Joueur dans la base de données
     * @param value Joueur à insérer
     * @return la requête pour ajouter du joueur dans la base de données
     * @see DAOSimplified#getRequestAdd(Object)
     */
    @Override
    public String getRequestAdd(Joueur value) {
        if (value.getId_equipe() != null) {
            return "INSERT INTO joueur (nom, prenom, pseudo, img, id_equipe, id_poste, id_pays) VALUES ('" + value.getNom() + "', '" + value.getPrenom() + "', '" + value.getPseudo() + "', '" + value.getImage() + "', " + value.getId_equipe() + ", '" + value.getPoste().name() + "', " + value.getPays().getId() + ")";
        }
        return "INSERT INTO joueur (nom, prenom, pseudo, img, id_equipe, id_poste, id_pays) VALUES ('" + value.getNom() + "', '" + value.getPrenom() + "', '" + value.getPseudo() + "', '" + value.getImage() + "', NULL, '" + value.getPoste().name() + "', " + value.getPays().getId() + ")";
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un Joueur dans la base de données
     * @param id identifiant du Joueur à supprimer
     * @return la requête pour supprimer du joueur dans la base de données
     * @see DAOSimplified#getRequestDelete(Object)
     */
    @Override
    public String getRequestDelete(Integer id) {
        return "delete from joueur where id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'un Joueur dans la base de données
     * @param value Joueur à mettre à jour
     * @return la requête pour mettre à jour du joueur dans la base de données
     * @see DAOSimplified#getRequestUpdate(Object)
     */
    @Override
    public String getRequestUpdate(Joueur value) {
        if (value.getId_equipe() != null) {

            return "UPDATE joueur SET nom = '" + value.getNom() + "', prenom = '" + value.getPrenom() + "', pseudo = '" + value.getPseudo() + "', img = '" + value.getImage() + "', id_equipe = " + value.getId_equipe() + ", id_poste = '" + value.getPoste().name() + "', id_pays = " + value.getPays().getId() + " WHERE id = " + value.getId();
        }
        return "UPDATE joueur SET nom = '" + value.getNom() + "', prenom = '" + value.getPrenom() + "', pseudo = '" + value.getPseudo() + "', img = '" + value.getImage() + "', id_equipe = NULL, id_poste = '" + value.getPoste().name() + "', id_pays = " + value.getPays().getId() + " WHERE id = " + value.getId();
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'un Joueur dans la base de données par son identifiant
     * @param id identifiants du Joueur à récupérer
     * @return la requête pour récupérer du joueur dans la base de données
     * @see DAOSimplified#getRequestGetById(Object)
     */
    @Override
    protected String getRequestGetById(Integer id) {
        return "SELECT * FROM joueur WHERE id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les Joueurs dans la base de données
     * @return la requête pour récupérer tous les Joueurs dans la base de données
     * @see DAOSimplified#getRequestGetAll()
     */
    @Override
    public String getRequestGetAll() {
        return "SELECT * FROM joueur";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les Joueurs sans équipe dans la base de données
     * @return la requête pour récupérer tous les Joueurs sans équipe dans la base de données
     */
    public String getRequestGetAllSansEquipe() {
    	return "SELECT * FROM joueur WHERE id_equipe IS NULL";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les Joueurs d'une équipe dans la base de données
     * @param id_equipe identifiant de l'équipe
     * @return la requête pour récupérer tous les Joueurs d'une équipe dans la base de données
     */
    public String getRequestGetByEquipe(Integer id_equipe) {
    	return "SELECT * from joueur WHERE id_equipe = " + id_equipe;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération du dernier identifiant d'un Joueur dans la base de données
     * @return la requête pour récupérer le dernier identifiant d'un Joueur dans la base de données
     * @see DAOSimplified#getRequestGetLastId()
     */
    @Override
    public String getRequestGetLastId() {
        return "SELECT MAX(id) FROM joueur";
    }

    /**
     * Methode renvoyant la liste de tous les Joueurs d'une équipe dans la base de données
     * @return la liste de tous les Joueurs d'une équipe dans la base de données
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public List<Joueur> getAllSansEquipe() throws SQLException {
        ArrayList<Joueur> joueurs = new ArrayList<>();
        ResultSet resultSet = this.db.executeStatement((Statement statement) -> {
            try {
                return statement.executeQuery(getRequestGetAllSansEquipe());
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (resultSet!= null) {
            while (resultSet.next()) {
                joueurs.add(resultSetToT(resultSet));
            }
        }
        return joueurs;
    }

    /**
     * Methode renvoyant la liste de tous les Joueurs d'une équipe dans la base de données
     * @param equipe équipe dont on veut récupérer les joueurs
     * @return la liste de tous les Joueurs d'une équipe dans la base de données
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public List<Joueur> getByEquipe(Equipe equipe) throws SQLException {
    	ArrayList<Joueur> joueurs = new ArrayList<>();

    	ResultSet resultSet = this.db.executeStatement((Statement statement) -> {
            try {
                return statement.executeQuery(getRequestGetByEquipe(equipe.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (resultSet!= null) {
            while (resultSet.next()) {
            	joueurs.add(resultSetToT(resultSet));
            }
        }
		return joueurs;
    }

    /**
     * Transforme un ResultSet en Joueur
     * @param resultSet ResultSet à transformer
     * @return le joueur créé
     * @see DAOSimplified#resultSetToT(ResultSet)
     */
    @Override
    public Joueur resultSetToT(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            DAOPays dp = DAOPays.getDAO();
            Pays p = dp.getById(resultSet.getInt("id_pays"));
            Joueur J = new Joueur(
            			resultSet.getInt("id"),
                		resultSet.getString("nom"),
                		resultSet.getString("prenom"),
                		resultSet.getString("pseudo"),
                		resultSet.getString("img"),
                		Poste.valueOf(resultSet.getString("id_poste")), p,
                    Optional.of(resultSet.getInt("id_equipe")));
            return J;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
