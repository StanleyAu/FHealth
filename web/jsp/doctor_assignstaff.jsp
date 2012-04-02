
<%@page language="java" import="java.util.*, Util.WebUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% List<String> fields = Arrays.asList(
            "Staff Name", "staff");%>
<html>
    <head>
        <title>Hospital Management Console</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.js"></script>
        <script type="text/javascript" src="static/js/common.js"></script>
        <script type="text/javascript" src="static/js/jquery.tablesorter.js"></script>
        <script type="text/javascript" src="static/js/tablesorter_filter.js"></script>
        <script type="text/javascript" src="static/js/doctor_assignstaff.js"></script>
        <%=WebUtil.js_var("menu_items", request.getAttribute("menu_items"))%>
        <%=WebUtil.js_var("user", request.getAttribute("user"))%>
        <%=WebUtil.js_var("sa_data", request.getAttribute("d_data"))%>
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
                <table id="staff" class="tablesorter">
                    <thead><tr>
                        <% for (int i = 0; i < fields.size(); i += 2) {%>
                        <th><%= fields.get(i)%></th>
                        <% }%>
                        <th>Assign Staff</th>
                    </tr></thead>
                    <tbody id="apptbody">
                        <%
                            ArrayList pas_data = (ArrayList) request.getAttribute("d_data");
                            String table_css = "";
                            String is_assigned = "";
                            for (Integer i = 0; i < pas_data.size(); i++) {
                                table_css = (i % 2 != 0) ? "second" : "";
                        %>
                        <tr id='<%= i.toString()%>' class='<%= table_css%>'>
                            <% HashMap pa_data = (HashMap) pas_data.get(i);
                            is_assigned = "";
                            if ((pa_data.get("doctor_id") != null)){
                                if (pa_data.get("doctor_id").toString().equals(pa_data.get("current_doctor_id").toString())){
                                        is_assigned = "checked=\"checked\"";
                                }
                            }
                                
                            for (int j = 0; j < fields.size(); j += 2) {%>
                            <td><%= pa_data.get(fields.get(j + 1))%></td>
                            <% }%>
                            <td><input type="checkbox" id="<%= pa_data.get("id")%>" <%=is_assigned%>></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
                <div class="filter">
                    <br>Filter Table (Staff Name):
                    <input name="filter" id="filter-box" value="" maxlength="10" size="10" type="text">
                    <input id="filter-clear-button" type="submit" value="Clear"/>
                </div>
            </div>
            <div class="buttons-container">
                <div class="button" id="update">Update</div>
            </div>
        </div>
    </body>
</html>    
