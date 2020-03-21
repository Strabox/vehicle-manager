$(document).ready(function(){
	var vehicleName = $("#vehicleName").text();
	var vehicleType = $("#vehicleType").attr("name");
	var noteIdToDelete;
	var registrationIdToDelete;
	var alertIdToDelete;
	// Awesome Tables initializations (Datatable plugin)
	//Order by time (descending order) and untie with date (descending order)
	var registriesTable = $("#registrationsTable").DataTable({order : [[ 0, "desc" ], [2,"desc"]], language : chosenTableLang});
	var notesTable = $("#notesTable").DataTable( { language : chosenTableLang } );
	var alertsTable = $("#alertsTable").DataTable( { language : chosenTableLang } );
	
	function populateEditForm() {
		$("#editName").val($("#vehicleName").text());
		$("#editBrand").val($("#vehicleBrand").text());
		document.getElementById("editAcquisitionDate").valueAsDate = new Date($("#vehicleAcquistionDate").text());
		$("#editFabricationYear option").filter(function() { return $(this).text() == $("#vehicleFabricationYear").text(); }).prop("selected",true);
		$("#editLicense").val($("#vehicleLicense").text());
		if($("#vehicleLicenseDate").length) {
			document.getElementById("editLicenseDate").valueAsDate = new Date($("#vehicleLicenseDate").text());
		}
	}
	
	function updateVehicleInformation(vehicleName) {
		$("#vehicleBrand").text($("#editBrand").val());
		$("#vehicleAcquistionDate").text($("#editAcquisitionDate").val());
		$("#vehicleFabricationYear").text($("#editFabricationYear").val());
		$("#vehicleLicense").text($("#editLicense").val());
		$("#vehicleLicenseDate").text($("#editLicenseDate").val());
		$("#calculationAcquisitionYears").load(encodeURI("/vehicle/" + vehicleName + "/acquisitionyears"));
		$("#calculationLicenseYears").load(encodeURI("/vehicle/" + vehicleName + "/licenseyears"));
		$("#calculationFabricationAge").load(encodeURI("/vehicle/" + vehicleName + "/years"));
	}
	
	/* ################## Populating Elements ########################## */
	
	//Populate Edit Form Select options (fabrication Year)
	populateYearSelection("editFabricationYear");
	
	/* ########################## Printing ############################# */
	
	/* Bind print function to print buttons */
	$(".printButton").click(function(){
		var printType = $(this).attr("id");
		var printUrl = "/vehicle/" + vehicleName + "/print?type=" + vehicleType +"&print=" + printType + 
						"&lang=" + getCurrentLang();
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
					$.ajax({
						beforeSend: addCsrfHeader,
						url: "/vehicle/" + vehicleName + "/registration",
						type: "POST",
						data: JSON.stringify($("#registrationForm").serializeFormJSON()),
						contentType: "application/json; charset=utf-8",
						success: function(data, textStatus, request) {
							$("#registrationModal").modal("toggle");
							$("#responseAlertSuccessHeader").text("Registo")
							$("#responseAlertSuccessText").text("Registo adicionado com sucesso");
							$("#responseAlertModalSuccess").modal("toggle");
							$.get({
								  url: "/vehicle/registrationTableRow/" + vehicleName + "/regId/" + data,
								  success: function(response) {
									  var wrapper = document.createElement("thead");
									  wrapper.innerHTML = response;
									  registriesTable.row.add(wrapper.firstChild).draw(true);
								  },
								  error: function(xhr) { location.reload(true); }
							});
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
					$.ajax({
						beforeSend: addCsrfHeader,
						url: "/vehicle/" + vehicleName + "/note",
						type: "POST",
						data: JSON.stringify($("#noteForm").serializeFormJSON()),
						contentType: "application/json; charset=utf-8",
						success: function(data, textStatus, request) {
							$("#notesModal").modal("toggle");
							$("#responseAlertSuccessHeader").text("Notas")
							$("#responseAlertSuccessText").text("Nota adicionada com sucesso");
							$("#responseAlertModalSuccess").modal("toggle");
							$.get({
								  url: "/vehicle/noteTableRow/" + vehicleName + "/noteId/" + data,
								  success: function(response) {
									  var wrapper = document.createElement("thead");
									  wrapper.innerHTML = response;
									  notesTable.row.add(wrapper.firstChild).draw(true);
								  },
								  error: function(xhr) { location.reload(true); }
							});
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
					var url;
					if($("#periodicity").val() == "Todos os anos"){
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
						data: JSON.stringify($("#alertForm").serializeFormJSON()),
						contentType: "application/json; charset=utf-8",
						success: function(data, textStatus, request) {
							$("#alertsModal").modal("toggle");
							$("#responseAlertSuccessHeader").text("Alertas")
							$("#responseAlertSuccessText").text("Alerta adicionado com sucesso");
							$("#responseAlertModalSuccess").modal("toggle");
							$.get({
								  url: "/vehicle/notificationTaskTableRow/" + vehicleName + "/notiId/" + data,
								  success: function(response) {
									  var wrapper = document.createElement("thead");
									  wrapper.innerHTML = response;
									  alertsTable.row.add(wrapper.firstChild).draw(true);
								  },
								  error: function(xhr) { location.reload(true); }
							});
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
			$("#editImageForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					$.ajax({
						beforeSend: addCsrfHeader,
						url: "/vehicle/" + vehicleName + "/portrait",
						type: "POST",
						data: new FormData(this),
						cache: false,
			            contentType: false,
			            processData: false,
						success: function(data, textStatus, request) {
							// Reload the images
							$("#portraitImage").attr("src", $("#portraitImage").attr("src"));
							$("#vehiclePortraitFull").attr("src", $("#vehiclePortraitFull").attr("src"));
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
			
			populateEditForm();	//Initialize the edit form with current vehicle data
			
			$("#editVehicleForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					$.ajax({
						beforeSend: addCsrfHeader,
						url: "/vehicle/" + vehicleType + "/" + vehicleName + "/edit",
						type: "POST",
						data: JSON.stringify($("#editVehicleForm").serializeFormJSON()),
			            contentType: "application/json; charset=utf-8",
						success: function(data, textStatus, request) {
							updateVehicleInformation(vehicleName);
							$("#responseAlertSuccessHeader").text("Editar Informação")
							$("#responseAlertSuccessText").text("Informação editada com sucesso");
							$("#responseAlertModalSuccess").modal("toggle");
						},
						error: function(errMsg) {
							populateEditForm();	//Reset the form
							$("#responseAlertFailHeader").text("Editar Informação")
							$("#responseAlertFailText").text("Falha ao editar informação");
							$("#responseAlertModalFail").modal("toggle");
						},
					});
			  	}
				return false;	//Prevent submit action from reloading the page
			});
			
			$("#editVehicleForm").validator("validate");	//Call validate immediately
		})
		.on("hidden.bs.tab", function (e) {
			$("#editImageForm").validator().off("submit");
			$("#editImageForm").validator("destroy");
			$("#editVehicleForm").validator().off("submit");
			$("#editVehicleForm").validator("destroy");
	});

	/* ##################################################### */
	
});
