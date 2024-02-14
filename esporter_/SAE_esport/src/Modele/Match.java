package Modele;

import DAO.DAOMatch;
import vue.Observable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La classe {@code Match} représente un match entre deux {@code Equipe}s
 * @author Nail Lamarti
 */
public class Match extends Observable {

	/**
	 * Tableau des clés des observateurs requis pour cette classe
	 */
	private static final String[] OBSERVERSKEYS = {"Saison", "Tournoi", "Joueur", "Match"};

	/**
	 * Identifiant du match
	 */
	private int id;

	/**
	 * Date de début du match
	 */
	private CustomDateTime dateDebutMatch;

	/**
	 * Date de fin du match
	 */
	private CustomDateTime dateFinMatch;
	
	/**
	 * Score des deux équipes (Map(Equipe : Integer))
	 */
	private Map<Equipe, Resultat> score = new HashMap<>();
	
	/**
	 * Equipes participant au match
	 */
	private Equipe[] equipes;

	/**
	 * Tournoi du match
	 */
	private Tournoi  tournoi;

	/**
	 * Arbitre du match
	 */
	private Arbitre arbitre;
	
	/**
	 * Type du match
	 */
	private TypeMatch type;

	/**
	 * DAO de la classe {@code Match}
	 */
	private static DAOMatch dao = DAOMatch.getDAO();

	/**
	 * Constructeur de la classe {@code Match} avec ajout dans la base de données et notification des observateurs
	 * @param dateDebutMatch Date de début du match
	 * @param equipe1 Equipe 1
	 * @param equipe2 Equipe 2
	 * @param arbitre Arbitre du match
	 * @param tournoi Tournoi du match
	 * @param type Type du match
	 */
	public Match(CustomDateTime dateDebutMatch, Equipe equipe1, Equipe equipe2, Arbitre arbitre, Tournoi tournoi , TypeMatch type) {
		super();
		Observable.attach(OBSERVERSKEYS);
		this.setDateDebutMatch(dateDebutMatch);
		this.setDateFinMatch(dateDebutMatch.plusHour(1));
		this.setEquipes(equipe1, equipe2);
		this.score.put(equipe1, Resultat.PAS_FINI);
		this.score.put(equipe2, Resultat.PAS_FINI);
		this.setType(type);
		this.setArbitre(arbitre);
		this.setTournoi(tournoi);
        try {
            boolean b = dao.add(this);
			if (b) {
				Observable.notifyStaticObservers();
				setId();
			}
        } catch (SQLException e) {
			System.err.println("Failed to add Match in constructor " + getClass());
        }
    }

	/**
	 * Constructeur de la classe {@code Match}
	 * @param id Identifiant du match
	 * @param dateDebutMatch Date de début du match
	 * @param equipe1 Equipe 1
	 * @param equipe2 Equipe 2
	 * @param score1 Score de l'équipe 1
	 * @param score2 Score de l'équipe 2
	 * @param arbitre Arbitre du match
	 * @param tournoi Tournoi du match
	 * @param type Type du match
	 */
	public Match (int id, CustomDateTime dateDebutMatch, Equipe equipe1, Equipe equipe2, Resultat score1, Resultat score2, Arbitre arbitre, Tournoi tournoi , TypeMatch type) {
		this.setDateDebutMatch(dateDebutMatch);
		this.setDateFinMatch(dateDebutMatch.plusHour(1));
		this.setEquipes(equipe1, equipe2);
		this.score.replace(equipe1, score1);
		this.score.replace(equipe2, score2);
		this.setType(type);
		this.setArbitre(arbitre);
		this.setTournoi(tournoi);
		this.id = id;
	}

	/**
	 * Constructeur de la classe {@code Match} avec ajout dans la base de données et notification des observateurs
	 * @param dateDebutMatch Date de début du match
	 * @param dateFinMatch Date de fin du match
	 * @param equipe1 Equipe 1
	 * @param equipe2 Equipe 2
	 * @param arbitre Arbitre du match
	 * @param tournoi Tournoi du match
	 * @param type Type du match
	 */
	public Match(CustomDateTime dateDebutMatch, CustomDateTime dateFinMatch, Equipe equipe1, Equipe equipe2, Arbitre arbitre, Tournoi tournoi , TypeMatch type) {
		super();
		Observable.attach(OBSERVERSKEYS);
		this.setDateDebutMatch(dateDebutMatch);
		this.setDateFinMatch(dateFinMatch);
		this.setEquipes(equipe1, equipe2);
		this.setType(type);
		this.setArbitre(arbitre);
		this.setTournoi(tournoi);
		try {
			boolean b = dao.add(this);
			if (b) {
				Observable.notifyStaticObservers();
				setId();
			}
		} catch (SQLException e) {
			System.err.println("Failed to add Match in constructor " + getClass());
		}
    }

