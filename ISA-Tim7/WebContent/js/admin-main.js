$( document ).ready(function() {
    
	loadCities();
});

function loadCities() {
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/city/get",
	   	type : "Post",
	   	data : JSON.stringify({
			"bez" : "parametara"
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		
	   		alert("bkee");
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