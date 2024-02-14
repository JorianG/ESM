package connect;

import Modele.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

/**
	Classe permettant de se connecter à la base de données
	@Author Jorian
	@Version 1.0

 */
public class DBConnect {

	/**
	 * Instance de la classe DBConnect (Singleton)
	 */
	private static DBConnect dbInstance;

	public static final Integer PATH_LENGTH = 1000;

	private Connection conn;
	private String url;

	/**
	 * Constructeur de la classe, créé une connexion à la base de données
	 * @param url link of the database
	 * @throws SQLException si une erreur survient lors de la connexion
	 *
	 */
	public DBConnect(String url) throws SQLException {
		System.setProperty("derby.system.home", System.getProperty("user.dir")+ "/BDD");
		DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
		this.url = url;
		try {
			this.conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.err.println("Erreur de connexion");
			e.printStackTrace();
        }
	}

	public DBConnect() throws SQLException {
		this("jdbc:derby:SAE;create=true");
	}

	/**
	 * Methode renvoyant l'instance de la classe DBConnect
	 * @return l'instance de la classe DBConnect
	 * @throws SQLException 
	 */
	public static synchronized DBConnect getDbInstance() throws SQLException {
		if (dbInstance == null) {
			dbInstance = new DBConnect();
		}
		return dbInstance;
	}

	/**
	 * Methode fermant la connexion à la base de données
	 * @throws SQLException si une erreur survient lors de la fermeture de la connexion
	 */
	public void close() throws SQLException {
		this.conn.close();
	}

	public void commit() throws SQLException {
		this.conn.commit();
	}

	public void rollback() throws SQLException {
		this.conn.rollback();
	}

	public Connection getConn() {
		return conn;
	}

	/**
	 * Methode appliquant la fonction passée en paramètre et renvoyant le résultat de la fonction
	 * @param function fonction à appliquer
	 * @return retour de la fonction en paramètre
	 * @param <R> type de retour de la fonction
	 * @throws SQLException si une erreur survient lors de l'application de la fonction
	 */
	public <R> R executeStatement(Function<Statement, R> function) throws SQLException{
        return function.apply(this.conn.createStatement());
	}

