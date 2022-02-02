// Expected Output format
// [
//     {
//       "barcode": "string",
//       "quantity": 0,
//     }
// ]

const data = [
  { name: "barcode", value: "b1" },
  { name: "quantity", value: "p1" },
  { name: "barcode", value: "b2" },
  { name: "quantity", value: "p2" },
];

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
