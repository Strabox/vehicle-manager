/* Add CRSF header and token to the ajax request: POST, DELETE, PUT, FETCH
 * except!! the logout POST. */
function addCsrfHeader(ajaxRequest){
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	ajaxRequest.setRequestHeader(header, token);
}

/*
 * Populate Select input with all years from 1900 to the current year,
 * populate the select with current selectId
 */
function populateYearSelection(selectId){
	var currentYear = new Date().getFullYear();
	for(var year = currentYear;year >= 1900; year--) {
		$("#" + selectId).append("<option value=\""+ year +"\">" + year + "</option>");
	}
}

$(document).ready(function(){
	
	/* Logout click handler  */
	$(".logout").click(function(){
		var csrfToken = $("meta[name='_csrf']").attr("content");
		$.ajax({
		    url: "/logout",
		    type: "POST",
		    data: "_csrf=" + csrfToken,
		    success: function(data, textStatus) {
		        window.location.href = "/login?logout"
		    },
			error: function(data, textStatus) {
				window.location.href = "/expiredSession";
			}
		});
	});
	
	/* Form function to serialize form to a JSON object */
	$.fn.serializeFormJSON = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
	
});