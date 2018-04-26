var obtainedData = [];
var index = 0;
var first = true;

$(document).ready(reloadData);


function reloadData()  {
    console.log("Reloading data")
    $.ajax({
      url: "/session"
    }).then(function(data) {
        index = 0;
        first = true;
        document.getElementById('button1').disabled=false;
        obtainedData = data;
        clicked();
    });
}




function disableButton() {
    document.getElementById('button1').disabled=true;
}

function displayProgress() {
    document.getElementById('progress').textContent = (index+1) + "/" + obtainedData.length;
}


function clicked() {
	console.log('Index ' + index + ' first ' + first)
	displayProgress();
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