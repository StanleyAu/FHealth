package FHealth;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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
    
    public ResultSet executeQuery(String query) {
        Connection _conn = getConnection();
        ResultSet rs = null;
        PreparedStatement pms = null;
        try {
            pms = _conn.prepareStatement(query);
            rs = pms.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pms.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs == null) {
            System.out.println("Result set is null");
        }
        return rs;
    }
    
    public ArrayList query(String searchQuery) {
        ResultSet rs = null;
        try {
            //connect to DB 
            rs = executeQuery(searchQuery);

            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            ArrayList list = new ArrayList(50);
            while (rs.next()) {
                HashMap row = new HashMap(columns);
                for (int i = 1; i <= columns; i++) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }
        }
    }
}
