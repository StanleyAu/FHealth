/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import Auth.UserBean;
import com.google.gson.Gson;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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
    
    public DatabaseFactory db() {
        return DatabaseFactory.getInstance();
    }

    public ArrayList query(String searchQuery) {
        return DatabaseFactory.getInstance().query(searchQuery);
    }

    public int update(String updateQuery) {
        return DatabaseFactory.getInstance().update(updateQuery);
    }
    // Will return null if user is not logged in

    protected UserBean getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        UserBean user = (UserBean) (session.getAttribute("currentSessionUser"));
        return user; // Should return null if no user in cookie
    }

    protected boolean logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("currentSessionUser");
            return true;
        } else {
            return false;
        }
    }

    protected Integer getIntParam(HttpServletRequest request, String key,
            Integer default_value) {
        String s_param = request.getParameter(key);
        System.out.println("Not broken on getParam: " + s_param);
        Integer value;
        if (s_param == null) {
                if (default_value == null) {
                    value = null;
                }
                else {
                    value = default_value;
                }
            }
        else {
            value = Integer.parseInt(s_param);
        }
        System.out.println("Not broken on ternary op");
        return value;
    }

    protected Boolean getBoolParam(HttpServletRequest request, String key,
            Boolean default_value) {
        String s_param = request.getParameter(key);
        Boolean value = (s_param == null)
                ? default_value : Boolean.parseBoolean(s_param);
        return value;
    }

    protected String getStringParam(HttpServletRequest request, String key,
            String default_value) {
        String s_param = request.getParameter(key);
        String value = (s_param == null)
                ? default_value : s_param;
        return value;
    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected ArrayList<ArrayList> getDoctorList() {
        String sql = "SELECT id, concat(first_name,' ',last_name) doctor"
                + " from doctor order by last_name";
        ArrayList doc_list = query(sql);
        return doc_list;
    }
    
    protected ArrayList<ArrayList> getPatientList() {
        String sql = "SELECT id, concat(first_name,' ',last_name) patient"
                + " from patient order by last_name";
        ArrayList p_list = query(sql);
        return p_list;
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
