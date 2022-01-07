package com.increff.employee.model;

import java.util.List;

public class OrderForm {
    private List<OrderItemForm> orderItems;

    public List<OrderItemForm> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemForm> orderItems) {
        this.orderItems = orderItems;
    }
}
