//HELPER METHOD
function toJson($form) {
  var serialized = $form.serializeArray();

  var s = "";
  var data = {};
  for (s in serialized) {
    data[serialized[s]["name"]] = serialized[s]["value"];
  }
  var json = JSON.stringify(data);
  return json;
}

function handleAjaxError(response) {
  $(".notifyjs-corner").empty();
  try {
    var response = JSON.parse(response.responseText);
    $.notify(response.message, { type: "error", autoHide: false });
  } catch (error) {
    $.notify("Something went wrong!", "error");
  }
}

function readFileData(file, callback) {
  var config = {
    header: true,
    delimiter: "\t",
    skipEmptyLines: "greedy",
    complete: function (results) {
      //Check that file length should not be greater than 5000
      if (results.data.length > 5000) {
        $.notify("File size exceeds 5000 rows", "error");
        return;
      }
      callback(results);
    },
  };
  Papa.parse(file, config);
}

function writeFileData(arr) {
  var config = {
    quoteChar: "",
    escapeChar: "",
    delimiter: "\t",
  };

  var data = Papa.unparse(arr, config);
  var blob = new Blob([data], { type: "text/tsv;charset=utf-8;" });
  var fileUrl = null;

  if (navigator.msSaveBlob) {
    fileUrl = navigator.msSaveBlob(blob, "download.tsv");
  } else {
    fileUrl = window.URL.createObjectURL(blob);
  }
  var tempLink = document.createElement("a");
  tempLink.href = fileUrl;
  tempLink.setAttribute("download", "download.tsv");
  tempLink.click();
}

function convertTimeStampToDateTime(timestamp) {
  var date = new Date(timestamp);
  return (
    date.getDate() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getFullYear() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes() +
    ":" +
    date.getSeconds()
  );
}
function generatePdf(selector, outputFileName) {
  html2canvas($(selector)[0], {
    onrendered: function (canvas) {
      var data = canvas.toDataURL();
      var docDefinition = {
        content: [
          {
            image: data,
            width: 500,
          },
        ],
      };
      pdfMake.createPdf(docDefinition).download(outputFileName);
    },
  });
}
