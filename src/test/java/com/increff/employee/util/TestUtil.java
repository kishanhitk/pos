package com.increff.employee.util;

import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.ProductForm;

public class TestUtil {
    public static BrandCategoryForm getBrandForm(String brand, String category) {
        BrandCategoryForm form = new BrandCategoryForm();
        form.setBrand(brand);
        form.setCategory(category);
        return form;
    }

    public static ProductForm getProductFormDto(String string, Integer id, double d) {
        ProductForm form = new ProductForm();
        form.setBrandCategory(id);
        form.setName(string);
        form.setMrp(d);
        return form;
    }
}
