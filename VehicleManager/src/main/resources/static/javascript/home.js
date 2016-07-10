$(document).ready(function() {
	var vehicleToDelete;
	var notificationDone;
	//Awesome Table initialization (Datatable plugin)
	var vehiclesTable = $("#vehicles").DataTable();
	var activeNotificationsTable = $("#activeNotificationsTable").DataTable();
	
	function getDeleteButtonHTML(vehicleName) {
		return "<button id=\"" + vehicleName + "\" " +
				"type=\"button\" " +
				"class=\"btn btn-danger del-vehicle\" " +
				"data-toggle=\"modal\" " +
				"data-target=\"#confirmModal\">" +
				"Apagar" +
				"</button>";
	}
	
	function getVehicleLinkHTML(vehicleName,type) {
		return  "<a href=\"/vehicle/" + vehicleName +
				"?type=" + type + "\" >" + vehicleName + "</a>";
	}
	
	function getVehiclePortraitHTML(vehicleName,type) {
		return  "<a href=\"/vehicle/" + vehicleName +
				"?type=" + type + "\" > <img src=\"/image/" + vehicleName + 
				"\" width=\"100vw\" height=\"75vh\" class=\"img-thumbnail\" " +
				"alt=\"Foto do Veículo\"/></a>";
	}
	
	populateYearSelection("fabricationYearU");
	
	/* ########## Print all vehicles data ############ */
	
	/* Bind print function to print button */
	$(".printButton").click(function(){
		var printType = $(this).attr("id");
		var printUrl = "/vehicles/print";
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
					var registration = new Object();
					registration.description = $("#doneDescription").val();
					registration.time = $("#time").val();
					registration.date = $("#doneDate").val();
					$.ajax({
						beforeSend: addCsrfHeader,
					    url: "/notification/completed/" + notificationDone,
					    type: "POST",
					    data: JSON.stringify(registration),
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
	
	$("#confirm").click(function( ){
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
			if (!ei.isDefaultPrevented()) {	//Valid Form
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
						vehiclesTable.row.add([getVehicleLinkHTML(vehicle.name,"licensed"),
						                       getVehiclePortraitHTML(vehicle.name,"licensed"),
						                       vehicle.brand,vehicle.acquisitionDate,
						                         getDeleteButtonHTML(vehicle.name)]).draw(true);
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
						vehiclesTable.row.add([getVehicleLinkHTML(vehicle.name,"unlicensed"),
						                       getVehiclePortraitHTML(vehicle.name,"unlicensed"),
						                       vehicle.brand,vehicle.acquisitionDate,
						                         getDeleteButtonHTML(vehicle.name)]).draw(true);
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