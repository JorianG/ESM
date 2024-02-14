package DAO;

import Modele.Pays;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe permettant de définir les méthodes spécifiques d'un DAO pour un Pays
 */
public class DAOPays extends DAOSimplified<Pays, Integer> {

    /**
     * Instance de la classe (patron singleton)
     */
    private static DAOPays instance;

    /**
     * Constructeur de la classe
     * @throws SQLException exception SQL si la connexion à la base de données échoue
     */
    private DAOPays() throws SQLException {
    }

    /**
     * Methode permettant de récupérer l'instance de la classe (patron singleton)
     * @return l'instance de la classe
     */
    public static synchronized DAOPays getDAO() {
    	if (instance == null) {
    		try {
				instance = new DAOPays();
			} catch (SQLException e) {
				System.err.println("Erreur instance DAOPays");
			}
    	}
    	return instance;
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'un Pays dans la base de données
     * @param value Pays à insérer
     * @return la requête pour ajouter du Pays dans la base de données
     * @see DAOSimplified#getRequestAdd(Object)
     */
    @Override
    public String getRequestAdd(Pays value) {
        return "INSERT INTO PAYS (nom, drapeau) VALUES ('" + value.getNom() + "', '" + value.getDrapeau() + "')";
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un Pays dans la base de données
     * @param id identifiant d'un Pays à supprimer
     * @return la requête pour supprimer du Pays dans la base de données
     * @see DAOSimplified#getRequestDelete(Object)
     */
    @Override
    public String getRequestDelete(Integer id) {
        return "DELETE FROM PAYS WHERE id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'un Pays dans la base de données
     * @param value Pays à mettre à jour
     * @return la requête pour mettre à jour du Pays dans la base de données
     * @see DAOSimplified#getRequestUpdate(Object)
     */
    @Override
    public String getRequestUpdate(Pays value) {
        return "UPDATE PAYS SET nom = '" + value.getNom() + "', drapeau = '" + value.getDrapeau() + "' WHERE id = " + value.getId();
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'un Pays dans la base de données par son identifiant
     * @param id identifiants du Pays à récupérer
     * @return la requête pour récupérer l'objet Pays dans la base de données
     * @see DAOSimplified#getRequestGetById(Object)
     */
    @Override
    protected String getRequestGetById(Integer id) {
        return "SELECT * FROM PAYS WHERE id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les Pays dans la base de données
     * @return la requête pour récupérer tous les objets dans la base de données
     * @see DAOSimplified#getRequestGetAll()
     */
    @Override
    public String getRequestGetAll() {
        return "SELECT * FROM PAYS";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération du dernier identifiant d'un Pays dans la base de données
     * @return la requête pour récupérer le dernier identifiant d'un Pays dans la base de données
     */
    @Override
    public String getRequestGetLastId() {
        return "SELECT MAX(id) FROM PAYS";
    }

    /**
     * Methode renvoyant un objet Pays à partir d'un ResultSet
     * @param resultSet ResultSet à transformer
     * @return l'objet Pays créé
     * @see DAOSimplified#resultSetToT(ResultSet)
     */
    @Override
    public Pays resultSetToT(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            return new Pays(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("drapeau"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
