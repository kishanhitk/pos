function addRow() {
  // grab form
  const id = Math.floor(Math.random() * 10000000);
  $("#order-form-div")
    .append(`<div class="form-row d-flex justify-content-between" id="row-${id}">
  <div class="form-group col-4">
  <label for="inputBarcode${id}">Barcode</label>
  <input type="text" class="form-control" required id="inputBarcode${id}"
  placeholder="Enter Product Barcode">
  </div>
  <div class="form-group col-3">
  <label for="inputQuantity${id}">Barcode</label>
  <input type="number" class="form-control" required id="inputQuantity${id}" placeholder="Quantity">
  </div>
  <div class="form-group col-3">
  <label for="inputSellingPrice${id}">Barcode</label>
  <input type="number" step="0.01" class="form-control"  required id="inputSellingPrice${id}"
  placeholder="Selling Price">
  </div>
  <button class="btn btn-danger delete-row col-1" type="button" id=${id}>X</button>
  </div>`);
  $(".delete-row").on("click", deleteRow);
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

function deleteRow() {
  var id = $(this).attr("id");
  console.log(id);
  $("#row-" + id).remove();
}

function init() {
  $("#add-row").click(addRow);
  $("#order-form").submit(placeOrder);
  $(".delete-row").on("click", deleteRow);
}

$(document).ready(init);
