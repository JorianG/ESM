package Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public abstract class DAOTest {

    @Before
    public abstract void setUp() throws Exception;

    @After
    public abstract void tearDown() throws SQLException;

    @Test
    public abstract void testAdd() throws SQLException;

    @Test
    public abstract void testDelete() throws SQLException;

    @Test
    public abstract void testUpdate() throws SQLException;

    @Test
    public abstract void testGetById() throws SQLException;

    @Test
    public abstract void testGetAll() throws SQLException;
}