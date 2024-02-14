package DAO;

import Modele.Equipe;
import Modele.Pays;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe permettant de définir les méthodes spécifiques d'un DAO pour une Equipe
 */
public class DAOEquipe extends DAOSimplified<Equipe, Integer> {

    /**
     * Instance de la classe (patron singleton)
     */
	private static DAOEquipe instance;

    /**
     * Constructeur de la classe
     * @throws SQLException exception SQL si la connexion à la base de données échoue
     */
    private DAOEquipe() throws SQLException {
    }

    /**
     * Methode permettant de récupérer l'instance de la classe (patron singleton)
     * @return l'instance de la classe
     */
    public static synchronized DAOEquipe getDAO() {
    	if (instance == null) {
    		try {
				instance = new DAOEquipe();
			} catch (SQLException e) {
				System.err.println("Erreur instance DAOEquipe");
			}
    	}
    	return instance;
    }
    
    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'une Equipe dans la base de données
     * @param value Equipe à ajouter
     * @return la requête pour ajouter l'Equipe dans la base de données
     * @see DAOSimplified#getRequestAdd(Object)
     */
    @Override
    public String getRequestAdd(Equipe value) {
        return "INSERT INTO Equipe (nom, code, img, idPays, world_rank) VALUES ('"
                + value.getNom() + "', '"
                + value.getCode() + "', '"
                + value.getImage() + "', "
                + value.getPays().getId() + ", "
                + value.getWorldRank() + ")";
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'une Equipe dans la base de données
     * @param id identifiant de l'Equipe à supprimer
     * @return la requête pour supprimer l'Equipe dans la base de données
     * @see DAOSimplified#getRequestDelete(Object)
     */
    @Override
    public String getRequestDelete(Integer id) {
        return "DELETE FROM Equipe WHERE id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'une Equipe dans la table de correspondance Equipe-Tournoi (Participer_Tournoi)
     * @param id identifiant de l'Equipe à supprimer
     * @return la requête pour supprimer l'Equipe dans la table de correspondance Equipe-Tournoi (Participer_Tournoi)
     */
    public String getRequestDeleteInTournoi(Integer id) {
        return "DELETE FROM PARTICIPER_TOURNOI WHERE id_Equipe = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'une Equipe dans la table Match_LOL
     * @param id identifiant de l'Equipe à supprimer
     * @return la requête pour supprimer l'Equipe dans la table Match_LOL
     */
    public String getRequestDeleteInMatch(Integer id) {
        return "DELETE FROM MATCH_LOL WHERE ID_EQUIPE1 = " + id + " OR ID_EQUIPE2 = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'une Equipe dans la table de correspondance Equipe-Saison (Participer_Saison)
     * @param id identifiant de l'Equipe à supprimer
     * @return la requête pour supprimer l'Equipe dans la table de correspondance Equipe-Saison (Participer_Saison)
     */
    public String getRequestDeleteInSaison(Integer id) {
        return "DELETE FROM PARTICIPER_SAISON WHERE id_Equipe = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'une Equipe dans la base de données
     * @param value Equipe à mettre à jour
     * @return la requête pour mettre à jour l'Equipe dans la base de données
     * @see DAOSimplified#getRequestUpdate(Object)
     */
    @Override
    public String getRequestUpdate(Equipe value) {
        return "UPDATE Equipe SET nom = '" + value.getNom()
        		+ "', code = '" + value.getCode()
        		+ "', img = '" + value.getImage()
                + "', idPays = " + value.getPays().getId()
                + ", world_rank = " + value.getWorldRank()
                + " WHERE id = " + value.getId() ;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'une Equipe dans la base de données par son identifiant
     * @param id identifiants de l'Equipe à récupérer
     * @return la requête pour récupérer l'Equipe dans la base de données
     * @see DAOSimplified#getRequestGetById(Object)
     */
    @Override
    protected String getRequestGetById(Integer id) {
        return "SELECT * FROM Equipe WHERE id = " + id;
    }


    /**
     * Methode renvoyant la requête nécessaire à la récupération de toutes les Equipes dans la base de données
     * @return la requête pour récupérer toutes les Equipes dans la base de données
     * @see DAOSimplified#getRequestGetAll()
     */
    @Override
    public String getRequestGetAll() {
        return "SELECT * FROM Equipe";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'une ou plusieurs Equipes dans la base de données via leur nom
     * @param nom nom de l'Equipe à récupérer
     * @return la requête pour récupérer l'Equipe dans la base de données
     */
    public String getRequestByNom(String nom) {
    	return "SELECT * FROM Equipe WHERE nom LIKE '%"+nom+"%'";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération du dernier identifiant de la table Equipe
     * @return la requête pour récupérer le dernier identifiant de la table Equipe
     * @see DAOSimplified#getRequestGetLastId()
     */
    @Override
    public String getRequestGetLastId() {
        return "SELECT MAX(id) FROM Equipe";
    }

    /**
     * Methode permettant de récupérer une ou plusieurs Equipes dans la base de données via leur nom
     * @param nom nom de l'Equipe à récupérer
     * @return la liste des Equipes correspondant au nom
     * @throws SQLException exception SQL si la récupération échoue
     */
    public List<Equipe> getByNom(String nom) throws SQLException{
    	 ArrayList<Equipe> list = new ArrayList<>();
         ResultSet resultSet = this.db.executeStatement((Statement statement) -> {
             try {
                 return statement.executeQuery(getRequestByNom(nom));
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
     * Methode permettant de supprimer une Equipe dans la base de données
     * @param id identifiant de l'objet à supprimer
     * @return true si la suppression a été effectuée, false sinon
     * @throws SQLException si une erreur survient lors de la suppression
     * @see DAOSimplified#delete(Object...)
     */
     @Override
     public boolean delete(Integer... id) throws SQLException {
        boolean b = this.db.executeStatement(statement -> {
            try {
                statement.executeUpdate(getRequestDeleteInTournoi(id[0]));
                return true;
            } catch (SQLException e) {
                System.err.println("Erreur delete Equipe in Tournoi");
                return false;
            }
        }) && this.db.executeStatement(statement -> {
            try {
                statement.executeUpdate(getRequestDeleteInMatch(id[0]));
                return true;
            } catch (SQLException e) {
                System.err.println("Erreur delete Equipe in Match");
                return false;
            }
        }) && this.db.executeStatement(statement -> {
            try {
                statement.executeUpdate(getRequestDeleteInSaison(id[0]));
                return true;
            } catch (SQLException e) {
                System.err.println("Erreur delete Equipe in Saison");
                return false;
            }
        }) && super.delete(id);
        return b;
     }

    /**
     * Methode permettant de récupérer le dernier identifiant d'une Equipe dans la base de données
     * @return le dernier identifiant d'une Equipe dans la base de données
     * @throws SQLException si une erreur survient lors de la récupération
     * @see DAOSimplified#getLastId()
     */
     @Override
     public int getLastId() throws SQLException {
         ResultSet resultSet = this.db.executeStatement((Statement statement) -> {
             try {
                 return statement.executeQuery("SELECT max(id) as id FROM Equipe");
             } catch (SQLException e) {
                 e.printStackTrace();
                 return null;
             }
         });
         resultSet.next();
         return resultSet.getInt("id");

     }


    /**
     * Methode permettant de convertir un {@code ResultSet} en {@code Equipe}
     * @param resultSet Le {@code ResultSet} à convertir
     * @return L'{@code Equipe} correspondant au {@code ResultSet}
     * @see DAOSimplified#resultSetToT(ResultSet)
     */
    @Override
    public Equipe resultSetToT(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            return new Equipe(
            		resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("code"),
                    resultSet.getString("img"),
                    Pays.getById(resultSet.getInt("idPays")),
                    resultSet.getInt("world_rank"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
