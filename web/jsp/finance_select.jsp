<%@ page language="java" import="java.util.*" import="javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
  <title>Hospital Management Console</title>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
  <script src="static/js/common.js"></script>
  <script src="static/js/financeselect.js"></script>
  <link rel='stylesheet' type='text/css' href='static/css/common.css'>
  <link rel='stylesheet' type='text/css' href='static/css/tables.css'>
  <link rel='stylesheet' type='text/css' href='static/css/jquery-ui-1.8.18.custom.css'>
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
    <h1 class="titleblock">Doctor Name</h1>
    <div>
        <form action="FHealth/finance" method="POST">
            <input type="text" id="beforeDate" value="">
            <input type="text" id="afterDate" value="">
            <input type="button" id="submitAppt" value="Submit" />
        </form>
    </div>
    <table>
        <thead>
            <tr>
                <th>Appointments</th>
            </tr>
        </thead>
        <tbody id="apptbody">
                        <%! Iterator itr; int i = 1; String table_css; %>
            <% List data = (List)request.getAttribute("data");
                        for (itr=data.iterator(); itr.hasNext(); )
            {
                table_css = (i % 2 == 0)?"second":"";
                HashMap hm = (HashMap) itr.next();
                String firstname = Integer.toString((Integer)hm.get("patient_id"));
            %>
            <tr class='<%=table_css%>'>
            <td><%=firstname%></td>
            </tr>
            <%i++;}%>
        </tbody>
    </table>
  </div>
</body>
</html>

