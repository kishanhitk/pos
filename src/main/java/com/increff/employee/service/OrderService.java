package com.increff.employee.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemData;
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
    private OrderItemService orderItemService;
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
            if (product == null) {
                throw new ApiException("Product with barcode " + orderItem.getBarcode() + " not found");
            }
            OrderItemPojo orderItemPojo = convertUtil.convert(orderItem);
            orderItemPojo.setOrderID(order.getId());
            orderItemPojo.setProductId(product.getId());
            orderItemDao.insert(orderItemPojo);
            inventoryService.reduce(orderItemPojo.getProductId(), orderItemPojo.getQuantity());
        }
    }

    public List<OrderPojo> getAll() {
        return orderDao.selectAll();
    }

    public OrderData getOrderDetails(int id) throws ApiException {
        OrderPojo orderItemPojo = getCheck(id);
        List<OrderItemData> orderItems = orderItemService.get(id);
        Double total = 0.0;
        for (OrderItemData orderItem : orderItems) {
            total += orderItem.getQuantity() * orderItem.getSellingPrice();
        }
        return new OrderData(orderItemPojo, orderItems, total);
    }

    private OrderPojo getCheck(int id) throws ApiException {
        OrderPojo order = orderDao.select(id);
        if (order == null) {
            throw new ApiException("Order with given id not found");
        }
        return order;
    }

    public void update(OrderPojo p) {
        // TODO Auto-generated method stub

    }

    public void delete(int id) {
        // TODO Auto-generated method stub

    }

    public List<OrderPojo> getAllBetween(Date startingDate, Date endingDate) {
        return orderDao.selectAllBetween(startingDate, endingDate);
    }

    public List<OrderItemPojo> getOrderItemByOrder(int id) {
        return orderItemDao.selectByOrderId(id);
    }

}
