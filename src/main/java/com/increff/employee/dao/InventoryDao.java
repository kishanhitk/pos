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

    public void delete(int id) {
        em.remove(select(id));
    }

    public InventoryPojo select(int id) {
        return em.find(InventoryPojo.class, id);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery("select p from InventoryPojo p", InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo p) {
        TypedQuery<InventoryPojo> query = getQuery("select p from InventoryPojo p where productId=:productId",
                InventoryPojo.class);
        query.setParameter("productId", p.getId());
        InventoryPojo inventory = getSingle(query);
        inventory.setQuantity(p.getQuantity());
        em.merge(inventory);
    }
}
