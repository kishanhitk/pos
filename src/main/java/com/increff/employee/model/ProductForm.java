package com.increff.employee.model;

public class ProductForm {
    private String name;
    private int brandCategory;
    private Double mrp;

    public String getName() {
        return name;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public int getBrandCategory() {
        return brandCategory;
    }

    public void setBrandCategory(int brandCategory) {
        this.brandCategory = brandCategory;
    }

    public void setName(String name) {
        this.name = name;
    }
}
