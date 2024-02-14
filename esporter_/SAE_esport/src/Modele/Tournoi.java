package Modele;

import DAO.DAOTournoi;
import vue.Observable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * La classe {@code Tournoi} représente un tournoi
 * @author Nail Lamarti
 */
public class Tournoi extends Observable {

	/**
	 * Liste des clés des observateurs requis par la classe {@code Tournoi}
	 */
	private static final String[] OBSERVERSKEYS = {"Saison", "Equipe", "Tournoi", "Arbitre"}; // + match ptet
	
	/**
	 * Identifiant du tournoi
	 */
	private int id;
	
	/**
	 * Nom du tournoi
	 */
	private String nom;
	
	/**
	 * Date de début du tournoi
	 */
	private LocalDate dateDebut;
	
	/**
	 * Date de fin du tournoi
	 */
	private LocalDate dateFin;
	
	/**
	 * Lien vers l'image du tournoi
	 */
	private String img;
	
	/**
	 * Pool d'arbitres du tournoi (Set)
	 */
	private Set<Arbitre> poolArbitres;

	/**
	 * Login pour le compte arbitre
	 */
	private String login;

	/**
	 * Mot de passe pour le compte arbitre
	 */
	private String password;
	
	/**
	 * Pool d'équipes du tournoi (HashMap equipe associés à ces points))
	 */
	private HashMap<Equipe, Integer> listEquipes;

	/**
	 * Liste des matchs du tournoi
	 */
	private List<Match> listMatchs = new ArrayList<Match>();

	/**
	 * Saison du tournoi
	 */
	private Saison saison;

	/**
	 * Niveau du tournoi
	 */
	private NiveauTournoi niveau;

	private boolean	 terminer = false;

	/**
	 * DAO du tournoi
	 */
	private static DAOTournoi dao = DAOTournoi.getDAO();

	/**
	 * Constructeur de la classe {@code Tournoi} avec insertion dans la base de données et notification des observateurs
	 * @param nom Le nom du tournoi
	 * @param dateDebut La date de début du tournoi
	 * @param dateFin La date de fin du tournoi
	 * @param img path de l'image du tournoi
	 * @param niveau Le niveau du tournoi
	 * @param equipes La liste des équipes du tournoi
	 * @throws SQLException si l'insertion dans la base de données échoue
	 */
	public Tournoi(String nom, LocalDate dateDebut, LocalDate dateFin, String img, NiveauTournoi niveau, HashMap<Equipe, Integer> equipes, boolean terminer) throws SQLException {
		super();
		Observable.attach(OBSERVERSKEYS);
		try {
			this.nom = nom;
			this.dateDebut = dateDebut;
			this.dateFin = dateFin;
			this.img = img;
			this.setSaison();
			this.poolArbitres = this.getSaison().getArbitresRandom();
			this.listEquipes = equipes;
			this.niveau = niveau;
			this.setLogin();
			this.setPassword();
			this.setTerminer(terminer);
            saison.addTournoi(this);
			boolean b = dao.add(this);
			if (b) {
				Observable.notifyStaticObservers();
				this.setId();
				this.setListMatchs(this.genererMatchs());
			}
		} catch (SQLException e) {
			System.err.println("Failed to add Tournoi in constructor " + getClass());
			throw new SQLException("Les dates de ce tournoi se superpose avec un autre tournoi");
        }
    }

	/**
	 * Constructeur de la classe {@code Tournoi} avec insertion dans la base de données et notification des observateurs
	 * @param nom Le nom du tournoi
	 * @param dateDebut La date de début du tournoi
	 * @param dateFin La date de fin du tournoi
	 * @param img path de l'image du tournoi
	 * @param niveau Le niveau du tournoi
	 * @param equipes La liste des équipes du tournoi
	 * @param arbitres La liste des arbitres du tournoi
	 * @throws SQLException si l'insertion dans la base de données échoue
	 */
	public Tournoi (String nom, LocalDate dateDebut, LocalDate dateFin, String img, NiveauTournoi niveau, HashMap<Equipe, Integer> equipes, Set<Arbitre> arbitres, boolean terminer) throws SQLException {
		super();
		Observable.attach(OBSERVERSKEYS);
		try {
			this.nom = nom;
			this.dateDebut = dateDebut;
			this.dateFin = dateFin;
			this.img = img;
			this.setSaison();
			this.setPoolArbitres(arbitres);
			this.listEquipes = equipes;
			this.niveau = niveau;
			this.setLogin();
			this.setPassword();
			this.setTerminer(terminer);
			saison.addTournoi(this);
			boolean b = dao.add(this);
			if (b) {
				Observable.notifyStaticObservers();
				this.setId();
				this.setListMatchs(this.genererMatchs());
			}
		} catch (SQLException e) {
			System.err.println("Failed to add Tournoi in constructor " + getClass());
			throw new SQLException("Les dates de ce tournoi se superpose avec un autre tournoi");
		}
	}

