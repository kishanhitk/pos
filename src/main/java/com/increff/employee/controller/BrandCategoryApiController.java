package com.increff.employee.controller;

import java.util.List;

import com.increff.employee.dto.BrandCategoryDto;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.pojo.BrandCategoryPojo;
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
public class BrandCategoryApiController {

    @Autowired
    private BrandCategoryDto dto;

    @ApiOperation(value = "Adds a brand category")
    @RequestMapping(path = "/api/brandcategories", method = RequestMethod.POST)
    public BrandCategoryPojo add(@RequestBody BrandCategoryForm form) throws ApiException {
        return dto.addBrandCategory(form);
    }

    @ApiOperation(value = "Get all brand categories")
    @RequestMapping(path = "/api/brandcategories", method = RequestMethod.GET)
    public List<BrandCategoryData> getAll() {
        return dto.getAllBrandCategories();
    }

    @ApiOperation(value = "Get brand category by ID")
    @RequestMapping(path = "/api/brandcategories/{id}", method = RequestMethod.GET)
    public BrandCategoryData getById(@PathVariable Integer id) throws ApiException {
        return dto.getBrandCategoryById(id);
    }

    @ApiOperation(value = "Update Brand Details")
    @RequestMapping(path = "/api/brandcategories/{id}", method = RequestMethod.PUT)
    public BrandCategoryPojo update(@PathVariable int id, @RequestBody BrandCategoryForm f) throws ApiException {
        return dto.updateBrandCategory(id, f);
    }
}