	@SuppressWarnings("unused")
	public void createTable() throws SQLException {
		try {
			Statement statement = this.conn.createStatement();

			statement.execute("CREATE TABLE PAYS(" +
					"id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1 )," +
					"nom VARCHAR(50) NOT NULL UNIQUE," +
					"drapeau VARCHAR(100) NOT NULL UNIQUE" +
					")");

			statement.execute("CREATE TABLE SAISON(" +
					"annee INT NOT NULL PRIMARY KEY," +
					"nom VARCHAR(50)," +
					"logo varchar("+PATH_LENGTH+")," +
					"archived boolean not null default false" +
					")");

			statement.execute("CREATE TABLE ARBITRE(" +
					"id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
					"nom VARCHAR(50)," +
					"prenom VARCHAR(50)," +
					"image VARCHAR("+PATH_LENGTH+")" +
					")");

			statement.execute("CREATE TABLE ADMIN (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            		+ "login VARCHAR(50) NOT NULL UNIQUE, "
            		+ "mdp VARCHAR(50) NOT NULL , "
            		+ "PRIMARY KEY (id))");
			
			statement.execute("CREATE TABLE EQUIPE (" +
                    "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                    "nom VARCHAR(50) NOT NULL UNIQUE, " +
                    "code VARCHAR(3) NOT NULL,"+
                    "img VARCHAR("+PATH_LENGTH+") NOT NULL," +
                    "idpays INTEGER NOT NULL," +
                    "world_rank INTEGER NOT NULL," +
                    "PRIMARY KEY (id))");

			statement.execute("CREATE TABLE JOUEUR("
					+ "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
					+ "nom VARCHAR(50) NOT NULL, "
					+ "prenom VARCHAR(50) NOT NULL, "
					+ "pseudo VARCHAR(50) NOT NULL UNIQUE, "
					+ "img VARCHAR("+PATH_LENGTH+"), "
					+ "id_equipe INT REFERENCES EQUIPE(id), "
					+ "id_poste VARCHAR(50) NOT NULL, "
					+ "id_pays INT NOT NULL REFERENCES PAYS(id))");


			statement.execute(
					"CREATE TABLE TOURNOI(" +
							"id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), " +
							"nom VARCHAR(50) NOT NULL , " +
							"date_debut DATE UNIQUE NOT NULL," +
							"date_fin DATE UNIQUE NOT NULL, " +
							"img VARCHAR("+PATH_LENGTH+")," +
							"login_arbitrage VARCHAR(50) UNIQUE NOT NULL, " +
							"mdp_arbitrage VARCHAR(50)  NOT NULL, " +
							"niveau VARCHAR(50) not null , " +
							"id_saison INT NOT NULL REFERENCES SAISON(ANNEE), " +
							"terminer boolean not null default false)"
			);

			statement.execute(
					"CREATE TABLE MATCH_LOL(" +
							"id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
							"date_debut_match TIMESTAMP NOT NULL," +
							"date_fin_match TIMESTAMP," +
							"id_equipe1 INT REFERENCES EQUIPE(id), " +
							"id_equipe2 INT REFERENCES EQUIPE(id), " +
							"score1 INT NOT NULL, " +
							"score2 INT NOT NULL, " +
							"id_arbitre INT REFERENCES ARBITRE(id), " +
							"id_tournoi INT REFERENCES TOURNOI(id), " +
							"type_match VARCHAR(50) NOT NULL)"
			);

			statement.execute(
					"CREATE TABLE PARTICIPER_TOURNOI(" +
							"id_equipe INT REFERENCES EQUIPE(id), " +
							"id_tournoi INT REFERENCES TOURNOI(id)," +
							"points INT NOT NULL DEFAULT -1," +
							"PRIMARY KEY (id_tournoi, id_equipe))"
			);

			statement.execute(
					"CREATE TABLE ARBITRE_SAISON(" +
							"id_arbitre INT REFERENCES ARBITRE(id), " +
							"annee INT REFERENCES SAISON(ANNEE)," +
							"PRIMARY KEY (annee, id_arbitre))"
			);

			statement.execute(
					"CREATE TABLE ARBITRAGE_TOURNOI(" +
							"id_arbitre INT REFERENCES ARBITRE(id), " +
							"id_tournoi INT REFERENCES TOURNOI(id)," +
							"PRIMARY KEY (id_tournoi, id_arbitre))"
			);

			statement.execute(
					"CREATE TABLE PARTICIPER_SAISON(" +
							"annee int references SAISON(ANNEE), " +
							"id_equipe int references EQUIPE(ID), " +
							"points int not null default 0, " +
							"primary key (annee, id_equipe))"
			);

			Pays p = new Pays("Afghanistan", "af");
			p = new Pays("Albanie", "al");
			p = new Pays("Allemagne", "de");
			p = new Pays("Algérie", "dz");
			p = new Pays("Andorre", "ad");
			p = new Pays("Angola", "ao");
			p = new Pays("Antigua-et-Barbuda", "ag");
			p = new Pays("Argentine", "ar");
			p = new Pays("Arménie", "am");
			p = new Pays("Australie", "au");
			p = new Pays("Autriche", "at");
			p = new Pays("Azerbaïdjan", "az");
			p = new Pays("Bahamas", "bs");
			p = new Pays("Bahreïn", "bh");
			p = new Pays("Bangladesh", "bd");
			p = new Pays("Barbade", "bb");
			p = new Pays("Biélorussie", "by");
			p = new Pays("Belgique", "be");
			p = new Pays("Belize", "bz");
			p = new Pays("Bénin", "bj");
			p = new Pays("Bhoutan", "bt");
			p = new Pays("Bolivie", "bo");
			p = new Pays("Bosnie-Herzégovine", "ba");
			p = new Pays("Botswana", "bw");
			p = new Pays("Brésil", "br");
			p = new Pays("Brunei", "bn");
			p = new Pays("Bulgarie", "bg");
			p = new Pays("Burkina Faso", "bf");
			p = new Pays("Burundi", "bi");
			p = new Pays("Cambodge", "kh");
			p = new Pays("Cameroun", "cm");
			p = new Pays("Canada", "ca");
			p = new Pays("Cap-Vert", "cv");
			p = new Pays("République centrafricaine", "cf");
			p = new Pays("Tchad", "td");
			p = new Pays("Chili", "cl");
			p = new Pays("Chine", "cn");
			p = new Pays("Colombie", "co");
			p = new Pays("Comores", "km");
			p = new Pays("Congo", "cg");
			p = new Pays("Costa Rica", "cr");
			p = new Pays("Croatie", "hr");
			p = new Pays("Cuba", "cu");
			p = new Pays("Chypre", "cy");
			p = new Pays("République tchèque", "cz");
			p = new Pays("Danemark", "dk");
			p = new Pays("Djibouti", "dj");
			p = new Pays("Dominique", "dm");
			p = new Pays("République dominicaine", "do");
			p = new Pays("Timor oriental", "tl");
			p = new Pays("Équateur", "ec");
			p = new Pays("Égypte", "eg");
			p = new Pays("Espagne", "es");
			p = new Pays("Salvador", "sv");
			p = new Pays("Guinée équatoriale", "gq");
			p = new Pays("Érythrée", "er");
			p = new Pays("Estonie", "ee");
			p = new Pays("Éthiopie", "et");
			p = new Pays("Fidji", "fj");
			p = new Pays("Finlande", "fi");
			p = new Pays("France", "fr");
			p = new Pays("Gabon", "ga");
			p = new Pays("Gambie", "gm");
			p = new Pays("Géorgie", "ge");
			p = new Pays("Ghana", "gh");
			p = new Pays("Grèce", "gr");
			p = new Pays("Grenade", "gd");
			p = new Pays("Guatemala", "gt");
			p = new Pays("Guinée", "gn");
			p = new Pays("Guinée-Bissau", "gw");
			p = new Pays("Guyane", "gy");
			p = new Pays("Haïti", "ht");
			p = new Pays("Honduras", "hn");
			p = new Pays("Hongrie", "hu");
			p = new Pays("Islande", "is");
			p = new Pays("Inde", "in");
			p = new Pays("Indonésie", "id");
			p = new Pays("Iran", "ir");
			p = new Pays("Irak", "iq");
			p = new Pays("Irlande", "ie");
			p = new Pays("Israël", "il");
			p = new Pays("Italie", "it");
			p = new Pays("Jamaïque", "jm");
			p = new Pays("Japon", "jp");
			p = new Pays("Jordanie", "jo");
			p = new Pays("Kazakhstan", "kz");
			p = new Pays("Kenya", "ke");
			p = new Pays("Kiribati", "ki");
			p = new Pays("Corée du Nord", "kp");
			p = new Pays("Corée du Sud", "kr");
			p = new Pays("Koweït", "kw");
			p = new Pays("Kirghizistan", "kg");
			p = new Pays("Laos", "la");
			p = new Pays("Lettonie", "lv");
			p = new Pays("Liban", "lb");
			p = new Pays("Lesotho", "ls");
			p = new Pays("Libéria", "lr");
			p = new Pays("Libye", "ly");
			p = new Pays("Liechtenstein", "li");
			p = new Pays("Lituanie", "lt");
			p = new Pays("Luxembourg", "lu");
			p = new Pays("Macédoine", "mk");
			p = new Pays("Madagascar", "mg");
			p = new Pays("Malawi", "mw");
			p = new Pays("Malaisie", "my");
			p = new Pays("Maldives", "mv");
			p = new Pays("Mali", "ml");
			p = new Pays("Malte", "mt");
			p = new Pays("Îles Marshall", "mh");
			p = new Pays("Mauritanie", "mr");
			p = new Pays("Maurice", "mu");
			p = new Pays("Mexique", "mx");
			p = new Pays("Micronésie", "fm");
			p = new Pays("Moldavie", "md");
			p = new Pays("Monaco", "mc");
			p = new Pays("Mongolie", "mn");
			p = new Pays("Monténégro", "me");
			p = new Pays("Maroc", "ma");
			p = new Pays("Mozambique", "mz");
			p = new Pays("Myanmar", "mm");
			p = new Pays("Namibie", "na");
			p = new Pays("Nauru", "nr");
			p = new Pays("Népal", "np");
			p = new Pays("Pays-Bas", "nl");
			p = new Pays("Nouvelle-Zélande", "nz");
			p = new Pays("Nicaragua", "ni");
			p = new Pays("Niger", "ne");
			p = new Pays("Nigeria", "ng");
			p = new Pays("Norvège", "no");
			p = new Pays("Oman", "om");
			p = new Pays("Pakistan", "pk");
			p = new Pays("Palaos", "pw");
			p = new Pays("Palestine", "ps");
			p = new Pays("Panama", "pa");
			p = new Pays("Papouasie-Nouvelle-Guinée", "pg");
			p = new Pays("Paraguay", "py");
			p = new Pays("Pérou", "pe");
			p = new Pays("Philippines", "ph");
			p = new Pays("Pologne", "pl");
			p = new Pays("Portugal", "pt");
			p = new Pays("Qatar", "qa");
			p = new Pays("Roumanie", "ro");
			p = new Pays("Russie", "ru");
			p = new Pays("Rwanda", "rw");
			p = new Pays("Saint-Christophe-et-Niévès", "kn");
			p = new Pays("Sainte-Lucie", "lc");
			p = new Pays("Saint-Vincent-et-les-Grenadines", "vc");
			p = new Pays("Samoa", "ws");
			p = new Pays("Saint-Marin", "sm");
			p = new Pays("Sao Tomé-et-Principe", "st");
			p = new Pays("Arabie saoudite", "sa");
			p = new Pays("Sénégal", "sn");
			p = new Pays("Serbie", "rs");
			p = new Pays("Seychelles", "sc");
			p = new Pays("Sierra Leone", "sl");
			p = new Pays("Singapour", "sg");
			p = new Pays("Slovaquie", "sk");
			p = new Pays("Slovénie", "si");
			p = new Pays("Îles Salomon", "sb");
			p = new Pays("Somalie", "so");
			p = new Pays("Afrique du Sud", "za");
			p = new Pays("Soudan du Sud", "ss");
			p = new Pays("Sri Lanka", "lk");
			p = new Pays("Soudan", "sd");
			p = new Pays("Suriname", "sr");
			p = new Pays("Swaziland", "sz");
			p = new Pays("Suède", "se");
			p = new Pays("Suisse", "ch");
			p = new Pays("Syrie", "sy");
			p = new Pays("Tadjikistan", "tj");
			p = new Pays("Tanzanie", "tz");
			p = new Pays("Thaïlande", "th");
			p = new Pays("Togo", "tg");
			p = new Pays("Tonga", "to");
			p = new Pays("Trinité-et-Tobago", "tt");
			p = new Pays("Tunisie", "tn");
			p = new Pays("Turquie", "tr");
			p = new Pays("Turkménistan", "tm");
			p = new Pays("Tuvalu", "tv");
			p = new Pays("Ouganda", "ug");
			p = new Pays("Ukraine", "ua");
			p = new Pays("Émirats arabes unis", "ae");
			p = new Pays("Royaume-Uni", "gb");
			p = new Pays("États-Unis", "us");
			p = new Pays("Uruguay", "uy");
			p = new Pays("Ouzbékistan", "uz");
			p = new Pays("Vanuatu", "vu");
			p = new Pays("Cité du Vatican", "va");
			p = new Pays("Venezuela", "ve");
			p = new Pays("Viêt Nam", "vn");
			p = new Pays("Yémen", "ye");
			p = new Pays("Zambie", "zm");
			p = new Pays("Zimbabwe", "zw");

			
			List<Joueur> j1 = new ArrayList<>();
			j1.add(new Joueur("Martin", "Larsson", "Rekkles", "rekklesImg", Poste.ADC, Pays.getById(167), Optional.empty()));
			j1.add(new Joueur("Gabriël", "Rau", "Bwipo", "bwipoImg", Poste.JUNGLE, Pays.getById(167), Optional.empty()));
			j1.add(new Joueur("Oskar", "Boderek", "Selfmade", "FakerImg", Poste.MIDLANE, Pays.getById(167), Optional.empty()));
			j1.add(new Joueur("Elias", "Edlund", "Upset", "upsetImg", Poste.SUPPORT, Pays.getById(167), Optional.empty()));
			j1.add(new Joueur("Zdravets", "Galabov", "Hylissang", "zywooImg", Poste.TOPLANE, Pays.getById(167), Optional.empty()));

			List<Joueur> j2 = new ArrayList<>();
			j2.add(new Joueur("Marek", "Bazsó", "Humanoid", "rekklesImg", Poste.MIDLANE, Pays.getById(53), Optional.empty()));
			j2.add(new Joueur("Zhiqiang", "Zhao", "Shadow", "bwipoImg", Poste.JUNGLE, Pays.getById(53), Optional.empty()));
			j2.add(new Joueur("Matyáš", "Orság", "Carzzy", "FakerImg", Poste.ADC, Pays.getById(53), Optional.empty()));
			j2.add(new Joueur("Norman", "Kaiser", "Kaiser", "upsetImg", Poste.SUPPORT, Pays.getById(53), Optional.empty()));
			j2.add(new Joueur("Andre", "Guerreiro", "Orome", "zywooImg", Poste.TOPLANE, Pays.getById(53), Optional.empty()));

			List<Joueur> j3 = new ArrayList<>();
			j3.add(new Joueur("Erlend", "Holm", "Nukeduck", "rekklesImg", Poste.MIDLANE, Pays.getById(184), Optional.empty()));
			j3.add(new Joueur("Mads", "Brock-Pedersen", "Broxah", "bwipoImg", Poste.JUNGLE, Pays.getById(184), Optional.empty()));
			j3.add(new Joueur("Edward", "Abgaryan", "Tactical", "FakerImg", Poste.ADC, Pays.getById(184), Optional.empty()));
			j3.add(new Joueur("Elias", "Lipp", "CoreJJ", "upsetImg", Poste.SUPPORT, Pays.getById(184), Optional.empty()));
			j3.add(new Joueur("Barney", "Morris", "Alphari", "zywooImg", Poste.TOPLANE, Pays.getById(184), Optional.empty()));

			List<Joueur> j4 = new ArrayList<>();
			j4.add(new Joueur("Lukas", "Rossander", "gla1ve", "rekklesImg", Poste.TOPLANE, Pays.getById(46), Optional.empty()));
			j4.add(new Joueur("Marcin", "Jankowski", "Jankos", "bwipoImg", Poste.JUNGLE, Pays.getById(46), Optional.empty()));
			j4.add(new Joueur("Rasmus", "Winther", "Caps", "FakerImg", Poste.MIDLANE, Pays.getById(46), Optional.empty()));
			j4.add(new Joueur("Casper", "Kobberup", "Kobbe", "upsetImg", Poste.ADC, Pays.getById(46), Optional.empty()));
			j4.add(new Joueur("Mihael", "Mehle", "Mikyx", "zywooImg", Poste.SUPPORT, Pays.getById(46), Optional.empty()));

			List<Joueur> j5 = new ArrayList<>();
			j5.add(new Joueur("Denis", "Sharipov", "electronic", "rekklesImg", Poste.JUNGLE, Pays.getById(181), Optional.empty()));
			j5.add(new Joueur("Kirill", "Mikhaylov", "Boombl4", "bwipoImg", Poste.SUPPORT, Pays.getById(181), Optional.empty()));
			j5.add(new Joueur("Ilya", "Zalutskiy", "Perfecto", "FakerImg", Poste.TOPLANE, Pays.getById(181), Optional.empty()));
			j5.add(new Joueur("Valerij", "Vakhovsjkyj", "B1T", "upsetImg", Poste.MIDLANE, Pays.getById(181), Optional.empty()));
			j5.add(new Joueur("Oleksandr","Kostyljev", "s1mple", "zywooImg", Poste.ADC, Pays.getById(181), Optional.empty()));

			List<Joueur> j6 = new ArrayList<>();
			j6.add(new Joueur("Nemanja", "Isaković", "huNter-", "rekklesImg", Poste.JUNGLE, Pays.getById(3), Optional.empty()));
			j6.add(new Joueur("Nemanja", "Kovač", "nexa", "bwipoImg", Poste.MIDLANE, Pays.getById(3), Optional.empty()));
			j6.add(new Joueur("François", "Dubé-Bourgeois", "AmaNEk", "FakerImg", Poste.SUPPORT, Pays.getById(3), Optional.empty()));
			j6.add(new Joueur("Nikola", "Kovač", "NiKo", "upsetImg", Poste.TOPLANE, Pays.getById(3), Optional.empty()));
			j6.add(new Joueur("Helvijs", "Saukants", "broky", "zywooImg", Poste.ADC, Pays.getById(3), Optional.empty()));

			List<Joueur> j7 = new ArrayList<>();
			j7.add(new Joueur("Timothy", "Ta", "autimatic", "rekklesImg", Poste.JUNGLE, Pays.getById(184), Optional.empty()));
			j7.add(new Joueur("William", "Lo", "RUSH", "bwipoImg", Poste.MIDLANE, Pays.getById(184), Optional.empty()));
			j7.add(new Joueur("Damian", "Steele", "daps", "FakerImg", Poste.SUPPORT, Pays.getById(184), Optional.empty()));
			j7.add(new Joueur("Kenneth", "Schiller", "koosta", "upsetImg", Poste.TOPLANE, Pays.getById(184), Optional.empty()));
			j7.add(new Joueur("Michael", "Grzesiek", "shroud", "zywooImg", Poste.ADC, Pays.getById(184), Optional.empty()));

			List<Joueur> j8 = new ArrayList<>();
			j8.add(new Joueur("Dan", "Herlocher", "apEX", "rekklesImg", Poste.JUNGLE, Pays.getById(61), Optional.empty()));
			j8.add(new Joueur("Cédric", "Guipouy", "RpK", "bwipoImg", Poste.MIDLANE, Pays.getById(61), Optional.empty()));
			j8.add(new Joueur("Mathieu", "Herbaut", "ZywOo", "FakerImg", Poste.SUPPORT, Pays.getById(61), Optional.empty()));
			j8.add(new Joueur("Alex", "McMeekin", "ALEX", "upsetImg", Poste.TOPLANE, Pays.getById(61), Optional.empty()));
			j8.add(new Joueur("Richard", "Papillon", "shox", "zywooImg", Poste.ADC, Pays.getById(61), Optional.empty()));

			List<Joueur> j9 = new ArrayList<>();
			j9.add(new Joueur("Lee", "Sang-hyeok", "Faker", "rekklesImg", Poste.MIDLANE, Pays.getById(90), Optional.empty()));
			j9.add(new Joueur("Kim", "Chang-dong", "Canna", "bwipoImg", Poste.TOPLANE, Pays.getById(90), Optional.empty()));
			j9.add(new Joueur("Moon", "Tae-hyeon", "Cuzz", "FakerImg", Poste.JUNGLE, Pays.getById(90), Optional.empty()));
			j9.add(new Joueur("Park", "Jin-seong", "Teddy", "upsetImg", Poste.ADC, Pays.getById(90), Optional.empty()));
			j9.add(new Joueur("Lee", "Ho-seong", "Effort", "zywooImg", Poste.SUPPORT, Pays.getById(90), Optional.empty()));

			List<Joueur> j10 = new ArrayList<>();
			j10.add(new Joueur("Kim", "Tae-sang", "Khan", "rekklesImg", Poste.TOPLANE, Pays.getById(37), Optional.empty()));
			j10.add(new Joueur("Gao", "Sheng", "Tian", "bwipoImg", Poste.JUNGLE, Pays.getById(37), Optional.empty()));
			j10.add(new Joueur("Kim", "Tae-min", "Doinb", "FakerImg", Poste.MIDLANE, Pays.getById(37), Optional.empty()));
			j10.add(new Joueur("Lin", "Wei-Ting", "Lwx", "upsetImg", Poste.ADC, Pays.getById(37), Optional.empty()));
			j10.add(new Joueur("Liu", "Qing-Song", "Crisp", "zywooImg", Poste.SUPPORT, Pays.getById(37), Optional.empty()));

			List<List<Joueur>> listeDraft = new ArrayList<>();
			listeDraft.add(j1);
			listeDraft.add(j2);
			listeDraft.add(j3);
			listeDraft.add(j4);
			listeDraft.add(j5);
			listeDraft.add(j6);
			listeDraft.add(j7);
			listeDraft.add(j8);
			listeDraft.add(j9);
			listeDraft.add(j10);

			HashMap<Equipe, Integer> classement = new HashMap<>();
			List<Equipe> equipes = new ArrayList<>();
			
			equipes.add(new Equipe("Fnatic", "FNC", "fnatic", Pays.getById(167), 1110));
			equipes.add(new Equipe("MAD Lions", "MAD", "MAD_Lions", Pays.getById(53), 1500));
			equipes.add(new Equipe("Team Liquid", "TL", "teamLiquid", Pays.getById(184), 2000));
			equipes.add(new Equipe("Astralis", "AST", "Astralis", Pays.getById(46), 2400));
			equipes.add(new Equipe("Natus Vincere", "NV", "navi", Pays.getById(181), 1670));
			equipes.add(new Equipe("G2 Esports", "G2", "icon", Pays.getById(3), 1900));
			equipes.add(new Equipe("Cloud9", "C9", "cloud9", Pays.getById(184), 1050));
			equipes.add(new Equipe("Team Vitality", "VIT", "TeamVitality", Pays.getById(61), 1001));
			equipes.add(new Equipe("T1", "T1", "T1", Pays.getById(37), 1000));
			equipes.add(new Equipe("FunPlus Phoenix", "FPX", "FunPlus_Phoenix", Pays.getById(37), 1000));

			for (Equipe equipe : equipes) {
				classement.put(equipe, 0);
				for(List<Joueur> joueurs : listeDraft){
					for(Joueur joueur : listeDraft.get(equipes.indexOf(equipe))){
						joueur.setId_equipe(Optional.of(equipe.getId()));
						Joueur.update(joueur);
						//equipe.addJoueur(joueur);
						//Equipe.update(equipe);
					}
				}
			}
			
			
			List<Arbitre> arbitres = new ArrayList<>();
			arbitres.add(new Arbitre("Doe", "Jane", "arbitre"));
			arbitres.add(new Arbitre("Smith", "Bob", "arbitre"));
			arbitres.add(new Arbitre("Johnson", "Emily", "arbitre"));
			arbitres.add(new Arbitre("Brown", "Olivia", "arbitre"));
			arbitres.add(new Arbitre("Davis", "Sophia", "arbitre"));
			arbitres.add(new Arbitre("Miller", "Emma", "arbitre"));
			arbitres.add(new Arbitre("Wilson", "Liam", "arbitre"));
			arbitres.add(new Arbitre("Taylor", "Noah", "arbitre"));
			arbitres.add(new Arbitre("Anderson", "Ava", "arbitre"));
			arbitres.add(new Arbitre("Thomas", "Isabella", "arbitre"));
			arbitres.add(new Arbitre("Jackson", "Mia", "arbitre"));
			arbitres.add(new Arbitre("White", "Charlotte", "arbitre"));

			Admin a = new Admin("admin","1234");
			
			new Saison(2024, "Saison 2024", "", new HashSet<>(arbitres), classement, false, true);
			
			Set<Arbitre> listeArbitres = new HashSet<>(arbitres);

			List<Tournoi> listeTournois = new ArrayList<>();
			
			HashMap<Equipe, Integer> equipeTournoi1 = new HashMap<>();
			for (int i = 0; i < 8; i++) {
				equipeTournoi1.put(equipes.get(i), 0);
			}
			listeTournois.add(new Tournoi("Worlds 2024", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 25), "tournoi", NiveauTournoi.INTERNATIONAL, equipeTournoi1, listeArbitres, false));

			HashMap<Equipe, Integer> equipeTournoi2 = new HashMap<>();
			for (int i = 0; i < 10; i++) {
				if (i % 2 == 0) {
					if(equipeTournoi2.size() <= 6) {
						equipeTournoi2.put(equipes.get(i), 0);
					}
				}
			}
			listeTournois.add(new Tournoi("LCS 2024", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 12), "tournoi", NiveauTournoi.NATIONAL, equipeTournoi2, listeArbitres, true));
			
