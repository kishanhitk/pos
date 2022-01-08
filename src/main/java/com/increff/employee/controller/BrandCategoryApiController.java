package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;

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
public class BrandCategoryApiController {

    @Autowired
    private BrandCategoryService service;

    @ApiOperation(value = "Adds a brand category")
    @RequestMapping(path = "/api/brandcategory", method = RequestMethod.POST)
    public void add(@RequestBody BrandCategoryForm form) throws ApiException {
        BrandCategoryPojo p = convert(form);
        service.add(p);
    }

    @ApiOperation(value = "Get all brand categories")
    @RequestMapping(path = "/api/brandcategory", method = RequestMethod.GET)
    public List<BrandCategoryData> getAll() {
        List<BrandCategoryPojo> list = service.getAll();
        List<BrandCategoryData> list2 = new ArrayList<BrandCategoryData>();
        for (BrandCategoryPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Get brand category by ID")
    @RequestMapping(path = "/api/brandcategory/{id}", method = RequestMethod.GET)
    public BrandCategoryData getById(@PathVariable Integer id) throws ApiException {
        BrandCategoryPojo p = service.get(id);
        BrandCategoryData d = convert(p);
        return d;
    }

    @ApiOperation(value = "Update Brand Details")
    @RequestMapping(path = "/api/brandcategory/{id}", method = RequestMethod.PUT)
    public BrandCategoryData update(@PathVariable int id, @RequestBody BrandCategoryForm f) throws ApiException {
        BrandCategoryPojo p = convert(f);
        BrandCategoryPojo updated = service.update(id, p);
        BrandCategoryData uf = convert(updated);
        return uf;
    }

    private static BrandCategoryPojo convert(BrandCategoryForm form) {
        BrandCategoryPojo p = new BrandCategoryPojo();
        p.setBrand(form.getBrand());
        p.setCategory(form.getCategory());
        return p;
    }

    private static BrandCategoryData convert(BrandCategoryPojo p) {
        BrandCategoryData d = new BrandCategoryData();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        d.setId(p.getId());
        d.setCreatedAt(p.getCreatedAt());
        d.setUpdatedAt(p.getUpdatedAt());
        return d;
    }

}
