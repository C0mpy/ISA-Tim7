/**
 * 
 */

function addEmployee() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/employee/add",
	   	type : "Post",
	   	data : JSON.stringify({
			"f_name" : $("#empFirName").val(),
			"l_name" : $("#empLasName").val(),
			"email" : $("#empEmail").val(),
			"pass" : $("#empPass").val(),
			"type" : $( "#empType option:selected" ).text()
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		alert("added");
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }
	    			
	});	
};