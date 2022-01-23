package com.increff.employee.controller;

import java.util.List;

import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InventoryApiController {

    @Autowired
    private InventoryDto dto;

    @ApiOperation(value = "Adds a Inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public InventoryPojo addInventory(@RequestBody InventoryForm form) throws ApiException {
        return dto.addInventory(form);
    }

    @ApiOperation(value = "Get all Inventorys")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll() {
        return dto.getAll();
    }

    @ApiOperation(value = "Get Inventory by id")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable Integer id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Update Inventory by id")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public InventoryPojo update(@PathVariable Integer id, @RequestBody InventoryForm form) throws ApiException {
        return dto.update(id, form);
    }
}
