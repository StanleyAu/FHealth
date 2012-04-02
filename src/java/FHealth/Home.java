/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Auth.*;
import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.HashSet;
/**
 *
 * @author Admin
 */
@WebServlet(name = "Home", urlPatterns = {"/Home"})
public class Home extends AuthServlet {
    
    protected HashSet getAllow(){
        HashSet<String> allow = new HashSet<String>();
        allow.add("admin");
        allow.add("patient");
        allow.add("staff");
        allow.add("finance");
        allow.add("doctor");
        return allow;
    }
    
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/home.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    
}
