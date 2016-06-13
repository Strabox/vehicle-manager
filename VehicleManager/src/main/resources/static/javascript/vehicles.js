function getDeleteButtonHTML(vehicleName){
	var res = "<button id=\"" + vehicleName + "\" " +
			"type=\"button\" " +
			"class=\"btn btn-danger del-vehicle\" " +
			"data-toggle=\"modal\" " +
			"data-target=\"#confirmModal\">" +
			"Apagar" +
			"</button>";
	alert(res);
	return res;
}

$(document).ready(function(){
	var vehicleToDelete;
	//Awesome Table initialization
	var vehiclesTable = $("#vehicles").DataTable();
	
	/* ################## Remove Vehicle ################## */
	
	/* Prepare vehicle to be removed. */
	$(".del-vehicle").click(function(){
		vehicleToDelete = $(this).attr("id");
	});
	
	$("#confirm").click(function(){
		$.ajax({
		    url: "/vehicle/" + vehicleToDelete,
		    type: "DELETE",
		    success: function(result) {
		    	$("#confirmModal").modal("toggle");
		    	vehiclesTable.row(document.getElementById(vehicleToDelete+"row")).remove().draw(true);
		    },
			failure: function(result) {
				//TODO
			}
		});
	});	
	
	/* ################### Create Vehicle Form #################### */
	
	/* Needed due to form is inside modal, we need instanciate/destroy 
	 * validator in hide/show of the modal. */
	$("#vehicleModal")
		.on("shown.bs.modal", function (e) {
			$("#createVehicleForm").validator().on("submit", function (e) {
				if (!e.isDefaultPrevented()) {	//Valid Form
					alert("Submitting");
					var formData = new FormData();
					var vehicle = new Object();
					vehicle.name = $("#name").val();
					vehicle.brand = $("#brand").val();
					vehicle.acquisitionDate = $("#acquisitionDate").val();
					formData.append("file",document.getElementById("file").files[0]);
					formData.append("vehicle",new Blob([JSON.stringify(vehicle)],{ type: "application/json" } ));
					$.ajax({
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
							vehiclesTable.row.add([vehicle.name,vehicle.brand,vehicle.acquisitionDate,
							                         getDeleteButtonHTML(vehicle.name)]).draw(true);
						},
						failure: function(errMsg) {
							$("#vehicleModal").modal("toggle");
							$("#responseAlertFailHeader").text("Criação de Veículo");
							$("#responseAlertFailText").text("Problema ao criar veículo");
							$("#responseAlertModalFail").modal("toggle");
						}
					});
				}
				return false;
			});
		})
		.on("hidden.bs.modal", function (e) {
			$("#createVehicleForm").validator().off("submit");
			$("#createVehicleForm").validator("destroy");
	});

});
