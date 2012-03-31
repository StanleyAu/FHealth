<%@ page language="java" import="java.util.*" import="javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
  <title>Hospital Management Console</title>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
  <script src="static/js/common.js"></script>
  <script src="static/js/financelist.js"></script>
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
                <th>Doctors</th>
            </tr>
        </thead>
        <tbody>
            <%! Iterator itr; int i = 1; String table_css; %>
            <% List data = (List)request.getAttribute("data");
                        for (itr=data.iterator(); itr.hasNext(); )
            {
                table_css = (i % 2 == 0)?"second":"";
                HashMap hm = (HashMap) itr.next();
                String firstname = (String)hm.get("first_name");
                String lastname = (String)hm.get("last_name");
            %>
            <tr class='<%=table_css%>'>
            <td><a href=<%="/finance?fname="+firstname+"&lname="+lastname%>><%=firstname+" "+lastname%></a></td>
            </tr>
            <%i++;}%>
        </tbody>
    </table>
  </div>
</body>
</html>

