package DAO;

import Modele.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe permettant de définir les méthodes spécifiques d'un DAO pour un admin
 */
public class DAOAdmin extends DAOSimplified<Admin, Integer> {

    /**
     * Instance de la classe (patron singleton)
     */
    private static DAOAdmin instance;

    /**
     * Constructeur de la classe
     * @throws SQLException exception SQL si la connexion à la base de données échoue
     */
    private DAOAdmin() throws SQLException {
        super();
    }

    /**
     * Methode permettant de récupérer l'instance de la classe (patron singleton)
     * @return l'instance de la classe
     */
    public static synchronized DAOAdmin getDAO() {
    	if (instance == null) {
    		try {
				instance = new DAOAdmin();
			} catch (SQLException e) {
				System.err.println("Erreur instance DAOAdmin");
			}
    	}
    	return instance;
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'un Admin dans la base de données
     * @param value Admin à ajouter
     * @return la requête pour ajouter l'Admin dans la base de données
     * @see DAOSimplified#getRequestAdd(Object)
     */
    @Override
    public String getRequestAdd(Admin value) {
        return "INSERT INTO ADMIN (login, mdp) VALUES ('" + value.getLogin() + "', '" + value.getMdp() + "')";
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un Admin dans la base de données
     * @param id identifiant de l'Admin à supprimer
     * @return la requête pour supprimer l'Admin dans la base de données
     * @see DAOSimplified#getRequestDelete(Object)
     */
    @Override
    public String getRequestDelete(Integer id) {
        return "DELETE FROM ADMIN WHERE id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'un Admin dans la base de données
     * @param value Admin à mettre à jour
     * @return la requête pour mettre à jour l'Admin dans la base de données
     * @see DAOSimplified#getRequestUpdate(Object)
     */
    @Override
    public String getRequestUpdate(Admin value) {
        return "UPDATE ADMIN SET login = '" + value.getLogin() + "', mdp = '" + value.getMdp() + "' WHERE id = " + value.getId();
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'un Admin dans la base de données par son identifiant
     * @param id identifiants de l'Admin à récupérer
     * @return la requête pour récupérer l'Admin dans la base de données
     * @see DAOSimplified#getRequestGetById(Object)
     */
    @Override
    protected String getRequestGetById(Integer id) {
        return "SELECT * FROM ADMIN WHERE id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les Admins dans la base de données
     * @return la requête pour récupérer tous les Admins dans la base de données
     * @see DAOSimplified#getRequestGetAll()
     */
    @Override
    public String getRequestGetAll() {
        return "SELECT * FROM ADMIN";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération du dernier identifiant de la table Admin
     * @return la requête pour récupérer le dernier identifiant de la table Admin
     * @see DAOSimplified#getRequestGetLastId()
     */
    @Override
    public String getRequestGetLastId() {
        return "SELECT MAX(id) FROM ADMIN";
    }

    /**
     * Transforme un ResultSet en Admin
     * @param resultSet ResultSet à transformer
     * @return l'Admin créé
     * @see DAOSimplified#resultSetToT(ResultSet)
     */
    @Override
    public Admin resultSetToT(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            return new Admin(resultSet.getInt("id"),
                    resultSet.getString("login"),
                    resultSet.getInt("mdp")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
