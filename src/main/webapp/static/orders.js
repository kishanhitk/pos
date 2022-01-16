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
    var orderDate = new Date(e.createdAt);
    // Convert the date to string and format it.
    var orderDateStr = orderDate.toLocaleDateString();
    var buttonHtml =
      ' <button class="btn btn-primary" onclick="displayEditOrder(' +
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

function displayEditOrder(id) {
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
  $("#order-edit-form input[name=name]").val(data.name);
  $("#order-edit-form input[name=mrp]").val(data.mrp);
  $("#order-edit-form input[name=id]").val(data.id);
  $("#edit-order-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
  $("#update-order").click(updateOrder);
  $("#refresh-data").click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);
