function getSalesReportUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/reports/sales";
}

function getBrandCategoryUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/brandcategories";
}

function filterSalesReport(e) {
  e.preventDefault();
  getSalesReport();
}

function getSalesReport() {
  //   e.preventDefault();
  var $form = $("#filter-form");
  var json = toJson($form);
  console.log(json);
  var url = getSalesReportUrl();
  var params = {
    brand: "amul",
    startDate: "2020/01/01",
    endDate: "2024/01/31",
  };
  $.get(url, params, function (returnedData) {
    displaySalesReportList(returnedData);
  }).fail(function (response) {
    handleAjaxError(response);
  });
}

//UI DISPLAY METHODS
function displaySalesReportList(data) {
  var $tbody = $("#sales-report-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var row =
      "<tr>" +
      "<td>" +
      e.category +
      "</td>" +
      "<td>" +
      e.quantity +
      "</td>" +
      "<td>" +
      e.revenue +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}

function addDataToBrandCategoryDropdown(data, formId) {
  var $brand = $(`${formId} select[name=brandCategory]`);
  $brand.empty();
  $brand.append('<option value="">Select Brand</option>');
  for (var i in data) {
    var e = data[i];
    var option = '<option value="' + e.id + '">' + e.brand + "</option>";
    $brand.append(option);
  }
}

function populateBrandCategoryDropDown() {
  var url = getBrandCategoryUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      addDataToBrandCategoryDropdown(data, "#filter-form");
    },
    error: handleAjaxError,
  });
}

//INITIALIZATION CODE
function init() {
  $("#refresh-data").click(getSalesReport);
  $("#startingdatepicker").datepicker({
    uiLibrary: "bootstrap4",
  });
  $("#endingdatepicker").datepicker({
    uiLibrary: "bootstrap4",
  });
  $("#filter-form").submit(filterSalesReport);
}

$(document).ready(init);
$(document).ready(populateBrandCategoryDropDown);
