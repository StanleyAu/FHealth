
<%@page language="java" import="java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% List<String> fields = Arrays.asList(
            "First Name", "first_name",
            "Last Name", "last_name",
            "OHIP", "OHIP",
            "SIN", "SIN",
            "Address", "address",
            "Phone", "phone",
            "Current Health", "current_health",
            "Doctor", "doctor");%>
<% HashMap p_data = (HashMap) request.getAttribute("p_data");%>
<html>
    <head>
        <title>Hospital Management Console</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script src="static/js/common.js"></script>
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
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
            <% for (int i = 0; i < fields.size(); i += 2) {%>
            <label for="<%= fields.get(i + 1)%>">
                <%= fields.get(i)%></label>
            <input name="<%= fields.get(i + 1)%>"
                   value="<%= p_data.get(fields.get(i + 1))%>"><br/>
            <% }%>
            <div class="buttons-container">
                <div class="button" id="edit">Edit</div>
                <div class="button hide" id="update">Update</div>
            </div>
        </div>
    </body>
</html>    
