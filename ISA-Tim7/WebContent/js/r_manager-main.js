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
			"type" : $( "#empType option:selected" ).text(),
			"shoe" : $("input[name=empShSize]:checked").val(),
			"dress" : $("input[name=empDrSize]:checked").val(),
			"birth" : $("#empBirth").val(),
			"id_res": sessionStorage.restaurantId
		}),
	   	contentType : 'application/json',
		dataType : 'text',
	   	success : function(data) {
	   		if(data!="")
	   			$('#empError').html(data);
	   		else{
	   			
	   			bootbox.alert("Employee has successfully added");
	   			$('#addEmplModal').modal('toggle');
	   		}
	   			
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    },
	   	
	});	
};