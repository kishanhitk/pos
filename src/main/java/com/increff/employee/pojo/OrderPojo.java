package com.increff.employee.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity()
@Table(name = "orders")
public class OrderPojo extends AbstractPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
