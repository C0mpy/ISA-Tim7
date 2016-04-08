/**
 * 
 */
function getEmployees(){
		
		$('#empTable').empty();
		$.ajax ({
		   	url : "../ISA-Tim7/rest/employee/get",
		   	type : "Post",
		   	data : JSON.stringify({
		   		"id_res": sessionStorage.restaurantId
			}),
		   	contentType : 'application/json',
			dataType : "json",
		   	success : function(data) {
		   		
		   		list = data == null ? [] : (data instanceof Array ? data : [ data ]);
		   		
		   		if (list.length !=0) {
		   			
				   	$.each(list, function(index, obj) {	   		
				   		
				   		$('#empTable')
				   		.append(
				   				"<thead>" +
				   				"<tr>"+
				   					"<th>Email</th>" +
				   					"<th>First Name</th>" +
				   					"<th>Last Name</th>"+
				   					"<th>Type</th>" + 
				   				"</tr>" +
				   				"</thead>" +
				   				"<tbody>"+
				   					"<tr>"+
				   					"<td>"+obj.email+"</td>"+
				   					"<td>"+obj.firstName+"</td>"+
				   					"<td>"+obj.lastName+"</td>"+
				   					"<td>"+obj.employeeType+"</td>" +
				   					"</tr></tbody>+" +
				   					"<thead>" +
				   				"<tr>"+
				   					"<th>Restaurant Id</th>" +
				   					"<th>Date Birth</th>" +
				   					"<th>Dress Size</th>"+
				   					"<th>Shoe Size</th>" + 
				   				"</tr>" +
				   				"</thead>" +
				   				"<tbody>"+
				   					"<tr>"+
				   					"<td>"+obj.restaurantId+"</td>"+
				   					"<td>"+obj.birth+"</td>"+
				   					"<td>"+obj.dress+"</td>"+
				   					"<td>"+obj.shoe+"</td>" +
				   					"</tr>" +
				   					"<tr>" +
				   					"<td colspan=\"4\"><button type=\"button\" class=\"btn btn-primary\" onclick=\"modifyEmployee()\">Modify</button>" +
				   					"&nbsp;<button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteEmployee()\">Delete</button>" +
				   					"&nbsp;<button type=\"button\" class=\"btn btn-primary\" onclick=\"scheduleEmployee()\">Schedule</button></td>" +
				   					"</tr><tr><td colspan=\"4\"><br><br><br></tr>" +
				   					"</tbody>")
			   		});
			   
			   	}
		   	},
		    error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("AJAX ERROR");
		    }
		    			
		});
		
	};
	

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