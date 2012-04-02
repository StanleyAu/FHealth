<%-- 
    Document   : register
    Created on : 30-Mar-2012, 7:25:19 PM
    Author     : Stan
--%>

<%@page language="java" %>
<%@page import="Util.WebUtil"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Map.Entry"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hospital Management Console - Login</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script src="static/js/common.js"></script>
        <%=WebUtil.js_var("menu_items", request.getAttribute("menu_items"))%>
        <%=WebUtil.js_var("user", request.getAttribute("user"))%>
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
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
            <form action="register" method="POST">
                <div class="data-container">
                    <label for="username">Username</label>
                    <input type="text" name="username" value=""><br/>
                    <label for="password">Password</label>
                    <input type="text" name="password" value=""><br/>
                    <label for="firstname">First Name</label>
                    <input type="text" name="firstname" value=""><br />
                    <label for="lastname">Last Name</label>
                    <input type="text" name="lastname" value=""><br />
                    <label for="ohip">OHIP</label>
                    <input type="text" name="ohip" value=""><br />
                    <label for="sin">SIN</label>
                    <input type="text" name="sin" value=""><br />
                    <label for="address">Address</label>
                    <input type="text" name="address" value=""><br />
                    <label for="phone">Phone</label>
                    <input type="text" name="phone" value=""><br />
                    <%
                        ArrayList al = (ArrayList) request.getAttribute("roles");
                        Iterator alit = al.iterator();
                        while (alit.hasNext()) {
                            HashMap<String, Object> hm = (HashMap) alit.next();
                            int role_id = (Integer) hm.get("id");
                            String role = (String) hm.get("role");
                            String label = role.substring(0, 1).toUpperCase()
                                    + role.substring(1);
                    %>
                    <div class="checkbox-container">
                        <label for="cb_<%=role%>"><%=label%></label>
                        <input type="checkbox" id="cb_<%=role%>" name="role" value="<%=role_id%>.<%=role%>" />
                    </div>
                    <%
                        }
                    %>
                </div>
                <div class="buttons-container">
                    <input type="submit" name="submit" value="Register" />
                </div>
            </form>
        </div>
    </body>
</html>
