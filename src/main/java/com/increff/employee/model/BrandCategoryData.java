package com.increff.employee.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BrandCategoryData extends BrandCategoryForm {
    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
