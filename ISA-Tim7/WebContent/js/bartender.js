$( document ).ready(function() {
    
	$("#showOrders").on('shown.bs.modal', function () {
		loadOrders();
	});
	
	$("#calendar").fullCalendar({
	});

	$("#shifts").on('shown.bs.modal', function () {
        $("#calendar").fullCalendar('render');
	});
	
	$("#editProfile").on('show.bs.modal', function () {
		loadEmployeeData();
	})
	
	fillEvents();

});

function loadOrders() {
	
	$('#orders').empty();
	$.ajax ({
	   	url : "../ISA-Tim7/rest/order/getOpen",
	   	type : "Post",
	   	data : JSON.stringify({
			"rest" : sessionStorage.restaurantId,
			"employee" : "BARTENDER"
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		
	   		list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	   		
	   		if (list.length !=0) {
	   			
			   	$.each(list, function(index, obj) {	   		   		
			   		$("#orders")
			   	    .append($('<tr>')
			   	        .append($('<td>')
			   	            .append($('<p>')
			   	                .text(obj.orderId)
			   	            )
			   	            .addClass("first")
			   	        )
			   	        .append($('<td>')
			   	        	.append($('<p>')
			   	        		.text(obj.name)
			   	        	)
			   	        	.addClass("second")
			   	        )
			   	        .append($('<td>')
				   	        .append($('<button>')
				   	        	.addClass("btn btn-primary")
				   	        	.text("Take Order")
				   	        	.click(function() {
				   	        		console.log(obj.orderId);
				   	        	})
				   	        )
				   	        .addClass("third")
				   	    )
				   	)

		   		});
		   
		   	}
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }
	    			
	});
}

function fillEvents() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/employee/getShifts",
	   	type : "Post",
	   	data : JSON.stringify({
			"email" : sessionStorage.email
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		
	   		list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	   		
	   		if (list.length !=0) {
	   			
			   	$.each(list, function(index, obj) {	   		
			   		
			   		var event={title: obj.time, start: new Date(obj.date)};

			   		$('#calendar').fullCalendar('renderEvent', event, true);
		   		});
		   
		   	}
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }
	    			
	});
}

function loadEmployeeData() {
	$("#empFirName").val(sessionStorage.firstName);
	$("#empLasName").val(sessionStorage.lastName);
	$("#empEmail").val(sessionStorage.email);
	$("#empPass").val(sessionStorage.password);
	$("#empType").val(sessionStorage.employeeType);
	$("#empBirth").val(sessionStorage.dateOfBirth);
	$("#empDrSize label input:radio").each(function() {
		if($(this).val() == sessionStorage.dressSize) {
			$(this).prop("checked", true);
			$(this).parent().addClass("active");
		} 
	});
	$("#empShSize label input:radio").each(function() {
		if($(this).val() == sessionStorage.shoeSize) {
			$(this).prop("checked", true);
			$(this).parent().addClass("active");
		} 
	});
}


function saveChanges() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/employee/editProfile",
	   	type : "Post",
	   	data : JSON.stringify({
			"f_name" : $("#empFirName").val(),
			"l_name" : $("#empLasName").val(),
			"email" : sessionStorage.email,
			"pass" : $("#empPass").val(),
			"type" : $( "#empType option:selected" ).val(),
			"shoe" : $("input[name=empShSize]:checked").val(),
			"dress" : $("input[name=empDrSize]:checked").val(),
			"birth" : $("#empBirth").val(),
			"id_res": sessionStorage.restaurantId
		}),
	   	contentType : 'application/json',
		dataType : 'text',
	   	success : function(data) {
		   	bootbox.alert("Profile data changed!");
		   	$('#editProfile').modal('toggle');	
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    }
	});
	   	
}
