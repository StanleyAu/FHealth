package FHealth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author brian
 */
public class AppController extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DatabaseFactory.getInstance();
    }

    public Connection getConnection()
    {
        return DatabaseFactory.getInstance().getConnection();
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection _conn = getConnection();
        ResultSet rs = null;
        try {
            String selectStatement = "SELECT * FROM patient";
            PreparedStatement pms = _conn.prepareStatement(selectStatement);
            rs = pms.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet patientServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet patientServlet at " + request.getContextPath() + "</h1>");
            out.println("Testing to see if dbconnection has initialized <br />");
            if (rs != null && rs.next()) {
                out.println(rs.getString(1));
                out.println(rs.getString(2));
                out.println(rs.getString(3));
            }
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
