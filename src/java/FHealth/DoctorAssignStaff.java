/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import Util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Auth.*;

/**
 *
 * @author Yeung
 */
public class DoctorAssignStaff extends AuthServlet {

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
        String s_param;
        
        Integer d_id = getIntParam(request, "doctor_id", -1);        
        String sql;
        if (d_id != null) {
            sql = String.format(
                    "select s.id, sa.doctor_id, %d "
                    + "as current_doctor_id,"
                    + "concat(first_name,' ',last_name) staff "
                    + "from staff_assignment sa "
                    + "right join staff s on sa.staff_id = s.id", d_id);
        }
        else {
            sql = "select s.id, sa.doctor_id, "
                    + "concat(first_name,' ',last_name) staff, "
                    + "from staff_assignment sa "
                    + "right join staff s on sa.staff_id = s.id ";
        }
        ArrayList d_data = query(sql);
        if (d_data == null || d_data.isEmpty()) {
            PrintWriter out = response.getWriter();
            out.print("Not valid data");
            return;
        }
        request.setAttribute("d_data", d_data);
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/doctor_assignstaff.jsp");
        if (dispatcher != null) {
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
