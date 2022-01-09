package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;

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
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItemForm> orderItems) throws ApiException {
        service.add(orderItems);
    }

    @ApiOperation(value = "Get all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAll() {
        List<OrderPojo> list = service.getAll();
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderPojo p : list) {
            list2.add(new OrderData(p));
        }
        return list2;
    }

    @ApiOperation(value = "Get order by id")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderData getOrderDetails(@PathVariable int id) throws ApiException {
        return service.getOrderDetails(id);
    }
}
