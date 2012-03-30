<%-- 
    Document   : index
    Created on : 29-Mar-2012, 5:13:08 PM
    Author     : Yeung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rs" dataSource="jdbc/hospital_G004">
select id, first_name, last_name from patient
</sql:query>
<!DOCTYPE html>
<html>
  <head>
    <title>DB Test</title>
    <link rel="stylesheet" type="text/css" href="common.css">
  </head>
  <body>

  <h2>Results</h2>

    <c:forEach var="row" items="${rs.rows}">
        id ${row.id}<br/>
        fname ${row.first_name}<br/>
        lname ${row.last_name}<br/>
    </c:forEach>

  </body>
</html>