package com.increff.employee.util;

import com.increff.employee.model.BrandCategoryForm;

public class TestUtil {
    public static BrandCategoryForm getBrandForm(String brand, String category) {
        BrandCategoryForm form = new BrandCategoryForm();
        form.setBrand(brand);
        form.setCategory(category);
        return form;
    }
}
