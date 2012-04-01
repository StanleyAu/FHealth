/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() { 
    // call the tablesorter plugin 
    //$("div.data-container #patients").tablesorter();
    $("div.data-container #patients")
      .tablesorter({debug: false, widgets: ['zebra'], sortList: [[0,0]]})
      .tablesorterFilter({filterContainer: $("#filter-box"),
                          filterClearContainer: $("#filter-clear-button"),
                          filterColumns: [0,1,2,3,4,5,6],
                          filterCaseSensitive: false})                 
}); 

