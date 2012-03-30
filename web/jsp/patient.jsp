
<%@page language="java" import="java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% List data=(List)request.getAttribute("data"); %>
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
    <label for="first_name">First Name</label>
    <input name="first_name" value="<%=data.get(1)%>"><br/>
    <label for="last_name">Last Name</label>
    <input name="last_name" value="<%=data.get(2)%>"><br/>
    <label for="ohip">OHIP</label>
    <input name="ohip" value="<%=data.get(3)%>"><br/>
    <label for="sin">SIN</label>
    <input name="sin" value="<%=data.get(4)%>"><br/>
    <label for="address">Address</label>
    <input name="address" value="<%=data.get(5)%>"><br/>
    <label for="phone">Phone</label>
    <input name="phone" value="<%=data.get(6)%>"><br/>
    <label for="current_health">Current Health</label>
    <input name="current_health" value="<%=data.get(7)%>"><br/>
    <label for="default_doctor">Default Doctor</label>
    <input name="default_doctor" value="<%=data.get(8)%>"><br/>
  <div class="buttons-container">
    <div class="button" id="edit">Edit</div>
    <div class="button hide" id="update">Update</div>
  </div>
  </div>
</body>
</html>    
