/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FHealth;

import Auth.AuthServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Stan
 */
public class Booking extends AuthServlet {

    @Override
    public void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer a_id = getIntParam(request, "id", null);
        Boolean editable = getBoolParam(request, "editable", Boolean.FALSE);
        request.setAttribute("editable", editable);
        request.setAttribute("id", a_id);

        if (a_id != null) {
            // Populate appointment details by ID
            String sql =
                    "SELECT id, dt, duration, patient_id, doctor_id, status, latest_diagnosis_id "
                    + "FROM appointment "
                    + "WHERE id = ?";
            try {
                PreparedStatement pms = db().getStatement(sql);
                pms.setInt(1, a_id);
                ArrayList a_data = db().query(pms);
                if (a_data.isEmpty()) {
                    PrintWriter out = response.getWriter();
                    out.print("invalid appointment id");
                    return;
                }
                HashMap a_map = (HashMap)a_data.get(0);
                System.out.println(a_map);
                request.setAttribute("a_data", a_data.get(0));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ArrayList p_data = getPatientList();
        request.setAttribute("p_data", p_data);
        ArrayList d_data = getDoctorList();
        request.setAttribute("d_data", d_data);

        //Disptching request
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/booking.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String post_type = request.getParameter("submit");
        String insertQuery =
                "INSERT INTO appointment (dt, duration, patient_id, doctor_id, status) "
                + "VALUES (?, ?, ?, ?, ?)";
        String updateQuery =
                "UPDATE appointment "
                + "SET dt=?, duration=?, patient_id=?, doctor_id=?, status=? "
                + "WHERE id= ?";

        int a_id = 0;
        String date = request.getParameter("date");
        String startTime = request.getParameter("time");
        // May throw NumberFormatException
        int patient = Integer.parseInt(request.getParameter("patient"));
        int doctor = Integer.parseInt(request.getParameter("doctor"));
        int duration = (int) Long.parseLong(request.getParameter("duration"));
        String status = request.getParameter("status");
        DateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm");
        Object datetime = null;
        try {
            Date dt = df.parse(date + ' ' + startTime);
            datetime = new java.sql.Timestamp(dt.getTime());
            // The JDBC driver knows what to do with a java.sql type:
            // preparedStatement.setObject(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement pms = null;
            if (post_type.equals("Save")) {
                pms = db().getStatement(insertQuery, true);
            } else if (post_type.equals("Update")) {
                a_id = Integer.parseInt(request.getParameter("id"));
                System.out.println("114:a_id: " + a_id);
                pms = db().getStatement(updateQuery);
                pms.setInt(6, a_id);
            }
            pms.setObject(1, datetime);
            pms.setInt(2, duration);
            pms.setInt(3, patient);
            pms.setInt(4, doctor);
            pms.setString(5, status);
            System.out.println("123:Query: " + pms);
            if (post_type.equals("Save")) {
                HashMap rs = (HashMap) db().getGeneratedKeys(pms).get(0);
                System.out.println("GenKeys: " + rs);
                long tmp = (Long) rs.get("GENERATED_KEY");
                a_id = (int) tmp;
            } else if (post_type.equals("Update")) {
                int result = db().update(pms);
            }

            //request.setAttribute("commit", (result == 1) ? true : false);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("booking?editable=true&"
                + "notification_text="+post_type+"%20Success&id=" + a_id);
        /*
         * //Dispatching request RequestDispatcher dispatcher =
         * request.getRequestDispatcher("booking"); if (dispatcher != null) {
         * dispatcher.forward(request, response); }
         */
    }
}
