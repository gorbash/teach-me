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
        success: conceptAdded
        }).fail(function (xhr, ajaxOptions, thrownError) {
            notifyCreateFailed(xhr.status)
        });
}

function conceptAdded() {
    console.log("concept added successfully")
    document.getElementById('conceptName').value=""
    document.getElementById('conceptDefinition').value=""
    reloadTable()
    document.getElementById('conceptName').focus()
    notifyCreateSuccessful()
}

function notifyCreateSuccessful() {
    document.getElementById('notificationArea').className = ""
    document.getElementById('notificationArea').classList.add("alert")
    document.getElementById('notificationArea').classList.add("alert-success")
    document.getElementById('notificationArea').textContent="Concept created successfully"
}


function notifyCreateFailed(errorMsg) {
    document.getElementById('notificationArea').className = ""
    document.getElementById('notificationArea').classList.add("alert")
    document.getElementById('notificationArea').classList.add("alert-danger")
    document.getElementById('notificationArea').textContent="Concept not created: " + errorMsg
}


function notifyDeleteSuccessful() {
    document.getElementById('notificationArea').className = ""
    document.getElementById('notificationArea').classList.add("alert")
    document.getElementById('notificationArea').classList.add("alert-warning")
    document.getElementById('notificationArea').textContent="Concept deleted successfully"
}

var tableData = {}
var cellsInRow = 3

function reloadTable() {
    console.log("Reloading table")
        $.ajax({
          url: "/concepts"
        }).then(function(data) {
            tableData = data;
            drawTable();
     });

}

function drawTable() {
    // get the reference for the body
    var div1 = document.getElementById('conceptsTableContainer');

    var tblForRemoval = document.getElementById('conceptsTable');

    if (tblForRemoval != null) {
        div1.removeChild(tblForRemoval);
    }

    // creates a <table> element
    var tbl = document.createElement("table");
    tbl.classList.add("table")
    tbl.classList.add("table-striped")
    tbl.id = "conceptsTable"

    var tBody = document.createElement("tbody");

    tbl.appendChild(tBody)

    var concepts = tableData._embedded.concepts;
    var rows = concepts.length;
    console.log("Creating " + rows + " rows")
    // creating rows
    for (var r = 0; r < rows; r++) {
        var concept = concepts[r];
        link = concept._links.self.href;
        var row = document.createElement("tr");

        // create cells in row

        var cell = document.createElement("td");
        var cellText = document.createTextNode(concept.name);
        cell.appendChild(cellText);
        row.appendChild(cell);

        var cell = document.createElement("td");
        var editButton = document.createElement("button");
        editButton.innerHTML = "Edit";
        editButton.classList.add("btn")
        editButton.classList.add("btn-info")
        editButton.entityLink = link;
        cell.appendChild(editButton);
        editButton.addEventListener ("click", function() { editConcept(this.entityLink); });
        row.appendChild(cell);

        var cell = document.createElement("td");
        var deleteButton = document.createElement("button");
        deleteButton.classList.add("btn")
        deleteButton.classList.add("btn-danger")
        deleteButton.innerHTML = "Delete";
        deleteButton.entityLink = link;
        cell.appendChild(deleteButton);
        deleteButton.addEventListener ("click", function() { deleteConcept(this.entityLink); });
        row.appendChild(cell);

	    tBody.appendChild(row); // add the row to the end of the table body
    }
    div1.appendChild(tbl); // appends <table> into <div1>
}

function deleteConcept(conceptHref){
    console.log("Deleting " + conceptHref)
    $.ajax({
        type: "DELETE",
        url: conceptHref
    }).then(function(data) {
        console.log("Deleted")
        reloadTable()
        notifyDeleteSuccessful()
    });
}

function editConcept(conceptHref){
    console.log("Edit " + conceptHref)
}

$(document).ready(reloadTable);