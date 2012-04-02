/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import Auth.AuthServlet;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Yeung
 */
public class Appointment extends AuthServlet {

    String page = "jsp/patient_appointment.jsp";
    @Override
    protected HashSet getAllow(){
        HashSet<String> allow = new HashSet<String>();
        allow.add("patient");
        allow.add("doctor");
        return allow;
    }
    @Override
    public void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer p_id = Integer.parseInt(request.getParameter("patient_id"));
        String sql = String.format(
                "select dt,"
                + "duration,"
                + "concat(first_name,' ',last_name) doctor,"
                + "status, diagnosis, prescriptions, "
                + "comments, procedures, a.id "
                + "from appointment a "
                + "join doctor doc on a.doctor_id = doc.id "
                + "left join diagnosis d on a.latest_diagnosis_id = d.id "
                + "where a.patient_id = %d", p_id);
        ArrayList pas_data = query(sql);
        request.setAttribute("pas_data", pas_data);
        String editable = request.getParameter("editable") == null ? 
                   "false" : request.getParameter("editable");
        request.setAttribute("editable", editable);

        //Disptching request
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}