	/**
	 * Constructeur de la classe {@code Tournoi}
	 * @param id L'identifiant du tournoi
	 * @param nom Le nom du tournoi
	 * @param dateDebut La date de début du tournoi
	 * @param dateFin La date de fin du tournoi
	 * @param img path de l'image du tournoi
	 * @param niveau Le niveau du tournoi
	 * @param equipes La liste des équipes du tournoi
	 * @param login Le login pour le compte arbitre
	 * @param password Le mot de passe pour le compte arbitre
	 */
	public Tournoi(int id, String nom, LocalDate dateDebut, LocalDate dateFin, String img, NiveauTournoi niveau, HashMap<Equipe, Integer> equipes,
				   String login, String password, boolean terminer) {
        this.id = id;
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.saison = Saison.getById(dateDebut.getYear());
		this.img = img;
		this.setPoolArbitres();
		this.listEquipes = equipes;
		this.niveau = niveau;
		this.setLogin(login);
		this.setPassword(password);
		this.setTerminer(terminer);
		this.setListMatchs(Match.getByTournoi(this));
    }

	/**
	 * Constructeur de la classe {@code Tournoi}
	 * @param id L'identifiant du tournoi
	 * @param nom Le nom du tournoi
	 * @param dateDebut La date de début du tournoi
	 * @param dateFin La date de fin du tournoi
	 * @param img path de l'image du tournoi
	 * @param niveau Le niveau du tournoi
	 * @param equipes La liste des équipes du tournoi
	 * @param arbitres La liste des arbitres du tournoi
	 * @param login Le login pour le compte arbitre
	 * @param password Le mot de passe pour le compte arbitre
	 */
	public Tournoi(int id, String nom, LocalDate dateDebut, LocalDate dateFin, String img, NiveauTournoi niveau, HashMap<Equipe, Integer> equipes,
				   Set<Arbitre> arbitres, String login, String password, boolean terminer) {
		this.id = id;
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.saison = Saison.getById(dateDebut.getYear());
		this.img = img;
		this.setPoolArbitres(arbitres);
		this.listEquipes = equipes;
		this.niveau = niveau;
		this.setLogin(login);
		this.setPassword(password);
		this.setTerminer(terminer);
		this.setListMatchs(Match.getByTournoi(this));
	}

	/**
	 * Fonction permettant de générer les matchs du tournoi
	 * pour que toutes les équipes se rencontrent et les ajoutent dans la base de données
	 * @return La liste des matchs générés
	 */
	public List<Match> genererMatchs() {
		List<Match> matchs = new ArrayList<>();
		List<Equipe> equipes = new ArrayList<>(this.listEquipes.keySet());
		// génére des matchs entre toutes les équipes
		for(int i = 0; i < equipes.size()-1; i++) {
			for(int j = i + 1; j < equipes.size(); j++) {
				Match match = new Match(i, equipes.get(i), equipes.get(j), getRandomArbitre(), this, TypeMatch.POOL);
				matchs.add(match);
			}
		}

		// trie la liste pour qu'une équipe ne joue pas deux fois de suite sans aléatoire
		List<Match> finalMatchs = matchs;
		matchs = matchs.stream().sorted((m1, m2) -> {
			if ((m1.getEquipes()[0].equals(m2.getEquipes()[0]) || m1.getEquipes()[0].equals(m2.getEquipes()[1]))
			|| (m1.getEquipes()[1].equals(m2.getEquipes()[0]) || m1.getEquipes()[1].equals(m2.getEquipes()[1]))){
				return 0;
			}
			if (finalMatchs.indexOf(m1) > finalMatchs.indexOf(m2)) {
				return -1;
			}
			return 1;
		}).collect(Collectors.toList());
		// Pour chaque match, ajoute leur date de début et de fin en fonction de l'ordre de la liste
		// en repartissant les matchs sur le nombre de jours du tournoi - 1 du tournoi dans la plage horaires
		// 8h-18h
		int[] heures = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
		int nbJours = (int) (dateFin.toEpochDay() - dateDebut.toEpochDay());
		int nbMatchs = matchs.size();
		int nbMatchsRestants = nbMatchs % nbJours;
		int matchIndex = 0;

		for (int i = 0; i < nbJours; i++) {
			int nbMatchsParJour = nbMatchs / nbJours;
			int heure = heures[heures.length - 1];
			if (nbMatchsRestants > 0) {
				nbMatchsParJour++;
				nbMatchsRestants--;
			}
			for (int j = 0; j < nbMatchsParJour; j++) {
				CustomDateTime dateDebut = new CustomDateTime(this.getDateDebut().plusDays(i), LocalTime.of(heure, 0));
				CustomDateTime dateFin = new CustomDateTime(this.getDateDebut().plusDays(i), LocalTime.of(heure, 55));
				matchs.get(matchIndex).setDateDebutMatch(dateDebut);
				matchs.get(matchIndex).setDateFinMatch(dateFin);
				matchIndex++;
				heure--;
			}
		}

		for (Match m : matchs) {
			System.out.println(m.getDateDebutMatch() + " - " + m.getDateFinMatch());
			new Match(m.getDateDebutMatch(), m.getDateFinMatch(), m.getEquipes()[0], m.getEquipes()[1], m.getArbitre(), m.getTournoi(), m.getType());
		}
		System.out.println(matchs);
		return matchs;
	}

