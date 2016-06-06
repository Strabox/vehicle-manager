var vehicleToDelete;

function prepareVehicleRemove(vehicle){
	vehicleToDelete = vehicle;
}

$(document).ready(function(){
	//Awesome Table initialization
	var vehiclesTable = $("#vehicles").DataTable();
	//Click Event - Send remove request
	$("#confirm").click(function(){
		$.ajax({
		    url: "/vehicle/" + vehicleToDelete,
		    type: "DELETE",
		    success: function(result) {
		    	$('#confirmModal').modal("toggle");
		    	vehiclesTable.row(document.getElementById(vehicleToDelete)).remove().draw(true);
		    },
			failure: function(result) {
				//TODO
			}
		});
	});	
	// Click Event - Submit Vehicle
	$("#createVehicleForm").validator().on("submit", function (e) {
		  if (e.isDefaultPrevented()) {	//NOT OK
		    // handle the invalid form...
			  alert("crap;")
		  } else {						//OK
			  alert("nice");
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
					  alert("OK");
					  $("#createResponse").text("Veículo criado com sucesso");
					  $("#creationModal").delay(500).modal("toggle");
				  },
				  failure: function(errMsg) {
					  $("#createResponse").text("Problema ao criar veículo");
					  $("#creationModal").modal("toggle");
				  }
			  });
		  }
	});
});
