<%-- 
    Document   : loginsuccess
    Created on : 30-Mar-2012, 1:59:23 PM
    Author     : Stan
--%>

<%@page language="java" 
        contentType="text/html"
        pageEncoding="UTF-8"
        import="Auth.UserBean"
        %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hospital Management Console - Login Success</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script src="../static/js/common.js"></script>
        <link rel="stylesheet" type="text/css" href="../static/css/common.css"/>
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
            <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));%>	
            Welcome <%= currentUser.getUsername()%>
        </div>
    </body>
</html>