	/**
	 * Getter de l'identifiant du tournoi
	 * @return L'identifiant du tournoi
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter du nom du tournoi
	 * @return Le nom du tournoi
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Getter de la date du tournoi
	 * @return La date de début du tournoi
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	
	/**
	 * Getter de la date de fin du tournoi
	 * @return La date de fin du tournoi
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}
	
	/**
	 * Getter du lien de l'image du tournoi
	 * @return Le lien vers l'image du tournoi
	 */
	public String getImage() {
		return img;
	}
	
	/**
	 * Getter du niveau du tournoi
	 * @return Le niveau du tournoi
	 */
	public NiveauTournoi getNiveau() {
		return niveau;
	}
	
	/**
	 * Setter de l'identifiant du tournoi
	 */
	public void setId() throws SQLException {
		this.id = dao.getLastId();
	}
	
	/**
	 * Setter du nom du tournoi
	 * @param nom Le nom du tournoi
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Setter de la date de début du tournoi
	 * @param dateDebut La date de début du tournoi
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}
	
	/**
	 * Setter de la date de fin du tournoi
	 * @param dateFin La date de fin du tournoi
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
	
	/**
	 * Setter du lien de l'image du tournoi
	 * @param img Le lien vers l'image du tournoi
	 */
	public void setImage(String img) {
		this.img = img;
	}

	/**
	 * Getter de la liste des équipes du tournoi
	 * @return La liste des équipes du tournoi
	 */
	public HashMap<Equipe, Integer> getListEquipes() {
		return listEquipes;
	}

	/**
	 * Setter de la liste des arbitres du tournoi
	 */
	public void setPoolArbitres() {
		this.poolArbitres = this.saison.getArbitresRandom();
	}

	/**
	 * Setter de la liste des arbitres du tournoi
	 * @param arbitres La liste des arbitres à ajouter
	 */
	public void setPoolArbitres(Set<Arbitre> arbitres) {
		this.poolArbitres = arbitres;
	}

	/**
	 * Getter de la liste des arbitres du tournoi
	 * @return La liste des arbitres du tournoi
	 */
	public Set<Arbitre> getPoolArbitres() {
		return poolArbitres;
	}

	/**
	 * Fonction permettant d'ajouter un match dans la liste des matchs du tournoi
	 * @param match Le match à ajouter
	 */
	public void AddMatch(Match... match) {
		this.listMatchs.addAll(Arrays.asList(match));
	}

	/**
	 * Setter de la liste des matchs du tournoi
	 * @param listMatchs La liste des matchs à ajouter
	 */
	public void setListMatchs(List<Match> listMatchs) {
		this.listMatchs = listMatchs;
	}

	/**
	 * Getter de la liste des matchs du tournoi
	 * @return La liste des matchs du tournoi
	 */
	public List<Match> getListMatchs() {
		return listMatchs;
	}

	/**
	 * Fonction permettant de retirer un match de la liste des matchs du tournoi
	 * @param match Le match à retirer
	 */
	public void removeMatch(Match match) {
		this.listMatchs.remove(match);
	}

