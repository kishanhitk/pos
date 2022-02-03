package com.increff.employee.controller;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import com.increff.employee.dto.BillDto;
import com.increff.employee.model.BillData;
import com.increff.employee.util.PDFUtil;
import com.increff.employee.util.XMLUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InvoiceApiController {
    @Autowired
    private BillDto dto;

    @ApiOperation(value = "Get Invoice")
    @RequestMapping(path = "/api/invoice/{id}", method = RequestMethod.GET)
    public void add(@PathVariable Integer id,
            HttpServletResponse response) throws Exception {
        BillData billData = dto.getBillData(id);
        XMLUtil.createXml(billData);
        PDFUtil.createPDF();
        byte[] encodedBytes = org.apache.commons.io.FileUtils.readFileToByteArray(new File("bill.pdf"));
        PDFUtil.createResponse(response, encodedBytes, id);
    }
}
