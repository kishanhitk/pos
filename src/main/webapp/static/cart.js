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

let cartItems = [];

function addToCart(event) {
  event.preventDefault();
  const $form = $("#cart-item-form");
  const data = JSON.parse(toJson($form));

  console.log(data);
  console.log(cartItems);
  const barcode = data.barcode;
  // Update existing item
  const existingItem = cartItems.find((item) => {
    return item.barcode === barcode;
  });
  if (existingItem) {
    existingItem.quantity =
      Number(existingItem.quantity) + Number(data.quantity);
    existingItem.sellingPrice =
      Number(existingItem.sellingPrice) + Number(data.sellingPrice);
  } else {
    // Add new item
    cartItems.push({
      barcode: barcode,
      quantity: Number(data.quantity),
      sellingPrice: Number(data.sellingPrice),
    });
  }
  //   Clear the form
  $form.trigger("reset");
  displayCartItemsToTable();
}

function displayCartItemsToTable() {
  if (cartItems.length > 0) {
    $("#place-order").show();
  }
  const $table = $("#cart-items-table");
  $table.empty();
  $table.append(`<thead class="table-dark">
    <tr>
        <th>Barcode</th>
        <th>Quantity</th>
        <th>Selling Price</th>
        <th>Total</th>
        <th>Action</th>
    </tr>
</thead>`);
  $table.append(`<tbody>`);
  cartItems.forEach((item) => {
    $table.append(`<tr id=${item.barcode}>
        <td>${item.barcode}</td>
        <td>${item.quantity}</td>
        <td>${item.sellingPrice}</td>
        <td>${item.quantity * item.sellingPrice}</td>
        <td><button class="delete-row btn btn-outline-danger" onclick="deleteRow(${
          item.barcode
        })">Delete</button></td>
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
  console.log("place order");
  console.log(cartItems);
  $.ajax({
    url: getOrderUrl(),
    type: "POST",
    data: JSON.stringify(cartItems),
    contentType: "application/json",
    success: function (data) {
      console.log(data);
      $("#success-modal").modal("show");
    },
    error: function (data) {
      handleAjaxError(data);
    },
  });
}

function deleteRow(barcode) {
  console.log(barcode);
  console.log(cartItems);
  cartItems = cartItems.filter((item) => {
    return item.barcode != barcode;
  });
  console.log(cartItems);
  displayCartItemsToTable();
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
  $("#place-order").hide();
  $("#place-order").click(placeOrder);
  $(".delete-row").on("click", deleteRow);
}

$(document).ready(init);
