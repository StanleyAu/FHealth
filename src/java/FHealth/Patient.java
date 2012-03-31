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
 
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String s_param;
        
    }
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String s_param;
        
        s_param = request.getParameter("patient_id");
        Integer p_id = (s_param == null)?null:Integer.parseInt(s_param);
        
        s_param = request.getParameter("new_record");
        Boolean new_record = (s_param == null)?Boolean.FALSE:Boolean.parseBoolean(s_param);
        
        s_param = request.getParameter("editable");
        System.out.println(s_param);
        Boolean editable = (s_param == null)?Boolean.FALSE:Boolean.parseBoolean(s_param);
        System.out.println(editable);
        
        request.setAttribute("new_record", new_record);
        request.setAttribute("editable", editable);
        request.setAttribute("patient_id", p_id);
        if (!new_record){
            if (p_id == null){
                PrintWriter out = response.getWriter();
                out.print("need patient id");
                return;
            }
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
            if (p_data.isEmpty()) {
                PrintWriter out = response.getWriter();
                out.print("invalid patient id");
                return;
            }
            request.setAttribute("p_data", p_data.get(0));
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/patient.jsp");
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
