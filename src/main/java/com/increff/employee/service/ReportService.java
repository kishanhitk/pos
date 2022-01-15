package com.increff.employee.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.model.InventoryReportData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
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

    
}
