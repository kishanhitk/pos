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

cartItems = [];

function addToCart(event) {
  event.preventDefault();
  const $form = $("#cart-item-form");
  const data = toJson($form);
  cartItems.push(data);
  //   Clear the form
  $form.trigger("reset");
  displayCartItemsToTable();
  console.log(cartItems);
}

function displayCartItemsToTable() {
  const $table = $("#cart-items-table");
  $table.empty();
  $table.append(`<thead class="table-dark">
    <tr>
        <th>Barcode</th>
        <th>Name</th>
        <th>Quantity</th>
        <th>Selling Price</th>
        <th>Total</th>
        <th>Action</th>
    </tr>
</thead>`);
  $table.append(`<tbody>`);
  cartItems.forEach((item) => {
    item = JSON.parse(item);
    $table.append(`<tr>
        <td>${item.barcode}</td>
        <td>${item.name}</td>
        <td>${item.quantity}</td>
        <td>${item.sellingPrice}</td>
        <td>${item.quantity * item.sellingPrice}</td>
        <td><button class="delete-row btn btn-outline-danger" data-row-id="${
          item.barcode
        }">Delete</button></td>
    </tr>`);
  });
  $table.append(`</tbody>`);
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

  var $form = $("#order-form");
  var data = convertToOrderItems($form.serializeArray());

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
  $("#cart-item-form").submit(addToCart);
  //   $("#add-row").click(addRow);
  $("#order-form").submit(placeOrder);
  $(".delete-row").on("click", deleteRow);
}

$(document).ready(init);
