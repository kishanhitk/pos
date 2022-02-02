package com.increff.employee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceData {
    public String name;
    public int quantity;
    public double mrp;
    public int id;
}