	/**
	 * Fonction permettant de retirer un match de la liste des matchs du tournoi
	 * @param id L'identifiant du match à retirer
	 */
	public void removeMatch(int id) {
		for(Match m : listMatchs) {
			if(m.getId() == id) {
				listMatchs.remove(m);
				break;
			}
		}
	}

	/**
	 * Setter de la saison du tournoi
	 */
	public void setSaison() {
		this.saison = Saison.getById(dateDebut.getYear());
	}

	/**
	 * Getter de la saison du tournoi
	 * @return La saison du tournoi
	 */
	public Saison getSaison() {
		return saison;
	}

	/**
	 * Fonction permettant de savoir si le tournoi est en cours
	 * @return {@code true} si le tournoi est en cours, {@code false} sinon
	 */
	public boolean isCurrent() {
		CustomDateTime now = new CustomDateTime(LocalDate.now());
		CustomDateTime deb = new CustomDateTime(dateDebut);
		CustomDateTime fin = new CustomDateTime(dateFin);
		if (now.isBetween(deb, fin) || now.equals(deb) || now.equals(fin)) {
			return true;
		}
        return false;
	}

	/**
	 * Generateur de chaine aléatoire parmi un alphabet défini
	 * @param l Longueur de la chaine à générer
	 * @return La chaine générée
	 */
	public String genererChaine(int l) {
		StringBuilder chaine = new StringBuilder();
		Random rand = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123567890-_*&";
		int longueur = alphabet.length();
		for(int i = 0; i < l; i++) {
			int k = rand.nextInt(longueur);
			chaine.append(alphabet.charAt(k));
		}
		return chaine.toString();
	}

	/**
	 * Getter de login pour le compte arbitre
	 * @return Le login généré
	 */
	public void setLogin() {
		this.login = "Arbitre-" + getNom();
	}

	/**
	 * Setter de login pour le compte arbitre
	 *
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Getter de login pour le compte arbitre
	 * @return Le login généré
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Getter de mot de passe pour le compte arbitre
	 * @return Le mot de passe généré
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter de mot de passe pour le compte arbitre
     */
	public void setPassword() {
		this.password = genererChaine(20);
	}

	/**
	 * Setter de mot de passe pour le compte arbitre
	 * @param password Le mot de passe
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter de l'arbitre aléatoire
	 * @return Un Arbitre aléatoire de la liste des arbitres du tournoi
	 */
	public Arbitre getRandomArbitre() {
		Random rand = new Random();
		int k = rand.nextInt(this.getPoolArbitres().size());
		return (Arbitre) this.getPoolArbitres().toArray()[k];
	}

	/**
	 * Getter du podium du tournoi (les 4 premières équipes)
	 * @return Le podium du tournoi
	 */
	public List<HashMap<Equipe, Integer>> getPodium() {
	    List<Equipe> podium = this.getListEquipes()
	    		.keySet()
	    		.stream()
				.sorted(new compareEquipe())
	            .collect(Collectors.toList());
	    
	    Collections.reverse(podium);
	    
	    podium = podium.stream()
				.limit(4)
				.collect(Collectors.toList());
	
	    List<HashMap<Equipe, Integer>> res = new ArrayList<>();
	    for (Equipe e : podium) {
	        HashMap<Equipe, Integer> map = new HashMap<>();
	        map.put(e, this.getListEquipes().get(e));
	        res.add(map);
	    }
	    return res;
	}

	public boolean isTerminer() {
		return terminer;
	}

	public void setTerminer(boolean terminer) {
		this.terminer = terminer;
	}

	/**
	 * Comparateur d'équipes, compare les équipes en fonction de leur nombre de points dans le tournoi puis
	 * si égalité en fonction de leur classement mondial et si égalité, au hasard
	 */
	private class compareEquipe implements Comparator<Equipe> {
		@Override
		public int compare(Equipe e1, Equipe e2) {
			int comp = listEquipes.get(e1).compareTo(listEquipes.get(e2));
			if (comp == 0) {
				comp = (e1.getWorldRank() - e2.getWorldRank());
				if (comp == 0) {
					comp = Math.random() > 0.5 ? 1 : -1;
				}
			}
			return comp;
		}
	}

