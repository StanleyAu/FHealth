/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import FHealth.DatabaseFactory;
import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Yeung
 */
public class Appointment extends BaseServlet {

    String page = "jsp/patient_appointment.jsp";

    @Override
    public void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer p_id = Integer.parseInt(request.getParameter("patient_id"));
        String sql = String.format(
                "select dt,"
                + "duration,"
                + "concat(first_name,' ',last_name) doctor,"
                + "status, diagnosis, prescriptions, "
                + "comments, procedures "
                + "from appointment a "
                + "join doctor doc on a.doctor_id = doc.id "
                + "left join diagnosis d on a.latest_diagnosis_id = d.id "
                + "where a.patient_id = %d", p_id);

        System.out.println(sql);
        ArrayList pas_data = query(sql);
        System.out.println(pas_data);
        request.setAttribute("pas_data", pas_data);

        //Disptching request
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}