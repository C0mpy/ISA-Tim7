$(window).load(function(){
	if(sessionStorage.activated=="false"){
		$('#acc_code').modal('show');
	
	 }else{
		 $(".profile-usertitle-name").append(sessionStorage.firstName+" "+sessionStorage.lastName)
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

function changeLname(){
var lname=$("#lname").val();
	
	if(lname==""){
		alert("popuni polje")
	}else{
		$.ajax({
			url:"../ISA-Tim7/rest/user/changeLname",
			type:"post",
			data: JSON.stringify({
				"user":sessionStorage.email,
				"lname" : lname
			}),
			contentType:"application/json",
			dataType:"text",
			success:function(data){
				//notifikacija ili banana
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("AJAX ERRORcina");	
			}
		});
	}
}

function changeFname(){
	var fname=$("#fname").val();
	
	if(fname==""){
		alert("popuni polje")
	}else{
		$.ajax({
			url:"../ISA-Tim7/rest/user/changeFname",
			type:"post",
			data: JSON.stringify({
				"user":sessionStorage.email,
				"fname" : fname
			}),
			contentType:"application/json",
			dataType:"text",
			success:function(data){
				//notifikacija ili banana
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("AJAX ERRORcina");	
			}
		});
	}
}


function changePassword(){
	var oldpass=$("#oldpass").val();
	var newpass=$("#newpass").val();
	
	if(oldpass=="" || newpass==""){
		alert("popuni polja");
	}else{
	
		$.ajax({
			type:"post",
			url:"../ISA-Tim7/rest/user/changePassword",
			data: JSON.stringify({
				"user":sessionStorage.email,
				"oldp" : oldpass,
				"newp" : newpass
			}),
			contentType:"application/json",
			dataType:"text",
			success:function(data){
				alert(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("AJAX ERRORcina");	
			}
		});
	}
}


function search_restaurants(){
	
}

function search_people(){
	$.ajax({
		url: "../ISA-Tim7/rest/Users/search",
		//todo
		
		
		
	});
}