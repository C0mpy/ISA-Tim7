$(document).ready(function() {
	
	$("#name").text(sessionStorage.firstName + " " + sessionStorage.lastName);
	$("#email").text(sessionStorage.email);
	$("#birth").text(sessionStorage.dateOfBirth);
	$("#calendar").fullCalendar({
	});

	$("#shifts").on('shown.bs.modal', function () {
        $("#calendar").fullCalendar('render');
	});
	
	$("#editProfile").on('show.bs.modal', function () {
		loadEmployeeData();
	})
	
	fillEvents();
	loadCanvas();
});

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

function loadCanvas() {
	
	var canvas = new fabric.Canvas('canvas', {backgroundColor : "rgb(230, 230, 230)"});
	fabric.Object.prototype.selectable = false;
	
	canvas.on('mouse:up', function(e) {
		
		var activeObject = e.target;
		$.ajax ({
		   	url : "../ISA-Tim7/rest/order/getOrderForTable",
		   	type : "Post",
		   	data : JSON.stringify({
				"table_id": activeObject.get('id'),
				"rest_id": sessionStorage.restaurantId
			}),
		   	contentType : 'application/json',
			dataType : 'json',
		   	success : function(data) {
		   		$("#order").empty();
		   		$("#order")
		   	    .append($('<li>')
		   	    	.addClass("list-group-item")
		   	    	.addClass("list-group-item-warning")
		   	        .append($('<h3>')
		   	            .text("Orders:")
		   	        )
		 
		   	    )
		   		list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			   	$.each(list, function(index, obj) {
			   		$("#order")
			   	    .append($('<li>')
			   	    	.addClass("list-group-item")
			   	    	.addClass("list-group-item-warning")
			   	        .append($('<p>')
			   	            .text(obj.name + " : " + obj.price + " din ")
			   	        )
			 
			   	    )

			   	});
		   		
		   	},
		    error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("Ajax error");
		    },
		   	
		});	
	});
	
	$("#canvWrap").css({"border-color": "#C1E0FF", 
        "border-weight":"1px", 
        "border-style":"solid"});
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/getPlan",
	   	type : "Post",
	   	data : JSON.stringify({
			"id_res": sessionStorage.restaurantId
		}),
	   	contentType : 'application/json',
		dataType : 'text',
	   	success : function(data) {
	   		canvas.loadFromJSON(data, canvas.renderAll.bind(canvas));
	   		
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    },
	   	
	});	
	
	canvas.setWidth($("#wrap").width());
	canvas.setHeight($("#profile").height());

}