package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.InventoryPojo;

@Repository
public class InventoryDao extends AbstractDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo p) {
        em.persist(p);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery("select p from InventoryPojo p", InventoryPojo.class);
        return query.getResultList();
    }

    @Transactional
    public void update(InventoryPojo p) {
        em.merge(p);
    }

    public InventoryPojo selectByProductId(int id) {
        TypedQuery<InventoryPojo> query = getQuery("select p from InventoryPojo p where p.productId = :id",
                InventoryPojo.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