	/**
	 * Constructeur de la classe {@code Match}
	 * @param id Identifiant du match
	 * @param dateDebutMatch Date de début du match
	 * @param dateFinMatch Date de fin du match
	 * @param equipe1 Equipe 1
	 * @param equipe2 Equipe 2
	 * @param score1 Score de l'équipe 1
	 * @param score2 Score de l'équipe 2
	 * @param arbitre Arbitre du match
	 * @param tournoi Tournoi du match
	 * @param type Type du match
	 */
	public Match(int id, CustomDateTime dateDebutMatch, CustomDateTime dateFinMatch, Equipe equipe1, Equipe equipe2, Resultat score1, Resultat score2, Arbitre arbitre, Tournoi tournoi , TypeMatch type) {
		this(id, dateDebutMatch, equipe1, equipe2, score1, score2, arbitre, tournoi, type);
		this.setDateFinMatch(dateFinMatch);
	}

	/**
	 * Constructeur de la classe {@code Match} avec ajout dans la base de données et notification des observateurs
	 * @param equipe1 L'équipe 1
	 * @param equipe2 L'équipe 2
	 * @param arbitre L'arbitre du match
	 * @param tournoi Le tournoi du match
	 * @param type Le type du match
	 */
	public Match(Equipe equipe1, Equipe equipe2, Arbitre arbitre, Tournoi tournoi,TypeMatch type) {
		super();
		Observable.attach(OBSERVERSKEYS);
		this.setEquipes(equipe1, equipe2);
		this.setType(type);
		this.setArbitre(arbitre);
		this.setTournoi(tournoi);
		try {
			boolean b = dao.add(this);
			if (b) {
				Observable.notifyStaticObservers();
				setId();
			}
		} catch (SQLException e) {
			System.err.println("Failed to add Match in constructor " + getClass());
		}
	}

	/**
	 * Constructeur de la classe {@code Match}
	 * @param id L'identifiant du match
	 * @param equipe1 L'équipe 1
	 * @param equipe2 L'équipe 2
	 * @param arbitre L'arbitre du match
	 * @param tournoi Le tournoi du match
	 * @param type Le type du match
	 */
	public Match(int id, Equipe equipe1, Equipe equipe2, Arbitre arbitre, Tournoi tournoi,TypeMatch type) {
		this.setEquipes(equipe1, equipe2);
		this.setType(type);
		this.setArbitre(arbitre);
		this.setTournoi(tournoi);
		this.id = id;
	}


	/**
	 * Getter de l'identifiant du match
	 * @return L'identifiant du match
	 */
    public int getId() {
        return id;
    }

	/**
	 * Getter de la date de début du match
	 * @return La date de début du match
	 */
	public CustomDateTime getDateDebutMatch() {
		return dateDebutMatch;
	}

	/**
	 * Getter de la date de fin du match
	 * @return La date de fin du match
	 */
	public CustomDateTime getDateFinMatch() {
		return dateFinMatch;
	}
	
	/**
	 * Getter de l'équipe 1
	 * @return L'équipe 1
	 */
	public Equipe getEquipe1() {
		return equipes[0];
	}
	
	/**
	 * Getter de l'équipe 2
	 * @return L'équipe 2
	 */
	public Equipe getEquipe2() {
		return equipes[1];
	}
	
	/**
	 * Getter du score de chaque équipe
	 * @param e L'équipe dont on veut connaître le score
	 * @return Le score de l'équipe e
	 */
	public Resultat getScore(Equipe e) {
		return score.get(e);
	}
	
	/**
	 * Getter du type de match
	 * @return Le type de match
	 */
	public TypeMatch getType() {
		return type;
	}

	/**
	 * Getter des équipes du match
	 * @return Les équipes du match
	 */
	public Equipe[] getEquipes() {
		return equipes;
	}

	/**
	 * Getter du tournoi du match
	 * @return Le tournoi du match
	 */
	public Tournoi getTournoi() {
		return tournoi;
	}

