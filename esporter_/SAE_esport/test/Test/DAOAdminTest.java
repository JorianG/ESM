package Test;

import DAO.DAOAdmin;
import Modele.Admin;
import connect.DBConnect;
import vue.APP;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DAOAdminTest extends DAOTest {

    Admin admin;
    Admin admin2;

    @Override
    public void setUp() throws Exception {
        admin = new Admin("Jean", "motDePasse");
        admin2 = new Admin("JP", "motDePasse");
    }

    @Override
    public void tearDown() throws SQLException {
        Admin.delete(admin.getId());
        Admin.delete(admin2.getId());
    }

    @Override
    public void testAdd() throws SQLException {
        Admin a = new Admin("adminAdded", "admin");
        assertEquals(a, Admin.getById(a.getId()));
        Admin.delete(a.getId());
    }

    @Override
    public void testDelete() throws SQLException {
        assertTrue(Admin.delete(admin.getId()));
    }

    @Override
    public void testUpdate() throws SQLException {
        assertTrue(Admin.update(new Admin(admin2.getId(),"JP", "nouveauMotDePasse")));
    }

    @Override
    public void testGetById() throws SQLException {
        Admin admin = new Admin(this.admin.getId(), "Jean", "motDePasse");
        assertEquals(admin, Admin.getById(admin.getId()));
    }

    @Override
    public void testGetAll() throws SQLException {
        assertEquals(3, Admin.getAll().size());
    }
}