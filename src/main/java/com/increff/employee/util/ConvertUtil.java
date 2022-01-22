package com.increff.employee.util;

import java.util.UUID;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;

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

    public static ProductPojo convertProductFormToProductPojo(ProductForm form) {
        ProductPojo p = new ProductPojo();
        p.setBrandCategoryId(form.getBrandCategory());
        p.setName(form.getName());
        p.setMrp(form.getMrp());
        p.setBarcode(UUID.randomUUID().toString());
        return p;
    }

    public static ProductData convertProductPojoToProductData(ProductPojo p) {
        ProductData d = new ProductData();
        d.setBrandCategory(p.getBrandCategoryId());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        d.setId(p.getId());
        d.setBarcode(p.getBarcode());
        return d;
    }
}
