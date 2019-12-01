
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/employee";
}

//BUTTON ACTIONS
function addEmployee(event){
	//Set the values to update
	var $form = $("#employee-form");
	var json = toJson($form);
	var url = getEmployeeUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getEmployeeList();  
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateEmployee(event){
	$('#edit-employee-modal').modal('toggle');
	//Get the ID
	var id = $("#employee-edit-form input[name=id]").val();	
	var url = getEmployeeUrl() + "/" + id;

	//Set the values to update
	var $form = $("#employee-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getEmployeeList();   
	   },
	   error: handleAjaxError
	});

	return false;
}


function getEmployeeList(){
	var url = getEmployeeUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayEmployeeList(data);  
	   },
	   error: handleAjaxError
	});
}

function deleteEmployee(id){
	var url = getEmployeeUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getEmployeeList();  
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	processCount = 0;
	fileData = [];
	errorData = [];
	
	var file = $('#employeeFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	$('#processCount').html("" + processCount);
	$('#rowCount').html("" + fileData.length);
	$('#errorCount').html("" + errorData.length);
	if(processCount==fileData.length){
		return;
	}
	
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getEmployeeUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		uploadRows();  
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayEmployeeList(data){
	console.log('Printing employee data');
	var $tbody = $('#employee-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteEmployee(' + e.id + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditEmployee(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>'  + e.age + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditEmployee(id){
	var url = getEmployeeUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayEmployee(data);   
	   },
	   error: handleAjaxError
	});	
}

function displayUploadData(){
	$('#upload-employee-modal').modal('toggle');
}

function displayEmployee(data){
	$("#employee-edit-form input[name=name]").val(data.name);	
	$("#employee-edit-form input[name=age]").val(data.age);	
	$("#employee-edit-form input[name=id]").val(data.id);	
	$('#edit-employee-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-employee').click(addEmployee);
	$('#update-employee').click(updateEmployee);
	$('#refresh-data').click(getEmployeeList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
}

$(document).ready(init);
$(document).ready(getEmployeeList);

