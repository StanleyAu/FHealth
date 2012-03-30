<%@ page language="java" import="java.util.*" import="javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
  <title>Hospital Management Console</title>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
  <script src="static/js/common.js"></script>
  <link rel='stylesheet' type='text/css' href='static/css/common.css'>
  <link rel='stylesheet' type='text/css' href='static/css/tables.css'>
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
    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Duration</th>
                <th>Doctor</th>
                <th>Appointment Status</th>
                <th>Diagnosis</th>
            </tr>
        </thead>
        <tbody>
            <%! Iterator itr; int i = 1; String table_css; %>
            <% List data = (List)request.getAttribute("data");
                        for (itr=data.iterator(); itr.hasNext(); )
            {
                table_css = (i % 2 == 0)?"second":"";
            %>
            <tr class='<%=table_css%>'>
            <td><%=itr.next()%></td>
            <td><%=itr.next()%></td>
            <td><%=itr.next()%></td>
            <td><%=itr.next()%></td>
            <td><%=itr.next()%></td>
            </tr>
            <%i++;}%>
        </tbody>
    </table>
  </div>
</body>
</html>
