package com.increff.employee.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity()
@Table(name = "Products", uniqueConstraints = @UniqueConstraint(columnNames = { "Barcode" }))
public class ProductPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // TODO Add unique Constraint
    @Column(name = "Barcode")
    private String barcode;
    private String name;
    private Double mrp;
    private int brandCategory;

    @Column(name = "Barcode")
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
