/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function create_doctor_dropdown(){
    var $dd = $("<select name='default_doctor_id'></select>");
    $.each(window.doc_data, function(i, d){
        $dd.append("<option value='"+d['id'].toString()+"'>"+
            d['doctor']+"</option>");
    })
    if(!window.new_record){
        $dd.val(window.p_data['default_doctor_id']);
    }
    $('input[name|="default_doctor_id"]').replaceWith($dd);
}

function get_p_data(){
    var p_data = {};
    
    $.each($('input,select'), function(i, e){
        p_data[$(e).attr('name')]=$(e).val();
    })
    return p_data;
}

function save_new_record(){
    console.log("save");
    var data = {
        "p_data":JSON.stringify(get_p_data()),
        "op":"save"
    };
    $.ajax({
        type:"POST",
        url:"/FHealth/Patient",
        data:data,
        success:save_success
    });
}

function save_success(resp){
    $('.response-text').html(resp);
}

function update_record(){
    var data = {
        "patient_id":window.patient_id,
        "p_data":JSON.stringify(get_p_data()),
        "op":"update"
    };
    $.ajax({
        url:"/FHealth/Patient",
        type:"Post",
        data:data,
        success:update_success
    });
}

function update_success(resp){
    $('.response-text').html(resp);
}

$(document).ready(function(){
    $('div.button#save').click(save_new_record);
    $('div.button#update').click(update_record);
    create_doctor_dropdown();
    if (!window.editable && !window.new_record){
        $.each($('input,select'), function(i, e){
            $(e).attr("disabled", "disabled");
        });
    }
    if (!window.user.roles.staff){
        $('select').attr('disabled', 'disabled');
    }
});
