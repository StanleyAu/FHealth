
<%@page language="java" import="java.util.*, Util.WebUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% List<String> fields = Arrays.asList(
            "Patient ID", "id",
            "Patient Name", "patient");%>
<html>
    <head>
        <title>Hospital Management Console</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.js"></script>
        <script type="text/javascript" src="static/js/common.js"></script>
        <script type="text/javascript" src="static/js/jquery.tablesorter.js"></script>
        <script type="text/javascript" src="static/js/tablesorter_filter.js"></script>
        <script type="text/javascript" src="static/js/doctor_patientsearch.js"></script>
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
                <div class="menu-item">MENU ITEM</div>
            </div>
        </div>
        <div class="content">
            <div class="data-container">
                <table id="patients" class="tablesorter">
                    <thead>
                        <tr>
                            <% for (int i = 0; i < fields.size(); i += 2) {%>
                            <th><%= fields.get(i)%></th>
                            <% }%>
                            <th>View Appointments</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%  ArrayList d_data = (ArrayList) request.getAttribute("d_data");
                            String table_css = "";
                            for (Integer i = 0; i < d_data.size(); i++) {
                                table_css = (i % 2 != 0) ? "second" : "";
                        %>
                        <tr id='<%= i.toString()%>' class='<%= table_css%>'>
                            <% HashMap pa_data = (HashMap) d_data.get(i);%>
                            <td><%= pa_data.get(fields.get(1))%></td>
                            <td><%= pa_data.get(fields.get(3))%></td>
                            <td><a href="/FHealth/appointment?patient_id=<%= pa_data.get(fields.get(1))%>">More Info</a></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <div class="filter">
                <br>Filter Table (Patient ID or Name):
                <input name="filter" id="filter-box" value="" maxlength="10" size="10" type="text">
                <input id="filter-clear-button" type="submit" value="Clear"/>
            </div>
            <div class="response-text"></div>
        </div>
    </body>
</html>    
