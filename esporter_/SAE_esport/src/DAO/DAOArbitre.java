package DAO;

import Modele.Arbitre;
import Modele.Tournoi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de définir les méthodes spécifiques d'un DAO pour un arbitre
 */
public class DAOArbitre extends DAOSimplified<Arbitre, Integer> {

    /**
     * Instance de la classe (patron singleton)
     */
	private static DAOArbitre instance;

    /**
     * Constructeur de la classe
     * @throws SQLException exception SQL si la connexion à la base de données échoue
     */
    private DAOArbitre() throws SQLException {
        super();

    }

    /**
     * Methode permettant de récupérer l'instance de la classe (patron singleton)
     * @return l'instance de la classe
     */
    public static synchronized DAOArbitre getDAO() {
    	if (instance == null) {
    		try {
				instance = new DAOArbitre();
			} catch (SQLException e) {
				System.err.println("Erreur instance DAOArbitre");
			}
    	}
    	return instance;
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'un arbitre dans la base de données
     * @param value objet à ajouter
     * @return la requête pour ajouter l'arbitre dans la base de données
     * @see DAOSimplified#getRequestAdd(Object)
     */
    @Override
    public String getRequestAdd(Arbitre value) {
        return "INSERT INTO ARBITRE (nom, prenom, image) values" +
                "('" + value.getNom() + "', " +
                "'" + value.getPrenom() + "', " +
                "'" + value.getImage() + "')";
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un arbitre dans la base de données
     * @param id identifiant de l'objet à supprimer
     * @return la requête pour supprimer l'arbitre dans la base de données
     * @see DAOSimplified#getRequestDelete(Object)
     */
    @Override
    public String getRequestDelete(Integer id) {
        return "delete from arbitre where id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un arbitre dans la table de correspondance arbitre_saison
     * @param id identifiant de l'objet à supprimer
     * @return la requête pour supprimer l'arbitre dans la table de correspondance arbitre_saison
     */
    public String getRequestDeleteInSaison(Integer id) {
    	return "DELETE FROM Arbitre_Saison WHERE ID_ARBITRE = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un arbitre dans la table de correspondance arbitre_tournoi
     * @param id identifiant de l'objet à supprimer
     * @return la requête pour supprimer l'arbitre dans la table de correspondance arbitre_tournoi
     */
    public String getRequestDeleteInTournoi(Integer id) {
    	return "DELETE FROM ARBITRAGE_TOURNOI WHERE id_Arbitre = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la désaffectation d'un arbitre dans la base de données (table Match_LOL)
     * @param id identifiant de l'objet à supprimer
     * @return la requête pour désaffecter l'arbitre dans la base de données (table Match_LOL)
     */
    public String getRequestSetNullInMatch(Integer id) {
    	return "UPDATE MATCH_LOL SET id_Arbitre = NULL WHERE id_Arbitre = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'un arbitre dans la base de données
     * @param value objet à mettre à jour
     * @return la requête pour mettre à jour l'arbitre dans la base de données
     * @see DAOSimplified#getRequestUpdate(Object)
     */
    @Override
    public String getRequestUpdate(Arbitre value) {
        return "UPDATE arbitre SET nom = '" + value.getNom() + "', " +
                "prenom = '" + value.getPrenom() + "', " +
                "image = '" + value.getImage() + "' " +
                "WHERE id = " + value.getId();
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'un arbitre dans la base de données par son identifiant
     * @param id identifiants de l'objet à récupérer
     * @return la requête pour récupérer l'arbitre dans la base de données
     * @see DAOSimplified#getRequestGetById(Object)
     */
    @Override
    protected String getRequestGetById(Integer id) {
        return "select * from arbitre where id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les arbitres dans la base de données
     * @return la requête pour récupérer tous les arbitres dans la base de données
     * @see DAOSimplified#getRequestGetAll()
     */
    @Override
    public String getRequestGetAll() {
        return "select * from arbitre";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'un arbitre via son nom dans la base de données
     * @param nom nom de l'arbitre à récupérer
     * @return la requête pour récupérer l'arbitre dans la base de données
     */
    public String getRequestByNom(String nom) {
    	return "SELECT * FROM Arbitre WHERE nom LIKE '%"+nom+"%'";
    }

    /**
     * Méthode permettant de supprimer un arbitre dans la base de données
     * @param id identifiant de l'objet à supprimer
     * @return true si la suppression a été effectuée, false sinon
     * @throws SQLException si une erreur survient lors de la suppression
     * @see DAOSimplified#delete(Object...)
     */
    @Override
    public boolean delete(Integer... id) throws SQLException {
        	boolean b = this.db.executeStatement((Statement statement) -> {
                try {
                    statement.executeUpdate(getRequestDeleteInSaison(id[0]));
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            });
            boolean b2 = this.db.executeStatement((Statement statement) -> {
                try {
                    statement.executeUpdate(getRequestDeleteInTournoi(id[0]));
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            });
            boolean b3 = this.db.executeStatement((Statement statement) -> {
                try {
                    statement.executeUpdate(getRequestSetNullInMatch(id[0]));
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            });
        	return super.delete(id) && b && b2 && b3;
    }

    /**
     * Méthode permettant de récupérer un ou des arbitres dans la base de données via leur nom
     * @return la liste des arbitres récupérés
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public List<Arbitre> getByNom(String nom) throws SQLException{
    	 ArrayList<Arbitre> list = new ArrayList<>();
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
     * Transforme un ResultSet en arbitre
     * @param resultSet ResultSet à transformer
     * @return l'arbitre créé
     * @see DAOSimplified#resultSetToT(ResultSet)
     */
    @Override
    public Arbitre resultSetToT(ResultSet resultSet) {
        try {
            return new Arbitre(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getString("image"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération du dernier identifiant de la table arbitre
     * @return la requête pour récupérer le dernier identifiant de la table arbitre
     * @see DAOSimplified#getRequestGetLastId()
     */
	@Override
	public String getRequestGetLastId() {
        return "SELECT MAX(id) FROM arbitre";
	}
}
