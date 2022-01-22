package com.increff.employee.controller;

import java.util.List;

import com.increff.employee.dto.ReportDto;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ReportApiController {

    @Autowired
    private ReportDto dto;

    @ApiOperation(value = "Get inventory detail of brandcategory")
    @RequestMapping(path = "api/reports/inventory", method = RequestMethod.GET)
    public List<InventoryReportData> getInventoryReport() throws ApiException {
        return dto.inventoryReport();
    }

    @ApiOperation(value = "Get brandcategory detail")
    @RequestMapping(path = "api/reports/brand", method = RequestMethod.GET)
    public List<BrandCategoryPojo> getBrandCategoryReport() throws ApiException {
        return dto.getBrandCategoryReport();
    }

    @ApiOperation(value = "Get sales detail of brandcategory")
    @RequestMapping(path = "api/reports/sales", method = RequestMethod.GET)
    public List<SalesReportData> getSalesReport(
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) throws ApiException

    {
        return dto.getCategoryWiseSalesReport(brand, startDate, endDate);
    }
}
