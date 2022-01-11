package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;

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
public class ProductApiController {

    @Autowired
    private ProductService service;

    @ApiOperation(value = "Adds a product")
    @RequestMapping(path = "/api/products", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        ProductPojo p = convert(form);
        service.add(p);
    }

    @ApiOperation(value = "Get all products")
    @RequestMapping(path = "/api/products", method = RequestMethod.GET)
    public List<ProductData> getAll() {
        List<ProductPojo> list = service.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Get product by id")
    @RequestMapping(path = "/api/products/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable Integer id) throws ApiException {
        ProductPojo p = service.get(id);
        return convert(p);
    }

    @ApiOperation(value = "Update product by id")
    @RequestMapping(path = "/api/products/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody ProductForm form) throws ApiException {
        ProductPojo p = convert(form);
        service.update(id, p);
    }

    private static ProductPojo convert(ProductForm form) {
        ProductPojo p = new ProductPojo();
        p.setBrandCategory(form.getBrandCategory());
        p.setName(form.getName());
        p.setMrp(form.getMrp());
        p.setBarcode(UUID.randomUUID().toString());
        return p;
    }

    private static ProductData convert(ProductPojo p) {
        ProductData d = new ProductData();
        d.setBrandCategory(p.getBrandCategory());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        d.setId(p.getId());
        d.setBarcode(p.getBarcode());
        return d;
    }
}
