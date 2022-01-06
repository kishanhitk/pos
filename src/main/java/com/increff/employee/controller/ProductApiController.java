package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        ProductPojo p = convert(form);
        service.add(p);
    }

    @ApiOperation(value = "Get all products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAll() {
        List<ProductPojo> list = service.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    private static ProductPojo convert(ProductForm form) {
        ProductPojo p = new ProductPojo();
        p.setBrandCategory(form.getBrandCategory());
        p.setName(form.getName());
        p.setMrp(form.getMrp());
        return p;
    }

    private static ProductData convert(ProductPojo p) {
        ProductData d = new ProductData();
        d.setBrandCategory(p.getBrandCategory());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        d.setId(p.getId());
        return d;
    }
}
