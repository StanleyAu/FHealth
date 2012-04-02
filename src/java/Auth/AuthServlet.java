/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auth;

import java.util.*;
import FHealth.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stan
 */
public class AuthServlet extends BaseServlet {

    UserBean currentUser;
    protected HashSet<String> getAllow(){
        return new HashSet<String>();
    }
    protected HashSet<String> postAllow(){
        return new HashSet<String>();
    }
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
    
    protected boolean isRoleAllowed(HttpServletRequest request, 
            HttpServletResponse response, HashSet<String> allowed)
            throws ServletException, IOException {
        HashSet<String> allows = new HashSet<String> (allowed);
        System.out.println(allowed.toString());
        System.out.println(currentUser.getRoles().toString());
        allows.retainAll(currentUser.getRoles());
        if (allows.size() == 0) {
            response.sendRedirect(
                    response.encodeRedirectURL(
                    "/FHealth/denied.jsp"));
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
        if (this.isAuthValid(request, response) && 
                isRoleAllowed(request, response, getAllow())) {
            request.setAttribute("menu_items", authMenuItems());
            request.setAttribute("user", currentUser);
            processGetRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (this.isAuthValid(request, response) && 
                isRoleAllowed(request, response, postAllow())) {
            processPostRequest(request, response);
        }
    }
}
