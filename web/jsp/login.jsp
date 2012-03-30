<%-- 
    Document   : login
    Created on : 30-Mar-2012, 2:04:52 AM
    Author     : Stan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hospital Management Console - Login</title>
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
            <form action="../login" method="POST">
                <label for="username">Username</label>
                <input type="text" name="username" value=""><br/>
                <label for="password">Password</label>
                <input type="text" name="password" value=""><br/>
                <div class="buttons-container">
                    <input type="submit" value="Login" />
                </div>
            </form>
        </div>
    </body>
</html>
