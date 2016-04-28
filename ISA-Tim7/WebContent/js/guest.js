$(window).load(function(){
	if(sessionStorage.userType!="GUEST"){
		window.location.href="index.html";
	}
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
	var query = $("#rsearch").val();
	query.toLowerCase();
	
	$.ajax({
		url: "../ISA-Tim7/rest/user/searchRestaurants",
		type : "post",
		data: JSON.stringify({
			//todo
			}),
		contentType : "application/json",
		dataType:"json",
		success:function(data){
			alert(JSON.stringify(data));
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERRORcina");	
		}
		
		
	});
}

function searchPeople(){
	var query = $("#psearch").val();
	query.toLowerCase();
	if(query.indexOf(";") > -1){
		alert("ne moz to");
	}else{
	
		$.ajax({
			url: "../ISA-Tim7/rest/user/searchPeople",
			type : "post",
			data: JSON.stringify({
				"name":query,
				"sender":sessionStorage.email
				}),
			contentType : "application/json",
			dataType:"json",
			success:function(data){
				alert(JSON.stringify(data));
				$("#psearchresult").empty();
				var table=$("<table></table>");
				
				$.each(data,function(index,obj){
					if(obj.email!=sessionStorage.email){
						var tr=$("<tr  ></tr>");
						
						tr.append("<td>"+obj.firstName+" "+obj.lastName+"</td>");
						tr.append("<td>"+obj.email+"</td>");
						if(obj.status1=="nista")
						tr.append("<td><input type=button value='ADD FRIEND' " +
								"onclick=\"addFriend('"+obj.email+"')\"></td>");
						if(obj.status1=="true")
							tr.append("<td><input  value='FRIEND'></td>");
						else
							tr.append("<td><input  value='PENDING'></td>");
						tr.append("<td><hr></td>");
						table.append(tr);
					}
				});
				$("#psearchresult").append(table);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("AJAX ERRORcina");	
			}
			
			
		});
	}
}

function addFriend(email){
	
	
	
	$.ajax({
		url: "../ISA-Tim7/rest/friends/addFriend",
		type : "post",
		data: JSON.stringify({
			 "friend1":sessionStorage.email,
			 "friend2":email
			}),
		contentType : "application/json",
		dataType:"text",
		success:function(data){
			alert(data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERRORcina");	
		}
	});
}

function getMyFriends(){
	
	
	$.ajax({
		url: "../ISA-Tim7/rest/friends/getMyFriends",
		type : "post",
		data: JSON.stringify({
			 "user":sessionStorage.email,
			 "order":$("input[name=optradio]:checked").val()
			}),
		contentType : "application/json",
		dataType:"json",
		success:function(data){
			
			$("#fsearchresult").empty();
			var table=$("<table></table>");
			
			$.each(data,function(index,obj){
				if(obj.email!=sessionStorage.email){
					var tr=$("<tr  ></tr>");
					
					tr.append("<td>"+obj.firstName+" "+obj.lastName+"</td>");
					tr.append("<td>"+obj.email+"</td>");
					if(obj.status){
					tr.append("<td><input type=button value='DELETE' onclick=\"deleteFriend('"+obj.email+"')\"></td>");
					}else{
						tr.append("<td><input value='pending'> </td>")
					}
					tr.append("<td><hr></td>");
					table.append(tr);
				}
			});
			$("#fsearchresult").append(table);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERRORcina");	
		}
	})
	
}

function deleteFriend(email){
	$.ajax({
		url: "../ISA-Tim7/rest/friends/deleteFriend",
		type : "post",
		data: JSON.stringify({
			 "user":sessionStorage.email,
			 "friend":email
			}),
		contentType : "application/json",
		dataType:"text",
		success:function(data){
			alert(data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("AJAX ERRORcina");	
		}
	})
	
}


function logout(){
	sessionStorage.clear()
	window.location.href="index.html";
}