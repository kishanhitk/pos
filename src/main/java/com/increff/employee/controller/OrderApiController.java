package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void add(@RequestBody List<OrderItemForm> orderItems) {
        List<OrderItemPojo> list = new ArrayList<OrderItemPojo>();
        for (OrderItemForm o : orderItems) {
            list.add(convert(o));
        }
        service.add(list);
    }

    private OrderItemPojo convert(OrderItemForm form) {
        OrderItemPojo pojo = new OrderItemPojo();
        pojo.setProductId(form.getProductId());
        pojo.setQuantity(form.getQuantity());
        return pojo;
    }
}
