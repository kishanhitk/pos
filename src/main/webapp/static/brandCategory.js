function getBrandCategoryReportUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/brandcategories";
}

//BUTTON ACTIONS
function addBrandCategory(event) {
  //Set the values to update
  event.preventDefault();
  var $form = $("#brandCategory-form");
  var json = toJson($form);
  var url = getBrandCategoryReportUrl();

  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getBrandCategoryList();
      $form.trigger("reset");
      $.notify("Brand Category Added", "success");
    },
    error: (resp) => {
      handleAjaxError(resp);
    },
  });

  return false;
}

function updateBrandCategory(event) {
  event.preventDefault();
  $("#edit-brandCategory-modal").modal("toggle");
  //Get the ID
  var id = $("#brandCategory-edit-form input[name=id]").val();
  var url = getBrandCategoryReportUrl() + "/" + id;

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
      getBrandCategoryList();
      $.notify("Brand Category Updated", "success");
    },
    error: (response) => {
      handleAjaxError(response);
    },
  });

  return false;
}

function getBrandCategoryList() {
  var url = getBrandCategoryReportUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayBrandCategoryReportList(data);
    },
    error: handleAjaxError,
  });
}

function deleteBrandCategory(id) {
  var url = getBrandCategoryReportUrl() + "/" + id;

  $.ajax({
    url: url,
    type: "DELETE",
    success: function (data) {
      getBrandCategoryList();
      $.notify("Deleted", "success");
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
  //If everything processed then refresh the list
  if (processCount == fileData.length) {
    $.notify("Upload Complete", "info");
    getBrandCategoryList();
    return;
  }

  //Process next row
  var row = fileData[processCount];
  processCount++;

  var json = JSON.stringify(row);
  var url = getBrandCategoryReportUrl();

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

function displayBrandCategoryReportList(data) {
  var $tbody = $("#brandCategory-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      ' <button type="button" class="btn btn-outline-primary" onclick="displayEditBrandCategory(' +
      e.id +
      ')">Edit</button>';
    var row =
      "<tr>" +
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
  var url = getBrandCategoryReportUrl() + "/" + id;
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
  resetVariablesCounts();
}
function resetVariablesCounts() {
  processCount = 0;
  fileData = [];
  errorData = [];
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
  $("#brandCategory-form").submit(addBrandCategory);
  $("#brandCategory-edit-form").submit(updateBrandCategory);
  $("#refresh-data").click(getBrandCategoryList);
  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
  $("#download-errors").click(downloadErrors);
  $("#brandCategoryFile").on("change", () => {
    updateFileName();
    resetVariablesCounts();
  });
}

$(document).ready(init);
$(document).ready(getBrandCategoryList);
