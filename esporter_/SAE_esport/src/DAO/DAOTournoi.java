package DAO;

import Modele.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Classe permettant de définir les méthodes spécifiques d'un DAO pour un Tournoi
 */
public class DAOTournoi extends DAOSimplified<Tournoi, Integer> {

    /**
     * Instance de la classe
     */
    private static DAOTournoi dao;

    /**
     * Constructeur de la classe
     * @throws SQLException exception SQL
     */
    private DAOTournoi() throws SQLException {
    }

    /**
     * Methode permettant de récupérer l'instance de la classe
     * @return instance de la classe
     */
    public static synchronized DAOTournoi getDAO() {
        if (dao == null) {
            try {
                dao = new DAOTournoi();
            } catch (SQLException e) {
                System.err.println("failed to create instance of DAOTournoi");
            }
        }
        return dao;
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'un Tournoi dans la base de données
     * @param value objet à ajouter
     * @return la requête pour ajouter un tournoi dans la base de données
     * @see DAOSimplified#getRequestAdd(Object)
     */
    @Override
    public String getRequestAdd(Tournoi value) {
        return "INSERT INTO TOURNOI (nom, date_debut, date_fin, img, login_arbitrage, mdp_arbitrage, niveau, id_saison, terminer) values" +
                "('" + value.getNom() + "', " +
                "DATE('" + Date.valueOf(value.getDateDebut()).toString() + "'), " +
                "DATE('" + Date.valueOf(value.getDateFin()).toString() + "'), " +
                "'" + value.getImage() + "', " +
                "'" + value.getLogin() + "', " +
                "'" + value.getPassword() + "', " +
                "'" + value.getNiveau().name() + "', " +
                (value.getSaison() != null ? value.getSaison().getAnnee() : "NULL") + ", " +
                value.isTerminer() + ")";
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'une équipe dans un tournoi dans la base de données
     * @param value tournoi
     * @param equipe équipe
     * @param points points
     * @return la requête pour ajouter une équipe dans un tournoi dans la base de données
     */
    public String getRequestAddEquipeInTournoi(Tournoi value, Equipe equipe, int points) {
        return "INSERT INTO PARTICIPER_TOURNOI (id_tournoi, id_equipe, points) values" +
                "(" + value.getId() + ", " +
                equipe.getId() + ", " +
                points + ")";
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'un arbitre dans un tournoi dans la base de données
     * @param value tournoi
     * @param arbitre arbitre
     * @return la requête pour ajouter un arbitre dans un tournoi dans la base de données
     */
    public String getRequestAddArbitreInTournoi(Tournoi value, Arbitre arbitre) {
        return "INSERT INTO ARBITRAGE_TOURNOI (id_tournoi, id_arbitre) values" +
                "(" + value.getId() + ", " +
                arbitre.getId() + ")";
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un Tournoi dans la base de données
     * @param id identifiant de l'objet à supprimer
     * @return la requête pour supprimer un tournoi dans la base de données
     * @see DAOSimplified#getRequestDelete(Object)
     */
    @Override
    public String getRequestDelete(Integer id) {
        return "DELETE FROM TOURNOI WHERE id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'une équipe dans un tournoi dans la base de données
     * @param id identifiant de l'objet à supprimer
     * @return la requête pour supprimer une équipe dans un tournoi dans la base de données
     */
    public String getRequestDeleteEquipeInTournoi(Integer id) {
        return "DELETE FROM PARTICIPER_TOURNOI WHERE id_tournoi = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un arbitre dans un tournoi dans la base de données
     * @param id identifiant de l'objet à supprimer
     * @return la requête pour supprimer un arbitre dans un tournoi dans la base de données
     */
    public String getRequestDeleteArbitreInTournoi(Integer id) {
        return "DELETE FROM ARBITRAGE_TOURNOI WHERE id_tournoi = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'un Tournoi dans la base de données
     * @param value objet à mettre à jour
     * @return la requête pour mettre à jour un tournoi dans la base de données
     * @see DAOSimplified#getRequestUpdate(Object)
     */
    @Override
    public String getRequestUpdate(Tournoi value) {
        return "UPDATE TOURNOI SET nom = '" + value.getNom() + "', " +
                "date_debut = DATE('" + value.getDateDebut() + "'), " +
                "date_fin = DATE('" + value.getDateFin() + "'), " +
                "img = '" + value.getImage() + "', " +
                "login_arbitrage = '" + value.getLogin() + "', " +
                "mdp_arbitrage = '" + value.getPassword() + "', " +
                "niveau = '" + value.getNiveau().name() + "', " +
                "id_saison = " + (value.getSaison() != null ? value.getSaison().getAnnee() : "NULL") + ", " +
                "terminer = " + value.isTerminer() + " " +
                "WHERE id = " + value.getId();
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'une équipe dans un tournoi dans la base de données
     * @param value tournoi
     * @param equipe équipe
     * @param points points
     * @return la requête pour mettre à jour une équipe dans un tournoi dans la base de données
     */
    private String getRequestUpdateEquipeInTournoi(Tournoi value, Equipe equipe, int points) {
        return "UPDATE PARTICIPER_TOURNOI SET points = " + points + " WHERE id_tournoi = " + value.getId() + " AND id_equipe = " + equipe.getId();
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'un Tournoi dans la base de données par son identifiant
     * @param id identifiants de l'objet à récupérer
     * @return la requête pour récupérer un tournoi dans la base de données
     * @see DAOSimplified#getRequestGetById(Object)
     */
    @Override
    protected String getRequestGetById(Integer id) {
        return "SELECT * FROM TOURNOI WHERE id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les Tournois dans la base de données
     * @return la requête pour récupérer tous les tournois dans la base de données
     * @see DAOSimplified#getRequestGetAll()
     */
    @Override
    public String getRequestGetAll() {
        return "SELECT * FROM TOURNOI";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération du dernier identifiant d'un Tournoi dans la base de données
     * @return la requête pour récupérer le dernier identifiant d'un tournoi dans la base de données
     * @see DAOSimplified#getRequestGetLastId()
     */
    @Override
    public String getRequestGetLastId() {
        return "SELECT MAX(id) FROM TOURNOI";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération des équipes d'un tournoi dans la base de données
     * @param id identifiant du tournoi
     * @return la requête pour récupérer les équipes d'un tournoi dans la base de données
     */
    private String getRequestGetEquipesForTournoi(Integer id) {
        return "SELECT * FROM PARTICIPER_TOURNOI WHERE id_tournoi = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération des arbitres d'un tournoi dans la base de données
     * @param id identifiant du tournoi
     * @return la requête pour récupérer les arbitres d'un tournoi dans la base de données
     */
    private String getRequestGetArbitresForTournoi(Integer id) {
        return "SELECT * FROM ARBITRAGE_TOURNOI WHERE id_tournoi = " + id;
    }

    /**
     * Getter de la requête permettant la récupération des tournois d'une saison
     * @param id identifiant de la saison recherché
     * @return requête de récupération des tournois d'une saison
     */
    private String getRequestGetBySaison(Integer id) {
        return "SELECT * FROM TOURNOI WHERE id_saison = " + id;
    }

    /**
     * Getter de la requête permettant la récupération des points d'une équipe dans un tournoi
     * @param t tournoi
     * @param e équipe
     * @return requête de récupération des points d'une équipe dans un tournoi
     */
    private String getRequestGetEquipePoints(Tournoi t, Equipe e) {
        return "SELECT points FROM PARTICIPER_TOURNOI WHERE id_tournoi = " + t.getId() + " AND id_equipe = " + e.getId();
    }

    /**
     * Getter de la requête permettant la récupération des tournois d'une équipe
     * @param e équipe
     * @return requête de récupération des tournois d'une équipe
     */
    public String getRequestGetTournoiByEquipe(Equipe e) {
    	return "SELECT * FROM PARTICIPER_TOURNOI WHERE id_equipe = " + e.getId();
    }

    /**
     * Getter de la requête permettant la récupération des tournois d'un arbitre
     * @param a arbitre
     * @return requête de récupération des tournois d'un arbitre
     */
    public String getRequestGetTournoiByArbitre(Arbitre a) {
    	return "SELECT * FROM ARBITRAGE_TOURNOI WHERE id_arbitre = " + a.getId();
    }

    /**
     * Getter de la requête permettant la récupération d'un tournoi par son login et son mot de passe
     * @param login login de l'arbitre
     * @param mdp mot de passe de l'arbitre
     * @return requête de récupération d'un tournoi par son login et son mot de passe
     */
    public String getRequestGetTournoiByIndentification(String login, String mdp) {
    	return "SELECT * FROM TOURNOI WHERE login_arbitrage = '" + login + "' AND mdp_arbitrage = '" + mdp + "'";
    }

    /**
     * Methode permettant de transformer un ResultSet en Tournoi
     * @param resultSet ResultSet à transformer
     * @return Tournoi créé à partir du ResultSet
     * @see DAOSimplified#resultSetToT(ResultSet)
     */
    @Override
    public Tournoi resultSetToT(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            int id = resultSet.getInt("id");
            HashMap<Equipe, Integer> e = getEquipesForTournoi(id);
            Set<Arbitre> a = getArbitresForTournoi(id);
            Tournoi t = new Tournoi(id,
                    resultSet.getString("nom"),
                    resultSet.getDate("date_debut").toLocalDate(),
                    resultSet.getDate("date_fin").toLocalDate(),
                    resultSet.getString("img"),
                    NiveauTournoi.valueOf(resultSet.getString("niveau") ),
                    e,
                    a,
                    resultSet.getString("login_arbitrage"),
                    resultSet.getString("mdp_arbitrage"),
                    resultSet.getBoolean("terminer")
            );
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Methode permettant d'ajouter un tournoi dans la base de données
     * @param value objet à ajouter
     * @return true si l'ajout a été effectué, false sinon
     * @throws SQLException si une erreur survient lors de l'ajout
     * @see DAOSimplified#add(Object)
     */
    @Override
    public boolean add(Tournoi value) throws SQLException {
        if (!verif(value)) {
            throw new SQLException("Date Tournoi invalide");
        }
        super.add(value);
        value.setId();
        return
                this.db.executeStatement((statement) -> {
                    try {
                        for (Equipe e: value.getListEquipes().keySet()) {
                            statement.execute(getRequestAddEquipeInTournoi(value, e, value.getListEquipes().get(e)));
                        }
                        System.out.println("add equipes in tournoi");
                        return true;
                    } catch (SQLException e) {
                        System.err.println("failed to add equipes in tournoi");
                        e.printStackTrace();
                        return false;
                    }
                }) &&
                this.db.executeStatement((statement) -> {
                    try {
                        for (Arbitre a: value.getPoolArbitres()) {
                            statement.execute(getRequestAddArbitreInTournoi(value, a));
                        }
                        System.out.println("add arbitres in tournoi");
                        return true;
                    } catch (SQLException e) {
                        System.err.println("failed to add arbitres in tournoi");
                        return false;
                    }
                });


    }

    /**
     * Methode permettant de supprimer un tournoi dans la base de données
     * @param ids identifiant de l'objet à supprimer
     * @return true si la suppression a été effectuée, false sinon
     * @throws SQLException si une erreur survient lors de la suppression
     * @see DAOSimplified#delete(Object...)
     */
    @Override
    public boolean delete(Integer... ids) throws SQLException {
        return this.db.executeStatement((statement) -> {
                    try {
                        statement.execute(getRequestDeleteEquipeInTournoi(ids[0]));
                        return true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }) &&
                this.db.executeStatement((statement) -> {
                    try {
                        statement.execute(getRequestDeleteArbitreInTournoi(ids[0]));
                        return true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }) && super.delete(ids);
    }

    /**
     * Methode permettant de mettre à jour un tournoi dans la base de données
     * @param value objet à mettre à jour
     * @return true si la mise à jour a été effectuée, false sinon
     * @throws SQLException si une erreur survient lors de la mise à jour
     * @see DAOSimplified#update(Object)
     */
    @Override
    public boolean update(Tournoi value) throws SQLException {
        if (!verif(value)) throw new SQLException("Date Tournoi invalide");
        Boolean update = this.db.executeStatement((Statement statement) -> {
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
        for (Equipe e: value.getListEquipes().keySet()) {
            update = update && this.db.executeStatement((Statement statement) -> {
                try {
                    statement.execute(getRequestUpdateEquipeInTournoi(value, e, value.getListEquipes().get(e)));
                    System.out.println("update" + value.getClass().toString());
                    return true;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.err.println("Failed to update" + value.getClass().toString());
                }
                return false;
            });
        }
        return update;

    }

    /**
     * Methode permettant de récupérer les équipes d'un tournoi dans la base de données par son identifiant
     * @param id identifiants de l'objet à récupérer
     * @return la liste des équipes récupérées
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public HashMap<Equipe, Integer> getEquipesForTournoi(Integer id) throws SQLException {
        return this.db.executeStatement((statement) -> {
            try {
                ResultSet resultSet = statement.executeQuery(getRequestGetEquipesForTournoi(id));
                HashMap<Equipe, Integer> equipes = new HashMap<>();
                while (resultSet.next()) {
                    equipes.put(Equipe.getById(resultSet.getInt("id_equipe")),
                            resultSet.getInt("points"));
                }
                return equipes;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Methode permettant de récupérer les arbitres d'un tournoi dans la base de données par son identifiant
     * @param id identifiants de l'objet à récupérer
     * @return la liste des arbitres récupérés
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public Set<Arbitre> getArbitresForTournoi(Integer id) throws SQLException {
        return this.db.executeStatement((statement) -> {
            try {
                ResultSet resultSet = statement.executeQuery(getRequestGetArbitresForTournoi(id));
                HashSet<Arbitre> arbitres = new HashSet<>();
                while (resultSet.next()) {
                    arbitres.add(Arbitre.getById(resultSet.getInt("id_arbitre")));
                }
                return arbitres;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Methode permettant la récupération des tournois d'une saison
     * @param id identifiant de la saison recherché
     * @return requête de récupération des tournois d'une saison
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public HashSet<Tournoi> getBySaison(Integer id) throws SQLException {
        return this.db.executeStatement(statement -> {
            try {
                ResultSet resultSet = statement.executeQuery(getRequestGetBySaison(id));
                HashSet<Tournoi> tournois = new HashSet<>();
                while (resultSet.next()) {
                    tournois.add(resultSetToT(resultSet));
                }
                return tournois;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Methode permettant la récupération des points d'une équipe dans un tournoi
     * @param t tournoi
     * @param e équipe
     * @return requête de récupération des points d'une équipe dans un tournoi
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public int getEquipePoints(Tournoi t, Equipe e) throws SQLException {
        return this.db.executeStatement(statement -> {
            try {
                ResultSet resultSet = statement.executeQuery(getRequestGetEquipePoints(t, e));
                if (resultSet.next()) {
                    return resultSet.getInt("points");
                }
                return -1;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return -1;
            }
        });
    }

    /**
     * Methode permettant la récupération des tournois d'une équipe
     * @param e équipe
     * @return requête de récupération des tournois d'une équipe
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public List<Tournoi> getTournoiByEquipe(Equipe e) throws SQLException {
    	return this.db.executeStatement(statement -> {
    		try {
    			ResultSet resultSet = statement.executeQuery(getRequestGetTournoiByEquipe(e));
    			List<Tournoi> tournois = new ArrayList<>();
    			while (resultSet.next()) {
    				tournois.add(getById(resultSet.getInt("id_tournoi")));
    			}
    			return tournois;
    		} catch (SQLException ex) {
    			ex.printStackTrace();
    			return null;
    		}
    	});
    }

    /**
     * Methode permettant la récupération des tournois d'un arbitre
     * @param a arbitre
     * @return requête de récupération des tournois d'un arbitre
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public List<Tournoi> getTournoiByArbitre(Arbitre a) throws SQLException {
    	return this.db.executeStatement(statement -> {
    		try {
    			ResultSet resultSet = statement.executeQuery(getRequestGetTournoiByArbitre(a));
    			List<Tournoi> tournois = new ArrayList<>();
    			while (resultSet.next()) {
    				tournois.add(getById(resultSet.getInt("id_tournoi")));
    			}
    			return tournois;
    		} catch (SQLException ex) {
    			ex.printStackTrace();
    			return null;
    		}
    	});
    }

    /**
     * Methode permettant la récupération d'un tournoi par son login et son mot de passe
     * @param login login de l'arbitre
     * @param mdp mot de passe de l'arbitre
     * @return requête de récupération d'un tournoi par son login et son mot de passe
     * @throws SQLException si une erreur survient lors de la récupération
     */
    public Tournoi getTournoiByIndentification(String login, String mdp) throws SQLException {
        	return this.db.executeStatement(statement -> {
        		try {
        			ResultSet resultSet = statement.executeQuery(getRequestGetTournoiByIndentification(login, mdp));
        			if (resultSet.next()) {
        				return getById(resultSet.getInt("id"));
        			}
        			return null;
        		} catch (SQLException ex) {
                    ex.printStackTrace();
        			return null;
        		}
        	});
    }

    /**
     * Methode permettant de vérifier si un tournoi est valide
     * @param t tournoi
     * @return true si la date de début est avant la date de fin
     */
    private boolean dureeTournoi(Tournoi t) {
        return t.getDateFin().isAfter(t.getDateDebut());
    }

    /**
     * Methode permettant de vérifier que les dates d'un tournoi ne se supperposent pas avec un autre tournoi
     * @param t tournoi
     * @return true si les dates se supperposent, false sinon
     */
    private boolean dateSupperposee(Tournoi t) {
        List<Tournoi> listTournoi = new ArrayList<>(Objects.requireNonNull(Tournoi.getAll()));
        for (Tournoi t2 : listTournoi) {
            CustomDateTime dateDebutT = new CustomDateTime(t.getDateDebut().atStartOfDay());
            CustomDateTime dateFinT = new CustomDateTime(t.getDateFin().atTime(23, 59, 59));
            CustomDateTime dateDebutT2 = new CustomDateTime(t2.getDateDebut().atStartOfDay());
            CustomDateTime dateFinT2 = new CustomDateTime(t2.getDateFin().atTime(23, 59, 59));
            if (t.getId() != t2.getId()
                    && (dateDebutT.isBetween(dateDebutT2, dateFinT2)
                    || dateFinT.isBetween(dateDebutT2, dateFinT2)
                    || dateDebutT2.isBetween(dateDebutT, dateFinT)
                    || dateFinT2.isBetween(dateDebutT, dateFinT))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Methode permettant de vérifier que le nombre d'équipe est compris entre 4 et 8
     * @param t tournoi
     * @return true si le nombre d'équipe est compris entre 4 et 8, false sinon
     */
    private boolean nombreEquipe(Tournoi t) {
        return t.getListEquipes().size() >= 4 && t.getListEquipes().size() <= 8;
    }

    /**
     * Methode permettant de vérifier que les attributs non null d'un tournoi sont non null
     * @param t tournoi
     * @return true si les attributs non null d'un tournoi sont non null, false sinon
     */
    private boolean compositeNotNull(Tournoi t) {
        return t.getListEquipes() != null && t.getPoolArbitres() != null;
    }

    /**
     * Methode permettant de vérifier qu'un tournoi est valide
     * @param t tournoi
     * @return true si le tournoi est valide, false sinon
     */
    private boolean verif(Tournoi t) {
        return dureeTournoi(t) && !dateSupperposee(t) && nombreEquipe(t) && compositeNotNull(t);
    }
}
