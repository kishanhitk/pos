function addRow() {
  // grab form
  const id = Math.random() * 1000;
  $("#order-form-div")
    .append(`<div class="form-row d-flex justify-content-between">
  <div class="form-group col-4">
  <label for="inputBarcode${id}">Barcode</label>
  <input type="text" class="form-control" id="inputBarcode${id}"
  placeholder="Enter Product Barcode">
  </div>
  <div class="form-group col-4">
  <label for="inputQuantity${id}">Barcode</label>
  <input type="number" class="form-control" id="inputQuantity${id}" placeholder="Quantity">
  </div>
  <div class="form-group col-4">
  <label for="inputSellingPrice${id}">Barcode</label>
  <input type="number" step="0.01" class="form-control" id="inputSellingPrice${id}"
  placeholder="Selling Price">
  </div>
  </div>`);
}

function placeOrder(e) {
  e.preventDefault();
  var $form = $("#order-form");
  var formData = $form.serializeArray();
  var order = {};
  console.log(formData);
  formData.forEach(function (entry) {
    order[entry.name] = entry.value;
  });
  console.log(order);
}

function init() {
  $("#add-row").click(addRow);
  $("#order-form").submit(placeOrder);
}

$(document).ready(init);
