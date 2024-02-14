package DAO;

import connect.DBConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Classe abstraite permettant de définir les méthodes de base d'un DAO
 * @param <T> type de l'objet à manipuler
 * @param <K> type de l'identifiant de l'objet
 */
public abstract class DAOSimplified<T, K> implements DAO<T, K> {

    DBConnect db;
    String url;

    /**
     * Constructeur de la classe
     */
    public DAOSimplified() throws SQLException {
        this.db = DBConnect.getDbInstance();
        this.url = db.getUrl();
    }

    public DAOSimplified(String url) throws SQLException {
        this.url = url;
        this.db = new DBConnect(url);
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'un objet dans la base de données
     * @param value objet à ajouter
     * @return la requête pour ajouter l'objet dans la base de données
     */
    public abstract String getRequestAdd(T value);

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un objet dans la base de données
     * @param id identifiant de l'objet à supprimer
     * @return la requête pour supprimer l'objet dans la base de données
     */
    public abstract String getRequestDelete(K id);

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'un objet dans la base de données
     * @param value objet à mettre à jour
     * @return la requête pour mettre à jour l'objet dans la base de données
     */
    public abstract String getRequestUpdate(T value);

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'un objet dans la base de données par son identifiant
     * @param id identifiants de l'objet à récupérer
     * @return la requête pour récupérer l'objet dans la base de données
     */
    protected abstract String getRequestGetById(K id);

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les objets dans la base de données
     * @return la requête pour récupérer tous les objets dans la base de données
     */
    public abstract String getRequestGetAll();

    public abstract String getRequestGetLastId();

    /**
     * Ajoute un objet dans la base de données
     * @param value objet à ajouter
     * @return true si l'objet a été ajouté, false sinon
     * @throws SQLException si une erreur survient lors de l'ajout
     * @see DAO#add(Object)
     */
    @Override
    public boolean add(T value) throws SQLException {
        return this.db.executeStatement((Statement statement) -> {
            try {
                statement.execute(getRequestAdd(value));
                System.out.println("add" + value.getClass().toString());
                return true;
            } catch (SQLException e) {
                System.err.println("failed to add " + value.getClass().toString());
                e.printStackTrace();
            }
            return false;
        });
    }

    /**
     * Supprime un objet de la base de données
     * @param ids identifiant de l'objet à supprimer
     * @return true si l'objet a été supprimé, false sinon
     * @throws SQLException si une erreur survient lors de la suppression
     * @see DAO#delete(Object...)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean delete(K... ids) throws SQLException {
        return this.db.executeStatement((Statement statement) -> {
            try {
                statement.execute(getRequestDelete(ids[0]));
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    /**
     * Met à jour un objet de la base de données
     * @param value objet à mettre à jour
     * @return true si l'objet a été mis à jour, false sinon
     * @throws SQLException si une erreur survient lors de la mise à jour
     * @see DAO#update(Object)
     */
    @Override
    public boolean update(T value) throws SQLException {
        return this.db.executeStatement((Statement statement) -> {
            try {
                statement.execute(getRequestUpdate(value));
                System.out.println("update" + value.getClass().toString());
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Failed to update" + value.getClass().toString());
            }
            return false;
        });
    }

    /**
     * Récupère un objet de la base de données
     * @param ids identifiants de l'objet à récupérer
     * @return l'objet récupéré
     * @throws SQLException si une erreur survient lors de la récupération
     * @see DAO#getById(Object...)
     */
    @SuppressWarnings("unchecked")
	@Override
    public T getById(K... ids) throws SQLException {
        try (Statement statement = this.db.getConn().createStatement()) {
            ResultSet resultSet = statement.executeQuery(getRequestGetById(ids[0]));
            if (resultSet.next()) {
                return resultSetToT(resultSet);
            }
            return null;
        }
    }

    /**
     * Récupère tous les objets de la base de données
     * @return la liste des objets récupérés
     * @throws SQLException si une erreur survient lors de la récupération
     * @see DAO#getAll()
     */
    @Override
    public List<T> getAll() throws SQLException {
        ArrayList<T> list = new ArrayList<>();
        ResultSet resultSet = this.db.executeStatement((Statement statement) -> {
            try {
                return statement.executeQuery(getRequestGetAll());
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (resultSet!= null) {
            while (resultSet.next()) {
                list.add(resultSetToT(resultSet));
            }
        }

        return list;
    }

    /**
     * Récupère le dernier id de la base de données
     * @return le dernier id
     * @throws SQLException si une erreur survient lors de la récupération
     * @see DAO#getLastId()
     */
    @Override
    public int getLastId() throws SQLException {
    	return this.db.executeStatement((Statement statement) -> {
            try {
                ResultSet resultSet = statement.executeQuery(getRequestGetLastId());
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return -1;
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        });
    }

    /**
     * Transforme un ResultSet en objet
     * @param resultSet ResultSet à transformer
     * @return l'objet transformé
     */
    public abstract T resultSetToT(ResultSet resultSet);


}
