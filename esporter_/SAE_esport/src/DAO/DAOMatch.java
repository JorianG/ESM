package DAO;

import Modele.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe abstraite permettant de définir les méthodes de base d'un DAO
 */
public class DAOMatch extends DAOSimplified<Match, Integer> {

    /**
     * Instance de la classe
     */
    public static DAOMatch instance;

    /**
     * Constructeur de la classe
     * @throws SQLException exception SQL si la connexion à la base de données échoue
     */
    private DAOMatch() throws SQLException {
    }

    /**
     * Methode permettant de récupérer l'instance de la classe (patron singleton)
     * @return l'instance de la classe
     */
    public static synchronized DAOMatch getDAO() {
    	if (instance == null) {
            try {
                instance = new DAOMatch();
            } catch (SQLException e) {
                System.err.println("Failed to create DAO Match");
            }
        }
        return instance;
    }

    /**
     * Methode renvoyant la requête nécessaire à l'ajout d'un match dans la base de données
     * @param value match à ajouter
     * @return la requête pour ajouter le match dans la base de données
     * @see DAOSimplified#getRequestAdd(Object)
     */
    @Override
    public String getRequestAdd(Match value) {
        return "INSERT INTO MATCH_LOL (date_debut_match, date_fin_match, id_equipe1, id_equipe2, score1, score2, id_arbitre, id_tournoi, type_match) values" +
                "(" + (value.getDateDebutMatch() != null ? "'" + value.getDateDebutMatch().formatSQL() + "', " : "NULL, " )+
                (value.getDateFinMatch() != null ? "'" + value.getDateFinMatch().formatSQL() + "', " : "NULL, ")+
                value.getEquipe1().getId() + ", " +
                value.getEquipe2().getId() + ", " +
                value.getScore(value.getEquipe1()).getValeur() + ", " +
                value.getScore(value.getEquipe2()).getValeur() + ", " +
                value.getArbitre().getId() + ", " +
                value.getTournoi().getId() + ", " +
                "'" + value.getType().name() + "')";

    }

