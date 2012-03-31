<%@ page language="java" import="java.util.*" import="javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
  <title>Hospital Management Console</title>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
  <script src="static/js/common.js"></script>
  <script src="static/js/updatedefaultdoctor.js"></script>
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
                <th>Patient</th>
                <th>Default Doctor</th>
            </tr>
        </thead>
        <tbody>
            <%! Iterator itr, d_itr; int i = 1; String table_css; %>
            <% List data = (List)request.getAttribute("data");
            for (itr=data.iterator(); itr.hasNext(); )
            {
                table_css = (i % 2 == 0)?"second":"";
                HashMap hm = (HashMap) itr.next();
                String pfirstname = (String)hm.get("p_first_name");
                String plastname = (String)hm.get("p_last_name");
                String pid = (String)hm.get("p_id");
                String p_did = (String)hm.get("d_id");
            %>
            <tr class='<%=table_css%>'>
            <td><%=pfirstname+" "+plastname%></td>
            <td>
                <select id="p<%=pid%>-doc" NAME=<%=pid%> data-orig="<%=p_did%>">
                <% List doctors = (List)request.getAttribute("doctors");
                for (d_itr=doctors.iterator(); d_itr.hasNext(); )
                {
                    HashMap hm_doc = (HashMap) d_itr.next();
                    String dfirstname = (String)hm_doc.get("first_name");
                    String dlastname = (String)hm_doc.get("last_name");
                    int did = (Integer)hm_doc.get("id");
                    String selected = (Integer.parseInt(p_did) == did)? "Selected" : "";
                %>
                <option value=<%=did%> <%=selected%>><%=dfirstname+" "+dlastname%></option>
                <%}%>
                </select>
            </td>
            </tr>
            <%i++;}%>
        </tbody>
    </table>
    <div class="buttons-container">
        <div class="button" id="update">Update</div>
    </div>
  </div>
</body>
</html>