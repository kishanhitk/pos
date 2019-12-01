function getLoginUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/session/login";
}


//BUTTON ACTIONS
function login(event){
	//Set the values to update
	var $form = $("#login-form");
	var json = toJson($form);
	var url = getLoginUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		console.log("Login successful");	
	   		getEmployeeList();     //...
	   },
	   error: function(){
	   		alert("Invalid username or password");
	   }
	});

	return false;
}


