function getbrandReportUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/reports/brand";
}

function getbrandReport() {
  var url = getbrandReportUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displaybrandReportList(data);
    },
    error: handleAjaxError,
  });
}

//UI DISPLAY METHODS
function displaybrandReportList(data) {
  var $tbody = $("#brandCategory-table").find("tbody");
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
      e.id +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}

function downloadBrandReport() {
  $("#brandCategory-table").tableToCSV("brand_report");
}
//INITIALIZATION CODE
function init() {
  $("#refresh-data").click(getbrandReport);
  $("#download-data").click(downloadBrandReport);
}

$(document).ready(init);
$(document).ready(getbrandReport);
