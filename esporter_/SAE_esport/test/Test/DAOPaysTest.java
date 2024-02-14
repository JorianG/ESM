package Test;

import Modele.Pays;
import connect.DBConnect;

import java.sql.SQLException;
import java.util.Objects;

import static org.junit.Assert.*;


public class DAOPaysTest extends DAOTest{
    DBConnect db;
    Pays pays1;
    Pays pays2;
    Pays pays3;

    @Override
    public void setUp() throws Exception {
        db = DBConnect.getDbInstance();
        pays1 = new Pays("FranceTest", "drapeauFr");
        pays2 = new Pays("ChineTest", "drapeauCh");
        pays3 = new Pays("CoréeTest", "drapeauCo");
    }

    @Override
    public void tearDown() throws SQLException {
        Pays.delete(pays1.getId());
        Pays.delete(pays2.getId());
        Pays.delete(pays3.getId());
        db.close();
    }

    @Override
    public void testAdd() {
        Pays pays = new Pays("JaponTest", "drapeauJp");
        assertEquals(pays, Pays.getById(pays.getId()));
        Pays.delete(pays.getId());
    }

    @Override
    public void testDelete() {
        assertTrue(Pays.delete(pays1.getId()));
        assertTrue(Pays.delete(pays2.getId()));
        assertTrue(Pays.delete(pays3.getId()));
    }

    @Override
    public void testUpdate() throws SQLException {
        assertTrue(Pays.update(new Pays(pays2.getId(), "Chinemod", "drapeauChmod")));
    }

    @Override
    public void testGetById() {
        Pays pays = new Pays(pays3.getId(), "CoréeTest", "drapeauCo");
        assertEquals(pays, Pays.getById(pays3.getId()));
    }

    @Override
    public void testGetAll() {
        assertEquals(9, Objects.requireNonNull(Pays.getAll()).size());
    } // TODO changer 9 par 198
}
