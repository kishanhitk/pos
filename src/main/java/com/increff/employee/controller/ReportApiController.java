package com.increff.employee.controller;

import java.util.List;

import com.increff.employee.model.InventoryReportData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ReportApiController {

    @Autowired
    private ReportService service;

    @ApiOperation(value = "Get inventory detail of brandcategory")
    @RequestMapping(path = "api/reports/inventory", method = RequestMethod.GET)
    public List<InventoryReportData> getInventoryReport() throws ApiException {
        return service.inventoryReport();
    }

    @ApiOperation(value = "Get brandcategory detail")
    @RequestMapping(path = "api/reports/brandcategory", method = RequestMethod.GET)
    public List<BrandCategoryPojo> getBrandCategoryReport() throws ApiException {
        return service.getBrandCategoryReport();
    }
}
