function saveConcept() {
    var concept = {}
    concept.name = document.getElementById('conceptName').value
    concept.definition = document.getElementById('conceptDefinition').value
    console.log(JSON.stringify(concept))

    $.ajax({
        type: "POST",
        url: "/concepts",
        data: JSON.stringify(concept),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: conceptAdded,
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
}

function conceptAdded() {
    console.log("concept added successfully")
    document.getElementById('conceptName').value=""
    document.getElementById('conceptDefinition').value=""
}