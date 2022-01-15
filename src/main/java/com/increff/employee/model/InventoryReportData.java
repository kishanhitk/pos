package com.increff.employee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryReportData {
    private String brand;
    private String category;
    private Integer quantity;
    private Integer id;
}
