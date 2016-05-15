/**
 * 
 */


$.getScript("js/fabric.js", function(){

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
				   					"&nbsp;<button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteEmployee()\">Delete</button>" +
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
			   					"<td colspan=\"4\"><button type=\"button\" class=\"btn btn-primary\" onclick=\"modifyFood()\">Modify</button>" +
			   					"&nbsp;<button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteFood()\">Delete</button></td>" +
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
			   					"<td colspan=\"4\"><button type=\"button\" class=\"btn btn-primary\" onclick=\"modifyFood()\">Modify</button>" +
			   					"&nbsp;<button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteFood()\">Delete</button></td>" +
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


var canvas=null;

function addCorner(){
	 
	 fabric.Image.fromURL('../ISA-Tim7/image/corner2.png', function(img) {
		    img.scale(0.5).set({
		      left: 0,
		      top: 0,
		      cornerSize:6
		      
		    });
		    canvas.add(img).setActiveObject(img);
		  });
}

function addWall(){
	fabric.Image.fromURL('../ISA-Tim7/image/wall2.png', function(img) {
	    img.scale(0.5).set({
	      left: 0,
	      top: 0,
	      cornerSize:4    
	    });
	    canvas.add(img).setActiveObject(img);
	  });
	
}
function addTable(){
	  fabric.Image.fromURL('../ISA-Tim7/image/table22.png', function(img) {
		    img.scale(0.5).set({
		      left: 0,
		      top: 0,
		      cornerSize:6
		    });
		    canvas.add(img).setActiveObject(img);
		  });

  }
function addTable3(){
	  fabric.Image.fromURL('../ISA-Tim7/image/table32.png', function(img) {
		    img.scale(0.5).set({
		      left: 0,
		      top: 0,
		      cornerSize:6
		    });
		    canvas.add(img).setActiveObject(img);
		  });

  }

function addTable22(){
	  fabric.Image.fromURL('../ISA-Tim7/image/tab22.png', function(img) {
		    img.scale(0.5).set({
		      left: 0,
		      top: 0,
		      cornerSize:6
		    });
		    canvas.add(img).setActiveObject(img);
		  });

  }

function addRightDoor(){
	fabric.Image.fromURL('../ISA-Tim7/image/right_door.png', function(img) {
	    img.scale(0.8).set({
	      left: 0,
	      top: 0,
	      cornerSize:6
	    });
	    canvas.add(img).setActiveObject(img);
	  });
}

function addLeftDoor(){
	fabric.Image.fromURL('../ISA-Tim7/image/left_door.png', function(img) {
	    img.scale(0.8).set({
	      left: 0,
	      top: 0,
	      cornerSize:6
	    });
	    canvas.add(img).setActiveObject(img);
	  });
}

function addToilet(){
	fabric.Image.fromURL('../ISA-Tim7/image/wc1.png', function(img) {
	    img.scale(0.8).set({
	      left: 0,
	      top: 0,
	      cornerSize:6
	    });
	    canvas.add(img).setActiveObject(img);
	  });
}

function addToiletSink(){
	fabric.Image.fromURL('../ISA-Tim7/image/wc2.png', function(img) {
	    img.scale(0.8).set({
	      left: 0,
	      top: 0,
	      cornerSize:6
	    });
	    canvas.add(img).setActiveObject(img);
	  });
}

function addToiletLogo(){
	fabric.Image.fromURL('../ISA-Tim7/image/wc3.png', function(img) {
	    img.scale(0.8).set({
	      left: 0,
	      top: 0,
	      cornerSize:6
	    });
	    canvas.add(img).setActiveObject(img);
	  });
}

function setCanvas(){
	
	if(canvas==null){
		canvas = new fabric.Canvas('c');
	}else{
		canvas.clear();
	}
	
	fabric.Object.prototype.transparentCorners = true;
	
	
}
function savePlan(){
	
	var json = canvas.toJSON();
	json = JSON.stringify(json);
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/plan",
	   	type : "Post",
	   	data : JSON.stringify({
			"id_res": sessionStorage.restaurantId,
			"plan": json
		}),
	   	contentType : 'application/json',
		dataType : 'text',
	   	success : function(data) {
	   		bootbox.alert("Restauran plan has successfully modified");
	   		//$('#addEmplModal').modal('toggle');
	   		
	   			
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    },
	   	
	});	
	
}

function loadPlan(){
	
	canvas.clear();
	canvas.loadFromJSON(str);
}
