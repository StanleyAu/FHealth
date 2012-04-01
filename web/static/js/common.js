function sync_height(){
  $('.left-pane-container').height(
    Math.max(window.innerHeight, $(document).height()));
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

function init_menu(){
  var $lp = $('.left-pane');
  $lp.append(make_menu_header());
  if (window.menu_items){
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
