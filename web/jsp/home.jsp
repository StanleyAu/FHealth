<%@page language="java" import="java.util.*, Util.WebUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
            <div class="data-container">
            </div>
            <div class="buttons-container">
            </div>
            <div class="response-text"></div>
        </div>
    </body>
</html>
