package com.increff.employee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemForm {
    private Integer quantity;
    private String barcode;
    private Double sellingPrice;
}
