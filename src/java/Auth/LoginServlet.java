/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auth;

import FHealth.BaseServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends BaseServlet {

    @Override
    public void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        String redir = getStringParam(request, "redir", "");
        System.out.println("Redir GET: " + redir);
        request.setAttribute("redir", redir);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/login.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            UserBean user = new UserBean();
            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));

            user = UserDAO.login(user);

            if (user.isValid()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user);
                String redir = request.getParameter("redir");
                if (!redir.isEmpty() && redir != null && !redir.equals("null")) {
                    response.sendRedirect(redir);
                    System.out.println("Sent redirect with redir=" + redir);
                }
                response.sendRedirect("/FHealth/Home"); //logged-in page
                return;
            } else {
                response.sendRedirect("/FHealth/jsp/loginfail.jsp"); //error page 
                return;
            }
        } catch (Throwable theException) {
            System.out.println(theException);
        }
    }
}