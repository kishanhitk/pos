package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.ConvertUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    ConvertUtil convertUtil = new ConvertUtil();

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderItemForm> orderItems) throws ApiException {
        OrderPojo order = new OrderPojo();
        orderDao.insert(order);
        for (OrderItemForm orderItem : orderItems) {
            ProductPojo product = productService.getProductByBarcode(orderItem.getBarcode());
            OrderItemPojo orderItemPojo = convertUtil.convert(orderItem);
            orderItemPojo.setOrderID(order.getId());
            orderItemPojo.setProductId(product.getId());
            orderItemDao.insert(orderItemPojo);
            inventoryService.reduce(orderItemPojo.getProductId(), orderItemPojo.getQuantity());
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
