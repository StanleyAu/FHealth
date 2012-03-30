/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import Auth.UserBean;
import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException{
    }
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException{
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
      processGetRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
      processPostRequest(request, response);
    }
    
}
