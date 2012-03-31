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
    Integer p_id = Integer.parseInt(request.getParameter("patient_id"));
    String sql = String.format(
            "SELECT p.first_name 'First Name',"
            + "p.last_name 'Last Name',"
            + "p.OHIP 'OHIP',"
            + "p.SIN 'SIN',"
            + "p.address 'Address',"
            + "p.phone 'Phone',"
            + "p.current_health 'Current Health',"
            + "concat(d.first_name, ' ', d.last_name) doctor "
            + "from patient p left join "
            + "doctor d on p.default_doctor_id = d.id "
            + "where p.id = %d", p_id);
    ArrayList p_data = query(sql);
    if (p_data.isEmpty()){
      PrintWriter out = response.getWriter();
      out.print("invalid patient id");
      return;
    }
    System.out.println(p_data.get(0));
    request.setAttribute("p_data", p_data.get(0));
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
