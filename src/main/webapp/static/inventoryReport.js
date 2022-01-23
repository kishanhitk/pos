function getInventoryReportUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/reports/inventory";
}

function getInventoryReport() {
  var url = getInventoryReportUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayInventoryReportList(data);
    },
    error: handleAjaxError,
  });
}

//UI DISPLAY METHODS
function displayInventoryReportList(data) {
  var $tbody = $("#inventory-report-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var row =
      "<tr>" +
      "<td>" +
      e.brand +
      "</td>" +
      "<td>" +
      e.category +
      "</td>" +
      "<td>" +
      e.quantity +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}
function downloadInventoryReport() {
  generatePdf("#inventory-report-table", "Inventory Report");
}
//INITIALIZATION CODE
function init() {
  $("#refresh-data").click(getInventoryReport);
  $("#download-data").click(downloadInventoryReport);
}

$(document).ready(init);
$(document).ready(getInventoryReport);
