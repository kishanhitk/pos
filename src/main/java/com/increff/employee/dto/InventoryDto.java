package com.increff.employee.dto;

import java.util.List;

import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.util.ConvertUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryDto {

    @Autowired
    private InventoryService inventoryService;

    public InventoryPojo addInventory(InventoryForm form) throws ApiException {
        validateForm(form);
        InventoryPojo inventoryPojo = inventoryService.getByProductId(form.getProductId());
        inventoryPojo.setQuantity(inventoryPojo.getQuantity() + form.getQuantity());
        return inventoryService.update(inventoryPojo.getId(), inventoryPojo);
    }

    public List<InventoryPojo> getAll() {
        return inventoryService.getAll();
    }

    public InventoryPojo get(Integer id) throws ApiException {
        return inventoryService.get(id);
    }

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
