/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author brian
 */
@WebServlet(name = "Finance", urlPatterns = {"/finance"})
public class Finance extends BaseServlet {
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
            if (request.getParameter("fname") == null || request.getParameter("lname") == null) {
            response.setContentType("text/html;charset=UTF-8");
            List dl = new ArrayList();
            try {
                String sql = "select * from doctor";
                dl = query(sql);

            } catch (SQLException  e){
                e.printStackTrace();
            }
            request.setAttribute("data", dl);
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/finance_list.jsp");
            if (dispatcher != null){
                dispatcher.forward(request, response);
            }
        }
        else {
            response.setContentType("text/html;charset=UTF-8");
            //Date comes in the YY/MM/DD format
            String beforedate = request.getParameter("beforeDate");
            String afterdate = request.getParameter("afterDate");
            List dl = new ArrayList();
            try {
                //String sql = String.format("select * from appointment where dt BETWEEN Date('%s') AND Date('%s')", 
                //                            beforedate, 
                //                            afterdate);
                String sql = "Select * from appointment";
                dl = query(sql);

            } catch (SQLException  e){
                e.printStackTrace();
            }
            request.setAttribute("data", dl);
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/finance_select.jsp");
            if (dispatcher != null){
                dispatcher.forward(request, response);
            }
        }
    }
    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
            //Date comes in the YY/MM/DD format
            String beforedate = request.getParameter("beforeDate");
            String afterdate = request.getParameter("afterDate");
            List dl;
            String json = "";
            try {
                String sql = String.format("select * from appointment where dt BETWEEN Date('%s') AND Date('%s')", 
                                            beforedate, 
                                            afterdate);
                dl = query(sql);
                json = new Gson().toJson(dl, ArrayList.class);
            } catch (SQLException  e){
                e.printStackTrace();
            }
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write(json);
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
