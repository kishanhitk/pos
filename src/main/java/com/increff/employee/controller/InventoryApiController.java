package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;

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
    private InventoryService service;

    @ApiOperation(value = "Adds a Inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm form) throws ApiException {
        InventoryPojo p = convert(form);
        service.add(p);
    }

    @ApiOperation(value = "Get all Inventorys")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll() {
        List<InventoryPojo> list = service.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Get Inventory by id")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable Integer id) throws ApiException {
        InventoryPojo p = service.get(id);
        return convert(p);
    }

    @ApiOperation(value = "Update Inventory by id")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody InventoryForm form) throws ApiException {
        InventoryPojo p = convert(form);
        service.update(id, p);
    }

    private static InventoryPojo convert(InventoryForm form) {
        InventoryPojo p = new InventoryPojo();
        p.setProductId(form.getId());
        p.setQuantity(form.getQuantity());
        return p;

    }

    private static InventoryData convert(InventoryPojo p) {
        InventoryData d = new InventoryData();
        d.setId(p.getProductId());
        d.setQuantity(p.getQuantity());
        d.setCreatedAt(p.getCreatedAt());
        d.setUpdatedAt(p.getUpdatedAt());
        return d;
    }
}
