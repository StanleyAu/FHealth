function sync_height(){
  $('.left-pane-container').height(
    Math.max(window.innerHeight, $(document).height()));
}

function init_var(){
  var url_search = top.location.search;
  $.each(url_search.substr(1).split('&'), function(i, kv){
    var kv_split = kv.split('=');
    if (window[kv_split[0]] == null){
      window[kv_split[0]]=decodeURIComponent(kv_split[1]);
    }
  });
}

function init_menu(){
  var $lp = $('.left-pane');
  $lp.append(make_menu_header());
  if (window.menu_items){
      $.each(window.menu_items,function(i, role_item){
          $lp.append(make_menu_role(role_item[0][1]));
          $.each(role_item[1], function(i, role_action){
              $lp.append(make_role_action(role_action));
          })
      })
  }
}

function show_notification(noti){
  $('.content .notification-text')
    .css('display','block')
    .html(noti)
    .fadeOut(3000);
}

function init_notification(){
  var $noti = $('.notification-text');
  if ($noti.length == 0){
    $('.content').prepend("<div class='notification-text'></div>");
  }
  if (window['notification_text'] != null){
    show_notification(window['notification_text']);
  }
}

function init_disabled(){
  if(window.editable && 
      (window.editable == false ||
        window.editable == "False" ||
        window.editable == "false")){
    $('input, select, textarea').attr('disabled','disabled');
  }
}

function replace_var(string){
  var replaced = string.replace('$(pid)', window.user.roles.patient)
        .replace('$(did)', window.user.roles.doctor)
        .replace('$(sid)', window.user.roles.staff);
  return replaced;
}

function make_menu_header(){
  var menu_header = "<div class='menu-header'>Menu</div>";
  return menu_header;
}

function make_menu_role(role){
  var menu_role = "<div class='menu-role'>"+role+"</div>";
  return menu_role;
}

function make_role_action(action){
  var role_action = "<div class='role-item'><a href='"
      +replace_var(action[1])+"'>"+action[0]+"</a></div>";
  return role_action;  
}

$(document).ready(function(){
  init_var();
  init_menu();
  init_notification();
  init_disabled();
  sync_height();
});
