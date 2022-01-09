package com.increff.employee.model;

import java.sql.Timestamp;
import java.util.List;

import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderData {

    public OrderData(OrderPojo orderItemPojo, List<OrderItemPojo> orderItemPojos) {
        this.id = orderItemPojo.getId();
        this.createdAt = orderItemPojo.getCreatedAt();
        this.updatedAt = orderItemPojo.getUpdatedAt();
        List<OrderItemData> orderItemDatas = new java.util.ArrayList<>();
        for (OrderItemPojo orderItem : orderItemPojos) {
            orderItemDatas.add(new OrderItemData(orderItem));
        }
        this.orderItems = orderItemDatas;
    }

    public OrderData(OrderPojo orderItemPojo) {
        this.id = orderItemPojo.getId();
        this.createdAt = orderItemPojo.getCreatedAt();
        this.updatedAt = orderItemPojo.getUpdatedAt();
    }

    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<OrderItemData> orderItems;
    private Double totalAmount;
}
