package DAO;

import Modele.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe permettant de définir les méthodes spécifiques d'un DAO pour une Saison
 */
public class DAOSaison extends DAOSimplified<Saison, Integer> {

    /**
     * Instance de la classe (patron singleton)
     */
	private static DAOSaison instance;

    /**
     * Constructeur de la classe
     * @throws SQLException exception SQL si la connexion à la base de données échoue
     */
    private DAOSaison() throws SQLException {
    }

    /**
     * Methode permettant de récupérer l'instance de la classe (patron singleton)
     * @return l'instance de la classe
     */
    public static synchronized DAOSaison getDAO() {
    	if (instance == null) {
    		try {
				instance = new DAOSaison();
			} catch (SQLException e) {
				System.err.println("Erreur instance DAOSaison");
			}
    	}
    	return instance;
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'une Saison dans la base de données
     * @param value Saison à ajouter
     * @return la requête pour ajouter la Saison dans la base de données
     * @see DAOSimplified#getRequestAdd(Object)
     */
    @Override
    public String getRequestAdd(Saison value) {
        return "INSERT INTO SAISON (ANNEE, NOM, LOGO) VALUES (" + value.getAnnee() + ", '"
                + value.getNom() + "', '"
                + value.getLogo() + "')";
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'une Saison dans la base de données
     * @param id identifiant de la Saison à supprimer
     * @return la requête pour supprimer la Saison dans la base de données
     * @see DAOSimplified#getRequestDelete(Object)
     */
    @Override
    public String getRequestDelete(Integer id) {
        return "DELETE FROM SAISON WHERE ANNEE = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'une Saison dans la base de données
     * @param value Saison à mettre à jour
     * @return la requête pour mettre à jour la Saison dans la base de données
     * @see DAOSimplified#getRequestUpdate(Object)
     */
    @Override
    public String getRequestUpdate(Saison value) {
        return "UPDATE SAISON SET NOM = '" + value.getNom() + "', "
                + "LOGO = '" + value.getLogo() + "', "
                + "ARCHIVED = " + value.isArchived() + " WHERE ANNEE = " + value.getAnnee();
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'une Saison dans la base de données par son identifiant
     * @param id identifiants de la Saison à récupérer
     * @return la requête pour récupérer la Saison dans la base de données
     * @see DAOSimplified#getRequestGetById(Object)
     */
    @Override
    protected String getRequestGetById(Integer id) {
        return "SELECT * FROM SAISON WHERE ANNEE = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de toutes les Saisons dans la base de données
     * @return la requête pour récupérer toutes les Saisons dans la base de données
     * @see DAOSimplified#getRequestGetAll()
     */
    @Override
    public String getRequestGetAll() {
        return "SELECT * FROM SAISON";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les arbitres d'une Saison dans la base de données
     * @param id identifiants de la Saison à récupérer
     * @return la requête pour récupérer les arbitres de la Saison dans la base de données
     */
    public String getRequestGetArbitreInSaison(Integer id) {
    	return "SELECT * FROM ARBITRE_SAISON WHERE ANNEE = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'un arbitre dans une Saison dans la base de données
     * @param value Saison à ajouter
     * @param arbitre arbitre à ajouter
     * @return la requête pour ajouter l'arbitre dans la Saison dans la base de données
     */
    public String getRequestAddArbitreInSaison(Saison value, Arbitre arbitre) {
    	return "INSERT INTO ARBITRE_SAISON (ANNEE, ID_ARBITRE) values" +
                "(" + value.getAnnee() + ", " +
                arbitre.getId() + ")";
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un arbitre dans une Saison dans la base de données
     * @param id identifiant de la Saison à supprimer
     * @return la requête pour supprimer l'arbitre dans la Saison dans la base de données
     */
    public String getRequestDeleteArbitreInSaison(Integer id) {
        return "DELETE FROM ARBITRE_SAISON WHERE ANNEE = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de toutes les équipes d'une Saison dans la base de données
     * @param id identifiants de la Saison à récupérer
     * @return la requête pour récupérer les équipes de la Saison dans la base de données
     */
    private String getRequestGetEquipeBySaison(Integer id) {
        return "SELECT * FROM PARTICIPER_SAISON WHERE ANNEE = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'une équipe dans une Saison dans la base de données
     * @param value Saison à ajouter
     * @param equipe équipe à ajouter
     * @param points points de l'équipe (0 par défaut)
     * @return la requête pour ajouter l'équipe dans la Saison dans la base de données
     */
    private String getRequestAddEquipeInSaison(Saison value, Equipe equipe, int points) {
        return "INSERT INTO PARTICIPER_SAISON (ANNEE, ID_EQUIPE, POINTS) values" +
                "(" + value.getAnnee() + ", " +
                equipe.getId() + ", " +
                points + ")";
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'une équipe dans une Saison dans la base de données
     * @param id identifiant de la Saison à supprimer
     * @return la requête pour supprimer l'équipe dans la Saison dans la base de données
     */
    private String getRequestDeleteEquipeInSaison(Integer id) {
        return "DELETE FROM PARTICIPER_SAISON WHERE ANNEE = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'une équipe dans une Saison dans la base de données
     * @param value Saison à mettre à jour
     * @param equipe équipe à mettre à jour
     * @param points points de l'équipe
     * @return la requête pour mettre à jour l'équipe dans la Saison dans la base de données
     */
    private String getRequestUpdateEquipeInSaison(Saison value, Equipe equipe, int points) {
        return "UPDATE PARTICIPER_SAISON SET POINTS = " + points + " WHERE ANNEE = " + value.getAnnee() + " AND ID_EQUIPE = " + equipe.getId();
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération des points d'une équipe dans une Saison dans la base de données
     * @param saison Saion à récupérer
     * @param equipe équipe à récupérer
     * @return la requête pour récupérer les points de l'équipe dans la Saison dans la base de données
     */
    private String getRequestGetPointsEquipe(Saison saison, Equipe equipe) {
        return "SELECT POINTS FROM PARTICIPER_SAISON WHERE ANNEE = " + saison.getAnnee() + " AND ID_EQUIPE = " + equipe.getId();
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération du dernier identifiant d'une Saison dans la base de données
     * @return la requête pour récupérer le dernier identifiant d'une Saison dans la base de données
     * @see DAOSimplified#getRequestGetLastId()
     */
    @Override
    public String getRequestGetLastId() {
        return "SELECT MAX(id) FROM SAISON";
    }

    /**
     * Methode permettant de transformer un ResultSet en une Saison
     * @param resultSet ResultSet à transformer
     * @return la Saison créée
     * @see DAOSimplified#resultSetToT(ResultSet)
     */
    @Override
    public Saison resultSetToT(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            int annee = resultSet.getInt("ANNEE");
            return new Saison(annee,
                    resultSet.getString("NOM"),
                    resultSet.getString("LOGO"),
                    getArbitreInSaison(annee),
                    getEquipeBySaison(annee),
                    resultSet.getBoolean("ARCHIVED"),
                    false
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ajoute un objet dans la base de données
     * @param value objet à ajouter
     * @return true si l'objet a bien été ajouté, false sinon
     * @throws SQLException si une erreur survient lors de l'ajout
     * @see DAOSimplified#add(Object)
     */
    @Override
    public boolean add(Saison value) throws SQLException {
        if (!verif(value)) return false;
        boolean b = super.add(value);
        AtomicBoolean b2 = new AtomicBoolean(false);
        if (b) {
            for (Equipe e : value.getClassement().keySet()) {
                db.executeStatement(statement -> {
                    try {
                        statement.execute(getRequestAddEquipeInSaison(value, e, value.getClassement().get(e)));
                        return true;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        b2.set(false);
                        return false;
                    }
                });
            }
        }
        AtomicBoolean b3 = new AtomicBoolean(false);
        if (b) {
            for (Arbitre a : value.getPoolArbitres()) {
                db.executeStatement(statement -> {
                    try {
                        statement.execute(getRequestAddArbitreInSaison(value, a));
                        return true;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        b3.set(false);
                        return false;
                    }
                });
            }
        }
        return b && b2.get() & b3.get();
    }

    /**
     * Met à jour un objet de la base de données
     * @param value objet à mettre à jour
     * @return true si l'objet a bien été mis à jour, false sinon
     * @throws SQLException si une erreur survient lors de la mise à jour
     * @see DAOSimplified#update(Object)
     */
    @Override
    public boolean update(Saison value) throws SQLException {
        if (!verif(value)) return false;
        boolean b = super.update(value);
        AtomicBoolean b2 = new AtomicBoolean(true);;
        if (b) {
            for (Equipe e : value.getClassement().keySet()) {
                db.executeStatement(statement -> {
                    try {
                        statement.execute(getRequestUpdateEquipeInSaison(value, e, value.getClassement().get(e)));
                        return true;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        b2.set(false);
                        return false;
                    }
                });
            }
        }
        return b && b2.get();
    }

    /**
     * Supprime un objet de la base de données
     * @param id identifiant de l'objet à supprimer
     * @return true si l'objet a bien été supprimé, false sinon
     * @throws SQLException si une erreur survient lors de la suppression
     * @see DAOSimplified#delete(Object...)
     */
    @Override
    public boolean delete(Integer... id) throws SQLException {
        Saison s = getById(id[0]);
        s.setArchived(true);
        boolean b = false;
        for (Tournoi tournoi : Tournoi.getBySaison(id[0])) {
            for (Match m : Objects.requireNonNull(Match.getByTournoi(tournoi))) {
                b = Match.delete(m.getId());
                if (!b) break;
            }
        }

        return b && db.executeStatement(statement -> {
            try {
                statement.execute(getRequestDeleteArbitreInSaison(id[0]));
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }) && super.update(s);
    }

    public boolean destroy(Integer... id) throws SQLException {
        for (Tournoi t : Tournoi.getBySaison(id[0])) {
            Tournoi.delete(t.getId());
        }

        return db.executeStatement(statement -> {
            try {
                statement.execute(getRequestDeleteEquipeInSaison(id[0]));
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }) && super.delete(id[0]);
    }

    /**
     * Récupère un objet de la base de données
     * @param id identifiant de l'objet à récupérer
     * @return l'objet récupéré
     * @throws SQLException si une erreur survient lors de la récupération
     * @see DAOSimplified#getById(Object...)
     */
    public HashMap<Equipe, Integer> getEquipeBySaison(Integer id) throws SQLException {
    	return db.executeStatement(statement -> {
            try {
                ResultSet resultSet = statement.executeQuery(getRequestGetEquipeBySaison(id));
                HashMap<Equipe, Integer> equipes = new HashMap<>();
                while (resultSet.next()) {
                    equipes.put(Equipe.getById(resultSet.getInt("id_equipe")), resultSet.getInt("points"));
                }
                return equipes;
            } catch (SQLException e) {
                System.err.println("Failed to get Equipe by Saison in getEquipeBySaison " + Saison.class);
                return new HashMap<>();
            }
        });
    }

    /**
     * Récupère les points d'une équipe dans une Saison dans la base de données
     * @param saison Saison à récupérer
     * @param equipe équipe à récupérer
     * @return les points de l'équipe dans la Saison dans la base de données
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public int getPointsEquipe(Saison saison, Equipe equipe) throws SQLException {
    	return db.executeStatement(statement -> {
            try {
                ResultSet resultSet = statement.executeQuery(getRequestGetPointsEquipe(saison, equipe));
                if (resultSet.next()) {
                    return resultSet.getInt("points");
                }
                return -1;
            } catch (SQLException e) {
                System.err.println("Failed to get Equipe by Saison in getEquipeBySaison " + Saison.class);
                return -1;
            }
        });
    }

    /**
     * Récupère les arbitres d'une Saison dans la base de données
     * @param id identifiant de la Saison à récupérer
     * @return les arbitres de la Saison dans la base de données
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public Set<Arbitre> getArbitreInSaison(Integer id) throws SQLException {
    	return db.executeStatement(statement -> {
            try {
                ResultSet resultSet = statement.executeQuery(getRequestGetArbitreInSaison(id));
                Set<Arbitre> arbitres = new HashSet<>();
                while (resultSet.next()) {
                    arbitres.add(Arbitre.getById(resultSet.getInt("id_arbitre")));
                }
                return arbitres;
            } catch (SQLException e) {
                System.err.println("Failed to get Arbitre by Saison in getArbitreBySaison " + Saison.class);
                return new HashSet<>();
            }
        });
    }

    /**
     * Methode vérifiant le nombre d'arbitres d'une Saison
     * @param saison une Saison
     * @return true si le nombre d'arbitres est supérieur ou égal à 10, false sinon
     */
    private boolean nombreArbitre(Saison saison) {
    	return saison.getPoolArbitres().size() >= 10;
    }

    /**
     * Methode vérifiant le nombre d'équipes d'une Saison
     * @param saison une Saison
     * @return true si le nombre d'équipes est supérieur ou égal à 4, false sinon
     */
    private boolean nombreEquipe(Saison saison) {
    	return saison.getClassement().size() >= 4;
    }

    /**
     * Methode vérifiant que les attributs de la Saison ne sont pas null
     * @param saison une Saison
     * @return true si les attributs de la Saison ne sont pas null, false sinon
     */
    private boolean compositeNotNull(Saison saison) {
    	return saison.getClassement() != null && saison.getPoolArbitres() != null;
    }

    /**
     * Methode vérifiant qu'une Saison est valide
     * @param saison une Saison
     * @return true si la Saison est valide, false sinon
     */
    private boolean verif(Saison saison) {
    	return compositeNotNull(saison) && nombreArbitre(saison) && nombreEquipe(saison);
    }
}
