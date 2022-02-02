package com.increff.employee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BillData {
    private  int id;
    private List<InvoiceData> items;
}
