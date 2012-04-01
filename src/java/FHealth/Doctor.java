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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yeung
 */
@WebServlet(name = "Doctor", urlPatterns = {"/Doctor"})
public class Doctor extends BaseServlet {

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
        
        Integer d_id = getIntParam(request, "doctor_id", null);
        
        request.setAttribute("doctor_id", d_id);
            if (d_id == null){
                PrintWriter out = response.getWriter();
                out.print("need doctor id");
                return;
            }
            String sql = String.format(
                    "select p.id, "
                    + "concat(first_name,' ',last_name) patient "
                    + "from doctor_patient dp "
                    + "join patient p on dp.patient_id = p.id "
                    + "where dp.doctor_id = %d", d_id);
            ArrayList d_data = query(sql);
            System.out.println(d_data.toString());
            if (d_data.isEmpty()) {
                PrintWriter out = response.getWriter();
                out.print("invalid doctor id");
                return;
            }
            request.setAttribute("d_data", d_data);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/doctor_patientsearch.jsp");
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
