/* 
 * Objects to internationalisation in tables from Databale Plugin 
 */

var portugueseTableLang = { 
	lengthMenu: 	"Mostra _MENU_ registos por página",
	zeroRecords: 	"Nenhum registo encontrado",
	info: 			"Mostrando página _PAGE_ de _PAGES_ páginas",
    infoEmpty: 		"Sem registos",
    infoFiltered: 	"(filtrado de _MAX_ registos)",
	search: 		"Procurar: ",
	paginate: {
        first:      "Primeiro",
        last:       "Último",
        next:       "Próximo",
        previous:   "Anterior"
    }
};

var englishTableLang = { 
	lengthMenu: 	"Show _MENU_ records per page",
	zeroRecords: 	"Nothing found",
	info: 			"Showing page _PAGE_ of _PAGES_",
    infoEmpty: 		"No records available",
    infoFiltered: 	"(filtered from _MAX_ total records)",
	search: 		"Search: ",
	paginate: {
        first:      "First",
        last:       "Last",
        next:       "Next",
        previous:   "Previous"
    }
};

var chosenTableLang;

if(getCurrentLang() == "pt_PT") {
	chosenTableLang = portugueseTableLang;
} else if(getCurrentLang() == "en_US") {
	chosenTableLang = englishTableLang;
}

/* #################################################################### */
/* ##################### Auxiliary Functions ########################## */
/* #################################################################### */

/**
 * Add CRSF header and token to the ajax request: POST, DELETE, PUT, FETCH
 * needed when CSRF is enabled in Spring Security module
 * @param ajaxRequest
 */
function addCsrfHeader(ajaxRequest){
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	ajaxRequest.setRequestHeader(header, token);
}

/**
 * Populate Select input with all years from 1900 to the current year,
 * populate the select with current selectId
 * @param selectId
 */
function populateYearSelection(selectId){
	var currentYear = new Date().getFullYear();
	for(var year = currentYear;year >= 1900; year--) {
		$("#" + selectId).append("<option value=\""+ year +"\">" + year + "</option>");
	}
}

/**
 * Return current language selected by the user
 *
 */
function getCurrentLang() {
	return $("html").attr("lang");
}

/**
 * Set a parameter in the URL if it doesn't exist or replace if exist
 * @param name URL parameter name
 * @param value URL paramenter value
 */
function setParam(name, value) {
	var l = window.location;

    /* build params */
    var params = {};        
    var x = /(?:\??)([^=&?]+)=?([^&?]*)/g;        
    var s = l.search;
    for(var r = x.exec(s); r; r = x.exec(s)) {
        r[1] = decodeURIComponent(r[1]);
        if (!r[2]) r[2] = '%%';
        params[r[1]] = r[2];
    }

    /* set param */
    params[name] = encodeURIComponent(value);

    /* build search */
    var search = [];
    for(var i in params) {
        var p = encodeURIComponent(i);
        var v = params[i];
        if (v != '%%') p += '=' + v;
        search.push(p);
    }
    search = search.join('&');

    /* execute search */
    l.search = search;
}

$(document).ready(function() {
	
	/* Logout click handler  */
	$(".logout").on("click", function(){
		$.ajax({
			beforeSend: addCsrfHeader,
		    url: "/logout",
		    type: "POST",
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