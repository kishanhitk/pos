function getOrderUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/orders";
}
function getProductUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/products";
}

function getOrderListPageUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/ui/orders";
}
function addRow() {
  // grab form
  const id = Math.floor(Math.random() * 10000000);
  $("#order-form-div").append(`<div class="row" id="row-${id}">
  <div class="form-group col-5">
  <div class="input-group">
      <input type="text" class="form-control" id="inputBarcode${id}" required name="barcode"
          placeholder="Enter Product Barcode">
      <div class="input-group-append">
          <button class="btn btn-primary" type="button" id="fetch-details" onchange="resetProductDetails(${id})"
              onclick="fetchProductDetailsByBarcode(${id})">Fetch
              Details</button>
      </div>
  </div>
</div>
<div class="form-group col-2">
  <input type="text" class="form-control" id="inputName${id}" required name="name" disabled
      placeholder="Product Name">
</div>
<div class="form-group col-2">
  <input type="number" class="form-control" required id="inputQuantity${id}" name="quantity" placeholder="Quantity">
</div>
<div class="form-group col-2">
  <input type="number" step="0.01" class="form-control" required id="inputSellingPrice${id}" name="sellingPrice"
      placeholder="Selling Price">
</div>
<div class="form-group col-1">
  <button class="btn btn-danger delete-row" type="button" id=${id}>X</button>
</div>
</div>`);
  $(".delete-row").on("click", deleteRow);
}
function convertToOrderItems(data) {
  const output = [];
  const barcodes = [];
  const quantities = [];
  const sellingPrices = [];

  data.forEach((entry) => {
    if (entry.name === "barcode") {
      barcodes.push(entry.value);
    }
    if (entry.name === "quantity") {
      quantities.push(entry.value);
    }
    if (entry.name === "sellingPrice") {
      sellingPrices.push(entry.value);
    }
  });
  for (let i = 0; i < barcodes.length; i++) {
    output.push({
      barcode: barcodes[i],
      quantity: quantities[i],
      sellingPrice: sellingPrices[i],
    });
  }
  return output;
}

function placeOrder(e) {
  e.preventDefault();
  console.log("place order");
  var $form = $("#order-form");
  var data = convertToOrderItems($form.serializeArray());
  console.log(data);
  $.ajax({
    url: getOrderUrl(),
    type: "POST",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: function (data) {
      $("#success-modal").modal("show");
    },
    error: function (data) {
      handleAjaxError(data);
    },
  });
}

function deleteRow() {
  var id = $(this).attr("id");
  $("#row-" + id).remove();
}

function fetchProductDetailsByBarcode(rowId) {
  var row = $("#row-" + rowId);
  var barcode = row.find("#inputBarcode" + rowId).val();
  var url = getProductUrl() + "/barcode/" + barcode;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      if (data) {
        row.find("#inputSellingPrice" + rowId).val(data.mrp);
        row.find("#inputName" + rowId).val(data.name);
      }
    },
    error: handleAjaxError,
  });
}

function resetProductDetails(rowId) {
  var row = $("#row-" + rowId);
  row.find("#inputSellingPrice" + rowId).val("");
  row.find("#inputName" + rowId).val("");
}
function init() {
  $("#add-row").click(addRow);
  $("#order-form").submit(placeOrder);
  $(".delete-row").on("click", deleteRow);
}

$(document).ready(init);
