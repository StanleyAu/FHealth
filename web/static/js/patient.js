/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function save_success(){
    $('.response-text').html("Save success!");
}

function update_success(){
    $('.response-text').html("Update success!");
}


function save_new_record(){
    console.log("save");
    save_success();
}

function update_record(){
    console.log("update");
    update_success();
}

$(document).ready(function(){
    $('div.button#save').click(save_new_record);
    $('div.button#update').click(update_record);
});