	/**
	 * Methode permettant de cloturer la pool du tournoi et de générer les matchs de fin de tournoi
	 * @return {@code true} si la pool est cloturée, {@code false} sinon
	 */
	public boolean cloturerPool() {
		boolean verif = true;
		CustomDateTime now = new CustomDateTime(LocalDateTime.now());
		
		//on vérifie que tous les matchs sont terminés
		for (Match m : Match.getByTournoi(this)) {
			if (m.getDateFinMatch().isAfter(now)) {
				verif = false;
			}
		}
		
		if (verif) {
			
			// on sélectionne le jour de fin du tournoi, si le tournoi se finit aujourd'hui on sélectionne l'heure actuelle
			CustomDateTime dateMatch = new CustomDateTime(this.getDateFin());
			if (now.getMonth() == this.getDateFin().getMonth().getValue() && now.getDay() == this.getDateFin().getDayOfMonth()) {
				dateMatch = now;
			}
			
			// on récupère les 4 premières équipes du classement
			List<HashMap<Equipe, Integer>> podium = this.getPodium();
			
			// on génère les matchs de fin de tournoi
			Match petiteFinale = new Match(dateMatch, dateMatch.plusHour(1), podium.get(2).keySet().iterator().next(), podium.get(3).keySet().iterator().next(), this.getRandomArbitre(), this, TypeMatch.PETITE_FINALE);
			Match finale = new Match(dateMatch, dateMatch.plusHour(1), podium.get(0).keySet().iterator().next(), podium.get(1).keySet().iterator().next(), this.getRandomArbitre(), this, TypeMatch.FINALE);
			this.AddMatch(petiteFinale, finale);
			Tournoi.update(this);
		}
		return verif;
	}
	
	public boolean isFinished() {
		CustomDateTime now = new CustomDateTime(LocalDateTime.now());
		for (Match m : this.getListMatchs()) {
			if (m.getType() == TypeMatch.FINALE && m.getDateFinMatch().isBefore(now)) {
				return true;
			}
		}
		return false;
	}

	public boolean isStarted() {
		CustomDateTime now = new CustomDateTime(LocalDateTime.now());
		return new CustomDateTime(dateDebut).isBefore(now);
	}


	/**
	 * Methode permettant de cloturer le tournoi et de calculer les points des équipes
	 * @return {@code true} si le tournoi est cloturé, {@code false} sinon
	 */
	public boolean cloturerTournoi() {
		if (isFinished()) {
			
			// calcul des points gagnés par chaque équipe pour chaque match
			double mult = this.getNiveau().getMultiplicateur();
			for (Match m : Objects.requireNonNull(Match.getByTournoi(this))) {
				for (Equipe e : m.getEquipes()) {
					int pts = (int) (saison.getClassement().get(e) + (m.getType().getPointsByResultat(m.getScore(e)) * mult));
					saison.getClassement().replace(e, pts);
				}
			}
			Saison.update(saison);
		}
		return isFinished();
	}
	
	/**
	 * Methode d'affichage d'un tournoi
	 * @return Le {@code String} correspondant au tournoi
	 */
	@Override
	public String toString() {
		return "(" + id + ") " + nom  + " : " + dateDebut.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + " -> " + dateFin.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + " - " + niveau ;
	}

	/**
	 * Methode de hashage d'un tournoi
	 * @return Le hash correspondant au tournoi
	 */
	@Override
	public int hashCode() {
		return (this.getNom() + this.getNiveau()).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tournoi tournoi = (Tournoi) o;
		return Objects.equals(nom, tournoi.nom)
				&& Objects.equals(dateDebut, tournoi.dateDebut)
				&& Objects.equals(dateFin, tournoi.dateFin)
				&& Objects.equals(img, tournoi.img)
				&& Objects.equals(poolArbitres, tournoi.poolArbitres)
				&& Objects.equals(listEquipes, tournoi.listEquipes)
				&& niveau == tournoi.niveau;
	}

	/**
	 * Methode de mise à jour d'un tournoi dans la base de données et de notification des observateurs
	 * @param tournoi {@code Tournoi} à mettre à jour
	 * @return {@code true} si la mise à jour a été effectuée, {@code false} sinon
	 */
	public static boolean update(Tournoi tournoi) {
		try {
			boolean b = dao.update(tournoi);
			Observable.attach(OBSERVERSKEYS);
			if (b) {
				Observable.notifyStaticObservers();
			}
			return b;
		} catch (SQLException e) {
			System.err.println("Failed to update Tournoi in update " + Tournoi.class + " Les dates de ce tournoi se superpose avec un autre tournoi.");
			return false;
		}
	}

