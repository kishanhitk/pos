package com.increff.employee.service;

import java.util.List;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;

    public void add(List<OrderItemPojo> orderItems) {
        OrderPojo order = new OrderPojo();
        orderDao.insert(order);
        for (OrderItemPojo orderItem : orderItems) {
            orderItem.setOrderID(order.getId());
            orderItemDao.insert(orderItem);
        }
    }

    public List<OrderPojo> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderPojo get(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    public void update(OrderPojo p) {
        // TODO Auto-generated method stub

    }

    public void delete(int id) {
        // TODO Auto-generated method stub

    }

}
