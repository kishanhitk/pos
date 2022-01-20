package com.increff.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.pojo.OrderItemPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ProductService productService;

    public List<OrderItemData> get(int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList = orderItemDao.selectByOrderId(orderId);
        // map OrderItemPojo to OrderItemData
        return orderItemPojoList.stream().map(orderItemPojo -> {
            OrderItemData orderItemData = new OrderItemData();
            orderItemData.setId(orderItemPojo.getId());
            orderItemData.setOrderId(orderItemPojo.getOrderID());
            orderItemData.setProductId(orderItemPojo.getProductId());
            orderItemData.setQuantity(orderItemPojo.getQuantity());
            orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
            orderItemData.setBarcode(productService.get(orderItemPojo.getProductId()).getBarcode());
            return orderItemData;
        }).collect(Collectors.toList());
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
