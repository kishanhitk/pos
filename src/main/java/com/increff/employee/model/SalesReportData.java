package com.increff.employee.model;

import com.increff.employee.pojo.BrandCategoryPojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesReportData {
    public SalesReportData(BrandCategoryPojo brandCategory, int i, double d) {
        this.brand = brandCategory.getBrand();
        this.category = brandCategory.getCategory();
        this.brandCategoryId = brandCategory.getId();
        this.quantity = i;
        this.revenue = d;
    }

    private Double revenue;
    private Integer quantity;
    private String category;
    private String brand;
    private Integer brandCategoryId;
}
