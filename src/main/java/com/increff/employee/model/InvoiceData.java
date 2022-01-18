package com.increff.employee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceData {
    private String name;
    private int quantity;
    private double mrp;
    private int id;

}
