function sync_height(){
  $('.left-pane-container').height(
    Math.max(window.innerHeight, $(document).height()));
}

function make_menu_role(role){
  var menu_role = "<div class='menu-role'>"+role+"</div><hr></hr>";
  return menu_role;
}

function make_role_action(action){
  var role_action = "<div class='role-item'><a href='"
      +action[1]+"'>"+action[0]+"</a></div>";
  return role_action;  
}

function init_menu(){
  if (window.menu_items){
      var $lp = $('.left-pane');
      $.each(window.menu_items,function(i, role_item){
          $lp.append(make_menu_role(role_item[0]));
          $.each(role_item[1], function(i, role_action){
              $lp.append(make_role_action(role_action));
          })
      })
  }
}

$(document).ready(function(){
  init_menu();
  sync_height();
});
