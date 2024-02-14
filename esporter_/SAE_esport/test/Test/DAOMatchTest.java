package Test;

import DAO.*;
import Modele.*;
import connect.DBConnect;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Classe de test pour le DAOMatch
 * @see DAOMatch
 * @author Jorian GOUAGOUT
 */
public class DAOMatchTest extends DAOTest{
    /**
     * Connection à la base de données
     */
    DBConnect dbConnect;

    List<Match> matches = new ArrayList<>();

    Set<Equipe> equipes;
    Set<Arbitre> arbitres;
    Arbitre arbitre1;
    Arbitre arbitre2;
    Arbitre arbitre3;
    Pays france;
    Pays coree;
    Tournoi tournoi;
    /**
     * Initialisation des données de test
     */
    @Override
    public void setUp() throws Exception {
        dbConnect = new DBConnect();
        //dbConnect.createTable();
        france = new Pays("FranceT", "imgFrT");
        coree = new Pays("CoréeT", "imgCoT");
        System.out.println("pays added");

        Equipe equipe1 = new Equipe("nom", "ET1","img", france, 1000);
        Equipe equipe2 = new Equipe("nom2", "ET2","img2", coree, 1000);
        Equipe equipe3 = new Equipe("nom3", "ET3","img3", france, 1000);
        Equipe equipe4 = new Equipe("nom4", "ET4","img4", coree, 1000);
        Equipe equipe5 = new Equipe("nom5", "ET5","img5", france, 1000);
        Equipe equipe6 = new Equipe("nom6", "ET6","img6", coree, 1000);

        HashMap<Equipe, Integer> pointsSaison = new HashMap<>();
        HashMap<Equipe, Integer> pointTournoi = new HashMap<>();
        equipes = new HashSet<>();
        equipes.add(equipe1);
        equipes.add(equipe2);
        equipes.add(equipe3);
        equipes.add(equipe4);
        equipes.add(equipe5);
        equipes.add(equipe6);
        for (Equipe equipe : equipes) {
            pointsSaison.put(equipe, 0);
            pointTournoi.put(equipe, 0);
        }

        System.out.println("equipe added");

        arbitre1 = new Arbitre("nomT1","prenomT1","imgT1");
        arbitre2 = new Arbitre("nomT2","prenomT2","imgT2");
        arbitre3 = new Arbitre("nomT3","prenomT3","imgT3");

        arbitres = new HashSet<>();
        arbitres.add(arbitre1);
        arbitres.add(arbitre2);
        arbitres.add(arbitre3);

        //Saison.getSaison(2022, "saisonTest", "img", new HashSet<>(), pointsSaison, true);

        tournoi = new Tournoi("nomTT1",
                LocalDate.of(2000,1,1),
                LocalDate.of(2000,12,31),
                "img",NiveauTournoi.LOCAL,pointTournoi,false);

        Match match1 = new Match(
                new CustomDateTime(2000, 3, 12, 15, 0),
                new CustomDateTime(2000, 3, 12, 16, 0),
                equipe1,equipe2,arbitre1,tournoi,TypeMatch.POOL);
        Match match2 = new Match(
                new CustomDateTime(2000, 3, 12, 12, 0),
                new CustomDateTime(2000, 3, 12, 13, 0),
                equipe3,equipe4,arbitre2,tournoi,TypeMatch.POOL);
        Match match3 = new Match(
                new CustomDateTime(2000, 3, 15, 21, 0),
                new CustomDateTime(2000, 3, 15, 22, 0),
                equipe5,equipe6,arbitre3,tournoi,TypeMatch.POOL);

        matches.add(match1);
        matches.add(match2);
        matches.add(match3);

    }

    /**
     * Suppression des données de test de la base de données
     * @throws SQLException
     */
    @Override
    public void tearDown() throws SQLException {

        for (Match match : matches) {
            Match.delete(match.getId());
        }
        Saison.delete(2022);
        Tournoi.delete(tournoi.getId());
        for (Equipe equipe : equipes) {
            Equipe.delete(equipe.getId());
        }
        for (Arbitre arbitre : arbitres) {
            Arbitre.delete(arbitre.getId());
        }
        Pays.delete(france.getId());
        Pays.delete(coree.getId());
        System.out.println("tearDown");
        dbConnect.close();
    }

    /**
     * Test de la méthode add de DAOMatch
     * @see DAOMatch#add(Match)
     */
    @Override
    public void testAdd() {
        Match match = new Match(new CustomDateTime(2000, 5, 1, 15, 0), new CustomDateTime(2000, 5, 1, 16, 0),(Equipe) equipes.toArray()[0],(Equipe) equipes.toArray()[1],arbitre1,tournoi,TypeMatch.POOL);
        assertEquals(match, Match.getById(match.getId()));
        Match.delete(match.getId());
    }

    /**
     * Test de la méthode delete de DAOMatch
     */
    @Override
    public void testDelete() {
        assertTrue(Match.delete(matches.get(0).getId()));
        assertNull(Match.getById(matches.get(0).getId()));
    }


    /**
     * Test de la méthode update de DAOMatch
     * @see DAOMatch#update(Match)
     */
    @Override
    public void testUpdate() {
        matches.get(0).setEquipes(
                new Equipe(2, "nom2", "ET2", "img2", coree, 1000),
                new Equipe(3, "nom3", "ET3", "img3", france, 1000));
        Match.update(matches.get(0));
        assertEquals(matches.get(0), Match.getById(matches.get(0).getId()));
        assertNotEquals(matches.get(1), Match.getById(matches.get(0).getId()));
    }
    /**
     * Test de la méthode getById de DAOMatch
     */
    @Override
    public void testGetById()  {
        Match m = Match.getById(matches.get(0).getId());
        System.out.println(m);
        assertEquals(matches.get(0),m);
    }

    /**
     * Test de la méthode getAll de DAOMatch
     * @see DAOMatch#getRequestGetAll()
     */
    @Override
    public void testGetAll() {
        assertEquals(15, Match.getAll().size());
    }

    @Test
    public void testGetByTournoi() {
        assertEquals(3, Match.getByTournoi(tournoi).size());
    }

    @Test
    public void testGetByDate() {
        assertEquals(2, Match.getByDate(matches.get(0).getDateDebutMatch().getDate()).size());
    }

}