	/**
	 * Getter de l'arbitre du match
	 * @return L'arbitre du match
	 */
	public Arbitre getArbitre() {
		return arbitre;
	}

	/**
	 * Setter de la date de début du match
	 * @param dateDebutMatch La date de début du match
	 */
	public void setDateDebutMatch(CustomDateTime dateDebutMatch) {
		this.dateDebutMatch = dateDebutMatch;
	}

	/**
	 * Setter de la date de fin du match
	 * @param dateFinMatch La date de fin du match
	 */
	public void setDateFinMatch(CustomDateTime dateFinMatch) {
		this.dateFinMatch = dateFinMatch;
	}
    
	/**
	 * Setter de l'identifiant du match par récupération de l'identifiant du dernier match ajouté dans la base de données
	 */
    private void setId() throws SQLException {
        this.id = dao.getLastId();
    }

	/**
	 * Setter des équipes du match
	 * @param equipe1 L'équipe 1
	 * @param equipe2 L'équipe 2
	 */
	public void setEquipes(Equipe equipe1, Equipe equipe2) {
		this.equipes = new Equipe[] {equipe1, equipe2};
		resetScore();
	}

	/**
	 * Setter des équipes du match
	 * @param equipes Les équipes du match
	 */
	public void setEquipes(Equipe[] equipes) {
		this.equipes = equipes;
		resetScore();
	}

	/**
	 * Setter du score par default de chaque équipe
	 */
	public void resetScore() {
		score = new HashMap<>();
		score.put(getEquipe1(), Resultat.PAS_FINI);
		score.put(getEquipe2(), Resultat.PAS_FINI);
	}

	/**
	 * Setter du tournoi du match
	 * @param tournoi Le tournoi du match
	 */
	public void setTournoi(Tournoi tournoi) {
		this.tournoi = tournoi;
	}

	/**
	 * Setter de l'arbitre du match
	 * @param arbitre L'arbitre du match
	 */
	public void setArbitre(Arbitre arbitre) {
		this.arbitre = arbitre;
	}

	/**
	 * Setter du résultat d'un match
	 * @param e L'équipe gagnante
	 */
	public void setVictorious(Equipe e) {
		for (Equipe eq : score.keySet()) {
			if (eq == e) {
				score.replace(eq, Resultat.VICTOIRE);
			} else {
				score.replace(eq, Resultat.DEFAITE);
			}
		}
		Match.update(this);
		this.modClassementTournoi();
	}

	/**
	 * Methode de mise à jour du classement du tournoi
	 */
	public void modClassementTournoi() {
		for (Equipe e : score.keySet()) {
			this.getTournoi().setPointsEquipe(e, Tournoi.getEquipePoints(this.getTournoi(), e) + this.getScore(e).getPoints());
			Tournoi.update(this.getTournoi());
		}
	}

