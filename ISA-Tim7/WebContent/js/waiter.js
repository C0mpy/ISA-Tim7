$(document).ready(function() {

	$("#calendar").fullCalendar({
	});

	$("#shifts").on('shown.bs.modal', function () {
        $("#calendar").fullCalendar('render');
	});
	fillEvents();
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