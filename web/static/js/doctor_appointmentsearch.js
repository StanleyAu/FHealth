/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function init_columns(){
    if (!window.user.roles.staff){
        $.each($('tr td:nth-child(2)'), function(i, e){
            var name = $(e).children('a').html();
            $(e).html(name);
        });
        $('tr td:nth-child(8) th:nth-child(8)').addClass('Hide');
    }
    if (!window.user.roles.staff){
        $('tr td:nth-child(9) th:nth-child(9)').addClass('Hide');
    }
}

$(document).ready(function() { 
    // call the tablesorter plugin 
    //$("div.data-container #patients").tablesorter();
    $("div.data-container #patients")
      .tablesorter({debug: false, widgets: ['zebra'], sortList: [[0,0]]})
      .tablesorterFilter({filterContainer: $("#filter-box"),
                          filterClearContainer: $("#filter-clear-button"),
                          filterColumns: [0,1,2,3,4,5,6],
                          filterCaseSensitive: false});
    $("#patients").css('width',1200);
    init_columns();
}); 

