<%@ page language="java" import="java.util.*, javax.servlet.*, Util.WebUtil;" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hospital Management Console - Login</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
        <script src="static/js/common.js"></script>
        <script src="static/js/financelist.js"></script>
        <%=WebUtil.js_var("menu_items", request.getAttribute("menu_items"))%>
        <%=WebUtil.js_var("user", request.getAttribute("user"))%>
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
        <link rel="stylesheet" type="text/css" href="static/css/tables.css"/>
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
            <table>
                <thead>
                    <tr>
                        <th>Doctors</th>
                    </tr>
                </thead>
                <tbody>
                    <%! Iterator itr;
                        int i = 1;
                        String table_css;%>
                    <% List data = (List) request.getAttribute("data");
                        for (itr = data.iterator(); itr.hasNext();) {
                            table_css = (i % 2 == 0) ? "second" : "";
                            HashMap hm = (HashMap) itr.next();
                            String firstname = (String) hm.get("first_name");
                            String lastname = (String) hm.get("last_name");
                    %>
                    <tr class='<%=table_css%>'>
                        <td><a href=<%="/FHealth/finance?fname=" + firstname + "&lname=" + lastname%>><%=firstname + " " + lastname%></a></td>
                    </tr>
                    <%i++;
                        }%>
                </tbody>
            </table>
        </div>
    </body>
</html>

