
function init_staff_only_access(){
    if(!window.user.roles.admin && window.user.roles.staff){
        $('input[type="checkbox"]').attr('disabled', 'disabled');
        $('input[type="checkbox"]#cb_patient').attr('checked',true);
    }
}

function disable_unnecessary_fields(){
    if($('#cb_patient').attr('checked')){
        $('input[type="text"]').attr("disabled", "");
        return;
    }
    $('input[type="text"]').attr("disabled", "disabled");
    $('input[type="text"][name="username"]').attr("disabled", "");
    $('input[type="text"][name="password"]').attr("disabled", "");
    if($('#cb_staff').attr('checked') ||
        $('#cb_patient').attr('checked') ||
        $('#cb_doctor').attr('checked')){
        $('input[type="text"][name="firstname"]').attr("disabled", "");
        $('input[type="text"][name="lastname"]').attr("disabled", "");
    }
}

$(document).ready(function(){
    init_staff_only_access();
    disable_unnecessary_fields();
    $('input[type="checkbox"]').change(function(){
        disable_unnecessary_fields();
    })
});

