package com.increff.employee.util;

import java.util.List;
import java.util.UUID;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
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

    public static InventoryPojo convertInventoryFormToInventoryPojo(InventoryForm form) {
        InventoryPojo p = new InventoryPojo();
        p.setProductId(form.getProductId());
        p.setQuantity(form.getQuantity());
        return p;

    }

    public static InventoryData convertInventoryPojoToInventoryData(InventoryPojo p) {
        InventoryData d = new InventoryData();
        d.setProductId(p.getProductId());
        d.setQuantity(p.getQuantity());
        d.setCreatedAt(p.getCreatedAt());
        d.setUpdatedAt(p.getUpdatedAt());
        return d;
    }

    public static OrderItemData convertOrderItemPojoToOrderItemData(OrderItemPojo pojo, String barcode) {
        OrderItemData data = new OrderItemData();
        data.setId(pojo.getId());
        data.setOrderId(pojo.getOrderID());
        data.setProductId(pojo.getProductId());
        data.setQuantity(pojo.getQuantity());
        data.setSellingPrice(pojo.getSellingPrice());
        data.setCreatedAt(pojo.getCreatedAt());
        data.setUpdatedAt(pojo.getUpdatedAt());
        data.setBarcode(barcode);
        return data;
    }

    public static OrderData convertOrderPojoToOrderData(OrderPojo p, List<OrderItemData> orderItems) {
        OrderData d = new OrderData();
        d.setCreatedAt(p.getCreatedAt());
        d.setId(p.getId());
        d.setUpdatedAt(p.getUpdatedAt());
        d.setOrderItems(orderItems);
        return d;
    }
}
