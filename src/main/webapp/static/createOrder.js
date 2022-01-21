function getOrderUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/orders";
}
function getOrderListPageUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/ui/orders";
}
function addRow() {
  // grab form
  const id = Math.floor(Math.random() * 10000000);
  $("#order-form-div").append(`<div class="form-row" id="row-${id}">
  <div class="form-group col-4">
  <label for="inputBarcode${id}">Barcode</label>
  <input type="text" class="form-control" required id="inputBarcode${id}" name="barcode"
  placeholder="Enter Product Barcode">
  </div>
  <div class="form-group col-3">
  <label for="inputQuantity${id}">Barcode</label>
  <input type="number" class="form-control" required id="inputQuantity${id}" name="quantity" placeholder="Quantity">
  </div>
  <div class="form-group col-3">
  <label for="inputSellingPrice${id}">Barcode</label>
  <input type="number" step="0.01" class="form-control"  required id="inputSellingPrice${id}" name="sellingPrice"
  placeholder="Selling Price">
  </div>
  <button class="btn btn-danger delete-row col-1" type="button" id=${id}>X</button>
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
      $.notify("Order Placed Successfully", "success");
      window.location.href = getOrderListPageUrl();
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

function init() {
  $("#add-row").click(addRow);
  $("#order-form").submit(placeOrder);
  $(".delete-row").on("click", deleteRow);
}

$(document).ready(init);
