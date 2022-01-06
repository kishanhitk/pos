package com.increff.employee.model;

public class ProductData extends ProductForm {

    private int id;
    private String barcode;

    public int getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setId(int id) {
        this.id = id;
    }

}
