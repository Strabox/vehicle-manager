$(document).ready(function(){
	var vehicleName = $("#vehicleName").text();
	var vehicleType = $("#vehicleType").attr("name");
	var noteIdToDelete;
	var registrationIdToDelete;
	var alertIdToDelete;
	// Awesome Tables initializations (Datatable plugin)
	var registriesTable = $("#registrationsTable").DataTable({"order": [[ 0, "desc" ]]});
	var notesTable = $("#notesTable").DataTable();
	var alertsTable = $("#alertsTable").DataTable();
	
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
	
	function getDeleteAlertButtonHtml(id){
		return "<button id=\"" + id + "\" " +
				"type=\"button\" " +
				"class=\"btn btn-danger delete-alert\" " +
				"data-toggle=\"modal\" " +
				"data-target=\"#confirmAlertDeleteModal\">Apagar</button>";
	}
	
	/* ########################## Printing #############################*/
	
	/* Bind print function to print buttons */
	$(".printButton").click(function(){
		var printType = $(this).attr("id");
		var printUrl = "/vehicle/" + vehicleName + "/print?type=" + vehicleType +"&print=" + printType;
		var myWindow = window.open(printUrl,"","width = 800,height = 550");
		myWindow.print();
	});
	
	/* ##################### Delete Registrations ######################## */

	$(document).on("click", ".delete-reg", function(){
		registrationIdToDelete = $(this).attr("id");
	});
	
	/* Bind delete registration function to modal button */
	$("#deleteRegistration").click(function(){
		$.ajax({
			beforeSend: addCsrfHeader,
			url: "/vehicle/" + vehicleName + "/registration/" + registrationIdToDelete,
			type: "DELETE",
			success: function(result) {
				$("#confirmRegDeleteModal").modal("toggle");
				registriesTable.row(document.getElementById(registrationIdToDelete+"reg")).remove().draw(true);
			},
			error: function(errMsg) {
				$("#confirmRegDeleteModal").modal("toggle");
			},
		});
	});
	
	/* ####################### Delete Notes ######################## */
	
	$(document).on("click", ".delete-note", function(){
		noteIdToDelete = $(this).attr("id");
	});
	
	/* Remove Note - Note confirmation */
	$("#deleteNote").click(function(){
		$.ajax({
			beforeSend: addCsrfHeader,
			url: "/vehicle/" + vehicleName + "/note/" + noteIdToDelete,
			type: "DELETE",
			success: function(result) {
				$("#confirmNoteDeleteModal").modal("toggle");
				notesTable.row(document.getElementById(noteIdToDelete+"note")).remove().draw(true);
			},
			error: function(errMsg) {
				$("#confirmNoteDeleteModal").modal("toggle");
			},
		});
	});
	
	/* ####################### Delete Alerts ###################### */
	
	$(document).on("click", ".delete-alert", function(){
		alertIdToDelete = $(this).attr("id");
	});
	
	/* Remove Note - Note confirmation */
	$("#deleteAlert").click(function(){
		$.ajax({
			beforeSend: addCsrfHeader,
			url: "/vehicle/" + vehicleName + "/alert/" + alertIdToDelete,
			type: "DELETE",
			success: function(result) {
				$("#confirmAlertDeleteModal").modal("toggle");
				alertsTable.row(document.getElementById(alertIdToDelete+"alert")).remove().draw(true);
			},
			error: function(errMsg) {
				$("#confirmAlertDeleteModal").modal("toggle");
			},
		});
	});
	
	/* ################ Add Registration Form ################## */
	
	/* Needed due to form is inside modal, we need instanciate/destroy 
	 * validator in hide/show of the modal. */
	$("#registrationModal")
		.on("shown.bs.modal", function (e) {
			$("#time").focus();					//Focus the first input field.
			$("#registrationForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					var registration = new Object();
					registration.time = $("#time").val();
					registration.description = $("#description").val(); 
					registration.date = $("#date").val();
					$.ajax({
						beforeSend: addCsrfHeader,
						url: "/vehicle/" + vehicleName + "/registration",
						type: "POST",
						data: JSON.stringify(registration),
						contentType: "application/json; charset=utf-8",
						success: function(data, textStatus, request) {
							$("#registrationModal").modal("toggle");
							$("#responseAlertSuccessHeader").text("Registo")
							$("#responseAlertSuccessText").text("Registo adicionado com sucesso");
							$("#responseAlertModalSuccess").modal("toggle");
							var row = registriesTable.row.add([registration.time,
		                         registration.description,
		                         registration.date,
		                         getDeleteRegButtonHtml(data)]).draw(true).node();
							$(row).attr("id",data+"reg");
						},
						error: function(errMsg) {
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
			$("#note").focus();
			$("#noteForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					var token = $("meta[name='_csrf']").attr("content");
					var header = $("meta[name='_csrf_header']").attr("content");
					var note = new Object();
					note.description = $("#note").val();
					$.ajax({
						beforeSend: addCsrfHeader,
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
						},
						error: function(errMsg) {
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
			$("#alertDescription").focus();
			$("#alertForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					var alert = new Object();
					var url;
					alert.description = $("#alertDescription").val();
					alert.notiDate = $("#notiDate").val();
					if($("#periodicity").val() == "Ano a ano"){
						url = "/vehicle/" + vehicleName + "/notification?type=Year";
					} else if($("#periodicity").val() == "De meio em meio ano"){
						url = "/vehicle/" + vehicleName + "/notification?type=HalfYear";
					} else if($("#periodicity").val() == "Só uma vez"){
						url = "/vehicle/" + vehicleName + "/notification?type=OneTime";
					}
					$.ajax({
						beforeSend: addCsrfHeader,
						url: url,
						type: "POST",
						data: JSON.stringify(alert),
						contentType: "application/json; charset=utf-8",
						success: function(data, textStatus, request) {
							$("#alertsModal").modal("toggle");
							$("#responseAlertSuccessHeader").text("Alertas")
							$("#responseAlertSuccessText").text("Alerta adicionado com sucesso");
							$("#responseAlertModalSuccess").modal("toggle");
							var row = alertsTable.row.add([alert.description,alert.notiDate,
										                     getDeleteAlertButtonHtml(data)]).draw(true).node();
							$(row).attr("id",data+"alert");
						},
						error: function(errMsg) {
							$("#alertsModal").modal("toggle");
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
	
	/* ################### Edit Form ########################## */
	
	$("#editTabPill")
		.on("shown.bs.tab", function (e) {
			$("#editForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					var formData = new FormData(this);
					$.ajax({
						beforeSend: addCsrfHeader,
						url: "/vehicle/" + vehicleName + "/portrait",
						type: "POST",
						data: formData,
						cache: false,
			            contentType: false,
			            processData: false,
						success: function(data, textStatus, request) {
							// Reload the image
							$("#portraitImage").attr("src", $("#portraitImage").attr("src"));
						},
						error: function(errMsg) {
							$("#responseAlertFailHeader").text("Editar")
							$("#responseAlertFailText").text("Falha ao editar imagem");
							$("#responseAlertModalFail").modal("toggle");
						},
					});
			  	}
				return false;	//Prevent submit action from reloading the page
			});
		})
		.on("hidden.bs.tab", function (e) {
			$("#editForm").validator().off("submit");
			$("#editForm").validator("destroy");
	});

	/* ##################################################### */
	
});
