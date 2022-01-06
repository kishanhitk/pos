package com.increff.employee.pojo;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity()
@Table(name = "Products", uniqueConstraints = @UniqueConstraint(columnNames = { "barcode" }))
public class ProductPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "barcode", updatable = false)
    private String barcode;

    private String name;

    private Double mrp;

    private int brandCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // TODO Generate UUID for barcode
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "barcode", updatable = false)
    public String getBarcode() {
        return barcode;
    }

    public int getBrandCategory() {
        return brandCategory;
    }

    public void setBrandCategory(int brandCategory) {
        this.brandCategory = brandCategory;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

}
