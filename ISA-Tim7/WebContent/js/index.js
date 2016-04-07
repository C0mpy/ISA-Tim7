$(document).on('submit', '#form', function(e) {
	e.preventDefault();

	$.ajax ({
	   	url : "../ISA-Tim7/rest/user/login",
	   	type : "Post",
	   	data : JSON.stringify({
			"email" : $("#email").val(),
			"password" : $("#pass").val(),
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		
	   		if(data != null) {

	   			sessionStorage.firstName = data.firstName;
	   			sessionStorage.lastName = data.lastName;
	   			sessionStorage.password = data.password;
	   			sessionStorage.userType = data.type;
	   			sessionStorage.restaurantId = data.restaurantId;
	   			sessionStorage.employeeType = data.employeeType;  
	   			if(sessionStorage.userType == "SYS_MANAGER") {
	   				window.location.href = "admin-main.html";
	   			}
	   			else if(sessionStorage.userType == "R_MANAGER") {
	   				window.location.href = "r_manager-main.html";
	   			}
	   			else if(sessionStorage.userType == "EMPLOYEE") {
	   				if(sessionStorage.employeeType == "COOK") {
	   					
	   				}
	   				else if(sessionStorage.employeeType == "BARTENDER") {
	   					
	   				}
	   				else if(sessionStorage.employeeType == "WAITER") {
	   					window.location.href = "waiter.html";
	   				}
	   			}
	   			else if(sessionStorage.userType == "GUEST") {
	   				
	   			}
	   			else if(sessionStorage.userType == "SUPPLIER") {
	   				
	   			}
	   			
	   		}
	   		else {
	   			alert("nema naloga")
	   			
	   		}
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }
	    			
	});
});
