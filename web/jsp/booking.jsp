<%-- 
    Document   : view_appointment
    Created on : 1-Apr-2012, 1:42:52 AM
    Author     : Stan
--%>

<%@page language="java" import="java.util.*, Util.WebUtil" %>
<%@page import="java.sql.Timestamp" %>
<%@page import="java.text.SimpleDateFormat, java.text.DateFormat" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" media="all" />
        <link rel='stylesheet' type='text/css' href='static/css/tables.css'>
        <link rel='stylesheet' type='text/css' href='static/css/timePicker.css'>
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"></script>
        <script type="text/javascript" src="static/js/common.js"></script>
        <script type="text/javascript" src="static/js/jquery.timePicker.min.js"></script>
        <%=WebUtil.js_var("menu_items", request.getAttribute("menu_items"))%>
        <%=WebUtil.js_var("user", request.getAttribute("user"))%>
        <title>Hospital Management Console - Schedule Appointment</title>
        
    </head>
    <body>
        <script>
            $(function() {
                $( "#tx_date" ).datepicker({ minDate: -10, maxDate: "+1Y" });
                // Use default settings
                $("#tx_time, #tx_time_end").timePicker();
                
                function updateDuration() {
                    ;    duration = (endTime - startTime);
                    $("#duration").val(duration / 1000 / 60);
                }
                
                // Store time used by duration.
                var startTime = $.timePicker("#tx_time").getTime();
                var endTime = $.timePicker("#tx_time_end").getTime();
                var duration;

                $("#tx_time").change(function() {
                    var time = $.timePicker("#tx_time").getTime();
                    // If duration doesn't exist, set startTime first, then calculate duration
                    if ($("#duration").val() == "null") {
                        startTime = time;
                        if(startTime > endTime) {
                            $.timePicker("#tx_time_end").setTime(new Date(startTime.getTime()));
                        }
                        updateDuration();
                    }
                    else if ($("#tx_time_end").val()) { // Only update when second input has a value.
                        duration = endTime - startTime;
                        startTime = time;
                        // Calculate and update the time in the second input.
                        $.timePicker("#tx_time_end").setTime(new Date(new Date(time.getTime() + duration)));
                    }
                    else {
                        startTime = time;
                    }
                });
                // Validate.
                $("#tx_time_end").change(function() {
                    var time = $.timePicker("#tx_time_end").getTime();
                    endTime = time;
                    if ($("#tx_time").val()) {
                        if(startTime > time) {
                            $.timePicker("#tx_time_end").setTime(new Date(startTime.getTime()));
                        }
                        updateDuration();
                    }
                });
            });
        </script>
        <div class="top-bar-wrapper">
            <div class="top-bar">Hospital Network Console</div>
        </div>
        <div class="left-pane-container">
            <div class="left-pane">
            </div>
        </div>
        <div class="content">
            <form action="booking" method="POST">
                <%
                    Boolean new_record = (Boolean) request.getAttribute("new_record");
                    Boolean editable = (Boolean) request.getAttribute("editable");
                    Integer a_id = (Integer) request.getAttribute("id");
                    HashMap hm = (HashMap) request.getAttribute("a_data");

                    String date = "";
                    String time = "";
                    String time_end = "";
                    Integer duration = null;
                    Integer patient_id = null;
                    Integer doctor_id = null;
                    String status = "Pending";

                    if (hm != null) {
                        Timestamp stamp = (Timestamp) hm.get("dt");
                        duration = (Integer) hm.get("duration");
                        DateFormat date_f = new SimpleDateFormat("MM/dd/yy");
                        DateFormat time_f = new SimpleDateFormat("hh:mm");
                        Calendar dateAdd = Calendar.getInstance();
                        dateAdd.setTime(stamp);
                        dateAdd.add(Calendar.MINUTE, duration);
                        date = date_f.format(stamp);
                        time = time_f.format(stamp);
                        time_end = time_f.format(dateAdd.getTime());

                        patient_id = (Integer) hm.get("patient_id");
                        doctor_id = (Integer) hm.get("doctor_id");
                        status = (String) hm.get("status");
                    }
                %>
                <input type="hidden" name="id" value=<%=a_id%> />
                <div>
                    <fieldset>
                        <legend>Date &amp; Time</legend>
                        <label for="tx_date">Date</label>

                        <input id="tx_date" type="text" name="date" value="<%=date%>" /><br />
                        <label for="tx_time">Time</label>
                        <input id="tx_time" type="text" name="time" value="<%=time%>" />
                        <label for="tx_time_end">to</label>
                        <input id="tx_time_end" type="text" name="time_end" value="<%=time_end%>" />
                        <input id="duration" type="hidden" name="duration" value=<%=duration%> />
                    </fieldset>
                    <fieldset>
                        <legend>Details</legend>
                        <div class="data-container">
                            <label for="cb_patient">Patient</label>
                            <select id="cb_patient" name="patient">
                                <option value="" >-- Please Select --</option>
                                <%
                                    ArrayList p_list = (ArrayList) request.getAttribute("p_data");
                                    Iterator p_itr = p_list.iterator();
                                    while (p_itr.hasNext()) {
                                        HashMap pair = (HashMap) p_itr.next();
                                        int id = (Integer) pair.get("id");
                                        String name = (String) pair.get("patient");
                                        if ((patient_id != null) && (id == patient_id)) {
                                %>
                                <option value="<%=id%>" selected><%=name%></option>
                                <%
                                } else {
                                %>
                                <option value="<%=id%>"><%=name%></option>
                                <%
                                        }
                                    }

                                %>
                            </select><br />
                            <label for="cb_doctor">Doctor</label>
                            <select id="cb_doctor" name="doctor">
                                <option value="" selected>-- Please Select --</option>
                                <%
                                    ArrayList d_list = (ArrayList) request.getAttribute("d_data");
                                    Iterator d_itr = d_list.iterator();
                                    while (d_itr.hasNext()) {
                                        HashMap pair = (HashMap) d_itr.next();
                                        int id = (Integer) pair.get("id");
                                        String name = (String) pair.get("doctor");
                                        if ((doctor_id != null) && (id == doctor_id)) {
                                %>
                                <option value="<%=id%>" selected><%=name%></option>
                                <%
                                } else {
                                %>
                                <option value="<%=id%>"><%=name%></option>
                                <%
                                        }
                                    }

                                %>
                            </select><br />
                            <!-- tx_status should be hidden on create -->
                            <label for="cb_status">Status</label>
                            <select id="cb_status" name="status">
                                <%
                                    List<String> statuses = Arrays.asList(
                                            "Pending", "Cancel", "Rescheduled", "Complete");
                                    Iterator<String> itr = statuses.iterator();
                                    while (itr.hasNext()) {
                                        String val = itr.next();
                                        if (val.equals(status)) {
                                %>
                                <option value="<%=val%>" selected><%=val%></option>
                                <%
                                } else {
                                %>
                                <option value="<%=val%>"><%=val%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </fieldset>
                </div>
                <div class="buttons-container">
                    <% if (a_id == null) {%>
                    <input type="submit" name="submit" value="Save" />
                    <% } else if (editable) {%>
                    <input type="submit" name="submit" value="Update" />
                    <% }%>
                </div>
            </form>
        </div>
    </body>
</html>

