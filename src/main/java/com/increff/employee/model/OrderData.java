package com.increff.employee.model;

import java.sql.Timestamp;
import java.util.List;

import com.increff.employee.pojo.OrderPojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderData {

    public OrderData(OrderPojo orderItemPojo, List<OrderItemData> orderItemData) {
        this.id = orderItemPojo.getId();
        this.createdAt = orderItemPojo.getCreatedAt();
        this.updatedAt = orderItemPojo.getUpdatedAt();
        this.orderItems = orderItemData;
    }

    public OrderData(OrderPojo orderItemPojo) {
        this.id = orderItemPojo.getId();
        this.createdAt = orderItemPojo.getCreatedAt();
        this.updatedAt = orderItemPojo.getUpdatedAt();
    }

    public OrderData(OrderPojo orderItemPojo, List<OrderItemData> orderItems2, Double total) {
        this.id = orderItemPojo.getId();
        this.createdAt = orderItemPojo.getCreatedAt();
        this.updatedAt = orderItemPojo.getUpdatedAt();
        this.orderItems = orderItems2;
        this.totalAmount = total;

    }

    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<OrderItemData> orderItems;
    private Double totalAmount;
}
