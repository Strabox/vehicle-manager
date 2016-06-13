$(document).ready(function(){
	var vehicleName = $("#vehicleName").text();
	var noteIdToDelete;
	var registrationIdToDelete;
	//Awesome Tables initializations
	var registriesTable = $("#registrationsTable").DataTable({"order": [[ 0, "desc" ]]});
	var notesTable = $("#notesTable").DataTable();
	
	function getDeleteRegButtonHtml(id){
		return "<button id=\"" + id + "\" " +
				"type=\"button\" " +
				"class=\"btn btn-danger delete-reg\" " +
				"data-toggle=\"modal\" " +
				"data-target=\"#confirmRegDeleteModal\">Apagar</button>";
	}
	
	function getDeleteNoteButtonHtml(id){
		return "<button id=\"" + id + "\" " +
				"type=\"button\" " +
				"class=\"btn btn-danger delete-note\" " +
				"data-toggle=\"modal\" " +
				"data-target=\"#confirmNoteDeleteModal\">Apagar</button>";
	}
	
	function bindDeleteButtons(){
		$(".delete-reg").click(function(){
			registrationIdToDelete = $(this).attr("id");
		});
		$(".delete-note").click(function(){
			noteIdToDelete = $(this).attr("id");
		});
	}
	
	bindDeleteButtons();	//Bind all the delete buttons to the event.
	
	/* ##################### Delete Registrations ######################## */

	
	$("#deleteRegistration").click(function(){
		alert("Removing: "+ registrationIdToDelete);
		$.ajax({
			url: "/vehicle/" + vehicleName + "/registration/" + registrationIdToDelete,
			type: "DELETE",
			success: function(result) {
				$("#confirmRegDeleteModal").modal("toggle");
				registriesTable.row(document.getElementById(registrationIdToDelete+"reg")).remove().draw(true);
			},
			failure: function(errMsg) {
				$("#confirmRegDeleteModal").modal("toggle");
			},
		});
	});
	
	/* ####################### Delete Notes ######################## */
	
	/* Remove Note - Note confirmation */
	$("#deleteNote").click(function(){
		$.ajax({
			url: "/vehicle/" + vehicleName + "/note/" + noteIdToDelete,
			type: "DELETE",
			success: function(result) {
				$("#confirmNoteDeleteModal").modal("toggle");
				notesTable.row(document.getElementById(noteIdToDelete+"note")).remove().draw(true);
			},
			failure: function(errMsg) {
				$("#confirmNoteDeleteModal").modal("toggle");
			},
		});
	});
	
	/* ################ Add Registration Form ################## */
	
	/* Needed due to form is inside modal, we need instanciate/destroy 
	 * validator in hide/show of the modal. */
	$("#registrationModal")
		.on("shown.bs.modal", function (e) {
			$("#registrationForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					var registration = new Object();
					registration.time = $("#time").val();
					registration.description = $("#description").val(); 
					registration.date = $("#date").val();
					$.ajax({
						url: "/vehicle/" + vehicleName + "/registration",
						type: "POST",
						data: JSON.stringify(registration),
						contentType: "application/json; charset=utf-8",
						success: function(data, textStatus, request) {
							alert(data);
							$("#registrationModal").modal("toggle");
							$("#responseAlertSuccessHeader").text("Registo")
							$("#responseAlertSuccessText").text("Registo adicionado com sucesso");
							$("#responseAlertModalSuccess").modal("toggle");
							var row = registriesTable.row.add([registration.time,
		                         registration.description,
		                         registration.date,
		                         getDeleteRegButtonHtml(data)]).draw(true).node();
							$(row).attr("id",data+"reg");
							bindDeleteButtons();
						},
						failure: function(errMsg) {
							$("#registrationModal").modal("toggle");
							$("#responseAlertFailHeader").text("Registo")
							$("#responseAlertFailText").text("Falha ao adicionar registo");
							$("#responseAlertModalFail").modal("toggle");
						},
					});
			  	}
				return false;	//Prevent submit reloading the page
			});
		})
		.on("hidden.bs.modal", function (e) {
			$("#registrationForm").validator().off("submit");
			$("#registrationForm").validator("destroy");
	});

	/* ################ Add Note Form ################## */
	
	/* Needed due to form is inside modal, we need instanciate/destroy 
	 * validator in hide/show of the modal. */
	$("#notesModal")
		.on("shown.bs.modal", function (e) {
			$("#noteForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					var note = new Object();
					note.description = $("#note").val();
					$.ajax({
						url: "/vehicle/" + vehicleName + "/note",
						type: "POST",
						data: JSON.stringify(note),
						contentType: "application/json; charset=utf-8",
						success: function(data, textStatus, request) {
							$("#notesModal").modal("toggle");
							$("#responseAlertSuccessHeader").text("Notas")
							$("#responseAlertSuccessText").text("Nota adicionada com sucesso");
							$("#responseAlertModalSuccess").modal("toggle");
							var row = notesTable.row.add([note.description,
			                     getDeleteNoteButtonHtml(data)]).draw(true).node();
							$(row).attr("id",data+"note");
							bindDeleteButtons();
						},
						failure: function(errMsg) {
							$("#notesModal").modal("toggle");
							$("#responseAlertFailHeader").text("Notas")
							$("#responseAlertFailText").text("Falha ao adicionar nota");
							$("#responseAlertModalFail").modal("toggle");
						},
					});
					return false;	//Prevent submit reloading the page
				}
			});
		})
		.on("hidden.bs.modal", function (e) {
			$("#noteForm").validator().off("submit");
			$("#noteForm").validator("destroy")
	});
	
	/* ################## Add Alert Form ########################### */
	
	/* Needed due to form is inside modal, we need instanciate/destroy 
	 * validator in hide/show of the modal. */
	$("#alertsModal")
		.on("shown.bs.modal", function (e) {
			$("#alertForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					var alert = new Object();
					var url;
					alert.description = $("#alertDescription").val();
					alert.notiDate = $("#notiDate").val();
					if($("#periodicity").val() == "Ano a ano"){
						url = "/vehicle/" + vehicleName + "/notification?type=Year";
					}
					else if($("#periodicity").val() == "De meio em meio ano"){
						url = "/vehicle/" + vehicleName + "/notification?type=HalfYear";
					}
					$.ajax({
						url: url,
						type: "POST",
						data: JSON.stringify(alert),
						contentType: "application/json; charset=utf-8",
						success: function(data, textStatus, request) {
							$("#alertsModal").modal("toggle");
							$("#responseAlertSuccessHeader").text("Alertas")
							$("#responseAlertSuccessText").text("Alerta adicionado com sucesso");
							$("#responseAlertModalSuccess").modal("toggle");
						},
						failure: function(errMsg) {
							$("#notesModal").modal("toggle");
							$("#responseAlertFailHeader").text("Alertas")
							$("#responseAlertFailText").text("Falha ao adicionar alerta");
							$("#responseAlertModalFail").modal("toggle");
						},
					});
					return false;	//Prevent submit reloading the page
				}
			});
		})
		.on("hidden.bs.modal", function (e) {
			$("#alertForm").validator().off("submit");
			$("#alertForm").validator("destroy")
	});
	
});
