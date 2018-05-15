
function getNotificationArea() {
    return  document.getElementById('notificationArea');
}

function getConceptNameArea() {
    return  document.getElementById('conceptName');
}

function getConceptDefArea() {
    return  document.getElementById('conceptDefinition');
}

function getSaveButton() {
    return  document.getElementById('saveButton');
}

function saveConcept() {
    var concept = {}
    concept.name = getConceptNameArea().value
    concept.definition = getConceptDefArea().value
    console.log(JSON.stringify(concept))
    var conceptHref = getConceptNameArea().existingConceptHref

    if (conceptHref == null) {
        console.log("Creating")
        $.ajax({
            type: "POST",
            url: "/concepts2",
            data: JSON.stringify(concept),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: conceptAdded
            }).fail(function (xhr, ajaxOptions, thrownError) {
                notifyCreateFailed(xhr.status)
            });
    } else {
        console.log("Updating")
        $.ajax({
            type: "PUT",
            url: conceptHref,
            data: JSON.stringify(concept),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: conceptAdded
            }).fail(function (xhr, ajaxOptions, thrownError) {
                notifyCreateFailed(xhr.status)
            });
    }
}

function conceptAdded() {
    console.log("concept added successfully")
    getConceptNameArea().value=""
    getConceptDefArea().value=""
    getConceptNameArea().existingConceptHref = null
    getSaveButton().textContent = "Create"
    reloadTable()
    getConceptNameArea().focus()
    notifyCreateSuccessful()
}



function notifyCreateSuccessful() {
    getNotificationArea().className = ""
    getNotificationArea().classList.add("alert")
    getNotificationArea().classList.add("alert-success")
    getNotificationArea().textContent="Concept created successfully"
}


function notifyCreateFailed(errorMsg) {
    getNotificationArea().className = ""
    getNotificationArea().classList.add("alert")
    getNotificationArea().classList.add("alert-danger")
    getNotificationArea().textContent="Concept not created: " + errorMsg
}


function notifyDeleteSuccessful() {
    getNotificationArea().className = ""
    getNotificationArea().classList.add("alert")
    getNotificationArea().classList.add("alert-warning")
    getNotificationArea().textContent="Concept deleted successfully"
}

var tableData = {}
var cellsInRow = 3

function reloadTable() {
    console.log("Reloading table")
        $.ajax({
          url: "/concepts2"
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

    var concepts = tableData;
    var rows = concepts.length;
    console.log("Creating " + rows + " rows")
    // creating rows
    for (var r = 0; r < rows; r++) {
        var concept = concepts[r];
        link = "/concepts2/" + concept.id;
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
    $.ajax({
            url: conceptHref
        }).then(function(data) {
            getConceptNameArea().value = data.name;
            getConceptDefArea().value = data.definition
            getConceptNameArea().focus()
            getConceptNameArea().existingConceptHref = conceptHref
            getSaveButton().textContent = "Update"

        });
}

$(document).ready(reloadTable);