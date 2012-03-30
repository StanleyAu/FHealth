/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auth;

import FHealth.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends BaseServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        response.sendRedirect("/FHealth/jsp/login.jsp");
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            UserBean user = new UserBean();
            user.setUsername(request.getParameter("un"));
            user.setPassword(request.getParameter("pw"));

            user = UserDAO.login(user);

            if (user.isValid()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user);
                response.sendRedirect("/FHealth/jsp/loginsuccess.jsp"); //logged-in page      		
            } else {
                response.sendRedirect("/FHealth/jsp/loginfail.jsp"); //error page 
            }
        } catch (Throwable theException) {
            System.out.println(theException);
        }
    }
}