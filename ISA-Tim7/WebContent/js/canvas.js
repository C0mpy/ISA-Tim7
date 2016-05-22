/**
 * JavaScript fajl koji omogucava menadžeru restorana pravljenje plana restorana
 * 
 * Autor: Miodrag Vilotijević
 * email: miodragvilotijevic@gmail.com
 */

$(document).on({
    ajaxStart: function() { $("body").addClass("loading");    },
     ajaxStop: function() { $("body").removeClass("loading"); }    
});

var canvas=null;
var br=0;
var plan_not_added = false;

function setCanvas(){
	if(canvas==null){
		canvas = new fabric.Canvas('c');
		
	}else{
		canvas.clear();
		countTables();
	}
	
	$.ajax ({
	   	url : "../ISA-Tim7/rest/restaurant/getPlan",
	   	type : "Post",
	   	data : JSON.stringify({
			"id_res": sessionStorage.restaurantId
		}),
	   	contentType : 'application/json',
		dataType : 'text',
	   	success : function(data) {
	   		if(data=="")
	   			plan_not_added=true;
	   		
	   		canvas.loadFromJSON(data,canvas.renderAll.bind(canvas));
	   		alert("Plan is loaded");
	   		countTables();
	   		
	   	},
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("Ajax error");
	    },
	   	
	});	
	
	fabric.Object.prototype.transparentCorners = true;
		
}

function countTables(){
	br=0;
	canvas.forEachObject(function(obj){
		if(obj.get('id')!=0){
			br++;
		}
			
	});
	
}

function savePlan(){
	var json = canvas.toJSON();
	json = JSON.stringify(json);
	
	if(plan_not_added){
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
		   		bootbox.alert("Restauran plan has successfully added");
		   		plan_not_added = false;
		   	},
		    error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("Ajax error");
		    },
		   	
		});	
		
	}else{
		$.ajax ({
		   	url : "../ISA-Tim7/rest/restaurant/modifyPlan",
		   	type : "Post",
		   	data : JSON.stringify({
				"id_res": sessionStorage.restaurantId,
				"plan": json
			}),
		   	contentType : 'application/json',
			dataType : 'text',
		   	success : function(data) {
		   		bootbox.alert("Restauran plan has successfully modified");
		   	},
		    error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("Ajax error");
		    },
		   	
		});	
	}
	
}

function loadPlan(){
	canvas.clear();
	canvas.loadFromJSON(str);
}

function deleteObjects(){
	var activeObject = canvas.getActiveObject(),
    activeGroup = canvas.getActiveGroup();
    if (activeObject) {
        if (confirm('Are you sure?')) {
            canvas.remove(activeObject);
        }
    }
    else if (activeGroup) {
        if (confirm('Are you sure?')) {
            var objectsInGroup = activeGroup.getObjects();
            canvas.discardActiveGroup();
            objectsInGroup.forEach(function(object) {
            canvas.remove(object);
            });
        }
    }
    
    br=0;
    canvas.forEachObject(function(obj){
		if(obj.get('id')!=0){
			++br;
			var ob = obj;
			ob['id']=br;
			ob.getObjects()[1].setText(br.toString());
			
			canvas.remove(obj);
			canvas.add(ob);
		}	
	});
    canvas.renderAll.bind(canvas);
}

function addTable(){
	++br;
	fabric.Image.fromURL('../ISA-Tim7/image/table1.png', function(img) {
	    img.scale(0.3).set({
	      left: 0,
	      top: 0,
	      cornerSize:6
	    });
	    
	    var text = new fabric.Text(br.toString(), {
            fontFamily: 'Arial',
            fontSize:12,
        });
        
        var group = new fabric.Group([ img,text ], { "id": br, left: 200, top: 200 });
        canvas.add(group).setActiveObject(group);
	});           
	
 }

function addTable2(){
	fabric.Image.fromURL('../ISA-Tim7/image/table2.png', function(img) {
	    img.scale(0.3).set({
	      id: ++br,
	      left: 0,
	      top: 0,
	      cornerSize:6
	    });
	    
	    var text = new fabric.Text(img.get('id').toString(), {
            fontFamily: 'Arial',
            fontSize:12,
        });
        
        var group = new fabric.Group([ img,text ], { left: 200, top: 200 });
        canvas.add(group).setActiveObject(group);
	});       

 }

function addWall(){
	fabric.Image.fromURL('../ISA-Tim7/image/wall1.png', function(img) {
	    img.scale(0.5).set({
	      left: 0,
	      top: 0,
	      cornerSize:3
	    });
	    
	    canvas.add(img).setActiveObject(img);
	});
	
}

function addWall2(){
	 fabric.Image.fromURL('../ISA-Tim7/image/wall2.png', function(img) {
		 img.scale(0.5).set({
			 left: 0,
		     top: 0,
		     cornerSize:4
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
