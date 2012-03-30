
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rst" dataSource="jdbc/hospital_G004">
select first_name, last_name, OHIP, SIN,
address, phone, current_health, default_doctor_id
from patient where id = 1;
</sql:query>
<c:set var="p" value="${rst.rows[0]}"></c:set>
<html>
<head>
  <title>Hospital Management Console</title>
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
    <label for="first_name">First Name</label>
    <input name="first_name" value="${p.first_name}"><br/>
    <label for="last_name">Last Name</label>
    <input name="last_name" value="${p.last_name}"><br/>
    <label for="ohip">OHIP</label>
    <input name="ohip" value="${p.OHIP}"><br/>
    <label for="sin">SIN</label>
    <input name="sin" value="${p.SIN}"><br/>
    <label for="address">Address</label>
    <input name="address" value="${p.address}"><br/>
    <label for="phone">Phone</label>
    <input name="phone" value="${p.phone}"><br/>
    <label for="current_health">Current Health</label>
    <input name="current_health" value="${p.current_health}"><br/>
    <label for="default_doctor">Default Doctor</label>
    <input name="default_doctor" value="${p.default_doctor_id}"><br/>
  <div class="buttons-container">
    <div class="button" id="edit">Edit</div>
    <div class="button hide" id="update">Update</div>
  </div>
  </div>
</body>
</html>    
