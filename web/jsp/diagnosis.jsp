
<%@page language="java" import="java.util.*, Util.WebUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    List<String> fields = null;
    if ((Boolean) request.getAttribute("editable") == true) {
        fields = Arrays.asList(
                "Diagnosis", "diagnosis",
                //"Prescriptions", "prescriptions",
                "Comments", "comments",
                "Procedures", "procedures");
    } else {
        fields = Arrays.asList(
                "Diagnosis", "diagnosis",
                //"Prescriptions", "prescriptions",
                "Procedures", "procedures");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script type="text/javascript" src="static/js/common.js"></script>
        <script type="text/javascript" src="static/js/diagnosis.js"></script>
        <%=WebUtil.js_var("menu_items", request.getAttribute("menu_items"))%>
        <%=WebUtil.js_var("user", request.getAttribute("user"))%>
        <%= (Boolean) request.getAttribute("new_record")
                    ? "" : WebUtil.js_var("p_data", (HashMap) request.getAttribute("p_data"))%>
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
                <label class="textarea" for="<%= fields.get(i + 1)%>">
                    <%= fields.get(i)%></label>
                <textarea name="<%= fields.get(i + 1)%>"><%= (!new_record)
                            ? p_data.get(fields.get(i + 1)) : ""%></textarea><br/>
                    <% }%>
                <label class="textarea" for="lb_pres">
                    Prescriptions</label>
                <select id="lb_pres" name="prescriptions" multiple="true">
                    <%
                        ArrayList p_list = (ArrayList) request.getAttribute("drugs");
                        Iterator p_itr = p_list.iterator();
                        String last_cls = "";
                        while (p_itr.hasNext()) {
                            // Drugs are ordered by class (type)
                            HashMap p_map = (HashMap) p_itr.next();
                            int id = (Integer) p_map.get("id");
                            String name = (String) p_map.get("name");
                            String cls = (String) p_map.get("class");
                            String selected = "";
                            if (!new_record) {
                                System.out.println(p_data.get("prescriptions"));
                                String p_pres_raw = (String) p_data.get("prescriptions");
                                if (p_pres_raw != null) {
                                    String[] p_pres = p_pres_raw.split(",");
                                    Set p_set = new HashSet(Arrays.asList(p_pres));
                                    if (p_set.contains(String.format("%d", id))) {
                                        selected = " selected";
                                    }
                                }
                            }
                            if (!last_cls.equals(cls)) {
                    %>
                    <option value="">-- <%=cls%> --</option>
                    <%
                            last_cls = cls;
                        }
                    %>
                    <option value=<%=id%><%=selected%>><%=name%></option>
                    <%
                        }
                    %>
                </select>
                <input type="hidden" name="appointment_id" 
                       value="<%=request.getAttribute("appointment_id")%>"
            </div>
            <div class="buttons-container">
                <div class="button" id="save">Save</div>
            </div>
            <div class="response-text"></div>
        </div>
    </body>
</html>    
