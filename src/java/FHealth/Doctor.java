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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Auth.*;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Yeung
 */
@WebServlet(name = "Doctor", urlPatterns = {"/Doctor"})
public class Doctor extends AuthServlet {
    @Override
    protected HashSet getAllow(){
        HashSet<String> allow = new HashSet<String>();
        allow.add("doctor");
        return allow;
    }
    @Override
    protected HashSet postAllow(){
        HashSet<String> allow = new HashSet<String>();
        allow.add("doctor");
        return allow;
    }
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
        if (d_id == null) {
            PrintWriter out = response.getWriter();
            out.print("need doctor id");
            return;
        }
        String sql = String.format(
                "select p.id, "
                + "p.first_name,"
                + "p.last_name,"
                + "MAX(a.dt) last_visit "
                + "from doctor_patient dp "
                + "join patient p on dp.patient_id = p.id "
                + "join appointment a on a.patient_id = p.id "
                + "where dp.doctor_id = %d "
                + "group by p.id, p.first_name, p.last_name", d_id);
        ArrayList d_data = query(sql);
        System.out.println(d_data.toString());
        if (d_data.isEmpty()) {
            PrintWriter out = response.getWriter();
            out.print("invalid doctor id");
            return;
        }
        ArrayList pat_id = new ArrayList();
        for (Object data : d_data) {
            HashMap hm = (HashMap) data;
            pat_id.add(Integer.toString((Integer)hm.get("id")));
        }
        request.setAttribute("pat_id", pat_id);
        request.setAttribute("d_data", d_data);
        ArrayList doc_data = getDoctorList();
        request.setAttribute("doc_data", doc_data);

        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/doctor_patientsearch.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
    
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String currdoc = request.getParameter("curdoc");
        String patid = request.getParameter("patid");
        String docid = request.getParameter("docid");
        if (patid == null || docid == null || currdoc == null) {
            return;
        }
        String sql = String.format("SELECT * from doctor_patient WHERE "
                                        +"patient_id=%d AND doctor_id=%d",
                                        Integer.parseInt(patid),
                                        Integer.parseInt(currdoc));
        ArrayList d_data = query(sql);
        if (d_data == null || d_data.isEmpty()) return;
        HashMap hm = (HashMap) d_data.get(0);
        if (!(Boolean)hm.get("is_main_doctor")) return;
        String action = request.getParameter("action");
        if (action == null) {
            sql = String.format("SELECT * from doctor_patient WHERE "
                                        +"patient_id=%d AND doctor_id=%d",
                                        Integer.parseInt(patid),
                                        Integer.parseInt(docid));
            d_data = query(sql);
            if (d_data == null || d_data.isEmpty()) {
                HashMap JSON = new HashMap();
                JSON.put("action", "grant");
                String jsonstr = new Gson().toJson(JSON, HashMap.class);;
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.write(jsonstr);
            } else {
                hm = (HashMap) d_data.get(0);
                if ((Boolean)hm.get("is_main_doctor")) {
                    return;
                } else {
                    HashMap JSON = new HashMap();
                    JSON.put("action", "revoke");
                    String jsonstr = new Gson().toJson(JSON, HashMap.class);;
                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.write(jsonstr);
                }
            }
        } else {
            if (action.equals("grant")) {
                sql = String.format("INSERT INTO doctor_patient("
                             +"doctor_id, patient_id) VALUES ("
                             +"%d, %d)", 
                             Integer.parseInt(docid),
                             Integer.parseInt(patid));
                int ret = update(sql);
                PrintWriter out = response.getWriter();
                out.print((ret==1)?"save success":"invalid data");
                return;
            }
            else {
                sql = String.format("DELETE FROM doctor_patient "
                             +"WHERE doctor_id=%d AND patient_id=%d",
                             Integer.parseInt(docid), 
                             Integer.parseInt(patid));
                int ret = update(sql);
                PrintWriter out = response.getWriter();
                out.print((ret==1)?"save success":"invalid data");
            }
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
