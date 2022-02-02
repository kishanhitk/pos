package com.increff.employee.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity()
@Table(name = "order_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "orderID", "productId" }) })
public class OrderItemPojo extends AbstractPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer orderID;
    private Integer productId;
    private Integer quantity;
    private Double sellingPrice;
}
