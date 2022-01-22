package com.increff.employee.util;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.OrderItemPojo;

public class ConvertUtil {
    public static OrderItemPojo convertOrderItemFormToOrderItemPojo(OrderItemForm form) {
        OrderItemPojo pojo = new OrderItemPojo();
        pojo.setQuantity(form.getQuantity());
        pojo.setSellingPrice(form.getSellingPrice());
        return pojo;
    }

    public static BrandCategoryPojo convertBrandCategoryFormtoBrandCategoryPojo(BrandCategoryForm form) {
        BrandCategoryPojo pojo = new BrandCategoryPojo();
        pojo.setBrand(form.getBrand());
        pojo.setCategory(form.getCategory());
        return pojo;
    }

    public static BrandCategoryData convertBrandCategoryPojotoBrandCategoryData(BrandCategoryPojo pojo) {
        BrandCategoryData data = new BrandCategoryData();
        data.setBrand(pojo.getBrand());
        data.setCategory(pojo.getCategory());
        data.setCreatedAt(pojo.getCreatedAt());
        data.setId(pojo.getId());
        data.setUpdatedAt(pojo.getUpdatedAt());
        return data;
    }
}
