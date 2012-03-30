/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Admin
 */
@WebServlet(name = "Patient", urlPatterns = {"/Patient"})
public class Patient extends BaseServlet {

  /**
   * Processes requests for both HTTP
   * <code>GET</code> and
   * <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    List dl = new ArrayList();
    try {
      Connection conn = DatabaseFactory.getInstance()
        .getConnection();
      String sql = "select * from patient where id = 1";
      Statement s = conn.createStatement();
      s.executeQuery(sql);
      ResultSet rs = s.getResultSet();
      
      /*
       * TODO output your page here. You may use following sample code.
       */
      rs.next();
      dl.add(rs.getInt("id"));
      dl.add(rs.getString("first_name"));
      dl.add(rs.getString("last_name"));
      dl.add(rs.getString("OHIP"));
      dl.add(rs.getString("SIN"));
      dl.add(rs.getString("address"));
      dl.add(rs.getString("phone"));
      dl.add(rs.getString("current_health"));
      dl.add(rs.getInt("default_doctor_id"));
      
      rs.close();
      
      
    } catch (SQLException  e){
      e.printStackTrace();
    }
    finally{
    }
    request.setAttribute("data", dl);
    RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/patient.jsp");
    if (dispatcher != null){
      dispatcher.forward(request, response);
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}
