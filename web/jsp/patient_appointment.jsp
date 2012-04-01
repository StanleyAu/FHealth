<%@ page language="java" import="java.util.*, Util.WebUtil, javax.servlet.*" %>
<% List<String> fields = Arrays.asList(
            "Date", "dt",
            "Duration", "duration",
            "Doctor", "doctor",
            "Status", "status");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' type='text/css' href='static/css/tables.css'>
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script type="text/javascript" src="static/js/common.js"></script>
        <%=WebUtil.js_var("menu_items", request.getAttribute("menu_items"))%>
        <%=WebUtil.js_var("user", request.getAttribute("user"))%>
        <title>Hospital Management Console</title>
        
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
            <h1 class="titleblock">Patient Name</h1>
            <table>
                <thead><tr>
                        <% for (int i = 0; i < fields.size(); i += 2) {%>
                        <th><%= fields.get(i)%></th>
                        <% }%>
                        <th>View Diagnosis</th>
                    </tr></thead>
                <tbody>
                    <%
                        ArrayList pas_data = (ArrayList) request.getAttribute("pas_data");
                        String table_css = "";
                        for (Integer i = 0; i < pas_data.size(); i++) {
                            table_css = (i % 2 != 0) ? "second" : "";
                    %>
                    <tr id='<%= i.toString()%>' class='<%= table_css%>'>
                        <%
                            HashMap pa_data = (HashMap) pas_data.get(i);
                            for (int j = 0; j < fields.size(); j += 2) {
                        %>
                        <td><%= pa_data.get(fields.get(j + 1))%></td>
                        <% }%>
                        <td><a href=<%="/FHealth/diagnosis?appointment_id="+pa_data.get("id")+"&new_record=False&editable=False"%>>More Info</a></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
    </body>
</html>
