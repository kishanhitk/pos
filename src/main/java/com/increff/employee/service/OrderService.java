package com.increff.employee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.increff.employee.dao.OrderDao;
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

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;
    // TODO: Make mutiple service calls to COntroller layer | Use only dao in
    // service layer | Add transacational in controller if needed

    public List<OrderPojo> getAll() {
        return orderDao.selectAll();
    }

    public OrderPojo createNewOrder() {
        OrderPojo order = new OrderPojo();
        return orderDao.insert(order);
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

    public void update(int orderId, List<OrderItemForm> orderItems) throws ApiException {
        if (orderItems.size() == 0) {
            throw new ApiException("Order items can not be empty");
        }
        revertInventory(orderId);
        List<OrderItemPojo> newOrderItems = new ArrayList<OrderItemPojo>();
        for (OrderItemForm orderItem : orderItems) {
            ProductPojo product = productService.getProductByBarcode(orderItem.getBarcode());
            if (product == null) {
                throw new ApiException("Product with barcode " + orderItem.getBarcode() + " not found");
            }
            OrderItemPojo orderItemPojo = ConvertUtil.convertOrderItemFormToOrderItemPojo(orderItem);
            orderItemPojo.setOrderID(orderId);
            orderItemPojo.setProductId(product.getId());
            newOrderItems.add(orderItemPojo);
            inventoryService.reduce(orderItemPojo.getProductId(), orderItemPojo.getQuantity());
        }
        orderItemService.deleteByOrderId(orderId);
        orderItemService.insertMutiple(newOrderItems);
    }

    public void revertInventory(int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList = orderItemService.selectByOrderId(orderId);
        for (OrderItemPojo orderItemPojo : orderItemPojoList) {
            inventoryService.increase(orderItemPojo.getProductId(), orderItemPojo.getQuantity());
        }
    }

    public List<OrderPojo> getAllBetween(Date startingDate, Date endingDate) {
        return orderDao.selectAllBetween(startingDate, endingDate);
    }

    public List<OrderItemPojo> getOrderItemByOrder(int id) {
        return orderItemService.selectByOrderId(id);
    }

}
