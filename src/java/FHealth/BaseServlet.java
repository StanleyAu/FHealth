/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import FHealth.DatabaseFactory;
import Auth.UserBean;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stan
 */
public class BaseServlet extends HttpServlet {

    public ArrayList query(String searchQuery)
            throws SQLException {
        Connection currentCon = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //connect to DB 
            currentCon = DatabaseFactory.getInstance().getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);

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
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }
            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }
                currentCon = null;
            }
        }
    }

    // Will return null if user is not logged in
    public UserBean getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        UserBean user = (UserBean) (session.getAttribute("currentSessionUser"));
        return user; // Should return null if no user in cookie
    }
}
