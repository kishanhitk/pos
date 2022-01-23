package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.ConvertUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InventoryDto {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;

    @Transactional(rollbackFor = ApiException.class)
    public InventoryPojo addInventory(InventoryForm form) throws ApiException {
        validateForm(form);
        InventoryPojo inventoryPojo = inventoryService.getByProductId(form.getProductId());
        inventoryPojo.setQuantity(inventoryPojo.getQuantity() + form.getQuantity());
        return inventoryService.update(inventoryPojo.getId(), inventoryPojo);
    }

    @Transactional(readOnly = true)
    public List<InventoryData> getAll() {
        List<InventoryPojo> inventoryPojos = inventoryService.getAll();
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : inventoryPojos) {
            ProductPojo productPojo = productService.get(inventoryPojo.getProductId());
            InventoryData inventoryData = ConvertUtil.convertInventoryPojoToInventoryData(inventoryPojo, productPojo);
            inventoryDataList.add(inventoryData);
        }
        return inventoryDataList;
    }

    @Transactional(readOnly = true)
    public InventoryData get(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(id);
        ProductPojo productPojo = productService.get(inventoryPojo.getProductId());
        InventoryData inventoryData = ConvertUtil.convertInventoryPojoToInventoryData(inventoryPojo, productPojo);
        return inventoryData;
    }

    @Transactional(rollbackFor = ApiException.class)
    public InventoryPojo update(Integer id, InventoryForm form) throws ApiException {
        validateForm(form);
        InventoryPojo inventoryPojo = ConvertUtil.convertInventoryFormToInventoryPojo(form);
        return inventoryService.update(id, inventoryPojo);
    }

    public void validateForm(InventoryForm inventoryForm) throws ApiException {
        if (inventoryForm.getQuantity() < 0) {
            throw new ApiException("Quantity should be a postive non zero number.");
        }
    }
}
