
<%@page language="java" import="java.util.*, Util.WebUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% List<String> fields = Arrays.asList(
            "Appointment ID", "id",
            "Diagnosis ID", "diag_id",
            "Date", "dt",
            "Patient Name", "patient",
            "Diagnosis", "diagnosis",
            "Procedure", "procedures",
            "Comments", "comments",
            "Prescriptions", "prescriptions",
            "Status", "status");%>
<html>
    <head>
        <title>Hospital Management Console</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.js"></script>
        <script type="text/javascript" src="static/js/common.js"></script>
        <script type="text/javascript" src="static/js/jquery.tablesorter.js"></script>
        <script type="text/javascript" src="static/js/tablesorter_filter.js"></script>
        <script type="text/javascript" src="static/js/doctor_appointmentsearch.js"></script>
        <%=WebUtil.js_var("menu_items", request.getAttribute("menu_items"))%>
        <%=WebUtil.js_var("user", request.getAttribute("user"))%>
        <%= WebUtil.js_var("doctor_id", (Integer) request.getAttribute("doctor_id"))%>
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
        <link rel="stylesheet" type="text/css" href="static/css/tablesorter.css"/>
    </head>
    <body>
        <div class="top-bar-wrapper">
            <div class="top-bar">Hospital Network Console</div>
        </div>
        <div class="left-pane-container">
            <div class="left-pane">
            </div>
        </div>
        <div class="content">
            <div class="data-container">
                <table id="patients" class="tablesorter">
                    <thead>
                        <tr>
                            <% for (int i = 4; i < fields.size(); i += 2) {%>
                            <th><%= fields.get(i)%></th>
                            <% }%>
                            <th>Change Booking</th>
                            <th>Change Diagnosis</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%  ArrayList a_data = (ArrayList) request.getAttribute("a_data");
                            String table_css = "";
                            for (Integer i = 0; i < a_data.size(); i++) {
                                table_css = (i % 2 != 0) ? "second" : "";
                        %>
                        <tr id='<%= i.toString()%>' class='<%= table_css%>'>
                            <% HashMap pa_data = (HashMap) a_data.get(i);
                             for (int j = 4; j < fields.size(); j += 2) {
                                    Object val = pa_data.get(fields.get(j + 1));
                                    if (val == null) {
                                        val = "";
                                    }
                                 if (j != 6) {
                            %>
                            <td><%= val%></td>
                            <% } else {%>
                            <td><a href=<%="/FHealth/Patient?patient_id="+pa_data.get("patient_id")+"&editable=True&new_record=False"%>><%= val%></a></td>
                            <% } }%>
                            <td><a href=<%="/FHealth/booking?id="+pa_data.get("id")+"&editable=True"%>>Edit</a></td>
                            <td><a href=<%="/FHealth/diagnosis?appointment_id="+pa_data.get("id")+"&editable=true&new_record=true"%>>Edit</a></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <div class="filter">
                <br>Filter Table:
                <input name="filter" id="filter-box" value="" maxlength="10" size="10" type="text">
                <input id="filter-clear-button" type="submit" value="Clear"/>
            </div>
            <div class="response-text"></div>
        </div>
    </body>
</html>    
