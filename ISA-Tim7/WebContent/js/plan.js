var canvas
	(function() {
		  canvas = new fabric.Canvas('c');
		  
		 
		  fabric.Object.prototype.transparentCorners = true;

		  var radius = 300;
		  
		})();
	
	function addCorner(){
		 fabric.Image.fromURL('http://localhost:8081/ISA-Tim7/image/corner2.png', function(img) {
			    img.scale(0.5).set({
			      left: 0,
			      top: 0,
			      cornerSize:6
			      
			    });
			    canvas.add(img).setActiveObject(img);
			  });
	}
	
	function addWall(){
		fabric.Image.fromURL('http://localhost:8081/GoJs/image/wall2.png', function(img) {
		    img.scale(0.5).set({
		      left: 0,
		      top: 0,
		      cornerSize:4    
		    });
		    canvas.add(img).setActiveObject(img);
		  });
		
	}
	function addTable(){
		  fabric.Image.fromURL('http://localhost:8081/GoJs/image/table22.png', function(img) {
			    img.scale(0.5).set({
			      left: 0,
			      top: 0,
			      cornerSize:6
			    });
			    canvas.add(img).setActiveObject(img);
			  });

	  }
	function addTable3(){
		  fabric.Image.fromURL('http://localhost:8081/GoJs/image/table32.png', function(img) {
			    img.scale(0.5).set({
			      left: 0,
			      top: 0,
			      cornerSize:6
			    });
			    canvas.add(img).setActiveObject(img);
			  });

	  }
	
	function addTable22(){
		  fabric.Image.fromURL('http://localhost:8081/GoJs/image/tab22.png', function(img) {
			    img.scale(0.5).set({
			      left: 0,
			      top: 0,
			      cornerSize:6
			    });
			    canvas.add(img).setActiveObject(img);
			  });

	  }
	
	function addRightDoor(){
		fabric.Image.fromURL('http://localhost:8081/GoJs/image/right_door.png', function(img) {
		    img.scale(0.8).set({
		      left: 0,
		      top: 0,
		      cornerSize:6
		    });
		    canvas.add(img).setActiveObject(img);
		  });
	}
	
	function addLeftDoor(){
		fabric.Image.fromURL('http://localhost:8081/GoJs/image/left_door.png', function(img) {
		    img.scale(0.8).set({
		      left: 0,
		      top: 0,
		      cornerSize:6
		    });
		    canvas.add(img).setActiveObject(img);
		  });
	}
	
	function addToilet(){
		fabric.Image.fromURL('http://localhost:8081/GoJs/image/wc1.png', function(img) {
		    img.scale(0.8).set({
		      left: 0,
		      top: 0,
		      cornerSize:6
		    });
		    canvas.add(img).setActiveObject(img);
		  });
	}
	
	function addToiletSink(){
		fabric.Image.fromURL('http://localhost:8081/GoJs/image/wc2.png', function(img) {
		    img.scale(0.8).set({
		      left: 0,
		      top: 0,
		      cornerSize:6
		    });
		    canvas.add(img).setActiveObject(img);
		  });
	}
	
	function addToiletLogo(){
		fabric.Image.fromURL('http://localhost:8081/GoJs/image/wc3.png', function(img) {
		    img.scale(0.8).set({
		      left: 0,
		      top: 0,
		      cornerSize:6
		    });
		    canvas.add(img).setActiveObject(img);
		  });
	}
	
	function savePlan(){
		
		var json = canvas.toJSON();
		
		json = JSON.stringify(json);
		
		window.alert(json)
	}
	
	function loadPlan(){
		
		canvas.clear();
		canvas.loadFromJSON(str);
	}