	/**
	 * Methode de suppression d'un tournoi dans la base de données et de notification des observateurs
	 * @param id Identifiant du tournoi à supprimer
	 * @return {@code true} si la suppression a été effectuée, {@code false} sinon
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
			System.err.println("Failed to delete Tournoi in delete " + Tournoi.class);
			return false;
		}
	}

	/**
	 * Methode de récupération d'un tournoi dans la base de données
	 * @param id Identifiant du tournoi à récupérer
	 * @return Le {@code Tournoi} correspondant à l'identifiant
	 */
	public static Tournoi getById(int id) {
		try {
			return dao.getById(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Tournoi in getById " + Tournoi.class);
			return null;
		}
	}

	/**
	 * Methode de récupération de tous les tournois dans la base de données
	 * @return La liste des {@code Tournoi} correspondant à la requête
	 */
	public static List<Tournoi> getAll() {
		try {
			return dao.getAll();
		} catch (SQLException e) {
			System.err.println("Failed to get Tournoi in getAll " + Tournoi.class);
			return null;
		}
	}

	/**
	 * Methode de récupération du classement des équipes du tournoi dans la base de données
	 * @param id Identifiant du tournoi
	 * @return  Le classement des {@code Equipe} correspondant à la requête
	 */
	public static HashMap<Equipe, Integer> getEquipesForTournoi(int id) {
		try {
			return dao.getEquipesForTournoi(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Equipes for Tournoi in getEquipesForTournoi " + Tournoi.class);
			return null;
		}
	}

	/**
	 * Methode de récupération des arbitres du tournoi dans la base de données
	 * @param id Identifiant du tournoi
	 * @return  La liste des {@code Arbitre} correspondant à la requête
	 */
	public static Set<Arbitre> getArbitresForTournoi(int id) {
		try {
			return dao.getArbitresForTournoi(id);
		} catch (SQLException e) {
			System.err.println("Failed to get Arbitres for Tournoi in getArbitresForTournoi " + Tournoi.class);
			return null;
		}
	}

	/**
	 * Méthode de récupération des tournois d'une saison dans la base de données
	 * @param id Identifiant de la {@code Saison} à récupérer
	 * @return La liste des {@code Tournoi} correspondant à la requête
	 */
	public static HashSet<Tournoi> getBySaison(int id) {
		try {
			return dao.getBySaison(id);
		} catch (SQLException e) {
			System.err.println("Failed to get list " + Tournoi.class + " by saison");
			return null;
        }
    }

	/**
	 * Méthode de récupération d'un tournoi par ses identifiants arbitre dans la base de données
	 * @param login Login de l'arbitre
	 * @param mdp Mot de passe de l'arbitre
	 * @return Le {@code Tournoi} correspondant à la requête
	 */
	public static Tournoi getTournoiByIdentification(String login, String mdp) {
		try {
			return dao.getTournoiByIndentification(login, mdp);
		} catch (SQLException e) {
			System.err.println("Failed to get Tournoi in getTournoiByIdentification " + Tournoi.class);
			return null;
		}
	}

	/**
	 * Setter des points d'une équipe dans le tournoi
	 * @param e L'équipe
	 * @param i Le nombre de points
	 */
	public void setPointsEquipe(Equipe e, Integer i) {
		this.getListEquipes().replace(e, i);
	}

	/**
	 * Getter des points d'une équipe dans un tournoi dans la base de données
	 * @param t Le tournoi
	 * @param e L'équipe
	 * @return Le nombre de points
	 */
	public static int getEquipePoints(Tournoi t, Equipe e) {
		try {
			return dao.getEquipePoints(t, e);
		} catch (SQLException ex) {
			System.err.println("Failed to get points for Equipe in getEquipePoints " + Tournoi.class);
			return -1;
		}
	}

	/**
	 * Getter des tournois d'une équipe dans la base de données
	 * @param e L'équipe
	 * @return La liste des {@code Tournoi} correspondant à la requête
	 */
	public static List<Tournoi> getTournoiByEquipe(Equipe e) {
		try {
			return dao.getTournoiByEquipe(e);
		} catch (SQLException ex) {
			System.err.println("Failed to get Tournoi by Equipe in getTournoiByEquipe " + Tournoi.class);
			return null;
		}
	}

	/**
	 * Getter des tournois d'un arbitre dans la base de données
	 * @param a L'arbitre
	 * @return La liste des {@code Tournoi} correspondant à la requête
	 */
	public static List<Tournoi> getTournoiByArbitre(Arbitre a) {
		try {
			return dao.getTournoiByArbitre(a);
		} catch (SQLException ex) {
			System.err.println("Failed to get Tournoi by Arbitre in getTournoiByArbitre " + Tournoi.class);
			return null;
		}
	}
}
