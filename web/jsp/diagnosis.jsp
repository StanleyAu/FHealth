
<%@page language="java" import="java.util.*, Util.WebUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% List<String> fields = Arrays.asList(
            "Diagnosis", "diagnosis",
            "Prescriptions", "prescriptions",
            "Comments", "comments",
            "Procedures", "procedures");%>
<html>
    <head>
        <title>Hospital Management Console</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script type="text/javascript" src="static/js/common.js"></script>
        <script type="text/javascript" src="static/js/diagnosis.js"></script>
        <%= (Boolean)request.getAttribute("new_record")?
            "":WebUtil.js_var("p_data", (HashMap)request.getAttribute("p_data")) %>
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
            <div class="data-container">
            <% Boolean new_record = (Boolean) request.getAttribute("new_record");
                Boolean editable = (Boolean) request.getAttribute("editable");
                HashMap p_data = new HashMap();
                if (!new_record) {
                    p_data = (HashMap) request.getAttribute("p_data");
                }
                for (int i = 0; i < fields.size(); i += 2) {%>
            <label class="textarea" for="<%= fields.get(i + 1)%>">
                <%= fields.get(i)%></label>
            <textarea name="<%= fields.get(i + 1)%>">
                   <%= (new_record)? "":p_data.get(fields.get(i + 1))%></textarea><br/>
            <% }%>
            <input type="hidden" name="appointment_id" 
                   value="<%=request.getAttribute("appointment_id")%>"
            </div>
            <div class="buttons-container">
                <% if (new_record) { %>
                <div class="button" id="save">Save</div>
                <% }else if(editable) { %>
                <div class="button" id="update">Update</div>
                <% } %>
            </div>
            <div class="response-text"></div>
        </div>
    </body>
</html>    