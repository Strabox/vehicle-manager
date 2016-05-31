$(document).ready(function(){
	var deleteButtonHtml = "<button type=\"button\" class=\"btn btn-danger\">Apagar</button>";
	var vehicleName = $("#vehicleName").text();
	var noteIdToDelete;
	var registrationIdToDelete;
	//Awesome Table initialization
	var registriesTable = $("#registrationsTable").DataTable({"order": [[ 0, "desc" ]]});
	var notesTable = $("#notesTable").DataTable();
	
	$(".deleteReg").click(function(){
		alert("wow");
	});
	
	$("#addRegistration").click(function(){
		var registration = new Object();
		registration.time = $("#time").val();
		registration.description = $("#description").val(); 
		registration.date = $("#date").val();
		var data = JSON.stringify(registration);
		$.ajax({
			url: "/vehicle/" + vehicleName + "/registration",
			type: "POST",
			data: data,
			contentType: "application/json; charset=utf-8",
			success: function(result) {
				$("#registrationModal").modal("toggle");
				registriesTable.row.add([registration.time,
				                         registration.description,
				                         registration.date,
				                         deleteButtonHtml]).draw(false);
			},
			failure: function(errMsg) {
				//TODO
				$("#registrationModal").modal("toggle");
			},
		});
	});
	
	$("#addNote").click(function(){
		var note = new Object();
		note.description = $("#note").val();
		var data = JSON.stringify(note);
		$.ajax({
			url: "/vehicle/" + vehicleName + "/note",
			type: "POST",
			data: data,
			contentType: "application/json; charset=utf-8",
			success: function(result) {
				$("#notesModal").modal("toggle");
				notesTable.row.add([note.description,
				                         deleteButtonHtml]).draw(false);
			},
			failure: function(errMsg) {
				//TODO
				$("#registrationModal").modal("toggle");
			},
		});
	});
	
});
