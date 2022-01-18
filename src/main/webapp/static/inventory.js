function getInventoryUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addInventory(event) {
  //Set the values to update
  var $form = $("#inventory-form");
  var json = toJson($form);
  var url = getInventoryUrl();

  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getInventoryList();
    },
    error: handleAjaxError,
  });

  return false;
}

function updateInventory(event) {
  $("#edit-inventory-modal").modal("toggle");
  //Get the ID
  var id = $("#inventory-edit-form input[name=id]").val();
  var url = getInventoryUrl() + "/" + id;

  //Set the values to update
  var $form = $("#inventory-edit-form");
  var json = toJson($form);
  console.log(json);
  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getInventoryList();
    },
    error: handleAjaxError,
  });

  return false;
}

function getInventoryList() {
  var url = getInventoryUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayInventoryList(data);
    },
    error: handleAjaxError,
  });
}

function deleteInventory(id) {
  var url = getInventoryUrl() + "/" + id;

  $.ajax({
    url: url,
    type: "DELETE",
    success: function (data) {
      getInventoryList();
    },
    error: handleAjaxError,
  });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
  var file = $("#inventoryFile")[0].files[0];
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
  console.log(json);
  var url = getInventoryUrl();
  url = url + "/" + row.id;
  //Make ajax call
  $.ajax({
    url: url,
    //!Using PUT because inventory data is already created during adding product
    type: "PUT",
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

function displayInventoryList(data) {
  var $tbody = $("#inventory-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      ' <button type="button" class="btn btn-outline-primary" onclick="displayEditInventory(' +
      e.id +
      ')">Edit</button>';
    var row =
      "<tr>" +
      "<td>" +
      e.id +
      "</td>" +
      "<td>" +
      e.quantity +
      "</td>" +
      "<td>" +
      buttonHtml +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}

function displayEditInventory(id) {
  var url = getInventoryUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayInventory(data);
    },
    error: handleAjaxError,
  });
}

function resetUploadDialog() {
  //Reset file name
  var $file = $("#inventoryFile");
  $file.val("");
  $("#inventoryFileName").html("Choose File");
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
  var $file = $("#inventoryFile");
  var fileName = $file.val();
  $("#inventoryFileName").html(fileName);
}

function displayUploadData() {
  resetUploadDialog();
  $("#upload-inventory-modal").modal("toggle");
}

function getBrandCategoryUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/brandcategories";
}

function displayInventory(data) {
  $("#inventory-edit-form input[name=quantity]").val(data.quantity);
  $("#inventory-edit-form input[name=id]").val(data.id);
  $("#edit-inventory-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
  $("#add-inventory").click(addInventory);
  $("#update-inventory").click(updateInventory);
  $("#refresh-data").click(getInventoryList);
  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
  $("#download-errors").click(downloadErrors);
  $("#inventoryFile").on("change", updateFileName);
}

$(document).ready(init);
$(document).ready(getInventoryList);
