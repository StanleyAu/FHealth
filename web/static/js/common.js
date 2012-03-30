function sync_height(){
  $('.left-pane-container').height(Math.max(window.innerHeight, $(document).height()));
}

$(document).ready(function(){
  sync_height();
});
