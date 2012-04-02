function create_doctor_dropdown(){
    $.each($('input.doctor_list'), function(i, v){
        var $dd = $("<select class='drights'></select>");
        $.each(window.doc_data, function(i, d){
            $dd.append("<option value='"+d['id'].toString()+"'>"+
                d['doctor']+"</option>");
        })
        $dd.attr("name", 'docfor'+window.pat_id[i]);
        $(v).replaceWith($dd);
    })
}

$(document).ready(function() { 
    // call the tablesorter plugin 
    //$("div.data-container #patients").tablesorter();
    $("div.data-container #patients")
      .tablesorter({debug: false, widgets: ['zebra'], sortList: [[0,0]]})
      .tablesorterFilter({filterContainer: $("#filter-box"),
                          filterClearContainer: $("#filter-clear-button"),
                          filterColumns: [0,1],
                          filterCaseSensitive: false});
   create_doctor_dropdown();
   $("select.drights").change(function() {
       $("input.access").remove();
       var element = $(this);
       $.ajax({
           type: "POST",
           url: "/FHealth/doctor",
           data: {"patid":$(this).attr("name").substring(6), "docid":$(this).val(), 
           "curdoc":window.doctor_id},
           success: function(json) {
               if (typeof(json.action) != "undefined") {
                    var $ii = $("<input type='button' class='access' />")
                    $ii.attr("value", json.action);
                    $ii.attr("id", element.attr("name").substring(6)+"for"+element.val());
                    $(element).after($ii);
               }
           }
       }); 
   });
   $("tr").delegate("input.access", "click", function() {
       var ids = $(this).attr("id").split("for");
       $.ajax({
           type: "POST",
           url: "/FHealth/doctor",
           data: {"patid":ids[0], "docid":ids[1], "action":$(this).val(),
           "curdoc":window.doctor_id},
           success: function(json) {
               alert(json);
               $("input.access").remove();
           }
       });
   });
}); 

