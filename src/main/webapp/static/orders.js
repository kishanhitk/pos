function getOrderUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/orders";
}

function updateOrder(event) {
  event.preventDefault();
  $("#edit-order-modal").modal("toggle");
  console.log("update order");
  //Get the ID
  var id = $("#edit-order-form input[name=id]").val();
  var url = getOrderUrl() + "/" + id;
  //Set the values to update
  var $form = $("#edit-order-form");
  var json = toJson($form);
  console.log(url);
  console.log(json);

  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getOrderList();
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
      ' <button class="btn btn-primary" onclick="displayOrderDetails(' +
      e.id +
      ')">Details</button> <button class="btn btn-outline-primary" onclick="displayEditOrderForm(' +
      e.id +
      ')">Edit</button> ';
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
  $("#edit-order-form input[name=id]").val(orderId);
  $("#order-id").text(orderId);
  $("#order-date").text(orderDateStr);
  const $form = $("#edit-order-form-div");
  $form.empty();
  for (let i = 0; i < orderItems.length; i++) {
    const orderItem = orderItems[i];
    const rowId = orderItem.id;
    const row =
      '<div id="row-' +
      rowId +
      '" class="row">' +
      '<div class="col-3">' +
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
      '<div class="col-3 mt-4">' +
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
      console.log(data);
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
    '<div class="col-3">' +
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
    '<div class="col-3 mt-4">' +
    '<button class="btn btn-danger delete-row" onclick="deleteRow(' +
    rowId +
    ')" type="button" id="delete' +
    rowId +
    '">X</button>' +
    "</div>" +
    "</div>";
  $form.append(row);
}

//INITIALIZATION CODE
function init() {
  $("#update-order").click(updateOrder);
  $("#refresh-data").click(getOrderList);
  $("#edit-order-form").submit(updateOrder);
  $("#add-row").click(addRow);
}

$(document).ready(init);
$(document).ready(getOrderList);
