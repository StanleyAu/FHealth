package FHealth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorList extends BaseServlet {

 @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String s_param;
        
        Integer d_id = getIntParam(request, "staff_id", -1);        
        String sql;
        if (d_id != null) {
            sql = String.format(
                    "select staff_id, "
                    + "concat(first_name,' ',last_name) doctor, "
                    + "from staff_assignment sa "
                    + "join doctor d on sa.doctor_id = d.id "
                    + "where sa.staff_id = %d", d_id);
        }
        else {
            sql = "select staff_id, "
                    + "concat(first_name,' ',last_name) doctor, "
                    + "from staff_assignment sa "
                    + "join doctor d on sa.doctor_id = d.id ";
        }
        ArrayList d_data = query(sql);
        if (d_data == null || d_data.isEmpty()) {
            PrintWriter out = response.getWriter();
            out.print("Not valid data");
            return;
        }
        request.setAttribute("d_data", d_data);
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/doctorlist.jsp");
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
