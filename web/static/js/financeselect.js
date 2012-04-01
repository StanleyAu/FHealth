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
            var i = 1;
            var htmlString = '';
            $("#apptbody").empty();
            $.each(json, function(rows, columns) {
                var classname = (i % 2 == 1) ? "" : "second";
                
                htmlString += ('<tr class=\"'+classname+'\">'+
                                    '<td>'+columns.dt+'</td>'+
                                    '<td>'+columns.duration+'</td>'+
                                    '<td>'+columns.status+'</td>'+
                                    '</tr>');
                i++;
            })
            $("#apptbody").html(htmlString);
           }
       });
   });
});


