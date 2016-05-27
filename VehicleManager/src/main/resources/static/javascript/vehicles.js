function loadTable(){
	$('#example').dataTable();
}

function deleteVehicle(vehicle){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			$(document.getElementById(vehicle)).remove();
		}
	};
	xhttp.open("DELETE", "/vehicle/"+vehicle, true);
	xhttp.send();
}

$(document).ready(loadTable);