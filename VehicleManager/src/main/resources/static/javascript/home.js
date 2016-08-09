$(document).ready(function() {
	/* Awesome Table initialization (Datatable plugin)
	  - Only the first column content is searchable.
	  - State save, saves the table state between user navigations to other pages.
	*/
	var vehiclesTable = $("#vehicles").DataTable( { aoColumns : [ {"bSearchable": true}, 
	                                                              {"bSearchable": false},
	                                                              {"bSearchable": false},
	                                                              {"bSearchable": false}, 
	                                                              {"bSearchable": false}],
	                                                stateSave : true, 
	                                                language : chosenTableLang
												});
	vehiclesTable.state.clear();	// Reset the table on log in (same effect as at logout) !!
	var activeNotificationsTable = $("#activeNotificationsTable").DataTable( { language : chosenTableLang } );
	var vehicleToDelete;
	var notificationDone;
	
	/* ############ Populate Year Selection Fieldset ############# */
	
	populateYearSelection("fabricationYearU");
	
	/* ########## Print all vehicles data ############ */
	
	/* Bind print function to print button */
	$(".printButton").click(function() {
		var printType = $(this).attr("id");
		var printUrl = "/vehicles/print?lang=" + getCurrentLang();
		var myWindow = window.open(printUrl,"","width = 800,height = 550");
		myWindow.print();
	});
	
	/* ################## Task Done ################## */
	
	$(document).on("click", ".task-done", function() {
		notificationDone = $(this).attr("id");
	});
	
	$("#taskDoneModal")
		.on("shown.bs.modal", function (e) {
			var notiDescription = $("#" + notificationDone + "rowNoti").children("td").eq(1).text();
			$("#doneDescription").val(notiDescription);
			$("#taskDoneForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					$.ajax({
						beforeSend: addCsrfHeader,
					    url: "/notification/completed/" + notificationDone,
					    type: "POST",
					    data: JSON.stringify($("#taskDoneForm").serializeFormJSON()),
					    contentType: "application/json; charset=utf-8",
					    success: function(result) {
					    	$("#taskDoneModal").modal("toggle");
					    	activeNotificationsTable.row(document.getElementById(notificationDone+"rowNoti")).remove().draw(true);
					    },
						error: function(result) {
							$("#taskDoneModal").modal("toggle");
							$("#responseAlertFailHeader").text("Tarefa realizada");
							$("#responseAlertFailText").text("Problema ao submeter a realização da tarefa");
							$("#responseAlertModalFail").modal("toggle");
						}
					});
				}
				return false;	//Prevent submit reloading the page
			});
		})
		.on("hidden.bs.modal", function (e) {
			$("#taskDoneForm").validator().off("submit");
			$("#taskDoneForm").validator("destroy");
	});
	
	/* ################## Remove Vehicle ################## */
	
	$(document).on("click", ".del-vehicle", function() {
		vehicleToDelete = $(this).attr("id");
	});
	
	$("#confirm").click(function() {
		$.ajax({
			beforeSend: addCsrfHeader,
		    url: "/vehicle/" + vehicleToDelete,
		    type: "DELETE",
		    success: function(result) {
		    	$("#confirmModal").modal("toggle");
		    	vehiclesTable.row(document.getElementById(vehicleToDelete+"row")).remove().draw(true);
		    },
			error: function(result) {
				$("#confirmModal").modal("toggle");
				$("#responseAlertFailHeader").text("Remover veículo");
				$("#responseAlertFailText").text("Problema ao remover veículo");
				$("#responseAlertModalFail").modal("toggle");
			}
		});
	});	
	
	/* ################### Create Vehicle Forms #################### */
	
	function activateLicensedFormValidator() {
		$("#createLicensedVehicleForm").validator().on("submit", function (ei) {
			if (!ei.isDefaultPrevented()) {		//Valid Form
				var formData = new FormData();
				var vehicle = new Object();
				vehicle.license = new Object();
				vehicle.name = $("#nameL").val();
				vehicle.brand = $("#brandL").val();
				vehicle.acquisitionDate = $("#acquisitionDateL").val();
				vehicle.license.license = $("#licenseL").val();
				vehicle.license.date = $("#licenseDateL").val();
				formData.append("file",document.getElementById("fileL").files[0]);
				formData.append("vehicle",new Blob([JSON.stringify(vehicle)],{ type: "application/json" } ));
				$.ajax({
					beforeSend: addCsrfHeader,
					url: "/vehicle/licensed",
					type: "POST",
					data: formData,
					// Options to tell jQuery not to process data or worry about content-type.
					contentType: false,
					processData: false,
					success: function(response) {
						$("#vehicleModal").modal("toggle");
						$("#responseAlertSuccessHeader").text("Criação de Veículo");
						$("#responseAlertSuccessText").text("Veículo criado com sucesso");
						$("#responseAlertModalSuccess").modal("toggle");
						$.get({
							  url: "/home/vehicleTableRow/" + vehicle.name + "/type/licensed",
							  success: function(response) {
								  var wrapper = document.createElement("thead");
								  wrapper.innerHTML = response;
								  vehiclesTable.row.add(wrapper.firstChild).draw(true);
							  },
							  error: function(xhr) { location.reload(true); }
						});
					},
					error: function(errMsg) {
						$("#vehicleModal").modal("toggle");
						$("#responseAlertFailHeader").text("Criação de Veículo");
						$("#responseAlertFailText").text("Problema ao criar veículo");
						$("#responseAlertModalFail").modal("toggle");
					}
				});
			}
			return false;
		});
	}
	
	function activateUnlicensedFormValidator() {
		$("#createUnlicensedVehicleForm").validator().on("submit", function (ee) {
			if (!ee.isDefaultPrevented()) {	//Valid Form
				var formData = new FormData();
				var vehicle = new Object();
				vehicle.name = $("#nameU").val();
				vehicle.brand = $("#brandU").val();
				vehicle.fabricationYear = $("#fabricationYearU").val();
				vehicle.acquisitionDate = $("#acquisitionDateU").val();
				formData.append("file",document.getElementById("fileU").files[0]);
				formData.append("vehicle",new Blob([JSON.stringify(vehicle)],{ type: "application/json" } ));
				$.ajax({
					beforeSend: addCsrfHeader,
					url: "/vehicle/unlicensed",
					type: "POST",
					data: formData,
					// Options to tell jQuery not to process data or worry about content-type.
					contentType: false,
					processData: false,
					success: function(response) {
						$("#vehicleModal").modal("toggle");
						$("#responseAlertSuccessHeader").text("Criação de Veículo");
						$("#responseAlertSuccessText").text("Veículo criado com sucesso");
						$("#responseAlertModalSuccess").modal("toggle");
						$.get({
							  url: "/home/vehicleTableRow/" + vehicle.name + "/type/unlicensed",
							  success: function(response) {
								  var wrapper = document.createElement("thead");
								  wrapper.innerHTML = response;
								  vehiclesTable.row.add(wrapper.firstChild).draw(true);
							  },
							  error: function(xhr) { location.reload(true); }
						});
					},
					error: function(errMsg) {
						$("#vehicleModal").modal("toggle");
						$("#responseAlertFailHeader").text("Criação de Veículo");
						$("#responseAlertFailText").text("Problema ao criar veículo");
						$("#responseAlertModalFail").modal("toggle");
					}
				});
			}
			return false;
		});
	}
	
	/* Needed due to form is inside tab, we need instanciate/destroy 
	 * validator in hide/show of the tab. */
	$("#vehicleModal")
		.on("shown.bs.modal", function (e) {
			$("#nameU").focus();
			activateUnlicensedFormValidator();
		})
		.on("hidden.bs.modal", function (e) {
			$("#createUnlicensedVehicleForm").validator().off("submit");
			$("#createUnlicensedVehicleForm").validator("destroy");
	});
	
	/* Needed due to form is inside tab, we need instanciate/destroy 
	 * validator in hide/show of the tab. */
	$("#unlicensedTab")
		.on("shown.bs.tab", function (e) {
			$("#nameU").focus();
		})
		.on("hidden.bs.tab", function (e) {
			$("#nameU").blur();
	});
	
	/* Needed due to form is inside tab, we need instanciate/destroy 
	 * validator in hide/show of the tab. */
	$("#licensedTab")
		.on("shown.bs.tab", function (e) {
			$("#nameL").focus();
			activateLicensedFormValidator();
		})
		.on("hidden.bs.tab", function (e) {
			$("#createLicensedVehicleForm").validator().off("submit");
			$("#createLicensedVehicleForm").validator("destroy");
	});
	
	
});