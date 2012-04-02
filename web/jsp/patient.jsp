
<%@page language="java" import="java.util.*, Util.WebUtil" %>
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
            "Default Doctor", "default_doctor_id");%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script type="text/javascript" src="static/js/common.js"></script>
        <script type="text/javascript" src="static/js/patient.js"></script>
        <%= WebUtil.js_var("doc_data", (ArrayList)request.getAttribute("doc_data")) %>
        <%= WebUtil.js_var("editable", (Boolean)request.getAttribute("editable")) %>
        <%= WebUtil.js_var("new_record", (Boolean)request.getAttribute("new_record")) %>
        <%= (Boolean)request.getAttribute("new_record")?
            "":WebUtil.js_var("p_data", (HashMap)request.getAttribute("p_data")) %>
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
            <% Boolean new_record = (Boolean) request.getAttribute("new_record");
                Boolean editable = (Boolean) request.getAttribute("editable");
                HashMap p_data = new HashMap();
                if (!new_record) {
                    p_data = (HashMap) request.getAttribute("p_data");
                }
                for (int i = 0; i < fields.size(); i += 2) {%>
            <label for="<%= fields.get(i + 1)%>"><%= fields.get(i)%></label>
            <input name="<%= fields.get(i + 1)%>"
                   value="<%= (new_record)? "":p_data.get(fields.get(i + 1))%>"/><br/>
            <% }%>
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
