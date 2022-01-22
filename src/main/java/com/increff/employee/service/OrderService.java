package com.increff.employee.service;

import java.util.Date;
import java.util.List;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public OrderPojo createNewOrder() {
        OrderPojo order = new OrderPojo();
        return orderDao.insert(order);
    }

    public OrderPojo getById(int id) throws ApiException {
        return getCheck(id);
    }

    public List<OrderPojo> getAll() {
        return orderDao.selectAll();
    }

    public OrderPojo getCheck(int id) throws ApiException {
        OrderPojo order = orderDao.select(id);
        if (order == null) {
            throw new ApiException("Order with given id not found");
        }
        return order;
    }

    public OrderPojo update(OrderPojo orderPojo) throws ApiException {
        return orderDao.update(orderPojo);
    }

    public List<OrderPojo> getAllBetween(Date startingDate, Date endingDate) {
        return orderDao.selectAllBetween(startingDate, endingDate);
    }

}
