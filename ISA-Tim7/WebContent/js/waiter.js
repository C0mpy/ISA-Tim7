$(document).ready(function() {
	
	$("#name").text(sessionStorage.firstName + " " + sessionStorage.lastName);
	$("#email").text(sessionStorage.email);
	$("#birth").text(sessionStorage.dateOfBirth);
	$("#calendar").fullCalendar({
	});

	initiateNotificationCheck();
	
	$("#shifts").on('shown.bs.modal', function () {
        $("#calendar").fullCalendar('render');
	});
	
	$("#editProfile").on('show.bs.modal', function () {
		loadEmployeeData();
	})
	
	$("#placeOrder").on('shown.bs.modal', function () {
        loadProductTable();
	});
	
	fillEvents();
	loadCanvas();
	loadProducts()
	$("#findProduct").on("input", function(e) {
	     findProducts();
	});
	
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
	   		
	   		var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	   		
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
	
	canvas.on('object:selected', function(e){
		activeObject = e.target;
		getOrderForTable();
		e.target.lockMovementX = true;
		e.target.lockMovementY = true;
		e.target.hasControls = false;
		e.target.hasRotatingPoint = false;
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

function addVisit() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/visit/writeAnon",
	   	type : "Post",
	   	data : JSON.stringify({
			"guestEmail" : "",
			"tableId" : activeObject.get('id'),
			"restId" : sessionStorage.restaurantId,
			"shiftId" : sessionStorage.shiftId,
			"employeeId" : sessionStorage.email
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		sessionStorage.orderId = data;
	   		getOrderForTable();
	   	}			
	});
}

function getOrderForTable() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/order/getOrderIdForTable",
	   	type : "Post",
	   	data : JSON.stringify({
			"table_id": activeObject.get('id'),
			"rest_id": sessionStorage.restaurantId
		}),
	   	contentType : 'application/json',
		dataType : 'json',
	   	success : function(data) {
	   		sessionStorage.orderId = data;
	   	}
	   	
	});	
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/order/getOrderForTable",
	   	type : "Post",
	   	data : JSON.stringify({
			"table_id": activeObject.get('id'),
			"rest_id": sessionStorage.restaurantId,
			"order_id": sessionStorage.orderId
		}),
	   	contentType : 'application/json',
		dataType : 'json',
	   	success : function(data) {
	   		
	   		$("#order").html('<li class="list-group-item list-group-item-warning"><h3>Orders:</h3></li>');
	   		
	   		if(data == null) {
	   			$("#order").append('<li class="list-group-item list-group-item-warning"><button style="display: block; width: 100%;" type="button" class="btn btn-primary" onclick="addVisit()"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;Add a Visit</button></li>');
	   		}
	   		else {
	   			
		   		var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			   	$.each(list, function(index, obj) {
			   		$.each(lista, function(ind, ob) {
			   			if(obj.productId == lista[ind].product.id) {
			   				
			   				$("#order")
					   	    .append($('<li>')
					   	    	.attr('id', 'li' + obj.id)
					   	    	.on("click", function(e) {
					   	        	selectedOrder = obj.id;
					   	        })
					   	        .append($('<a>')
					   	        	.attr('href', '#')
					   	        	.text(lista[ind].product.name + " : " + lista[ind].product.price + " " )
					   	            .append($('<span>')
					   	        		.addClass("glyphicon")
					   	        		.addClass("glyphicon-euro")
					   	            )
					   	        )

					   	    	.addClass("list-group-item")
					   	    	.addClass("list-group-item-warning")
					 
					   	    )
					   	    
					   	    /* prikaz bojom stanja naruzbine
					   	    if(obj.state == 'OPEN') {
			   	        		console.log('is open')
			   	        		$('#li'+obj.id).css({'background-color' : '#F9EDB1'})
			   	        	}
			   	        	else if(obj.state == 'ACCEPTED') {
			   	        		$('#li'+obj.id).css({'background-color' : '#C6F9B3'})
			   	        	}
			   	        	else if(obj.state == 'FINISHED') {
			   	        		$('#li'+obj.id).css({'background-color' : '#F7B2B2'})
			   	        	}
			   	        	*/
			   				
			   			}
			   		});
			   		
			   	});
			   	
			   	$("#order").append('<li class="list-group-item list-group-item-warning"><button style="display: block; width: 100%;" type="button" class="btn btn-primary" data-toggle="modal" data-target="#placeOrder"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;Place an Order</button></li>');
			   	$("#order").append($('<li>')
			   	    .addClass("list-group-item")
			   	    .addClass("list-group-item-warning")
			   		.append($('<button>')
		   	        	.addClass("btn")
		   	        	.addClass("btn-danger")
		   	        	.text("Remove")
		   	        	.on("click", function() {
		   	        		
		   	        		removeOrder();
		   	        		
		   	        	})
		   	        	.css({'width' : '100%'})
	   	        	)
		   	    )
		   	    $("#order").append($('<li>')
			   	    .addClass("list-group-item")
			   	    .addClass("list-group-item-warning")
			   		.append($('<button>')
		   	        	.addClass("btn")
		   	        	.addClass("btn-success")
		   	        	.text("Close Tab")
		   	        	.on("click", function() {
		   	        		
		   	        		closeTab();
		   	        		
		   	        	})
		   	        	.css({'width' : '100%'})
	   	        	)
		   	    )
		   	    
	   		}
	   		
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    },
	   	
	});	
}

