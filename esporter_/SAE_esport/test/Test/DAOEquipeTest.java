package Test;

import DAO.DAOEquipe;
import DAO.DAOPays;
import Modele.Equipe;
import Modele.Joueur;
import Modele.Pays;
import Modele.Poste;
import connect.DBConnect;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Classe de test pour le DAOEquipe
 * @see DAOEquipe
 * @author Jorian GOUAGOUT
 */
public class DAOEquipeTest extends DAOTest{
    /**
     * Connection à la base de données
     * @throws Exception
     */
    DBConnect db;
    Equipe equipeTest;
    List<Joueur> joueursTest;
    int id;
    
    /**
     * Initialisation des données de test
     * @throws Exception
     */
    @Override
    public void setUp() throws Exception {
        equipeTest = new Equipe("nomT", "EQT","imgT", Pays.getById(1), 1000);
        joueursTest = new ArrayList<>();
        joueursTest.add(new Joueur("nomJ1", "prenomJ1", "J1", "imgJ1", Poste.ADC, Pays.getById(1), Optional.of(equipeTest.getId())));
        joueursTest.add(new Joueur("nomJ2", "prenomJ2", "J2", "imgJ2", Poste.TOPLANE, Pays.getById(1), Optional.of(equipeTest.getId())));
        joueursTest.add(new Joueur("nomJ3", "prenomJ3", "J3", "imgJ3", Poste.JUNGLE, Pays.getById(1), Optional.of(equipeTest.getId())));
        joueursTest.add(new Joueur("nomJ4", "prenomJ4", "J4", "imgJ4", Poste.MIDLANE, Pays.getById(1), Optional.of(equipeTest.getId())));
        joueursTest.add(new Joueur("nomJ5", "prenomJ5", "J5", "imgJ5", Poste.ADC, Pays.getById(1), Optional.of(equipeTest.getId())));
        id = equipeTest.getId();
    }

    /**
     * Suppression des données de test de la base de données
     * @throws SQLException
     */
    @Override
    public void tearDown() throws SQLException {
        Equipe.delete(equipeTest.getId());
        for (Joueur j : joueursTest) {
            Joueur.delete(j.getId());
        }
    }

    /**
     * Test de la méthode add de DAOEquipe
     * @throws SQLException
     * @see DAOEquipe#add(Equipe)
     */
    @Override
    public void testAdd() throws SQLException {
        Equipe e = new Equipe("nomTadd", "ET","imgTadd", Pays.getById(1), 1000);
        assertEquals(e, Equipe.getById(e.getId()));
        Equipe.delete(e.getId());
    }

    /**
     * Test de la méthode delete de DAOEquipe
     * @throws SQLException
     */
    @Override
    public void testDelete() throws SQLException {
        assertTrue(Equipe.delete(equipeTest.getId()));
        assertNull(Equipe.getById(equipeTest.getId()));
    }

    /**
     * Test de la méthode update de DAOEquipe
     * @throws SQLException
     * @see DAOEquipe#update(Equipe)
     */
    @Override
    public void testUpdate() throws SQLException {
        Equipe e = Equipe.getById(equipeTest.getId());
        e.setNom("nommodif");
        assertTrue(Equipe.update(e));
    }

    /**
     * Test de la méthode getById de DAOEquipe
     * @throws SQLException
     */
    @Override
    public void testGetById() throws SQLException {
        assertEquals(equipeTest, Equipe.getById(id));
    }

    /**
     * Test de la méthode getAll de DAOEquipe
     * @throws SQLException
     * @see DAOEquipe#getRequestGetAll()
     */
    @Override
    public void testGetAll() throws SQLException {
        assertEquals(11, Equipe.getAll().size());
    }

    @Test
    public void testGetByNom() throws SQLException {
        assertEquals(equipeTest, Equipe.getByNom("nomT").get(0));
    }

    @Test
    public void testGetLastId() throws SQLException {
        assertEquals(Equipe.getAll().size(), Equipe.getLastId());
    }


}
