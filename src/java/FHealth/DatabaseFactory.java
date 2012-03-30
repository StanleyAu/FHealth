package FHealth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author brian
 */
public class DatabaseFactory {
    private Connection _conn = null;
    private static DatabaseFactory dbf;
    
    public DatabaseFactory() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hospital_G004";
            _conn = DriverManager.getConnection(url,"user_G004","jwusmells");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }    
    
    public static DatabaseFactory getInstance() {
        if (dbf == null) {
            dbf = new DatabaseFactory();
        }
        return dbf;
    }
    
    public Connection getConnection() {
        return _conn;
    }
}
