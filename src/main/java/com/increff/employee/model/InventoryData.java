package com.increff.employee.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryData extends InventoryForm {
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String productName;
    private String productBarcode;
}
