
$("#ok").hide();
$("#notok").hide();
$("#pwdok").hide();
$("#pwdnotok").hide();


$(document).on('submit', '#register_form', function(e) {
	e.preventDefault();

	if($("#firstname").val()=="" || $("#firstname").val()==""
		|| $("#pwd2").val()=="" || $("#pwd").val()=="" || $("#email").val()==""){
		alert("popuni sva polja");
	}else{
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/user/addGuest",
	   	type : "Post",
	   	data : JSON.stringify({
			"email" : $("#email").val(),
			"pwd" : $("#pwd").val(),
			"fname"	: $("#firstname").val(),
			"lname"	: $("#lastname").val()
		}),
	   	contentType : 'application/json',
		dataType : "json",
	   	success : function(data) {
	   		alert("korisnik registrovan");
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERRORcina");
	    }
	    			
		});
	}
});

$("#email").keyup(function(){
	
	var email=$("#email").val();
	
	if(isValid(email)){
	
		$.ajax({
			url:"../ISA-Tim7/rest/user/check",
			type:"post",
			data: email,
			contentType:"text/plain",
			dataType: "text",
			success:function(data){
				
				if(data=="false"){
					$("#ok").show();
					$("#notok").hide();
					$("#regbtn").prop('disabled', false);
				}else{
					$("#notok").show();
					$("#ok").hide();
					$("#regbtn").prop('disabled', true);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("AJAX ERROR");
		    }
	
		});
	
	}else{
		$("#notok").show();
		$("#ok").hide();
		$("#regbtn").prop('disabled', true);
	}
		
});

$("#pwd2").keyup(function(){
	
	var pwd1=$("#pwd").val();
	var pwd2=$("#pwd2").val();
	if(pwd1!=pwd2){
		$("#pwdok").hide();
		$("#pwdnotok").show();
		$("#regbtn").prop('disabled', true);
	}else{
		$("#pwdok").show();
		$("#pwdnotok").hide();
		$("#regbtn").prop('disabled', false);
	}
});

$("#pwd").keyup(function(){
	
	var pwd1=$("#pwd").val();
	var pwd2=$("#pwd2").val();
	
		if(pwd2!=""){
		if(pwd1!=pwd2){
			$("#pwdok").hide();
			$("#regbtn").prop('disabled', true);
			$("#pwdnotok").show();
		}else{
			$("#pwdok").show();
			$("#regbtn").prop('disabled', false);
			$("#pwdnotok").hide();
		}
	}
});

function isValid(emailAddress) {
    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
    return pattern.test(emailAddress);
};