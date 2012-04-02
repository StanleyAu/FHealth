
function update_assignment(){
    var new_sa = [];
    var old_sa = [];
    
    var inserted_sa = [];
    var removed_sa = [];
    
    $.each($('input[type="checkbox"]'), function(i, s){
        if ($(s).attr('checked')){
            new_sa.push($(s).attr('id'));
        }
    });
    $.each(window.sa_data, function(i, s){
        if (s.assigned == 1){
            old_sa.push(s.id);
            if (new_sa.indexOf(s.id)==-1){
                removed_sa.push(s.id);
            }
        }
    });
    $.each(new_sa, function(i, s){
        if (old_sa.indexOf(s) == -1){
            inserted_sa.push(s);
        }
    });
    console.log(removed_sa, inserted_sa);
    $.ajax({
       type:'POST',
       url:'doctorassignstaff',
       'data':{
           'removed':removed_sa.join(","),
           'inserted':JSON.stringify(inserted_sa)
       },
       success:update_response
    });
}

function update_response(resp){
    show_notification(resp);
}

function init_checkbox(){
    if(window.sa_data){
        $.each(sa_data, function(i, s){
            if (s.assigned ==1){
                $('input[type="checkbox"][id="'+s.id+'"]')
                .attr('checked', 'checked');
            }
        })
    }
}

$(document).ready(function() { 
    // call the tablesorter plugin 
    //$("div.data-container #patients").tablesorter();
    $("div.data-container #staff")
      .tablesorter({debug: false, widgets: ['zebra'], sortList: [[0,0]]})
      .tablesorterFilter({filterContainer: $("#filter-box"),
                          filterClearContainer: $("#filter-clear-button"),
                          filterColumns: [0,1],
                          filterCaseSensitive: false});
    init_checkbox();
    $('div.button#update#').click(update_assignment);
});