function loadProducts() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/getProducts",
	   	type : "Post",
	   	data : JSON.stringify({
			"id_res" : sessionStorage.restaurantId
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		
	   		var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	   		lista = []
	   		
	   		if (list.length !=0) {
	   			
			   	$.each(list, function(index, obj) {	
			   		lista[index] = {selected:false, product:obj, ammount:0}
			   	});
		   
		   	}
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }
	    			
	});
}

function loadProductTable() {
	
	loadProducts();
	$("#productsList tbody").html('');
	
	$.each(lista, function(index, obj) {	   		
		$("#productsList tbody")
		    .append($('<tr>')
		    	.append($('<td>')
		    		.append($('<input>')
	   				.attr('type', 'checkbox')
		    			.attr('index', index)
		    			.attr('id', 'cb' + index)
		    			.change(function() {
		    				if(lista[index].selected == true) {
		    					lista[index].selected = false;
		    				}
		    				else {
		    					lista[index].selected = true;
		    				}
		    			})
		    		)
		    	)
		    	.append($('<td>')
		    		.append($('<p>')
		    			.text(lista[index].product.name)
		    			.attr('index', index)
		    		)
		    	)
		    	.append($('<td>')
		    		.append($('<p>')
		    			.text(lista[index].product.price)
		    			.attr('index', index)
		    		)
		    	)
		    	.append($('<td>')
		    		.append($('<input>')
			   	    	.attr('type', 'number')
			   	    	.attr('index', index)
			   	    	.attr('value', 0)
			   	    	.attr('min', 0)
			   	    	.change(function(e) {
			   	    		console.log($(this).val());
			    				lista[index].ammount = $(this).val();
			    		})
		    		)
		    	)
		    );
	});
}

function findProducts() {
	
	$("#productsList tbody").html('');
	
	$.each(lista, function(index, obj) {
		console.log($("#findProduct").val());
		if ((obj.product.name).match($("#findProduct").val())) {
			
			$("#productsList tbody")
	   	    .append($('<tr>')
	   	    	.append($('<td>')
	   	    		.append($('<input>')
	   	    			.attr('type', 'checkbox')
	   	    			.attr('index', index)
	   	    			.attr('id', 'cb' + index)
	   	    			.change(function() {
	   	    				if(obj.selected == true) {
	   	    					obj.selected = false;
	   	    				}
	   	    				else {
	   	    					obj.selected = true;
	   	    				}
	   	    			})
	   	    		)
	   	    	)
	   	    	.append($('<td>')
	   	    		.append($('<p>')
	   	    			.text(obj.product.name)
	   	    			.attr('index', index)
	   	    		)
	   	    	)
	   	    	.append($('<td>')
	   	    		.append($('<p>')
	   	    			.text(obj.product.price)
	   	    			.attr('index', index)
	   	    		)
	   	    	)
	   	    	.append($('<td>')
	   	    		.append($('<input>')
   	    				.attr('type', 'number')
			   	    	.attr('index', index)
			   	    	.attr('value', lista[index].ammount)
			   	    	.attr('min', 0)
			   	    	.change(function(e) {
			   	    		console.log($(this).val());
	   	    				lista[index].ammount = $(this).val();
	   	    			
	   	    			})
			   	    )
	   	    	)
	   	    )
	   	    
	   	    if(obj.selected == true) {
	   	    	$("#cb" + index).prop('checked', true);
	   	    }
			$("#am" + index).val(obj.ammount);
		}
	});
}

function placeOrder() {
	
	$.each(lista, function(index, obj) {
		
		if(obj.selected == true) {
			for(var i = 0; i < obj.ammount; i++) {
				
				$.ajax ({
				   	url : "../ISA-Tim7/rest/order/addProductForOrder",
				   	type : "Post",
				   	data : JSON.stringify({
				   		"product_id": obj.product.id,
						"rest_id": sessionStorage.restaurantId,
						"order_id": sessionStorage.orderId
					}),
				   	contentType : 'application/json',
					dataType : 'json',
				   	success : function(data) {
				   		
				   		getOrderForTable();	
				   		
				   	},
				    error : function(XMLHttpRequest, textStatus, errorThrown) {
				    	alert("Ajax error");
				    },
				   	
				});	

			}
		}
		
	});
	$("#placeOrder").modal('hide');
	getOrderForTable();
}

function removeOrder() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/order/removeOrder",
	   	type : "Post",
	   	data : JSON.stringify({
			"order_id": selectedOrder
		}),
	   	contentType : 'application/json',
		dataType : 'json',
	   	success : function(data) {
	   		getOrderForTable();	
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    },
	   	
	});	
}

function closeTab() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/order/closeTab",
	   	type : "Post",
	   	data : JSON.stringify({
			"order_id" : sessionStorage.orderId
		}),
	   	contentType : 'application/json',
		dataType : 'json',
	   	success : function(data) {
	   		getOrderForTable();	
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    },
	   	
	});
}

function initiateNotificationCheck() {
	
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/user/waiterNotificationRead",
	   	type : "Post",
	   	data : JSON.stringify({
			"email" : sessionStorage.email
		}),
	   	contentType : 'application/json',
		dataType : 'json',
	   	success : function(data) {   		
	   		
	   		$("#notificationTable")
	   	    .append($('<tr>')
	   	    	.append($('<td>')
	   	    		.append($('<p>')
	   	    			.text(data.from)
	   	    		)
	   	    	)
	   	    	.append($('<td>')
	   	    		.append($('<p>')
	   	    			.text(data.text)
	   	    		)
	   	    	)
	   	    )
	   	    
	   	    
	   	initiateNotificationCheck();
	   	    	
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    },
	   	
	});
}