	/**
	 * Methode de vérification de la fin d'un match
	 * @return {@code true} si le match est fini, {@code false} sinon
	 */
	public boolean isFinished() {
		for (Resultat r : score.values()) {
			if (r != Resultat.PAS_FINI) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Setter du type de match
	 * @param type Le type de match
	 */
	public void setType(TypeMatch type) {
		this.type = type;
	}

	/**
	 * Methode d'affichage d'un match
	 * @return Le {@code String} correspondant au match
	 */
	@Override
	public String toString() {
		return "Match " + type + " (" + id + ") [" + getDateDebutMatch() + " : " + getDateFinMatch() + "]\n"
				+ "\t" + getEquipe1().getNom() + " - " + getScore(getEquipe1())
				+ " / "+ getScore(getEquipe2()) + " - " + getEquipe2().getNom();
	}

	/**
	 * Methode de hashage d'un match
	 * @return Le hash correspondant au match
	 */
	@Override
	public int hashCode() {
		return (getDateDebutMatch() + getEquipe1().getNom() + getEquipe2().getNom() + getId()).hashCode();
	}

	/**
	 * Methode de comparaison de deux matchs
	 * @param o Le match à comparer
	 * @return {@code true} si les deux matchs sont égaux, {@code false} sinon
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Match)) {
			return false;
		}
		Match other = (Match) o;
		return (this.getEquipe1().equals(other.getEquipe1())
				&& this.getEquipe2().equals(other.getEquipe2()))
				&& this.getId() == other.getId();
	}

	/**
	 * Methode de mise à jour d'un match dans la base de données et notification des observateurs
	 * @param match Le match à mettre à jour
	 * @return {@code true} si le match a été mis à jour, {@code false} sinon
	 */
	public static boolean update(Match match) {
		try {
			boolean b = dao.update(match);
			Observable.attach(OBSERVERSKEYS);
			if (b) {
				Observable.notifyStaticObservers();
			}
			return b;
		} catch (SQLException e) {
			System.err.println("Failed to update Match in update " + Match.class);
			return false;
		}
	}

	/**
	 * Methode de suppression d'un match dans la base de données et notification des observateurs
	 * @param id L'identifiant du match à supprimer
	 * @return {@code true} si le match a été supprimé, {@code false} sinon
	 */
	public static boolean delete(int id) {
		try {
			boolean b = dao.delete(id);
			Observable.attach(OBSERVERSKEYS);
			if (b) {
				Observable.notifyStaticObservers();
			}
			return b;
		} catch (SQLException e) {
			System.err.println("Failed to delete Match in delete " + Match.class);
			return false;
		}
	}

	/**
	 * Methode de récupération d'un match dans la base de données
	 * @param id L'identifiant du match à récupérer
	 * @return Le match récupéré
	 */
	public static Match getById(int id) {
		try {
			return dao.getById(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Match in getById " + Match.class);
			return null;
		}
	}

	/**
	 * Methode de récupération de tous les matchs dans la base de données
	 * @return La liste des matchs récupérés
	 */
	public static List<Match> getAll() {
		try {
			return dao.getAll();
		} catch (SQLException e) {
			System.err.println("Failed to get Match in getAll " + Match.class);
			return null;
		}
	}

	/**
	 * Methode de récupération de tous les matchs d'un tournoi dans la base de données
	 * @param tournoi Le tournoi dont on veut récupérer les matchs
	 * @return La liste des matchs récupérés
	 */
	public static List<Match> getByTournoi(Tournoi tournoi) {
		try {
			return dao.getByTournoi(tournoi);
		} catch (SQLException e) {
			System.err.println("Failed to get Match in getByTournoi " + Match.class);
			return null;
		}
	}

	/**
	 * Methode de récupération de tous les matchs à une date donnée dans la base de données
	 * @param date La date dont on veut récupérer les matchs
	 * @return La liste des matchs récupérés
	 */
	public static List<Match> getByDate(String date) {
		try {
			return dao.getByDate(date);
		} catch (SQLException e) {
			System.err.println("Failed to get Match in getByDate " + Match.class);
			return null;
		}
	}

	/**
	 * Methode de vérification de l'existence d'un match entre deux équipes dans un tournoi
	 * @param idEquipe1 L'identifiant de l'équipe 1
	 * @param idEquipe2 L'identifiant de l'équipe 2
	 * @param idTournoi L'identifiant du tournoi
	 * @return {@code true} si le match existe, {@code false} sinon
	 * @throws SQLException Exception SQL si la requête ne s'est pas bien déroulée
	 */
	public static boolean isMatchBetweenEquipeAndTournoi(int idEquipe1, int idEquipe2, int idTournoi) throws SQLException {
        return dao.isMatchBetweenEquipeAndTournoi(idEquipe1, idEquipe2, idTournoi);
    }

	/**
	 * Methode de récupération du nombre de matchs qu'à une équipe dans un tournoi
	 * @param idEquipe L'identifiant de l'équipe
	 * @param idTournoi L'identifiant du tournoi
	 * @return Le nombre de matchs qu'à l'équipe dans le tournoi
	 * @throws SQLException Exception SQL si la requête ne s'est pas bien déroulée
	 */
	public static int getNbMatchByEquipeAndTournoi(int idEquipe, int idTournoi) throws SQLException {
		return dao.getNbMatchByEquipeAndTournoi(idEquipe, idTournoi);
	}

	/**
	 * Methode de récupération de tous les matchs auxquels participe un joueur dans la base de données
	 * @param joueur Le joueur dont on veut récupérer les matchs
	 * @return La liste des matchs récupérés
	 */
	public static List<Match> getByJoueur(Joueur joueur) {
		try {
			return dao.getByJoueur(joueur);
		} catch (SQLException e) {
			System.err.println("Failed to get Match in getByEquipe " + Match.class);
			return null;
		}
	}
}
