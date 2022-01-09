package com.increff.employee.model;

import java.sql.Timestamp;

import com.increff.employee.pojo.OrderItemPojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemData extends OrderItemForm {

    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public OrderItemData(OrderItemForm form) {
        this.setQuantity(form.getQuantity());
        this.setBarcode(form.getBarcode());
        this.setSellingPrice(form.getSellingPrice());
    }

    public OrderItemData(OrderItemPojo orderItem) {
        this.setId(orderItem.getId());
        this.setQuantity(orderItem.getQuantity());
        this.setSellingPrice(orderItem.getSellingPrice());
        this.setCreatedAt(orderItem.getCreatedAt());
        this.setUpdatedAt(orderItem.getUpdatedAt());
    }
}