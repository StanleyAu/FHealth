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

/**
 *
 * @author Stan
 */
public class RegisterServlet extends BaseServlet {

    @Override
    public void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/register.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            RegisterBean reg = new RegisterBean();
            reg.setFirstName(request.getParameter("firstname"));
            reg.setLastName(request.getParameter("lastname"));
            reg.setUsername(request.getParameter("username"));
            reg.setPassword(request.getParameter("password"));
            reg.setOhip(request.getParameter("ohip"));
            reg.setSin(request.getParameter("sin"));
            reg.setAddress(request.getParameter("address"));
            reg.setPhone(request.getParameter("phone"));

            UserBean user = UserDAO.register(reg);

            if (user.isValid()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user);
                response.sendRedirect("/FHealth/jsp/loginsuccess.jsp"); //logged-in page      		
            } else {
                response.sendRedirect("/FHealth/jsp/loginfail.jsp"); //error page 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
