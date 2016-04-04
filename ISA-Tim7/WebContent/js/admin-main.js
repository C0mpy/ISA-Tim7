$( document ).ready(function() {
    
	loadCities();
});

function loadCities() {
	
	$('#resCity').empty();
	$.ajax ({
	   	url : "../ISA-Tim7/rest/city/getAll",
	   	type : "Post",
	   	data : JSON.stringify({
			"bez" : "parametara"
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		
	   		list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	   		
	   		if (list.length !=0) {
	   			
			   	$.each(list, function(index, obj) {	   		
			   			
			   		$('#resCity')
			   		.append($("<option></option>")
			   		.attr("value", index)
			   		.text(obj.zip + " " + obj.name)); 
		   		});
		   
		   	}
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }
	    			
	});
	
};

function addCity() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/city/add",
	   	type : "Post",
	   	data : JSON.stringify({
	   		"zip" : $("#cityZip").val(),
			"name" : $("#cityName").val()
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		loadCities();
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }
	    			
	});
};

function addRestaurant() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/add",
	   	type : "Post",
	   	data : JSON.stringify({
			"name" : $("#resName").val(),
			"description" : $("#resDesc").val(),
			"address" : $("#resAddress").val(),
			"city" : $( "#resCity option:selected" ).text()
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

function loadRestaurants() {
	
	$('#manRest').empty();
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/getAll",
	   	type : "Post",
	   	data : JSON.stringify({
			"bez" : "parametara"
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		
	   		list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	   		
	   		if (list.length !=0) {
	   			
			   	$.each(list, function(index, obj) {	   		
			   			
			   		$('#resCity')
			   		.append($("<option></option>")
			   		.attr("value", index)
			   		.text(obj.zip + " " + obj.name)); 
		   		});
		   
		   	}
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }
	    			
	});
	
};