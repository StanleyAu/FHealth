$(document).ready(function () {
   $( "#beforeDate" ).datepicker({dateFormat: 'yy/mm/dd'});
   $( "#afterDate" ).datepicker({dateFormat: 'yy/mm/dd'});
   
   $("#submitAppt").click(function() {
       var beforedate = $("#beforeDate").val();
       var afterdate = $("#afterDate").val();
       $.ajax({
           type: "POST",
           url: "/FHealth/finance",
           data: {"beforeDate":beforedate, "afterDate":afterdate},
           success: function(json) {
            $.each(json, function(rows, columns){
                $("#apptbody").html('<tr><td>'+columns.dt+'</td></tr>');
            })
           }
       });
   });
});


