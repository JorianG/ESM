//package Test;
//
//import Modele.*;
//import connect.DBConnect;
//import org.junit.Test;
//
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.HashSet;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class DAOSaisonTest extends DAOTest{
//    DBConnect dbConnect;
//
//    Pays france;
//    HashMap<Equipe, Integer> equipes = new HashMap<>();
//    HashMap<Equipe, Integer> pointTouroi = new HashMap<>();
//    Equipe equipe1;
//    HashSet<Tournoi> tournois = new HashSet<>();
//
//    Saison saison;
//    @Override
//    public void setUp() throws Exception {
//        dbConnect = DBConnect.getDbInstance();
//        //dbConnect.createTable();
//        france = new Pays("FranceT", "imgFrT");
//
//        equipe1 = new Equipe("nom", "E1","img", france, 1000);
//        Equipe equipe2 = new Equipe("nom2", "E2","img2", france, 1000);
//        Equipe equipe3 = new Equipe("nom3", "E3","img3", france, 1000);
//        Equipe equipe4 = new Equipe("nom4", "E4","img4", france, 1000);
//        equipes.put(equipe1, 15);
//        equipes.put(equipe2, 0);
//        equipes.put(equipe3, 0);
//        equipes.put(equipe4, 0);
//        pointTouroi.put(equipe1, 0);
//        pointTouroi.put(equipe2, 0);
//        pointTouroi.put(equipe3, 0);
//        pointTouroi.put(equipe4, 0);
//
//        Saison.getSaison(2022, "nomT", "logoT", tournois, equipes, true); // TODO
//
//        Tournoi t = new Tournoi("Tournoi1",
//                LocalDate.of(2022, 1,1),
//                LocalDate.of(2022, 12, 31), "iconT", NiveauTournoi.INTERNATIONAL,
//                pointTouroi, new HashSet<>(Arrays.asList(new Arbitre[]{new Arbitre("nomT", "prenomT", "photoT")})));
//        tournois.add(t);
//
//    }
//
//    @Override
//    public void tearDown() {
//        Pays.delete(france.getId());
//        for (Tournoi t: tournois) {
//            Tournoi.delete(t.getId());
//        }
//        Saison.delete(Saison.getSaison().getAnnee());
//        for (Equipe e: equipes.keySet()) {
//            Equipe.delete(e.getId());
//        }
//    }
//
//    @Override
//    public void testAdd() {
//        System.out.println(Saison.getSaison());
//        Saison s = Saison.getSaison(2021, "nomTT", "logoT", new HashSet<>(), new HashMap<>(), true);
//        Saison sGetted = Saison.getById(2021);
//        assertEquals(s, sGetted);
//        Saison.delete(2021);
//    }
//
//    @Override
//    public void testDelete() {
//        assertTrue(Saison.delete(Saison.getSaison().getAnnee()));
//    }
//
//    @Override
//    public void testUpdate() {
//        Saison saisonTMod = Saison.getSaison(2022, "nomTMod", "logoTMod", new HashSet<>(), new HashMap<>(), false);
//        assertTrue(Saison.update(saisonTMod));
//        assertEquals(saisonTMod, Saison.getById(2022));
//    }
//
//    @Override
//    public void testGetById() {
//        Saison s = Saison.getById(2022);
//        assertEquals(s, Saison.getSaison());
//    }
//
//    @Override
//    public void testGetAll() {
//        assertEquals(Saison.getAll().size(), 1);
//    }
//
//    @Test
//    public void testGetEquipeBySaison() {
//    	HashMap<Equipe, Integer> equipes = Saison.getEquipeBySaison(2022);
//    	assertEquals(equipes.size(), 4);
//    }
//
//    @Test
//    public void testGetPointsEquipe() {
//    	assertEquals(15, Saison.getPointsEquipe(Saison.getSaison(), equipe1));
//    }
//}
