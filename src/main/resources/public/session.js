$(document).ready(function() {
    $.ajax({
        url: "session"
    }).then(function(data) {
      // $('.greeting-id').append(data.id);
       //$('.greeting-content').append(data.content);
       console.log(data);
    });
});

function nextUpdate() {
    console.log("pressed");
    console.log(data);
}