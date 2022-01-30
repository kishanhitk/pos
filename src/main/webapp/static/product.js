function getProductUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/products";
}

function getBrandCategoryUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/brandcategories";
}
//BUTTON ACTIONS
function addProduct(event) {
  event.preventDefault();
  //Set the values to update
  var $form = $("#product-form");
  var json = toJson($form);
  var url = getProductUrl();

  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      $form.trigger("reset");
      $.notify("Product Added", "success");
      getProductList();
    },
    error: handleAjaxError,
  });

  return false;
}

function updateProduct(event) {
  $("#edit-product-modal").modal("toggle");
  //Get the ID
  var id = $("#product-edit-form input[name=id]").val();
  var url = getProductUrl() + "/" + id;

  //Set the values to update
  var $form = $("#product-edit-form");
  var json = toJson($form);

  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      $.notify("Product Details Updated", "success");
      getProductList();
    },
    error: handleAjaxError,
  });

  return false;
}

function getProductList() {
  var url = getProductUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayProductList(data);
    },
    error: handleAjaxError,
  });
}

function deleteProduct(id) {
  var url = getProductUrl() + "/" + id;

  $.ajax({
    url: url,
    type: "DELETE",
    success: function (data) {
      getProductList();
    },
    error: handleAjaxError,
  });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
  var file = $("#productFile")[0].files[0];
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
    $.notify("Upload Complete", "info");
    getProductList();
    return;
  }

  //Process next row
  var row = fileData[processCount];
  processCount++;

  var json = JSON.stringify(row);

  var url = getProductUrl();

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

function displayProductList(data) {
  var $tbody = $("#product-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      ' <button type="button" class="btn btn-outline-primary" onclick="displayEditProduct(' +
      e.id +
      ')">Edit</button>';
    var row =
      "<tr>" +
      "<td>" +
      e.name +
      "</td>" +
      "<td>" +
      e.brand +
      "</td>" +
      "<td>" +
      e.category +
      "</td>" +
      "<td>" +
      e.mrp +
      "</td>" +
      "<td>" +
      e.barcode +
      "</td>" +
      "<td>" +
      buttonHtml +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}

function displayEditProduct(id) {
  var url = getProductUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayProduct(data);
    },
    error: handleAjaxError,
  });
}

function resetUploadDialog() {
  //Reset file name
  var $file = $("#productFile");
  $file.val("");
  $("#productFileName").html("Choose File");
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
  var $file = $("#productFile");
  var fileName = $file.val();
  $("#productFileName").html(fileName);
}

function displayUploadData() {
  resetUploadDialog();
  $("#upload-product-modal").modal("toggle");
}

function displayProduct(data) {
  $("#product-edit-form input[name=name]").val(data.name);
  $("#product-edit-form input[name=mrp]").val(data.mrp);
  $("#product-edit-form input[name=id]").val(data.id);
  $("#product-edit-form select[name=brandCategory]")
    .val(data.brandCategory)
    .change();
  $("#edit-product-modal").modal("toggle");
}

function addDataToBrandCategoryDropdown(data, formId) {
  var $brand = $(`${formId} select[name=brandCategory]`);
  $brand.empty();
  for (var i in data) {
    var e = data[i];
    var option =
      '<option value="' +
      e.id +
      '">' +
      e.brand +
      "-" +
      e.category +
      "</option>";
    $brand.append(option);
  }
}

function populateBrandCategoryDropDown() {
  var url = getBrandCategoryUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      addDataToBrandCategoryDropdown(data, "#product-form");
      addDataToBrandCategoryDropdown(data, "#product-edit-form");
    },
    error: handleAjaxError,
  });
}

//INITIALIZATION CODE
function init() {
  $("#product-form").submit(addProduct);
  $("#product-edit-form").submit(updateProduct);
  $("#refresh-data").click(getProductList);
  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
  $("#download-errors").click(downloadErrors);
  $("#productFile").on("change", () => {
    updateFileName();
    resetVariablesCounts();
  });
}

$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(populateBrandCategoryDropDown);
