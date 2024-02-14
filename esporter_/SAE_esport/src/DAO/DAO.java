package DAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface permettant de définir les méthodes de base d'un DAO
 * @param <T> type de l'objet à manipuler
 * @param <K> type de l'identifiant de l'objet
 */
public interface DAO<T, K> {

    /**
     * Ajoute un objet dans la base de données
     * @param value objet à ajouter
     * @return true si l'objet a été ajouté, false sinon
     * @throws SQLException si une erreur survient lors de l'ajout
     */
    boolean add(T value) throws SQLException;

    /**
     * Supprime un objet de la base de données
     * @param ids identifiant de l'objet à supprimer
     * @return true si l'objet a été supprimé, false sinon
     * @throws SQLException si une erreur survient lors de la suppression
     */
    boolean delete(@SuppressWarnings("unchecked") K... ids) throws SQLException;

    /**
     * Met à jour un objet de la base de données
     * @param value objet à mettre à jour
     * @return true si l'objet a été mis à jour, false sinon
     * @throws SQLException si une erreur survient lors de la mise à jour
     */
    boolean update(T value) throws SQLException;

    /**
     * Récupère un objet de la base de données
     * @param ids identifiants de l'objet à récupérer
     * @return l'objet récupéré
     * @throws SQLException si une erreur survient lors de la récupération
     */
    T getById(@SuppressWarnings("unchecked") K... ids) throws SQLException;

    /**
     * Récupère tous les objets de la base de données
     * @return la liste des objets récupérés
     * @throws SQLException si une erreur survient lors de la récupération
     */
    List<T> getAll() throws SQLException;

    /**
     * Récupère le dernier id de la table
     * @return le dernier id de la table
     * @throws SQLException si une erreur survient lors de la récupération
     */
    int getLastId() throws SQLException;

}
