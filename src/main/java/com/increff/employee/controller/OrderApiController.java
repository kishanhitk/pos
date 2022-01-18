package com.increff.employee.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.increff.employee.model.InvoiceData;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import com.increff.employee.util.PDFUtil;
import com.increff.employee.util.XMLUtil;

import org.apache.fop.apps.FOPException;
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
    private OrderService service;

    @ApiOperation(value = "Create an order")
    @RequestMapping(path = "/api/orders", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItemForm> orderItems, HttpServletResponse response)
            throws ApiException, ParserConfigurationException, TransformerException, IOException, FOPException {
        List<InvoiceData> list = service.add(orderItems);
        XMLUtil.createXml(list);
        byte[] encodedBytes = PDFUtil.createPDF();
        PDFUtil.createResponse(response, encodedBytes);
    }

    @ApiOperation(value = "Get all orders")
    @RequestMapping(path = "/api/orders", method = RequestMethod.GET)
    public List<OrderData> getAll() {
        List<OrderPojo> list = service.getAll();
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderPojo p : list) {
            list2.add(new OrderData(p));
        }
        return list2;
    }

    @ApiOperation(value = "Get order by id")
    @RequestMapping(path = "/api/orders/{id}", method = RequestMethod.GET)
    public OrderData getOrderDetails(@PathVariable int id) throws ApiException {
        return service.getOrderDetails(id);
    }
}
