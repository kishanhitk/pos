function getOrderUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/orders";
}
function getInvoiceUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/invoice";
}

function convertFormToOrderItems(data) {
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
function updateOrder(event) {
  event.preventDefault();

  //Get the ID
  var id = $("#edit-order-form input[name=id]").val();
  var url = getOrderUrl() + "/" + id;
  //Set the values to update
  var $form = $("#edit-order-form");
  var data = convertFormToOrderItems($form.serializeArray());

  $.ajax({
    url: url,
    type: "PUT",
    data: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      $.notify("Order updated successfully", "success");
      $("#edit-order-modal").modal("toggle");
    },
    error: handleAjaxError,
  });

  return false;
}

function getOrderList() {
  var url = getOrderUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayOrderList(data);
    },
    error: handleAjaxError,
  });
}

//UI DISPLAY METHODS

function displayOrderList(data) {
  var $tbody = $("#order-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var orderDateStr = convertTimeStampToDateTime(e.createdAt);
    var buttonHtml =
      ' <button class="btn btn-outline-primary" onclick="displayOrderDetails(' +
      e.id +
      ')">Details</button>';
    var row =
      "<tr>" +
      "<td>" +
      e.id +
      "</td>" +
      "<td>" +
      orderDateStr +
      "</td>" +
      "<td>" +
      buttonHtml +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}

function displayEditOrderForm(id) {
  var url = getOrderUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      populateEditOrderModalForm(data);
    },
    error: handleAjaxError,
  });
}

function populateEditOrderModalForm(data) {
  const orderItems = data.orderItems;
  const orderDateStr = convertTimeStampToDateTime(data.createdAt);
  const orderId = data.id;
  const $form = $("#edit-order-form-div");
  $("#edit-order-form input[name=id]").val(orderId);
  $("#edit-order-form").find("#order-id").text(orderId);
  $("#edit-order-form").find("#order-date").text(orderDateStr);
  $form.empty();
  for (let i = 0; i < orderItems.length; i++) {
    const orderItem = orderItems[i];
    const rowId = orderItem.id;
    const row =
      '<div id="row-' +
      rowId +
      '" class="row">' +
      '<div class="col-4">' +
      `<label for="barcode-${rowId}">Barcode</label>` +
      `<input type="text" class="form-control" id="barcode-${rowId}" name="barcode" value="` +
      orderItem.barcode +
      '" readonly>' +
      "</div>" +
      '<div class="col-3">' +
      `<label for="quantity-${rowId}">Quantity</label>` +
      `<input type="number" class="form-control" id="quantity-${rowId}" name="quantity" value="` +
      orderItem.quantity +
      '">' +
      "</div>" +
      '<div class="col-3">' +
      `<label for="sellingPrice-${rowId}">Price</label>` +
      `<input type="number" step="0.01" class="form-control" id="sellingPrice-${rowId}" name="sellingPrice" value="` +
      orderItem.sellingPrice +
      '" readonly>' +
      "</div>" +
      '<div class="col-2 mt-4">' +
      '<button class="btn btn-danger delete-row" onclick="deleteRow(' +
      orderItem.id +
      ')" type="button" id="delete' +
      rowId +
      '">X</button>' +
      "</div>" +
      "</div>";
    $form.append(row);
  }
  $("#edit-order-modal").modal("toggle");
}

function deleteRow(id) {
  $("#row-" + id).remove();
}

function displayOrderDetails(id) {
  var url = getOrderUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayOrder(data);
    },
    error: handleAjaxError,
  });
}

function displayOrder(data) {
  const orderItems = data.orderItems;
  const orderDateStr = convertTimeStampToDateTime(data.createdAt);
  const orderId = data.id;
  $("#order-id").text(orderId);
  $("#order-date").text(orderDateStr);
  //Display list of order items
  const $orderItemsTable = $("#order-items-table");
  $orderItemsTable.find("tbody").empty();
  for (let i = 0; i < orderItems.length; i++) {
    const orderItem = orderItems[i];
    const productId = orderItem.productId;
    const quantity = orderItem.quantity;
    const sellingPrice = orderItem.sellingPrice;
    const totalPrice = quantity * sellingPrice;
    const totalPriceStr = totalPrice.toFixed(2);
    const row =
      "<tr>" +
      "<td>" +
      productId +
      "</td>" +
      "<td>" +
      quantity +
      "</td>" +
      "<td>" +
      sellingPrice +
      "</td>" +
      "<td>" +
      totalPriceStr +
      "</td>" +
      "</tr>";
    $orderItemsTable.find("tbody").append(row);
  }
  $("#order-details-modal").modal("toggle");
}
function addRow() {
  // grab form
  const $form = $("#edit-order-form-div");
  const rowId = Math.floor(Math.random() * 10000000);
  const row =
    '<div id="row-' +
    rowId +
    '" class="row">' +
    '<div class="col-4">' +
    `<label for="barcode-${rowId}">Barcode</label>` +
    `<input type="text" class="form-control" id="barcode-${rowId}" required name="barcode" >` +
    "</div>" +
    '<div class="col-3">' +
    `<label for="quantity-${rowId}">Quantity</label>` +
    `<input type="number" class="form-control" id="quantity-${rowId}" required name="quantity">` +
    "</div>" +
    '<div class="col-3">' +
    `<label for="sellingPrice-${rowId}">Price</label>` +
    `<input type="number" step="0.01" class="form-control" id="sellingPrice-${rowId}" required name="sellingPrice" >` +
    "</div>" +
    '<div class="col-2 mt-4">' +
    '<button class="btn btn-danger delete-row" onclick="deleteRow(' +
    rowId +
    ')" type="button" id="delete' +
    rowId +
    '">X</button>' +
    "</div>" +
    "</div>";
  $form.append(row);
}

function downloadInvoice() {
  const orderId = $("#order-id").text();
  const url = getInvoiceUrl() + "/" + orderId;
  window.open(url, "_blank");
}

//INITIALIZATION CODE
function init() {
  $("#update-order").click(updateOrder);
  $("#refresh-data").click(getOrderList);
  $("#edit-order-form").submit(updateOrder);
  $("#add-row").click(addRow);
  $("#download-invoice").click(downloadInvoice);
}

$(document).ready(init);
$(document).ready(getOrderList);
