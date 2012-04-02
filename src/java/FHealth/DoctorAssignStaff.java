/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import Util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Auth.*;
import java.util.HashSet;

/**
 *
 * @author Yeung
 */
public class DoctorAssignStaff extends AuthServlet {

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
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String s_param;
        
        Integer d_id = getIntParam(request, "doctor_id", -1);        
        String sql;
        if (d_id != null) {
            sql = String.format(
                    "select s.id, max(if(sa.doctor_id=%d, 1, 0)) assigned, "
                    + "concat(first_name,' ',last_name) staff "
                    + "from staff_assignment sa "
                    + "right join staff s on sa.staff_id = s.id "
                    + "group by s.id, staff", d_id);
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

    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String removed = request.getParameter("removed");
        String s_param = request.getParameter("inserted");
        ArrayList inserted = (ArrayList)WebUtil.gson.fromJson(s_param, ArrayList.class);
        
        int ret;
        String sql;
        if (!removed.equals("")){
            sql = String.format("DELETE FROM staff_assignment WHERE "
                    + "doctor_id = %d and staff_id in (%s);",
                    getUser(request).getRoleId("doctor"), removed);
            System.out.println(sql);
            ret = update(sql);
        }

        for (int i=0;i<inserted.size();i++){
            sql = String.format("INSERT INTO staff_assignment "
                    + "VALUES(%s, %d)",
                    inserted.get(i),
                    getUser(request).getRoleId("doctor")
                    );
            System.out.println(sql);
            ret = update(sql);
        }
        
        PrintWriter out = response.getWriter();
        out.print("Update Success");
        return;
    }
    
}
