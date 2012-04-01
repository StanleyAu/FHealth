/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auth;

import java.util.*;
import FHealth.BaseServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stan
 */
public class AuthServlet extends BaseServlet {

    UserBean currentUser;

    protected boolean isAuthValid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        currentUser = this.getUser(request);
        String uri = request.getRequestURI();
        System.out.println(uri);
        if (currentUser == null) {
            response.sendRedirect(
                    response.encodeRedirectURL(
                    "/FHealth/login?redir="
                    + request.getRequestURI()
                    + "?" + request.getQueryString()));
            return false;
        }
        return true;
    }
    
    protected ArrayList authMenuItems(){
        ArrayList menu_items = currentUser.menuItems();
        return menu_items;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (this.isAuthValid(request, response)) {
            request.setAttribute("menu_items", authMenuItems());
            request.setAttribute("user", currentUser);
            processGetRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (this.isAuthValid(request, response)) {
            processPostRequest(request, response);
        }
    }
}
