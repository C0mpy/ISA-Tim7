/**
 * 
 */



$.getScript("js/fabric.js", function(){

});



$(document).on({
    ajaxStart: function() { $("body").addClass("loading");    },
     ajaxStop: function() { $("body").removeClass("loading"); }    
});

$(document).ready(function() {
	
	draggableEvents();


	/* initialize the calendar
	-----------------------------------------------------------------*/

	$('#calendar').fullCalendar({
		theme: true,
		
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month'
		},
		isUserCreated: true,
		defaultView: 'month',
		editable: true,
		droppable: true, // this allows things to be dropped onto the calendar
		drop: function(event) {
			
		
			shifts.push(event.format())
			
			// is the "remove after drop" checkbox checked?
			if ($('#drop-remove').is(':checked')) {
				// if so, remove the element from the "Draggable Events" list
				$(this).remove();
			}
		},
		
		eventReceive: function(event){
			shifts[shifts.length-1] = shifts[shifts.length-1] +" "+ event.title;
			
		},
		
		eventDrop: function(event, delta, revertFunc) {
	        shifts.push(event.start.format()+" "+event.title);
	       

	    },
		
		eventDragStop: function(event, jsEvent, ui, view) {
			shifts.splice(shifts.indexOf(event.start.format()+" "+event.title),1);
			
		}
	});
});

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
				   					"&nbsp;<button type=\"button\" class=\"btn btn-danger btn-ok\" onclick=\"deleteEmployee()\">Delete</button>" +
				   					"&nbsp;<button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#addSchedule\" onclick=\"scheduleEmployee('"+obj.email+"','"+obj.restaurantId+"')\">Schedule</button></td>" +
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

var shifts = new Array();
var currentEmail = {};
var currentId = {};
function scheduleEmployee(){
	/*
	//Get all client events
	var allEvents = $('#calendar').fullCalendar('clientEvents');

	var userEventIds= [];

	//Find ever non usercreated event and push the id to an array
	$.each(allEvents,function(index, value){
	    if(value.isUserCreated !== true){
	    userEventIds.push(value._id);
	    }
	});

	*/
	
	shifts=[];
	currentEmail = arguments[0];
	currentId = arguments[1];
	calendarReady();
	$('#mb').append($('#wrap'));
	
	//Remove events with ids of non usercreated events
	$('#calendar').fullCalendar( 'removeEvents', userEventIds);
	
	$('#calendar').fullCalendar('render');
	
	$('#calendar').fullCalendar('rerenderEvents');
	$('#calendar').fullCalendar('refresh');
	
	
};

function saveShifts(){
	
	for (i = 0; i < shifts.length; i++) { 
	    var temp = shifts[i].split(" ");
	    var date = temp[0];
	    var time = temp[1];
	    
	    $.ajax ({
		   	url : "../ISA-Tim7/rest/employee/shifts",
		   	type : "Post",
		   	data : JSON.stringify({
				"email" : currentEmail,
				"date" : date,
				"time" : time
			}),
		   	contentType : 'application/json',
			dataType : 'text',
		   	success : function(data) {
		   		bootbox.alert("The schedule is successfully changed");
		   			
		   	},
		    error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("Ajax error");
		    },
		   	
		});	
	    
	}
}

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

function printFood(){
	$('#productTable').empty();
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/getFood",
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
			   		
			   		$('#productTable')
			   		.append(
			   				"<thead>" +
			   				"<tr>"+
			   					"<th>Name</th>" +
			   					"<th>Price</th>" + 
			   					"<th>Type</th>" +
			   				"</tr>" +
			   				"</thead>" +
			   				"<tbody>"+
			   					"<tr>"+
			   					"<td>"+obj.name+"</td>"+
			   					"<td>"+obj.price+"<span class=\"glyphicon glyphicon-euro \" style=\"font-size:14px\"></td>"+
			   					"<td>"+obj.type+"</td>"+
			   					"</tr></tbody>+" +
			   					"<thead>" +
			   				"<tr>"+
			   					"<th>Description</th>" +
			   				"</tr>" +
			   				"</thead>" +
			   				"<tbody>"+
			   					"<tr>"+
			   					"<td>"+obj.description+"</td>"+
			   					"</tr>" +
			   					"<tr>" +
			   					"<td colspan=\"4\"><button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#modifyProd\" onclick=\"setProduct('"+obj.orderId+"')\">Modify</button>" +
			   					"&nbsp;<button type=\"button\" class=\"btn btn-danger btn-ok\" onclick=\"deleteProduct('"+obj.orderId+"')\">Delete</button></td>" +
			   					"</tr><tr><td colspan=\"4\"><br><br><br></tr>" +
			   					"</tbody>")
		   		});
			   	
		   	}
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }
	    			
	});
	
}

function setResDetails(){
	document.getElementById("restName").value ="";
	$('#resDescription').empty();
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/getResDetails",
	   	type : "Post",
	   	data : JSON.stringify({
	   		"id_res": sessionStorage.restaurantId
		}),
	   	contentType : 'application/json',
		dataType : "json",
		success : function(data) {
	   		if(data!=""){
	   			document.getElementById("restName").value += data.name;
	   			$('#resDescription').html(data.description);
	   		}
	   			
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    }		
	});
	
}

function modifyResDetails(){
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/addResDetails",
	   	type : "Post",
	   	data : JSON.stringify({
	   		"id_res": sessionStorage.restaurantId,
	   		"name" : $("#restName").val(),
			"description" : $("#resDescription").val(),
	   		
		}),
	   	contentType : 'application/json',
		dataType : "text",
		success : function(data) {
	   		if(data!="")
	   			$('#modifyResError').html(data);
	   		else{
	   			bootbox.alert("Restaurant details has successfully modified");
	   			$('#modify').modal('toggle');
	   		}
	   			
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	
	    	alert("Ajax error");
	    }			
	});
	
}

