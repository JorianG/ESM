package Test;

import DAO.*;
import Modele.Arbitre;
import connect.DBConnect;
import org.junit.*;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DAOArbitreTest extends DAOTest {

    Arbitre a;
    Arbitre a2;

    @Before
    public void setUp() throws Exception {
        a = new Arbitre("jean", "prenomTest", "imgTest");
        a2 = new Arbitre( "Paul", "prenom2Test", "img2Test");
    }

    @After
    public void tearDown() throws SQLException {
        Arbitre.delete(a.getId());
        Arbitre.delete(a2.getId());
    }

    @Override
    public void testAdd() {
        Arbitre a3 = new Arbitre("nom3Test", "prenom3Test", "img3Test");
        assertEquals(a3, Arbitre.getById(a3.getId()));
        Arbitre.delete(a3.getId());
    }

    @Override
    public void testDelete() {
        assertTrue(Arbitre.delete(a.getId()));
    }

    @Override
    public void testUpdate() {
        assertTrue(Arbitre.update(new Arbitre(a.getId(),  "nomupdate", "prenomupdate", "imgupdate")));
    }

    @Override
    public void testGetById() {
        assertEquals(a, Arbitre.getById(a.getId()));
    }

    @Override
    public void testGetAll() {
        assertEquals(14, Arbitre.getAll().size());
    }

    @Test
    public void testGetByNom() {
        assertEquals(a, Arbitre.getByNom("ea").get(0));
    }
}