function getOrderUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/orders";
}

function updateOrder(event) {
  $("#edit-order-modal").modal("toggle");
  //Get the ID
  var id = $("#order-edit-form input[name=id]").val();
  var url = getOrderUrl() + "/" + id;

  //Set the values to update
  var $form = $("#order-edit-form");
  var json = toJson($form);

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
      ')">Details</button> ';
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

  $("#edit-order-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
  $("#update-order").click(updateOrder);
  $("#refresh-data").click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);
