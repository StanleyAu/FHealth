$(document).ready(function () {
   $( "#beforeDate" ).datepicker({dateFormat: 'yy/mm/dd'});
   $( "#afterDate" ).datepicker({dateFormat: 'yy/mm/dd'});
   
   $("#submitAppt").click(function() {
       var beforedate = $("#beforeDate").val();
       var afterdate = $("#afterDate").val();
       $.ajax({
           type: "POST",
           url: "/FHealth/finance",
           data: {"beforeDate":beforedate, "afterDate":afterdate, "docid":window.doc_id},
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
                                    '<td>'+columns.patient+'</td>'+
                                    '<td><a href='+ 
                                    '/FHealth/diagnosis?appointment_id='+
                                    +columns.id+
                                    '&new_record=False&editable=False">More Info</a>'+
                                    '</tr>');
                i++;
            })
            $("#apptbody").html(htmlString);
           }
       });
   });
});


