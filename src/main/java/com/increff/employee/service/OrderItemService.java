package com.increff.employee.service;

import java.util.List;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public List<OrderItemPojo> get(int orderId) throws ApiException {
        return orderItemDao.selectByOrderId(orderId);
    }

    public void deleteByOrderId(int orderId) {
        orderItemDao.deleteByOrderId(orderId);
    }

    public void insertMutiple(List<OrderItemPojo> newOrderItems) {
        for (OrderItemPojo orderItemPojo : newOrderItems) {
            orderItemDao.insert(orderItemPojo);
        }
    }

    public void insert(OrderItemPojo orderItemPojo) {
        orderItemDao.insert(orderItemPojo);
    }

    public List<OrderItemPojo> selectByOrderId(int id) {
        return orderItemDao.selectByOrderId(id);

    }

}
