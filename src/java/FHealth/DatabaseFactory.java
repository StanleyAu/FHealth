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
            _conn = DriverManager.getConnection(url, "user_G004", "jwusmells");
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

    public PreparedStatement getStatement(String searchQuery)
            throws SQLException {
        return getConnection().prepareStatement(searchQuery);
    }

    public ArrayList query(PreparedStatement pms) {
        ResultSet rs = null;
        try {
            rs = pms.executeQuery();

            return _query(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (pms != null) {
                try {
                    pms.close();
                } catch (Exception e) {
                }
                pms = null;
            }
        }
    }

    public ArrayList query(String searchQuery) {
        PreparedStatement pms = null;
        ResultSet rs = null;
        try {
            pms = getStatement(searchQuery);
            rs = pms.executeQuery();

            return _query(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (pms != null) {
                try {
                    pms.close();
                } catch (Exception e) {
                }
                pms = null;
            }
        }
    }

    protected ArrayList _query(ResultSet rs) {
        try {
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
