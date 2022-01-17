package com.increff.employee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.model.InventoryReportData;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderService orderService;

    // Get inventory detail of brandcategory
    @Transactional
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

    @Transactional
    public List<BrandCategoryPojo> getBrandCategoryReport() throws ApiException {
        List<BrandCategoryPojo> brandCategoryList = brandCategoryService.getAll();
        return brandCategoryList;
    }

    @Transactional
    public List<SalesReportData> getCategoryWiseSalesReport(String brand, String startDate, String endDate)
            throws ApiException {
        Date startingDate = new Date(startDate);
        Date endingDate = new Date(endDate);
        List<SalesReportData> salesReportData = new ArrayList<SalesReportData>();
        List<BrandCategoryPojo> brandCategoryList = brandCategoryService.getByBrandName(brand);
        // Steps:
        // 1. Get all the orders between startingDate and endingDate
        // 2. Get all the order items of the orders
        // 3. Get the product of the order items
        // 4. Get the brandCategory of the products
        // 5. Get the list of brandCategory
        List<OrderPojo> orderList = orderService.getAllBetween(startingDate, endingDate);
        List<OrderItemPojo> orderItemList = new ArrayList<OrderItemPojo>();
        for (OrderPojo order : orderList) {
            List<OrderItemPojo> orderItemListTemp = orderService.getOrderItemByOrder(order.getId());
            orderItemList.addAll(orderItemListTemp);
        }
        List<ProductPojo> productList = new ArrayList<ProductPojo>();
        for (OrderItemPojo orderItem : orderItemList) {
            ProductPojo product = productService.get(orderItem.getProductId());
            productList.add(product);
        }
        for (BrandCategoryPojo brandCategory : brandCategoryList) {
            SalesReportData salesReportItemDataItem = new SalesReportData(brandCategory, 0, 0.00);
            salesReportData.add(salesReportItemDataItem);
        }

        for (OrderItemPojo orderItem : orderItemList) {
            int productId = orderItem.getProductId();
            ProductPojo product = productList.stream().filter(p -> p.getId() == productId).findFirst().get();
            int brandCategoryId = product.getBrandCategory();
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
