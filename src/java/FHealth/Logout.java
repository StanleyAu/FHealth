package FHealth;

import Auth.AuthServlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author brian
 */
public class Logout extends AuthServlet {
    
  protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        logout(request);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/login.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}
