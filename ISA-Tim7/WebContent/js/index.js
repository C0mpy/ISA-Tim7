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
	   			
	   			sessionStorage.email = data.email;
	   			sessionStorage.firstName = data.firstName;
	   			sessionStorage.lastName = data.lastName;
	   			sessionStorage.password = data.password;
	   			sessionStorage.userType = data.type;  
	   			if(sessionStorage.userType == "SYS_MANAGER") {
	   				window.location.href = "admin-main.html";
	   			}
	   			else if(sessionStorage.userType == "R_MANAGER") {
	   				sessionStorage.restaurantId = data.restaurantId;
	   				window.location.href = "r_manager-main.html";
	   			}
	   			else if(sessionStorage.userType == "EMPLOYEE") {
	   				
	   				sessionStorage.restaurantId = data.restaurantId;
		   			sessionStorage.employeeType = data.employeeType;
		   			sessionStorage.dateOfBirth = data.birth;
		   			sessionStorage.dressSize = data.dress;
		   			sessionStorage.shoeSize = data.shoe;
	   				
	   				if(sessionStorage.employeeType == "COOK") {
	   					window.location.href = "cook.html";
	   				}
	   				else if(sessionStorage.employeeType == "BARTENDER") {
	   					window.location.href = "bartender.html";
	   				}
	   				else if(sessionStorage.employeeType == "WAITER") {
	   					window.location.href = "waiter.html";
	   				}
	   			}
	   			else if(sessionStorage.userType == "GUEST") {
	   				sessionStorage.activated=data.activated;
	   				window.location.href="guest.html"
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
