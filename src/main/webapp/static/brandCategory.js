function getInventoryReportUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/brandcategories";
}

//BUTTON ACTIONS
function addBrandCategory(event) {
  //Set the values to update
  var $form = $("#brandCategory-form");
  var json = toJson($form);
  var url = getInventoryReportUrl();

  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getInventoryReport();
    },
    error: (resp) => {
      console.log(resp);
      handleAjaxError(resp);
    },
  });

  return false;
}

function updateBrandCategory(event) {
  $("#edit-brandCategory-modal").modal("toggle");
  //Get the ID
  var id = $("#brandCategory-edit-form input[name=id]").val();
  var url = getInventoryReportUrl() + "/" + id;

  //Set the values to update
  var $form = $("#brandCategory-edit-form");
  var json = toJson($form);

  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getInventoryReport();
    },
    error: (response) => {
      console.log(response);
      handleAjaxError(response);
    },
  });

  return false;
}

function getInventoryReport() {
  var url = getInventoryReportUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayInventoryReportList(data);
    },
    error: handleAjaxError,
  });
}

function deleteBrandCategory(id) {
  var url = getInventoryReportUrl() + "/" + id;

  $.ajax({
    url: url,
    type: "DELETE",
    success: function (data) {
      getInventoryReport();
    },
    error: handleAjaxError,
  });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
  var file = $("#brandCategoryFile")[0].files[0];
  readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results) {
  fileData = results.data;
  uploadRows();
}

function uploadRows() {
  //Update progress
  updateUploadDialog();
  //If everything processed then return
  if (processCount == fileData.length) {
    return;
  }

  //Process next row
  var row = fileData[processCount];
  processCount++;

  var json = JSON.stringify(row);
  var url = getInventoryReportUrl();

  //Make ajax call
  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      uploadRows();
    },
    error: function (response) {
      row.error = response.responseText;
      errorData.push(row);
      uploadRows();
    },
  });
}

function downloadErrors() {
  writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayInventoryReportList(data) {
  var $tbody = $("#brandCategory-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      ' <button onclick="displayEditBrandCategory(' + e.id + ')">edit</button>';
    var row =
      "<tr>" +
      "<td>" +
      e.id +
      "</td>" +
      "<td>" +
      e.brand +
      "</td>" +
      "<td>" +
      e.category +
      "</td>" +
      "<td>" +
      buttonHtml +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}

function displayEditBrandCategory(id) {
  var url = getInventoryReportUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayBrandCategory(data);
    },
    error: handleAjaxError,
  });
}

function resetUploadDialog() {
  //Reset file name
  var $file = $("#brandCategoryFile");
  $file.val("");
  $("#brandCategoryFileName").html("Choose File");
  //Reset various counts
  processCount = 0;
  fileData = [];
  errorData = [];
  //Update counts
  updateUploadDialog();
}

function updateUploadDialog() {
  $("#rowCount").html("" + fileData.length);
  $("#processCount").html("" + processCount);
  $("#errorCount").html("" + errorData.length);
}

function updateFileName() {
  var $file = $("#brandCategoryFile");
  var fileName = $file.val();
  $("#brandCategoryFileName").html(fileName);
}

function displayUploadData() {
  resetUploadDialog();
  $("#upload-brandCategory-modal").modal("toggle");
}

function displayBrandCategory(data) {
  $("#brandCategory-edit-form input[name=brand]").val(data.brand);
  $("#brandCategory-edit-form input[name=category]").val(data.category);
  $("#brandCategory-edit-form input[name=id]").val(data.id);
  $("#edit-brandCategory-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
  $("#add-brandCategory").click(addBrandCategory);
  $("#update-brandCategory").click(updateBrandCategory);
  $("#refresh-data").click(getInventoryReport);
  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
  $("#download-errors").click(downloadErrors);
  $("#brandCategoryFile").on("change", updateFileName);
}

$(document).ready(init);
$(document).ready(getInventoryReport);
