/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Auth.*;
import java.util.HashMap;

/**
 *
 * @author Yeung
 */
@WebServlet(name = "Doctor_Diagnosis", urlPatterns = {"/Doctor_Diagnosis"})
public class Doctor_Diagnosis extends AuthServlet {

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
                "select a.id,  concat(d.id) diag_id, dt, prescriptions, "
                + "concat(first_name,' ',last_name) patient, "
                + "diagnosis, "
                + "procedures, comments, patient_id "
                + "from appointment a "
                + "join patient p on a.patient_id = p.id "
                + "left join diagnosis d on a.latest_diagnosis_id = d.id "
                + "where a.doctor_id = %d", d_id);
        ArrayList a_data = query(sql);
        if (a_data.isEmpty()) {
            PrintWriter out = response.getWriter();
            out.print("invalid doctor id");
            return;
        }
        sql = "select * from prescription";
        ArrayList p_list = query(sql);
        HashMap drugs = new HashMap();
        for (int j = 0; j < p_list.size(); j++) 
        {
            HashMap drug = (HashMap) p_list.get(j);
            drugs.put((Integer)drug.get("id"), drug.get("name"));
        }
        for (int i = 0; i < a_data.size(); i++){
            HashMap hm = (HashMap)a_data.get(i);
            String prescriptions = (String)hm.get("prescriptions");
            String[] pres = prescriptions.split(",");
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < pres.length; k++) {
                if (drugs.get(Integer.parseInt(pres[k])) != null)
                    sb.append(drugs.get(Integer.parseInt(pres[k]))+",");
            }
            sb.deleteCharAt(sb.length()-1);
            ((HashMap)a_data.get(i)).put("prescriptions", sb.toString());
        }
        System.out.println(a_data.toString());
        request.setAttribute("a_data", a_data);

        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/doctor_appointmentsearch.jsp");
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
