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
  var url = getSalesReportUrl();
  $.get(url, JSON.parse(json), function (returnedData) {
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
  var $brand = $(`${formId} select[name=brand]`);
  $brand.empty();
  var set = new Set(data.map((item) => item.brand));
  console.log(set);
  for (let i of set) {
    let option = '<option value="' + i + '">' + i + "</option>";
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
      getSalesReport();
    },
    error: handleAjaxError,
  });
}

//INITIALIZATION CODE
function init() {
  $("#refresh-data").click(getSalesReport);
  $("#startingdatepicker").datepicker({
    // Set one month before today
    value: new Date(
      new Date().setMonth(new Date().getMonth() - 1)
    ).toLocaleDateString(),
    uiLibrary: "bootstrap4",
  });
  $("#endingdatepicker").datepicker({
    // Set todays date as default date
    value: new Date().toLocaleDateString(),
    uiLibrary: "bootstrap4",
  });
  $("#filter-form").submit(filterSalesReport);
}

$(document).ready(init);
$(document).ready(populateBrandCategoryDropDown);
