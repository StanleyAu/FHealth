<%@ page language="java" import="java.util.*, Util.WebUtil, javax.servlet.*" %>
<% List<String> fields = Arrays.asList(
            "Date", "dt",
            "Duration", "duration",
            "Status", "status",
            "Patient", "patient");%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="static/css/tables.css"/>
    <link rel='stylesheet' type='text/css' href='static/css/jquery-ui-1.8.18.custom.css'>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hospital Management Console - Login</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
    <script src="static/js/common.js"></script>
    <script src="static/js/financelist.js"></script>
    <script src="static/js/financeselect.js"></script>
    <%=WebUtil.js_var("doc_id", request.getAttribute("doc_id"))%>
    <%=WebUtil.js_var("menu_items", request.getAttribute("menu_items"))%>
    <%=WebUtil.js_var("user", request.getAttribute("user"))%>
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
    <div>
        <form action="FHealth/finance" method="POST">
            <input type="text" id="beforeDate" value="">
            <input type="text" id="afterDate" value="">
            <input type="button" id="submitAppt" value="Submit" />
        </form>
    </div>
    </br>
    <table>
        <thead><tr>
            <% for (int i = 0; i < fields.size(); i += 2) {%>
            <th><%= fields.get(i)%></th>
            <% }%>
            <th>Diagnosis</th>
        </tr></thead>
        <tbody id="apptbody">
            <%
                ArrayList pas_data = (ArrayList) request.getAttribute("data");
                String table_css = "";
                for (Integer i = 0; i < pas_data.size(); i++) {
                    table_css = (i % 2 != 0) ? "second" : "";
            %>
            <tr id='<%= i.toString()%>' class='<%= table_css%>'>
                <% HashMap pa_data = (HashMap) pas_data.get(i);
                for (int j = 0; j < fields.size(); j += 2) {%>
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

