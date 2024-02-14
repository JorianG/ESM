package Test;

import Modele.Equipe;
import Modele.Joueur;
import Modele.Pays;
import Modele.Poste;
import connect.DBConnect;
import org.junit.*;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DAOJoueurTest extends DAOTest {

    DBConnect db;
    Joueur j;
    Joueur j2;
    Equipe e;
    int id;

    @Before
    public void setUp() throws Exception {
        db = DBConnect.getDbInstance();
        e = new Equipe("nomTest", "ETK","img", Pays.getById(1), 1000);
        j = new Joueur("nomJTest", "prenomJTest", "pseudoJTest", "imgJTest", Poste.ADC, Pays.getById(2), Optional.of(e.getId()));
        id = j.getId();
        j2 = new Joueur("nomJ2Test", "prenomJ2Test", "pseudoJ2Test", "imgJ2Test", Poste.ADC, Pays.getById(2), Optional.empty());
    }

    @After
    public void tearDown() throws SQLException {
        Joueur.delete(j.getId());
        Joueur.delete(j2.getId());
        Equipe.delete(e.getId());
        db.close();
    }

    @Override
    public void testAdd() throws SQLException {
        Joueur jT = new Joueur("nomTestAdd", "prenomTestAdd", "pseudoTestAdd", "imgTestAdd", Poste.TOPLANE, Pays.getById(1), Optional.empty());
        assertEquals(jT, Joueur.getById(jT.getId()));
        Joueur.delete(jT.getId());
    }

    @Override
    public void testDelete() throws SQLException {
        assertTrue(Joueur.delete(j.getId()));
        assertNull(Joueur.getById(j.getId()));
    }

    @Override
    public void testUpdate() throws SQLException {
    	Joueur j = Joueur.getById(id);
    	j.setNom("nomDeTestModif");
        assertTrue(Joueur.update(j));
    }

    @Override
    public void testGetById() throws SQLException {
        assertEquals(j, Joueur.getById(id));
    }

    @Override
    public void testGetAll() throws SQLException {
        assertEquals(52, Joueur.getAll().size());
    }

    @Test
    public void testGetByEquipe() throws SQLException {
        assertEquals(1, Joueur.getByEquipe(e).size());
    }

    @Test
    public void testGetAllSansEquipe() throws SQLException {
        assertEquals(1, Joueur.getAllSansEquipe().size());
    }
}