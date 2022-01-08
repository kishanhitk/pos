package com.increff.employee.pojo;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractPojo {

    // Created At
    @CreationTimestamp
    private Timestamp createdAt;

    // Updated At
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Version
    private Integer version;
}