			HashMap<Equipe, Integer> equipeTournoi3 = new HashMap<>();
			for (int i = 0; i < 10; i++) {
				if (i % 2 == 1) {
					if(equipeTournoi3.size() <= 4) {
						equipeTournoi3.put(equipes.get(i), 0);
					}
				}
			}
			listeTournois.add(new Tournoi("LCK 2024", LocalDate.of(2024, 1, 13), LocalDate.of(2024, 1, 19), "tournoi", NiveauTournoi.LOCAL, equipeTournoi3, listeArbitres, false));
			
			for (int i=0 ; i<3 ; i++) {
				listeTournois.get(i).setLogin("arbitre" + i);
				listeTournois.get(i).setPassword("1234");
				Tournoi.update(listeTournois.get(i));
			}
			
			for (Tournoi t : listeTournois) {
				if (t.getDateFin().isBefore(LocalDate.now())) {
					for (Match m : Match.getByTournoi(t)) {
						m.setVictorious(m.getEquipe1());
					}
					t.cloturerPool();
					for (Match m : Match.getByTournoi(t)) {
						if (m.getType() != TypeMatch.POOL) {
							m.setVictorious(m.getEquipe1());
						}
					}
					t.cloturerTournoi();
				}
			}
			
			statement.close();
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void dropTable(String table){
		try {
			Statement statement = this.conn.createStatement();
			statement.execute("DROP TABLE " + table);
			statement.close();
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

    public String getUrl() {
		return this.url;
    }
}
