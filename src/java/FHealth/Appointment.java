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
public class Appointment extends HttpServlet {
    
 String page="patient_appointment.jsp";

 public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException,IOException {
    Connection conn = DatabaseFactory.getInstance().getConnection();  
    PrintWriter out = response.getWriter();
    ResultSet rs;
    //Establish connection to MySQL database
    String sql = "select dt, duration, doctor_id, status, latest_diagnosis_id from appointment";        
    List dataList = new ArrayList(); 
    try {
        PreparedStatement pms = conn.prepareStatement(sql);
        rs = pms.executeQuery();
        while (rs.next ()){
            //Add records into data list
            dataList.add(rs.getTimestamp("dt"));
            dataList.add(rs.getInt("duration"));
            dataList.add(rs.getInt("doctor_id"));
            dataList.add(rs.getString("status"));
            dataList.add(rs.getInt("latest_diagnosis_id"));
        }
        rs.close ();
    }
    catch(Exception e){
        System.out.println("Exception is ;"+e);
    }
    request.setAttribute("data",dataList);
    
    //Disptching request
    RequestDispatcher dispatcher = request.getRequestDispatcher(page);
    if (dispatcher != null){
        dispatcher.forward(request, response);
    } 
  }
}