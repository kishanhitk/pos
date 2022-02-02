package com.increff.employee.controller;

import java.util.List;

import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
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
public class ProductApiController {

    @Autowired
    private ProductDto dto;

    @ApiOperation(value = "Adds a product")
    @RequestMapping(path = "/api/products", method = RequestMethod.POST)
    public ProductPojo add(@RequestBody ProductForm form) throws ApiException {
        return dto.add(form);
    }

    @ApiOperation(value = "Get all products")
    @RequestMapping(path = "/api/products", method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Get product by id")
    @RequestMapping(path = "/api/products/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable Integer id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation("Get product by barcode")
    @RequestMapping(path = "/api/products/barcode/{barcode}", method = RequestMethod.GET)
    public ProductData getByBarcode(@PathVariable String barcode) throws ApiException {
        return dto.getByBarcode(barcode);
    }

    @ApiOperation(value = "Update product by id")
    @RequestMapping(path = "/api/products/{id}", method = RequestMethod.PUT)
    public ProductPojo update(@PathVariable Integer id, @RequestBody ProductForm form) throws ApiException {
        return dto.update(id, form);
    }
}
