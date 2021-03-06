package com.increff.employee.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.BillData;
import com.increff.employee.model.InvoiceData;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.PDFUtil;
import com.increff.employee.util.XMLUtil;

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
public class OrderApiController {

    @Autowired
    private OrderDto dto;

    @ApiOperation(value = "Create an order")
    @RequestMapping(path = "/api/orders", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItemForm> orderItems,
            HttpServletResponse response) throws Exception {
        BillData billData = dto.addOrder(orderItems);
        XMLUtil.createXml(billData);
        PDFUtil.createPDF();
        byte[] encodedBytes = org.apache.commons.io.FileUtils.readFileToByteArray(new File("bill.pdf"));
        PDFUtil.createResponse(response, encodedBytes);
    }

    @ApiOperation(value = "Get all orders")
    @RequestMapping(path = "/api/orders", method = RequestMethod.GET)
    public List<OrderData> getAll() throws ApiException {
        return dto.getAllOrders();
    }

    @ApiOperation(value = "Get order by id")
    @RequestMapping(path = "/api/orders/{id}", method = RequestMethod.GET)
    public OrderData getOrderDetails(@PathVariable int id) throws ApiException {
        return dto.getOrderDetails(id);
    }

    @ApiOperation(value = "Update order by id")
    @RequestMapping(path = "/api/orders/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody List<OrderItemForm> orderItems) throws ApiException {
        dto.updateOrder(id, orderItems);
    }
}
