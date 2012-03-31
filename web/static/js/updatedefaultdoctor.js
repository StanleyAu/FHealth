/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function on_change_doctor(){
    console.log($(this).attr("id")+" changed doctor to " + $(this).val());
}

function on_update(){
    console.log($(this).attr("id")+" changed doctor to " + $(this).val());
    window.patients = $(".content table select");
    for (i=0; i<patients.length; i++){
        if ($(patients.get(i)).val() != $(patients.get(i)).attr("data-orig"))
            {
                console.log($(patients.get(i)).val() + " " + $(patients.get(i)).attr("data-orig"));
            }
    }
}


$(document).ready(function(){
  $(".content table select").change(on_change_doctor);
  $(".button").click(on_update);
});

