package connect;


import java.sql.SQLException;
//THIS IS A TEST HELLO FROM CONTROLEUR
public class ql {
    public static void main(String[] args) throws SQLException {
        DBConnect db = DBConnect.getDbInstance();
        db.createTable();
    }
}
