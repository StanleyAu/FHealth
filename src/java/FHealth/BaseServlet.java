/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import Auth.UserBean;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stan
 */
public class BaseServlet extends HttpServlet {

    public ArrayList query(String searchQuery)
            throws SQLException {
        return DatabaseFactory.getInstance().query(searchQuery);
    }

    // Will return null if user is not logged in
    public UserBean getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        UserBean user = (UserBean) (session.getAttribute("currentSessionUser"));
        return user; // Should return null if no user in cookie
    }
}
