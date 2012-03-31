/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import Util.WebUtil;
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
        
        System.out.println("patient post");
        System.out.println(request.getParameterMap());
        String op = request.getParameter("op");
        String p_json = request.getParameter("p_data");
        System.out.println(op);
        System.out.println(p_json);
        if (p_json == null){
            PrintWriter out = response.getWriter();
            out.print("missing data");
            return;
        }
        HashMap p_data= WebUtil.gson.fromJson(p_json, HashMap.class);
        System.out.println(p_data);
        if (op.equals("save")){
            String sql = String.format("insert into patient "
                    + "(first_name, last_name, OHIP, SIN, "
                    + "address, phone, current_health, default_doctor_id) "
                    + "values('%s','%s','%s', '%s','%s','%s','%s',%s)",
                    (String)p_data.get("first_name"),
                    (String)p_data.get("last_name"),
                    (String)p_data.get("OHIP"),
                    (String)p_data.get("SIN"),
                    (String)p_data.get("address"),
                    (String)p_data.get("phone"),
                    (String)p_data.get("current_health"),
                    (String)p_data.get("default_doctor_id"));
            System.out.println(sql);
            int ret = update(sql);
            PrintWriter out = response.getWriter();
            out.print((ret==1)?"save success":"invalid data");
            return;
        }else if (op.equals("update")){
            Integer p_id = getIntParam(request, "patient_id", null);
            String sql = String.format("update patient "
                    + "set first_name = '%s',last_name = '%s',"
                    + "OHIP = '%s', SIN = '%s', address = '%s',"
                    + "phone = '%s', current_health = '%s', "
                    + "default_doctor_id = %s where id = %d",
                    (String)p_data.get("first_name"),
                    (String)p_data.get("last_name"),
                    (String)p_data.get("OHIP"),
                    (String)p_data.get("SIN"),
                    (String)p_data.get("address"),
                    (String)p_data.get("phone"),
                    (String)p_data.get("current_health"),
                    (String)p_data.get("default_doctor_id"),
                    p_id);
            System.out.println(sql);
            int ret = update(sql);
            PrintWriter out = response.getWriter();
            out.print((ret==1)?"update success":"invalid data");
        }else {
            PrintWriter out = response.getWriter();
            out.print("invalid operation");
            return;
        }
    }
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String s_param;
        
        Integer p_id = getIntParam(request, "patient_id", null);
        Boolean new_record = getBoolParam(request, "new_record", Boolean.FALSE);
        Boolean editable = getBoolParam(request, "editable", Boolean.FALSE);
        
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
                    "SELECT p.* "
                    + "from patient p "
                    + "where p.id = %d", p_id);
            ArrayList p_data = query(sql);
            if (p_data.isEmpty()) {
                PrintWriter out = response.getWriter();
                out.print("invalid patient id");
                return;
            }
            request.setAttribute("p_data", p_data.get(0));
        }
        ArrayList doc_data = getDoctorList();
        request.setAttribute("doc_data", doc_data);
        
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
