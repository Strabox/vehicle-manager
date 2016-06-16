/* Add CRSF header and token to the ajax request: POST, DELETE, PUT, FETCH
 * EXCEPT the Logout POST. */
function addCsrfHeader(ajaxRequest){
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	ajaxRequest.setRequestHeader(header, token);
}
	
$(document).ready(function(){
	
	/* Logout click handler */
	$("#logout").click(function(){
		var csrfToken = $("meta[name='_csrf']").attr("content");
		$.ajax({
		    url: "/logout",
		    type: "POST",
		    data: "_csrf=" + csrfToken,
		    success: function(data, textStatus) {
		        window.location.href = "/login?logout"
		    }
		});
	});
	
});