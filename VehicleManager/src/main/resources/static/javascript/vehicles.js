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
			  var unlicensedVehicle = new Object();
			  unlicensedVehicle.name = $("#name").val();
			  unlicensedVehicle.brand = $("#brand").val();
			  unlicensedVehicle.acquisitionDate = $("#acquisitionDate").val();
			  var data = JSON.stringify(unlicensedVehicle);
			  $.ajax({
				  url: "/vehicle/unlicensed",
				  type: "POST",
				  data: data,
				  contentType: "application/json; charset=utf-8",
				  success: function(response) {
					  location.reload();
					  $("#createResponse").text("Veículo criado com sucesso");
					  $("#creationModal").modal("toggle");
				  },
				  failure: function(errMsg) {
					  $("#createResponse").text("Problema ao criar veículo");
					  $("#creationModal").modal("toggle");
				  }
			  });
		  }
	});
});
