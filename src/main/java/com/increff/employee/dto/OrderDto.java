package com.increff.employee.dto;

import java.util.List;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.ConvertUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderDto {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackFor = ApiException.class)
    public void addOrder(List<OrderItemForm> orderItems) throws ApiException {
        validate(orderItems);
        OrderPojo orderPojo = orderService.createNewOrder();
        for (OrderItemForm orderItem : orderItems) {
            ProductPojo product = productService.getProductByBarcode(orderItem.getBarcode());
            if (product == null) {
                throw new ApiException("Product with barcode " + orderItem.getBarcode() + " not found");
            }
            OrderItemPojo orderItemPojo = ConvertUtil.convertOrderItemFormToOrderItemPojo(orderItem);
            orderItemPojo.setOrderID(orderPojo.getId());
            orderItemPojo.setProductId(product.getId());
            orderItemService.insert(orderItemPojo);
            inventoryService.reduce(orderItemPojo.getProductId(), orderItemPojo.getQuantity());
        }
    }

    public List<OrderData> getAllOrders() {
        return null;
    }

    public OrderData getOrderDetails(int id) {
        return null;
    }

    public void update(int id, List<OrderItemForm> orderItems) {
    }

    private void validate(List<OrderItemForm> orderItems) throws ApiException {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new ApiException("Order items cannot be empty");
        }
    }

}
