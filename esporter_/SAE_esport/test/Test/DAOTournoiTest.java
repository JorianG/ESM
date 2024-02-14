package Test;

import Modele.*;
import connect.DBConnect;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DAOTournoiTest extends DAOTest{
    DBConnect dbConnect;

    HashMap<Equipe, Integer> equipes = new HashMap<>();
    Set<Arbitre> arbitres = new HashSet<>();
    Equipe equipe1;
    Tournoi t;
    Pays france;
    Pays coree;

    Saison saison;

    @Override
    public void setUp() throws Exception {
        dbConnect = DBConnect.getDbInstance();
        france = new Pays("FranceT", "imgFrT");
        coree = new Pays("CoréeT", "imgCoT");

        equipe1 = new Equipe("nomT", "ET1","img", france, 1000);
        Equipe equipe2 = new Equipe("nom2T", "ET2","img2", coree, 1000);
        Equipe equipe3 = new Equipe("nom3T", "ET3","img3", coree, 1000);
        Equipe equipe4 = new Equipe("nom4T", "ET4","img4", coree, 1000);
        equipes.put(equipe1, 12);
        equipes.put(equipe2, 0);
        equipes.put(equipe3, 0);
        equipes.put(equipe4, 0);

        Arbitre arbitre1 = new Arbitre("PierreT", "JeanT", "photo");
        Arbitre arbitre2 = new Arbitre("JeanT", "PierreT", "photo");
        Arbitre arbitre3 = new Arbitre("PierreT", "photoT", "Jean");
        arbitres.add(arbitre1);
        arbitres.add(arbitre2);
        arbitres.add(arbitre3);

        HashMap<Equipe, Integer> classements = new HashMap<>();
        for (Equipe e: equipes.keySet()) {
            classements.put(e, 0);
        }

        //saison = Saison.getSaison(2000, "nomT", "logoT", new HashSet<>(), classements, true);

        t = new Tournoi("Tournoi1T", LocalDate.of(2000, 2, 12),
                LocalDate.of(2000, 2, 15), "logoTournoi", NiveauTournoi.INTERNATIONAL, equipes, false);
    }

    @Override
    public void tearDown() throws SQLException {
        Tournoi.delete(t.getId());
        Saison.delete(saison.getAnnee());
        for (Equipe e : equipes.keySet()) {
            Equipe.delete(e.getId());
        }
        for (Arbitre a : arbitres) {
            Arbitre.delete(a.getId());
        }
        Pays.delete(france.getId());
        Pays.delete(coree.getId());
        dbConnect.close();
    }

    @Override
    public void testAdd() throws SQLException {
        Tournoi t2 = new Tournoi("Tournoi2T",
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 31),
                "icontournoi",
                NiveauTournoi.NATIONAL,
                equipes, false);
        assertEquals(t2, Tournoi.getById(t2.getId()));
        Tournoi.delete(t2.getId());
    }

    @Override
    public void testDelete() {
        assertTrue(Tournoi.delete(t.getId()));
    }

    @Override
    public void testUpdate() {
//        Tournoi t2 = new Tournoi(t.getId(), "Tournoi1Tmodif",
//                LocalDate.of(2000, 2, 15),
//                LocalDate.of(2000, 2, 18),
//                "logoTournoi",
//                NiveauTournoi.INTERNATIONAL,
//                equipes, arbitres);
//        assertTrue(Tournoi.update(t2));
//        assertEquals(t2, Tournoi.getById(t.getId()));
    }

    @Override
    public void testGetById() {
        Tournoi t = Tournoi.getById(this.t.getId());
        assertEquals("Tournoi1T", t.getNom());
        assertEquals(t.getDateDebut(), LocalDate.of(2000, 2, 12));
        assertEquals(t.getDateFin(), LocalDate.of(2000, 2, 15));
        assertEquals("logoTournoi", t.getImage());
        assertEquals(t.getNiveau(), NiveauTournoi.INTERNATIONAL);
        assertEquals(4, t.getListEquipes().size());
        assertEquals(3, t.getPoolArbitres().size());
    }

    @Override
    public void testGetAll() {
        assertEquals(5, Tournoi.getAll().size());
    }

    @Test
    public void testGetEquipesForTournoi() {
        assertEquals(4, Tournoi.getEquipesForTournoi(t.getId()).size());
    }

    @Test
    public void testGetArbitresForTournoi() {
        assertEquals(3, Tournoi.getArbitresForTournoi(t.getId()).size());
    }

    @Test
    public void testGetBySaison() {
        assertEquals(1, Tournoi.getBySaison(2000).size());
    }

    @Test
    public void testGetPointsEquipe() {
        assertEquals(12, Tournoi.getEquipePoints(t, equipe1));
    }
}