function printBeverage(){
	$('#productTable').empty();
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/getBeverage",
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
			   		
			   		$('#productTable')
			   		.append(
			   				"<thead>" +
			   				"<tr>"+
			   					"<th>Name</th>" +
			   					"<th>Price</th>" + 
			   					"<th>Type</th>" +
			   				"</tr>" +
			   				"</thead>" +
			   				"<tbody>"+
			   					"<tr>"+
			   					"<td>"+obj.name+"</td>"+
			   					"<td>"+obj.price+"<span class=\"glyphicon glyphicon-euro \" style=\"font-size:14px\"></td>"+
			   					"<td>"+obj.type+"</td>"+
			   					"</tr></tbody>+" +
			   					"<thead>" +
			   				"<tr>"+
			   					"<th>Description</th>" +
			   				"</tr>" +
			   				"</thead>" +
			   				"<tbody>"+
			   					"<tr>"+
			   					"<td>"+obj.description+"</td>"+
			   					"</tr>" +
			   					"<tr>" +
			   					"<td colspan=\"4\"><button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#modifyProd\"  onclick=\"setProduct('"+obj.orderId+"')\">Modify</button>" +
			   					"&nbsp;<button type=\"button\" class=\"btn btn-danger btn-ok\" onclick=\"deleteProduct('"+obj.orderId+"')\">Delete</button></td>" +
			   					"</tr><tr><td colspan=\"4\"><br><br><br></tr>" +
			   					"</tbody>")
		   		});
			   	
		   	}
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    } 			
	});
	
}

function deleteProduct(s){
	bootbox.confirm("Are you sure you want to delete a product?", function(result) {
		  	if(result){
		  		
		  		$.ajax ({
		  		   	url : "../ISA-Tim7/rest/restaurant/deleteProd",
		  		   	type : "Post",
		  		   	data : JSON.stringify({
		  				"id_res": sessionStorage.restaurantId,
		  				"id_product": s
		  			}),
		  		   	contentType : 'application/json',
		  			dataType : 'json',
		  		   	success : function(data) {
		  		   		bootbox.alert("Product has successfully deleted");
		  		   		
		  		   		
		  		   			
		  		   	},
		  		    error : function(XMLHttpRequest, textStatus, errorThrown) {
		  		    	alert("Ajax error");
		  		    },
		  		   	
		  		});			
		  	}	
		});

}

var productId=null;
function setProduct(s){
	productId = s;
	
	document.getElementById("modifyName").value ="";
	$('#modifyDescription').empty();
	document.getElementById("modifyPrice").value ="";
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/getProdDetails",
	   	type : "Post",
	   	data : JSON.stringify({
	   		"id_res": sessionStorage.restaurantId,
	   		"id_product": s
		}),
	   	contentType : 'application/json',
		dataType : "json",
		success : function(data) {
	   		if(data!=""){
	   			document.getElementById("modifyName").value = data.name;
	   			$('#modifyDescription').html(data.description);
	   			document.getElementById("modifyPrice").value = data.price;
	   			
	   			document.getElementById("modifyType").value = data.type;
	   			document.getElementById("modifyType").data('combobox').refresh()
	   			
	   		}
	   			
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    }   			
	});
		
}

function modifyProduct(){
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/modifyProdDetails",
	   	type : "Post",
	   	data : JSON.stringify({
	   		"id_res": sessionStorage.restaurantId,
	   		"id_product": productId,
	   		"name" : $("#modifyName").val(),
			"description" : $("#modifyDescription").val(),
			"price" : $("#modifyPrice").val(),
			"type": $("#modifyType").val()
	   		
		}),
	   	contentType : 'application/json',
		dataType : "text",
		success : function(data) {
	   		if(data!="")
	   			$('#modifyProdError').html(data);
	   		else{
	   			bootbox.alert("Product details has successfully modified");
	   			$('#modifyProd').modal('toggle');
	   		}
	   			
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	
	    	alert("Ajax error");
	    }
	});
	
}

function addProduct() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/addProduct",
	   	type : "Post",
	   	data : JSON.stringify({
			"type" : $( "#productType option:selected" ).text(),
			"name" : $("#productName").val(),
			"description" : $("#productDescription").val(),
			"price" : $("#productPrice").val(),
			"id_res": sessionStorage.restaurantId
		}),
	   	contentType : 'application/json',
		dataType : 'text',
	   	success : function(data) {
	   		if(data!="")
	   			$('#productError').html(data);
	   		else{
	   			
	   			bootbox.alert("Product has successfully added");
	   			$('#addProductModal').modal('toggle');
	   		}
	   			
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    },
	   	
	});	
};

function draggableEvents(){
	/* initialize the external events
	-----------------------------------------------------------------*/

	$('#external-events .fc-event').each(function() {

		// store data so the calendar knows to render an event upon drop
		$(this).data('event', {
			title: $.trim($(this).text()), // use the element's text as the event title
			stick: true // maintain when user navigates (see docs on the renderEvent method)
		});

		// make the event draggable using jQuery UI
		$(this).draggable({
			zIndex: 999,
			revert: true,      // will cause the event to go back to its
			revertDuration: 0  //  original position after the drag
		});

	});
}

function addShift() {
	$('#external-events')
			   		.append("<div class='fc-event'>"+st.value+"-"+en.value+"</div>");
	draggableEvents();
}
