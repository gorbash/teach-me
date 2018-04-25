var obtainedData = [];

$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/session"
    }).then(function(data) {
	   document.getElementById('button1').disabled=false;
	   obtainedData = data;
	   clicked();
    });
});

var index = 0;
var first = true;


function disableButton() {
    document.getElementById('button1').disabled=true;
}

function clicked() {
	console.log('Index ' + index + ' first ' + first)
	if (index < obtainedData.length) {
		var concept = obtainedData[index];
		if (first) {
			document.getElementById('button1').textContent='Show answer';
			document.getElementById('question').textContent=concept.name;
			document.getElementById('answer').textContent='';			
		}
		else {
			document.getElementById('button1').textContent='Next question';
			document.getElementById('question').textContent=concept.name;
			document.getElementById('answer').textContent=concept.definition;
			index++;
			if (index == obtainedData.length) {
                disableButton();
			}
		}	
		first = !first;
	} else {
		document.getElementById('question').textContent='All questions done';
		document.getElementById('answer').textContent='All questions done';
	}
}