    /**
     * Methode renvoyant la requête nécessaire à la suppression d'un Match dans la base de données
     * @param id identifiant du match à supprimer
     * @return la requête pour supprimer le match dans la base de données
     * @see DAOSimplified#getRequestDelete(Object)
     */
    @Override
    public String getRequestDelete(Integer id) {
        return "delete from match_lol where id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la mise à jour d'un match dans la base de données
     * @param value match à mettre à jour
     * @return la requête pour mettre à jour le match dans la base de données
     * @see DAOSimplified#getRequestUpdate(Object)
     */
    @Override
    public String getRequestUpdate(Match value) {
        return "UPDATE match_lol SET " +
                "date_debut_match = " + (value.getDateDebutMatch() != null ? "'" + value.getDateDebutMatch().formatSQL() + "', " : "NULL, ") +
                "date_fin_match = "+ (value.getDateFinMatch() != null ? "'" + value.getDateFinMatch().formatSQL() + "', " : "NULL, ")+
                "id_equipe1 = " + value.getEquipe1().getId() + ", " +
                "id_equipe2 = " + value.getEquipe2().getId() + ", " +
                "score1 = " + value.getScore(value.getEquipe1()).getValeur() + ", " +
                "score2 = " + value.getScore(value.getEquipe2()).getValeur() + ", " +
                "id_arbitre = " + value.getArbitre().getId() + ", " +
                "id_tournoi = " + value.getTournoi().getId() + ", " +
                "type_match = '" + value.getType().name() + "' " +
                "WHERE id = " + value.getId();
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération d'un match dans la base de données par son identifiant
     * @param id identifiants du match à récupérer
     * @return la requête pour récupérer le match dans la base de données
     * @see DAOSimplified#getRequestGetById(Object)
     */
    @Override
    protected String getRequestGetById(Integer id) {
        return "select * from match_lol where id = " + id;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les matchs dans la base de données
     * @return la requête pour récupérer tous les matchs dans la base de données
     * @see DAOSimplified#getRequestGetAll()
     */
    @Override
    public String getRequestGetAll() {
        return "select * from match_lol";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de l'id du dernier match dans la base de données
     * @return la requête pour récupérer l'id du dernier match dans la base de données
     * @see DAOSimplified#getRequestGetLastId()
     */
    @Override
    public String getRequestGetLastId() {
        return "SELECT MAX(id) FROM match_lol";
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les matchs appartenant à un tournoi dans la base de données
     * @param idTournoi identifiant du tournoi
     * @return la requête pour récupérer tous les matchs appartenant à un tournoi dans la base de données
     */
    public String getRequestGetByTournoi(int idTournoi) {
        return "select * from match_lol where id_tournoi = " + idTournoi;
    }

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les matchs ayant lieu à une date donnée dans la base de données
     * @param date date des matchs à récupérer
     * @return la requête pour récupérer tous les matchs ayant lieu à une date donnée dans la base de données
     */
    public String getRequestGetByDate(String date) {
        return "select * from match_lol where DATE(date_debut_match) = '" + date + "'";
    }

    /**
     * Methode renvoyant les matchs d'une équipe donnée dans un tournoi donnée dans la base de données
     * @param idEquipe identifiant de l'équipe
     * @param idTournoi identifiant du tournoi
     * @return la liste des matchs d'une équipe donnée dans un tournoi donnée dans la base de données
     * @throws SQLException exception SQL si la récupération des matchs échoue
     */
	public int getNbMatchByEquipeAndTournoi(int idEquipe, int idTournoi) throws SQLException {
		ResultSet resultSet = this.db.executeStatement((statement) -> {
			try {
				return statement.executeQuery("select count(*) nb from match_lol "
				+ "where id_tournoi = " + idTournoi + 
				" and (id_equipe1 = " + idEquipe + " or id_equipe2 = " + idEquipe + ")"
				+ " and type_match = '" + TypeMatch.POOL + "'");
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		});
		
		if (resultSet!= null) {
			while (resultSet.next()) {
				return resultSet.getInt("nb");
            }
		}
		return 0;
	}

    /**
     * Methode vérifiant si un match existe entre deux équipes données dans un tournoi donné dans la base de données
     * @param idEquipe1 identifiant de la première équipe
     * @param idEquipe2 identifiant de la deuxième équipe
     * @param idTournoi identifiant du tournoi
     * @return vrai si un match existe entre deux équipes dans un tournoi donné dans la base de données, faux sinon
     * @throws SQLException exception SQL si la récupération des matchs échoue
     */
	public boolean isMatchBetweenEquipeAndTournoi(int idEquipe1, int idEquipe2, int idTournoi) throws SQLException {
		ResultSet resultSet = this.db.executeStatement((statement) -> {
			try {
				return statement.executeQuery("select count(*) from match_lol where id_tournoi = " + idTournoi
						+ " and ((id_equipe1 = " + idEquipe1 + " and id_equipe2 = " + idEquipe2 + ") or (id_equipe1 = "
						+ idEquipe2 + " and id_equipe2 = " + idEquipe1 + "))");
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		});
		
		if (resultSet != null) {
			while (resultSet.next()) {
				return resultSet.getInt(1) > 0;
			}
		}
		return false;
	}

    /**
     * Methode renvoyant la requête nécessaire à la récupération de tous les matchs auxquel un joueur à participer dans la base de données
     * @param j joueur
     * @return la requête pour récupérer tous les matchs auxquel un joueur à participer dans la base de données
     */
    public String getRequestGetByJoueur(Joueur j) {
        	return "select * from match_lol where id_equipe1 = " + j.getEquipe().getId() + " or id_equipe2 = " + j.getEquipe().getId();
    }

    /**
     * Methode permettant d'ajouter un match dans la base de données
     * @param value objet à ajouter
     * @return true si le match a été ajouté, false sinon
     * @throws SQLException si une erreur survient lors de l'ajout
     * @see DAOSimplified#add(Object)
     */
    @Override
    public boolean add(Match value) throws SQLException {
        if (!verif(value)) {
            System.err.println("Date Match invalide");
            return false;
        }
        return this.db.executeStatement((Statement statement) -> {
            try {
                System.out.println(getRequestAdd(value));
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
     * Methode permettant de mettre à jour un match dans la base de données
     * @param value objet à mettre à jour
     * @return true si le match a été mis à jour, false sinon
     * @throws SQLException si une erreur survient lors de la mise à jour
     * @see DAOSimplified#update(Object)
     */
    @Override
    public boolean update(Match value) throws SQLException {
        if (!verif(value)) {
            System.err.println("Date Match invalide");
            return false;
        }
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
     * Methode permettant de récupérer tous les matchs d'un tournoi dans la base de données
     * @param tournoi tournoi
     * @return la liste des matchs d'un tournoi dans la base de données
     * @throws SQLException exception SQL si la récupération des matchs échoue
     */
    public List<Match> getByTournoi(Tournoi tournoi) throws SQLException {
        ArrayList<Match> list = new ArrayList<>();
        ResultSet resultSet = this.db.executeStatement((statement) -> {
            try {
                return statement.executeQuery(getRequestGetByTournoi(tournoi.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (resultSet!= null) {
            while (resultSet.next()) {
                list.add(resultSetToT(resultSet, tournoi));
            }
        }

        return list;
    }

    /**
     * Methode permettant de récupérer tous les matchs ayant lieu à une date donnée dans la base de données
     * @param date date des matchs à récupérer
     * @return la liste des matchs ayant lieu à une date donnée dans la base de données
     * @throws SQLException exception SQL si la récupération des matchs échoue
     */
    public List<Match> getByDate(String date) throws SQLException {
        ArrayList<Match> list = new ArrayList<>();
        ResultSet resultSet = this.db.executeStatement((statement) -> {
            try {
                return statement.executeQuery(getRequestGetByDate(date));
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
     * Methode permettant de récupérer tous les matchs auxquel un joueur à participer dans la base de données
     * @param j joueur
     * @return la liste des matchs auxquel un joueur à participer dans la base de données
     * @throws SQLException exception SQL si la récupération des matchs échoue
     */
    public List<Match> getByJoueur(Joueur j) throws SQLException {
        ArrayList<Match> list = new ArrayList<>();
        ResultSet resultSet = this.db.executeStatement((statement) -> {
            try {
                return statement.executeQuery(getRequestGetByJoueur(j));
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
     * Methode permettant de transformer un ResultSet en Match
     * @param resultSet ResultSet à transformer
     * @return le match créé à partir du ResultSet
     * @see DAOSimplified#resultSetToT(ResultSet)
     */
    @Override
    public Match resultSetToT(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            Equipe equipe1 = Equipe.getById(resultSet.getInt("id_equipe1"));
            Equipe equipe2 = Equipe.getById(resultSet.getInt("id_equipe2"));
            Arbitre arbitre = Arbitre.getById(resultSet.getInt("id_arbitre"));
            Tournoi tournoi = Tournoi.getById(resultSet.getInt("id_tournoi"));
            return new Match(resultSet.getInt("id"),
                    resultSet.getTimestamp("date_debut_match") == null ? null : new CustomDateTime(resultSet.getTimestamp("date_debut_match").toLocalDateTime()),
                    resultSet.getTimestamp("date_fin_match") == null ? null : new CustomDateTime(resultSet.getTimestamp("date_fin_match").toLocalDateTime()),
                    equipe1,
                    equipe2,
                    Resultat.get(resultSet.getInt("score1")),
                    Resultat.get(resultSet.getInt("score2")),
                    arbitre,
                    tournoi,
                    TypeMatch.valueOf(resultSet.getString("type_match"))
            );
        } catch (Exception e) {
            System.err.println("Failed to convert resultSet into Match");
            return null;
        }
    }

    /**
     * Methode permettant de transformer un ResultSet en Match
     * @param resultSet ResultSet à transformer
     * @param tournoi tournoi du match
     * @return le match créé à partir du ResultSet
     */
    public Match resultSetToT(ResultSet resultSet, Tournoi tournoi) {
        if (resultSet == null) return null;
        try {
            Equipe equipe1 = Equipe.getById(resultSet.getInt("id_equipe1"));
            Equipe equipe2 = Equipe.getById(resultSet.getInt("id_equipe2"));
            Arbitre arbitre = Arbitre.getById(resultSet.getInt("id_arbitre"));
            return new Match(resultSet.getInt("id"),
                    resultSet.getTimestamp("date_debut_match") == null ? null : new CustomDateTime(resultSet.getTimestamp("date_debut_match").toLocalDateTime()),
                    resultSet.getTimestamp("date_fin_match") == null ? null : new CustomDateTime(resultSet.getTimestamp("date_fin_match").toLocalDateTime()),
                    equipe1,
                    equipe2,
                    Resultat.get(resultSet.getInt("score1")),
                    Resultat.get(resultSet.getInt("score2")),
                    arbitre,
                    tournoi,
                    TypeMatch.valueOf(resultSet.getString("type_match"))
            );
        } catch (Exception e) {
            System.err.println("Failed to convert resultSet into Match");
            return null;
        }
    }

    /**
     * Methode permettant de vérifier si un match est sur un seul jour
     * @param m match à vérifier
     * @return vrai si le match est sur un seul jour, faux sinon
     */
    private boolean isOnOneDay(Match m) {
        if (m.getDateFinMatch() == null) return true;
        return m.getDateDebutMatch().getDate().equals(m.getDateFinMatch().getDate());
    }

    /**
     * Methode permettant de vérifier si les dates d'un match sont valides (date de début avant date de fin)
     * @param m match à vérifier
     * @return vrai si les dates d'un match sont valides, faux sinon
     */
    public boolean crenneauValide(Match m) {
        if (m.getDateFinMatch() != null) {
            return m.getDateFinMatch().isAfter(m.getDateDebutMatch());
        }
        return true;
    }

//    /**
//     * Methode permettant de vérifier si un match est superposé à un autre match
//     * @param m match à vérifier
//     * @return vrai si le match est superposé à un autre match, faux sinon
//     */
//    private boolean isCrenneauSupperpose(Match m) {
//        List<Match> matchList = Match.getByDate(m.getDateDebutMatch().getDateSQL());
//        for (Match match : matchList) {
//            if (m.getDateFinMatch() != null && !m.equals(match)) {
//                if (m.getDateDebutMatch().isBetween(match.getDateDebutMatch(), match.getDateFinMatch())
//                        || m.getDateFinMatch().isBetween(match.getDateDebutMatch(), match.getDateFinMatch())) {
//                    return true;
//                }
//            } else {
//                if (m.getDateDebutMatch().isBetween(match.getDateDebutMatch(), match.getDateDebutMatch())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    /**
     * Methode permettant de vérifier si un match est dans un tournoi
     * @param m match à vérifier
     * @return vrai si le match est dans un tournoi, faux sinon
     */
    private boolean isDuringTournoi(Match m) {
        return m.getDateDebutMatch().isBetween(m.getTournoi().getDateDebut(), m.getTournoi().getDateFin()) &&
        m.getDateFinMatch().isBetween(m.getTournoi().getDateDebut(), m.getTournoi().getDateFin());
    }

    /**
     * Methode permettant de vérifier si les deux équipes d'un match sont différentes
     * @param m match à vérifier
     * @return vrai si les deux équipes d'un match sont différentes, faux sinon
     */
    public boolean isNotSameEquipe(Match m) {
    	return m.getEquipe1() != m.getEquipe2();
    }

    /**
     * Methode permettant de vérifier si un arbitre est disponible pour un match
     * @param m match à vérifier
     * @return vrai si l'arbitre est disponible pour un match, faux sinon
     */
    public boolean isArbitreDispo(Match m) {
        for (Match match : Objects.requireNonNull(Match.getByDate(m.getDateDebutMatch().getDateSQL()))) {
            if (!match.equals(m) && match.getArbitre().equals(m.getArbitre()) && match.getDateDebutMatch().equals(m.getDateDebutMatch())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Methode permettant de vérifier si un match est valide
     * @param m match à vérifier
     * @return vrai si le match est valide, faux sinon
     */
    private boolean verif(Match m) { // rajouter potentiellement !isCrenneauSupperpose(m)
        return isOnOneDay(m) && crenneauValide(m) && isDuringTournoi(m) && isNotSameEquipe(m) && isArbitreDispo(m);
    }
}
