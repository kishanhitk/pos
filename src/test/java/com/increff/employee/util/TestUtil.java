package com.increff.employee.util;

import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.OrderItemForm;
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

    public static InventoryForm getInventoryForm(Integer id, int i) {
        InventoryForm form = new InventoryForm();
        form.setProductId(id);
        form.setQuantity(i);
        return form;
    }

    public static OrderItemForm[] getOrderItemFormArrayDto(String barcode, String barcode2, int i, int j, Double mrp,
            Double mrp2) {
        OrderItemForm[] orderItemForms = new OrderItemForm[2];
        orderItemForms[0] = new OrderItemForm();
        orderItemForms[0].setBarcode(barcode);
        orderItemForms[0].setQuantity(i);
        orderItemForms[0].setSellingPrice(mrp);
        orderItemForms[1] = new OrderItemForm();
        orderItemForms[1].setBarcode(barcode2);
        orderItemForms[1].setQuantity(j);
        orderItemForms[1].setSellingPrice(mrp2);
        return orderItemForms;

    }
}
