function get_p_data(){
    var p_data = {};
    
    $.each($('input,select,textarea'), function(i, e){
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
        url:"/FHealth/diagnosis",
        data:data,
        success:save_success
    });
}

function save_success(resp){
    $('.response-text').html(resp);
}

$(document).ready(function(){
    $('div.button#save').click(save_new_record);
});