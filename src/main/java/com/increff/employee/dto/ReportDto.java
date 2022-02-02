package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.model.InventoryReportData;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    // Get inventory detail of brandcategory
    @Transactional(readOnly = true)
    public List<InventoryReportData> inventoryReport() throws ApiException {
        List<InventoryReportData> inventoryReportData = new ArrayList<InventoryReportData>();
        List<BrandCategoryPojo> brandCategoryList = brandCategoryService.getAll();
        for (BrandCategoryPojo brandCategory : brandCategoryList) {
            InventoryReportData inventoryReportItemDataItem = new InventoryReportData();
            inventoryReportItemDataItem.setBrand(brandCategory.getBrand());
            inventoryReportItemDataItem.setCategory(brandCategory.getCategory());
            inventoryReportItemDataItem.setId(brandCategory.getId());
            int quantity = 0;
            List<ProductPojo> productList = productService.getProductByBrandCategory(brandCategory.getId());
            for (ProductPojo product : productList) {
                InventoryPojo inventory = inventoryService.get(product.getId());
                quantity += inventory.getQuantity();
            }
            inventoryReportItemDataItem.setQuantity(quantity);
            inventoryReportData.add(inventoryReportItemDataItem);
        }
        return inventoryReportData;
    }

    @Transactional(readOnly = true)
    public List<BrandCategoryPojo> getBrandCategoryReport() throws ApiException {
        List<BrandCategoryPojo> brandCategoryList = brandCategoryService.getAll();
        return brandCategoryList;
    }

    @Transactional(readOnly = true)
    public List<SalesReportData> getCategoryWiseSalesReport(String brand, String startDate, String endDate)
            throws ApiException {
        Date startingDate = new Date(startDate);
        Date endingDate = new Date(endDate);
        List<SalesReportData> salesReportData = new ArrayList<SalesReportData>();
        List<BrandCategoryPojo> brandCategoryList = brandCategoryService.getByBrandName(brand);
        List<OrderPojo> orderList = orderService.getAllBetween(startingDate, endingDate);
        List<OrderItemPojo> orderItemList = new ArrayList<OrderItemPojo>();

        // Get all order items of all orders
        for (OrderPojo order : orderList) {
            List<OrderItemPojo> orderItemListTemp = orderItemService.selectByOrderId(order.getId());
            orderItemList.addAll(orderItemListTemp);
        }

        // Get all product of all order items
        List<ProductPojo> productList = new ArrayList<ProductPojo>();
        for (OrderItemPojo orderItem : orderItemList) {
            ProductPojo product = productService.get(orderItem.getProductId());
            productList.add(product);
        }
        // Initialize salesReportData
        for (BrandCategoryPojo brandCategory : brandCategoryList) {
            SalesReportData salesReportItemDataItem = new SalesReportData(brandCategory, 0, 0.00);
            salesReportData.add(salesReportItemDataItem);
        }

        // Calculate salesReportData
        for (OrderItemPojo orderItem : orderItemList) {
            int productId = orderItem.getProductId();
            ProductPojo product = productList.stream().filter(p -> p.getId() == productId).findFirst().get();
            int brandCategoryId = product.getBrandCategoryId();
            // Find and update salesReportData
            for (SalesReportData salesReportItemDataItem : salesReportData) {
                if (salesReportItemDataItem.getBrandCategoryId() == brandCategoryId) {
                    salesReportItemDataItem
                            .setQuantity(salesReportItemDataItem.getQuantity() + orderItem.getQuantity());
                    salesReportItemDataItem.setRevenue(
                            salesReportItemDataItem.getRevenue()
                                    + orderItem.getQuantity() * orderItem.getSellingPrice());
                }
            }
        }
        return salesReportData;
    }

}
