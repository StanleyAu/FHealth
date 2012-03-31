/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yeung
 */
public class UpdateDefaultDoctor extends BaseServlet {

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
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
            if (request.getParameter("fname") == null || request.getParameter("lname") == null) {
            response.setContentType("text/html;charset=UTF-8");
            List dl = new ArrayList();
            try {
                String sql = "SELECT concat(patient.id) AS p_id, "
                        + "concat(patient.first_name) AS p_first_name, "
                        + "concat(patient.last_name) AS p_last_name, "
                        + "concat(doctor.id) AS d_id "
                        + "FROM patient INNER JOIN doctor "
                        + "ON patient.default_doctor_id = doctor.id";
                dl = query(sql);
            } catch (SQLException  e){
                e.printStackTrace();
            }
            request.setAttribute("data", dl);
            List doctors = new ArrayList();
            try {
                String sql = "SELECT id, first_name, last_name "
                        + "FROM doctor "
                        + "ORDER BY id";
                doctors = query(sql);
            } catch (SQLException  e){
                e.printStackTrace();
            }
            
            request.setAttribute("doctors", doctors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/update_default_doctor.jsp");
            if (dispatcher != null){
                dispatcher.forward(request, response);
            }
        }
        else {
            response.setContentType("text/html;charset=UTF-8");
            //Date comes in the YY/MM/DD format
            String beforedate = request.getParameter("beforeDate");
            String afterdate = request.getParameter("afterDate");
            List dl = new ArrayList();
            try {
                String sql = String.format("select * from appointment where dt BETWEEN Date('%s') AND Date('%s')", 
                                            beforedate, 
                                            afterdate);
                dl = query(sql);

            } catch (SQLException  e){
                e.printStackTrace();
            }
            request.setAttribute("data", dl);
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/update_default_doctor.jsp");
            if (dispatcher != null){
                dispatcher.forward(request, response);
            }
        }
    }
}
