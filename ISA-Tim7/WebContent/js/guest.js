$(window).load(function(){
	if(sessionStorage.activated=="false"){
		$('#acc_code').modal('show');
	
	 }else{
		 alert("ok")
	 }
    });

function activate(){
	alert(sessionStorage.email);
	$.ajax({
		url:"../ISA-Tim7/rest/user/activate_acc",
		type:"post",
		data: JSON.stringify({
			"email" : sessionStorage.email,
			"token" : $("#activationcode").val()}),
		contentType:"application/json",
		dataType: "text",
		success:function(data){
			
			if(data=="false"){
				$("#acc_alert").show();
			}else{
				$('#acc_code').modal('hide');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERROR");
	    }

	});
	
}
function resend_email(){
	$.ajax({
		url:"../ISA-Tim7/rest/user/resend",
		type:"post",
		data:sessionStorage.email,
		contentType:"text/plain",
		success:function(data){
			//notifikacija
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERRORcina");	
		}
		
	});
	
}