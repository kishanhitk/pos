package com.increff.employee.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity()
@Table(name = "Inventory")
public class InventoryPojo extends AbstractPojo {
    @Id
    @Column(nullable = false)
    private Integer id;
    // TODO: Should have its own ID too

    @Column(nullable = false)
    private Integer quantity;
}
