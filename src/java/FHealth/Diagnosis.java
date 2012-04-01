package FHealth;

import Util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author brian
 */
public class Diagnosis extends BaseServlet {

    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String op = request.getParameter("op");
        String p_json = request.getParameter("p_data");
        if (p_json == null){
            PrintWriter out = response.getWriter();
            out.print("missing data");
            return;
        }
        HashMap p_data= WebUtil.gson.fromJson(p_json, HashMap.class);
        if (op.equals("save")){
            String sql = String.format("insert into diagnosis "
                    + "(appointment_id, diagnosis, perscriptions, comments, "
                    + "procedures) "
                    + "values(%s,'%s','%s','%s', '%s')",
                    (String)p_data.get("appointment_id"),
                    (String)p_data.get("diagnosis"),
                    (String)p_data.get("comments"),
                    (String)p_data.get("procedures"));
            int ret = update(sql);
            PrintWriter out = response.getWriter();
            out.print((ret==1)?"save success":"invalid data");
            return;
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
        
        Integer p_id = getIntParam(request, "appointment_id", null);
        Boolean new_record = getBoolParam(request, "new_record", Boolean.FALSE);
        Boolean editable = getBoolParam(request, "editable", Boolean.FALSE);
        
        request.setAttribute("new_record", new_record);
        request.setAttribute("editable", editable);
        request.setAttribute("appointment_id", p_id);
        if (!new_record){
            if (p_id == null){
                PrintWriter out = response.getWriter();
                out.print("need appointment id");
                return;
            }
            String sql = String.format(
                    "SELECT p.* "
                    + "from diagnosis p "
                    + "where p.appointment_id = %d "
                    + "ORDER BY p.appointment_id ASC", p_id);
            ArrayList p_data = query(sql);
            if (p_data == null || p_data.isEmpty()) {
                PrintWriter out = response.getWriter();
                out.print("No Diagnosis for specified data");
                return;
            }
            request.setAttribute("p_data", p_data.get(p_data.size()-1));
        }
        
        request.setAttribute("appointment_id", p_id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/diagnosis.jsp");
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
